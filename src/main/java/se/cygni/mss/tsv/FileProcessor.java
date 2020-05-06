package se.cygni.mss.tsv;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class FileProcessor {

    private static final String STEM_FILE_URL = "https://datasets.imdbws.com/";
    private static final String[] FILE_NAMES = {
            "name.basics.tsv.gz",
            "title.basics.tsv.gz",
            "title.principals.tsv.gz",
            "title.ratings.tsv.gz"};
    private static int CONNECT_TIMEOUT = 1000;
    private static int READ_TIMEOUT = 50000;

    public static void download() {
        for (String filename : FILE_NAMES) {
            try {
                String FILE_URL = STEM_FILE_URL + filename;
                String FILE_NAME = "./downloads/" + filename;

                FileUtils.copyURLToFile(
                        new URL(FILE_URL),
                        new File(FILE_NAME),
                        CONNECT_TIMEOUT,
                        READ_TIMEOUT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void unzip() {
        for (String filename : FILE_NAMES) {
            String sourceFile = "./downloads/" + filename;
            String targetFile = "src/main/resources/" + FilenameUtils.getBaseName(filename);
            Gunzipper.unzip(sourceFile, targetFile);
        }
    }
}
