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

package org.jopendocument.dom;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;

/**
 * OpenDocument metadata, obtained through {@link ODPackage#getMeta()}.
 * 
 * @author Sylvain
 * @see "section 3 of OpenDocument v1.1"
 */
public class ODMeta extends ODNode {

    static ODMeta create(ODXMLDocument parent) {
        return create(parent, false);
    }

    static ODMeta create(ODXMLDocument parent, boolean b) {
        final Element meta = parent.getChild("meta", b);
        return meta == null ? null : new ODMeta(meta, parent);
    }

    private static final Map<XMLVersion, List<Element>> ELEMS_ORDER;
    static {
        ELEMS_ORDER = new HashMap<XMLVersion, List<Element>>(2);
        ELEMS_ORDER.put(XMLVersion.getOOo(), createChildren(XMLVersion.getOOo()));
        ELEMS_ORDER.put(XMLVersion.getOD(), createChildren(XMLVersion.getOD()));
    }

    private static final List<Element> createChildren(XMLVersion ins) {
        final Namespace meta = ins.getMETA();
        final Namespace dc = ins.getNS("dc");
        final List<Element> res = new ArrayList<Element>(8);
        res.add(new Element("generator", meta));
        res.add(new Element("title", dc));
        res.add(new Element("description", dc));
        res.add(new Element("subject", dc));
        res.add(new Element("keyword", meta));
        res.add(new Element("initial-creator", meta));
        res.add(new Element("creator", dc));
        res.add(new Element("printed-by", meta));
        res.add(new Element("creation-date", meta));
        res.add(new Element("date", dc));
        res.add(new Element("print-date", meta));
        res.add(new Element("template", meta));
        res.add(new Element("auto-reload", meta));
        res.add(new Element("hyperlink-behaviour", meta));
        res.add(new Element("language", dc));
        res.add(new Element("editing-cycles", meta));
        res.add(new Element("editing-duration", meta));
        res.add(new Element("document-statistic", meta));
        res.add(new Element("user-defined", meta));
        return res;
    }

    // *** instance

    private final ODXMLDocument parent;
    private final ChildCreator childCreator;

    private ODMeta(final Element elem, ODXMLDocument parent) {
        super(elem);
        this.parent = parent;
        this.childCreator = new ChildCreator(this.getElement(), ELEMS_ORDER.get(this.getNS()));
    }

    protected final ODXMLDocument getParent() {
        return this.parent;
    }

    protected final XMLVersion getNS() {
        return this.getParent().getVersion();
    }

    public final String getGenerator() {
        return this.getMetaChild("generator").getTextTrim();
    }

    public final void setGenerator(final String s) {
        this.getMetaChild("generator").setText(s);
    }

    public final String getTitle() {
        return this.getDCChild("title").getTextTrim();
    }

    public final void setTitle(final String s) {
        this.getDCChild("title").setText(s);
    }

    public final String getDescription() {
        return this.getDCChild("description").getTextTrim();
    }

    public final void setDescription(final String s) {
        this.getDCChild("description").setText(s);
    }

    public final String getSubject() {
        return this.getDCChild("subject").getTextTrim();
    }

    public final void setSubject(final String s) {
        this.getDCChild("subject").setText(s);
    }

    public final List<String> getKeywords() {
        final List<Element> keywordsElem = getKeywordElems();
        final List<String> res = new ArrayList<String>(keywordsElem.size());
        for (final Element elem : keywordsElem)
            res.add(elem.getTextTrim());
        return res;
    }

    @SuppressWarnings("unchecked")
    private List<Element> getKeywordElems() {
        return this.getElement().getChildren("keyword", this.getNS().getMETA());
    }

    public final void setKeywords(final List<String> s) {
        this.getKeywordElems().clear();
        for (final String keyword : s)
            this.childCreator.addChild(this.getNS().getMETA(), "keyword").setText(keyword);
    }

    public final String getInitialCreator() {
        return this.getMetaChild("initial-creator").getTextTrim();
    }

    public final void setInitialCreator(final String s) {
        this.getMetaChild("initial-creator").setText(s);
    }

    public final String getCreator() {
        return this.getDCChild("creator").getTextTrim();
    }

    public final void setCreator(final String s) {
        this.getDCChild("creator").setText(s);
    }

