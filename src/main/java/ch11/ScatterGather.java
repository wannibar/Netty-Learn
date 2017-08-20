package ch11;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * Created by WQS on 2017/8/17.
 * Mail: 1027738387@qq.com
 * Github: https://github.com/wannibar
 */

/**
 * 可以用于包含消息头和消息体这样的数据,消息头放入一个buffer,消息体放入另外一个buffer
 */

// telnet localhost 8899
public class ScatterGather {
    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(8899);
        serverSocketChannel.bind(address);

        int msgLen = 2 + 3 + 4;
        ByteBuffer[] buffers = new ByteBuffer[3];
        buffers[0] = ByteBuffer.allocate(2);
        buffers[1] = ByteBuffer.allocate(3);
        buffers[2] = ByteBuffer.allocate(4);

        SocketChannel socketChannel = serverSocketChannel.accept();
        while (true) {

            int bytesRead = 0;
            while ((bytesRead) < msgLen) {
                long r = socketChannel.read(buffers);
                bytesRead += r;
                System.out.println("bytesRead:" + bytesRead);
            }

            Arrays.asList(buffers).forEach(Buffer::flip);

            long bytesWrite = 0;
            while (bytesWrite < msgLen) {
                long r = socketChannel.write(buffers);
                bytesWrite += r;
            }

            Arrays.asList(buffers).forEach(Buffer::clear);
            System.out.println("read:write -- > " + bytesRead + ":" + bytesWrite);
        }
    }
}
