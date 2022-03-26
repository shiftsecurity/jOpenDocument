package org.jopendocument.dom.template.engine;

import org.apache.commons.lang3.RandomStringUtils;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jopendocument.dom.ODPackage;
import org.jopendocument.dom.XMLVersion;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.jopendocument.util.ODPackageUtils;

import java.io.IOException;

/**
 * Spreadsheet.sheet, contains Chart object.
 *
 * Use this class to replace placeholder by spreadsheet chart object.
 */
public class ChartSheet {
    private SpreadSheet spreadSheet;
    private Sheet sheet;
    private String newObjectId;

    public ChartSheet(SpreadSheet spreadSheet, Sheet sheet) {
        this.spreadSheet = spreadSheet;
        this.sheet = sheet;
        this.newObjectId = "Object " + RandomStringUtils.randomAlphabetic(10);
    }

    public SpreadSheet getSpreadSheet() {
        return spreadSheet;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public String getSheetName() {
        return getSheet().getName();
    }

    public String getNewObjectId() {
        return newObjectId;
    }

    private Element getDrawObject() throws JDOMException {
        String xQuery = "//table:table[@table:name=\"" + getSheetName() + "\"]//table:shapes/draw:frame/draw:object[1]";
        Element elem = (Element) spreadSheet.getXPath(xQuery).selectSingleNode(spreadSheet.getContentDocument());
        return elem;
    }

    private String getDrawObjectHref() throws JDOMException {
        Element elem = getDrawObject();
        return elem.getAttributeValue("href", spreadSheet.getVersion().getNS("xlink"));
    }

    private Element getDrawFrame() throws JDOMException {
        String xQuery = "//table:table[@table:name=\"" + getSheetName() + "\"]//table:shapes/draw:frame[1]";
        Element elem = (Element) spreadSheet.getXPath(xQuery).selectSingleNode(spreadSheet.getContentDocument());
        return elem;
    }

    public String getDrawFrameWidth() throws JDOMException {
        return getDrawFrame().getAttributeValue("width", spreadSheet.getVersion().getNS("svg"));
    }
    public String getDrawFrameHeight() throws JDOMException {
        return getDrawFrame().getAttributeValue("height", spreadSheet.getVersion().getNS("svg"));
    }

    public void copyChartObject(ODPackage pkg) throws JDOMException, IOException {
        String pathObj = "./" + newObjectId;
        ODPackageUtils.copyPackageDir(
                spreadSheet.getPackage(),
                pkg,
                getDrawObjectHref(),
                pathObj,
                "application/vnd.oasis.opendocument.chart"
                );
    }

    private Element getDrawObjectElement(ODPackage pkg) throws JDOMException {
        XMLVersion version = pkg.getVersion();
        Namespace nsDraw = version.getNS("draw");
        Namespace nsXLink = version.getNS("xlink");
        String href = "./" + newObjectId;

        Element elem = new Element("object", nsDraw);
        elem.setAttribute("href", href, nsXLink);
        elem.setAttribute("type", "simple", nsXLink);
        elem.setAttribute("show", "embed", nsXLink);
        elem.setAttribute("actuate", "onLoad", nsXLink);
        return elem;
    }

    public Element getDrawFrameElement(ODPackage pkg) throws JDOMException {
        XMLVersion version = pkg.getVersion();
        Namespace nsDraw = version.getNS("draw");
        Namespace nsText = version.getTEXT();
        Namespace nsSVG = version.getNS("svg");
        Element elem = new Element("frame", nsDraw);
        elem.setAttribute("name", newObjectId, nsDraw);
        elem.setAttribute("anchor-type", "as-char", nsText);
        elem.setAttribute("width", getDrawFrameWidth(), nsSVG);
        elem.setAttribute("height", getDrawFrameHeight(), nsSVG);
        elem.setAttribute("z-index", "20", nsDraw);
        elem.addContent(getDrawObjectElement(pkg));
        return elem;
    }
}
