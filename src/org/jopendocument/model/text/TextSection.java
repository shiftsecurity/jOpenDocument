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

import org.jopendocument.model.office.OfficeDdeSource;
import org.jopendocument.model.table.TableTable;

/**
 * 
 */
public class TextSection {

    protected String textCondition;
    protected String textDisplay;
    protected List<Object> textHOrTextPOrTextOrderedListOrTextUnorderedListOrTableTableOrTextSectionOrTextTableOfContentOrTextIllustrationIndexOrTextTableIndexOrTextObjectIndexOrTextUserIndexOrTextAlphabeticalIndexOrTextBibliographyOrTextIndexTitleOrTextChangeOrTextChangeStartOrTextChangeEnd;
    protected String textIsHidden;
    protected String textName;
    protected String textProtected;
    protected String textProtectionKey;
    protected List<Object> textSectionSourceOrOfficeDdeSource;
    protected String textStyleName;

    /**
     * Gets the value of the textCondition property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextCondition() {
        return this.textCondition;
    }

    /**
     * Gets the value of the textDisplay property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextDisplay() {
        if (this.textDisplay == null) {
            return "true";
        } else {
            return this.textDisplay;
        }
    }

    /**
     * Gets the value of the
     * textHOrTextPOrTextOrderedListOrTextUnorderedListOrTableTableOrTextSectionOrTextTableOfContentOrTextIllustrationIndexOrTextTableIndexOrTextObjectIndexOrTextUserIndexOrTextAlphabeticalIndexOrTextBibliographyOrTextIndexTitleOrTextChangeOrTextChangeStartOrTextChangeEnd
     * property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the
     * textHOrTextPOrTextOrderedListOrTextUnorderedListOrTableTableOrTextSectionOrTextTableOfContentOrTextIllustrationIndexOrTextTableIndexOrTextObjectIndexOrTextUserIndexOrTextAlphabeticalIndexOrTextBibliographyOrTextIndexTitleOrTextChangeOrTextChangeStartOrTextChangeEnd
     * property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getTextHOrTextPOrTextOrderedListOrTextUnorderedListOrTableTableOrTextSectionOrTextTableOfContentOrTextIllustrationIndexOrTextTableIndexOrTextObjectIndexOrTextUserIndexOrTextAlphabeticalIndexOrTextBibliographyOrTextIndexTitleOrTextChangeOrTextChangeStartOrTextChangeEnd()
     *         .add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TextH } {@link TextP }
     * {@link TextOrderedList } {@link TextUnorderedList } {@link TableTable } {@link TextSection }
     * {@link TextTableOfContent } {@link TextIllustrationIndex } {@link TextTableIndex }
     * {@link TextObjectIndex } {@link TextUserIndex } {@link TextAlphabeticalIndex }
     * {@link TextBibliography } {@link TextIndexTitle } {@link TextChange }
     * {@link TextChangeStart } {@link TextChangeEnd }
     * 
     * 
     */
    public List<Object> getTextHOrTextPOrTextOrderedListOrTextUnorderedListOrTableTableOrTextSectionOrTextTableOfContentOrTextIllustrationIndexOrTextTableIndexOrTextObjectIndexOrTextUserIndexOrTextAlphabeticalIndexOrTextBibliographyOrTextIndexTitleOrTextChangeOrTextChangeStartOrTextChangeEnd() {
        if (this.textHOrTextPOrTextOrderedListOrTextUnorderedListOrTableTableOrTextSectionOrTextTableOfContentOrTextIllustrationIndexOrTextTableIndexOrTextObjectIndexOrTextUserIndexOrTextAlphabeticalIndexOrTextBibliographyOrTextIndexTitleOrTextChangeOrTextChangeStartOrTextChangeEnd == null) {
            this.textHOrTextPOrTextOrderedListOrTextUnorderedListOrTableTableOrTextSectionOrTextTableOfContentOrTextIllustrationIndexOrTextTableIndexOrTextObjectIndexOrTextUserIndexOrTextAlphabeticalIndexOrTextBibliographyOrTextIndexTitleOrTextChangeOrTextChangeStartOrTextChangeEnd = new ArrayList<Object>();
        }
        return this.textHOrTextPOrTextOrderedListOrTextUnorderedListOrTableTableOrTextSectionOrTextTableOfContentOrTextIllustrationIndexOrTextTableIndexOrTextObjectIndexOrTextUserIndexOrTextAlphabeticalIndexOrTextBibliographyOrTextIndexTitleOrTextChangeOrTextChangeStartOrTextChangeEnd;
    }

    /**
     * Gets the value of the textIsHidden property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextIsHidden() {
        return this.textIsHidden;
    }

    /**
     * Gets the value of the textName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextName() {
        return this.textName;
    }

    /**
     * Gets the value of the textProtected property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextProtected() {
        if (this.textProtected == null) {
            return "false";
        } else {
            return this.textProtected;
        }
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
     * Gets the value of the textSectionSourceOrOfficeDdeSource property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the textSectionSourceOrOfficeDdeSource
     * property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getTextSectionSourceOrOfficeDdeSource().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TextSectionSource }
     * {@link OfficeDdeSource }
     * 
     * 
     */
    public List<Object> getTextSectionSourceOrOfficeDdeSource() {
        if (this.textSectionSourceOrOfficeDdeSource == null) {
            this.textSectionSourceOrOfficeDdeSource = new ArrayList<Object>();
        }
        return this.textSectionSourceOrOfficeDdeSource;
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
     * Sets the value of the textCondition property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextCondition(final String value) {
        this.textCondition = value;
    }

    /**
     * Sets the value of the textDisplay property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextDisplay(final String value) {
        this.textDisplay = value;
    }

    /**
     * Sets the value of the textIsHidden property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextIsHidden(final String value) {
        this.textIsHidden = value;
    }

    /**
     * Sets the value of the textName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextName(final String value) {
        this.textName = value;
    }

    /**
     * Sets the value of the textProtected property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextProtected(final String value) {
        this.textProtected = value;
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
     * Sets the value of the textStyleName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextStyleName(final String value) {
        this.textStyleName = value;
    }

}
