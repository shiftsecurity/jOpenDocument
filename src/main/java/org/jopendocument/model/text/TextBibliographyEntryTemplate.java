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

package org.jopendocument.model.text;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class TextBibliographyEntryTemplate {

    protected String textBibliographyType;
    protected List<Object> textIndexEntrySpanOrTextIndexEntryTabStopOrTextIndexEntryBibliography;
    protected String textStyleName;

    /**
     * Gets the value of the textBibliographyType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextBibliographyType() {
        return this.textBibliographyType;
    }

    /**
     * Gets the value of the textIndexEntrySpanOrTextIndexEntryTabStopOrTextIndexEntryBibliography
     * property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the
     * textIndexEntrySpanOrTextIndexEntryTabStopOrTextIndexEntryBibliography property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getTextIndexEntrySpanOrTextIndexEntryTabStopOrTextIndexEntryBibliography().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TextIndexEntrySpan }
     * {@link TextIndexEntryTabStop } {@link TextIndexEntryBibliography }
     * 
     * 
     */
    public List<Object> getTextIndexEntrySpanOrTextIndexEntryTabStopOrTextIndexEntryBibliography() {
        if (this.textIndexEntrySpanOrTextIndexEntryTabStopOrTextIndexEntryBibliography == null) {
            this.textIndexEntrySpanOrTextIndexEntryTabStopOrTextIndexEntryBibliography = new ArrayList<Object>();
        }
        return this.textIndexEntrySpanOrTextIndexEntryTabStopOrTextIndexEntryBibliography;
    }

    /**
     * Gets the value of the textStyleName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextStyleName() {
        return this.textStyleName;
    }

    /**
     * Sets the value of the textBibliographyType property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextBibliographyType(final String value) {
        this.textBibliographyType = value;
    }

    /**
     * Sets the value of the textStyleName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextStyleName(final String value) {
        this.textStyleName = value;
    }

}
