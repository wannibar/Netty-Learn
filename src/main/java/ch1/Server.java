package ch1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

// curl -X POST localhost:8888 测试

// 服务器端充当一个HTTP服务器
public class Server {

    public static void main(String[] args) {

        EventLoopGroup bossGroup = new NioEventLoopGroup();     //完成连接的接收,负责把连接分发给workerGroup
        EventLoopGroup workerGroup = new NioEventLoopGroup();   //完成连接的后续处理

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                // handler是针对boss组
                // childHandler是针对worker组
                //.handler(null)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerInitializer());

        try {
            ChannelFuture channelFuture = serverBootstrap.bind(8888).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
