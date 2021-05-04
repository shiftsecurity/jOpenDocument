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

package org.jopendocument.model.office;

import java.util.ArrayList;
import java.util.List;

import org.jopendocument.model.MetaAutoReload;
import org.jopendocument.model.MetaDocumentStatistic;
import org.jopendocument.model.MetaHyperlinkBehaviour;
import org.jopendocument.model.MetaKeywords;
import org.jopendocument.model.MetaTemplate;
import org.jopendocument.model.MetaUserDefined;

/**
 * 
 */
public class OfficeMeta {

    protected String dcCreator;
    protected String dcDate;
    protected String dcDescription;
    protected String dcLanguage;
    protected String dcSubject;
    protected String dcTitle;
    protected MetaAutoReload metaAutoReload;
    protected String metaCreationDate;
    protected MetaDocumentStatistic metaDocumentStatistic;
    protected String metaEditingCycles;
    protected String metaEditingDuration;
    protected String metaGenerator;
    protected MetaHyperlinkBehaviour metaHyperlinkBehaviour;
    protected String metaInitialCreator;
    protected MetaKeywords metaKeywords;
    protected String metaPrintDate;
    protected String metaPrintedBy;
    protected MetaTemplate metaTemplate;
    protected List<MetaUserDefined> metaUserDefined;

    /**
     * Gets the value of the dcCreator property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDcCreator() {
        return this.dcCreator;
    }

    /**
     * Gets the value of the dcDate property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDcDate() {
        return this.dcDate;
    }

    /**
     * Gets the value of the dcDescription property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDcDescription() {
        return this.dcDescription;
    }

    /**
     * Gets the value of the dcLanguage property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDcLanguage() {
        return this.dcLanguage;
    }

    /**
     * Gets the value of the dcSubject property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDcSubject() {
        return this.dcSubject;
    }

    /**
     * Gets the value of the dcTitle property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDcTitle() {
        return this.dcTitle;
    }

    /**
     * Gets the value of the metaAutoReload property.
     * 
     * @return possible object is {@link MetaAutoReload }
     * 
     */
    public MetaAutoReload getMetaAutoReload() {
        return this.metaAutoReload;
    }

    /**
     * Gets the value of the metaCreationDate property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getMetaCreationDate() {
        return this.metaCreationDate;
    }

    /**
     * Gets the value of the metaDocumentStatistic property.
     * 
     * @return possible object is {@link MetaDocumentStatistic }
     * 
     */
    public MetaDocumentStatistic getMetaDocumentStatistic() {
        return this.metaDocumentStatistic;
    }

    /**
     * Gets the value of the metaEditingCycles property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getMetaEditingCycles() {
        return this.metaEditingCycles;
    }

    /**
     * Gets the value of the metaEditingDuration property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getMetaEditingDuration() {
        return this.metaEditingDuration;
    }

    /**
     * Gets the value of the metaGenerator property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getMetaGenerator() {
        return this.metaGenerator;
    }

    /**
     * Gets the value of the metaHyperlinkBehaviour property.
     * 
     * @return possible object is {@link MetaHyperlinkBehaviour }
     * 
     */
    public MetaHyperlinkBehaviour getMetaHyperlinkBehaviour() {
        return this.metaHyperlinkBehaviour;
    }

    /**
     * Gets the value of the metaInitialCreator property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getMetaInitialCreator() {
        return this.metaInitialCreator;
    }

    /**
     * Gets the value of the metaKeywords property.
     * 
     * @return possible object is {@link MetaKeywords }
     * 
     */
    public MetaKeywords getMetaKeywords() {
        return this.metaKeywords;
    }

    /**
     * Gets the value of the metaPrintDate property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getMetaPrintDate() {
        return this.metaPrintDate;
    }

    /**
     * Gets the value of the metaPrintedBy property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getMetaPrintedBy() {
        return this.metaPrintedBy;
    }

    /**
     * Gets the value of the metaTemplate property.
     * 
     * @return possible object is {@link MetaTemplate }
     * 
     */
    public MetaTemplate getMetaTemplate() {
        return this.metaTemplate;
    }

    /**
     * Gets the value of the metaUserDefined property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the metaUserDefined property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getMetaUserDefined().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link MetaUserDefined }
     * 
     * 
     */
    public List<MetaUserDefined> getMetaUserDefined() {
        if (this.metaUserDefined == null) {
            this.metaUserDefined = new ArrayList<MetaUserDefined>();
        }
        return this.metaUserDefined;
    }

    /**
     * Sets the value of the dcCreator property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDcCreator(final String value) {
        this.dcCreator = value;
    }

    /**
     * Sets the value of the dcDate property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDcDate(final String value) {
        this.dcDate = value;
    }

    /**
     * Sets the value of the dcDescription property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDcDescription(final String value) {
        this.dcDescription = value;
    }

    /**
     * Sets the value of the dcLanguage property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDcLanguage(final String value) {
        this.dcLanguage = value;
    }

    /**
     * Sets the value of the dcSubject property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDcSubject(final String value) {
        this.dcSubject = value;
    }

    /**
     * Sets the value of the dcTitle property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setDcTitle(final String value) {
        this.dcTitle = value;
    }

    /**
     * Sets the value of the metaAutoReload property.
     * 
     * @param value allowed object is {@link MetaAutoReload }
     * 
     */
    public void setMetaAutoReload(final MetaAutoReload value) {
        this.metaAutoReload = value;
    }

    /**
     * Sets the value of the metaCreationDate property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setMetaCreationDate(final String value) {
        this.metaCreationDate = value;
    }

    /**
     * Sets the value of the metaDocumentStatistic property.
     * 
     * @param value allowed object is {@link MetaDocumentStatistic }
     * 
     */
    public void setMetaDocumentStatistic(final MetaDocumentStatistic value) {
        this.metaDocumentStatistic = value;
    }

    /**
     * Sets the value of the metaEditingCycles property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setMetaEditingCycles(final String value) {
        this.metaEditingCycles = value;
    }

    /**
     * Sets the value of the metaEditingDuration property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setMetaEditingDuration(final String value) {
        this.metaEditingDuration = value;
    }

    /**
     * Sets the value of the metaGenerator property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setMetaGenerator(final String value) {
        this.metaGenerator = value;
    }

    /**
     * Sets the value of the metaHyperlinkBehaviour property.
     * 
     * @param value allowed object is {@link MetaHyperlinkBehaviour }
     * 
     */
    public void setMetaHyperlinkBehaviour(final MetaHyperlinkBehaviour value) {
        this.metaHyperlinkBehaviour = value;
    }

    /**
     * Sets the value of the metaInitialCreator property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setMetaInitialCreator(final String value) {
        this.metaInitialCreator = value;
    }

    /**
     * Sets the value of the metaKeywords property.
     * 
     * @param value allowed object is {@link MetaKeywords }
     * 
     */
    public void setMetaKeywords(final MetaKeywords value) {
        this.metaKeywords = value;
    }

    /**
     * Sets the value of the metaPrintDate property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setMetaPrintDate(final String value) {
        this.metaPrintDate = value;
    }

    /**
     * Sets the value of the metaPrintedBy property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setMetaPrintedBy(final String value) {
        this.metaPrintedBy = value;
    }

    /**
     * Sets the value of the metaTemplate property.
     * 
     * @param value allowed object is {@link MetaTemplate }
     * 
     */
    public void setMetaTemplate(final MetaTemplate value) {
        this.metaTemplate = value;
    }

}
