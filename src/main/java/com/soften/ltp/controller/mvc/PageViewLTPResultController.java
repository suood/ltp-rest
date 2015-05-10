package com.soften.ltp.controller.mvc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.soften.ltp.TransposeJson.*;
import com.soften.ltp.model.EmotionWord;
import com.soften.ltp.service.SentenceEmotionService;
import com.soften.ltp.service.WordEmotionService;
import com.soften.util.HttpClientUtil;
import com.soften.util.ICTCLASUtil;
import com.soften.util.XMLTransposeUtil;
import com.sun.jersey.server.wadl.generators.resourcedoc.model.WadlParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by mysgq1 on 15/2/28.
 */
@Controller
public class PageViewLTPResultController {
    private static final String LTP_URL = "http://182.92.184.136:12345/ltp";
    private static final HttpClientUtil util = HttpClientUtil.getInstance("utf-8");
    @Autowired
    private SentenceEmotionService sentenceEmotionService;
    @Autowired
    private WordEmotionService wordEmotionService;
    /**
     * 提交一句话做 哈工大cloud 句法分析 和ICTCLAS分词
     * 将哈工大cloud的句法分析 映射到ICTCLAS的分词
     */

    @RequestMapping("/emotion")
    public @ResponseBody String getEmotion(ModelMap modelMap,HttpServletRequest httpServletRequest) throws Exception {
        //获取文本
        String text = httpServletRequest.getParameter("text");
        //获取主体词
        String keyWords = httpServletRequest.getParameter("keyword");
        String sentString = getSentenceAndWordIndex(text,keyWords);
        List <ICTCLASSentence> ictclasSentenceList = JSON.parseArray(sentString,ICTCLASSentence.class);
        for (ICTCLASSentence ictclasSentence : ictclasSentenceList) {
            //逐个处理关键词 及对应的句子
           // String jsonString = ICTCLASUtil.getWords(UUID.randomUUID().toString().replace("-", ""), ictclasSentence.getSentence(),"json");
            //将每一句话进行句法分析。
            if (ictclasSentence.getSentence() == null||ictclasSentence.getSentence().equals("")) {
                //如果句子是空的 那么跳过
                continue;
            }
            Xml4nlp xml4nlp = getXml4nlp(ictclasSentence.getSentence());
            //属性评价 获取
            List <Sent> sentList = xml4nlp.getDoc().getPara().getSent();
            //如果没有句子返回空
            if (sentList == null || sentList.isEmpty()){
                return "";
            }

            //获取第一个句子
            Sent sent = sentList.get(0);
            //获取所有的句法词汇
            List<Word> wordList = sent.getWord();
            
        }
        //获取所有的ICTCLAS分词词汇

        //生成新的List<String>wordList获取对应的词汇 情感词汇的Map<String,EmotionWord>
        //将所有词汇拼接称为一句话 传入句法分析
        //TODO 是否要将ICTCLAS分词过后的词组成句子提交到CLOUD中做句法分析。
//        text=stringBuffer.toString();

        Xml4nlp xml4nlp = getXml4nlp(text);
        List <Sent> sentList = xml4nlp.getDoc().getPara().getSent();
        //如果没有句子返回空
        if (sentList == null || sentList.isEmpty()){
            return "";
        }
        //获取第一个句子
        Sent sent = sentList.get(0);
        //获取所有的句法词汇
        List<Word> wordList = sent.getWord();
        //TODO 后期加入否定词
        //通过ICTCLAS分词的结果获得每个词的情感方向
        //Map <String,EmotionWord>map = wordEmotionService.getSentEmotionWordMap(wordStringList);

        return JSON.toJSONString(xml4nlp);
    }
    @RequestMapping("/sentence")
    public  String sentence() throws Exception {
        return "emotion";
    }

