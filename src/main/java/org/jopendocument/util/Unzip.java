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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * Permet de lire un fichier zip.
 * 
 * @author ILM Informatique
 * @see org.jopendocument.util.Zip
 */
public class Unzip extends ZipFile {

    public static void toDir(File zip, File dir) throws IOException {
        Unzip unz = new Unzip(zip);
        unz.unzip(dir);
        unz.close();
    }

    /**
     * Ouvre un fichier zip en lecture.
     * <p>
     * Note : ne pas oublier de fermer.
     * </p>
     * 
     * @param f the ZIP file to be opened for reading
     * @exception ZipException if a ZIP error has occurred
     * @exception IOException if an I/O error has occurred
     * @see ZipFile#close()
     */
    public Unzip(File f) throws ZipException, IOException {
        super(f);
    }

    // *** Lecture

    /**
     * Décompresse le zip dans un dossier. Si destDir n'existe pas le crée.
     * 
     * @param destDir ou mettre le résultat.
     * @throws ZipException si erreur lors de la décompression.
     * @throws IOException si erreur de lecture, ou de création de la destination.
     */
    public void unzip(File destDir) throws ZipException, IOException {
        if (destDir == null)
            destDir = new File("");
        destDir.mkdirs();

        final Enumeration en = this.entries();
        while (en.hasMoreElements()) {
            ZipEntry target = (ZipEntry) en.nextElement();
            unzip(destDir, target);
        }
    }

    public void unzip(File destDir, String entryName) throws ZipException, IOException {
        unzipEntry(this, this.getEntry(entryName), destDir);
    }

    public void unzip(File destDir, ZipEntry entry) throws ZipException, IOException {
        unzipEntry(this, entry, destDir);
    }

    static private void unzipEntry(ZipFile zf, ZipEntry target, File to) throws ZipException, IOException {
        File file = new File(to, target.getName());
        if (target.isDirectory()) {
            file.mkdirs();
        } else {
            file.getParentFile().mkdirs();

            InputStream is = zf.getInputStream(target);
            BufferedInputStream bis = new BufferedInputStream(is);
            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            int c;
            while ((c = bis.read()) != -1) {
                bos.write((byte) c);
            }
            bos.close();
            fos.close();
        }
    }

    /**
     * Renvoie un flux sans dézipper le fichier.
     * 
     * @param entryName le nom de l'entrée voulue.
     * @return le flux correspondant, ou <code>null</code> si non existant.
     * @throws IOException si erreur lecture.
     * @see ZipFile#close()
     */
    public InputStream getInputStream(String entryName) throws IOException {
        final ZipEntry entry = this.getEntry(entryName);
        if (entry == null)
            // l'entrée n'existe pas
            return null;
        else
            return this.getInputStream(entry);
    }

}