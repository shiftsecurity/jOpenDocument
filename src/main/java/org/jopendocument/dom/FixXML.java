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

import org.jopendocument.util.SimpleXMLPath;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom.Attribute;

public class FixXML {

    public static void main(String[] args) throws IOException {
        final File f = new File(args[0]);
        final ODPackage pkg = ODPackage.createFromFile(f);
        final String generator = pkg.getMeta().getGenerator();
        // LibreOffice incorrectly generates empty attributes
        if (generator.startsWith("LibreOffice/3.4") || generator.startsWith("LibreOffice/3.5")) {
            final List<Attribute> attrs = SimpleXMLPath.allAttributes("text-position", "style").selectNodes(pkg.getContent().getDocument().getRootElement());
            boolean modified = false;
            for (final Attribute attr : attrs) {
                if (attr.getValue().length() == 0) {
                    attr.detach();
                    modified = true;
                }
            }
            if (modified) {
                final File saved = pkg.saveAs(new File(f.getParentFile(), f.getName() + "-fixed"));
                System.out.println("Saved " + saved);
            } else {
                System.out.println("Nothing to do");
            }
        }
    }

}
