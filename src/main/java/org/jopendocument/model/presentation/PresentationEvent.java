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
public class PresentationEvent {

    protected String presentationAction;
    protected String presentationDirection;
    protected String presentationEffect;
    protected PresentationSound presentationSound;
    protected String presentationSpeed;
    protected String presentationStartScale;
    protected String presentationVerb;
    protected String scriptEventName;
    protected String xlinkActuate;
    protected String xlinkHref;
    protected String xlinkShow;
    protected String xlinkType;

    /**
     * Gets the value of the presentationAction property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPresentationAction() {
        return this.presentationAction;
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
     * Gets the value of the presentationVerb property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPresentationVerb() {
        return this.presentationVerb;
    }

    /**
     * Gets the value of the scriptEventName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getScriptEventName() {
        return this.scriptEventName;
    }

    /**
     * Gets the value of the xlinkActuate property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXlinkActuate() {
        return this.xlinkActuate;
    }

    /**
     * Gets the value of the xlinkHref property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXlinkHref() {
        return this.xlinkHref;
    }

    /**
     * Gets the value of the xlinkShow property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXlinkShow() {
        return this.xlinkShow;
    }

    /**
     * Gets the value of the xlinkType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXlinkType() {
        return this.xlinkType;
    }

    /**
     * Sets the value of the presentationAction property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPresentationAction(final String value) {
        this.presentationAction = value;
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

    /**
     * Sets the value of the presentationVerb property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPresentationVerb(final String value) {
        this.presentationVerb = value;
    }

    /**
     * Sets the value of the scriptEventName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setScriptEventName(final String value) {
        this.scriptEventName = value;
    }

    /**
     * Sets the value of the xlinkActuate property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setXlinkActuate(final String value) {
        this.xlinkActuate = value;
    }

    /**
     * Sets the value of the xlinkHref property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setXlinkHref(final String value) {
        this.xlinkHref = value;
    }

    /**
     * Sets the value of the xlinkShow property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setXlinkShow(final String value) {
        this.xlinkShow = value;
    }

    /**
     * Sets the value of the xlinkType property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setXlinkType(final String value) {
        this.xlinkType = value;
    }

}
