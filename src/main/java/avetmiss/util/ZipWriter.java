package avetmiss.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipWriter {
    private final static Logger LOG = LoggerFactory.getLogger("Zip");


    /**
     * This method creates the zip archive and then goes through
     * each file in the chosen directory, adding each one to the
     * archive. Note the use of the try with resource to avoid
     * any finally blocks.
     */
    public void createZip(String directoryToZip, String outputZipFilePath) {
        // the directory to be zipped
        Path directory = Paths.get(directoryToZip);

        // the zip file name that we will create
        File zipFileName = Paths.get(outputZipFilePath).toFile();

        // open the zip stream in a try resource block, no finally needed
        try( ZipOutputStream zipStream = new ZipOutputStream(
                new FileOutputStream(zipFileName)) ) {

            // traverse every file in the selected directory and add them
            // to the zip file by calling addToZipFile(..)
            DirectoryStream<Path> dirStream = Files.newDirectoryStream(directory);
            dirStream.forEach(path ->
                    addToZipFile(path, zipStream));

            LOG.info("Zip file created in " + directory.toFile().getPath());
        }
        catch(IOException|ZipParsingException e) {
            LOG.error("Error while zipping.", e);
        }
    }

    /**
     * Adds an extra file to the zip archive, copying in the created
     * date and a comment.
     * @param file file to be archived
     * @param zipStream archive to contain the file.
     */
    private void addToZipFile(Path file, ZipOutputStream zipStream) {
        String inputFileName = file.toFile().getPath();
        try (FileInputStream inputStream = new FileInputStream(inputFileName)) {

            // create a new ZipEntry, which is basically another file
            // within the archive. We omit the path from the filename
            ZipEntry entry = new ZipEntry(file.toFile().getName());
            entry.setCreationTime(FileTime.fromMillis(file.toFile().lastModified()));
            entry.setComment("Created by TheCodersCorner");
            zipStream.putNextEntry(entry);

            LOG.info("Generated new entry for: " + inputFileName);

            // Now we copy the existing file into the zip archive. To do
            // this we write into the zip stream, the call to putNextEntry
            // above prepared the stream, we now write the bytes for this
            // entry. For another source such as an in memory array, you'd
            // just change where you read the information from.
            byte[] readBuffer = new byte[2048];
            int amountRead;
            int written = 0;

            while ((amountRead = inputStream.read(readBuffer)) > 0) {
                zipStream.write(readBuffer, 0, amountRead);
                written += amountRead;
            }

            LOG.info("Stored " + written + " bytes to " + inputFileName);


        }
        catch(IOException e) {
            throw new ZipParsingException("Unable to process " + inputFileName, e);
        }
    }


    /**
     * We want to let a checked exception escape from a lambda that does not
     * allow exceptions. The only way I can see of doing this is to wrap the
     * exception in a RuntimeException. This is a somewhat unfortunate side
     * effect of lambda's being based off of interfaces.
     */
    private class ZipParsingException extends RuntimeException {
        public ZipParsingException(String reason, Exception inner) {
            super(reason, inner);
        }
    }
}
