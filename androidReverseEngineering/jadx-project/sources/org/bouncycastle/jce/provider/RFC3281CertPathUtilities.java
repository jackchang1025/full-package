package org.bouncycastle.jce.provider;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.x509.CRLDistPoint;
import org.bouncycastle.asn1.x509.DistributionPoint;
import org.bouncycastle.asn1.x509.DistributionPointName;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.TargetInformation;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.jcajce.PKIXCRLStore;
import org.bouncycastle.jcajce.PKIXCertRevocationCheckerParameters;
import org.bouncycastle.jcajce.PKIXCertStoreSelector;
import org.bouncycastle.jcajce.PKIXExtendedBuilderParameters;
import org.bouncycastle.jcajce.PKIXExtendedParameters;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.jce.exception.ExtCertPathValidatorException;
import org.bouncycastle.x509.PKIXAttrCertChecker;
import org.bouncycastle.x509.X509AttributeCertificate;
import org.bouncycastle.x509.X509CertStoreSelector;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
class RFC3281CertPathUtilities {
    private static final String TARGET_INFORMATION = Extension.targetInformation.getId();
    private static final String NO_REV_AVAIL = Extension.noRevAvail.getId();
    private static final String CRL_DISTRIBUTION_POINTS = Extension.cRLDistributionPoints.getId();
    private static final String AUTHORITY_INFO_ACCESS = Extension.authorityInfoAccess.getId();

