package org.netty.example.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/*
server-side channel
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // 실제 구동하는 것을 DiscardServer는 확인하기 어렵기 때문에, EchoServer로 바꿔서 확인해보자.
        ctx.write(msg);
        ctx.flush();
    }

    // called with exception by Netty due to an I/O error or by a handler implementation which throw exception while processing events
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close(); // close connection when exception raised
    }
}
