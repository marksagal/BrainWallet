package biz.sagal.lib;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.DumpedPrivateKey;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Brain Wallet library
 * @author Mark Sagal <mark@sagal.biz>
 * @since  2018-10-13
 */
public class BrainWallet {
    /**
     * Reference of ECKey
     */
    private ECKey eckey;

    /**
     * BrainWallet constructor
     * @param passphrase Passphrase string
     */
    public BrainWallet(final String passphrase) {
        this.eckey = this.getECKey(passphrase);
    }

    /**
     * Gets address
     * @return Wallet address
     */
    public final String getAddress() {
        final Address address = this.eckey.toAddress(NetworkParameters.prodNet());
        return address.toString();
    }

    /**
     * Gets private key
     * @return Wallet private key (WIF)
     */
    public final String getPrivateKey() {
        final DumpedPrivateKey privateKey = this.eckey.getPrivateKeyEncoded(NetworkParameters.prodNet());
        return privateKey.toString();
    }

    /**
     * Gets ECKey
     * @param passphrase Get ECKey from passphrase
     * @return ECKey
     */
    private ECKey getECKey(final String passphrase) {
        byte[] hash = null;

        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(passphrase.getBytes(StandardCharsets.UTF_8));
            hash = md.digest();
        } catch (final NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return new ECKey(hash, null);
    }
}
