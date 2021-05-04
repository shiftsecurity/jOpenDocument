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
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.RenderingHints.Key;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;

public final class DummyGraphics2D extends Graphics2D {

    @Override
    public final void addRenderingHints(Map<?, ?> hints) {
    }

    @Override
    public final void clip(Shape s) {
    }

    @Override
    public final void draw(Shape s) {
    }

    @Override
    public final void drawGlyphVector(GlyphVector g, float x, float y) {
    }

    @Override
    public final boolean drawImage(Image img, AffineTransform xform, ImageObserver obs) {
        return true;
    }

    @Override
    public final void drawImage(BufferedImage img, BufferedImageOp op, int x, int y) {
    }

    @Override
    public final void drawRenderableImage(RenderableImage img, AffineTransform xform) {
    }

    @Override
    public final void drawRenderedImage(RenderedImage img, AffineTransform xform) {
    }

    @Override
    public final void drawString(String str, int x, int y) {
    }

    @Override
    public final void drawString(String str, float x, float y) {
    }

    @Override
    public final void drawString(AttributedCharacterIterator iterator, int x, int y) {
    }

    @Override
    public final void drawString(AttributedCharacterIterator iterator, float x, float y) {
    }

    @Override
    public final void fill(Shape s) {
    }

    @Override
    public final Color getBackground() {
        return null;
    }

    @Override
    public final Composite getComposite() {
        return null;
    }

    @Override
    public final GraphicsConfiguration getDeviceConfiguration() {
        return null;
    }

    @Override
    public final FontRenderContext getFontRenderContext() {
        return null;
    }

    @Override
    public final Paint getPaint() {
        return null;
    }

    @Override
    public final Object getRenderingHint(Key hintKey) {
        return null;
    }

    @Override
    public final RenderingHints getRenderingHints() {
        return null;
    }

    @Override
    public final Stroke getStroke() {
        return null;
    }

    @Override
    public final AffineTransform getTransform() {
        return null;
    }

    @Override
    public final boolean hit(Rectangle rect, Shape s, boolean onStroke) {
        return false;
    }

    @Override
    public final void rotate(double theta) {
    }

    @Override
    public final void rotate(double theta, double x, double y) {
    }

    @Override
    public final void scale(double sx, double sy) {
    }

    @Override
    public final void setBackground(Color color) {
    }

    @Override
    public final void setComposite(Composite comp) {
    }

    @Override
    public final void setPaint(Paint paint) {
    }

    @Override
    public final void setRenderingHint(Key hintKey, Object hintValue) {
    }

    @Override
    public final void setRenderingHints(Map<?, ?> hints) {
    }

    @Override
    public final void setStroke(Stroke s) {
    }

    @Override
    public final void setTransform(AffineTransform Tx) {
    }

    @Override
    public final void shear(double shx, double shy) {
    }

    @Override
    public final void transform(AffineTransform Tx) {
    }

    @Override
    public final void translate(int x, int y) {
    }

    @Override
    public final void translate(double tx, double ty) {
    }

    @Override
    public final void clearRect(int x, int y, int width, int height) {
    }

    @Override
    public final void clipRect(int x, int y, int width, int height) {
    }

    @Override
    public final void copyArea(int x, int y, int width, int height, int dx, int dy) {
    }

    @Override
    public final Graphics create() {
        return this;
    }

    @Override
    public final void dispose() {
    }

    @Override
    public final void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
    }

    @Override
    public final boolean drawImage(Image img, int x, int y, ImageObserver observer) {
        return false;
    }

    @Override
    public final boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
        return false;
    }

    @Override
    public final boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
        return false;
    }

    @Override
    public final boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
        return false;
    }

    @Override
    public final boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, ImageObserver observer) {
        return false;
    }

    @Override
    public final boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, Color bgcolor, ImageObserver observer) {
        return false;
    }

    @Override
    public final void drawLine(int x1, int y1, int x2, int y2) {
    }

    @Override
    public final void drawOval(int x, int y, int width, int height) {
    }

    @Override
    public final void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
    }

    @Override
    public final void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
    }

    @Override
    public final void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
    }

    @Override
    public final void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
    }

    @Override
    public final void fillOval(int x, int y, int width, int height) {
    }

    @Override
    public final void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
    }

    @Override
    public final void fillRect(int x, int y, int width, int height) {
    }

    @Override
    public final void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
    }

    @Override
    public final Shape getClip() {
        return null;
    }

    @Override
    public final Rectangle getClipBounds() {
        return null;
    }

    @Override
    public final Color getColor() {
        return null;
    }

    @Override
    public final Font getFont() {
        return null;
    }

    @Override
    public final FontMetrics getFontMetrics(Font f) {
        return new DummyFontMetrics(f);
    }

    @Override
    public final void setClip(Shape clip) {
    }

    @Override
    public final void setClip(int x, int y, int width, int height) {
    }

    @Override
    public final void setColor(Color c) {
    }

    @Override
    public final void setFont(Font font) {
    }

    @Override
    public final void setPaintMode() {
    }

    @Override
    public final void setXORMode(Color c1) {
    }

}
