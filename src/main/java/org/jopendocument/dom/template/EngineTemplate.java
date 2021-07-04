/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 2008 jOpenDocument, by ILM Informatique. All rights reserved.
 * 
 * The contents of this file are subject to the terms of the GNU General Public License Version 3
 * only ("GPL"). You may not use this file except in compliance with the License. You can obtain a
 * copy of the License at http://www.gnu.org/licenses/gpl-3.0.html See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in each file.
 */

package org.jopendocument.dom.template;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.jdom.JDOMException;
import org.jopendocument.dom.ODSingleXMLDocument;
import org.jopendocument.dom.template.engine.DataModel;

public class EngineTemplate {
    private final Template template;
    private final DataModel engine;

    public EngineTemplate(final File f, DataModel engine) throws IOException, TemplateException, JDOMException {
        this.template = new Template(f);
        this.engine = engine;
    }

    public EngineTemplate(final InputStream i, DataModel engine) throws IOException, TemplateException, JDOMException {
        this.template = new Template(i);
        this.engine = engine;
    }

    public final void showParagraph(String name) {
        this.setField("showParagraph_" + name, Boolean.TRUE);
    }

    public final void hideParagraph(String name) {
        this.setField("showParagraph_" + name, Boolean.FALSE);
    }

    public final void showTable(String name) {
        this.setField("showTable_" + name, Boolean.TRUE);
    }

    public final void hideTable(String name) {
        this.setField("showTable_" + name, Boolean.FALSE);
    }

    public final void showTableRow(String name) {
        this.setField("showTableRow_" + name, Boolean.TRUE);
    }

    public final void hideTableRow(String name) {
        this.setField("showTableRow_" + name, Boolean.FALSE);
    }

    public final void showSection(String name) {
        this.setField("showSection_" + name, Boolean.TRUE);
    }

    public final void hideSection(String name) {
        this.setField("showSection_" + name, Boolean.FALSE);
    }

    public final void showListItem(String name) {
        this.setField("showListItem_" + name, Boolean.TRUE);
    }

    public final void hideListItem(String name) {
        this.setField("showListItem_" + name, Boolean.FALSE);
    }

    public void setField(String key, Object value) {
        this.engine.put(key, value);
    }

    public ODSingleXMLDocument createDocument() throws TemplateException {
        return this.template.createDocument(this.engine);
    }

    public File saveAs(File outFile) throws IOException, TemplateException {
        // Create the document
        ODSingleXMLDocument doc = this.createDocument();
        return doc.saveToPackageAs(outFile);
    }
}
