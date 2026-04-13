package org.bouncycastle.jcajce.provider.digest;

import android.sun.security.pkcs.PKCS9Attribute;
import org.bouncycastle.asn1.BERTags;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.digests.SHA3Digest;
import org.bouncycastle.crypto.digests.SHAKEDigest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseMac;
import p000a.AbstractC0000a;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class SHA3 {

    public static class Digest224 extends DigestSHA3 {
        public Digest224() {
            super(BERTags.FLAGS);
        }
    }

    public static class Digest256 extends DigestSHA3 {
        public Digest256() {
            super(256);
        }
    }

    public static class Digest384 extends DigestSHA3 {
        public Digest384() {
            super(384);
        }
    }

    public static class Digest512 extends DigestSHA3 {
        public Digest512() {
            super(512);
        }
    }

    public static class DigestSHA3 extends BCMessageDigest implements Cloneable {
        public DigestSHA3(int i2) {
            super(new SHA3Digest(i2));
        }

        @Override // java.security.MessageDigest, java.security.MessageDigestSpi
        public Object clone() {
            BCMessageDigest bCMessageDigest = (BCMessageDigest) super.clone();
            bCMessageDigest.digest = new SHA3Digest((SHA3Digest) this.digest);
            return bCMessageDigest;
        }
    }

    public static class DigestSHAKE extends BCMessageDigest implements Cloneable {
        public DigestSHAKE(int i2, int i3) {
            super(new SHAKEDigest(i2));
        }

        @Override // java.security.MessageDigest, java.security.MessageDigestSpi
        public Object clone() {
            BCMessageDigest bCMessageDigest = (BCMessageDigest) super.clone();
            bCMessageDigest.digest = new SHAKEDigest((SHAKEDigest) this.digest);
            return bCMessageDigest;
        }
    }

    public static class DigestShake128_256 extends DigestSHAKE {
        public DigestShake128_256() {
            super(128, 256);
        }
    }

    public static class DigestShake256_512 extends DigestSHAKE {
        public DigestShake256_512() {
            super(256, 512);
        }
    }

    public static class HashMac224 extends HashMacSHA3 {
        public HashMac224() {
            super(BERTags.FLAGS);
        }
    }

    public static class HashMac256 extends HashMacSHA3 {
        public HashMac256() {
            super(256);
        }
    }

    public static class HashMac384 extends HashMacSHA3 {
        public HashMac384() {
            super(384);
        }
    }

    public static class HashMac512 extends HashMacSHA3 {
        public HashMac512() {
            super(512);
        }
    }

    public static class HashMacSHA3 extends BaseMac {
        public HashMacSHA3(int i2) {
            super(new HMac(new SHA3Digest(i2)));
        }
    }

    public static class KeyGenerator224 extends KeyGeneratorSHA3 {
        public KeyGenerator224() {
            super(BERTags.FLAGS);
        }
    }

    public static class KeyGenerator256 extends KeyGeneratorSHA3 {
        public KeyGenerator256() {
            super(256);
        }
    }

    public static class KeyGenerator384 extends KeyGeneratorSHA3 {
        public KeyGenerator384() {
            super(384);
        }
    }

    public static class KeyGenerator512 extends KeyGeneratorSHA3 {
        public KeyGenerator512() {
            super(512);
        }
    }

    public static class KeyGeneratorSHA3 extends BaseKeyGenerator {
        public KeyGeneratorSHA3(int i2) {
            super(AbstractC0000a.m11g("HMACSHA3-", i2), i2, new CipherKeyGenerator());
        }
    }

    public static class Mappings extends DigestAlgorithmProvider {
        private static final String PREFIX = SHA3.class.getName();

        @Override // org.bouncycastle.jcajce.provider.util.AlgorithmProvider
        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder sb = new StringBuilder();
            String str = PREFIX;
            AbstractC0413b.m1030x(AbstractC0413b.m1016j(AbstractC0413b.m1016j(AbstractC0413b.m1016j(sb, str, "$Digest224", configurableProvider, "MessageDigest.SHA3-224"), str, "$Digest256", configurableProvider, "MessageDigest.SHA3-256"), str, "$Digest384", configurableProvider, "MessageDigest.SHA3-384"), str, "$Digest512", configurableProvider, "MessageDigest.SHA3-512");
            AbstractC0413b.m1029w(str, "$Digest224", configurableProvider, PKCS9Attribute.MESSAGE_DIGEST_STR, NISTObjectIdentifiers.id_sha3_224);
            AbstractC0413b.m1029w(str, "$Digest256", configurableProvider, PKCS9Attribute.MESSAGE_DIGEST_STR, NISTObjectIdentifiers.id_sha3_256);
            AbstractC0413b.m1029w(str, "$Digest384", configurableProvider, PKCS9Attribute.MESSAGE_DIGEST_STR, NISTObjectIdentifiers.id_sha3_384);
            configurableProvider.addAlgorithm(PKCS9Attribute.MESSAGE_DIGEST_STR, NISTObjectIdentifiers.id_sha3_512, str + "$Digest512");
            AbstractC0413b.m1030x(AbstractC0413b.m1016j(new StringBuilder(), str, "$DigestShake256_512", configurableProvider, "MessageDigest.SHAKE256-512"), str, "$DigestShake128_256", configurableProvider, "MessageDigest.SHAKE128-256");
            AbstractC0413b.m1029w(str, "$DigestShake256_512", configurableProvider, PKCS9Attribute.MESSAGE_DIGEST_STR, NISTObjectIdentifiers.id_shake256);
            AbstractC0413b.m1029w(str, "$DigestShake128_256", configurableProvider, PKCS9Attribute.MESSAGE_DIGEST_STR, NISTObjectIdentifiers.id_shake128);
            configurableProvider.addAlgorithm("Alg.Alias.MessageDigest.SHAKE256", "SHAKE256-512");
            configurableProvider.addAlgorithm("Alg.Alias.MessageDigest.SHAKE128", "SHAKE128-256");
            addHMACAlgorithm(configurableProvider, "SHA3-224", AbstractC0000a.m18n(new StringBuilder(), str, "$HashMac224"), AbstractC0000a.m30z(str, "$KeyGenerator224"));
            addHMACAlias(configurableProvider, "SHA3-224", NISTObjectIdentifiers.id_hmacWithSHA3_224);
            addHMACAlgorithm(configurableProvider, "SHA3-256", AbstractC0000a.m30z(str, "$HashMac256"), AbstractC0000a.m30z(str, "$KeyGenerator256"));
            addHMACAlias(configurableProvider, "SHA3-256", NISTObjectIdentifiers.id_hmacWithSHA3_256);
            addHMACAlgorithm(configurableProvider, "SHA3-384", AbstractC0000a.m30z(str, "$HashMac384"), AbstractC0000a.m30z(str, "$KeyGenerator384"));
            addHMACAlias(configurableProvider, "SHA3-384", NISTObjectIdentifiers.id_hmacWithSHA3_384);
            addHMACAlgorithm(configurableProvider, "SHA3-512", AbstractC0000a.m30z(str, "$HashMac512"), AbstractC0000a.m30z(str, "$KeyGenerator512"));
            addHMACAlias(configurableProvider, "SHA3-512", NISTObjectIdentifiers.id_hmacWithSHA3_512);
        }
    }

    private SHA3() {
    }
}
