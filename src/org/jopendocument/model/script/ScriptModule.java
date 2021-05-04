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
public class ScriptModule {

    protected String scriptName;
    protected String scriptSourceCode;

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
     * Gets the value of the scriptSourceCode property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getScriptSourceCode() {
        return this.scriptSourceCode;
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
     * Sets the value of the scriptSourceCode property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setScriptSourceCode(final String value) {
        this.scriptSourceCode = value;
    }

}
