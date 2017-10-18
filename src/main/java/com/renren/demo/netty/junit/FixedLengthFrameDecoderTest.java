package com.renren.demo.netty.junit;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;

/**
 * @author : peng.chen5@renren-inc.com
 * @Time : 2017/9/18 下午5:20
 */
public class FixedLengthFrameDecoderTest {

    public void testFramesDecoder(){
        ByteBuf buf = Unpooled.buffer();
        for(int i = 0 ; i < 9 ; i++){
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();
        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        assert (channel.writeInbound(input.retain()) == true);
        assert channel.finish() == true;

        ByteBuf read = channel.readInbound();
        assert buf.readSlice(3) == read;
        read.release();

        read = channel.readInbound();
        assert buf.readSlice(3) == read;
        read.release();

        read = channel.readInbound();
        assert buf.readSlice(3) == read;
        read.release();

        assert channel.readInbound() == null;
        buf.release();
    }

    public void testFramesDecoder2(){

    }
}
