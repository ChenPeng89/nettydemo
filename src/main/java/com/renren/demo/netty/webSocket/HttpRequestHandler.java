package com.renren.demo.netty.webSocket;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author : peng.chen5@renren-inc.com
 * @Time : 2017/10/8 上午11:46
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private final String wsUri;
    private static final File INDEX;

    static {
        URL location=HttpRequestHandler.class.getProtectionDomain().getCodeSource().getLocation();
        try{
            String path = location.toURI() + "index.html";
            path = !path.contains("file:") ? path : path.substring(5);
            INDEX = new File(path);
        }catch (URISyntaxException e){
            throw new IllegalStateException("Unable to locate indesx.html" , e);
        }
    }

    public HttpRequestHandler(String wsUri){
        this.wsUri = wsUri;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        // 如果请求了/ws，说明要使用websocket，那么添加引用计数，并将它传递给下一个ChannelInboundhandler，并且在调用玩channelread0后会调用fullehttprequest的release()方法释放资源
        if(wsUri.equalsIgnoreCase(request.uri())){
            ctx.fireChannelRead(request.retain());
        }else{
            if(HttpHeaders.is100ContinueExpected(request)){
                send100Continue(ctx);
            }
            RandomAccessFile file = new RandomAccessFile(INDEX , "r");
            HttpResponse response = new DefaultHttpResponse(request.protocolVersion() , HttpResponseStatus.OK);
            response.headers().set(HttpHeaders.Names.CONTENT_TYPE , "text/html;charset=UTF-8");
            boolean keepalive = HttpHeaders.isKeepAlive(request);
            if(keepalive){
                response.headers().set(HttpHeaders.Names.CONTENT_LENGTH , file.length());
                response.headers().set(HttpHeaders.Names.CONNECTION , HttpHeaders.Values.KEEP_ALIVE);
            }
            ctx.write(response);
            if(ctx.pipeline().get(SslHandler.class) == null){
                ctx.write(new DefaultFileRegion(file.getChannel() , 0 , file.length()));     //直接使用零拷贝特性
            }else{
                ctx.write(new ChunkedNioFile(file.getChannel()));
            }
            ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
            if(!keepalive){
                future.addListener(ChannelFutureListener.CLOSE);
            }
        }
    }

    private static void send100Continue(ChannelHandlerContext ctx){
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1 , HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush(response);
    }

}