    public static void additionalChecks(X509AttributeCertificate x509AttributeCertificate, Set set, Set set2) {
        Iterator it = set.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (x509AttributeCertificate.getAttributes(str) != null) {
                throw new CertPathValidatorException(AbstractC0000a.m16l("Attribute certificate contains prohibited attribute: ", str, "."));
            }
        }
        Iterator it2 = set2.iterator();
        while (it2.hasNext()) {
            String str2 = (String) it2.next();
            if (x509AttributeCertificate.getAttributes(str2) == null) {
                throw new CertPathValidatorException(AbstractC0000a.m16l("Attribute certificate does not contain necessary attribute: ", str2, "."));
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:54:0x00fa, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void checkCRL(DistributionPoint distributionPoint, X509AttributeCertificate x509AttributeCertificate, PKIXExtendedParameters pKIXExtendedParameters, Date date, Date date2, X509Certificate x509Certificate, CertStatus certStatus, ReasonsMask reasonsMask, List list, JcaJceHelper jcaJceHelper) {
        Iterator it;
        X509CRL x509crl;
        ReasonsMask processCRLD;
        X509CRL x509crl2;
        if (x509AttributeCertificate.getExtensionValue(X509Extensions.NoRevAvail.getId()) != null) {
            return;
        }
        if (date2.getTime() > date.getTime()) {
            throw new AnnotatedException("Validation time is in future.");
        }
        Iterator it2 = CertPathValidatorUtilities.getCompleteCRLs(new PKIXCertRevocationCheckerParameters(pKIXExtendedParameters, date2, null, -1, x509Certificate, null), distributionPoint, x509AttributeCertificate, pKIXExtendedParameters, date2).iterator();
        boolean z2 = false;
        AnnotatedException e2 = null;
        while (it2.hasNext() && certStatus.getCertStatus() == 11 && !reasonsMask.isAllReasons()) {
            try {
                x509crl = (X509CRL) it2.next();
                processCRLD = RFC3280CertPathUtilities.processCRLD(x509crl, distributionPoint);
            } catch (AnnotatedException e3) {
                e2 = e3;
                it = it2;
            }
            if (processCRLD.hasNewReasons(reasonsMask)) {
                it = it2;
                try {
                    PublicKey processCRLG = RFC3280CertPathUtilities.processCRLG(x509crl, RFC3280CertPathUtilities.processCRLF(x509crl, x509AttributeCertificate, null, null, pKIXExtendedParameters, list, jcaJceHelper));
                    if (pKIXExtendedParameters.isUseDeltasEnabled()) {
                        try {
                            x509crl2 = RFC3280CertPathUtilities.processCRLH(CertPathValidatorUtilities.getDeltaCRLs(date, x509crl, pKIXExtendedParameters.getCertStores(), pKIXExtendedParameters.getCRLStores(), jcaJceHelper), processCRLG);
                        } catch (AnnotatedException e4) {
                            e2 = e4;
                        }
                    } else {
                        x509crl2 = null;
                    }
                    if (pKIXExtendedParameters.getValidityModel() != 1 && x509AttributeCertificate.getNotAfter().getTime() < x509crl.getThisUpdate().getTime()) {
                        throw new AnnotatedException("No valid CRL for current time found.");
                    }
                    RFC3280CertPathUtilities.processCRLB1(distributionPoint, x509AttributeCertificate, x509crl);
                    RFC3280CertPathUtilities.processCRLB2(distributionPoint, x509AttributeCertificate, x509crl);
                    RFC3280CertPathUtilities.processCRLC(x509crl2, x509crl, pKIXExtendedParameters);
                    RFC3280CertPathUtilities.processCRLI(date2, x509crl2, x509AttributeCertificate, certStatus, pKIXExtendedParameters);
                    RFC3280CertPathUtilities.processCRLJ(date2, x509crl, x509AttributeCertificate, certStatus);
                    if (certStatus.getCertStatus() == 8) {
                        certStatus.setCertStatus(11);
                    }
                    reasonsMask.addReasons(processCRLD);
                    z2 = true;
                } catch (AnnotatedException e5) {
                    e2 = e5;
                }
                it2 = it;
            } else {
                continue;
            }
        }
        throw e2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0166  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void checkCRLs(X509AttributeCertificate x509AttributeCertificate, PKIXExtendedParameters pKIXExtendedParameters, Date date, Date date2, X509Certificate x509Certificate, List list, JcaJceHelper jcaJceHelper) {
        boolean z2;
        String str;
        AnnotatedException annotatedException;
        if (!pKIXExtendedParameters.isRevocationEnabled()) {
            return;
        }
        if (x509AttributeCertificate.getExtensionValue(NO_REV_AVAIL) != null) {
            if (x509AttributeCertificate.getExtensionValue(CRL_DISTRIBUTION_POINTS) != null || x509AttributeCertificate.getExtensionValue(AUTHORITY_INFO_ACCESS) != null) {
                throw new CertPathValidatorException("No rev avail extension is set, but also an AC revocation pointer.");
            }
            return;
        }
        try {
            CRLDistPoint cRLDistPoint = CRLDistPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(x509AttributeCertificate, CRL_DISTRIBUTION_POINTS));
            ArrayList arrayList = new ArrayList();
            try {
                arrayList.addAll(CertPathValidatorUtilities.getAdditionalStoresFromCRLDistributionPoint(cRLDistPoint, pKIXExtendedParameters.getNamedCRLStoreMap(), date2, jcaJceHelper));
                PKIXExtendedParameters.Builder builder = new PKIXExtendedParameters.Builder(pKIXExtendedParameters);
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    builder.addCRLStore((PKIXCRLStore) arrayList);
                }
                PKIXExtendedParameters build = builder.build();
                CertStatus certStatus = new CertStatus();
                ReasonsMask reasonsMask = new ReasonsMask();
                String str2 = "No valid CRL for distribution point found.";
                boolean z3 = true;
                int i2 = 11;
                if (cRLDistPoint != null) {
                    try {
                        DistributionPoint[] distributionPoints = cRLDistPoint.getDistributionPoints();
                        int i3 = 0;
                        z2 = false;
                        while (i3 < distributionPoints.length && certStatus.getCertStatus() == i2 && !reasonsMask.isAllReasons()) {
                            try {
                                int i4 = i3;
                                str = str2;
                                try {
                                    checkCRL(distributionPoints[i3], x509AttributeCertificate, (PKIXExtendedParameters) build.clone(), date, date2, x509Certificate, certStatus, reasonsMask, list, jcaJceHelper);
                                    i3 = i4 + 1;
                                    i2 = 11;
                                    str2 = str;
                                    z2 = true;
                                } catch (AnnotatedException e2) {
                                    e = e2;
                                    annotatedException = new AnnotatedException(str, e);
                                    if (certStatus.getCertStatus() == 11) {
                                    }
                                    z3 = z2;
                                    if (!z3) {
                                    }
                                }
                            } catch (AnnotatedException e3) {
                                e = e3;
                                str = str2;
                            }
                        }
                        str = str2;
                        annotatedException = null;
                    } catch (Exception e4) {
                        throw new ExtCertPathValidatorException("Distribution points could not be read.", e4);
                    }
                } else {
                    str = "No valid CRL for distribution point found.";
                    annotatedException = null;
                    z2 = false;
                }
                if (certStatus.getCertStatus() == 11) {
                    try {
                    } catch (AnnotatedException e5) {
                        annotatedException = new AnnotatedException(str, e5);
                    }
                    if (!reasonsMask.isAllReasons()) {
                        try {
                            checkCRL(new DistributionPoint(new DistributionPointName(0, new GeneralNames(new GeneralName(4, PrincipalUtils.getEncodedIssuerPrincipal(x509AttributeCertificate)))), null, null), x509AttributeCertificate, (PKIXExtendedParameters) build.clone(), date, date2, x509Certificate, certStatus, reasonsMask, list, jcaJceHelper);
                            if (!z3) {
                                throw new ExtCertPathValidatorException("No valid CRL found.", annotatedException);
                            }
                            if (certStatus.getCertStatus() != 11) {
                                StringBuilder m22r = AbstractC0000a.m22r("Attribute certificate revocation after " + certStatus.getRevocationDate(), ", reason: ");
                                m22r.append(RFC3280CertPathUtilities.crlReasons[certStatus.getCertStatus()]);
                                throw new CertPathValidatorException(m22r.toString());
                            }
                            if (!reasonsMask.isAllReasons() && certStatus.getCertStatus() == 11) {
                                certStatus.setCertStatus(12);
                            }
                            if (certStatus.getCertStatus() == 12) {
                                throw new CertPathValidatorException("Attribute certificate status could not be determined.");
                            }
                            return;
                        } catch (Exception e6) {
                            throw new AnnotatedException("Issuer from certificate for CRL could not be reencoded.", e6);
                        }
                    }
                }
                z3 = z2;
                if (!z3) {
                }
            } catch (AnnotatedException e7) {
                throw new CertPathValidatorException("No additional CRL locations could be decoded from CRL distribution point extension.", e7);
            }
        } catch (AnnotatedException e8) {
            throw new CertPathValidatorException("CRL distribution point extension could not be read.", e8);
        }
    }

    public static CertPath processAttrCert1(X509AttributeCertificate x509AttributeCertificate, PKIXExtendedParameters pKIXExtendedParameters) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        if (x509AttributeCertificate.getHolder().getIssuer() != null) {
            X509CertSelector x509CertSelector = new X509CertSelector();
            x509CertSelector.setSerialNumber(x509AttributeCertificate.getHolder().getSerialNumber());
            for (Principal principal : x509AttributeCertificate.getHolder().getIssuer()) {
                try {
                    if (principal instanceof X500Principal) {
                        x509CertSelector.setIssuer(((X500Principal) principal).getEncoded());
                    }
                    CertPathValidatorUtilities.findCertificates(linkedHashSet, new PKIXCertStoreSelector.Builder(x509CertSelector).build(), pKIXExtendedParameters.getCertStores());
                } catch (IOException e2) {
                    throw new ExtCertPathValidatorException("Unable to encode X500 principal.", e2);
                } catch (AnnotatedException e3) {
                    throw new ExtCertPathValidatorException("Public key certificate for attribute certificate cannot be searched.", e3);
                }
            }
            if (linkedHashSet.isEmpty()) {
                throw new CertPathValidatorException("Public key certificate specified in base certificate ID for attribute certificate cannot be found.");
            }
        }
        if (x509AttributeCertificate.getHolder().getEntityNames() != null) {
            X509CertStoreSelector x509CertStoreSelector = new X509CertStoreSelector();
            for (Principal principal2 : x509AttributeCertificate.getHolder().getEntityNames()) {
                try {
                    if (principal2 instanceof X500Principal) {
                        x509CertStoreSelector.setIssuer(((X500Principal) principal2).getEncoded());
                    }
                    CertPathValidatorUtilities.findCertificates(linkedHashSet, new PKIXCertStoreSelector.Builder(x509CertStoreSelector).build(), pKIXExtendedParameters.getCertStores());
                } catch (IOException e4) {
                    throw new ExtCertPathValidatorException("Unable to encode X500 principal.", e4);
                } catch (AnnotatedException e5) {
                    throw new ExtCertPathValidatorException("Public key certificate for attribute certificate cannot be searched.", e5);
                }
            }
            if (linkedHashSet.isEmpty()) {
                throw new CertPathValidatorException("Public key certificate specified in entity name for attribute certificate cannot be found.");
            }
        }
        PKIXExtendedParameters.Builder builder = new PKIXExtendedParameters.Builder(pKIXExtendedParameters);
        Iterator it = linkedHashSet.iterator();
        ExtCertPathValidatorException extCertPathValidatorException = null;
        CertPathBuilderResult certPathBuilderResult = null;
        while (it.hasNext()) {
            X509CertStoreSelector x509CertStoreSelector2 = new X509CertStoreSelector();
            x509CertStoreSelector2.setCertificate((X509Certificate) it.next());
            builder.setTargetConstraints(new PKIXCertStoreSelector.Builder(x509CertStoreSelector2).build());
            try {
                try {
                    certPathBuilderResult = CertPathBuilder.getInstance("PKIX", BouncyCastleProvider.PROVIDER_NAME).build(new PKIXExtendedBuilderParameters.Builder(builder.build()).build());
                } catch (InvalidAlgorithmParameterException e6) {
                    throw new RuntimeException(e6.getMessage());
                } catch (CertPathBuilderException e7) {
                    extCertPathValidatorException = new ExtCertPathValidatorException("Certification path for public key certificate of attribute certificate could not be build.", e7);
                }
            } catch (NoSuchAlgorithmException e8) {
                throw new ExtCertPathValidatorException("Support class could not be created.", e8);
            } catch (NoSuchProviderException e9) {
                throw new ExtCertPathValidatorException("Support class could not be created.", e9);
            }
        }
        if (extCertPathValidatorException == null) {
            return certPathBuilderResult.getCertPath();
        }
        throw extCertPathValidatorException;
    }

