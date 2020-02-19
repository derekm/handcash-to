package org.hackunix.handcash_to.api;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hackunix.handcash_io.api.Handle;

public interface PaymailHandleResource {

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Handle get() throws Exception;

}
