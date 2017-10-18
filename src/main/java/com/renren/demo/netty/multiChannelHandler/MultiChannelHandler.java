package com.renren.demo.netty.multiChannelHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

/**
 * 在引导期间添加多个handler
 * @author : peng.chen5@renren-inc.com
 * @Time : 2017/9/18 上午11:27
 */
public class MultiChannelHandler {

    public void connect() throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(new NioEventLoopGroup() , new NioEventLoopGroup()).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializerImpl());
        ChannelFuture future = bootstrap.bind();
        future.sync();
    }

    final class ChannelInitializerImpl extends ChannelInitializer<Channel>{
        @Override
        protected void initChannel(Channel channel) throws Exception {
            ChannelPipeline pipeline = channel.pipeline();
            pipeline.addLast(new HttpClientCodec());
            pipeline.addLast(new HttpObjectAggregator(Integer.MAX_VALUE));
        }
    }
}
