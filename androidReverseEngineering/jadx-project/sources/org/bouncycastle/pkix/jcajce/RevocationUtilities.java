package org.bouncycastle.pkix.jcajce;

import java.io.IOException;
import java.math.BigInteger;
import java.security.PublicKey;
import java.security.cert.CRLException;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.Certificate;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509CRLSelector;
import java.security.cert.X509Certificate;
import java.security.cert.X509Extension;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.DSAPublicKeySpec;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1Enumerated;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.RFC4519Style;
import org.bouncycastle.asn1.x509.CRLDistPoint;
import org.bouncycastle.asn1.x509.DistributionPoint;
import org.bouncycastle.asn1.x509.DistributionPointName;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.IssuingDistributionPoint;
import org.bouncycastle.jcajce.PKIXCRLStore;
import org.bouncycastle.jcajce.PKIXCRLStoreSelector;
import org.bouncycastle.jcajce.PKIXCertStoreSelector;
import org.bouncycastle.jcajce.PKIXExtendedParameters;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.StoreException;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
class RevocationUtilities {
    protected static final String ISSUING_DISTRIBUTION_POINT = Extension.issuingDistributionPoint.getId();

    public static void checkCRLsNotEmpty(Set set, Object obj) {
        if (set.isEmpty()) {
            throw new CRLNotFoundException("No CRLs found for issuer \"" + RFC4519Style.INSTANCE.toString(getIssuer((X509Certificate) obj)) + "\"");
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

    public static List<PKIXCRLStore> getAdditionalStoresFromCRLDistributionPoint(CRLDistPoint cRLDistPoint, Map<GeneralName, PKIXCRLStore> map) {
        if (cRLDistPoint == null) {
            return Collections.emptyList();
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
            return arrayList;
        } catch (Exception e2) {
            throw new AnnotatedException("Distribution points could not be read.", e2);
        }
    }

    public static void getCRLIssuersFromDistributionPoint(DistributionPoint distributionPoint, Collection collection, X509CRLSelector x509CRLSelector) {
        ArrayList arrayList = new ArrayList();
        if (distributionPoint.getCRLIssuer() != null) {
            GeneralName[] names = distributionPoint.getCRLIssuer().getNames();
            for (int i2 = 0; i2 < names.length; i2++) {
                if (names[i2].getTagNo() == 4) {
                    try {
                        arrayList.add(X500Name.getInstance(names[i2].getName()));
                    } catch (IllegalArgumentException e2) {
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
        try {
            boolean isIndirectCRL = isIndirectCRL(x509crl);
            X509Certificate x509Certificate = (X509Certificate) obj;
            X500Name issuer = getIssuer(x509Certificate);
            if ((isIndirectCRL || issuer.equals(getIssuer(x509crl))) && (revokedCertificate = x509crl.getRevokedCertificate(x509Certificate.getSerialNumber())) != null) {
                if (isIndirectCRL) {
                    X500Principal certificateIssuer = revokedCertificate.getCertificateIssuer();
                    if (!issuer.equals(certificateIssuer == null ? getIssuer(x509crl) : getX500Name(certificateIssuer))) {
                        return;
                    }
                }
                int i2 = 0;
                if (revokedCertificate.hasExtensions()) {
                    try {
                        ASN1Enumerated aSN1Enumerated = ASN1Enumerated.getInstance(getExtensionValue(revokedCertificate, Extension.reasonCode));
                        if (aSN1Enumerated != null) {
                            i2 = aSN1Enumerated.intValueExact();
                        }
                    } catch (Exception e2) {
                        throw new AnnotatedException("Reason code CRL entry extension could not be decoded.", e2);
                    }
                }
                Date revocationDate = revokedCertificate.getRevocationDate();
                if (!date.before(revocationDate) || i2 == 0 || i2 == 1 || i2 == 2 || i2 == 10) {
                    certStatus.setCertStatus(i2);
                    certStatus.setRevocationDate(revocationDate);
                }
            }
        } catch (CRLException e3) {
            throw new AnnotatedException("Failed check for indirect CRL.", e3);
        }
    }

    public static Set getCompleteCRLs(DistributionPoint distributionPoint, Object obj, Date date, List list, List list2) {
        X509CRLSelector x509CRLSelector = new X509CRLSelector();
        try {
            HashSet hashSet = new HashSet();
            hashSet.add(getIssuer((X509Certificate) obj));
            getCRLIssuersFromDistributionPoint(distributionPoint, hashSet, x509CRLSelector);
            if (obj instanceof X509Certificate) {
                x509CRLSelector.setCertificateChecking((X509Certificate) obj);
            }
            Set findCRLs = PKIXCRLUtil.findCRLs(new PKIXCRLStoreSelector.Builder(x509CRLSelector).setCompleteCRLEnabled(true).build(), date, list, list2);
            checkCRLsNotEmpty(findCRLs, obj);
            return findCRLs;
        } catch (AnnotatedException e2) {
            throw new AnnotatedException("Could not get issuer information from distribution point.", e2);
        }
    }

    public static Set getDeltaCRLs(Date date, X509CRL x509crl, List<CertStore> list, List<PKIXCRLStore> list2) {
        X509CRLSelector x509CRLSelector = new X509CRLSelector();
        try {
            x509CRLSelector.addIssuerName(x509crl.getIssuerX500Principal().getEncoded());
            try {
                ASN1Primitive extensionValue = getExtensionValue(x509crl, Extension.cRLNumber);
                BigInteger positiveValue = extensionValue != null ? ASN1Integer.getInstance(extensionValue).getPositiveValue() : null;
                try {
                    byte[] extensionValue2 = x509crl.getExtensionValue(ISSUING_DISTRIBUTION_POINT);
                    x509CRLSelector.setMinCRLNumber(positiveValue != null ? positiveValue.add(BigInteger.valueOf(1L)) : null);
                    PKIXCRLStoreSelector.Builder builder = new PKIXCRLStoreSelector.Builder(x509CRLSelector);
                    builder.setIssuingDistributionPoint(extensionValue2);
                    builder.setIssuingDistributionPointEnabled(true);
                    builder.setMaxBaseCRLNumber(positiveValue);
                    Set<X509CRL> findCRLs = PKIXCRLUtil.findCRLs(builder.build(), date, list, list2);
                    HashSet hashSet = new HashSet();
                    for (X509CRL x509crl2 : findCRLs) {
                        if (isDeltaCRL(x509crl2)) {
                            hashSet.add(x509crl2);
                        }
                    }
                    return hashSet;
                } catch (Exception e2) {
                    throw new AnnotatedException("issuing distribution point extension value could not be read", e2);
                }
            } catch (Exception e3) {
                throw new AnnotatedException("cannot extract CRL number extension from CRL", e3);
            }
        } catch (IOException e4) {
            throw new AnnotatedException("cannot extract issuer from CRL.", e4);
        }
    }

    public static ASN1Primitive getExtensionValue(X509Extension x509Extension, ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        byte[] extensionValue = x509Extension.getExtensionValue(aSN1ObjectIdentifier.getId());
        if (extensionValue == null) {
            return null;
        }
        return getObject(aSN1ObjectIdentifier, extensionValue);
    }

    private static X500Name getIssuer(X509CRL x509crl) {
        return getX500Name(x509crl.getIssuerX500Principal());
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

    private static ASN1Primitive getObject(ASN1ObjectIdentifier aSN1ObjectIdentifier, byte[] bArr) {
        try {
            return ASN1Primitive.fromByteArray(ASN1OctetString.getInstance(bArr).getOctets());
        } catch (Exception e2) {
            throw new AnnotatedException(AbstractC0413b.m1009c("exception processing extension ", aSN1ObjectIdentifier), e2);
        }
    }

    public static Date getValidityDate(PKIXExtendedParameters pKIXExtendedParameters, Date date) {
        Date validityDate = pKIXExtendedParameters.getValidityDate();
        return validityDate == null ? date : validityDate;
    }

    private static X500Name getX500Name(X500Principal x500Principal) {
        return X500Name.getInstance(x500Principal.getEncoded());
    }

    private static boolean isDeltaCRL(X509CRL x509crl) {
        Set<String> criticalExtensionOIDs = x509crl.getCriticalExtensionOIDs();
        if (criticalExtensionOIDs == null) {
            return false;
        }
        return criticalExtensionOIDs.contains(RFC3280CertPathUtilities.DELTA_CRL_INDICATOR);
    }

    public static boolean isIndirectCRL(X509CRL x509crl) {
        try {
            byte[] extensionValue = x509crl.getExtensionValue(Extension.issuingDistributionPoint.getId());
            if (extensionValue != null) {
                if (IssuingDistributionPoint.getInstance(ASN1OctetString.getInstance(extensionValue).getOctets()).isIndirectCRL()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e2) {
            throw new CRLException("exception reading IssuingDistributionPoint", e2);
        }
    }

    private static X500Name getIssuer(X509Certificate x509Certificate) {
        return getX500Name(x509Certificate.getIssuerX500Principal());
    }
}
