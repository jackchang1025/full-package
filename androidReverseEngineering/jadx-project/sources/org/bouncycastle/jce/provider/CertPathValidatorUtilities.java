package org.bouncycastle.jce.provider;

import android.sun.security.x509.ReasonFlags;
import android.support.v4.os.EnvironmentCompat;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.security.PublicKey;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.PolicyQualifierInfo;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509CRLSelector;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.security.cert.X509Extension;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.DSAPublicKeySpec;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Enumerated;
import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1OutputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1String;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.RFC4519Style;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import org.bouncycastle.asn1.x509.CRLDistPoint;
import org.bouncycastle.asn1.x509.DistributionPoint;
import org.bouncycastle.asn1.x509.DistributionPointName;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.PolicyInformation;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.internal.asn1.isismtt.ISISMTTObjectIdentifiers;
import org.bouncycastle.jcajce.PKIXCRLStore;
import org.bouncycastle.jcajce.PKIXCRLStoreSelector;
import org.bouncycastle.jcajce.PKIXCertRevocationCheckerParameters;
import org.bouncycastle.jcajce.PKIXCertStore;
import org.bouncycastle.jcajce.PKIXCertStoreSelector;
import org.bouncycastle.jcajce.PKIXExtendedBuilderParameters;
import org.bouncycastle.jcajce.PKIXExtendedParameters;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.jce.exception.ExtCertPathBuilderException;
import org.bouncycastle.jce.exception.ExtCertPathValidatorException;
import org.bouncycastle.util.Properties;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.StoreException;
import org.bouncycastle.x509.X509AttributeCertificate;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
class CertPathValidatorUtilities {
    protected static final String ANY_POLICY = "2.5.29.32.0";
    protected static final int CRL_SIGN = 6;
    protected static final int KEY_CERT_SIGN = 5;
    protected static final String CERTIFICATE_POLICIES = Extension.certificatePolicies.getId();
    protected static final String BASIC_CONSTRAINTS = Extension.basicConstraints.getId();
    protected static final String POLICY_MAPPINGS = Extension.policyMappings.getId();
    protected static final String SUBJECT_ALTERNATIVE_NAME = Extension.subjectAlternativeName.getId();
    protected static final String NAME_CONSTRAINTS = Extension.nameConstraints.getId();
    protected static final String KEY_USAGE = Extension.keyUsage.getId();
    protected static final String INHIBIT_ANY_POLICY = Extension.inhibitAnyPolicy.getId();
    protected static final String ISSUING_DISTRIBUTION_POINT = Extension.issuingDistributionPoint.getId();
    protected static final String DELTA_CRL_INDICATOR = Extension.deltaCRLIndicator.getId();
    protected static final String POLICY_CONSTRAINTS = Extension.policyConstraints.getId();
    protected static final String FRESHEST_CRL = Extension.freshestCRL.getId();
    protected static final String CRL_DISTRIBUTION_POINTS = Extension.cRLDistributionPoints.getId();
    protected static final String AUTHORITY_KEY_IDENTIFIER = Extension.authorityKeyIdentifier.getId();
    protected static final String CRL_NUMBER = Extension.cRLNumber.getId();
    protected static final String[] crlReasons = {"unspecified", "keyCompromise", "cACompromise", "affiliationChanged", ReasonFlags.SUPERSEDED, "cessationOfOperation", "certificateHold", EnvironmentCompat.MEDIA_UNKNOWN, "removeFromCRL", "privilegeWithdrawn", "aACompromise"};

    public static void checkCRLsNotEmpty(PKIXCertRevocationCheckerParameters pKIXCertRevocationCheckerParameters, Set set, Object obj) {
        if (set.isEmpty()) {
            if (obj instanceof X509AttributeCertificate) {
                throw new RecoverableCertPathValidatorException("No CRLs found for issuer \"" + ((X509AttributeCertificate) obj).getIssuer().getPrincipals()[0] + "\"", null, pKIXCertRevocationCheckerParameters.getCertPath(), pKIXCertRevocationCheckerParameters.getIndex());
            }
            throw new RecoverableCertPathValidatorException("No CRLs found for issuer \"" + RFC4519Style.INSTANCE.toString(PrincipalUtils.getIssuerPrincipal((X509Certificate) obj)) + "\"", null, pKIXCertRevocationCheckerParameters.getCertPath(), pKIXCertRevocationCheckerParameters.getIndex());
        }
    }

