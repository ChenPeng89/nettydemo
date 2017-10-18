package com.renren.demo.netty.junit;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author : peng.chen5@renren-inc.com
 * @Time : 2017/9/18 下午5:10
 */
public class FixedLengthFrameDecoder extends ByteToMessageDecoder {
    private final int frameLength;

    public FixedLengthFrameDecoder(int frameLength) {
        if(frameLength <= 0){
            throw new IllegalArgumentException("frameLength must be a positive integer : " + frameLength);
        }
        this.frameLength = frameLength;
    }

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        while(in.readableBytes() >= frameLength){
            ByteBuf buf = in.readBytes(frameLength);
            out.add(buf);
        }
    }
}
