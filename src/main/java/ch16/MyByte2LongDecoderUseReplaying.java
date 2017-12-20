package ch16;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * Created by WQS on 2017/9/1.
 * Mail: 1027738387@qq.com
 * Github: https://github.com/wannibar
 */

// Void:不需要状态管理
public class MyByte2LongDecoderUseReplaying extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByte2LongDecoderUseReplaying is called.....");
        // 使用ReplayingDecoder无需判断字节是否足够
        out.add(in.readLong());
    }
}
