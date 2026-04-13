package org.bouncycastle.jcajce.provider.symmetric;

import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.engines.XSalsa20Engine;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseStreamCipher;
import org.bouncycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters;
import org.bouncycastle.jcajce.provider.util.AlgorithmProvider;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public final class XSalsa20 {

    public static class AlgParams extends IvAlgorithmParameters {
        @Override // org.bouncycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters, java.security.AlgorithmParametersSpi
        public String engineToString() {
            return "XSalsa20 IV";
        }
    }

    public static class Base extends BaseStreamCipher {
        public Base() {
            super(new XSalsa20Engine(), 24);
        }
    }

    public static class KeyGen extends BaseKeyGenerator {
        public KeyGen() {
            super("XSalsa20", 256, new CipherKeyGenerator());
        }
    }

    public static class Mappings extends AlgorithmProvider {
        private static final String PREFIX = XSalsa20.class.getName();

        @Override // org.bouncycastle.jcajce.provider.util.AlgorithmProvider
        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder sb = new StringBuilder();
            String str = PREFIX;
            AbstractC0413b.m1030x(AbstractC0413b.m1016j(AbstractC0413b.m1016j(sb, str, "$Base", configurableProvider, "Cipher.XSALSA20"), str, "$KeyGen", configurableProvider, "KeyGenerator.XSALSA20"), str, "$AlgParams", configurableProvider, "AlgorithmParameters.XSALSA20");
        }
    }

    private XSalsa20() {
    }
}
