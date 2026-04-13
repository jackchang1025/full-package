package org.bouncycastle.cms.bc;

import java.io.OutputStream;
import java.security.SecureRandom;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.crypto.modes.AEADBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.util.CipherFactory;
import org.bouncycastle.operator.DefaultSecretKeySizeProvider;
import org.bouncycastle.operator.GenericKey;
import org.bouncycastle.operator.MacCaptureStream;
import org.bouncycastle.operator.OutputAEADEncryptor;
import org.bouncycastle.operator.OutputEncryptor;
import org.bouncycastle.operator.SecretKeySizeProvider;
import org.bouncycastle.tls.CipherSuite;

/* loaded from: classes.dex */
public class BcCMSContentEncryptorBuilder {
    private static final SecretKeySizeProvider KEY_SIZE_PROVIDER = DefaultSecretKeySizeProvider.INSTANCE;
    private final ASN1ObjectIdentifier encryptionOID;
    private EnvelopedDataHelper helper;
    private final int keySize;
    private SecureRandom random;

    public static class AADStream extends OutputStream {
        private AEADBlockCipher cipher;

        public AADStream(AEADBlockCipher aEADBlockCipher) {
            this.cipher = aEADBlockCipher;
        }

        @Override // java.io.OutputStream
        public void write(int i2) {
            this.cipher.processAADByte((byte) i2);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i2, int i3) {
            this.cipher.processAADBytes(bArr, i2, i3);
        }
    }

    public class CMSAuthOutputEncryptor extends CMSOutputEncryptor implements OutputAEADEncryptor {
        private AEADBlockCipher aeadCipher;
        private MacCaptureStream macOut;

        public CMSAuthOutputEncryptor(ASN1ObjectIdentifier aSN1ObjectIdentifier, int i2, SecureRandom secureRandom) {
            super(aSN1ObjectIdentifier, i2, secureRandom);
            this.aeadCipher = getCipher();
        }

        private AEADBlockCipher getCipher() {
            Object obj = this.cipher;
            if (obj instanceof AEADBlockCipher) {
                return (AEADBlockCipher) obj;
            }
            throw new IllegalArgumentException("Unable to create Authenticated Output Encryptor without Authenticaed Data cipher!");
        }

        @Override // org.bouncycastle.operator.AADProcessor
        public OutputStream getAADStream() {
            return new AADStream(this.aeadCipher);
        }

        @Override // org.bouncycastle.operator.AADProcessor
        public byte[] getMAC() {
            return this.macOut.getMac();
        }

        @Override // org.bouncycastle.cms.bc.BcCMSContentEncryptorBuilder.CMSOutputEncryptor, org.bouncycastle.operator.OutputEncryptor
        public OutputStream getOutputStream(OutputStream outputStream) {
            MacCaptureStream macCaptureStream = new MacCaptureStream(outputStream, this.aeadCipher.getMac().length);
            this.macOut = macCaptureStream;
            return CipherFactory.createOutputStream(macCaptureStream, this.cipher);
        }
    }

    public class CMSOutputEncryptor implements OutputEncryptor {
        private AlgorithmIdentifier algorithmIdentifier;
        protected Object cipher;
        private KeyParameter encKey;

        public CMSOutputEncryptor(ASN1ObjectIdentifier aSN1ObjectIdentifier, int i2, SecureRandom secureRandom) {
            secureRandom = secureRandom == null ? new SecureRandom() : secureRandom;
            this.encKey = new KeyParameter(BcCMSContentEncryptorBuilder.this.helper.createKeyGenerator(aSN1ObjectIdentifier, i2, secureRandom).generateKey());
            AlgorithmIdentifier generateEncryptionAlgID = BcCMSContentEncryptorBuilder.this.helper.generateEncryptionAlgID(aSN1ObjectIdentifier, this.encKey, secureRandom);
            this.algorithmIdentifier = generateEncryptionAlgID;
            this.cipher = EnvelopedDataHelper.createContentCipher(true, this.encKey, generateEncryptionAlgID);
        }

        @Override // org.bouncycastle.operator.OutputEncryptor
        public AlgorithmIdentifier getAlgorithmIdentifier() {
            return this.algorithmIdentifier;
        }

        @Override // org.bouncycastle.operator.OutputEncryptor
        public GenericKey getKey() {
            return new GenericKey(this.algorithmIdentifier, this.encKey.getKey());
        }

        @Override // org.bouncycastle.operator.OutputEncryptor
        public OutputStream getOutputStream(OutputStream outputStream) {
            return CipherFactory.createOutputStream(outputStream, this.cipher);
        }
    }

    public BcCMSContentEncryptorBuilder(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this(aSN1ObjectIdentifier, KEY_SIZE_PROVIDER.getKeySize(aSN1ObjectIdentifier));
    }

    public OutputEncryptor build() {
        return this.helper.isAuthEnveloped(this.encryptionOID) ? new CMSAuthOutputEncryptor(this.encryptionOID, this.keySize, this.random) : new CMSOutputEncryptor(this.encryptionOID, this.keySize, this.random);
    }

    public BcCMSContentEncryptorBuilder setSecureRandom(SecureRandom secureRandom) {
        this.random = secureRandom;
        return this;
    }

    public BcCMSContentEncryptorBuilder(ASN1ObjectIdentifier aSN1ObjectIdentifier, int i2) {
        int i3;
        this.helper = new EnvelopedDataHelper();
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
}
