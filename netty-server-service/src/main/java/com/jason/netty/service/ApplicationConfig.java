package com.jason.netty.service;

import com.jason.netty.service.app.BaseJsonProcessor;
import com.jason.netty.service.snapshot.MessageWatch;
import com.jason.netty.service.snapshot.SnapshotService;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Application Container
 * 
 * Initialise Application by Configuration, and provide overall business
 * logic(Facade)
 * 
 * @author jason
 * 
 */
public class ApplicationConfig {

	private static Logger log = Logger.getLogger(ApplicationConfig.class
			.getName());
	private static SnapshotService snapshot = null;

	/**
	 * @return the snapshot
	 */
	public static SnapshotService getSnapshotService() {
		return snapshot;
	}

	/**
	 * Initialise Application by Configuration
	 */
	public static void init() {

		snapshot = new SnapshotService();
		log.info("App init...");
	}

	/**
	 * 内容处理
	 * @param request
	 * @param msg
	 * @param messageWatch
	 * @param listException
	 */
	public StringBuilder process(HttpRequest request,
								   Object msg, byte[] contentByte,
									MessageWatch messageWatch, List<Exception> listException)
			throws Exception {
		StringBuilder outPut = null;
		if (msg instanceof HttpContent) {
//			// request获取输入
//			HttpContent chunk = (HttpContent) msg;
//			ByteBuf buf = chunk.content();
//			byte[] bufByte = ByteUtils.read(buf);
//
//			String deContent =  StringHandler.getRequestStr(bufByte);
//			buf.release();
//
//			BaseJsonProcessor baseJsonProcessor = new BaseJsonProcessor(request, ctx, msg,
//					messageWatch, listException);
//			response = baseJsonProcessor.process(deContent);

			// request获取输入
			HttpContent chunk = (HttpContent) msg;
			ByteBuf buf = chunk.content();
			String content = buf.toString(io.netty.util.CharsetUtil.UTF_8);
			buf.release();
			log.info("request jsonstring:" + content);

			String contentByteStr = new String(contentByte);

			BaseJsonProcessor baseJsonProcessor = new BaseJsonProcessor(messageWatch, listException);
			outPut = baseJsonProcessor.makeAppContent(contentByteStr);

		}
		return outPut;
	}

	/**
	 * the overall business process logic
	 *
	 * @param msgCtx
	 * @throws Exception
	 */
	public static HttpResponse process(HttpMessageContext msgCtx) throws Exception {
		/*
		 * get processor by request uri
		 */
		RandomErrorProcessor processor = new RandomErrorProcessor(msgCtx.getRequestUri());

		return processor.process(msgCtx);
	}

	/**
	 * call before close service
	 */
	public static void release() {
	}
}
