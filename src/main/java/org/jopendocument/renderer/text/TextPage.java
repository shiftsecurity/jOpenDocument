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

package org.jopendocument.renderer.text;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import org.jopendocument.model.Page;

public class TextPage extends Page {
    List<RenderBlock> blocks = new ArrayList<RenderBlock>();

    public void render(Graphics2D g2) {
        g2.setFont(new JLabel().getFont().deriveFont(12f));
      //  g2.drawString(this.toString(), 13, 13);
        int stop = blocks.size();
        for (int i = 0; i < stop; i++) {
            RenderBlock b = blocks.get(i);
            b.render(g2);
        }
    }

    public void add(RenderBlock b) {
        this.blocks.add(b);

    }
}
