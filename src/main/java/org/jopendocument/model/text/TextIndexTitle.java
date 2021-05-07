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

import org.jopendocument.model.table.TableTable;

/**
 * 
 */
public class TextIndexTitle {

    protected List<Object> textHOrTextPOrTextOrderedListOrTextUnorderedListOrTableTableOrTextSectionOrTextTableOfContentOrTextIllustrationIndexOrTextTableIndexOrTextObjectIndexOrTextUserIndexOrTextAlphabeticalIndexOrTextBibliographyOrTextIndexTitleOrTextChangeOrTextChangeStartOrTextChangeEnd;
    protected String textName;
    protected String textStyleName;

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
     * Gets the value of the textName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextName() {
        return this.textName;
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
     * Sets the value of the textName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextName(final String value) {
        this.textName = value;
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
