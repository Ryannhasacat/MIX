package com.mix.nio.netty.decoder;

import com.mix.nio.netty.RequestData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.nio.charset.Charset;
import java.util.List;

public class RequestDecoder extends ReplayingDecoder<RequestData> {

    Charset charset = Charset.forName("UTF-8");
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        RequestData requestData = new RequestData();
        requestData.setIntValue(in.readInt());
        int strLen = in.readInt();
        requestData.setStringValue(in.readCharSequence(strLen,charset).toString());
        out.add(requestData);
    }
}
