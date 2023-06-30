package org.netty.example.discard;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class DiscardServer {

    private int port;

    public DiscardServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        // multithreaded event loop that handles I/O operation
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // Boss accept connection and register accepted connection to worker
        EventLoopGroup workerGroup = new NioEventLoopGroup(); // handles traffic of accepted connection

        try {
            ServerBootstrap b = new ServerBootstrap(); // helper class that sets up a server. Channel을 통해 직접 세팅도 가능하지만 번거롭다.
            b.group(bossGroup, workerGroup) // ServerBootstrap 초기 세팅
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new EchoServerHandler()); // 시스템이 복잡해져서 여러 채널이 추가되면 익명클래스가 top-level class로 승격된다.
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128) // 그냥 option들 넣어놓는 hash-map에 저장
                .childOption(ChannelOption.SO_KEEPALIVE, true);

            // Bind and start to accept incoming connections 서버 구동
            ChannelFuture f = b.bind(port).sync();
            // 서버 소켓이 닫힐때까지 기다리다가 닫히면 종료한다.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        new DiscardServer(port).run();
    }
}
