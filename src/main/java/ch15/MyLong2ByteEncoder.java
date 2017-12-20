package ch15;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by WQS on 2017/9/1.
 * Mail: 1027738387@qq.com
 * Github: https://github.com/wannibar
 */
public class MyLong2ByteEncoder extends MessageToByteEncoder<Long> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("MyLong2ByteEncoder called and msg is " + msg);
        out.writeLong(msg);
    }


}
