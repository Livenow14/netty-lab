package org.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Handles a server-side channel.
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter { // (1)

  // This method is called with the received message, whenever new data is received from a client.
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
    // ByteBuf is a reference-counted object which has to be released explicitly via the release() method.
    ctx.write(msg); // (1)
    ctx.flush(); // (2)
  }

  // This method is called with a Throwable when an exception was raised by Netty due to an I/O error or by a handler implementation due to the exception thrown while processing events.
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
    // Close the connection when an exception is raised.
    cause.printStackTrace();
    ctx.close();
  }
}