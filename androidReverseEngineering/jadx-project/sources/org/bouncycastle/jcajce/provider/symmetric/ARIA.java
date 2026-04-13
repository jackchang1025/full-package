package org.bouncycastle.jcajce.provider.symmetric;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.IvParameterSpec;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.nsri.NSRIObjectIdentifiers;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.engines.ARIAEngine;
import org.bouncycastle.crypto.engines.ARIAWrapEngine;
import org.bouncycastle.crypto.engines.ARIAWrapPadEngine;
import org.bouncycastle.crypto.engines.RFC3211WrapEngine;
import org.bouncycastle.crypto.generators.Poly1305KeyGenerator;
import org.bouncycastle.crypto.macs.GMac;
import org.bouncycastle.crypto.modes.AEADBlockCipher;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.modes.CCMBlockCipher;
import org.bouncycastle.crypto.modes.CFBBlockCipher;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.modes.OFBBlockCipher;
import org.bouncycastle.internal.asn1.cms.CCMParameters;
import org.bouncycastle.internal.asn1.cms.GCMParameters;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameterGenerator;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseMac;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher;
import org.bouncycastle.jcajce.provider.symmetric.util.BlockCipherProvider;
import org.bouncycastle.jcajce.provider.symmetric.util.GcmSpecUtil;
import org.bouncycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters;
import org.bouncycastle.jcajce.spec.AEADParameterSpec;
import p000a.AbstractC0000a;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public final class ARIA {

    public static class AlgParamGen extends BaseAlgorithmParameterGenerator {
        @Override // java.security.AlgorithmParameterGeneratorSpi
        public AlgorithmParameters engineGenerateParameters() {
            byte[] bArr = new byte[16];
            if (this.random == null) {
                this.random = CryptoServicesRegistrar.getSecureRandom();
            }
            this.random.nextBytes(bArr);
            try {
                AlgorithmParameters createParametersInstance = createParametersInstance("ARIA");
                createParametersInstance.init(new IvParameterSpec(bArr));
                return createParametersInstance;
            } catch (Exception e2) {
                throw new RuntimeException(e2.getMessage());
            }
        }

        @Override // java.security.AlgorithmParameterGeneratorSpi
        public void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {
            throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for ARIA parameter generation.");
        }
    }

    public static class AlgParams extends IvAlgorithmParameters {
        @Override // org.bouncycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters, java.security.AlgorithmParametersSpi
        public String engineToString() {
            return "ARIA IV";
        }
    }

    public static class AlgParamsCCM extends BaseAlgorithmParameters {
        private CCMParameters ccmParams;

        @Override // java.security.AlgorithmParametersSpi
        public byte[] engineGetEncoded() {
            return this.ccmParams.getEncoded();
        }

        @Override // java.security.AlgorithmParametersSpi
        public void engineInit(AlgorithmParameterSpec algorithmParameterSpec) {
            if (GcmSpecUtil.isGcmSpec(algorithmParameterSpec)) {
                this.ccmParams = CCMParameters.getInstance(GcmSpecUtil.extractGcmParameters(algorithmParameterSpec));
            } else {
                if (!(algorithmParameterSpec instanceof AEADParameterSpec)) {
                    throw new InvalidParameterSpecException("AlgorithmParameterSpec class not recognized: ".concat(algorithmParameterSpec.getClass().getName()));
                }
                AEADParameterSpec aEADParameterSpec = (AEADParameterSpec) algorithmParameterSpec;
                this.ccmParams = new CCMParameters(aEADParameterSpec.getNonce(), aEADParameterSpec.getMacSizeInBits() / 8);
            }
        }

        @Override // java.security.AlgorithmParametersSpi
        public String engineToString() {
            return "CCM";
        }

        @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters
        public AlgorithmParameterSpec localEngineGetParameterSpec(Class cls) {
            if (cls == AlgorithmParameterSpec.class || GcmSpecUtil.isGcmSpec(cls)) {
                return GcmSpecUtil.gcmSpecExists() ? GcmSpecUtil.extractGcmSpec(this.ccmParams.toASN1Primitive()) : new AEADParameterSpec(this.ccmParams.getNonce(), this.ccmParams.getIcvLen() * 8);
            }
            if (cls == AEADParameterSpec.class) {
                return new AEADParameterSpec(this.ccmParams.getNonce(), this.ccmParams.getIcvLen() * 8);
            }
            if (cls == IvParameterSpec.class) {
                return new IvParameterSpec(this.ccmParams.getNonce());
            }
            throw new InvalidParameterSpecException("AlgorithmParameterSpec not recognized: ".concat(cls.getName()));
        }

        @Override // java.security.AlgorithmParametersSpi
        public byte[] engineGetEncoded(String str) {
            if (isASN1FormatString(str)) {
                return this.ccmParams.getEncoded();
            }
            throw new IOException("unknown format specified");
        }

        @Override // java.security.AlgorithmParametersSpi
        public void engineInit(byte[] bArr) {
            this.ccmParams = CCMParameters.getInstance(bArr);
        }

        @Override // java.security.AlgorithmParametersSpi
        public void engineInit(byte[] bArr, String str) {
            if (!isASN1FormatString(str)) {
                throw new IOException("unknown format specified");
            }
            this.ccmParams = CCMParameters.getInstance(bArr);
        }
    }

    public static class AlgParamsGCM extends BaseAlgorithmParameters {
        private GCMParameters gcmParams;

        @Override // java.security.AlgorithmParametersSpi
        public byte[] engineGetEncoded() {
            return this.gcmParams.getEncoded();
        }

        @Override // java.security.AlgorithmParametersSpi
        public void engineInit(AlgorithmParameterSpec algorithmParameterSpec) {
            if (GcmSpecUtil.isGcmSpec(algorithmParameterSpec)) {
                this.gcmParams = GcmSpecUtil.extractGcmParameters(algorithmParameterSpec);
            } else {
                if (!(algorithmParameterSpec instanceof AEADParameterSpec)) {
                    throw new InvalidParameterSpecException("AlgorithmParameterSpec class not recognized: ".concat(algorithmParameterSpec.getClass().getName()));
                }
                AEADParameterSpec aEADParameterSpec = (AEADParameterSpec) algorithmParameterSpec;
                this.gcmParams = new GCMParameters(aEADParameterSpec.getNonce(), aEADParameterSpec.getMacSizeInBits() / 8);
            }
        }

        @Override // java.security.AlgorithmParametersSpi
        public String engineToString() {
            return "GCM";
        }

        @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters
        public AlgorithmParameterSpec localEngineGetParameterSpec(Class cls) {
            if (cls == AlgorithmParameterSpec.class || GcmSpecUtil.isGcmSpec(cls)) {
                return GcmSpecUtil.gcmSpecExists() ? GcmSpecUtil.extractGcmSpec(this.gcmParams.toASN1Primitive()) : new AEADParameterSpec(this.gcmParams.getNonce(), this.gcmParams.getIcvLen() * 8);
            }
            if (cls == AEADParameterSpec.class) {
                return new AEADParameterSpec(this.gcmParams.getNonce(), this.gcmParams.getIcvLen() * 8);
            }
            if (cls == IvParameterSpec.class) {
                return new IvParameterSpec(this.gcmParams.getNonce());
            }
            throw new InvalidParameterSpecException("AlgorithmParameterSpec not recognized: ".concat(cls.getName()));
        }

        @Override // java.security.AlgorithmParametersSpi
        public byte[] engineGetEncoded(String str) {
            if (isASN1FormatString(str)) {
                return this.gcmParams.getEncoded();
            }
            throw new IOException("unknown format specified");
        }

        @Override // java.security.AlgorithmParametersSpi
        public void engineInit(byte[] bArr) {
            this.gcmParams = GCMParameters.getInstance(bArr);
        }

        @Override // java.security.AlgorithmParametersSpi
        public void engineInit(byte[] bArr, String str) {
            if (!isASN1FormatString(str)) {
                throw new IOException("unknown format specified");
            }
            this.gcmParams = GCMParameters.getInstance(bArr);
        }
    }

    public static class CBC extends BaseBlockCipher {
        public CBC() {
            super(new CBCBlockCipher(new ARIAEngine()), 128);
        }
    }

    public static class CCM extends BaseBlockCipher {
        public CCM() {
            super((AEADBlockCipher) new CCMBlockCipher(new ARIAEngine()), false, 12);
        }
    }

    public static class CFB extends BaseBlockCipher {
        public CFB() {
            super(new BufferedBlockCipher(new CFBBlockCipher(new ARIAEngine(), 128)), 128);
        }
    }

    public static class ECB extends BaseBlockCipher {
        public ECB() {
            super(new BlockCipherProvider() { // from class: org.bouncycastle.jcajce.provider.symmetric.ARIA.ECB.1
                @Override // org.bouncycastle.jcajce.provider.symmetric.util.BlockCipherProvider
                public BlockCipher get() {
                    return new ARIAEngine();
                }
            });
        }
    }

    public static class GCM extends BaseBlockCipher {
        public GCM() {
            super(new GCMBlockCipher(new ARIAEngine()));
        }
    }

    public static class GMAC extends BaseMac {
        public GMAC() {
            super(new GMac(new GCMBlockCipher(new ARIAEngine())));
        }
    }

    public static class KeyFactory extends BaseSecretKeyFactory {
        public KeyFactory() {
            super("ARIA", null);
        }
    }

    public static class KeyGen extends BaseKeyGenerator {
        public KeyGen() {
            this(256);
        }

        public KeyGen(int i2) {
            super("ARIA", i2, new CipherKeyGenerator());
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
        private static final String PREFIX = ARIA.class.getName();

        @Override // org.bouncycastle.jcajce.provider.util.AlgorithmProvider
        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder sb = new StringBuilder();
            String str = PREFIX;
            AbstractC0413b.m1030x(sb, str, "$AlgParams", configurableProvider, "AlgorithmParameters.ARIA");
            ASN1ObjectIdentifier aSN1ObjectIdentifier = NSRIObjectIdentifiers.id_aria128_cbc;
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters", aSN1ObjectIdentifier, "ARIA");
            ASN1ObjectIdentifier aSN1ObjectIdentifier2 = NSRIObjectIdentifiers.id_aria192_cbc;
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters", aSN1ObjectIdentifier2, "ARIA");
            ASN1ObjectIdentifier aSN1ObjectIdentifier3 = NSRIObjectIdentifiers.id_aria256_cbc;
            AbstractC0413b.m1032z(AbstractC0413b.m1021o(configurableProvider, "Alg.Alias.AlgorithmParameters", aSN1ObjectIdentifier3, "ARIA", str), "$AlgParamGen", configurableProvider, "AlgorithmParameterGenerator.ARIA");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator", aSN1ObjectIdentifier, "ARIA");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator", aSN1ObjectIdentifier2, "ARIA");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator", aSN1ObjectIdentifier3, "ARIA");
            ASN1ObjectIdentifier aSN1ObjectIdentifier4 = NSRIObjectIdentifiers.id_aria128_ofb;
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator", aSN1ObjectIdentifier4, "ARIA");
            ASN1ObjectIdentifier aSN1ObjectIdentifier5 = NSRIObjectIdentifiers.id_aria192_ofb;
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator", aSN1ObjectIdentifier5, "ARIA");
            ASN1ObjectIdentifier aSN1ObjectIdentifier6 = NSRIObjectIdentifiers.id_aria256_ofb;
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator", aSN1ObjectIdentifier6, "ARIA");
            ASN1ObjectIdentifier aSN1ObjectIdentifier7 = NSRIObjectIdentifiers.id_aria128_cfb;
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator", aSN1ObjectIdentifier7, "ARIA");
            ASN1ObjectIdentifier aSN1ObjectIdentifier8 = NSRIObjectIdentifiers.id_aria192_cfb;
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator", aSN1ObjectIdentifier8, "ARIA");
            ASN1ObjectIdentifier aSN1ObjectIdentifier9 = NSRIObjectIdentifiers.id_aria256_cfb;
            AbstractC0413b.m1032z(AbstractC0413b.m1021o(configurableProvider, "Alg.Alias.AlgorithmParameterGenerator", aSN1ObjectIdentifier9, "ARIA", str), "$ECB", configurableProvider, "Cipher.ARIA");
            ASN1ObjectIdentifier aSN1ObjectIdentifier10 = NSRIObjectIdentifiers.id_aria128_ecb;
            AbstractC0413b.m1029w(str, "$ECB", configurableProvider, "Cipher", aSN1ObjectIdentifier10);
            ASN1ObjectIdentifier aSN1ObjectIdentifier11 = NSRIObjectIdentifiers.id_aria192_ecb;
            AbstractC0413b.m1029w(str, "$ECB", configurableProvider, "Cipher", aSN1ObjectIdentifier11);
            ASN1ObjectIdentifier aSN1ObjectIdentifier12 = NSRIObjectIdentifiers.id_aria256_ecb;
            configurableProvider.addAlgorithm("Cipher", aSN1ObjectIdentifier12, str + "$ECB");
            AbstractC0413b.m1032z(AbstractC0413b.m1017k(AbstractC0413b.m1021o(configurableProvider, "Cipher", aSN1ObjectIdentifier6, AbstractC0000a.m18n(AbstractC0413b.m1018l(AbstractC0413b.m1021o(configurableProvider, "Cipher", aSN1ObjectIdentifier4, AbstractC0000a.m18n(AbstractC0413b.m1018l(AbstractC0413b.m1021o(configurableProvider, "Cipher", aSN1ObjectIdentifier8, AbstractC0000a.m18n(AbstractC0413b.m1018l(AbstractC0413b.m1021o(configurableProvider, "Cipher", aSN1ObjectIdentifier3, AbstractC0000a.m18n(AbstractC0413b.m1018l(AbstractC0413b.m1021o(configurableProvider, "Cipher", aSN1ObjectIdentifier, AbstractC0000a.m18n(new StringBuilder(), str, "$CBC"), str), "$CBC", configurableProvider, "Cipher", aSN1ObjectIdentifier2), str, "$CBC"), str), "$CFB", configurableProvider, "Cipher", aSN1ObjectIdentifier7), str, "$CFB"), str), "$CFB", configurableProvider, "Cipher", aSN1ObjectIdentifier9), str, "$OFB"), str), "$OFB", configurableProvider, "Cipher", aSN1ObjectIdentifier5), str, "$OFB"), str), "$RFC3211Wrap", configurableProvider, "Cipher.ARIARFC3211WRAP", str), "$Wrap", configurableProvider, "Cipher.ARIAWRAP");
            ASN1ObjectIdentifier aSN1ObjectIdentifier13 = NSRIObjectIdentifiers.id_aria128_kw;
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier13, "ARIAWRAP");
            ASN1ObjectIdentifier aSN1ObjectIdentifier14 = NSRIObjectIdentifiers.id_aria192_kw;
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier14, "ARIAWRAP");
            ASN1ObjectIdentifier aSN1ObjectIdentifier15 = NSRIObjectIdentifiers.id_aria256_kw;
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier15, "ARIAWRAP");
            configurableProvider.addAlgorithm("Cipher.ARIAWRAPPAD", AbstractC0413b.m1015i(configurableProvider, "Alg.Alias.Cipher.ARIAKW", "ARIAWRAP", str, "$WrapPad"));
            ASN1ObjectIdentifier aSN1ObjectIdentifier16 = NSRIObjectIdentifiers.id_aria128_kwp;
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier16, "ARIAWRAPPAD");
            ASN1ObjectIdentifier aSN1ObjectIdentifier17 = NSRIObjectIdentifiers.id_aria192_kwp;
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier17, "ARIAWRAPPAD");
            ASN1ObjectIdentifier aSN1ObjectIdentifier18 = NSRIObjectIdentifiers.id_aria256_kwp;
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier18, "ARIAWRAPPAD");
            StringBuilder m1021o = AbstractC0413b.m1021o(configurableProvider, "KeyGenerator", aSN1ObjectIdentifier5, AbstractC0000a.m18n(AbstractC0413b.m1018l(AbstractC0413b.m1021o(configurableProvider, "KeyGenerator", aSN1ObjectIdentifier9, AbstractC0000a.m18n(AbstractC0413b.m1018l(AbstractC0413b.m1021o(configurableProvider, "KeyGenerator", aSN1ObjectIdentifier7, AbstractC0000a.m18n(AbstractC0413b.m1018l(AbstractC0413b.m1021o(configurableProvider, "KeyGenerator", aSN1ObjectIdentifier2, AbstractC0000a.m18n(AbstractC0413b.m1018l(AbstractC0413b.m1021o(configurableProvider, "KeyGenerator", aSN1ObjectIdentifier12, AbstractC0000a.m18n(AbstractC0413b.m1018l(AbstractC0413b.m1021o(configurableProvider, "KeyGenerator", aSN1ObjectIdentifier10, AbstractC0000a.m18n(AbstractC0413b.m1018l(AbstractC0413b.m1021o(configurableProvider, "KeyGenerator", aSN1ObjectIdentifier17, AbstractC0000a.m18n(AbstractC0413b.m1018l(AbstractC0413b.m1021o(configurableProvider, "KeyGenerator", aSN1ObjectIdentifier15, AbstractC0000a.m18n(AbstractC0413b.m1018l(AbstractC0413b.m1021o(configurableProvider, "KeyGenerator", aSN1ObjectIdentifier13, AbstractC0413b.m1015i(configurableProvider, "KeyGenerator.ARIA", AbstractC0413b.m1015i(configurableProvider, "Alg.Alias.Cipher.ARIAKWP", "ARIAWRAPPAD", str, "$KeyGen"), str, "$KeyGen128"), str), "$KeyGen192", configurableProvider, "KeyGenerator", aSN1ObjectIdentifier14), str, "$KeyGen256"), str), "$KeyGen128", configurableProvider, "KeyGenerator", aSN1ObjectIdentifier16), str, "$KeyGen192"), str), "$KeyGen256", configurableProvider, "KeyGenerator", aSN1ObjectIdentifier18), str, "$KeyGen128"), str), "$KeyGen192", configurableProvider, "KeyGenerator", aSN1ObjectIdentifier11), str, "$KeyGen256"), str), "$KeyGen128", configurableProvider, "KeyGenerator", aSN1ObjectIdentifier), str, "$KeyGen192"), str), "$KeyGen256", configurableProvider, "KeyGenerator", aSN1ObjectIdentifier3), str, "$KeyGen128"), str), "$KeyGen192", configurableProvider, "KeyGenerator", aSN1ObjectIdentifier8), str, "$KeyGen256"), str), "$KeyGen128", configurableProvider, "KeyGenerator", aSN1ObjectIdentifier4), str, "$KeyGen192"), str);
            m1021o.append("$KeyGen256");
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier6, m1021o.toString());
            ASN1ObjectIdentifier aSN1ObjectIdentifier19 = NSRIObjectIdentifiers.id_aria128_ccm;
            AbstractC0413b.m1029w(str, "$KeyGen128", configurableProvider, "KeyGenerator", aSN1ObjectIdentifier19);
            ASN1ObjectIdentifier aSN1ObjectIdentifier20 = NSRIObjectIdentifiers.id_aria192_ccm;
            AbstractC0413b.m1029w(str, "$KeyGen192", configurableProvider, "KeyGenerator", aSN1ObjectIdentifier20);
            ASN1ObjectIdentifier aSN1ObjectIdentifier21 = NSRIObjectIdentifiers.id_aria256_ccm;
            AbstractC0413b.m1029w(str, "$KeyGen256", configurableProvider, "KeyGenerator", aSN1ObjectIdentifier21);
            ASN1ObjectIdentifier aSN1ObjectIdentifier22 = NSRIObjectIdentifiers.id_aria128_gcm;
            AbstractC0413b.m1029w(str, "$KeyGen128", configurableProvider, "KeyGenerator", aSN1ObjectIdentifier22);
            ASN1ObjectIdentifier aSN1ObjectIdentifier23 = NSRIObjectIdentifiers.id_aria192_gcm;
            AbstractC0413b.m1029w(str, "$KeyGen192", configurableProvider, "KeyGenerator", aSN1ObjectIdentifier23);
            ASN1ObjectIdentifier aSN1ObjectIdentifier24 = NSRIObjectIdentifiers.id_aria256_gcm;
            configurableProvider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier24, str + "$KeyGen256");
            AbstractC0413b.m1030x(new StringBuilder(), str, "$KeyFactory", configurableProvider, "SecretKeyFactory.ARIA");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory", aSN1ObjectIdentifier, "ARIA");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory", aSN1ObjectIdentifier2, "ARIA");
            StringBuilder m1021o2 = AbstractC0413b.m1021o(configurableProvider, "Alg.Alias.SecretKeyFactory", aSN1ObjectIdentifier3, "ARIA", str);
            m1021o2.append("$AlgParamGen");
            configurableProvider.addAlgorithm("AlgorithmParameterGenerator.ARIACCM", m1021o2.toString());
            AbstractC0413b.m1032z(AbstractC0413b.m1005C(AbstractC0413b.m1019m(AbstractC0413b.m1019m(new StringBuilder("Alg.Alias.AlgorithmParameterGenerator."), aSN1ObjectIdentifier19, configurableProvider, "ARIACCM", "Alg.Alias.AlgorithmParameterGenerator."), aSN1ObjectIdentifier20, configurableProvider, "ARIACCM", "Alg.Alias.AlgorithmParameterGenerator."), aSN1ObjectIdentifier21, configurableProvider, "ARIACCM", str), "$CCM", configurableProvider, "Cipher.ARIACCM");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier19, "CCM");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier20, "CCM");
            StringBuilder m1021o3 = AbstractC0413b.m1021o(configurableProvider, "Alg.Alias.Cipher", aSN1ObjectIdentifier21, "CCM", str);
            m1021o3.append("$AlgParamGen");
            configurableProvider.addAlgorithm("AlgorithmParameterGenerator.ARIAGCM", m1021o3.toString());
            AbstractC0413b.m1032z(AbstractC0413b.m1005C(AbstractC0413b.m1019m(AbstractC0413b.m1019m(new StringBuilder("Alg.Alias.AlgorithmParameterGenerator."), aSN1ObjectIdentifier22, configurableProvider, "ARIAGCM", "Alg.Alias.AlgorithmParameterGenerator."), aSN1ObjectIdentifier23, configurableProvider, "ARIAGCM", "Alg.Alias.AlgorithmParameterGenerator."), aSN1ObjectIdentifier24, configurableProvider, "ARIAGCM", str), "$GCM", configurableProvider, "Cipher.ARIAGCM");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier22, "ARIAGCM");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier23, "ARIAGCM");
            StringBuilder m1021o4 = AbstractC0413b.m1021o(configurableProvider, "Alg.Alias.Cipher", aSN1ObjectIdentifier24, "ARIAGCM", str);
            m1021o4.append("$GMAC");
            addGMacAlgorithm(configurableProvider, "ARIA", m1021o4.toString(), AbstractC0000a.m30z(str, "$KeyGen"));
            addPoly1305Algorithm(configurableProvider, "ARIA", AbstractC0000a.m30z(str, "$Poly1305"), AbstractC0000a.m30z(str, "$Poly1305KeyGen"));
        }
    }

    public static class OFB extends BaseBlockCipher {
        public OFB() {
            super(new BufferedBlockCipher(new OFBBlockCipher(new ARIAEngine(), 128)), 128);
        }
    }

    public static class Poly1305 extends BaseMac {
        public Poly1305() {
            super(new org.bouncycastle.crypto.macs.Poly1305(new ARIAEngine()));
        }
    }

    public static class Poly1305KeyGen extends BaseKeyGenerator {
        public Poly1305KeyGen() {
            super("Poly1305-ARIA", 256, new Poly1305KeyGenerator());
        }
    }

    public static class RFC3211Wrap extends BaseWrapCipher {
        public RFC3211Wrap() {
            super(new RFC3211WrapEngine(new ARIAEngine()), 16);
        }
    }

    public static class Wrap extends BaseWrapCipher {
        public Wrap() {
            super(new ARIAWrapEngine());
        }
    }

    public static class WrapPad extends BaseWrapCipher {
        public WrapPad() {
            super(new ARIAWrapPadEngine());
        }
    }

    private ARIA() {
    }
}
