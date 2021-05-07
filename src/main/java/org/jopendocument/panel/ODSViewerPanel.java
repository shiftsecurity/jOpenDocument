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

package org.jopendocument.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import org.jopendocument.model.OpenDocument;
import org.jopendocument.print.DocumentPrinter;
import org.jopendocument.renderer.ODTRenderer;

public class ODSViewerPanel extends JPanel implements ProgressListener {
    /**
     * 
     */
    private static final long serialVersionUID = -6113257667157151508L;
    private final ODTRenderer renderer;
    private int mode;
    private int zoom = 100;
    private static final int MODE_PAGE = 0;
    private static final int MODE_WIDTH = 1;
    private static final int MODE_ZOOM = 2;
    private JScrollPane scroll;
    private final JPanel viewer = new JPanel();
    private final JTextField textFieldZoomValue = new JTextField(5);
    private int currentPageIndex = 0;
    private final JPanel tools = new JPanel();
    private JProgressBar progressBar;
    private DocumentPrinter printListener;

    public ODSViewerPanel(final OpenDocument doc) {
        this(doc, null);
    }

    public ODSViewerPanel(final OpenDocument doc, final boolean ignoreMargin) {
        this(doc, null, ignoreMargin);
    }

    public ODSViewerPanel(final OpenDocument doc, final DocumentPrinter printListener) {
        this(doc, printListener, true);
    }

    public ODSViewerPanel(final String url, String odspXml, final DocumentPrinter printListener, final boolean ignoreMargin) {
        renderer = new ODTRenderer(url, odspXml);
        renderer.addProgressListener(this);
        renderer.setIgnoreMargins(ignoreMargin);
        this.printListener = printListener;
        init(ignoreMargin);
        tools.add(new JLabel(Messages.getString("ODSViewerPanel.loading")));
        progressBar = new JProgressBar(0, 100);
        tools.add(progressBar);
        this.setPreferredSize(new Dimension(750, 700));
        this.setMinimumSize(new Dimension(740, 700));
    }

