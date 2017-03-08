package com.mailstorage.web.jersey;

import com.mailstorage.web.response.ErrorResponse;
import org.glassfish.jersey.server.validation.ValidationError;
import org.glassfish.jersey.server.validation.internal.ValidationHelper;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.stream.Collectors;

/**
 * @author metal
 */
@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException cve) {
        Response.Status badRequestError = Response.Status.BAD_REQUEST;
        return Response
                .status(badRequestError)
                .entity(new ErrorResponse(badRequestError, collectViolations(cve)))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    private String collectViolations(ConstraintViolationException cve) {
        return ValidationHelper
                .constraintViolationToValidationErrors(cve)
                .stream()
                .map(ValidationError::getMessage)
                .collect(Collectors.joining(", "));
    }
}
