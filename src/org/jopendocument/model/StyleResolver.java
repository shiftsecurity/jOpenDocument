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

package org.jopendocument.model;

import org.jopendocument.util.Tuple2;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jopendocument.model.style.StyleStyle;

public class StyleResolver {
    private final Map<Tuple2<String, String>, StyleStyle> styles = new HashMap<Tuple2<String, String>, StyleStyle>();

    public void add(StyleStyle style) {
        if (styles.containsKey(style.getStyleName())) {
            throw new IllegalArgumentException("Style: " + style + " already in the style resolver : " + styles.get(style.getStyleName()));
        }
        styles.put(Tuple2.create(style.getStyleName(), style.getStyleFamily()), style);
    }

    public StyleStyle getStyle(String name, String family) {
        return styles.get(Tuple2.create(name, family));
    }

    public void dump() {

        Collection<StyleStyle> all = styles.values();
        System.err.println("StyleResolver: " + all.size() + " styles");
        for (Iterator<StyleStyle> iterator = all.iterator(); iterator.hasNext();) {
            StyleStyle styleStyle = iterator.next();
            System.err.println(styleStyle);
        }
    }
}
