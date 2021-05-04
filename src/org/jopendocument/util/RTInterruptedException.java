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
 * Signal an interrupt as a non checked exception. NOTE: the <i>interrupted status</i> of the
 * current thread is set in the constructor.
 * 
 * @author Sylvain
 */
public class RTInterruptedException extends RuntimeException {

    {
        // remettre le flag pour les méthodes appelantes.
        Thread.currentThread().interrupt();
    }

    public RTInterruptedException() {
        super();
    }

    public RTInterruptedException(String message, Throwable cause) {
        super(message, cause);
    }

    public RTInterruptedException(String message) {
        super(message);
    }

    public RTInterruptedException(Throwable cause) {
        super(cause);
    }

}
