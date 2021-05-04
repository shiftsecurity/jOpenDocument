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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class PropertiesUtils {

    public static final Properties createFromMap(final Map<String, String> map) {
        final Properties res = new Properties();
        for (final Entry<String, String> e : map.entrySet()) {
            res.setProperty(e.getKey(), e.getValue());
        }
        return res;
    }

    public static final Properties createFromFile(final File f) throws IOException {
        return create(new BufferedInputStream(new FileInputStream(f)));
    }

    public static final Properties createFromResource(final Class<?> ctxt, final String rsrc) throws IOException {
        return create(ctxt.getResourceAsStream(rsrc));
    }

    protected static final Properties create(final InputStream stream) throws IOException {
        return create(stream, true);
    }

    public static final Properties create(final InputStream stream, final boolean close) throws IOException {
        if (stream != null) {
            try {
                final Properties res = new Properties();
                res.load(stream);
                return res;
            } finally {
                if (close)
                    stream.close();
            }
        } else {
            return null;
        }
    }

    public static final void load(final Properties props, final Properties toLoad) {
        for (final String key : toLoad.stringPropertyNames()) {
            final String value = toLoad.getProperty(key);
            assert value != null;
            props.setProperty(key, value);
        }
    }
}
