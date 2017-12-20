package ch16;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by WQS on 2017/9/1.
 * Mail: 1027738387@qq.com
 * Github: https://github.com/wannibar
 */
public class MyByte2LongDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("decode handler is called");
        System.out.println("can read " + in.readableBytes());

        // 一定要判断字节是否足够
        if (in.readableBytes() >= 8) {
            out.add(in.readLong());
        } else {
            System.out.println("bytebuf does not have enough bytes to Long");
        }
    }
}
