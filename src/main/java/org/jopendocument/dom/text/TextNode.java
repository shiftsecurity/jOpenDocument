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

package org.jopendocument.dom.text;

import org.jopendocument.dom.ODDocument;
import org.jopendocument.dom.ODNodeDesc.Children;
import org.jopendocument.dom.ODPackage;
import org.jopendocument.dom.OOXML;
import org.jopendocument.dom.Style;
import org.jopendocument.dom.Style.ResolveResult;
import org.jopendocument.dom.StyledNode;
import org.jopendocument.dom.XMLFormatVersion;
import org.jopendocument.dom.spreadsheet.Cell;
import org.jopendocument.util.CollectionUtils;
import org.jopendocument.util.cc.IPredicate;
import org.jopendocument.util.DescendantIterator;
import org.jopendocument.util.JDOMUtils;
import org.jopendocument.util.SimpleXMLPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.jdom.Attribute;
import org.jdom.Content;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.Text;

/**
 * A text node that can be created ex nihilo. Ie without a document at first.
 * 
 * @author Sylvain CUAZ
 * 
 * @param <S> type of style.
 */
public abstract class TextNode<S extends TextStyle> extends StyledNode<S, ODDocument> {

    // see §6.1.2 White Space Characters of OpenDocument v1.2
    private static final Pattern multiSpacePattern = Pattern.compile("[\t\r\n ]+");
    public static final String LINE_SEPARATOR = "\u2028";
    private static final char LINE_SEPARATOR_CHAR = LINE_SEPARATOR.charAt(0);
    public static final String PARAGRAPH_SEPARATOR = "\u2029";
    /**
     * Used by Microsoft Word as a line separator.
     * 
     * @see <a href="http://support.microsoft.com/kb/59096/en-us">Microsoft</a>
     * @see <a href="http://unicode.org/reports/tr13/tr13-9.html">UNICODE NEWLINE GUIDELINES</a>
     */
    public static final char VERTICAL_TAB_CHAR = '\u000B';

    static public String getChildrenCharacterContent(final Element parentElem, final XMLFormatVersion vers, final boolean ooMode) {
        return getChildrenCharacterContent(parentElem, vers, ooMode, false);
    }

    /**
     * Return the text value of the passed element.
     * 
     * @param parentElem an element containing paragraphs.
     * @param vers the version of the element.
     * @param ooMode whether to use the OO way or the standard way.
     * @param useSeparator if <code>true</code> line-breaks are returned as {@value #LINE_SEPARATOR}
     *        and paragraphs as {@value #PARAGRAPH_SEPARATOR}, if <code>false</code> only
     *        <code>'\n'</code> is used.
     * @return the parsed text value.
     * @see #getCharacterContent(Element, XMLFormatVersion, boolean, boolean)
     */
    static public String getChildrenCharacterContent(final Element parentElem, final XMLFormatVersion vers, final boolean ooMode, final boolean useSeparator) {
        final List<String> ps = getChildrenCharacterContent(parentElem, vers, ooMode, useSeparator, null);
        return CollectionUtils.join(ps, useSeparator ? PARAGRAPH_SEPARATOR : "\n");

    }

    static private List<String> getChildrenCharacterContent(final Element parentElem, final XMLFormatVersion vers, final boolean ooMode, final boolean useSeparator, final Option option) {
        final List<String> ps = new ArrayList<String>();
        for (final Object o : parentElem.getChildren()) {
            final Element child = (Element) o;
            if ((child.getName().equals("p") || child.getName().equals("h")) && child.getNamespacePrefix().equals("text")) {
                @SuppressWarnings("unchecked")
                final List<Content> content = child.getContent();
                ps.add(getCharacterContent(content, vers, ooMode, useSeparator, option));
            }
        }
        return ps;
    }

    /**
     * Get the number of lines in the passed element.
     * 
     * @param parentElem an element containing paragraphs.
     * @param vers the version of the element.
     * @param ooMode whether to use the OO way or the standard way.
     * @return 0 if the element contains no paragraphs, otherwise the number of paragraphs and line
     *         breaks.
     */
    static public int getLinesCount(final Element parentElem, final XMLFormatVersion vers, final boolean ooMode) {
        final List<String> ps = getChildrenCharacterContent(parentElem, vers, ooMode, false, Option.ONLY_SEP);
        int res = 0;
        for (final String p : ps) {
            // one line for the paragraph plus one for each line break
            res += 1 + p.length();
        }
        return res;
    }

