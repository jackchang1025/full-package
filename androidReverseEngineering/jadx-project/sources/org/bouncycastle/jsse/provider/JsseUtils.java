package org.bouncycastle.jsse.provider;

import java.io.IOException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ocsp.OCSPResponse;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.bouncycastle.jcajce.spec.EdDSAParameterSpec;
import org.bouncycastle.jsse.BCSNIHostName;
import org.bouncycastle.jsse.BCSNIMatcher;
import org.bouncycastle.jsse.BCSNIServerName;
import org.bouncycastle.jsse.BCX509ExtendedTrustManager;
import org.bouncycastle.jsse.BCX509Key;
import org.bouncycastle.jsse.java.security.BCCryptoPrimitive;
import org.bouncycastle.tls.AlertDescription;
import org.bouncycastle.tls.AlertLevel;
import org.bouncycastle.tls.Certificate;
import org.bouncycastle.tls.CertificateEntry;
import org.bouncycastle.tls.CertificateStatus;
import org.bouncycastle.tls.NamedGroup;
import org.bouncycastle.tls.ProtocolName;
import org.bouncycastle.tls.ProtocolVersion;
import org.bouncycastle.tls.SecurityParameters;
import org.bouncycastle.tls.ServerName;
import org.bouncycastle.tls.SignatureAndHashAlgorithm;
import org.bouncycastle.tls.TlsContext;
import org.bouncycastle.tls.TlsCredentialedDecryptor;
import org.bouncycastle.tls.TlsCredentialedSigner;
import org.bouncycastle.tls.TlsUtils;
import org.bouncycastle.tls.TrustedAuthority;
import org.bouncycastle.tls.crypto.TlsCertificate;
import org.bouncycastle.tls.crypto.TlsCryptoParameters;
import org.bouncycastle.tls.crypto.impl.jcajce.JcaDefaultTlsCredentialedSigner;
import org.bouncycastle.tls.crypto.impl.jcajce.JcaTlsCertificate;
import org.bouncycastle.tls.crypto.impl.jcajce.JcaTlsCrypto;
import org.bouncycastle.tls.crypto.impl.jcajce.JceDefaultTlsCredentialedDecryptor;
import com.guard.wallet.entity.BuildConfig;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
abstract class JsseUtils {
    private static final boolean provTlsAllowLegacyMasterSecret = PropertyUtils.getBooleanSystemProperty("jdk.tls.allowLegacyMasterSecret", true);
    private static final boolean provTlsAllowLegacyResumption = PropertyUtils.getBooleanSystemProperty("jdk.tls.allowLegacyResumption", false);
    private static final int provTlsMaxCertificateChainLength = PropertyUtils.getIntegerSystemProperty("jdk.tls.maxCertificateChainLength", 10, 1, Integer.MAX_VALUE);
    private static final int provTlsMaxHandshakeMessageSize = PropertyUtils.getIntegerSystemProperty("jdk.tls.maxHandshakeMessageSize", 32768, 1024, Integer.MAX_VALUE);
    private static final boolean provTlsRequireCloseNotify = PropertyUtils.getBooleanSystemProperty("com.sun.net.ssl.requireCloseNotify", true);
    private static final boolean provTlsUseExtendedMasterSecret = PropertyUtils.getBooleanSystemProperty("jdk.tls.useExtendedMasterSecret", true);
    static final Set<BCCryptoPrimitive> KEY_AGREEMENT_CRYPTO_PRIMITIVES_BC = Collections.unmodifiableSet(EnumSet.of(BCCryptoPrimitive.KEY_AGREEMENT));
    static final Set<BCCryptoPrimitive> KEY_ENCAPSULATION_CRYPTO_PRIMITIVES_BC = Collections.unmodifiableSet(EnumSet.of(BCCryptoPrimitive.KEY_ENCAPSULATION));
    static final Set<BCCryptoPrimitive> SIGNATURE_CRYPTO_PRIMITIVES_BC = Collections.unmodifiableSet(EnumSet.of(BCCryptoPrimitive.SIGNATURE));
    static String EMPTY_STRING = BuildConfig.FLAVOR;
    static X509Certificate[] EMPTY_X509CERTIFICATES = new X509Certificate[0];

