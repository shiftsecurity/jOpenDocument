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

package org.jopendocument.dom.template;

import org.jopendocument.dom.template.engine.DataModel;
import org.jopendocument.dom.template.engine.Material;
import org.jopendocument.dom.template.engine.Parsed;
import org.jopendocument.dom.ODPackage;
import org.jopendocument.dom.ODSingleXMLDocument;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Generates documents merging template and data. <br>
 * <br>
 * Sample usage:
 * 
 * <pre>
 * Template template = new Template(new FileInputStream(&quot;template.sxw&quot;));
 * Map vars = new HashMap();
 * vars.put(&quot;title&quot;, &quot;The Title&quot;);
 * //...
 * template.createDocument(vars, new FileOutputStream(&quot;document.sxw&quot;));
 * </pre>
 */
public class Template {

    protected final Parsed<ODPackage> contentTemplate;

    /**
     * Loads a template from the specified input stream.
     * 
     * @param in a stream on a ODF package.
     * @throws IOException if the stream can't be read.
     * @throws TemplateException if the template statements are invalid.
     */
    public Template(InputStream in) throws IOException, TemplateException {
        this(new ODPackage(in));
    }

    public Template(File f) throws IOException, TemplateException {
        this(new ODPackage(f));
    }

    public Template(final ODPackage contents) throws IOException, TemplateException {
        // createDocument needs ODSingleXMLDocument
        contents.toSingle();
        this.contentTemplate = new Parsed<ODPackage>(Material.from(contents));
    }

    /**
     * Generates a document merging template and data.
     * 
     * @param engine the data model.
     * @param out stream the document is written to.
     * @throws IOException si erreur de zippage.
     * @throws TemplateException
     */
    public void createDocument(DataModel engine, OutputStream out) throws IOException, TemplateException {
        this.execute(engine).save(out);
    }

    public ODSingleXMLDocument createDocument(DataModel engine) throws TemplateException {
        return (ODSingleXMLDocument) execute(engine).getContent();
    }

    private ODPackage execute(DataModel engine) throws TemplateException {
        return this.contentTemplate.execute(engine);
    }
}