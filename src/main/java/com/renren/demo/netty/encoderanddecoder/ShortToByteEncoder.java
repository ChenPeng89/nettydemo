package com.renren.demo.netty.encoderanddecoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author : peng.chen5@renren-inc.com
 * @Time : 2017/10/17 下午2:38
 */
public class ShortToByteEncoder extends MessageToByteEncoder<Short> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Short msg, ByteBuf out) throws Exception {
        out.writeShort(msg);
    }
}