    public static void findCertificates(LinkedHashSet linkedHashSet, PKIXCertStoreSelector pKIXCertStoreSelector, List list) {
        for (Object obj : list) {
            if (obj instanceof Store) {
                try {
                    linkedHashSet.addAll(((Store) obj).getMatches(pKIXCertStoreSelector));
                } catch (StoreException e2) {
                    throw new AnnotatedException("Problem while picking certificates from X.509 store.", e2);
                }
            } else {
                try {
                    linkedHashSet.addAll(PKIXCertStoreSelector.getCertificates(pKIXCertStoreSelector, (CertStore) obj));
                } catch (CertStoreException e3) {
                    throw new AnnotatedException("Problem while picking certificates from certificate store.", e3);
                }
            }
        }
    }

    public static Collection findIssuerCerts(X509Certificate x509Certificate, List<CertStore> list, List<PKIXCertStore> list2) {
        byte[] keyIdentifier;
        X509CertSelector x509CertSelector = new X509CertSelector();
        try {
            x509CertSelector.setSubject(PrincipalUtils.getIssuerPrincipal(x509Certificate).getEncoded());
            try {
                byte[] extensionValue = x509Certificate.getExtensionValue(AUTHORITY_KEY_IDENTIFIER);
                if (extensionValue != null && (keyIdentifier = AuthorityKeyIdentifier.getInstance(ASN1OctetString.getInstance(extensionValue).getOctets()).getKeyIdentifier()) != null) {
                    x509CertSelector.setSubjectKeyIdentifier(new DEROctetString(keyIdentifier).getEncoded());
                }
            } catch (Exception unused) {
            }
            PKIXCertStoreSelector<? extends Certificate> build = new PKIXCertStoreSelector.Builder(x509CertSelector).build();
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            try {
                findCertificates(linkedHashSet, build, list);
                findCertificates(linkedHashSet, build, list2);
                return linkedHashSet;
            } catch (AnnotatedException e2) {
                throw new AnnotatedException("Issuer certificate cannot be searched.", e2);
            }
        } catch (Exception e3) {
            throw new AnnotatedException("Subject criteria for certificate selector to find issuer certificate could not be set.", e3);
        }
    }

    public static Collection findTargets(PKIXExtendedBuilderParameters pKIXExtendedBuilderParameters) {
        PKIXExtendedParameters baseParameters = pKIXExtendedBuilderParameters.getBaseParameters();
        PKIXCertStoreSelector targetConstraints = baseParameters.getTargetConstraints();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        try {
            findCertificates(linkedHashSet, targetConstraints, baseParameters.getCertificateStores());
            findCertificates(linkedHashSet, targetConstraints, baseParameters.getCertStores());
            if (!linkedHashSet.isEmpty()) {
                return linkedHashSet;
            }
            Certificate certificate = targetConstraints.getCertificate();
            if (certificate != null) {
                return Collections.singleton(certificate);
            }
            throw new CertPathBuilderException("No certificate found matching targetConstraints.");
        } catch (AnnotatedException e2) {
            throw new ExtCertPathBuilderException("Error finding target certificate.", e2);
        }
    }

    public static TrustAnchor findTrustAnchor(X509Certificate x509Certificate, Set set) {
        return findTrustAnchor(x509Certificate, set, null);
    }

