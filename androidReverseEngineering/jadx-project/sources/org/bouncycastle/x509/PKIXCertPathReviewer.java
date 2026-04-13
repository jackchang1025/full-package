package org.bouncycastle.x509;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXParameters;
import java.security.cert.PolicyNode;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1Enumerated;
import org.bouncycastle.asn1.ASN1IA5String;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.x509.AccessDescription;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.AuthorityInformationAccess;
import org.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
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
import org.bouncycastle.asn1.x509.qualified.ETSIQCObjectIdentifiers;
import org.bouncycastle.asn1.x509.qualified.MonetaryValue;
import org.bouncycastle.asn1.x509.qualified.QCStatement;
import org.bouncycastle.asn1.x509.qualified.RFC3739QCObjectIdentifiers;
import org.bouncycastle.i18n.ErrorBundle;
import org.bouncycastle.i18n.LocaleString;
import org.bouncycastle.i18n.filter.TrustedInput;
import org.bouncycastle.i18n.filter.UntrustedInput;
import org.bouncycastle.i18n.filter.UntrustedUrlInput;
import org.bouncycastle.jce.provider.AnnotatedException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.provider.PKIXNameConstraintValidator;
import org.bouncycastle.jce.provider.PKIXNameConstraintValidatorException;
import org.bouncycastle.jce.provider.PKIXPolicyNode;
import org.bouncycastle.jce.provider.RFC3280CertPathUtilities;
import org.bouncycastle.util.Integers;
import org.bouncycastle.util.Objects;

/* loaded from: classes.dex */
public class PKIXCertPathReviewer extends CertPathValidatorUtilities {
    private static final String RESOURCE_NAME = "org.bouncycastle.x509.CertPathReviewerMessages";
    protected CertPath certPath;
    protected List certs;
    protected Date currentDate;
    protected List[] errors;
    private boolean initialized;

    /* renamed from: n */
    protected int f1660n;
    protected List[] notifications;
    protected PKIXParameters pkixParams;
    protected PolicyNode policyTree;
    protected PublicKey subjectPublicKey;
    protected TrustAnchor trustAnchor;
    protected Date validDate;
    private static final String QC_STATEMENT = Extension.qCStatements.getId();
    private static final String CRL_DIST_POINTS = Extension.cRLDistributionPoints.getId();
    private static final String AUTH_INFO_ACCESS = Extension.authorityInfoAccess.getId();

    public PKIXCertPathReviewer() {
    }

    public PKIXCertPathReviewer(CertPath certPath, PKIXParameters pKIXParameters) {
        init(certPath, pKIXParameters);
    }

