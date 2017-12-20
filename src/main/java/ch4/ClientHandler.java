package ch4;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class ClientHandler extends CustomHeartbeatHandler {

    private Client client;

    public ClientHandler(String name, Client client) {
        super(name);
        this.client = client;
    }

    // 客户端打印接收到的消息
    @Override
    protected void handleData(ChannelHandlerContext ctx, ByteBuf byteBuf) {
        byte[] data = new byte[byteBuf.readableBytes() - 5]; // length(4bytes) + type(1byte)
        byteBuf.skipBytes(5);
        byteBuf.readBytes(data);
        String content = new String(data);
        System.out.println(name + " get content: " + content);
    }

    // 若客户端在指定的时间间隔内没有读和写操作, 则客户端会自动向服务器发送一个 PING 心跳,
    // 服务器收到 PING 心跳消息时, 需要回复一个 PONG 消息.
    @Override
    protected void handleAllIdle(ChannelHandlerContext ctx) {
        /**
         * 一般是客户端负责发送心跳的 PING 消息, 因此客户端注意关注 ALL_IDLE 事件,
         * 在这个事件触发后, 客户端需要向服务器发送 PING 消息, 告诉服务器"我还存活着".
         * 如果客户端掉线了，则无法发送PING消息，10s内服务端无法收到消息，进而可以认为客户端掉线
         */
        super.handleAllIdle(ctx);
        sendPingMsg(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        client.doConnect();
    }
}
