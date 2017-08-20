package ch11;

import java.nio.ByteBuffer;

/**
 * Created by WQS on 2017/8/17.
 * Mail: 1027738387@qq.com
 * Github: https://github.com/wannibar
 */
public class NioTest6 {
    public static void main(String[] args) {

        ByteBuffer buffer = ByteBuffer.allocate(10);

        for (int i = 0; i < 10; ++i)
            buffer.put((byte) i);


        for (int i = 0; i < buffer.capacity(); ++i) {
            System.out.println((int) buffer.get(i));
        }


        System.out.println("使用slice.............");

        buffer.position(2);
        buffer.limit(6);
        ByteBuffer shareBuffer = buffer.slice();
        System.out.println(shareBuffer.position() + ":" + shareBuffer.limit() + ":" + shareBuffer.capacity());
        for (int i = 0; i < shareBuffer.capacity(); ++i) {
            byte x = shareBuffer.get(i);
            shareBuffer.put(i, (byte) (x * 2));
        }


        buffer.limit(buffer.capacity());
        for (int i = 0; i < buffer.capacity(); ++i) {
            System.out.println((int) buffer.get(i));
        }

        ByteBuffer readOnlyBuff = buffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuff.getClass());
    }
}
