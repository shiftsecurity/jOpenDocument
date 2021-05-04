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

package org.jopendocument.util;

import org.jopendocument.util.ExceptionUtils;
import org.jopendocument.util.ListMap;
import org.jopendocument.util.cc.IPredicate;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.validation.Schema;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public abstract class Validator {

    private final Document doc;
    private final XMLOutputter outputter;

    protected Validator(final Document doc, final IPredicate<Object> foreignPredicate) {
        super();
        this.doc = doc;
        this.outputter = new XMLOutputter(Format.getRawFormat()) {
            @Override
            protected void printElement(Writer out, Element element, int level, NamespaceStack namespaces) throws IOException {
                if (foreignPredicate == null || !foreignPredicate.evaluateChecked(element))
                    super.printElement(out, element, level, namespaces);
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void printAttributes(Writer out, @SuppressWarnings("rawtypes") List attributes, Element parent, NamespaceStack namespaces) throws IOException {
                final List<Attribute> l;
                if (foreignPredicate == null) {
                    l = attributes;
                } else {
                    final int stop = attributes.size();
                    l = new ArrayList<Attribute>(stop);
                    for (int i = 0; i < stop; i++) {
                        final Attribute attr = (Attribute) attributes.get(i);
                        if (!foreignPredicate.evaluateChecked(attr))
                            l.add(attr);
                    }
                }
                super.printAttributes(out, l, parent, namespaces);
            }
        };
    }

    protected final Document getDoc() {
        return this.doc;
    }

    protected final String getDocToValidate() {
        return this.outputter.outputString(this.getDoc());
    }

    /**
     * Validate a document, stopping at the first problem.
     * 
     * @return <code>null</code> if <code>doc</code> is valid, a String describing the first problem
     *         otherwise.
     */
    public abstract String isValid();

    /**
     * Validate the whole document.
     * 
     * @return all problems (with line number) indexed by type, e.g. ERROR unexpected attribute
     *         "style:join-border" => [on line 22:50, on line 14901:290].
     */
    public abstract Map<String, List<String>> validateCompletely();

    static public final class JAXPValidator extends Validator {

        private final Schema schema;

        /**
         * Validate a document using JAXP.
         * 
         * @param doc the document to validate
         * @param foreignPredicate <code>null</code> if <code>doc</code> should be validated as is,
         *        otherwise content matching it will not be validated.
         * @param schema the schema.
         */
        public JAXPValidator(final Document doc, final IPredicate<Object> foreignPredicate, final Schema schema) {
            super(doc, foreignPredicate);
            this.schema = schema;
        }

        @Override
        public String isValid() {
            final SAXException exn = JDOMUtils.validate(getDocToValidate(), this.schema, null);
            if (exn == null)
                return null;
            else if (exn instanceof SAXParseException)
                return exn.getLocalizedMessage() + " " + RecordingErrorHandler.getDesc((SAXParseException) exn);
            else
                return exn.getLocalizedMessage();
        }

        @Override
        public Map<String, List<String>> validateCompletely() {
            final RecordingErrorHandler recErrorHandler = new RecordingErrorHandler();
            final SAXException exn = JDOMUtils.validate(getDocToValidate(), this.schema, recErrorHandler);
            assert exn == null : "Exception thrown despite the error handler";
            return recErrorHandler.getMap();
        }
    }

    static public final class DTDValidator extends Validator {

        private final SAXBuilder b;

        public DTDValidator(final Document doc) {
            this(doc, null, new SAXBuilder());
        }

        /**
         * Validate a document using its DTD.
         * 
         * @param doc the document to validate
         * @param foreignPredicate <code>null</code> if <code>doc</code> should be validated as is,
         *        otherwise content matching it will not be validated.
         * @param b a builder which can resolve doc's DTD.
         */
        public DTDValidator(final Document doc, final IPredicate<Object> foreignPredicate, final SAXBuilder b) {
            super(doc, foreignPredicate);
            this.b = b;
        }

        @Override
        public String isValid() {
            try {
                JDOMUtils.validateDTD(getDocToValidate(), this.b, null);
                return null;
            } catch (JDOMException e) {
                return ExceptionUtils.getStackTrace(e);
            }
        }

        @Override
        public Map<String, List<String>> validateCompletely() {
            try {
                final RecordingErrorHandler recErrorHandler = new RecordingErrorHandler();
                JDOMUtils.validateDTD(getDocToValidate(), this.b, recErrorHandler);
                return recErrorHandler.getMap();
            } catch (JDOMException e) {
                throw new IllegalStateException("Unable to read the document", e);
            }
        }

    }

    private static final class RecordingErrorHandler implements ErrorHandler {
        private final ListMap<String, String> res;

        private RecordingErrorHandler() {
            this(new ListMap<String, String>());
        }

        private RecordingErrorHandler(ListMap<String, String> res) {
            this.res = res;
        }

        public final ListMap<String, String> getMap() {
            return this.res;
        }

        @Override
        public void warning(SAXParseException e) throws SAXException {
            addExn("WARNING", e);
        }

        @Override
        public void fatalError(SAXParseException e) throws SAXException {
            addExn("FATAL", e);
        }

        @Override
        public void error(SAXParseException e) throws SAXException {
            addExn("ERROR", e);
        }

        private void addExn(final String level, SAXParseException e) {
            // e.g. ERROR unexpected attribute "style:join-border" => on line 14901:290
            this.res.add(level + " " + e.getMessage(), getDesc(e));
        }

        static String getDesc(SAXParseException e) {
            final String f = e.getSystemId() == null ? "" : " of document " + e.getSystemId();
            return "on line " + e.getLineNumber() + ":" + e.getColumnNumber() + f;
        }
    }
}