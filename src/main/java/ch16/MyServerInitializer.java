package ch16;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //pipeline.addLast("MyDecodeHandler", new MyByte2LongDecoder());  // in

        pipeline.addLast("MyDecodeHandler", new MyByte2LongDecoderUseReplaying());  // in
        pipeline.addLast("MyLong2StringDecoder", new MyLong2StringDecoder());  // in
        pipeline.addLast("MyEncodeHandler", new MyLong2ByteEncoder());  // out
        pipeline.addLast("ServerHandler", new MyServerHandler());       // in

    }
}

