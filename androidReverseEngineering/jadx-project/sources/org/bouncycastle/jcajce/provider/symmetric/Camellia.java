package org.bouncycastle.jcajce.provider.symmetric;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ntt.NTTObjectIdentifiers;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.engines.CamelliaEngine;
import org.bouncycastle.crypto.engines.CamelliaWrapEngine;
import org.bouncycastle.crypto.engines.RFC3211WrapEngine;
import org.bouncycastle.crypto.generators.Poly1305KeyGenerator;
import org.bouncycastle.crypto.macs.GMac;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameterGenerator;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseMac;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher;
import org.bouncycastle.jcajce.provider.symmetric.util.BlockCipherProvider;
import org.bouncycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters;
import p000a.AbstractC0000a;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public final class Camellia {

    public static class AlgParamGen extends BaseAlgorithmParameterGenerator {
        @Override // java.security.AlgorithmParameterGeneratorSpi
        public AlgorithmParameters engineGenerateParameters() {
            byte[] bArr = new byte[16];
            if (this.random == null) {
                this.random = CryptoServicesRegistrar.getSecureRandom();
            }
            this.random.nextBytes(bArr);
            try {
                AlgorithmParameters createParametersInstance = createParametersInstance("Camellia");
                createParametersInstance.init(new IvParameterSpec(bArr));
                return createParametersInstance;
            } catch (Exception e2) {
                throw new RuntimeException(e2.getMessage());
            }
        }

        @Override // java.security.AlgorithmParameterGeneratorSpi
        public void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {
            throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for Camellia parameter generation.");
        }
    }

    public static class AlgParams extends IvAlgorithmParameters {
        @Override // org.bouncycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters, java.security.AlgorithmParametersSpi
        public String engineToString() {
            return "Camellia IV";
        }
    }

    public static class CBC extends BaseBlockCipher {
        public CBC() {
            super(new CBCBlockCipher(new CamelliaEngine()), 128);
        }
    }

    public static class ECB extends BaseBlockCipher {
        public ECB() {
            super(new BlockCipherProvider() { // from class: org.bouncycastle.jcajce.provider.symmetric.Camellia.ECB.1
                @Override // org.bouncycastle.jcajce.provider.symmetric.util.BlockCipherProvider
                public BlockCipher get() {
                    return new CamelliaEngine();
                }
            });
        }
    }

    public static class GMAC extends BaseMac {
        public GMAC() {
            super(new GMac(new GCMBlockCipher(new CamelliaEngine())));
        }
    }

    public static class KeyFactory extends BaseSecretKeyFactory {
        public KeyFactory() {
            super("Camellia", null);
        }
    }

    public static class KeyGen extends BaseKeyGenerator {
        public KeyGen() {
            this(256);
        }

        public KeyGen(int i2) {
            super("Camellia", i2, new CipherKeyGenerator());
        }
    }

    public static class KeyGen128 extends KeyGen {
        public KeyGen128() {
            super(128);
        }
    }

    public static class KeyGen192 extends KeyGen {
        public KeyGen192() {
            super(192);
        }
    }

    public static class KeyGen256 extends KeyGen {
        public KeyGen256() {
            super(256);
        }
    }

    public static class Mappings extends SymmetricAlgorithmProvider {
        private static final String PREFIX = Camellia.class.getName();

        @Override // org.bouncycastle.jcajce.provider.util.AlgorithmProvider
        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder sb = new StringBuilder();
            String str = PREFIX;
            AbstractC0413b.m1030x(sb, str, "$AlgParams", configurableProvider, "AlgorithmParameters.CAMELLIA");
            ASN1ObjectIdentifier aSN1ObjectIdentifier = NTTObjectIdentifiers.id_camellia128_cbc;
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters", aSN1ObjectIdentifier, "CAMELLIA");
            ASN1ObjectIdentifier aSN1ObjectIdentifier2 = NTTObjectIdentifiers.id_camellia192_cbc;
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters", aSN1ObjectIdentifier2, "CAMELLIA");
            ASN1ObjectIdentifier aSN1ObjectIdentifier3 = NTTObjectIdentifiers.id_camellia256_cbc;
            AbstractC0413b.m1032z(AbstractC0413b.m1021o(configurableProvider, "Alg.Alias.AlgorithmParameters", aSN1ObjectIdentifier3, "CAMELLIA", str), "$AlgParamGen", configurableProvider, "AlgorithmParameterGenerator.CAMELLIA");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator", aSN1ObjectIdentifier, "CAMELLIA");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator", aSN1ObjectIdentifier2, "CAMELLIA");
            AbstractC0413b.m1030x(AbstractC0413b.m1016j(AbstractC0413b.m1018l(AbstractC0413b.m1021o(configurableProvider, "Cipher", aSN1ObjectIdentifier2, AbstractC0000a.m18n(AbstractC0413b.m1018l(AbstractC0413b.m1017k(AbstractC0413b.m1021o(configurableProvider, "Alg.Alias.AlgorithmParameterGenerator", aSN1ObjectIdentifier3, "CAMELLIA", str), "$ECB", configurableProvider, "Cipher.CAMELLIA", str), "$CBC", configurableProvider, "Cipher", aSN1ObjectIdentifier), str, "$CBC"), str), "$CBC", configurableProvider, "Cipher", aSN1ObjectIdentifier3), str, "$RFC3211Wrap", configurableProvider, "Cipher.CAMELLIARFC3211WRAP"), str, "$Wrap", configurableProvider, "Cipher.CAMELLIAWRAP");
            ASN1ObjectIdentifier aSN1ObjectIdentifier4 = NTTObjectIdentifiers.id_camellia128_wrap;
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier4, "CAMELLIAWRAP");
            ASN1ObjectIdentifier aSN1ObjectIdentifier5 = NTTObjectIdentifiers.id_camellia192_wrap;
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier5, "CAMELLIAWRAP");
            ASN1ObjectIdentifier aSN1ObjectIdentifier6 = NTTObjectIdentifiers.id_camellia256_wrap;
            AbstractC0413b.m1032z(AbstractC0413b.m1021o(configurableProvider, "Alg.Alias.Cipher", aSN1ObjectIdentifier6, "CAMELLIAWRAP", str), "$KeyFactory", configurableProvider, "SecretKeyFactory.CAMELLIA");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory", aSN1ObjectIdentifier, "CAMELLIA");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory", aSN1ObjectIdentifier2, "CAMELLIA");
            StringBuilder m1021o = AbstractC0413b.m1021o(configurableProvider, "KeyGenerator", aSN1ObjectIdentifier3, AbstractC0000a.m18n(AbstractC0413b.m1018l(AbstractC0413b.m1021o(configurableProvider, "KeyGenerator", aSN1ObjectIdentifier, AbstractC0000a.m18n(AbstractC0413b.m1018l(AbstractC0413b.m1021o(configurableProvider, "KeyGenerator", aSN1ObjectIdentifier5, AbstractC0000a.m18n(AbstractC0413b.m1018l(AbstractC0413b.m1017k(AbstractC0413b.m1021o(configurableProvider, "Alg.Alias.SecretKeyFactory", aSN1ObjectIdentifier3, "CAMELLIA", str), "$KeyGen", configurableProvider, "KeyGenerator.CAMELLIA", str), "$KeyGen128", configurableProvider, "KeyGenerator", aSN1ObjectIdentifier4), str, "$KeyGen192"), str), "$KeyGen256", configurableProvider, "KeyGenerator", aSN1ObjectIdentifier6), str, "$KeyGen128"), str), "$KeyGen192", configurableProvider, "KeyGenerator", aSN1ObjectIdentifier2), str, "$KeyGen256"), str);
            m1021o.append("$GMAC");
            addGMacAlgorithm(configurableProvider, "CAMELLIA", m1021o.toString(), AbstractC0000a.m30z(str, "$KeyGen"));
            addPoly1305Algorithm(configurableProvider, "CAMELLIA", AbstractC0000a.m30z(str, "$Poly1305"), AbstractC0000a.m30z(str, "$Poly1305KeyGen"));
        }
    }

    public static class Poly1305 extends BaseMac {
        public Poly1305() {
            super(new org.bouncycastle.crypto.macs.Poly1305(new CamelliaEngine()));
        }
    }

    public static class Poly1305KeyGen extends BaseKeyGenerator {
        public Poly1305KeyGen() {
            super("Poly1305-Camellia", 256, new Poly1305KeyGenerator());
        }
    }

    public static class RFC3211Wrap extends BaseWrapCipher {
        public RFC3211Wrap() {
            super(new RFC3211WrapEngine(new CamelliaEngine()), 16);
        }
    }

    public static class Wrap extends BaseWrapCipher {
        public Wrap() {
            super(new CamelliaWrapEngine());
        }
    }

    private Camellia() {
    }
}
