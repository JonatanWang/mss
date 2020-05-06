package se.cygni.mss.tsv;

import java.io.*;
import java.util.zip.*;

public class Gunzipper {

    public static void unzip(String sourceFile, String targetFile) {

        try (
                // Create a file input stream to read the source file.
                FileInputStream fis = new FileInputStream(sourceFile);

                // Create a gzip input stream to decompress the source
                // file defined by the file input stream.
                GZIPInputStream gzis = new GZIPInputStream(fis);

                // Create file output stream where the decompress result
                // will be stored.
                FileOutputStream fos = new FileOutputStream(targetFile)) {

            // Create a buffer and temporary variable used during the
            // file decompress process.
            byte[] buffer = new byte[1024];
            int length;

            // Read from the compressed source file and write the
            // decompress file.
            while ((length = gzis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}