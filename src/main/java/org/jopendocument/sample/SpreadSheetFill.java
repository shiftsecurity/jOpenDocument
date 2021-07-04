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

import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

public class SpreadSheetFill {

    public static void main(String[] args) {
        try {
            // Load the file.
            File file = new File("template/invoice.ods");
            final Sheet sheet = SpreadSheet.createFromFile(file).getSheet(0);
            // Change date.
            sheet.getCellAt("I10").setValue(new Date());
            // Change strings.
            sheet.setValueAt("Filling test", 1, 1);
            sheet.getCellAt("B27").setValue("On site support");
            // Change number.
            sheet.getCellAt("F24").setValue(3);
            // Or better yet use a named range
            // (relative to the first cell of the range, wherever it might be).
            sheet.getSpreadSheet().getTableModel("Products").setValueAt(1, 5, 4);
            // Save to file and open it.
            File outputFile = new File("fillingTest.ods");
            OOUtils.open(sheet.getSpreadSheet().saveAs(outputFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
