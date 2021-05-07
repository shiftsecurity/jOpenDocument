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

package org.jopendocument.io.svm;

import org.jopendocument.util.StreamUtils;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Inflater;

import org.jopendocument.io.LittleEndianInputStream;
import org.jopendocument.util.Log;

/**
 * Work in progress of a SVM Reader. It handles StarView metafiles : an undocumented format used in
 * StarOffice, OpenOffice, LibreOffice... This code reflects all the hacks used in SVM files to
 * encode BMP ;)
 * */
public class SVMReader {
    public static final int META_NULL_ACTION = 0;
    public static final int META_PIXEL_ACTION = 100;
    public static final int META_POINT_ACTION = 101;
    public static final int META_LINE_ACTION = 102;
    public static final int META_RECT_ACTION = 103;
    public static final int META_ROUNDRECT_ACTION = 104;
    public static final int META_ELLIPSE_ACTION = 105;
    public static final int META_ARC_ACTION = 106;
    public static final int META_PIE_ACTION = 107;
    public static final int META_CHORD_ACTION = 108;
    public static final int META_POLYLINE_ACTION = 109;
    public static final int META_POLYGON_ACTION = 110;
    public static final int META_POLYPOLYGON_ACTION = 111;
    public static final int META_TEXT_ACTION = 112;
    public static final int META_TEXTARRAY_ACTION = 113;
    public static final int META_STRETCHTEXT_ACTION = 114;
    public static final int META_TEXTRECT_ACTION = 115;
    public static final int META_BMP_ACTION = 116;
    public static final int META_BMPSCALE_ACTION = 117;
    public static final int META_BMPSCALEPART_ACTION = 118;
    public static final int META_BMPEX_ACTION = 119;
    public static final int META_BMPEXSCALE_ACTION = 120;
    public static final int META_BMPEXSCALEPART_ACTION = 121;
    public static final int META_MASK_ACTION = 122;
    public static final int META_MASKSCALE_ACTION = 123;
    public static final int META_MASKSCALEPART_ACTION = 124;
    public static final int META_GRADIENT_ACTION = 125;
    public static final int META_HATCH_ACTION = 126;
    public static final int META_WALLPAPER_ACTION = 127;
    public static final int META_CLIPREGION_ACTION = 128;
    public static final int META_ISECTRECTCLIPREGION_ACTION = 129;
    public static final int META_ISECTREGIONCLIPREGION_ACTION = 130;
    public static final int META_MOVECLIPREGION_ACTION = 131;
    public static final int META_LINECOLOR_ACTION = 132;
    public static final int META_FILLCOLOR_ACTION = 133;
    public static final int META_TEXTCOLOR_ACTION = 134;
    public static final int META_TEXTFILLCOLOR_ACTION = 135;
    public static final int META_TEXTALIGN_ACTION = 136;
    public static final int META_MAPMODE_ACTION = 137;
    public static final int META_FONT_ACTION = 138;
    public static final int META_PUSH_ACTION = 139;
    public static final int META_POP_ACTION = 140;
    public static final int META_RASTEROP_ACTION = 141;
    public static final int META_TRANSPARENT_ACTION = 142;
    public static final int META_EPS_ACTION = 143;
    public static final int META_REFPOINT_ACTION = 144;
    public static final int META_TEXTLINECOLOR_ACTION = 145;
    public static final int META_TEXTLINE_ACTION = 146;
    public static final int META_FLOATTRANSPARENT_ACTION = 147;
    public static final int META_GRADIENTEX_ACTION = 148;
    public static final int META_LAYOUTMODE_ACTION = 149;
    public static final int META_TEXTLANGUAGE_ACTION = 150;
    public static final int META_OVERLINECOLOR_ACTION = 151;
    public static final int META_RENDERGRAPHIC_ACTION = 152;
    public static final int META_COMMENT_ACTION = 512;
    private InputStream in;
    private BufferedImage image;

    public SVMReader(File f) throws IOException {
        this.in = new FileInputStream(f);
        parse();
        this.in.close();
    }

    public SVMReader(InputStream stream) throws IOException {
        this.in = stream;
        parse();
    }

    public BufferedImage getImage() {
        return image;
    }

