/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 2008-2013 jOpenDocument, by ILM Informatique. All rights reserved.
 * 
 * The contents of this file are subject to the terms of the GNU
 * General Public License Version 3 only ("GPL").  
 * You may not use this file except in compliance with the License. 
 * You can obtain a copy of the License at http://www.gnu.org/licenses/gpl-3.0.html
 * See the License for the specific language governing permissions and limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in each file.
 * 
 */

package org.jopendocument.model;

import org.jopendocument.util.FileUtils;
import org.jopendocument.util.ImageUtils;
import org.jopendocument.util.StreamUtils;
import org.jopendocument.util.StringInputStream;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.ImageIcon;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jopendocument.io.SaxContentUnmarshaller;
import org.jopendocument.io.SaxStylesUnmarshaller;
import org.jopendocument.io.svm.SVMReader;
import org.jopendocument.model.office.OfficeAutomaticStyles;
import org.jopendocument.model.office.OfficeBody;
import org.jopendocument.model.office.OfficeMasterStyles;
import org.jopendocument.model.office.OfficeSpreadsheet;
import org.jopendocument.model.office.OfficeStyles;
import org.jopendocument.model.style.StyleDefaultStyle;
import org.jopendocument.model.style.StylePageLayoutProperties;
import org.jopendocument.model.style.StyleStyle;
import org.jopendocument.model.table.TableTable;
import org.jopendocument.model.table.TableTableRow;
import org.jopendocument.util.Log;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class OpenDocument {

    private static final int MAX_IN_MEMORY_HQ_IMAGE = 10;
    private final StyleResolver styleResolver = new StyleResolver();
    private OfficeAutomaticStyles autoStyles;

    private OfficeBody body;
    private final Map<String, Image> images = new HashMap<String, Image>();
    private final Map<String, Image> imagesHQ = new HashMap<String, Image>();

    private OfficeMasterStyles masterStyles;

    private List<Page> pages;

    private OfficeStyles styles;

    private ZipFile zipFile;

    private Map<Integer, Integer> splitRowValues = new HashMap<Integer, Integer>(); // Sheet ->
    private String odsp;

    // split

    /**
     * Creates an empty document You may use a loadFrom method on it
     */
    public OpenDocument() {

    }

    /**
     * Creates a document from a file
     * 
     * @param f a spreadsheet file (in Open Document format)
     */
    public OpenDocument(final File f) {
        this.loadFrom(f);
        this.loadODSP();
    }

    /**
     * Creates a document from a file
     * 
     * @param f a spreadsheet file (in Open Document format)
     */
    public OpenDocument(final File f, String odspXML) {
        this.loadFrom(f);
        this.loadODSP(odspXML);
    }

    private void computePages() {
        this.pages = new ArrayList<Page>();
        final List<OfficeSpreadsheet> l = this.body.getOfficeSpreadsheets();

        int currentHeight = 0;
        for (final OfficeSpreadsheet sheet : l) {

            final List<TableTable> tables = sheet.getTables();
            for (final TableTable t : tables) {

                final StylePageLayoutProperties pageLayoutProperties = t.getPageLayoutProperties();
                int pageHeight = 0;
                if (pageLayoutProperties != null) {

                    pageHeight = pageLayoutProperties.getPageHeight();
                }
                final int printStartRow = t.getPrintStartRow();
                final int printStopRow = t.getPrintStopRow();
                final int printStartCol = t.getPrintStartCol();
                final int printStopCol = t.getPrintStopCol();
                System.out.println("PageHeigth:" + pageHeight);
                final List<TableTableRow> rows = t.getRowsInRange(printStartRow, printStopRow);

                final int splitRow = this.getSplitEveryRow(tables.indexOf(t));

                PrintedPage p = new PrintedPage();
                final int rowCount = rows.size();
                for (int j = 0; j < rowCount; j++) {
                    final TableTableRow row = rows.get(j);
                    p.addRow(row);

                    if (splitRow > 0) {
                        if (j > 0 && (j + 1) % (splitRow) == 0) {
                            this.pages.add(p);
                            p = new PrintedPage();
                        }
                    } else {
                        currentHeight += row.getHeight();

                        if (currentHeight > pageHeight) {

                            currentHeight = 0;
                            this.pages.add(p);

                            p = new PrintedPage();
                        }
                    }
                }
                if (!this.pages.contains(p) && !p.isEmpty()) {
                    this.pages.add(p);
                }

            }
        }

    }

    public OfficeAutomaticStyles getAutomaticStyles() {
        return this.autoStyles;
    }

    public OfficeBody getBody() {
        return this.body;
    }

    public Image getImage(final String xlinkHref) {

        final Image i = this.images.get(xlinkHref);
        if (i != null) {
            return i;
        }

        final ZipEntry entry = this.zipFile.getEntry(xlinkHref);

        byte[] bs = null;
        try {
            final InputStream stream = this.zipFile.getInputStream(entry);
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            final int BUFFER_SIZE = 4096;
            final byte[] buffer = new byte[BUFFER_SIZE];
            while (true) {
                final int count = stream.read(buffer, 0, BUFFER_SIZE);
                if (count == -1) {
                    break;
                }
                out.write(buffer, 0, count);
            }

            out.close();
            bs = out.toByteArray();

            stream.read(bs);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        Image image = null;

        if (entry.getName().toLowerCase().endsWith(".svm")) {
            try {
                SVMReader r = new SVMReader(new ByteArrayInputStream(bs));
                image = r.getImage();
            } catch (IOException e) {
                throw new IllegalStateException("Unable to load:'" + xlinkHref + "'", e);
            }

        } else {
            final ImageIcon ic = new ImageIcon(bs);
            image = ic.getImage();
            if (ic.getImageLoadStatus() != java.awt.MediaTracker.COMPLETE) {
                throw new IllegalStateException("Unable to load:'" + xlinkHref + "'");
            }
        }
        this.images.put(xlinkHref, image);

        return image;
    }

    public Image getImage(final String xlinkHref, final int width, final int height) {
        final String key = width + "," + height;
        Image im = this.imagesHQ.get(key);
        if (im != null) {
            return im;
        }

        im = ImageUtils.createQualityResizedImage(this.getImage(xlinkHref), width, height);
        // On fait un peu de ménage si trop de d'images
        if (this.imagesHQ.size() > MAX_IN_MEMORY_HQ_IMAGE) {
            this.imagesHQ.clear();
        }
        this.imagesHQ.put(key, im);
        return im;
    }

    public OfficeMasterStyles getMasterStyles() {
        return this.masterStyles;
    }

    public Page getPrintedPage(final int i) {

        if (this.pages == null) {
            this.computePages();
        }
        return this.pages.get(i);

    }

    public int getPrintedPageCount() {
        return this.pages.size();
    }

    private void loadODSP(String xml) {
        final SAXBuilder builder = new SAXBuilder();
        try {
            final Document document = builder.build(new StringInputStream(xml));
            parseODSPDocument(document);
            this.odsp = xml;
        } catch (final JDOMException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }

    }

    private void loadODSP() {
        final String path = this.getZipFile().getName() + "p";

        final File f = new File(path);
        if (!f.exists()) {
            System.err.println("ODSP Not Exist");
            return;
        }
        try {
            loadODSP(FileUtils.read(f));
        } catch (IOException e) {
            System.err.println("Error readin ODSP");

        }

    }

    private void parseODSPDocument(final Document document) {
        final Element root = document.getRootElement();
        final Element splitrow = root.getChild("spliteveryrow");
        if (splitrow != null) {
            final List<Element> l = splitrow.getChildren("sheet");
            for (final Element element : l) {
                final String s = element.getAttributeValue("number");

                if (s != null && s.trim().length() > 0) {                   
                    final String value = element.getValue();
                    if (value != null && value.trim().length() > 0) {
                        int row = Integer.valueOf(value);
                        final int n = Integer.valueOf(s);
                        splitRowValues.put(n, row);
                    }

                }
            }

        }
    }

    public int getSplitEveryRow(final int indexOfSheet) {
        final Integer value = this.splitRowValues.get(indexOfSheet);
        if (value != null) {
            return value;
        }
        return -1;
    }

    public OfficeStyles getStyles() {
        return this.styles;
    }

    public ZipFile getZipFile() {
        return this.zipFile;
    }

    public void importAutoStylesFrom(final OfficeAutomaticStyles stylesToAdd) {
        for (final StyleStyle s : stylesToAdd.getStyles()) {
            this.autoStyles.addStyle(s);
        }
    }

    public void init(final OfficeBody aBody, OfficeAutomaticStyles bodyAutoStyles, final OfficeStyles s, OfficeAutomaticStyles auto, final OfficeMasterStyles masters) {
        if (aBody == null) {
            throw new IllegalArgumentException("OfficeBody cannot be null");
        }
        if (bodyAutoStyles == null) {
            Log.get().info("bodyAutoStyles null");
            bodyAutoStyles = new OfficeAutomaticStyles();
        }
        if (s == null) {
            throw new IllegalArgumentException("OfficeStyles cannot be null");
        }
        if (auto == null) {
            Log.get().info("autoStyles null");
            auto = new OfficeAutomaticStyles();
        }
        if (masters == null) {
            throw new IllegalArgumentException("OfficeMasterStyles cannot be null");
        }

        this.body = aBody;
        this.body.setDocument(this);
        this.styles = s;
        this.autoStyles = auto;
        this.importAutoStylesFrom(bodyAutoStyles);

        StyleDefaultStyle mainDefaultCellStyle = s.getDefaultCellStyle();
        if (mainDefaultCellStyle == null) {
            mainDefaultCellStyle = new StyleDefaultStyle();
            mainDefaultCellStyle.setStyleFamily("table-cell");

        }
        final StyleStyle defaultCellStyle = new StyleStyle();
        defaultCellStyle.setStyleName("Default");
        defaultCellStyle.setTextProperties(mainDefaultCellStyle.getStyleTextProperties());
        defaultCellStyle.setStyleProperties(mainDefaultCellStyle.getStyleProperties());
        this.autoStyles.addStyle(defaultCellStyle);
        this.masterStyles = masters;

    }

    public void loadFrom(final File f) {
        if (!f.getName().toLowerCase().endsWith(".ods")) {
            throw new IllegalArgumentException("This class is only for ODS files");
        }
        final SaxContentUnmarshaller contentHandler = new SaxContentUnmarshaller(this);
        final SaxStylesUnmarshaller stylesHandler = new SaxStylesUnmarshaller(this);

        try {

            this.zipFile = new ZipFile(f);

            final XMLReader rdr = XMLReaderFactory.createXMLReader();
            rdr.setContentHandler(contentHandler);

            final ZipEntry contnetEntry = this.zipFile.getEntry("content.xml");
            final InputSource inputSource1 = new InputSource(new InputStreamReader(this.zipFile.getInputStream(contnetEntry), "UTF-8"));

            rdr.parse(inputSource1);

            rdr.setContentHandler(stylesHandler);

            final ZipEntry stylesEntry = this.zipFile.getEntry("styles.xml");
            if (stylesEntry != null) {
                final InputSource inputSource2 = new InputSource(new InputStreamReader(this.zipFile.getInputStream(stylesEntry), "UTF-8"));

                rdr.parse(inputSource2);
            }

        } catch (final Exception e1) {

            e1.printStackTrace();
        }
        this.init(contentHandler.getBody(), contentHandler.getAutomaticstyles(), stylesHandler.getStyles(), stylesHandler.getAutomaticStyles(), stylesHandler.getMasterStyles());
        try {
            zipFile.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void loadFrom(final String fileName) {
        this.loadFrom(new File(fileName));
    }

    public void loadFrom(final URL url) throws IOException {
        if (!url.getPath().toLowerCase().endsWith(".ods")) {
            throw new IllegalArgumentException("This class is only for ODS files");
        }
        final File file = File.createTempFile("jOpenDocument", ".ods");
        file.deleteOnExit();
        final FileOutputStream fileOutputStream = new FileOutputStream(file);
        StreamUtils.copy(url.openStream(), fileOutputStream);
        fileOutputStream.close();
        this.loadFrom(file);
    }

    public void preloadImage(final String xlinkHref) {
        this.getImage(xlinkHref);
    }

    public void setAutomaticStyles(final OfficeAutomaticStyles autostyles) {
        this.autoStyles = autostyles;
    }

    public void setMasterStyles(final OfficeMasterStyles masterStyles) {
        this.masterStyles = masterStyles;
    }

    public StyleResolver getStyleResolver() {
        return styleResolver;
    }

    public StyleStyle getStyle(String name, String family) {
        StyleStyle s = styleResolver.getStyle(name, family);
        if (s == null) {
            System.err.println("Unable to find style: " + name + " family:" + family);
            styleResolver.dump();
            throw new IllegalStateException("Unable to find a style");
        }
        return s;
    }

    public String getOdsp() {
        return odsp;
    }
}
