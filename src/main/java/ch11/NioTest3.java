package ch11;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * Created by WQS on 2017/8/16.
 * Mail: 1027738387@qq.com
 * Github: https://github.com/wannibar
 */
public class NioTest3 {
    public static void main(String[] args) throws IOException {
        FileOutputStream out = new FileOutputStream("src/main/java/ch11/NioTest3.txt");
        FileChannel channel = out.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        byte[] msg = "hello world".getBytes(Charset.forName("UTF-8"));
        for (int i = 0; i < msg.length; ++i) {
            buffer.put(msg[i]);
        }
        buffer.flip();

        channel.write(buffer);

        out.close();
    }
}
