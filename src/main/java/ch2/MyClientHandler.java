package ch2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

public class MyClientHandler extends SimpleChannelInboundHandler<String> {

    // 服务器端发送数据到客户端时会调用这个方法
    // msg:服务器端发送给客户端的内容
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        Thread.sleep(3000);

        System.out.println(ctx.channel().remoteAddress() + ":" + msg);  //打印服务器端的地址和内容

        // 发回给服务器端的内容
        ctx.channel().writeAndFlush("from client:" + LocalDateTime.now());

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 关闭连接
        cause.printStackTrace();
        ctx.close();
    }

    // 这里客户端先发送一个消息出去,这样才能使得服务器端和客户端正常通信
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + ":");  //打印服务器端的地址和内容
        // 发回给服务器端的内容
        ctx.channel().writeAndFlush("client....");
    }

}
