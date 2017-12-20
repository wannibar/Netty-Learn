package ch4;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

public abstract class CustomHeartbeatHandler extends SimpleChannelInboundHandler<ByteBuf> {
    public static final byte PING_MSG = 1;
    public static final byte PONG_MSG = 2;
    public static final byte CUSTOM_MSG = 3;

    protected String name;
    private int heartBeatCount = 0;

    public CustomHeartbeatHandler(String name) {
        this.name = name;
    }



    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        if (msg.getByte(4) == PING_MSG) {
            sendPongMsg(ctx);
        } else if (msg.getByte(4) == PONG_MSG) {
            System.out.println(name + " get PONG_MSG from " + ctx.channel().remoteAddress());
        } else {
            handleData(ctx, msg);
        }
    }


    protected void sendPingMsg(ChannelHandlerContext ctx) {
        ByteBuf buf = ctx.alloc().buffer(5);
        buf.writeInt(5);
        buf.writeByte(PING_MSG);
        ctx.writeAndFlush(buf);
        heartBeatCount++;
        System.out.println(name + " send PING_MSG to " + ctx.channel().remoteAddress() + ", heart beat count is " + heartBeatCount);
    }


    private void sendPongMsg(ChannelHandlerContext context) {
        ByteBuf buf = context.alloc().buffer(5);
        buf.writeInt(5);
        buf.writeByte(PONG_MSG);
        context.channel().writeAndFlush(buf);
        heartBeatCount++;
        System.out.println(name + " sent PONG_MSG to " + context.channel().remoteAddress() + ", heart beat count is " + heartBeatCount);
    }

    protected abstract void handleData(ChannelHandlerContext ctx, ByteBuf buf);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // IdleStateHandler 所产生的 IdleStateEvent 的处理逻辑.
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case READER_IDLE:
                    handleReaderIdle(ctx);
                    break;
                case WRITER_IDLE:
                    handleWriterIdle(ctx);
                    break;
                case ALL_IDLE:
                    handleAllIdle(ctx);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("---" + ctx.channel().remoteAddress() + " is active---");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("---" + ctx.channel().remoteAddress() + " is inactive---");
    }

    protected void handleReaderIdle(ChannelHandlerContext ctx) {
        System.err.println("---READER_IDLE---");
    }

    protected void handleWriterIdle(ChannelHandlerContext ctx) {
        System.err.println("---WRITER_IDLE---");
    }

    protected void handleAllIdle(ChannelHandlerContext ctx) {
        System.err.println("---ALL_IDLE---");
    }
}
