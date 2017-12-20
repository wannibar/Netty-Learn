package ch4;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // IdeleStateHandler检测客户端在指定时间内有无读操作(客户端每隔5s,如果没有读写操作，则客户端发送一个PING消息到服务端)
        // 服务端则检测10内是否可以读到数据，如果10S内都读不到客户端发来的数据，可以认为客户端是掉线了
        pipeline.addLast("idleStateHandler", new IdleStateHandler(10, 0, 0, TimeUnit.SECONDS));
        pipeline.addLast("lengthDecode", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, -4, 0));
        pipeline.addLast("myHander", new ServerHandler());
    }
}
