package com.mailstorage.web.handlers;

import com.mailstorage.web.artifacts.LengthExtractor;
import com.mailstorage.web.response.OkResponse;
import org.apache.commons.io.IOUtils;
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
    @Autowired
    private LengthExtractor lengthExtractor;

    @POST
    @Path("/upload")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadPost(
            @NotNull(message = "user_id parameter is required") @QueryParam("user_id") String userId,
            @NotNull(message = "filename parameter is required") @QueryParam("filename") String fileName,
            InputStream input) throws IOException
    {
        OutputStream os = new FileOutputStream("received.eml");
        IOUtils.copy(input, os);

        File received = new File("received.eml");
        if (lengthExtractor.isTooBig(received)) {
            throw new RuntimeException("File is too big and won't be saved");
        }
        return Response
                .ok(new OkResponse())
                .build();
    }
}
