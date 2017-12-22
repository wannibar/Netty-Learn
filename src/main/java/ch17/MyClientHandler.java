package ch17;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;


public class MyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int cnt = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

        byte[] buf = new byte[msg.readableBytes()];
        msg.readBytes(buf);
        String s = new String(buf, Charset.forName("UTF-8"));
        System.out.println("client recieve  " + s + " and cnt is " + (++cnt));

    }

    // 这里客户端先发送一个消息出去,这样才能使得服务器端和客户端正常通信
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            ByteBuf buf = Unpooled.copiedBuffer("send from client " + i, Charset.forName("UTF-8"));
            ctx.channel().writeAndFlush(buf);
        }
    }
}