    /**
     * 要传入做句法分析的句子
     * @param text
     * @return
     * @throws Exception
     */
    private static Xml4nlp getXml4nlp(String text) throws Exception {
        Map params = new HashMap();
        params.put("s",text);
        params.put("x","n");
        params.put("t","all");
        String resultStr = util.getResponseBodyAsString(LTP_URL,params);
        Xml4nlp xml4nlp = XMLTransposeUtil.transposeXMLString(resultStr);
        return xml4nlp;
    }
    private  void getResult (StringBuffer stringBuffer,List<String>wordStringList,String jsonString){
        List <ICTCLASWord> ictclasWordList =JSON.parseArray(jsonString, ICTCLASWord.class);
        //生成新的List<String>wordList获取对应的词汇 情感词汇的Map<String,EmotionWord>
        //将所有词汇拼接称为一句话 传入句法分析
        for (ICTCLASWord ictclasWord : ictclasWordList) {
            stringBuffer.append(ictclasWord.getWord());
            wordStringList.add(ictclasWord.getWord());
        }
    }
    /**
     * 获取ICTCLAS句子分词结果
     * @param string
     * @return
     */
    private String getIctclasJson(String string) throws Exception {
        String jsonString = ICTCLASUtil.getWords(UUID.randomUUID().toString().replace("-", ""), string,"json");
        List <ICTCLASWord> ictclasWordList =JSON.parseArray(jsonString, ICTCLASWord.class);
        return jsonString;
    }
    /**
     * 获取ICTCLAS的  提取句子
     * http://118.144.77.154:8081/hmnlp/sentence.do
     * 参数：
     * 1、id
     * 2、content（内容）
     * 3、sentenceKey（提句关键词）
     * 4、sentenceBreakKey（断句符，可为空，默认取数据库中配置）
     *  返回：
     * 1、id
     * 2、word（配置到的关键词）
     * 3、sentence（提取出句子）
     * 4、index（词出现位置）
     */
    private String getSentenceAndWordIndex(String content,String sentenceKeyWords) throws Exception {
        Map params= new HashMap();
        params.put("id",UUID.randomUUID().toString().replace("-",""));
        params.put("content",content);
        params.put("sentenceKey",sentenceKeyWords);
//        params.put("sentenceBreakKey","#");

        //获取ICTCALS分词数据
        String jsonString =  util.getResponseBodyAsString("http://118.144.77.154:8081/hmnlp/sentence.do",params);

        return jsonString;
    }

//    分词
//    http://118.144.77.154:8081/hmnlp/words.do
//    参数：
//            1、id
//    2、content（内容）
//            3、result（返回类型，输入json）
//    返回：
//            1、分词结果
//
//

//
public static void main(String[] args) throws Exception {
    Map params= new HashMap();
    params.put("id",UUID.randomUUID().toString().replace("-",""));
    params.put("content","海尔洗衣机容量大，噪声小，洗得干净。");
    params.put("sentenceKey","海尔洗衣机");
//    params.put("sentenceBreakKey","");

    //获取ICTCALS分词数据
    String jsonString =  util.getResponseBodyAsString("http://118.144.77.154:8081/hmnlp/sentence.do",params);
    System.out.println(jsonString);
    List<ICTCLASSentence> ictclasSentenceList =JSON.parseArray(jsonString, ICTCLASSentence.class);
    ICTCLASSentence ictclasSentence  = ictclasSentenceList.get(0);
   Xml4nlp xml4nlp = getXml4nlp("海尔洗衣机容量大，噪声小，洗得干净。");
    List <Sent> sentList = xml4nlp.getDoc().getPara().getSent();
    List <Word> wordList = sentList.get(0).getWord();
    transposePropertyJudge(wordList,ictclasSentence.getWord());
}
    //通过词性和句法角色，以及词与词之间的句法关系来确定属性 评价
    private static void transposePropertyJudge( List<Word> wordList,String subjectWord){
        StringBuffer sourceSubjectWord = new StringBuffer(subjectWord);
        Map subjectWordIndexMap = null;
        StringBuffer subjectWordBuffer = new StringBuffer();
        Map<Integer,Word> subRelateMap = new HashMap<Integer ,Word>();
        Map<Integer,List> wordParentMap = new HashMap<Integer ,List>();
        for (int i = 0; i < wordList.size(); i++) {
            Word word = wordList.get(i);
            //将word放到map中方便后续操作，不再迭代。
            Integer partentInteger = Integer.parseInt(word.getParent());
            if(wordParentMap.get(partentInteger)==null){
                List <Word> list = new ArrayList<Word>();
                list.add(word);
                wordParentMap.put(partentInteger,list);
            }else{
                List <Word> list = wordParentMap.get(partentInteger);
                list.add(word);
                wordParentMap.put(partentInteger,list);
            }

            //－－－－寻获主题词
            //是否整词恰好匹配
            boolean isEquals = subjectWord.equals(word.getCont());
            //是否拆分匹配
            boolean isStartWith = subjectWord.startsWith(word.getCont());
            //将元素的index位置放到hashset中 备用

            if (isStartWith && !subjectWordBuffer.equals(sourceSubjectWord)) {
                subjectWordIndexMap = new HashMap();
                int j = i;
                while (!(subjectWordBuffer.equals(sourceSubjectWord))){
                    String subjectWordTmpl =subjectWordBuffer.toString();
                    if (subjectWord.equals(subjectWordTmpl)){
                        break;
                    }

                    //将开头拼接上去。
                    Word subjectWordTmp = wordList.get(j);
                    //记录所在位置
                    subjectWordIndexMap.put(j,wordList.get(j));
                    subRelateMap.put(Integer.parseInt(subjectWordTmp.getParent()),subjectWordTmp);
                    subjectWordBuffer.append(subjectWordTmp.getCont());
                    j++;
                }
                //将循环移动到 主体词后。
            }

            if (isEquals){
                subjectWordIndexMap = new HashMap();
                Word subjectWordTmp = wordList.get(i);
                subjectWordIndexMap.put(i,wordList.get(i));
                subjectWordBuffer.append(subjectWordTmp.getCont());
                subRelateMap.put(Integer.parseInt(subjectWordTmp.getParent()),subjectWordTmp);
            }
        }

        //获取第一个SBV
        Set <Integer> set = subRelateMap.keySet();
        //第一个SBV结构的词 此词作为属性
        Word sbvWord = null;
        for (Integer integer : set) {
            //忽略主体词之间的关系。
            if (subjectWordIndexMap.get(integer) != null) {
                continue;
            }
            //
            Word word = subRelateMap.get(integer);
            String relateString = word.getRelate();
            //寻获第一个SBV
            if (relateString.equals("SBV")){
                sbvWord = subRelateMap.get(integer);
            }else{
                //如果不是“SBV”继续迭代
                while(!relateString.equals("SBV")){
                    word = wordList.get(Integer.parseInt(word.getParent()));
                    relateString =  word.getRelate();
                    if(relateString.equals("SBV")){
                        sbvWord = word;
                        break;
                    }
                }
            }
            if (sbvWord!=null){
                break;
            }
        }
        //实际位属性词 或者 主体词，通过 subjectIndexMap确定。
        sbvWord.getId();
        //获取SBV关系的指向词。
        Word sbvPointWord = wordList.get(Integer.parseInt(sbvWord.getParent()));
        //获取SBV的指向
        //将最后一个
        List<Word> sbvParentList = null;
        if (sbvPointWord.getRelate().equals("COO")){
            //指向最基础的SBV
            sbvParentList =  wordParentMap.get(Integer.parseInt(sbvPointWord.getParent()));
        }else{
            //本身就是最基础的SBV
            sbvParentList = wordParentMap.get(sbvPointWord.getId());
        }
        List<Word> cooList = new ArrayList<Word>();
        for (Word word : sbvParentList) {
            if (word.getRelate().equals("COO")){
                cooList.add(word);
            }else{
                continue;
            }
        }
        //所有的SBV中的 评价词已经找到。
        cooList.add(sbvPointWord);
        List <SubjectJudge> List =  getJudge(wordParentMap,subjectWordIndexMap,cooList,subjectWordBuffer.toString());
        System.out.println(JSON.toJSONString(List));

    }

