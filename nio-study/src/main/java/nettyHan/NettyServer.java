package nettyHan;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 *tcp 服务器
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {

        //两个都是无限循环
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();

        //
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        //服务器端启动对象
        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class) //
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //pipline 设置处理器
                        socketChannel.pipeline().addLast(new NettyServerHandler());
                    }
                });
        System.out.println("***********ready");

        //绑定窗口
        ChannelFuture cf = bootstrap.bind(6668).sync();

        //对关闭通道进行监听
        cf.channel().closeFuture().sync();
    }
}
