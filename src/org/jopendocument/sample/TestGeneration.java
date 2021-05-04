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

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.UIManager;

import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.template.EngineTemplate;
import org.jopendocument.dom.template.RhinoTemplate;

public class TestGeneration {

    public static void main(String[] args) {

        // new FileTemplate(new File("WWF-Kurseinladung.odt")).createDocument(new
        // HashMap()).saveAs(new File("out"));

        // Set the platform L&F.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Load

        FileDialog dialog = new FileDialog(new Frame(), "Open", FileDialog.LOAD);
        dialog.setFile("*.odt");
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        String file = dialog.getFile();
        if (file != null) {
            if (dialog.getDirectory() != null) {
                file = dialog.getDirectory() + System.getProperty("file.separator") + file;
            }

            try {
                File templateFile = new File(file);
                File outFile = new File("out2.odt");
                EngineTemplate template = new RhinoTemplate(templateFile);

                template.setField("title", "title");
                template.setField("toto", "toto");
                template.setField("courseDate", "courseDate");

                final List<Map<String, String>> infos = new ArrayList<Map<String, String>>();
                infos.add(createMap("January", "-12"));
                infos.add(createMap("February", "-8"));
                infos.add(createMap("March", "-5"));
                template.setField("infos", infos);
                template.setField("months", infos);
                template.hideParagraph("p1");
                // Save to file.
                outFile = template.saveAs(outFile);

                // Open the document with OpenOffice.org !
                OOUtils.open(outFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static Map<String, String> createMap(String n, String m) {
        final Map<String, String> res = new HashMap<String, String>();
        res.put("name", n);
        res.put("value", m);

        return res;
    }
}
