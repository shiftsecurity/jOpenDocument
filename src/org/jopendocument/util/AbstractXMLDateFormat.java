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

import java.text.Format;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

abstract class AbstractXMLDateFormat extends Format {

    // thread-safe since state-less
    protected final static DatatypeFactory factory;
    static {
        try {
            factory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            // shouldn't happen since an implementation is provided with the JRE
            throw new IllegalStateException(e);
        }
    }

    protected final TimeZone tz;
    protected final Locale locale;

    // null means VM default
    protected AbstractXMLDateFormat(final TimeZone timezone, final Locale aLocale) {
        this.tz = timezone == null ? null : (TimeZone) timezone.clone();
        // immutable
        this.locale = aLocale;
    }

    protected final TimeZone getTimeZone() {
        return this.tz == null ? TimeZone.getDefault() : this.tz;
    }

    protected final Locale getLocale() {
        return this.locale == null ? Locale.getDefault() : this.locale;
    }
}
