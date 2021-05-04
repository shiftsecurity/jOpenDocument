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

package org.jopendocument.dom.template.statements;

import org.jopendocument.dom.template.TemplateException;
import org.jopendocument.dom.template.engine.DataModel;
import org.jopendocument.dom.template.engine.Material;
import org.jopendocument.dom.template.engine.Parsed;
import org.jopendocument.dom.template.engine.Processor;
import org.jopendocument.dom.ODPackage;
import org.jopendocument.dom.ODSingleXMLDocument;
import org.jopendocument.util.ExceptionUtils;
import org.jopendocument.util.cache.CacheResult;
import org.jopendocument.util.cache.ICache;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

public class Include extends Statement {

    private static final String PREFIX = "[IOD";

    private final ICache<File, ODSingleXMLDocument, File> cache;
    private final ICache<String, Parsed<ODSingleXMLDocument>, File> parsedCache;

    public Include() {
        super("include");
        this.cache = new ICache<File, ODSingleXMLDocument, File>(180);
        this.parsedCache = new ICache<String, Parsed<ODSingleXMLDocument>, File>(180);
    }

    public boolean matches(Element elem) {
        if (!elem.getQualifiedName().equals("text:a"))
            return false;
        final String name = getName(elem);
        // see 5.1.4 Hyperlinks § Name
        return name != null && name.startsWith(PREFIX);
    }

    private static String getName(Element elem) {
        return elem.getAttributeValue("name", elem.getNamespace("office"));
    }

    public void prepare(Element script) {
        // ../file.odt#source|region
        final String href = script.getAttributeValue("href", script.getNamespace("xlink"));

        final int sharp = href.indexOf('#');
        final String path = href.substring(0, sharp);
        // oo sometimes encodes the pipe, sometimes it doesn't
        final int lastIndex;
        final int pipe = href.lastIndexOf('|');
        if (pipe < 0)
            lastIndex = href.lastIndexOf("%7C");
        else
            lastIndex = pipe;
        final String section = href.substring(sharp + 1, lastIndex);

        final String name = getName(script);
        final String paramS = name.substring(PREFIX.length(), name.lastIndexOf(']')).trim();
        final Element param = paramS.length() > 0 ? new Element("param").setText(paramS) : null;

        script.removeContent();
        script.getAttributes().clear();
        script.setName(this.getName());
        script.setNamespace(stmtNS);
        script.setAttribute("path", path);
        script.setAttribute("section", section);
        if (param != null)
            script.addContent(param);
    }

    public void execute(Processor<?> processor, Element tag, DataModel model) throws TemplateException {
        if (processor.getMaterial().getBase() == null)
            throw new TemplateException("no base file for " + processor.getMaterial());

        try {
            // relative to the file, so use getCanonicalFile to remove ..
            final String relativePath = tag.getAttributeValue("path");
            final File ref = new File(processor.getMaterial().getBase(), relativePath).getCanonicalFile();
            final Parsed<ODSingleXMLDocument> parsed = getParsed(ref, tag.getAttributeValue("section"), processor.getParsed());
            final ODSingleXMLDocument docExecuted = parsed.execute(this.getModel(model, tag));

            // replace enclosing text:p
            final Object whole = processor.getMaterial().getWhole();
            final ODSingleXMLDocument single;
            if (whole instanceof ODPackage)
                single = (ODSingleXMLDocument) ((ODPackage) whole).getContent();
            else
                single = (ODSingleXMLDocument) whole;
            single.replace(getAncestorByName(tag, "p"), docExecuted);
        } catch (IOException e) {
            throw ExceptionUtils.createExn(TemplateException.class, "", e);
        } catch (JDOMException e) {
            throw ExceptionUtils.createExn(TemplateException.class, "", e);
        }
        tag.detach();
    }

    private Parsed<ODSingleXMLDocument> getParsed(final File ref, final String sectionName, final Parsed<?> parsed) throws JDOMException, IOException, TemplateException {
        final Parsed<ODSingleXMLDocument> res;
        final String key = ref.getPath() + "#" + sectionName;
        final CacheResult<Parsed<ODSingleXMLDocument>> cached = this.parsedCache.check(key);
        if (cached.getState() == CacheResult.State.NOT_IN_CACHE) {
            res = this.createParsed(ref, sectionName, parsed);
            this.parsedCache.put(key, res, Collections.<File> emptySet());
        } else {
            res = cached.getRes();
        }
        return res;
    }

    private Parsed<ODSingleXMLDocument> createParsed(final File ref, final String sectionName, final Parsed<?> parsed) throws JDOMException, IOException, TemplateException {
        final ODSingleXMLDocument docToAdd = getXMLDocument(ref).clone();

        final XPath sectionXP = docToAdd.getXPath("//text:section[@text:name = '" + sectionName + "']");
        final Element section = (Element) sectionXP.selectSingleNode(docToAdd.getDocument());
        // ajouter la section car souvent des if s'y réfèrent.
        docToAdd.getBody().setContent(section.detach());

        final Material<ODSingleXMLDocument> from = Material.from(docToAdd);
        from.setBase(ref);
        return new Parsed<ODSingleXMLDocument>(from, parsed);
    }

    private ODSingleXMLDocument getXMLDocument(final File ref) throws JDOMException, IOException {
        final ODSingleXMLDocument res;
        final CacheResult<ODSingleXMLDocument> cached = this.cache.check(ref);
        if (cached.getState() == CacheResult.State.NOT_IN_CACHE) {
            res = ODSingleXMLDocument.createFromPackage(ref);
            this.cache.put(ref, res, Collections.<File> emptySet());
        } else {
            res = cached.getRes();
        }
        return res;
    }

    // creates a new model if tag has parameters
    private DataModel getModel(final DataModel dm, final Element tag) throws TemplateException {
        final DataModel res;
        final Element param = tag.getChild("param");
        if (param != null) {
            res = dm.copy();
            // evaluate
            res.eval(param.getText());
        } else
            res = dm;
        return res;
    }
}