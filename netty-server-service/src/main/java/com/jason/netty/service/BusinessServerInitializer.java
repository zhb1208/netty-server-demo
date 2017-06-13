package com.jason.netty.service;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * TODO:add description of class here, then delete the line.
 *
 * @author zhanghongbing
 * @version 15/3/24
 */
public class BusinessServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();

        // Uncomment the following line if you want HTTPS
        //SSLEngine engine = SecureChatSslContextFactory.getServerContext().createSSLEngine();
        //engine.setUseClientMode(false);
        //p.addLast("ssl", new SslHandler(engine));

        // 接收请求
        p.addLast("aggegator",new HttpObjectAggregator(1024*1024*2048-1));//定义缓冲数据量
        // server端接收到的是httpRequest，所以要使用HttpRequestDecoder进行解码
        p.addLast("decoder",new HttpRequestDecoder());
        // to be used since huge file transfer
        p.addLast("chunkedWriter", new ChunkedWriteHandler());
        // decompress
        p.addLast("inflater", new HttpContentDecompressor());
        // 响应请求
        // server端发送的是httpResponse，所以要使用HttpResponseEncoder进行编码
        p.addLast("encoder",new HttpResponseEncoder());
//        // compress
        p.addLast("deflater", new HttpContentCompressor());
        // http chunk handler
        p.addLast("handler", new BusinessServerHandler());
    }
}