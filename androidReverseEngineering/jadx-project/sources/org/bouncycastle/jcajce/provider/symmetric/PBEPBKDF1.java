package org.bouncycastle.jcajce.provider.symmetric;

import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.PBEParameterSpec;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.pkcs.PBEParameter;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters;
import org.bouncycastle.jcajce.provider.util.AlgorithmProvider;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class PBEPBKDF1 {

    public static class AlgParams extends BaseAlgorithmParameters {
        PBEParameter params;

        @Override // java.security.AlgorithmParametersSpi
        public byte[] engineGetEncoded() {
            try {
                return this.params.getEncoded(ASN1Encoding.DER);
            } catch (IOException e2) {
                throw new RuntimeException("Oooops! " + e2.toString());
            }
        }

        @Override // java.security.AlgorithmParametersSpi
        public void engineInit(AlgorithmParameterSpec algorithmParameterSpec) {
            if (!(algorithmParameterSpec instanceof PBEParameterSpec)) {
                throw new InvalidParameterSpecException("PBEParameterSpec required to initialise a PBKDF1 PBE parameters algorithm parameters object");
            }
            PBEParameterSpec pBEParameterSpec = (PBEParameterSpec) algorithmParameterSpec;
            this.params = new PBEParameter(pBEParameterSpec.getSalt(), pBEParameterSpec.getIterationCount());
        }

        @Override // java.security.AlgorithmParametersSpi
        public String engineToString() {
            return "PBKDF1 Parameters";
        }

        @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters
        public AlgorithmParameterSpec localEngineGetParameterSpec(Class cls) {
            if (cls == PBEParameterSpec.class || cls == AlgorithmParameterSpec.class) {
                return new PBEParameterSpec(this.params.getSalt(), this.params.getIterationCount().intValue());
            }
            throw new InvalidParameterSpecException("unknown parameter spec passed to PBKDF1 PBE parameters object.");
        }

        @Override // java.security.AlgorithmParametersSpi
        public byte[] engineGetEncoded(String str) {
            if (isASN1FormatString(str)) {
                return engineGetEncoded();
            }
            return null;
        }

        @Override // java.security.AlgorithmParametersSpi
        public void engineInit(byte[] bArr) {
            this.params = PBEParameter.getInstance(bArr);
        }

        @Override // java.security.AlgorithmParametersSpi
        public void engineInit(byte[] bArr, String str) {
            if (!isASN1FormatString(str)) {
                throw new IOException("Unknown parameters format in PBKDF2 parameters object");
            }
            engineInit(bArr);
        }
    }

    public static class Mappings extends AlgorithmProvider {
        private static final String PREFIX = PBEPBKDF1.class.getName();

        @Override // org.bouncycastle.jcajce.provider.util.AlgorithmProvider
        public void configure(ConfigurableProvider configurableProvider) {
            configurableProvider.addAlgorithm("AlgorithmParameters.PBKDF1", PREFIX + "$AlgParams");
            AbstractC0413b.m1003A(AbstractC0413b.m1019m(AbstractC0413b.m1019m(AbstractC0413b.m1019m(AbstractC0413b.m1019m(new StringBuilder("Alg.Alias.AlgorithmParameters."), PKCSObjectIdentifiers.pbeWithMD2AndDES_CBC, configurableProvider, "PBKDF1", "Alg.Alias.AlgorithmParameters."), PKCSObjectIdentifiers.pbeWithMD5AndDES_CBC, configurableProvider, "PBKDF1", "Alg.Alias.AlgorithmParameters."), PKCSObjectIdentifiers.pbeWithMD5AndRC2_CBC, configurableProvider, "PBKDF1", "Alg.Alias.AlgorithmParameters."), PKCSObjectIdentifiers.pbeWithSHA1AndDES_CBC, configurableProvider, "PBKDF1", "Alg.Alias.AlgorithmParameters."), PKCSObjectIdentifiers.pbeWithSHA1AndRC2_CBC, configurableProvider, "PBKDF1");
        }
    }

    private PBEPBKDF1() {
    }
}
