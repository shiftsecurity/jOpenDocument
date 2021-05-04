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

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class PresentationSettings {

    protected String presentationAnimations;
    protected String presentationEndless;
    protected String presentationForceManual;
    protected String presentationFullScreen;
    protected String presentationMouseAsPen;
    protected String presentationMouseVisible;
    protected String presentationPause;
    protected List<PresentationShow> presentationShow;
    protected String presentationShowLogo;
    protected String presentationStartPage;
    protected String presentationStartWithNavigator;
    protected String presentationStayOnTop;
    protected String presentationTransitionOnClick;

    /**
     * Gets the value of the presentationAnimations property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPresentationAnimations() {
        if (this.presentationAnimations == null) {
            return "enabled";
        } else {
            return this.presentationAnimations;
        }
    }

    /**
     * Gets the value of the presentationEndless property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPresentationEndless() {
        if (this.presentationEndless == null) {
            return "false";
        } else {
            return this.presentationEndless;
        }
    }

    /**
     * Gets the value of the presentationForceManual property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPresentationForceManual() {
        if (this.presentationForceManual == null) {
            return "false";
        } else {
            return this.presentationForceManual;
        }
    }

    /**
     * Gets the value of the presentationFullScreen property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPresentationFullScreen() {
        if (this.presentationFullScreen == null) {
            return "true";
        } else {
            return this.presentationFullScreen;
        }
    }

    /**
     * Gets the value of the presentationMouseAsPen property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPresentationMouseAsPen() {
        if (this.presentationMouseAsPen == null) {
            return "false";
        } else {
            return this.presentationMouseAsPen;
        }
    }

    /**
     * Gets the value of the presentationMouseVisible property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPresentationMouseVisible() {
        if (this.presentationMouseVisible == null) {
            return "true";
        } else {
            return this.presentationMouseVisible;
        }
    }

    /**
     * Gets the value of the presentationPause property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPresentationPause() {
        return this.presentationPause;
    }

    /**
     * Gets the value of the presentationShow property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the presentationShow property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getPresentationShow().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link PresentationShow }
     * 
     * 
     */
    public List<PresentationShow> getPresentationShow() {
        if (this.presentationShow == null) {
            this.presentationShow = new ArrayList<PresentationShow>();
        }
        return this.presentationShow;
    }

    /**
     * Gets the value of the presentationShowLogo property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPresentationShowLogo() {
        if (this.presentationShowLogo == null) {
            return "false";
        } else {
            return this.presentationShowLogo;
        }
    }

    /**
     * Gets the value of the presentationStartPage property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPresentationStartPage() {
        return this.presentationStartPage;
    }

    /**
     * Gets the value of the presentationStartWithNavigator property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPresentationStartWithNavigator() {
        if (this.presentationStartWithNavigator == null) {
            return "false";
        } else {
            return this.presentationStartWithNavigator;
        }
    }

    /**
     * Gets the value of the presentationStayOnTop property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPresentationStayOnTop() {
        if (this.presentationStayOnTop == null) {
            return "false";
        } else {
            return this.presentationStayOnTop;
        }
    }

    /**
     * Gets the value of the presentationTransitionOnClick property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPresentationTransitionOnClick() {
        if (this.presentationTransitionOnClick == null) {
            return "enabled";
        } else {
            return this.presentationTransitionOnClick;
        }
    }

    /**
     * Sets the value of the presentationAnimations property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPresentationAnimations(final String value) {
        this.presentationAnimations = value;
    }

    /**
     * Sets the value of the presentationEndless property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPresentationEndless(final String value) {
        this.presentationEndless = value;
    }

    /**
     * Sets the value of the presentationForceManual property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPresentationForceManual(final String value) {
        this.presentationForceManual = value;
    }

    /**
     * Sets the value of the presentationFullScreen property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPresentationFullScreen(final String value) {
        this.presentationFullScreen = value;
    }

    /**
     * Sets the value of the presentationMouseAsPen property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPresentationMouseAsPen(final String value) {
        this.presentationMouseAsPen = value;
    }

    /**
     * Sets the value of the presentationMouseVisible property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPresentationMouseVisible(final String value) {
        this.presentationMouseVisible = value;
    }

    /**
     * Sets the value of the presentationPause property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPresentationPause(final String value) {
        this.presentationPause = value;
    }

    /**
     * Sets the value of the presentationShowLogo property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPresentationShowLogo(final String value) {
        this.presentationShowLogo = value;
    }

    /**
     * Sets the value of the presentationStartPage property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPresentationStartPage(final String value) {
        this.presentationStartPage = value;
    }

    /**
     * Sets the value of the presentationStartWithNavigator property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPresentationStartWithNavigator(final String value) {
        this.presentationStartWithNavigator = value;
    }

    /**
     * Sets the value of the presentationStayOnTop property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPresentationStayOnTop(final String value) {
        this.presentationStayOnTop = value;
    }

    /**
     * Sets the value of the presentationTransitionOnClick property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPresentationTransitionOnClick(final String value) {
        this.presentationTransitionOnClick = value;
    }

}
