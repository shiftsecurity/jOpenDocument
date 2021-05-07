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

import org.jopendocument.dom.text.Heading;
import org.jopendocument.dom.text.Paragraph;
import org.jopendocument.dom.text.Span;

import java.util.HashMap;
import java.util.Map;

import net.jcip.annotations.GuardedBy;

import org.jdom.Element;

/**
 * Store all known descriptors.
 * 
 * @author Sylvain
 */
public final class ODNodeDescRegistry {

    private final Map<Class<? extends ODNode>, ODNodeDesc<?>> byClass = new HashMap<Class<? extends ODNode>, ODNodeDesc<?>>();
    @GuardedBy("byVersion")
    private final Map<XMLFormatVersion, Map<String, ODNodeDesc<?>>> byVersion = new HashMap<XMLFormatVersion, Map<String, ODNodeDesc<?>>>(8);

    ODNodeDescRegistry() {
        registerDesc(Span.NODE_DESC);
        registerDesc(Paragraph.NODE_DESC);
        registerDesc(Heading.NODE_DESC);
    }

    private final void registerDesc(final ODNodeDesc<?> desc) {
        if (this.byClass.put(desc.getNodeClass(), desc) != null)
            throw new IllegalStateException("Two NodeDesc with same node class : " + desc.getNodeClass());
    }

    private Map<Class<? extends ODNode>, ODNodeDesc<?>> getDESCS() {
        return this.byClass;
    }

    private final Map<String, ODNodeDesc<?>> getDescByElemName(final XMLFormatVersion vers) {
        synchronized (this.byVersion) {
            Map<String, ODNodeDesc<?>> res = this.byVersion.get(vers);
            if (res == null) {
                res = new HashMap<String, ODNodeDesc<?>>(getDESCS().size());
                for (final ODNodeDesc<?> desc : getDESCS().values()) {
                    final String elemName = desc.createProto(vers).getName();
                    // use name space if this happens
                    if (res.put(elemName, desc) != null)
                        throw new IllegalStateException("Two NodeDesc with same element name : " + elemName);
                }
                this.byVersion.put(vers, res);
            }
            return res;
        }
    }

    final ODNodeDesc<?> getDesc(final XMLFormatVersion vers, final Element e) {
        return getDescByElemName(vers).get(e.getName());
    }

    public final <N extends ODNode> ODNodeDesc<N> getDesc(final Class<N> nodeClass) {
        @SuppressWarnings("unchecked")
        final ODNodeDesc<N> res = (ODNodeDesc<N>) getDESCS().get(nodeClass);
        assert res == null || res.getNodeClass() == nodeClass;
        return res;
    }

    /**
     * Create a node for the passed element.
     * 
     * @param doc the document
     * @param e an element in <code>doc</code>.
     * @return a new node.
     * @see ODNodeDesc#wrapNode(ODDocument, Element)
     */
    public final ODNode wrap(final ODDocument doc, final Element e) {
        if (doc.getPackage().getXMLFile(e.getDocument()) == null)
            throw new IllegalArgumentException("Element not in " + doc);
        final ODNodeDesc<?> desc = this.getDesc(doc.getFormatVersion(), e);
        if (desc == null)
            return null;
        else
            return desc.wrapNode(doc, e);
    }
}
