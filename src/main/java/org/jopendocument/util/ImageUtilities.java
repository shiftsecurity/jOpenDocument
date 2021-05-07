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

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.RepaintManager;

import org.jopendocument.renderer.ODTRenderer;

/**
 * Tools for Image manipulation
 */
public class ImageUtilities {

    private ODTRenderer renderer;

    public ImageUtilities(ODTRenderer renderer) {
        this.renderer = renderer;
    }

    public void saveAsPng(String string) {
        try {
            // Save as PNG
            File file = new File(string);
            int width = 1000;
            int height = 1000;

            // Create a buffered image in which to draw
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            // Create a graphics contents on the buffered image
            Graphics2D g2d = bufferedImage.createGraphics();

            // Draw graphics
            disableDoubleBuffering(renderer);
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
            renderer.print(g2d);
            g2d.setColor(Color.red);
            g2d.drawLine(10, 10, 30, 30);

            // Graphics context no longer needed so dispose it
            g2d.dispose();

            ImageIO.write(bufferedImage, "png", file);
            enableDoubleBuffering(renderer);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void disableDoubleBuffering(Component c) {
        RepaintManager currentManager = RepaintManager.currentManager(c);
        currentManager.setDoubleBufferingEnabled(false);
    }

    /** Re-enables double buffering globally. */

    public static void enableDoubleBuffering(Component c) {
        RepaintManager currentManager = RepaintManager.currentManager(c);
        currentManager.setDoubleBufferingEnabled(true);
    }

}
