package com.renren.demo.netty.encoderanddecoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author : peng.chen5@renren-inc.com
 * @Time : 2017/10/17 下午12:05
 */
public class IntegerToStringDecoder extends MessageToMessageDecoder<Integer> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Integer msg, List<Object> out) throws Exception {
        out.add(Integer.valueOf(msg));
    }
}
