package com.mailstorage.web.response;

/**
 * @author metal
 */
public abstract class SimpleResponse {
    private final boolean success;

    public SimpleResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
