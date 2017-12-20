package ch12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by WQS on 2017/8/18.
 * Mail: 1027738387@qq.com
 * Github: https://github.com/wannibar
 */
public class NioClient {
    public static void main(String[] args) throws IOException {

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ);

        socketChannel.connect(new InetSocketAddress("localhost", 9999));
        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            selectionKeys.forEach(selectionKey -> {
                if (selectionKey.isConnectable()) {
                    SocketChannel sc = (SocketChannel) selectionKey.channel();
                    if (sc.isConnectionPending()) {
                        try {
                            sc.finishConnect();
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            buffer.put((LocalTime.now().toString() + ": 连接建立").getBytes());
                            buffer.flip();
                            sc.write(buffer);

                            ExecutorService executorService = Executors.newSingleThreadExecutor();
                            executorService.submit(() -> {
                                while (true) {
                                    try {
                                        buffer.clear();
                                        InputStreamReader isr = new InputStreamReader(System.in);
                                        BufferedReader br = new BufferedReader(isr);

                                        String sendMsg = br.readLine();
                                        buffer.put(sendMsg.getBytes());
                                        buffer.flip();
                                        sc.write(buffer);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        System.out.println("in thread caught exception, so Close Client:" + sc);
                                        try {
                                            sc.close();
                                        } catch (IOException e2) {
                                            System.out.println("close exception...");
                                            e2.printStackTrace();
                                        }
                                    } finally {
                                    }
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (selectionKey.isReadable()) {
                    SocketChannel rsc = (SocketChannel) selectionKey.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    System.out.println(rsc);
                    try {
                        int cnt = rsc.read(buffer);
                        if (cnt > 0) {
                            String recvMsg = new String(buffer.array(), 0, cnt);
                            System.out.println("Recv:" + recvMsg);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("exception caught, so Close Client:" + rsc);
                        try {
                            rsc.close();
                        } catch (IOException e2) {
                            System.out.println("close exception...");
                            e2.printStackTrace();
                        }
                    }
                }
            });

            selectionKeys.clear();
        }
    }
}
