package com.soften.util;

import com.apple.laf.AquaTreeUI;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mysgq1 on 15/3/13.
 */
public class ICTCLASUtil {
    private static  final String WORD_URL = "http://118.144.77.154:8081/hmnlp/words.do";
    private static final String SENTENCE_URL = "http://118.144.77.154:8081/hmnlp/sentence.do";
    private static final HttpClientUtil HTTP_CLIENT_UTIL = HttpClientUtil.getInstance();


    public  static String getWords(String id,String content,String resultType) throws Exception {
        resultType = resultType==null||resultType.isEmpty()?"json":resultType;
        Map params = new HashMap();
        params.put("id", id);
        params.put("content", content);
        params.put("result",resultType);
        return HTTP_CLIENT_UTIL.getResponseBodyAsString(WORD_URL,params);
    }

    public static String getSentence(String id,String content,String sentenceKey,String sentenceBreakKey) throws Exception {
        Map params = new HashMap();
        params.put("id", id);
        params.put("content", content);
        params.put("sentenceKey",sentenceKey);
        params.put("sentenceBreakKey",sentenceBreakKey);
        return HTTP_CLIENT_UTIL.getResponseBodyAsString(SENTENCE_URL,params);
    }
}
