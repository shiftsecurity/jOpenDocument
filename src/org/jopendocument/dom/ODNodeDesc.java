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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.jcip.annotations.GuardedBy;

import org.jdom.Element;
import org.jdom.Parent;
import org.jdom.filter.ElementFilter;

/**
 * Describe an OpenDocument node.
 * 
 * @author Sylvain
 * 
 * @param <N> type of node.
 */
// not compatible with StyleDesc since they have a XMLVersion (this class has none and its methods
// take a XMLFormatVersion)
public abstract class ODNodeDesc<N extends ODNode> {

    static private ODNodeDescRegistry REGISTRY;

    public static synchronized ODNodeDescRegistry getRegistry() {
        if (REGISTRY == null)
            REGISTRY = new ODNodeDescRegistry();
        return REGISTRY;
    }

    private final Class<N> nodeClass;
    @GuardedBy("this")
    private final Map<XMLFormatVersion, ElementFilter> filters;

    protected ODNodeDesc(final Class<N> nodeClass) {
        this.nodeClass = nodeClass;
        this.filters = new HashMap<XMLFormatVersion, ElementFilter>();
    }

    public final Class<N> getNodeClass() {
        return this.nodeClass;
    }

    public abstract Element createProto(final XMLFormatVersion vers);

    public synchronized final ElementFilter getFilter(final XMLFormatVersion vers) {
        ElementFilter res = this.filters.get(vers);
        if (res == null) {
            final Element proto = this.createProto(vers);
            res = new ElementFilter(proto.getName(), proto.getNamespace());
            this.filters.put(vers, res);
        }
        return res;
    }

    public static class Children<N extends ODNode> {

        private final ODNodeDesc<N> desc;
        private final ODDocument doc;
        private final XMLFormatVersion vers;
        private final List<Element> children;

        @SuppressWarnings("unchecked")
        protected Children(final ODNodeDesc<N> desc, final ODDocument doc, final XMLFormatVersion vers, final Parent p) {
            super();
            if (vers == null)
                throw new NullPointerException("Null version");
            if (doc != null && !doc.getFormatVersion().equals(vers))
                throw new IllegalArgumentException("Version mismatch");
            this.doc = doc;
            this.desc = desc;
            this.vers = vers;
            this.children = Collections.unmodifiableList(p.getContent(desc.getFilter(vers)));
        }

        protected final ODNodeDesc<N> getDesc() {
            return this.desc;
        }

        public final XMLFormatVersion getVersion() {
            return this.vers;
        }

        public final List<Element> getList() {
            return this.children;
        }

        public final int getCount() {
            return this.children.size();
        }

        public final N get(final int i) {
            return this.get(null, i);
        }

        public final N get(ODDocument doc, final int i) {
            if (doc == null) {
                doc = this.doc;
            } else {
                // this.doc already checked
                if (!doc.getFormatVersion().equals(this.getVersion()))
                    throw new IllegalArgumentException("Version mismatch");
            }
            if (doc == null)
                return getDocumentLess(i);
            else
                // the following line checks that the element is in doc
                return this.desc.wrapNode(doc, this.children.get(i));
        }

        protected N getDocumentLess(final int i) {
            throw new NullPointerException("Null document");
        }
    }

    public Children<N> getChildren(final ODDocument doc, final Parent p) {
        return new Children<N>(this, doc, doc.getFormatVersion(), p);
    }

    public Children<N> getChildren(final XMLFormatVersion vers, final Parent p) {
        return new Children<N>(this, null, vers, p);
    }

    public abstract N wrapNode(final ODDocument doc, final Element e);
}
