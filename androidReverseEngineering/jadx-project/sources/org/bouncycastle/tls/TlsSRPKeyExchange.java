package org.bouncycastle.tls;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import org.bouncycastle.tls.crypto.TlsCertificate;
import org.bouncycastle.tls.crypto.TlsSRP6Client;
import org.bouncycastle.tls.crypto.TlsSRP6Server;
import org.bouncycastle.tls.crypto.TlsSRPConfig;
import org.bouncycastle.tls.crypto.TlsSecret;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.BigIntegers;
import org.bouncycastle.util.io.TeeInputStream;

/* loaded from: classes.dex */
public class TlsSRPKeyExchange extends AbstractTlsKeyExchange {
    protected TlsCertificate serverCertificate;
    protected TlsCredentialedSigner serverCredentials;
    protected TlsSRP6Client srpClient;
    protected TlsSRPConfigVerifier srpConfigVerifier;
    protected TlsSRPIdentity srpIdentity;
    protected TlsSRPLoginParameters srpLoginParameters;
    protected BigInteger srpPeerCredentials;
    protected byte[] srpSalt;
    protected TlsSRP6Server srpServer;

    public TlsSRPKeyExchange(int i2, TlsSRPIdentity tlsSRPIdentity, TlsSRPConfigVerifier tlsSRPConfigVerifier) {
        super(checkKeyExchange(i2));
        this.serverCertificate = null;
        this.srpSalt = null;
        this.srpClient = null;
        this.serverCredentials = null;
        this.srpServer = null;
        this.srpPeerCredentials = null;
        this.srpIdentity = tlsSRPIdentity;
        this.srpConfigVerifier = tlsSRPConfigVerifier;
    }

    private static int checkKeyExchange(int i2) {
        switch (i2) {
            case 21:
            case 22:
            case 23:
                return i2;
            default:
                throw new IllegalArgumentException("unsupported key exchange algorithm");
        }
    }

    public static BigInteger validatePublicValue(BigInteger bigInteger, BigInteger bigInteger2) {
        BigInteger mod = bigInteger2.mod(bigInteger);
        if (mod.equals(BigInteger.ZERO)) {
            throw new TlsFatalAlert((short) 47);
        }
        return mod;
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public void generateClientKeyExchange(OutputStream outputStream) {
        byte[] sRPIdentity = this.srpIdentity.getSRPIdentity();
        TlsSRPUtils.writeSRPParameter(this.srpClient.generateClientCredentials(this.srpSalt, sRPIdentity, this.srpIdentity.getSRPPassword()), outputStream);
        this.context.getSecurityParametersHandshake().srpIdentity = Arrays.clone(sRPIdentity);
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public TlsSecret generatePreMasterSecret() {
        TlsSRP6Server tlsSRP6Server = this.srpServer;
        return this.context.getCrypto().createSecret(BigIntegers.asUnsignedByteArray(tlsSRP6Server != null ? tlsSRP6Server.calculateSecret(this.srpPeerCredentials) : this.srpClient.calculateSecret(this.srpPeerCredentials)));
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchange, org.bouncycastle.tls.TlsKeyExchange
    public byte[] generateServerKeyExchange() {
        TlsSRPConfig config = this.srpLoginParameters.getConfig();
        TlsSRP6Server createSRP6Server = this.context.getCrypto().createSRP6Server(config, this.srpLoginParameters.getVerifier());
        this.srpServer = createSRP6Server;
        BigInteger generateServerCredentials = createSRP6Server.generateServerCredentials();
        BigInteger[] explicitNG = config.getExplicitNG();
        ServerSRPParams serverSRPParams = new ServerSRPParams(explicitNG[0], explicitNG[1], this.srpLoginParameters.getSalt(), generateServerCredentials);
        DigestInputBuffer digestInputBuffer = new DigestInputBuffer();
        serverSRPParams.encode(digestInputBuffer);
        TlsCredentialedSigner tlsCredentialedSigner = this.serverCredentials;
        if (tlsCredentialedSigner != null) {
            TlsUtils.generateServerKeyExchangeSignature(this.context, tlsCredentialedSigner, null, digestInputBuffer);
        }
        return digestInputBuffer.toByteArray();
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public void processClientCredentials(TlsCredentials tlsCredentials) {
        throw new TlsFatalAlert((short) 80);
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchange, org.bouncycastle.tls.TlsKeyExchange
    public void processClientKeyExchange(InputStream inputStream) {
        this.srpPeerCredentials = validatePublicValue(this.srpLoginParameters.getConfig().getExplicitNG()[0], TlsSRPUtils.readSRPParameter(inputStream));
        this.context.getSecurityParametersHandshake().srpIdentity = Arrays.clone(this.srpLoginParameters.getIdentity());
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchange, org.bouncycastle.tls.TlsKeyExchange
    public void processServerCertificate(Certificate certificate) {
        if (this.keyExchange == 21) {
            throw new TlsFatalAlert((short) 80);
        }
        this.serverCertificate = certificate.getCertificateAt(0);
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public void processServerCredentials(TlsCredentials tlsCredentials) {
        if (this.keyExchange == 21) {
            throw new TlsFatalAlert((short) 80);
        }
        this.serverCredentials = TlsUtils.requireSignerCredentials(tlsCredentials);
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchange, org.bouncycastle.tls.TlsKeyExchange
    public void processServerKeyExchange(InputStream inputStream) {
        InputStream inputStream2;
        DigestInputBuffer digestInputBuffer;
        if (this.keyExchange != 21) {
            digestInputBuffer = new DigestInputBuffer();
            inputStream2 = new TeeInputStream(inputStream, digestInputBuffer);
        } else {
            inputStream2 = inputStream;
            digestInputBuffer = null;
        }
        ServerSRPParams parse = ServerSRPParams.parse(inputStream2);
        if (digestInputBuffer != null) {
            TlsUtils.verifyServerKeyExchangeSignature(this.context, inputStream, this.serverCertificate, null, digestInputBuffer);
        }
        TlsSRPConfig tlsSRPConfig = new TlsSRPConfig();
        tlsSRPConfig.setExplicitNG(new BigInteger[]{parse.getN(), parse.getG()});
        if (!this.srpConfigVerifier.accept(tlsSRPConfig)) {
            throw new TlsFatalAlert((short) 71);
        }
        this.srpSalt = parse.getS();
        this.srpPeerCredentials = validatePublicValue(parse.getN(), parse.getB());
        this.srpClient = this.context.getCrypto().createSRP6Client(tlsSRPConfig);
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchange, org.bouncycastle.tls.TlsKeyExchange
    public boolean requiresServerKeyExchange() {
        return true;
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public void skipServerCredentials() {
        if (this.keyExchange != 21) {
            throw new TlsFatalAlert((short) 80);
        }
    }

    public TlsSRPKeyExchange(int i2, TlsSRPLoginParameters tlsSRPLoginParameters) {
        super(checkKeyExchange(i2));
        this.serverCertificate = null;
        this.srpSalt = null;
        this.srpClient = null;
        this.serverCredentials = null;
        this.srpServer = null;
        this.srpPeerCredentials = null;
        this.srpLoginParameters = tlsSRPLoginParameters;
    }
}
