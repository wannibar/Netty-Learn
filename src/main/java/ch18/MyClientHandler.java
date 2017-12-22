package ch18;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;


public class MyClientHandler extends SimpleChannelInboundHandler<PersonProtocol> {

    private int cnt = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PersonProtocol msg) throws Exception {
        int length = msg.getLength();
        byte[] content = msg.getContent();

        System.out.println("server recieve length  " + length);
        System.out.println("server recieve content " + new String(content, Charset.forName("UTF-8")));
        System.out.println("server recieve msgCnt  " + ++cnt);
    }

    // 这里客户端先发送一个消息出去,这样才能使得服务器端和客户端正常通信
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            PersonProtocol p = new PersonProtocol();
            String msg = "client to server & msg is " + i;
            byte[] msgBytes = msg.getBytes(Charset.forName("UTF-8"));
            p.setContent(msgBytes);
            p.setLength(msgBytes.length);
            ctx.channel().writeAndFlush(p);
        }
    }
}
