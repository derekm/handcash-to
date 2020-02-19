package org.hackunix.handcash_to.server;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.enterprise.context.RequestScoped;
import javax.naming.NamingException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import org.bitcoinj.core.Base58;
import org.glassfish.jersey.server.mvc.Template;
import org.hackunix.bsvalias.api.objects.CapabilityDiscovery;
import org.hackunix.bsvalias.api.objects.PaymentRequest;
import org.hackunix.bsvalias.api.objects.PaymentResponse;
import org.hackunix.bsvalias.client.BsvaliasClient;
import org.hackunix.bsvalias.srv.SrvClient;
import org.hackunix.handcash_io.api.Handle;
import org.hackunix.handcash_to.api.PaymailHandleResource;
import org.hackunix.jersey.client.JerseyClient;

import com.github.t1.log.LogLevel;
import com.github.t1.log.Logged;

@Logged(level = LogLevel.INFO)
@RequestScoped
public class PaymailHandleEndpoint implements PaymailHandleResource {

	@PathParam("handle")
	private String handle;

	@Override
	public Handle get() throws Exception {
		String[] parts = handle.split("@");
		String authority;
		try {
			SrvClient srv = new SrvClient(handle);
			authority = srv.getAuthority();
		} catch (NamingException e) {
			authority = parts[1] + ":443";
		}
		String baseUrl = "https://" + authority + "/";
		BsvaliasClient client = new BsvaliasClient(baseUrl, false);
		CapabilityDiscovery cap = client.root().wellKnown().bsvalias().get();
		if (cap.capabilities.senderValidation == true) {
			throw new ForbiddenException("Sender Validation not yet supported: " + handle);
		}
//		Feature feature = new LoggingFeature(Logger.getLogger(PaymailHandleEndpoint.class.getName()), Level.INFO,
//				LoggingFeature.Verbosity.PAYLOAD_ANY, 30000);
		WebTarget target = JerseyClient.constructClient()
//				.register(feature)
				.target(cap.capabilities.paymentDestination)
				.resolveTemplate("alias", parts[0])
				.resolveTemplate("domain.tld", parts[1]);
		PaymentRequest req = new PaymentRequest();
		req.senderName = "paycash.to";
		req.senderHandle = "derekm@moneybutton.com";
		req.senderPaymail = "derekm@moneybutton.com";
		req.dt = Instant.now();
		PaymentResponse res;
		try {
			res = target.request(MediaType.APPLICATION_JSON)
					.post(Entity.json(req), PaymentResponse.class);
		} catch (NotFoundException e) {
			throw new NotFoundException("Handle does not exist: " + handle);
		}
		String address = outputScriptToBitcoinAddress(res.output);

		Handle alias = new Handle();
		alias.receivingAddress = address;
		return alias;
	}

	@GET
	@Produces({MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
	@Template(name = "/paymail.ftl")
	public Map<String, Object> getHtml() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("model", get());
		map.put("handle", handle);
		return map;
	}

	// http://lenschulwitz.com/base58
	// e.g., 76a9145ef2bcdeb545b080b019501d80b7e88bf7808e0e88ac
	private String outputScriptToBitcoinAddress(String output) throws Exception {
		Matcher matcher = Pattern.compile("76a914([0-9a-f]+)88ac").matcher(output);
		if (matcher.matches()) {
			String pkh = matcher.group(1);
			byte[] hex = new HexBinaryAdapter().unmarshal(pkh);
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			byteStream.write(0x00);
			byteStream.write(hex);
			hex = byteStream.toByteArray();
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] checksum = md.digest(md.digest(hex));
			ByteArrayOutputStream addrStream = new ByteArrayOutputStream();
			addrStream.write(hex);
			addrStream.write(checksum, 0, 4);
			byte[] addr = addrStream.toByteArray();
			return Base58.encode(addr);
		}
		throw new ForbiddenException("Output script not supported: " + handle + " " + output);
	}

}
