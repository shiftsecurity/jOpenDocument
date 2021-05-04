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

/**
 * 
 */
public class TextFootnote {

    protected TextFootnoteBody textFootnoteBody;
    protected TextFootnoteCitation textFootnoteCitation;
    protected String textId;

    /**
     * Gets the value of the textFootnoteBody property.
     * 
     * @return possible object is {@link TextFootnoteBody }
     * 
     */
    public TextFootnoteBody getTextFootnoteBody() {
        return this.textFootnoteBody;
    }

    /**
     * Gets the value of the textFootnoteCitation property.
     * 
     * @return possible object is {@link TextFootnoteCitation }
     * 
     */
    public TextFootnoteCitation getTextFootnoteCitation() {
        return this.textFootnoteCitation;
    }

    /**
     * Gets the value of the textId property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTextId() {
        return this.textId;
    }

    /**
     * Sets the value of the textFootnoteBody property.
     * 
     * @param value allowed object is {@link TextFootnoteBody }
     * 
     */
    public void setTextFootnoteBody(final TextFootnoteBody value) {
        this.textFootnoteBody = value;
    }

    /**
     * Sets the value of the textFootnoteCitation property.
     * 
     * @param value allowed object is {@link TextFootnoteCitation }
     * 
     */
    public void setTextFootnoteCitation(final TextFootnoteCitation value) {
        this.textFootnoteCitation = value;
    }

    /**
     * Sets the value of the textId property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTextId(final String value) {
        this.textId = value;
    }

}
