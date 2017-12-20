package ch15;

import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;


public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("MyDecodeHandler", new MyByte2LongDecoder()); // in handler
        pipeline.addLast("MyEncodeHandler", new MyLong2ByteEncoder()); // out handler

        pipeline.addLast("ServerHandler", new MyClientHandler());   // in handler
    }
}
