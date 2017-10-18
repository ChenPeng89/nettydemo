package com.renren.demo.netty.https;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * @author : peng.chen5@renren-inc.com
 * @Time : 2017/9/20 下午2:32
 */
public class HttpPipelineInitializer extends ChannelInitializer<Channel> {
    private final boolean client;

    public HttpPipelineInitializer(boolean client) {
        this.client = client;
    }

    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        if(client){
            pipeline.addLast("decoder" , new HttpResponseDecoder());
            pipeline.addLast("encoder" , new HttpRequestEncoder());
        }else{
            pipeline.addLast("decoder" , new HttpRequestDecoder());
            pipeline.addLast("encoder" , new HttpResponseEncoder());
        }
    }
}
