package com.soften.ltp.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.soften.ltp.TransposeJson.Sent;
import com.soften.ltp.TransposeJson.Word;
import com.soften.ltp.mapper.SentenceEmotionMapper;
import com.soften.ltp.model.SentenceEmotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.sockjs.frame.Jackson2SockJsMessageCodec;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by mysgq1 on 15/3/9.
 */
@Service
public class SentenceEmotionService {
    @Autowired
    private WordEmotionService wordEmotionService;
    @Autowired
    private SentenceEmotionMapper sentenceEmotionMapper;

    /**
     * 根据句子中的数组查询获得 情感词及对应结果。
     * @param words
     * @return
     */
    public int  saveSentenceEmotionByWords(String []words){
        Map map = wordEmotionService.getSentEmotionWordMap(words);
        SentenceEmotion sentenceEmotion = new SentenceEmotion();
        sentenceEmotion.setUuid(UUID.randomUUID().toString().replaceAll("-",""));
        List list = Arrays.asList(words);
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBufferWords = new StringBuffer();
        for (String word : words) {
            stringBufferWords.append(word);
            if (map.get(word) != null) {
                stringBuffer.append(JSON.toJSONString(map.get(word)));
            }else{
                stringBuffer.append(word);
            }
        }
        sentenceEmotion.setSentence(stringBufferWords.toString());
        sentenceEmotion.setSentenceWords(stringBufferWords.toString());
        return sentenceEmotionMapper.insert(sentenceEmotion);
    }
}
