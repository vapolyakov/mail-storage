package com.mailstorage.data.raw;

import com.mailstorage.utils.file.FileNameUtils;
import com.mailstorage.utils.timestamp.CurrentDateProvider;

import java.io.*;

/**
 * @author metal
 *
 * Raw file storage that saves all incoming files in HDFS. Needs to be properly configured.
 * HdfsFileStorage uses as file id HDFS file path from it's root folder. HdfsFileStorage bundles files by user id and
 * current date.
 */
public class HdfsFileStorage implements RawFileStorage<String> {
    private final HdfsFileStorageDaoImpl hdfsFileStorageDao;

    public HdfsFileStorage(HdfsFileStorageDaoImpl hdfsFileStorageDao) {
        this.hdfsFileStorageDao = hdfsFileStorageDao;
    }

    @Override
    public String save(RawFileInfo rawFileInfo) {
        String fileIdInHdfs = getPathToUpload(rawFileInfo);
        hdfsFileStorageDao.save(rawFileInfo.getData(), fileIdInHdfs);
        return fileIdInHdfs;
    }

    @Override
    public void get(String id, File destination) {
        hdfsFileStorageDao.get(id, destination);
    }

    private String getPathToUpload(RawFileInfo fileInfo) {
        String folder = fileInfo.getUserId() + "/" + CurrentDateProvider.getDate();
        return FileNameUtils.getRandomPath(folder, fileInfo.getInitialFileName());
    }
}
