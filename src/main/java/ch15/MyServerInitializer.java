package ch15;

import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;


public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("MyDecodeHandler", new MyByte2LongDecoder());  // in
        pipeline.addLast("MyEncodeHandler", new MyLong2ByteEncoder());  // out

        pipeline.addLast("IN1", new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                System.out.println("IN1 channelRead");
                super.channelRead(ctx, msg);
            }
        });

        pipeline.addLast("ServerHandler", new MyServerHandler());       // in

        pipeline.addLast("OUT1", new ChannelOutboundHandlerAdapter() {

            // read()方法从tail->head
            // 注意：该方法不是读数据，而是注册读事件
            @Override
            public void read(ChannelHandlerContext ctx) throws Exception {
                System.out.println("out1 read....");
                super.read(ctx);
            }


            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                System.out.println("out1 write");
                super.write(ctx, msg, promise);
            }
        });
        pipeline.addLast("OUT2", new ChannelOutboundHandlerAdapter() {
            @Override
            public void read(ChannelHandlerContext ctx) throws Exception {
                System.out.println("out2 read....");
                super.read(ctx);
            }

            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                System.out.println("out2 write");
                super.write(ctx, msg, promise);
            }
        });
        pipeline.addLast("OUT3", new ChannelOutboundHandlerAdapter() {

            // read()方法从tail->head
            // 注意：该方法不是读数据，而是注册读事件
            @Override
            public void read(ChannelHandlerContext ctx) throws Exception {
                System.out.println("out3 read....");
                super.read(ctx);
            }

            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                System.out.println("out3 write");
                super.write(ctx, msg, promise);
            }
        });


        System.out.println(pipeline);
    }
}