    /**
     * Return the text value of the passed element. This method doesn't just return the XML text
     * content, it also parses XML elements (like paragraphs, tabs and line-breaks). For the
     * differences between the OO way (as of 3.1) and the OpenDocument way see section 5.1.1
     * White-space Characters of OpenDocument-v1.0-os and §6.1.2 of OpenDocument-v1.2-part1. In
     * essence OpenOffice never trim strings.
     * 
     * @param pElem a text element, e.g. text:p or text:h.
     * @param vers the version of the element.
     * @param ooMode whether to use the OO way or the standard way.
     * @return the parsed text value.
     */
    static public final String getCharacterContent(final Element pElem, final XMLFormatVersion vers, final boolean ooMode) {
        return getCharacterContent(pElem, vers, ooMode, false);
    }

    /**
     * Return the text value of the passed element. This method doesn't just return the XML text
     * content, it also parses XML elements (like paragraphs, tabs and line-breaks). For the
     * differences between the OO way (as of 3.1) and the OpenDocument way see section 5.1.1
     * White-space Characters of OpenDocument-v1.0-os and §6.1.2 of OpenDocument-v1.2-part1. In
     * essence OpenOffice never trim strings.
     * 
     * @param pElem a text element, e.g. text:p or text:h.
     * @param vers the version of the element.
     * @param ooMode whether to use the OO way or the standard way.
     * @param useSeparator if <code>true</code> line-breaks are returned as {@value #LINE_SEPARATOR}
     *        otherwise <code>'\n'</code> is used.
     * @return the parsed text value.
     */
    @SuppressWarnings("unchecked")
    static public final String getCharacterContent(final Element pElem, final XMLFormatVersion vers, final boolean ooMode, final boolean useSeparator) {
        return getCharacterContent(pElem.getContent(), vers, ooMode, useSeparator, null);
    }

    private static enum Option {
        STOP_AT_FIRST_CHAR, ONLY_SEP
    }

