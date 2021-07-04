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

import org.jopendocument.util.JDOMUtils;
import net.jcip.annotations.ThreadSafe;

import org.jdom.Element;

@ThreadSafe
public final class EventListener {
    private final Element elem;
    private final String name;

    EventListener(final Element elem) {
        assert elem.getNamespacePrefix().equals("script");
        this.elem = elem;
        this.name = elem.getAttributeValue("event-name", elem.getNamespace());
    }

    public final Element getElement() {
        return this.elem;
    }

    public final String getName() {
        return this.name;
    }

    public final String getLanguage() {
        return this.elem.getAttributeValue("language", this.elem.getNamespace());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final EventListener other = (EventListener) obj;
        // first cheap comparison
        return this.name.equals(other.name) && JDOMUtils.equalsDeep(this.elem, other.elem);
    }
}
