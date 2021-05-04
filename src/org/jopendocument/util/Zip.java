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
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Set;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;

/**
 * Permet d'écrire dans un fichier zip.
 * 
 * @author ILM Informatique
 * @see org.jopendocument.util.Unzip
 */
public class Zip {

    /**
     * Copie de from dans to seulement les entrées dont le nom n'est pas dans
     * <code>excludedEntries</code>.
     * 
     * @param from le zip source.
     * @param excludedEntries les noms des entrées à exclure.
     * @param to le zip de destination, s'il existe déjà les entrées de from seront ajoutées aux
     *        existantes.
     * @return le fichier zip dest.
     * @throws ZipException
     * @throws IOException
     */
    static public Zip createFrom(File from, File to, Set excludedEntries) throws ZipException, IOException {
        Unzip unz = new Unzip(from);
        Zip res = new Zip(to);
        final Enumeration en = unz.entries();
        while (en.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) en.nextElement();
            if (!excludedEntries.contains(entry.getName())) {
                res.zip(entry.getName(), unz.getInputStream(entry));
            }
        }
        unz.close();
        return res;
    }

    /**
     * Efface les entrées spécifées de src. Si dest existe, il sera ecrasé.
     * 
     * @param src le zip source.
     * @param entriesName les noms des entrées à effacer.
     * @param dest le zip de destination.
     * @throws ZipException
     * @throws IOException
     */
    static public void delete(File src, Set entriesName, File dest) throws ZipException, IOException {
        if (dest.exists())
            dest.delete();
        createFrom(src, dest, entriesName).close();
    }

    // *** Instance

    private final OutputStream outstream;
    private ZipOutputStream zos;
    // is an entry open, ie addEntry() has been called but closeEntry() not yet
    private boolean entryOpen;

    /**
     * Construit un fichier zip. ATTN Le fichier passé sera écrasé lors de la première écriture.
     * 
     * @param f le fichier dans lequel sauver, peut ne pas exister.
     * @throws FileNotFoundException if f cannot be written to.
     */
    public Zip(File f) throws FileNotFoundException {
        this(new FileOutputStream(f));
    }

    /**
     * Construit un fichier zip.
     * 
     * @param out un stream dans lequel écrire.
     */
    public Zip(OutputStream out) {
        this.outstream = out;
        this.zos = null;
        this.entryOpen = false;
    }

    public synchronized void close() throws IOException {
        if (this.zos != null) {
            // ferme aussi le FileOutputStream
            this.zos.close();
        }
    }

    // *** Ecriture

    private synchronized ZipOutputStream getOutStream() {
        if (this.zos == null) {
            this.zos = new ZipOutputStream(this.outstream);
        }
        return this.zos;
    }

    /**
     * Ajoute newFile dans ce fichier. Il sera enregistré dans le zip directement à la racine.
     * 
     * @param newFile le fichier à ajouter.
     * @throws IOException si le fichier ne peut etre zippé.
     */
    public void zip(File newFile) throws IOException {
        // on ne garde que la derniere partie du chemin
        this.zip(newFile.getName(), new BufferedInputStream(new FileInputStream(newFile)));
    }

    /**
     * Zippe le contenu de <code>in</code>.
     * 
     * @param name le nom de l'entrée.
     * @param in l'ajout.
     * @throws IOException si in ne peut etre zippé.
     */
    public synchronized void zip(String name, InputStream in) throws IOException {
        this.putNextEntry(name);

        byte b[] = new byte[512];
        int len = 0;
        while ((len = in.read(b)) != -1) {
            this.getOutStream().write(b, 0, len);
        }

        this.closeEntry();
    }

    public void zip(String name, byte[] in, final boolean compressed) throws IOException {
        // don't make #zip(String, InputStream) call this method, it would require to read the
        // entire stream into memory
        if (compressed)
            this.zip(name, new ByteArrayInputStream(in));
        else
            this.zipNonCompressed(name, in);
    }

    /**
     * Zip the passed array with the {@link ZipEntry#STORED} method. This method takes care of the
     * CRC and size.
     * 
     * @param name the entry name.
     * @param in what to zip.
     * @throws IOException if an error occurs.
     */
    public synchronized void zipNonCompressed(String name, byte[] in) throws IOException {
        final ZipEntry entry = new ZipEntry(name);
        entry.setMethod(ZipEntry.STORED);
        final CRC32 crc = new CRC32();
        crc.update(in);
        entry.setCrc(crc.getValue());
        entry.setSize(in.length);

        this.putNextEntry(entry);
        this.getOutStream().write(in);
        this.closeEntry();
    }

    /**
     * Adds a new entry to this zip file. ATTN you must close the returned stream before you can add
     * to this zip again.
     * 
     * @param name the name of the entry.
     * @return a stream to write to the entry.
     * @throws IOException if a pb occurs.
     */
    public synchronized OutputStream createEntry(String name) throws IOException {
        this.putNextEntry(name);
        return new BufferedOutputStream(this.getOutStream()) {
            public void close() throws IOException {
                this.flush();
                Zip.this.closeEntry();
            }
        };
    }

    private final synchronized void putNextEntry(String name) throws IOException, FileNotFoundException {
        this.putNextEntry(new ZipEntry(name));
    }

    private final synchronized void putNextEntry(ZipEntry entry) throws IOException, FileNotFoundException {
        if (this.entryOpen)
            throw new IllegalStateException("previous entry not closed");
        this.entryOpen = true;
        this.getOutStream().putNextEntry(entry);
    }

    protected final synchronized void closeEntry() throws IOException {
        this.getOutStream().closeEntry();
        this.entryOpen = false;
    }

}
