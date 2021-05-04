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
public class TextUserIndexEntryTemplate {

    protected List<Object> textIndexEntryChapterOrTextIndexEntryPageNumberOrTextIndexEntryTextOrTextIndexEntrySpanOrTextIndexEntryTabStop;
    protected String textOutlineLevel;
    protected String textStyleName;

    /**
     * Gets the value of the
     * textIndexEntryChapterOrTextIndexEntryPageNumberOrTextIndexEntryTextOrTextIndexEntrySpanOrTextIndexEntryTabStop
     * property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the
     * textIndexEntryChapterOrTextIndexEntryPageNumberOrTextIndexEntryTextOrTextIndexEntrySpanOrTextIndexEntryTabStop
     * property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getTextIndexEntryChapterOrTextIndexEntryPageNumberOrTextIndexEntryTextOrTextIndexEntrySpanOrTextIndexEntryTabStop().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TextIndexEntryChapter }
     * {@link TextIndexEntryPageNumber } {@link TextIndexEntryText } {@link TextIndexEntrySpan }
     * {@link TextIndexEntryTabStop }
     * 
     * 
     */
    public List<Object> getTextIndexEntryChapterOrTextIndexEntryPageNumberOrTextIndexEntryTextOrTextIndexEntrySpanOrTextIndexEntryTabStop() {
        if (this.textIndexEntryChapterOrTextIndexEntryPageNumberOrTextIndexEntryTextOrTextIndexEntrySpanOrTextIndexEntryTabStop == null) {
            this.textIndexEntryChapterOrTextIndexEntryPageNumberOrTextIndexEntryTextOrTextIndexEntrySpanOrTextIndexEntryTabStop = new ArrayList<Object>();
        }
        return this.textIndexEntryChapterOrTextIndexEntryPageNumberOrTextIndexEntryTextOrTextIndexEntrySpanOrTextIndexEntryTabStop;
    }

    /**
     * Gets the value of the textOutlineLevel property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextOutlineLevel() {
        return this.textOutlineLevel;
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
     * Sets the value of the textOutlineLevel property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextOutlineLevel(final String value) {
        this.textOutlineLevel = value;
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
