package ch11;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by WQS on 2017/8/16.
 * Mail: 1027738387@qq.com
 * Github: https://github.com/wannibar
 */
public class NioTest2 {
    public static void main(String[] args) throws IOException {
        FileInputStream in = new FileInputStream("src/main/java/ch11/NioTest2.txt");
        FileChannel channel = in.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        channel.read(buffer);

        buffer.flip();
        while (buffer.hasRemaining()) {
            byte b = buffer.get();
            System.out.print((char) b);
        }

        in.close();
    }
}
