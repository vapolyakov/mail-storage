package com.mailstorage.utils.file;

import com.mailstorage.utils.timestamp.CurrentDateProvider;
import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * @author metal
 *
 * Saves incoming data from specific user in files given by LocalFileManager and bundled by current date.
 */
public class IncomingFileSaver {
    private LocalFileManager localFileManager;

    public IncomingFileSaver(LocalFileManager localFileManager) {
        this.localFileManager = localFileManager;
    }

    /**
     * Saves incoming file via LocalFileManager in currentDate/userId folder.
     *
     * @param userId incoming file owner id
     * @param fileName incoming file name, can be empty
     * @param input incoming data
     * @return local file containing all incoming data
     * @throws IOException if I/O problem occurs
     */
    public File saveIncomingFileLocally(String userId, String fileName, InputStream input) throws IOException {
        String currentDate = CurrentDateProvider.getDate();
        File saved = localFileManager.getFileUploadTo(getFolderPrefix(userId, currentDate), fileName);
        OutputStream output = new FileOutputStream(saved);
        IOUtils.copy(input, output);
        return saved;
    }

    private String getFolderPrefix(String userId, String currentDate) {
        return currentDate + "/" + userId;
    }
}
