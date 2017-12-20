package ch14;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by WQS on 2017/9/1.
 * Mail: 1027738387@qq.com
 * Github: https://github.com/wannibar
 */
public class ByteBufTest {
    public static void main(String[] args) {
        ByteBuf buffer = Unpooled.buffer(10);

        for (int i = 0; i < 10; ++i) {
            buffer.writeByte(0x30 + i);
        }

        for (int i = 0; i < buffer.capacity(); ++i){
            System.out.println((char)buffer.getByte(i));
        }
    }
}
