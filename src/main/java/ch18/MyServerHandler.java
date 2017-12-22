package ch18;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<PersonProtocol> {

    private int cnt = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PersonProtocol msg) throws Exception {
        int length = msg.getLength();
        byte[] content = msg.getContent();

        System.out.println("server recieve length  " + length);
        System.out.println("server recieve content " + new String(content, Charset.forName("UTF-8")));
        System.out.println("server recieve msgCnt  " + ++cnt);

        String uuid = UUID.randomUUID().toString();
        int responseLength = uuid.getBytes(Charset.forName("UTF-8")).length;
        byte[] responseContent = uuid.getBytes(Charset.forName("UTF-8"));

        PersonProtocol p = new PersonProtocol();
        p.setLength(responseLength);
        p.setContent(responseContent);
        ctx.channel().writeAndFlush(p);
    }
}
