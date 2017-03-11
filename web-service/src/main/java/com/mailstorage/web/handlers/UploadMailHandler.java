package com.mailstorage.web.handlers;

import com.mailstorage.utils.file.IncomingFileSaver;
import com.mailstorage.web.artifacts.LengthExtractor;
import com.mailstorage.web.response.OkResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

/**
 * @author metal
 */
@Path("/")
@Component
public class UploadMailHandler {
    private static final Logger logger = LoggerFactory.getLogger(UploadMailHandler.class);

    @Autowired
    private LengthExtractor lengthExtractor;

    @Autowired
    private IncomingFileSaver incomingFileSaver;

    @POST
    @Path("/upload")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadPost(
            @NotNull(message = "user_id parameter is required") @QueryParam("user_id") String userId,
            @NotNull(message = "filename parameter is required") @QueryParam("filename") String fileName,
            InputStream input) throws IOException
    {
        logger.info("Handling /upload request for user_id={} and filename={}", userId, fileName);

        File saved = incomingFileSaver.saveIncomingFileLocally(userId, fileName, input);
        logger.info("File successfully received and saved locally in {}", saved.getAbsoluteFile());

        if (lengthExtractor.isTooBig(saved)) {
            throw new RuntimeException("File is too big and won't be saved");
        }
        return Response
                .ok(new OkResponse())
                .build();
    }
}
