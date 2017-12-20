package ch16;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * Created by WQS on 2017/9/1.
 * Mail: 1027738387@qq.com
 * Github: https://github.com/wannibar
 */
public class MyLong2StringDecoder extends MessageToMessageDecoder<Long> {

    @Override
    protected void decode(ChannelHandlerContext ctx, Long msg, List<Object> out) throws Exception {
        System.out.println("MyLong2StringDecoder called");
        out.add(String.valueOf(msg));
    }
}
