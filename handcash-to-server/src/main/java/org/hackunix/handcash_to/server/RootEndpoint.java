package org.hackunix.handcash_to.server;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.spi.CDI;
import javax.ws.rs.Path;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;

import org.hackunix.handcash_to.api.BitcoinUriResource;
import org.hackunix.handcash_to.api.HandCashToResource;
import org.hackunix.handcash_to.api.HandleResource;
import org.hackunix.handcash_to.api.LegacyAddressResource;

@Path("/")
@RequestScoped
public class RootEndpoint implements HandCashToResource {

    @Context
    private ResourceContext resourceCtx;

	@Override
	public HandleResource handle(String handle) {
    	return resourceCtx.initResource(CDI.current().select(HandleEndpoint.class).get());
	}

	@Override
	public LegacyAddressResource address(String address) {
    	return resourceCtx.initResource(CDI.current().select(LegacyAddressEndpoint.class).get());
	}

	@Override
	public BitcoinUriResource uri(String uri) {
    	return resourceCtx.initResource(CDI.current().select(BitcoinUriEndpoint.class).get());
	}

}
