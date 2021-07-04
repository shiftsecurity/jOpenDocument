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

package org.jopendocument.dom.spreadsheet;

import org.jopendocument.dom.ODDocument;
import org.jopendocument.dom.OOXML;
import org.jopendocument.dom.XMLFormatVersion;
import org.jopendocument.dom.text.Heading;
import org.jopendocument.dom.text.Paragraph;
import org.jopendocument.dom.text.Span;
import org.jopendocument.dom.text.TextNode;
import org.jopendocument.dom.text.TextNodeDesc;
import org.jopendocument.util.JDOMUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdom.Content;
import org.jdom.Element;
import org.jdom.Text;
import org.jdom.filter.ElementFilter;
import org.jdom.filter.Filter;

// this represent a list of lines separated by Sep, e.g. :
// foo Sep.ANY bar Sep.LINE baz Sep.PARAGRAPH
public final class Lines {

    // remove all text:span (keeping their content) from elem
    static private final void flattenSpans(final Element elem, final XMLFormatVersion vers) {
        final ElementFilter filter = TextNodeDesc.get(Span.class).getFilter(vers);
        int i = 0;
        int size = elem.getContentSize();
        while (i < size) {
            final Content c = elem.getContent(i);
            if (filter.matches(c)) {
                elem.addContent(i, ((Element) c).removeContent());
                c.detach();
                size = elem.getContentSize();
            } else {
                i++;
            }
        }
    }

    // newline or line separator or paragraph separator
    static private final Pattern SEP_PATTERN = Pattern.compile("(\r?\n)|" + TextNode.VERTICAL_TAB_CHAR + "|\\p{Zl}|\\p{Zp}");

    static private enum Sep {
        // allow to re-use exiting lines (be it a line-break or a new paragraph)
        ANY,
        // explicit line-break
        LINE,
        // explicit new paragraph
        PARAGRAPH
    }

    static private final Sep getSep(final String s, final boolean onlyP) {
        final int codePoint = s.codePointAt(0);
        final Sep res;
        if (codePoint == '\n' || codePoint == '\r') {
            res = Sep.ANY;
        } else if (codePoint == TextNode.VERTICAL_TAB_CHAR) {
            res = Sep.LINE;
        } else {
            final int cat = Character.getType(codePoint);
            if (cat == Character.PARAGRAPH_SEPARATOR) {
                res = Sep.PARAGRAPH;
            } else if (cat == Character.LINE_SEPARATOR) {
                res = Sep.LINE;
            } else {
                throw new IllegalArgumentException("Unknown codePoint " + codePoint);
            }
        }
        return onlyP ? Sep.PARAGRAPH : res;
    }

    private final ODDocument doc;
    private final OOXML xml;
    private final LinkedList<String> lines;
    private final LinkedList<Sep> separators;

    public Lines(final ODDocument doc, final String text) {
        super();
        this.doc = doc;
        this.xml = OOXML.get(doc.getFormatVersion(), false);
        this.lines = new LinkedList<String>();
        this.separators = new LinkedList<Sep>();
        this.parse(text, isCalc());
    }

    private final boolean isCalc() {
        return this.doc instanceof SpreadSheet;
    }

    // building

    private void parse(final String value, final boolean onlyP) {
        final Matcher matcher = SEP_PATTERN.matcher(value);
        int i = 0;
        while (matcher.find()) {
            this.add(value.substring(i, matcher.start()), getSep(matcher.group(), onlyP));
            i = matcher.end();
        }
        this.addLast(value.substring(i));
    }

    private void add(final String s, final Sep sep) {
        if (sep == null)
            throw new NullPointerException("Null separator");
        addLine(s);
        this.separators.add(sep);
    }

    private final void addLine(final String s) {
        if (s == null)
            throw new NullPointerException("Null string");
        this.lines.add(s);
    }

    private void addLast(final String s) {
        this.addLine(s);
        checkLineFirst(true, "Size mismatch");
    }

    // checking

    private void checkLineFirst(final boolean b, final String msg) {
        if (!checkLineFirst(b, false))
            throw new IllegalArgumentException(msg);
    }

    public final boolean checkLineFirst(final boolean b) {
        return checkLineFirst(b, true);
    }

    public final boolean checkLineFirst(final boolean b, final boolean allowEmpty) {
        final int linesSize = this.lines.size();
        final int sepSize = this.separators.size();
        if (linesSize == 0 && sepSize == 0)
            return allowEmpty;

        final boolean lineFirst;
        if (linesSize == sepSize + 1)
            lineFirst = true;
        else if (linesSize == sepSize)
            lineFirst = false;
        else
            throw new IllegalArgumentException("Size problem");
        return lineFirst == b;
    }

    // consuming

    public Sep peekSep() {
        return this.separators.peekFirst();
    }

    public String peekLine() {
        return this.lines.peekFirst();
    }

    public boolean allConsumed() {
        return peekLine() == null;
    }

