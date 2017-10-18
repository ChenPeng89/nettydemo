package com.renren.demo.netty.https;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * <pre>使用https协议</pre>
 * @author : peng.chen5@renren-inc.com
 * @Time : 2017/9/20 下午3:09
 */
public class HttpsCodecInitializer extends ChannelInitializer<Channel> {
    private final SslContext sslContext;
    private final boolean isClient;

    public HttpsCodecInitializer(SslContext sslContext, boolean isClient) {
        this.sslContext = sslContext;
        this.isClient = isClient;
    }

    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        SSLEngine engine = sslContext.newEngine(channel.alloc());
        pipeline.addFirst("ssl" , new SslHandler(engine));

        if(isClient){
            pipeline.addLast("codec" , new HttpClientCodec());
        }else{
            pipeline.addLast("codec" , new HttpServerCodec());
        }
    }
}
