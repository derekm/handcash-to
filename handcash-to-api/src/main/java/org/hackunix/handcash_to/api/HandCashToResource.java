package org.hackunix.handcash_to.api;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

public interface HandCashToResource {

	@Path("{handle : \\$[a-zA-Z0-9._-]{4,}}")
	public HandleResource handle(@PathParam("handle") String handle);

	@Path("{address : 1[a-km-zA-HJ-NP-Z1-9]{26,34}}")
	public LegacyAddressResource address(@PathParam("address") String address);

	@Path("{uri : bitcoin%3A1[a-km-zA-HJ-NP-Z1-9]{26,34}[a-zA-Z0-9:@!$&'()*+,;=%~_.-]+}")
	public BitcoinUriResource uri(@PathParam("uri") String uri);

//	@Path("{uri : bitcoincash%3A1[a-km-zA-HJ-NP-Z1-9]{26,34}[a-zA-Z0-9:@!$&'()*+,;=%~_.-]+}")
//	public BitcoinUriResource cashAddr(@PathParam("uri") String uri);

}
