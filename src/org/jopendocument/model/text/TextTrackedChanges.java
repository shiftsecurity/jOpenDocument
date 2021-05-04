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
public class TextTrackedChanges {

    protected List<TextChangedRegion> textChangedRegion;
    protected String textProtectionKey;
    protected String textTrackChanges;

    /**
     * Gets the value of the textChangedRegion property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the textChangedRegion property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getTextChangedRegion().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TextChangedRegion }
     * 
     * 
     */
    public List<TextChangedRegion> getTextChangedRegion() {
        if (this.textChangedRegion == null) {
            this.textChangedRegion = new ArrayList<TextChangedRegion>();
        }
        return this.textChangedRegion;
    }

    /**
     * Gets the value of the textProtectionKey property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextProtectionKey() {
        return this.textProtectionKey;
    }

    /**
     * Gets the value of the textTrackChanges property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextTrackChanges() {
        if (this.textTrackChanges == null) {
            return "true";
        } else {
            return this.textTrackChanges;
        }
    }

    /**
     * Sets the value of the textProtectionKey property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextProtectionKey(final String value) {
        this.textProtectionKey = value;
    }

    /**
     * Sets the value of the textTrackChanges property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextTrackChanges(final String value) {
        this.textTrackChanges = value;
    }

}