    /**
     *  组装对象属性评价
     * @param parentMap
     * @param subjectIndexMap
     * @param cooList
     * @param subjectWord
     * @return
     */
    private  static List <SubjectJudge> getJudge(Map<Integer,List>parentMap,Map<Integer,Word>subjectIndexMap,List<Word>cooList,String subjectWord){
        List <SubjectJudge> subjectJudgeList = new ArrayList<SubjectJudge>();
        for (Word word : cooList) {
            SubjectJudge subjectJudge = new SubjectJudge();
            subjectJudge.setSubjectWord(subjectWord);
            subjectJudge.setJudgeWord(word);
            //如果该词是主体词，跳过
            if(subjectIndexMap.get(word.getId())!=null){
                continue;
            }
            List<Word> parentWordList= parentMap.get(word.getId());
            for (Word parentWord : parentWordList) {

                if (!parentWord.getRelate().equals("SBV")){
                    //如果不是SBV跳过
                    continue;
                }
                if(subjectIndexMap.get(parentWord.getId())!=null){
                    continue;
                }
                subjectJudge.setPropertyWord(parentWord);
                subjectJudgeList.add(subjectJudge);
                break;
            }

        }
        return subjectJudgeList;
    }

    //        //句法分块。
//        Word wordPointHead = (Word)wordParentMap.get(-1).get(0);
//        List <Word> wordBlockList = wordParentMap.get(wordPointHead.getId());
//        List<Word> tempList = new ArrayList<Word>();
//        //将第一个语法块放进去。
//        tempList.add(wordPointHead);
//        for (Word word : wordBlockList) {
//            if (word.getRelate().equals("COO")){
//                tempList.add(word);
//            }else{
//                continue;
//            }
//        }
//        if (wordPointHead.getRelate().equals("COO")){
//            tempList.add(wordList.get(Integer.parseInt(wordPointHead.getParent())));
//        }
//        //只保留与HED节点相关联的几个开头。
//        wordBlockList = tempList;
//
//        for (Word blockWord : wordBlockList) {
//
//            //TODO 每个语句块一个对象属性评价
//            //获取词性
//            String blockPos = blockWord.getPos();
//            //形容词或者成语
//            if (blockPos.equals("a")||blockPos.equals("i")) {
//                //获取所有修饰词。
//                List <Word> currentPointWordList = wordParentMap.get(blockWord.getId());
//                //取到SBV 和ADV SBV为 属性或对象 ADV为权重词或否定词
//                //TODO 后续应当加入权重词和否定词判断
//                for (Word currentPointWord : currentPointWordList) {
//                    //词性
//                    currentPointWord.getPos();
//                    String currentRelate = currentPointWord.getRelate();
//                    if (currentRelate.equals("ADV")) {
//
//                    }
//                    if (currentRelate.equals("SBV")){
//
//                    }
//                    if(currentRelate.equals()){
//
//                    }
//                }
//            }
//
//        }
//
//
//        Set<Integer> relateSet = subRelateMap.keySet();
//        for (Integer integer : relateSet) {
//            if (subjectWordIndexMap.get(integer)!=null){
//                //如果主体词被拆分自我映射句法关系，跳过
//                continue;
//            }
//            //获取wordList中的对象
//           Word currentWord = wordList.get(integer);
//            if (subjectWordIndexMap.get(Integer.parseInt(currentWord.getParent())) != null || currentWord.getRelate().equals("WP")) {
//                //与主体词无修饰关系的词，或者 标点 抛弃掉
//                continue;
//            }
//            //  找到有修饰关系的词 根据此行
//
//            Word relateWord = wordList .get(Integer.parseInt(currentWord.getParent()));
//            //判断词性 名词，形容词，等。
//            String wordPos = relateWord.getPos();
//            String relate = currentWord.getRelate();
//            if(relate.equals("ATT")&&wordPos.startsWith("n")){ //判断属性
//             //获取属性
//                wordParentMap.get(Integer.parseInt(relateWord.getParent()));
//
//            }
//            while(Integer.parseInt(relateWord.getParent())!=-1){
//
//            }
//            Integer wordRelate = Integer.parseInt(relateWord.getRelate());
//
//        }
//        //递归算法。获取与指向词相关及子级相关的词Id
//        for (int i = 0; i < wordList.size(); i++) {
//            //逐词判断修饰关系
//            if (subjectWordIndexMap.get(i) !=null ){
//                continue;
//            }
//            //假如不在主体词范围内 判断是否修饰主体词
//            Word word = wordList.get(i);
//
//
//
//        }


