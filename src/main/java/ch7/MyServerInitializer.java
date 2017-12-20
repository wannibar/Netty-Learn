package ch7;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;


public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        /**
         * 一般来说,服务器端会接收多种类型的消息，但是这里只能写一种，对于这种情况,有2种解决办法
         * 1:自己写一个Decoder,缺点是不够通用
         * 2:通过proto定义,使用Union Type
         */
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        // 解码器,处理客户端发送过来的数据类型
        pipeline.addLast(new ProtobufDecoder(DataInfo.Teacher.getDefaultInstance()));
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast(new ProtobufEncoder());

        pipeline.addLast("ServerHandler", new MyServerHandler());
    }
}

