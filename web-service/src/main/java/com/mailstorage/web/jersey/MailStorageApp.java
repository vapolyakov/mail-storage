package com.mailstorage.web.jersey;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

/**
 * @author metal
 *
 * Jersey mail storage configuration. Initializes handlers from specific package and
 * allows to present application exceptions with fixed error response json scheme.
 */
public class MailStorageApp extends ResourceConfig {
    public MailStorageApp() {
        packages("com.mailstorage.web.handlers");
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        register(ConstraintViolationExceptionMapper.class);
        register(GenericExceptionMapper.class);
    }
}
