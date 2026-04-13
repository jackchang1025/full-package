package org.bouncycastle.tls.crypto.impl.jcajce;

import java.security.DigestException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.SecureRandomSpi;
import org.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.jcajce.util.NamedJcaJceHelper;
import org.bouncycastle.jcajce.util.ProviderJcaJceHelper;
import org.bouncycastle.tls.crypto.TlsCryptoProvider;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class JcaTlsCryptoProvider implements TlsCryptoProvider {
    private JcaJceHelper helper = new DefaultJcaJceHelper();

    public static class NonceEntropySource extends SecureRandom {

        public static class NonceEntropySourceSpi extends SecureRandomSpi {
            private final MessageDigest digest;
            private final byte[] seed;
            private final SecureRandom source;
            private final byte[] state;

            public NonceEntropySourceSpi(SecureRandom secureRandom, MessageDigest messageDigest) {
                this.source = secureRandom;
                this.digest = messageDigest;
                byte[] generateSeed = secureRandom.generateSeed(messageDigest.getDigestLength());
                this.seed = generateSeed;
                this.state = new byte[generateSeed.length];
            }

            private void runDigest(byte[] bArr, byte[] bArr2, byte[] bArr3) {
                this.digest.update(bArr);
                this.digest.update(bArr2);
                try {
                    this.digest.digest(bArr3, 0, bArr3.length);
                } catch (DigestException e2) {
                    throw Exceptions.illegalStateException("unable to generate nonce data: " + e2.getMessage(), e2);
                }
            }

            @Override // java.security.SecureRandomSpi
            public byte[] engineGenerateSeed(int i2) {
                return this.source.generateSeed(i2);
            }

            @Override // java.security.SecureRandomSpi
            public void engineNextBytes(byte[] bArr) {
                synchronized (this.digest) {
                    int length = this.state.length;
                    int i2 = 0;
                    while (i2 != bArr.length) {
                        byte[] bArr2 = this.state;
                        if (length == bArr2.length) {
                            this.source.nextBytes(bArr2);
                            byte[] bArr3 = this.seed;
                            byte[] bArr4 = this.state;
                            runDigest(bArr3, bArr4, bArr4);
                            length = 0;
                        }
                        bArr[i2] = this.state[length];
                        i2++;
                        length++;
                    }
                }
            }

            @Override // java.security.SecureRandomSpi
            public void engineSetSeed(byte[] bArr) {
                synchronized (this.digest) {
                    byte[] bArr2 = this.seed;
                    runDigest(bArr2, bArr, bArr2);
                }
            }
        }

        public NonceEntropySource(JcaJceHelper jcaJceHelper, SecureRandom secureRandom) {
            super(new NonceEntropySourceSpi(secureRandom, jcaJceHelper.createDigest("SHA-512")), secureRandom.getProvider());
        }
    }

    public JcaJceHelper getHelper() {
        return this.helper;
    }

    public JcaTlsCryptoProvider setProvider(String str) {
        this.helper = new NamedJcaJceHelper(str);
        return this;
    }

    public JcaTlsCryptoProvider setProvider(Provider provider) {
        this.helper = new ProviderJcaJceHelper(provider);
        return this;
    }

    @Override // org.bouncycastle.tls.crypto.TlsCryptoProvider
    public JcaTlsCrypto create(SecureRandom secureRandom) {
        if (secureRandom == null) {
            try {
                JcaJceHelper jcaJceHelper = this.helper;
                secureRandom = jcaJceHelper instanceof DefaultJcaJceHelper ? SecureRandom.getInstance("DEFAULT") : SecureRandom.getInstance("DEFAULT", jcaJceHelper.createDigest("SHA-512").getProvider());
            } catch (GeneralSecurityException e2) {
                throw Exceptions.illegalStateException(AbstractC0000a.m19o(e2, new StringBuilder("unable to create JcaTlsCrypto: ")), e2);
            }
        }
        return create(secureRandom, (SecureRandom) new NonceEntropySource(this.helper, secureRandom));
    }

    @Override // org.bouncycastle.tls.crypto.TlsCryptoProvider
    public JcaTlsCrypto create(SecureRandom secureRandom, SecureRandom secureRandom2) {
        return new JcaTlsCrypto(this.helper, secureRandom, secureRandom2);
    }
}
