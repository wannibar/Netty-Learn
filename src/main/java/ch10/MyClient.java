package ch10;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by WQS on 2017/8/14.
 * Mail: 1027738387@qq.com
 * Github: https://github.com/wannibar
 */
public class MyClient {

    private final ManagedChannel channel;
    // 使用阻塞方式
    private final StudentServiceGrpc.StudentServiceBlockingStub blockingStub;
    private final StudentServiceGrpc.StudentServiceStub asyncStub;

    public MyClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext(true));
    }

    MyClient(ManagedChannelBuilder<?> channelBuilder) {
        channel = channelBuilder.build();
        blockingStub = StudentServiceGrpc.newBlockingStub(channel);
        asyncStub = StudentServiceGrpc.newStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void greet(String name) {
        MyRequest request = MyRequest.newBuilder().setUsername(name).build();
        MyResponse response;
        try {
            response = blockingStub.getRealNameByUserName(request);
            System.out.println(response.getRealname());
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
            return;
        }
    }

    public static void main(String[] args) throws Exception {
        MyClient client = new MyClient("localhost", 50051);

        System.out.println("-----------测试分割线-----------");
        try {
            String user = "world";
            client.greet(user);

            System.out.println("-----------测试分割线-----------");

            // 服务器端返回stream实际上返回的是一个iterator
            Iterator<StudentResp> it = client.blockingStub.getStudentsByAge(
                    StudentReq.newBuilder().setAge(20).build());
            while (it.hasNext()) {
                StudentResp resp = it.next();
                System.out.println(resp.getName() + ":" + resp.getCity());
            }

            System.out.println("-----------测试分割线-----------");

            client.getStudentsWrapperByAges();

            System.out.println("-----------测试分割线-----------");

            client.biTalk();

            System.out.println("-----------测试分割线-----------");
        } finally {
            client.shutdown();
        }
    }


    public void getStudentsWrapperByAges() throws InterruptedException {

        final CountDownLatch finishLatch = new CountDownLatch(1);
        StreamObserver<StudentRespList> responseObserver = new StreamObserver<StudentRespList>() {
            @Override
            public void onNext(StudentRespList studentRespList) {
                studentRespList.getStudentRespListList()
                        .stream()
                        .forEach((x) -> System.out.println(x.getAge() + ":" + x.getName() + ":" + x.getCity()));
                System.out.println("......");
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("client getStudentsWrapperByAges ERROR");
                finishLatch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("client onCompleted");
                finishLatch.countDown();
            }
        };

        StreamObserver<StudentReq> requestObserver = asyncStub.getStudentsWrapperByAges(responseObserver);
        // Send param ages  randomly...
        Random random = new Random();
        try {
            for (int i = 0; i < 5; ++i) {
                int age = random.nextInt(100);
                System.out.println("client send age:" + age);
                StudentReq req = StudentReq.newBuilder().setAge(age).build();
                requestObserver.onNext(req);
                Thread.sleep(random.nextInt(1000));
                if (finishLatch.getCount() == 0) {
                    // RPC completed or errored before we finished sending.
                    // Sending further requests won't error, but they will just be thrown away.
                    return;
                }
            }
        } catch (RuntimeException e) {
            // Cancel RPC
            requestObserver.onError(e);
            throw e;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Mark the end of requests
        requestObserver.onCompleted();

        // Receiving happens asynchronously
        if (!finishLatch.await(1, TimeUnit.MINUTES)) {
            System.out.println("getStudentsWrapperByAges can not finish within 1 minutes");
        }
    }

    public void biTalk() {
        StreamObserver<StreamResp> responseObserver = new StreamObserver<StreamResp>() {
            @Override
            public void onNext(StreamResp value) {
                System.out.println("客户端收到信息:" + value.getRespInfo());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("biTalk ERROR");
            }

            @Override
            public void onCompleted() {
                System.out.println("completed");
            }
        };

        StreamObserver<StreamReq> requestObserver = asyncStub.biTalk(responseObserver);

        for (int i = 0; i < 10; ++i) {
            requestObserver.onNext(StreamReq.newBuilder().setReqInfo("hello:" + i).build());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        requestObserver.onCompleted();
    }
}
