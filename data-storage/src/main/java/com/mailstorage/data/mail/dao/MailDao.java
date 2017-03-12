package com.mailstorage.data.mail.dao;

import com.flipkart.hbaseobjectmapper.AbstractHBDAO;
import com.mailstorage.data.mail.entities.Mail;
import org.apache.hadoop.conf.Configuration;

import java.io.IOException;

/**
 * @author metal
 */
public class MailDao extends AbstractHBDAO<Long, Mail> {
    public MailDao(Configuration conf) throws IOException {
        super(conf);
    }
}
