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
import org.jopendocument.dom.template.engine.RhinoTemplateScriptEngine;

/**
 * A template using <a href="https://developer.mozilla.org/en-US/docs/Rhino">Rhino</a>.
 * 
 * @author Sylvain
 * @see JavaScriptTemplate
 */
public class RhinoTemplate extends EngineTemplate {

    public RhinoTemplate(String fileName) throws IOException, TemplateException, JDOMException {
        this(new File(fileName));
    }

    public RhinoTemplate(File f) throws IOException, TemplateException, JDOMException {
        super(f, new RhinoTemplateScriptEngine());
    }

    public RhinoTemplate(InputStream s) throws IOException, TemplateException, JDOMException {
        super(s, new RhinoTemplateScriptEngine());
    }
}
