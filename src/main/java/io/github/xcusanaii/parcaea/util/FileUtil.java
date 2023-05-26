package io.github.xcusanaii.parcaea.util;

import java.io.File;

import static io.github.xcusanaii.parcaea.Parcaea.LOGGER;

public class FileUtil {

    public static File getUniqueFile(String fileName, String extension) {
        File file = new File(fileName + extension);
        int suffix = 1;
        while (file.exists()) {
            file = new File(fileName + "_" + suffix + extension);
            suffix++;
        }
        return file;
    }

    public static void deleteFiles(String dirName) {
        File dir = new File(dirName);
        if (dir.exists()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (!file.delete()) LOGGER.info("failed to delete file " + file.getPath());
                }
            }
        }
    }
}
