package org.bouncycastle.jcajce.provider.digest;

import org.bouncycastle.asn1.BERTags;
import org.bouncycastle.asn1.misc.MiscObjectIdentifiers;
import org.bouncycastle.crypto.digests.Blake2sDigest;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.tls.CipherSuite;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class Blake2s {

    public static class Blake2s128 extends BCMessageDigest implements Cloneable {
        public Blake2s128() {
            super(new Blake2sDigest(128));
        }

        @Override // java.security.MessageDigest, java.security.MessageDigestSpi
        public Object clone() {
            Blake2s128 blake2s128 = (Blake2s128) super.clone();
            blake2s128.digest = new Blake2sDigest((Blake2sDigest) this.digest);
            return blake2s128;
        }
    }

    public static class Blake2s160 extends BCMessageDigest implements Cloneable {
        public Blake2s160() {
            super(new Blake2sDigest(CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256));
        }

        @Override // java.security.MessageDigest, java.security.MessageDigestSpi
        public Object clone() {
            Blake2s160 blake2s160 = (Blake2s160) super.clone();
            blake2s160.digest = new Blake2sDigest((Blake2sDigest) this.digest);
            return blake2s160;
        }
    }

    public static class Blake2s224 extends BCMessageDigest implements Cloneable {
        public Blake2s224() {
            super(new Blake2sDigest(BERTags.FLAGS));
        }

        @Override // java.security.MessageDigest, java.security.MessageDigestSpi
        public Object clone() {
            Blake2s224 blake2s224 = (Blake2s224) super.clone();
            blake2s224.digest = new Blake2sDigest((Blake2sDigest) this.digest);
            return blake2s224;
        }
    }

    public static class Blake2s256 extends BCMessageDigest implements Cloneable {
        public Blake2s256() {
            super(new Blake2sDigest(256));
        }

        @Override // java.security.MessageDigest, java.security.MessageDigestSpi
        public Object clone() {
            Blake2s256 blake2s256 = (Blake2s256) super.clone();
            blake2s256.digest = new Blake2sDigest((Blake2sDigest) this.digest);
            return blake2s256;
        }
    }

    public static class Mappings extends DigestAlgorithmProvider {
        private static final String PREFIX = Blake2s.class.getName();

        @Override // org.bouncycastle.jcajce.provider.util.AlgorithmProvider
        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder sb = new StringBuilder();
            String str = PREFIX;
            sb.append(str);
            sb.append("$Blake2s256");
            configurableProvider.addAlgorithm("MessageDigest.BLAKE2S-256", sb.toString());
            StringBuilder m1005C = AbstractC0413b.m1005C(new StringBuilder("Alg.Alias.MessageDigest."), MiscObjectIdentifiers.id_blake2s256, configurableProvider, "BLAKE2S-256", str);
            m1005C.append("$Blake2s224");
            configurableProvider.addAlgorithm("MessageDigest.BLAKE2S-224", m1005C.toString());
            StringBuilder m1005C2 = AbstractC0413b.m1005C(new StringBuilder("Alg.Alias.MessageDigest."), MiscObjectIdentifiers.id_blake2s224, configurableProvider, "BLAKE2S-224", str);
            m1005C2.append("$Blake2s160");
            configurableProvider.addAlgorithm("MessageDigest.BLAKE2S-160", m1005C2.toString());
            StringBuilder m1005C3 = AbstractC0413b.m1005C(new StringBuilder("Alg.Alias.MessageDigest."), MiscObjectIdentifiers.id_blake2s160, configurableProvider, "BLAKE2S-160", str);
            m1005C3.append("$Blake2s128");
            configurableProvider.addAlgorithm("MessageDigest.BLAKE2S-128", m1005C3.toString());
            AbstractC0413b.m1003A(new StringBuilder("Alg.Alias.MessageDigest."), MiscObjectIdentifiers.id_blake2s128, configurableProvider, "BLAKE2S-128");
        }
    }

    private Blake2s() {
    }
}
