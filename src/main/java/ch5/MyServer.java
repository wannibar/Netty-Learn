package ch5;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

// WebSocket
// 如果建立连接后,服务器端或者客户端开启飞行模式或者直接恰掉网络等,则都不能互相感应到对方掉线.
// 都会认为连接仍然建立着,这种情况需要加入心跳包来解决

// 注意观察HTTP请求,先建立HTTP请求,然后升级,状态码101(转换协议),以及Frames
public class MyServer {
    public static void main(String[] args) {
        {
            EventLoopGroup boss = new NioEventLoopGroup();
            EventLoopGroup worker = new NioEventLoopGroup();

            // handler是针对boss组
            // childHandler是针对worker组
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new MyServerInitializer());

            try {
                ChannelFuture channelFuture = serverBootstrap.bind(9965).sync();
                channelFuture.channel().closeFuture().sync();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                boss.shutdownGracefully();
                worker.shutdownGracefully();
            }
        }
    }
}
