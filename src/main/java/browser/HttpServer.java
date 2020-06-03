package browser;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang
 * date: 2020/6/3 15:40
 * description:
 */
public class HttpServer {
    public static final int PORT = 9999;
    private static final EventLoopGroup NIO_EVENT_LOOP_GROUP = new NioEventLoopGroup();
    private static final ServerBootstrap BOOTSTRAP = new ServerBootstrap();

    public static void main(String[] args) throws InterruptedException {
        try {
            BOOTSTRAP.group(NIO_EVENT_LOOP_GROUP)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerHandlerInit());
            ChannelFuture future = BOOTSTRAP.bind(PORT).sync();
            System.out.println("服务端启动成功,端口是:"+PORT);
            future.channel().closeFuture().sync();
        } finally {
            NIO_EVENT_LOOP_GROUP.shutdownGracefully().sync();
        }
    }

}
