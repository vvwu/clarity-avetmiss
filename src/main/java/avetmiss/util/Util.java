package avetmiss.util;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class Util {
    private static Logger logger = LoggerFactory.getLogger(Util.class);

    public static int maxNumericDirectory(File parentDir) {
        File[] numericDirs = numericDirectories(parentDir);

        if(numericDirs.length == 0) {
            return 0;
        }

        return Integer.parseInt(numericDirs[0].getName());
    }

    public static File[] numericDirectories(File parentDir) {
        File[] numericDirs =
                parentDir.listFiles(
                        pathname -> pathname.isDirectory() && NumberUtils.isDigits(pathname.getName()));

        if(numericDirs == null || numericDirs.length == 0) {
            return new File[0];
        }

        Comparator<File> numericDescendingOrder =
                (o1, o2) -> Integer.compare(Integer.parseInt(o2.getName()), Integer.parseInt(o1.getName()));

        Arrays.sort(numericDirs, numericDescendingOrder);

        logger.info("Returning {} numeric directories under: {}", numericDirs.length, parentDir);
        return numericDirs;
    }
}
