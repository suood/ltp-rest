package com.soften.ltp.service;

import com.soften.ltp.TransposeJson.Sent;
import com.soften.ltp.TransposeJson.Word;
import com.soften.ltp.mapper.EmotionWordMapper;
import com.soften.ltp.model.EmotionWord;
import com.soften.ltp.model.EmotionWordExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mysgq1 on 15/3/5.
 */
@Service
public class WordEmotionService {
    @Autowired
    private EmotionWordMapper emotionWordMapper;

    /**
     * 根据单一的词来获取对应的情感词对象
     * @param word
     * @return
     */
    public EmotionWord getEmotionWordBySingleWord(String word){
        if (word.equals("wp")){
            //标点符号不查询
            return  null;
        }
        EmotionWordExample emotionWordExample = new EmotionWordExample();
        emotionWordExample.createCriteria().andWordEqualTo(word);
        List<EmotionWord> list =emotionWordMapper.selectByExample(emotionWordExample);
       return  list != null&& list.size()>0 ?list.get(0):null;
    }

    /**
     * 查List中个字符串的对应情感次。按顺序存储在 &nbsp;<code>LinkedHashMap</code>&nbsp;对象中
     * @param stringList
     * @return 有序的<code>LinkedHashMap</code>
     * @see java.util.LinkedHashMap
     */
    public LinkedHashMap<String,EmotionWord> getSentEmotionWordMap(List<String>stringList){
        LinkedHashMap<String,EmotionWord> map = new LinkedHashMap<String,EmotionWord>();
        for (String word : stringList) {
            EmotionWord emotionWord =getEmotionWordBySingleWord(word);
            if (emotionWord != null) {
                map.put(emotionWord.getWord(),emotionWord);
            }
        }
        return  map;
    }
    public Map<String,EmotionWord> getSentEmotionWordMap(String [] words){
        Map<String,EmotionWord> map = new HashMap<String,EmotionWord>();
        for (String word : words) {
            EmotionWordExample emotionWordExample = new EmotionWordExample();
            emotionWordExample.createCriteria().andWordEqualTo(word);
            List<EmotionWord> list = emotionWordMapper.selectByExample(emotionWordExample);
            EmotionWord   wordEmotion  = list != null&& list.size()>0 ?list.get(0):null;
            if (wordEmotion != null) {
                map.put(wordEmotion.getWord(),wordEmotion);
            }
        }
        return  map;
    }


}
