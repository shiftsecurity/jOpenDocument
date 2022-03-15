package org.jopendocument.dom.template;

import org.jdom.Element;
import org.jopendocument.dom.ODSingleXMLDocument;
import org.jopendocument.dom.text.Span;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class JavaScriptTemplateTest {
    private static final String ODT_FIELD_SIMPLE = "/template-field-simple.odt";
    private static final String ODT_FIELD_AS_STR = "/template-field-str.odt";
    private static final String ODT_FIELD_ENCODE = "/template-field-enc.odt";
    private static final String ODT_FIELD_OOXML = "/template-field-ooxml.odt";
    private static final String FIELD_NAME = "fieldStr";

    private static final String ODT_TAG_PARAGRAPH = "/template-tag-paragraph.odt";
    private static final String ODT_TAG_SECTION = "/template-tag-section.odt";
    private static final String ODT_TAG_TABLE = "/template-tag-table.odt";
    private static final String ODT_TAG_LIST_ITEM = "/template-tag-list-item.odt";
    private static final String ODT_TAG_TABLE_ROW = "/template-tag-table-row.odt";

    private static final String ODT_REPEAT_PARAGRAPH = "/template-repeat-paragraph.odt";
    private static final String ODT_REPEAT_SECTION = "/template-repeat-section.odt";
    private static final String ODT_REPEAT_TABLE = "/template-repeat-table.odt";
    private static final String ODT_REPEAT_LIST_ITEM = "/template-repeat-list-item.odt";
    private static final String ODT_REPEAT_TABLE_ROW = "/template-repeat-table-row.odt";

    @Test
    public void testSetFieldSimple() throws Exception {
        JavaScriptTemplate template = new JavaScriptTemplate(getClass().getResourceAsStream(ODT_FIELD_SIMPLE));
        String TEST_STR = "test <test> test\ntest\ttest";
        template.setField(FIELD_NAME, TEST_STR);
        ODSingleXMLDocument doc = template.createDocument();
        Element elem = (Element) doc.getDocument().getContent().get(0);
        Object obj = doc.getXPath(String.format("//text:span[text()='%s']", TEST_STR)).selectSingleNode(elem);
        assertNotNull(obj);
    }
    @Test
    public void testSetFieldSimpleXML() throws Exception {
        JavaScriptTemplate template = new JavaScriptTemplate(getClass().getResourceAsStream(ODT_FIELD_SIMPLE));
        String TEST_STR = "test <test> test\ntest\ttest";
        Span TEST_VALUE = new Span(TEST_STR);
        template.setField(FIELD_NAME, TEST_VALUE.getElement());
        ODSingleXMLDocument doc = template.createDocument();
        Element elem = (Element) doc.getDocument().getContent().get(0);
        Object obj1 = doc.getXPath(String.format("//text:span[text()='%s']", TEST_STR)).selectSingleNode(elem);
        assertNull(obj1);
        Object obj2 = doc.getXPath("//text:span[text()='test <test> test']").selectSingleNode(elem);
        assertNotNull(obj2);
    }

    @Test
    public void testSetFieldAsStr() throws Exception {
        JavaScriptTemplate template = new JavaScriptTemplate(getClass().getResourceAsStream(ODT_FIELD_AS_STR));
        String TEST_STR = "test <test> test\ntest\ttest";
        template.setField(FIELD_NAME, TEST_STR);
        ODSingleXMLDocument doc = template.createDocument();
        Element elem = (Element) doc.getDocument().getContent().get(0);
        Object obj = doc.getXPath(String.format("//text:span[text()='%s']", TEST_STR)).selectSingleNode(elem);
        assertNotNull(obj);
    }

    @Test
    public void testSetFieldAsStrXML() throws Exception {
        JavaScriptTemplate template = new JavaScriptTemplate(getClass().getResourceAsStream(ODT_FIELD_AS_STR));
        String TEST_STR = "test <test> test\ntest\ttest";
        Span TEST_VALUE = new Span(TEST_STR);
        template.setField(FIELD_NAME, TEST_VALUE.getElement());
        ODSingleXMLDocument doc = template.createDocument();
        Element elem = (Element) doc.getDocument().getContent().get(0);
        String elemText = "<text:span xmlns:text=\"urn:oasis:names:tc:opendocument:xmlns:text:1.0\">test &lt;test&gt; test<text:line-break />test<text:tab />test</text:span>";
        Object obj = doc.getXPath(String.format("//text:span[text()='%s']", elemText)).selectSingleNode(elem);
        assertNotNull(obj);
    }

    @Test
    public void testSetFieldEncode() throws Exception {
        JavaScriptTemplate template = new JavaScriptTemplate(getClass().getResourceAsStream(ODT_FIELD_ENCODE));
        String TEST_STR = "test <test> test\ntest\ttest";
        template.setField(FIELD_NAME, TEST_STR);
        ODSingleXMLDocument doc = template.createDocument();
        Element elem = (Element) doc.getDocument().getContent().get(0);
        Object obj1 = doc.getXPath(String.format("//text:span[text()='%s']", TEST_STR)).selectSingleNode(elem);
        assertNull(obj1);
        Object obj2 = doc.getXPath("//text:span[text()='test <test> test']").selectSingleNode(elem);
        assertNotNull(obj2);
    }

    @Test
    public void testSetFieldOOXML() throws Exception {
        JavaScriptTemplate template = new JavaScriptTemplate(getClass().getResourceAsStream(ODT_FIELD_OOXML));
        String TEST_STR = "test <text:span>hoge</text:span> test\ntest\ttest";
        template.setField(FIELD_NAME, TEST_STR);
        ODSingleXMLDocument doc = template.createDocument();
        Element elem = (Element) doc.getDocument().getContent().get(0);
        Object obj1 = doc.getXPath(String.format("//text:span[text()='%s']", TEST_STR)).selectSingleNode(elem);
        assertNull(obj1);
        Object obj2 = doc.getXPath("//text:span[text()='hoge']").selectSingleNode(elem);
        assertNotNull(obj2);
    }

    @Test
    public void testShowHideParagraph() throws Exception {
        JavaScriptTemplate template = new JavaScriptTemplate(getClass().getResourceAsStream(ODT_TAG_PARAGRAPH));
        template.showParagraph("pShow");
        template.hideParagraph("pHide");
        ODSingleXMLDocument doc = template.createDocument();
        Element elem = (Element) doc.getDocument().getContent().get(0);

        Object pShow = doc.getXPath("//text:p[text()='Paragraph to show']").selectSingleNode(elem);
        assertNotNull(pShow);
        Object pHide = doc.getXPath("//text:p[text()='Paragraph to hide']").selectSingleNode(elem);
        assertNull(pHide);
    }

    @Test
    public void testShowHideSection() throws Exception {
        JavaScriptTemplate template = new JavaScriptTemplate(getClass().getResourceAsStream(ODT_TAG_SECTION));
        template.showSection("secShow");
        template.hideSection("secHide");
        ODSingleXMLDocument doc = template.createDocument();
        Element elem = (Element) doc.getDocument().getContent().get(0);

        Object secShow = doc.getXPath("//text:p[text()='Section to show']").selectSingleNode(elem);
        assertNotNull(secShow);
        Object secHide = doc.getXPath("//text:p[text()='Section to hide']").selectSingleNode(elem);
        assertNull(secHide);
    }

    @Test
    public void testShowHideTable() throws Exception {
        JavaScriptTemplate template = new JavaScriptTemplate(getClass().getResourceAsStream(ODT_TAG_TABLE));
        template.showTable("tblShow");
        template.hideTable("tblHide");
        ODSingleXMLDocument doc = template.createDocument();
        Element elem = (Element) doc.getDocument().getContent().get(0);

        Object tblShow = doc.getXPath("//text:span[text()='Table to show']").selectSingleNode(elem);
        assertNotNull(tblShow);
        Object tblHide = doc.getXPath("//text:span[text()='Table to hide']").selectSingleNode(elem);
        assertNull(tblHide);
    }

    @Test
    public void testShowHideListItem() throws Exception {
        JavaScriptTemplate template = new JavaScriptTemplate(getClass().getResourceAsStream(ODT_TAG_LIST_ITEM));
        template.showListItem("itemShow");
        template.hideListItem("itemHide");
        ODSingleXMLDocument doc = template.createDocument();
        Element elem = (Element) doc.getDocument().getContent().get(0);

        Object listItemShow = doc.getXPath("//text:span[text()='List item to show']").selectSingleNode(elem);
        assertNotNull(listItemShow);
        Object listItemHide = doc.getXPath("//text:span[text()='List item to hide']").selectSingleNode(elem);
        assertNull(listItemHide);
    }

    @Test
    public void testShowHideTableRow() throws Exception {
        JavaScriptTemplate template = new JavaScriptTemplate(getClass().getResourceAsStream(ODT_TAG_TABLE_ROW));
        template.showTableRow("rowShow");
        template.hideTableRow("rowHide");
        ODSingleXMLDocument doc = template.createDocument();
        Element elem = (Element) doc.getDocument().getContent().get(0);

        Object tblRowShow = doc.getXPath("//text:span[text()='Row to show']").selectSingleNode(elem);
        assertNotNull(tblRowShow);
        Object tblRowHide = doc.getXPath("//text:span[text()='Row to hide']").selectSingleNode(elem);
        assertNull(tblRowHide);
    }

    @Test
    public void testRepeatParagraph() throws Exception {
        JavaScriptTemplate template = new JavaScriptTemplate(getClass().getResourceAsStream(ODT_REPEAT_PARAGRAPH));
        Map<String, String> item1 = new HashMap<>();
        item1.put("v1", "world");
        Map<String, String> item2 = new HashMap<>();
        item2.put("v1", "OpenDocument");
        List<Map<String, String>> items = new ArrayList();
        items.add(item1);
        items.add(item2);

        template.setField("items", items);
        ODSingleXMLDocument doc = template.createDocument();
        Element elem = (Element) doc.getDocument().getContent().get(0);
        Object obj1 = doc.getXPath("//text:span[text()='world']").selectSingleNode(elem);
        assertNotNull(obj1);
        Object obj2 = doc.getXPath("//text:span[text()='OpenDocument']").selectSingleNode(elem);
        assertNotNull(obj2);
    }

    @Test
    public void testRepeatSection() throws Exception {
        JavaScriptTemplate template = new JavaScriptTemplate(getClass().getResourceAsStream(ODT_REPEAT_SECTION));
        Map<String, String> item1 = new HashMap<>();
        item1.put("v1", "world");
        Map<String, String> item2 = new HashMap<>();
        item2.put("v1", "OpenDocument");
        List<Map<String, String>> items = new ArrayList();
        items.add(item1);
        items.add(item2);

        template.setField("items", items);
        ODSingleXMLDocument doc = template.createDocument();
        Element elem = (Element) doc.getDocument().getContent().get(0);
        Object obj1 = doc.getXPath("//text:span[text()='world']").selectSingleNode(elem);
        assertNotNull(obj1);
        Object obj2 = doc.getXPath("//text:span[text()='OpenDocument']").selectSingleNode(elem);
        assertNotNull(obj2);
    }

    @Test
    public void testRepeatTable() throws Exception {
        JavaScriptTemplate template = new JavaScriptTemplate(getClass().getResourceAsStream(ODT_REPEAT_TABLE));
        Map<String, String> item1 = new HashMap<>();
        item1.put("v1", "world");
        Map<String, String> item2 = new HashMap<>();
        item2.put("v1", "OpenDocument");
        List<Map<String, String>> items = new ArrayList();
        items.add(item1);
        items.add(item2);

        template.setField("items", items);
        ODSingleXMLDocument doc = template.createDocument();
        Element elem = (Element) doc.getDocument().getContent().get(0);
        Object obj1 = doc.getXPath("//text:span[text()='world']").selectSingleNode(elem);
        assertNotNull(obj1);
        Object obj2 = doc.getXPath("//text:span[text()='OpenDocument']").selectSingleNode(elem);
        assertNotNull(obj2);
    }

    @Test
    public void testRepeatListItem() throws Exception {
        JavaScriptTemplate template = new JavaScriptTemplate(getClass().getResourceAsStream(ODT_REPEAT_LIST_ITEM));
        Map<String, String> item1 = new HashMap<>();
        item1.put("v1", "world");
        Map<String, String> item2 = new HashMap<>();
        item2.put("v1", "OpenDocument");
        List<Map<String, String>> items = new ArrayList();
        items.add(item1);
        items.add(item2);

        template.setField("items", items);
        ODSingleXMLDocument doc = template.createDocument();
        Element elem = (Element) doc.getDocument().getContent().get(0);
        Object obj1 = doc.getXPath("//text:span[text()='world']").selectSingleNode(elem);
        assertNotNull(obj1);
        Object obj2 = doc.getXPath("//text:span[text()='OpenDocument']").selectSingleNode(elem);
        assertNotNull(obj2);
    }

    @Test
    public void testRepeatTableRow() throws Exception {
        JavaScriptTemplate template = new JavaScriptTemplate(getClass().getResourceAsStream(ODT_REPEAT_TABLE_ROW));
        Map<String, String> item1 = new HashMap<>();
        item1.put("v1", "world");
        Map<String, String> item2 = new HashMap<>();
        item2.put("v1", "OpenDocument");
        List<Map<String, String>> items = new ArrayList();
        items.add(item1);
        items.add(item2);

        template.setField("items", items);
        ODSingleXMLDocument doc = template.createDocument();
        Element elem = (Element) doc.getDocument().getContent().get(0);
        Object obj1 = doc.getXPath("//text:span[text()='world']").selectSingleNode(elem);
        assertNotNull(obj1);
        Object obj2 = doc.getXPath("//text:span[text()='OpenDocument']").selectSingleNode(elem);
        assertNotNull(obj2);
    }
}
