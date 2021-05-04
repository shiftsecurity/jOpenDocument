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

import org.jopendocument.dom.ODPackage.RootElement;
import org.jopendocument.util.SimpleXMLPath;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;

/**
 * Remove the time zone part of dates (fix for LibO >= 4.1).
 * 
 * @author Sylvain
 * @see ODValueType#isTimeZoneIgnored()
 */
public class FixTimeZone {

    public static void main(String[] args) throws Exception {
        // TODO if no arguments display a frame to drop files
        final File f = new File(args[0]);
        removeTimeZone(f);
    }

    public static void removeTimeZone(final File f) throws IOException, ParseException {
        final ODPackage pkg = ODPackage.createFromFile(f);
        final SimpleXMLPath<Attribute> allAttr = SimpleXMLPath.allAttributes(ODValueType.DATE.getValueAttribute(), pkg.getVersion().getOFFICE().getPrefix());
        int count = 0;
        for (final RootElement root : RootElement.values()) {
            final Document doc = pkg.getDocument(root.getZipEntry());
            if (doc != null) {
                for (final Attribute attr : allAttr.selectNodes(doc.getRootElement())) {
                    final String origVal = attr.getValue();
                    final String fixed = fixDate(origVal);
                    if (!fixed.equals(origVal)) {
                        attr.setValue(fixed);
                        count++;
                    }
                }
            }
        }
        final int attrCount = count;
        final ODMeta meta = pkg.getMeta(false);
        if (meta != null) {
            if (fixMetaElem(meta.getChild("creation-date", meta.getNS().getMETA(), false)))
                count++;
            if (fixMetaElem(meta.getChild("date", meta.getNS().getNS("dc"), false)))
                count++;
        }
        if (count > 0) {
            System.out.println("Fixed " + count + " (including " + (count - attrCount) + " in meta)");
            final File saved = pkg.saveAs(new File(f.getParentFile(), f.getName() + "-dateOK"));
            System.out.println("Saved " + saved);
        } else {
            System.out.println("Nothing to do");
        }
    }

    protected static boolean fixMetaElem(final Element dateElem) throws ParseException {
        boolean res = false;
        if (dateElem != null) {
            final String val = dateElem.getTextTrim();
            final String fixed = fixDate(val);
            if (!fixed.equals(val)) {
                dateElem.setText(fixed);
                res = true;
            }
        }
        return res;
    }

    protected static String fixDate(final String val) throws ParseException {
        return ODValueType.DATE.format(ODValueType.parseDateValue(val, null, null, true));
    }
}
