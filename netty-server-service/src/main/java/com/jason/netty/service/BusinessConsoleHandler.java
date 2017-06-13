/**
 * Create at Jan 16, 2013
 */
package com.jason.netty.service;

import com.alibaba.fastjson.JSON;
import com.jason.netty.service.snapshot.SnapshotService;
import com.jason.util.ServerInfo;
import com.jason.utils.Constants;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author wanghua
 * 
 */
public class BusinessConsoleHandler extends SimpleChannelInboundHandler<HttpObject> {

	private StringBuilder consoleHelpInfo = new StringBuilder();

	private static Logger logger = Logger
			.getLogger(BusinessConsoleHandler.class);
	private HttpRequest request;
	private SnapshotService snapshot = ApplicationConfig.getSnapshotService();

	public BusinessConsoleHandler() {
		// create help hint
		consoleHelpInfo.append("<html>");
		consoleHelpInfo.append("\n<body bgcolor=\"white\">");
		consoleHelpInfo.append("<h1> Netty Server Console </h1>");
		consoleHelpInfo.append("<br>Usage:\n");
		consoleHelpInfo.append("<br>Actions\n");
		consoleHelpInfo
				.append("<br><a href=\"console?action=status\">status</a>\n");
		consoleHelpInfo
				.append("<br><a href=\"console?action=snapshot\">snapshot</a>\n");
		consoleHelpInfo.append("<br>\n<h4>\n</font>\n</body>\n</html>");
	}

	private String makeConsoleResponse(HttpRequest request) {

		QueryStringDecoder queryStringDecoder = new QueryStringDecoder(
				request.getUri());
		Map<String, List<String>> params = queryStringDecoder.parameters();

		List<String> actions = params.get("action");
		if (null == actions) {
			return consoleHelpInfo.toString();
		}

//		logger.info("actions="+actions);

		for (String action : actions) {
			if ("status".equalsIgnoreCase(action)) {
				return getStatusInfo();
			} else if ("snapshot".equalsIgnoreCase(action)) {
				return getSnapshotInfo();
			} else if ("stop".equalsIgnoreCase(action)) {
				String actionInfo = "stoped by console.";
				logger.info(actionInfo);
			}

		}
		return consoleHelpInfo.toString();
	}

	private String getStatusInfo() {
		StringBuffer buffer = new StringBuffer();

		buffer.append("<html>");
		buffer.append("\n<body bgcolor=\"white\">");
		buffer.append("<h1>Netty Server Console</h1>");

		buffer.append("<pre>");
		buffer.append("\n\n====Uptime");
		try {
			ServerInfo.loadUptime(buffer);
		} catch (IOException e) {
		}

		buffer.append("\n\n====System Info");
		try {
			ServerInfo.loadSystemInfo(buffer);
		} catch (IOException e) {
		}

		buffer.append("\n\n====Memory Info");
		try {
			ServerInfo.loadMemoryInfo(buffer);
		} catch (IOException e) {
		}

		buffer.append("\n\n====Thread Info");
		try {
			ServerInfo.loadThreadInfo(buffer);
		} catch (IOException e) {
		}

		buffer.append("</pre>");
		buffer.append("<br>\n<h4>\n</font>\n</body>\n</html>");

		return buffer.toString();
	}

	/**
	 * @return
	 */
	private String getSnapshotInfo() {
		StringBuffer buffer = new StringBuffer();

		buffer.append("<html>");
		buffer.append("\n<body bgcolor=\"white\">");
		buffer.append("<h1>StatSDK Server Console</h1>\n");
		buffer.append("<h2>Snapshot query at " + new Date() + "</h2>\n");
		buffer.append(snapshot.getWatchInfo(0, 1, true));
		buffer.append("\n</body>\n</html>");

		return buffer.toString();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg)
			throws Exception {
		if (msg instanceof HttpRequest) {
			HttpRequest request = this.request = (HttpRequest) msg;
			if (request.getUri().contains("/console")) {
				ResponseGenerator.writeResponse(ctx.channel(), request,
						makeConsoleResponse(request), Constants.CONTENT_HTML_TYPE);
			} else if (request.getUri().contains("/appjson")) {
				ResponseGenerator.writeResponse(ctx.channel(), request,
						jsonResponse(), Constants.CONTENT_JSON_TYPE);
			} else {
				ResponseGenerator.writeResponse(ctx.channel(), request,
						consoleHelpInfo.toString(), Constants.CONTENT_HTML_TYPE);
			}
		}
	}

	/**
	 * 获取reponse的json串
	 *
	 * @return
	 */
	private String jsonResponse() {
		String result = JSON.toJSONString(snapshot.getMap());
		return result;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		logger.error(cause.toString());
		ResponseGenerator.WriteErrorResponse(ctx.channel(), request, cause);
	}

}