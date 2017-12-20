package ch4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

// 参考：https://segmentfault.com/a/1190000006931568
public class ServerHandler extends CustomHeartbeatHandler {

    public ServerHandler() {
        super("server");
    }

    // 服务端原封不动的把数据发回给客户端
    @Override
    protected void handleData(ChannelHandlerContext ctx, ByteBuf buf) {

        byte[] data = new byte[buf.readableBytes() - 5];
        ByteBuf responseBuf = Unpooled.copiedBuffer(buf);
        buf.skipBytes(5);
        buf.readBytes(data);
        String content = new String(data);
        System.out.println(name + " get content: " + content);
        ctx.writeAndFlush(responseBuf);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        if (msg.getByte(4) == PING_MSG) {
            // server不回复PONG信息，减轻服务端压力
        } else {
            handleData(ctx, msg);
        }
    }

    // 客户端每隔5s,如果没有读写操作，则客户端发送一个PING消息到服务端
    // 服务端如果在10s内都没有读到数据，可以认为客户端掉线了，此时关闭客户端
    @Override
    protected void handleReaderIdle(ChannelHandlerContext ctx) {
        super.handleReaderIdle(ctx);
        System.err.println("---client " + ctx.channel().remoteAddress().toString() + " reader timeout, close it---");
        ctx.close();
    }
}
