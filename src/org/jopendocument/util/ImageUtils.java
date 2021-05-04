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

import org.jopendocument.util.FillMode.ZoomIn;
import org.jopendocument.util.FillMode.ZoomOut;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.ImageIcon;

/**
 * Tools for Image manipulation
 */
public class ImageUtils {

    public static BufferedImage createSmallerImage(File f, int maxWidth, int maxHeight) throws IOException {
        final ImageInfo info = new ImageInfo();
        final BufferedInputStream ins = new BufferedInputStream(new FileInputStream(f));
        info.setInput(ins);
        if (!info.check())
            throw new IllegalStateException("unable to parse the picture");
        ins.close();
        final int w = info.getWidth();
        final int h = info.getHeight();

        if (w <= maxWidth && h <= maxHeight) {
            return null;
        }
        final Image img = new ImageIcon(f.getAbsolutePath()).getImage();
        return createSmallerImage(img, maxWidth, maxHeight);
    }

    /**
     * Transform the passed image so that it fits into a rectangle of <code>maxWidth</code> by
     * <code>maxHeight</code>.
     * 
     * @param orginalImage the image to transform.
     * @param maxWidth the maximum width of the returned image.
     * @param maxHeight the maximum height of the returned image.
     * @return an image that fits or <code>null</code> if <code>original</code> already fits.
     */
    public static BufferedImage createSmallerImage(Image orginalImage, int maxWidth, int maxHeight) {
        final int w = orginalImage.getWidth(null);
        final int h = orginalImage.getHeight(null);
        if (w <= maxWidth && h <= maxHeight)
            return null;

        final int newWidth, newHeight;
        final float imageRatio = w / (float) h;
        final float finalRatio = maxWidth / maxHeight;
        if (imageRatio > finalRatio) {
            newWidth = maxWidth;
            newHeight = (int) (newWidth / imageRatio);
        } else {
            newHeight = maxHeight;
            newWidth = (int) (newHeight * imageRatio);
        }
        return createQualityResizedImage(orginalImage, newWidth, newHeight);
    }

    /**
     * Create a resized Image with high quality rendering, specifying a ratio rather than pixels.
     * 
     * @param orginalImage the original Image
     * @param finalRatio the ratio that the returned image will have.
     * @param applySoftFilter soft filter
     * @param bgColor the background color
     * @param fast algorithm to use for resampling.
     * @return the resized image of the given ratio.
     */
    public static BufferedImage createQualityResizedImage(Image orginalImage, float finalRatio, boolean applySoftFilter, Color bgColor, boolean fast) {
        int w = orginalImage.getWidth(null);
        int h = orginalImage.getHeight(null);
        float imageRatio = w / (float) h;
        final int newWidth, newHeight;
        if (finalRatio > imageRatio) {
            newHeight = h;
            newWidth = (int) (newHeight * finalRatio);
        } else {
            newWidth = w;
            newHeight = (int) (newWidth / finalRatio);
        }
        return createQualityResizedImage(orginalImage, newWidth, newHeight, false, true, bgColor, fast);
    }

    /**
     * Create a resized Image with high quality rendering
     * 
     * @param orginalImage the original Image
     * @param width the desired width
     * @param height the desired heights
     * @param applySoftFilter soft filter
     * @param keepRatio <code>true</code> to keep the ratio
     * @param bgColor the background color
     * @param fast algorithm to use for resampling.
     * @return the resized image of the given size
     */
    public static BufferedImage createQualityResizedImage(Image orginalImage, int width, int height, boolean applySoftFilter, boolean keepRatio, Color bgColor, boolean fast) {
        return createQualityResizedImage(orginalImage, width, height, applySoftFilter, keepRatio ? new ZoomOut(bgColor) : FillMode.STRETCH, fast);
    }

