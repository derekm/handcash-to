package org.hackunix.handcash_to.api;

import javax.ws.rs.Path;

public interface BitcoinUriResource {

	@Path("qr")
	public QrResource qr();

}
