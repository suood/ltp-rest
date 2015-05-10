package com.soften.util;

import com.soften.ltp.TransposeJson.ICTCLASWord;
import com.soften.ltp.TransposeJson.ResultEmotionWord;
import com.soften.ltp.TransposeJson.Word;
import com.soften.ltp.model.EmotionWord;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by mysgq1 on 15/3/19.
 */
public class CloudTransposeICTCLAS {

    /**
     * * 将ICTCLAS分词 与 哈工大句法分析结合起来
     *
     * @param wordList &nbsp;<code>List&lt;Word&gt;</code>&nbsp;对象
     * @param ictclasWordList &nbsp;ICTCLAS分词结果<code>java.util.List</code>&nbsp;对象
     * @param emotionWordLinkedHashMap &nbsp;哈工大句法分析的&nbsp;<code>java.util.LinkedHashMap</code>&nbsp;对象
     * @return <code>List&lt;ResultEmotionWord&gt;</code>
     * @see com.soften.ltp.TransposeJson.ICTCLASWord
     * @see com.soften.ltp.TransposeJson.ResultEmotionWord
     * @see com.soften.ltp.TransposeJson.Sent
     */
    public static List<ResultEmotionWord> resultTranspose(List<Word>wordList,List<ICTCLASWord> ictclasWordList ,LinkedHashMap<String,EmotionWord>emotionWordLinkedHashMap){
        LinkedHashMap<String,ICTCLASWord> ictclasMap = transferICTCLASWord(ictclasWordList);
        LinkedHashMap <String,Word>  ltpMap = transferLTPWord(wordList);
        return null;
    }

    private static LinkedHashMap<String,ICTCLASWord> transferICTCLASWord(List<ICTCLASWord>ictclasWordList){
        LinkedHashMap<String,ICTCLASWord> map = new LinkedHashMap<String,ICTCLASWord>();
        for (ICTCLASWord ictclasWord : ictclasWordList) {
            map.put(ictclasWord.getWord(),ictclasWord);
        }
        return map;
    }
    private static LinkedHashMap<String,Word> transferLTPWord(List<Word>wordList){
        LinkedHashMap<String,Word> map = new LinkedHashMap<String,Word>();
        for (Word word : wordList) {
            map.put(word.getCont(),word);
        }
        return map;
    }
}
