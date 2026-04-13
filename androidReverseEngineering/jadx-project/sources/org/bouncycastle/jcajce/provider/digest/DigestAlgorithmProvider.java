package org.bouncycastle.jcajce.provider.digest;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.util.AlgorithmProvider;
import p000a.AbstractC0000a;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
abstract class DigestAlgorithmProvider extends AlgorithmProvider {
    public void addHMACAlgorithm(ConfigurableProvider configurableProvider, String str, String str2, String str3) {
        String m15k = AbstractC0000a.m15k("HMAC", str);
        configurableProvider.addAlgorithm("Mac." + m15k, str2);
        configurableProvider.addAlgorithm("Alg.Alias.Mac.HMAC-" + str, m15k);
        configurableProvider.addAlgorithm("Alg.Alias.Mac.HMAC/" + str, m15k);
        configurableProvider.addAlgorithm("KeyGenerator." + m15k, str3);
        configurableProvider.addAlgorithm("Alg.Alias.KeyGenerator.HMAC-" + str, m15k);
        configurableProvider.addAlgorithm("Alg.Alias.KeyGenerator.HMAC/" + str, m15k);
    }

    public void addHMACAlias(ConfigurableProvider configurableProvider, String str, ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        String m15k = AbstractC0000a.m15k("HMAC", str);
        configurableProvider.addAlgorithm("Alg.Alias.Mac." + aSN1ObjectIdentifier, m15k);
        AbstractC0413b.m1003A(new StringBuilder("Alg.Alias.KeyGenerator."), aSN1ObjectIdentifier, configurableProvider, m15k);
    }
}
