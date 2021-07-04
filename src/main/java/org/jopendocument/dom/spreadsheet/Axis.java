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

package org.jopendocument.dom.spreadsheet;

import org.jopendocument.dom.StyleProperties;
import net.jcip.annotations.Immutable;

import org.jdom.Attribute;
import org.jdom.Element;

@Immutable
public enum Axis {
    ROW("row"), COLUMN("column");

    private final String shortName, elemName, headerName, groupName, pluralName, repeatedAttrName;

    private Axis(final String shortName) {
        this.shortName = shortName;

        this.elemName = "table-" + shortName;
        this.headerName = "table-header-" + shortName + "s";
        this.groupName = this.elemName + "-group";
        this.pluralName = this.elemName + "s";
        this.repeatedAttrName = "number-" + shortName + "s-repeated";
    }

    public final String getShortName() {
        return this.shortName;
    }

    public final String getElemName() {
        return this.elemName;
    }

    public final String getHeaderName() {
        return this.headerName;
    }

    public final String getGroupName() {
        return this.groupName;
    }

    public final String getPluralName() {
        return this.pluralName;
    }

    public final String getRepeatedAttrName() {
        return this.repeatedAttrName;
    }

    final Attribute getRepeatedAttr(final Element elem) {
        assert elem.getName().equals(this.getElemName());
        return elem.getAttribute(getRepeatedAttrName(), elem.getNamespace());
    }

    final int getRepeated(final Element elem) {
        assert elem.getName().equals(this.getElemName());
        return StyleProperties.parseInt(elem.getAttributeValue(getRepeatedAttrName(), elem.getNamespace()), 1);
    }

    final void setRepeated(final Element elem, final int i) {
        assert elem.getName().equals(this.getElemName());
        RepeatedBreaker.setRepeated(elem, getRepeatedAttrName(), i);
    }
}
