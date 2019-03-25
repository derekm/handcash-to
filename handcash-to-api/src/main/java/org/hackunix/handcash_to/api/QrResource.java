package org.hackunix.handcash_to.api;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.StreamingOutput;

public interface QrResource {

	@GET
	@Produces("image/png")
	public StreamingOutput getPng();

	@GET
	@Produces("image/jpg")
	public StreamingOutput getJpg();

	@GET
	@Produces("image/gif")
	public StreamingOutput getGif();

}
