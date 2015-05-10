package com.soften.ltp.TransposeJson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mysgq1 on 15/3/4.
 */
public    class Sent{


        private Integer id;
        private String cont;
        private List<Word> word = new ArrayList<Word>();

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCont() {
            return cont;
        }

        public void setCont(String cont) {
            this.cont = cont;
        }

        public List<Word> getWord() {
            return word;
        }

        public void setWord(List<Word> word) {
            this.word = word;
        }
    }