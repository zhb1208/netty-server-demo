/**
 * Create at Feb 20, 2013
 */
package com.jason.netty.service.snapshot;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jason
 * 
 *         Netty message context: each request has own instance, every process
 *         add state to it.
 * 
 *         pay my respects to org.apache.commons.lang.time.StopWatch
 */
public class MessageWatch {

	public static final int STATE_ALL = 0;
	public static final int STATE_BUSINESS = 1;

	private static Logger logger = LoggerFactory.getLogger(MessageWatch.class);

	/**
	 * 5 stopwatch, each has starttime&endtime
	 */
	private long[][] stopwatch = new long[6][2];
	private HttpRequest request;
	private String remoteIP;
	private int responseCode = 200;
	private boolean boolBusi = false;
	private Map<String, Integer> subBusiness = new HashMap<String, Integer>();

	/**
	 *
	 */
	public MessageWatch(HttpRequest request) {
		this.stopwatch[STATE_ALL][0] = System.nanoTime();
		this.request = request;
	}

	public void start(int state) {
		this.stopwatch[state][0] = System.nanoTime();
	}

	public void stop(int state) {
		this.stopwatch[state][1] = System.nanoTime();
	}

	public String getRequestUri() {
		return HttpHeaders.getHost(request, "") + request.getUri();
	}

	/**
	 * @return the remoteIP
	 */
	public String getRemoteIP() {
		return remoteIP;
	}

	public long getAliveTime() {
		if (stopwatch[STATE_ALL][1] == 0) {
			stopwatch[STATE_ALL][1] = System.nanoTime();
		}
		return stopwatch[STATE_ALL][1] - stopwatch[STATE_ALL][0];
	}

	public long getAliveTime(int state) {
		if (state >= stopwatch.length) {
			return -1;
		}
		if (stopwatch[STATE_ALL][1] == 0) {
			stopwatch[state][1] = System.nanoTime();
		}
		return stopwatch[state][1] - stopwatch[state][0];
	}

	public int getResponseCode() {
		return responseCode;
	}

	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}


	/**
	 * @return the request
	 */
	public HttpRequest getRequest() {
		return request;
	}

	public boolean isBusiness() {
		return boolBusi;
	}

	public void setBusiness() {
		boolBusi = true;
		this.start(MessageWatch.STATE_BUSINESS);
	}

	public Map getSubBusiness() {
		return subBusiness;
	}

	/**
	 * 设置子业务
	 *
	 * @param name
	 * @param index
	 */
	public void setSubBusiness(String name, int index) {
		subBusiness.put(name, index);
		this.start(index);
	}

	public void setRemoteIP(String remoteIP) {
		this.remoteIP = remoteIP;
	}
}