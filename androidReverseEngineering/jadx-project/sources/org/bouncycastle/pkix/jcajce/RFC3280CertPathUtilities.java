package org.bouncycastle.pkix.jcajce;

import java.io.IOException;
import java.security.PublicKey;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathValidatorException;
import java.security.cert.Certificate;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLSelector;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.security.cert.X509Extension;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.CRLDistPoint;
import org.bouncycastle.asn1.x509.DistributionPoint;
import org.bouncycastle.asn1.x509.DistributionPointName;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.IssuingDistributionPoint;
import org.bouncycastle.jcajce.PKIXCRLStoreSelector;
import org.bouncycastle.jcajce.PKIXCertStoreSelector;
import org.bouncycastle.jcajce.PKIXExtendedBuilderParameters;
import org.bouncycastle.jcajce.PKIXExtendedParameters;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.util.Arrays;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
class RFC3280CertPathUtilities {
    protected static final int CRL_SIGN = 6;
    protected static final int KEY_CERT_SIGN = 5;
    public static final String ISSUING_DISTRIBUTION_POINT = Extension.issuingDistributionPoint.getId();
    public static final String FRESHEST_CRL = Extension.freshestCRL.getId();
    public static final String DELTA_CRL_INDICATOR = Extension.deltaCRLIndicator.getId();
    public static final String BASIC_CONSTRAINTS = Extension.basicConstraints.getId();
    public static final String AUTHORITY_KEY_IDENTIFIER = Extension.authorityKeyIdentifier.getId();

    public static void checkCRL(DistributionPoint distributionPoint, PKIXExtendedParameters pKIXExtendedParameters, Date date, Date date2, X509Certificate x509Certificate, X509Certificate x509Certificate2, PublicKey publicKey, CertStatus certStatus, ReasonsMask reasonsMask, List list, JcaJceHelper jcaJceHelper) {
        Iterator it;
        Set<String> criticalExtensionOIDs;
        if (date2.getTime() > date.getTime()) {
            throw new AnnotatedException("Validation time is in future.");
        }
        Iterator it2 = RevocationUtilities.getCompleteCRLs(distributionPoint, x509Certificate, date2, pKIXExtendedParameters.getCertStores(), pKIXExtendedParameters.getCRLStores()).iterator();
        boolean z2 = false;
        AnnotatedException e2 = null;
        while (it2.hasNext() && certStatus.getCertStatus() == 11 && !reasonsMask.isAllReasons()) {
            try {
                X509CRL x509crl = (X509CRL) it2.next();
                ReasonsMask processCRLD = processCRLD(x509crl, distributionPoint);
                if (processCRLD.hasNewReasons(reasonsMask)) {
                    it = it2;
                    AnnotatedException annotatedException = e2;
                    try {
                        X509CRL processCRLH = pKIXExtendedParameters.isUseDeltasEnabled() ? processCRLH(RevocationUtilities.getDeltaCRLs(date2, x509crl, pKIXExtendedParameters.getCertStores(), pKIXExtendedParameters.getCRLStores()), processCRLG(x509crl, processCRLF(x509crl, x509Certificate, x509Certificate2, publicKey, pKIXExtendedParameters, list, jcaJceHelper))) : null;
                        if (pKIXExtendedParameters.getValidityModel() != 1 && x509Certificate.getNotAfter().getTime() < x509crl.getThisUpdate().getTime()) {
                            throw new AnnotatedException("No valid CRL for current time found.");
                        }
                        processCRLB1(distributionPoint, x509Certificate, x509crl);
                        processCRLB2(distributionPoint, x509Certificate, x509crl);
                        processCRLC(processCRLH, x509crl, pKIXExtendedParameters);
                        processCRLI(date2, processCRLH, x509Certificate, certStatus, pKIXExtendedParameters);
                        processCRLJ(date2, x509crl, x509Certificate, certStatus);
                        if (certStatus.getCertStatus() == 8) {
                            certStatus.setCertStatus(11);
                        }
                        reasonsMask.addReasons(processCRLD);
                        Set<String> criticalExtensionOIDs2 = x509crl.getCriticalExtensionOIDs();
                        if (criticalExtensionOIDs2 != null) {
                            HashSet hashSet = new HashSet(criticalExtensionOIDs2);
                            hashSet.remove(Extension.issuingDistributionPoint.getId());
                            hashSet.remove(Extension.deltaCRLIndicator.getId());
                            if (!hashSet.isEmpty()) {
                                throw new AnnotatedException("CRL contains unsupported critical extensions.");
                            }
                        }
                        if (processCRLH != null && (criticalExtensionOIDs = processCRLH.getCriticalExtensionOIDs()) != null) {
                            HashSet hashSet2 = new HashSet(criticalExtensionOIDs);
                            hashSet2.remove(Extension.issuingDistributionPoint.getId());
                            hashSet2.remove(Extension.deltaCRLIndicator.getId());
                            if (!hashSet2.isEmpty()) {
                                throw new AnnotatedException("Delta CRL contains unsupported critical extension.");
                            }
                        }
                        z2 = true;
                        it2 = it;
                        e2 = annotatedException;
                    } catch (AnnotatedException e3) {
                        e2 = e3;
                        it2 = it;
                    }
                } else {
                    continue;
                }
            } catch (AnnotatedException e4) {
                e2 = e4;
                it = it2;
            }
        }
        AnnotatedException annotatedException2 = e2;
        if (!z2) {
            throw annotatedException2;
        }
    }

