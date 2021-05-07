package org.jopendocument.sample;

import java.io.File;
import java.io.IOException;

import org.jopendocument.dom.ODMeta;
import org.jopendocument.dom.ODPackage;
import org.jopendocument.dom.ODUserDefinedMeta;

public class Metadata {
    public static void main(String[] args) throws IOException {

        // Load our document
        final File file = new File("template/tables.odt");
        final ODPackage pkg = new ODPackage(file);
        final ODMeta meta = pkg.getMeta();

        // Set some meta values
        meta.setTitle("jOpenDocument library sample");
        if (meta.getDescription().length() == 0) {
            meta.setDescription("A simple table");
        }

        // Get or create our meta if not exists
        ODUserDefinedMeta myMeta = meta.getUserMeta("Info 1", true);

        System.out.println("Old value: " + myMeta.getValue());
        System.out.println("Old type: " + myMeta.getValueType().getName());
        
        // set the value to Hello
        myMeta.setValue("Hello");

        System.out.println("New value: " + myMeta.getValue());
        System.out.println("New type: " + myMeta.getValueType().getName());
        
        
        // Save the document
        pkg.saveAs(new File("template/meta_out.odt"));

    }
}
