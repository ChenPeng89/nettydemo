package com.renren.demo.netty.https;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.*;

/**
 * @author : peng.chen5@renren-inc.com
 * @Time : 2017/9/20 下午3:47
 */
public class WebSocketServerInitializer extends ChannelInitializer<Channel> {

    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast( new HttpServerCodec() ,
                                    new HttpObjectAggregator(65536) ,
                                    new WebSocketServerProtocolHandler("/websocket") ,
                                    new TextFrameHandler() ,
                                    new BinaryFrameHandler() ,
                                    new ContinuationFrameHandler());
    }


    public static final class TextFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

        protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
            // handle text frame
        }
    }


    public static final class BinaryFrameHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {

        protected void channelRead0(ChannelHandlerContext channelHandlerContext, BinaryWebSocketFrame binaryWebSocketFrame) throws Exception {
            // handle binary frame
        }
    }

    public static final class ContinuationFrameHandler extends SimpleChannelInboundHandler<ContinuationWebSocketFrame> {
        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, ContinuationWebSocketFrame continuationWebSocketFrame) throws Exception {
            // handle continuation frame
        }
    }
}