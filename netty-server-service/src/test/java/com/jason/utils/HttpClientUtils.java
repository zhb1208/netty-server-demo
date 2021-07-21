package com.jason.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jason.command.CommandEnum;
import com.jason.dto.*;
import com.jason.dto.actioninfo.CommonActionData;
import com.jason.dto.user.UserDto;
import com.jason.util.JSONTool;
import org.apache.http.Consts;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 13-12-23
 * Time: 上午11:33
 * To change this template use File | Settings | File Templates.
 */
public class HttpClientUtils {

    private static Logger log = null;

    public static String urlGet(String url) {
        String response = null;
        try {
            // 创建一个默认的HttpClient
            HttpClient httpclient = HttpClients.createDefault();
            // 创建一个GET请求
            HttpGet request = new HttpGet(url);
            // 发送GET请求，并将响应内容转换成字符串
            response = httpclient.execute(request, new BasicResponseHandler());

//            log.v("response text", response);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static void urlConnectionPost(String urlStr, String content) {
        StringBuilder responseBuilder = null;
        BufferedReader reader = null;
        OutputStreamWriter wr = null;

        URL url;
        try {
            url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(1000 * 5);
            wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(content);
            wr.flush();

            // Get the response
            reader = new BufferedReader(new InputStreamReader(conn
                    .getInputStream()));
            responseBuilder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            wr.close();
            reader.close();

            System.out.println(responseBuilder.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * api请求
     * @param urlStr
     * @param requestDto
     * @return
     */
    public static <T> T urlConnectionPost(String urlStr, RequestDto requestDto, TypeReference<T> type) {
        StringBuilder responseBuilder = null;
        BufferedReader reader = null;
        OutputStreamWriter wr = null;
        String content = null;
        ReplyDto replyDto = null;
        URL url;
        try {
            content = JSONTool.toJSONString(requestDto);
            url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(1000 * 5);
            wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(content);
            wr.flush();

            // Get the response
            reader = new BufferedReader(new InputStreamReader(conn
                    .getInputStream()));
            responseBuilder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            wr.close();
            reader.close();

            System.out.println(responseBuilder.toString());
            return JSONTool.parseString2Object(responseBuilder.toString(), type);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


    /**
     * api请求
     * @param urlStr
     * @param requestDto
     * @return
     */
    public static ReplyDto urlConnectionPost(String urlStr, RequestDto requestDto) {
        StringBuilder responseBuilder = null;
        BufferedReader reader = null;
        OutputStreamWriter wr = null;
        String content = null;
        ReplyDto replyDto = null;
        URL url;
        try {
            content = JSONTool.toJSONString(requestDto);
            url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(1000 * 5);
            wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(content);
            wr.flush();

            // Get the response
            reader = new BufferedReader(new InputStreamReader(conn
                    .getInputStream()));
            responseBuilder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            wr.close();
            reader.close();

            System.out.println(responseBuilder.toString());
            return JSONTool.parseString2Object(responseBuilder.toString(), ReplyDto.class);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static void cloudScan(){
        String url = "http://localhost:8080/jason";
        // instance requestDto
        RequestDto requestDto = new RequestDto();
        SystemInfo systemInfo = new SystemInfo();
        systemInfo.setImei("11111");
        systemInfo.setImsi("22222");
        ClientInfo clientInfo = new ClientInfo();
        ActionInfo actionInfo = new ActionInfo();
        actionInfo.setValue(CommandEnum.CLOUDSCAN.getCode());
        CommonActionData commonActionData = new CommonActionData();
        commonActionData.setAddress("request address");
        commonActionData.setBody("request data body");
        commonActionData.setDate("2013-12-24");
        commonActionData.setSmstype("1");

        // data
        Map<String, String> data = new HashMap<String, String>();
        data.put("requestData", JSONTool.toJSONString(commonActionData));
        requestDto.setData(data);

        requestDto.setSystemInfo(systemInfo);
        requestDto.setClientInfo(clientInfo);
        requestDto.setActionInfo(actionInfo);

        // cloudScan request
        ReplyDto replyDto = HttpClientUtils.urlConnectionPost(url, requestDto);
        // test replyDto
        Map<String, String> replyMap = replyDto.getData();
        String replyData = replyMap.get("replyData");
        CommonActionData commonActionData1 = JSONTool.parseString2Object(replyData, CommonActionData.class);
        ActionInfo replayActionInfo = replyDto.getActionInfo();
        System.out.println(replayActionInfo.getCode());
        System.out.println(replayActionInfo.getValue());
        System.out.println(commonActionData1.getAddress());
        System.out.println(commonActionData1.getBody());
        System.out.println(commonActionData1.getDate());
        System.out.println(commonActionData1.getSmstype());
    }


    public static void register(){
        String url = "http://localhost:8080/jason";
        // instance requestDto
        RequestDto requestDto = new RequestDto();
        SystemInfo systemInfo = new SystemInfo();
        systemInfo.setImei("11111");
        systemInfo.setImsi("22222");
        ClientInfo clientInfo = new ClientInfo();
        ActionInfo actionInfo = new ActionInfo();
        actionInfo.setValue(CommandEnum.REGISTER.getCode());
        UserDto userDto = new UserDto();
//        userDto.setId(1L);
        userDto.setName("registerusername");
        userDto.setEmail("register@hesine.com");

        // data
        Map<String, String> data = new HashMap<String, String>();
        data.put("requestData", JSONTool.toJSONString(userDto));
        requestDto.setData(data);

        requestDto.setSystemInfo(systemInfo);
        requestDto.setClientInfo(clientInfo);
        requestDto.setActionInfo(actionInfo);

        // cloudScan request
        ReplyDto replyDto = HttpClientUtils.urlConnectionPost(url, requestDto);
        // test replyDto
        Map<String, String> replyMap = replyDto.getData();
        String replyData = replyMap.get("replyData");
        CommonActionData commonActionData1 = JSONTool.parseString2Object(replyData, CommonActionData.class);
        ActionInfo replayActionInfo = replyDto.getActionInfo();
        System.out.println(replayActionInfo.getCode());
        System.out.println(replayActionInfo.getValue());
        System.out.println(commonActionData1.getAddress());
        System.out.println(commonActionData1.getBody());
        System.out.println(commonActionData1.getDate());
        System.out.println(commonActionData1.getSmstype());
    }

    public static void active(){
        String url = "http://localhost:8080/jason";
        // instance requestDto
        RequestDto requestDto = new RequestDto();
        SystemInfo systemInfo = new SystemInfo();
        systemInfo.setImei("11111");
        systemInfo.setImsi("22222");
        ClientInfo clientInfo = new ClientInfo();
        ActionInfo actionInfo = new ActionInfo();
        actionInfo.setValue(CommandEnum.ACTIVED.getCode());
        Map<String, String> map = new HashMap<String, String>();
        map.put("requestData", "mapvalue");

        requestDto.setSystemInfo(systemInfo);
        requestDto.setClientInfo(clientInfo);
        requestDto.setActionInfo(actionInfo);
        requestDto.setData(map);

        // active request
        ReplyDto replyDto = HttpClientUtils.urlConnectionPost(url, requestDto);

        // test replyDto
        ActionInfo replayActionInfo = replyDto.getActionInfo();
        Map<String, String> replyMap = replyDto.getData();
        String replyData = replyMap.get("replyData");
        CommonActionData commonActionData1 = JSONTool.parseString2Object(replyData, CommonActionData.class);
        System.out.println(replayActionInfo.getCode());
        System.out.println(replayActionInfo.getValue());
        System.out.println(commonActionData1.getAddress());
        System.out.println(commonActionData1.getBody());
        System.out.println(commonActionData1.getDate());
        System.out.println(commonActionData1.getSmstype());
    }

    public static void demo(){
//        String url = "http://localhost:8080/jason";
        String url = "http://localhost:8080";
        String content = "hello world!";
        // instance requestDto
        RequestDto requestDto = new RequestDto();
        SystemInfo systemInfo = new SystemInfo();
        systemInfo.setImei("11111");
        systemInfo.setImsi("22222");
        ClientInfo clientInfo = new ClientInfo();
        ActionInfo actionInfo = new ActionInfo();
        actionInfo.setValue(CommandEnum.ACTIVED.getCode());
        Map<String, String> map = new HashMap<String, String>();
        map.put("requestData", "mapvalue");

        requestDto.setSystemInfo(systemInfo);
        requestDto.setClientInfo(clientInfo);
        requestDto.setActionInfo(actionInfo);
        requestDto.setData(map);

        // demo request
        HttpClientUtils.urlConnectionPost(url, content);

    }

    public static byte[] postHeaderRequest(String url, byte[] postData)
            throws ClientProtocolException, IOException {
        URL urlObj = new URL(url);
        CloseableHttpClient httpClient = getHttpClient(urlObj.getHost(),
                urlObj.getPort());
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new ByteArrayEntity(postData));


        byte[] content = null;
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            content = EntityUtils.toByteArray(response.getEntity());
        } finally {
            httpClient.close();
        }
        return content;
    }

    private static PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();

    public static synchronized CloseableHttpClient getHttpClient(String host,
                                                                 int port) {
        HttpHost targetHost = new HttpHost(host, port);
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setCharset(Consts.UTF_8).build();

        cm.setMaxTotal(200); // 连接池里的最大连接数
        cm.setDefaultMaxPerRoute(50); // 每个路由的默认最大连接数
        cm.setMaxPerRoute(new HttpRoute(targetHost), 50);
        cm.setConnectionConfig(targetHost, connectionConfig);
        HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
            public boolean retryRequest(IOException exception,
                                        int executionCount, HttpContext context) {
                if (executionCount >= 5) {
                    // 如果已经重试了5次，就放弃
                    return false;
                }
                return true;
            }

        };
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm).setRetryHandler(myRetryHandler)
                .build();

        return httpClient;
    }

    public static byte[] gzip(byte[] foo){
        byte[] returnByteData = null;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            dos.writeLong(0);
            dos.writeInt(1);
            dos.writeLong(0);
            dos.write(foo);
            dos.flush();

            returnByteData = os.toByteArray();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

        return returnByteData;
    }

    public static void updateServerInfo(String url) throws Exception{
        //SendPNTokenRequest baseRequest = new SendPNTokenRequest();


        ActionInfo actionInfo = new ActionInfo();
        actionInfo.setCode(1001);
        actionInfo.setValue(1);
        String jsonString = JSON.toJSONString(actionInfo);
        System.out.println("postData : "+ jsonString);
        byte[] response = HttpClientUtils.postHeaderRequest(url, gzip(jsonString.getBytes()));
        if (response != null) {
//            byte[] responseByte = aes.rnmDecrypt(response);
            System.out.println("response : "+ new String(response));
        } else {
            System.out.println("reponse == null");
        }

    }

    public static void main(String[] args){
        String url = "http://localhost:8080";
//        String url = "http://localhost:8844";
//        HttpClientUtils.active();
        HttpClientUtils.cloudScan();
//        HttpClientUtils.register();
//        HttpClientUtils.demo();
//        try {
//            HttpClientUtils.updateServerInfo(url);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}
