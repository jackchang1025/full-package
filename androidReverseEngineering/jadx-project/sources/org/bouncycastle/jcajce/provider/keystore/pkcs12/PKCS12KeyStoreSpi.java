package org.bouncycastle.jcajce.provider.keystore.pkcs12;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.bouncycastle.asn1.ASN1BMPString;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.BEROctetString;
import org.bouncycastle.asn1.BERSequence;
import org.bouncycastle.asn1.DERBMPString;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.asn1.cryptopro.GOST28147Parameters;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.ntt.NTTObjectIdentifiers;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.AuthenticatedSafe;
import org.bouncycastle.asn1.pkcs.CertBag;
import org.bouncycastle.asn1.pkcs.ContentInfo;
import org.bouncycastle.asn1.pkcs.EncryptedData;
import org.bouncycastle.asn1.pkcs.EncryptedPrivateKeyInfo;
import org.bouncycastle.asn1.pkcs.MacData;
import org.bouncycastle.asn1.pkcs.PBES2Parameters;
import org.bouncycastle.asn1.pkcs.PBKDF2Params;
import org.bouncycastle.asn1.pkcs.PKCS12PBEParams;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.Pfx;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.pkcs.SafeBag;
import org.bouncycastle.asn1.util.ASN1Dump;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import org.bouncycastle.asn1.x509.DigestInfo;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import org.bouncycastle.cms.CMSEnvelopedGenerator;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.util.DigestFactory;
import org.bouncycastle.jcajce.BCLoadStoreParameter;
import org.bouncycastle.jcajce.PKCS12Key;
import org.bouncycastle.jcajce.PKCS12StoreParameter;
import org.bouncycastle.jcajce.provider.keystore.util.AdaptingKeyStoreSpi;
import org.bouncycastle.jcajce.provider.keystore.util.ParameterUtil;
import org.bouncycastle.jcajce.spec.GOST28147ParameterSpec;
import org.bouncycastle.jcajce.spec.PBKDF2KeySpec;
import org.bouncycastle.jcajce.util.BCJcaJceHelper;
import org.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.jce.interfaces.BCKeyStore;
import org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.provider.JDKPKCS12StoreParameter;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Integers;
import org.bouncycastle.util.Properties;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;
import com.guard.wallet.entity.BuildConfig;
import p000a.AbstractC0000a;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class PKCS12KeyStoreSpi extends KeyStoreSpi implements PKCSObjectIdentifiers, X509ObjectIdentifiers, BCKeyStore {
    static final int CERTIFICATE = 1;
    static final int KEY = 2;
    static final int KEY_PRIVATE = 0;
    static final int KEY_PUBLIC = 1;
    static final int KEY_SECRET = 2;
    private static final int MIN_ITERATIONS = 51200;
    static final int NULL = 0;
    static final String PKCS12_MAX_IT_COUNT_PROPERTY = "org.bouncycastle.pkcs12.max_it_count";
    private static final int SALT_SIZE = 20;
    static final int SEALED = 4;
    static final int SECRET = 3;
    private static final DefaultSecretKeyProvider keySizeProvider = new DefaultSecretKeyProvider();
    private ASN1ObjectIdentifier certAlgorithm;
    private CertificateFactory certFact;
    private IgnoresCaseHashtable certs;
    private ASN1ObjectIdentifier keyAlgorithm;
    private IgnoresCaseHashtable keys;
    private IgnoresCaseHashtable localIds;
    private final JcaJceHelper helper = new BCJcaJceHelper();
    private Hashtable chainCerts = new Hashtable();
    private Hashtable keyCerts = new Hashtable();
    protected SecureRandom random = CryptoServicesRegistrar.getSecureRandom();
    private AlgorithmIdentifier macAlgorithm = new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1, DERNull.INSTANCE);
    private int itCount = 102400;
    private int saltLength = 20;

    public static class BCPKCS12KeyStore extends AdaptingKeyStoreSpi {
        public BCPKCS12KeyStore() {
            super(new BCJcaJceHelper(), new PKCS12KeyStoreSpi(new BCJcaJceHelper(), PKCSObjectIdentifiers.pbeWithSHAAnd3_KeyTripleDES_CBC, PKCSObjectIdentifiers.pbeWithSHAAnd40BitRC2_CBC));
        }
    }

    public static class BCPKCS12KeyStore3DES extends AdaptingKeyStoreSpi {
        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public BCPKCS12KeyStore3DES() {
            super(r0, new PKCS12KeyStoreSpi(r2, r3, r3));
            BCJcaJceHelper bCJcaJceHelper = new BCJcaJceHelper();
            BCJcaJceHelper bCJcaJceHelper2 = new BCJcaJceHelper();
            ASN1ObjectIdentifier aSN1ObjectIdentifier = PKCSObjectIdentifiers.pbeWithSHAAnd3_KeyTripleDES_CBC;
        }
    }

    public class CertId {
        byte[] id;

        public CertId(PublicKey publicKey) {
            this.id = PKCS12KeyStoreSpi.this.createSubjectKeyId(publicKey).getKeyIdentifier();
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof CertId) {
                return Arrays.areEqual(this.id, ((CertId) obj).id);
            }
            return false;
        }

        public int hashCode() {
            return Arrays.hashCode(this.id);
        }

        public CertId(byte[] bArr) {
            this.id = bArr;
        }
    }

    public static class DefPKCS12KeyStore extends AdaptingKeyStoreSpi {
        public DefPKCS12KeyStore() {
            super(new DefaultJcaJceHelper(), new PKCS12KeyStoreSpi(new DefaultJcaJceHelper(), PKCSObjectIdentifiers.pbeWithSHAAnd3_KeyTripleDES_CBC, PKCSObjectIdentifiers.pbeWithSHAAnd40BitRC2_CBC));
        }
    }

    public static class DefPKCS12KeyStore3DES extends AdaptingKeyStoreSpi {
        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public DefPKCS12KeyStore3DES() {
            super(r0, new PKCS12KeyStoreSpi(r2, r3, r3));
            DefaultJcaJceHelper defaultJcaJceHelper = new DefaultJcaJceHelper();
            DefaultJcaJceHelper defaultJcaJceHelper2 = new DefaultJcaJceHelper();
            ASN1ObjectIdentifier aSN1ObjectIdentifier = PKCSObjectIdentifiers.pbeWithSHAAnd3_KeyTripleDES_CBC;
        }
    }

    public static class DefaultSecretKeyProvider {
        private final Map KEY_SIZES;

        public DefaultSecretKeyProvider() {
            HashMap hashMap = new HashMap();
            hashMap.put(new ASN1ObjectIdentifier(CMSEnvelopedGenerator.CAST5_CBC), Integers.valueOf(128));
            hashMap.put(PKCSObjectIdentifiers.des_EDE3_CBC, Integers.valueOf(192));
            hashMap.put(NISTObjectIdentifiers.id_aes128_CBC, Integers.valueOf(128));
            hashMap.put(NISTObjectIdentifiers.id_aes192_CBC, Integers.valueOf(192));
            hashMap.put(NISTObjectIdentifiers.id_aes256_CBC, Integers.valueOf(256));
            hashMap.put(NTTObjectIdentifiers.id_camellia128_cbc, Integers.valueOf(128));
            hashMap.put(NTTObjectIdentifiers.id_camellia192_cbc, Integers.valueOf(192));
            hashMap.put(NTTObjectIdentifiers.id_camellia256_cbc, Integers.valueOf(256));
            hashMap.put(CryptoProObjectIdentifiers.gostR28147_gcfb, Integers.valueOf(256));
            this.KEY_SIZES = Collections.unmodifiableMap(hashMap);
        }

        public int getKeySize(AlgorithmIdentifier algorithmIdentifier) {
            Integer num = (Integer) this.KEY_SIZES.get(algorithmIdentifier.getAlgorithm());
            if (num != null) {
                return num.intValue();
            }
            return -1;
        }
    }

    public static class IgnoresCaseHashtable {
        private Hashtable keys;
        private Hashtable orig;

        private IgnoresCaseHashtable() {
            this.orig = new Hashtable();
            this.keys = new Hashtable();
        }

        public Enumeration elements() {
            return this.orig.elements();
        }

        public Object get(String str) {
            String str2 = (String) this.keys.get(str == null ? null : Strings.toLowerCase(str));
            if (str2 == null) {
                return null;
            }
            return this.orig.get(str2);
        }

        public Enumeration keys() {
            return this.orig.keys();
        }

        public void put(String str, Object obj) {
            String lowerCase = str == null ? null : Strings.toLowerCase(str);
            String str2 = (String) this.keys.get(lowerCase);
            if (str2 != null) {
                this.orig.remove(str2);
            }
            this.keys.put(lowerCase, str);
            this.orig.put(str, obj);
        }

        public Object remove(String str) {
            String str2 = (String) this.keys.remove(str == null ? null : Strings.toLowerCase(str));
            if (str2 == null) {
                return null;
            }
            return this.orig.remove(str2);
        }

        public int size() {
            return this.orig.size();
        }
    }

    public PKCS12KeyStoreSpi(JcaJceHelper jcaJceHelper, ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1ObjectIdentifier aSN1ObjectIdentifier2) {
        this.keys = new IgnoresCaseHashtable();
        this.localIds = new IgnoresCaseHashtable();
        this.certs = new IgnoresCaseHashtable();
        this.keyAlgorithm = aSN1ObjectIdentifier;
        this.certAlgorithm = aSN1ObjectIdentifier2;
        try {
            this.certFact = jcaJceHelper.createCertificateFactory("X.509");
        } catch (Exception e2) {
            throw new IllegalArgumentException(AbstractC0000a.m29y(e2, new StringBuilder("can't create cert factory - ")));
        }
    }

    private byte[] calculatePbeMac(ASN1ObjectIdentifier aSN1ObjectIdentifier, byte[] bArr, int i2, char[] cArr, boolean z2, byte[] bArr2) {
        PBEParameterSpec pBEParameterSpec = new PBEParameterSpec(bArr, i2);
        Mac createMac = this.helper.createMac(aSN1ObjectIdentifier.getId());
        createMac.init(new PKCS12Key(cArr, z2), pBEParameterSpec);
        createMac.update(bArr2);
        return createMac.doFinal();
    }

    private Cipher createCipher(int i2, char[] cArr, AlgorithmIdentifier algorithmIdentifier) {
        AlgorithmParameterSpec gOST28147ParameterSpec;
        PBES2Parameters pBES2Parameters = PBES2Parameters.getInstance(algorithmIdentifier.getParameters());
        PBKDF2Params pBKDF2Params = PBKDF2Params.getInstance(pBES2Parameters.getKeyDerivationFunc().getParameters());
        AlgorithmIdentifier algorithmIdentifier2 = AlgorithmIdentifier.getInstance(pBES2Parameters.getEncryptionScheme());
        SecretKeyFactory createSecretKeyFactory = this.helper.createSecretKeyFactory(pBES2Parameters.getKeyDerivationFunc().getAlgorithm().getId());
        SecretKey generateSecret = pBKDF2Params.isDefaultPrf() ? createSecretKeyFactory.generateSecret(new PBEKeySpec(cArr, pBKDF2Params.getSalt(), validateIterationCount(pBKDF2Params.getIterationCount()), keySizeProvider.getKeySize(algorithmIdentifier2))) : createSecretKeyFactory.generateSecret(new PBKDF2KeySpec(cArr, pBKDF2Params.getSalt(), validateIterationCount(pBKDF2Params.getIterationCount()), keySizeProvider.getKeySize(algorithmIdentifier2), pBKDF2Params.getPrf()));
        Cipher createCipher = this.helper.createCipher(pBES2Parameters.getEncryptionScheme().getAlgorithm().getId());
        ASN1Encodable parameters = pBES2Parameters.getEncryptionScheme().getParameters();
        if (parameters instanceof ASN1OctetString) {
            gOST28147ParameterSpec = new IvParameterSpec(ASN1OctetString.getInstance(parameters).getOctets());
        } else {
            GOST28147Parameters gOST28147Parameters = GOST28147Parameters.getInstance(parameters);
            gOST28147ParameterSpec = new GOST28147ParameterSpec(gOST28147Parameters.getEncryptionParamSet(), gOST28147Parameters.getIV());
        }
        createCipher.init(i2, generateSecret, gOST28147ParameterSpec);
        return createCipher;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private SafeBag createSafeBag(String str, Certificate certificate) {
        CertBag certBag = new CertBag(PKCSObjectIdentifiers.x509Certificate, new DEROctetString(certificate.getEncoded()));
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        boolean z2 = false;
        if (certificate instanceof PKCS12BagAttributeCarrier) {
            PKCS12BagAttributeCarrier pKCS12BagAttributeCarrier = (PKCS12BagAttributeCarrier) certificate;
            ASN1ObjectIdentifier aSN1ObjectIdentifier = PKCSObjectIdentifiers.pkcs_9_at_friendlyName;
            ASN1BMPString aSN1BMPString = (ASN1BMPString) pKCS12BagAttributeCarrier.getBagAttribute(aSN1ObjectIdentifier);
            if ((aSN1BMPString == null || !aSN1BMPString.getString().equals(str)) && str != null) {
                pKCS12BagAttributeCarrier.setBagAttribute(aSN1ObjectIdentifier, new DERBMPString(str));
            }
            Enumeration bagAttributeKeys = pKCS12BagAttributeCarrier.getBagAttributeKeys();
            while (bagAttributeKeys.hasMoreElements()) {
                ASN1ObjectIdentifier aSN1ObjectIdentifier2 = (ASN1ObjectIdentifier) bagAttributeKeys.nextElement();
                if (!aSN1ObjectIdentifier2.equals((ASN1Primitive) PKCSObjectIdentifiers.pkcs_9_at_localKeyId)) {
                    ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
                    aSN1EncodableVector2.add(aSN1ObjectIdentifier2);
                    aSN1EncodableVector2.add(new DERSet(pKCS12BagAttributeCarrier.getBagAttribute(aSN1ObjectIdentifier2)));
                    aSN1EncodableVector.add(new DERSequence(aSN1EncodableVector2));
                    z2 = true;
                }
            }
        }
        if (!z2) {
            ASN1EncodableVector aSN1EncodableVector3 = new ASN1EncodableVector();
            aSN1EncodableVector3.add(PKCSObjectIdentifiers.pkcs_9_at_friendlyName);
            aSN1EncodableVector3.add(new DERSet(new DERBMPString(str)));
            aSN1EncodableVector.add(new DERSequence(aSN1EncodableVector3));
        }
        return new SafeBag(PKCSObjectIdentifiers.certBag, certBag.toASN1Primitive(), new DERSet(aSN1EncodableVector));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SubjectKeyIdentifier createSubjectKeyId(PublicKey publicKey) {
        try {
            return new SubjectKeyIdentifier(getDigest(SubjectPublicKeyInfo.getInstance(publicKey.getEncoded())));
        } catch (Exception unused) {
            throw new RuntimeException("error creating key");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v28 */
    /* JADX WARN: Type inference failed for: r1v29 */
    /* JADX WARN: Type inference failed for: r1v31 */
    /* JADX WARN: Type inference failed for: r1v32 */
    /* JADX WARN: Type inference failed for: r1v33 */
    /* JADX WARN: Type inference failed for: r1v36 */
    /* JADX WARN: Type inference failed for: r1v7, types: [java.util.Hashtable] */
    /* JADX WARN: Type inference failed for: r1v8, types: [java.util.Hashtable] */
    /* JADX WARN: Type inference failed for: r4v8, types: [java.util.Set] */
    /* JADX WARN: Type inference failed for: r6v13, types: [java.lang.Object, java.security.cert.Certificate] */
    /* JADX WARN: Type inference failed for: r6v7, types: [java.lang.Object, java.security.cert.Certificate] */
    private void doStore(OutputStream outputStream, char[] cArr, boolean z2) {
        String str;
        boolean z3;
        boolean z4;
        int size = this.keys.size();
        String str2 = ASN1Encoding.BER;
        if (size == 0) {
            if (cArr == null) {
                Enumeration keys = this.certs.keys();
                ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
                while (keys.hasMoreElements()) {
                    try {
                        String str3 = (String) keys.nextElement();
                        aSN1EncodableVector.add(createSafeBag(str3, (Certificate) this.certs.get(str3)));
                    } catch (CertificateEncodingException e2) {
                        throw new IOException("Error encoding certificate: " + e2.toString());
                    }
                }
                ASN1ObjectIdentifier aSN1ObjectIdentifier = PKCSObjectIdentifiers.data;
                if (z2) {
                    new Pfx(new ContentInfo(aSN1ObjectIdentifier, new DEROctetString(new DERSequence(new ContentInfo(aSN1ObjectIdentifier, new DEROctetString(new DERSequence(aSN1EncodableVector).getEncoded()))).getEncoded())), null).encodeTo(outputStream, ASN1Encoding.DER);
                    return;
                } else {
                    new Pfx(new ContentInfo(aSN1ObjectIdentifier, new BEROctetString(new BERSequence(new ContentInfo(aSN1ObjectIdentifier, new BEROctetString(new BERSequence(aSN1EncodableVector).getEncoded()))).getEncoded())), null).encodeTo(outputStream, ASN1Encoding.BER);
                    return;
                }
            }
        } else if (cArr == null) {
            throw new NullPointerException("no password supplied for PKCS#12 KeyStore");
        }
        ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
        Enumeration keys2 = this.keys.keys();
        while (keys2.hasMoreElements()) {
            byte[] bArr = new byte[20];
            this.random.nextBytes(bArr);
            String str4 = (String) keys2.nextElement();
            PrivateKey privateKey = (PrivateKey) this.keys.get(str4);
            PKCS12PBEParams pKCS12PBEParams = new PKCS12PBEParams(bArr, MIN_ITERATIONS);
            EncryptedPrivateKeyInfo encryptedPrivateKeyInfo = new EncryptedPrivateKeyInfo(new AlgorithmIdentifier(this.keyAlgorithm, pKCS12PBEParams.toASN1Primitive()), wrapKey(this.keyAlgorithm.getId(), privateKey, pKCS12PBEParams, cArr));
            ASN1EncodableVector aSN1EncodableVector3 = new ASN1EncodableVector();
            if (privateKey instanceof PKCS12BagAttributeCarrier) {
                PKCS12BagAttributeCarrier pKCS12BagAttributeCarrier = (PKCS12BagAttributeCarrier) privateKey;
                ASN1ObjectIdentifier aSN1ObjectIdentifier2 = PKCSObjectIdentifiers.pkcs_9_at_friendlyName;
                ASN1BMPString aSN1BMPString = (ASN1BMPString) pKCS12BagAttributeCarrier.getBagAttribute(aSN1ObjectIdentifier2);
                if (aSN1BMPString == null || !aSN1BMPString.getString().equals(str4)) {
                    pKCS12BagAttributeCarrier.setBagAttribute(aSN1ObjectIdentifier2, new DERBMPString(str4));
                }
                ASN1ObjectIdentifier aSN1ObjectIdentifier3 = PKCSObjectIdentifiers.pkcs_9_at_localKeyId;
                if (pKCS12BagAttributeCarrier.getBagAttribute(aSN1ObjectIdentifier3) == null) {
                    pKCS12BagAttributeCarrier.setBagAttribute(aSN1ObjectIdentifier3, createSubjectKeyId(engineGetCertificate(str4).getPublicKey()));
                }
                Enumeration bagAttributeKeys = pKCS12BagAttributeCarrier.getBagAttributeKeys();
                z4 = false;
                while (bagAttributeKeys.hasMoreElements()) {
                    ASN1ObjectIdentifier aSN1ObjectIdentifier4 = (ASN1ObjectIdentifier) bagAttributeKeys.nextElement();
                    ASN1EncodableVector aSN1EncodableVector4 = new ASN1EncodableVector();
                    aSN1EncodableVector4.add(aSN1ObjectIdentifier4);
                    aSN1EncodableVector4.add(new DERSet(pKCS12BagAttributeCarrier.getBagAttribute(aSN1ObjectIdentifier4)));
                    aSN1EncodableVector3.add(new DERSequence(aSN1EncodableVector4));
                    z4 = true;
                }
            } else {
                z4 = false;
            }
            if (!z4) {
                ASN1EncodableVector aSN1EncodableVector5 = new ASN1EncodableVector();
                Certificate engineGetCertificate = engineGetCertificate(str4);
                aSN1EncodableVector5.add(PKCSObjectIdentifiers.pkcs_9_at_localKeyId);
                aSN1EncodableVector5.add(new DERSet(createSubjectKeyId(engineGetCertificate.getPublicKey())));
                aSN1EncodableVector3.add(new DERSequence(aSN1EncodableVector5));
                ASN1EncodableVector aSN1EncodableVector6 = new ASN1EncodableVector();
                aSN1EncodableVector6.add(PKCSObjectIdentifiers.pkcs_9_at_friendlyName);
                aSN1EncodableVector6.add(new DERSet(new DERBMPString(str4)));
                aSN1EncodableVector3.add(new DERSequence(aSN1EncodableVector6));
            }
            aSN1EncodableVector2.add(new SafeBag(PKCSObjectIdentifiers.pkcs8ShroudedKeyBag, encryptedPrivateKeyInfo.toASN1Primitive(), new DERSet(aSN1EncodableVector3)));
        }
        BEROctetString bEROctetString = new BEROctetString(new DERSequence(aSN1EncodableVector2).getEncoded(ASN1Encoding.DER));
        byte[] bArr2 = new byte[20];
        this.random.nextBytes(bArr2);
        ASN1EncodableVector aSN1EncodableVector7 = new ASN1EncodableVector();
        AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(this.certAlgorithm, new PKCS12PBEParams(bArr2, MIN_ITERATIONS).toASN1Primitive());
        Object hashtable = new Hashtable();
        Enumeration keys3 = this.keys.keys();
        while (keys3.hasMoreElements()) {
            try {
                String str5 = (String) keys3.nextElement();
                ?? engineGetCertificate2 = engineGetCertificate(str5);
                Enumeration enumeration = keys3;
                CertBag certBag = new CertBag(PKCSObjectIdentifiers.x509Certificate, new DEROctetString(engineGetCertificate2.getEncoded()));
                ASN1EncodableVector aSN1EncodableVector8 = new ASN1EncodableVector();
                if (engineGetCertificate2 instanceof PKCS12BagAttributeCarrier) {
                    PKCS12BagAttributeCarrier pKCS12BagAttributeCarrier2 = (PKCS12BagAttributeCarrier) engineGetCertificate2;
                    ASN1ObjectIdentifier aSN1ObjectIdentifier5 = PKCSObjectIdentifiers.pkcs_9_at_friendlyName;
                    ASN1BMPString aSN1BMPString2 = (ASN1BMPString) pKCS12BagAttributeCarrier2.getBagAttribute(aSN1ObjectIdentifier5);
                    if (aSN1BMPString2 == null || !aSN1BMPString2.getString().equals(str5)) {
                        pKCS12BagAttributeCarrier2.setBagAttribute(aSN1ObjectIdentifier5, new DERBMPString(str5));
                    }
                    ASN1ObjectIdentifier aSN1ObjectIdentifier6 = PKCSObjectIdentifiers.pkcs_9_at_localKeyId;
                    if (pKCS12BagAttributeCarrier2.getBagAttribute(aSN1ObjectIdentifier6) == null) {
                        pKCS12BagAttributeCarrier2.setBagAttribute(aSN1ObjectIdentifier6, createSubjectKeyId(engineGetCertificate2.getPublicKey()));
                    }
                    Enumeration bagAttributeKeys2 = pKCS12BagAttributeCarrier2.getBagAttributeKeys();
                    z3 = false;
                    while (bagAttributeKeys2.hasMoreElements()) {
                        ASN1ObjectIdentifier aSN1ObjectIdentifier7 = (ASN1ObjectIdentifier) bagAttributeKeys2.nextElement();
                        Enumeration enumeration2 = bagAttributeKeys2;
                        ASN1EncodableVector aSN1EncodableVector9 = new ASN1EncodableVector();
                        aSN1EncodableVector9.add(aSN1ObjectIdentifier7);
                        aSN1EncodableVector9.add(new DERSet(pKCS12BagAttributeCarrier2.getBagAttribute(aSN1ObjectIdentifier7)));
                        aSN1EncodableVector8.add(new DERSequence(aSN1EncodableVector9));
                        z3 = true;
                        bagAttributeKeys2 = enumeration2;
                        str2 = str2;
                    }
                    str = str2;
                } else {
                    str = str2;
                    z3 = false;
                }
                if (!z3) {
                    ASN1EncodableVector aSN1EncodableVector10 = new ASN1EncodableVector();
                    aSN1EncodableVector10.add(PKCSObjectIdentifiers.pkcs_9_at_localKeyId);
                    aSN1EncodableVector10.add(new DERSet(createSubjectKeyId(engineGetCertificate2.getPublicKey())));
                    aSN1EncodableVector8.add(new DERSequence(aSN1EncodableVector10));
                    ASN1EncodableVector aSN1EncodableVector11 = new ASN1EncodableVector();
                    aSN1EncodableVector11.add(PKCSObjectIdentifiers.pkcs_9_at_friendlyName);
                    aSN1EncodableVector11.add(new DERSet(new DERBMPString(str5)));
                    aSN1EncodableVector8.add(new DERSequence(aSN1EncodableVector11));
                }
                aSN1EncodableVector7.add(new SafeBag(PKCSObjectIdentifiers.certBag, certBag.toASN1Primitive(), new DERSet(aSN1EncodableVector8)));
                hashtable.put(engineGetCertificate2, engineGetCertificate2);
                keys3 = enumeration;
                str2 = str;
            } catch (CertificateEncodingException e3) {
                throw new IOException("Error encoding certificate: " + e3.toString());
            }
        }
        String str6 = str2;
        Enumeration keys4 = this.certs.keys();
        while (keys4.hasMoreElements()) {
            try {
                String str7 = (String) keys4.nextElement();
                Certificate certificate = (Certificate) this.certs.get(str7);
                if (this.keys.get(str7) == null) {
                    aSN1EncodableVector7.add(createSafeBag(str7, certificate));
                    hashtable.put(certificate, certificate);
                }
            } catch (CertificateEncodingException e4) {
                throw new IOException("Error encoding certificate: " + e4.toString());
            }
        }
        ?? usedCertificateSet = getUsedCertificateSet();
        Enumeration keys5 = this.chainCerts.keys();
        while (keys5.hasMoreElements()) {
            try {
                ?? r6 = (Certificate) this.chainCerts.get((CertId) keys5.nextElement());
                if (usedCertificateSet.contains(r6) && hashtable.get(r6) == null) {
                    CertBag certBag2 = new CertBag(PKCSObjectIdentifiers.x509Certificate, new DEROctetString(r6.getEncoded()));
                    ASN1EncodableVector aSN1EncodableVector12 = new ASN1EncodableVector();
                    if (r6 instanceof PKCS12BagAttributeCarrier) {
                        PKCS12BagAttributeCarrier pKCS12BagAttributeCarrier3 = (PKCS12BagAttributeCarrier) r6;
                        Enumeration bagAttributeKeys3 = pKCS12BagAttributeCarrier3.getBagAttributeKeys();
                        while (bagAttributeKeys3.hasMoreElements()) {
                            ASN1ObjectIdentifier aSN1ObjectIdentifier8 = (ASN1ObjectIdentifier) bagAttributeKeys3.nextElement();
                            if (!aSN1ObjectIdentifier8.equals((ASN1Primitive) PKCSObjectIdentifiers.pkcs_9_at_localKeyId)) {
                                ASN1EncodableVector aSN1EncodableVector13 = new ASN1EncodableVector();
                                aSN1EncodableVector13.add(aSN1ObjectIdentifier8);
                                aSN1EncodableVector13.add(new DERSet(pKCS12BagAttributeCarrier3.getBagAttribute(aSN1ObjectIdentifier8)));
                                aSN1EncodableVector12.add(new DERSequence(aSN1EncodableVector13));
                                hashtable = hashtable;
                            }
                        }
                    }
                    Object obj = hashtable;
                    aSN1EncodableVector7.add(new SafeBag(PKCSObjectIdentifiers.certBag, certBag2.toASN1Primitive(), new DERSet(aSN1EncodableVector12)));
                    hashtable = obj;
                }
            } catch (CertificateEncodingException e5) {
                throw new IOException("Error encoding certificate: " + e5.toString());
            }
        }
        byte[] cryptData = cryptData(true, algorithmIdentifier, cArr, false, new DERSequence(aSN1EncodableVector7).getEncoded(ASN1Encoding.DER));
        ASN1ObjectIdentifier aSN1ObjectIdentifier9 = PKCSObjectIdentifiers.data;
        ContentInfo contentInfo = new ContentInfo(aSN1ObjectIdentifier9, new BEROctetString(new AuthenticatedSafe(new ContentInfo[]{new ContentInfo(aSN1ObjectIdentifier9, bEROctetString), new ContentInfo(PKCSObjectIdentifiers.encryptedData, new EncryptedData(aSN1ObjectIdentifier9, algorithmIdentifier, new BEROctetString(cryptData)).toASN1Primitive())}).getEncoded(z2 ? ASN1Encoding.DER : str6)));
        byte[] bArr3 = new byte[this.saltLength];
        this.random.nextBytes(bArr3);
        try {
            new Pfx(contentInfo, new MacData(new DigestInfo(this.macAlgorithm, calculatePbeMac(this.macAlgorithm.getAlgorithm(), bArr3, this.itCount, cArr, false, ((ASN1OctetString) contentInfo.getContent()).getOctets())), bArr3, this.itCount)).encodeTo(outputStream, z2 ? ASN1Encoding.DER : str6);
        } catch (Exception e6) {
            throw new IOException(AbstractC0000a.m29y(e6, new StringBuilder("error constructing MAC: ")));
        }
    }

    private static byte[] getDigest(SubjectPublicKeyInfo subjectPublicKeyInfo) {
        Digest createSHA1 = DigestFactory.createSHA1();
        byte[] bArr = new byte[createSHA1.getDigestSize()];
        byte[] bytes = subjectPublicKeyInfo.getPublicKeyData().getBytes();
        createSHA1.update(bytes, 0, bytes.length);
        createSHA1.doFinal(bArr, 0);
        return bArr;
    }

    private Set getUsedCertificateSet() {
        HashSet hashSet = new HashSet();
        Enumeration keys = this.keys.keys();
        while (keys.hasMoreElements()) {
            Certificate[] engineGetCertificateChain = engineGetCertificateChain((String) keys.nextElement());
            for (int i2 = 0; i2 != engineGetCertificateChain.length; i2++) {
                hashSet.add(engineGetCertificateChain[i2]);
            }
        }
        Enumeration keys2 = this.certs.keys();
        while (keys2.hasMoreElements()) {
            hashSet.add(engineGetCertificate((String) keys2.nextElement()));
        }
        return hashSet;
    }

    private int validateIterationCount(BigInteger bigInteger) {
        int intValue = bigInteger.intValue();
        if (intValue < 0) {
            throw new IllegalStateException("negative iteration count found");
        }
        BigInteger asBigInteger = Properties.asBigInteger(PKCS12_MAX_IT_COUNT_PROPERTY);
        if (asBigInteger == null || asBigInteger.intValue() >= intValue) {
            return intValue;
        }
        StringBuilder m21q = AbstractC0000a.m21q("iteration count ", intValue, " greater than ");
        m21q.append(asBigInteger.intValue());
        throw new IllegalStateException(m21q.toString());
    }

    public byte[] cryptData(boolean z2, AlgorithmIdentifier algorithmIdentifier, char[] cArr, boolean z3, byte[] bArr) {
        ASN1ObjectIdentifier algorithm = algorithmIdentifier.getAlgorithm();
        int i2 = z2 ? 1 : 2;
        if (!algorithm.on(PKCSObjectIdentifiers.pkcs_12PbeIds)) {
            if (!algorithm.equals((ASN1Primitive) PKCSObjectIdentifiers.id_PBES2)) {
                throw new IOException(AbstractC0413b.m1009c("unknown PBE algorithm: ", algorithm));
            }
            try {
                return createCipher(i2, cArr, algorithmIdentifier).doFinal(bArr);
            } catch (Exception e2) {
                throw new IOException(AbstractC0000a.m29y(e2, new StringBuilder("exception decrypting data - ")));
            }
        }
        PKCS12PBEParams pKCS12PBEParams = PKCS12PBEParams.getInstance(algorithmIdentifier.getParameters());
        try {
            PBEParameterSpec pBEParameterSpec = new PBEParameterSpec(pKCS12PBEParams.getIV(), pKCS12PBEParams.getIterations().intValue());
            PKCS12Key pKCS12Key = new PKCS12Key(cArr, z3);
            Cipher createCipher = this.helper.createCipher(algorithm.getId());
            createCipher.init(i2, pKCS12Key, pBEParameterSpec);
            return createCipher.doFinal(bArr);
        } catch (Exception e3) {
            throw new IOException(AbstractC0000a.m29y(e3, new StringBuilder("exception decrypting data - ")));
        }
    }

    @Override // java.security.KeyStoreSpi
    public Enumeration engineAliases() {
        Hashtable hashtable = new Hashtable();
        Enumeration keys = this.certs.keys();
        while (keys.hasMoreElements()) {
            hashtable.put(keys.nextElement(), "cert");
        }
        Enumeration keys2 = this.keys.keys();
        while (keys2.hasMoreElements()) {
            String str = (String) keys2.nextElement();
            if (hashtable.get(str) == null) {
                hashtable.put(str, "key");
            }
        }
        return hashtable.keys();
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineContainsAlias(String str) {
        return (this.certs.get(str) == null && this.keys.get(str) == null) ? false : true;
    }

    @Override // java.security.KeyStoreSpi
    public void engineDeleteEntry(String str) {
        Key key = (Key) this.keys.remove(str);
        Certificate certificate = (Certificate) this.certs.remove(str);
        if (certificate != null) {
            this.chainCerts.remove(new CertId(certificate.getPublicKey()));
        }
        if (key != null) {
            String str2 = (String) this.localIds.remove(str);
            if (str2 != null) {
                certificate = (Certificate) this.keyCerts.remove(str2);
            }
            if (certificate != null) {
                this.chainCerts.remove(new CertId(certificate.getPublicKey()));
            }
        }
    }

    @Override // java.security.KeyStoreSpi
    public Certificate engineGetCertificate(String str) {
        if (str == null) {
            throw new IllegalArgumentException("null alias passed to getCertificate.");
        }
        Certificate certificate = (Certificate) this.certs.get(str);
        if (certificate != null) {
            return certificate;
        }
        String str2 = (String) this.localIds.get(str);
        return (Certificate) (str2 != null ? this.keyCerts.get(str2) : this.keyCerts.get(str));
    }

    @Override // java.security.KeyStoreSpi
    public String engineGetCertificateAlias(Certificate certificate) {
        Enumeration elements = this.certs.elements();
        Enumeration keys = this.certs.keys();
        while (elements.hasMoreElements()) {
            Certificate certificate2 = (Certificate) elements.nextElement();
            String str = (String) keys.nextElement();
            if (certificate2.equals(certificate)) {
                return str;
            }
        }
        Enumeration elements2 = this.keyCerts.elements();
        Enumeration keys2 = this.keyCerts.keys();
        while (elements2.hasMoreElements()) {
            Certificate certificate3 = (Certificate) elements2.nextElement();
            String str2 = (String) keys2.nextElement();
            if (certificate3.equals(certificate)) {
                return str2;
            }
        }
        return null;
    }

    @Override // java.security.KeyStoreSpi
    public Certificate[] engineGetCertificateChain(String str) {
        byte[] keyIdentifier;
        if (str == null) {
            throw new IllegalArgumentException("null alias passed to getCertificateChain.");
        }
        Certificate[] certificateArr = null;
        if (!engineIsKeyEntry(str)) {
            return null;
        }
        Certificate engineGetCertificate = engineGetCertificate(str);
        if (engineGetCertificate != null) {
            Vector vector = new Vector();
            while (engineGetCertificate != null) {
                X509Certificate x509Certificate = (X509Certificate) engineGetCertificate;
                byte[] extensionValue = x509Certificate.getExtensionValue(Extension.authorityKeyIdentifier.getId());
                Certificate certificate = (extensionValue == null || (keyIdentifier = AuthorityKeyIdentifier.getInstance(ASN1OctetString.getInstance(extensionValue).getOctets()).getKeyIdentifier()) == null) ? null : (Certificate) this.chainCerts.get(new CertId(keyIdentifier));
                if (certificate == null) {
                    Principal issuerDN = x509Certificate.getIssuerDN();
                    if (!issuerDN.equals(x509Certificate.getSubjectDN())) {
                        Enumeration keys = this.chainCerts.keys();
                        while (true) {
                            if (!keys.hasMoreElements()) {
                                break;
                            }
                            X509Certificate x509Certificate2 = (X509Certificate) this.chainCerts.get(keys.nextElement());
                            if (x509Certificate2.getSubjectDN().equals(issuerDN)) {
                                try {
                                    x509Certificate.verify(x509Certificate2.getPublicKey());
                                    certificate = x509Certificate2;
                                    break;
                                } catch (Exception unused) {
                                    continue;
                                }
                            }
                        }
                    }
                }
                if (!vector.contains(engineGetCertificate)) {
                    vector.addElement(engineGetCertificate);
                    if (certificate != engineGetCertificate) {
                        engineGetCertificate = certificate;
                    }
                }
                engineGetCertificate = null;
            }
            int size = vector.size();
            certificateArr = new Certificate[size];
            for (int i2 = 0; i2 != size; i2++) {
                certificateArr[i2] = (Certificate) vector.elementAt(i2);
            }
        }
        return certificateArr;
    }

    @Override // java.security.KeyStoreSpi
    public Date engineGetCreationDate(String str) {
        if (str == null) {
            throw new NullPointerException("alias == null");
        }
        if (this.keys.get(str) == null && this.certs.get(str) == null) {
            return null;
        }
        return new Date();
    }

    @Override // java.security.KeyStoreSpi
    public Key engineGetKey(String str, char[] cArr) {
        if (str != null) {
            return (Key) this.keys.get(str);
        }
        throw new IllegalArgumentException("null alias passed to getKey.");
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineIsCertificateEntry(String str) {
        return this.certs.get(str) != null && this.keys.get(str) == null;
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineIsKeyEntry(String str) {
        return this.keys.get(str) != null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:172:0x04ac  */
    /* JADX WARN: Removed duplicated region for block: B:245:0x048e  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0106  */
    /* JADX WARN: Type inference failed for: r12v17 */
    /* JADX WARN: Type inference failed for: r12v18, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r12v19 */
    /* JADX WARN: Type inference failed for: r12v20 */
    /* JADX WARN: Type inference failed for: r12v21 */
    /* JADX WARN: Type inference failed for: r12v22 */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v15 */
    /* JADX WARN: Type inference failed for: r1v16, types: [org.bouncycastle.asn1.ASN1OctetString] */
    /* JADX WARN: Type inference failed for: r1v17, types: [org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable] */
    /* JADX WARN: Type inference failed for: r1v21 */
    /* JADX WARN: Type inference failed for: r1v25 */
    /* JADX WARN: Type inference failed for: r1v26 */
    /* JADX WARN: Type inference failed for: r1v27 */
    /* JADX WARN: Type inference failed for: r2v41, types: [org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable] */
    /* JADX WARN: Type inference failed for: r3v7, types: [java.lang.Object, java.security.cert.Certificate] */
    /* JADX WARN: Type inference failed for: r6v38 */
    /* JADX WARN: Type inference failed for: r6v39, types: [org.bouncycastle.asn1.ASN1OctetString] */
    /* JADX WARN: Type inference failed for: r6v41 */
    /* JADX WARN: Type inference failed for: r6v42 */
    /* JADX WARN: Type inference failed for: r6v43 */
    /* JADX WARN: Type inference failed for: r6v44 */
    /* JADX WARN: Type inference failed for: r6v45 */
    @Override // java.security.KeyStoreSpi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void engineLoad(InputStream inputStream, char[] cArr) {
        boolean z2;
        boolean z3;
        int i2;
        String str;
        boolean z4;
        boolean z5;
        ASN1Sequence aSN1Sequence;
        ASN1Primitive aSN1Primitive;
        ASN1Sequence aSN1Sequence2;
        ?? r12;
        ?? r6;
        ASN1Primitive aSN1Primitive2;
        if (inputStream == null) {
            return;
        }
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        bufferedInputStream.mark(10);
        int read = bufferedInputStream.read();
        if (read < 0) {
            throw new EOFException("no data in keystore stream");
        }
        if (read != 48) {
            throw new IOException("stream does not represent a PKCS12 key store");
        }
        bufferedInputStream.reset();
        try {
            Pfx pfx = Pfx.getInstance(new ASN1InputStream(bufferedInputStream).readObject());
            ContentInfo authSafe = pfx.getAuthSafe();
            Vector vector = new Vector();
            int i3 = 1;
            int i4 = 0;
            if (pfx.getMacData() != null) {
                if (cArr == null) {
                    throw new NullPointerException("no password supplied when one expected");
                }
                MacData macData = pfx.getMacData();
                DigestInfo mac = macData.getMac();
                this.macAlgorithm = mac.getAlgorithmId();
                byte[] salt = macData.getSalt();
                this.itCount = validateIterationCount(macData.getIterationCount());
                this.saltLength = salt.length;
                byte[] octets = ((ASN1OctetString) authSafe.getContent()).getOctets();
                try {
                    byte[] calculatePbeMac = calculatePbeMac(this.macAlgorithm.getAlgorithm(), salt, this.itCount, cArr, false, octets);
                    byte[] digest = mac.getDigest();
                    if (!Arrays.constantTimeAreEqual(calculatePbeMac, digest)) {
                        if (cArr.length > 0) {
                            throw new IOException("PKCS12 key store mac invalid - wrong password or corrupted file.");
                        }
                        if (!Arrays.constantTimeAreEqual(calculatePbeMac(this.macAlgorithm.getAlgorithm(), salt, this.itCount, cArr, true, octets), digest)) {
                            throw new IOException("PKCS12 key store mac invalid - wrong password or corrupted file.");
                        }
                        z2 = true;
                        C06421 c06421 = null;
                        this.keys = new IgnoresCaseHashtable();
                        this.localIds = new IgnoresCaseHashtable();
                        if (authSafe.getContentType().equals((ASN1Primitive) PKCSObjectIdentifiers.data)) {
                            z3 = false;
                        } else {
                            ContentInfo[] contentInfo = AuthenticatedSafe.getInstance(ASN1OctetString.getInstance(authSafe.getContent()).getOctets()).getContentInfo();
                            int i5 = 0;
                            z3 = false;
                            while (i4 != contentInfo.length) {
                                if (contentInfo[i4].getContentType().equals((ASN1Primitive) PKCSObjectIdentifiers.data)) {
                                    ASN1Sequence aSN1Sequence3 = ASN1Sequence.getInstance(ASN1OctetString.getInstance(contentInfo[i4].getContent()).getOctets());
                                    C06421 c064212 = c06421;
                                    int i6 = i5;
                                    while (i5 != aSN1Sequence3.size()) {
                                        SafeBag safeBag = SafeBag.getInstance(aSN1Sequence3.getObjectAt(i5));
                                        int i7 = i6;
                                        if (safeBag.getBagId().equals((ASN1Primitive) PKCSObjectIdentifiers.pkcs8ShroudedKeyBag)) {
                                            EncryptedPrivateKeyInfo encryptedPrivateKeyInfo = EncryptedPrivateKeyInfo.getInstance(safeBag.getBagValue());
                                            PrivateKey unwrapKey = unwrapKey(encryptedPrivateKeyInfo.getEncryptionAlgorithm(), encryptedPrivateKeyInfo.getEncryptedData(), cArr, z2);
                                            if (safeBag.getBagAttributes() != null) {
                                                Enumeration objects = safeBag.getBagAttributes().getObjects();
                                                C06421 c064213 = c064212;
                                                int i8 = i7;
                                                aSN1Sequence2 = aSN1Sequence3;
                                                int i9 = i3;
                                                r12 = c064213;
                                                r6 = c064213;
                                                while (objects.hasMoreElements()) {
                                                    Enumeration enumeration = objects;
                                                    ASN1Sequence aSN1Sequence4 = (ASN1Sequence) objects.nextElement();
                                                    ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) aSN1Sequence4.getObjectAt(i8);
                                                    ASN1Set aSN1Set = (ASN1Set) aSN1Sequence4.getObjectAt(i9);
                                                    if (aSN1Set.size() > 0) {
                                                        aSN1Primitive2 = (ASN1Primitive) aSN1Set.getObjectAt(0);
                                                        if (unwrapKey instanceof PKCS12BagAttributeCarrier) {
                                                            PKCS12BagAttributeCarrier pKCS12BagAttributeCarrier = (PKCS12BagAttributeCarrier) unwrapKey;
                                                            ASN1Encodable bagAttribute = pKCS12BagAttributeCarrier.getBagAttribute(aSN1ObjectIdentifier);
                                                            if (bagAttribute == null) {
                                                                pKCS12BagAttributeCarrier.setBagAttribute(aSN1ObjectIdentifier, aSN1Primitive2);
                                                            } else if (!bagAttribute.toASN1Primitive().equals(aSN1Primitive2)) {
                                                                throw new IOException("attempt to add existing attribute with different value");
                                                            }
                                                        }
                                                    } else {
                                                        aSN1Primitive2 = null;
                                                    }
                                                    if (aSN1ObjectIdentifier.equals((ASN1Primitive) PKCSObjectIdentifiers.pkcs_9_at_friendlyName)) {
                                                        String string = ((ASN1BMPString) aSN1Primitive2).getString();
                                                        this.keys.put(string, unwrapKey);
                                                        r12 = string;
                                                    } else if (aSN1ObjectIdentifier.equals((ASN1Primitive) PKCSObjectIdentifiers.pkcs_9_at_localKeyId)) {
                                                        r6 = (ASN1OctetString) aSN1Primitive2;
                                                    }
                                                    i9 = 1;
                                                    i8 = 0;
                                                    objects = enumeration;
                                                    r6 = r6;
                                                }
                                            } else {
                                                aSN1Sequence2 = aSN1Sequence3;
                                                r12 = null;
                                                r6 = 0;
                                            }
                                            if (r6 != 0) {
                                                String str2 = new String(Hex.encode(r6.getOctets()));
                                                if (r12 == null) {
                                                    this.keys.put(str2, unwrapKey);
                                                } else {
                                                    this.localIds.put(r12, str2);
                                                }
                                            } else {
                                                this.keys.put("unmarked", unwrapKey);
                                                z3 = true;
                                            }
                                        } else {
                                            aSN1Sequence2 = aSN1Sequence3;
                                            if (safeBag.getBagId().equals((ASN1Primitive) PKCSObjectIdentifiers.certBag)) {
                                                vector.addElement(safeBag);
                                            } else {
                                                System.out.println("extra in data " + safeBag.getBagId());
                                                System.out.println(ASN1Dump.dumpAsString(safeBag));
                                            }
                                        }
                                        i5++;
                                        c064212 = null;
                                        i3 = 1;
                                        i6 = 0;
                                        aSN1Sequence3 = aSN1Sequence2;
                                    }
                                } else if (contentInfo[i4].getContentType().equals((ASN1Primitive) PKCSObjectIdentifiers.encryptedData)) {
                                    EncryptedData encryptedData = EncryptedData.getInstance(contentInfo[i4].getContent());
                                    int i10 = 0;
                                    ASN1Sequence aSN1Sequence5 = ASN1Sequence.getInstance(cryptData(false, encryptedData.getEncryptionAlgorithm(), cArr, z2, encryptedData.getContent().getOctets()));
                                    while (i10 != aSN1Sequence5.size()) {
                                        SafeBag safeBag2 = SafeBag.getInstance(aSN1Sequence5.getObjectAt(i10));
                                        if (safeBag2.getBagId().equals((ASN1Primitive) PKCSObjectIdentifiers.certBag)) {
                                            vector.addElement(safeBag2);
                                            z5 = z2;
                                            aSN1Sequence = aSN1Sequence5;
                                        } else if (safeBag2.getBagId().equals((ASN1Primitive) PKCSObjectIdentifiers.pkcs8ShroudedKeyBag)) {
                                            EncryptedPrivateKeyInfo encryptedPrivateKeyInfo2 = EncryptedPrivateKeyInfo.getInstance(safeBag2.getBagValue());
                                            PrivateKey unwrapKey2 = unwrapKey(encryptedPrivateKeyInfo2.getEncryptionAlgorithm(), encryptedPrivateKeyInfo2.getEncryptedData(), cArr, z2);
                                            PKCS12BagAttributeCarrier pKCS12BagAttributeCarrier2 = (PKCS12BagAttributeCarrier) unwrapKey2;
                                            Enumeration objects2 = safeBag2.getBagAttributes().getObjects();
                                            ASN1OctetString aSN1OctetString = null;
                                            String str3 = null;
                                            while (objects2.hasMoreElements()) {
                                                boolean z6 = z2;
                                                ASN1Sequence aSN1Sequence6 = (ASN1Sequence) objects2.nextElement();
                                                ASN1Sequence aSN1Sequence7 = aSN1Sequence5;
                                                ASN1ObjectIdentifier aSN1ObjectIdentifier2 = (ASN1ObjectIdentifier) aSN1Sequence6.getObjectAt(0);
                                                Enumeration enumeration2 = objects2;
                                                ASN1Set aSN1Set2 = (ASN1Set) aSN1Sequence6.getObjectAt(1);
                                                if (aSN1Set2.size() > 0) {
                                                    aSN1Primitive = (ASN1Primitive) aSN1Set2.getObjectAt(0);
                                                    ASN1Encodable bagAttribute2 = pKCS12BagAttributeCarrier2.getBagAttribute(aSN1ObjectIdentifier2);
                                                    if (bagAttribute2 == null) {
                                                        pKCS12BagAttributeCarrier2.setBagAttribute(aSN1ObjectIdentifier2, aSN1Primitive);
                                                    } else if (!bagAttribute2.toASN1Primitive().equals(aSN1Primitive)) {
                                                        throw new IOException("attempt to add existing attribute with different value");
                                                    }
                                                } else {
                                                    aSN1Primitive = null;
                                                }
                                                if (aSN1ObjectIdentifier2.equals((ASN1Primitive) PKCSObjectIdentifiers.pkcs_9_at_friendlyName)) {
                                                    String string2 = ((ASN1BMPString) aSN1Primitive).getString();
                                                    this.keys.put(string2, unwrapKey2);
                                                    str3 = string2;
                                                } else if (aSN1ObjectIdentifier2.equals((ASN1Primitive) PKCSObjectIdentifiers.pkcs_9_at_localKeyId)) {
                                                    aSN1OctetString = (ASN1OctetString) aSN1Primitive;
                                                }
                                                aSN1Sequence5 = aSN1Sequence7;
                                                objects2 = enumeration2;
                                                z2 = z6;
                                            }
                                            z5 = z2;
                                            aSN1Sequence = aSN1Sequence5;
                                            String str4 = new String(Hex.encode(aSN1OctetString.getOctets()));
                                            if (str3 == null) {
                                                this.keys.put(str4, unwrapKey2);
                                            } else {
                                                this.localIds.put(str3, str4);
                                            }
                                        } else {
                                            z5 = z2;
                                            aSN1Sequence = aSN1Sequence5;
                                            if (safeBag2.getBagId().equals((ASN1Primitive) PKCSObjectIdentifiers.keyBag)) {
                                                PrivateKey privateKey = BouncyCastleProvider.getPrivateKey(PrivateKeyInfo.getInstance(safeBag2.getBagValue()));
                                                PKCS12BagAttributeCarrier pKCS12BagAttributeCarrier3 = (PKCS12BagAttributeCarrier) privateKey;
                                                Enumeration objects3 = safeBag2.getBagAttributes().getObjects();
                                                ASN1OctetString aSN1OctetString2 = null;
                                                String str5 = null;
                                                while (objects3.hasMoreElements()) {
                                                    ASN1Sequence aSN1Sequence8 = ASN1Sequence.getInstance(objects3.nextElement());
                                                    ASN1ObjectIdentifier aSN1ObjectIdentifier3 = ASN1ObjectIdentifier.getInstance(aSN1Sequence8.getObjectAt(0));
                                                    Enumeration enumeration3 = objects3;
                                                    ASN1Set aSN1Set3 = ASN1Set.getInstance(aSN1Sequence8.getObjectAt(1));
                                                    if (aSN1Set3.size() > 0) {
                                                        ASN1Primitive aSN1Primitive3 = (ASN1Primitive) aSN1Set3.getObjectAt(0);
                                                        ASN1Encodable bagAttribute3 = pKCS12BagAttributeCarrier3.getBagAttribute(aSN1ObjectIdentifier3);
                                                        if (bagAttribute3 == null) {
                                                            pKCS12BagAttributeCarrier3.setBagAttribute(aSN1ObjectIdentifier3, aSN1Primitive3);
                                                        } else if (!bagAttribute3.toASN1Primitive().equals(aSN1Primitive3)) {
                                                            throw new IOException("attempt to add existing attribute with different value");
                                                        }
                                                        if (aSN1ObjectIdentifier3.equals((ASN1Primitive) PKCSObjectIdentifiers.pkcs_9_at_friendlyName)) {
                                                            str5 = ((ASN1BMPString) aSN1Primitive3).getString();
                                                            this.keys.put(str5, privateKey);
                                                        } else if (aSN1ObjectIdentifier3.equals((ASN1Primitive) PKCSObjectIdentifiers.pkcs_9_at_localKeyId)) {
                                                            aSN1OctetString2 = (ASN1OctetString) aSN1Primitive3;
                                                        }
                                                    }
                                                    objects3 = enumeration3;
                                                }
                                                String str6 = new String(Hex.encode(aSN1OctetString2.getOctets()));
                                                if (str5 == null) {
                                                    this.keys.put(str6, privateKey);
                                                } else {
                                                    this.localIds.put(str5, str6);
                                                }
                                            } else {
                                                System.out.println("extra in encryptedData " + safeBag2.getBagId());
                                                System.out.println(ASN1Dump.dumpAsString(safeBag2));
                                            }
                                        }
                                        i10++;
                                        aSN1Sequence5 = aSN1Sequence;
                                        z2 = z5;
                                    }
                                } else {
                                    z4 = z2;
                                    System.out.println("extra " + contentInfo[i4].getContentType().getId());
                                    System.out.println("extra " + ASN1Dump.dumpAsString(contentInfo[i4].getContent()));
                                    i4++;
                                    c06421 = null;
                                    i3 = 1;
                                    i5 = 0;
                                    z2 = z4;
                                }
                                z4 = z2;
                                i4++;
                                c06421 = null;
                                i3 = 1;
                                i5 = 0;
                                z2 = z4;
                            }
                        }
                        ?? r1 = 0;
                        this.certs = new IgnoresCaseHashtable();
                        this.chainCerts = new Hashtable();
                        this.keyCerts = new Hashtable();
                        i2 = 0;
                        while (i2 != vector.size()) {
                            SafeBag safeBag3 = (SafeBag) vector.elementAt(i2);
                            CertBag certBag = CertBag.getInstance(safeBag3.getBagValue());
                            if (!certBag.getCertId().equals((ASN1Primitive) PKCSObjectIdentifiers.x509Certificate)) {
                                throw new RuntimeException("Unsupported certificate type: " + certBag.getCertId());
                            }
                            try {
                                ?? generateCertificate = this.certFact.generateCertificate(new ByteArrayInputStream(((ASN1OctetString) certBag.getCertValue()).getOctets()));
                                if (safeBag3.getBagAttributes() != null) {
                                    Enumeration objects4 = safeBag3.getBagAttributes().getObjects();
                                    str = r1;
                                    while (objects4.hasMoreElements()) {
                                        ASN1Sequence aSN1Sequence9 = ASN1Sequence.getInstance(objects4.nextElement());
                                        ASN1ObjectIdentifier aSN1ObjectIdentifier4 = ASN1ObjectIdentifier.getInstance(aSN1Sequence9.getObjectAt(0));
                                        ASN1Set aSN1Set4 = ASN1Set.getInstance(aSN1Sequence9.getObjectAt(1));
                                        if (aSN1Set4.size() > 0) {
                                            ASN1Primitive aSN1Primitive4 = (ASN1Primitive) aSN1Set4.getObjectAt(0);
                                            if (generateCertificate instanceof PKCS12BagAttributeCarrier) {
                                                PKCS12BagAttributeCarrier pKCS12BagAttributeCarrier4 = (PKCS12BagAttributeCarrier) generateCertificate;
                                                ASN1Encodable bagAttribute4 = pKCS12BagAttributeCarrier4.getBagAttribute(aSN1ObjectIdentifier4);
                                                if (bagAttribute4 != null) {
                                                    if (aSN1ObjectIdentifier4.equals((ASN1Primitive) PKCSObjectIdentifiers.pkcs_9_at_localKeyId)) {
                                                        String hexString = Hex.toHexString(((ASN1OctetString) aSN1Primitive4).getOctets());
                                                        if (!this.keys.keys.containsKey(hexString) && !this.localIds.keys.containsKey(hexString)) {
                                                        }
                                                    }
                                                    if (!bagAttribute4.toASN1Primitive().equals(aSN1Primitive4)) {
                                                        throw new IOException("attempt to add existing attribute with different value");
                                                    }
                                                } else {
                                                    pKCS12BagAttributeCarrier4.setBagAttribute(aSN1ObjectIdentifier4, aSN1Primitive4);
                                                }
                                            }
                                            if (aSN1ObjectIdentifier4.equals((ASN1Primitive) PKCSObjectIdentifiers.pkcs_9_at_friendlyName)) {
                                                str = ((ASN1BMPString) aSN1Primitive4).getString();
                                            } else if (aSN1ObjectIdentifier4.equals((ASN1Primitive) PKCSObjectIdentifiers.pkcs_9_at_localKeyId)) {
                                                r1 = (ASN1OctetString) aSN1Primitive4;
                                            }
                                        }
                                    }
                                } else {
                                    str = null;
                                    r1 = 0;
                                }
                                this.chainCerts.put(new CertId(generateCertificate.getPublicKey()), generateCertificate);
                                if (!z3) {
                                    if (r1 != 0) {
                                        this.keyCerts.put(new String(Hex.encode(r1.getOctets())), generateCertificate);
                                    }
                                    if (str != null) {
                                        this.certs.put(str, generateCertificate);
                                    }
                                } else if (this.keyCerts.isEmpty()) {
                                    String str7 = new String(Hex.encode(createSubjectKeyId(generateCertificate.getPublicKey()).getKeyIdentifier()));
                                    this.keyCerts.put(str7, generateCertificate);
                                    IgnoresCaseHashtable ignoresCaseHashtable = this.keys;
                                    ignoresCaseHashtable.put(str7, ignoresCaseHashtable.remove("unmarked"));
                                }
                                i2++;
                                r1 = 0;
                            } catch (Exception e2) {
                                throw new RuntimeException(e2.toString());
                            }
                        }
                    }
                } catch (IOException e3) {
                    throw e3;
                } catch (Exception e4) {
                    throw new IOException(AbstractC0000a.m29y(e4, new StringBuilder("error constructing MAC: ")));
                }
            } else if (cArr != null && cArr.length != 0 && !Properties.isOverrideSet("org.bouncycastle.pkcs12.ignore_useless_passwd")) {
                throw new IOException("password supplied for keystore that does not require one");
            }
            z2 = false;
            C06421 c064214 = null;
            this.keys = new IgnoresCaseHashtable();
            this.localIds = new IgnoresCaseHashtable();
            if (authSafe.getContentType().equals((ASN1Primitive) PKCSObjectIdentifiers.data)) {
            }
            ?? r13 = 0;
            this.certs = new IgnoresCaseHashtable();
            this.chainCerts = new Hashtable();
            this.keyCerts = new Hashtable();
            i2 = 0;
            while (i2 != vector.size()) {
            }
        } catch (Exception e5) {
            throw new IOException(e5.getMessage());
        }
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineProbe(InputStream inputStream) {
        return false;
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetCertificateEntry(String str, Certificate certificate) {
        if (this.keys.get(str) != null) {
            throw new KeyStoreException(AbstractC0000a.m16l("There is a key entry with the name ", str, "."));
        }
        this.certs.put(str, certificate);
        this.chainCerts.put(new CertId(certificate.getPublicKey()), certificate);
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetKeyEntry(String str, Key key, char[] cArr, Certificate[] certificateArr) {
        boolean z2 = key instanceof PrivateKey;
        if (!z2) {
            throw new KeyStoreException("PKCS12 does not support non-PrivateKeys");
        }
        if (z2 && certificateArr == null) {
            throw new KeyStoreException("no certificate chain for private key");
        }
        if (this.keys.get(str) != null) {
            engineDeleteEntry(str);
        }
        this.keys.put(str, key);
        if (certificateArr != null) {
            this.certs.put(str, certificateArr[0]);
            for (int i2 = 0; i2 != certificateArr.length; i2++) {
                this.chainCerts.put(new CertId(certificateArr[i2].getPublicKey()), certificateArr[i2]);
            }
        }
    }

    @Override // java.security.KeyStoreSpi
    public int engineSize() {
        Hashtable hashtable = new Hashtable();
        Enumeration keys = this.certs.keys();
        while (keys.hasMoreElements()) {
            hashtable.put(keys.nextElement(), "cert");
        }
        Enumeration keys2 = this.keys.keys();
        while (keys2.hasMoreElements()) {
            String str = (String) keys2.nextElement();
            if (hashtable.get(str) == null) {
                hashtable.put(str, "key");
            }
        }
        return hashtable.size();
    }

    @Override // java.security.KeyStoreSpi
    public void engineStore(OutputStream outputStream, char[] cArr) {
        doStore(outputStream, cArr, false);
    }

    @Override // org.bouncycastle.jce.interfaces.BCKeyStore
    public void setRandom(SecureRandom secureRandom) {
        this.random = secureRandom;
    }

    public PrivateKey unwrapKey(AlgorithmIdentifier algorithmIdentifier, byte[] bArr, char[] cArr, boolean z2) {
        ASN1ObjectIdentifier algorithm = algorithmIdentifier.getAlgorithm();
        try {
            if (!algorithm.on(PKCSObjectIdentifiers.pkcs_12PbeIds)) {
                if (algorithm.equals((ASN1Primitive) PKCSObjectIdentifiers.id_PBES2)) {
                    return (PrivateKey) createCipher(4, cArr, algorithmIdentifier).unwrap(bArr, BuildConfig.FLAVOR, 2);
                }
                throw new IOException(AbstractC0413b.m1009c("exception unwrapping private key - cannot recognise: ", algorithm));
            }
            PKCS12PBEParams pKCS12PBEParams = PKCS12PBEParams.getInstance(algorithmIdentifier.getParameters());
            PBEParameterSpec pBEParameterSpec = new PBEParameterSpec(pKCS12PBEParams.getIV(), validateIterationCount(pKCS12PBEParams.getIterations()));
            Cipher createCipher = this.helper.createCipher(algorithm.getId());
            createCipher.init(4, new PKCS12Key(cArr, z2), pBEParameterSpec);
            return (PrivateKey) createCipher.unwrap(bArr, BuildConfig.FLAVOR, 2);
        } catch (Exception e2) {
            throw new IOException(AbstractC0000a.m29y(e2, new StringBuilder("exception unwrapping private key - ")));
        }
    }

    public byte[] wrapKey(String str, Key key, PKCS12PBEParams pKCS12PBEParams, char[] cArr) {
        PBEKeySpec pBEKeySpec = new PBEKeySpec(cArr);
        try {
            SecretKeyFactory createSecretKeyFactory = this.helper.createSecretKeyFactory(str);
            PBEParameterSpec pBEParameterSpec = new PBEParameterSpec(pKCS12PBEParams.getIV(), pKCS12PBEParams.getIterations().intValue());
            Cipher createCipher = this.helper.createCipher(str);
            createCipher.init(3, createSecretKeyFactory.generateSecret(pBEKeySpec), pBEParameterSpec);
            return createCipher.wrap(key);
        } catch (Exception e2) {
            throw new IOException(AbstractC0000a.m29y(e2, new StringBuilder("exception encrypting data - ")));
        }
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetKeyEntry(String str, byte[] bArr, Certificate[] certificateArr) {
        throw new RuntimeException("operation not supported");
    }

    @Override // java.security.KeyStoreSpi
    public void engineStore(KeyStore.LoadStoreParameter loadStoreParameter) {
        PKCS12StoreParameter pKCS12StoreParameter;
        char[] password;
        if (loadStoreParameter == null) {
            throw new IllegalArgumentException("'param' arg cannot be null");
        }
        boolean z2 = loadStoreParameter instanceof PKCS12StoreParameter;
        if (!z2 && !(loadStoreParameter instanceof JDKPKCS12StoreParameter)) {
            throw new IllegalArgumentException("No support for 'param' of type ".concat(loadStoreParameter.getClass().getName()));
        }
        if (z2) {
            pKCS12StoreParameter = (PKCS12StoreParameter) loadStoreParameter;
        } else {
            JDKPKCS12StoreParameter jDKPKCS12StoreParameter = (JDKPKCS12StoreParameter) loadStoreParameter;
            pKCS12StoreParameter = new PKCS12StoreParameter(jDKPKCS12StoreParameter.getOutputStream(), loadStoreParameter.getProtectionParameter(), jDKPKCS12StoreParameter.isUseDEREncoding());
        }
        KeyStore.ProtectionParameter protectionParameter = loadStoreParameter.getProtectionParameter();
        if (protectionParameter == null) {
            password = null;
        } else {
            if (!(protectionParameter instanceof KeyStore.PasswordProtection)) {
                throw new IllegalArgumentException("No support for protection parameter of type ".concat(protectionParameter.getClass().getName()));
            }
            password = ((KeyStore.PasswordProtection) protectionParameter).getPassword();
        }
        doStore(pKCS12StoreParameter.getOutputStream(), password, pKCS12StoreParameter.isForDEREncoding());
    }

    @Override // java.security.KeyStoreSpi
    public void engineLoad(KeyStore.LoadStoreParameter loadStoreParameter) {
        if (loadStoreParameter == null) {
            engineLoad(null, null);
        } else {
            if (!(loadStoreParameter instanceof BCLoadStoreParameter)) {
                throw new IllegalArgumentException("no support for 'param' of type ".concat(loadStoreParameter.getClass().getName()));
            }
            engineLoad(((BCLoadStoreParameter) loadStoreParameter).getInputStream(), ParameterUtil.extractPassword(loadStoreParameter));
        }
    }
}