    public ODSViewerPanel(final OpenDocument doc, final DocumentPrinter printListener, final boolean ignoreMargin) {
        this.printListener = printListener;
        renderer = new ODTRenderer(doc);
        renderer.setIgnoreMargins(ignoreMargin);
        updateMode(MODE_ZOOM, this.zoom);
        init(ignoreMargin);
        initTools(doc);
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                updateMode(mode, zoom);
            }
        });
    }

    private void init(final boolean ignoreMargin) {

        Toolkit.getDefaultToolkit().setDynamicLayout(false);
        this.setOpaque(false);

        // Viewer
        viewer.setOpaque(false);
        viewer.setBackground(Color.DARK_GRAY);
        viewer.setLayout(null);
        renderer.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        viewer.add(renderer);
        this.setLayout(new BorderLayout());
        this.add(tools, BorderLayout.NORTH);
        scroll = new JScrollPane(viewer);
        scroll.setOpaque(false);
        scroll.getHorizontalScrollBar().setUnitIncrement(30);
        scroll.getVerticalScrollBar().setUnitIncrement(30);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        ((JComponent) scroll.getViewport().getView()).setOpaque(false);
        this.add(scroll, BorderLayout.CENTER);

    }

    private void initTools(final OpenDocument doc) {
        final JButton buttonTailleReelle = new JButton(Messages.getString("ODSViewerPanel.normalSize"));
        buttonTailleReelle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateMode(MODE_ZOOM, 100);
            }
        });
        tools.add(buttonTailleReelle);
        final JButton buttonFullPage = new JButton(Messages.getString("ODSViewerPanel.fitPage"));
        buttonFullPage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (mode != MODE_PAGE) {
                    int width = (int) scroll.getViewportBorderBounds().getWidth();
                    int height = (int) scroll.getViewportBorderBounds().getHeight();
                    final double resizeW = renderer.getPageWidth() / width;
                    final double resizeH = renderer.getPageHeight() / height;
                    double resize = resizeH;
                    if (resizeW > resizeH) {
                        resize = resizeW;
                    }
                    updateMode(MODE_PAGE, (int) ((100 * 360) / resize));
                }
            }
        });
        tools.add(buttonFullPage);
        final JButton buttonFullWidth = new JButton(Messages.getString("ODSViewerPanel.fitWidth"));
        buttonFullWidth.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int width = (int) (scroll.getViewportBorderBounds().getWidth());
                final double resizeW = renderer.getPageWidth() / width;
                updateMode(MODE_WIDTH, (int) ((100 * 360) / resizeW));
            }
        });
        tools.add(buttonFullWidth);

        final JButton buttonZoomOut = new JButton("-");
        buttonZoomOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (zoom > 30) {
                    updateMode(mode, zoom - 20);
                }
            }
        });
        tools.add(buttonZoomOut);
        textFieldZoomValue.setEditable(false);
        tools.add(textFieldZoomValue);
        final JButton buttonZoomIn = new JButton("+");
        buttonZoomIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int z = zoom + 20;
                if (z > 400)
                    z = 400;
                updateMode(mode, z);
            }
        });
        tools.add(buttonZoomIn);
        if (doc.getPrintedPageCount() > 1) {
            final JTextField page = new JTextField(5);
            page.setHorizontalAlignment(JTextField.CENTER);
            JButton previousButton = new JButton("<");
            previousButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    if (currentPageIndex > 0) {
                        currentPageIndex--;
                        updatePage(currentPageIndex);
                        updatePageCount(doc, page);
                    }

                }

            });
            JButton nextButton = new JButton(">");
            nextButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    if (currentPageIndex < doc.getPrintedPageCount() - 1) {
                        currentPageIndex++;
                        updatePage(currentPageIndex);
                        updatePageCount(doc, page);
                    }

                }

            });
            tools.add(previousButton);
            updatePageCount(doc, page);
            tools.add(page);
            tools.add(nextButton);
        }
        if (printListener != null) {

            final JButton buttonPrint = new JButton(Messages.getString("ODSViewerPanel.print"));
            buttonPrint.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    printListener.print(doc);

                }
            });
            tools.add(buttonPrint);
        }
    }

    /**
     * @param doc
     * @param page
     */
    private void updatePageCount(final OpenDocument doc, final JTextField page) {
        page.setText((currentPageIndex + 1) + "/" + doc.getPrintedPageCount());
    }

    protected void updatePage(int i) {
        this.renderer.setCurrentPage(i);
    }

    private void updateMode(int m, int zoom_value) {
        this.mode = m;
        this.zoom = zoom_value;
        this.textFieldZoomValue.setText(zoom + " %");

        renderer.setResizeFactor(((100 * 360) / zoom_value));

        int w = this.renderer.getPageWidthInPixel();

        int h = this.renderer.getPageHeightInPixel();

        int posx = 0;
        int posy = 0;
        if (scroll != null) {
            posx = (scroll.getViewportBorderBounds().width - w) / 2;
            posy = (scroll.getViewportBorderBounds().height - h) / 2;
        }
        if (posy > 10)
            posy = 10;

        if (posx < 0)
            posx = 0;
        if (posy < 0)
            posy = 0;
        renderer.setLocation(posx, posy);
        // final int renderedHeight = renderer.getPrintHeightInPixel();
        // renderer.setSize(renderer.getPrintWidthInPixel(), renderedHeight);
        final Dimension size = new Dimension(w, h);
        viewer.setPreferredSize(size);

        // Let the scroll pane know to update itself
        // and its scrollbars.
        viewer.revalidate();
        repaint();

    }

    public ODTRenderer getRenderer() {
        return renderer;
    }

    @Override
    public void progressUpdate(int percent) {
        this.progressBar.setValue(percent);
        if (percent >= 100) {
            updateMode(MODE_ZOOM, this.zoom);
            renderer.removeProgressListener(this);
            this.addComponentListener(new ComponentAdapter() {

                public void componentResized(ComponentEvent e) {
                    updateMode(mode, zoom);

                }

            });

            tools.removeAll();
            initTools(renderer.getDocument());
            this.revalidate();
        }
    }
}
