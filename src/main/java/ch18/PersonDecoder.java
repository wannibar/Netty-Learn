package ch18;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * Created by WQS on 2017/9/1.
 * Mail: 1027738387@qq.com
 * Github: https://github.com/wannibar
 */
public class PersonDecoder extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("PersonDecoder.decode() invoked");
        int length = in.readInt();
        byte[] content = new byte[length];
        in.readBytes(content);  //读取content.length这么长

        PersonProtocol p = new PersonProtocol();
        p.setContent(content);
        p.setLength(length);
        out.add(p);
    }
}
