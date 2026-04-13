package org.bouncycastle.jcajce.provider.symmetric;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.kisa.KISAObjectIdentifiers;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.engines.SEEDEngine;
import org.bouncycastle.crypto.engines.SEEDWrapEngine;
import org.bouncycastle.crypto.generators.Poly1305KeyGenerator;
import org.bouncycastle.crypto.macs.CMac;
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
public final class SEED {

    public static class AlgParamGen extends BaseAlgorithmParameterGenerator {
        @Override // java.security.AlgorithmParameterGeneratorSpi
        public AlgorithmParameters engineGenerateParameters() {
            byte[] bArr = new byte[16];
            if (this.random == null) {
                this.random = CryptoServicesRegistrar.getSecureRandom();
            }
            this.random.nextBytes(bArr);
            try {
                AlgorithmParameters createParametersInstance = createParametersInstance("SEED");
                createParametersInstance.init(new IvParameterSpec(bArr));
                return createParametersInstance;
            } catch (Exception e2) {
                throw new RuntimeException(e2.getMessage());
            }
        }

        @Override // java.security.AlgorithmParameterGeneratorSpi
        public void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {
            throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for SEED parameter generation.");
        }
    }

    public static class AlgParams extends IvAlgorithmParameters {
        @Override // org.bouncycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters, java.security.AlgorithmParametersSpi
        public String engineToString() {
            return "SEED IV";
        }
    }

    public static class CBC extends BaseBlockCipher {
        public CBC() {
            super(new CBCBlockCipher(new SEEDEngine()), 128);
        }
    }

    public static class CMAC extends BaseMac {
        public CMAC() {
            super(new CMac(new SEEDEngine()));
        }
    }

    public static class ECB extends BaseBlockCipher {
        public ECB() {
            super(new BlockCipherProvider() { // from class: org.bouncycastle.jcajce.provider.symmetric.SEED.ECB.1
                @Override // org.bouncycastle.jcajce.provider.symmetric.util.BlockCipherProvider
                public BlockCipher get() {
                    return new SEEDEngine();
                }
            });
        }
    }

    public static class GMAC extends BaseMac {
        public GMAC() {
            super(new GMac(new GCMBlockCipher(new SEEDEngine())));
        }
    }

    public static class KeyFactory extends BaseSecretKeyFactory {
        public KeyFactory() {
            super("SEED", null);
        }
    }

    public static class KeyGen extends BaseKeyGenerator {
        public KeyGen() {
            super("SEED", 128, new CipherKeyGenerator());
        }
    }

    public static class Mappings extends SymmetricAlgorithmProvider {
        private static final String PREFIX = SEED.class.getName();

        @Override // org.bouncycastle.jcajce.provider.util.AlgorithmProvider
        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder sb = new StringBuilder();
            String str = PREFIX;
            sb.append(str);
            sb.append("$AlgParams");
            configurableProvider.addAlgorithm("AlgorithmParameters.SEED", sb.toString());
            StringBuilder sb2 = new StringBuilder("Alg.Alias.AlgorithmParameters.");
            ASN1ObjectIdentifier aSN1ObjectIdentifier = KISAObjectIdentifiers.id_seedCBC;
            StringBuilder m1005C = AbstractC0413b.m1005C(sb2, aSN1ObjectIdentifier, configurableProvider, "SEED", str);
            m1005C.append("$AlgParamGen");
            configurableProvider.addAlgorithm("AlgorithmParameterGenerator.SEED", m1005C.toString());
            AbstractC0413b.m1030x(AbstractC0413b.m1018l(AbstractC0413b.m1017k(AbstractC0413b.m1005C(new StringBuilder("Alg.Alias.AlgorithmParameterGenerator."), aSN1ObjectIdentifier, configurableProvider, "SEED", str), "$ECB", configurableProvider, "Cipher.SEED", str), "$CBC", configurableProvider, "Cipher", aSN1ObjectIdentifier), str, "$Wrap", configurableProvider, "Cipher.SEEDWRAP");
            ASN1ObjectIdentifier aSN1ObjectIdentifier2 = KISAObjectIdentifiers.id_npki_app_cmsSeed_wrap;
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier2, "SEEDWRAP");
            AbstractC0413b.m1030x(AbstractC0413b.m1018l(AbstractC0413b.m1021o(configurableProvider, "KeyGenerator", aSN1ObjectIdentifier, AbstractC0413b.m1015i(configurableProvider, "KeyGenerator.SEED", AbstractC0413b.m1015i(configurableProvider, "Alg.Alias.Cipher.SEEDKW", "SEEDWRAP", str, "$KeyGen"), str, "$KeyGen"), str), "$KeyGen", configurableProvider, "KeyGenerator", aSN1ObjectIdentifier2), str, "$KeyFactory", configurableProvider, "SecretKeyFactory.SEED");
            StringBuilder m1021o = AbstractC0413b.m1021o(configurableProvider, "Alg.Alias.SecretKeyFactory", aSN1ObjectIdentifier, "SEED", str);
            m1021o.append("$CMAC");
            addCMacAlgorithm(configurableProvider, "SEED", m1021o.toString(), AbstractC0000a.m30z(str, "$KeyGen"));
            addGMacAlgorithm(configurableProvider, "SEED", AbstractC0000a.m30z(str, "$GMAC"), AbstractC0000a.m30z(str, "$KeyGen"));
            addPoly1305Algorithm(configurableProvider, "SEED", AbstractC0000a.m30z(str, "$Poly1305"), AbstractC0000a.m30z(str, "$Poly1305KeyGen"));
        }
    }

    public static class Poly1305 extends BaseMac {
        public Poly1305() {
            super(new org.bouncycastle.crypto.macs.Poly1305(new SEEDEngine()));
        }
    }

    public static class Poly1305KeyGen extends BaseKeyGenerator {
        public Poly1305KeyGen() {
            super("Poly1305-SEED", 256, new Poly1305KeyGenerator());
        }
    }

    public static class Wrap extends BaseWrapCipher {
        public Wrap() {
            super(new SEEDWrapEngine());
        }
    }

    private SEED() {
    }
}
