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

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class ScriptLibraryEmbedded {

    protected List<ScriptModule> scriptModule;
    protected String scriptName;
    protected String scriptReadonly;

    /**
     * Gets the value of the scriptModule property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the scriptModule property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getScriptModule().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link ScriptModule }
     * 
     * 
     */
    public List<ScriptModule> getScriptModule() {
        if (this.scriptModule == null) {
            this.scriptModule = new ArrayList<ScriptModule>();
        }
        return this.scriptModule;
    }

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

}
