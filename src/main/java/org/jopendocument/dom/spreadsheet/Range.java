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

import org.jopendocument.util.CompareUtils;

import java.awt.Point;
import java.util.regex.Matcher;

/**
 * A cell range.
 * 
 * @author Sylvain
 */
public final class Range {

    /**
     * Parse a range.
     * 
     * @param range the string form, e.g. "Sheet1.A23:.AA34".
     * @return the parsed range.
     */
    static public final Range parse(String range) {
        final Matcher m = SpreadSheet.cellRangePattern.matcher(range);
        if (!m.matches())
            throw new IllegalStateException(range + " is not a valid range address");
        final String sheet1 = SpreadSheet.parseSheetName(m.group(1));
        final String sheet2 = SpreadSheet.parseSheetName(m.group(6));

        final Point start = Table.resolve(m.group(4));
        final String cell2 = m.group(9);
        final Point end = cell2 == null ? null : Table.resolve(cell2);

        return new Range(sheet1, start, sheet2, end);
    }

    private final String sheet1, sheet2;
    private final Point start, end;

    /**
     * Create a new instance with a single cell.
     * 
     * @param sheet name of the sheet.
     * @param point coordinate of the cell.
     */
    public Range(String sheet, Point point) {
        this(sheet, point, point);
    }

    public Range(String sheet, Point startPoint, Point endPoint) {
        this(sheet, startPoint, null, endPoint);
    }

    /**
     * Create a new instance.
     * 
     * @param startSheet name of the start sheet.
     * @param startPoint coordinate of the start.
     * @param endSheet name of the end sheet, can be <code>null</code> if the range doesn't span
     *        multiple sheets.
     * @param endPoint coordinate of the end, can be <code>null</code> for single cell range.
     */
    public Range(String startSheet, Point startPoint, String endSheet, Point endPoint) {
        super();
        if (startSheet == null && endSheet != null)
            throw new NullPointerException("null start sheet, but non null endSheet : " + endSheet);
        this.sheet1 = startSheet;
        this.sheet2 = endSheet == null ? startSheet : endSheet;
        if (startPoint == null)
            throw new NullPointerException("Null start point");
        this.start = startPoint;
        if (endPoint == null && spanSheets())
            throw new IllegalArgumentException("End cell must be passed for range spanning sheets : " + getStartSheet() + " to " + getEndSheet());
        this.end = endPoint == null ? this.start : endPoint;
        assert this.start != null && this.end != null;
    }

    public final String getStartSheet() {
        return this.sheet1;
    }

    public final Point getStartPoint() {
        return this.start;
    }

    public final String getEndSheet() {
        return this.sheet2;
    }

    public final Point getEndPoint() {
        return this.end;
    }

    public final boolean spanSheets() {
        return !CompareUtils.equals(this.getStartSheet(), this.getEndSheet());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Range o = (Range) obj;
        return CompareUtils.equals(this.sheet1, o.sheet1) && this.start.equals(o.start) && CompareUtils.equals(this.sheet2, o.sheet2) && this.end.equals(o.end);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.sheet1 == null) ? 0 : this.sheet1.hashCode());
        result = prime * result + ((this.start == null) ? 0 : this.start.hashCode());
        result = prime * result + ((this.sheet2 == null) ? 0 : this.sheet2.hashCode());
        result = prime * result + ((this.end == null) ? 0 : this.end.hashCode());
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(32);
        if (this.getStartSheet() != null)
            sb.append(this.getStartSheet());
        // the RelaxNG pattern requires the dot
        sb.append(".");
        sb.append(Table.getAddress(getStartPoint()));
        if (this.spanSheets()) {
            sb.append(":");
            sb.append(this.getEndSheet());
            sb.append(".");
            sb.append(Table.getAddress(getEndPoint()));
        } else if (!getEndPoint().equals(getStartPoint())) {
            sb.append(":");
            // the RelaxNG pattern requires the dot
            sb.append(".");
            sb.append(Table.getAddress(getEndPoint()));
        }
        return sb.toString();
    }
}