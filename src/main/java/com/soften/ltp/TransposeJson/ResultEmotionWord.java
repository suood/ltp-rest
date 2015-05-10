package com.soften.ltp.TransposeJson;

import com.soften.ltp.model.EmotionWord;

/**
 * Created by mysgq1 on 15/3/19.
 */
public class ResultEmotionWord {
    public String getWordString() {
        return wordString;
    }

    public void setWordString(String wordString) {
        this.wordString = wordString;
    }

    public EmotionWord getEmotionWord() {
        return emotionWord;
    }

    public void setEmotionWord(EmotionWord emotionWord) {
        this.emotionWord = emotionWord;
    }

    private String wordString;
    private EmotionWord emotionWord;
}
