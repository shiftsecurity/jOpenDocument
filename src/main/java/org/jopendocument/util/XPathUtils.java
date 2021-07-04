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

/**
 * @author Sylvain CUAZ
 */
public final class XPathUtils {

    /**
     * Compute the parent of a path. Note : path must lead to an element.
     * 
     * @param path the path with at least one slash, eg "./elem".
     * @return the parent of path, eg ".".
     */
    public final static String parentOf(String path) {
        return path.substring(0, path.lastIndexOf('/'));
    }

    /**
     * Compute the name of the last part of the path.
     * 
     * @param path the path, eg "./elem" or "elem".
     * @return the name, eg "elem".
     */
    public final static String basename(String path) {
        return path.substring(path.lastIndexOf('/') + 1);
    }

    /**
     * Compute the namespace prefix of a qualified name. NOTE : this also works if qName is a path,
     * eg "./office:body/office:text".
     * 
     * @param qName a qualified name, eg "office:text".
     * @return the prefix or <code>null</code> if there's none, eg "office".
     */
    public final static String namespace(String qName) {
        qName = basename(qName);
        final int colonIndex = qName.lastIndexOf(':');
        if (colonIndex < 0)
            return null;
        else
            return qName.substring(0, colonIndex);
    }

    /**
     * Compute the local name of a qualified name. NOTE : this also works if qName is a path, eg
     * "./office:body/office:text" or if there's no prefix, eg "./office:body/child".
     * 
     * @param qName a qualified name, eg "office:text".
     * @return the local name, eg "text".
     */
    public final static String localName(String qName) {
        qName = basename(qName);
        return qName.substring(qName.lastIndexOf(':') + 1);
    }

    private XPathUtils() {
        // static only
    }
}