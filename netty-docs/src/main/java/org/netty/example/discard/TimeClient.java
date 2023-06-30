package org.netty.example.discard;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TimeClient {

    public static void main(String[] args) throws Exception {
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        EventLoopGroup workerGroup = new NioEventLoopGroup(); // boss, worker 따로 있지 않음

        try {
            Bootstrap b = new Bootstrap(); // server bootstrap과 거의 동일하지만, non-server 용인 client-side 용이거나 connectionless 할 때 사용된다.
            b.group(workerGroup);
            b.channel(NioSocketChannel.class); // 여기도 client가 아니라서 ServerSocket이 아니라 그냥 SocketChannel이다.
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new TimeClientHandler());
                }
            });


            ChannelFuture f = b.connect(host, port).sync(); // server의 bind와 다르게 connect() 이다.

            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
