package ch15;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

public class MyServerHandler extends SimpleChannelInboundHandler<Long> {

    // 客户端发送数据到服务器端时会调用这个方法
    // msg:客户端发过来的请求对象
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {


        Thread.sleep(3000); // 仅仅是为了测试,不输出太多信息用

        // 服务器端收到客户端的消息
        System.out.print("MyServerHandler channelRead & recieve from " + ctx.channel().remoteAddress() + ":" + msg);

        // 返回给客户端的消息

        Long r = new Random().nextLong();
        System.out.println(" and server gen :" + r);
        ChannelFuture cf = ctx.channel().writeAndFlush(r);

        // ctx.writeAndFlush("another write and flush way..."); // 事件处理流更短

        /*
        cf.addListener((f) -> {
            System.out.println("write end....");
        });
        */
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 发生异常关闭连接
        cause.printStackTrace();
        ctx.close();
    }

    // 或者在客户端先发起一条消息
//    @Override
//    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
//        ctx.writeAndFlush(0L);
//    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("MyHandler Active");
    }

}
