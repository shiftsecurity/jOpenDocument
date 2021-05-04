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

import org.jopendocument.dom.Length;
import org.jopendocument.dom.ODDocument;
import org.jopendocument.dom.StyleStyleDesc;
import org.jopendocument.dom.StyledNode;
import org.jopendocument.dom.XMLVersion;
import org.jopendocument.dom.text.TextDocument;

import org.jdom.Element;

public class Column<D extends ODDocument> extends TableCalcNode<ColumnStyle, D> {

    static Element createEmpty(XMLVersion ns, ColumnStyle style) {
        final Element res = new Element("table-column", ns.getTABLE());
        if (style != null)
            StyledNode.setStyleName(res, style.getName());
        return res;
    }

    private final Table<D> parent;

    public Column(final Table<D> parent, Element tableColElem, StyleStyleDesc<ColumnStyle> colStyleDesc) {
        super(parent.getODDocument(), tableColElem, colStyleDesc);
        this.parent = parent;
    }

    public final Length getWidth() {
        final ColumnStyle style = this.getStyle();
        return style == null ? Length.getNone() : style.getWidth();
    }

    public final void setWidth(final Length l) {
        this.setWidth(l, getODDocument() instanceof TextDocument);
    }

    public final void setWidth(final Length l, final boolean keepTableWidth) {
        this.getPrivateStyle().setWidth(l);
        this.parent.updateWidth(keepTableWidth);
    }
}
