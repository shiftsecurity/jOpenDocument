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

package org.jopendocument.dom.style;

import org.jopendocument.util.CompareUtils;

import java.util.HashMap;
import java.util.Map;

public enum RelationalOperator {
    LT("<") {
        @Override
        protected boolean evaluate(int i) {
            return i < 0;
        }
    },
    GT(">") {
        @Override
        protected boolean evaluate(int i) {
            return i > 0;
        }
    },
    LE("<=") {
        @Override
        protected boolean evaluate(int i) {
            return i <= 0;
        }
    },
    GE(">=") {
        @Override
        protected boolean evaluate(int i) {
            return i >= 0;
        }
    },
    EQ("=") {
        @Override
        protected boolean evaluate(int i) {
            return i == 0;
        }
    },
    NE("!=") {
        @Override
        protected boolean evaluate(int i) {
            return !EQ.evaluate(i);
        }
    };

    private final String s;

    private RelationalOperator(final String s) {
        this.s = s;
    }

    public final String asString() {
        return this.s;
    }

    public final boolean compare(final Object o1, final Object o2) {
        return this.evaluate(CompareUtils.compare(o1, o2));
    }

    protected abstract boolean evaluate(int i);

    /**
     * Regular expression with all operators.
     * 
     * <pre>
     * &lt;|&gt;|&lt;=|&gt;=|=|!=
     * </pre>
     */
    public static final String OR_PATTERN;
    private static final Map<String, RelationalOperator> instances;
    static {
        instances = new HashMap<String, RelationalOperator>();
        final StringBuilder sb = new StringBuilder(32);
        for (final RelationalOperator op : values()) {
            instances.put(op.s, op);
            sb.append(op.asString());
            sb.append('|');
        }
        // remove last |
        sb.setLength(sb.length() - 1);
        OR_PATTERN = sb.toString();
    }

    public static RelationalOperator getInstance(String op) {
        return instances.get(op);
    }
}