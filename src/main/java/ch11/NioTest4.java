package ch11;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by WQS on 2017/8/17.
 * Mail: 1027738387@qq.com
 * Github: https://github.com/wannibar
 */
public class NioTest4 {
    public static void main(String[] args) throws Exception {

        FileInputStream in = new FileInputStream("src/main/java/ch11/nio4_input");
        FileOutputStream out = new FileOutputStream("src/main/java/ch11/nio4_output");

        FileChannel inChannel = in.getChannel();
        FileChannel outChannel = out.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(4);
        while (true) {

            buffer.clear(); // this line  is very important // not flip

            int readCnt = inChannel.read(buffer);
            System.out.println("Real readCnt: " + readCnt);
            if (readCnt == -1) {
                break;
            }

            buffer.flip();
            int writeCnt = outChannel.write(buffer);
            System.out.println("Real writeCnt: " + writeCnt);
        }

        in.close();
        out.close();

    }
}
