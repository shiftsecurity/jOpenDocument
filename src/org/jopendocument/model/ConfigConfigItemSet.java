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

package org.jopendocument.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class ConfigConfigItemSet {

    protected List<Object> configConfigItemOrConfigConfigItemSetOrConfigConfigItemMapNamedOrConfigConfigItemMapIndexed;
    protected String configName;

    /**
     * Gets the value of the
     * configConfigItemOrConfigConfigItemSetOrConfigConfigItemMapNamedOrConfigConfigItemMapIndexed
     * property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the
     * configConfigItemOrConfigConfigItemSetOrConfigConfigItemMapNamedOrConfigConfigItemMapIndexed
     * property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getConfigConfigItemOrConfigConfigItemSetOrConfigConfigItemMapNamedOrConfigConfigItemMapIndexed().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link ConfigConfigItem }
     * {@link ConfigConfigItemSet } {@link ConfigConfigItemMapNamed }
     * {@link ConfigConfigItemMapIndexed }
     * 
     * 
     */
    public List<Object> getConfigConfigItemOrConfigConfigItemSetOrConfigConfigItemMapNamedOrConfigConfigItemMapIndexed() {
        if (this.configConfigItemOrConfigConfigItemSetOrConfigConfigItemMapNamedOrConfigConfigItemMapIndexed == null) {
            this.configConfigItemOrConfigConfigItemSetOrConfigConfigItemMapNamedOrConfigConfigItemMapIndexed = new ArrayList<Object>();
        }
        return this.configConfigItemOrConfigConfigItemSetOrConfigConfigItemMapNamedOrConfigConfigItemMapIndexed;
    }

    /**
     * Gets the value of the configName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getConfigName() {
        return this.configName;
    }

    /**
     * Sets the value of the configName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setConfigName(final String value) {
        this.configName = value;
    }

}
