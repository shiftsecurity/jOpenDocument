package org.jopendocument.dom.template;

import org.apache.commons.io.FileUtils;
import org.jdom.Element;
import org.jopendocument.dom.ODPackageEntry;
import org.jopendocument.dom.ODSingleXMLDocument;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.jopendocument.dom.template.engine.ChartSheet;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class JavaScriptTemplateChartTest {
    private static final String ODT_CHART = "/template-chart.odt";
    private static final String ODS_CHART = "/chart.ods";
    private static final String ODS_CHART_SHEET = "chart";

    @Test
    public void testSetFieldChart() throws Exception {
        JavaScriptTemplate template = new JavaScriptTemplate(getClass().getResourceAsStream(ODT_CHART));

        File tmpFile = File.createTempFile("jOpenDocument", "chart");
        FileUtils.copyToFile(getClass().getResourceAsStream(ODS_CHART), tmpFile);
        SpreadSheet spreadSheet = SpreadSheet.createFromFile(tmpFile);

        ChartSheet chartSheet = new ChartSheet(spreadSheet, spreadSheet.getSheet(ODS_CHART_SHEET));

        template.setField("CHART_PLACEHOLDER", chartSheet);
        ODSingleXMLDocument doc = template.createDocument();
        Element elem = (Element) doc.getDocument().getContent().get(0);
        Object frameElem = doc.getXPath(String.format("//draw:frame[@draw:name='%s']", chartSheet.getNewObjectId())).selectSingleNode(elem);
        assertNotNull(frameElem);
        Object objectElem = doc.getXPath(String.format("//draw:object[@xlink:href='%s']", "./"+chartSheet.getNewObjectId())).selectSingleNode(frameElem);
        assertNotNull(objectElem);
        ODPackageEntry entry1 = doc.getPackage().getEntry(String.format("%s/content.xml", chartSheet.getNewObjectId()));
        assertNotNull(entry1);
        ODPackageEntry entry2 = doc.getPackage().getEntry(String.format("ObjectReplacements/%s", chartSheet.getNewObjectId()));
        assertNotNull(entry2);
        //doc.saveToPackageAs(new File("debug-chart.odt"));
    }
}
