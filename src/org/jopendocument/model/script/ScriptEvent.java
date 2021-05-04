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
public class ScriptEvent {

    protected String scriptEventName;
    protected String scriptLanguage;
    protected String scriptLocation;
    protected String scriptMacroName;
    protected String value;

    /**
     * Gets the value of the scriptEventName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getScriptEventName() {
        return this.scriptEventName;
    }

    /**
     * Gets the value of the scriptLanguage property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getScriptLanguage() {
        return this.scriptLanguage;
    }

    /**
     * Gets the value of the scriptLocation property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getScriptLocation() {
        return this.scriptLocation;
    }

    /**
     * Gets the value of the scriptMacroName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getScriptMacroName() {
        return this.scriptMacroName;
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
     * Sets the value of the scriptEventName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setScriptEventName(final String value) {
        this.scriptEventName = value;
    }

    /**
     * Sets the value of the scriptLanguage property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setScriptLanguage(final String value) {
        this.scriptLanguage = value;
    }

    /**
     * Sets the value of the scriptLocation property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setScriptLocation(final String value) {
        this.scriptLocation = value;
    }

    /**
     * Sets the value of the scriptMacroName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setScriptMacroName(final String value) {
        this.scriptMacroName = value;
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
