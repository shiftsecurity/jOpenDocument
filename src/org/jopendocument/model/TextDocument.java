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

import org.jopendocument.util.ImageUtils;
import org.jopendocument.util.StreamUtils;

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

import org.jopendocument.io.SaxContentUnmarshaller;
import org.jopendocument.io.SaxStylesUnmarshaller;
import org.jopendocument.io.svm.SVMReader;
import org.jopendocument.model.office.OfficeAutomaticStyles;
import org.jopendocument.model.office.OfficeBody;
import org.jopendocument.model.office.OfficeMasterStyles;
import org.jopendocument.model.office.OfficeStyles;
import org.jopendocument.model.office.OfficeText;
import org.jopendocument.model.style.StyleDefaultStyle;
import org.jopendocument.model.style.StyleStyle;
import org.jopendocument.model.text.TextH;
import org.jopendocument.renderer.text.HeaderRenderBlock;
import org.jopendocument.renderer.text.RenderBlock;
import org.jopendocument.renderer.text.TextPage;
import org.jopendocument.util.Log;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class TextDocument extends OpenDocument {

    private static final int MAX_IN_MEMORY_HQ_IMAGE = 10;

    private OfficeAutomaticStyles autoStyles;

    private OfficeBody body;
    private final Map<String, Image> images = new HashMap<String, Image>();
    private final Map<String, Image> imagesHQ = new HashMap<String, Image>();

    private OfficeMasterStyles masterStyles;

    private List<TextPage> pages;

    private OfficeStyles styles;

    private ZipFile zipFile;

    // split

    /**
     * Creates an empty document You may use a loadFrom method on it
     */
    public TextDocument() {

    }

    /**
     * Creates a document from a file
     * 
     * @param f a spreadsheet file (in Open Document format)
     */
    public TextDocument(final File f) {
        this.loadFrom(f);

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

    private void computePages() {
        this.pages = new ArrayList<TextPage>();
        TextPage p = new TextPage();
        this.pages.add(p);
        OfficeText text = this.getBody().getText();
        System.err.println("--- Dump ----- " + text.getElementCount() + " elements");
        int stop = text.getElementCount();
        //
        int availableHeight = 0;
        int availableWidth = 0;
        int currentX = 0;
        int currentY = 0;

        for (int i = 0; i < stop; i++) {
            final Object element = text.getElementAt(i);
            System.err.println(i + ":" + element);
            if (element instanceof TextH) {
                RenderBlock b = new HeaderRenderBlock((TextH) element, availableWidth, availableHeight);
                b.setX(currentX);
                b.setY(currentY);
                p.add(b);
                availableHeight -= b.getHeight();
                currentY += b.getHeight();
            }

        }

    }

    public int getPrintedPageCount() {
        return this.pages.size();
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

    public void init(final OfficeBody aBody, final OfficeAutomaticStyles bodyAutoStyles, final OfficeStyles s, final OfficeAutomaticStyles auto, final OfficeMasterStyles masters) {
        if (aBody == null) {
            throw new IllegalArgumentException("OfficeBody cannot be null");
        }
        if (bodyAutoStyles == null) {
            throw new IllegalArgumentException("OfficeAutomaticStyles cannot be null");
        }
        if (s == null) {
            throw new IllegalArgumentException("OfficeStyles cannot be null");
        }
        if (auto == null) {
            throw new IllegalArgumentException("OfficeAutomaticStyles cannot be null");
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
        final SaxContentUnmarshaller contentHandler = new SaxContentUnmarshaller(this);
        final SaxStylesUnmarshaller stylesHandler = new SaxStylesUnmarshaller(this);

        try {
            this.zipFile = new ZipFile(f);
            final XMLReader rdr = XMLReaderFactory.createXMLReader();

            // Style parsing
            rdr.setContentHandler(stylesHandler);
            final ZipEntry stylesEntry = this.zipFile.getEntry("styles.xml");
            if (stylesEntry != null) {
                final InputSource styleSource = new InputSource(new InputStreamReader(this.zipFile.getInputStream(stylesEntry), "UTF-8"));
                rdr.parse(styleSource);
            }

            // Content parsing
            rdr.setContentHandler(contentHandler);
            final ZipEntry contentEntry = this.zipFile.getEntry("content.xml");
            final InputSource contentSource = new InputSource(new InputStreamReader(this.zipFile.getInputStream(contentEntry), "UTF-8"));
            rdr.parse(contentSource);

        } catch (final Exception e1) {
            e1.printStackTrace();
        }
        this.init(contentHandler.getBody(), contentHandler.getAutomaticstyles(), stylesHandler.getStyles(), stylesHandler.getAutomaticStyles(), stylesHandler.getMasterStyles());

    }

    public void loadFrom(final String fileName) {
        this.loadFrom(new File(fileName));
    }

    public void loadFrom(final URL url) throws IOException {
        if (!url.getPath().toLowerCase().endsWith(".odt")) {
            throw new IllegalArgumentException("This class is only for ODT files");
        }
        final File file = File.createTempFile("jOpenDocument", ".odt");
        file.deleteOnExit();
        final FileOutputStream fileOutputStream = new FileOutputStream(file);
        StreamUtils.copy(url.openStream(), fileOutputStream);
        fileOutputStream.close();
        this.loadFrom(file);
    }

    public void preloadImage(final String xlinkHref) {
        try {
            this.getImage(xlinkHref);
        } catch (Exception e) {
            Log.get().severe("Unable to preload image " + xlinkHref);
            e.printStackTrace();
        }
    }

    public void setAutomaticStyles(final OfficeAutomaticStyles autostyles) {
        this.autoStyles = autostyles;
    }

    public void setMasterStyles(final OfficeMasterStyles masterStyles) {
        this.masterStyles = masterStyles;
    }

    public void dumpPages() {
        OfficeText text = this.getBody().getText();
        if (text == null) {
            System.err.println("--- Dump ----- null Text in body");
            return;
        }
        System.err.println("--- Dump ----- " + text.getElementCount() + " elements");
        int stop = text.getElementCount();
        for (int i = 0; i < stop; i++) {
            System.err.println(i + ":" + text.getElementAt(i));

        }

    }
}
