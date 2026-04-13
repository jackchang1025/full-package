package org.bouncycastle.operator.jcajce;

import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Provider;
import java.security.ProviderException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.MGF1ParameterSpec;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.asn1.cryptopro.Gost2814789EncryptedKey;
import org.bouncycastle.asn1.cryptopro.GostR3410KeyTransport;
import org.bouncycastle.asn1.cryptopro.GostR3410TransportParameters;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.RSAESOAEPparams;
import org.bouncycastle.asn1.rosstandart.RosstandartObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.jcajce.spec.GOST28147WrapParameterSpec;
import org.bouncycastle.jcajce.spec.UserKeyingMaterialSpec;
import org.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import org.bouncycastle.jcajce.util.NamedJcaJceHelper;
import org.bouncycastle.jcajce.util.ProviderJcaJceHelper;
import org.bouncycastle.operator.AsymmetricKeyWrapper;
import org.bouncycastle.operator.GenericKey;
import org.bouncycastle.operator.OperatorException;
import org.bouncycastle.pqc.crypto.sphincs.SPHINCSKeyParameters;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2KeyGenParameterSpec;
import org.bouncycastle.util.Arrays;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class JceAsymmetricKeyWrapper extends AsymmetricKeyWrapper {
    private static final Map digests;
    private static final Set gostAlgs;
    private Map extraMappings;
    private OperatorHelper helper;
    private PublicKey publicKey;
    private SecureRandom random;

    static {
        HashSet hashSet = new HashSet();
        gostAlgs = hashSet;
        hashSet.add(CryptoProObjectIdentifiers.gostR3410_2001_CryptoPro_ESDH);
        hashSet.add(CryptoProObjectIdentifiers.gostR3410_2001);
        hashSet.add(RosstandartObjectIdentifiers.id_tc26_agreement_gost_3410_12_256);
        hashSet.add(RosstandartObjectIdentifiers.id_tc26_agreement_gost_3410_12_512);
        hashSet.add(RosstandartObjectIdentifiers.id_tc26_gost_3410_12_256);
        hashSet.add(RosstandartObjectIdentifiers.id_tc26_gost_3410_12_512);
        HashMap hashMap = new HashMap();
        digests = hashMap;
        ASN1ObjectIdentifier aSN1ObjectIdentifier = OIWObjectIdentifiers.idSHA1;
        DERNull dERNull = DERNull.INSTANCE;
        hashMap.put("SHA1", new AlgorithmIdentifier(aSN1ObjectIdentifier, dERNull));
        hashMap.put(McElieceCCA2KeyGenParameterSpec.SHA1, new AlgorithmIdentifier(aSN1ObjectIdentifier, dERNull));
        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = NISTObjectIdentifiers.id_sha224;
        hashMap.put("SHA224", new AlgorithmIdentifier(aSN1ObjectIdentifier2, dERNull));
        hashMap.put(McElieceCCA2KeyGenParameterSpec.SHA224, new AlgorithmIdentifier(aSN1ObjectIdentifier2, dERNull));
        ASN1ObjectIdentifier aSN1ObjectIdentifier3 = NISTObjectIdentifiers.id_sha256;
        hashMap.put("SHA256", new AlgorithmIdentifier(aSN1ObjectIdentifier3, dERNull));
        hashMap.put("SHA-256", new AlgorithmIdentifier(aSN1ObjectIdentifier3, dERNull));
        ASN1ObjectIdentifier aSN1ObjectIdentifier4 = NISTObjectIdentifiers.id_sha384;
        hashMap.put("SHA384", new AlgorithmIdentifier(aSN1ObjectIdentifier4, dERNull));
        hashMap.put(McElieceCCA2KeyGenParameterSpec.SHA384, new AlgorithmIdentifier(aSN1ObjectIdentifier4, dERNull));
        ASN1ObjectIdentifier aSN1ObjectIdentifier5 = NISTObjectIdentifiers.id_sha512;
        hashMap.put("SHA512", new AlgorithmIdentifier(aSN1ObjectIdentifier5, dERNull));
        hashMap.put("SHA-512", new AlgorithmIdentifier(aSN1ObjectIdentifier5, dERNull));
        ASN1ObjectIdentifier aSN1ObjectIdentifier6 = NISTObjectIdentifiers.id_sha512_224;
        hashMap.put("SHA512/224", new AlgorithmIdentifier(aSN1ObjectIdentifier6, dERNull));
        hashMap.put("SHA-512/224", new AlgorithmIdentifier(aSN1ObjectIdentifier6, dERNull));
        hashMap.put("SHA-512(224)", new AlgorithmIdentifier(aSN1ObjectIdentifier6, dERNull));
        ASN1ObjectIdentifier aSN1ObjectIdentifier7 = NISTObjectIdentifiers.id_sha512_256;
        hashMap.put("SHA512/256", new AlgorithmIdentifier(aSN1ObjectIdentifier7, dERNull));
        hashMap.put(SPHINCSKeyParameters.SHA512_256, new AlgorithmIdentifier(aSN1ObjectIdentifier7, dERNull));
        hashMap.put("SHA-512(256)", new AlgorithmIdentifier(aSN1ObjectIdentifier7, dERNull));
    }

    public JceAsymmetricKeyWrapper(AlgorithmParameters algorithmParameters, PublicKey publicKey) {
        super(extractFromSpec(algorithmParameters.getParameterSpec(AlgorithmParameterSpec.class)));
        this.helper = new OperatorHelper(new DefaultJcaJceHelper());
        this.extraMappings = new HashMap();
        this.publicKey = publicKey;
    }

    private static AlgorithmIdentifier extractFromSpec(AlgorithmParameterSpec algorithmParameterSpec) {
        if (!(algorithmParameterSpec instanceof OAEPParameterSpec)) {
            throw new IllegalArgumentException("unknown spec: ".concat(algorithmParameterSpec.getClass().getName()));
        }
        OAEPParameterSpec oAEPParameterSpec = (OAEPParameterSpec) algorithmParameterSpec;
        if (!oAEPParameterSpec.getMGFAlgorithm().equals(OAEPParameterSpec.DEFAULT.getMGFAlgorithm())) {
            throw new IllegalArgumentException("unknown MGF: " + oAEPParameterSpec.getMGFAlgorithm());
        }
        if (oAEPParameterSpec.getPSource() instanceof PSource.PSpecified) {
            return new AlgorithmIdentifier(PKCSObjectIdentifiers.id_RSAES_OAEP, new RSAESOAEPparams(getDigest(oAEPParameterSpec.getDigestAlgorithm()), new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, getDigest(((MGF1ParameterSpec) oAEPParameterSpec.getMGFParameters()).getDigestAlgorithm())), new AlgorithmIdentifier(PKCSObjectIdentifiers.id_pSpecified, new DEROctetString(((PSource.PSpecified) oAEPParameterSpec.getPSource()).getValue()))));
        }
        throw new IllegalArgumentException("unknown PSource: " + oAEPParameterSpec.getPSource().getAlgorithm());
    }

    private static AlgorithmIdentifier getDigest(String str) {
        AlgorithmIdentifier algorithmIdentifier = (AlgorithmIdentifier) digests.get(str);
        if (algorithmIdentifier != null) {
            return algorithmIdentifier;
        }
        throw new IllegalArgumentException(AbstractC0000a.m15k("unknown digest name: ", str));
    }

    public static boolean isGOST(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return gostAlgs.contains(aSN1ObjectIdentifier);
    }

    @Override // org.bouncycastle.operator.KeyWrapper
    public byte[] generateWrappedKey(GenericKey genericKey) {
        byte[] bArr;
        if (!isGOST(getAlgorithmIdentifier().getAlgorithm())) {
            Cipher createAsymmetricWrapper = this.helper.createAsymmetricWrapper(getAlgorithmIdentifier().getAlgorithm(), this.extraMappings);
            try {
                AlgorithmParameters createAlgorithmParameters = this.helper.createAlgorithmParameters(getAlgorithmIdentifier());
                if (createAlgorithmParameters != null) {
                    createAsymmetricWrapper.init(3, this.publicKey, createAlgorithmParameters, this.random);
                } else {
                    createAsymmetricWrapper.init(3, this.publicKey, this.random);
                }
                bArr = createAsymmetricWrapper.wrap(OperatorUtils.getJceKey(genericKey));
            } catch (IllegalStateException | UnsupportedOperationException | InvalidKeyException | GeneralSecurityException | ProviderException unused) {
                bArr = null;
            }
            if (bArr != null) {
                return bArr;
            }
            try {
                createAsymmetricWrapper.init(1, this.publicKey, this.random);
                return createAsymmetricWrapper.doFinal(OperatorUtils.getJceKey(genericKey).getEncoded());
            } catch (InvalidKeyException e2) {
                throw new OperatorException("unable to encrypt contents key", e2);
            } catch (GeneralSecurityException e3) {
                throw new OperatorException("unable to encrypt contents key", e3);
            }
        }
        try {
            this.random = CryptoServicesRegistrar.getSecureRandom(this.random);
            KeyPairGenerator createKeyPairGenerator = this.helper.createKeyPairGenerator(getAlgorithmIdentifier().getAlgorithm());
            createKeyPairGenerator.initialize(((ECPublicKey) this.publicKey).getParams(), this.random);
            KeyPair generateKeyPair = createKeyPairGenerator.generateKeyPair();
            byte[] bArr2 = new byte[8];
            this.random.nextBytes(bArr2);
            SubjectPublicKeyInfo subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(generateKeyPair.getPublic().getEncoded());
            GostR3410TransportParameters gostR3410TransportParameters = subjectPublicKeyInfo.getAlgorithm().getAlgorithm().on(RosstandartObjectIdentifiers.id_tc26) ? new GostR3410TransportParameters(RosstandartObjectIdentifiers.id_tc26_gost_28147_param_Z, subjectPublicKeyInfo, bArr2) : new GostR3410TransportParameters(CryptoProObjectIdentifiers.id_Gost28147_89_CryptoPro_A_ParamSet, subjectPublicKeyInfo, bArr2);
            KeyAgreement createKeyAgreement = this.helper.createKeyAgreement(getAlgorithmIdentifier().getAlgorithm());
            createKeyAgreement.init(generateKeyPair.getPrivate(), new UserKeyingMaterialSpec(gostR3410TransportParameters.getUkm()));
            createKeyAgreement.doPhase(this.publicKey, true);
            ASN1ObjectIdentifier aSN1ObjectIdentifier = CryptoProObjectIdentifiers.id_Gost28147_89_CryptoPro_KeyWrap;
            SecretKey generateSecret = createKeyAgreement.generateSecret(aSN1ObjectIdentifier.getId());
            byte[] encoded = OperatorUtils.getJceKey(genericKey).getEncoded();
            Cipher createCipher = this.helper.createCipher(aSN1ObjectIdentifier);
            createCipher.init(3, generateSecret, new GOST28147WrapParameterSpec(gostR3410TransportParameters.getEncryptionParamSet(), gostR3410TransportParameters.getUkm()));
            byte[] wrap = createCipher.wrap(new SecretKeySpec(encoded, "GOST"));
            return new GostR3410KeyTransport(new Gost2814789EncryptedKey(Arrays.copyOfRange(wrap, 0, 32), Arrays.copyOfRange(wrap, 32, 36)), gostR3410TransportParameters).getEncoded();
        } catch (Exception e4) {
            throw new OperatorException(AbstractC0000a.m9e(e4, new StringBuilder("exception wrapping key: ")), e4);
        }
    }

    public JceAsymmetricKeyWrapper setAlgorithmMapping(ASN1ObjectIdentifier aSN1ObjectIdentifier, String str) {
        this.extraMappings.put(aSN1ObjectIdentifier, str);
        return this;
    }

    public JceAsymmetricKeyWrapper setProvider(String str) {
        this.helper = new OperatorHelper(new NamedJcaJceHelper(str));
        return this;
    }

    public JceAsymmetricKeyWrapper setSecureRandom(SecureRandom secureRandom) {
        this.random = secureRandom;
        return this;
    }

    public JceAsymmetricKeyWrapper(PublicKey publicKey) {
        super(SubjectPublicKeyInfo.getInstance(publicKey.getEncoded()).getAlgorithm());
        this.helper = new OperatorHelper(new DefaultJcaJceHelper());
        this.extraMappings = new HashMap();
        this.publicKey = publicKey;
    }

    public JceAsymmetricKeyWrapper setProvider(Provider provider) {
        this.helper = new OperatorHelper(new ProviderJcaJceHelper(provider));
        return this;
    }

    public JceAsymmetricKeyWrapper(X509Certificate x509Certificate) {
        this(x509Certificate.getPublicKey());
    }

    public JceAsymmetricKeyWrapper(AlgorithmParameterSpec algorithmParameterSpec, PublicKey publicKey) {
        super(extractFromSpec(algorithmParameterSpec));
        this.helper = new OperatorHelper(new DefaultJcaJceHelper());
        this.extraMappings = new HashMap();
        this.publicKey = publicKey;
    }

    public JceAsymmetricKeyWrapper(AlgorithmIdentifier algorithmIdentifier, PublicKey publicKey) {
        super(algorithmIdentifier);
        this.helper = new OperatorHelper(new DefaultJcaJceHelper());
        this.extraMappings = new HashMap();
        this.publicKey = publicKey;
    }
}
