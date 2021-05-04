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
import org.jopendocument.dom.template.engine.Processor;

import org.jdom.Element;

public class SetStmt extends BaseStatement {
    public SetStmt() {
        super("set");
    }

    @SuppressWarnings("unchecked")
    public void prepare(Element script, Element command) throws TemplateException {
        String var = command.getAttributeValue("var");
        if (var == null) {
            throw new TemplateException("missing required attribute \"var\" for \"set\" tag");
        }
        String value = command.getAttributeValue("value");
        if (value == null) {
            throw new TemplateException("missing required attribute \"value\" for \"set\" tag");
        }
        Element parent = script.getParentElement();
        final Element setTag = getElement(this.getName()).setAttribute("var", var).setAttribute("value", value);
        int index = parent.indexOf(script);
        parent.getContent().add(index, setTag);
        script.detach();
    }

    public void execute(Processor<?> processor, Element tag, DataModel model) throws TemplateException {
        String var = tag.getAttributeValue("var");
        String valueExpression = tag.getAttributeValue("value");
        model.put(var, model.eval(valueExpression));
        tag.detach();
    }

}
