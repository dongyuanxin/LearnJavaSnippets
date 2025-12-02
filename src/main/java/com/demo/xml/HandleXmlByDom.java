package com.demo.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * 直接使用DOM来操作XML：创建DocumentBuilderFactory实例，创建DocumentBuilder实例，调用parse方法解析XML文件，获取根节点，遍历节点。
 * 优势：直接读取，遍历节点，代码易读
 * 缺点：一次性全部吃到内存里，占用内存较大
 */
public class HandleXmlByDom {
    public static void main(String[] args) {

        try (InputStream input = HandleXmlByDom.class.getResourceAsStream("/book.xml")) {
            if (input == null) {
                System.out.println("File not found.");
                return;
            }
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(input);
            printNode(doc, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void printNode(Node n, int indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print(' ');
        }
        switch (n.getNodeType()) {
            case Node.DOCUMENT_NODE:
                System.out.println("Document: " + n.getNodeName());
                break;
            case Node.ELEMENT_NODE:
                System.out.println("Element: " + n.getNodeName());
                break;
            case Node.TEXT_NODE:
                System.out.println("Text: " + n.getNodeName() + " = " + n.getNodeValue());
                break;
            case Node.ATTRIBUTE_NODE:
                System.out.println("Attr: " + n.getNodeName() + " = " + n.getNodeValue());
                break;
            case Node.CDATA_SECTION_NODE:
                System.out.println("CDATA: " + n.getNodeName() + " = " + n.getNodeValue());
                break;
            case Node.COMMENT_NODE:
                System.out.println("Comment: " + n.getNodeName() + " = " + n.getNodeValue());
                break;
            default:
                System.out.println("NodeType: " + n.getNodeType() + ", NodeName: " + n.getNodeName());
        }
        for (Node child = n.getFirstChild(); child != null; child = child.getNextSibling()) {
            printNode(child, indent + 1);
        }
    }
}
