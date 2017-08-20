package ch11;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * Created by WQS on 2017/8/16.
 * Mail: 1027738387@qq.com
 * Github: https://github.com/wannibar
 */
public class NioTest {
    public static void main(String[] args) {

        IntBuffer buffer = IntBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); ++i) {
            int random = new SecureRandom().nextInt(100);
            System.out.println(random);
            buffer.put(random);
        }

        System.out.println("===================");
        buffer.flip();
        while(buffer.hasRemaining())
            System.out.println(buffer.get());
    }
}
