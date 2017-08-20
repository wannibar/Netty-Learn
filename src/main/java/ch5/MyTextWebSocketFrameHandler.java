package ch5;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

public class MyTextWebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {

        if (msg instanceof TextWebSocketFrame) {

            // 收到客户端发送过来的消息
            String request = ((TextWebSocketFrame) msg).text();

            System.out.println("收到消息:" + request);
            // 发送给客户端的信息
            ctx.channel().writeAndFlush(new TextWebSocketFrame(request.toUpperCase()));

        } else {
            System.out.println("not TextWebSocketFrame");
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // 每个channel都有一个全局唯一的id值
        System.out.println("handler added!" + ctx.channel().id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // 每个channel都有一个全局唯一的id值
        System.out.println("handler removed!" + ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
