/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 2008 jOpenDocument, by ILM Informatique. All rights reserved.
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

package org.jopendocument.sample;

import java.io.File;
import java.io.IOException;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.jopendocument.dom.Length;
import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

public class SpreadSheetCreation {

    public static void main(String[] args) {
        // Create the data to save.
        final Object[][] data = new Object[6][2];
        data[0] = new Object[] { "January", 1 };
        data[1] = new Object[] { "February", 3 };
        data[2] = new Object[] { "March", 8 };
        data[3] = new Object[] { "April", 10 };
        data[4] = new Object[] { "May", 15 };
        data[5] = new Object[] { "June", 18 };

        final String[] columns = new String[] { "Month", "Temp" };

        final TableModel model = new DefaultTableModel(data, columns);
        try {
            // Save the data to an ODS file and open it.
            final File file = new File("temperature.ods");
            final Sheet sheet0 = SpreadSheet.createEmpty(model).getSheet(0);
            sheet0.getColumn(0).setWidth(Length.MM(52));
            sheet0.getColumn(1).setWidth(Length.INCH(1));
            sheet0.getODDocument().saveAs(file);
            OOUtils.open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
