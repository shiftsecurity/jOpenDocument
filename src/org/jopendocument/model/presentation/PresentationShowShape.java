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
public class PresentationShowShape {

    protected String drawShapeId;
    protected String presentationDirection;
    protected String presentationEffect;
    protected String presentationPathId;
    protected PresentationSound presentationSound;
    protected String presentationSpeed;
    protected String presentationStartScale;

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
     * Gets the value of the presentationDirection property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPresentationDirection() {
        if (this.presentationDirection == null) {
            return "none";
        } else {
            return this.presentationDirection;
        }
    }

    /**
     * Gets the value of the presentationEffect property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPresentationEffect() {
        if (this.presentationEffect == null) {
            return "none";
        } else {
            return this.presentationEffect;
        }
    }

    /**
     * Gets the value of the presentationPathId property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPresentationPathId() {
        return this.presentationPathId;
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
     * Gets the value of the presentationSpeed property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPresentationSpeed() {
        if (this.presentationSpeed == null) {
            return "medium";
        } else {
            return this.presentationSpeed;
        }
    }

    /**
     * Gets the value of the presentationStartScale property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPresentationStartScale() {
        if (this.presentationStartScale == null) {
            return "100%";
        } else {
            return this.presentationStartScale;
        }
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
     * Sets the value of the presentationDirection property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPresentationDirection(final String value) {
        this.presentationDirection = value;
    }

    /**
     * Sets the value of the presentationEffect property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPresentationEffect(final String value) {
        this.presentationEffect = value;
    }

    /**
     * Sets the value of the presentationPathId property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPresentationPathId(final String value) {
        this.presentationPathId = value;
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

    /**
     * Sets the value of the presentationSpeed property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPresentationSpeed(final String value) {
        this.presentationSpeed = value;
    }

    /**
     * Sets the value of the presentationStartScale property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPresentationStartScale(final String value) {
        this.presentationStartScale = value;
    }

}
