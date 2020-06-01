package echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang
 * date: 2020/6/1 14:55
 * description:
 */
public class EchoClient {

    private final String ip;
    private final int port;

    public EchoClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void start() throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(ip, port))
                    .handler(new EchoClientHandler());
            ChannelFuture future = bootstrap.connect().sync();
            future.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        EchoClient echoClient = new EchoClient("127.0.0.1", 9999);
        echoClient.start();
    }
}
