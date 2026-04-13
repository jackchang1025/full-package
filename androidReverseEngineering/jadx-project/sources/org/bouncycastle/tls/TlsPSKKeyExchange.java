package org.bouncycastle.tls;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.bouncycastle.tls.crypto.TlsAgreement;
import org.bouncycastle.tls.crypto.TlsCryptoParameters;
import org.bouncycastle.tls.crypto.TlsDHConfig;
import org.bouncycastle.tls.crypto.TlsECConfig;
import org.bouncycastle.tls.crypto.TlsEncryptor;
import org.bouncycastle.tls.crypto.TlsSecret;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class TlsPSKKeyExchange extends AbstractTlsKeyExchange {
    protected TlsAgreement agreement;
    protected TlsDHConfig dhConfig;
    protected TlsDHGroupVerifier dhGroupVerifier;
    protected TlsECConfig ecConfig;
    protected TlsSecret preMasterSecret;
    protected byte[] psk;
    protected TlsPSKIdentity pskIdentity;
    protected TlsPSKIdentityManager pskIdentityManager;
    protected byte[] psk_identity_hint;
    protected TlsCredentialedDecryptor serverCredentials;
    protected TlsEncryptor serverEncryptor;

    public TlsPSKKeyExchange(int i2, TlsPSKIdentity tlsPSKIdentity, TlsDHGroupVerifier tlsDHGroupVerifier) {
        this(i2, tlsPSKIdentity, null, tlsDHGroupVerifier, null, null);
    }

    private static int checkKeyExchange(int i2) {
        if (i2 != 24) {
            switch (i2) {
                case 13:
                case 14:
                case 15:
                    break;
                default:
                    throw new IllegalArgumentException("unsupported key exchange algorithm");
            }
        }
        return i2;
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public void generateClientKeyExchange(OutputStream outputStream) {
        byte[] bArr = this.psk_identity_hint;
        if (bArr == null) {
            this.pskIdentity.skipIdentityHint();
        } else {
            this.pskIdentity.notifyIdentityHint(bArr);
        }
        byte[] pSKIdentity = this.pskIdentity.getPSKIdentity();
        if (pSKIdentity == null) {
            throw new TlsFatalAlert((short) 80);
        }
        byte[] psk = this.pskIdentity.getPSK();
        this.psk = psk;
        if (psk == null) {
            throw new TlsFatalAlert((short) 80);
        }
        TlsUtils.writeOpaque16(pSKIdentity, outputStream);
        this.context.getSecurityParametersHandshake().pskIdentity = Arrays.clone(pSKIdentity);
        int i2 = this.keyExchange;
        if (i2 == 14) {
            generateEphemeralDH(outputStream);
        } else if (i2 == 24) {
            generateEphemeralECDH(outputStream);
        } else if (i2 == 15) {
            this.preMasterSecret = TlsUtils.generateEncryptedPreMasterSecret(this.context, this.serverEncryptor, outputStream);
        }
    }

    public void generateEphemeralDH(OutputStream outputStream) {
        TlsUtils.writeOpaque16(this.agreement.generateEphemeral(), outputStream);
    }

    public void generateEphemeralECDH(OutputStream outputStream) {
        TlsUtils.writeOpaque8(this.agreement.generateEphemeral(), outputStream);
    }

    public byte[] generateOtherSecret(int i2) {
        TlsAgreement tlsAgreement;
        TlsSecret tlsSecret;
        int i3 = this.keyExchange;
        if (i3 == 13) {
            return new byte[i2];
        }
        if ((i3 == 14 || i3 == 24) && (tlsAgreement = this.agreement) != null) {
            return tlsAgreement.calculateSecret().extract();
        }
        if (i3 != 15 || (tlsSecret = this.preMasterSecret) == null) {
            throw new TlsFatalAlert((short) 80);
        }
        return tlsSecret.extract();
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public TlsSecret generatePreMasterSecret() {
        byte[] generateOtherSecret = generateOtherSecret(this.psk.length);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(generateOtherSecret.length + 4 + this.psk.length);
        TlsUtils.writeOpaque16(generateOtherSecret, byteArrayOutputStream);
        TlsUtils.writeOpaque16(this.psk, byteArrayOutputStream);
        Arrays.fill(this.psk, (byte) 0);
        this.psk = null;
        return this.context.getCrypto().createSecret(byteArrayOutputStream.toByteArray());
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchange, org.bouncycastle.tls.TlsKeyExchange
    public byte[] generateServerKeyExchange() {
        byte[] hint = this.pskIdentityManager.getHint();
        this.psk_identity_hint = hint;
        if (hint == null && !requiresServerKeyExchange()) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = this.psk_identity_hint;
        if (bArr == null) {
            TlsUtils.writeOpaque16(TlsUtils.EMPTY_BYTES, byteArrayOutputStream);
        } else {
            TlsUtils.writeOpaque16(bArr, byteArrayOutputStream);
        }
        int i2 = this.keyExchange;
        if (i2 == 14) {
            TlsDHConfig tlsDHConfig = this.dhConfig;
            if (tlsDHConfig == null) {
                throw new TlsFatalAlert((short) 80);
            }
            TlsDHUtils.writeDHConfig(tlsDHConfig, byteArrayOutputStream);
            this.agreement = this.context.getCrypto().createDHDomain(this.dhConfig).createDH();
            generateEphemeralDH(byteArrayOutputStream);
        } else if (i2 == 24) {
            TlsECConfig tlsECConfig = this.ecConfig;
            if (tlsECConfig == null) {
                throw new TlsFatalAlert((short) 80);
            }
            TlsECCUtils.writeECConfig(tlsECConfig, byteArrayOutputStream);
            this.agreement = this.context.getCrypto().createECDomain(this.ecConfig).createECDH();
            generateEphemeralECDH(byteArrayOutputStream);
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public void processClientCredentials(TlsCredentials tlsCredentials) {
        throw new TlsFatalAlert((short) 80);
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchange, org.bouncycastle.tls.TlsKeyExchange
    public void processClientKeyExchange(InputStream inputStream) {
        byte[] readOpaque16 = TlsUtils.readOpaque16(inputStream);
        byte[] psk = this.pskIdentityManager.getPSK(readOpaque16);
        this.psk = psk;
        if (psk == null) {
            throw new TlsFatalAlert(AlertDescription.unknown_psk_identity);
        }
        this.context.getSecurityParametersHandshake().pskIdentity = readOpaque16;
        int i2 = this.keyExchange;
        if (i2 == 14) {
            processEphemeralDH(TlsUtils.readOpaque16(inputStream, 1));
            return;
        }
        if (i2 == 24) {
            processEphemeralECDH(TlsUtils.readOpaque8(inputStream, 1));
        } else if (i2 == 15) {
            this.preMasterSecret = this.serverCredentials.decrypt(new TlsCryptoParameters(this.context), TlsUtils.readEncryptedPMS(this.context, inputStream));
        }
    }

    public void processEphemeralDH(byte[] bArr) {
        this.agreement.receivePeerValue(bArr);
    }

    public void processEphemeralECDH(byte[] bArr) {
        TlsECCUtils.checkPointEncoding(this.ecConfig.getNamedGroup(), bArr);
        this.agreement.receivePeerValue(bArr);
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchange, org.bouncycastle.tls.TlsKeyExchange
    public void processServerCertificate(Certificate certificate) {
        if (this.keyExchange != 15) {
            throw new TlsFatalAlert((short) 10);
        }
        this.serverEncryptor = certificate.getCertificateAt(0).createEncryptor(3);
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public void processServerCredentials(TlsCredentials tlsCredentials) {
        if (this.keyExchange != 15) {
            throw new TlsFatalAlert((short) 80);
        }
        this.serverCredentials = TlsUtils.requireDecryptorCredentials(tlsCredentials);
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchange, org.bouncycastle.tls.TlsKeyExchange
    public void processServerKeyExchange(InputStream inputStream) {
        this.psk_identity_hint = TlsUtils.readOpaque16(inputStream);
        int i2 = this.keyExchange;
        if (i2 == 14) {
            this.dhConfig = TlsDHUtils.receiveDHConfig(this.context, this.dhGroupVerifier, inputStream);
            byte[] readOpaque16 = TlsUtils.readOpaque16(inputStream, 1);
            this.agreement = this.context.getCrypto().createDHDomain(this.dhConfig).createDH();
            processEphemeralDH(readOpaque16);
            return;
        }
        if (i2 == 24) {
            this.ecConfig = TlsECCUtils.receiveECDHConfig(this.context, inputStream);
            byte[] readOpaque8 = TlsUtils.readOpaque8(inputStream, 1);
            this.agreement = this.context.getCrypto().createECDomain(this.ecConfig).createECDH();
            processEphemeralECDH(readOpaque8);
        }
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchange, org.bouncycastle.tls.TlsKeyExchange
    public boolean requiresServerKeyExchange() {
        int i2 = this.keyExchange;
        return i2 == 14 || i2 == 24;
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public void skipServerCredentials() {
        if (this.keyExchange == 15) {
            throw new TlsFatalAlert((short) 80);
        }
    }

    private TlsPSKKeyExchange(int i2, TlsPSKIdentity tlsPSKIdentity, TlsPSKIdentityManager tlsPSKIdentityManager, TlsDHGroupVerifier tlsDHGroupVerifier, TlsDHConfig tlsDHConfig, TlsECConfig tlsECConfig) {
        super(checkKeyExchange(i2));
        this.psk_identity_hint = null;
        this.psk = null;
        this.serverCredentials = null;
        this.pskIdentity = tlsPSKIdentity;
        this.pskIdentityManager = tlsPSKIdentityManager;
        this.dhGroupVerifier = tlsDHGroupVerifier;
        this.dhConfig = tlsDHConfig;
        this.ecConfig = tlsECConfig;
    }

    public TlsPSKKeyExchange(int i2, TlsPSKIdentityManager tlsPSKIdentityManager, TlsDHConfig tlsDHConfig, TlsECConfig tlsECConfig) {
        this(i2, null, tlsPSKIdentityManager, null, tlsDHConfig, tlsECConfig);
    }
}
