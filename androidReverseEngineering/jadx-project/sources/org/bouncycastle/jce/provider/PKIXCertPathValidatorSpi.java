package org.bouncycastle.jce.provider;

import java.security.InvalidAlgorithmParameterException;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathParameters;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertPathValidatorSpi;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.TBSCertificate;
import org.bouncycastle.jcajce.PKIXExtendedBuilderParameters;
import org.bouncycastle.jcajce.PKIXExtendedParameters;
import org.bouncycastle.jcajce.interfaces.BCX509Certificate;
import org.bouncycastle.jcajce.util.BCJcaJceHelper;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.jce.exception.ExtCertPathValidatorException;
import org.bouncycastle.x509.ExtendedPKIXParameters;

/* loaded from: classes.dex */
public class PKIXCertPathValidatorSpi extends CertPathValidatorSpi {
    private final JcaJceHelper helper;
    private final boolean isForCRLCheck;

    public PKIXCertPathValidatorSpi() {
        this(false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void checkCertificate(X509Certificate x509Certificate) {
        if (x509Certificate instanceof BCX509Certificate) {
            try {
            } catch (RuntimeException e2) {
                e = e2;
            }
            if (((BCX509Certificate) x509Certificate).getTBSCertificateNative() != null) {
                return;
            }
            e = null;
            throw new AnnotatedException("unable to process TBSCertificate", e);
        }
        try {
            TBSCertificate.getInstance(x509Certificate.getTBSCertificate());
        } catch (IllegalArgumentException e3) {
            throw new AnnotatedException(e3.getMessage());
        } catch (CertificateEncodingException e4) {
            throw new AnnotatedException("unable to process TBSCertificate", e4);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v8, types: [org.bouncycastle.asn1.x509.AlgorithmIdentifier] */
    @Override // java.security.cert.CertPathValidatorSpi
    public CertPathValidatorResult engineValidate(CertPath certPath, CertPathParameters certPathParameters) {
        PKIXExtendedParameters pKIXExtendedParameters;
        int i2;
        List<? extends Certificate> list;
        X500Name ca;
        PublicKey cAPublicKey;
        HashSet hashSet;
        int i3;
        ArrayList[] arrayListArr;
        List list2;
        int i4;
        HashSet hashSet2;
        if (certPathParameters instanceof PKIXParameters) {
            PKIXExtendedParameters.Builder builder = new PKIXExtendedParameters.Builder((PKIXParameters) certPathParameters);
            if (certPathParameters instanceof ExtendedPKIXParameters) {
                ExtendedPKIXParameters extendedPKIXParameters = (ExtendedPKIXParameters) certPathParameters;
                builder.setUseDeltasEnabled(extendedPKIXParameters.isUseDeltasEnabled());
                builder.setValidityModel(extendedPKIXParameters.getValidityModel());
            }
            pKIXExtendedParameters = builder.build();
        } else if (certPathParameters instanceof PKIXExtendedBuilderParameters) {
            pKIXExtendedParameters = ((PKIXExtendedBuilderParameters) certPathParameters).getBaseParameters();
        } else {
            if (!(certPathParameters instanceof PKIXExtendedParameters)) {
                throw new InvalidAlgorithmParameterException("Parameters must be a " + PKIXParameters.class.getName() + " instance.");
            }
            pKIXExtendedParameters = (PKIXExtendedParameters) certPathParameters;
        }
        if (pKIXExtendedParameters.getTrustAnchors() == null) {
            throw new InvalidAlgorithmParameterException("trustAnchors is null, this is not allowed for certification path validation.");
        }
        List<? extends Certificate> certificates = certPath.getCertificates();
        int size = certificates.size();
        int i5 = -1;
        if (certificates.isEmpty()) {
            throw new CertPathValidatorException("Certification path is empty.", null, certPath, -1);
        }
        Date validityDate = CertPathValidatorUtilities.getValidityDate(pKIXExtendedParameters, new Date());
        Set initialPolicies = pKIXExtendedParameters.getInitialPolicies();
        try {
            TrustAnchor findTrustAnchor = CertPathValidatorUtilities.findTrustAnchor((X509Certificate) certificates.get(certificates.size() - 1), pKIXExtendedParameters.getTrustAnchors(), pKIXExtendedParameters.getSigProvider());
            if (findTrustAnchor == null) {
                i2 = 1;
                list = certificates;
                try {
                    throw new CertPathValidatorException("Trust anchor for certification path not found.", null, certPath, -1);
                } catch (AnnotatedException e2) {
                    e = e2;
                    throw new CertPathValidatorException(e.getMessage(), e.getUnderlyingException(), certPath, list.size() - i2);
                }
            }
            checkCertificate(findTrustAnchor.getTrustedCert());
            PKIXExtendedParameters build = new PKIXExtendedParameters.Builder(pKIXExtendedParameters).setTrustAnchor(findTrustAnchor).build();
            int i6 = size + 1;
            ArrayList[] arrayListArr2 = new ArrayList[i6];
            for (int i7 = 0; i7 < i6; i7++) {
                arrayListArr2[i7] = new ArrayList();
            }
            HashSet hashSet3 = new HashSet();
            hashSet3.add(RFC3280CertPathUtilities.ANY_POLICY);
            PKIXPolicyNode pKIXPolicyNode = new PKIXPolicyNode(new ArrayList(), 0, hashSet3, null, new HashSet(), RFC3280CertPathUtilities.ANY_POLICY, false);
            arrayListArr2[0].add(pKIXPolicyNode);
            PKIXNameConstraintValidator pKIXNameConstraintValidator = new PKIXNameConstraintValidator();
            HashSet hashSet4 = new HashSet();
            int i8 = build.isExplicitPolicyRequired() ? 0 : i6;
            int i9 = build.isAnyPolicyInhibited() ? 0 : i6;
            if (build.isPolicyMappingInhibited()) {
                i6 = 0;
            }
            X509Certificate trustedCert = findTrustAnchor.getTrustedCert();
            try {
                if (trustedCert != null) {
                    ca = PrincipalUtils.getSubjectPrincipal(trustedCert);
                    cAPublicKey = trustedCert.getPublicKey();
                } else {
                    ca = PrincipalUtils.getCA(findTrustAnchor);
                    cAPublicKey = findTrustAnchor.getCAPublicKey();
                }
                try {
                    i5 = CertPathValidatorUtilities.getAlgorithmIdentifier(cAPublicKey);
                    i5.getAlgorithm();
                    i5.getParameters();
                    if (build.getTargetConstraints() != null && !build.getTargetConstraints().match((Certificate) certificates.get(0))) {
                        throw new ExtCertPathValidatorException("Target certificate in certification path does not match targetConstraints.", null, certPath, 0);
                    }
                    List certPathCheckers = build.getCertPathCheckers();
                    Iterator it = certPathCheckers.iterator();
                    while (it.hasNext()) {
                        ((PKIXCertPathChecker) it.next()).init(false);
                    }
                    ProvCrlRevocationChecker provCrlRevocationChecker = build.isRevocationEnabled() ? new ProvCrlRevocationChecker(this.helper) : null;
                    int i10 = 1;
                    TrustAnchor trustAnchor = findTrustAnchor;
                    int i11 = size;
                    X509Certificate x509Certificate = null;
                    int i12 = i6;
                    int i13 = i9;
                    PKIXPolicyNode pKIXPolicyNode2 = pKIXPolicyNode;
                    int i14 = i8;
                    int size2 = certificates.size() - 1;
                    int i15 = i14;
                    while (size2 >= 0) {
                        int i16 = size - size2;
                        int i17 = size;
                        X509Certificate x509Certificate2 = (X509Certificate) certificates.get(size2);
                        int i18 = size2 == certificates.size() + (-1) ? i10 : 0;
                        try {
                            checkCertificate(x509Certificate2);
                            int i19 = i13;
                            List<? extends Certificate> list3 = certificates;
                            int i20 = i15;
                            int i21 = size2;
                            Date date = validityDate;
                            Date date2 = validityDate;
                            int i22 = i12;
                            ProvCrlRevocationChecker provCrlRevocationChecker2 = provCrlRevocationChecker;
                            ProvCrlRevocationChecker provCrlRevocationChecker3 = provCrlRevocationChecker;
                            PKIXNameConstraintValidator pKIXNameConstraintValidator2 = pKIXNameConstraintValidator;
                            ArrayList[] arrayListArr3 = arrayListArr2;
                            boolean z2 = i18;
                            TrustAnchor trustAnchor2 = trustAnchor;
                            PKIXExtendedParameters pKIXExtendedParameters2 = build;
                            List list4 = certPathCheckers;
                            int i23 = i10;
                            RFC3280CertPathUtilities.processCertA(certPath, build, date, provCrlRevocationChecker2, i21, cAPublicKey, z2, ca, trustedCert);
                            RFC3280CertPathUtilities.processCertBC(certPath, i21, pKIXNameConstraintValidator2, this.isForCRLCheck);
                            PKIXPolicyNode processCertE = RFC3280CertPathUtilities.processCertE(certPath, i21, RFC3280CertPathUtilities.processCertD(certPath, i21, hashSet4, pKIXPolicyNode2, arrayListArr3, i19, this.isForCRLCheck));
                            RFC3280CertPathUtilities.processCertF(certPath, i21, processCertE, i20);
                            if (i16 != i17) {
                                if (x509Certificate2 == null || x509Certificate2.getVersion() != i23) {
                                    RFC3280CertPathUtilities.prepareNextCertA(certPath, i21);
                                    arrayListArr = arrayListArr3;
                                    PKIXPolicyNode prepareCertB = RFC3280CertPathUtilities.prepareCertB(certPath, i21, arrayListArr, processCertE, i22);
                                    RFC3280CertPathUtilities.prepareNextCertG(certPath, i21, pKIXNameConstraintValidator2);
                                    int prepareNextCertH1 = RFC3280CertPathUtilities.prepareNextCertH1(certPath, i21, i20);
                                    int prepareNextCertH2 = RFC3280CertPathUtilities.prepareNextCertH2(certPath, i21, i22);
                                    int prepareNextCertH3 = RFC3280CertPathUtilities.prepareNextCertH3(certPath, i21, i19);
                                    i4 = RFC3280CertPathUtilities.prepareNextCertI1(certPath, i21, prepareNextCertH1);
                                    i3 = RFC3280CertPathUtilities.prepareNextCertI2(certPath, i21, prepareNextCertH2);
                                    int prepareNextCertJ = RFC3280CertPathUtilities.prepareNextCertJ(certPath, i21, prepareNextCertH3);
                                    RFC3280CertPathUtilities.prepareNextCertK(certPath, i21);
                                    i11 = RFC3280CertPathUtilities.prepareNextCertM(certPath, i21, RFC3280CertPathUtilities.prepareNextCertL(certPath, i21, i11));
                                    RFC3280CertPathUtilities.prepareNextCertN(certPath, i21);
                                    Set<String> criticalExtensionOIDs = x509Certificate2.getCriticalExtensionOIDs();
                                    if (criticalExtensionOIDs != null) {
                                        hashSet2 = new HashSet(criticalExtensionOIDs);
                                        hashSet2.remove(RFC3280CertPathUtilities.KEY_USAGE);
                                        hashSet2.remove(RFC3280CertPathUtilities.CERTIFICATE_POLICIES);
                                        hashSet2.remove(RFC3280CertPathUtilities.POLICY_MAPPINGS);
                                        hashSet2.remove(RFC3280CertPathUtilities.INHIBIT_ANY_POLICY);
                                        hashSet2.remove(RFC3280CertPathUtilities.ISSUING_DISTRIBUTION_POINT);
                                        hashSet2.remove(RFC3280CertPathUtilities.DELTA_CRL_INDICATOR);
                                        hashSet2.remove(RFC3280CertPathUtilities.POLICY_CONSTRAINTS);
                                        hashSet2.remove(RFC3280CertPathUtilities.BASIC_CONSTRAINTS);
                                        hashSet2.remove(RFC3280CertPathUtilities.SUBJECT_ALTERNATIVE_NAME);
                                        hashSet2.remove(RFC3280CertPathUtilities.NAME_CONSTRAINTS);
                                    } else {
                                        hashSet2 = new HashSet();
                                    }
                                    list2 = list4;
                                    RFC3280CertPathUtilities.prepareNextCertO(certPath, i21, hashSet2, list2);
                                    X500Name subjectPrincipal = PrincipalUtils.getSubjectPrincipal(x509Certificate2);
                                    try {
                                        PublicKey nextWorkingKey = CertPathValidatorUtilities.getNextWorkingKey(certPath.getCertificates(), i21, this.helper);
                                        AlgorithmIdentifier algorithmIdentifier = CertPathValidatorUtilities.getAlgorithmIdentifier(nextWorkingKey);
                                        algorithmIdentifier.getAlgorithm();
                                        algorithmIdentifier.getParameters();
                                        pKIXPolicyNode2 = prepareCertB;
                                        i13 = prepareNextCertJ;
                                        ca = subjectPrincipal;
                                        cAPublicKey = nextWorkingKey;
                                        trustedCert = x509Certificate2;
                                        arrayListArr2 = arrayListArr;
                                        certPathCheckers = list2;
                                        x509Certificate = x509Certificate2;
                                        i10 = i23;
                                        certificates = list3;
                                        validityDate = date2;
                                        build = pKIXExtendedParameters2;
                                        size = i17;
                                        i15 = i4;
                                        trustAnchor = trustAnchor2;
                                        i12 = i3;
                                        size2 = i21 - 1;
                                        pKIXNameConstraintValidator = pKIXNameConstraintValidator2;
                                        provCrlRevocationChecker = provCrlRevocationChecker3;
                                    } catch (CertPathValidatorException e3) {
                                        throw new CertPathValidatorException("Next working key could not be retrieved.", e3, certPath, i21);
                                    }
                                } else if (i16 != i23 || !x509Certificate2.equals(trustAnchor2.getTrustedCert())) {
                                    throw new CertPathValidatorException("Version 1 certificates can't be used as CA ones.", null, certPath, i21);
                                }
                            }
                            i3 = i22;
                            arrayListArr = arrayListArr3;
                            list2 = list4;
                            pKIXPolicyNode2 = processCertE;
                            i13 = i19;
                            i11 = i11;
                            i4 = i20;
                            arrayListArr2 = arrayListArr;
                            certPathCheckers = list2;
                            x509Certificate = x509Certificate2;
                            i10 = i23;
                            certificates = list3;
                            validityDate = date2;
                            build = pKIXExtendedParameters2;
                            size = i17;
                            i15 = i4;
                            trustAnchor = trustAnchor2;
                            i12 = i3;
                            size2 = i21 - 1;
                            pKIXNameConstraintValidator = pKIXNameConstraintValidator2;
                            provCrlRevocationChecker = provCrlRevocationChecker3;
                        } catch (AnnotatedException e4) {
                            throw new CertPathValidatorException(e4.getMessage(), e4.getUnderlyingException(), certPath, size2);
                        }
                    }
                    TrustAnchor trustAnchor3 = trustAnchor;
                    PKIXExtendedParameters pKIXExtendedParameters3 = build;
                    ArrayList[] arrayListArr4 = arrayListArr2;
                    List list5 = certPathCheckers;
                    int i24 = size2;
                    int i25 = i24 + 1;
                    int wrapupCertB = RFC3280CertPathUtilities.wrapupCertB(certPath, i25, RFC3280CertPathUtilities.wrapupCertA(i15, x509Certificate));
                    Set<String> criticalExtensionOIDs2 = x509Certificate.getCriticalExtensionOIDs();
                    if (criticalExtensionOIDs2 != null) {
                        hashSet = new HashSet(criticalExtensionOIDs2);
                        hashSet.remove(RFC3280CertPathUtilities.KEY_USAGE);
                        hashSet.remove(RFC3280CertPathUtilities.CERTIFICATE_POLICIES);
                        hashSet.remove(RFC3280CertPathUtilities.POLICY_MAPPINGS);
                        hashSet.remove(RFC3280CertPathUtilities.INHIBIT_ANY_POLICY);
                        hashSet.remove(RFC3280CertPathUtilities.ISSUING_DISTRIBUTION_POINT);
                        hashSet.remove(RFC3280CertPathUtilities.DELTA_CRL_INDICATOR);
                        hashSet.remove(RFC3280CertPathUtilities.POLICY_CONSTRAINTS);
                        hashSet.remove(RFC3280CertPathUtilities.BASIC_CONSTRAINTS);
                        hashSet.remove(RFC3280CertPathUtilities.SUBJECT_ALTERNATIVE_NAME);
                        hashSet.remove(RFC3280CertPathUtilities.NAME_CONSTRAINTS);
                        hashSet.remove(RFC3280CertPathUtilities.CRL_DISTRIBUTION_POINTS);
                        hashSet.remove(Extension.extendedKeyUsage.getId());
                    } else {
                        hashSet = new HashSet();
                    }
                    RFC3280CertPathUtilities.wrapupCertF(certPath, i25, list5, hashSet);
                    PKIXPolicyNode wrapupCertG = RFC3280CertPathUtilities.wrapupCertG(certPath, pKIXExtendedParameters3, initialPolicies, i25, arrayListArr4, pKIXPolicyNode2, hashSet4);
                    if (wrapupCertB > 0 || wrapupCertG != null) {
                        return new PKIXCertPathValidatorResult(trustAnchor3, wrapupCertG, x509Certificate.getPublicKey());
                    }
                    throw new CertPathValidatorException("Path processing failed on policy.", null, certPath, i24);
                } catch (CertPathValidatorException e5) {
                    throw new ExtCertPathValidatorException("Algorithm identifier of public key of trust anchor could not be read.", e5, certPath, -1);
                }
            } catch (RuntimeException e6) {
                throw new ExtCertPathValidatorException("Subject of trust anchor could not be (re)encoded.", e6, certPath, i5);
            }
        } catch (AnnotatedException e7) {
            e = e7;
            i2 = 1;
            list = certificates;
        }
    }

    public PKIXCertPathValidatorSpi(boolean z2) {
        this.helper = new BCJcaJceHelper();
        this.isForCRLCheck = z2;
    }
}
