package org.bouncycastle.jce.provider;

import android.sun.security.x509.ReasonFlags;
import android.support.v4.os.EnvironmentCompat;
import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderSpi;
import java.security.cert.CertPathValidatorException;
import java.security.cert.Certificate;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.X509CRL;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.security.cert.X509Extension;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1String;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.CRLDistPoint;
import org.bouncycastle.asn1.x509.DistributionPoint;
import org.bouncycastle.asn1.x509.DistributionPointName;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.GeneralSubtree;
import org.bouncycastle.asn1.x509.IssuingDistributionPoint;
import org.bouncycastle.asn1.x509.NameConstraints;
import org.bouncycastle.asn1.x509.PolicyInformation;
import org.bouncycastle.jcajce.PKIXCRLStore;
import org.bouncycastle.jcajce.PKIXCertRevocationChecker;
import org.bouncycastle.jcajce.PKIXCertRevocationCheckerParameters;
import org.bouncycastle.jcajce.PKIXCertStoreSelector;
import org.bouncycastle.jcajce.PKIXExtendedBuilderParameters;
import org.bouncycastle.jcajce.PKIXExtendedParameters;
import org.bouncycastle.jcajce.provider.symmetric.util.ClassUtil;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.jce.exception.ExtCertPathValidatorException;
import org.bouncycastle.util.Arrays;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
class RFC3280CertPathUtilities {
    public static final String ANY_POLICY = "2.5.29.32.0";
    protected static final int CRL_SIGN = 6;
    protected static final int KEY_CERT_SIGN = 5;
    private static final Class revChkClass = ClassUtil.loadClass(RFC3280CertPathUtilities.class, "java.security.cert.PKIXRevocationChecker");
    public static final String CERTIFICATE_POLICIES = Extension.certificatePolicies.getId();
    public static final String POLICY_MAPPINGS = Extension.policyMappings.getId();
    public static final String INHIBIT_ANY_POLICY = Extension.inhibitAnyPolicy.getId();
    public static final String ISSUING_DISTRIBUTION_POINT = Extension.issuingDistributionPoint.getId();
    public static final String FRESHEST_CRL = Extension.freshestCRL.getId();
    public static final String DELTA_CRL_INDICATOR = Extension.deltaCRLIndicator.getId();
    public static final String POLICY_CONSTRAINTS = Extension.policyConstraints.getId();
    public static final String BASIC_CONSTRAINTS = Extension.basicConstraints.getId();
    public static final String CRL_DISTRIBUTION_POINTS = Extension.cRLDistributionPoints.getId();
    public static final String SUBJECT_ALTERNATIVE_NAME = Extension.subjectAlternativeName.getId();
    public static final String NAME_CONSTRAINTS = Extension.nameConstraints.getId();
    public static final String AUTHORITY_KEY_IDENTIFIER = Extension.authorityKeyIdentifier.getId();
    public static final String KEY_USAGE = Extension.keyUsage.getId();
    public static final String CRL_NUMBER = Extension.cRLNumber.getId();
    protected static final String[] crlReasons = {"unspecified", "keyCompromise", "cACompromise", "affiliationChanged", ReasonFlags.SUPERSEDED, "cessationOfOperation", "certificateHold", EnvironmentCompat.MEDIA_UNKNOWN, "removeFromCRL", "privilegeWithdrawn", "aACompromise"};

