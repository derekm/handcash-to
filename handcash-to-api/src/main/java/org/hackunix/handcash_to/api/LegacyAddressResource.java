package org.hackunix.handcash_to.api;

import javax.ws.rs.Path;

public interface LegacyAddressResource {

	@Path("qr")
	public QrResource qr();

}
