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

import org.jopendocument.model.office.OfficeChangeInfo;

/**
 * 
 */
public class TextFormatChange {

    protected OfficeChangeInfo officeChangeInfo;

    /**
     * Gets the value of the officeChangeInfo property.
     * 
     * @return possible object is {@link OfficeChangeInfo }
     * 
     */
    public OfficeChangeInfo getOfficeChangeInfo() {
        return this.officeChangeInfo;
    }

    /**
     * Sets the value of the officeChangeInfo property.
     * 
     * @param value allowed object is {@link OfficeChangeInfo }
     * 
     */
    public void setOfficeChangeInfo(final OfficeChangeInfo value) {
        this.officeChangeInfo = value;
    }

}
