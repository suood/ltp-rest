package com.soften.ltp.judger;

import org.apache.commons.collections.map.HashedMap;

import java.util.Map;

/**
 * 标注句法关系 和词的语法角色
 * Created by mysgq1 on 15/3/13.
 */
public class WordMarker {
    /**
     *句法关系
     */
    public static Map relationship_Map ;
    static {
        relationship_Map = new HashedMap();
        relationship_Map.put("SBV", "主谓关系");//	SBV 主谓关系	subject-verb	我送她一束花 (我 <-- 送)
        relationship_Map.put("VOB", "动宾关系");//	VOB	直接宾语，verb-object	我送她一束花 (送 --> 花)
        relationship_Map.put("IOB", "间宾关系");//	IOB	间接宾语，indirect-object	我送她一束花 (送 --> 她)
        relationship_Map.put("FOB", "前置宾语");//	FOB	前置宾语，fronting-object	他什么书都读(书 < -- 读)
        relationship_Map.put("DBL", "兼语");//	DBL	兼语 double	他请我吃饭 (请 --> 我)
        relationship_Map.put("ATT", "定中关系");	//ATT 定中关系	attribute 红苹果 (红 < -- 苹果)
        relationship_Map.put("ADV", "状中结构");//	ADV 状中结构	adverbial	非常美丽(非常 < -- 美丽)
        relationship_Map.put("CMP", "动补结构");	//CMP 动补结构	complement	做完了作业(做--> 完)
        relationship_Map.put("COO", "并列关系");	//COO并列关系	coordinate	大山和大海(大山-- > 大海)
        relationship_Map.put("POB", "介宾关系");//	POB 介宾关系	preposition-object	在贸易区内(在--> 内)
        relationship_Map.put("LAD", "左附加关系");//	LAD	左附加关系 left adjunct 大山和大海(和 < -- 大海)
        relationship_Map.put("RAD", "右附加关系");//	RAD 右附加关系	right adjunct 孩子们(孩子--> 们)
        relationship_Map.put("IS", "独立结构");//	IS 独立结构	independent structure	两个单句在结构上彼此独立
        relationship_Map.put("HED", "核心关系");//	HED	 核心关系 head	指整个句子的核心
    }


    /**
     *语法角色
     */
    public static Map ROLE_MAP ;
    static{
        ROLE_MAP = new HashedMap();
        ROLE_MAP.put("ADV", "附加的，默认标记");//	adverbial, default tag ( 附加的，默认标记 )
        ROLE_MAP.put("BNE", "受益人");//	beneﬁciary(受益人)
        ROLE_MAP.put("CND", "条件");	//condition(条件)
        ROLE_MAP.put("DIR", "方向");	//direction(方向)
        ROLE_MAP.put("DGR", "程度");	//degree(程度)
        ROLE_MAP.put("EXT","扩展");	//extent(扩展)
        ROLE_MAP.put("FRQ", "频率");	//frequency(频率)
        ROLE_MAP.put("LOC", "地点");	//locative(地点)
        ROLE_MAP.put("MNR", "方式");	//manner(方式)
        ROLE_MAP.put("PRP", "目的或原因");//purpose or reason(目的或原因)
        ROLE_MAP.put("TMP", "时间");//temporal(时间 )
        ROLE_MAP.put("TPC", "主题");//topic(主题 )
        ROLE_MAP.put("CRD", "并列参数");//	coordinated arguments(并列参数 )
        ROLE_MAP.put("PRD",	"谓语动词");//predicate(谓语动词 )
        ROLE_MAP.put("PSR",	"持有者");//possessor(持有者 )
        ROLE_MAP.put("PSE",	"被持有");//possessee(被持有 )
    }

}
