package org.hackunix.handcash_to.server;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.spi.CDI;
import javax.ws.rs.Path;

//import org.hackunix.handcash_to.api.BitcoinUriResource;
import org.hackunix.handcash_to.api.HandCashToResource;
import org.hackunix.handcash_to.api.HandCashHandleResource;
import org.hackunix.handcash_to.api.LegacyAddressResource;
import org.hackunix.handcash_to.api.PaymailHandleResource;
import org.hackunix.handcash_to.api.RelayXHandleResource;

@Path("/")
@RequestScoped
public class RootEndpoint implements HandCashToResource {

	@Override
	public HandCashHandleResource handcash(String handle) {
		return CDI.current().select(HandCashHandleEndpoint.class).get();
	}

	@Override
	public RelayXHandleResource relayx(String handle) {
		return CDI.current().select(RelayXHandleEndpoint.class).get();
	}

	@Override
	public PaymailHandleResource paymail(String handle) {
		return CDI.current().select(PaymailHandleEndpoint.class).get();
	}

	@Override
	public LegacyAddressResource address(String address) {
		return CDI.current().select(LegacyAddressEndpoint.class).get();
	}

//	@Override
//	public BitcoinUriResource uri(String uri) {
//		return resourceCtx.initResource(CDI.current().select(BitcoinUriEndpoint.class).get());
//	}

}
