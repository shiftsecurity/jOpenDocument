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

public class StyleTableProperties {

    private boolean isDisplayed;
    private String writingMode;
    private String borderMode;

    public void setDisplay(boolean b) {
        this.isDisplayed = b;

    }

    public void setWritingMode(String value) {
        this.writingMode = value;

    }

    public void setBorderModel(String value) {
        this.borderMode = value;

    }

}
