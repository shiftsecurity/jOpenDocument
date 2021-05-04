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

package org.jopendocument.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ODTContentExtractor {

	private File file;

	private String text;

	public ODTContentExtractor(File f) {
		this.file = f;
	}

	public void load() throws IOException {
		ZipFile zFile = new ZipFile(this.file);
		ZipEntry e = zFile.getEntry("content.xml");
		InputStream in = zFile.getInputStream(e);

		BufferedReader r = new BufferedReader(new InputStreamReader(in));

		final char[] chars = new char[(int) e.getSize()];
		r.read(chars);
		StringBuilder builder = new StringBuilder();
		boolean add = false;
		final int length = chars.length;
		for (int i = 0; i < length; i++) {
			char c = chars[i];
			if (c == '<') {
				add = false;
			} else if (c == '>') {
				add = true;
			} else if (add) {
				builder.append(c);
			}
		}
		in.close();
		zFile.close();
		this.text = builder.toString();
	}

	/**
	 * Checks if the file contains a word. This method is case insensitive.
	 * 
	 * @param s
	 *            a word or text
	 * @return true if the text if found
	 */
	public boolean containsIgnoreCase(String s) {
		String all = this.text.toLowerCase();
		String string = s.toLowerCase();
		return (all.indexOf(string) >= 0);
	}

}
