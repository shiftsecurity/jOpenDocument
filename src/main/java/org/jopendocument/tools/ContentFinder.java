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

package org.jopendocument.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jopendocument.io.ODTContentExtractor;

/**
 * A small application to find documents containing a given text
 */

public class ContentFinder extends JFrame {

	private static final long serialVersionUID = -8535510980733968129L;
	private File selectedDir;
	final JTextField textFieldFolder = new JTextField(200);

	public ContentFinder() {
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(new JLabel("Explore folder:"), BorderLayout.WEST);

		p.add(textFieldFolder, BorderLayout.CENTER);
		final JButton buttonSelection = new JButton("Select folder");
		buttonSelection.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				openSelectionDialog();

			}
		});
		p.add(buttonSelection, BorderLayout.EAST);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(new JLabel("Find text:"), BorderLayout.WEST);
		final JTextField textFieldRecherche = new JTextField(200);
		mainPanel.add(textFieldRecherche, BorderLayout.CENTER);
		final JLabel status = new JLabel("");
		mainPanel.add(status, BorderLayout.SOUTH);

		final JTextArea result = new JTextArea();
		result.setEditable(false);
		JScrollPane scroll = new JScrollPane(result);

		final JButton searchButton = new JButton("Find");
		searchButton.setEnabled(false);
		//
		JPanel framePanel = new JPanel();
		framePanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(2, 2, 2, 2);
		framePanel.add(p, c);
		//
		c.gridy++;
		framePanel.add(mainPanel, c);
		//
		c.gridy++;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		framePanel.add(scroll, c);
		//
		c.gridy++;
		c.gridy++;
		c.weightx = 0;
		c.weighty = 0;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.EAST;
		framePanel.add(searchButton, c);

		this.setContentPane(framePanel);

		searchButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				searchButton.setEnabled(false);
				if (selectedDir == null) {
					openSelectionDialog();

				}
				Runnable r = new Runnable() {
					public void run() {
						if (selectedDir != null && selectedDir.isDirectory()) {
							File files[] = selectedDir.listFiles();
							result.setText("");
							for (int i = 0; i < files.length; i++) {
								File file = files[i];

								final String name = file.getName();
								if (name.endsWith(".sxw") || name.endsWith(".sxc") || name.endsWith(".ods") || name.endsWith(".odt")) {
									updateStatus("Looking at:" + file.getAbsolutePath());
									ODTContentExtractor l = new ODTContentExtractor(file);
									try {
										l.load();
									} catch (IOException e) {

										e.printStackTrace();
									}
									if (l.containsIgnoreCase(textFieldRecherche.getText())) {

										result.append(file.getAbsolutePath() + "\n");
									}
								}
								updateStatus("Done");

							}
						}
						SwingUtilities.invokeLater(new Runnable() {

							public void run() {
								searchButton.setEnabled(true);

							}

						});

					}

					private void updateStatus(final String s) {
						SwingUtilities.invokeLater(new Runnable() {

							public void run() {
								status.setText(s);
							}
						});
					}
				};
				Thread th = new Thread(r);
				th.start();

			}
		});
		textFieldRecherche.getDocument().addDocumentListener(new DocumentListener() {

			public void changedUpdate(DocumentEvent e) {
				checkDocument();

			}

			private void checkDocument() {
				if (textFieldRecherche.getText().trim().length() == 0) {
					searchButton.setEnabled(false);
				} else {
					searchButton.setEnabled(true);
				}
			}

			public void insertUpdate(DocumentEvent e) {
				checkDocument();

			}

			public void removeUpdate(DocumentEvent e) {
				checkDocument();

			}
		});
		textFieldFolder.getDocument().addDocumentListener(new DocumentListener() {

			public void changedUpdate(DocumentEvent e) {
				checkDocument();

			}

			private void checkDocument() {
				String fNma = textFieldFolder.getText().trim();
				File f = new File(fNma);
				if (f.exists() && f.isDirectory()) {
					selectedDir = f;
					textFieldFolder.setBackground(Color.WHITE);
				} else {
					textFieldFolder.setBackground(new Color(255, 220, 110));
				}
			}

			public void insertUpdate(DocumentEvent e) {
				checkDocument();

			}

			public void removeUpdate(DocumentEvent e) {
				checkDocument();

			}
		});

	}

	protected void openSelectionDialog() {
		JFileChooser fChooser = new JFileChooser();
		fChooser.setApproveButtonText("Look in folder...");
		fChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (fChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

			File fi = fChooser.getSelectedFile();
			if (fi != null && fi.isDirectory()) {
				this.selectedDir = fi;
				this.textFieldFolder.setText(fi.getAbsolutePath());
			}
		}

	}

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		final JFrame f = new ContentFinder();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(new Dimension(800, 400));
		f.setLocation(100, 100);
		f.setVisible(true);
	}
}
