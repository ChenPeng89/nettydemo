package com.renren.demo.netty.encoderanddecoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.CombinedChannelDuplexHandler;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.List;

/**
 * @author : peng.chen5@renren-inc.com
 * @Time : 2017/10/17 下午4:21
 */
public class CombineByteCharCodec extends CombinedChannelDuplexHandler<ByteToCharDecoder , CharToByteEncoder>{

    public CombineByteCharCodec() {
        super(new ByteToCharDecoder() , new CharToByteEncoder());
    }
}

class ByteToCharDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        while (in.readableBytes() >= 2) {
            out.add(in.readChar());
        }
    }
}

class CharToByteEncoder extends MessageToByteEncoder<Character> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Character msg, ByteBuf out)
            throws Exception {
        out.writeChar(msg);
    }
}
