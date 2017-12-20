package ch12;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by WQS on 2017/8/18.
 * Mail: 1027738387@qq.com
 * Github: https://github.com/wannibar
 */

public class NioServer {

    private static Map<SocketChannel, String> clientMap = new HashMap<>();

    public static void main(String[] args) throws Exception {

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(9999));
        ssc.configureBlocking(false);

        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("监听端口:9999");

        while (true) {
            int keysCnt = selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            selectionKeys.forEach(key -> {
                final SocketChannel client;
                try {
                    if (key.isAcceptable()) {
                        ServerSocketChannel ss = (ServerSocketChannel) key.channel();
                        client = ss.accept();
                        if (client != null) {
                            client.configureBlocking(false);
                            client.register(selector, SelectionKey.OP_READ);
                            String clientID = "[" + UUID.randomUUID().toString() + "]";
                            clientMap.put(client, clientID);
                        }
                    } else if (key.isReadable()) {
                        SocketChannel sc = null;
                        try {
                            sc = (SocketChannel) key.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(1024);

                            int count = sc.read(buffer);
                            if (count > 0) {
                                buffer.flip();
                                String recvMsg = String.valueOf(Charset.forName("UTF-8").decode(buffer).array());
                                System.out.println("client count: " + clientMap.size());
                                System.out.println(clientMap.get(sc) + " recv " + recvMsg);

                                String senderID = "";
                                for (Map.Entry<SocketChannel, String> entry : clientMap.entrySet()) {
                                    if (entry.getKey() == sc) {
                                        senderID = entry.getValue();
                                        break;
                                    }
                                }

                                // 向每个在线的客户端发送数据
                                for (Map.Entry<SocketChannel, String> entry : clientMap.entrySet()) {
                                    SocketChannel v = entry.getKey();
                                    ByteBuffer buf = ByteBuffer.allocate(1024);
                                    buf.put((senderID + ":" + recvMsg).getBytes());
                                    buf.flip();
                                    v.write(buf);
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("Close Client:" + sc);
                            clientMap.remove(sc);
                            try {
                                sc.close();
                            } catch (IOException e2) {
                                System.out.println("close exception...");
                                e2.printStackTrace();
                            }
                        } finally {
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            selectionKeys.clear();
        }
    }
}
