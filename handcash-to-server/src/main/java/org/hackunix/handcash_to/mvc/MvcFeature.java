package org.hackunix.handcash_to.mvc;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;

@Provider
@ApplicationScoped
public class MvcFeature implements Feature {

	@Override
	public boolean configure(FeatureContext context) {
		context
		    .property(FreemarkerMvcFeature.TEMPLATE_BASE_PATH, "/templates")
		    .register(FreemarkerMvcFeature.class);
		return true;
	}

}
