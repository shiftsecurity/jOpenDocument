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

package org.jopendocument.model.office;

import java.util.List;
import java.util.Vector;

import org.jopendocument.model.style.StyleDefaultStyle;
import org.jopendocument.model.style.StyleStyle;

/**
 * 
 */
public class OfficeStyles {

    private final List<StyleDefaultStyle> defaultStyles = new Vector<StyleDefaultStyle>();
    private final List<StyleStyle> styles = new Vector<StyleStyle>();

    public void addDefaultStyle(final StyleDefaultStyle defaultStyle) {
        this.defaultStyles.add(defaultStyle);
    }

    public void addStyle(final StyleStyle style) {
        this.styles.add(style);
    }

    public StyleDefaultStyle getDefaultCellStyle() {
        for (final StyleDefaultStyle s : this.defaultStyles) {
            if (s.getStyleFamily().equals("table-cell")) {
                return s;
            }
        }
        return null;
    }

}
