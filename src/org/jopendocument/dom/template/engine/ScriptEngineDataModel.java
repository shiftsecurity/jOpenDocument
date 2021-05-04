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

import java.util.Map;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

/**
 * A data model using a {@link javax.script.ScriptEngine}.
 * 
 * @author Sylvain
 */
public class ScriptEngineDataModel extends DataModel {
    private final ScriptEngine engine;
    private final Bindings bindings;

    public ScriptEngineDataModel(ScriptEngine e) {
        this.engine = e;
        this.bindings = new SimpleBindings();
        this.bindings.putAll(this.engine.getBindings(ScriptContext.ENGINE_SCOPE));
    }

    public final ScriptEngine getEngine() {
        return this.engine;
    }

    @Override
    public Object _eval(String script) throws ScriptException {
        return this.engine.eval(script, this.bindings);
    }

    @Override
    public void put(String key, Object value) {
        this.bindings.put(key, value);
    }

    @Override
    public void putAll(Map<? extends String, ?> toMerge) {
        this.bindings.putAll(toMerge);
    }

    @Override
    public DataModel copy() {
        final ScriptEngineDataModel res = new ScriptEngineDataModel(this.getEngine());
        res.bindings.putAll(this.bindings);
        return res;
    }
}
