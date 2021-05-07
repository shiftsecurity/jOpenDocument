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

package org.jopendocument.renderer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jopendocument.model.OpenDocument;
import org.jopendocument.model.PrintedPage;
import org.jopendocument.model.draw.DrawFrame;
import org.jopendocument.model.draw.DrawImage;
import org.jopendocument.model.office.OfficeBody;
import org.jopendocument.model.office.OfficeSpreadsheet;
import org.jopendocument.model.style.StyleStyle;
import org.jopendocument.model.style.StyleTableCellProperties;
import org.jopendocument.model.table.TableShapes;
import org.jopendocument.model.table.TableTable;
import org.jopendocument.model.table.TableTableCell;
import org.jopendocument.model.table.TableTableColumn;
import org.jopendocument.model.table.TableTableRow;
import org.jopendocument.panel.ProgressListener;
import org.jopendocument.util.ValueHelper;

@SuppressWarnings("unqualified-field-access")
public class ODTRenderer extends JPanel {

    private static final long serialVersionUID = -4903349568929293597L;

    private double resizeFactor;

    private OfficeBody body;

    private TableTable table;

    private int printHeightPixel;

    private int printWidthPixel;

    private int printWidth;

    private int printHeight;

    private static final ODTCellBackgroundRenderer backgroundRenderer = new ODTCellBackgroundRenderer();

    private static final ODTCellBorderRenderer borderRenderer = new ODTCellBorderRenderer();

    private static final ODTCellTextRenderer textRenderer = new ODTCellTextRenderer();

    private static final ODTCellImageRenderer imageRenderer = new ODTCellImageRenderer();

    private static boolean debug = false;

    private PrintedPage currentPage;

    private boolean paintMaxResolution;

    private boolean ignoreMargins;

    private OpenDocument od;

    private int currentPageIndex;

    private List<ProgressListener> progressListeners = new ArrayList<ProgressListener>();
    long lastUpdate = 0;

    public ODTRenderer(OpenDocument doc) {
        setDocument(doc);

    }

    private synchronized void setDocument(OpenDocument doc) {
        this.body = doc.getBody();
        this.setBackground(Color.WHITE);
        this.currentPageIndex = 0;
        this.currentPage = (PrintedPage) doc.getPrintedPage(this.currentPageIndex);
        setResizeFactor(360);
        this.od = doc;
    }

    public ODTRenderer(final String fURL, final String odspXml) {
        this.setBackground(Color.WHITE);
        Thread t = new Thread() {
            public void run() {

                try {

                    URL url = new URL(fURL);
                    URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
                    url = uri.toURL();

                    HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
                    httpConn.setUseCaches(false);

                    int responseCode = httpConn.getResponseCode();

                    // always check HTTP response code first
                    if (responseCode == HttpURLConnection.HTTP_OK) {

                        int contentLength = httpConn.getContentLength();

                        // opens input stream from the HTTP connection

                        InputStream inputStream = httpConn.getInputStream();

                        // opens an output stream to save into file
                        File createTempFile = File.createTempFile("ocPreview", ".ods");
                        FileOutputStream outputStream = new FileOutputStream(createTempFile);

                        byte[] buffer = new byte[256];
                        int bytesRead = -1;
                        long totalBytesRead = 0;
                        int percentCompleted = 0;

                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                            totalBytesRead += bytesRead;
                            percentCompleted = (int) (totalBytesRead * 100 / contentLength);
                            if (percentCompleted < 100) {
                                // 100% only called when the document is created
                                setProgress(percentCompleted);
                            }
                        }

                        outputStream.close();
                        inputStream.close();
                        httpConn.disconnect();

                        OpenDocument d = new OpenDocument(createTempFile, odspXml);
                        setDocument(d);
                        setProgress(100);
                        repaint();
                    } else {
                        JOptionPane.showMessageDialog(ODTRenderer.this, "Unable to get " + fURL);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(ODTRenderer.this, "Unable to get " + fURL);
                    e.printStackTrace();
                }

            }
        };
        t.setName("ODTRenderer loading " + fURL);
        t.start();
    }

