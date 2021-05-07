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

import java.util.HashMap;
import java.util.Map;

import ognl.Ognl;
import ognl.OgnlException;

/**
 * Contains data as regular Java objects.
 */
public class OGNLDataModel extends DataModel {

    private Map<String, Object> context;
    private Object root;

    /**
     * @param root the top-level Java bean or Map
     */
    @SuppressWarnings("unchecked")
    public OGNLDataModel(Object root) {
        this.root = root;
        this.context = Ognl.createDefaultContext(null);
        this.context.put("fmt", DataFormatter.getInstance());
    }

    @SuppressWarnings("unchecked")
    public OGNLDataModel(OGNLDataModel dm) {
        this.root = dm.root;
        final Map<String, Object> copy = new HashMap<String, Object>(dm.context);
        this.context = Ognl.addDefaultContext(Ognl.getRoot(dm.context), Ognl.getClassResolver(dm.context), Ognl.getTypeConverter(dm.context), Ognl.getMemberAccess(dm.context), copy);
    }

    @Override
    public DataModel copy() {
        return new OGNLDataModel(this);
    }

    @Override
    public void put(String name, Object value) {
        this.context.put(name, value);
    }

    @Override
    public void putAll(Map<? extends String, ?> toMerge) {
        this.context.putAll(toMerge);
    }

    @Override
    public Object _eval(String expression) throws OgnlException {
        return Ognl.getValue(expression, this.context, this.root);
    }

}
