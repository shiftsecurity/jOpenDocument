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

package org.jopendocument.model.style;

import java.util.ArrayList;
import java.util.List;

import org.jopendocument.model.table.TableTable;
import org.jopendocument.model.text.TextAlphabeticalIndex;
import org.jopendocument.model.text.TextAlphabeticalIndexAutoMarkFile;
import org.jopendocument.model.text.TextBibliography;
import org.jopendocument.model.text.TextChange;
import org.jopendocument.model.text.TextChangeEnd;
import org.jopendocument.model.text.TextChangeStart;
import org.jopendocument.model.text.TextDdeConnectionDecls;
import org.jopendocument.model.text.TextH;
import org.jopendocument.model.text.TextIllustrationIndex;
import org.jopendocument.model.text.TextIndexTitle;
import org.jopendocument.model.text.TextObjectIndex;
import org.jopendocument.model.text.TextOrderedList;
import org.jopendocument.model.text.TextP;
import org.jopendocument.model.text.TextSection;
import org.jopendocument.model.text.TextSequenceDecls;
import org.jopendocument.model.text.TextTableIndex;
import org.jopendocument.model.text.TextTableOfContent;
import org.jopendocument.model.text.TextUnorderedList;
import org.jopendocument.model.text.TextUserFieldDecls;
import org.jopendocument.model.text.TextUserIndex;
import org.jopendocument.model.text.TextVariableDecls;

/**
 * 
 */
public class StyleFooterLeft {

    protected String styleDisplay;
    protected List<Object> textVariableDeclsOrTextSequenceDeclsOrTextUserFieldDeclsOrTextDdeConnectionDeclsOrTextAlphabeticalIndexAutoMarkFileOrTextHOrTextPOrTextOrderedListOrTextUnorderedListOrTableTableOrTextSectionOrTextTableOfContentOrTextIllustrationIndexOrTextTableIndexOrTextObjectIndexOrTextUserIndexOrTextAlphabeticalIndexOrTextBibliographyOrTextIndexTitleOrTextChangeOrTextChangeStartOrTextChangeEndOrStyleRegionLeftOrStyleRegionCenterOrStyleRegionRight;