    public static CertPathValidatorResult processAttrCert2(CertPath certPath, PKIXExtendedParameters pKIXExtendedParameters) {
        try {
            try {
                return CertPathValidator.getInstance("PKIX", BouncyCastleProvider.PROVIDER_NAME).validate(certPath, pKIXExtendedParameters);
            } catch (InvalidAlgorithmParameterException e2) {
                throw new RuntimeException(e2.getMessage());
            } catch (CertPathValidatorException e3) {
                throw new ExtCertPathValidatorException("Certification path for issuer certificate of attribute certificate could not be validated.", e3);
            }
        } catch (NoSuchAlgorithmException e4) {
            throw new ExtCertPathValidatorException("Support class could not be created.", e4);
        } catch (NoSuchProviderException e5) {
            throw new ExtCertPathValidatorException("Support class could not be created.", e5);
        }
    }

    public static void processAttrCert3(X509Certificate x509Certificate, PKIXExtendedParameters pKIXExtendedParameters) {
        boolean[] keyUsage = x509Certificate.getKeyUsage();
        if (keyUsage != null && ((keyUsage.length <= 0 || !keyUsage[0]) && (keyUsage.length <= 1 || !keyUsage[1]))) {
            throw new CertPathValidatorException("Attribute certificate issuer public key cannot be used to validate digital signatures.");
        }
        if (x509Certificate.getBasicConstraints() != -1) {
            throw new CertPathValidatorException("Attribute certificate issuer is also a public key certificate issuer.");
        }
    }

