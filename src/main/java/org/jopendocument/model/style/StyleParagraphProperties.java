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

package org.jopendocument.model.style;

import java.util.ArrayList;
import java.util.List;

public class StyleParagraphProperties {

    private String marginLeft;

    private String textAlign;
    private List<StyleTabStops> tabStops;

    public String getMarginLeft() {
        return this.marginLeft;
    }

    public String getTextAlign() {
        return this.textAlign;
    }

    public void setMarginLeft(final String value) {
        this.marginLeft = value;

    }

    public void setTextAlign(final String value) {
        this.textAlign = value;

    }

    public void addTabStops(StyleTabStops p) {
        if (tabStops == null) {
            tabStops = new ArrayList<StyleTabStops>();
        }
        tabStops.add(p);

    }

}
