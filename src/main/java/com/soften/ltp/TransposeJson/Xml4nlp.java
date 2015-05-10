package com.soften.ltp.TransposeJson;


import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by mysgq1 on 15/3/4.
 */

public class Xml4nlp {
    //    xml4nlp 为根结点，无任何属性值；
//    note 为标记结点，具有的属性分别为：sent, word, pos, ne, parser, srl；分别代表分句，分词，词性标注，命名实体识别，依存句法分析，词义消歧，语义角色标注；值为"n"，表明未做，值为"y"则表示完成，如pos="y"，表示已经完成了词性标注；
//    doc 为篇章结点，以段落为单位包含文本内容；无任何属性值；
//    para 为段落结点，需含id 属性，其值从0 开始；
//    sent 为句子结点，需含属性为id，cont；id 为段落中句子序号，其值从0 开始；cont 为句子内容；
//    word 为分词结点，需含属性为id, cont；id 为句子中的词的序号，其值从0 开始，cont为分词内容；可选属性为 pos, ne, parent, relate；pos 的内容为词性标注内容；ne 为命名实体内容；parent 与 relate 成对出现，parent 为依存句法分析的父亲结点id 号，relate 为相对应的关系；
//    arg 为语义角色信息结点，任何一个谓词都会带有若干个该结点；其属性为id, type, beg，end；id 为序号，从0 开始；type 代表角色名称；beg 为开始的词序号，end 为结束的序号；
//    各结点及属性的逻辑关系说明如下：
//
//    各结点层次关系可以从图中清楚获得，凡带有id 属性的结点是可以包含多个；
//    如果sent="n"即未完成分句，则不应包含sent 及其下结点；
//    如果sent="y" word="n"即完成分句，未完成分词，则不应包含word 及其下结点；
//    其它情况均是在sent="y" word="y"的情况下：
//    如果 pos="y" 则分词结点中必须包含pos 属性；
//    如果 ne="y" 则分词结点中必须包含ne 属性；
//    如果 parser="y" 则分词结点中必须包含parent 及relate 属性；
//    如果 srl="y" 则凡是谓词(predicate)的分词会包含若干个arg 结点；
//    在XML格式的分析中，用户可以通过指定参数pattern=ws | pos | ner | dp | srl | all 来指名分析任务并获取对应的XML结果。
    public Note getNote() {
        return note;
    }public void setNote(Note note) {
        this.note = note;
    }public Doc getDoc() {
        return doc;
    }public void setDoc(Doc doc) {
        this.doc = doc;
    }private Note note;
    private Doc doc;


