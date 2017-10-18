package com.renren.demo.netty.shareEventLoop;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 * <pre>有这样一种使用场景，服务端在接收到连接时，需要作为客户端发送连接，这时候，一般会想到新建一个channel，并且新建一个和它对应的eventloop，
 * 但是，这样会引起新的线程创建以及线程上下文切换的开销，因此，有一种解决方案是，可以通过bootstrap.group()方法复用服务端的eventloop，这样会减少线程的创建和上下文切换的开销</pre>
 * @author : peng.chen5@renren-inc.com
 * @Time : 2017/9/18 上午10:46
 */
public class ShareEventLoop {

    public static void main(String[] args) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(new NioEventLoopGroup() , new NioEventLoopGroup())
        .channel(NioServerSocketChannel.class).childHandler(new SimpleChannelInboundHandler<ByteBuf>() {
            ChannelFuture connectFuture;

            @Override
            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.channel(NioSocketChannel.class).handler(
                        new SimpleChannelInboundHandler<ByteBuf>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
                                System.out.println("Receive data");
                            }
                        }
                );
                bootstrap.group(ctx.channel().eventLoop());
                connectFuture = bootstrap.connect(new InetSocketAddress("www.manning.com" , 80));
            }

            @Override
            protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
                if(connectFuture.isDone()){
                    System.out.println(byteBuf.toString(CharsetUtil.UTF_8));
                }
            }
        });

        ChannelFuture future = bootstrap.bind(new InetSocketAddress(8080));
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()){
                    System.out.println("Server bound");
                }else{
                    System.err.println("Bind attempt failed");
                    channelFuture.cause().printStackTrace();
                }
            }
        });

    }
}
