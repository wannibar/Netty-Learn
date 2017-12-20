package ch14;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by WQS on 2017/9/1.
 * Mail: 1027738387@qq.com
 * Github: https://github.com/wannibar
 */
public class ByteBufTest2 {
    public static void main(String[] args) {
        CompositeByteBuf cbf = Unpooled.compositeBuffer();

        ByteBuf hbf = Unpooled.buffer(10); // JVM堆缓冲
        ByteBuf dbf = Unpooled.directBuffer(8); // 长度为8的缓冲

        cbf.addComponents(hbf, dbf);
        //cbf.removeComponent(0);

        cbf.forEach(System.out::println);
    }
}
