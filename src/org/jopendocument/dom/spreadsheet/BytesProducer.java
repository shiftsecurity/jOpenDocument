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

package org.jopendocument.dom.spreadsheet;

import org.jopendocument.dom.ODFrame;
import org.jopendocument.util.ImageInfo;
import org.jopendocument.util.ImageUtils;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jdom.Element;

abstract class BytesProducer {

    /**
     * The data of an image to put in <code>frame</code>.
     * 
     * @param frame the frame where this image will be put.
     * @return the corresponding bytes.
     */
    abstract byte[] getBytes(ODFrame<?> frame);

    /**
     * The format of the data returned by {@link #getBytes(Element)}.
     * 
     * @return the name of the format, <code>null</code> if unknown, eg "png".
     */
    abstract String getFormat();

    // *** concrete subclasses

    // a no-op Producer
    static final class ByteArrayProducer extends BytesProducer {

        private final byte[] data;
        private final boolean keepRatio;

        public ByteArrayProducer(byte[] data) {
            this(data, false);
        }

        public ByteArrayProducer(byte[] data, boolean keepRatio) {
            super();
            this.data = data;
            this.keepRatio = keepRatio;
        }

        @Override
        public byte[] getBytes(final ODFrame<?> frame) {
            if (this.keepRatio) {
                final ImageInfo info = new ImageInfo();
                info.setInput(new ByteArrayInputStream(this.data));
                if (!info.check())
                    throw new IllegalStateException("unable to parse the picture");
                final double imgRatio = info.getWidth() / (double) info.getHeight();

                final double ratio = frame.getRatio();

                // svg:x="0.075cm" svg:y="0.343cm"
                if (imgRatio > ratio) {
                    final double newFrameHeight = frame.getWidth().doubleValue() / imgRatio;
                    final double diff = frame.getHeight().doubleValue() - newFrameHeight;
                    frame.setSVGAttr("y", frame.getY().doubleValue() + diff / 2.0d);
                    frame.setSVGAttr("height", newFrameHeight);
                } else {
                    final double newFrameWidth = frame.getHeight().doubleValue() * imgRatio;
                    final double diff = frame.getWidth().doubleValue() - newFrameWidth;
                    frame.setSVGAttr("x", frame.getX().doubleValue() + diff / 2.0d);
                    frame.setSVGAttr("width", newFrameWidth);
                }

                // table:end-cell-address="Feuille1.F52" table:end-x="2.247cm" table:end-y="0.066cm"
                final Element frameElem = frame.getElement();
                frameElem.removeAttribute("end-cell-address", frameElem.getNamespace("table"));
                frameElem.removeAttribute("end-x", frameElem.getNamespace("table"));
                frameElem.removeAttribute("end-y", frameElem.getNamespace("table"));
            }

            return this.data;
        }

        @Override
        public String getFormat() {
            return null;
        }
    }

    // will generate a new png image (and can also keep ratio)
    static final class ImageProducer extends BytesProducer {

        private final Image img;
        private final boolean keepRatio;

        public ImageProducer(Image img, boolean keepRatio) {
            super();
            this.img = img;
            this.keepRatio = keepRatio;
        }

        @Override
        public byte[] getBytes(final ODFrame<?> frame) {
            final BufferedImage bImg;
            if (this.keepRatio) {
                final float ratio = (float) frame.getRatio();
                bImg = ImageUtils.createQualityResizedImage(this.img, ratio, true, Color.WHITE, true);
            } else
                bImg = ImageUtils.createQualityResizedImage(this.img, this.img.getWidth(null), this.img.getHeight(null), true, true, Color.WHITE, true);
            final ByteArrayOutputStream out = new ByteArrayOutputStream(1024 * 1024);
            try {
                ImageIO.write(bImg, getFormat(), out);
            } catch (IOException e) {
                throw new IllegalStateException("unable to export " + bImg + " to " + getFormat());
            }

            return out.toByteArray();
        }

        @Override
        public String getFormat() {
            return "png";
        }
    }
}