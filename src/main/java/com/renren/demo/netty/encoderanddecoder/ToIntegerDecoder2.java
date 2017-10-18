package com.renren.demo.netty.encoderanddecoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author : peng.chen5@renren-inc.com
 * @Time : 2017/10/17 上午11:56
 */
public class ToIntegerDecoder2 extends ReplayingDecoder<Void> {
    /**
     * <p>ReplayingDecoder 扩展了 ByteToMessageDecoder ， 使得我们不必调用readableBytes()方法。
     * 因此，如果引入 ByteToMessageDecoder 不会增加复杂性的话，可以考虑，如果会，那么使用 ReplayingDecoder
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        out.add(in.readInt());
    }
}