    protected void setProgress(final int percentCompleted) {

        long t = System.currentTimeMillis();
        if (t - lastUpdate > 100 || percentCompleted >= 100) {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    // Copy to allow removal in progressUpdate
                    final List<ProgressListener> cList = new ArrayList<ProgressListener>(progressListeners);
                    for (ProgressListener p : cList) {
                        p.progressUpdate(percentCompleted);
                    }

                }
            });
            lastUpdate = t;
        }

    }

    public void setCurrentPage(int i) {
        this.currentPageIndex = i;
        this.currentPage = (PrintedPage) od.getPrintedPage(i);

        updateSize();
        repaint();
    }

    private void updateSize() {
        this.setSize(getPageWidthInPixel(), getPageHeightInPixel());
    }

    public double getPageHeight() {
        return this.getPageHeightInPixel() * resizeFactor;
    }

    public int getPageHeightInPixel() {
        int h = 0;
        final TableTableRow[] rows = this.currentPage.getRows();
        final int rowCount = rows.length;
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            final TableTableRow row = rows[rowIndex];
            h += (int) (row.getHeight() / this.resizeFactor);

        }
        return h;
    }

    public double getPageWidth() {
        return this.getPageWidthInPixel() * resizeFactor;
    }

    public int getPageWidthInPixel() {
        final int printStartCol = table.getPrintStartCol();
        final int printStopCol = table.getPrintStopCol();
        final TableTableRow[] rows = this.currentPage.getRows();

        final double currentResizeFactor = this.resizeFactor;
        int marginLeft = table.getPageLayoutProperties().getMarginLeft();
        if (ignoreMargins) {
            marginLeft = 0;
        }

        int h = (int) (marginLeft / currentResizeFactor);
        final TableTableColumn[] columns = table.getColumns().toArray(new TableTableColumn[0]);

        for (int rowIndex = 0; rowIndex < 1; rowIndex++) {
            final TableTableRow row = rows[rowIndex];
            final TableTableCell[] cells = row.getCellsInRange(printStartCol, printStopCol);
            final int cellsSize = cells.length;

            // les cells repeated sont deja clonées
            for (int i = 0; i < cellsSize; i++) {
                final TableTableCell cell = cells[i];
                if (cell == null) {
                    continue;
                }
                final TableTableColumn col = columns[printStartCol + i];

                final StyleStyle style = cell.getStyle();
                h += (int) (col.getWidth() / this.resizeFactor);
            }
        }
        return h;
    }

    public int getPrintedPagesNumber() {
        return this.od.getPrintedPageCount();
    }

    public synchronized void setResizeFactor(double resizeFactor) {
        this.resizeFactor = resizeFactor;
        if (body != null) {
            final List<OfficeSpreadsheet> l = this.body.getOfficeSpreadsheets();
            final int spreadSheatCount = l.size();
            for (int i = 0; i < spreadSheatCount; i++) {
                final List<TableTable> tables = l.get(i).getTables();
                final int size = tables.size();
                for (int j = 0; j < size; j++) {
                    final TableTable t = tables.get(j);
                    this.table = t;
                    // FIXME here printWidth is the width of the whole sheet
                    printWidth = t.getPrintWidth() + t.getPageLayoutProperties().getMarginLeft() + t.getPageLayoutProperties().getMarginRight();
                    // FIXME here printHeight is the height of one page
                    printHeight = t.getPageLayoutProperties().getPageHeight();
                    if (ignoreMargins) {
                        // FIXME here of the whole sheet
                        printWidth = t.getPrintWidth();
                        printHeight = t.getPrintHeight();
                    }
                    printWidthPixel = (int) Math.ceil(printWidth / resizeFactor);
                    printHeightPixel = (int) Math.ceil(printHeight / resizeFactor);
                    setPreferredSize(new Dimension(printWidthPixel, printHeightPixel));
                    break;
                }
            }

            updateSize();
            repaint();
        }
    }

    public int getPrintWidthInPixel() {
        return printWidthPixel;
    }

    public int getPrintHeightInPixel() {
        return printHeightPixel;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        if (debug) {
            printRendererInformation();
            System.out.println("********************** RENDERING BACKGROUND *************");
        }
        drawODTBackground(g2);
        if (debug) {
            System.out.println("********************** RENDERING BORDERS *************");
        }
        drawODTBorders(g2);
        if (debug) {
            System.out.println("********************** RENDERING TEXTS *************");
        }
        drawODTText(g2);
        if (debug) {
            System.out.println("********************** RENDERING IMAGES *************");
        }
        drawODTImages(g2);

    }

    /**
     * @param g2
     */
    private final void drawODTImages(Graphics2D g2) {

        if (!paintMaxResolution) {

            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);

        } else {

            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        }
        // Images in Cells
        drawCells(g2, imageRenderer);

        // Image in Page
        final TableTable currentTable = this.table;
        TableShapes shapes = currentTable.getTableShapes();
        if (shapes == null) {
            return;
        }

        List<DrawFrame> frames = shapes.getDrawFrames();
        final double currentResizeFactor = this.resizeFactor;
        int borderLeft = (int) (currentTable.getPageLayoutProperties().getMarginLeft() / currentResizeFactor);
        int borderRight = (int) (currentTable.getPageLayoutProperties().getMarginRight() / currentResizeFactor);
        int borderTop = (int) (currentTable.getPageLayoutProperties().getMarginTop() / currentResizeFactor);
        int borderBottom = (int) (currentTable.getPageLayoutProperties().getMarginBottom() / currentResizeFactor);
        if (this.ignoreMargins) {
            borderLeft = 0;
            borderRight = 0;
            borderTop = 0;
            borderBottom = 0;
        }

        for (DrawFrame frame : frames) {
            DrawImage dIm = frame.getDrawImage();
            if (dIm != null) {
                final Rectangle frameRect = new Rectangle(ValueHelper.getLength(frame.getSvgX()), ValueHelper.getLength(frame.getSvgY()), ValueHelper.getLength(frame.getSvgWidth()),
                        ValueHelper.getLength(frame.getSvgHeight()));
                // FIXME doesn't work since printHeight is wrong (see setResizeFactor())
                // final int currentOrigin = this.currentPageIndex * this.printHeight;
                // final Rectangle pageRect = new Rectangle(0, currentOrigin, this.printWidth,
                // this.printHeight);
                // if (frameRect.intersects(pageRect)) {
                frameRect.x /= resizeFactor;
                frameRect.y /= resizeFactor;
                frameRect.width /= resizeFactor;
                frameRect.height /= resizeFactor;
                // after since already resized
                frameRect.translate(borderLeft, borderTop);

                Image im = null;
                if (!paintMaxResolution) {
                    im = body.getDocument().getImage(dIm.getXlinkHref(), frameRect.width, frameRect.height);
                    g2.drawImage(im, frameRect.x, frameRect.y, null);
                } else {
                    im = body.getDocument().getImage(dIm.getXlinkHref());
                    g2.drawImage(im, frameRect.x, frameRect.y, frameRect.width, frameRect.height, null);
                }
                // }
            }
        }
    }

    /**
     * @param g2
     */
    private final void drawODTText(Graphics2D g2) {
        // Texts
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        drawCells(g2, textRenderer);
    }

    /**
     * @param g2
     */
    private final void drawODTBorders(Graphics2D g2) {
        // Borders
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        drawCells(g2, borderRenderer);
    }

    /**
     * @param g2
     */
    private final void drawODTBackground(Graphics2D g2) {
        // Background
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        drawCells(g2, backgroundRenderer);
    }

    private final void drawCells(final Graphics2D g, final ODTCellRenderer renderer) {
        // Constants
        final TableTable currentTable = this.table;

        final int printStartCol = currentTable.getPrintStartCol();
        final int printStopCol = currentTable.getPrintStopCol();
        final TableTableRow[] rows = this.currentPage.getRows();
        final int rowCount = rows.length;
        final double currentResizeFactor = this.resizeFactor;
        int marginLeft = currentTable.getPageLayoutProperties().getMarginLeft();
        int marginTop = currentTable.getPageLayoutProperties().getMarginTop();
        if (ignoreMargins) {
            marginLeft = 0;
            marginTop = 0;
        }

        final int borderLeft = (int) (marginLeft / currentResizeFactor);
        final TableTableColumn[] columns = table.getColumns().toArray(new TableTableColumn[0]);
        final double[] columnsWidth = new double[columns.length];
        for (int i = 0; i < columns.length; i++) {
            columnsWidth[i] = columns[i].getWidth() / currentResizeFactor;

        }

        int y = (int) (marginTop / currentResizeFactor);

        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            final TableTableRow row = rows[rowIndex];
            final int rowHeight = (int) (row.getHeight() / currentResizeFactor);

            final TableTableCell[] cells = row.getCellsInRange(printStartCol, printStopCol);
            final int cellsSize = cells.length;

            int x = borderLeft;

            // les cells repeated sont deja clonées
            for (int i = 0; i < cellsSize; i++) {
                final TableTableCell cell = cells[i];
                if (cell == null) {
                    continue;
                }
                try {
                    final StyleStyle style = cell.getStyle();
                    int cellWidth = (int) columnsWidth[printStartCol + i];

                    final StyleTableCellProperties cellProps = style.getStyleTableCellProperties();
                    int cellHeight = rowHeight;
                    if (cell.getTableNumberRowsSpanned() > 1) {
                        final int max = rows.length - 1;
                        for (int repeat = 1; repeat < cell.getTableNumberRowsSpanned(); repeat++) {
                            int rIndex = Math.min(rowIndex + repeat, max);
                            cellHeight += rows[rIndex].getHeight() / currentResizeFactor;
                        }
                    }
                    if (cell.getTableNumberColumnsSpanned() > 1) {
                        for (int repeat = 1; repeat < cell.getTableNumberColumnsSpanned(); repeat++) {

                            final int colIndex = printStartCol + i + repeat;
                            final double w = columnsWidth[colIndex];
                            cellWidth += w;
                        }
                    }

                    if (!cell.isCovered()) {
                        renderer.draw(g, x, y, cellWidth, cellHeight, currentResizeFactor, cell, cellProps);
                    }
                } catch (Exception e) {
                    System.err.println("Failed on x:" + x + " y:" + y + " Cell:" + cell);
                    e.printStackTrace();
                }
                x += columnsWidth[printStartCol + i];

            }
            y += rowHeight;

        }
    }

    public double getPrintWidth() {
        return this.printWidth;
    }

    public double getPrintHeight() {
        return this.printHeight;
    }

    /**
     * Set the image rendering policy
     * 
     * @param b : true if you need an extra definition (ex for printing)
     */
    public void setPaintMaxResolution(boolean b) {
        this.paintMaxResolution = b;
        imageRenderer.setPaintMaxResolution(b);

    }

    public void setIgnoreMargins(boolean b) {
        this.ignoreMargins = b;

    }

    public void printRendererInformation() {
        System.out.println("==== Spreadsheet Renderer ===");
        System.out.println("Rendering file: " + this.od.getZipFile().getName());
        System.out.println("Document margins ignored: " + this.ignoreMargins);
        System.out.println("Max image quality: " + this.paintMaxResolution);
        System.out.println("Current page: " + (this.currentPageIndex + 1) + "  / " + this.getPrintedPagesNumber());
        System.out.println("Width: " + (long) getPrintWidth() + " micrometers (" + getPrintWidth() / 10000 + " cms)");
        System.out.println("Height: " + (long) getPrintHeight() + " micrometers (" + getPrintHeight() / 10000 + " cms)");
        System.out.println("Size in pixels: " + this.getPrintWidthInPixel() + "x" + this.getPrintHeightInPixel());
        System.out.println("Resize factor " + this.resizeFactor);
        System.out.println("Page:" + this.currentPage.getRows().length + " rows ");
        System.out.println();
    }

    public void setDebug(boolean b) {
        debug = true;
    }

    public void printRow(int startRow, int stopRow, int startColumn, int stopColumn) {
        final TableTableRow[] rows = this.currentPage.getRows();
        for (int i = startRow; i < stopRow; i++) {
            TableTableRow row = rows[i];
            TableTableCell[] cells = row.getCellsInRange(startColumn, stopColumn);
            System.out.println("======== Row:" + i);
            for (int j = startColumn; j < stopColumn; j++) {
                TableTableCell cell = cells[j];
                // System.out.println("Cell" + j + ":" + cell + ":" + cell.getFullText() +
                // " Spanned:" + cell.getTableNumberColumnsSpanned() + " Repeated:" +
                // cell.getTableNumberColumnsRepeated()
                // + " Covered:" + cell.isCovered());
            }
        }
    }

    public void addProgressListener(ProgressListener l) {
        this.progressListeners.add(l);

    }

    public void removeProgressListener(ProgressListener l) {
        this.progressListeners.remove(l);
    }

    public synchronized OpenDocument getDocument() {
        return this.od;
    }

}
