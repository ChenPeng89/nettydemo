package com.renren.demo.netty.https;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * <pre>发送心跳</pre>
 * @author : peng.chen5@renren-inc.com
 * @Time : 2017/9/20 下午4:05
 */
public class IdleStateHandlerInitializer extends ChannelInitializer<Channel> {
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        // IdleStateHandler在指定的时间间隔会触发发送一个IdleStateEvent事件。下面的例子是超过60秒没接受或者发送消息，会触发事件。
        pipeline.addLast(new IdleStateHandler(0 , 0 , 60 , TimeUnit.SECONDS));
        pipeline.addLast(new HeartbeatHandler());
    }

    public static final class HeartbeatHandler extends ChannelInboundHandlerAdapter{
        /** 心跳信息 */
        private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("HEARTBEAT" , CharsetUtil.ISO_8859_1));

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if(evt instanceof IdleStateEvent){ // 检测到IdleStateEvent事件后，会发送心跳信息，并在发送失败时关闭连接
                ctx.writeAndFlush(HEARTBEAT_SEQUENCE).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }else{
                super.userEventTriggered(ctx, evt);
            }
        }
    }
}
