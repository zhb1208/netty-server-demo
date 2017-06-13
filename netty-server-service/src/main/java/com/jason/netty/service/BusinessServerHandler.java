package com.jason.netty.service;

import com.jason.netty.service.snapshot.MessageWatch;
import com.jason.utils.ByteUtils;
import com.jason.utils.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * 业务服务处理类
 *
 * @author zhanghongbing
 * @version 15/3/24
 */
public class BusinessServerHandler extends ChannelInboundHandlerAdapter {

    private static final byte[] CONTENT = { 'H', 'e', 'l', 'l', 'o', ' ', 'W', 'o', 'r', 'l', 'd' };

    private static Logger log = LoggerFactory.getLogger(BusinessServerHandler.class);

    private HttpRequest request;
    private MessageWatch messageWatch;
    private List<Exception> listException;
    private byte[] contentByte = null;
    private String curUri;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        if (msg instanceof HttpRequest) {
            request = (HttpRequest) msg;

            // request检测点
            messageWatch = new MessageWatch(request);
            messageWatch.start(MessageWatch.STATE_ALL);
            listException = new ArrayList<Exception>();

            String uri = request.getUri();
            curUri = uri;

            // new getMethod
            if (log.isDebugEnabled()) {
                log.info("request uri: " + uri);
            }
            HttpHeaders headers = request.headers();

            // new getMethod
            if (log.isDebugEnabled()) {
                for (Map.Entry<String, String> entry : request.headers()) {
                    log.info("HEADER: " + entry.getKey() + '=' + entry.getValue());
                }
            }

            // get remoteIp
            String clientIP = headers.get("X-Forwarded-For");
            if (clientIP == null) {
                InetSocketAddress insocket = (InetSocketAddress) ctx.channel()
                        .remoteAddress();
                clientIP = insocket.getAddress().getHostAddress();
            }
            clientIP = StringUtils.isNotBlank(clientIP)?clientIP:"null";
            messageWatch.setRemoteIP(clientIP);
        }
        try {
            if (msg instanceof HttpContent) {
                //内容处理
                ApplicationConfig config = new ApplicationConfig();

                // 判断是否是最后内容
                if (msg instanceof LastHttpContent) {
                    HttpContent chunk2 = (HttpContent) msg;
                    ByteBuf buf2 = chunk2.content();
                    byte[] contentlast = ByteUtils.read(buf2);
                    // 合并日志
                    if (contentByte != null) {
                        contentByte = ByteUtils.byteMerger(contentByte, contentlast);
                    } else {
                        contentByte = contentlast;
                    }
                    StringBuilder outPut = config.process(request, msg,contentByte,
                            messageWatch,listException);

                    // response 输出
                    FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
                            OK, Unpooled.wrappedBuffer(outPut.toString().getBytes("UTF-8")));
                    response.headers().set(CONTENT_TYPE, "text/plain");
                    response.headers().set(CONTENT_LENGTH,
                            response.content().readableBytes());
                    if (HttpHeaders.isKeepAlive(request)) {
                        response.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
                    }
                    ctx.write(response);
                    ctx.flush();
                    contentByte = null;
                } else {
                    // 拼接chunk的Byte内容
                    HttpContent chunk1 = (HttpContent) msg;
                    ByteBuf buf = chunk1.content();
                    if (contentByte == null) {
                        contentByte = ByteUtils.read(buf);
                    } else {
                        byte[] contentTmp = ByteUtils.read(buf);
                        contentByte = ByteUtils.byteMerger(contentByte, contentTmp);
                    }
                }

//                // request获取输入
//                HttpContent chunk = (HttpContent) msg;
//                ByteBuf buf = chunk.content();
//                String content = buf.toString(io.netty.util.CharsetUtil.UTF_8);
//                buf.release();
//                log.info("request jsonstring:" + content);
//
//                BaseJsonProcessor baseJsonProcessor = new BaseJsonProcessor(messageWatch, listException);
//                StringBuilder outPut = baseJsonProcessor.makeAppContent(content);
//
//                // response 输出
//                FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
//                        OK, Unpooled.wrappedBuffer(outPut.toString().getBytes("UTF-8")));
//                response.headers().set(CONTENT_TYPE, "text/plain");
//                response.headers().set(CONTENT_LENGTH,
//                        response.content().readableBytes());
//                if (HttpHeaders.isKeepAlive(request)) {
//                    response.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
//                }
//                ctx.write(response);
//                ctx.flush();
            }

            this.processMessageWatch(messageWatch, listException);
        } catch (Exception e) {
            log.error("BusinessServerHandler.channelRead Exception", e);
            // 异常监控
            messageWatch.setBusiness();
            messageWatch.setResponseCode(Integer.parseInt(Constants.FAIL_CODE));
            messageWatch.stop(MessageWatch.STATE_BUSINESS);
            listException.add(e);
            this.processMessageWatch(messageWatch, listException);
        }
    }

    /**
     * 处理MessageWatch
     * @param messageWatch
     * @param listException
     */
    private void processMessageWatch(MessageWatch messageWatch, List<Exception> listException) {
        ApplicationConfig.getSnapshotService().addMessageWatch(messageWatch, listException);
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage());
        ctx.close();
    }

}
