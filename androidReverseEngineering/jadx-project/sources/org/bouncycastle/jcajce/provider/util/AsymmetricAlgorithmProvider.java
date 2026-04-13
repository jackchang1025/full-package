package org.bouncycastle.jcajce.provider.util;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public abstract class AsymmetricAlgorithmProvider extends AlgorithmProvider {
    public void addSignatureAlgorithm(ConfigurableProvider configurableProvider, String str, String str2, String str3) {
        addSignatureAlgorithm(configurableProvider, str, str2, str3, null);
    }

    public void registerOid(ConfigurableProvider configurableProvider, ASN1ObjectIdentifier aSN1ObjectIdentifier, String str, AsymmetricKeyInfoConverter asymmetricKeyInfoConverter) {
        configurableProvider.addAlgorithm("Alg.Alias.KeyFactory." + aSN1ObjectIdentifier, str);
        AbstractC0413b.m1003A(new StringBuilder("Alg.Alias.KeyPairGenerator."), aSN1ObjectIdentifier, configurableProvider, str);
        configurableProvider.addKeyInfoConverter(aSN1ObjectIdentifier, asymmetricKeyInfoConverter);
    }

    public void registerOidAlgorithmParameterGenerator(ConfigurableProvider configurableProvider, ASN1ObjectIdentifier aSN1ObjectIdentifier, String str) {
        configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + aSN1ObjectIdentifier, str);
        AbstractC0413b.m1003A(new StringBuilder("Alg.Alias.AlgorithmParameters."), aSN1ObjectIdentifier, configurableProvider, str);
    }

    public void registerOidAlgorithmParameters(ConfigurableProvider configurableProvider, ASN1ObjectIdentifier aSN1ObjectIdentifier, String str) {
        configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters." + aSN1ObjectIdentifier, str);
    }

    public void addSignatureAlgorithm(ConfigurableProvider configurableProvider, String str, String str2, String str3, ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        String str4 = str + "WITH" + str2;
        String str5 = str + "with" + str2;
        String str6 = str + "With" + str2;
        configurableProvider.addAlgorithm("Signature." + str4, str3);
        configurableProvider.addAlgorithm("Alg.Alias.Signature." + str5, str4);
        configurableProvider.addAlgorithm("Alg.Alias.Signature." + str6, str4);
        configurableProvider.addAlgorithm("Alg.Alias.Signature." + (str + "/" + str2), str4);
        if (aSN1ObjectIdentifier != null) {
            configurableProvider.addAlgorithm("Alg.Alias.Signature." + aSN1ObjectIdentifier, str4);
            AbstractC0413b.m1003A(new StringBuilder("Alg.Alias.Signature.OID."), aSN1ObjectIdentifier, configurableProvider, str4);
        }
    }

    public void addSignatureAlgorithm(ConfigurableProvider configurableProvider, String str, String str2, ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        configurableProvider.addAlgorithm("Signature." + str, str2);
        AbstractC0413b.m1003A(AbstractC0413b.m1019m(new StringBuilder("Alg.Alias.Signature."), aSN1ObjectIdentifier, configurableProvider, str, "Alg.Alias.Signature.OID."), aSN1ObjectIdentifier, configurableProvider, str);
    }
}
