package org.netty.example.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.Date;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // server와 달리 종종 IndexOutOfBoundsException이 발생한다. (추후 다룰 예정)
        ByteBuf m = (ByteBuf) msg;
        try {
            long currentTimeMillis = (m.readUnsignedInt() - 2208988800L) * 1000L;
            System.out.println(new Date(currentTimeMillis));
            ctx.close();
        } finally {
            m.release();
        }
    } // 문제는 stream based transport에서는 데이터가 패킷 단위로 전송되지 않을 수 있다는 것이다. (TCP는 stream based transport이다.)
    // 따라서 그냥 보낸 것을 패킷 단위가 아닌 byte 단위로 받아서 보내는 쪽이 의도한 대로 읽히지 않을 수 있다.

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