    static private final String getCharacterContent(final List<Content> pElem, final XMLFormatVersion vers, final boolean ooMode, final boolean useSeparator, final Option option) {
        if (pElem.isEmpty())
            return "";
        final OOXML xml = OOXML.get(vers, false);

        final StringBuilder sb = new StringBuilder();
        final Namespace textNS = xml.getVersion().getTEXT();
        final Element tabElem = xml.getTab();
        final Element newLineElem = xml.getLineBreak();
        // true if the string ends with a space that wasn't expanded from an XML element (e.g.
        // <tab/> or <text:s/>)
        boolean spaceSuffix = false;
        final Iterator<?> iter = new DescendantIterator(pElem, new IPredicate<Content>() {
            @Override
            public boolean evaluateChecked(Content input) {
                if (input instanceof Element) {
                    // don't descend into frames, graphical shapes...
                    return !((Element) input).getNamespace().getPrefix().equals("draw");
                }
                return true;
            }
        });
        while (iter.hasNext()) {
            final Object o = iter.next();
            if (option == Option.ONLY_SEP) {
                if (o instanceof Element && JDOMUtils.equals((Element) o, newLineElem)) {
                    sb.append(useSeparator ? LINE_SEPARATOR_CHAR : '\n');
                }
            } else {
                if (o instanceof Text) {
                    final String text = multiSpacePattern.matcher(((Text) o).getText()).replaceAll(" ");
                    // trim leading
                    if (!ooMode && text.startsWith(" ") && (spaceSuffix || sb.length() == 0))
                        sb.append(text.substring(1));
                    else
                        sb.append(text);
                    spaceSuffix = text.endsWith(" ");
                } else if (o instanceof Element) {
                    // perhaps handle conditions (conditional-text, hiddenparagraph, hidden-text)
                    final Element elem = (Element) o;
                    if (JDOMUtils.equals(elem, tabElem)) {
                        sb.append('\t');
                    } else if (JDOMUtils.equals(elem, newLineElem)) {
                        sb.append(useSeparator ? LINE_SEPARATOR_CHAR : '\n');
                    } else if (elem.getName().equals("s") && elem.getNamespace().equals(textNS)) {
                        final int count = Integer.valueOf(elem.getAttributeValue("c", textNS, "1"));
                        final char[] toAdd = new char[count];
                        Arrays.fill(toAdd, ' ');
                        sb.append(toAdd);
                    }
                }
            }
            if (option == Option.STOP_AT_FIRST_CHAR && sb.length() > 0)
                return sb.toString();
        }
        // trim trailing
        if (option != Option.ONLY_SEP && !ooMode && spaceSuffix)
            sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    // return the one and only <span> that contains the whole text, null otherwise.
    @SuppressWarnings("unchecked")
    static public final Element getWholeSpan(final Element pElem, final XMLFormatVersion vers, final boolean ooMode) {
        final Iterator<Element> spanIter = pElem.getContent(TextNodeDesc.get(Span.class).getFilter(vers)).iterator();
        if (!spanIter.hasNext())
            return null;
        final Element first = spanIter.next();
        if (spanIter.hasNext())
            return null;

        final int index = pElem.indexOf(first);
        if (getCharacterContent(pElem.getContent().subList(0, index), vers, ooMode, false, Option.STOP_AT_FIRST_CHAR).length() > 0
                || getCharacterContent(pElem.getContent().subList(index + 1, pElem.getContentSize()), vers, ooMode, false, Option.STOP_AT_FIRST_CHAR).length() > 0)
            return null;
        return first;
    }

    private final XMLFormatVersion version;
    protected ODDocument parent;

    // not public since, local element cannot be checked against vers
    protected TextNode(final Element local, final Class<S> styleClass, final XMLFormatVersion vers) {
        this(local, styleClass, vers, null);
    }

    protected TextNode(final Element local, final Class<S> styleClass, final ODDocument parent) {
        this(local, styleClass, null, parent);
    }

    private TextNode(final Element local, final Class<S> styleClass, final XMLFormatVersion vers, final ODDocument parent) {
        super(local, styleClass);
        this.version = vers == null ? parent.getFormatVersion() : vers;
        if (this.version == null)
            throw new NullPointerException("No version");
        this.setDocument(parent);
    }

    public final XMLFormatVersion getVersion() {
        return this.version;
    }

    @Override
    public final ODDocument getODDocument() {
        return this.parent;
    }

    public final void detach() {
        this.setDocument(null);
    }

    private final void setDocument(ODDocument doc) {
        if (doc != this.parent) {
            if (doc == null) {
                this.getElement().detach();
                this.parent = null;
            } else if (doc.getPackage().getXMLFile(this.getElement().getDocument()) == null) {
                throw new IllegalArgumentException("Not already in the passed document");
            } else {
                this.parent = doc;
            }
        }
    }

    public final void addToDocument(ODDocument doc, Element where, int index) {
        if (doc == null) {
            this.detach();
        } else {
            this.checkDocument(doc.getPackage(), where);
            if (index < 0)
                where.addContent(this.getElement());
            else
                where.addContent(index, this.getElement());
            this.setDocument(doc);
        }
    }

    protected final void checkDocument(final ODPackage pkg, final Element where) {
        if (!pkg.getFormatVersion().equals(this.getVersion()))
            throw new IllegalArgumentException("Version mismatch : " + this.getVersion() + " != " + pkg.getFormatVersion());
        if (pkg.getXMLFile(where.getDocument()) == null)
            throw new IllegalArgumentException("Where element not in the passed package");
        if (this.getStyleName() != null && getStyle(pkg, where.getDocument()) == null)
            throw new IllegalArgumentException("unknown style " + getStyleName() + " in " + pkg);
        for (final Attribute attr : SimpleXMLPath.allAttributes().selectNodes(getElement())) {
            if (Style.resolveReference(pkg, where.getDocument(), attr) == ResolveResult.NOT_RESOLVED)
                throw new IllegalArgumentException(this + " is using an undefined style : " + attr);
        }
    }

    public final void addTab() {
        this.getElement().addContent(OOXML.get(this.getVersion()).getTab());
    }

    public final void addContent(String text) {
        this.getElement().addContent(OOXML.get(this.getVersion()).encodeWSasList(text));
    }

    public final Span addStyledContent(String text, String styleName) {
        final Element elem = Span.createEmpty(getVersion());
        getElement().addContent(elem);
        final Span res = createSpan(elem);
        res.addContent(text);
        res.setStyleName(styleName);
        return res;
    }

    private Span createSpan(final Element elem) {
        final ODDocument doc = this.getODDocument();
        return doc == null ? TextNodeDesc.get(Span.class).wrapNode(getVersion(), elem) : TextNodeDesc.get(Span.class).wrapNode(doc, elem);
    }

    public final String getCharacterContent() {
        return this.getCharacterContent(Cell.getTextValueMode());
    }

    public final String getCharacterContent(final boolean ooMode) {
        return getCharacterContent(this.getElement(), getVersion(), ooMode);
    }

    public final Children<Span> getSpans() {
        final TextNodeDesc<Span> nodeDesc = TextNodeDesc.get(Span.class);
        // perhaps Children should get the document dynamically (since it can change)
        final ODDocument doc = this.getODDocument();
        return doc == null ? nodeDesc.getChildren(getVersion(), getElement()) : nodeDesc.getChildren(doc, getElement());
    }
}