    public static BufferedImage createQualityResizedImage(Image orginalImage, int width, int height, boolean applySoftFilter, FillMode fillMode, boolean fast) {
        if (orginalImage == null) {
            throw new IllegalArgumentException("null argument");
        }

        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

        int quality = Image.SCALE_SMOOTH;
        if (fast) {
            quality = Image.SCALE_FAST;
        }

        if (!fillMode.isRatioKept()) {
            Image resizedImage = orginalImage.getScaledInstance(width, height, quality);
            // This code ensures that all the pixels in the image are loaded:
            // NE PAS VIRER, deja trop de temps perdu a debugger ca!
            Image temp = new ImageIcon(resizedImage).getImage();
            g2.drawImage(temp, 0, 0, null);
        } else {
            float W = width;
            float H = height;
            final int w = orginalImage.getWidth(null);
            final int h = orginalImage.getHeight(null);
            float imageRatio = w / (float) h;
            float finalRatio = W / H;

            if (fillMode instanceof ZoomOut) {
                // Clear background and paint the image.
                g2.setColor(((ZoomOut) fillMode).getBackgroundColor());
                g2.fillRect(0, 0, width, height);

                int newH, newW;
                if (finalRatio > imageRatio) {
                    newW = Math.round(H * imageRatio);
                    newH = Math.round(H);
                    Image resizedImage = orginalImage.getScaledInstance(newW, newH, quality);
                    // This code ensures that all the pixels in the image are loaded:
                    // NE PAS VIRER, deja trop de temps perdu a debugger ca!
                    Image temp = new ImageIcon(resizedImage).getImage();
                    g2.drawImage(temp, (int) ((W - newW) / 2), 0, null);
                } else {
                    newW = Math.round(W);
                    newH = Math.round(W / imageRatio);
                    Image resizedImage = orginalImage.getScaledInstance(newW, newH, quality);
                    // This code ensures that all the pixels in the image are loaded:
                    // NE PAS VIRER, deja trop de temps perdu a debugger ca!
                    Image temp = new ImageIcon(resizedImage).getImage();
                    g2.drawImage(temp, 0, (int) ((H - newH) / 2), null);
                }
            } else if (fillMode instanceof ZoomIn) {
                final ZoomIn zoomIn = (ZoomIn) fillMode;
                if (finalRatio > imageRatio) {
                    final int clippedH = Math.round(w / finalRatio);
                    final int sy1 = zoomIn.getPosition(h, clippedH);
                    final int sy2 = sy1 + clippedH;
                    g2.drawImage(orginalImage, 0, 0, width, height, 0, sy1, w, sy2, null);
                } else {
                    final int clippedW = Math.round(h * finalRatio);
                    final int sx1 = zoomIn.getPosition(w, clippedW);
                    final int sx2 = sx1 + clippedW;
                    g2.drawImage(orginalImage, 0, 0, width, height, sx1, 0, sx2, h, null);
                }
            }

        }

        g2.dispose();

        if (applySoftFilter) {
            return getSoftFilteredImage(bufferedImage);
        }

        return bufferedImage;
    }

    public static BufferedImage createQualityResizedImage(Image orginalImage, int width, int height) {
        return createQualityResizedImage(orginalImage, width, height, false, false, Color.WHITE, false);
    }

    public static BufferedImage createQualityResizedImage(Image orginalImage, int width, int height, boolean keepRatio) {
        return createQualityResizedImage(orginalImage, width, height, false, keepRatio, Color.WHITE, false);
    }

    /**
     * Create an soft filtered Image
     * 
     * @param bufferedImage the orignial BufferedImage
     * @return the soft filtered Image
     */
    public static BufferedImage getSoftFilteredImage(BufferedImage bufferedImage) {
        // soften

        float softenFactor = 0.01f;
        float[] softenArray = { 0, softenFactor, 0, softenFactor, 1 - (softenFactor * 3), softenFactor, 0, softenFactor, 0 };
        Kernel kernel = new Kernel(3, 3, softenArray);
        ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        bufferedImage = cOp.filter(bufferedImage, null);

        return bufferedImage;
    }

}