    public static Set processCRLA1i(PKIXExtendedParameters pKIXExtendedParameters, Date date, X509Certificate x509Certificate, X509CRL x509crl) {
        HashSet hashSet = new HashSet();
        if (pKIXExtendedParameters.isUseDeltasEnabled()) {
            try {
                ASN1ObjectIdentifier aSN1ObjectIdentifier = Extension.freshestCRL;
                CRLDistPoint cRLDistPoint = CRLDistPoint.getInstance(RevocationUtilities.getExtensionValue(x509Certificate, aSN1ObjectIdentifier));
                if (cRLDistPoint == null) {
                    try {
                        cRLDistPoint = CRLDistPoint.getInstance(RevocationUtilities.getExtensionValue(x509crl, aSN1ObjectIdentifier));
                    } catch (AnnotatedException e2) {
                        throw new AnnotatedException("Freshest CRL extension could not be decoded from CRL.", e2);
                    }
                }
                if (cRLDistPoint != null) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.addAll(pKIXExtendedParameters.getCRLStores());
                    try {
                        arrayList.addAll(RevocationUtilities.getAdditionalStoresFromCRLDistributionPoint(cRLDistPoint, pKIXExtendedParameters.getNamedCRLStoreMap()));
                        try {
                            hashSet.addAll(RevocationUtilities.getDeltaCRLs(date, x509crl, pKIXExtendedParameters.getCertStores(), arrayList));
                        } catch (AnnotatedException e3) {
                            throw new AnnotatedException("Exception obtaining delta CRLs.", e3);
                        }
                    } catch (AnnotatedException e4) {
                        throw new AnnotatedException("No new delta CRL locations could be added from Freshest CRL extension.", e4);
                    }
                }
            } catch (AnnotatedException e5) {
                throw new AnnotatedException("Freshest CRL extension could not be decoded from certificate.", e5);
            }
        }
        return hashSet;
    }

    public static Set[] processCRLA1ii(PKIXExtendedParameters pKIXExtendedParameters, Date date, Date date2, X509Certificate x509Certificate, X509CRL x509crl) {
        X509CRLSelector x509CRLSelector = new X509CRLSelector();
        x509CRLSelector.setCertificateChecking(x509Certificate);
        try {
            x509CRLSelector.addIssuerName(x509crl.getIssuerX500Principal().getEncoded());
            Set findCRLs = PKIXCRLUtil.findCRLs(new PKIXCRLStoreSelector.Builder(x509CRLSelector).setCompleteCRLEnabled(true).build(), date2, pKIXExtendedParameters.getCertStores(), pKIXExtendedParameters.getCRLStores());
            HashSet hashSet = new HashSet();
            if (pKIXExtendedParameters.isUseDeltasEnabled()) {
                try {
                    hashSet.addAll(RevocationUtilities.getDeltaCRLs(date2, x509crl, pKIXExtendedParameters.getCertStores(), pKIXExtendedParameters.getCRLStores()));
                } catch (AnnotatedException e2) {
                    throw new AnnotatedException("Exception obtaining delta CRLs.", e2);
                }
            }
            return new Set[]{findCRLs, hashSet};
        } catch (IOException e3) {
            throw new AnnotatedException(AbstractC0000a.m13i("Cannot extract issuer from CRL.", e3), e3);
        }
    }

    public static void processCRLB1(DistributionPoint distributionPoint, Object obj, X509CRL x509crl) {
        ASN1Primitive extensionValue = RevocationUtilities.getExtensionValue(x509crl, Extension.issuingDistributionPoint);
        boolean z2 = true;
        boolean z3 = extensionValue != null && IssuingDistributionPoint.getInstance(extensionValue).isIndirectCRL();
        byte[] encoded = x509crl.getIssuerX500Principal().getEncoded();
        if (distributionPoint.getCRLIssuer() != null) {
            GeneralName[] names = distributionPoint.getCRLIssuer().getNames();
            boolean z4 = false;
            for (int i2 = 0; i2 < names.length; i2++) {
                if (names[i2].getTagNo() == 4) {
                    try {
                        if (Arrays.areEqual(names[i2].getName().toASN1Primitive().getEncoded(), encoded)) {
                            z4 = true;
                        }
                    } catch (IOException e2) {
                        throw new AnnotatedException("CRL issuer information from distribution point cannot be decoded.", e2);
                    }
                }
            }
            if (z4 && !z3) {
                throw new AnnotatedException("Distribution point contains cRLIssuer field but CRL is not indirect.");
            }
            if (!z4) {
                throw new AnnotatedException("CRL issuer of CRL does not match CRL issuer of distribution point.");
            }
            z2 = z4;
        } else if (!x509crl.getIssuerX500Principal().equals(((X509Certificate) obj).getIssuerX500Principal())) {
            z2 = false;
        }
        if (!z2) {
            throw new AnnotatedException("Cannot find matching CRL issuer for certificate.");
        }
    }

    public static void processCRLB2(DistributionPoint distributionPoint, Object obj, X509CRL x509crl) {
        GeneralName[] generalNameArr;
        try {
            IssuingDistributionPoint issuingDistributionPoint = IssuingDistributionPoint.getInstance(RevocationUtilities.getExtensionValue(x509crl, Extension.issuingDistributionPoint));
            if (issuingDistributionPoint != null) {
                if (issuingDistributionPoint.getDistributionPoint() != null) {
                    DistributionPointName distributionPoint2 = IssuingDistributionPoint.getInstance(issuingDistributionPoint).getDistributionPoint();
                    ArrayList arrayList = new ArrayList();
                    boolean z2 = false;
                    if (distributionPoint2.getType() == 0) {
                        for (GeneralName generalName : GeneralNames.getInstance(distributionPoint2.getName()).getNames()) {
                            arrayList.add(generalName);
                        }
                    }
                    if (distributionPoint2.getType() == 1) {
                        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
                        try {
                            Enumeration objects = ASN1Sequence.getInstance(x509crl.getIssuerX500Principal().getEncoded()).getObjects();
                            while (objects.hasMoreElements()) {
                                aSN1EncodableVector.add((ASN1Encodable) objects.nextElement());
                            }
                            aSN1EncodableVector.add(distributionPoint2.getName());
                            arrayList.add(new GeneralName(X500Name.getInstance(new DERSequence(aSN1EncodableVector))));
                        } catch (Exception e2) {
                            throw new AnnotatedException("Could not read CRL issuer.", e2);
                        }
                    }
                    if (distributionPoint.getDistributionPoint() != null) {
                        DistributionPointName distributionPoint3 = distributionPoint.getDistributionPoint();
                        GeneralName[] names = distributionPoint3.getType() == 0 ? GeneralNames.getInstance(distributionPoint3.getName()).getNames() : null;
                        if (distributionPoint3.getType() == 1) {
                            if (distributionPoint.getCRLIssuer() != null) {
                                generalNameArr = distributionPoint.getCRLIssuer().getNames();
                            } else {
                                generalNameArr = new GeneralName[1];
                                try {
                                    generalNameArr[0] = new GeneralName(X500Name.getInstance(((X509Certificate) obj).getIssuerX500Principal().getEncoded()));
                                } catch (Exception e3) {
                                    throw new AnnotatedException("Could not read certificate issuer.", e3);
                                }
                            }
                            names = generalNameArr;
                            for (int i2 = 0; i2 < names.length; i2++) {
                                Enumeration objects2 = ASN1Sequence.getInstance(names[i2].getName().toASN1Primitive()).getObjects();
                                ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
                                while (objects2.hasMoreElements()) {
                                    aSN1EncodableVector2.add((ASN1Encodable) objects2.nextElement());
                                }
                                aSN1EncodableVector2.add(distributionPoint3.getName());
                                names[i2] = new GeneralName(X500Name.getInstance(new DERSequence(aSN1EncodableVector2)));
                            }
                        }
                        if (names != null) {
                            int i3 = 0;
                            while (true) {
                                if (i3 >= names.length) {
                                    break;
                                }
                                if (arrayList.contains(names[i3])) {
                                    z2 = true;
                                    break;
                                }
                                i3++;
                            }
                        }
                        if (!z2) {
                            throw new AnnotatedException("No match for certificate CRL issuing distribution point name to cRLIssuer CRL distribution point.");
                        }
                    } else {
                        if (distributionPoint.getCRLIssuer() == null) {
                            throw new AnnotatedException("Either the cRLIssuer or the distributionPoint field must be contained in DistributionPoint.");
                        }
                        GeneralName[] names2 = distributionPoint.getCRLIssuer().getNames();
                        int i4 = 0;
                        while (true) {
                            if (i4 >= names2.length) {
                                break;
                            }
                            if (arrayList.contains(names2[i4])) {
                                z2 = true;
                                break;
                            }
                            i4++;
                        }
                        if (!z2) {
                            throw new AnnotatedException("No match for certificate CRL issuing distribution point name to cRLIssuer CRL distribution point.");
                        }
                    }
                }
                try {
                    BasicConstraints basicConstraints = BasicConstraints.getInstance(RevocationUtilities.getExtensionValue((X509Extension) obj, Extension.basicConstraints));
                    if (obj instanceof X509Certificate) {
                        if (issuingDistributionPoint.onlyContainsUserCerts() && basicConstraints != null && basicConstraints.isCA()) {
                            throw new AnnotatedException("CA Cert CRL only contains user certificates.");
                        }
                        if (issuingDistributionPoint.onlyContainsCACerts() && (basicConstraints == null || !basicConstraints.isCA())) {
                            throw new AnnotatedException("End CRL only contains CA certificates.");
                        }
                    }
                    if (issuingDistributionPoint.onlyContainsAttributeCerts()) {
                        throw new AnnotatedException("onlyContainsAttributeCerts boolean is asserted.");
                    }
                } catch (Exception e4) {
                    throw new AnnotatedException("Basic constraints extension could not be decoded.", e4);
                }
            }
        } catch (Exception e5) {
            throw new AnnotatedException("Issuing distribution point extension could not be decoded.", e5);
        }
    }

    public static void processCRLC(X509CRL x509crl, X509CRL x509crl2, PKIXExtendedParameters pKIXExtendedParameters) {
        if (x509crl == null) {
            return;
        }
        try {
            ASN1ObjectIdentifier aSN1ObjectIdentifier = Extension.issuingDistributionPoint;
            IssuingDistributionPoint issuingDistributionPoint = IssuingDistributionPoint.getInstance(RevocationUtilities.getExtensionValue(x509crl2, aSN1ObjectIdentifier));
            if (pKIXExtendedParameters.isUseDeltasEnabled()) {
                if (!x509crl.getIssuerX500Principal().equals(x509crl2.getIssuerX500Principal())) {
                    throw new AnnotatedException("complete CRL issuer does not match delta CRL issuer");
                }
                try {
                    IssuingDistributionPoint issuingDistributionPoint2 = IssuingDistributionPoint.getInstance(RevocationUtilities.getExtensionValue(x509crl, aSN1ObjectIdentifier));
                    boolean z2 = true;
                    if (issuingDistributionPoint != null ? !issuingDistributionPoint.equals(issuingDistributionPoint2) : issuingDistributionPoint2 != null) {
                        z2 = false;
                    }
                    if (!z2) {
                        throw new AnnotatedException("Issuing distribution point extension from delta CRL and complete CRL does not match.");
                    }
                    try {
                        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = Extension.authorityKeyIdentifier;
                        ASN1Primitive extensionValue = RevocationUtilities.getExtensionValue(x509crl2, aSN1ObjectIdentifier2);
                        try {
                            ASN1Primitive extensionValue2 = RevocationUtilities.getExtensionValue(x509crl, aSN1ObjectIdentifier2);
                            if (extensionValue == null) {
                                throw new AnnotatedException("CRL authority key identifier is null.");
                            }
                            if (extensionValue2 == null) {
                                throw new AnnotatedException("Delta CRL authority key identifier is null.");
                            }
                            if (!extensionValue.equals(extensionValue2)) {
                                throw new AnnotatedException("Delta CRL authority key identifier does not match complete CRL authority key identifier.");
                            }
                        } catch (AnnotatedException e2) {
                            throw new AnnotatedException("Authority key identifier extension could not be extracted from delta CRL.", e2);
                        }
                    } catch (AnnotatedException e3) {
                        throw new AnnotatedException("Authority key identifier extension could not be extracted from complete CRL.", e3);
                    }
                } catch (Exception e4) {
                    throw new AnnotatedException("Issuing distribution point extension from delta CRL could not be decoded.", e4);
                }
            }
        } catch (Exception e5) {
            throw new AnnotatedException("issuing distribution point extension could not be decoded.", e5);
        }
    }

    public static ReasonsMask processCRLD(X509CRL x509crl, DistributionPoint distributionPoint) {
        try {
            IssuingDistributionPoint issuingDistributionPoint = IssuingDistributionPoint.getInstance(RevocationUtilities.getExtensionValue(x509crl, Extension.issuingDistributionPoint));
            if (issuingDistributionPoint != null && issuingDistributionPoint.getOnlySomeReasons() != null && distributionPoint.getReasons() != null) {
                return new ReasonsMask(distributionPoint.getReasons()).intersect(new ReasonsMask(issuingDistributionPoint.getOnlySomeReasons()));
            }
            if ((issuingDistributionPoint == null || issuingDistributionPoint.getOnlySomeReasons() == null) && distributionPoint.getReasons() == null) {
                return ReasonsMask.allReasons;
            }
            return (distributionPoint.getReasons() == null ? ReasonsMask.allReasons : new ReasonsMask(distributionPoint.getReasons())).intersect(issuingDistributionPoint == null ? ReasonsMask.allReasons : new ReasonsMask(issuingDistributionPoint.getOnlySomeReasons()));
        } catch (Exception e2) {
            throw new AnnotatedException("Issuing distribution point extension could not be decoded.", e2);
        }
    }

    public static Set processCRLF(X509CRL x509crl, Object obj, X509Certificate x509Certificate, PublicKey publicKey, PKIXExtendedParameters pKIXExtendedParameters, List list, JcaJceHelper jcaJceHelper) {
        int i2;
        X509CertSelector x509CertSelector = new X509CertSelector();
        try {
            x509CertSelector.setSubject(x509crl.getIssuerX500Principal().getEncoded());
            PKIXCertStoreSelector<? extends Certificate> build = new PKIXCertStoreSelector.Builder(x509CertSelector).build();
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            try {
                RevocationUtilities.findCertificates(linkedHashSet, build, pKIXExtendedParameters.getCertificateStores());
                RevocationUtilities.findCertificates(linkedHashSet, build, pKIXExtendedParameters.getCertStores());
                linkedHashSet.add(x509Certificate);
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                Iterator it = linkedHashSet.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    X509Certificate x509Certificate2 = (X509Certificate) it.next();
                    if (x509Certificate2.equals(x509Certificate)) {
                        arrayList.add(x509Certificate2);
                        arrayList2.add(publicKey);
                    } else {
                        try {
                            CertPathBuilder createCertPathBuilder = jcaJceHelper.createCertPathBuilder("PKIX");
                            X509CertSelector x509CertSelector2 = new X509CertSelector();
                            x509CertSelector2.setCertificate(x509Certificate2);
                            PKIXExtendedParameters.Builder targetConstraints = new PKIXExtendedParameters.Builder(pKIXExtendedParameters).setTargetConstraints(new PKIXCertStoreSelector.Builder(x509CertSelector2).build());
                            if (list.contains(x509Certificate2)) {
                                targetConstraints.setRevocationEnabled(false);
                            } else {
                                targetConstraints.setRevocationEnabled(true);
                            }
                            List<? extends Certificate> certificates = createCertPathBuilder.build(new PKIXExtendedBuilderParameters.Builder(targetConstraints.build()).build()).getCertPath().getCertificates();
                            arrayList.add(x509Certificate2);
                            arrayList2.add(RevocationUtilities.getNextWorkingKey(certificates, 0, jcaJceHelper));
                        } catch (CertPathBuilderException e2) {
                            throw new AnnotatedException("CertPath for CRL signer failed to validate.", e2);
                        } catch (CertPathValidatorException e3) {
                            throw new AnnotatedException("Public key of issuer certificate of CRL could not be retrieved.", e3);
                        } catch (Exception e4) {
                            throw new AnnotatedException(e4.getMessage());
                        }
                    }
                }
                HashSet hashSet = new HashSet();
                AnnotatedException annotatedException = null;
                for (i2 = 0; i2 < arrayList.size(); i2++) {
                    boolean[] keyUsage = ((X509Certificate) arrayList.get(i2)).getKeyUsage();
                    if (keyUsage == null || (keyUsage.length > 6 && keyUsage[6])) {
                        hashSet.add(arrayList2.get(i2));
                    } else {
                        annotatedException = new AnnotatedException("Issuer certificate key usage extension does not permit CRL signing.");
                    }
                }
                if (hashSet.isEmpty() && annotatedException == null) {
                    throw new AnnotatedException("Cannot find a valid issuer certificate.");
                }
                if (!hashSet.isEmpty() || annotatedException == null) {
                    return hashSet;
                }
                throw annotatedException;
            } catch (AnnotatedException e5) {
                throw new AnnotatedException("Issuer certificate for CRL cannot be searched.", e5);
            }
        } catch (IOException e6) {
            throw new AnnotatedException("subject criteria for certificate selector to find issuer certificate for CRL could not be set", e6);
        }
    }

    public static PublicKey processCRLG(X509CRL x509crl, Set set) {
        Iterator it = set.iterator();
        Exception e2 = null;
        while (it.hasNext()) {
            PublicKey publicKey = (PublicKey) it.next();
            try {
                x509crl.verify(publicKey);
                return publicKey;
            } catch (Exception e3) {
                e2 = e3;
            }
        }
        throw new AnnotatedException("Cannot verify CRL.", e2);
    }

    public static X509CRL processCRLH(Set set, PublicKey publicKey) {
        Iterator it = set.iterator();
        Exception e2 = null;
        while (it.hasNext()) {
            X509CRL x509crl = (X509CRL) it.next();
            try {
                x509crl.verify(publicKey);
                return x509crl;
            } catch (Exception e3) {
                e2 = e3;
            }
        }
        if (e2 == null) {
            return null;
        }
        throw new AnnotatedException("Cannot verify delta CRL.", e2);
    }

    public static void processCRLI(Date date, X509CRL x509crl, Object obj, CertStatus certStatus, PKIXExtendedParameters pKIXExtendedParameters) {
        if (!pKIXExtendedParameters.isUseDeltasEnabled() || x509crl == null) {
            return;
        }
        RevocationUtilities.getCertStatus(date, x509crl, obj, certStatus);
    }

    public static void processCRLJ(Date date, X509CRL x509crl, Object obj, CertStatus certStatus) {
        if (certStatus.getCertStatus() == 11) {
            RevocationUtilities.getCertStatus(date, x509crl, obj, certStatus);
        }
    }
}
