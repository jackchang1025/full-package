package org.bouncycastle.jcajce.provider.symmetric;

import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.engines.VMPCEngine;
import org.bouncycastle.crypto.macs.VMPCMac;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseMac;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseStreamCipher;
import org.bouncycastle.jcajce.provider.util.AlgorithmProvider;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public final class VMPC {

    public static class Base extends BaseStreamCipher {
        public Base() {
            super(new VMPCEngine(), 16);
        }
    }

    public static class KeyGen extends BaseKeyGenerator {
        public KeyGen() {
            super("VMPC", 128, new CipherKeyGenerator());
        }
    }

    public static class Mac extends BaseMac {
        public Mac() {
            super(new VMPCMac());
        }
    }

    public static class Mappings extends AlgorithmProvider {
        private static final String PREFIX = VMPC.class.getName();

        @Override // org.bouncycastle.jcajce.provider.util.AlgorithmProvider
        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder sb = new StringBuilder();
            String str = PREFIX;
            AbstractC0413b.m1030x(AbstractC0413b.m1016j(AbstractC0413b.m1016j(sb, str, "$Base", configurableProvider, "Cipher.VMPC"), str, "$KeyGen", configurableProvider, "KeyGenerator.VMPC"), str, "$Mac", configurableProvider, "Mac.VMPCMAC");
            configurableProvider.addAlgorithm("Alg.Alias.Mac.VMPC", "VMPCMAC");
            configurableProvider.addAlgorithm("Alg.Alias.Mac.VMPC-MAC", "VMPCMAC");
        }
    }

    private VMPC() {
    }
}
