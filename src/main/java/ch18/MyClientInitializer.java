package ch18;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();


        pipeline.addLast("decoder", new PersonDecoder());
        pipeline.addLast("encoder", new PersonEncoder());

        pipeline.addLast("ClientHandler", new MyClientHandler());
    }
}
