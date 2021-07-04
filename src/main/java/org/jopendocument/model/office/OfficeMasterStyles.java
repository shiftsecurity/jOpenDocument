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

import org.jopendocument.model.draw.DrawLayerSet;
import org.jopendocument.model.style.StyleHandoutMaster;
import org.jopendocument.model.style.StyleMasterPage;

public class OfficeMasterStyles {

    private DrawLayerSet drawLayerSet;

    private StyleHandoutMaster styleHandoutMaster;

    private final List<StyleMasterPage> styleMasterPage = new ArrayList<StyleMasterPage>(2);

    public void addMasterPage(final StyleMasterPage page) {
        this.styleMasterPage.add(page);
    }

    /**
     * Gets the value of the drawLayerSet property.
     * 
     * @return possible object is {@link DrawLayerSet }
     */
    public DrawLayerSet getDrawLayerSet() {
        return this.drawLayerSet;
    }

    public StyleMasterPage getMasterPageFromStyleName(final String name) {
        if (name == null) {
            throw new IllegalArgumentException("null is not a valid StyleMasterPage name");
        }
        for (final StyleMasterPage p : this.styleMasterPage) {
            if (p.getStyleName().equals(name)) {
                return p;
            }
        }
        System.err.println(this.styleMasterPage);
        throw new IllegalArgumentException("Unable to find StyleMasterPage named:" + name);

    }

    public List<StyleMasterPage> getMasterPages() {
        return this.styleMasterPage;
    }

    /**
     * Gets the value of the styleHandoutMaster property.
     * 
     * @return possible object is {@link StyleHandoutMaster }
     */
    public StyleHandoutMaster getStyleHandoutMaster() {
        return this.styleHandoutMaster;
    }

    /**
     * Sets the value of the drawLayerSet property.
     * 
     * @param value allowed object is {@link DrawLayerSet }
     */
    public void setDrawLayerSet(final DrawLayerSet value) {
        this.drawLayerSet = value;
    }

    /**
     * Sets the value of the styleHandoutMaster property.
     * 
     * @param value allowed object is {@link StyleHandoutMaster }
     */
    public void setStyleHandoutMaster(final StyleHandoutMaster value) {
        this.styleHandoutMaster = value;
    }
}
