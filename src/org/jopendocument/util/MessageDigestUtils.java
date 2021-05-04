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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestUtils {

    private static final char[] HEX_CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', };

    /**
     * Turns array of bytes into string representing each byte as unsigned hex number.
     * 
     * @param hash Array of bytes to convert to hex-string
     * @return Generated hex string
     */
    public static String asHex(byte hash[]) {
        return toString(hash, HEX_CHARS);
    }

    public static String toString(final byte[] hash, final char[] chars) {
        char buf[] = new char[hash.length * 2];
        for (int i = 0, x = 0; i < hash.length; i++) {
            buf[x++] = chars[(hash[i] >>> 4) & 0xf];
            buf[x++] = chars[hash[i] & 0xf];
        }
        return new String(buf);
    }

    public static byte[] fromHex(String hash) {
        final byte[] buf = new byte[hash.length() / 2];
        for (int i = 0; i < buf.length; i++) {
            buf[i] = (byte) Integer.parseInt(hash.substring(2 * i, 2 * i + 2), 16);
        }
        return buf;
    }

    /**
     * Split an int into 4 bytes.
     * 
     * @param integer an int, eg -2^31, or 16.
     * @return the 4 bytes of its two's complement, eg [0x80, 0x00, 0x00, 0x00] or [0x00, 0x00,
     *         0x00, 0x10].
     */
    public static byte[] int2bytes(int integer) {
        final byte[] byteStr = new byte[4];
        byteStr[0] = (byte) (integer >>> 24);
        byteStr[1] = (byte) ((integer >>> 16) & 0xff);
        byteStr[2] = (byte) ((integer >>> 8) & 0xff);
        byteStr[3] = (byte) (integer & 0xff);
        return byteStr;
    }

    /**
     * Cat 4 bytes to make an int.
     * 
     * @param buf an array of at least 4 bytes, eg [0x80, 0x00, 0x00, 0x03].
     * @return the resulting int, eg -2^31 + 3 or -2147483645.
     */
    public static int bytes2int(byte[] buf) {
        // eg -128 == 10000000 promoted to int => 11...110000000
        // so we need & 0xff to remove added ones (except for the 1st since they're all shifted
        // away)
        return (buf[0] << 24) | ((buf[1] & 0xff) << 16) | ((buf[2] & 0xff) << 8) | (buf[3] & 0xff);
    }

    public static String getHashString(MessageDigest md) {
        return asHex(md.digest());
    }

    public static String getHashString(String algo, byte[] data) throws NoSuchAlgorithmException {
        final MessageDigest md = MessageDigest.getInstance(algo);
        return getHashString(md, data);
    }

    public static String getHashString(MessageDigest md, byte[] data) {
        md.update(data);
        return getHashString(md);
    }

    public static String getHashString(MessageDigest md, final InputStream ins) throws IOException {
        final DigestOutputStream out = new DigestOutputStream(StreamUtils.NULL_OS, md);
        StreamUtils.copy(ins, out);
        out.close();
        return getHashString(md);
    }

    public static String getMD5(File f) throws IOException {
        final InputStream ins = new FileInputStream(f);
        try {
            return getHashString(getMD5(), ins);
        } finally {
            ins.close();
        }
    }

    public static MessageDigest getMD5() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("MD5 is part of the standard vm", e);
        }
    }

    private MessageDigestUtils() {
    }

}
