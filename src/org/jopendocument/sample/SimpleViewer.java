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

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.jopendocument.model.OpenDocument;
import org.jopendocument.panel.ODSViewerPanel;

public class SimpleViewer {

    public static void main(String[] args) throws IOException {

        // Set the platform L&F.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Load the spreadsheet.
        final OpenDocument doc = new OpenDocument();
        FileDialog dialog = new FileDialog(new Frame(), "Open", FileDialog.LOAD);
        dialog.setFile("*.ods");
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        String file = dialog.getFile();
        if (file != null) {
            if (dialog.getDirectory() != null) {
                file = dialog.getDirectory() + System.getProperty("file.separator") + file;
            }
            doc.loadFrom(file);
            // Show time !
            final JFrame mainFrame = new JFrame("Viewer");
            ODSViewerPanel viewerPanel = new ODSViewerPanel(doc, true);

            mainFrame.setContentPane(viewerPanel);
            mainFrame.pack();
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setLocation(10, 10);
            mainFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "File not found");
        }
    }
}
