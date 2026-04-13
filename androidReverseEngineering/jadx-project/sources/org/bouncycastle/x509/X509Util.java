package org.bouncycastle.x509;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.RSASSAPSSparams;
import org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.util.Strings;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
class X509Util {
    private static Hashtable algorithms = new Hashtable();
    private static Hashtable params = new Hashtable();
    private static Set noParams = new HashSet();

    public static class Implementation {
        Object engine;
        Provider provider;

        public Implementation(Object obj, Provider provider) {
            this.engine = obj;
            this.provider = provider;
        }

        public Object getEngine() {
            return this.engine;
        }

        public Provider getProvider() {
            return this.provider;
        }
    }

    static {
        Hashtable hashtable = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier = PKCSObjectIdentifiers.md2WithRSAEncryption;
        hashtable.put("MD2WITHRSAENCRYPTION", aSN1ObjectIdentifier);
        algorithms.put("MD2WITHRSA", aSN1ObjectIdentifier);
        Hashtable hashtable2 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = PKCSObjectIdentifiers.md5WithRSAEncryption;
        hashtable2.put("MD5WITHRSAENCRYPTION", aSN1ObjectIdentifier2);
        algorithms.put("MD5WITHRSA", aSN1ObjectIdentifier2);
        Hashtable hashtable3 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier3 = PKCSObjectIdentifiers.sha1WithRSAEncryption;
        hashtable3.put("SHA1WITHRSAENCRYPTION", aSN1ObjectIdentifier3);
        algorithms.put("SHA1WITHRSA", aSN1ObjectIdentifier3);
        Hashtable hashtable4 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier4 = PKCSObjectIdentifiers.sha224WithRSAEncryption;
        hashtable4.put("SHA224WITHRSAENCRYPTION", aSN1ObjectIdentifier4);
        algorithms.put("SHA224WITHRSA", aSN1ObjectIdentifier4);
        Hashtable hashtable5 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier5 = PKCSObjectIdentifiers.sha256WithRSAEncryption;
        hashtable5.put("SHA256WITHRSAENCRYPTION", aSN1ObjectIdentifier5);
        algorithms.put("SHA256WITHRSA", aSN1ObjectIdentifier5);
        Hashtable hashtable6 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier6 = PKCSObjectIdentifiers.sha384WithRSAEncryption;
        hashtable6.put("SHA384WITHRSAENCRYPTION", aSN1ObjectIdentifier6);
        algorithms.put("SHA384WITHRSA", aSN1ObjectIdentifier6);
        Hashtable hashtable7 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier7 = PKCSObjectIdentifiers.sha512WithRSAEncryption;
        hashtable7.put("SHA512WITHRSAENCRYPTION", aSN1ObjectIdentifier7);
        algorithms.put("SHA512WITHRSA", aSN1ObjectIdentifier7);
        Hashtable hashtable8 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier8 = PKCSObjectIdentifiers.id_RSASSA_PSS;
        hashtable8.put("SHA1WITHRSAANDMGF1", aSN1ObjectIdentifier8);
        algorithms.put("SHA224WITHRSAANDMGF1", aSN1ObjectIdentifier8);
        algorithms.put("SHA256WITHRSAANDMGF1", aSN1ObjectIdentifier8);
        algorithms.put("SHA384WITHRSAANDMGF1", aSN1ObjectIdentifier8);
        algorithms.put("SHA512WITHRSAANDMGF1", aSN1ObjectIdentifier8);
        Hashtable hashtable9 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier9 = TeleTrusTObjectIdentifiers.rsaSignatureWithripemd160;
        hashtable9.put("RIPEMD160WITHRSAENCRYPTION", aSN1ObjectIdentifier9);
        algorithms.put("RIPEMD160WITHRSA", aSN1ObjectIdentifier9);
        Hashtable hashtable10 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier10 = TeleTrusTObjectIdentifiers.rsaSignatureWithripemd128;
        hashtable10.put("RIPEMD128WITHRSAENCRYPTION", aSN1ObjectIdentifier10);
        algorithms.put("RIPEMD128WITHRSA", aSN1ObjectIdentifier10);
        Hashtable hashtable11 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier11 = TeleTrusTObjectIdentifiers.rsaSignatureWithripemd256;
        hashtable11.put("RIPEMD256WITHRSAENCRYPTION", aSN1ObjectIdentifier11);
        algorithms.put("RIPEMD256WITHRSA", aSN1ObjectIdentifier11);
        Hashtable hashtable12 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier12 = X9ObjectIdentifiers.id_dsa_with_sha1;
        hashtable12.put("SHA1WITHDSA", aSN1ObjectIdentifier12);
        algorithms.put("DSAWITHSHA1", aSN1ObjectIdentifier12);
        Hashtable hashtable13 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier13 = NISTObjectIdentifiers.dsa_with_sha224;
        hashtable13.put("SHA224WITHDSA", aSN1ObjectIdentifier13);
        Hashtable hashtable14 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier14 = NISTObjectIdentifiers.dsa_with_sha256;
        hashtable14.put("SHA256WITHDSA", aSN1ObjectIdentifier14);
        Hashtable hashtable15 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier15 = NISTObjectIdentifiers.dsa_with_sha384;
        hashtable15.put("SHA384WITHDSA", aSN1ObjectIdentifier15);
        Hashtable hashtable16 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier16 = NISTObjectIdentifiers.dsa_with_sha512;
        hashtable16.put("SHA512WITHDSA", aSN1ObjectIdentifier16);
        Hashtable hashtable17 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier17 = X9ObjectIdentifiers.ecdsa_with_SHA1;
        hashtable17.put("SHA1WITHECDSA", aSN1ObjectIdentifier17);
        algorithms.put("ECDSAWITHSHA1", aSN1ObjectIdentifier17);
        Hashtable hashtable18 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier18 = X9ObjectIdentifiers.ecdsa_with_SHA224;
        hashtable18.put("SHA224WITHECDSA", aSN1ObjectIdentifier18);
        Hashtable hashtable19 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier19 = X9ObjectIdentifiers.ecdsa_with_SHA256;
        hashtable19.put("SHA256WITHECDSA", aSN1ObjectIdentifier19);
        Hashtable hashtable20 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier20 = X9ObjectIdentifiers.ecdsa_with_SHA384;
        hashtable20.put("SHA384WITHECDSA", aSN1ObjectIdentifier20);
        Hashtable hashtable21 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier21 = X9ObjectIdentifiers.ecdsa_with_SHA512;
        hashtable21.put("SHA512WITHECDSA", aSN1ObjectIdentifier21);
        Hashtable hashtable22 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier22 = CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94;
        hashtable22.put("GOST3411WITHGOST3410", aSN1ObjectIdentifier22);
        algorithms.put("GOST3411WITHGOST3410-94", aSN1ObjectIdentifier22);
        Hashtable hashtable23 = algorithms;
        ASN1ObjectIdentifier aSN1ObjectIdentifier23 = CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001;
        hashtable23.put("GOST3411WITHECGOST3410", aSN1ObjectIdentifier23);
        algorithms.put("GOST3411WITHECGOST3410-2001", aSN1ObjectIdentifier23);
        algorithms.put("GOST3411WITHGOST3410-2001", aSN1ObjectIdentifier23);
        noParams.add(aSN1ObjectIdentifier17);
        noParams.add(aSN1ObjectIdentifier18);
        noParams.add(aSN1ObjectIdentifier19);
        noParams.add(aSN1ObjectIdentifier20);
        noParams.add(aSN1ObjectIdentifier21);
        noParams.add(aSN1ObjectIdentifier12);
        noParams.add(OIWObjectIdentifiers.dsaWithSHA1);
        noParams.add(aSN1ObjectIdentifier13);
        noParams.add(aSN1ObjectIdentifier14);
        noParams.add(aSN1ObjectIdentifier15);
        noParams.add(aSN1ObjectIdentifier16);
        noParams.add(aSN1ObjectIdentifier22);
        noParams.add(aSN1ObjectIdentifier23);
        ASN1ObjectIdentifier aSN1ObjectIdentifier24 = OIWObjectIdentifiers.idSHA1;
        DERNull dERNull = DERNull.INSTANCE;
        params.put("SHA1WITHRSAANDMGF1", creatPSSParams(new AlgorithmIdentifier(aSN1ObjectIdentifier24, dERNull), 20));
        params.put("SHA224WITHRSAANDMGF1", creatPSSParams(new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha224, dERNull), 28));
        params.put("SHA256WITHRSAANDMGF1", creatPSSParams(new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256, dERNull), 32));
        params.put("SHA384WITHRSAANDMGF1", creatPSSParams(new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha384, dERNull), 48));
        params.put("SHA512WITHRSAANDMGF1", creatPSSParams(new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha512, dERNull), 64));
    }

    public static byte[] calculateSignature(ASN1ObjectIdentifier aSN1ObjectIdentifier, String str, String str2, PrivateKey privateKey, SecureRandom secureRandom, ASN1Encodable aSN1Encodable) {
        if (aSN1ObjectIdentifier == null) {
            throw new IllegalStateException("no signature algorithm specified");
        }
        Signature signatureInstance = getSignatureInstance(str, str2);
        if (secureRandom != null) {
            signatureInstance.initSign(privateKey, secureRandom);
        } else {
            signatureInstance.initSign(privateKey);
        }
        signatureInstance.update(aSN1Encodable.toASN1Primitive().getEncoded(ASN1Encoding.DER));
        return signatureInstance.sign();
    }

    public static X509Principal convertPrincipal(X500Principal x500Principal) {
        try {
            return new X509Principal(x500Principal.getEncoded());
        } catch (IOException unused) {
            throw new IllegalArgumentException("cannot convert principal");
        }
    }

    private static RSASSAPSSparams creatPSSParams(AlgorithmIdentifier algorithmIdentifier, int i2) {
        return new RSASSAPSSparams(algorithmIdentifier, new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, algorithmIdentifier), new ASN1Integer(i2), new ASN1Integer(1L));
    }

    public static Iterator getAlgNames() {
        Enumeration keys = algorithms.keys();
        ArrayList arrayList = new ArrayList();
        while (keys.hasMoreElements()) {
            arrayList.add(keys.nextElement());
        }
        return arrayList.iterator();
    }

    public static ASN1ObjectIdentifier getAlgorithmOID(String str) {
        String upperCase = Strings.toUpperCase(str);
        return algorithms.containsKey(upperCase) ? (ASN1ObjectIdentifier) algorithms.get(upperCase) : new ASN1ObjectIdentifier(upperCase);
    }

    public static Implementation getImplementation(String str, String str2) {
        Provider[] providers = Security.getProviders();
        for (int i2 = 0; i2 != providers.length; i2++) {
            Implementation implementation = getImplementation(str, Strings.toUpperCase(str2), providers[i2]);
            if (implementation != null) {
                return implementation;
            }
            try {
                getImplementation(str, str2, providers[i2]);
            } catch (NoSuchAlgorithmException unused) {
            }
        }
        throw new NoSuchAlgorithmException(AbstractC0000a.m15k("cannot find implementation ", str2));
    }

    public static Provider getProvider(String str) {
        Provider provider = Security.getProvider(str);
        if (provider != null) {
            return provider;
        }
        throw new NoSuchProviderException(AbstractC0000a.m16l("Provider ", str, " not found"));
    }

    public static AlgorithmIdentifier getSigAlgID(ASN1ObjectIdentifier aSN1ObjectIdentifier, String str) {
        if (noParams.contains(aSN1ObjectIdentifier)) {
            return new AlgorithmIdentifier(aSN1ObjectIdentifier);
        }
        String upperCase = Strings.toUpperCase(str);
        return params.containsKey(upperCase) ? new AlgorithmIdentifier(aSN1ObjectIdentifier, (ASN1Encodable) params.get(upperCase)) : new AlgorithmIdentifier(aSN1ObjectIdentifier, DERNull.INSTANCE);
    }

    public static Signature getSignatureInstance(String str) {
        return Signature.getInstance(str);
    }

    public static byte[] calculateSignature(ASN1ObjectIdentifier aSN1ObjectIdentifier, String str, PrivateKey privateKey, SecureRandom secureRandom, ASN1Encodable aSN1Encodable) {
        if (aSN1ObjectIdentifier == null) {
            throw new IllegalStateException("no signature algorithm specified");
        }
        Signature signatureInstance = getSignatureInstance(str);
        if (secureRandom != null) {
            signatureInstance.initSign(privateKey, secureRandom);
        } else {
            signatureInstance.initSign(privateKey);
        }
        signatureInstance.update(aSN1Encodable.toASN1Primitive().getEncoded(ASN1Encoding.DER));
        return signatureInstance.sign();
    }

    public static Signature getSignatureInstance(String str, String str2) {
        return str2 != null ? Signature.getInstance(str, str2) : Signature.getInstance(str);
    }

    public static Implementation getImplementation(String str, String str2, Provider provider) {
        String upperCase = Strings.toUpperCase(str2);
        while (true) {
            String property = provider.getProperty("Alg.Alias." + str + "." + upperCase);
            if (property == null) {
                break;
            }
            upperCase = property;
        }
        String property2 = provider.getProperty(str + "." + upperCase);
        if (property2 != null) {
            try {
                ClassLoader classLoader = provider.getClass().getClassLoader();
                return new Implementation((classLoader != null ? classLoader.loadClass(property2) : Class.forName(property2)).newInstance(), provider);
            } catch (ClassNotFoundException unused) {
                StringBuilder m23s = AbstractC0000a.m23s("algorithm ", upperCase, " in provider ");
                m23s.append(provider.getName());
                m23s.append(" but no class \"");
                m23s.append(property2);
                m23s.append("\" found!");
                throw new IllegalStateException(m23s.toString());
            } catch (Exception unused2) {
                StringBuilder m23s2 = AbstractC0000a.m23s("algorithm ", upperCase, " in provider ");
                m23s2.append(provider.getName());
                m23s2.append(" but class \"");
                m23s2.append(property2);
                m23s2.append("\" inaccessible!");
                throw new IllegalStateException(m23s2.toString());
            }
        }
        StringBuilder m23s3 = AbstractC0000a.m23s("cannot find implementation ", upperCase, " for provider ");
        m23s3.append(provider.getName());
        throw new NoSuchAlgorithmException(m23s3.toString());
    }
}
