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
public class ScriptLibraries {

    protected List<Object> scriptLibraryEmbeddedOrScriptLibraryLinked;
    protected String xmlnsScript;
    protected String xmlnsXlink;

    /**
     * Gets the value of the scriptLibraryEmbeddedOrScriptLibraryLinked property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the
     * scriptLibraryEmbeddedOrScriptLibraryLinked property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getScriptLibraryEmbeddedOrScriptLibraryLinked().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link ScriptLibraryEmbedded }
     * {@link ScriptLibraryLinked }
     * 
     * 
     */
    public List<Object> getScriptLibraryEmbeddedOrScriptLibraryLinked() {
        if (this.scriptLibraryEmbeddedOrScriptLibraryLinked == null) {
            this.scriptLibraryEmbeddedOrScriptLibraryLinked = new ArrayList<Object>();
        }
        return this.scriptLibraryEmbeddedOrScriptLibraryLinked;
    }

    /**
     * Gets the value of the xmlnsScript property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXmlnsScript() {
        if (this.xmlnsScript == null) {
            return "http://openoffice.org/2000/script";
        } else {
            return this.xmlnsScript;
        }
    }

    /**
     * Gets the value of the xmlnsXlink property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getXmlnsXlink() {
        if (this.xmlnsXlink == null) {
            return "http://www.w3.org/1999/xlink";
        } else {
            return this.xmlnsXlink;
        }
    }

    /**
     * Sets the value of the xmlnsScript property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setXmlnsScript(final String value) {
        this.xmlnsScript = value;
    }

    /**
     * Sets the value of the xmlnsXlink property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setXmlnsXlink(final String value) {
        this.xmlnsXlink = value;
    }

}
