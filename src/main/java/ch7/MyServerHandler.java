package ch7;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

// 服务器端处理客户端发送来的Teacher,随机返回一个Student对象给客户端
public class MyServerHandler extends SimpleChannelInboundHandler<DataInfo.Teacher> {

    // 客户端发送数据到服务器端时会调用这个方法
    // msg:客户端发过来的请求对象
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataInfo.Teacher msg) throws Exception {

        // 服务器端收到客户端的消息
        System.out.println(msg+"\n");

        // 返回给客户端的消息
        ctx.channel().writeAndFlush(msg.getStudents(new Random().nextInt(msg.getStudentsCount())));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 发生异常关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
