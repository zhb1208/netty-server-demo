/**
 * 
 */
package com.jason.netty.startup;

import com.jason.netty.service.ApplicationConfig;
import com.jason.netty.service.BusinessConsoleHandler;
import com.jason.netty.service.BusinessServerInitializer;
import com.jason.utils.Constants;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;


/**
 * @author jason
 * 
 */
@Component
public class HttpServer implements Server {

	private static Logger log = Logger.getLogger(HttpServer.class.getName());

	private static int businessPort, consolePort;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jason.netty.Server#init()
	 */
	public void init() {

		String envPort = System.getenv("PORT_BUSINESS");
		if (null != envPort) {
			businessPort = Integer.parseInt(envPort);
		}
		if (businessPort <= 0) {
			businessPort = Constants.BUSINESS_PORT;
		}
		consolePort = Constants.CONSOLE_PORT;

		ApplicationConfig.init();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jason.netty.Server#start()
	 */
	public void start() throws Exception {
		startBusiness();
		startConsole();
	}

	private void startBusiness() throws Exception {
		// Configure the server.
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
				.childHandler(new BusinessServerInitializer())
				.option(ChannelOption.SO_BACKLOG, 1024)
				.option(ChannelOption.SO_REUSEADDR, true)
				.childOption(ChannelOption.SO_KEEPALIVE, true);

		ChannelFuture f = b.bind(businessPort).sync();
		f.channel();
		log.info("Business start at " + businessPort);
//		f.channel().closeFuture().sync();
	}

//	private void startBusiness() throws Exception {
//		// Configure the server.
//		EventLoopGroup bossGroup = new NioEventLoopGroup();
//		EventLoopGroup workerGroup = new NioEventLoopGroup();
//		try {
//			io.netty.bootstrap.ServerBootstrap b = new io.netty.bootstrap.ServerBootstrap();
//			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
//					.childHandler(new BusinessServerInitializer())
//					.option(ChannelOption.SO_BACKLOG, 1024)
//					.option(ChannelOption.SO_REUSEADDR, true)
//					.childOption(ChannelOption.SO_KEEPALIVE, true);
//
//			ChannelFuture f = b.bind(businessPort).sync();
//			log.info("Business start at " + businessPort);
//			f.channel().closeFuture().sync();
//		} finally {
//			workerGroup.shutdownGracefully();
//			bossGroup.shutdownGracefully();
//		}
//	}

	private void startConsole() throws Exception {

		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() { // (4)
					@Override
					public void initChannel(SocketChannel ch)
							throws Exception {
						ChannelPipeline pipeline = ch.pipeline();
						pipeline.addLast(new HttpResponseEncoder());
						pipeline.addLast(new HttpRequestDecoder());
						pipeline.addLast(new BusinessConsoleHandler());
					}
				}).option(ChannelOption.SO_REUSEADDR, true);
		b.bind(consolePort).sync();
		log.info("Console start at " + consolePort);

	}

	public void stop() {

	}

	public void status() {
		log.info("Show Status");
	}
}
