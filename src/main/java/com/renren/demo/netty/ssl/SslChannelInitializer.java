package com.renren.demo.netty.ssl;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * @author : peng.chen5@renren-inc.com
 * @Time : 2017/9/20 下午12:05
 */
public class SslChannelInitializer extends ChannelInitializer<Channel> {

    private final SslContext context;
    private final boolean startTls;     // 如果设置为true，意味着第一个写入的消息不会被加密（客户端应该设置为true）

    public SslChannelInitializer(SslContext context, boolean startTls) {
        this.context = context;
        this.startTls = startTls;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        SSLEngine engine = context.newEngine(channel.alloc());      // 对于每一个sslHandler实例，都使用Channel的ByteBufAllocator从sslcontext获取一个新的sslengine
        channel.pipeline().addFirst("ssl" , new SslHandler(engine , startTls));
    }
}
