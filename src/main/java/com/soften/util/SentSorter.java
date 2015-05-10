package com.soften.util;

import com.soften.ltp.TransposeJson.Sent;
import com.soften.ltp.TransposeJson.Word;
import org.apache.commons.beanutils.BeanComparator;
//import org.apache.commons.collections.ComparatorUtils;
//import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.collections.comparators.ComparatorChain;

import java.util.ArrayList;
import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

/**
 * 将每个句子的词按照id排序
 * Created by mysgq1 on 15/3/5.
 */

public  class SentSorter {
    public static List<Word> getSortedWordList(Sent sent){
        List <Word> wordList = sent.getWord();
//        Comparator mycmp = ComparableComparator.getInstance();
//        mycmp = ComparatorUtils.nullLowComparator(mycmp);  //允许null
//        mycmp = ComparatorUtils.reversedComparator(mycmp); //逆序
        //声明要排序的对象的属性，并指明所使用的排序规则，如果不指明，则用默认排序
        ArrayList<Object> sortFields = new ArrayList<Object>(1);
//      sortFields.add(new BeanComparator("id", mycmp)); //id逆序  (主)
        sortFields.add(new BeanComparator("id"));      //name正序 (副)
        //创建一个排序链
        ComparatorChain multiSort = new ComparatorChain(sortFields);
        //开始真正的排序，按照先主，后副的规则
        Collections.sort(wordList, multiSort);
        return wordList;
    }
}
