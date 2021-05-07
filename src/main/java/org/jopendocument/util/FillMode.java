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

import javax.swing.SwingConstants;

/**
 * Specify how an image fills an area.
 * 
 * @author Sylvain CUAZ
 */
public class FillMode {
    /** The whole image is stretched to fill the whole area */
    public static final FillMode STRETCH = new FillMode(false);
    public static final FillMode ZOOM_CENTER = new ZoomIn(SwingConstants.CENTER);
    public static final FillMode ZOOM_LEADING = new ZoomIn(SwingConstants.LEADING);
    public static final FillMode ZOOM_TRAILING = new ZoomIn(SwingConstants.TRAILING);

    private final boolean keepRatio;

    protected FillMode(boolean keepRatio) {
        super();
        this.keepRatio = keepRatio;
    }

    public final boolean isRatioKept() {
        return this.keepRatio;
    }

    public static class ZoomOut extends FillMode {

        private final Color bg;

        /**
         * The image keeps its ratio and is centred in the area, leaving two outer stripes.
         * 
         * @param bg the colour of the 2 stripes.
         */
        public ZoomOut(final Color bg) {
            super(true);
            this.bg = bg;
        }

        public final Color getBackgroundColor() {
            return this.bg;
        }

        // TODO support position
    }

    public static class ZoomIn extends FillMode {

        private final int position;

        /**
         * The image keeps its ratio but only a part of it is used, avoiding outer stripes.
         * 
         * @param position what part of the image should be used, can be
         *        {@link SwingConstants#CENTER}, {@link SwingConstants#LEADING} or
         *        {@link SwingConstants#TRAILING}.
         */
        public ZoomIn(final int position) {
            super(true);
            if (position != SwingConstants.CENTER && position != SwingConstants.LEADING && position != SwingConstants.TRAILING)
                throw new IllegalArgumentException("Neither center, leading nor trailing");
            this.position = position;
        }

        public final int getPosition() {
            return this.position;
        }

        // if external is the length of a rectangle and internal the length of an inner rectangle
        // then return the offset of the internal rectangle
        public final int getPosition(final int external, final int internal) {
            if (external < internal)
                throw new IllegalArgumentException("External < Internal : " + external + " < " + internal);
            final int res;
            if (getPosition() == SwingConstants.CENTER) {
                res = (external - internal) / 2;
            } else if (getPosition() == SwingConstants.LEADING) {
                res = 0;
            } else if (getPosition() == SwingConstants.TRAILING) {
                res = external - internal;
            } else {
                throw new IllegalStateException("Unkwown position");
            }
            return res;
        }
    }
}
