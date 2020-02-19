package org.hackunix.handcash_to.wallet;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.script.Script;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class WalletTest {

	@Test
	public void testWalletInitialization() throws UnreadableWalletException {
		NetworkParameters params = MainNetParams.get();
		String seedCode = "weather urban kid since alone nation accident aspect vault great detail fitness";
		DeterministicSeed seed = new DeterministicSeed(seedCode, null, "", LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
	    Wallet walletBip44 = Wallet.fromSeed(
	    		params, seed, Script.ScriptType.P2PKH,
	    		ImmutableList.of(new ChildNumber(44, true), new ChildNumber(236, true), ChildNumber.ZERO_HARDENED)
	    		);
		//Wallet restoredWallet = Wallet.fromSeed(params, seed, Script.ScriptType.P2PKH);
	}

}
