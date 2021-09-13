package com.mix.nio.netty;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ProcessingHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RequestData requestData = (RequestData) msg;

        ResponseData responseData = new ResponseData();
        responseData.setIntValue(requestData.getIntValue()*2);
        ChannelFuture channelFuture = ctx.writeAndFlush(responseData);
        channelFuture.addListener(ChannelFutureListener.CLOSE);
        System.out.println(requestData);
    }
}
