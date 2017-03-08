package com.mailstorage.web.response;

import javax.ws.rs.core.Response;

/**
 * @author metal
 */
public class ErrorResponse extends SimpleResponse {
    private final Response.Status status;
    private final int code;
    private final String message;

    public ErrorResponse(Response.Status status, String message) {
        super(false);
        this.status = status;
        this.code = status.getStatusCode();
        this.message = message;
    }

    public Response.Status getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