    /**
     *
     * @param list
     * @param parentWordMap parent作为KEY的map存放list<code>Map&lt;Integer,Word&gt;</code>
     * @param subjectMap subjectMap主体词对应的序列号的map
     * @param startIndex 开始序号
     * @param objectPropertyEvaluate
     */
    /*private void computRelate(List<Word>list ,Map<Integer,List<Word>>parentWordMap,Map<Integer,Word>subjectMap,Integer startIndex,ObjectPropertyEvaluate objectPropertyEvaluate){

            List <Word> parentHEDAfterListWords = parentWordMap.get(startIndex);
            for (int i = 0; i < parentHEDAfterListWords.size(); i++) {
                Word afterHeadword = parentHEDAfterListWords.get(i);
                String relate = afterHeadword.getRelate();
                String wordPos = afterHeadword.getPos();
                if (relate.equals("ATT")){
                    //名次＝>属性?
                    if(afterHeadword.getPos().startsWith("n")){
                        objectPropertyEvaluate.setObject(afterHeadword.getCont());
                    }
                    //获取下一层次
                    computRelate(list, parentWordMap, subjectMap,startIndex,objectPropertyEvaluate);
                }
                if (relate.equals("RAD")){
                    if(afterHeadword.getPos().startsWith("n")|| afterHeadword.getPos().startsWith("v")){
                        StringBuffer a = new StringBuffer(list.get(Integer.parseInt(afterHeadword.getParent())).getCont()).append(afterHeadword.getCont());
                        //objectPropertyEvaluate.setObject(a.toString()); 无法确定是否是 属性次
                    }

                }
                if (relate.equals("LAD")){
                    if(afterHeadword.getPos().startsWith("n")|| afterHeadword.getPos().startsWith("v")){
                        StringBuffer a = new StringBuffer(afterHeadword.getCont()).append(list.get(Integer.parseInt(afterHeadword.getParent())).getCont());
                        objectPropertyEvaluate.setObject(a.toString());
                    }

                }
                if (relate.equals("VOB")){
                    //动宾

                }
                //TODO 权重次或者否定词
                if (relate.equals("ADV")){

                }
                if (relate.equals("SBV")){
                    //名次＝>属性?
                    afterHeadword.getPos().startsWith("n");
                    //获取下一层次
//                    computRelate(list,parentWordMap,subjectMap,);
                }
                if (relate.equals("COO")){
                    wordPos.equals("a");
                    //同级并列
                    computRelate(list,parentWordMap,subjectMap,startIndex);
                }

            }
            //纪录ID


        }*/
}

class  SubjectJudge{
    public String getSubjectWord() {
        return subjectWord;
    }

    public void setSubjectWord(String subjectWord) {
        this.subjectWord = subjectWord;
    }

    public Word getPropertyWord() {
        return propertyWord;
    }

    public void setPropertyWord(Word propertyWord) {
        this.propertyWord = propertyWord;
    }

    public Word getJudgeWord() {
        return judgeWord;
    }

    public void setJudgeWord(Word judgeWord) {
        this.judgeWord = judgeWord;
    }

    private String subjectWord;
    private Word propertyWord;
    private Word judgeWord;
}