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

package org.jopendocument.util;

import org.jopendocument.util.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class XMLTextExtractor {

    private File f;

    public XMLTextExtractor(File file) {
        this.f = file;
    }

    /**
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) {
        XMLTextExtractor e = new XMLTextExtractor(new File("content.xml"));
        FileOutputStream fOp;
        try {
            fOp = new FileOutputStream("out.txt");

            e.extractText(fOp);
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    public void extractText(final FileOutputStream op) {
        try {
            final Writer pWrt = new BufferedWriter(new OutputStreamWriter(op, StringUtils.UTF8));
            final XMLReader rdr = XMLReaderFactory.createXMLReader();
            rdr.setContentHandler(new DefaultHandler() {
                @Override
                public void characters(char ch[], int start, int length) throws SAXException {
                    try {
                        pWrt.write(ch, start, length);
                    } catch (IOException e) {
                        throw new SAXException(e);
                    }
                }
            });

            final InputSource inputSource1 = new InputSource(new FileReader(f));
            pWrt.close();
            rdr.parse(inputSource1);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
