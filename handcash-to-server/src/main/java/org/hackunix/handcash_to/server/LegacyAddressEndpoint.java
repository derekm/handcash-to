package org.hackunix.handcash_to.server;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.spi.CDI;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;

import org.hackunix.handcash_to.api.QrResource;
import org.hackunix.handcash_to.api.LegacyAddressResource;

@RequestScoped
public class LegacyAddressEndpoint implements LegacyAddressResource {

    @Context
    private ResourceContext resourceCtx;

    @Override
    public QrResource qr() {
        return resourceCtx.initResource(CDI.current().select(QrEndpoint.class).get());
    }

}
