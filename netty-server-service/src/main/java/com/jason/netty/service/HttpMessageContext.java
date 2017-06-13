/**
 * Create at Feb 21, 2013
 */
package com.jason.netty.service;

import com.jason.business.BusinessException;
import com.jason.netty.service.snapshot.MessageWatch;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jason
 * 
 *         Each http process has a HttpMessageContext
 */
public class HttpMessageContext {

	private static Logger log = Logger.getLogger(HttpMessageContext.class
			.getName());

	private HttpRequest request;
	private MessageWatch watch;

    private boolean keepAlive;
    private String requestUri;

	/**
	 * @return the keepAlive
	 */
	public boolean isKeepAlive() {
		return keepAlive;
	}

	/**
	 * 
	 */
	public HttpMessageContext(HttpRequest request) {
		this.request = request;
		this.watch = new MessageWatch(request);

		this.requestUri = watch.getRequestUri();

		keepAlive = HttpHeaders.isKeepAlive(request);
	}

	/*
	 * Release context and add watch to snapshot
	 */
	public void release() {
		// add messageWatch to Snapshot.
		ApplicationConfig.getSnapshotService().addMessageWatch(watch, exceptions);
		watch = null;
	}

	private List<Exception> exceptions = new ArrayList<Exception>();
	public void addException(Exception ex) {

		exceptions.add(ex);
		int responseCode;
		if (ex instanceof BusinessException) {
			responseCode = HttpResponseStatus.INTERNAL_SERVER_ERROR.code();
			log.fatal("code[" + responseCode + ",error[" + ex.getMessage()
					+ "]");
		} else if (ex instanceof HttpException) {
			responseCode = HttpResponseStatus.BAD_REQUEST.code();
		} else {
			responseCode = HttpResponseStatus.NOT_IMPLEMENTED.code();
			log.warn("code[" + responseCode + ",error[" + ex.getMessage() + "]");
		}

		watch.setResponseCode(responseCode);
		log.fatal("from: " + watch.getRemoteIP() + ", access: "
				+ watch.getRequestUri() + " at " + ex.getMessage());
	}

	public int getResponseCode() {
		return watch.getResponseCode();
	}


	/**
	 * @return the request
	 */
	public HttpRequest getRequest() {
		return request;
	}

	public static final int IN_BUSINESS = 1;
	public static final int OUT_BUSINESS = 2;

	public void updateProcessStatus(int state) {
		if (state == IN_BUSINESS) {
			watch.setBusiness();
		} else if (state == OUT_BUSINESS) {
			watch.stop(MessageWatch.STATE_BUSINESS);
		}
	}

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }
}