    private String IPtoString(byte[] bArr) {
        try {
            return InetAddress.getByAddress(bArr).getHostAddress();
        } catch (Exception unused) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i2 = 0; i2 != bArr.length; i2++) {
                stringBuffer.append(Integer.toHexString(bArr[i2] & 255));
                stringBuffer.append(' ');
            }
            return stringBuffer.toString();
        }
    }

    private void checkCriticalExtensions() {
        List<PKIXCertPathChecker> certPathCheckers = this.pkixParams.getCertPathCheckers();
        Iterator<PKIXCertPathChecker> it = certPathCheckers.iterator();
        while (it.hasNext()) {
            try {
                try {
                    it.next().init(false);
                } catch (CertPathValidatorException e2) {
                    throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.certPathCheckerError", new Object[]{e2.getMessage(), e2, e2.getClass().getName()}), e2);
                }
            } catch (CertPathReviewerException e3) {
                addError(e3.getErrorMessage(), e3.getIndex());
                return;
            }
        }
        for (int size = this.certs.size() - 1; size >= 0; size--) {
            X509Certificate x509Certificate = (X509Certificate) this.certs.get(size);
            Set<String> criticalExtensionOIDs = x509Certificate.getCriticalExtensionOIDs();
            if (criticalExtensionOIDs != null && !criticalExtensionOIDs.isEmpty()) {
                criticalExtensionOIDs.remove(CertPathValidatorUtilities.KEY_USAGE);
                criticalExtensionOIDs.remove(CertPathValidatorUtilities.CERTIFICATE_POLICIES);
                criticalExtensionOIDs.remove(CertPathValidatorUtilities.POLICY_MAPPINGS);
                criticalExtensionOIDs.remove(CertPathValidatorUtilities.INHIBIT_ANY_POLICY);
                criticalExtensionOIDs.remove(CertPathValidatorUtilities.ISSUING_DISTRIBUTION_POINT);
                criticalExtensionOIDs.remove(CertPathValidatorUtilities.DELTA_CRL_INDICATOR);
                criticalExtensionOIDs.remove(CertPathValidatorUtilities.POLICY_CONSTRAINTS);
                criticalExtensionOIDs.remove(CertPathValidatorUtilities.BASIC_CONSTRAINTS);
                criticalExtensionOIDs.remove(CertPathValidatorUtilities.SUBJECT_ALTERNATIVE_NAME);
                criticalExtensionOIDs.remove(CertPathValidatorUtilities.NAME_CONSTRAINTS);
                if (size == 0) {
                    criticalExtensionOIDs.remove(Extension.extendedKeyUsage.getId());
                }
                String str = QC_STATEMENT;
                if (criticalExtensionOIDs.contains(str) && processQcStatements(x509Certificate, size)) {
                    criticalExtensionOIDs.remove(str);
                }
                Iterator<PKIXCertPathChecker> it2 = certPathCheckers.iterator();
                while (it2.hasNext()) {
                    try {
                        it2.next().check(x509Certificate, criticalExtensionOIDs);
                    } catch (CertPathValidatorException e4) {
                        throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.criticalExtensionError", new Object[]{e4.getMessage(), e4, e4.getClass().getName()}), e4.getCause(), this.certPath, size);
                    }
                }
                if (!criticalExtensionOIDs.isEmpty()) {
                    Iterator<String> it3 = criticalExtensionOIDs.iterator();
                    while (it3.hasNext()) {
                        addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.unknownCriticalExt", new Object[]{new ASN1ObjectIdentifier(it3.next())}), size);
                    }
                }
            }
        }
    }

    private void checkNameConstraints() {
        PKIXNameConstraintValidator pKIXNameConstraintValidator = new PKIXNameConstraintValidator();
        try {
            for (int size = this.certs.size() - 1; size > 0; size--) {
                X509Certificate x509Certificate = (X509Certificate) this.certs.get(size);
                if (!CertPathValidatorUtilities.isSelfIssued(x509Certificate)) {
                    X500Principal subjectPrincipal = CertPathValidatorUtilities.getSubjectPrincipal(x509Certificate);
                    try {
                        ASN1Sequence aSN1Sequence = (ASN1Sequence) new ASN1InputStream(new ByteArrayInputStream(subjectPrincipal.getEncoded())).readObject();
                        try {
                            pKIXNameConstraintValidator.checkPermittedDN(aSN1Sequence);
                            try {
                                pKIXNameConstraintValidator.checkExcludedDN(aSN1Sequence);
                                try {
                                    ASN1Sequence aSN1Sequence2 = (ASN1Sequence) CertPathValidatorUtilities.getExtensionValue(x509Certificate, CertPathValidatorUtilities.SUBJECT_ALTERNATIVE_NAME);
                                    if (aSN1Sequence2 != null) {
                                        for (int i2 = 0; i2 < aSN1Sequence2.size(); i2++) {
                                            GeneralName generalName = GeneralName.getInstance(aSN1Sequence2.getObjectAt(i2));
                                            try {
                                                pKIXNameConstraintValidator.checkPermitted(generalName);
                                                pKIXNameConstraintValidator.checkExcluded(generalName);
                                            } catch (PKIXNameConstraintValidatorException e2) {
                                                throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.notPermittedEmail", new Object[]{new UntrustedInput(generalName)}), e2, this.certPath, size);
                                            }
                                        }
                                    }
                                } catch (AnnotatedException e3) {
                                    throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.subjAltNameExtError"), e3, this.certPath, size);
                                }
                            } catch (PKIXNameConstraintValidatorException e4) {
                                throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.excludedDN", new Object[]{new UntrustedInput(subjectPrincipal.getName())}), e4, this.certPath, size);
                            }
                        } catch (PKIXNameConstraintValidatorException e5) {
                            throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.notPermittedDN", new Object[]{new UntrustedInput(subjectPrincipal.getName())}), e5, this.certPath, size);
                        }
                    } catch (IOException e6) {
                        throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.ncSubjectNameError", new Object[]{new UntrustedInput(subjectPrincipal)}), e6, this.certPath, size);
                    }
                }
                try {
                    ASN1Sequence aSN1Sequence3 = (ASN1Sequence) CertPathValidatorUtilities.getExtensionValue(x509Certificate, CertPathValidatorUtilities.NAME_CONSTRAINTS);
                    if (aSN1Sequence3 != null) {
                        NameConstraints nameConstraints = NameConstraints.getInstance(aSN1Sequence3);
                        GeneralSubtree[] permittedSubtrees = nameConstraints.getPermittedSubtrees();
                        if (permittedSubtrees != null) {
                            pKIXNameConstraintValidator.intersectPermittedSubtree(permittedSubtrees);
                        }
                        GeneralSubtree[] excludedSubtrees = nameConstraints.getExcludedSubtrees();
                        if (excludedSubtrees != null) {
                            for (int i3 = 0; i3 != excludedSubtrees.length; i3++) {
                                pKIXNameConstraintValidator.addExcludedSubtree(excludedSubtrees[i3]);
                            }
                        }
                    }
                } catch (AnnotatedException e7) {
                    throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.ncExtError"), e7, this.certPath, size);
                }
            }
        } catch (CertPathReviewerException e8) {
            addError(e8.getErrorMessage(), e8.getIndex());
        }
    }

    private void checkPathLength() {
        BasicConstraints basicConstraints;
        BigInteger pathLenConstraint;
        int intValue;
        int i2 = this.f1660n;
        int i3 = 0;
        for (int size = this.certs.size() - 1; size > 0; size--) {
            X509Certificate x509Certificate = (X509Certificate) this.certs.get(size);
            if (!CertPathValidatorUtilities.isSelfIssued(x509Certificate)) {
                if (i2 <= 0) {
                    addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.pathLengthExtended"));
                }
                i2--;
                i3++;
            }
            try {
                basicConstraints = BasicConstraints.getInstance(CertPathValidatorUtilities.getExtensionValue(x509Certificate, CertPathValidatorUtilities.BASIC_CONSTRAINTS));
            } catch (AnnotatedException unused) {
                addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.processLengthConstError"), size);
                basicConstraints = null;
            }
            if (basicConstraints != null && (pathLenConstraint = basicConstraints.getPathLenConstraint()) != null && (intValue = pathLenConstraint.intValue()) < i2) {
                i2 = intValue;
            }
        }
        addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.totalPathLength", new Object[]{Integers.valueOf(i3)}));
    }

    /* JADX WARN: Removed duplicated region for block: B:226:0x013d A[Catch: CertPathReviewerException -> 0x05f7, TRY_LEAVE, TryCatch #7 {CertPathReviewerException -> 0x05f7, blocks: (B:15:0x006f, B:19:0x007f, B:22:0x008c, B:26:0x009c, B:27:0x00a7, B:29:0x00ad, B:32:0x00ce, B:33:0x00d6, B:35:0x00dc, B:41:0x00e1, B:42:0x00ed, B:48:0x00f9, B:51:0x0100, B:52:0x0109, B:54:0x010f, B:57:0x0119, B:63:0x0120, B:65:0x0124, B:69:0x0210, B:71:0x0216, B:72:0x0219, B:74:0x021f, B:76:0x022b, B:83:0x0233, B:81:0x0236, B:87:0x0239, B:89:0x023f, B:90:0x0248, B:92:0x024e, B:101:0x0271, B:102:0x027d, B:103:0x027e, B:109:0x0282, B:111:0x028a, B:112:0x028e, B:114:0x0294, B:117:0x02b6, B:119:0x02c0, B:121:0x02c5, B:122:0x02d1, B:124:0x02d2, B:125:0x02de, B:128:0x02e1, B:129:0x02ee, B:131:0x02f4, B:133:0x031a, B:135:0x0332, B:136:0x0329, B:139:0x0339, B:140:0x033f, B:142:0x0345, B:151:0x034d, B:146:0x0377, B:157:0x0355, B:158:0x0361, B:160:0x0363, B:161:0x0372, B:163:0x0380, B:172:0x039f, B:174:0x03a9, B:175:0x03ad, B:177:0x03b3, B:191:0x03c3, B:180:0x03d0, B:201:0x03dd, B:203:0x03e7, B:107:0x0425, B:210:0x03ef, B:211:0x03fd, B:213:0x03fe, B:214:0x040c, B:221:0x040e, B:222:0x041c, B:223:0x0133, B:224:0x0137, B:226:0x013d, B:229:0x0153, B:231:0x015d, B:232:0x0162, B:234:0x0168, B:235:0x0176, B:237:0x017c, B:263:0x0188, B:247:0x0195, B:248:0x019b, B:250:0x01a1, B:258:0x01ba, B:239:0x018b, B:246:0x018f, B:265:0x01f3, B:270:0x0203, B:271:0x020f, B:278:0x0434, B:279:0x0441, B:281:0x0442, B:286:0x0453, B:288:0x045d, B:289:0x0462, B:291:0x0468, B:294:0x0476, B:308:0x048b, B:315:0x05dd, B:316:0x05e9, B:318:0x0496, B:319:0x04a2, B:320:0x04a3, B:322:0x04a9, B:324:0x04b1, B:326:0x04b7, B:329:0x04c1, B:330:0x04c4, B:332:0x04ca, B:334:0x04da, B:335:0x04de, B:337:0x04e4, B:339:0x04ec, B:342:0x04ef, B:344:0x04f4, B:345:0x04f8, B:347:0x04fe, B:349:0x050c, B:351:0x0514, B:352:0x0517, B:354:0x051d, B:356:0x0529, B:358:0x052d, B:361:0x0530, B:363:0x0533, B:364:0x053f, B:366:0x0544, B:368:0x054e, B:369:0x0551, B:371:0x0557, B:373:0x0567, B:374:0x056b, B:376:0x0571, B:379:0x0581, B:384:0x0585, B:387:0x0588, B:389:0x058b, B:390:0x0591, B:392:0x0597, B:394:0x05a9, B:400:0x05b3, B:402:0x05b9, B:403:0x05bc, B:405:0x05c2, B:407:0x05ce, B:409:0x05d2, B:412:0x05d5, B:416:0x05ea, B:417:0x05f6), top: B:14:0x006f, inners: #0, #1, #2, #3, #5, #6, #8, #10 }] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0120 A[Catch: CertPathReviewerException -> 0x05f7, TryCatch #7 {CertPathReviewerException -> 0x05f7, blocks: (B:15:0x006f, B:19:0x007f, B:22:0x008c, B:26:0x009c, B:27:0x00a7, B:29:0x00ad, B:32:0x00ce, B:33:0x00d6, B:35:0x00dc, B:41:0x00e1, B:42:0x00ed, B:48:0x00f9, B:51:0x0100, B:52:0x0109, B:54:0x010f, B:57:0x0119, B:63:0x0120, B:65:0x0124, B:69:0x0210, B:71:0x0216, B:72:0x0219, B:74:0x021f, B:76:0x022b, B:83:0x0233, B:81:0x0236, B:87:0x0239, B:89:0x023f, B:90:0x0248, B:92:0x024e, B:101:0x0271, B:102:0x027d, B:103:0x027e, B:109:0x0282, B:111:0x028a, B:112:0x028e, B:114:0x0294, B:117:0x02b6, B:119:0x02c0, B:121:0x02c5, B:122:0x02d1, B:124:0x02d2, B:125:0x02de, B:128:0x02e1, B:129:0x02ee, B:131:0x02f4, B:133:0x031a, B:135:0x0332, B:136:0x0329, B:139:0x0339, B:140:0x033f, B:142:0x0345, B:151:0x034d, B:146:0x0377, B:157:0x0355, B:158:0x0361, B:160:0x0363, B:161:0x0372, B:163:0x0380, B:172:0x039f, B:174:0x03a9, B:175:0x03ad, B:177:0x03b3, B:191:0x03c3, B:180:0x03d0, B:201:0x03dd, B:203:0x03e7, B:107:0x0425, B:210:0x03ef, B:211:0x03fd, B:213:0x03fe, B:214:0x040c, B:221:0x040e, B:222:0x041c, B:223:0x0133, B:224:0x0137, B:226:0x013d, B:229:0x0153, B:231:0x015d, B:232:0x0162, B:234:0x0168, B:235:0x0176, B:237:0x017c, B:263:0x0188, B:247:0x0195, B:248:0x019b, B:250:0x01a1, B:258:0x01ba, B:239:0x018b, B:246:0x018f, B:265:0x01f3, B:270:0x0203, B:271:0x020f, B:278:0x0434, B:279:0x0441, B:281:0x0442, B:286:0x0453, B:288:0x045d, B:289:0x0462, B:291:0x0468, B:294:0x0476, B:308:0x048b, B:315:0x05dd, B:316:0x05e9, B:318:0x0496, B:319:0x04a2, B:320:0x04a3, B:322:0x04a9, B:324:0x04b1, B:326:0x04b7, B:329:0x04c1, B:330:0x04c4, B:332:0x04ca, B:334:0x04da, B:335:0x04de, B:337:0x04e4, B:339:0x04ec, B:342:0x04ef, B:344:0x04f4, B:345:0x04f8, B:347:0x04fe, B:349:0x050c, B:351:0x0514, B:352:0x0517, B:354:0x051d, B:356:0x0529, B:358:0x052d, B:361:0x0530, B:363:0x0533, B:364:0x053f, B:366:0x0544, B:368:0x054e, B:369:0x0551, B:371:0x0557, B:373:0x0567, B:374:0x056b, B:376:0x0571, B:379:0x0581, B:384:0x0585, B:387:0x0588, B:389:0x058b, B:390:0x0591, B:392:0x0597, B:394:0x05a9, B:400:0x05b3, B:402:0x05b9, B:403:0x05bc, B:405:0x05c2, B:407:0x05ce, B:409:0x05d2, B:412:0x05d5, B:416:0x05ea, B:417:0x05f6), top: B:14:0x006f, inners: #0, #1, #2, #3, #5, #6, #8, #10 }] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0216 A[Catch: CertPathReviewerException -> 0x05f7, TryCatch #7 {CertPathReviewerException -> 0x05f7, blocks: (B:15:0x006f, B:19:0x007f, B:22:0x008c, B:26:0x009c, B:27:0x00a7, B:29:0x00ad, B:32:0x00ce, B:33:0x00d6, B:35:0x00dc, B:41:0x00e1, B:42:0x00ed, B:48:0x00f9, B:51:0x0100, B:52:0x0109, B:54:0x010f, B:57:0x0119, B:63:0x0120, B:65:0x0124, B:69:0x0210, B:71:0x0216, B:72:0x0219, B:74:0x021f, B:76:0x022b, B:83:0x0233, B:81:0x0236, B:87:0x0239, B:89:0x023f, B:90:0x0248, B:92:0x024e, B:101:0x0271, B:102:0x027d, B:103:0x027e, B:109:0x0282, B:111:0x028a, B:112:0x028e, B:114:0x0294, B:117:0x02b6, B:119:0x02c0, B:121:0x02c5, B:122:0x02d1, B:124:0x02d2, B:125:0x02de, B:128:0x02e1, B:129:0x02ee, B:131:0x02f4, B:133:0x031a, B:135:0x0332, B:136:0x0329, B:139:0x0339, B:140:0x033f, B:142:0x0345, B:151:0x034d, B:146:0x0377, B:157:0x0355, B:158:0x0361, B:160:0x0363, B:161:0x0372, B:163:0x0380, B:172:0x039f, B:174:0x03a9, B:175:0x03ad, B:177:0x03b3, B:191:0x03c3, B:180:0x03d0, B:201:0x03dd, B:203:0x03e7, B:107:0x0425, B:210:0x03ef, B:211:0x03fd, B:213:0x03fe, B:214:0x040c, B:221:0x040e, B:222:0x041c, B:223:0x0133, B:224:0x0137, B:226:0x013d, B:229:0x0153, B:231:0x015d, B:232:0x0162, B:234:0x0168, B:235:0x0176, B:237:0x017c, B:263:0x0188, B:247:0x0195, B:248:0x019b, B:250:0x01a1, B:258:0x01ba, B:239:0x018b, B:246:0x018f, B:265:0x01f3, B:270:0x0203, B:271:0x020f, B:278:0x0434, B:279:0x0441, B:281:0x0442, B:286:0x0453, B:288:0x045d, B:289:0x0462, B:291:0x0468, B:294:0x0476, B:308:0x048b, B:315:0x05dd, B:316:0x05e9, B:318:0x0496, B:319:0x04a2, B:320:0x04a3, B:322:0x04a9, B:324:0x04b1, B:326:0x04b7, B:329:0x04c1, B:330:0x04c4, B:332:0x04ca, B:334:0x04da, B:335:0x04de, B:337:0x04e4, B:339:0x04ec, B:342:0x04ef, B:344:0x04f4, B:345:0x04f8, B:347:0x04fe, B:349:0x050c, B:351:0x0514, B:352:0x0517, B:354:0x051d, B:356:0x0529, B:358:0x052d, B:361:0x0530, B:363:0x0533, B:364:0x053f, B:366:0x0544, B:368:0x054e, B:369:0x0551, B:371:0x0557, B:373:0x0567, B:374:0x056b, B:376:0x0571, B:379:0x0581, B:384:0x0585, B:387:0x0588, B:389:0x058b, B:390:0x0591, B:392:0x0597, B:394:0x05a9, B:400:0x05b3, B:402:0x05b9, B:403:0x05bc, B:405:0x05c2, B:407:0x05ce, B:409:0x05d2, B:412:0x05d5, B:416:0x05ea, B:417:0x05f6), top: B:14:0x006f, inners: #0, #1, #2, #3, #5, #6, #8, #10 }] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x023f A[Catch: CertPathReviewerException -> 0x05f7, TryCatch #7 {CertPathReviewerException -> 0x05f7, blocks: (B:15:0x006f, B:19:0x007f, B:22:0x008c, B:26:0x009c, B:27:0x00a7, B:29:0x00ad, B:32:0x00ce, B:33:0x00d6, B:35:0x00dc, B:41:0x00e1, B:42:0x00ed, B:48:0x00f9, B:51:0x0100, B:52:0x0109, B:54:0x010f, B:57:0x0119, B:63:0x0120, B:65:0x0124, B:69:0x0210, B:71:0x0216, B:72:0x0219, B:74:0x021f, B:76:0x022b, B:83:0x0233, B:81:0x0236, B:87:0x0239, B:89:0x023f, B:90:0x0248, B:92:0x024e, B:101:0x0271, B:102:0x027d, B:103:0x027e, B:109:0x0282, B:111:0x028a, B:112:0x028e, B:114:0x0294, B:117:0x02b6, B:119:0x02c0, B:121:0x02c5, B:122:0x02d1, B:124:0x02d2, B:125:0x02de, B:128:0x02e1, B:129:0x02ee, B:131:0x02f4, B:133:0x031a, B:135:0x0332, B:136:0x0329, B:139:0x0339, B:140:0x033f, B:142:0x0345, B:151:0x034d, B:146:0x0377, B:157:0x0355, B:158:0x0361, B:160:0x0363, B:161:0x0372, B:163:0x0380, B:172:0x039f, B:174:0x03a9, B:175:0x03ad, B:177:0x03b3, B:191:0x03c3, B:180:0x03d0, B:201:0x03dd, B:203:0x03e7, B:107:0x0425, B:210:0x03ef, B:211:0x03fd, B:213:0x03fe, B:214:0x040c, B:221:0x040e, B:222:0x041c, B:223:0x0133, B:224:0x0137, B:226:0x013d, B:229:0x0153, B:231:0x015d, B:232:0x0162, B:234:0x0168, B:235:0x0176, B:237:0x017c, B:263:0x0188, B:247:0x0195, B:248:0x019b, B:250:0x01a1, B:258:0x01ba, B:239:0x018b, B:246:0x018f, B:265:0x01f3, B:270:0x0203, B:271:0x020f, B:278:0x0434, B:279:0x0441, B:281:0x0442, B:286:0x0453, B:288:0x045d, B:289:0x0462, B:291:0x0468, B:294:0x0476, B:308:0x048b, B:315:0x05dd, B:316:0x05e9, B:318:0x0496, B:319:0x04a2, B:320:0x04a3, B:322:0x04a9, B:324:0x04b1, B:326:0x04b7, B:329:0x04c1, B:330:0x04c4, B:332:0x04ca, B:334:0x04da, B:335:0x04de, B:337:0x04e4, B:339:0x04ec, B:342:0x04ef, B:344:0x04f4, B:345:0x04f8, B:347:0x04fe, B:349:0x050c, B:351:0x0514, B:352:0x0517, B:354:0x051d, B:356:0x0529, B:358:0x052d, B:361:0x0530, B:363:0x0533, B:364:0x053f, B:366:0x0544, B:368:0x054e, B:369:0x0551, B:371:0x0557, B:373:0x0567, B:374:0x056b, B:376:0x0571, B:379:0x0581, B:384:0x0585, B:387:0x0588, B:389:0x058b, B:390:0x0591, B:392:0x0597, B:394:0x05a9, B:400:0x05b3, B:402:0x05b9, B:403:0x05bc, B:405:0x05c2, B:407:0x05ce, B:409:0x05d2, B:412:0x05d5, B:416:0x05ea, B:417:0x05f6), top: B:14:0x006f, inners: #0, #1, #2, #3, #5, #6, #8, #10 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void checkPolicy() {
        int i2;
        int i3;
        int i4;
        PKIXPolicyNode pKIXPolicyNode;
        Set<String> set;
        String str;
        int i5;
        int i6;
        HashSet hashSet;
        String str2;
        int i7;
        int intValueExact;
        int intValueExact2;
        String str3;
        HashSet hashSet2;
        Enumeration objects;
        HashSet hashSet3;
        String str4;
        int i8;
        int i9;
        Set<String> criticalExtensionOIDs;
        String str5 = "CertPathReviewer.policyExtError";
        Set<String> initialPolicies = this.pkixParams.getInitialPolicies();
        int i10 = this.f1660n + 1;
        ArrayList[] arrayListArr = new ArrayList[i10];
        for (int i11 = 0; i11 < i10; i11++) {
            arrayListArr[i11] = new ArrayList();
        }
        HashSet hashSet4 = new HashSet();
        hashSet4.add(RFC3280CertPathUtilities.ANY_POLICY);
        PKIXPolicyNode pKIXPolicyNode2 = new PKIXPolicyNode(new ArrayList(), 0, hashSet4, null, new HashSet(), RFC3280CertPathUtilities.ANY_POLICY, false);
        arrayListArr[0].add(pKIXPolicyNode2);
        if (this.pkixParams.isExplicitPolicyRequired()) {
            i3 = 0;
            i2 = 1;
        } else {
            i2 = 1;
            i3 = this.f1660n + 1;
        }
        int i12 = this.pkixParams.isAnyPolicyInhibited() ? 0 : this.f1660n + i2;
        int i13 = this.pkixParams.isPolicyMappingInhibited() ? 0 : this.f1660n + i2;
        try {
            int size = this.certs.size() - i2;
            PKIXPolicyNode pKIXPolicyNode3 = pKIXPolicyNode2;
            X509Certificate x509Certificate = null;
            HashSet hashSet5 = null;
            while (size >= 0) {
                int i14 = this.f1660n - size;
                X509Certificate x509Certificate2 = (X509Certificate) this.certs.get(size);
                int i15 = i10;
                try {
                    ASN1Sequence aSN1Sequence = (ASN1Sequence) CertPathValidatorUtilities.getExtensionValue(x509Certificate2, CertPathValidatorUtilities.CERTIFICATE_POLICIES);
                    if (aSN1Sequence == null || pKIXPolicyNode3 == null) {
                        set = initialPolicies;
                        str = str5;
                        i5 = i12;
                        i6 = i13;
                        pKIXPolicyNode3 = pKIXPolicyNode3;
                    } else {
                        Enumeration objects2 = aSN1Sequence.getObjects();
                        set = initialPolicies;
                        HashSet hashSet6 = new HashSet();
                        while (objects2.hasMoreElements()) {
                            PolicyInformation policyInformation = PolicyInformation.getInstance(objects2.nextElement());
                            PKIXPolicyNode pKIXPolicyNode4 = pKIXPolicyNode3;
                            ASN1ObjectIdentifier policyIdentifier = policyInformation.getPolicyIdentifier();
                            String str6 = str5;
                            hashSet6.add(policyIdentifier.getId());
                            if (!RFC3280CertPathUtilities.ANY_POLICY.equals(policyIdentifier.getId())) {
                                try {
                                    Set qualifierSet = CertPathValidatorUtilities.getQualifierSet(policyInformation.getPolicyQualifiers());
                                    if (!CertPathValidatorUtilities.processCertD1i(i14, arrayListArr, policyIdentifier, qualifierSet)) {
                                        CertPathValidatorUtilities.processCertD1ii(i14, arrayListArr, policyIdentifier, qualifierSet);
                                    }
                                } catch (CertPathValidatorException e2) {
                                    throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.policyQualifierError"), e2, this.certPath, size);
                                }
                            }
                            pKIXPolicyNode3 = pKIXPolicyNode4;
                            str5 = str6;
                        }
                        str = str5;
                        PKIXPolicyNode pKIXPolicyNode5 = pKIXPolicyNode3;
                        if (hashSet5 != null && !hashSet5.contains(RFC3280CertPathUtilities.ANY_POLICY)) {
                            hashSet2 = new HashSet();
                            for (Object obj : hashSet5) {
                                if (hashSet6.contains(obj)) {
                                    hashSet2.add(obj);
                                }
                            }
                            if (i12 <= 0) {
                                if (i14 < this.f1660n && CertPathValidatorUtilities.isSelfIssued(x509Certificate2)) {
                                }
                                i5 = i12;
                                i6 = i13;
                                hashSet3 = hashSet2;
                                pKIXPolicyNode3 = pKIXPolicyNode5;
                                for (i9 = i14 - 1; i9 >= 0; i9--) {
                                    ArrayList arrayList = arrayListArr[i9];
                                    for (int i16 = 0; i16 < arrayList.size(); i16++) {
                                        PKIXPolicyNode pKIXPolicyNode6 = (PKIXPolicyNode) arrayList.get(i16);
                                        if (!pKIXPolicyNode6.hasChildren()) {
                                            PKIXPolicyNode removePolicyNode = CertPathValidatorUtilities.removePolicyNode(pKIXPolicyNode3, arrayListArr, pKIXPolicyNode6);
                                            pKIXPolicyNode3 = removePolicyNode;
                                            if (removePolicyNode == null) {
                                                break;
                                            }
                                        }
                                    }
                                }
                                criticalExtensionOIDs = x509Certificate2.getCriticalExtensionOIDs();
                                if (criticalExtensionOIDs != null) {
                                    boolean contains = criticalExtensionOIDs.contains(CertPathValidatorUtilities.CERTIFICATE_POLICIES);
                                    ArrayList arrayList2 = arrayListArr[i14];
                                    for (int i17 = 0; i17 < arrayList2.size(); i17++) {
                                        ((PKIXPolicyNode) arrayList2.get(i17)).setCritical(contains);
                                    }
                                }
                                hashSet5 = hashSet3;
                            }
                            objects = aSN1Sequence.getObjects();
                            while (objects.hasMoreElements()) {
                                PolicyInformation policyInformation2 = PolicyInformation.getInstance(objects.nextElement());
                                if (RFC3280CertPathUtilities.ANY_POLICY.equals(policyInformation2.getPolicyIdentifier().getId())) {
                                    try {
                                        Set qualifierSet2 = CertPathValidatorUtilities.getQualifierSet(policyInformation2.getPolicyQualifiers());
                                        ArrayList arrayList3 = arrayListArr[i14 - 1];
                                        hashSet3 = hashSet2;
                                        for (int i18 = 0; i18 < arrayList3.size(); i18++) {
                                            PKIXPolicyNode pKIXPolicyNode7 = (PKIXPolicyNode) arrayList3.get(i18);
                                            for (Object obj2 : pKIXPolicyNode7.getExpectedPolicies()) {
                                                ArrayList arrayList4 = arrayList3;
                                                int i19 = i12;
                                                if (obj2 instanceof String) {
                                                    str4 = (String) obj2;
                                                } else if (obj2 instanceof ASN1ObjectIdentifier) {
                                                    str4 = ((ASN1ObjectIdentifier) obj2).getId();
                                                } else {
                                                    arrayList3 = arrayList4;
                                                    i12 = i19;
                                                }
                                                Iterator children = pKIXPolicyNode7.getChildren();
                                                boolean z2 = false;
                                                while (children.hasNext()) {
                                                    Iterator it = children;
                                                    if (str4.equals(((PKIXPolicyNode) children.next()).getValidPolicy())) {
                                                        z2 = true;
                                                    }
                                                    children = it;
                                                }
                                                if (z2) {
                                                    i8 = i13;
                                                } else {
                                                    HashSet hashSet7 = new HashSet();
                                                    hashSet7.add(str4);
                                                    i8 = i13;
                                                    PKIXPolicyNode pKIXPolicyNode8 = new PKIXPolicyNode(new ArrayList(), i14, hashSet7, pKIXPolicyNode7, qualifierSet2, str4, false);
                                                    pKIXPolicyNode7.addChild(pKIXPolicyNode8);
                                                    arrayListArr[i14].add(pKIXPolicyNode8);
                                                }
                                                arrayList3 = arrayList4;
                                                i12 = i19;
                                                i13 = i8;
                                            }
                                        }
                                        i5 = i12;
                                        i6 = i13;
                                        pKIXPolicyNode3 = pKIXPolicyNode5;
                                        while (i9 >= 0) {
                                        }
                                        criticalExtensionOIDs = x509Certificate2.getCriticalExtensionOIDs();
                                        if (criticalExtensionOIDs != null) {
                                        }
                                        hashSet5 = hashSet3;
                                    } catch (CertPathValidatorException e3) {
                                        throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.policyQualifierError"), e3, this.certPath, size);
                                    }
                                }
                            }
                            i5 = i12;
                            i6 = i13;
                            hashSet3 = hashSet2;
                            pKIXPolicyNode3 = pKIXPolicyNode5;
                            while (i9 >= 0) {
                            }
                            criticalExtensionOIDs = x509Certificate2.getCriticalExtensionOIDs();
                            if (criticalExtensionOIDs != null) {
                            }
                            hashSet5 = hashSet3;
                        }
                        hashSet2 = hashSet6;
                        if (i12 <= 0) {
                        }
                        objects = aSN1Sequence.getObjects();
                        while (objects.hasMoreElements()) {
                        }
                        i5 = i12;
                        i6 = i13;
                        hashSet3 = hashSet2;
                        pKIXPolicyNode3 = pKIXPolicyNode5;
                        while (i9 >= 0) {
                        }
                        criticalExtensionOIDs = x509Certificate2.getCriticalExtensionOIDs();
                        if (criticalExtensionOIDs != null) {
                        }
                        hashSet5 = hashSet3;
                    }
                    if (aSN1Sequence == null) {
                        pKIXPolicyNode3 = null;
                    }
                    if (i3 <= 0 && pKIXPolicyNode3 == null) {
                        throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.noValidPolicyTree"));
                    }
                    if (i14 != this.f1660n) {
                        try {
                            ASN1Primitive extensionValue = CertPathValidatorUtilities.getExtensionValue(x509Certificate2, CertPathValidatorUtilities.POLICY_MAPPINGS);
                            if (extensionValue != null) {
                                ASN1Sequence aSN1Sequence2 = (ASN1Sequence) extensionValue;
                                int i20 = 0;
                                while (i20 < aSN1Sequence2.size()) {
                                    ASN1Sequence aSN1Sequence3 = (ASN1Sequence) aSN1Sequence2.getObjectAt(i20);
                                    ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) aSN1Sequence3.getObjectAt(0);
                                    ASN1ObjectIdentifier aSN1ObjectIdentifier2 = (ASN1ObjectIdentifier) aSN1Sequence3.getObjectAt(1);
                                    boolean equals = RFC3280CertPathUtilities.ANY_POLICY.equals(aSN1ObjectIdentifier.getId());
                                    ASN1Sequence aSN1Sequence4 = aSN1Sequence2;
                                    if (equals) {
                                        throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.invalidPolicyMapping"), this.certPath, size);
                                    }
                                    if (RFC3280CertPathUtilities.ANY_POLICY.equals(aSN1ObjectIdentifier2.getId())) {
                                        throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.invalidPolicyMapping"), this.certPath, size);
                                    }
                                    i20++;
                                    aSN1Sequence2 = aSN1Sequence4;
                                }
                            }
                            if (extensionValue != null) {
                                ASN1Sequence aSN1Sequence5 = (ASN1Sequence) extensionValue;
                                HashMap hashMap = new HashMap();
                                HashSet hashSet8 = new HashSet();
                                int i21 = 0;
                                while (i21 < aSN1Sequence5.size()) {
                                    ASN1Sequence aSN1Sequence6 = (ASN1Sequence) aSN1Sequence5.getObjectAt(i21);
                                    ASN1Sequence aSN1Sequence7 = aSN1Sequence5;
                                    String id = ((ASN1ObjectIdentifier) aSN1Sequence6.getObjectAt(0)).getId();
                                    HashSet hashSet9 = hashSet5;
                                    String id2 = ((ASN1ObjectIdentifier) aSN1Sequence6.getObjectAt(1)).getId();
                                    if (hashMap.containsKey(id)) {
                                        ((Set) hashMap.get(id)).add(id2);
                                    } else {
                                        HashSet hashSet10 = new HashSet();
                                        hashSet10.add(id2);
                                        hashMap.put(id, hashSet10);
                                        hashSet8.add(id);
                                    }
                                    i21++;
                                    aSN1Sequence5 = aSN1Sequence7;
                                    hashSet5 = hashSet9;
                                }
                                hashSet = hashSet5;
                                Iterator it2 = hashSet8.iterator();
                                while (it2.hasNext()) {
                                    String str7 = (String) it2.next();
                                    if (i6 > 0) {
                                        try {
                                            CertPathValidatorUtilities.prepareNextCertB1(i14, arrayListArr, str7, hashMap, x509Certificate2);
                                            str3 = str;
                                        } catch (CertPathValidatorException e4) {
                                            throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.policyQualifierError"), e4, this.certPath, size);
                                        } catch (AnnotatedException e5) {
                                            throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, str), e5, this.certPath, size);
                                        }
                                    } else {
                                        str3 = str;
                                        if (i6 <= 0) {
                                            pKIXPolicyNode3 = CertPathValidatorUtilities.prepareNextCertB2(i14, arrayListArr, str7, pKIXPolicyNode3);
                                        }
                                    }
                                    str = str3;
                                }
                            } else {
                                hashSet = hashSet5;
                            }
                            str2 = str;
                            if (CertPathValidatorUtilities.isSelfIssued(x509Certificate2)) {
                                i7 = i5;
                                i13 = i6;
                            } else {
                                if (i3 != 0) {
                                    i3--;
                                }
                                i13 = i6 != 0 ? i6 - 1 : i6;
                                i7 = i5 != 0 ? i5 - 1 : i5;
                            }
                            try {
                                ASN1Sequence aSN1Sequence8 = (ASN1Sequence) CertPathValidatorUtilities.getExtensionValue(x509Certificate2, CertPathValidatorUtilities.POLICY_CONSTRAINTS);
                                if (aSN1Sequence8 != null) {
                                    Enumeration objects3 = aSN1Sequence8.getObjects();
                                    while (objects3.hasMoreElements()) {
                                        ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) objects3.nextElement();
                                        int tagNo = aSN1TaggedObject.getTagNo();
                                        if (tagNo == 0) {
                                            int intValueExact3 = ASN1Integer.getInstance(aSN1TaggedObject, false).intValueExact();
                                            if (intValueExact3 < i3) {
                                                i3 = intValueExact3;
                                            }
                                        } else if (tagNo == 1 && (intValueExact2 = ASN1Integer.getInstance(aSN1TaggedObject, false).intValueExact()) < i13) {
                                            i13 = intValueExact2;
                                        }
                                    }
                                }
                                try {
                                    ASN1Integer aSN1Integer = (ASN1Integer) CertPathValidatorUtilities.getExtensionValue(x509Certificate2, CertPathValidatorUtilities.INHIBIT_ANY_POLICY);
                                    if (aSN1Integer != null && (intValueExact = aSN1Integer.intValueExact()) < i7) {
                                        i7 = intValueExact;
                                    }
                                } catch (AnnotatedException unused) {
                                    throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.policyInhibitExtError"), this.certPath, size);
                                }
                            } catch (AnnotatedException unused2) {
                                throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.policyConstExtError"), this.certPath, size);
                            }
                        } catch (AnnotatedException e6) {
                            throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.policyMapExtError"), e6, this.certPath, size);
                        }
                    } else {
                        hashSet = hashSet5;
                        str2 = str;
                        i7 = i5;
                        i13 = i6;
                    }
                    size--;
                    x509Certificate = x509Certificate2;
                    str5 = str2;
                    hashSet5 = hashSet;
                    i10 = i15;
                    i12 = i7;
                    initialPolicies = set;
                } catch (AnnotatedException e7) {
                    throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, str5), e7, this.certPath, size);
                }
            }
            Set<String> set2 = initialPolicies;
            int i22 = i10;
            PKIXPolicyNode pKIXPolicyNode9 = pKIXPolicyNode3;
            if (!CertPathValidatorUtilities.isSelfIssued(x509Certificate) && i3 > 0) {
                i3--;
            }
            try {
                ASN1Sequence aSN1Sequence9 = (ASN1Sequence) CertPathValidatorUtilities.getExtensionValue(x509Certificate, CertPathValidatorUtilities.POLICY_CONSTRAINTS);
                if (aSN1Sequence9 != null) {
                    Enumeration objects4 = aSN1Sequence9.getObjects();
                    int i23 = i3;
                    while (objects4.hasMoreElements()) {
                        ASN1TaggedObject aSN1TaggedObject2 = (ASN1TaggedObject) objects4.nextElement();
                        if (aSN1TaggedObject2.getTagNo() == 0 && ASN1Integer.getInstance(aSN1TaggedObject2, false).intValueExact() == 0) {
                            i23 = 0;
                        }
                    }
                    i4 = 0;
                    i3 = i23;
                } else {
                    i4 = 0;
                }
                if (pKIXPolicyNode9 == null) {
                    if (this.pkixParams.isExplicitPolicyRequired()) {
                        throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.explicitPolicy"), this.certPath, size);
                    }
                    pKIXPolicyNode = null;
                } else if (!CertPathValidatorUtilities.isAnyPolicy(set2)) {
                    HashSet hashSet11 = new HashSet();
                    for (int i24 = i4; i24 < i22; i24++) {
                        ArrayList arrayList5 = arrayListArr[i24];
                        for (int i25 = i4; i25 < arrayList5.size(); i25++) {
                            PKIXPolicyNode pKIXPolicyNode10 = (PKIXPolicyNode) arrayList5.get(i25);
                            if (RFC3280CertPathUtilities.ANY_POLICY.equals(pKIXPolicyNode10.getValidPolicy())) {
                                Iterator children2 = pKIXPolicyNode10.getChildren();
                                while (children2.hasNext()) {
                                    PKIXPolicyNode pKIXPolicyNode11 = (PKIXPolicyNode) children2.next();
                                    if (!RFC3280CertPathUtilities.ANY_POLICY.equals(pKIXPolicyNode11.getValidPolicy())) {
                                        hashSet11.add(pKIXPolicyNode11);
                                    }
                                }
                            }
                        }
                    }
                    Iterator it3 = hashSet11.iterator();
                    pKIXPolicyNode = pKIXPolicyNode9;
                    while (it3.hasNext()) {
                        PKIXPolicyNode pKIXPolicyNode12 = (PKIXPolicyNode) it3.next();
                        Set<String> set3 = set2;
                        if (!set3.contains(pKIXPolicyNode12.getValidPolicy())) {
                            pKIXPolicyNode = CertPathValidatorUtilities.removePolicyNode(pKIXPolicyNode, arrayListArr, pKIXPolicyNode12);
                        }
                        set2 = set3;
                    }
                    if (pKIXPolicyNode != null) {
                        for (int i26 = this.f1660n - 1; i26 >= 0; i26--) {
                            ArrayList arrayList6 = arrayListArr[i26];
                            for (int i27 = i4; i27 < arrayList6.size(); i27++) {
                                PKIXPolicyNode pKIXPolicyNode13 = (PKIXPolicyNode) arrayList6.get(i27);
                                if (!pKIXPolicyNode13.hasChildren()) {
                                    pKIXPolicyNode = CertPathValidatorUtilities.removePolicyNode(pKIXPolicyNode, arrayListArr, pKIXPolicyNode13);
                                }
                            }
                        }
                    }
                } else if (!this.pkixParams.isExplicitPolicyRequired()) {
                    pKIXPolicyNode = pKIXPolicyNode9;
                } else {
                    if (hashSet5.isEmpty()) {
                        throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.explicitPolicy"), this.certPath, size);
                    }
                    HashSet hashSet12 = new HashSet();
                    int i28 = i4;
                    while (true) {
                        int i29 = i22;
                        if (i28 >= i29) {
                            break;
                        }
                        ArrayList arrayList7 = arrayListArr[i28];
                        for (int i30 = i4; i30 < arrayList7.size(); i30++) {
                            PKIXPolicyNode pKIXPolicyNode14 = (PKIXPolicyNode) arrayList7.get(i30);
                            if (RFC3280CertPathUtilities.ANY_POLICY.equals(pKIXPolicyNode14.getValidPolicy())) {
                                Iterator children3 = pKIXPolicyNode14.getChildren();
                                while (children3.hasNext()) {
                                    hashSet12.add(children3.next());
                                }
                            }
                        }
                        i28++;
                        i22 = i29;
                    }
                    Iterator it4 = hashSet12.iterator();
                    while (it4.hasNext()) {
                        hashSet5.contains(((PKIXPolicyNode) it4.next()).getValidPolicy());
                    }
                    pKIXPolicyNode = pKIXPolicyNode9;
                    for (int i31 = this.f1660n - 1; i31 >= 0; i31--) {
                        ArrayList arrayList8 = arrayListArr[i31];
                        for (int i32 = i4; i32 < arrayList8.size(); i32++) {
                            PKIXPolicyNode pKIXPolicyNode15 = (PKIXPolicyNode) arrayList8.get(i32);
                            if (!pKIXPolicyNode15.hasChildren()) {
                                pKIXPolicyNode = CertPathValidatorUtilities.removePolicyNode(pKIXPolicyNode, arrayListArr, pKIXPolicyNode15);
                            }
                        }
                    }
                }
                if (i3 <= 0 && pKIXPolicyNode == null) {
                    throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.invalidPolicy"));
                }
            } catch (AnnotatedException unused3) {
                throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.policyConstExtError"), this.certPath, size);
            }
        } catch (CertPathReviewerException e8) {
            addError(e8.getErrorMessage(), e8.getIndex());
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(15:(2:91|92)|(3:(15:94|95|96|(11:98|99|(2:102|100)|103|104|(2:107|105)|108|109|110|111|112)|119|99|(1:100)|103|104|(1:105)|108|109|110|111|112)|111|112)|122|95|96|(0)|119|99|(1:100)|103|104|(1:105)|108|109|110) */
    /* JADX WARN: Can't wrap try/catch for region: R(19:30|(2:137|138)(2:32|(2:130|131)(20:34|(2:38|(17:40|41|42|43|44|(18:91|92|(15:94|95|96|(11:98|99|(2:102|100)|103|104|(2:107|105)|108|109|110|111|112)|119|99|(1:100)|103|104|(1:105)|108|109|110|111|112)|122|95|96|(0)|119|99|(1:100)|103|104|(1:105)|108|109|110|111|112)(1:46)|(1:90)(1:50)|51|(7:53|(2:55|(1:57))(1:88)|58|59|(2:61|(1:63))(1:85)|64|(9:66|(1:83)|70|71|72|74|75|77|78))(1:89)|84|70|71|72|74|75|77|78))|129|41|42|43|44|(0)(0)|(1:48)|90|51|(0)(0)|84|70|71|72|74|75|77|78))|132|42|43|44|(0)(0)|(0)|90|51|(0)(0)|84|70|71|72|74|75|77|78) */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x0322, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x0323, code lost:
    
        r13 = r3;
        r15 = r4;
        r11 = r5;
        r16 = r6;
        r12 = r7;
        r18 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x02af, code lost:
    
        addError(new org.bouncycastle.i18n.ErrorBundle(org.bouncycastle.x509.PKIXCertPathReviewer.RESOURCE_NAME, "CertPathReviewer.crlAuthInfoAccError"), r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x0254, code lost:
    
        r1 = new java.lang.Object[r13];
        r1[r12] = new org.bouncycastle.i18n.filter.TrustedInput(r3.getNotAfter());
        r0 = new org.bouncycastle.i18n.ErrorBundle(org.bouncycastle.x509.PKIXCertPathReviewer.RESOURCE_NAME, "CertPathReviewer.certificateExpired", r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x027d, code lost:
    
        addError(r0, r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x0269, code lost:
    
        r1 = new java.lang.Object[r13];
        r1[r12] = new org.bouncycastle.i18n.filter.TrustedInput(r3.getNotBefore());
        r0 = new org.bouncycastle.i18n.ErrorBundle(org.bouncycastle.x509.PKIXCertPathReviewer.RESOURCE_NAME, "CertPathReviewer.certificateNotYetValid", r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x03e4, code lost:
    
        addError(new org.bouncycastle.i18n.ErrorBundle(org.bouncycastle.x509.PKIXCertPathReviewer.RESOURCE_NAME, "CertPathReviewer.pubKeyError"), r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x03e2, code lost:
    
        r6 = r16;
     */
    /* JADX WARN: Removed duplicated region for block: B:102:0x02cc A[LOOP:1: B:100:0x02c6->B:102:0x02cc, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:107:0x02f0 A[LOOP:2: B:105:0x02ea->B:107:0x02f0, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:149:0x0166  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x013e  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0141  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0176  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0333  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x033d  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x036d  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x03cb  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0288 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:98:0x02aa A[Catch: AnnotatedException -> 0x02af, TRY_LEAVE, TryCatch #2 {AnnotatedException -> 0x02af, blocks: (B:96:0x02a2, B:98:0x02aa), top: B:95:0x02a2 }] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x00f7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void checkSignatures() {
        TrustAnchor trustAnchor;
        TrustAnchor trustAnchor2;
        X500Principal x500Principal;
        X509Certificate x509Certificate;
        PublicKey publicKey;
        int size;
        ErrorBundle errorBundle;
        CRLDistPoint cRLDistPoint;
        AuthorityInformationAccess authorityInformationAccess;
        Iterator it;
        Iterator it2;
        X509Certificate x509Certificate2;
        int i2;
        int i3;
        PublicKey publicKey2;
        X500Principal x500Principal2;
        TrustAnchor trustAnchor3;
        ASN1Primitive extensionValue;
        ASN1Primitive extensionValue2;
        char c;
        char c2;
        int i4;
        char c3;
        AuthorityKeyIdentifier authorityKeyIdentifier;
        GeneralNames authorityCertIssuer;
        boolean[] keyUsage;
        X509Certificate x509Certificate3;
        Collection trustAnchors;
        char c4 = 2;
        char c5 = 0;
        int i5 = 1;
        addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.certPathValidDate", new Object[]{new TrustedInput(this.validDate), new TrustedInput(this.currentDate)}));
        try {
            List list = this.certs;
            x509Certificate3 = (X509Certificate) list.get(list.size() - 1);
            trustAnchors = getTrustAnchors(x509Certificate3, this.pkixParams.getTrustAnchors());
        } catch (CertPathReviewerException e2) {
            e = e2;
            trustAnchor = null;
        } catch (Throwable th) {
            th = th;
            trustAnchor = null;
        }
        if (trustAnchors.size() > 1) {
            addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.conflictingTrustAnchors", new Object[]{Integers.valueOf(trustAnchors.size()), new UntrustedInput(x509Certificate3.getIssuerX500Principal())}));
        } else {
            if (!trustAnchors.isEmpty()) {
                trustAnchor = (TrustAnchor) trustAnchors.iterator().next();
                try {
                    try {
                        try {
                            CertPathValidatorUtilities.verifyX509Certificate(x509Certificate3, trustAnchor.getTrustedCert() != null ? trustAnchor.getTrustedCert().getPublicKey() : trustAnchor.getCAPublicKey(), this.pkixParams.getSigProvider());
                        } catch (SignatureException unused) {
                            addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.trustButInvalidCert"));
                        } catch (Exception unused2) {
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.unknown", new Object[]{new UntrustedInput(th.getMessage()), new UntrustedInput(th)}));
                        trustAnchor2 = trustAnchor;
                        char c6 = 5;
                        if (trustAnchor2 == null) {
                        }
                        if (trustAnchor2 == null) {
                        }
                        X509Certificate x509Certificate4 = x509Certificate;
                        X500Principal x500Principal3 = x500Principal;
                        PublicKey publicKey3 = publicKey;
                        size = this.certs.size() - 1;
                        while (size >= 0) {
                        }
                        this.trustAnchor = trustAnchor2;
                        this.subjectPublicKey = publicKey3;
                    }
                } catch (CertPathReviewerException e3) {
                    e = e3;
                    addError(e.getErrorMessage());
                    trustAnchor2 = trustAnchor;
                    char c62 = 5;
                    if (trustAnchor2 == null) {
                    }
                    if (trustAnchor2 == null) {
                    }
                    X509Certificate x509Certificate42 = x509Certificate;
                    X500Principal x500Principal32 = x500Principal;
                    PublicKey publicKey32 = publicKey;
                    size = this.certs.size() - 1;
                    while (size >= 0) {
                    }
                    this.trustAnchor = trustAnchor2;
                    this.subjectPublicKey = publicKey32;
                }
                trustAnchor2 = trustAnchor;
                char c622 = 5;
                if (trustAnchor2 == null) {
                    X509Certificate trustedCert = trustAnchor2.getTrustedCert();
                    try {
                        x500Principal = trustedCert != null ? CertPathValidatorUtilities.getSubjectPrincipal(trustedCert) : new X500Principal(trustAnchor2.getCAName());
                    } catch (IllegalArgumentException unused3) {
                        addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.trustDNInvalid", new Object[]{new UntrustedInput(trustAnchor2.getCAName())}));
                        x500Principal = null;
                    }
                    if (trustedCert != null && (keyUsage = trustedCert.getKeyUsage()) != null && (keyUsage.length <= 5 || !keyUsage[5])) {
                        addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.trustKeyUsage"));
                    }
                } else {
                    x500Principal = null;
                }
                if (trustAnchor2 == null) {
                    x509Certificate = trustAnchor2.getTrustedCert();
                    publicKey = x509Certificate != null ? x509Certificate.getPublicKey() : trustAnchor2.getCAPublicKey();
                    try {
                        AlgorithmIdentifier algorithmIdentifier = CertPathValidatorUtilities.getAlgorithmIdentifier(publicKey);
                        algorithmIdentifier.getAlgorithm();
                        algorithmIdentifier.getParameters();
                    } catch (CertPathValidatorException unused4) {
                        addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.trustPubKeyError"));
                    }
                } else {
                    x509Certificate = null;
                    publicKey = null;
                }
                X509Certificate x509Certificate422 = x509Certificate;
                X500Principal x500Principal322 = x500Principal;
                PublicKey publicKey322 = publicKey;
                size = this.certs.size() - 1;
                while (size >= 0) {
                    int i6 = this.f1660n - size;
                    X509Certificate x509Certificate5 = (X509Certificate) this.certs.get(size);
                    if (publicKey322 != null) {
                        try {
                            CertPathValidatorUtilities.verifyX509Certificate(x509Certificate5, publicKey322, this.pkixParams.getSigProvider());
                        } catch (GeneralSecurityException e4) {
                            Object[] objArr = new Object[3];
                            objArr[c5] = e4.getMessage();
                            objArr[i5] = e4;
                            objArr[c4] = e4.getClass().getName();
                            errorBundle = new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.signatureNotVerified", objArr);
                            addError(errorBundle, size);
                            x509Certificate5.checkValidity(this.validDate);
                            if (this.pkixParams.isRevocationEnabled()) {
                            }
                            if (x500Principal2 != null) {
                            }
                            c = 2;
                            c2 = 0;
                            if (i2 == this.f1660n) {
                            }
                            c3 = 5;
                            x500Principal322 = x509Certificate2.getSubjectX500Principal();
                            publicKey322 = CertPathValidatorUtilities.getNextWorkingKey(this.certs, i3);
                            AlgorithmIdentifier algorithmIdentifier2 = CertPathValidatorUtilities.getAlgorithmIdentifier(publicKey322);
                            algorithmIdentifier2.getAlgorithm();
                            algorithmIdentifier2.getParameters();
                            int i7 = i3 - 1;
                            c4 = c;
                            c5 = c2;
                            c622 = c3;
                            x509Certificate422 = x509Certificate2;
                            trustAnchor2 = trustAnchor3;
                            size = i7;
                            i5 = i4;
                        }
                    } else if (CertPathValidatorUtilities.isSelfIssued(x509Certificate5)) {
                        try {
                            CertPathValidatorUtilities.verifyX509Certificate(x509Certificate5, x509Certificate5.getPublicKey(), this.pkixParams.getSigProvider());
                            addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.rootKeyIsValidButNotATrustAnchor"), size);
                        } catch (GeneralSecurityException e5) {
                            Object[] objArr2 = new Object[3];
                            objArr2[c5] = e5.getMessage();
                            objArr2[i5] = e5;
                            objArr2[c4] = e5.getClass().getName();
                            errorBundle = new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.signatureNotVerified", objArr2);
                            addError(errorBundle, size);
                            x509Certificate5.checkValidity(this.validDate);
                            if (this.pkixParams.isRevocationEnabled()) {
                            }
                            if (x500Principal2 != null) {
                            }
                            c = 2;
                            c2 = 0;
                            if (i2 == this.f1660n) {
                            }
                            c3 = 5;
                            x500Principal322 = x509Certificate2.getSubjectX500Principal();
                            publicKey322 = CertPathValidatorUtilities.getNextWorkingKey(this.certs, i3);
                            AlgorithmIdentifier algorithmIdentifier22 = CertPathValidatorUtilities.getAlgorithmIdentifier(publicKey322);
                            algorithmIdentifier22.getAlgorithm();
                            algorithmIdentifier22.getParameters();
                            int i72 = i3 - 1;
                            c4 = c;
                            c5 = c2;
                            c622 = c3;
                            x509Certificate422 = x509Certificate2;
                            trustAnchor2 = trustAnchor3;
                            size = i72;
                            i5 = i4;
                        }
                    } else {
                        ErrorBundle errorBundle2 = new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.NoIssuerPublicKey");
                        byte[] extensionValue3 = x509Certificate5.getExtensionValue(Extension.authorityKeyIdentifier.getId());
                        if (extensionValue3 != null && (authorityCertIssuer = (authorityKeyIdentifier = AuthorityKeyIdentifier.getInstance(ASN1OctetString.getInstance(extensionValue3).getOctets())).getAuthorityCertIssuer()) != null) {
                            GeneralName generalName = authorityCertIssuer.getNames()[c5];
                            BigInteger authorityCertSerialNumber = authorityKeyIdentifier.getAuthorityCertSerialNumber();
                            if (authorityCertSerialNumber != null) {
                                Object[] objArr3 = new Object[7];
                                objArr3[c5] = new LocaleString(RESOURCE_NAME, "missingIssuer");
                                objArr3[i5] = " \"";
                                objArr3[2] = generalName;
                                objArr3[3] = "\" ";
                                objArr3[4] = new LocaleString(RESOURCE_NAME, "missingSerial");
                                objArr3[5] = " ";
                                objArr3[6] = authorityCertSerialNumber;
                                errorBundle2.setExtraArguments(objArr3);
                                addError(errorBundle2, size);
                                x509Certificate5.checkValidity(this.validDate);
                                if (this.pkixParams.isRevocationEnabled()) {
                                    try {
                                        extensionValue2 = CertPathValidatorUtilities.getExtensionValue(x509Certificate5, CRL_DIST_POINTS);
                                    } catch (AnnotatedException unused5) {
                                        addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.crlDistPtExtError"), size);
                                    }
                                    try {
                                        if (extensionValue2 != null) {
                                            cRLDistPoint = CRLDistPoint.getInstance(extensionValue2);
                                            extensionValue = CertPathValidatorUtilities.getExtensionValue(x509Certificate5, AUTH_INFO_ACCESS);
                                            if (extensionValue != null) {
                                                authorityInformationAccess = AuthorityInformationAccess.getInstance(extensionValue);
                                                Vector cRLDistUrls = getCRLDistUrls(cRLDistPoint);
                                                Vector oCSPUrls = getOCSPUrls(authorityInformationAccess);
                                                it = cRLDistUrls.iterator();
                                                while (it.hasNext()) {
                                                    Object[] objArr4 = new Object[i5];
                                                    objArr4[c5] = new UntrustedUrlInput(it.next());
                                                    addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.crlDistPoint", objArr4), size);
                                                    i5 = 1;
                                                }
                                                it2 = oCSPUrls.iterator();
                                                while (it2.hasNext()) {
                                                    Object[] objArr5 = new Object[1];
                                                    objArr5[c5] = new UntrustedUrlInput(it2.next());
                                                    addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.ocspLocation", objArr5), size);
                                                }
                                                x509Certificate2 = x509Certificate5;
                                                i2 = i6;
                                                i3 = size;
                                                X509Certificate x509Certificate6 = x509Certificate422;
                                                publicKey2 = publicKey322;
                                                x500Principal2 = x500Principal322;
                                                trustAnchor3 = trustAnchor2;
                                                checkRevocation(this.pkixParams, x509Certificate5, this.validDate, x509Certificate6, publicKey322, cRLDistUrls, oCSPUrls, i3);
                                            }
                                            authorityInformationAccess = null;
                                            Vector cRLDistUrls2 = getCRLDistUrls(cRLDistPoint);
                                            Vector oCSPUrls2 = getOCSPUrls(authorityInformationAccess);
                                            it = cRLDistUrls2.iterator();
                                            while (it.hasNext()) {
                                            }
                                            it2 = oCSPUrls2.iterator();
                                            while (it2.hasNext()) {
                                            }
                                            x509Certificate2 = x509Certificate5;
                                            i2 = i6;
                                            i3 = size;
                                            X509Certificate x509Certificate62 = x509Certificate422;
                                            publicKey2 = publicKey322;
                                            x500Principal2 = x500Principal322;
                                            trustAnchor3 = trustAnchor2;
                                            checkRevocation(this.pkixParams, x509Certificate5, this.validDate, x509Certificate62, publicKey322, cRLDistUrls2, oCSPUrls2, i3);
                                        }
                                        checkRevocation(this.pkixParams, x509Certificate5, this.validDate, x509Certificate62, publicKey322, cRLDistUrls2, oCSPUrls2, i3);
                                    } catch (CertPathReviewerException e6) {
                                        e = e6;
                                        addError(e.getErrorMessage(), i3);
                                        if (x500Principal2 != null) {
                                        }
                                        c = 2;
                                        c2 = 0;
                                        if (i2 == this.f1660n) {
                                        }
                                        c3 = 5;
                                        x500Principal322 = x509Certificate2.getSubjectX500Principal();
                                        publicKey322 = CertPathValidatorUtilities.getNextWorkingKey(this.certs, i3);
                                        AlgorithmIdentifier algorithmIdentifier222 = CertPathValidatorUtilities.getAlgorithmIdentifier(publicKey322);
                                        algorithmIdentifier222.getAlgorithm();
                                        algorithmIdentifier222.getParameters();
                                        int i722 = i3 - 1;
                                        c4 = c;
                                        c5 = c2;
                                        c622 = c3;
                                        x509Certificate422 = x509Certificate2;
                                        trustAnchor2 = trustAnchor3;
                                        size = i722;
                                        i5 = i4;
                                    }
                                    cRLDistPoint = null;
                                    extensionValue = CertPathValidatorUtilities.getExtensionValue(x509Certificate5, AUTH_INFO_ACCESS);
                                    if (extensionValue != null) {
                                    }
                                    authorityInformationAccess = null;
                                    Vector cRLDistUrls22 = getCRLDistUrls(cRLDistPoint);
                                    Vector oCSPUrls22 = getOCSPUrls(authorityInformationAccess);
                                    it = cRLDistUrls22.iterator();
                                    while (it.hasNext()) {
                                    }
                                    it2 = oCSPUrls22.iterator();
                                    while (it2.hasNext()) {
                                    }
                                    x509Certificate2 = x509Certificate5;
                                    i2 = i6;
                                    i3 = size;
                                    X509Certificate x509Certificate622 = x509Certificate422;
                                    publicKey2 = publicKey322;
                                    x500Principal2 = x500Principal322;
                                    trustAnchor3 = trustAnchor2;
                                } else {
                                    x509Certificate2 = x509Certificate5;
                                    i2 = i6;
                                    i3 = size;
                                    publicKey2 = publicKey322;
                                    x500Principal2 = x500Principal322;
                                    trustAnchor3 = trustAnchor2;
                                }
                                if (x500Principal2 != null || x509Certificate2.getIssuerX500Principal().equals(x500Principal2)) {
                                    c = 2;
                                    c2 = 0;
                                } else {
                                    c = 2;
                                    c2 = 0;
                                    addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.certWrongIssuer", new Object[]{x500Principal2.getName(), x509Certificate2.getIssuerX500Principal().getName()}), i3);
                                }
                                if (i2 == this.f1660n) {
                                    if (x509Certificate2 != null) {
                                        i4 = 1;
                                        if (x509Certificate2.getVersion() == 1) {
                                            addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.noCACert"), i3);
                                        }
                                    } else {
                                        i4 = 1;
                                    }
                                    try {
                                        BasicConstraints basicConstraints = BasicConstraints.getInstance(CertPathValidatorUtilities.getExtensionValue(x509Certificate2, CertPathValidatorUtilities.BASIC_CONSTRAINTS));
                                        if (basicConstraints == null) {
                                            addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.noBasicConstraints"), i3);
                                        } else if (!basicConstraints.isCA()) {
                                            addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.noCACert"), i3);
                                        }
                                    } catch (AnnotatedException unused6) {
                                        addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.errorProcesingBC"), i3);
                                    }
                                    boolean[] keyUsage2 = x509Certificate2.getKeyUsage();
                                    if (keyUsage2 != null) {
                                        c3 = 5;
                                        if (keyUsage2.length <= 5 || !keyUsage2[5]) {
                                            addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.noCertSign"), i3);
                                        }
                                        x500Principal322 = x509Certificate2.getSubjectX500Principal();
                                        publicKey322 = CertPathValidatorUtilities.getNextWorkingKey(this.certs, i3);
                                        AlgorithmIdentifier algorithmIdentifier2222 = CertPathValidatorUtilities.getAlgorithmIdentifier(publicKey322);
                                        algorithmIdentifier2222.getAlgorithm();
                                        algorithmIdentifier2222.getParameters();
                                        int i7222 = i3 - 1;
                                        c4 = c;
                                        c5 = c2;
                                        c622 = c3;
                                        x509Certificate422 = x509Certificate2;
                                        trustAnchor2 = trustAnchor3;
                                        size = i7222;
                                        i5 = i4;
                                    }
                                } else {
                                    i4 = 1;
                                }
                                c3 = 5;
                                x500Principal322 = x509Certificate2.getSubjectX500Principal();
                                publicKey322 = CertPathValidatorUtilities.getNextWorkingKey(this.certs, i3);
                                AlgorithmIdentifier algorithmIdentifier22222 = CertPathValidatorUtilities.getAlgorithmIdentifier(publicKey322);
                                algorithmIdentifier22222.getAlgorithm();
                                algorithmIdentifier22222.getParameters();
                                int i72222 = i3 - 1;
                                c4 = c;
                                c5 = c2;
                                c622 = c3;
                                x509Certificate422 = x509Certificate2;
                                trustAnchor2 = trustAnchor3;
                                size = i72222;
                                i5 = i4;
                            }
                        }
                        addError(errorBundle2, size);
                        x509Certificate5.checkValidity(this.validDate);
                        if (this.pkixParams.isRevocationEnabled()) {
                        }
                        if (x500Principal2 != null) {
                        }
                        c = 2;
                        c2 = 0;
                        if (i2 == this.f1660n) {
                        }
                        c3 = 5;
                        x500Principal322 = x509Certificate2.getSubjectX500Principal();
                        publicKey322 = CertPathValidatorUtilities.getNextWorkingKey(this.certs, i3);
                        AlgorithmIdentifier algorithmIdentifier222222 = CertPathValidatorUtilities.getAlgorithmIdentifier(publicKey322);
                        algorithmIdentifier222222.getAlgorithm();
                        algorithmIdentifier222222.getParameters();
                        int i722222 = i3 - 1;
                        c4 = c;
                        c5 = c2;
                        c622 = c3;
                        x509Certificate422 = x509Certificate2;
                        trustAnchor2 = trustAnchor3;
                        size = i722222;
                        i5 = i4;
                    }
                    x509Certificate5.checkValidity(this.validDate);
                    if (this.pkixParams.isRevocationEnabled()) {
                    }
                    if (x500Principal2 != null) {
                    }
                    c = 2;
                    c2 = 0;
                    if (i2 == this.f1660n) {
                    }
                    c3 = 5;
                    x500Principal322 = x509Certificate2.getSubjectX500Principal();
                    publicKey322 = CertPathValidatorUtilities.getNextWorkingKey(this.certs, i3);
                    AlgorithmIdentifier algorithmIdentifier2222222 = CertPathValidatorUtilities.getAlgorithmIdentifier(publicKey322);
                    algorithmIdentifier2222222.getAlgorithm();
                    algorithmIdentifier2222222.getParameters();
                    int i7222222 = i3 - 1;
                    c4 = c;
                    c5 = c2;
                    c622 = c3;
                    x509Certificate422 = x509Certificate2;
                    trustAnchor2 = trustAnchor3;
                    size = i7222222;
                    i5 = i4;
                }
                this.trustAnchor = trustAnchor2;
                this.subjectPublicKey = publicKey322;
            }
            addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.noTrustAnchorFound", new Object[]{new UntrustedInput(x509Certificate3.getIssuerX500Principal()), Integers.valueOf(this.pkixParams.getTrustAnchors().size())}));
        }
        trustAnchor = null;
        trustAnchor2 = trustAnchor;
        char c6222 = 5;
        if (trustAnchor2 == null) {
        }
        if (trustAnchor2 == null) {
        }
        X509Certificate x509Certificate4222 = x509Certificate;
        X500Principal x500Principal3222 = x500Principal;
        PublicKey publicKey3222 = publicKey;
        size = this.certs.size() - 1;
        while (size >= 0) {
        }
        this.trustAnchor = trustAnchor2;
        this.subjectPublicKey = publicKey3222;
    }

    private X509CRL getCRL(String str) {
        try {
            URL url = new URL(str);
            if (!url.getProtocol().equals("http") && !url.getProtocol().equals("https")) {
                return null;
            }
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200) {
                return (X509CRL) CertificateFactory.getInstance("X.509", BouncyCastleProvider.PROVIDER_NAME).generateCRL(httpURLConnection.getInputStream());
            }
            throw new Exception(httpURLConnection.getResponseMessage());
        } catch (Exception e2) {
            throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.loadCrlDistPointError", new Object[]{new UntrustedInput(str), e2.getMessage(), e2, e2.getClass().getName()}));
        }
    }

    private boolean processQcStatements(X509Certificate x509Certificate, int i2) {
        ErrorBundle errorBundle;
        try {
            ASN1Sequence aSN1Sequence = (ASN1Sequence) CertPathValidatorUtilities.getExtensionValue(x509Certificate, QC_STATEMENT);
            boolean z2 = false;
            for (int i3 = 0; i3 < aSN1Sequence.size(); i3++) {
                QCStatement qCStatement = QCStatement.getInstance(aSN1Sequence.getObjectAt(i3));
                if (ETSIQCObjectIdentifiers.id_etsi_qcs_QcCompliance.equals((ASN1Primitive) qCStatement.getStatementId())) {
                    errorBundle = new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.QcEuCompliance");
                } else {
                    if (!RFC3739QCObjectIdentifiers.id_qcs_pkixQCSyntax_v1.equals((ASN1Primitive) qCStatement.getStatementId())) {
                        if (ETSIQCObjectIdentifiers.id_etsi_qcs_QcSSCD.equals((ASN1Primitive) qCStatement.getStatementId())) {
                            errorBundle = new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.QcSSCD");
                        } else if (ETSIQCObjectIdentifiers.id_etsi_qcs_LimiteValue.equals((ASN1Primitive) qCStatement.getStatementId())) {
                            MonetaryValue monetaryValue = MonetaryValue.getInstance(qCStatement.getStatementInfo());
                            monetaryValue.getCurrency();
                            double doubleValue = monetaryValue.getAmount().doubleValue() * Math.pow(10.0d, monetaryValue.getExponent().doubleValue());
                            addNotification(monetaryValue.getCurrency().isAlphabetic() ? new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.QcLimitValueAlpha", new Object[]{monetaryValue.getCurrency().getAlphabetic(), new TrustedInput(new Double(doubleValue)), monetaryValue}) : new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.QcLimitValueNum", new Object[]{Integers.valueOf(monetaryValue.getCurrency().getNumeric()), new TrustedInput(new Double(doubleValue)), monetaryValue}), i2);
                        } else {
                            addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.QcUnknownStatement", new Object[]{qCStatement.getStatementId(), new UntrustedInput(qCStatement)}), i2);
                            z2 = true;
                        }
                    }
                }
                addNotification(errorBundle, i2);
            }
            return true ^ z2;
        } catch (AnnotatedException unused) {
            addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.QcStatementExtError"), i2);
            return false;
        }
    }

    public void addError(ErrorBundle errorBundle) {
        this.errors[0].add(errorBundle);
    }

    public void addNotification(ErrorBundle errorBundle) {
        this.notifications[0].add(errorBundle);
    }

    /* JADX WARN: Removed duplicated region for block: B:170:0x027a  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0247  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x025d  */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void checkCRLs(PKIXParameters pKIXParameters, X509Certificate x509Certificate, Date date, X509Certificate x509Certificate2, PublicKey publicKey, Vector vector, int i2) {
        Iterator it;
        X509CRL x509crl;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        String str;
        boolean[] keyUsage;
        X509CRL x509crl2;
        Iterator it2;
        boolean z6;
        X509CRLStoreSelector x509CRLStoreSelector = new X509CRLStoreSelector();
        try {
            x509CRLStoreSelector.addIssuerName(CertPathValidatorUtilities.getEncodedIssuerPrincipal(x509Certificate).getEncoded());
            x509CRLStoreSelector.setCertificateChecking(x509Certificate);
            try {
                Set findCRLs = PKIXCRLUtil.findCRLs(x509CRLStoreSelector, pKIXParameters);
                it = findCRLs.iterator();
                if (findCRLs.isEmpty()) {
                    Iterator it3 = PKIXCRLUtil.findCRLs(new X509CRLStoreSelector(), pKIXParameters).iterator();
                    ArrayList arrayList = new ArrayList();
                    while (it3.hasNext()) {
                        arrayList.add(((X509CRL) it3.next()).getIssuerX500Principal());
                    }
                    addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.noCrlInCertstore", new Object[]{new UntrustedInput(x509CRLStoreSelector.getIssuerNames()), new UntrustedInput(arrayList), Integers.valueOf(arrayList.size())}), i2);
                }
            } catch (AnnotatedException e2) {
                addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.crlExtractionError", new Object[]{e2.getCause().getMessage(), e2.getCause(), e2.getCause().getClass().getName()}), i2);
                it = new ArrayList().iterator();
            }
            X509CRL x509crl3 = null;
            while (it.hasNext()) {
                x509crl3 = (X509CRL) it.next();
                Date thisUpdate = x509crl3.getThisUpdate();
                Date nextUpdate = x509crl3.getNextUpdate();
                Object[] objArr = {new TrustedInput(thisUpdate), new TrustedInput(nextUpdate)};
                if (nextUpdate == null || date.before(nextUpdate)) {
                    addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.localValidCRL", objArr), i2);
                    x509crl = x509crl3;
                    z2 = true;
                    break;
                }
                addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.localInvalidCRL", objArr), i2);
            }
            x509crl = x509crl3;
            z2 = false;
            if (!z2) {
                X500Principal issuerX500Principal = x509Certificate.getIssuerX500Principal();
                Iterator it4 = vector.iterator();
                boolean z7 = z2;
                while (true) {
                    if (!it4.hasNext()) {
                        z2 = z7;
                        break;
                    }
                    try {
                        String str2 = (String) it4.next();
                        X509CRL crl = getCRL(str2);
                        if (crl != null) {
                            X500Principal issuerX500Principal2 = crl.getIssuerX500Principal();
                            if (issuerX500Principal.equals(issuerX500Principal2)) {
                                x509crl2 = x509crl;
                                it2 = it4;
                                z6 = z7;
                                Date thisUpdate2 = crl.getThisUpdate();
                                Date nextUpdate2 = crl.getNextUpdate();
                                Object[] objArr2 = {new TrustedInput(thisUpdate2), new TrustedInput(nextUpdate2), new UntrustedUrlInput(str2)};
                                if (nextUpdate2 != null && !date.before(nextUpdate2)) {
                                    addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.onlineInvalidCRL", objArr2), i2);
                                }
                                try {
                                    addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.onlineValidCRL", objArr2), i2);
                                    x509crl = crl;
                                    z2 = true;
                                    break;
                                } catch (CertPathReviewerException e3) {
                                    e = e3;
                                    z7 = true;
                                    addNotification(e.getErrorMessage(), i2);
                                    it4 = it2;
                                    x509crl = x509crl2;
                                }
                            } else {
                                x509crl2 = x509crl;
                                it2 = it4;
                                z6 = z7;
                                try {
                                    addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.onlineCRLWrongCA", new Object[]{new UntrustedInput(issuerX500Principal2.getName()), new UntrustedInput(issuerX500Principal.getName()), new UntrustedUrlInput(str2)}), i2);
                                } catch (CertPathReviewerException e4) {
                                    e = e4;
                                    z7 = z6;
                                    addNotification(e.getErrorMessage(), i2);
                                    it4 = it2;
                                    x509crl = x509crl2;
                                }
                            }
                        } else {
                            x509crl2 = x509crl;
                            it2 = it4;
                            z6 = z7;
                        }
                        it4 = it2;
                        x509crl = x509crl2;
                        z7 = z6;
                    } catch (CertPathReviewerException e5) {
                        e = e5;
                        x509crl2 = x509crl;
                        it2 = it4;
                    }
                }
            }
            if (x509crl != null) {
                if (x509Certificate2 != null && (keyUsage = x509Certificate2.getKeyUsage()) != null && (keyUsage.length <= 6 || !keyUsage[6])) {
                    throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.noCrlSigningPermited"));
                }
                if (publicKey == null) {
                    throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.crlNoIssuerPublicKey"));
                }
                try {
                    x509crl.verify(publicKey, BouncyCastleProvider.PROVIDER_NAME);
                    X509CRLEntry revokedCertificate = x509crl.getRevokedCertificate(x509Certificate.getSerialNumber());
                    if (revokedCertificate != null) {
                        if (revokedCertificate.hasExtensions()) {
                            try {
                                ASN1Enumerated aSN1Enumerated = ASN1Enumerated.getInstance(CertPathValidatorUtilities.getExtensionValue(revokedCertificate, Extension.reasonCode.getId()));
                                if (aSN1Enumerated != null) {
                                    str = CertPathValidatorUtilities.crlReasons[aSN1Enumerated.intValueExact()];
                                    if (str == null) {
                                        str = CertPathValidatorUtilities.crlReasons[7];
                                    }
                                    LocaleString localeString = new LocaleString(RESOURCE_NAME, str);
                                    if (date.before(revokedCertificate.getRevocationDate())) {
                                        throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.certRevoked", new Object[]{new TrustedInput(revokedCertificate.getRevocationDate()), localeString}));
                                    }
                                    addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.revokedAfterValidation", new Object[]{new TrustedInput(revokedCertificate.getRevocationDate()), localeString}), i2);
                                }
                            } catch (AnnotatedException e6) {
                                throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.crlReasonExtError"), e6);
                            }
                        }
                        str = null;
                        if (str == null) {
                        }
                        LocaleString localeString2 = new LocaleString(RESOURCE_NAME, str);
                        if (date.before(revokedCertificate.getRevocationDate())) {
                        }
                    } else {
                        addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.notRevoked"), i2);
                    }
                    Date nextUpdate3 = x509crl.getNextUpdate();
                    if (nextUpdate3 == null || date.before(nextUpdate3)) {
                        z3 = false;
                        z4 = true;
                    } else {
                        z4 = true;
                        TrustedInput trustedInput = new TrustedInput(nextUpdate3);
                        z3 = false;
                        addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.crlUpdateAvailable", new Object[]{trustedInput}), i2);
                    }
                    try {
                        ASN1Primitive extensionValue = CertPathValidatorUtilities.getExtensionValue(x509crl, CertPathValidatorUtilities.ISSUING_DISTRIBUTION_POINT);
                        try {
                            ASN1Primitive extensionValue2 = CertPathValidatorUtilities.getExtensionValue(x509crl, CertPathValidatorUtilities.DELTA_CRL_INDICATOR);
                            if (extensionValue2 != null) {
                                X509CRLStoreSelector x509CRLStoreSelector2 = new X509CRLStoreSelector();
                                try {
                                    x509CRLStoreSelector2.addIssuerName(CertPathValidatorUtilities.getIssuerPrincipal(x509crl).getEncoded());
                                    x509CRLStoreSelector2.setMinCRLNumber(((ASN1Integer) extensionValue2).getPositiveValue());
                                    try {
                                        x509CRLStoreSelector2.setMaxCRLNumber(((ASN1Integer) CertPathValidatorUtilities.getExtensionValue(x509crl, CertPathValidatorUtilities.CRL_NUMBER)).getPositiveValue().subtract(BigInteger.valueOf(1L)));
                                        try {
                                            Iterator it5 = PKIXCRLUtil.findCRLs(x509CRLStoreSelector2, pKIXParameters).iterator();
                                            while (true) {
                                                if (!it5.hasNext()) {
                                                    z5 = z3;
                                                    break;
                                                }
                                                try {
                                                    if (Objects.areEqual(extensionValue, CertPathValidatorUtilities.getExtensionValue((X509CRL) it5.next(), CertPathValidatorUtilities.ISSUING_DISTRIBUTION_POINT))) {
                                                        z5 = z4;
                                                        break;
                                                    }
                                                } catch (AnnotatedException e7) {
                                                    throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.distrPtExtError"), e7);
                                                }
                                            }
                                            if (!z5) {
                                                throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.noBaseCRL"));
                                            }
                                        } catch (AnnotatedException e8) {
                                            throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.crlExtractionError"), e8);
                                        }
                                    } catch (AnnotatedException e9) {
                                        throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.crlNbrExtError"), e9);
                                    }
                                } catch (IOException e10) {
                                    throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.crlIssuerException"), e10);
                                }
                            }
                            if (extensionValue != null) {
                                IssuingDistributionPoint issuingDistributionPoint = IssuingDistributionPoint.getInstance(extensionValue);
                                try {
                                    BasicConstraints basicConstraints = BasicConstraints.getInstance(CertPathValidatorUtilities.getExtensionValue(x509Certificate, CertPathValidatorUtilities.BASIC_CONSTRAINTS));
                                    if (issuingDistributionPoint.onlyContainsUserCerts() && basicConstraints != null && basicConstraints.isCA()) {
                                        throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.crlOnlyUserCert"));
                                    }
                                    if (issuingDistributionPoint.onlyContainsCACerts() && (basicConstraints == null || !basicConstraints.isCA())) {
                                        throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.crlOnlyCaCert"));
                                    }
                                    if (issuingDistributionPoint.onlyContainsAttributeCerts()) {
                                        throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.crlOnlyAttrCert"));
                                    }
                                } catch (AnnotatedException e11) {
                                    throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.crlBCExtError"), e11);
                                }
                            }
                        } catch (AnnotatedException unused) {
                            throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.deltaCrlExtError"));
                        }
                    } catch (AnnotatedException unused2) {
                        throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.distrPtExtError"));
                    }
                } catch (Exception e12) {
                    throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.crlVerifyFailed"), e12);
                }
            }
            if (!z2) {
                throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.noValidCrlFound"));
            }
        } catch (IOException e13) {
            throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.crlIssuerException"), e13);
        }
    }

    public void checkRevocation(PKIXParameters pKIXParameters, X509Certificate x509Certificate, Date date, X509Certificate x509Certificate2, PublicKey publicKey, Vector vector, Vector vector2, int i2) {
        checkCRLs(pKIXParameters, x509Certificate, date, x509Certificate2, publicKey, vector, i2);
    }

    public void doChecks() {
        if (!this.initialized) {
            throw new IllegalStateException("Object not initialized. Call init() first.");
        }
        if (this.notifications != null) {
            return;
        }
        int i2 = this.f1660n;
        this.notifications = new List[i2 + 1];
        this.errors = new List[i2 + 1];
        int i3 = 0;
        while (true) {
            List[] listArr = this.notifications;
            if (i3 >= listArr.length) {
                checkSignatures();
                checkNameConstraints();
                checkPathLength();
                checkPolicy();
                checkCriticalExtensions();
                return;
            }
            listArr[i3] = new ArrayList();
            this.errors[i3] = new ArrayList();
            i3++;
        }
    }

    public Vector getCRLDistUrls(CRLDistPoint cRLDistPoint) {
        Vector vector = new Vector();
        if (cRLDistPoint != null) {
            for (DistributionPoint distributionPoint : cRLDistPoint.getDistributionPoints()) {
                DistributionPointName distributionPoint2 = distributionPoint.getDistributionPoint();
                if (distributionPoint2.getType() == 0) {
                    GeneralName[] names = GeneralNames.getInstance(distributionPoint2.getName()).getNames();
                    for (int i2 = 0; i2 < names.length; i2++) {
                        if (names[i2].getTagNo() == 6) {
                            vector.add(((ASN1IA5String) names[i2].getName()).getString());
                        }
                    }
                }
            }
        }
        return vector;
    }

    public CertPath getCertPath() {
        return this.certPath;
    }

    public int getCertPathSize() {
        return this.f1660n;
    }

    public List getErrors(int i2) {
        doChecks();
        return this.errors[i2 + 1];
    }

    public List getNotifications(int i2) {
        doChecks();
        return this.notifications[i2 + 1];
    }

    public Vector getOCSPUrls(AuthorityInformationAccess authorityInformationAccess) {
        Vector vector = new Vector();
        if (authorityInformationAccess != null) {
            AccessDescription[] accessDescriptions = authorityInformationAccess.getAccessDescriptions();
            for (int i2 = 0; i2 < accessDescriptions.length; i2++) {
                if (accessDescriptions[i2].getAccessMethod().equals((ASN1Primitive) AccessDescription.id_ad_ocsp)) {
                    GeneralName accessLocation = accessDescriptions[i2].getAccessLocation();
                    if (accessLocation.getTagNo() == 6) {
                        vector.add(((ASN1IA5String) accessLocation.getName()).getString());
                    }
                }
            }
        }
        return vector;
    }

    public PolicyNode getPolicyTree() {
        doChecks();
        return this.policyTree;
    }

    public PublicKey getSubjectPublicKey() {
        doChecks();
        return this.subjectPublicKey;
    }

    public TrustAnchor getTrustAnchor() {
        doChecks();
        return this.trustAnchor;
    }

    public Collection getTrustAnchors(X509Certificate x509Certificate, Set set) {
        ArrayList arrayList = new ArrayList();
        Iterator it = set.iterator();
        X509CertSelector x509CertSelector = new X509CertSelector();
        try {
            x509CertSelector.setSubject(CertPathValidatorUtilities.getEncodedIssuerPrincipal(x509Certificate).getEncoded());
            byte[] extensionValue = x509Certificate.getExtensionValue(Extension.authorityKeyIdentifier.getId());
            if (extensionValue != null) {
                AuthorityKeyIdentifier authorityKeyIdentifier = AuthorityKeyIdentifier.getInstance(ASN1Primitive.fromByteArray(((ASN1OctetString) ASN1Primitive.fromByteArray(extensionValue)).getOctets()));
                if (authorityKeyIdentifier.getAuthorityCertSerialNumber() != null) {
                    x509CertSelector.setSerialNumber(authorityKeyIdentifier.getAuthorityCertSerialNumber());
                }
            }
            while (it.hasNext()) {
                TrustAnchor trustAnchor = (TrustAnchor) it.next();
                if (trustAnchor.getTrustedCert() != null) {
                    if (x509CertSelector.match(trustAnchor.getTrustedCert())) {
                        arrayList.add(trustAnchor);
                    }
                } else if (trustAnchor.getCAName() != null && trustAnchor.getCAPublicKey() != null && CertPathValidatorUtilities.getEncodedIssuerPrincipal(x509Certificate).equals(new X500Principal(trustAnchor.getCAName()))) {
                    arrayList.add(trustAnchor);
                }
            }
            return arrayList;
        } catch (IOException unused) {
            throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.trustAnchorIssuerError"));
        }
    }

    public void init(CertPath certPath, PKIXParameters pKIXParameters) {
        if (this.initialized) {
            throw new IllegalStateException("object is already initialized!");
        }
        this.initialized = true;
        if (certPath == null) {
            throw new NullPointerException("certPath was null");
        }
        this.certPath = certPath;
        List<? extends Certificate> certificates = certPath.getCertificates();
        this.certs = certificates;
        this.f1660n = certificates.size();
        if (this.certs.isEmpty()) {
            throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.emptyCertPath"));
        }
        this.pkixParams = (PKIXParameters) pKIXParameters.clone();
        Date date = new Date();
        this.currentDate = date;
        this.validDate = CertPathValidatorUtilities.getValidityDate(this.pkixParams, date);
        this.notifications = null;
        this.errors = null;
        this.trustAnchor = null;
        this.subjectPublicKey = null;
        this.policyTree = null;
    }

    public boolean isValidCertPath() {
        doChecks();
        int i2 = 0;
        while (true) {
            List[] listArr = this.errors;
            if (i2 >= listArr.length) {
                return true;
            }
            if (!listArr[i2].isEmpty()) {
                return false;
            }
            i2++;
        }
    }

    public void addError(ErrorBundle errorBundle, int i2) {
        if (i2 < -1 || i2 >= this.f1660n) {
            throw new IndexOutOfBoundsException();
        }
        this.errors[i2 + 1].add(errorBundle);
    }

    public void addNotification(ErrorBundle errorBundle, int i2) {
        if (i2 < -1 || i2 >= this.f1660n) {
            throw new IndexOutOfBoundsException();
        }
        this.notifications[i2 + 1].add(errorBundle);
    }

    public List[] getErrors() {
        doChecks();
        return this.errors;
    }

    public List[] getNotifications() {
        doChecks();
        return this.notifications;
    }
}
