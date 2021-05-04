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

package org.jopendocument.renderer;

import java.util.ArrayList;
import java.util.List;

public class ODTCellTextLineItem {

    private int height;
    private List<ODTCellTextItem> items = new ArrayList<ODTCellTextItem>();

    public List<ODTCellTextItem> getItems() {
        return items;
    }

    public int getHeight() {
        return height;
    }

    public void addItem(ODTCellTextItem item) {
        if (item.getHeight() > this.height) {
            this.height = item.getHeight();
        }
        items.add(item);
    }

    public int getSize() {
        return this.items.size();
    }

    @Override
    public String toString() {
        return "Height:" + this.height + " items:" + items;
    }

    public int getTotalWidthWithoutSpace() {
        int w = 0;
        for (ODTCellTextItem item : items) {
            w += item.getWidthWithoutSpace();
        }
        return w;
    }

}
