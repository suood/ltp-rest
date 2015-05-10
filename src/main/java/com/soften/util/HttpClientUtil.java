package com.soften.util;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.soften.ltp.TransposeJson.Word;
import com.soften.ltp.TransposeJson.Xml4nlp;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;

/**
 * Created by mysgq1 on 15/2/28.
 *
 * Http Client工具类
 * 发起http client 请求
 *
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public final class HttpClientUtil {

    private static final String TYPE_STRING = "string";

    private static final String TYPE_BYTEARRAY = "byte[]";

    private static final String TYPE_STREAM = "stream";

    private static HttpClientUtil instance;

    private HttpClientUtil(){}

    /**
     * 使用默认的页面请求编码：utf-8
     * @return
     */
    public static HttpClientUtil getInstance(){
        return getInstance("UTF-8");
    }

    public static HttpClientUtil getInstance(String urlCharset){
        if(instance == null){
            instance = new HttpClientUtil();
        }
        //设置默认的url编码
        instance.setUrlCharset(urlCharset);
        return instance;
    }

    /**
     * 请求编码，默认使用utf-8
     */
    private String urlCharset = "UTF-8";

    /**
     * @param urlCharset 要设置的 urlCharset。
     */
    public void setUrlCharset(String urlCharset) {
        this.urlCharset = urlCharset;
    }
    /**
     * 获取字符串型返回结果，通过发起http post请求
     * @param targetUrl
     * @param params
     * @return
     * @throws Exception
     */
    public String getResponseBodyAsString(String targetUrl,Map params)throws Exception{

        return (String)setPostRequest(targetUrl,params,TYPE_STRING);
    }

    /**
     * 获取字符数组型返回结果，通过发起http post请求
     * @param targetUrl
     * @param params
     * @return
     * @throws Exception
     */
    public byte[] getResponseBodyAsByteArray(String targetUrl,Map params)throws Exception{

        return (byte[])setPostRequest(targetUrl,params,TYPE_BYTEARRAY);
    }

    /**
     * 将response的返回流写到outputStream中，通过发起http post请求
     * @param targetUrl                 请求地址
     * @param params                    请求参数<paramName,paramValue>
     * @throws Exception
     */
    public void getResponseBodyAsStream(String targetUrl,Map params)throws Exception{
        //response 的返回结果写到输出流
        setPostRequest(targetUrl,params,TYPE_STREAM);
    }

    /**
     * 利用http client模拟发送http post请求
     * @param targetUrl                 请求地址
     * @param params                    请求参数<paramName,paramValue>
     * @return  Object                  返回的类型可能是：String 或者 byte[] 或者 outputStream
     * @throws Exception
     */
    private Object setPostRequest(String targetUrl,Map params,String responseType)throws Exception{
        if(StringUtils.isBlank(targetUrl)){
            throw new IllegalArgumentException("调用HttpClientUtil.setPostRequest方法，targetUrl不能为空!");
        }

        Object responseResult = null;
        HttpClient client = null;
        PostMethod post = null;
        NameValuePair[] nameValuePairArr = null;
        SimpleHttpConnectionManager connectionManager = null;
        try{
            connectionManager =  new SimpleHttpConnectionManager(true);
            //连接超时,单位毫秒
            connectionManager.getParams().setConnectionTimeout(3000);
            //读取超时,单位毫秒
            connectionManager.getParams().setSoTimeout(60000);

            //设置获取内容编码
            connectionManager.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,urlCharset);
            client = new HttpClient(new HttpClientParams(),connectionManager);

            post = new PostMethod(targetUrl);
            //设置请求参数的编码
            post.getParams().setContentCharset(urlCharset);

            //服务端完成返回后，主动关闭链接
            post.setRequestHeader("Connection","close");
            if(params != null){
                nameValuePairArr = new NameValuePair[params.size()];

                Set key = params.keySet();
                Iterator keyIte = key.iterator();
                int index = 0;
                while(keyIte.hasNext()){
                    Object paramName = keyIte.next();
                    Object paramValue = params.get(paramName);
                    if(paramName instanceof String && paramValue instanceof String){
                        NameValuePair pair = new NameValuePair((String)paramName,(String)paramValue);
                        nameValuePairArr[index] = pair;
                        index++;
                    }
                }

                post.addParameters(nameValuePairArr);
            }

            int sendStatus = client.executeMethod(post);

            if(sendStatus == HttpStatus.SC_OK){

                if(StringUtils.equals(TYPE_STRING,responseType)){
                    responseResult = post.getResponseBodyAsString();
                }else if(StringUtils.equals(TYPE_BYTEARRAY,responseType)){
                    responseResult = post.getResponseBody();
                }else if(StringUtils.equals(TYPE_STREAM,responseType)){
                    InputStream tempStream = post.getResponseBodyAsStream();
                    byte[] temp = new byte[1024];
                    int index = -1;
                    while((index = tempStream.read(temp)) != -1){
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            //释放链接
            if(post != null){
                post.releaseConnection();
            }

            //关闭链接
            if(connectionManager != null){
                connectionManager.shutdown();
            }
        }

        return responseResult;
    }

    /**
     * 测试方法
     * @param args
     */
    public static void main1(String[] args)throws Exception{

        String url = "http://182.92.184.136:12345/ltp";
        Map params = new HashMap();
        params.put("s","洗衣机噪声很小,容量很大，色彩绚丽，款式新颖，简直太棒了！");
        params.put("x","n");
        params.put("t","srl");
        HttpClientUtil util = HttpClientUtil.getInstance("utf-8");
        String resultStr = util.getResponseBodyAsString(url, params);
        StringBuffer stringBuffer = new StringBuffer(StringConvertUtil.xml2json(resultStr));
        System.out.println(resultStr);
        Xml4nlp xml4nlp = XMLTransposeUtil.transposeXMLString(resultStr);

        System.out.println(JSON.toJSON(xml4nlp));
        List<Word> wordList = xml4nlp.getDoc().getPara().getSent().get(0).getWord();
        for (Word word : wordList) {
            System.out.println(word.getCont());
        }
//        resultStr =stringBuffer.substring(stringBuffer.indexOf(":")+1, stringBuffer.length()-1);
//        System.out.println(resultStr);
//        Gson gson = new Gson();
//        Xml4nlp xml4nlpt = gson.fromJson(resultStr,Xml4nlp.class);
////        Xml4nlp xml4nlpt = .parseObject(resultStr, Xml4nlp.class);
////        System.out.println(xml4nlpt.getNote());
//
//        System.out.println(resultStr);

//        for (String s : jsonObject.keySet()) {
//            JSONObject jsonObject1 = JSONObject.parseObject(jsonObject.get(s).toString());
//            for (String s1 : jsonObject1.keySet()) {
//                System.out.println(s1);
//            }
//        }
//        System.out.println("HttpClientUtil.main()-result:"+);
    }

    public static void main(String[] args) throws Exception {
        StringBuffer stringBuffer = new StringBuffer("海尔洗衣机噪声不小，容量也很大。");

        String url = "http://182.92.184.136:12345/ltp";
        Map params = new HashMap();
        params.put("s",stringBuffer.toString());
        params.put("x","n");
        params.put("t","srl");
        long now =System.currentTimeMillis();
        HttpClientUtil util = HttpClientUtil.getInstance("utf-8");
        String resultStr = util.getResponseBodyAsString(url, params);
        System.out.println(System.currentTimeMillis()-now);
        System.out.println(resultStr);
    }
}

