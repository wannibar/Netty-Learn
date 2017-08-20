package ch10;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * Created by WQS on 2017/8/14.
 * Mail: 1027738387@qq.com
 * Github: https://github.com/wannibar
 */
public class MyServer {

    private Server server;

    private void start() throws IOException {
    /* The port on which the server should run */
        int port = 50051;
        server = ServerBuilder.forPort(port)
                .addService(new StudentServiceImpl())
                .build()
                .start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Use stderr here since the logger may have been reset by its JVM shutdown hook.
            // can do some clean stuff ....
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            MyServer.this.stop();
            System.err.println("*** server shut down");
        }));
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            // server.awaitTermination(3000, TimeUnit.MICROSECONDS); // use this code can see shutdownHook
            server.awaitTermination();
        }
    }

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final MyServer server = new MyServer();
        server.start();
        server.blockUntilShutdown();
    }

}