    public List<Content> consume() {
        return this.consume(false);
    }

    public List<Content> consume(final boolean noSep) {
        if (!noSep) {
            this.separators.removeFirst();
        } else {
            checkLineFirst(true, "Already separator first");
        }
        final String res = this.lines.removeFirst();
        return this.xml.encodeWSasList(res);
    }

    public void consumeSep() {
        checkLineFirst(false, "Not separator first");
        this.separators.removeFirst();
    }

    // TODO add Integer startLine and Integer endLine parameters to only replace a subset of the
    // current text (null could mean after the end and negative could use TextNode.getLinesCount()).
    // This would allow to easily change the content of a text document.
    public final void setText(final Element elem, final boolean textMode) {
        if (this.doc.getPackage().getXMLFile(elem.getDocument()) == null)
            throw new IllegalArgumentException("Element not in document");
        final boolean isCalc = this.isCalc();
        final XMLFormatVersion vers = this.xml.getFormatVersion();
        if (!this.checkLineFirst(true))
            throw new IllegalStateException("Lines invalid");

        final Element tabElem = this.xml.getTab();
        final Element newLineElem = this.xml.getLineBreak();
        final Element spacesElem = this.xml.createSpaces(1);

        final Filter noNLTextFilter = new Filter() {
            @Override
            public boolean matches(Object obj) {
                if (obj instanceof Element) {
                    final Element elem = (Element) obj;
                    return JDOMUtils.equals(elem, tabElem) || JDOMUtils.equals(elem, spacesElem);
                } else {
                    return obj instanceof Text;
                }
            }
        };
        final Filter nlFilter = new ElementFilter(newLineElem.getName(), newLineElem.getNamespace());
        // reuse text:p to keep style
        final Filter pFilter = TextNodeDesc.get(Paragraph.class).getFilter(vers).or(TextNodeDesc.get(Heading.class).getFilter(vers));
        @SuppressWarnings("unchecked")
        final Iterator<Element> pChildren = new ArrayList<Element>(elem.getContent(pFilter)).iterator();
        while (pChildren.hasNext() && !this.allConsumed()) {
            final Element pElem = pChildren.next();
            // to keep it simple remove all text:span except if there's one for the whole text :
            // allow cells in spreadsheet to keep their character style.
            final Element wholeSpan = TextNode.getWholeSpan(pElem, vers, textMode);
            final Element wholeText = wholeSpan == null ? pElem : wholeSpan;
            flattenSpans(wholeText, vers);

            int j = 0;
            int size = wholeText.getContentSize();
            while (j < size) {
                final Content c = wholeText.getContent(j);
                if (noNLTextFilter.matches(c)) {
                    // remove current text
                    c.detach();
                    size--;
                } else if (nlFilter.matches(c)) {
                    // re-use line-break if allowed
                    if (this.peekSep() == Sep.LINE || this.peekSep() == Sep.ANY) {
                        // add before line-break
                        wholeText.addContent(j, this.consume());
                        size++;
                        // jump after line-break
                        j += 2;
                    } else {
                        // if not allowed (or we're at the last line) remove line-break
                        c.detach();
                        size--;
                    }
                } else {
                    // content that doesn't encode text
                    j++;
                }
            }
            // since we only consumed in the above loop when there was a next separator
            assert !this.allConsumed();
            wholeText.addContent(this.consume(true));
            // *** ATTN sep first
            assert this.checkLineFirst(false);

            // create requested new lines
            while (this.peekSep() == Sep.LINE) {
                wholeText.addContent((Content) newLineElem.clone());
                wholeText.addContent(this.consume());
            }

            // avoid creating paragraphs
            if (!isCalc && !this.allConsumed() && !pChildren.hasNext()) {
                while (this.peekSep() == Sep.LINE || this.peekSep() == Sep.ANY) {
                    wholeText.addContent((Content) newLineElem.clone());
                    wholeText.addContent(this.consume());
                }
            }
            assert this.peekSep() != Sep.LINE;
            if (!this.allConsumed()) {
                this.consumeSep();
            }
            // *** ATTN string first
            assert this.checkLineFirst(true);
        }
        assert this.checkLineFirst(true);
        // remove extra paragraphs
        while (pChildren.hasNext()) {
            pChildren.next().detach();
        }
        // create needed paragraphs
        Element pElem = null;
        while (!this.allConsumed()) {
            final boolean firstLoop = pElem == null;
            // except for the first loop (still string first) there's always a separator if
            // there's a line
            assert firstLoop || this.peekSep() != null;
            if (firstLoop || this.peekSep() == Sep.PARAGRAPH) {
                pElem = Paragraph.createEmpty(vers);
                // switch to sep first in the first loop
                pElem.setContent(this.consume(firstLoop));
                elem.addContent(pElem);
            } else {
                pElem.addContent((Content) newLineElem.clone());
                pElem.addContent(this.consume());
            }
        }
    }
}