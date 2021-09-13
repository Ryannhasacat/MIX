package com.mix.nio.netty.decoder;

import com.mix.nio.netty.RequestData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;

public class RequestDataEncoder extends MessageToByteEncoder<RequestData> {
    Charset charset = Charset.forName("UTF-8");
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RequestData requestData, ByteBuf out) throws Exception {
        out.writeInt(requestData.getIntValue());
        out.writeInt(requestData.getStringValue().length());
        out.writeCharSequence(requestData.getStringValue(),charset);
    }
}
