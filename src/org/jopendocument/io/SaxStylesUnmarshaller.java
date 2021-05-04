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

package org.jopendocument.io;

import static org.jopendocument.io.SaxContentUnmarshaller.log;

import java.util.Stack;

import org.jopendocument.model.OpenDocument;
import org.jopendocument.model.office.OfficeAutomaticStyles;
import org.jopendocument.model.office.OfficeMasterStyles;
import org.jopendocument.model.office.OfficeStyles;
import org.jopendocument.model.script.StyleGraphicProperties;
import org.jopendocument.model.style.StyleDefaultStyle;
import org.jopendocument.model.style.StyleListLevelLabelAlignment;
import org.jopendocument.model.style.StyleListLevelProperties;
import org.jopendocument.model.style.StyleMasterPage;
import org.jopendocument.model.style.StylePageLayout;
import org.jopendocument.model.style.StylePageLayoutProperties;
import org.jopendocument.model.style.StyleParagraphProperties;
import org.jopendocument.model.style.StyleStyle;
import org.jopendocument.model.style.StyleTabStop;
import org.jopendocument.model.style.StyleTabStops;
import org.jopendocument.model.style.StyleTableColumnProperties;
import org.jopendocument.model.style.StyleTextProperties;
import org.jopendocument.model.text.TextListLevelStyleBullet;
import org.jopendocument.model.text.TextListLevelStyleNumber;
import org.jopendocument.model.text.TextListStyle;
import org.jopendocument.model.text.TextOutlineLevelStyle;
import org.jopendocument.model.text.TextOutlineStyle;
import org.jopendocument.util.ValueHelper;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class SaxStylesUnmarshaller extends DefaultHandler {
    private OfficeStyles styles = new OfficeStyles();
    private OfficeAutomaticStyles autoStyles;
    private OfficeMasterStyles masterStyles;
    private Stack<Object> stack;

    private Object current;
    final private OpenDocument doc;

    public SaxStylesUnmarshaller(OpenDocument doc) {
        stack = new Stack<Object>();
        this.doc = doc;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attribs) {

        /*
         * for (int i = 0; i < this.stack.size(); i++) { System.out.print(" "); }
         * System.out.println("Start:" + qName + " " + localName + " +" + attribs.getLocalName(0));
         */// if next element is complex, push a new instance on the stack
           // if element has attributes, set them in the new instance
        if (qName.equals("office:styles")) {
            styles = new OfficeStyles();
            push(styles);
        } else if (qName.equals("style:default-style")) {
            StyleDefaultStyle defaultStyle = new StyleDefaultStyle();
            defaultStyle.setStyleFamily(attribs.getValue("style:family"));

            if (current instanceof OfficeStyles) {
                ((OfficeStyles) current).addDefaultStyle(defaultStyle);

            } else {
                log("Not OfficeStyles:" + current, false);
            }

            push(defaultStyle);

        } else if (qName.equals("style:text-properties")) {
            StyleTextProperties props = StyleTextProperties.getStyleTextProperties(attribs.getValue("style:font-name"), attribs.getValue("fo:font-size"), attribs.getValue("fo:font-weight"),
                    attribs.getValue("fo:font-style"), attribs.getValue("fo:color"));

            if (current instanceof StyleDefaultStyle) {
                ((StyleDefaultStyle) current).setStyleTextProperties(props);
            } else if (current instanceof StyleStyle) {
                ((StyleStyle) current).setTextProperties(props);
            } else if (current instanceof TextListLevelStyleBullet) {
                ((TextListLevelStyleBullet) current).setTextProperties(props);
            } else {
                log("Not StyleDefaultStyle:" + current, false);
            }

            push(props);

        } else if (qName.equals("office:automatic-styles")) {
            autoStyles = new OfficeAutomaticStyles();
            push(autoStyles);
        } else if (qName.equals("style:page-layout")) {
            StylePageLayout layout = new StylePageLayout();
            layout.setStyleName(attribs.getValue("style:name"));
            if (current instanceof OfficeAutomaticStyles) {
                autoStyles.addPageLayout(layout);
            } else {
                log("Not OfficeAutomaticStyles:" + current, false);
            }

            push(layout);

        } else if (qName.equals("style:page-layout-properties")) {
            StylePageLayoutProperties props = new StylePageLayoutProperties();
            props.setPageWidth(attribs.getValue("fo:page-width"));
            props.setPageHeight(attribs.getValue("fo:page-height"));
            props.setMarginTop(attribs.getValue("fo:margin-top"));
            props.setMarginBottom(attribs.getValue("fo:margin-bottom"));
            props.setMarginLeft(attribs.getValue("fo:margin-left"));
            props.setMarginRight(attribs.getValue("fo:margin-right"));
            props.setShadow(attribs.getValue("style:shadow"));
            props.setBackgroundColor(attribs.getValue("fo:background-color"));
            props.setScaleTo(attribs.getValue("style:scale-to"));
            props.setTableCentering(attribs.getValue("style:table-centering"));
            props.setWritingMode(attribs.getValue("style:writing-mode"));

            if (current instanceof StylePageLayout) {
                ((StylePageLayout) current).setPageLayoutProperties(props);
            } else {
                log("Not StylePageLayout:" + current, false);
            }

            push(props);

        } else if (qName.equals("office:master-styles")) {
            masterStyles = new OfficeMasterStyles();

            push(masterStyles);
        } else if (qName.equals("style:master-page")) {
            StyleMasterPage page = new StyleMasterPage();
            page.setStyleName(attribs.getValue("style:name"));
            page.setStylePageLayoutName(attribs.getValue("style:page-layout-name"));

            if (current instanceof OfficeMasterStyles) {
                masterStyles.addMasterPage(page);
            } else {
                log("Not OfficeMasterStyles:" + current, false);
            }

            push(page);

        } else if (qName.equals("style:style")) {
            final StyleStyle style = new StyleStyle();
            style.setStyleName(attribs.getValue("style:name"));
            style.setStyleFamily(attribs.getValue("style:family"));
            style.setStyleParentStyleName(attribs.getValue("style:parent-style-name"));
            style.setMasterPageName(attribs.getValue("style:master-page-name"));

            // style:data-style-name="N108"
            if (this.current instanceof OfficeStyles) {
                this.doc.getStyleResolver().add(style);
                this.styles.addStyle(style);
            } else if (this.current instanceof OfficeAutomaticStyles) {
                this.doc.getStyleResolver().add(style);
                this.styles.addStyle(style);
            } else {
                log("Not OfficeStyles: " + this.current + " style: " + style.getStyleName());
            }
            this.push(style);
        } else if (qName.equals("style:paragraph-properties")) {
            final StyleParagraphProperties props = new StyleParagraphProperties();
            props.setTextAlign(attribs.getValue("fo:text-align"));
            props.setMarginLeft(attribs.getValue("fo:margin-left"));

            if (this.current instanceof StyleDefaultStyle) {
                ((StyleDefaultStyle) this.current).setParagraphProperties(props);
            } else if (this.current instanceof StyleStyle) {
                ((StyleStyle) this.current).setParagraphProperties(props);
            } else {
                log("Not StyleDefaultStyle:" + this.current);
            }
            this.push(props);
        } else if (qName.equals("style:table-properties")) {
            final StyleTableProperties props = new StyleTableProperties();
            props.setBorderModel(attribs.getValue("table:border-model"));

            if (this.current instanceof StyleDefaultStyle) {
                ((StyleDefaultStyle) this.current).setTableProperties(props);
            } else if (this.current instanceof StyleStyle) {
                ((StyleStyle) this.current).setTableProperties(props);
            } else {
                log("Not StyleDefaultStyle:" + this.current);
            }
            this.push(props);
        } else if (qName.equals("style:table-column-properties")) {
            final StyleTableColumnProperties props = new StyleTableColumnProperties();
            if (attribs.getValue("style:column-width") != null)
                props.setStyleColumnWidth(attribs.getValue("style:column-width"));
            if (attribs.getValue("style:rel-column-width") != null)
                props.setStyleRelColumnWidth(attribs.getValue("style:rel-column-width"));
            if (this.current instanceof StyleStyle) {
                ((StyleStyle) this.current).setTableColumnProperties(props);
            } else {
                log("Not StyleStyle:" + this.current);
            }
            this.push(props);
        } else if (qName.equals("style:tab-stops")) {
            final StyleTabStops p = new StyleTabStops();

            if (this.current instanceof StyleParagraphProperties) {
                ((StyleParagraphProperties) this.current).addTabStops(p);
            } else {
                log("StyleParagraphProperties:" + this.current);
            }
            this.push(p);

        } else if (qName.equals("style:tab-stop")) {
            final StyleTabStop ts = new StyleTabStop();
            ts.setStylePosition(attribs.getValue("style:position"));
            if (this.current instanceof StyleTabStops) {
                ((StyleTabStops) this.current).add(ts);
            } else {
                log("StyleTabStops:" + this.current);
            }
            this.push(ts);

        } else if (qName.equals("style:graphic-properties")) {
            final StyleGraphicProperties props = new StyleGraphicProperties();
            props.setMarginLeft(attribs.getValue("fo:margin-left"));
            props.setMarginRight(attribs.getValue("fo:margin-right"));
            props.setMarginTop(attribs.getValue("fo:margin-top"));
            props.setMarginBottom(attribs.getValue("fo:margin-bottom"));
            props.setProtection(attribs.getValue("style:protect"));
            props.setWrap(attribs.getValue("style:wrap"));
            props.setNumberWrappedParagraphs(attribs.getValue("style:number-wrapped-paragraphs"));
            props.setVerticalPosition(attribs.getValue("style:vertical-pos"));
            props.setVerticalRelative(attribs.getValue("style:vertical-rel"));
            props.setHorizontalPosition(attribs.getValue("style:horizontal-pos"));
            props.setHorizontalRelative(attribs.getValue("style:horizontal-rel"));
            props.setPadding(attribs.getValue("fo:padding"));
            props.setBorder(attribs.getValue("fo:border"));
            props.setShadow(attribs.getValue("style:shadow"));

            if (this.current instanceof StyleDefaultStyle) {
                ((StyleDefaultStyle) this.current).setGraphicProperties(props);
            } else if (this.current instanceof StyleStyle) {
                ((StyleStyle) this.current).setGraphicProperties(props);
            } else {
                log("Not StyleDefaultStyle:" + this.current);
            }
            this.push(props);

        } else if (qName.equals("text:list-level-style-number")) {
            // /////////
            final TextListLevelStyleNumber tls = new TextListLevelStyleNumber();
            tls.setTextLevel(attribs.getValue("text:level"));
            tls.setTextStyleName(attribs.getValue("text:style-name"));
            tls.setStyleNumSuffix(attribs.getValue("style:num-suffix"));
            tls.setStyleNumPrefix(attribs.getValue("style:num-prefix"));
            tls.setStyleNumFormat(attribs.getValue("style:num-format"));
            tls.setTextStartValue(attribs.getValue("text:start-value"));

            if (this.current instanceof TextListStyle) {
                ((TextListStyle) this.current).addListLevelStyleNumber(tls);
            } else {
                log("Not TextListStyle: " + this.current);
            }
            this.push(tls);

        } else if (qName.equals("style:list-level-properties")) {
            final StyleListLevelProperties tls = new StyleListLevelProperties();
            tls.setSpaceBefore(attribs.getValue("text:space-before"));
            tls.setMinLabelWidth(attribs.getValue("text:min-label-width"));
            if (this.current instanceof TextListLevelStyleBullet) {
                ((TextListLevelStyleBullet) this.current).setStyleListLevelProperties(tls);
            } else if (this.current instanceof TextListLevelStyleNumber) {
                ((TextListLevelStyleNumber) this.current).setStyleListLevelProperties(tls);
            } else if (this.current instanceof TextOutlineLevelStyle) {
                ((TextOutlineLevelStyle) this.current).setStyleListLevelProperties(tls);
            } else {
                log("Not TextListLevelStyleBullet: " + this.current);
            }
            this.push(tls);
        } else if (qName.equals("style:list-level-label-alignment")) {
            final StyleListLevelLabelAlignment tls = new StyleListLevelLabelAlignment();
            tls.setLabelFollowedBy(attribs.getValue("text:label-followed-by"));
            tls.setListTabStopPosition(ValueHelper.getLength(attribs.getValue("text:list-tab-stop-position")));
            tls.setTextIndent(ValueHelper.getLength(attribs.getValue("fo:text-indent")));
            tls.setMarginLeft(ValueHelper.getLength(attribs.getValue("fo:margin-left")));

            if (this.current instanceof StyleListLevelProperties) {
                ((StyleListLevelProperties) this.current).setListLevelLabelAlignment(tls);
            } else {
                log("Not StyleListLevelProperties: " + this.current);
            }
            this.push(tls);

        }

        else if (qName.equals("text:outline-style")) {
            final TextOutlineStyle tls = new TextOutlineStyle();
            tls.setStyleName(attribs.getValue("style:name"));

            if (this.current instanceof OfficeStyles) {
                ((OfficeStyles) this.current).addStyle(tls);
            } else {
                log("Not TextOutlineStyle: " + this.current);
            }
            this.push(tls);
        } else if (qName.equals("text:outline-level-style")) {
            final TextOutlineLevelStyle tls = new TextOutlineLevelStyle();

            tls.setTextLevel(attribs.getValue("text:level"));
            tls.setStyleNumFormat(attribs.getValue("style:num-format"));
            if (this.current instanceof TextOutlineStyle) {
                ((TextOutlineStyle) this.current).addTextOutlineLevelStyle(tls);
            } else {
                log("Not TextOutlineStyle: " + this.current);
            }
            this.push(tls);
        } else if (qName.equals("text:list-style")) {
            // /////////
            final TextListStyle tls = new TextListStyle();
            tls.setStyleName(attribs.getValue("style:name"));
            if (this.current instanceof OfficeStyles) {
                ((OfficeStyles) this.current).addStyle(tls);
            } else {
                log("Not OfficeStyles: " + this.current);
            }
            this.push(tls);

        } else if (qName.equals("text:list-level-style-bullet")) {
            // /////////
            final TextListLevelStyleBullet tls = new TextListLevelStyleBullet();
            tls.setTextLevel(attribs.getValue("text:level"));
            tls.setTextStyleName(attribs.getValue("text:style-name"));
            tls.setStyleNumSuffix(attribs.getValue("style:num-suffix"));
            tls.setStyleNumPrefix(attribs.getValue("style:num-prefix"));
            tls.setTextBulletChar(attribs.getValue("text:bullet-char"));
            if (this.current instanceof TextListStyle) {
                ((TextListStyle) this.current).addListLevelStyleBullet(tls);
            } else {
                log("Not TextListStyle: " + this.current);
            }
            this.push(tls);

        } else if (qName.equals("office:font-face-decls") || qName.equals("style:font-face")) {
            // FIXME: Font Face handling
            push(uri);
        }

        // if none of the above, it is an unexpected element
        else {
            log("style.xml : ignoring :" + qName + " current:" + current, false);
            push(uri);

        }
    }

    // -----

    private void push(Object o) {
        this.current = o;
        this.stack.push(o);

    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        /*
         * for (int i = 0; i < this.stack.size() - 1; i++) { System.out.print(" "); }
         * System.out.println("End: " + qName + " " + localName);
         */

        pop();

    }

    private void pop() {

        if (!stack.isEmpty()) {
            stack.pop();

        }
        if (!stack.isEmpty()) {
            current = stack.peek();
        }
    }

    // -----

    @Override
    public void characters(char[] data, int start, int length) {

    }

    public OfficeStyles getStyles() {
        return styles;
    }

    public OfficeMasterStyles getMasterStyles() {
        return masterStyles;
    }

    public OfficeAutomaticStyles getAutomaticStyles() {
        return autoStyles;
    }
}