    /**
     * Gets the value of the styleDisplay property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStyleDisplay() {
        if (this.styleDisplay == null) {
            return "true";
        } else {
            return this.styleDisplay;
        }
    }

    /**
     * Gets the value of the
     * textVariableDeclsOrTextSequenceDeclsOrTextUserFieldDeclsOrTextDdeConnectionDeclsOrTextAlphabeticalIndexAutoMarkFileOrTextHOrTextPOrTextOrderedListOrTextUnorderedListOrTableTableOrTextSectionOrTextTableOfContentOrTextIllustrationIndexOrTextTableIndexOrTextObjectIndexOrTextUserIndexOrTextAlphabeticalIndexOrTextBibliographyOrTextIndexTitleOrTextChangeOrTextChangeStartOrTextChangeEndOrStyleRegionLeftOrStyleRegionCenterOrStyleRegionRight
     * property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the
     * textVariableDeclsOrTextSequenceDeclsOrTextUserFieldDeclsOrTextDdeConnectionDeclsOrTextAlphabeticalIndexAutoMarkFileOrTextHOrTextPOrTextOrderedListOrTextUnorderedListOrTableTableOrTextSectionOrTextTableOfContentOrTextIllustrationIndexOrTextTableIndexOrTextObjectIndexOrTextUserIndexOrTextAlphabeticalIndexOrTextBibliographyOrTextIndexTitleOrTextChangeOrTextChangeStartOrTextChangeEndOrStyleRegionLeftOrStyleRegionCenterOrStyleRegionRight
     * property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getTextVariableDeclsOrTextSequenceDeclsOrTextUserFieldDeclsOrTextDdeConnectionDeclsOrTextAlphabeticalIndexAutoMarkFileOrTextHOrTextPOrTextOrderedListOrTextUnorderedListOrTableTableOrTextSectionOrTextTableOfContentOrTextIllustrationIndexOrTextTableIndexOrTextObjectIndexOrTextUserIndexOrTextAlphabeticalIndexOrTextBibliographyOrTextIndexTitleOrTextChangeOrTextChangeStartOrTextChangeEndOrStyleRegionLeftOrStyleRegionCenterOrStyleRegionRight()
     *         .add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TextVariableDecls }
     * {@link TextSequenceDecls } {@link TextUserFieldDecls } {@link TextDdeConnectionDecls }
     * {@link TextAlphabeticalIndexAutoMarkFile } {@link TextH } {@link TextP }
     * {@link TextOrderedList } {@link TextUnorderedList } {@link TableTable } {@link TextSection }
     * {@link TextTableOfContent } {@link TextIllustrationIndex } {@link TextTableIndex }
     * {@link TextObjectIndex } {@link TextUserIndex } {@link TextAlphabeticalIndex }
     * {@link TextBibliography } {@link TextIndexTitle } {@link TextChange }
     * {@link TextChangeStart } {@link TextChangeEnd } {@link StyleRegionLeft }
     * {@link StyleRegionCenter } {@link StyleRegionRight }
     * 
     * 
     */
    public List<Object> getTextVariableDeclsOrTextSequenceDeclsOrTextUserFieldDeclsOrTextDdeConnectionDeclsOrTextAlphabeticalIndexAutoMarkFileOrTextHOrTextPOrTextOrderedListOrTextUnorderedListOrTableTableOrTextSectionOrTextTableOfContentOrTextIllustrationIndexOrTextTableIndexOrTextObjectIndexOrTextUserIndexOrTextAlphabeticalIndexOrTextBibliographyOrTextIndexTitleOrTextChangeOrTextChangeStartOrTextChangeEndOrStyleRegionLeftOrStyleRegionCenterOrStyleRegionRight() {
        if (this.textVariableDeclsOrTextSequenceDeclsOrTextUserFieldDeclsOrTextDdeConnectionDeclsOrTextAlphabeticalIndexAutoMarkFileOrTextHOrTextPOrTextOrderedListOrTextUnorderedListOrTableTableOrTextSectionOrTextTableOfContentOrTextIllustrationIndexOrTextTableIndexOrTextObjectIndexOrTextUserIndexOrTextAlphabeticalIndexOrTextBibliographyOrTextIndexTitleOrTextChangeOrTextChangeStartOrTextChangeEndOrStyleRegionLeftOrStyleRegionCenterOrStyleRegionRight == null) {
            this.textVariableDeclsOrTextSequenceDeclsOrTextUserFieldDeclsOrTextDdeConnectionDeclsOrTextAlphabeticalIndexAutoMarkFileOrTextHOrTextPOrTextOrderedListOrTextUnorderedListOrTableTableOrTextSectionOrTextTableOfContentOrTextIllustrationIndexOrTextTableIndexOrTextObjectIndexOrTextUserIndexOrTextAlphabeticalIndexOrTextBibliographyOrTextIndexTitleOrTextChangeOrTextChangeStartOrTextChangeEndOrStyleRegionLeftOrStyleRegionCenterOrStyleRegionRight = new ArrayList<Object>();
        }
        return this.textVariableDeclsOrTextSequenceDeclsOrTextUserFieldDeclsOrTextDdeConnectionDeclsOrTextAlphabeticalIndexAutoMarkFileOrTextHOrTextPOrTextOrderedListOrTextUnorderedListOrTableTableOrTextSectionOrTextTableOfContentOrTextIllustrationIndexOrTextTableIndexOrTextObjectIndexOrTextUserIndexOrTextAlphabeticalIndexOrTextBibliographyOrTextIndexTitleOrTextChangeOrTextChangeStartOrTextChangeEndOrStyleRegionLeftOrStyleRegionCenterOrStyleRegionRight;
    }

    /**
     * Sets the value of the styleDisplay property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStyleDisplay(final String value) {
        this.styleDisplay = value;
    }

}
