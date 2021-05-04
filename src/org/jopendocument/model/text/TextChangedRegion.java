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
public class TextChangedRegion {

    protected String textId;
    protected List<Object> textInsertionOrTextDeletionOrTextFormatChange;
    protected String textMergeLastParagraph;

    /**
     * Gets the value of the textId property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextId() {
        return this.textId;
    }

    /**
     * Gets the value of the textInsertionOrTextDeletionOrTextFormatChange property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the
     * textInsertionOrTextDeletionOrTextFormatChange property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getTextInsertionOrTextDeletionOrTextFormatChange().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TextInsertion }
     * {@link TextDeletion } {@link TextFormatChange }
     * 
     * 
     */
    public List<Object> getTextInsertionOrTextDeletionOrTextFormatChange() {
        if (this.textInsertionOrTextDeletionOrTextFormatChange == null) {
            this.textInsertionOrTextDeletionOrTextFormatChange = new ArrayList<Object>();
        }
        return this.textInsertionOrTextDeletionOrTextFormatChange;
    }

    /**
     * Gets the value of the textMergeLastParagraph property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextMergeLastParagraph() {
        if (this.textMergeLastParagraph == null) {
            return "true";
        } else {
            return this.textMergeLastParagraph;
        }
    }

    /**
     * Sets the value of the textId property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextId(final String value) {
        this.textId = value;
    }

    /**
     * Sets the value of the textMergeLastParagraph property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextMergeLastParagraph(final String value) {
        this.textMergeLastParagraph = value;
    }

}
