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
public class PresentationDim {

    protected String drawColor;
    protected String drawShapeId;
    protected PresentationSound presentationSound;

    /**
     * Gets the value of the drawColor property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDrawColor() {
        return this.drawColor;
    }

    /**
     * Gets the value of the drawShapeId property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDrawShapeId() {
        return this.drawShapeId;
    }

    /**
     * Gets the value of the presentationSound property.
     * 
     * @return possible object is {@link PresentationSound }
     * 
     */
    public PresentationSound getPresentationSound() {
        return this.presentationSound;
    }

    /**
     * Sets the value of the drawColor property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDrawColor(final String value) {
        this.drawColor = value;
    }

    /**
     * Sets the value of the drawShapeId property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDrawShapeId(final String value) {
        this.drawShapeId = value;
    }

    /**
     * Sets the value of the presentationSound property.
     * 
     * @param value allowed object is {@link PresentationSound }
     * 
     */
    public void setPresentationSound(final PresentationSound value) {
        this.presentationSound = value;
    }

}
