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

package org.jopendocument.dom.template.engine;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Contains utility methods to format dates and numbers.
 * 
 * An instance of this class is automatically available for use in template expressions as
 * <tt>#fmt</tt>.
 */
public class DataFormatter {
    private static DataFormatter instance = new DataFormatter();

    private DataFormatter() {
    }

    public synchronized static DataFormatter getInstance() {
        return instance;
    }

    public String formatDate(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public String formatNumber(Number number, String pattern) {
        return new DecimalFormat(pattern).format(number);
    }

}
