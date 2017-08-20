package ch10;

import io.grpc.stub.StreamObserver;

import java.util.UUID;

/**
 * Created by WQS on 2017/8/14.
 * Mail: 1027738387@qq.com
 * Github: https://github.com/wannibar
 */
public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {

    // 返回给客户端的结果是通过responseObserver返回
    @Override
    public void getRealNameByUserName(MyRequest request, StreamObserver<MyResponse> responseObserver) {
        MyResponse resp = MyResponse.newBuilder()
                .setRealname(request.getUsername().toUpperCase())
                .build();
        System.out.println("服务端收到:" + request.getUsername());
        // 非流式数据只能调用一次onNext
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    // 服务器端返回一个stream给客户端,客户端发起普通的请求
    @Override
    public void getStudentsByAge(StudentReq request, StreamObserver<StudentResp> responseObserver) {
        System.out.println("服务器端接收到的参数:" + request.getAge());

        responseObserver.onNext(StudentResp.newBuilder()
                .setAge(request.getAge())
                .setName("张三1")
                .setCity("Beijing1")
                .build());

        responseObserver.onNext(StudentResp.newBuilder()
                .setAge(request.getAge())
                .setName("张三2")
                .setCity("Beijing2")
                .build());

        responseObserver.onNext(StudentResp.newBuilder()
                .setAge(request.getAge())
                .setName("张三3")
                .setCity("Beijing3")
                .build());

        responseObserver.onNext(StudentResp.newBuilder()
                .setAge(request.getAge())
                .setName("张三4")
                .setCity("Beijing4")
                .build());

        responseObserver.onCompleted();
    }


    // 客户端发起stream请求,服务端返回普通的响应
    @Override
    public StreamObserver<StudentReq> getStudentsWrapperByAges(StreamObserver<StudentRespList> responseObserver) {
        return new StreamObserver<StudentReq>() {
            int ageTotal;

            // 接收到客户端stream发过来的所有内容
            @Override
            public void onNext(StudentReq value) {
                System.out.println("服务器端接收到:" + value.getAge());
                ageTotal += value.getAge();
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("getStudentsWrapperByAges ERROR");
            }

            @Override
            public void onCompleted() {
                StudentRespList.Builder builder = StudentRespList.newBuilder();
                for (int i = 0; i < 3; ++i) {
                    builder.addStudentRespList(StudentResp.newBuilder()
                            .setAge(ageTotal).setName("zhangsan-" + i).setCity("bj-" + i).build());
                }
                responseObserver.onNext(builder.build());
                responseObserver.onCompleted();
            }
        };
    }

    // 双向流数据传递
    @Override
    public StreamObserver<StreamReq> biTalk(StreamObserver<StreamResp> responseObserver) {
        return new StreamObserver<StreamReq>() {
            @Override
            public void onNext(StreamReq value) {
                System.out.println("[双向流]服务器接收到参数:" + value.getReqInfo());
                responseObserver.onNext(StreamResp.newBuilder().setRespInfo(UUID.randomUUID().toString()).build());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Server biTalk ERROR");
            }

            @Override
            public void onCompleted() {
                System.out.println("Server completed");
                responseObserver.onCompleted();
            }
        };
    }
}
