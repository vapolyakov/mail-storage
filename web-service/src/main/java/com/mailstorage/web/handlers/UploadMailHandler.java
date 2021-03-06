package com.mailstorage.web.handlers;

import com.mailstorage.core.Stages;
import com.mailstorage.data.raw.RawFileInfo;
import com.mailstorage.data.raw.RawFileStorage;
import com.mailstorage.utils.file.IncomingFileSaver;
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
 *
 * /upload handler. Allows to accept .eml files via POST request with two additional required query parameters:
 * user_id - user id to whom .eml file belongs
 * filename - initial .eml filename (to trace uploaded files to its sources)
 */
@Path("/")
@Component
public class UploadMailHandler {
    private static final Logger logger = LoggerFactory.getLogger(UploadMailHandler.class);

    @Autowired
    private IncomingFileSaver incomingFileSaver;

    @Autowired
    private RawFileStorage<String> rawFileStorage;

    @Autowired
    private Stages stages;

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

        RawFileInfo rawFileInfo = new RawFileInfo(userId, fileName, saved);
        String hdfsId = rawFileStorage.save(rawFileInfo);

        stages.extractGeneralInfo(rawFileInfo, hdfsId, true);

        return Response
                .ok(new OkResponse())
                .build();
    }
}
