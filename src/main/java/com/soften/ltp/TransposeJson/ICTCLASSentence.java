package com.soften.ltp.TransposeJson;

/**
 * Created by mysgq1 on 15/3/20.
 */
public class ICTCLASSentence {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    private String id;
    private String word;
    private String sentence;
    private String index;
}
