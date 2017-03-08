package com.mailstorage.web.jersey;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

/**
 * @author metal
 */
public class MailStorageApp extends ResourceConfig {
    public MailStorageApp() {
        packages("com.mailstorage.web.handlers");
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        register(ConstraintViolationExceptionMapper.class);
        register(GenericExceptionMapper.class);
    }
}
