package com.demo.xml;

import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class HandleXmlBySax {
    public static void main(String[] args) throws  Exception{
        InputStream input = HandleXmlBySax.class.getResourceAsStream("/book.xml");
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser saxParser = spf.newSAXParser();
        saxParser.parse(input, new HandleXmlBySaxHandler());
        input.close();
    }
}

/**
 * SAX解析器：
 * 1、相较于DOM解析器，SAX是基于事件驱动的，它把XML文档解析为事件，然后调用相应的处理方法处理这些事件
 * 2、SAX解析器是懒惰的Stream，它不会把整个XML文档加载到内存中，而是按需加载，所以占用内存并不多
 *
 * 编程技巧：
 * 1、事件处理器必须继承自 DefaultHandler，覆盖相应的方法，处理事件
 * 2、关注下startElement和endElement方法，可以获取深度（XML的嵌套关系）
 */
class HandleXmlBySaxHandler extends DefaultHandler {
    private int depth = 0; // 树形节点深度

    private void printIndent() {
        for (int i = 0; i < depth; i++) {
            System.out.print("  "); // 两个空格，代表一层缩进
        }
    }
    void print(Object... objs) {
        printIndent();
        for (Object obj : objs) {
            System.out.print(obj);
            System.out.print(" ");
        }
        System.out.println();
    }

    @Override
    public void startDocument() throws SAXException {
        print("start document");
    }

    @Override
    public void endDocument() throws SAXException {
        print("end document");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        print("start element:", localName, qName);
        depth++; // 进入子节点，深度+1
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        print("end element:", localName, qName);
        depth--; // 结束节点，深度-1
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        print("characters:", new String(ch, start, length));
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        print("error:", e);
    }
}
