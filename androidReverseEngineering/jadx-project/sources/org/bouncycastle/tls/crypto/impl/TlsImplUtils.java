package org.bouncycastle.tls.crypto.impl;

import org.bouncycastle.tls.ProtocolVersion;
import org.bouncycastle.tls.SecurityParameters;
import org.bouncycastle.tls.crypto.TlsCryptoParameters;
import org.bouncycastle.tls.crypto.TlsSecret;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class TlsImplUtils {
    public static TlsSecret PRF(SecurityParameters securityParameters, TlsSecret tlsSecret, String str, byte[] bArr, int i2) {
        return tlsSecret.deriveUsingPRF(securityParameters.getPRFAlgorithm(), str, bArr, i2);
    }

    public static byte[] calculateKeyBlock(TlsCryptoParameters tlsCryptoParameters, int i2) {
        SecurityParameters securityParametersHandshake = tlsCryptoParameters.getSecurityParametersHandshake();
        return PRF(securityParametersHandshake, securityParametersHandshake.getMasterSecret(), "key expansion", Arrays.concatenate(securityParametersHandshake.getServerRandom(), securityParametersHandshake.getClientRandom()), i2).extract();
    }

    public static boolean isSSL(TlsCryptoParameters tlsCryptoParameters) {
        return tlsCryptoParameters.getServerVersion().isSSL();
    }

    public static boolean isTLSv10(ProtocolVersion protocolVersion) {
        return ProtocolVersion.TLSv10.isEqualOrEarlierVersionOf(protocolVersion.getEquivalentTLSVersion());
    }

    public static boolean isTLSv11(ProtocolVersion protocolVersion) {
        return ProtocolVersion.TLSv11.isEqualOrEarlierVersionOf(protocolVersion.getEquivalentTLSVersion());
    }

    public static boolean isTLSv12(ProtocolVersion protocolVersion) {
        return ProtocolVersion.TLSv12.isEqualOrEarlierVersionOf(protocolVersion.getEquivalentTLSVersion());
    }

    public static boolean isTLSv13(ProtocolVersion protocolVersion) {
        return ProtocolVersion.TLSv13.isEqualOrEarlierVersionOf(protocolVersion.getEquivalentTLSVersion());
    }

    public static TlsSecret PRF(TlsCryptoParameters tlsCryptoParameters, TlsSecret tlsSecret, String str, byte[] bArr, int i2) {
        return PRF(tlsCryptoParameters.getSecurityParametersHandshake(), tlsSecret, str, bArr, i2);
    }

    public static boolean isTLSv10(TlsCryptoParameters tlsCryptoParameters) {
        return isTLSv10(tlsCryptoParameters.getServerVersion());
    }

    public static boolean isTLSv11(TlsCryptoParameters tlsCryptoParameters) {
        return isTLSv11(tlsCryptoParameters.getServerVersion());
    }

    public static boolean isTLSv12(TlsCryptoParameters tlsCryptoParameters) {
        return isTLSv12(tlsCryptoParameters.getServerVersion());
    }

    public static boolean isTLSv13(TlsCryptoParameters tlsCryptoParameters) {
        return isTLSv13(tlsCryptoParameters.getServerVersion());
    }
}
