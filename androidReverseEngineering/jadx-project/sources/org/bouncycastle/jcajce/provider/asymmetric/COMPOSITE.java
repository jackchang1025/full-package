package org.bouncycastle.jcajce.provider.asymmetric;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.misc.MiscObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jcajce.CompositePrivateKey;
import org.bouncycastle.jcajce.CompositePublicKey;
import org.bouncycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;
import org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;
import p000a.AbstractC0000a;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class COMPOSITE {
    private static final String PREFIX = "org.bouncycastle.jcajce.provider.asymmetric.COMPOSITE";
    private static AsymmetricKeyInfoConverter baseConverter;
    private static final Map<String, String> compositeAttributes;

    public static class CompositeKeyInfoConverter implements AsymmetricKeyInfoConverter {
        private final ConfigurableProvider provider;

        public CompositeKeyInfoConverter(ConfigurableProvider configurableProvider) {
            this.provider = configurableProvider;
        }

        @Override // org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter
        public PrivateKey generatePrivate(PrivateKeyInfo privateKeyInfo) {
            ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(privateKeyInfo.getPrivateKey().getOctets());
            PrivateKey[] privateKeyArr = new PrivateKey[aSN1Sequence.size()];
            for (int i2 = 0; i2 != aSN1Sequence.size(); i2++) {
                PrivateKeyInfo privateKeyInfo2 = PrivateKeyInfo.getInstance(aSN1Sequence.getObjectAt(i2));
                privateKeyArr[i2] = this.provider.getKeyInfoConverter(privateKeyInfo2.getPrivateKeyAlgorithm().getAlgorithm()).generatePrivate(privateKeyInfo2);
            }
            return new CompositePrivateKey(privateKeyArr);
        }

        @Override // org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter
        public PublicKey generatePublic(SubjectPublicKeyInfo subjectPublicKeyInfo) {
            ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(subjectPublicKeyInfo.getPublicKeyData().getBytes());
            PublicKey[] publicKeyArr = new PublicKey[aSN1Sequence.size()];
            for (int i2 = 0; i2 != aSN1Sequence.size(); i2++) {
                SubjectPublicKeyInfo subjectPublicKeyInfo2 = SubjectPublicKeyInfo.getInstance(aSN1Sequence.getObjectAt(i2));
                publicKeyArr[i2] = this.provider.getKeyInfoConverter(subjectPublicKeyInfo2.getAlgorithm().getAlgorithm()).generatePublic(subjectPublicKeyInfo2);
            }
            return new CompositePublicKey(publicKeyArr);
        }
    }

    public static class KeyFactory extends BaseKeyFactorySpi {
        @Override // java.security.KeyFactorySpi
        public Key engineTranslateKey(Key key) {
            try {
                if (key instanceof PrivateKey) {
                    return generatePrivate(PrivateKeyInfo.getInstance(key.getEncoded()));
                }
                if (key instanceof PublicKey) {
                    return generatePublic(SubjectPublicKeyInfo.getInstance(key.getEncoded()));
                }
                throw new InvalidKeyException("key not recognized");
            } catch (IOException e2) {
                throw new InvalidKeyException(AbstractC0000a.m8d(e2, new StringBuilder("key could not be parsed: ")));
            }
        }

        @Override // org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter
        public PrivateKey generatePrivate(PrivateKeyInfo privateKeyInfo) {
            return COMPOSITE.baseConverter.generatePrivate(privateKeyInfo);
        }

        @Override // org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter
        public PublicKey generatePublic(SubjectPublicKeyInfo subjectPublicKeyInfo) {
            return COMPOSITE.baseConverter.generatePublic(subjectPublicKeyInfo);
        }
    }

    public static class Mappings extends AsymmetricAlgorithmProvider {
        @Override // org.bouncycastle.jcajce.provider.util.AlgorithmProvider
        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder m1020n = AbstractC0413b.m1020n(configurableProvider, "KeyFactory.COMPOSITE", "org.bouncycastle.jcajce.provider.asymmetric.COMPOSITE$KeyFactory", "KeyFactory.");
            ASN1ObjectIdentifier aSN1ObjectIdentifier = MiscObjectIdentifiers.id_alg_composite;
            StringBuilder m1019m = AbstractC0413b.m1019m(m1020n, aSN1ObjectIdentifier, configurableProvider, "org.bouncycastle.jcajce.provider.asymmetric.COMPOSITE$KeyFactory", "KeyFactory.OID.");
            m1019m.append(aSN1ObjectIdentifier);
            configurableProvider.addAlgorithm(m1019m.toString(), "org.bouncycastle.jcajce.provider.asymmetric.COMPOSITE$KeyFactory");
            AsymmetricKeyInfoConverter unused = COMPOSITE.baseConverter = new CompositeKeyInfoConverter(configurableProvider);
            configurableProvider.addKeyInfoConverter(aSN1ObjectIdentifier, COMPOSITE.baseConverter);
        }
    }

    static {
        HashMap hashMap = new HashMap();
        compositeAttributes = hashMap;
        hashMap.put("SupportedKeyClasses", "org.bouncycastle.jcajce.CompositePublicKey|org.bouncycastle.jcajce.CompositePrivateKey");
        hashMap.put("SupportedKeyFormats", "PKCS#8|X.509");
    }
}