    private static void checkCRL(PKIXCertRevocationCheckerParameters pKIXCertRevocationCheckerParameters, DistributionPoint distributionPoint, PKIXExtendedParameters pKIXExtendedParameters, Date date, Date date2, X509Certificate x509Certificate, X509Certificate x509Certificate2, PublicKey publicKey, CertStatus certStatus, ReasonsMask reasonsMask, List list, JcaJceHelper jcaJceHelper) {
        Iterator it;
        X509CRL x509crl;
        Set<String> criticalExtensionOIDs;
        if (date2.getTime() > date.getTime()) {
            throw new AnnotatedException("Validation time is in future.");
        }
        Iterator it2 = CertPathValidatorUtilities.getCompleteCRLs(pKIXCertRevocationCheckerParameters, distributionPoint, x509Certificate, pKIXExtendedParameters, date2).iterator();
        boolean z2 = false;
        AnnotatedException e2 = null;
        while (it2.hasNext() && certStatus.getCertStatus() == 11 && !reasonsMask.isAllReasons()) {
            try {
                X509CRL x509crl2 = (X509CRL) it2.next();
                ReasonsMask processCRLD = processCRLD(x509crl2, distributionPoint);
                if (processCRLD.hasNewReasons(reasonsMask)) {
                    it = it2;
                    AnnotatedException annotatedException = e2;
                    try {
                        PublicKey processCRLG = processCRLG(x509crl2, processCRLF(x509crl2, x509Certificate, x509Certificate2, publicKey, pKIXExtendedParameters, list, jcaJceHelper));
                        if (pKIXExtendedParameters.isUseDeltasEnabled()) {
                            try {
                                x509crl = processCRLH(CertPathValidatorUtilities.getDeltaCRLs(date2, x509crl2, pKIXExtendedParameters.getCertStores(), pKIXExtendedParameters.getCRLStores(), jcaJceHelper), processCRLG);
                            } catch (AnnotatedException e3) {
                                e2 = e3;
                                it2 = it;
                            }
                        } else {
                            x509crl = null;
                        }
                        if (pKIXExtendedParameters.getValidityModel() != 1 && x509Certificate.getNotAfter().getTime() < x509crl2.getThisUpdate().getTime()) {
                            throw new AnnotatedException("No valid CRL for current time found.");
                        }
                        processCRLB1(distributionPoint, x509Certificate, x509crl2);
                        processCRLB2(distributionPoint, x509Certificate, x509crl2);
                        processCRLC(x509crl, x509crl2, pKIXExtendedParameters);
                        processCRLI(date2, x509crl, x509Certificate, certStatus, pKIXExtendedParameters);
                        processCRLJ(date2, x509crl2, x509Certificate, certStatus);
                        if (certStatus.getCertStatus() == 8) {
                            certStatus.setCertStatus(11);
                        }
                        reasonsMask.addReasons(processCRLD);
                        Set<String> criticalExtensionOIDs2 = x509crl2.getCriticalExtensionOIDs();
                        if (criticalExtensionOIDs2 != null) {
                            HashSet hashSet = new HashSet(criticalExtensionOIDs2);
                            hashSet.remove(Extension.issuingDistributionPoint.getId());
                            hashSet.remove(Extension.deltaCRLIndicator.getId());
                            if (!hashSet.isEmpty()) {
                                throw new AnnotatedException("CRL contains unsupported critical extensions.");
                            }
                        }
                        if (x509crl != null && (criticalExtensionOIDs = x509crl.getCriticalExtensionOIDs()) != null) {
                            HashSet hashSet2 = new HashSet(criticalExtensionOIDs);
                            hashSet2.remove(Extension.issuingDistributionPoint.getId());
                            hashSet2.remove(Extension.deltaCRLIndicator.getId());
                            if (!hashSet2.isEmpty()) {
                                throw new AnnotatedException("Delta CRL contains unsupported critical extension.");
                            }
                        }
                        it2 = it;
                        z2 = true;
                        e2 = annotatedException;
                    } catch (AnnotatedException e4) {
                        e2 = e4;
                    }
                } else {
                    continue;
                }
            } catch (AnnotatedException e5) {
                e2 = e5;
                it = it2;
            }
        }
        AnnotatedException annotatedException2 = e2;
        if (!z2) {
            throw annotatedException2;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0113  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void checkCRLs(PKIXCertRevocationCheckerParameters pKIXCertRevocationCheckerParameters, PKIXExtendedParameters pKIXExtendedParameters, Date date, Date date2, X509Certificate x509Certificate, X509Certificate x509Certificate2, PublicKey publicKey, List list, JcaJceHelper jcaJceHelper) {
        boolean z2;
        AnnotatedException e2;
        int i2;
        CertStatus certStatus;
        int i3;
        DistributionPoint[] distributionPointArr;
        int i4;
        CertStatus certStatus2;
        CertStatus certStatus3;
        try {
            CRLDistPoint cRLDistPoint = CRLDistPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(x509Certificate, CRL_DISTRIBUTION_POINTS));
            PKIXExtendedParameters.Builder builder = new PKIXExtendedParameters.Builder(pKIXExtendedParameters);
            try {
                Iterator<PKIXCRLStore> it = CertPathValidatorUtilities.getAdditionalStoresFromCRLDistributionPoint(cRLDistPoint, pKIXExtendedParameters.getNamedCRLStoreMap(), date2, jcaJceHelper).iterator();
                while (it.hasNext()) {
                    builder.addCRLStore(it.next());
                }
                CertStatus certStatus4 = new CertStatus();
                ReasonsMask reasonsMask = new ReasonsMask();
                PKIXExtendedParameters build = builder.build();
                boolean z3 = true;
                int i5 = 11;
                if (cRLDistPoint != null) {
                    try {
                        DistributionPoint[] distributionPoints = cRLDistPoint.getDistributionPoints();
                        if (distributionPoints != null) {
                            int i6 = 0;
                            z2 = false;
                            e2 = null;
                            while (i6 < distributionPoints.length && certStatus4.getCertStatus() == i5 && !reasonsMask.isAllReasons()) {
                                try {
                                    i3 = i6;
                                    distributionPointArr = distributionPoints;
                                    i4 = i5;
                                    certStatus2 = certStatus4;
                                    try {
                                        checkCRL(pKIXCertRevocationCheckerParameters, distributionPoints[i6], build, date, date2, x509Certificate, x509Certificate2, publicKey, certStatus4, reasonsMask, list, jcaJceHelper);
                                        z2 = true;
                                    } catch (AnnotatedException e3) {
                                        e2 = e3;
                                    }
                                } catch (AnnotatedException e4) {
                                    e2 = e4;
                                    i3 = i6;
                                    distributionPointArr = distributionPoints;
                                    i4 = i5;
                                    certStatus2 = certStatus4;
                                }
                                i6 = i3 + 1;
                                i5 = i4;
                                distributionPoints = distributionPointArr;
                                certStatus4 = certStatus2;
                            }
                            i2 = i5;
                            certStatus = certStatus4;
                            if (certStatus.getCertStatus() == i2 && !reasonsMask.isAllReasons()) {
                                try {
                                    try {
                                        checkCRL(pKIXCertRevocationCheckerParameters, new DistributionPoint(new DistributionPointName(0, new GeneralNames(new GeneralName(4, PrincipalUtils.getIssuerPrincipal(x509Certificate)))), null, null), (PKIXExtendedParameters) pKIXExtendedParameters.clone(), date, date2, x509Certificate, x509Certificate2, publicKey, certStatus, reasonsMask, list, jcaJceHelper);
                                    } catch (RuntimeException e5) {
                                        throw new AnnotatedException("Issuer from certificate for CRL could not be reencoded.", e5);
                                    }
                                } catch (AnnotatedException e6) {
                                    e2 = e6;
                                }
                                if (z3) {
                                    if (!(e2 instanceof AnnotatedException)) {
                                        throw new AnnotatedException("No valid CRL found.", e2);
                                    }
                                    throw e2;
                                }
                                if (certStatus.getCertStatus() == i2) {
                                    if (reasonsMask.isAllReasons() || certStatus.getCertStatus() != i2) {
                                        certStatus3 = certStatus;
                                    } else {
                                        certStatus3 = certStatus;
                                        certStatus3.setCertStatus(12);
                                    }
                                    if (certStatus3.getCertStatus() == 12) {
                                        throw new AnnotatedException("Certificate status could not be determined.");
                                    }
                                    return;
                                }
                                CertStatus certStatus5 = certStatus;
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
                                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                                StringBuilder m22r = AbstractC0000a.m22r("Certificate revocation after " + simpleDateFormat.format(certStatus5.getRevocationDate()), ", reason: ");
                                m22r.append(crlReasons[certStatus5.getCertStatus()]);
                                throw new AnnotatedException(m22r.toString());
                            }
                            z3 = z2;
                            if (z3) {
                            }
                        }
                    } catch (Exception e7) {
                        throw new AnnotatedException("Distribution points could not be read.", e7);
                    }
                }
                i2 = 11;
                certStatus = certStatus4;
                e2 = null;
                z2 = false;
                if (certStatus.getCertStatus() == i2) {
                    checkCRL(pKIXCertRevocationCheckerParameters, new DistributionPoint(new DistributionPointName(0, new GeneralNames(new GeneralName(4, PrincipalUtils.getIssuerPrincipal(x509Certificate)))), null, null), (PKIXExtendedParameters) pKIXExtendedParameters.clone(), date, date2, x509Certificate, x509Certificate2, publicKey, certStatus, reasonsMask, list, jcaJceHelper);
                    if (z3) {
                    }
                }
                z3 = z2;
                if (z3) {
                }
            } catch (AnnotatedException e8) {
                throw new AnnotatedException("No additional CRL locations could be decoded from CRL distribution point extension.", e8);
            }
        } catch (Exception e9) {
            throw new AnnotatedException("CRL distribution point extension could not be read.", e9);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x00d3, code lost:
    
        r5 = ((org.bouncycastle.asn1.ASN1Sequence) org.bouncycastle.jce.provider.CertPathValidatorUtilities.getExtensionValue(r4, org.bouncycastle.jce.provider.RFC3280CertPathUtilities.CERTIFICATE_POLICIES)).getObjects();
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00db, code lost:
    
        if (r5.hasMoreElements() == false) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00dd, code lost:
    
        r7 = org.bouncycastle.asn1.x509.PolicyInformation.getInstance(r5.nextElement());
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00f1, code lost:
    
        if (org.bouncycastle.jce.provider.RFC3280CertPathUtilities.ANY_POLICY.equals(r7.getPolicyIdentifier().getId()) == false) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00f3, code lost:
    
        r5 = org.bouncycastle.jce.provider.CertPathValidatorUtilities.getQualifierSet(r7.getPolicyQualifiers());
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x010f, code lost:
    
        r10 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0114, code lost:
    
        if (r4.getCriticalExtensionOIDs() == null) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0116, code lost:
    
        r12 = r4.getCriticalExtensionOIDs().contains(org.bouncycastle.jce.provider.RFC3280CertPathUtilities.CERTIFICATE_POLICIES);
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0123, code lost:
    
        r9 = (org.bouncycastle.jce.provider.PKIXPolicyNode) r6.getParent();
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0132, code lost:
    
        if (org.bouncycastle.jce.provider.RFC3280CertPathUtilities.ANY_POLICY.equals(r9.getValidPolicy()) == false) goto L95;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0134, code lost:
    
        r8 = new org.bouncycastle.jce.provider.PKIXPolicyNode(new java.util.ArrayList(), r3, (java.util.Set) r13.get(r11), r9, r10, r11, r12);
        r9.addChild(r8);
        r21[r3].add(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0122, code lost:
    
        r12 = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00fc, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0104, code lost:
    
        throw new org.bouncycastle.jce.exception.ExtCertPathValidatorException("Policy qualifier info set could not be decoded.", r0, r19, r20);
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0105, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x010d, code lost:
    
        throw new java.security.cert.CertPathValidatorException("Policy information could not be decoded.", r0, r19, r20);
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x010e, code lost:
    
        r5 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x01b5, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static PKIXPolicyNode prepareCertB(CertPath certPath, int i2, List[] listArr, PKIXPolicyNode pKIXPolicyNode, int i3) {
        int i4;
        boolean z2;
        List<? extends Certificate> certificates = certPath.getCertificates();
        X509Certificate x509Certificate = (X509Certificate) certificates.get(i2);
        int size = certificates.size() - i2;
        try {
            ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(CertPathValidatorUtilities.getExtensionValue(x509Certificate, POLICY_MAPPINGS));
            if (aSN1Sequence == null) {
                return pKIXPolicyNode;
            }
            HashMap hashMap = new HashMap();
            HashSet hashSet = new HashSet();
            boolean z3 = false;
            for (int i5 = 0; i5 < aSN1Sequence.size(); i5++) {
                ASN1Sequence aSN1Sequence2 = (ASN1Sequence) aSN1Sequence.getObjectAt(i5);
                String id = ((ASN1ObjectIdentifier) aSN1Sequence2.getObjectAt(0)).getId();
                String id2 = ((ASN1ObjectIdentifier) aSN1Sequence2.getObjectAt(1)).getId();
                if (hashMap.containsKey(id)) {
                    ((Set) hashMap.get(id)).add(id2);
                } else {
                    HashSet hashSet2 = new HashSet();
                    hashSet2.add(id2);
                    hashMap.put(id, hashSet2);
                    hashSet.add(id);
                }
            }
            Iterator it = hashSet.iterator();
            PKIXPolicyNode pKIXPolicyNode2 = pKIXPolicyNode;
            while (it.hasNext()) {
                String str = (String) it.next();
                if (i3 > 0) {
                    Iterator it2 = listArr[size].iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            z2 = z3;
                            break;
                        }
                        PKIXPolicyNode pKIXPolicyNode3 = (PKIXPolicyNode) it2.next();
                        if (pKIXPolicyNode3.getValidPolicy().equals(str)) {
                            pKIXPolicyNode3.expectedPolicies = (Set) hashMap.get(str);
                            z2 = true;
                            break;
                        }
                    }
                    if (z2) {
                        continue;
                    } else {
                        Iterator it3 = listArr[size].iterator();
                        while (true) {
                            if (it3.hasNext()) {
                                PKIXPolicyNode pKIXPolicyNode4 = (PKIXPolicyNode) it3.next();
                                if (ANY_POLICY.equals(pKIXPolicyNode4.getValidPolicy())) {
                                    try {
                                        break;
                                    } catch (AnnotatedException e2) {
                                        throw new ExtCertPathValidatorException("Certificate policies extension could not be decoded.", e2, certPath, i2);
                                    }
                                }
                            }
                        }
                    }
                } else if (i3 <= 0) {
                    Iterator it4 = listArr[size].iterator();
                    while (it4.hasNext()) {
                        PKIXPolicyNode pKIXPolicyNode5 = (PKIXPolicyNode) it4.next();
                        if (pKIXPolicyNode5.getValidPolicy().equals(str)) {
                            ((PKIXPolicyNode) pKIXPolicyNode5.getParent()).removeChild(pKIXPolicyNode5);
                            it4.remove();
                            for (int i6 = size - 1; i6 >= 0; i6--) {
                                List list = listArr[i6];
                                PKIXPolicyNode pKIXPolicyNode6 = pKIXPolicyNode2;
                                while (i4 < list.size()) {
                                    PKIXPolicyNode pKIXPolicyNode7 = (PKIXPolicyNode) list.get(i4);
                                    i4 = (pKIXPolicyNode7.hasChildren() || (pKIXPolicyNode6 = CertPathValidatorUtilities.removePolicyNode(pKIXPolicyNode6, listArr, pKIXPolicyNode7)) != null) ? i4 + 1 : 0;
                                }
                                pKIXPolicyNode2 = pKIXPolicyNode6;
                            }
                        }
                    }
                }
                z3 = false;
            }
            return pKIXPolicyNode2;
        } catch (AnnotatedException e3) {
            throw new ExtCertPathValidatorException("Policy mappings extension could not be decoded.", e3, certPath, i2);
        }
    }

    public static void prepareNextCertA(CertPath certPath, int i2) {
        try {
            ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Certificate) certPath.getCertificates().get(i2), POLICY_MAPPINGS));
            if (aSN1Sequence != null) {
                for (int i3 = 0; i3 < aSN1Sequence.size(); i3++) {
                    try {
                        ASN1Sequence aSN1Sequence2 = ASN1Sequence.getInstance(aSN1Sequence.getObjectAt(i3));
                        ASN1ObjectIdentifier aSN1ObjectIdentifier = ASN1ObjectIdentifier.getInstance(aSN1Sequence2.getObjectAt(0));
                        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = ASN1ObjectIdentifier.getInstance(aSN1Sequence2.getObjectAt(1));
                        if (ANY_POLICY.equals(aSN1ObjectIdentifier.getId())) {
                            throw new CertPathValidatorException("IssuerDomainPolicy is anyPolicy", null, certPath, i2);
                        }
                        if (ANY_POLICY.equals(aSN1ObjectIdentifier2.getId())) {
                            throw new CertPathValidatorException("SubjectDomainPolicy is anyPolicy", null, certPath, i2);
                        }
                    } catch (Exception e2) {
                        throw new ExtCertPathValidatorException("Policy mappings extension contents could not be decoded.", e2, certPath, i2);
                    }
                }
            }
        } catch (AnnotatedException e3) {
            throw new ExtCertPathValidatorException("Policy mappings extension could not be decoded.", e3, certPath, i2);
        }
    }

    public static void prepareNextCertG(CertPath certPath, int i2, PKIXNameConstraintValidator pKIXNameConstraintValidator) {
        try {
            ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Certificate) certPath.getCertificates().get(i2), NAME_CONSTRAINTS));
            NameConstraints nameConstraints = aSN1Sequence != null ? NameConstraints.getInstance(aSN1Sequence) : null;
            if (nameConstraints != null) {
                GeneralSubtree[] permittedSubtrees = nameConstraints.getPermittedSubtrees();
                if (permittedSubtrees != null) {
                    try {
                        pKIXNameConstraintValidator.intersectPermittedSubtree(permittedSubtrees);
                    } catch (Exception e2) {
                        throw new ExtCertPathValidatorException("Permitted subtrees cannot be build from name constraints extension.", e2, certPath, i2);
                    }
                }
                GeneralSubtree[] excludedSubtrees = nameConstraints.getExcludedSubtrees();
                if (excludedSubtrees != null) {
                    for (int i3 = 0; i3 != excludedSubtrees.length; i3++) {
                        try {
                            pKIXNameConstraintValidator.addExcludedSubtree(excludedSubtrees[i3]);
                        } catch (Exception e3) {
                            throw new ExtCertPathValidatorException("Excluded subtrees cannot be build from name constraints extension.", e3, certPath, i2);
                        }
                    }
                }
            }
        } catch (Exception e4) {
            throw new ExtCertPathValidatorException("Name constraints extension could not be decoded.", e4, certPath, i2);
        }
    }

    public static int prepareNextCertH1(CertPath certPath, int i2, int i3) {
        return (CertPathValidatorUtilities.isSelfIssued((X509Certificate) certPath.getCertificates().get(i2)) || i3 == 0) ? i3 : i3 - 1;
    }

    public static int prepareNextCertH2(CertPath certPath, int i2, int i3) {
        return (CertPathValidatorUtilities.isSelfIssued((X509Certificate) certPath.getCertificates().get(i2)) || i3 == 0) ? i3 : i3 - 1;
    }

    public static int prepareNextCertH3(CertPath certPath, int i2, int i3) {
        return (CertPathValidatorUtilities.isSelfIssued((X509Certificate) certPath.getCertificates().get(i2)) || i3 == 0) ? i3 : i3 - 1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x002e, code lost:
    
        r3 = org.bouncycastle.asn1.ASN1Integer.getInstance(r1, false).intValueExact();
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0037, code lost:
    
        if (r3 >= r5) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0039, code lost:
    
        return r3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int prepareNextCertI1(CertPath certPath, int i2, int i3) {
        try {
            ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Certificate) certPath.getCertificates().get(i2), POLICY_CONSTRAINTS));
            if (aSN1Sequence != null) {
                Enumeration objects = aSN1Sequence.getObjects();
                while (true) {
                    if (!objects.hasMoreElements()) {
                        break;
                    }
                    try {
                        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(objects.nextElement());
                        if (aSN1TaggedObject.getTagNo() == 0) {
                            break;
                        }
                    } catch (IllegalArgumentException e2) {
                        throw new ExtCertPathValidatorException("Policy constraints extension contents cannot be decoded.", e2, certPath, i2);
                    }
                }
            }
            return i3;
        } catch (Exception e3) {
            throw new ExtCertPathValidatorException("Policy constraints extension cannot be decoded.", e3, certPath, i2);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x002f, code lost:
    
        r4 = org.bouncycastle.asn1.ASN1Integer.getInstance(r1, false).intValueExact();
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0038, code lost:
    
        if (r4 >= r6) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x003a, code lost:
    
        return r4;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int prepareNextCertI2(CertPath certPath, int i2, int i3) {
        try {
            ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Certificate) certPath.getCertificates().get(i2), POLICY_CONSTRAINTS));
            if (aSN1Sequence != null) {
                Enumeration objects = aSN1Sequence.getObjects();
                while (true) {
                    if (!objects.hasMoreElements()) {
                        break;
                    }
                    try {
                        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(objects.nextElement());
                        if (aSN1TaggedObject.getTagNo() == 1) {
                            break;
                        }
                    } catch (IllegalArgumentException e2) {
                        throw new ExtCertPathValidatorException("Policy constraints extension contents cannot be decoded.", e2, certPath, i2);
                    }
                }
            }
            return i3;
        } catch (Exception e3) {
            throw new ExtCertPathValidatorException("Policy constraints extension cannot be decoded.", e3, certPath, i2);
        }
    }

    public static int prepareNextCertJ(CertPath certPath, int i2, int i3) {
        int intValueExact;
        try {
            ASN1Integer aSN1Integer = ASN1Integer.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Certificate) certPath.getCertificates().get(i2), INHIBIT_ANY_POLICY));
            return (aSN1Integer == null || (intValueExact = aSN1Integer.intValueExact()) >= i3) ? i3 : intValueExact;
        } catch (Exception e2) {
            throw new ExtCertPathValidatorException("Inhibit any-policy extension cannot be decoded.", e2, certPath, i2);
        }
    }

    public static void prepareNextCertK(CertPath certPath, int i2) {
        try {
            BasicConstraints basicConstraints = BasicConstraints.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Certificate) certPath.getCertificates().get(i2), BASIC_CONSTRAINTS));
            if (basicConstraints == null) {
                throw new CertPathValidatorException("Intermediate certificate lacks BasicConstraints", null, certPath, i2);
            }
            if (!basicConstraints.isCA()) {
                throw new CertPathValidatorException("Not a CA certificate", null, certPath, i2);
            }
        } catch (Exception e2) {
            throw new ExtCertPathValidatorException("Basic constraints extension cannot be decoded.", e2, certPath, i2);
        }
    }

    public static int prepareNextCertL(CertPath certPath, int i2, int i3) {
        if (CertPathValidatorUtilities.isSelfIssued((X509Certificate) certPath.getCertificates().get(i2))) {
            return i3;
        }
        if (i3 > 0) {
            return i3 - 1;
        }
        throw new ExtCertPathValidatorException("Max path length not greater than zero", null, certPath, i2);
    }

    public static int prepareNextCertM(CertPath certPath, int i2, int i3) {
        BigInteger pathLenConstraint;
        int intValue;
        try {
            BasicConstraints basicConstraints = BasicConstraints.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Certificate) certPath.getCertificates().get(i2), BASIC_CONSTRAINTS));
            return (basicConstraints == null || (pathLenConstraint = basicConstraints.getPathLenConstraint()) == null || (intValue = pathLenConstraint.intValue()) >= i3) ? i3 : intValue;
        } catch (Exception e2) {
            throw new ExtCertPathValidatorException("Basic constraints extension cannot be decoded.", e2, certPath, i2);
        }
    }

    public static void prepareNextCertN(CertPath certPath, int i2) {
        boolean[] keyUsage = ((X509Certificate) certPath.getCertificates().get(i2)).getKeyUsage();
        if (keyUsage != null) {
            if (keyUsage.length <= 5 || !keyUsage[5]) {
                throw new ExtCertPathValidatorException("Issuer certificate keyusage extension is critical and does not permit key signing.", null, certPath, i2);
            }
        }
    }

    public static void prepareNextCertO(CertPath certPath, int i2, Set set, List list) {
        X509Certificate x509Certificate = (X509Certificate) certPath.getCertificates().get(i2);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            try {
                ((PKIXCertPathChecker) it.next()).check(x509Certificate, set);
            } catch (CertPathValidatorException e2) {
                throw new CertPathValidatorException(e2.getMessage(), e2.getCause(), certPath, i2);
            }
        }
        if (set.isEmpty()) {
            return;
        }
        throw new ExtCertPathValidatorException("Certificate has unsupported critical extension: " + set, null, certPath, i2);
    }

    public static void processCRLB1(DistributionPoint distributionPoint, Object obj, X509CRL x509crl) {
        ASN1Primitive extensionValue = CertPathValidatorUtilities.getExtensionValue(x509crl, ISSUING_DISTRIBUTION_POINT);
        boolean z2 = true;
        boolean z3 = extensionValue != null && IssuingDistributionPoint.getInstance(extensionValue).isIndirectCRL();
        try {
            byte[] encoded = PrincipalUtils.getIssuerPrincipal(x509crl).getEncoded();
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
            } else if (!PrincipalUtils.getIssuerPrincipal(x509crl).equals(PrincipalUtils.getEncodedIssuerPrincipal(obj))) {
                z2 = false;
            }
            if (!z2) {
                throw new AnnotatedException("Cannot find matching CRL issuer for certificate.");
            }
        } catch (IOException e3) {
            throw new AnnotatedException(AbstractC0000a.m8d(e3, new StringBuilder("Exception encoding CRL issuer: ")), e3);
        }
    }

    public static void processCRLB2(DistributionPoint distributionPoint, Object obj, X509CRL x509crl) {
        GeneralName[] generalNameArr;
        try {
            IssuingDistributionPoint issuingDistributionPoint = IssuingDistributionPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(x509crl, ISSUING_DISTRIBUTION_POINT));
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
                            Enumeration objects = ASN1Sequence.getInstance(PrincipalUtils.getIssuerPrincipal(x509crl)).getObjects();
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
                                    generalNameArr[0] = new GeneralName(PrincipalUtils.getEncodedIssuerPrincipal(obj));
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
                    BasicConstraints basicConstraints = BasicConstraints.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Extension) obj, BASIC_CONSTRAINTS));
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
        if (x509crl.hasUnsupportedCriticalExtension()) {
            throw new AnnotatedException("delta CRL has unsupported critical extensions");
        }
        try {
            String str = ISSUING_DISTRIBUTION_POINT;
            IssuingDistributionPoint issuingDistributionPoint = IssuingDistributionPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(x509crl2, str));
            if (pKIXExtendedParameters.isUseDeltasEnabled()) {
                if (!PrincipalUtils.getIssuerPrincipal(x509crl).equals(PrincipalUtils.getIssuerPrincipal(x509crl2))) {
                    throw new AnnotatedException("Complete CRL issuer does not match delta CRL issuer.");
                }
                try {
                    IssuingDistributionPoint issuingDistributionPoint2 = IssuingDistributionPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(x509crl, str));
                    boolean z2 = true;
                    if (issuingDistributionPoint != null ? !issuingDistributionPoint.equals(issuingDistributionPoint2) : issuingDistributionPoint2 != null) {
                        z2 = false;
                    }
                    if (!z2) {
                        throw new AnnotatedException("Issuing distribution point extension from delta CRL and complete CRL does not match.");
                    }
                    try {
                        String str2 = AUTHORITY_KEY_IDENTIFIER;
                        ASN1Primitive extensionValue = CertPathValidatorUtilities.getExtensionValue(x509crl2, str2);
                        try {
                            ASN1Primitive extensionValue2 = CertPathValidatorUtilities.getExtensionValue(x509crl, str2);
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
            throw new AnnotatedException("Issuing distribution point extension could not be decoded.", e5);
        }
    }

    public static ReasonsMask processCRLD(X509CRL x509crl, DistributionPoint distributionPoint) {
        try {
            IssuingDistributionPoint issuingDistributionPoint = IssuingDistributionPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(x509crl, ISSUING_DISTRIBUTION_POINT));
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
            x509CertSelector.setSubject(PrincipalUtils.getIssuerPrincipal(x509crl).getEncoded());
            PKIXCertStoreSelector<? extends Certificate> build = new PKIXCertStoreSelector.Builder(x509CertSelector).build();
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            try {
                CertPathValidatorUtilities.findCertificates(linkedHashSet, build, pKIXExtendedParameters.getCertificateStores());
                CertPathValidatorUtilities.findCertificates(linkedHashSet, build, pKIXExtendedParameters.getCertStores());
                linkedHashSet.add(x509Certificate);
                Iterator it = linkedHashSet.iterator();
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
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
                            CertPathBuilderSpi pKIXCertPathBuilderSpi_8 = revChkClass != null ? new PKIXCertPathBuilderSpi_8(true) : new PKIXCertPathBuilderSpi(true);
                            X509CertSelector x509CertSelector2 = new X509CertSelector();
                            x509CertSelector2.setCertificate(x509Certificate2);
                            PKIXExtendedParameters.Builder targetConstraints = new PKIXExtendedParameters.Builder(pKIXExtendedParameters).setTargetConstraints(new PKIXCertStoreSelector.Builder(x509CertSelector2).build());
                            if (list.contains(x509Certificate2)) {
                                targetConstraints.setRevocationEnabled(false);
                            } else {
                                targetConstraints.setRevocationEnabled(true);
                            }
                            List<? extends Certificate> certificates = pKIXCertPathBuilderSpi_8.engineBuild(new PKIXExtendedBuilderParameters.Builder(targetConstraints.build()).build()).getCertPath().getCertificates();
                            arrayList.add(x509Certificate2);
                            arrayList2.add(CertPathValidatorUtilities.getNextWorkingKey(certificates, 0, jcaJceHelper));
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
            throw new AnnotatedException("Subject criteria for certificate selector to find issuer certificate for CRL could not be set.", e6);
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
        CertPathValidatorUtilities.getCertStatus(date, x509crl, obj, certStatus);
    }

    public static void processCRLJ(Date date, X509CRL x509crl, Object obj, CertStatus certStatus) {
        if (certStatus.getCertStatus() == 11) {
            CertPathValidatorUtilities.getCertStatus(date, x509crl, obj, certStatus);
        }
    }

    public static void processCertA(CertPath certPath, PKIXExtendedParameters pKIXExtendedParameters, Date date, PKIXCertRevocationChecker pKIXCertRevocationChecker, int i2, PublicKey publicKey, boolean z2, X500Name x500Name, X509Certificate x509Certificate) {
        X509Certificate x509Certificate2 = (X509Certificate) certPath.getCertificates().get(i2);
        if (!z2) {
            try {
                CertPathValidatorUtilities.verifyX509Certificate(x509Certificate2, publicKey, pKIXExtendedParameters.getSigProvider());
            } catch (GeneralSecurityException e2) {
                throw new ExtCertPathValidatorException("Could not validate certificate signature.", e2, certPath, i2);
            }
        }
        try {
            Date validCertDateFromValidityModel = CertPathValidatorUtilities.getValidCertDateFromValidityModel(date, pKIXExtendedParameters.getValidityModel(), certPath, i2);
            try {
                x509Certificate2.checkValidity(validCertDateFromValidityModel);
                if (pKIXCertRevocationChecker != null) {
                    pKIXCertRevocationChecker.initialize(new PKIXCertRevocationCheckerParameters(pKIXExtendedParameters, validCertDateFromValidityModel, certPath, i2, x509Certificate, publicKey));
                    pKIXCertRevocationChecker.check(x509Certificate2);
                }
                X500Name issuerPrincipal = PrincipalUtils.getIssuerPrincipal(x509Certificate2);
                if (issuerPrincipal.equals(x500Name)) {
                    return;
                }
                throw new ExtCertPathValidatorException("IssuerName(" + issuerPrincipal + ") does not match SubjectName(" + x500Name + ") of signing certificate.", null, certPath, i2);
            } catch (CertificateExpiredException e3) {
                throw new ExtCertPathValidatorException("Could not validate certificate: " + e3.getMessage(), e3, certPath, i2);
            } catch (CertificateNotYetValidException e4) {
                throw new ExtCertPathValidatorException("Could not validate certificate: " + e4.getMessage(), e4, certPath, i2);
            }
        } catch (AnnotatedException e5) {
            throw new ExtCertPathValidatorException("Could not validate time of certificate.", e5, certPath, i2);
        }
    }

    public static void processCertBC(CertPath certPath, int i2, PKIXNameConstraintValidator pKIXNameConstraintValidator, boolean z2) {
        List<? extends Certificate> certificates = certPath.getCertificates();
        X509Certificate x509Certificate = (X509Certificate) certificates.get(i2);
        int size = certificates.size();
        int i3 = size - i2;
        if (!CertPathValidatorUtilities.isSelfIssued(x509Certificate) || (i3 >= size && !z2)) {
            try {
                ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(PrincipalUtils.getSubjectPrincipal(x509Certificate));
                try {
                    pKIXNameConstraintValidator.checkPermittedDN(aSN1Sequence);
                    pKIXNameConstraintValidator.checkExcludedDN(aSN1Sequence);
                    try {
                        GeneralNames generalNames = GeneralNames.getInstance(CertPathValidatorUtilities.getExtensionValue(x509Certificate, SUBJECT_ALTERNATIVE_NAME));
                        RDN[] rDNs = X500Name.getInstance(aSN1Sequence).getRDNs(BCStyle.EmailAddress);
                        for (int i4 = 0; i4 != rDNs.length; i4++) {
                            GeneralName generalName = new GeneralName(1, ((ASN1String) rDNs[i4].getFirst().getValue()).getString());
                            try {
                                pKIXNameConstraintValidator.checkPermitted(generalName);
                                pKIXNameConstraintValidator.checkExcluded(generalName);
                            } catch (PKIXNameConstraintValidatorException e2) {
                                throw new CertPathValidatorException("Subtree check for certificate subject alternative email failed.", e2, certPath, i2);
                            }
                        }
                        if (generalNames != null) {
                            try {
                                GeneralName[] names = generalNames.getNames();
                                for (int i5 = 0; i5 < names.length; i5++) {
                                    try {
                                        pKIXNameConstraintValidator.checkPermitted(names[i5]);
                                        pKIXNameConstraintValidator.checkExcluded(names[i5]);
                                    } catch (PKIXNameConstraintValidatorException e3) {
                                        throw new CertPathValidatorException("Subtree check for certificate subject alternative name failed.", e3, certPath, i2);
                                    }
                                }
                            } catch (Exception e4) {
                                throw new CertPathValidatorException("Subject alternative name contents could not be decoded.", e4, certPath, i2);
                            }
                        }
                    } catch (Exception e5) {
                        throw new CertPathValidatorException("Subject alternative name extension could not be decoded.", e5, certPath, i2);
                    }
                } catch (PKIXNameConstraintValidatorException e6) {
                    throw new CertPathValidatorException("Subtree check for certificate subject failed.", e6, certPath, i2);
                }
            } catch (Exception e7) {
                throw new CertPathValidatorException("Exception extracting subject name when checking subtrees.", e7, certPath, i2);
            }
        }
    }

    public static PKIXPolicyNode processCertD(CertPath certPath, int i2, Set set, PKIXPolicyNode pKIXPolicyNode, List[] listArr, int i3, boolean z2) {
        String str;
        int i4;
        List<? extends Certificate> certificates = certPath.getCertificates();
        X509Certificate x509Certificate = (X509Certificate) certificates.get(i2);
        int size = certificates.size();
        int i5 = size - i2;
        try {
            ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(CertPathValidatorUtilities.getExtensionValue(x509Certificate, CERTIFICATE_POLICIES));
            if (aSN1Sequence == null || pKIXPolicyNode == null) {
                return null;
            }
            Enumeration objects = aSN1Sequence.getObjects();
            HashSet hashSet = new HashSet();
            while (objects.hasMoreElements()) {
                PolicyInformation policyInformation = PolicyInformation.getInstance(objects.nextElement());
                ASN1ObjectIdentifier policyIdentifier = policyInformation.getPolicyIdentifier();
                hashSet.add(policyIdentifier.getId());
                if (!ANY_POLICY.equals(policyIdentifier.getId())) {
                    try {
                        Set qualifierSet = CertPathValidatorUtilities.getQualifierSet(policyInformation.getPolicyQualifiers());
                        if (!CertPathValidatorUtilities.processCertD1i(i5, listArr, policyIdentifier, qualifierSet)) {
                            CertPathValidatorUtilities.processCertD1ii(i5, listArr, policyIdentifier, qualifierSet);
                        }
                    } catch (CertPathValidatorException e2) {
                        throw new ExtCertPathValidatorException("Policy qualifier info set could not be build.", e2, certPath, i2);
                    }
                }
            }
            if (set.isEmpty() || set.contains(ANY_POLICY)) {
                set.clear();
                set.addAll(hashSet);
            } else {
                HashSet hashSet2 = new HashSet();
                for (Object obj : set) {
                    if (hashSet.contains(obj)) {
                        hashSet2.add(obj);
                    }
                }
                set.clear();
                set.addAll(hashSet2);
            }
            if (i3 > 0 || ((i5 < size || z2) && CertPathValidatorUtilities.isSelfIssued(x509Certificate))) {
                Enumeration objects2 = aSN1Sequence.getObjects();
                while (true) {
                    if (!objects2.hasMoreElements()) {
                        break;
                    }
                    PolicyInformation policyInformation2 = PolicyInformation.getInstance(objects2.nextElement());
                    if (ANY_POLICY.equals(policyInformation2.getPolicyIdentifier().getId())) {
                        Set qualifierSet2 = CertPathValidatorUtilities.getQualifierSet(policyInformation2.getPolicyQualifiers());
                        List list = listArr[i5 - 1];
                        for (int i6 = 0; i6 < list.size(); i6++) {
                            PKIXPolicyNode pKIXPolicyNode2 = (PKIXPolicyNode) list.get(i6);
                            for (Object obj2 : pKIXPolicyNode2.getExpectedPolicies()) {
                                if (obj2 instanceof String) {
                                    str = (String) obj2;
                                } else if (obj2 instanceof ASN1ObjectIdentifier) {
                                    str = ((ASN1ObjectIdentifier) obj2).getId();
                                }
                                String str2 = str;
                                Iterator children = pKIXPolicyNode2.getChildren();
                                boolean z3 = false;
                                while (children.hasNext()) {
                                    if (str2.equals(((PKIXPolicyNode) children.next()).getValidPolicy())) {
                                        z3 = true;
                                    }
                                }
                                if (!z3) {
                                    HashSet hashSet3 = new HashSet();
                                    hashSet3.add(str2);
                                    PKIXPolicyNode pKIXPolicyNode3 = new PKIXPolicyNode(new ArrayList(), i5, hashSet3, pKIXPolicyNode2, qualifierSet2, str2, false);
                                    pKIXPolicyNode2.addChild(pKIXPolicyNode3);
                                    listArr[i5].add(pKIXPolicyNode3);
                                }
                            }
                        }
                    }
                }
            }
            PKIXPolicyNode pKIXPolicyNode4 = pKIXPolicyNode;
            for (int i7 = i5 - 1; i7 >= 0; i7--) {
                List list2 = listArr[i7];
                while (i4 < list2.size()) {
                    PKIXPolicyNode pKIXPolicyNode5 = (PKIXPolicyNode) list2.get(i4);
                    i4 = (pKIXPolicyNode5.hasChildren() || (pKIXPolicyNode4 = CertPathValidatorUtilities.removePolicyNode(pKIXPolicyNode4, listArr, pKIXPolicyNode5)) != null) ? i4 + 1 : 0;
                }
            }
            Set<String> criticalExtensionOIDs = x509Certificate.getCriticalExtensionOIDs();
            if (criticalExtensionOIDs != null) {
                boolean contains = criticalExtensionOIDs.contains(CERTIFICATE_POLICIES);
                List list3 = listArr[i5];
                for (int i8 = 0; i8 < list3.size(); i8++) {
                    ((PKIXPolicyNode) list3.get(i8)).setCritical(contains);
                }
            }
            return pKIXPolicyNode4;
        } catch (AnnotatedException e3) {
            throw new ExtCertPathValidatorException("Could not read certificate policies extension from certificate.", e3, certPath, i2);
        }
    }

    public static PKIXPolicyNode processCertE(CertPath certPath, int i2, PKIXPolicyNode pKIXPolicyNode) {
        try {
            if (ASN1Sequence.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Certificate) certPath.getCertificates().get(i2), CERTIFICATE_POLICIES)) == null) {
                return null;
            }
            return pKIXPolicyNode;
        } catch (AnnotatedException e2) {
            throw new ExtCertPathValidatorException("Could not read certificate policies extension from certificate.", e2, certPath, i2);
        }
    }

    public static void processCertF(CertPath certPath, int i2, PKIXPolicyNode pKIXPolicyNode, int i3) {
        if (i3 <= 0 && pKIXPolicyNode == null) {
            throw new ExtCertPathValidatorException("No valid policy tree found when one expected.", null, certPath, i2);
        }
    }

    public static int wrapupCertA(int i2, X509Certificate x509Certificate) {
        return (CertPathValidatorUtilities.isSelfIssued(x509Certificate) || i2 == 0) ? i2 : i2 - 1;
    }

    public static int wrapupCertB(CertPath certPath, int i2, int i3) {
        try {
            ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Certificate) certPath.getCertificates().get(i2), POLICY_CONSTRAINTS));
            if (aSN1Sequence != null) {
                Enumeration objects = aSN1Sequence.getObjects();
                while (objects.hasMoreElements()) {
                    ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) objects.nextElement();
                    if (aSN1TaggedObject.getTagNo() == 0) {
                        try {
                            if (ASN1Integer.getInstance(aSN1TaggedObject, false).intValueExact() == 0) {
                                return 0;
                            }
                        } catch (Exception e2) {
                            throw new ExtCertPathValidatorException("Policy constraints requireExplicitPolicy field could not be decoded.", e2, certPath, i2);
                        }
                    }
                }
            }
            return i3;
        } catch (AnnotatedException e3) {
            throw new ExtCertPathValidatorException("Policy constraints could not be decoded.", e3, certPath, i2);
        }
    }

    public static void wrapupCertF(CertPath certPath, int i2, List list, Set set) {
        X509Certificate x509Certificate = (X509Certificate) certPath.getCertificates().get(i2);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            try {
                ((PKIXCertPathChecker) it.next()).check(x509Certificate, set);
            } catch (CertPathValidatorException e2) {
                throw new ExtCertPathValidatorException(e2.getMessage(), e2, certPath, i2);
            } catch (Exception e3) {
                throw new CertPathValidatorException("Additional certificate path checker failed.", e3, certPath, i2);
            }
        }
        if (set.isEmpty()) {
            return;
        }
        throw new ExtCertPathValidatorException("Certificate has unsupported critical extension: " + set, null, certPath, i2);
    }

    public static PKIXPolicyNode wrapupCertG(CertPath certPath, PKIXExtendedParameters pKIXExtendedParameters, Set set, int i2, List[] listArr, PKIXPolicyNode pKIXPolicyNode, Set set2) {
        int size = certPath.getCertificates().size();
        if (pKIXPolicyNode == null) {
            if (pKIXExtendedParameters.isExplicitPolicyRequired()) {
                throw new ExtCertPathValidatorException("Explicit policy requested but none available.", null, certPath, i2);
            }
            return null;
        }
        if (!CertPathValidatorUtilities.isAnyPolicy(set)) {
            HashSet hashSet = new HashSet();
            for (List list : listArr) {
                for (int i3 = 0; i3 < list.size(); i3++) {
                    PKIXPolicyNode pKIXPolicyNode2 = (PKIXPolicyNode) list.get(i3);
                    if (ANY_POLICY.equals(pKIXPolicyNode2.getValidPolicy())) {
                        Iterator children = pKIXPolicyNode2.getChildren();
                        while (children.hasNext()) {
                            PKIXPolicyNode pKIXPolicyNode3 = (PKIXPolicyNode) children.next();
                            if (!ANY_POLICY.equals(pKIXPolicyNode3.getValidPolicy())) {
                                hashSet.add(pKIXPolicyNode3);
                            }
                        }
                    }
                }
            }
            Iterator it = hashSet.iterator();
            while (it.hasNext()) {
                PKIXPolicyNode pKIXPolicyNode4 = (PKIXPolicyNode) it.next();
                if (!set.contains(pKIXPolicyNode4.getValidPolicy())) {
                    pKIXPolicyNode = CertPathValidatorUtilities.removePolicyNode(pKIXPolicyNode, listArr, pKIXPolicyNode4);
                }
            }
            if (pKIXPolicyNode != null) {
                for (int i4 = size - 1; i4 >= 0; i4--) {
                    List list2 = listArr[i4];
                    for (int i5 = 0; i5 < list2.size(); i5++) {
                        PKIXPolicyNode pKIXPolicyNode5 = (PKIXPolicyNode) list2.get(i5);
                        if (!pKIXPolicyNode5.hasChildren()) {
                            pKIXPolicyNode = CertPathValidatorUtilities.removePolicyNode(pKIXPolicyNode, listArr, pKIXPolicyNode5);
                        }
                    }
                }
            }
        } else if (pKIXExtendedParameters.isExplicitPolicyRequired()) {
            if (set2.isEmpty()) {
                throw new ExtCertPathValidatorException("Explicit policy requested but none available.", null, certPath, i2);
            }
            HashSet hashSet2 = new HashSet();
            for (List list3 : listArr) {
                for (int i6 = 0; i6 < list3.size(); i6++) {
                    PKIXPolicyNode pKIXPolicyNode6 = (PKIXPolicyNode) list3.get(i6);
                    if (ANY_POLICY.equals(pKIXPolicyNode6.getValidPolicy())) {
                        Iterator children2 = pKIXPolicyNode6.getChildren();
                        while (children2.hasNext()) {
                            hashSet2.add(children2.next());
                        }
                    }
                }
            }
            Iterator it2 = hashSet2.iterator();
            while (it2.hasNext()) {
                set2.contains(((PKIXPolicyNode) it2.next()).getValidPolicy());
            }
            for (int i7 = size - 1; i7 >= 0; i7--) {
                List list4 = listArr[i7];
                for (int i8 = 0; i8 < list4.size(); i8++) {
                    PKIXPolicyNode pKIXPolicyNode7 = (PKIXPolicyNode) list4.get(i8);
                    if (!pKIXPolicyNode7.hasChildren()) {
                        pKIXPolicyNode = CertPathValidatorUtilities.removePolicyNode(pKIXPolicyNode, listArr, pKIXPolicyNode7);
                    }
                }
            }
        }
        return pKIXPolicyNode;
    }
}
