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
import org.jopendocument.util.JDOMUtils;

import org.jdom.Element;
import org.jdom.Namespace;

public abstract class BaseStatement extends Statement {

    private static String scriptLang = "JODScript";

    public static void setScriptLang(String scriptLang) {
        BaseStatement.scriptLang = scriptLang;
    }

    public BaseStatement(final String name) {
        super(name);
    }

    @Override
    public boolean matches(Element elem) {
        return elem.getName().equals("script") && elem.getNamespacePrefix().equals("text") && elem.getAttributeValue("language", elem.getNamespace("script")).equals(scriptLang)
                && elem.getText().trim().startsWith("<" + this.getName());
    }

    public void prepare(Element scriptElem) throws TemplateException {
        prepare(scriptElem, this.parseScript(scriptElem.getText()));
    }

    protected abstract void prepare(Element script, Element command) throws TemplateException;

    private Element parseScript(String text) throws TemplateException {
        Element command = null;
        try {
            command = JDOMUtils.parseElementString(text, new Namespace[0]);
        } catch (Exception documentException) {
            throw new TemplateException("invalid script: " + text, documentException);
        }
        return command;
    }

    // *** static

    /**
     * If elem is an OO section, remove it and replace it by its children.
     * 
     * @param elem the element to test.
     */
    protected static void removeSection(Element elem) {
        if (elem.getQualifiedName().equals("text:section")) {
            final boolean sectionSrc = elem.removeChild("section-source", elem.getNamespace("text"));
            final boolean ddeSrc = elem.removeChild("dde-source", elem.getNamespace("office"));
            // see 5.4 in OpenDocument-v1.2-part1-cd04
            assert !(sectionSrc && ddeSrc);
            pullUp(elem);
        }
    }

}
