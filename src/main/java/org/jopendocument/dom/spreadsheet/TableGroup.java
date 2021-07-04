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

import org.jopendocument.dom.ODNode;
import org.jopendocument.dom.StyleProperties;
import org.jopendocument.util.Tuple2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jdom.Element;

/**
 * A group of columns/rows that can be hidden.
 * 
 * @author Sylvain CUAZ
 */
public class TableGroup extends ODNode {

    static Tuple2<TableGroup, List<Element>> createRoot(final Table<?> table, final Axis col) {
        final TableGroup group = new TableGroup(table, col);
        return Tuple2.create(group, group.flatten());
    }

    private final Table<?> table;
    private final Axis axis;
    private final TableGroup parent;
    private final List<TableGroup> children;

    private int headerCount;
    private int first;
    private int size;

    // root group
    private TableGroup(final Table<?> table, final Axis col) {
        this(table, col, null, table.getElement(), 0);
    }

    private TableGroup(Table<?> table, TableGroup parent, Element elem, final int first) {
        this(table, parent.axis, parent, elem, first);
    }

    private TableGroup(Table<?> table, final Axis col, TableGroup parent, Element elem, final int first) {
        super(elem);
        if (table == null)
            throw new NullPointerException("null table");
        this.table = table;
        this.axis = col;
        this.parent = parent;
        this.first = first;
        this.children = new ArrayList<TableGroup>();
    }

    private List<Element> flatten() {
        // max() since a group can have only group children
        final List<Element> res = new ArrayList<Element>(Math.max(128, getElement().getContentSize()));
        final String fullName = this.axis.getElemName();
        final String groupName = this.axis.getGroupName();
        final String pluralName = this.axis.getPluralName();

        // A table shall not contain more than one <table:table-header-rows> element.
        final Element header = this.getElement().getChild(this.axis.getHeaderName(), getElement().getNamespace());
        if (header != null)
            this.headerCount = Table.flattenChildren(res, header, this.axis);
        else
            this.headerCount = 0;
        int size = this.headerCount;

        this.children.clear();
        @SuppressWarnings("unchecked")
        final List<Element> content = new ArrayList<Element>(getElement().getChildren());
        for (final Element child : content) {
            if (child.getName().equals(fullName)) {
                size += Table.flatten1(res, child, this.axis);
            } else if (child.getName().equals(pluralName)) {
                // ignore table-rows element (but add its children)
                size += Table.flattenChildren(res, child, this.axis);
            } else if (child.getName().equals(groupName)) {
                final TableGroup g = new TableGroup(getTable(), this, child, this.first + size);
                this.children.add(g);
                res.addAll(g.flatten());
                size += g.getSize();
            }
            // else nothing to do (header or soft-page-break)
        }

        this.size = size;

        return res;
    }

    public final Table<?> getTable() {
        return this.table;
    }

    /**
     * The parent of this group.
     * 
     * @return the parent, <code>null</code> if this is the root group.
     */
    public final TableGroup getParent() {
        return this.parent;
    }

    public final List<TableGroup> getChildren() {
        return Collections.unmodifiableList(this.children);
    }

    final TableGroup getDescendentOrSelfContaining(final int y) {
        if (!this.contains(y))
            return null;
        for (final TableGroup g : this.getChildren()) {
            final TableGroup res = g.getDescendentOrSelfContaining(y);
            if (res != null)
                return res;
        }
        return this;
    }

    public final boolean isDisplayed() {
        if (this.getParent() == null)
            return true;
        else
            // from table:display : the default value for this attribute is true
            return StyleProperties.parseBoolean(getElement().getAttributeValue("display", getElement().getNamespace()), true);
    }

    /**
     * The index of the first row/column in this group.
     * 
     * @return index of the first element.
     */
    public final int getFirst() {
        return this.first;
    }

    public final int getHeaderCount() {
        return this.headerCount;
    }

    public final int getSize() {
        return this.size;
    }

    public final boolean contains(final int i) {
        return i >= this.getFirst() && i < this.getFirst() + this.getSize();
    }

    final int getFollowingHeaderCount() {
        int res = this.getHeaderCount();
        // the table and each distinct group may contain one <table:table-header-rows> element, if
        // and only if the table rows contained in the <table:table-header-rows> elements are
        // adjacent.
        for (final TableGroup g : this.getChildren()) {
            if (g.getFirst() != this.getFirst() + res)
                break;
            res += g.getFollowingHeaderCount();
        }
        return res;
    }
}
