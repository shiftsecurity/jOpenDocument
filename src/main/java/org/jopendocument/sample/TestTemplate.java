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

package org.jopendocument.sample;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.template.RhinoTemplate;
import org.jopendocument.dom.template.EngineTemplate;

public class TestTemplate {

    public static void main(String[] args) {
        try {
            File templateFile = new File("template/test.odt");
            File outFile = new File("out.odt");
            // Load the template.
            EngineTemplate template = new RhinoTemplate(templateFile);

            // Fill with sample values.
            template.setField("toto", "value set using setField()");
            final List<Map<String, String>> months = new ArrayList<Map<String, String>>();
            months.add(createMap("January", "-12", "3"));
            months.add(createMap("February", "-8", "5"));
            months.add(createMap("March", "-5", "12"));
            months.add(createMap("April", "-1", "15"));
            months.add(createMap("May", "3", "21"));
            template.setField("months", months);

            template.hideParagraph("p1");
            template.hideSection("section1");

            // Save to file.
            template.saveAs(outFile);

            // Open the document with OpenOffice.org !
            OOUtils.open(outFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<String, String> createMap(String n, String min, String max) {
        final Map<String, String> res = new HashMap<String, String>();
        res.put("name", n);
        res.put("min", min);
        res.put("max", max);
        return res;
    }

}
