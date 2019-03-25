package org.hackunix.handcash_to.api;

import javax.ws.rs.GET;
import javax.ws.rs.core.Response;

public interface HandlePhotoResource {

	@GET
	public Response get();

}
