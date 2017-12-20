package ch16;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

public class MyClientHandler extends SimpleChannelInboundHandler<Long> {

    // 服务器端发送数据到客户端时会调用这个方法
    // msg:服务器端发送给客户端的内容
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {

        // 只是方便的放慢速度,实际环境中严禁做这样耗时操作
        Thread.sleep(3000);

        System.out.println("recive from server " + ctx.channel().remoteAddress() + ":" + msg);  //打印服务器端的地址和内容

        // 发回给服务器端的内容
        Long r = new Random().nextLong();
        System.out.println("client gen a random Long num :" + r);
        ctx.channel().writeAndFlush(r);
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
        System.out.println("to server " + ctx.channel().remoteAddress() + ":");  //打印服务器端的地址和内容

        // 发送一个ByteBuf可以绕过不能发送字符串的情况(因为最终都是发送字节)
        ctx.channel().writeAndFlush(1L);
    }

}