    public static class BCUnknownServerName extends BCSNIServerName {
        public BCUnknownServerName(int i2, byte[] bArr) {
            super(i2, bArr);
        }
    }

    public static boolean allowLegacyMasterSecret() {
        return provTlsAllowLegacyMasterSecret;
    }

    public static boolean allowLegacyResumption() {
        return provTlsAllowLegacyResumption;
    }

    public static void checkSessionCreationEnabled(ProvTlsManager provTlsManager) {
        if (!provTlsManager.getEnableSessionCreation()) {
            throw new IllegalStateException("Cannot resume session and session creation is disabled");
        }
    }

    public static <T> T[] clone(T[] tArr) {
        if (tArr == null) {
            return null;
        }
        return (T[]) ((Object[]) tArr.clone());
    }

    public static boolean contains(String[] strArr, String str) {
        for (String str2 : strArr) {
            if (str.equals(str2)) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean containsNull(T[] tArr) {
        for (T t2 : tArr) {
            if (t2 == null) {
                return true;
            }
        }
        return false;
    }

    public static BCSNIServerName convertSNIServerName(ServerName serverName) {
        short nameType = serverName.getNameType();
        byte[] nameData = serverName.getNameData();
        return nameType != 0 ? new BCUnknownServerName(nameType, nameData) : new BCSNIHostName(nameData);
    }

    public static List<BCSNIServerName> convertSNIServerNames(Vector<ServerName> vector) {
        if (vector == null || vector.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(vector.size());
        Enumeration<ServerName> elements = vector.elements();
        while (elements.hasMoreElements()) {
            arrayList.add(convertSNIServerName(elements.nextElement()));
        }
        return Collections.unmodifiableList(arrayList);
    }

    public static String[] copyOf(String[] strArr, int i2) {
        String[] strArr2 = new String[i2];
        System.arraycopy(strArr, 0, strArr2, 0, Math.min(strArr.length, i2));
        return strArr2;
    }

    public static TlsCredentialedDecryptor createCredentialedDecryptor(JcaTlsCrypto jcaTlsCrypto, BCX509Key bCX509Key) {
        return new JceDefaultTlsCredentialedDecryptor(jcaTlsCrypto, getCertificateMessage(jcaTlsCrypto, bCX509Key.getCertificateChain()), bCX509Key.getPrivateKey());
    }

    public static TlsCredentialedSigner createCredentialedSigner(TlsContext tlsContext, JcaTlsCrypto jcaTlsCrypto, BCX509Key bCX509Key, SignatureAndHashAlgorithm signatureAndHashAlgorithm) {
        return new JcaDefaultTlsCredentialedSigner(new TlsCryptoParameters(tlsContext), jcaTlsCrypto, bCX509Key.getPrivateKey(), getCertificateMessage(jcaTlsCrypto, bCX509Key.getCertificateChain()), signatureAndHashAlgorithm);
    }

    public static TlsCredentialedSigner createCredentialedSigner13(TlsContext tlsContext, JcaTlsCrypto jcaTlsCrypto, BCX509Key bCX509Key, SignatureAndHashAlgorithm signatureAndHashAlgorithm, byte[] bArr) {
        return new JcaDefaultTlsCredentialedSigner(new TlsCryptoParameters(tlsContext), jcaTlsCrypto, bCX509Key.getPrivateKey(), getCertificateMessage13(jcaTlsCrypto, bCX509Key.getCertificateChain(), bArr), signatureAndHashAlgorithm);
    }

    public static boolean equals(Object obj, Object obj2) {
        return obj == obj2 || !(obj == null || obj2 == null || !obj.equals(obj2));
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x000e, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static BCSNIServerName findMatchingSNIServerName(Vector<ServerName> vector, Collection<BCSNIMatcher> collection) {
        if (vector.isEmpty()) {
            return null;
        }
        List<BCSNIServerName> convertSNIServerNames = convertSNIServerNames(vector);
        for (BCSNIMatcher bCSNIMatcher : collection) {
            if (bCSNIMatcher != null) {
                int type = bCSNIMatcher.getType();
                Iterator<BCSNIServerName> it = convertSNIServerNames.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    BCSNIServerName next = it.next();
                    if (next != null && next.getType() == type) {
                        if (bCSNIMatcher.matches(next)) {
                            return next;
                        }
                    }
                }
            }
        }
        return null;
    }

    public static String getAlertLogMessage(String str, short s2, short s3) {
        StringBuilder m22r = AbstractC0000a.m22r(str, " ");
        m22r.append(AlertLevel.getText(s2));
        m22r.append(" ");
        m22r.append(AlertDescription.getText(s3));
        m22r.append(" alert");
        return m22r.toString();
    }

    public static String getApplicationProtocol(SecurityParameters securityParameters) {
        if (securityParameters == null || !securityParameters.isApplicationProtocolSet()) {
            return null;
        }
        ProtocolName applicationProtocol = securityParameters.getApplicationProtocol();
        return applicationProtocol == null ? BuildConfig.FLAVOR : applicationProtocol.getUtf8Decoding();
    }

    public static String getAuthTypeClient(short s2) {
        switch (s2) {
            case 1:
                return "RSA";
            case 2:
                return "DSA";
            case 3:
                return "EC";
            case 4:
            case 5:
            case 6:
                return "RSA";
            case 7:
                return EdDSAParameterSpec.Ed25519;
            case 8:
                return EdDSAParameterSpec.Ed448;
            case 9:
            case 10:
            case 11:
                return "RSASSA-PSS";
            default:
                switch (s2) {
                    case 26:
                    case 27:
                    case 28:
                        return "EC";
                    default:
                        throw new IllegalArgumentException();
                }
        }
    }

    public static String getAuthTypeServer(int i2) {
        if (i2 == 0) {
            return "UNKNOWN";
        }
        if (i2 == 1) {
            return "KE:RSA";
        }
        if (i2 == 3) {
            return "DHE_DSS";
        }
        if (i2 == 5) {
            return "DHE_RSA";
        }
        if (i2 == 7) {
            return "DH_DSS";
        }
        if (i2 == 9) {
            return "DH_RSA";
        }
        if (i2 == 22) {
            return "SRP_DSS";
        }
        if (i2 == 23) {
            return "SRP_RSA";
        }
        switch (i2) {
            case 16:
                return "ECDH_ECDSA";
            case 17:
                return "ECDHE_ECDSA";
            case 18:
                return "ECDH_RSA";
            case 19:
                return "ECDHE_RSA";
            default:
                throw new IllegalArgumentException();
        }
    }

    public static Vector<X500Name> getCertificateAuthorities(BCX509ExtendedTrustManager bCX509ExtendedTrustManager) {
        HashSet hashSet = new HashSet();
        for (X509Certificate x509Certificate : bCX509ExtendedTrustManager.getAcceptedIssuers()) {
            hashSet.add(x509Certificate.getBasicConstraints() >= 0 ? x509Certificate.getSubjectX500Principal() : x509Certificate.getIssuerX500Principal());
        }
        if (hashSet.isEmpty()) {
            return null;
        }
        Vector<X500Name> vector = new Vector<>(hashSet.size());
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            vector.add(X500Name.getInstance(((X500Principal) it.next()).getEncoded()));
        }
        return vector;
    }

    public static Certificate getCertificateMessage(JcaTlsCrypto jcaTlsCrypto, X509Certificate[] x509CertificateArr) {
        if (TlsUtils.isNullOrEmpty(x509CertificateArr)) {
            throw new IllegalArgumentException();
        }
        TlsCertificate[] tlsCertificateArr = new TlsCertificate[x509CertificateArr.length];
        for (int i2 = 0; i2 < x509CertificateArr.length; i2++) {
            tlsCertificateArr[i2] = new JcaTlsCertificate(jcaTlsCrypto, x509CertificateArr[i2]);
        }
        return new Certificate(tlsCertificateArr);
    }

    public static Certificate getCertificateMessage13(JcaTlsCrypto jcaTlsCrypto, X509Certificate[] x509CertificateArr, byte[] bArr) {
        if (TlsUtils.isNullOrEmpty(x509CertificateArr)) {
            throw new IllegalArgumentException();
        }
        CertificateEntry[] certificateEntryArr = new CertificateEntry[x509CertificateArr.length];
        for (int i2 = 0; i2 < x509CertificateArr.length; i2++) {
            certificateEntryArr[i2] = new CertificateEntry(new JcaTlsCertificate(jcaTlsCrypto, x509CertificateArr[i2]), null);
        }
        return new Certificate(bArr, certificateEntryArr);
    }

    public static X509Certificate getEndEntity(JcaTlsCrypto jcaTlsCrypto, Certificate certificate) {
        if (certificate == null || certificate.isEmpty()) {
            return null;
        }
        return getX509Certificate(jcaTlsCrypto, certificate.getCertificateAt(0));
    }

    public static String getJcaSignatureAlgorithmBC(String str, String str2) {
        if (!str.endsWith("withRSAandMGF1")) {
            return str;
        }
        return str + ":" + str2;
    }

    public static String getKeyAlgorithm(Key key) {
        return key instanceof PrivateKey ? getPrivateKeyAlgorithm((PrivateKey) key) : key instanceof PublicKey ? getPublicKeyAlgorithm((PublicKey) key) : key.getAlgorithm();
    }

    public static String getKeyType13(String str, int i2) {
        if (i2 < 0) {
            return str;
        }
        StringBuilder m22r = AbstractC0000a.m22r(str, "/");
        m22r.append(NamedGroup.getStandardName(i2));
        return m22r.toString();
    }

    public static String getKeyTypeLegacyClient(short s2) {
        if (s2 == 1) {
            return "RSA";
        }
        if (s2 == 2) {
            return "DSA";
        }
        if (s2 == 64) {
            return "EC";
        }
        throw new IllegalArgumentException();
    }

    public static String getKeyTypeLegacyServer(int i2) {
        return getAuthTypeServer(i2);
    }

    public static int getMaxCertificateChainLength() {
        return provTlsMaxCertificateChainLength;
    }

    public static int getMaxHandshakeMessageSize() {
        return provTlsMaxHandshakeMessageSize;
    }

    public static ASN1ObjectIdentifier getNamedCurveOID(PublicKey publicKey) {
        ASN1Encodable parameters;
        try {
            AlgorithmIdentifier algorithm = SubjectPublicKeyInfo.getInstance(publicKey.getEncoded()).getAlgorithm();
            if (!X9ObjectIdentifiers.id_ecPublicKey.equals((ASN1Primitive) algorithm.getAlgorithm()) || (parameters = algorithm.getParameters()) == null) {
                return null;
            }
            ASN1Primitive aSN1Primitive = parameters.toASN1Primitive();
            if (aSN1Primitive instanceof ASN1ObjectIdentifier) {
                return (ASN1ObjectIdentifier) aSN1Primitive;
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    public static String getPrivateKeyAlgorithm(PrivateKey privateKey) {
        String algorithm = privateKey.getAlgorithm();
        if ("RSA".equalsIgnoreCase(algorithm)) {
            if (PKCSObjectIdentifiers.id_RSASSA_PSS.equals((ASN1Primitive) PrivateKeyInfo.getInstance(privateKey.getEncoded()).getPrivateKeyAlgorithm().getAlgorithm())) {
                return "RSASSA-PSS";
            }
        }
        return algorithm;
    }

    public static List<String> getProtocolNames(Vector<ProtocolName> vector) {
        if (vector == null || vector.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList(vector.size());
        Iterator<ProtocolName> it = vector.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getUtf8Decoding());
        }
        return arrayList;
    }

    public static String getPublicKeyAlgorithm(PublicKey publicKey) {
        String algorithm = publicKey.getAlgorithm();
        if ("RSA".equalsIgnoreCase(algorithm)) {
            if (PKCSObjectIdentifiers.id_RSASSA_PSS.equals((ASN1Primitive) SubjectPublicKeyInfo.getInstance(publicKey.getEncoded()).getAlgorithm().getAlgorithm())) {
                return "RSASSA-PSS";
            }
        }
        return algorithm;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x001d, code lost:
    
        if ((r1 instanceof org.bouncycastle.jsse.BCSNIHostName) == false) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0021, code lost:
    
        return (org.bouncycastle.jsse.BCSNIHostName) r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x002b, code lost:
    
        return new org.bouncycastle.jsse.BCSNIHostName(r1.getEncoded());
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static BCSNIHostName getSNIHostName(List<BCSNIServerName> list) {
        if (list != null) {
            Iterator<BCSNIServerName> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                BCSNIServerName next = it.next();
                if (next != null && next.getType() == 0) {
                    break;
                }
            }
        }
        return null;
    }

    public static String getSignatureAlgorithmsReport(String str, List<SignatureSchemeInfo> list) {
        String[] jcaSignatureAlgorithmsBC = SignatureSchemeInfo.getJcaSignatureAlgorithmsBC(list);
        StringBuilder sb = new StringBuilder(str);
        sb.append(':');
        for (String str2 : jcaSignatureAlgorithmsBC) {
            sb.append(' ');
            sb.append(str2);
        }
        return sb.toString();
    }

    public static byte[] getStatusResponse(OCSPResponse oCSPResponse) {
        return oCSPResponse == null ? TlsUtils.EMPTY_BYTES : oCSPResponse.getEncoded(ASN1Encoding.DER);
    }

    public static List<byte[]> getStatusResponses(CertificateStatus certificateStatus) {
        if (certificateStatus == null) {
            return null;
        }
        short statusType = certificateStatus.getStatusType();
        if (statusType == 1) {
            return Collections.singletonList(getStatusResponse(certificateStatus.getOCSPResponse()));
        }
        if (statusType != 2) {
            return null;
        }
        Vector oCSPResponseList = certificateStatus.getOCSPResponseList();
        int size = oCSPResponseList.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i2 = 0; i2 < size; i2++) {
            arrayList.add(getStatusResponse((OCSPResponse) oCSPResponseList.elementAt(i2)));
        }
        return Collections.unmodifiableList(arrayList);
    }

    public static X500Principal getSubject(JcaTlsCrypto jcaTlsCrypto, Certificate certificate) {
        if (certificate == null || certificate.isEmpty()) {
            return null;
        }
        try {
            return getX509Certificate(jcaTlsCrypto, certificate.getCertificateAt(0)).getSubjectX500Principal();
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }
    }

    public static X500Principal[] getTrustedIssuers(Vector<TrustedAuthority> vector) {
        if (vector == null || vector.isEmpty()) {
            return null;
        }
        int size = vector.size();
        X500Principal[] x500PrincipalArr = new X500Principal[size];
        for (int i2 = 0; i2 < size; i2++) {
            TrustedAuthority trustedAuthority = vector.get(i2);
            if (2 != trustedAuthority.getIdentifierType()) {
                return null;
            }
            x500PrincipalArr[i2] = toX500Principal(trustedAuthority.getX509Name());
        }
        return x500PrincipalArr;
    }

    public static X509Certificate getX509Certificate(JcaTlsCrypto jcaTlsCrypto, TlsCertificate tlsCertificate) {
        return JcaTlsCertificate.convert(jcaTlsCrypto, tlsCertificate).getX509Certificate();
    }

    public static X509Certificate[] getX509CertificateChain(JcaTlsCrypto jcaTlsCrypto, Certificate certificate) {
        if (certificate == null || certificate.isEmpty()) {
            return EMPTY_X509CERTIFICATES;
        }
        try {
            int length = certificate.getLength();
            X509Certificate[] x509CertificateArr = new X509Certificate[length];
            for (int i2 = 0; i2 < length; i2++) {
                x509CertificateArr[i2] = JcaTlsCertificate.convert(jcaTlsCrypto, certificate.getCertificateAt(i2)).getX509Certificate();
            }
            return x509CertificateArr;
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }
    }

    public static boolean isNameSpecified(String str) {
        return !isNullOrEmpty(str);
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.length() < 1;
    }

    public static boolean isTLSv12(String str) {
        ProtocolVersion protocolVersion = ProvSSLContextSpi.getProtocolVersion(str);
        return protocolVersion != null && TlsUtils.isTLSv12(protocolVersion);
    }

    public static boolean isTLSv13(String str) {
        ProtocolVersion protocolVersion = ProvSSLContextSpi.getProtocolVersion(str);
        return protocolVersion != null && TlsUtils.isTLSv13(protocolVersion);
    }

    public static String removeAllWhitespace(String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }
        int length = str.length();
        char[] cArr = new char[length];
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            char charAt = str.charAt(i3);
            if (!Character.isWhitespace(charAt)) {
                cArr[i2] = charAt;
                i2++;
            }
        }
        return i2 == 0 ? EMPTY_STRING : i2 == length ? str : new String(cArr, 0, i2);
    }

    public static boolean requireCloseNotify() {
        return provTlsRequireCloseNotify;
    }

    public static String[] resize(String[] strArr, int i2) {
        return i2 < strArr.length ? copyOf(strArr, i2) : strArr;
    }

    public static String stripDoubleQuotes(String str) {
        return stripOuterChars(str, '\"', '\"');
    }

    private static String stripOuterChars(String str, char c, char c2) {
        int length;
        return (str == null || (length = str.length() - 1) <= 0 || str.charAt(0) != c || str.charAt(length) != c2) ? str : str.substring(1, length);
    }

    public static String stripSquareBrackets(String str) {
        return stripOuterChars(str, '[', ']');
    }

    public static X500Principal toX500Principal(X500Name x500Name) {
        if (x500Name == null) {
            return null;
        }
        return new X500Principal(x500Name.getEncoded(ASN1Encoding.DER));
    }

    public static X500Principal[] toX500Principals(Vector<X500Name> vector) {
        if (vector == null) {
            return null;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        int size = vector.size();
        for (int i2 = 0; i2 < size; i2++) {
            X500Principal x500Principal = toX500Principal(vector.get(i2));
            if (x500Principal != null) {
                linkedHashSet.add(x500Principal);
            }
        }
        return (X500Principal[]) linkedHashSet.toArray(new X500Principal[0]);
    }

    public static boolean useExtendedMasterSecret() {
        return provTlsUseExtendedMasterSecret;
    }

    public static Vector<ProtocolName> getProtocolNames(String[] strArr) {
        if (TlsUtils.isNullOrEmpty(strArr)) {
            return null;
        }
        Vector<ProtocolName> vector = new Vector<>(strArr.length);
        for (String str : strArr) {
            vector.add(ProtocolName.asUtf8Encoding(str));
        }
        return vector;
    }

    public static X509Certificate[] getX509CertificateChain(java.security.cert.Certificate[] certificateArr) {
        if (certificateArr == null) {
            return null;
        }
        if (certificateArr instanceof X509Certificate[]) {
            if (containsNull(certificateArr)) {
                return null;
            }
            return (X509Certificate[]) certificateArr;
        }
        X509Certificate[] x509CertificateArr = new X509Certificate[certificateArr.length];
        for (int i2 = 0; i2 < certificateArr.length; i2++) {
            java.security.cert.Certificate certificate = certificateArr[i2];
            if (!(certificate instanceof X509Certificate)) {
                return null;
            }
            x509CertificateArr[i2] = (X509Certificate) certificate;
        }
        return x509CertificateArr;
    }
}
