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

package org.jopendocument.dom;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;

/**
 * An ODF document, like a spreadsheet or a text file.
 * 
 * @author Sylvain
 */
public abstract class ODDocument {

    private final ODPackage pkg;
    private ODEpoch epoch;

    protected ODDocument(final ODPackage orig) {
        // don't want multiple document per package.
        if (orig.hasODDocument())
            throw new IllegalStateException("ODPackage already has an ODDocument");
        this.pkg = orig;
    }

    public final XMLVersion getVersion() {
        return this.getFormatVersion().getXMLVersion();
    }

    public final XMLFormatVersion getFormatVersion() {
        return this.getPackage().getFormatVersion();
    }

    public final ODPackage getPackage() {
        return this.pkg;
    }

    public final Document getContentDocument() {
        return this.getPackage().getContent().getDocument();
    }

    protected final Element getBody() {
        return getPackage().getContentType().getBody(this.getContentDocument());
    }

    private final String findEpoch() throws ParseException {
        final Namespace tableNS = getVersion().getTABLE();
        final Element settings = this.getBody().getChild("calculation-settings", tableNS);
        if (settings != null) {
            final Element nullDateElem = settings.getChild("null-date", tableNS);
            if (nullDateElem != null)
                return nullDateElem.getAttributeValue("date-value", tableNS);
        }
        return null;
    }

    public final ODEpoch getEpoch() {
        return this.getEpoch(false);
    }

    public final ODEpoch getEpoch(final boolean updateFromXML) {
        if (this.epoch == null || updateFromXML) {
            try {
                this.epoch = ODEpoch.getInstance(this.findEpoch());
            } catch (ParseException e) {
                // quite rare
                throw new IllegalStateException("Unable to parse the epoch of " + this, e);
            }
        }
        assert this.epoch != null;
        return this.epoch;
    }

    // *** Files

    public File saveAs(File file) throws FileNotFoundException, IOException {
        this.getPackage().setFile(file);
        return this.getPackage().save();
    }
}
