package com.renren.demo.netty.https;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * <pre>由于http请求和响应可能由许多部分组成，使用aggregator可以聚合他们形成完整的信息。</pre>
 * @author : peng.chen5@renren-inc.com
 * @Time : 2017/9/20 下午2:53
 */
public class HttpAggregatorInitializer extends ChannelInitializer<Channel> {
    private final boolean isClient;

    public HttpAggregatorInitializer(boolean isClient) {
        this.isClient = isClient;
    }

    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        if(isClient){
            pipeline.addLast("codec" , new HttpClientCodec());
        }else{
            pipeline.addLast("codec" , new HttpServerCodec());
        }
        // 将最大的消息大小为512k的HttpObjectAggregator添加到ChannelPipeline
        pipeline.addLast("aggregator" , new HttpObjectAggregator(512 * 1024));
    }
}
