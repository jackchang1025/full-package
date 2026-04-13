package org.bouncycastle.cms.jcajce;

import java.io.OutputStream;
import java.security.AccessController;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.PrivilegedAction;
import java.security.Provider;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.cms.GCMParameters;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.jcajce.io.CipherOutputStream;
import org.bouncycastle.operator.DefaultSecretKeySizeProvider;
import org.bouncycastle.operator.GenericKey;
import org.bouncycastle.operator.MacCaptureStream;
import org.bouncycastle.operator.OutputAEADEncryptor;
import org.bouncycastle.operator.OutputEncryptor;
import org.bouncycastle.operator.SecretKeySizeProvider;
import org.bouncycastle.operator.jcajce.JceGenericKey;
import org.bouncycastle.tls.CipherSuite;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class JceCMSContentEncryptorBuilder {
    private static final SecretKeySizeProvider KEY_SIZE_PROVIDER = DefaultSecretKeySizeProvider.INSTANCE;
    private AlgorithmIdentifier algorithmIdentifier;
    private AlgorithmParameters algorithmParameters;
    private final ASN1ObjectIdentifier encryptionOID;
    private EnvelopedDataHelper helper;
    private final int keySize;
    private SecureRandom random;

    public class CMSAuthOutputEncryptor implements OutputAEADEncryptor {
        private AlgorithmIdentifier algorithmIdentifier;
        private Cipher cipher;
        private SecretKey encKey;
        private MacCaptureStream macOut;

        public CMSAuthOutputEncryptor(ASN1ObjectIdentifier aSN1ObjectIdentifier, int i2, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) {
            KeyGenerator createKeyGenerator = JceCMSContentEncryptorBuilder.this.helper.createKeyGenerator(aSN1ObjectIdentifier);
            SecureRandom secureRandom2 = CryptoServicesRegistrar.getSecureRandom(secureRandom);
            if (i2 < 0) {
                createKeyGenerator.init(secureRandom2);
            } else {
                createKeyGenerator.init(i2, secureRandom2);
            }
            this.cipher = JceCMSContentEncryptorBuilder.this.helper.createCipher(aSN1ObjectIdentifier);
            this.encKey = createKeyGenerator.generateKey();
            algorithmParameters = algorithmParameters == null ? JceCMSContentEncryptorBuilder.this.helper.generateParameters(aSN1ObjectIdentifier, this.encKey, secureRandom2) : algorithmParameters;
            try {
                this.cipher.init(1, this.encKey, algorithmParameters, secureRandom2);
                this.algorithmIdentifier = JceCMSContentEncryptorBuilder.this.helper.getAlgorithmIdentifier(aSN1ObjectIdentifier, algorithmParameters == null ? this.cipher.getParameters() : algorithmParameters);
            } catch (GeneralSecurityException e2) {
                throw new CMSException(AbstractC0000a.m19o(e2, new StringBuilder("unable to initialize cipher: ")), e2);
            }
        }

        @Override // org.bouncycastle.operator.AADProcessor
        public OutputStream getAADStream() {
            if (JceCMSContentEncryptorBuilder.checkForAEAD()) {
                return new JceAADStream(this.cipher);
            }
            return null;
        }

        @Override // org.bouncycastle.operator.OutputEncryptor
        public AlgorithmIdentifier getAlgorithmIdentifier() {
            return this.algorithmIdentifier;
        }

        @Override // org.bouncycastle.operator.OutputEncryptor
        public GenericKey getKey() {
            return new JceGenericKey(this.algorithmIdentifier, this.encKey);
        }

        @Override // org.bouncycastle.operator.AADProcessor
        public byte[] getMAC() {
            return this.macOut.getMac();
        }

        @Override // org.bouncycastle.operator.OutputEncryptor
        public OutputStream getOutputStream(OutputStream outputStream) {
            this.macOut = new MacCaptureStream(outputStream, GCMParameters.getInstance(this.algorithmIdentifier.getParameters()).getIcvLen());
            return new CipherOutputStream(this.macOut, this.cipher);
        }
    }

    public class CMSOutputEncryptor implements OutputEncryptor {
        private AlgorithmIdentifier algorithmIdentifier;
        private Cipher cipher;
        private SecretKey encKey;

        public CMSOutputEncryptor(ASN1ObjectIdentifier aSN1ObjectIdentifier, int i2, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) {
            KeyGenerator createKeyGenerator = JceCMSContentEncryptorBuilder.this.helper.createKeyGenerator(aSN1ObjectIdentifier);
            SecureRandom secureRandom2 = CryptoServicesRegistrar.getSecureRandom(secureRandom);
            if (i2 < 0) {
                createKeyGenerator.init(secureRandom2);
            } else {
                createKeyGenerator.init(i2, secureRandom2);
            }
            this.cipher = JceCMSContentEncryptorBuilder.this.helper.createCipher(aSN1ObjectIdentifier);
            this.encKey = createKeyGenerator.generateKey();
            algorithmParameters = algorithmParameters == null ? JceCMSContentEncryptorBuilder.this.helper.generateParameters(aSN1ObjectIdentifier, this.encKey, secureRandom2) : algorithmParameters;
            try {
                this.cipher.init(1, this.encKey, algorithmParameters, secureRandom2);
                this.algorithmIdentifier = JceCMSContentEncryptorBuilder.this.helper.getAlgorithmIdentifier(aSN1ObjectIdentifier, algorithmParameters == null ? this.cipher.getParameters() : algorithmParameters);
            } catch (GeneralSecurityException e2) {
                throw new CMSException(AbstractC0000a.m19o(e2, new StringBuilder("unable to initialize cipher: ")), e2);
            }
        }

        @Override // org.bouncycastle.operator.OutputEncryptor
        public AlgorithmIdentifier getAlgorithmIdentifier() {
            return this.algorithmIdentifier;
        }

        @Override // org.bouncycastle.operator.OutputEncryptor
        public GenericKey getKey() {
            return new JceGenericKey(this.algorithmIdentifier, this.encKey);
        }

        @Override // org.bouncycastle.operator.OutputEncryptor
        public OutputStream getOutputStream(OutputStream outputStream) {
            return new CipherOutputStream(outputStream, this.cipher);
        }
    }

    public JceCMSContentEncryptorBuilder(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this(aSN1ObjectIdentifier, KEY_SIZE_PROVIDER.getKeySize(aSN1ObjectIdentifier));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean checkForAEAD() {
        return ((Boolean) AccessController.doPrivileged(new PrivilegedAction() { // from class: org.bouncycastle.cms.jcajce.JceCMSContentEncryptorBuilder.1
            @Override // java.security.PrivilegedAction
            public Object run() {
                try {
                    boolean z2 = true;
                    if (Cipher.class.getMethod("updateAAD", byte[].class) == null) {
                        z2 = false;
                    }
                    return Boolean.valueOf(z2);
                } catch (Exception unused) {
                    return Boolean.FALSE;
                }
            }
        })).booleanValue();
    }

    public OutputEncryptor build() {
        ASN1Encodable parameters;
        if (this.algorithmParameters != null) {
            return this.helper.isAuthEnveloped(this.encryptionOID) ? new CMSAuthOutputEncryptor(this.encryptionOID, this.keySize, this.algorithmParameters, this.random) : new CMSOutputEncryptor(this.encryptionOID, this.keySize, this.algorithmParameters, this.random);
        }
        AlgorithmIdentifier algorithmIdentifier = this.algorithmIdentifier;
        if (algorithmIdentifier != null && (parameters = algorithmIdentifier.getParameters()) != null && !parameters.equals(DERNull.INSTANCE)) {
            try {
                AlgorithmParameters createAlgorithmParameters = this.helper.createAlgorithmParameters(this.algorithmIdentifier.getAlgorithm());
                this.algorithmParameters = createAlgorithmParameters;
                createAlgorithmParameters.init(parameters.toASN1Primitive().getEncoded());
            } catch (Exception e2) {
                throw new CMSException(AbstractC0000a.m29y(e2, new StringBuilder("unable to process provided algorithmIdentifier: ")), e2);
            }
        }
        return this.helper.isAuthEnveloped(this.encryptionOID) ? new CMSAuthOutputEncryptor(this.encryptionOID, this.keySize, this.algorithmParameters, this.random) : new CMSOutputEncryptor(this.encryptionOID, this.keySize, this.algorithmParameters, this.random);
    }

    public JceCMSContentEncryptorBuilder setAlgorithmParameters(AlgorithmParameters algorithmParameters) {
        this.algorithmParameters = algorithmParameters;
        return this;
    }

    public JceCMSContentEncryptorBuilder setProvider(String str) {
        this.helper = new EnvelopedDataHelper(new NamedJcaJceExtHelper(str));
        return this;
    }

    public JceCMSContentEncryptorBuilder setSecureRandom(SecureRandom secureRandom) {
        this.random = secureRandom;
        return this;
    }

    public JceCMSContentEncryptorBuilder(ASN1ObjectIdentifier aSN1ObjectIdentifier, int i2) {
        int i3;
        this.helper = new EnvelopedDataHelper(new DefaultJcaJceExtHelper());
        this.encryptionOID = aSN1ObjectIdentifier;
        int keySize = KEY_SIZE_PROVIDER.getKeySize(aSN1ObjectIdentifier);
        if (aSN1ObjectIdentifier.equals((ASN1Primitive) PKCSObjectIdentifiers.des_EDE3_CBC)) {
            i3 = CipherSuite.TLS_PSK_WITH_AES_128_GCM_SHA256;
            if (i2 != 168 && i2 != keySize) {
                throw new IllegalArgumentException("incorrect keySize for encryptionOID passed to builder.");
            }
        } else {
            if (!aSN1ObjectIdentifier.equals((ASN1Primitive) OIWObjectIdentifiers.desCBC)) {
                if (keySize > 0 && keySize != i2) {
                    throw new IllegalArgumentException("incorrect keySize for encryptionOID passed to builder.");
                }
                this.keySize = i2;
                return;
            }
            i3 = 56;
            if (i2 != 56 && i2 != keySize) {
                throw new IllegalArgumentException("incorrect keySize for encryptionOID passed to builder.");
            }
        }
        this.keySize = i3;
    }

    public JceCMSContentEncryptorBuilder setProvider(Provider provider) {
        this.helper = new EnvelopedDataHelper(new ProviderJcaJceExtHelper(provider));
        return this;
    }

    public JceCMSContentEncryptorBuilder(AlgorithmIdentifier algorithmIdentifier) {
        this(algorithmIdentifier.getAlgorithm(), KEY_SIZE_PROVIDER.getKeySize(algorithmIdentifier.getAlgorithm()));
        this.algorithmIdentifier = algorithmIdentifier;
    }
}