    public static void main(String [] args){
        String s = "{\"note\":{\"srl\":\"y\",\"sent\":\"y\",\"ne\":\"y\",\"word\":\"y\",\"parser\":\"y\",\"wsd\":\"n\",\"pos\":\"y\"},\"doc\":{\"para\":{\"id\":\"0\",\"sent\":[{\"id\":\"0\",\"cont\":\"洗衣机噪声很小,容量很大，色彩绚丽，款式新颖，简直太棒了！\",\"word\":[{\"relate\":\"ATT\",\"id\":\"0\",\"ne\":\"O\",\"parent\":\"1\",\"cont\":\"洗衣机\",\"pos\":\"n\"},{\"relate\":\"SBV\",\"id\":\"1\",\"ne\":\"O\",\"parent\":\"3\",\"cont\":\"噪声\",\"pos\":\"n\"},{\"relate\":\"ADV\",\"id\":\"2\",\"ne\":\"O\",\"parent\":\"3\",\"cont\":\"很\",\"pos\":\"d\"},{\"relate\":\"HED\",\"id\":\"3\",\"ne\":\"O\",\"parent\":\"-1\",\"cont\":\"小\",\"pos\":\"a\",\"arg_1\":[{\"id\":\"0\",\"beg\":\"0\",\"type\":\"A0\",\"end\":\"1\"},{\"id\":\"1\",\"beg\":\"2\",\"type\":\"ADV\",\"end\":\"2\"}]},{\"relate\":\"WP\",\"id\":\"4\",\"ne\":\"O\",\"parent\":\"7\",\"cont\":\",\",\"pos\":\"wp\"},{\"relate\":\"SBV\",\"id\":\"5\",\"ne\":\"O\",\"parent\":\"7\",\"cont\":\"容量\",\"pos\":\"n\"},{\"relate\":\"ADV\",\"id\":\"6\",\"ne\":\"O\",\"parent\":\"7\",\"cont\":\"很\",\"pos\":\"d\"},{\"relate\":\"COO\",\"id\":\"7\",\"ne\":\"O\",\"parent\":\"3\",\"cont\":\"大\",\"pos\":\"a\"},{\"relate\":\"WP\",\"id\":\"8\",\"ne\":\"O\",\"parent\":\"3\",\"cont\":\"，\",\"pos\":\"wp\"},{\"relate\":\"SBV\",\"id\":\"9\",\"ne\":\"O\",\"parent\":\"10\",\"cont\":\"色彩\",\"pos\":\"n\"},{\"relate\":\"COO\",\"id\":\"10\",\"ne\":\"O\",\"parent\":\"3\",\"cont\":\"绚丽\",\"pos\":\"a\",\"arg\":{\"id\":\"0\",\"beg\":\"9\",\"type\":\"A0\",\"end\":\"9\"}},{\"relate\":\"WP\",\"id\":\"11\",\"ne\":\"O\",\"parent\":\"10\",\"cont\":\"，\",\"pos\":\"wp\"},{\"relate\":\"SBV\",\"id\":\"12\",\"ne\":\"O\",\"parent\":\"13\",\"cont\":\"款式\",\"pos\":\"n\"},{\"relate\":\"COO\",\"id\":\"13\",\"ne\":\"O\",\"parent\":\"10\",\"cont\":\"新颖\",\"pos\":\"a\",\"arg\":{\"id\":\"0\",\"beg\":\"12\",\"type\":\"C-A0\",\"end\":\"12\"}},{\"relate\":\"WP\",\"id\":\"14\",\"ne\":\"O\",\"parent\":\"10\",\"cont\":\"，\",\"pos\":\"wp\"},{\"relate\":\"ADV\",\"id\":\"15\",\"ne\":\"O\",\"parent\":\"17\",\"cont\":\"简直\",\"pos\":\"d\"},{\"relate\":\"ADV\",\"id\":\"16\",\"ne\":\"O\",\"parent\":\"17\",\"cont\":\"太\",\"pos\":\"d\"},{\"relate\":\"COO\",\"id\":\"17\",\"ne\":\"O\",\"parent\":\"3\",\"cont\":\"棒\",\"pos\":\"a\"},{\"relate\":\"RAD\",\"id\":\"18\",\"ne\":\"O\",\"parent\":\"17\",\"cont\":\"了\",\"pos\":\"u\"},{\"relate\":\"WP\",\"id\":\"19\",\"ne\":\"O\",\"parent\":\"3\",\"cont\":\"！\",\"pos\":\"wp\"}]},{\"id\":\"1\",\"cont\":\"我十分喜欢这款洗衣机，隆重向大家推荐。\",\"word\":[{\"relate\":\"SBV\",\"id\":\"0\",\"ne\":\"O\",\"parent\":\"2\",\"cont\":\"我\",\"pos\":\"r\"},{\"relate\":\"ADV\",\"id\":\"1\",\"ne\":\"O\",\"parent\":\"2\",\"cont\":\"十分\",\"pos\":\"m\"},{\"relate\":\"HED\",\"id\":\"2\",\"ne\":\"O\",\"parent\":\"-1\",\"cont\":\"喜欢\",\"pos\":\"v\"},{\"relate\":\"ATT\",\"id\":\"3\",\"ne\":\"O\",\"parent\":\"4\",\"cont\":\"这\",\"pos\":\"r\"},{\"relate\":\"ATT\",\"id\":\"4\",\"ne\":\"O\",\"parent\":\"5\",\"cont\":\"款\",\"pos\":\"q\"},{\"relate\":\"VOB\",\"id\":\"5\",\"ne\":\"O\",\"parent\":\"2\",\"cont\":\"洗衣机\",\"pos\":\"n\"},{\"relate\":\"WP\",\"id\":\"6\",\"ne\":\"O\",\"parent\":\"2\",\"cont\":\"，\",\"pos\":\"wp\"},{\"relate\":\"ADV\",\"id\":\"7\",\"ne\":\"O\",\"parent\":\"10\",\"cont\":\"隆重\",\"pos\":\"a\"},{\"relate\":\"ADV\",\"id\":\"8\",\"ne\":\"O\",\"parent\":\"10\",\"cont\":\"向\",\"pos\":\"p\"},{\"relate\":\"POB\",\"id\":\"9\",\"ne\":\"O\",\"parent\":\"8\",\"cont\":\"大家\",\"pos\":\"r\"},{\"relate\":\"COO\",\"id\":\"10\",\"ne\":\"O\",\"parent\":\"2\",\"cont\":\"推荐\",\"pos\":\"v\"},{\"relate\":\"WP\",\"id\":\"11\",\"ne\":\"O\",\"parent\":\"2\",\"cont\":\"。\",\"pos\":\"wp\"}]}]}}}";
//             JSONObject jsonObject = JSONObject.parseObject(s);
//             System.out.println(jsonObject.toJSONString());

    }
}






