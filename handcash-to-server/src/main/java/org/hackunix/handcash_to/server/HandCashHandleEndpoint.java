package org.hackunix.handcash_to.server;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.mvc.Template;
import org.hackunix.handcash_io.api.Handle;
import org.hackunix.handcash_io.client.HandCashClient;
import org.hackunix.handcash_to.api.HandlePhotoResource;
import org.hackunix.handcash_to.api.HandCashHandleResource;

import com.github.t1.log.LogLevel;
import com.github.t1.log.Logged;

@Logged(level = LogLevel.INFO)
@RequestScoped
public class HandCashHandleEndpoint implements HandCashHandleResource {

	@PathParam("handle")
	private String handle;

	private final HandCashClient handcash;

	@Inject
	public HandCashHandleEndpoint(HandCashClient handcash) {
		this.handcash = handcash;
	}

	@Override
	public Handle get() {
		String name = handle.replaceAll("(\\$)(.+)", "$2");
		try {
			return handcash.root().receivingAddress().handle(name).get();
		} catch (NotFoundException e) {
			throw new NotFoundException("Handle does not exist: " + handle);
		}
	}

	@GET
	@Produces({MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
	@Template(name = "/handcash.ftl")
	public Map<String, Object> getHtml() {
		Map<String, Object> map = new HashMap<>();
		map.put("model", get());
		map.put("handle", handle);
		return map;
	}

	@Override
	public HandlePhotoResource photo() {
		return CDI.current().select(HandlePhotoEndpoint.class).get();
	}

}
