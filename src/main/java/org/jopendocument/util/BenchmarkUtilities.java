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

import org.jopendocument.model.OpenDocument;
import org.jopendocument.renderer.ODTRenderer;

public class BenchmarkUtilities {

    public void render(OpenDocument doc, int iteration) {
        ODTRenderer renderer = new ODTRenderer(doc);
        System.out.println("Rendering");
        DummyGraphics2D g2 = new DummyGraphics2D();
        long t1 = System.nanoTime();
        for (int i = 0; i < iteration; i++) {
          
            renderer.paintComponent(g2);
        }

        long t2 = System.nanoTime();

        System.out.println("Rendering in :" + (t2 - t1) / 1000000 + " ms");
    }

}
