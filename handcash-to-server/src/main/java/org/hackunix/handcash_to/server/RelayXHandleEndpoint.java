package org.hackunix.handcash_to.server;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.mvc.Template;
import org.hackunix.handcash_io.api.Handle;
import org.hackunix.handcash_to.api.RelayXHandleResource;
import org.hackunix.relayx_io.client.RelayXClient;

import com.github.t1.log.LogLevel;
import com.github.t1.log.Logged;

@Logged(level = LogLevel.INFO)
@RequestScoped
public class RelayXHandleEndpoint implements RelayXHandleResource {

	@PathParam("handle")
	private String handle;

	private final RelayXClient relayx;

	@Inject
	public RelayXHandleEndpoint(RelayXClient relayx) {
		this.relayx = relayx;
	}

	@Override
	public Handle get() {
		Handle res = relayx.root().api().receivingAddress().handle(handle).get();
		if (res.receivingAddress == null) {
			throw new NotFoundException("Handle does not exist: " + handle);
		}
		return res;
	}

	@GET
	@Produces({MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
	@Template(name = "/relayx.ftl")
	public Map<String, Object> getHtml() {
		Map<String, Object> map = new HashMap<>();
		map.put("model", get());
		map.put("handle", handle);
		return map;
	}

}