    public static List<PKIXCertStore> getAdditionalStoresFromAltNames(byte[] bArr, Map<GeneralName, PKIXCertStore> map) {
        if (bArr == null) {
            return Collections.EMPTY_LIST;
        }
        GeneralName[] names = GeneralNames.getInstance(ASN1OctetString.getInstance(bArr).getOctets()).getNames();
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 != names.length; i2++) {
            PKIXCertStore pKIXCertStore = map.get(names[i2]);
            if (pKIXCertStore != null) {
                arrayList.add(pKIXCertStore);
            }
        }
        return arrayList;
    }

    public static List<PKIXCRLStore> getAdditionalStoresFromCRLDistributionPoint(CRLDistPoint cRLDistPoint, Map<GeneralName, PKIXCRLStore> map, Date date, JcaJceHelper jcaJceHelper) {
        if (cRLDistPoint == null) {
            return Collections.EMPTY_LIST;
        }
        try {
            DistributionPoint[] distributionPoints = cRLDistPoint.getDistributionPoints();
            ArrayList arrayList = new ArrayList();
            for (DistributionPoint distributionPoint : distributionPoints) {
                DistributionPointName distributionPoint2 = distributionPoint.getDistributionPoint();
                if (distributionPoint2 != null && distributionPoint2.getType() == 0) {
                    for (GeneralName generalName : GeneralNames.getInstance(distributionPoint2.getName()).getNames()) {
                        PKIXCRLStore pKIXCRLStore = map.get(generalName);
                        if (pKIXCRLStore != null) {
                            arrayList.add(pKIXCRLStore);
                        }
                    }
                }
            }
            if (arrayList.isEmpty() && Properties.isOverrideSet("org.bouncycastle.x509.enableCRLDP")) {
                try {
                    CertificateFactory createCertificateFactory = jcaJceHelper.createCertificateFactory("X.509");
                    for (int i2 = 0; i2 < distributionPoints.length; i2++) {
                        DistributionPointName distributionPoint3 = distributionPoints[i2].getDistributionPoint();
                        if (distributionPoint3 != null && distributionPoint3.getType() == 0) {
                            GeneralName[] names = GeneralNames.getInstance(distributionPoint3.getName()).getNames();
                            int i3 = 0;
                            while (true) {
                                if (i3 < names.length) {
                                    GeneralName generalName2 = names[i2];
                                    if (generalName2.getTagNo() == 6) {
                                        try {
                                            PKIXCRLStore crl = CrlCache.getCrl(createCertificateFactory, date, new URI(((ASN1String) generalName2.getName()).getString()));
                                            if (crl != null) {
                                                arrayList.add(crl);
                                            }
                                        } catch (Exception unused) {
                                            continue;
                                        }
                                    }
                                    i3++;
                                }
                            }
                        }
                    }
                } catch (Exception e2) {
                    throw new AnnotatedException(AbstractC0000a.m9e(e2, new StringBuilder("cannot create certificate factory: ")), e2);
                }
            }
            return arrayList;
        } catch (Exception e3) {
            throw new AnnotatedException("Distribution points could not be read.", e3);
        }
    }

    public static AlgorithmIdentifier getAlgorithmIdentifier(PublicKey publicKey) {
        try {
            return SubjectPublicKeyInfo.getInstance(publicKey.getEncoded()).getAlgorithm();
        } catch (Exception e2) {
            throw new ExtCertPathValidatorException("Subject public key cannot be decoded.", e2);
        }
    }

    public static void getCRLIssuersFromDistributionPoint(DistributionPoint distributionPoint, Collection collection, X509CRLSelector x509CRLSelector) {
        ArrayList arrayList = new ArrayList();
        if (distributionPoint.getCRLIssuer() != null) {
            GeneralName[] names = distributionPoint.getCRLIssuer().getNames();
            for (int i2 = 0; i2 < names.length; i2++) {
                if (names[i2].getTagNo() == 4) {
                    try {
                        arrayList.add(X500Name.getInstance(names[i2].getName().toASN1Primitive().getEncoded()));
                    } catch (IOException e2) {
                        throw new AnnotatedException("CRL issuer information from distribution point cannot be decoded.", e2);
                    }
                }
            }
        } else {
            if (distributionPoint.getDistributionPoint() == null) {
                throw new AnnotatedException("CRL issuer is omitted from distribution point but no distributionPoint field present.");
            }
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next());
            }
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            try {
                x509CRLSelector.addIssuerName(((X500Name) it2.next()).getEncoded());
            } catch (IOException e3) {
                throw new AnnotatedException("Cannot decode CRL issuer information.", e3);
            }
        }
    }

    public static void getCertStatus(Date date, X509CRL x509crl, Object obj, CertStatus certStatus) {
        X509CRLEntry revokedCertificate;
        ASN1Enumerated aSN1Enumerated;
        try {
            if (X509CRLObject.isIndirectCRL(x509crl)) {
                revokedCertificate = x509crl.getRevokedCertificate(getSerialNumber(obj));
                if (revokedCertificate == null) {
                    return;
                }
                X500Principal certificateIssuer = revokedCertificate.getCertificateIssuer();
                if (!PrincipalUtils.getEncodedIssuerPrincipal(obj).equals(certificateIssuer == null ? PrincipalUtils.getIssuerPrincipal(x509crl) : PrincipalUtils.getX500Name(certificateIssuer))) {
                    return;
                }
            } else if (!PrincipalUtils.getEncodedIssuerPrincipal(obj).equals(PrincipalUtils.getIssuerPrincipal(x509crl)) || (revokedCertificate = x509crl.getRevokedCertificate(getSerialNumber(obj))) == null) {
                return;
            }
            if (!revokedCertificate.hasExtensions()) {
                aSN1Enumerated = null;
            } else {
                if (revokedCertificate.hasUnsupportedCriticalExtension()) {
                    throw new AnnotatedException("CRL entry has unsupported critical extensions.");
                }
                try {
                    aSN1Enumerated = ASN1Enumerated.getInstance(getExtensionValue(revokedCertificate, Extension.reasonCode.getId()));
                } catch (Exception e2) {
                    throw new AnnotatedException("Reason code CRL entry extension could not be decoded.", e2);
                }
            }
            int intValueExact = aSN1Enumerated == null ? 0 : aSN1Enumerated.intValueExact();
            if (date.getTime() >= revokedCertificate.getRevocationDate().getTime() || intValueExact == 0 || intValueExact == 1 || intValueExact == 2 || intValueExact == 10) {
                certStatus.setCertStatus(intValueExact);
                certStatus.setRevocationDate(revokedCertificate.getRevocationDate());
            }
        } catch (CRLException e3) {
            throw new AnnotatedException("Failed check for indirect CRL.", e3);
        }
    }

    public static Set getCompleteCRLs(PKIXCertRevocationCheckerParameters pKIXCertRevocationCheckerParameters, DistributionPoint distributionPoint, Object obj, PKIXExtendedParameters pKIXExtendedParameters, Date date) {
        X509CRLSelector x509CRLSelector = new X509CRLSelector();
        try {
            HashSet hashSet = new HashSet();
            hashSet.add(PrincipalUtils.getEncodedIssuerPrincipal(obj));
            getCRLIssuersFromDistributionPoint(distributionPoint, hashSet, x509CRLSelector);
            if (obj instanceof X509Certificate) {
                x509CRLSelector.setCertificateChecking((X509Certificate) obj);
            }
            Set findCRLs = PKIXCRLUtil.findCRLs(new PKIXCRLStoreSelector.Builder(x509CRLSelector).setCompleteCRLEnabled(true).build(), date, pKIXExtendedParameters.getCertStores(), pKIXExtendedParameters.getCRLStores());
            checkCRLsNotEmpty(pKIXCertRevocationCheckerParameters, findCRLs, obj);
            return findCRLs;
        } catch (AnnotatedException e2) {
            throw new AnnotatedException("Could not get issuer information from distribution point.", e2);
        }
    }

    public static Set getDeltaCRLs(Date date, X509CRL x509crl, List<CertStore> list, List<PKIXCRLStore> list2, JcaJceHelper jcaJceHelper) {
        X509CRLSelector x509CRLSelector = new X509CRLSelector();
        try {
            x509CRLSelector.addIssuerName(PrincipalUtils.getIssuerPrincipal(x509crl).getEncoded());
            try {
                ASN1Primitive extensionValue = getExtensionValue(x509crl, CRL_NUMBER);
                BigInteger positiveValue = extensionValue != null ? ASN1Integer.getInstance(extensionValue).getPositiveValue() : null;
                try {
                    byte[] extensionValue2 = x509crl.getExtensionValue(ISSUING_DISTRIBUTION_POINT);
                    x509CRLSelector.setMinCRLNumber(positiveValue != null ? positiveValue.add(BigInteger.valueOf(1L)) : null);
                    PKIXCRLStoreSelector.Builder builder = new PKIXCRLStoreSelector.Builder(x509CRLSelector);
                    builder.setIssuingDistributionPoint(extensionValue2);
                    builder.setIssuingDistributionPointEnabled(true);
                    builder.setMaxBaseCRLNumber(positiveValue);
                    PKIXCRLStoreSelector<? extends CRL> build = builder.build();
                    Set<X509CRL> findCRLs = PKIXCRLUtil.findCRLs(build, date, list, list2);
                    if (findCRLs.isEmpty() && Properties.isOverrideSet("org.bouncycastle.x509.enableCRLDP")) {
                        try {
                            CertificateFactory createCertificateFactory = jcaJceHelper.createCertificateFactory("X.509");
                            DistributionPoint[] distributionPoints = CRLDistPoint.getInstance(extensionValue2).getDistributionPoints();
                            for (int i2 = 0; i2 < distributionPoints.length; i2++) {
                                DistributionPointName distributionPoint = distributionPoints[i2].getDistributionPoint();
                                if (distributionPoint != null && distributionPoint.getType() == 0) {
                                    GeneralName[] names = GeneralNames.getInstance(distributionPoint.getName()).getNames();
                                    int i3 = 0;
                                    while (true) {
                                        if (i3 < names.length) {
                                            GeneralName generalName = names[i2];
                                            if (generalName.getTagNo() == 6) {
                                                try {
                                                    PKIXCRLStore crl = CrlCache.getCrl(createCertificateFactory, date, new URI(((ASN1String) generalName.getName()).getString()));
                                                    if (crl != null) {
                                                        findCRLs = PKIXCRLUtil.findCRLs(build, date, Collections.EMPTY_LIST, Collections.singletonList(crl));
                                                    }
                                                } catch (Exception unused) {
                                                    continue;
                                                }
                                            }
                                            i3++;
                                        }
                                    }
                                }
                            }
                        } catch (Exception e2) {
                            throw new AnnotatedException(AbstractC0000a.m9e(e2, new StringBuilder("cannot create certificate factory: ")), e2);
                        }
                    }
                    HashSet hashSet = new HashSet();
                    for (X509CRL x509crl2 : findCRLs) {
                        if (isDeltaCRL(x509crl2)) {
                            hashSet.add(x509crl2);
                        }
                    }
                    return hashSet;
                } catch (Exception e3) {
                    throw new AnnotatedException("Issuing distribution point extension value could not be read.", e3);
                }
            } catch (Exception e4) {
                throw new AnnotatedException("CRL number extension could not be extracted from CRL.", e4);
            }
        } catch (IOException e5) {
            throw new AnnotatedException("Cannot extract issuer from CRL.", e5);
        }
    }

    public static ASN1Primitive getExtensionValue(X509Extension x509Extension, String str) {
        byte[] extensionValue = x509Extension.getExtensionValue(str);
        if (extensionValue == null) {
            return null;
        }
        return getObject(str, extensionValue);
    }

    public static PublicKey getNextWorkingKey(List list, int i2, JcaJceHelper jcaJceHelper) {
        DSAPublicKey dSAPublicKey;
        PublicKey publicKey = ((Certificate) list.get(i2)).getPublicKey();
        if (!(publicKey instanceof DSAPublicKey)) {
            return publicKey;
        }
        DSAPublicKey dSAPublicKey2 = (DSAPublicKey) publicKey;
        if (dSAPublicKey2.getParams() != null) {
            return dSAPublicKey2;
        }
        do {
            i2++;
            if (i2 >= list.size()) {
                throw new CertPathValidatorException("DSA parameters cannot be inherited from previous certificate.");
            }
            PublicKey publicKey2 = ((X509Certificate) list.get(i2)).getPublicKey();
            if (!(publicKey2 instanceof DSAPublicKey)) {
                throw new CertPathValidatorException("DSA parameters cannot be inherited from previous certificate.");
            }
            dSAPublicKey = (DSAPublicKey) publicKey2;
        } while (dSAPublicKey.getParams() == null);
        DSAParams params = dSAPublicKey.getParams();
        try {
            return jcaJceHelper.createKeyFactory("DSA").generatePublic(new DSAPublicKeySpec(dSAPublicKey2.getY(), params.getP(), params.getQ(), params.getG()));
        } catch (Exception e2) {
            throw new RuntimeException(e2.getMessage());
        }
    }

    private static ASN1Primitive getObject(String str, byte[] bArr) {
        try {
            return ASN1Primitive.fromByteArray(ASN1OctetString.getInstance(bArr).getOctets());
        } catch (Exception e2) {
            throw new AnnotatedException(AbstractC0000a.m15k("exception processing extension ", str), e2);
        }
    }

    public static final Set getQualifierSet(ASN1Sequence aSN1Sequence) {
        HashSet hashSet = new HashSet();
        if (aSN1Sequence == null) {
            return hashSet;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ASN1OutputStream create = ASN1OutputStream.create(byteArrayOutputStream);
        Enumeration objects = aSN1Sequence.getObjects();
        while (objects.hasMoreElements()) {
            try {
                create.writeObject((ASN1Encodable) objects.nextElement());
                hashSet.add(new PolicyQualifierInfo(byteArrayOutputStream.toByteArray()));
                byteArrayOutputStream.reset();
            } catch (IOException e2) {
                throw new ExtCertPathValidatorException("Policy qualifier info cannot be decoded.", e2);
            }
        }
        return hashSet;
    }

    private static BigInteger getSerialNumber(Object obj) {
        return ((X509Certificate) obj).getSerialNumber();
    }

    public static Date getValidCertDateFromValidityModel(Date date, int i2, CertPath certPath, int i3) {
        if (1 != i2 || i3 <= 0) {
            return date;
        }
        int i4 = i3 - 1;
        X509Certificate x509Certificate = (X509Certificate) certPath.getCertificates().get(i4);
        if (i4 == 0) {
            try {
                byte[] extensionValue = ((X509Certificate) certPath.getCertificates().get(i4)).getExtensionValue(ISISMTTObjectIdentifiers.id_isismtt_at_dateOfCertGen.getId());
                ASN1GeneralizedTime aSN1GeneralizedTime = extensionValue != null ? ASN1GeneralizedTime.getInstance(ASN1Primitive.fromByteArray(extensionValue)) : null;
                if (aSN1GeneralizedTime != null) {
                    try {
                        return aSN1GeneralizedTime.getDate();
                    } catch (ParseException e2) {
                        throw new AnnotatedException("Date from date of cert gen extension could not be parsed.", e2);
                    }
                }
            } catch (IOException unused) {
                throw new AnnotatedException("Date of cert gen extension could not be read.");
            } catch (IllegalArgumentException unused2) {
                throw new AnnotatedException("Date of cert gen extension could not be read.");
            }
        }
        return x509Certificate.getNotBefore();
    }

    public static Date getValidityDate(PKIXExtendedParameters pKIXExtendedParameters, Date date) {
        Date validityDate = pKIXExtendedParameters.getValidityDate();
        return validityDate == null ? date : validityDate;
    }

    public static boolean isAnyPolicy(Set set) {
        return set == null || set.contains("2.5.29.32.0") || set.isEmpty();
    }

    private static boolean isDeltaCRL(X509CRL x509crl) {
        Set<String> criticalExtensionOIDs = x509crl.getCriticalExtensionOIDs();
        if (criticalExtensionOIDs == null) {
            return false;
        }
        return criticalExtensionOIDs.contains(RFC3280CertPathUtilities.DELTA_CRL_INDICATOR);
    }

    public static boolean isIssuerTrustAnchor(X509Certificate x509Certificate, Set set, String str) {
        try {
            return findTrustAnchor(x509Certificate, set, str) != null;
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean isSelfIssued(X509Certificate x509Certificate) {
        return x509Certificate.getSubjectDN().equals(x509Certificate.getIssuerDN());
    }

    public static void prepareNextCertB1(int i2, List[] listArr, String str, Map map, X509Certificate x509Certificate) {
        boolean z2;
        Set set;
        Iterator it = listArr[i2].iterator();
        while (true) {
            if (!it.hasNext()) {
                z2 = false;
                break;
            }
            PKIXPolicyNode pKIXPolicyNode = (PKIXPolicyNode) it.next();
            if (pKIXPolicyNode.getValidPolicy().equals(str)) {
                pKIXPolicyNode.expectedPolicies = (Set) map.get(str);
                z2 = true;
                break;
            }
        }
        if (z2) {
            return;
        }
        for (PKIXPolicyNode pKIXPolicyNode2 : listArr[i2]) {
            if ("2.5.29.32.0".equals(pKIXPolicyNode2.getValidPolicy())) {
                try {
                    Enumeration objects = ASN1Sequence.getInstance(getExtensionValue(x509Certificate, CERTIFICATE_POLICIES)).getObjects();
                    while (true) {
                        if (!objects.hasMoreElements()) {
                            set = null;
                            break;
                        }
                        try {
                            PolicyInformation policyInformation = PolicyInformation.getInstance(objects.nextElement());
                            if ("2.5.29.32.0".equals(policyInformation.getPolicyIdentifier().getId())) {
                                try {
                                    set = getQualifierSet(policyInformation.getPolicyQualifiers());
                                    break;
                                } catch (CertPathValidatorException e2) {
                                    throw new ExtCertPathValidatorException("Policy qualifier info set could not be built.", e2);
                                }
                            }
                        } catch (Exception e3) {
                            throw new AnnotatedException("Policy information cannot be decoded.", e3);
                        }
                    }
                    Set set2 = set;
                    boolean contains = x509Certificate.getCriticalExtensionOIDs() != null ? x509Certificate.getCriticalExtensionOIDs().contains(CERTIFICATE_POLICIES) : false;
                    PKIXPolicyNode pKIXPolicyNode3 = (PKIXPolicyNode) pKIXPolicyNode2.getParent();
                    if ("2.5.29.32.0".equals(pKIXPolicyNode3.getValidPolicy())) {
                        PKIXPolicyNode pKIXPolicyNode4 = new PKIXPolicyNode(new ArrayList(), i2, (Set) map.get(str), pKIXPolicyNode3, set2, str, contains);
                        pKIXPolicyNode3.addChild(pKIXPolicyNode4);
                        listArr[i2].add(pKIXPolicyNode4);
                        return;
                    }
                    return;
                } catch (Exception e4) {
                    throw new AnnotatedException("Certificate policies cannot be decoded.", e4);
                }
            }
        }
    }

    public static PKIXPolicyNode prepareNextCertB2(int i2, List[] listArr, String str, PKIXPolicyNode pKIXPolicyNode) {
        int i3;
        Iterator it = listArr[i2].iterator();
        while (it.hasNext()) {
            PKIXPolicyNode pKIXPolicyNode2 = (PKIXPolicyNode) it.next();
            if (pKIXPolicyNode2.getValidPolicy().equals(str)) {
                ((PKIXPolicyNode) pKIXPolicyNode2.getParent()).removeChild(pKIXPolicyNode2);
                it.remove();
                for (int i4 = i2 - 1; i4 >= 0; i4--) {
                    List list = listArr[i4];
                    while (i3 < list.size()) {
                        PKIXPolicyNode pKIXPolicyNode3 = (PKIXPolicyNode) list.get(i3);
                        i3 = (pKIXPolicyNode3.hasChildren() || (pKIXPolicyNode = removePolicyNode(pKIXPolicyNode, listArr, pKIXPolicyNode3)) != null) ? i3 + 1 : 0;
                    }
                }
            }
        }
        return pKIXPolicyNode;
    }

    public static boolean processCertD1i(int i2, List[] listArr, ASN1ObjectIdentifier aSN1ObjectIdentifier, Set set) {
        List list = listArr[i2 - 1];
        for (int i3 = 0; i3 < list.size(); i3++) {
            PKIXPolicyNode pKIXPolicyNode = (PKIXPolicyNode) list.get(i3);
            if (pKIXPolicyNode.getExpectedPolicies().contains(aSN1ObjectIdentifier.getId())) {
                HashSet hashSet = new HashSet();
                hashSet.add(aSN1ObjectIdentifier.getId());
                PKIXPolicyNode pKIXPolicyNode2 = new PKIXPolicyNode(new ArrayList(), i2, hashSet, pKIXPolicyNode, set, aSN1ObjectIdentifier.getId(), false);
                pKIXPolicyNode.addChild(pKIXPolicyNode2);
                listArr[i2].add(pKIXPolicyNode2);
                return true;
            }
        }
        return false;
    }

    public static void processCertD1ii(int i2, List[] listArr, ASN1ObjectIdentifier aSN1ObjectIdentifier, Set set) {
        List list = listArr[i2 - 1];
        for (int i3 = 0; i3 < list.size(); i3++) {
            PKIXPolicyNode pKIXPolicyNode = (PKIXPolicyNode) list.get(i3);
            if ("2.5.29.32.0".equals(pKIXPolicyNode.getValidPolicy())) {
                HashSet hashSet = new HashSet();
                hashSet.add(aSN1ObjectIdentifier.getId());
                PKIXPolicyNode pKIXPolicyNode2 = new PKIXPolicyNode(new ArrayList(), i2, hashSet, pKIXPolicyNode, set, aSN1ObjectIdentifier.getId(), false);
                pKIXPolicyNode.addChild(pKIXPolicyNode2);
                listArr[i2].add(pKIXPolicyNode2);
                return;
            }
        }
    }

    public static PKIXPolicyNode removePolicyNode(PKIXPolicyNode pKIXPolicyNode, List[] listArr, PKIXPolicyNode pKIXPolicyNode2) {
        PKIXPolicyNode pKIXPolicyNode3 = (PKIXPolicyNode) pKIXPolicyNode2.getParent();
        if (pKIXPolicyNode == null) {
            return null;
        }
        if (pKIXPolicyNode3 != null) {
            pKIXPolicyNode3.removeChild(pKIXPolicyNode2);
            removePolicyNodeRecurse(listArr, pKIXPolicyNode2);
            return pKIXPolicyNode;
        }
        for (int i2 = 0; i2 < listArr.length; i2++) {
            listArr[i2] = new ArrayList();
        }
        return null;
    }

    private static void removePolicyNodeRecurse(List[] listArr, PKIXPolicyNode pKIXPolicyNode) {
        listArr[pKIXPolicyNode.getDepth()].remove(pKIXPolicyNode);
        if (pKIXPolicyNode.hasChildren()) {
            Iterator children = pKIXPolicyNode.getChildren();
            while (children.hasNext()) {
                removePolicyNodeRecurse(listArr, (PKIXPolicyNode) children.next());
            }
        }
    }

    public static void verifyX509Certificate(X509Certificate x509Certificate, PublicKey publicKey, String str) {
        if (str == null) {
            x509Certificate.verify(publicKey);
        } else {
            x509Certificate.verify(publicKey, str);
        }
    }

    public static TrustAnchor findTrustAnchor(X509Certificate x509Certificate, Set set, String str) {
        X509CertSelector x509CertSelector = new X509CertSelector();
        X500Principal issuerX500Principal = x509Certificate.getIssuerX500Principal();
        x509CertSelector.setSubject(issuerX500Principal);
        Iterator it = set.iterator();
        TrustAnchor trustAnchor = null;
        Exception e2 = null;
        X500Name x500Name = null;
        PublicKey publicKey = null;
        while (it.hasNext() && trustAnchor == null) {
            trustAnchor = (TrustAnchor) it.next();
            if (trustAnchor.getTrustedCert() != null) {
                if (x509CertSelector.match(trustAnchor.getTrustedCert())) {
                    publicKey = trustAnchor.getTrustedCert().getPublicKey();
                }
                trustAnchor = null;
            } else {
                if (trustAnchor.getCA() != null && trustAnchor.getCAName() != null && trustAnchor.getCAPublicKey() != null) {
                    if (x500Name == null) {
                        x500Name = X500Name.getInstance(issuerX500Principal.getEncoded());
                    }
                    try {
                        if (x500Name.equals(X500Name.getInstance(trustAnchor.getCA().getEncoded()))) {
                            publicKey = trustAnchor.getCAPublicKey();
                        }
                    } catch (IllegalArgumentException unused) {
                    }
                }
                trustAnchor = null;
            }
            if (publicKey != null) {
                try {
                    verifyX509Certificate(x509Certificate, publicKey, str);
                } catch (Exception e3) {
                    e2 = e3;
                    trustAnchor = null;
                    publicKey = null;
                }
            }
        }
        if (trustAnchor != null || e2 == null) {
            return trustAnchor;
        }
        throw new AnnotatedException("TrustAnchor found but certificate validation failed.", e2);
    }
}
