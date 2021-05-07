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

public class ConfigConfigItem {

    protected String configName;

    protected String configType;

    protected String value;

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
     * Gets the value of the configType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getConfigType() {
        return this.configType;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getvalue() {
        return this.value;
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

    /**
     * Sets the value of the configType property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setConfigType(final String value) {
        this.configType = value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setvalue(final String value) {
        this.value = value;
    }

}
