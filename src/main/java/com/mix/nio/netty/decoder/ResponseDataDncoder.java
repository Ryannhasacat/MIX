package com.mix.nio.netty.decoder;

import com.mix.nio.netty.RequestData;
import com.mix.nio.netty.ResponseData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class ResponseDataDncoder extends ReplayingDecoder<RequestData> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        ResponseData responseData = new ResponseData();
        responseData.setIntValue(in.readInt());
        out.add(responseData);
    }
}
