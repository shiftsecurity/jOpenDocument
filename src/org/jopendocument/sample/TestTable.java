/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 2008 jOpenDocument, by ILM Informatique. All rights reserved.
 * 
 * The contents of this file are subject to the terms of the GNU General Public License Version 3
 * only ("GPL"). You may not use this file except in compliance with the License. You can obtain a
 * copy of the License at http://www.gnu.org/licenses/gpl-3.0.html See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in each file.
 */

package org.jopendocument.sample;

import java.io.File;
import java.util.Date;

import org.jdom.Element;
import org.jopendocument.dom.ODDocument;
import org.jopendocument.dom.ODPackage;
import org.jopendocument.dom.ODXMLDocument;
import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.spreadsheet.Table;

public class TestTable {

    public static void main(String[] args) {
        try {
            File templateFile = new File("template/tables.odt");
            File outFile = new File("out.odt");

            final ODDocument single = new ODPackage(templateFile).getODDocument();
            final ODXMLDocument content = single.getPackage().getContent();

            // Growing
            {
                final Element table = content.getDescendantByName("table:table", "GrowingTable");
                final Table<ODDocument> t = new Table<ODDocument>(single, table);
                // this will bring the column count to 5
                // the new columns will be created with the same style as column 1
                // and the table width will grow
                t.setColumnCount(5, 1, false);
                t.setValueAt("Each new col has the", 2, 0);
                t.setValueAt("same width as col n°2", 2, 1);
                t.setValueAt("Float", 3, 0);
                t.setValueAt(5.2, 3, 1);
                t.setValueAt("Date", 4, 0);
                t.setValueAt(new Date(), 4, 1);
            }

            // Fixed
            {
                final Element table = content.getDescendantByName("table:table", "FixedTable");
                final Table<ODDocument> t = new Table<ODDocument>(single, table);
                // this will bring the column count to 5
                // the new columns will be created with the same style as column 1
                // but the table width will remain fixed, columns will be resized to
                // fit in the current table width (ratios will be kept)
                t.setColumnCount(5, 1, true);
                t.setValueAt("Each new col has the", 2, 0);
                t.setValueAt("same width as col n°2", 2, 1);
                t.setValueAt("and ratios", 3, 0);
                t.setValueAt("are kept", 3, 1);
                t.setValueAt("col 1 is still", 4, 0);
                t.setValueAt("half the col 2", 4, 1);
            }

            // Shrinking
            {
                final Element table = content.getDescendantByName("table:table", "ShrinkingTable");
                final Table<ODDocument> t = new Table<ODDocument>(single, table);
                // This will bring the column count to 3 by removing the third and fourth columns.
                // Other columns will remain unaffected, the table width will thus shrink.
                t.removeColumn(2, 4, false);
            }

            // Fixed
            {
                final Element table = content.getDescendantByName("table:table", "FixedTable2");
                final Table<ODDocument> t = new Table<ODDocument>(single, table);
                // This will bring the column count to 4 by removing the second column. The table
                // will keep its width, the other columns will thus grow to use the space freed.
                // As in OpenOffice, any merged cell on the removed column will be unmerged.
                t.removeColumn(1, true);
            }

            // Save to file.
            single.saveAs(outFile);

            // Open the document with OpenOffice.org !
            OOUtils.open(outFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
