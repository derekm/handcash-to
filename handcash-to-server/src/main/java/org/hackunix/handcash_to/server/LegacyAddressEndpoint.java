package org.hackunix.handcash_to.server;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.spi.CDI;

import org.hackunix.handcash_to.api.QrResource;
import org.hackunix.handcash_to.api.LegacyAddressResource;

@RequestScoped
public class LegacyAddressEndpoint implements LegacyAddressResource {

	@Override
	public QrResource qr() {
		return CDI.current().select(QrEndpoint.class).get();
	}

}
