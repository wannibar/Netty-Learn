package ch14;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * Created by WQS on 2017/9/1.
 * Mail: 1027738387@qq.com
 * Github: https://github.com/wannibar
 */
public class ByteBufTest1 {
    public static void main(String[] args) {

        /**
         * ByteBuf有4种类型：是否在JVM堆上和是否pooled
         */

        ByteBuf buf = Unpooled.copiedBuffer("我hello world", Charset.forName("UTF-8"));

        // 如果返回值为真,则是在JVM堆上分配(背后是字节数组),返回值为假,则是在堆外分配
        if (buf.hasArray()) { // Must if
            byte[] content = buf.array();
            System.out.println(new String(content, Charset.forName("UTF-8")));
            System.out.println(buf);

            System.out.println(buf.arrayOffset());
            System.out.println(buf.readerIndex());
            System.out.println(buf.writerIndex());

            System.out.println("capactity:" + buf.capacity());
            System.out.println("readable bytes count:" + buf.readableBytes());

            for (int i = 0; i < buf.readableBytes(); i++) {
                System.out.println((char) buf.getByte(i));
            }

            // 取前4个字节
            System.out.println(buf.getCharSequence(0, 4, Charset.forName("UTF-8")));
            System.out.println(buf.getCharSequence(4,6,Charset.forName("UTF-8")));
        }

    }
}
