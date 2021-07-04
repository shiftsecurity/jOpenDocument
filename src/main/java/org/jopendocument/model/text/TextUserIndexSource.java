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
public class TextUserIndexSource {

    protected String foCountry;
    protected String foLanguage;
    protected String textCopyOutlineLevels;
    protected String textIndexName;
    protected String textIndexScope;
    protected List<TextIndexSourceStyles> textIndexSourceStyles;
    protected TextIndexTitleTemplate textIndexTitleTemplate;
    protected String textRelativeTabStopPosition;
    protected String textSortAlgorithm;
    protected String textUseFloatingFrames;
    protected String textUseGraphics;
    protected String textUseIndexMarks;
    protected String textUseIndexSourceStyles;
    protected String textUseObjects;
    protected List<TextUserIndexEntryTemplate> textUserIndexEntryTemplate;
    protected String textUseTables;

    /**
     * Gets the value of the foCountry property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFoCountry() {
        return this.foCountry;
    }

    /**
     * Gets the value of the foLanguage property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFoLanguage() {
        return this.foLanguage;
    }

    /**
     * Gets the value of the textCopyOutlineLevels property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextCopyOutlineLevels() {
        if (this.textCopyOutlineLevels == null) {
            return "false";
        } else {
            return this.textCopyOutlineLevels;
        }
    }

    /**
     * Gets the value of the textIndexName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextIndexName() {
        return this.textIndexName;
    }

    /**
     * Gets the value of the textIndexScope property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextIndexScope() {
        if (this.textIndexScope == null) {
            return "document";
        } else {
            return this.textIndexScope;
        }
    }

    /**
     * Gets the value of the textIndexSourceStyles property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the textIndexSourceStyles property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getTextIndexSourceStyles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TextIndexSourceStyles }
     * 
     * 
     */
    public List<TextIndexSourceStyles> getTextIndexSourceStyles() {
        if (this.textIndexSourceStyles == null) {
            this.textIndexSourceStyles = new ArrayList<TextIndexSourceStyles>();
        }
        return this.textIndexSourceStyles;
    }

    /**
     * Gets the value of the textIndexTitleTemplate property.
     * 
     * @return possible object is {@link TextIndexTitleTemplate }
     * 
     */
    public TextIndexTitleTemplate getTextIndexTitleTemplate() {
        return this.textIndexTitleTemplate;
    }

    /**
     * Gets the value of the textRelativeTabStopPosition property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextRelativeTabStopPosition() {
        if (this.textRelativeTabStopPosition == null) {
            return "true";
        } else {
            return this.textRelativeTabStopPosition;
        }
    }

    /**
     * Gets the value of the textSortAlgorithm property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextSortAlgorithm() {
        return this.textSortAlgorithm;
    }

    /**
     * Gets the value of the textUseFloatingFrames property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextUseFloatingFrames() {
        if (this.textUseFloatingFrames == null) {
            return "false";
        } else {
            return this.textUseFloatingFrames;
        }
    }

    /**
     * Gets the value of the textUseGraphics property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextUseGraphics() {
        if (this.textUseGraphics == null) {
            return "false";
        } else {
            return this.textUseGraphics;
        }
    }

    /**
     * Gets the value of the textUseIndexMarks property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextUseIndexMarks() {
        if (this.textUseIndexMarks == null) {
            return "false";
        } else {
            return this.textUseIndexMarks;
        }
    }

    /**
     * Gets the value of the textUseIndexSourceStyles property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextUseIndexSourceStyles() {
        if (this.textUseIndexSourceStyles == null) {
            return "false";
        } else {
            return this.textUseIndexSourceStyles;
        }
    }

    /**
     * Gets the value of the textUseObjects property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextUseObjects() {
        if (this.textUseObjects == null) {
            return "false";
        } else {
            return this.textUseObjects;
        }
    }

    /**
     * Gets the value of the textUserIndexEntryTemplate property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the textUserIndexEntryTemplate property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getTextUserIndexEntryTemplate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TextUserIndexEntryTemplate }
     * 
     * 
     */
    public List<TextUserIndexEntryTemplate> getTextUserIndexEntryTemplate() {
        if (this.textUserIndexEntryTemplate == null) {
            this.textUserIndexEntryTemplate = new ArrayList<TextUserIndexEntryTemplate>();
        }
        return this.textUserIndexEntryTemplate;
    }

    /**
     * Gets the value of the textUseTables property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextUseTables() {
        if (this.textUseTables == null) {
            return "false";
        } else {
            return this.textUseTables;
        }
    }

    /**
     * Sets the value of the foCountry property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFoCountry(final String value) {
        this.foCountry = value;
    }

    /**
     * Sets the value of the foLanguage property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFoLanguage(final String value) {
        this.foLanguage = value;
    }

    /**
     * Sets the value of the textCopyOutlineLevels property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextCopyOutlineLevels(final String value) {
        this.textCopyOutlineLevels = value;
    }

    /**
     * Sets the value of the textIndexName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextIndexName(final String value) {
        this.textIndexName = value;
    }

    /**
     * Sets the value of the textIndexScope property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextIndexScope(final String value) {
        this.textIndexScope = value;
    }

    /**
     * Sets the value of the textIndexTitleTemplate property.
     * 
     * @param value allowed object is {@link TextIndexTitleTemplate }
     * 
     */
    public void setTextIndexTitleTemplate(final TextIndexTitleTemplate value) {
        this.textIndexTitleTemplate = value;
    }

    /**
     * Sets the value of the textRelativeTabStopPosition property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextRelativeTabStopPosition(final String value) {
        this.textRelativeTabStopPosition = value;
    }

    /**
     * Sets the value of the textSortAlgorithm property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextSortAlgorithm(final String value) {
        this.textSortAlgorithm = value;
    }

    /**
     * Sets the value of the textUseFloatingFrames property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextUseFloatingFrames(final String value) {
        this.textUseFloatingFrames = value;
    }

    /**
     * Sets the value of the textUseGraphics property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextUseGraphics(final String value) {
        this.textUseGraphics = value;
    }

    /**
     * Sets the value of the textUseIndexMarks property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextUseIndexMarks(final String value) {
        this.textUseIndexMarks = value;
    }

    /**
     * Sets the value of the textUseIndexSourceStyles property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextUseIndexSourceStyles(final String value) {
        this.textUseIndexSourceStyles = value;
    }

    /**
     * Sets the value of the textUseObjects property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextUseObjects(final String value) {
        this.textUseObjects = value;
    }

    /**
     * Sets the value of the textUseTables property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextUseTables(final String value) {
        this.textUseTables = value;
    }

}
