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
public class StyleColumns {

    protected String foColumnCount;
    protected String foColumnGap;
    protected List<StyleColumn> styleColumn;
    protected StyleColumnSep styleColumnSep;

    /**
     * Gets the value of the foColumnCount property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFoColumnCount() {
        return this.foColumnCount;
    }

    /**
     * Gets the value of the foColumnGap property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFoColumnGap() {
        return this.foColumnGap;
    }

    /**
     * Gets the value of the styleColumn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the styleColumn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getStyleColumn().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link StyleColumn }
     * 
     * 
     */
    public List<StyleColumn> getStyleColumn() {
        if (this.styleColumn == null) {
            this.styleColumn = new ArrayList<StyleColumn>();
        }
        return this.styleColumn;
    }

    /**
     * Gets the value of the styleColumnSep property.
     * 
     * @return possible object is {@link StyleColumnSep }
     * 
     */
    public StyleColumnSep getStyleColumnSep() {
        return this.styleColumnSep;
    }

    /**
     * Sets the value of the foColumnCount property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFoColumnCount(final String value) {
        this.foColumnCount = value;
    }

    /**
     * Sets the value of the foColumnGap property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFoColumnGap(final String value) {
        this.foColumnGap = value;
    }

    /**
     * Sets the value of the styleColumnSep property.
     * 
     * @param value allowed object is {@link StyleColumnSep }
     * 
     */
    public void setStyleColumnSep(final StyleColumnSep value) {
        this.styleColumnSep = value;
    }

}
