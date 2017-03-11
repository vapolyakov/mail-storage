package com.mailstorage.data.raw;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author metal
 */
public class HdfsFileStorageDaoImpl implements RawFileStorageDao<String> {
    private static final Logger logger = LoggerFactory.getLogger(HdfsFileStorageDaoImpl.class);

    private final FileSystem hdfs;
    private final Path root;
    private final String hdfsUri;

    public HdfsFileStorageDaoImpl(String hdfsUri, String rootPath) throws URISyntaxException, IOException {
        Configuration configuration = new Configuration();
        this.hdfs = FileSystem.get(new URI(hdfsUri), configuration);
        this.root = new Path(hdfsUri + rootPath);
        this.hdfsUri = hdfsUri;

        if (!hdfs.exists(root)) {
            hdfs.mkdirs(root);
        }
    }

    @Override
    public void save(File fileToUpload, String id) {
        logger.info("Uploading file {} to hdfs {}", fileToUpload.getAbsoluteFile(), id);
        try (
                InputStream input = new FileInputStream(fileToUpload);
                OutputStream output = hdfs.create(constructPathFromRelative(id))
        ) {
            IOUtils.copy(input, output);
        } catch (Exception e) {
            logger.error("Upload to hdfs failed", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void get(String id, File destination) {
        logger.info("Downloading file {} from hdfs to local path {}", id, destination.getAbsoluteFile());
        try (
                InputStream input = hdfs.open(constructPathFromRelative(id));
                OutputStream output = new FileOutputStream(destination)
        ) {
            IOUtils.copy(input, output);
        } catch (Exception e) {
            logger.error("Download from hdfs failed", e);
            throw new RuntimeException(e);
        }
    }

    void clean() throws IOException {
        hdfs.delete(root, true);
    }

    private Path constructPathFromRelative(String relativePath) {
        return Path.mergePaths(root, new Path(hdfsUri + "/" + relativePath));
    }
}
