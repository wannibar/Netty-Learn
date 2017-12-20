package ch17;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.Random;

public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int cnt = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

        byte[] buf = new byte[msg.readableBytes()];
        msg.readBytes(buf); //把数据复制到buf中

        String s = new String(buf, Charset.forName("UTF-8"));
        System.out.println("server recieve " + s + " and cnt is " + (++cnt));

        ByteBuf respbuf = Unpooled.copiedBuffer("" + new Random().nextInt(10), Charset.forName("UTF-8"));
        ctx.channel().writeAndFlush(respbuf);
    }
}
