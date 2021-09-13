package com.mix.nio.netty.bootstrap;

import com.mix.nio.netty.ClientHandler;
import com.mix.nio.netty.decoder.RequestDataEncoder;
import com.mix.nio.netty.decoder.ResponseDataDncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyClient {

    public static void main(String[] args) throws Exception {

        String host = "localhost";
        int port = 8080;
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new RequestDataEncoder(),
                            new ResponseDataDncoder(),new ClientHandler());
                }
            });
            ChannelFuture f = bootstrap.connect(host, port).sync();

            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
