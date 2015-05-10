package com.soften.ltp.controller.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.soften.util.HttpClientUtil;
import com.soften.util.StringConvertUtil;
import com.soften.util.XMLTransposeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mysgq1 on 15/2/28.
 */
@Controller("/")
public class RestLTPJsonResultController {

    private static final String LTP_URL = "http://182.92.184.136:12345/ltp";
    private static final HttpClientUtil util = HttpClientUtil.getInstance("utf-8");
//    ws(分词)，pos(词性标注)，ner(命名实体识别)，dp(依存句法分析)，srl(语义角色标注),all(全部任务)
    @RequestMapping("/all/{text}/")
    public @ResponseBody String getAllJson(@PathVariable("text")String text) throws Exception {
        Map params = new HashMap();
        params.put("s","text");
        params.put("x","n");
        params.put("t","all");
        String resultStr = util.getResponseBodyAsString(LTP_URL,params);
        return JSON.toJSON(XMLTransposeUtil.transposeXMLString(resultStr)).toString();
    }
    @RequestMapping("/ws/{text}/")
    public @ResponseBody String getWSJson(@PathVariable("text")String text)throws Exception {
        Map params = new HashMap();
        params.put("s","text");
        params.put("x","n");
        params.put("t","ws");
        String resultStr = util.getResponseBodyAsString(LTP_URL,params);
        return StringConvertUtil.xml2json(resultStr);
    }
    @RequestMapping("/pos/{text}")
    public @ResponseBody String getPOSJson(@PathVariable("text")String text) throws Exception {
        Map params = new HashMap();
        params.put("s","text");
        params.put("x","n");
        params.put("t","pos");
        String resultStr = util.getResponseBodyAsString(LTP_URL,params);
        return resultStr;
    }
    @RequestMapping("/ner/{text}")
    public @ResponseBody String getNERJson(@PathVariable("text")String text) throws Exception {
        Map params = new HashMap();
        params.put("s","text");
        params.put("x","n");
        params.put("t","ner");
        String resultStr = util.getResponseBodyAsString(LTP_URL,params);
        return resultStr;
    }
    @RequestMapping("/dp/{text}")
    public @ResponseBody String getDPJson(@PathVariable("text")String text) throws Exception {
        Map params = new HashMap();
        params.put("s","text");
        params.put("x","n");
        params.put("t","dp");
        String resultStr = util.getResponseBodyAsString(LTP_URL,params);
        return resultStr;
    }
    @RequestMapping("/srl/{text}")
    public @ResponseBody String getSRLJson(@PathVariable("text")String text) throws Exception {
        Map params = new HashMap();
        params.put("s","text");
        params.put("x","n");
        params.put("t","srl");
        String resultStr = util.getResponseBodyAsString(LTP_URL,params);
        return resultStr;
    }
}
