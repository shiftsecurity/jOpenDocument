/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 2008 jOpenDocument, by ILM Informatique. All rights reserved.
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

package org.jopendocument.sample;

import java.io.File;

import org.jopendocument.dom.ODSingleXMLDocument;
import org.jopendocument.dom.OOUtils;

public class TextCat {

    public static void main(String[] args) {
        try {
            // Load 2 text documents
            File file1 = new File("template/ooo2flyer_p1.odt");
            ODSingleXMLDocument p1 = ODSingleXMLDocument.createFromPackage(file1);

            File file2 = new File("template/ooo2flyer_p2.odt");
            ODSingleXMLDocument p2 = ODSingleXMLDocument.createFromPackage(file2);

            // Concatenate them
            p1.add(p2);

            // Save to file and Open the document with OpenOffice.org !
            OOUtils.open(p1.saveToPackageAs(new File("cat")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
