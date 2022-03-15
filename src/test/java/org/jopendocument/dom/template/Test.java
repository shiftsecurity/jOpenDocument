/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 2008 jOpenDocument, by ILM Informatique. All rights reserved.
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

package org.jopendocument.dom.template;

import java.util.List;

import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

public class Test {
	 public static void main(String[] args) {
	        // Obtain the manager object
	        ScriptEngineManager mgr = new ScriptEngineManager();
	      
	          // Obtain a list of factories
	          List<ScriptEngineFactory> list = mgr.getEngineFactories();
	          System.out.println("Supported Script Engines");
	        for (ScriptEngineFactory factory: list) {
	            // Obtains the full name of the ScriptEngine.
	                String name = factory.getEngineName();
	                String version = factory.getEngineVersion();
	                // Returns the name of the scripting language 
	            // supported by this ScriptEngine
	                String language = factory.getLanguageName();
	                String languageVersion = factory.getLanguageVersion();
	                System.out.printf(
	                 "Name: %s (%s) : Language: %s v. %s \n", 
	                    name, version, language, languageVersion);
	           // Get a list of aliases
	             List<String> engNames = factory.getNames();
	                 for(String e: engNames) {
	                       System.out.printf("\tEngine Alias: %s\n", e);
	                   }
	             }
	        }
}
