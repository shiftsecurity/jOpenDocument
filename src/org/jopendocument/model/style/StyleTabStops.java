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

/**
 * 
 */
public class StyleTabStops {

    protected List<StyleTabStop> styleTabStop = new ArrayList<StyleTabStop>();

    /**
     * Gets the value of the styleTabStop property.
     * 
     */
    public List<StyleTabStop> getStyleTabStop() {
        return this.styleTabStop;
    }

    public void add(StyleTabStop ts) {
        this.styleTabStop.add(ts);
    }

}
