package com.renren.demo.netty.encoderanddecoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * @author : peng.chen5@renren-inc.com
 * @Time : 2017/10/17 下午2:26
 */
public class SaftyByteToMessageDecoder extends ByteToMessageDecoder {

    private static final int MAX_FRAME_SIZE = 1024;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        int readable = in.readableBytes();
        if (readable > MAX_FRAME_SIZE){
            in.skipBytes(readable);
            throw new TooLongFrameException("Frame too big !!");
        }
    }
}
