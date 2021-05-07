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

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.jopendocument.model.OpenDocument;
import org.jopendocument.panel.ODSViewerPanel;
import org.jopendocument.print.DefaultDocumentPrinter;

public class SpreadSheetDisplayAndPrint {

	public static void main(String[] args) throws IOException {

		// Set the platform L&F.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		display();
		print();
	}

	private static void display() throws IOException {
		// Load the spreadsheet.
		final OpenDocument doc = new OpenDocument();
		doc.loadFrom("template/invoice.ods");

		// Show time !
		final JFrame mainFrame = new JFrame("Viewer");
		DefaultDocumentPrinter printer = new DefaultDocumentPrinter();
		
		ODSViewerPanel viewerPanel = new ODSViewerPanel(doc, printer, true);

		mainFrame.setContentPane(viewerPanel);
		mainFrame.pack();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocation(10, 10);
		mainFrame.setVisible(true);
	}

	private static void print() throws IOException {

		// Load the spreadsheet.
		final OpenDocument doc = new OpenDocument();
		doc.loadFrom("template/invoice.ods");

		// Print !
		DefaultDocumentPrinter printer = new DefaultDocumentPrinter();
		printer.print(doc);

	}
}
