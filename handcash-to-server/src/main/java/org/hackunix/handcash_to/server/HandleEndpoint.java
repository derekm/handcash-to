package org.hackunix.handcash_to.server;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.mvc.Template;
import org.hackunix.handcash_io.api.Handle;
import org.hackunix.handcash_io.client.HandCashClient;
import org.hackunix.handcash_to.api.HandlePhotoResource;
import org.hackunix.handcash_to.api.HandleResource;

import com.github.t1.log.LogLevel;
import com.github.t1.log.Logged;

@Logged(level = LogLevel.INFO)
@RequestScoped
public class HandleEndpoint implements HandleResource {

    @Context
    private ResourceContext resourceCtx;

    @PathParam("handle")
    private String handle;

    private final HandCashClient client;

    @Inject
    public HandleEndpoint(HandCashClient client) {
        this.client = client;
    }

    @Override
    public Handle get() {
        String name = handle.replaceAll("(\\$)(.+)", "$2");
        Handle obj = client.root().receivingAddress().handle(name).get();
        return obj;
    }

	@GET
	@Produces({MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
	@Template(name = "/handle.ftl")
    public Map<String, Object> getHtml() {
		Map<String, Object> map = new HashMap<>();
		map.put("model", get());
		map.put("handle", handle);
		return map;
    }

	@Override
	public HandlePhotoResource photo() {
        return resourceCtx.initResource(CDI.current().select(HandlePhotoEndpoint.class).get());
	}

}
