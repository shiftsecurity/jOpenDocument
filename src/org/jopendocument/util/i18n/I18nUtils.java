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

package org.jopendocument.util.i18n;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

import net.jcip.annotations.ThreadSafe;

public final class I18nUtils {

    // superclass is thread-safe and so is this
    @ThreadSafe
    static public class SameLanguageControl extends Control {
        @Override
        public List<Locale> getCandidateLocales(String baseName, Locale locale) {
            List<Locale> res = super.getCandidateLocales(baseName, locale);
            final int superSize = res.size();
            assert res.get(superSize - 1) == Locale.ROOT;
            // as per the documentation (if the given locale is equal to Locale.ROOT...)
            if (superSize == 1)
                return res;
            // remove Locale.ROOT
            res = res.subList(0, superSize - 1);
            assert res.get(res.size() - 1).getLanguage().equals(locale.getLanguage());
            return res;
        }

        @Override
        public Locale getFallbackLocale(String baseName, Locale locale) {
            if (baseName == null)
                throw new NullPointerException();
            return null;
        }
    }

    static private final SameLanguageControl INSTANCE = new SameLanguageControl();

    static public final String getPackageName(final Class<?> c) {
        return c.getPackage().getName() + ".translation";
    }

    static protected final String getBaseName(final Class<?> c) {
        return getPackageName(c) + ".messages";
    }

    static public final String RSRC_BASENAME = getBaseName(I18nUtils.class);
    static public final String TRUE_KEY = "true_key";
    static public final String FALSE_KEY = "false_key";
    static public final String YES_KEY = "yes_key";
    static public final String NO_KEY = "no_key";

    /**
     * Returns a Control that only loads bundle with the requested language. I.e. no fallback and no
     * base bundle.
     * 
     * @return a control only loading the requested language.
     * @see ResourceBundle#getBundle(String, Locale, ClassLoader, Control)
     */
    static public Control getSameLanguageControl() {
        return INSTANCE;
    }

    static public final String getBooleanKey(final boolean b) {
        return b ? TRUE_KEY : FALSE_KEY;
    }

    static public final String getYesNoKey(final boolean b) {
        return b ? YES_KEY : NO_KEY;
    }

    /**
     * Convert a string into a Locale Object. Waiting for <code>Locale.forLanguageTag()</code> in
     * java 7.
     * 
     * @param localeString a String returned from {@link Locale#toString()}.
     * @return the Locale.
     */
    public static Locale createLocaleFromString(final String localeString) {
        if (localeString == null)
            return null;

        final String language, country, variant;
        final int languageIndex = localeString.indexOf('_');
        if (languageIndex == -1) {
            language = localeString;
            country = "";
            variant = "";
        } else {
            language = localeString.substring(0, languageIndex);
            final int countryIndex = localeString.indexOf('_', languageIndex + 1);
            if (countryIndex == -1) {
                country = localeString.substring(languageIndex + 1);
                variant = "";
            } else {
                // all remaining is the variant
                country = localeString.substring(languageIndex + 1, countryIndex);
                variant = localeString.substring(countryIndex + 1);
            }
        }

        return new Locale(language, country, variant);
    }
}
