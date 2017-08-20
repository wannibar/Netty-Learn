package ch1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

// curl -X POST localhost:8888 测试
// 服务端主动关闭连接


public class MyHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    //该方法读取客户端发送过来的请求,并且返回响应给客户端
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        System.out.println("Out: " + msg.getClass());
        System.out.println("Remote Address:" + ctx.channel().remoteAddress());
        Thread.sleep(12000);

        if (msg instanceof HttpRequest) {

            HttpRequest request = (HttpRequest) msg;

            System.out.println("执行channelRead0");
            System.out.println("请求方法名：" + request.method().name());

            // chrome会请求icon
            URI uri = new URI(request.uri());
            System.out.println("URI:" + uri);
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("请求favicon");
                return;
            }

            ByteBuf content = Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            ctx.writeAndFlush(response);    //如果调用write则只会放到缓冲区,而不会发送给客户端

            System.out.println("close the connection");
            ctx.channel().close();          //关闭连接,应该根据HTTP版本决定啥时候关闭连接
        }
    }

    // 服务器和客户端建立连接时,会调用此方法,可以看到这个方法的参数并没有msg
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handler added");
        super.handlerAdded(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel registered");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel active");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel inactive");
        super.channelInactive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel unregistered");
        super.channelUnregistered(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handler removed");
        super.handlerRemoved(ctx);
    }
}
