package ch4;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.Random;
import java.util.concurrent.TimeUnit;
// http://blog.csdn.net/linuu/article/details/51509847
// http://blog.chenfu3991.com/post/netty-learn07.html

//TCP 通信的报文格式是:
//        +--------+-----+---------------+
//        | Length |Type |   Content     |
//        |   17   |  1  |"HELLO, WORLD" |
//        +--------+-----+---------------+
//1.客户端每隔一个随机的时间后, 向服务器发送消息, 服务器收到消息后, 立即将收到的消息原封不动地回复给客户端.
//2.若客户端在指定的时间间隔内没有读/写操作, 则客户端会自动向服务器发送一个 PING 心跳,
// 服务器收到 PING 心跳消息时, 需要回复一个 PONG 消息.
public class Client {

    private EventLoopGroup workGroup = new NioEventLoopGroup();
    private Channel channel;
    private Bootstrap bootstrap;

    public static void main(String[] args) {
        Client client = new Client();
        client.startAndSendData();
    }

    public void startAndSendData() {
        bootstrap = new Bootstrap();
        bootstrap.group(workGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        // 如果客户端在5s内都没有收到服务器的消息(read)或向服务器发送消息(write), 则产生ALL_IDLE事件
                        p.addLast(new IdleStateHandler(0, 0, 5));
                        p.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, -4, 0));
                        p.addLast(new ClientHandler("client", Client.this));
                    }
                });

        try {
            channel = bootstrap.connect("localhost", 9965).sync().channel();
            for (int i = 0; i < 2; i++) {
                String content = "client msg " + i;
                ByteBuf buf = channel.alloc().buffer(5 + content.getBytes().length);
                buf.writeInt(5 + content.getBytes().length);
                buf.writeByte(CustomHeartbeatHandler.CUSTOM_MSG);
                buf.writeBytes(content.getBytes());
                channel.writeAndFlush(buf);

                Thread.sleep(new Random().nextInt(3000));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
           //workGroup.shutdownGracefully();
        }
    }

    public void doConnect() {
        System.out.println("enter doConnect method.....");
        if (channel != null && channel.isActive()) {
            return;
        }

        System.out.println(bootstrap);
        ChannelFuture future = bootstrap.connect("localhost", 9965);
        future.addListener((ChannelFutureListener) futureListener -> {
            if (futureListener.isSuccess()) {
                channel = futureListener.channel();
                System.out.println("Connect to server successfully!");
            } else {
                System.out.println("Failed to connect to server, try connect after 10s");
                futureListener.channel().eventLoop().schedule(() -> doConnect(), 10, TimeUnit.SECONDS);
            }
        });
    }
}
