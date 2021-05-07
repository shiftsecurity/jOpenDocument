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

package org.jopendocument.model.presentation;

/**
 * 
 */
public class PresentationShow {

    protected String presentationName;
    protected String presentationPages;

    /**
     * Gets the value of the presentationName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPresentationName() {
        return this.presentationName;
    }

    /**
     * Gets the value of the presentationPages property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPresentationPages() {
        return this.presentationPages;
    }

    /**
     * Sets the value of the presentationName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPresentationName(final String value) {
        this.presentationName = value;
    }

    /**
     * Sets the value of the presentationPages property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPresentationPages(final String value) {
        this.presentationPages = value;
    }

}
