package ch13;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by WQS on 2017/9/1.
 * Mail: 1027738387@qq.com
 * Github: https://github.com/wannibar
 */

// 测试0拷贝
public class NewServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().setReuseAddress(true);
        serverSocketChannel.socket().bind(new InetSocketAddress(8888));

        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(true);  // 改成阻塞(默认就是阻塞)

            int readCnt = 0;
            while (readCnt != -1) {
                try {
                    readCnt = socketChannel.read(byteBuffer);
                } catch (Exception e) {
                    //e.printStackTrace();
                    socketChannel.close();
                    readCnt = -1;
                }
                byteBuffer.rewind();
            }
        }
    }
}
