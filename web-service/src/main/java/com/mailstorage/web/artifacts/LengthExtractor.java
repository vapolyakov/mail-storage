package com.mailstorage.web.artifacts;

import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author metal
 */
@Component
public class LengthExtractor {
    private final long maxLength;

    public LengthExtractor(long maxLength) {
        this.maxLength = maxLength;
    }

    public boolean isTooBig(File file) {
        return file.length() > maxLength;
    }
}