    private void parse() throws IOException {
        final LittleEndianInputStream in = new LittleEndianInputStream(new BufferedInputStream(this.in));
        final HeaderParser hParser = new HeaderParser(in);
        int size = hParser.getActionCount();
        Log.get().info("Reading " + size + " actions");
        in.read();

        for (int action = 0; action < size; action++) {
            int actionType;
            int version;
            int totalSize;
            // Here starts the Action itself. The first two bytes is the action type.
            actionType = in.readShort();

            // The VersionCompat object
            version = in.readShort();
            totalSize = in.readInt();

            byte[] rawData = new byte[totalSize];
            in.read(rawData);
            // Debug
            Log.get().info("action type: " + actionType + " version:" + version + " total size:" + totalSize);
            // Parse all actions.
            switch (actionType) {
            case META_NULL_ACTION:
            case META_PIXEL_ACTION:
            case META_POINT_ACTION:
            case META_LINE_ACTION:
            case META_RECT_ACTION:
            case META_ROUNDRECT_ACTION:
            case META_ELLIPSE_ACTION:
            case META_ARC_ACTION:
            case META_PIE_ACTION:
            case META_CHORD_ACTION:
            case META_POLYLINE_ACTION:
            case META_POLYGON_ACTION:
            case META_POLYPOLYGON_ACTION:
            case META_TEXT_ACTION:
            case META_TEXTARRAY_ACTION:
            case META_STRETCHTEXT_ACTION:
            case META_TEXTRECT_ACTION:
            case META_BMP_ACTION:
            case META_BMPSCALE_ACTION:
            case META_BMPSCALEPART_ACTION:
            case META_BMPEX_ACTION:
            case META_BMPEXSCALE_ACTION: {
                Log.get().fine("BMP Ex Scale Action, rawdata size: " + rawData.length);
                final ByteArrayInputStream bis = new ByteArrayInputStream(rawData);
                final LittleEndianInputStream i2 = new LittleEndianInputStream(bis);
                //
                final String magic = (char) i2.read() + "" + (char) i2.read();
                if (!magic.equals("BM")) {
                    Log.get().severe("Bag magic number : " + magic + ". Should be BM");
                    break;
                }
                // http://docs.libreoffice.org/vcl/html/metaact_8cxx_source.html
                // http://docs.libreoffice.org/vcl/html/dibtools_8cxx_source.html

                final int l = i2.readInt();
                i2.readShort();
                i2.readShort();
                int dataOffset = i2.readInt();
                Log.get().info("File size: " + l + " offset: " + dataOffset);

                // DIB Header
                int headerSize = i2.readInt();
                int imageWidth = i2.readInt();
                int imageHeight = i2.readInt();
                int colorPlanes = i2.readShort();
                int bitsPerPixel = i2.readShort();
                Log.get().info("Header size: " + headerSize + " image size: " + imageWidth + "x" + imageHeight + " color planes: " + colorPlanes + " : " + bitsPerPixel);
                int compression = i2.readInt();

                if (compression == 0x01004453) {
                    // F*cking 'SD01' compression
                    Log.get().info("ZCompression");
                } else {
                    Log.get().severe("Unsupported compression : " + compression);
                }
                int s = i2.readInt();
                int fSize = i2.readInt();
                // Resolution
                int hRes = i2.readInt();
                int vRes = i2.readInt();
                //
                int nbColor = i2.readInt();
                int nbImportantnColor = i2.readInt();

                int lenght = i2.readInt();
                i2.readInt();
                Log.get().info("FileSize: " + fSize + " Size: " + s + " res: " + hRes + "x" + vRes + " colors: " + nbColor + "/" + nbImportantnColor);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream(lenght);
                StreamUtils.copy(i2, outputStream, 1024);

                byte[] zCompressedData = outputStream.toByteArray();
                BufferedImage bImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
                try {
                    byte[] uncompressedDate = decompress(zCompressedData);

                    int offset = 0;
                    final int pad = imageWidth % 2;
                    final DataBuffer dataBuffer = bImage.getRaster().getDataBuffer();
                    for (int y = 0; y < imageHeight; y++) {
                        for (int x = 0; x < imageWidth; x++) {
                            int r = uncompressedDate[offset + 0];
                            int g = uncompressedDate[offset + 1];
                            int b = uncompressedDate[offset + 2];
                            int a = 255;
                            int pixel = ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | ((b & 0xFF) << 0);
                            dataBuffer.setElem(x + (imageHeight - y - 1) * imageWidth, pixel);
                            offset += 3;
                        }
                        offset += pad;
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    Log.get().severe("decompression error");
                }
                this.image = bImage;
                break;
            }
            case META_BMPEXSCALEPART_ACTION:
            case META_MASK_ACTION:
            case META_MASKSCALE_ACTION:
            case META_MASKSCALEPART_ACTION:
            case META_GRADIENT_ACTION:
            case META_HATCH_ACTION:
            case META_WALLPAPER_ACTION:
            case META_CLIPREGION_ACTION:
            case META_ISECTRECTCLIPREGION_ACTION:
            case META_ISECTREGIONCLIPREGION_ACTION:
            case META_MOVECLIPREGION_ACTION:
            case META_LINECOLOR_ACTION:
            case META_FILLCOLOR_ACTION:
            case META_TEXTCOLOR_ACTION:
            case META_TEXTFILLCOLOR_ACTION:
            case META_TEXTALIGN_ACTION:
            case META_MAPMODE_ACTION:
            case META_FONT_ACTION:
            case META_PUSH_ACTION:
            case META_POP_ACTION:
            case META_RASTEROP_ACTION:
            case META_TRANSPARENT_ACTION:
            case META_EPS_ACTION:
            case META_REFPOINT_ACTION:
            case META_TEXTLINECOLOR_ACTION:
            case META_TEXTLINE_ACTION:
            case META_FLOATTRANSPARENT_ACTION:
            case META_GRADIENTEX_ACTION:
            case META_LAYOUTMODE_ACTION:
            case META_TEXTLANGUAGE_ACTION:
            case META_OVERLINECOLOR_ACTION:
            case META_RENDERGRAPHIC_ACTION:
            case META_COMMENT_ACTION:

            default:
                Log.get().severe("unknown action type:" + actionType);
                break;
            }

        }
        if (this.image == null) {
            image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        }
        in.close();
    }

    private static byte[] decompress(byte[] data) throws Exception {
        final Inflater inflater = new Inflater();
        inflater.setInput(data);
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        final byte[] buffer = new byte[1024];
        while (!inflater.finished()) {
            final int count = inflater.inflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        final byte[] output = outputStream.toByteArray();
        return output;
    }

}
