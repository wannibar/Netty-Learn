package ch3;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {

    // 必须为static 否则,每次建立一个连接,就会调用ChannelInitializer的initChannel方法
    // 从而产生一个新的MyChatServerHandler,如果channelGroup不为static,那么每次建立连接,都会产生一个新的ChannelGroup
    // 显然没有任何意义,我们这里是要收集所有的Channel
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    // 服务器端收到任意一个客户端发送的信息,channelRead0会被调用
    // 任意一个客户端发送消息出去,服务器端负责把该消息发送给所有在线的客户端
    // 同时区分一下自己和别的客户端
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();    //发送消息的连接
        for (Channel c : channelGroup)
            if (c == channel)
                c.writeAndFlush("【自己】" + ":发送消息:" + msg + "\n");
            else
                c.writeAndFlush(channel.remoteAddress() + ":发送消息:" + msg + "\n");
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("Server handlerAdded() ");

        //add之前广播上线信息
        channelGroup.writeAndFlush("【服务器】:" + channel.remoteAddress() + "【上线】\n");

        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        // 可以不写下面这句代码,因为Netty在连接断开后,会自动调用remove
        channelGroup.remove(channel); //连接断开后channelGroup会自动remove已经断开的连接

        //remove后广播下线信息
        channelGroup.writeAndFlush("【服务器】:" + channel.remoteAddress() + "【下线】\n");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + "【上线】");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + "【下线】");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
