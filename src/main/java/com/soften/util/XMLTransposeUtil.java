package com.soften.util;

import com.soften.ltp.TransposeJson.*;
import org.jdom.DataConversionException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mysgq1 on 15/3/4.
 */
public class XMLTransposeUtil {

    public static Xml4nlp transposeXMLString (String xmlString) throws JDOMException, IOException {
        /** *创建一个新的字符串*** */
        StringReader xmlReader = new StringReader(xmlString);
        /** **创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入 */
        InputSource xmlSource = new InputSource(xmlReader);
        /** *创建一个SAXBuilder* */
        SAXBuilder builder = new SAXBuilder();
        /** *通过输入源SAX构造一个Document** */
        Document doc = builder.build(xmlSource);
        /** *获得根节点** */
        Element xml4nlpElement = doc.getRootElement();
        /** *获得note节点** */
        Element noteElement = xml4nlpElement.getChild("note");
        Note note = getNoteFromElement(noteElement);
        /**获取doc节点*** */
        Element docElement = xml4nlpElement.getChild("doc");
        Doc docObj = getDocFromElement(docElement);
        Xml4nlp xml4nlp = new Xml4nlp();
        xml4nlp.setNote(note);
        xml4nlp.setDoc(docObj);
        return xml4nlp;
    }
    private static  Note getNoteFromElement(Element element){
        Note note = new Note();
        note.setParser(element.getAttributeValue("parser"));
        note.setNe(element.getAttributeValue("ne"));
        note.setPos(element.getAttributeValue("pos"));
        note.setWsd(element.getAttributeValue("wsd"));
        note.setWord(element.getAttributeValue("word"));
        note.setSrl(element.getAttributeValue("srl"));
        note.setSent(element.getAttributeValue("sent"));
        return note;
    }
    private static Doc getDocFromElement(Element element) throws DataConversionException {
        Doc doc = new Doc();
        Element paraElement = element.getChild("para");
        doc.setPara(getParaFromElement(paraElement));
        return doc;
    }

    private static Para getParaFromElement(Element element) throws DataConversionException {
        Para para = new Para();
        para.setId(element.getAttribute("id").getIntValue());
        List <Element> children = element.getChildren("sent");
        List<Sent> sentList = new ArrayList<Sent>(children.size());
        for (Element child : children) {
            sentList.add(getSentFromElement(child));
        }
        para.setSent(sentList);
        return para;
    }
    private static Sent getSentFromElement(Element element) throws DataConversionException {
        Sent sent = new Sent();
        sent .setCont(element.getAttributeValue("cont"));
        sent .setId(element.getAttribute("id").getIntValue());
        List <Element> children = element.getChildren("word");
        List<Word> wordList = new ArrayList<Word>(children.size());
        for (Element child : children) {
            wordList.add(getWordFromElement(child));
        }
        sent.setWord(wordList);
        return sent;
    }
    private static Word getWordFromElement (Element element) throws DataConversionException {
        Word word = new Word();
        word.setId(element.getAttribute("id").getIntValue());
        word.setCont(element.getAttributeValue("cont"));
        word.setPos(element.getAttributeValue("pos"));
        word.setParent(element.getAttributeValue("parent"));
        word.setNe(element.getAttributeValue("ne"));
        word.setRelate(element.getAttributeValue("relate"));
        List <Element> children = element.getChildren("arg");
        List argList = new ArrayList(children.size());
        for (Element child : children) {
            argList.add(getArgFromElement(child));
        }
        word.setArg(argList);
        return  word;
    }
    private static  Arg getArgFromElement(Element element) throws DataConversionException {
        Arg arg = new Arg();
        arg.setId(element.getAttribute("id").getIntValue());
        arg.setType(element.getAttributeValue("type"));
        arg.setBeg(element.getAttributeValue("beg"));
        arg.setEnd(element.getAttributeValue("end"));
        return arg;
    }
    }
