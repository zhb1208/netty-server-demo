/**
 * Create at Feb 21, 2013
 */
package com.jason.netty.service;

import com.jason.business.*;
import io.netty.handler.codec.http.HttpResponse;
import org.apache.http.HttpException;
import org.apache.log4j.Logger;

import java.util.Random;

/**
 * @author jason a demo processor
 */
public class RandomErrorProcessor extends BaseProcessor {
	private static Logger log = Logger.getLogger(RandomErrorProcessor.class
			.getName());

	public RandomErrorProcessor(String requestUri) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jason.business.BaseProcessor#process(com.jason.netty.service.
	 * HttpMessageContext)
	 */
	@Override
	public HttpResponse process(HttpMessageContext msgCtx) throws Exception {

		Random errorRandom = new Random();
		int code = (int) (errorRandom.nextFloat() * 10);
		log.debug("Random code=" + code);
		if (code < 6) {
			return super.process(msgCtx);
		} else if (code < 9) {
			throw new HttpException("fake http exception");
		} else {
			errorRandom = new Random();
			code = (int) (errorRandom.nextFloat() * 10);
			if (code < 4) {
				throw new ParseRequestException("fake parse exception");
			} else if (code < 6) {
				throw new AuthenticationException("fake auth exception");
			} else if (code < 8) {
				throw new HandleRequestException("fake handle exception");
			}
			throw new BusinessException("fake business exception");
		}
	}

}
