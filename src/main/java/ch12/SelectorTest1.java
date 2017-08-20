package ch12;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by WQS on 2017/8/17.
 * Mail: 1027738387@qq.com
 * Github: https://github.com/wannibar
 */
public class SelectorTest1 {
    public static void main(String[] args) throws IOException {
        int[] ports = new int[5];
        ports[0] = 5000;
        ports[1] = 50001;
        ports[2] = 50002;
        ports[3] = 50003;
        ports[4] = 50004;

        Selector selector = Selector.open();

        // 绑定端口
        for (int port : ports) {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            ServerSocket serverSocket = serverSocketChannel.socket();
            serverSocket.bind(new InetSocketAddress(port));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("监听端口:" + port);
        }

        while (true) {
            int keysCnt = selector.select();
            System.out.println("select() returns:" + keysCnt);

            Set<SelectionKey> selectionKeySet = selector.selectedKeys();
            Iterator<SelectionKey> it = selectionKeySet.iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                if (key.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = channel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    it.remove();
                    System.out.println("获得客户端连接：" + socketChannel);
                } else if (key.isReadable()) {
                    SocketChannel sc = (SocketChannel) key.channel();

                    int bytesRead = 0;
                    while (true) {
                        ByteBuffer buffer = ByteBuffer.allocate(512);
                        buffer.clear();

                        int readCnt = sc.read(buffer);
                        if (readCnt < 0)
                            break;
                        buffer.flip();
                        int writeCnt = sc.write(buffer);
                        bytesRead += readCnt;
                    }
                    System.out.println("bytes read:" + bytesRead + ":来自于:" + sc);
                    it.remove();
                }
            }
        }
    }
}
