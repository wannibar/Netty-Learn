package ch7;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

// 服务器端发送给客户端的数据类型是Student
public class MyClientHandler extends SimpleChannelInboundHandler<DataInfo.Student> {

    // 服务器端发送数据到客户端时会调用这个方法
    // msg:服务器端发送给客户端的内容
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataInfo.Student msg) throws Exception {
        //打印服务器端发送过来的消息
        System.out.println(ctx.channel().remoteAddress() + ":" + msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + ":");  //打印服务器端的地址和内容
        // 发回给服务器端的内容
        DataInfo.Teacher teacher = DataInfo.Teacher.newBuilder()
                .setName("wqs").setAge(23).setAddress("大蜀山")
                .addStudents(0, DataInfo.Student.newBuilder().setName("A").setAge(0).setAddress("A0").build())
                .addStudents(1, DataInfo.Student.newBuilder().setName("A").setAge(1).setAddress("A1").build())
                .addStudents(2, DataInfo.Student.newBuilder().setName("A").setAge(2).setAddress("A2").build())
                .addStudents(3, DataInfo.Student.newBuilder().setName("A").setAge(3).setAddress("A3").build())
                .addStudents(4, DataInfo.Student.newBuilder().setName("A").setAge(4).setAddress("A4").build())
                .addStudents(5, DataInfo.Student.newBuilder().setName("A").setAge(5).setAddress("A5").build())
                .addStudents(6, DataInfo.Student.newBuilder().setName("A").setAge(6).setAddress("A6").build())
                .build();
        ctx.channel().writeAndFlush(teacher);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
