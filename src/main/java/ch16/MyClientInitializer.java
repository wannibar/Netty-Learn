package ch16;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //pipeline.addLast("MyDecodeHandler", new MyByte2LongDecoder()); // in handler

        pipeline.addLast("MyDecodeHandler", new MyByte2LongDecoderUseReplaying()); // in handler
        pipeline.addLast("MyEncodeHandler", new MyLong2ByteEncoder()); // out handler

        pipeline.addLast("ServerHandler", new MyClientHandler());   // in handler
    }
}
