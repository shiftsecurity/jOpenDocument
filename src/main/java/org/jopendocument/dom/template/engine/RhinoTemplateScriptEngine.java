package org.jopendocument.dom.template.engine;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class RhinoTemplateScriptEngine extends DataModel {
    private Context cx;
    private Scriptable scope;

    public RhinoTemplateScriptEngine() {
        cx = ContextFactory.getGlobal().enterContext();
        scope = cx.initStandardObjects();
    }

    @Override
    protected Object _eval(String script) {
        return Context.jsToJava(cx.evaluateString(scope, script, "", 1, null), Object.class);
    }

    @Override
    public void put(String key, Object value) {
        Object jsObj = Context.javaToJS(value, scope);
        ScriptableObject.putProperty(scope, key, jsObj);
    }

    @Override
    public DataModel copy() {
        throw new UnsupportedOperationException();
    }

}
