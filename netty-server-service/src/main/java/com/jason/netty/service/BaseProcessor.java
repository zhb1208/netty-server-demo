/**
 * Create at Feb 21, 2013
 */
package com.jason.netty.service;


import io.netty.handler.codec.http.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author jason
 *
 */
public class BaseProcessor {
	
	public HttpResponse process(HttpMessageContext msgCtx) throws Exception{
		
		HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);


		
		return response;
	}
	
	/**
	 * Common Method for this handler
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public StringBuilder parseRequestParameter(HttpRequest request)
			throws UnsupportedEncodingException {
		StringBuilder buf = new StringBuilder();
		buf.setLength(0);
		buf.append("WELCOME TO THE WILD WILD WEB SERVER\r\n");
		buf.append("===================================\r\n");

		buf.append("VERSION: " + request.getProtocolVersion() + "\r\n");
		buf.append("REQUEST_URI: " + request.getUri() + "\r\n\r\n");

		QueryStringDecoder queryStringDecoder = new QueryStringDecoder(
				request.getUri());
		Map<String, List<String>> params = queryStringDecoder.parameters();
		if (!params.isEmpty()) {
			for (Entry<String, List<String>> p : params.entrySet()) {
				String key = p.getKey();
				List<String> vals = p.getValue();
				for (String val : vals) {
					buf.append("PARAM: " + key + " = " + val + "\r\n");
				}
			}
			buf.append("\r\n");
		}
		return buf;
	}

	public StringBuilder makeTestContent(HttpRequest request)
			throws UnsupportedEncodingException {
		StringBuilder buf = new StringBuilder();
		buf.append("<html>");
		buf.append("\n<body bgcolor=\"white\">");
		buf.append("<h1> Request Information </h1>");
		buf.append("<font size=\"4\">");
		buf.append("<br>\nJSP Request Method: ").append(request.getMethod());
		buf.append("<br>\nRequest URI: ").append(request.getUri());
		buf.append("<br>\nRequest Protocol: ").append(
				request.getProtocolVersion());;

		buf.append("<!--          30           -->");
		buf.append("<!--          30           -->");
		buf.append("<!--          30           -->");
		buf.append("<!--          30           -->");
		buf.append("<!--          30           -->");
		buf.append("<!--          30           -->");
		buf.append("<!--          30           -->");
		buf.append("<!--          30           -->");
		buf.append("<!--          30           -->");
		buf.append("<!--          30           -->");

		buf.append("<br>\n<h4>\n</font>\n</body>\n</html>");

		return buf;
	}
}
