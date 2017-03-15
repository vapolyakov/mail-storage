package com.mailstorage.data.mail.entities;

import com.flipkart.hbaseobjectmapper.HBColumn;
import com.flipkart.hbaseobjectmapper.HBRowKey;
import com.flipkart.hbaseobjectmapper.HBTable;

import javax.activation.DataSource;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author metal
 */
@HBTable("mail")
public class Mail extends BasePrimaryEntity {
    @HBRowKey
    private Long timestamp;

    @HBColumn(family = "raw", column = "user_id")
    private String uid;

    @HBColumn(family = "raw", column = "filename")
    private String filename;

    @HBColumn(family = "raw", column = "hdfs_id")
    private String hdfsId;

    @HBColumn(family = "general", column = "from")
    private String from;

    @HBColumn(family = "general", column = "sent_date")
    private String sentDate;

    @HBColumn(family = "general", column = "to")
    private List<String> to;

    @HBColumn(family = "general", column = "cc")
    private List<String> cc;

    @HBColumn(family = "general", column = "bcc")
    private List<String> bcc;

    @HBColumn(family = "general", column = "subject")
    private String subject;

    @HBColumn(family = "general", column = "message")
    private String message;

    @HBColumn(family = "general", column = "attachments")
    private Map<String, String> attachments;

    private List<DataSource> attachmentsData;
    private File emailLocalFile;

    public Mail() {
    }

    public Mail(Long timestamp, String uid, String filename, String hdfsId,
            String from, String sentDate, List<String> to, List<String> cc, List<String> bcc,
            String subject, String message, Map<String, String> attachments)
    {
        this.timestamp = timestamp;
        this.uid = uid;
        this.filename = filename;
        this.hdfsId = hdfsId;
        this.from = from;
        this.sentDate = sentDate;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.subject = subject;
        this.message = message;
        this.attachments = attachments;
    }

    @Override
    public Long getTimestamp() {
        return timestamp;
    }

    @Override
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public Long composeRowKey() {
        return getTimestamp();
    }

    @Override
    public void parseRowKey(Long rowKey) {
        setTimestamp(rowKey);
    }

    public String getUid() {
        return uid;
    }

    public String getFilename() {
        return filename;
    }

    public String getHdfsId() {
        return hdfsId;
    }

    public String getFrom() {
        return from;
    }

    public String getSentDate() {
        return sentDate;
    }

    public List<String> getTo() {
        return to;
    }

    public List<String> getCc() {
        return cc;
    }

    public List<String> getBcc() {
        return bcc;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getAttachments() {
        return attachments;
    }

    public List<DataSource> getAttachmentsData() {
        return attachmentsData;
    }

    public void setAttachmentsData(List<DataSource> attachmentsData) {
        this.attachmentsData = attachmentsData;
    }

    public File getEmailLocalFile() {
        return emailLocalFile;
    }

    public void setEmailLocalFile(File emailLocalFile) {
        this.emailLocalFile = emailLocalFile;
    }
}
