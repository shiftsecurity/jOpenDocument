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

import org.jopendocument.dom.template.TemplateException;
import org.jopendocument.dom.template.statements.ForEach;
import org.jopendocument.dom.template.statements.If;
import org.jopendocument.dom.template.statements.Include;
import org.jopendocument.dom.template.statements.SetStmt;
import org.jopendocument.dom.template.statements.Statement;
import org.jopendocument.util.JDOMUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.jdom.Element;
import org.jdom.filter.ElementFilter;

/**
 * Parse material, which can then be executed with {@link #execute(DataModel)} .
 * 
 * @param <E> type of the material this parser works on.
 * @author Sylvain
 */
public class Parsed<E> {

    static private Set<Statement> createDefaultStatements() {
        final Set<Statement> res = new HashSet<Statement>();
        res.add(new ForEach());
        res.add(new If());
        res.add(new SetStmt());
        res.add(new Include());
        return res;
    }

    private final Material<E> src;
    private final Map<String, Statement> statements;

    public Parsed(Material<E> src) throws TemplateException {
        this(src, createDefaultStatements());
    }

    /**
     * Parse <code>src</code> using the same settings as <code>p</code>.
     * 
     * @param src the material to parse.
     * @param p settings.
     * @throws TemplateException if an error occurs.
     */
    public Parsed(Material<E> src, Parsed<?> p) throws TemplateException {
        this(src, new HashSet<Statement>(p.statements.values()));
    }

    public Parsed(Material<E> src, Set<Statement> statements) throws TemplateException {
        this.src = src;

        this.statements = new HashMap<String, Statement>(statements.size());
        for (final Statement stmt : statements) {
            this.statements.put(stmt.getName(), stmt);
        }

        this.prepare(statements);
    }

    private void prepare(final Set<Statement> statementsDef) throws TemplateException {
        if (this.src.hasRoot()) {
            final Map<Element, Statement> statements = new LinkedHashMap<Element, Statement>();
            final Iterator<Element> iter = this.src.getRoot().getDescendants(new ElementFilter());
            while (iter.hasNext()) {
                final Element e = iter.next();
                for (final Statement stmt : statementsDef) {
                    if (stmt.matches(e)) {
                        statements.put(e, stmt);
                        break;
                    }
                }
            }
            for (final Element e : statements.keySet()) {
                try {
                    statements.get(e).prepare(e);
                } catch (Exception exn) {
                    throw new TemplateException("Couldn't prepare " + JDOMUtils.output(e), exn);
                }
            }
        }
    }

    public Material<E> getSource() {
        return this.src;
    }

    public Statement getStatement(String name) {
        return this.statements.get(name);
    }

    public E execute(final DataModel data) throws TemplateException {
        if (!this.getSource().hasRoot()) {
            return this.getSource().getWhole();
        }
        return new Processor<E>(this, data).process().getWhole();
    }
}
