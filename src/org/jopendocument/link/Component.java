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

package org.jopendocument.link;

import java.io.File;
import java.util.Map;
import java.util.concurrent.Future;

public abstract class Component {

    private final OOConnexion parent;

    protected Component(OOConnexion parent) {
        super();
        this.parent = parent;
    }

    public OOConnexion getParent() {
        return this.parent;
    }

    public abstract void printDocument();

    public final void printDocument(final Map<String, ?> printProps) {
        printDocument(printProps, null);
    }

    /**
     * Impression d'un document sur une imprimante spécifique
     * 
     * @param printProps Propriétés de l'imprimante (nom, ...) si null alors conserve les propriétés
     *        par défaut du modéle
     * @param printOpt
     */
    public abstract void printDocument(final Map<String, ?> printProps, final Map<String, ?> printOpt);

    public final Future<File> saveToPDF(final File dest) {
        return saveToPDF(dest, "calc_pdf_Export");
    }

    public abstract Future<File> saveToPDF(final File dest, final String filter);

    public abstract void close();
}
