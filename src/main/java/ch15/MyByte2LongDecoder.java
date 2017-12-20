package ch15;

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


        if (in.readableBytes() >= 8) {
            out.add(in.readLong());
        } else {
            System.out.println("bytebuf does not have enough bytes to Long");
        }

        System.out.println(" out param is  " + out);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.print("MyByte2LongDecoder channelRead");
        super.channelRead(ctx, msg);
    }
}
