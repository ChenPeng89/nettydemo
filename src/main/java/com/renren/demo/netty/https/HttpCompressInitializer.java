package com.renren.demo.netty.https;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * <pre>使用压缩的方式来传输http数据，可以减小传输数据的大小，但会增加cpu的开销，不过是值得的。</pre>
 * @author : peng.chen5@renren-inc.com
 * @Time : 2017/9/20 下午3:01
 */
public class HttpCompressInitializer extends ChannelInitializer<Channel> {
    private final boolean isClient;

    public HttpCompressInitializer(boolean isClient) {
        this.isClient = isClient;
    }

    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        if(isClient){
            pipeline.addLast("codec" , new HttpClientCodec());
            pipeline.addLast("decompressor" , new HttpContentDecompressor());
        }else{
            pipeline.addLast("codec" , new HttpServerCodec());
            pipeline.addLast("compressor" , new HttpContentCompressor());
        }

    }
}
