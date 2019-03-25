package org.hackunix.handcash_to.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

import org.hackunix.handcash_to.api.QrResource;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

@RequestScoped
public class QrEndpoint implements QrResource {

	@PathParam("address")
	private String address;

	@PathParam("uri")
	private String uri;

	@Override
	public StreamingOutput getPng() {
		return new QrStreamingOutput(ImageType.PNG);
	}

	@Override
	public StreamingOutput getJpg() {
		return new QrStreamingOutput(ImageType.JPG);
	}

	@Override
	public StreamingOutput getGif() {
		return new QrStreamingOutput(ImageType.GIF);
	}

	class QrStreamingOutput implements StreamingOutput {
		ImageType type;

		QrStreamingOutput(ImageType type) {
			this.type = type;
		}

		@Override
		public void write(OutputStream output) throws IOException, WebApplicationException {
			String text = "";
			if (address != null) {
				text = address;
			} else if (uri != null) {
				text = URLDecoder.decode(uri, "UTF-8");
			}
			QRCode.from(text).to(type).withSize(250, 250).stream().writeTo(output);
		}
	};

}
