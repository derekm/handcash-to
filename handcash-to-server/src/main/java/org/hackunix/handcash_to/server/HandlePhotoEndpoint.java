package org.hackunix.handcash_to.server;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.hackunix.handcash_io.client.HandCashClient;
import org.hackunix.handcash_to.api.HandlePhotoResource;

@RequestScoped
public class HandlePhotoEndpoint implements HandlePhotoResource {

	@PathParam("handle")
	private String handle;

	private final HandCashClient client;

    @Inject
    public HandlePhotoEndpoint(HandCashClient client) {
        this.client = client;
    }

	@Override
	public Response get() {
        String name = handle.replaceAll("(\\$)(.+)", "$2");
		return client.root().photo().handle(name).get();

//		return new StreamingOutput() {
//			@Override
//			public void write(OutputStream output) throws IOException, WebApplicationException {
//				copy(res.readEntity(InputStream.class), output);
//			}
//		};
	}

//	// TODO FIXME code below copied from Java 9's java.nio.file.Files
//
//	// buffer size used for reading and writing
//    private static final int BUFFER_SIZE = 8192;
//
//    /**
//     * Reads all bytes from an input stream and writes them to an output stream.
//     */
//    private static long copy(InputStream source, OutputStream sink)
//        throws IOException
//    {
//        long nread = 0L;
//        byte[] buf = new byte[BUFFER_SIZE];
//        int n;
//        while ((n = source.read(buf)) > 0) {
//            sink.write(buf, 0, n);
//            nread += n;
//        }
//        return nread;
//    }

}
