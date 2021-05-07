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

import org.jopendocument.util.ValueHelper;

public class StyleTableColumnProperties {

    private int columnWidth;
    private int reLcolumnWidth;

    public final int getColumnWidth() {
        return this.columnWidth;
    }

    public void setFoBreakBefore(final String value) {

    }

    public void setStyleColumnWidth(final String value) {
        this.columnWidth = ValueHelper.getLength(value);
    }

    public void setStyleRelColumnWidth(String value) {
        this.reLcolumnWidth = Integer.parseInt(value.replace('*', ' ').trim());

    }

}
