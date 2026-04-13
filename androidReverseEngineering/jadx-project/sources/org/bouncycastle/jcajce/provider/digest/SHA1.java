package org.bouncycastle.jcajce.provider.digest;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.iana.IANAObjectIdentifiers;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseMac;
import org.bouncycastle.jcajce.provider.symmetric.util.PBESecretKeyFactory;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2KeyGenParameterSpec;
import org.bouncycastle.tls.CipherSuite;
import p000a.AbstractC0000a;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class SHA1 {

    public static class Digest extends BCMessageDigest implements Cloneable {
        public Digest() {
            super(new SHA1Digest());
        }

        @Override // java.security.MessageDigest, java.security.MessageDigestSpi
        public Object clone() {
            Digest digest = (Digest) super.clone();
            digest.digest = new SHA1Digest((SHA1Digest) this.digest);
            return digest;
        }
    }

    public static class HashMac extends BaseMac {
        public HashMac() {
            super(new HMac(new SHA1Digest()));
        }
    }

    public static class KeyGenerator extends BaseKeyGenerator {
        public KeyGenerator() {
            super("HMACSHA1", CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256, new CipherKeyGenerator());
        }
    }

    public static class Mappings extends DigestAlgorithmProvider {
        private static final String PREFIX = SHA1.class.getName();

        @Override // org.bouncycastle.jcajce.provider.util.AlgorithmProvider
        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder sb = new StringBuilder();
            String str = PREFIX;
            AbstractC0413b.m1030x(sb, str, "$Digest", configurableProvider, "MessageDigest.SHA-1");
            configurableProvider.addAlgorithm("Alg.Alias.MessageDigest.SHA1", McElieceCCA2KeyGenParameterSpec.SHA1);
            configurableProvider.addAlgorithm("Alg.Alias.MessageDigest.SHA", McElieceCCA2KeyGenParameterSpec.SHA1);
            StringBuilder sb2 = new StringBuilder("Alg.Alias.MessageDigest.");
            ASN1ObjectIdentifier aSN1ObjectIdentifier = OIWObjectIdentifiers.idSHA1;
            StringBuilder m1005C = AbstractC0413b.m1005C(sb2, aSN1ObjectIdentifier, configurableProvider, McElieceCCA2KeyGenParameterSpec.SHA1, str);
            m1005C.append("$HashMac");
            addHMACAlgorithm(configurableProvider, "SHA1", m1005C.toString(), AbstractC0000a.m30z(str, "$KeyGenerator"));
            addHMACAlias(configurableProvider, "SHA1", PKCSObjectIdentifiers.id_hmacWithSHA1);
            addHMACAlias(configurableProvider, "SHA1", IANAObjectIdentifiers.hmacSHA1);
            configurableProvider.addAlgorithm("Mac.PBEWITHHMACSHA", str + "$SHA1Mac");
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            AbstractC0413b.m1032z(sb3, "$SHA1Mac", configurableProvider, "Mac.PBEWITHHMACSHA1");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHHMACSHA", "PBEWITHHMACSHA1");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory." + aSN1ObjectIdentifier, "PBEWITHHMACSHA1");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHHMACSHA1", AbstractC0413b.m1015i(configurableProvider, "Alg.Alias.Mac." + aSN1ObjectIdentifier, "PBEWITHHMACSHA", str, "$PBEWithMacKeyFactory"));
        }
    }

    public static class PBEWithMacKeyFactory extends PBESecretKeyFactory {
        public PBEWithMacKeyFactory() {
            super("PBEwithHmacSHA", null, false, 2, 1, CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256, 0);
        }
    }

    public static class SHA1Mac extends BaseMac {
        public SHA1Mac() {
            super(new HMac(new SHA1Digest()));
        }
    }

    private SHA1() {
    }
}