    public final Calendar getCreationDate() {
        return this.getDateChild("creation-date", this.getNS().getMETA());
    }

    public final void setCreationDate(final Calendar cal) {
        this.setDateChild(cal, "creation-date", this.getNS().getMETA());
    }

    public final Calendar getModifDate() {
        return this.getDateChild("date", this.getNS().getNS("dc"));
    }

    public final void setModifDate(final Calendar cal) {
        this.setDateChild(cal, "date", this.getNS().getNS("dc"));
    }

    public final String getLanguage() {
        return this.getDCChild("language").getTextTrim();
    }

    public final void setLanguage(String s) {
        this.getDCChild("language").setText(s);
    }

    public final int getEditingCycles() {
        final String metaText = this.getMetaChild("editing-cycles").getTextTrim();
        if (metaText.length() == 0) {
            final int res = 1;
            setEditingCycles(res);
            return res;
        } else {
            return Integer.parseInt(metaText);
        }
    }

    public final void setEditingCycles(final int s) {
        this.getMetaChild("editing-cycles").setText(s + "");
    }

    /**
     * Return the metadata with the passed name.
     * 
     * @param name the name of user metadata.
     * @return the requested metadata, or <code>null</code> if none is found.
     */
    public final ODUserDefinedMeta getUserMeta(String name) {
        return this.getUserMeta(name, false);
    }

    /**
     * Return the metadata with the passed name, optionnaly creating it.
     * 
     * @param name the name of user metadata.
     * @param create <code>true</code> if it should be created.
     * @return the requested metadata, or <code>null</code> if none is found and <code>create</code>
     *         is <code>false</code>.
     */
    public final ODUserDefinedMeta getUserMeta(String name, boolean create) {
        return this.getUserMeta(name, create ? -1 : null);
    }

    /**
     * Return the metadata with the passed name, optionnaly creating it.
     * 
     * @param name the name of user metadata.
     * @param index if and where it should be created, <code>null</code> means do not add.
     * @return the requested metadata, or <code>null</code> if none is found and <code>create</code>
     *         is <code>false</code>.
     */
    public final ODUserDefinedMeta getUserMeta(String name, Integer index) {
        final Element userElem = ODUserDefinedMeta.getElement(this.getElement(), name, this.getNS());
        if (userElem != null)
            return new ODUserDefinedMeta(userElem, this.getParent());
        else if (index != null) {
            final ODUserDefinedMeta res = ODUserDefinedMeta.create(name, this.getParent());
            // from office-meta-content-strict in the relaxNG we can add anywhere
            if (index == -1) {
                this.getElement().addContent(res.getElement());
            } else {
                @SuppressWarnings("unchecked")
                final List<Element> existingChildren = this.getElement().getChildren(res.getElement().getName(), res.getElement().getNamespace());
                existingChildren.add(index, res.getElement());
            }
            return res;
        } else
            return null;
    }

    public final void removeUserMeta(String name) {
        final ODUserDefinedMeta userMeta = this.getUserMeta(name, false);
        if (userMeta != null)
            userMeta.getElement().detach();
    }

    public final List<String> getUserMetaNames() throws JDOMException {
        return ODUserDefinedMeta.getNames(this.getElement(), this.getNS());
    }

    // * getChild

    public final Element getMetaChild(final String name) {
        return this.getChild(name, this.getNS().getMETA());
    }

    public final boolean removeMetaChild(final String name) {
        return this.getElement().removeChild(name, this.getNS().getMETA());
    }

    public final Element getDCChild(final String name) {
        return this.getChild(name, this.getNS().getNS("dc"));
    }

    private final Element getChild(final String name, final Namespace ns) {
        return this.getChild(name, ns, true);
    }

    protected final Element getChild(final String name, final Namespace ns, final boolean create) {
        return this.childCreator.getChild(ns, name, create);
    }

    private final Calendar getDateChild(final String name, final Namespace ns) {
        final String date = this.getChild(name, ns).getTextTrim();
        if (date.length() == 0)
            return null;
        else {
            try {
                return ODValueType.parseDateValue(date);
            } catch (ParseException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    private final void setDateChild(final Calendar cal, final String name, final Namespace ns) {
        this.getChild(name, ns).setText(ODValueType.DATE.format(cal));
    }

}
