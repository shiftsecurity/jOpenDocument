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

package org.jopendocument.model.script;

/**
 * 
 */
public class ScriptLibraryLinked {

    protected String scriptName;
    protected String scriptReadonly;
    protected String xlinkHref;
    protected String xlinkType;

    /**
     * Gets the value of the scriptName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getScriptName() {
        return this.scriptName;
    }

    /**
     * Gets the value of the scriptReadonly property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getScriptReadonly() {
        return this.scriptReadonly;
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
     * Gets the value of the xlinkType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXlinkType() {
        if (this.xlinkType == null) {
            return "simple";
        } else {
            return this.xlinkType;
        }
    }

    /**
     * Sets the value of the scriptName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setScriptName(final String value) {
        this.scriptName = value;
    }

    /**
     * Sets the value of the scriptReadonly property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setScriptReadonly(final String value) {
        this.scriptReadonly = value;
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
     * Sets the value of the xlinkType property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setXlinkType(final String value) {
        this.xlinkType = value;
    }

}