    public static void processAttrCert4(X509Certificate x509Certificate, Set set) {
        Iterator it = set.iterator();
        boolean z2 = false;
        while (it.hasNext()) {
            TrustAnchor trustAnchor = (TrustAnchor) it.next();
            if (x509Certificate.getSubjectX500Principal().getName("RFC2253").equals(trustAnchor.getCAName()) || x509Certificate.equals(trustAnchor.getTrustedCert())) {
                z2 = true;
            }
        }
        if (!z2) {
            throw new CertPathValidatorException("Attribute certificate issuer is not directly trusted.");
        }
    }

    public static void processAttrCert5(X509AttributeCertificate x509AttributeCertificate, Date date) {
        try {
            x509AttributeCertificate.checkValidity(date);
        } catch (CertificateExpiredException e2) {
            throw new ExtCertPathValidatorException("Attribute certificate is not valid.", e2);
        } catch (CertificateNotYetValidException e3) {
            throw new ExtCertPathValidatorException("Attribute certificate is not valid.", e3);
        }
    }

    public static void processAttrCert7(X509AttributeCertificate x509AttributeCertificate, CertPath certPath, CertPath certPath2, PKIXExtendedParameters pKIXExtendedParameters, Set set) {
        Set<String> criticalExtensionOIDs = x509AttributeCertificate.getCriticalExtensionOIDs();
        String str = TARGET_INFORMATION;
        if (criticalExtensionOIDs.contains(str)) {
            try {
                TargetInformation.getInstance(CertPathValidatorUtilities.getExtensionValue(x509AttributeCertificate, str));
            } catch (IllegalArgumentException e2) {
                throw new ExtCertPathValidatorException("Target information extension could not be read.", e2);
            } catch (AnnotatedException e3) {
                throw new ExtCertPathValidatorException("Target information extension could not be read.", e3);
            }
        }
        criticalExtensionOIDs.remove(str);
        Iterator it = set.iterator();
        while (it.hasNext()) {
            ((PKIXAttrCertChecker) it.next()).check(x509AttributeCertificate, certPath, certPath2, criticalExtensionOIDs);
        }
        if (criticalExtensionOIDs.isEmpty()) {
            return;
        }
        throw new CertPathValidatorException("Attribute certificate contains unsupported critical extensions: " + criticalExtensionOIDs);
    }
}
