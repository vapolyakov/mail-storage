package com.mailstorage.web.jersey;

import com.mailstorage.web.response.ErrorResponse;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author metal
 *
 * Catches all exceptions and wraps it in 500 internal server error response
 * that matches general error response json scheme.
 */
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable ex) {
        return constructErrorResponse(ex);
    }

    private Response constructErrorResponse(Throwable ex) {
        if (ex instanceof WebApplicationException) {
            return ((WebApplicationException) ex).getResponse();
        } else {
            Response.Status internalServerError = Response.Status.INTERNAL_SERVER_ERROR;
            return Response
                    .status(internalServerError)
                    .entity(new ErrorResponse(internalServerError, ex.getMessage()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }
}
