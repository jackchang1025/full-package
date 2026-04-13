package org.bouncycastle.pkix.jcajce;

import android.sun.security.x509.ReasonFlags;
import android.support.v4.os.EnvironmentCompat;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.Provider;
import java.security.PublicKey;
import java.security.cert.CRL;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLSelector;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.CRLDistPoint;
import org.bouncycastle.asn1.x509.DistributionPoint;
import org.bouncycastle.asn1.x509.DistributionPointName;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.jcajce.PKIXCRLStore;
import org.bouncycastle.jcajce.PKIXExtendedParameters;
import org.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.jcajce.util.NamedJcaJceHelper;
import org.bouncycastle.jcajce.util.ProviderJcaJceHelper;
import org.bouncycastle.util.CollectionStore;
import org.bouncycastle.util.Iterable;
import org.bouncycastle.util.Selector;
import org.bouncycastle.util.Store;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class X509RevocationChecker extends PKIXCertPathChecker {
    public static final int CHAIN_VALIDITY_MODEL = 1;
    public static final int PKIX_VALIDITY_MODEL = 0;
    private final boolean canSoftFail;
    private final List<CertStore> crlCertStores;
    private final List<Store<CRL>> crls;
    private Date currentDate;
    private final long failHardMaxTime;
    private final long failLogMaxTime;
    private final Map<X500Principal, Long> failures;
    private final JcaJceHelper helper;
    private final boolean isCheckEEOnly;
    private X509Certificate signingCert;
    private final Set<TrustAnchor> trustAnchors;
    private final int validityModel;
    private X500Principal workingIssuerName;
    private PublicKey workingPublicKey;
    private static Logger LOG = Logger.getLogger(X509RevocationChecker.class.getName());
    private static final Map<GeneralName, WeakReference<X509CRL>> crlCache = Collections.synchronizedMap(new WeakHashMap());
    protected static final String[] crlReasons = {"unspecified", "keyCompromise", "cACompromise", "affiliationChanged", ReasonFlags.SUPERSEDED, "cessationOfOperation", "certificateHold", EnvironmentCompat.MEDIA_UNKNOWN, "removeFromCRL", "privilegeWithdrawn", "aACompromise"};

    public static class Builder {
        private boolean canSoftFail;
        private List<CertStore> crlCertStores;
        private List<Store<CRL>> crls;
        private long failHardMaxTime;
        private long failLogMaxTime;
        private boolean isCheckEEOnly;
        private Provider provider;
        private String providerName;
        private Set<TrustAnchor> trustAnchors;
        private int validityModel;

        public Builder(KeyStore keyStore) {
            this.crlCertStores = new ArrayList();
            this.crls = new ArrayList();
            this.validityModel = 0;
            this.trustAnchors = new HashSet();
            Enumeration<String> aliases = keyStore.aliases();
            while (aliases.hasMoreElements()) {
                String nextElement = aliases.nextElement();
                if (keyStore.isCertificateEntry(nextElement)) {
                    this.trustAnchors.add(new TrustAnchor((X509Certificate) keyStore.getCertificate(nextElement), null));
                }
            }
        }

        public Builder addCrls(CertStore certStore) {
            this.crlCertStores.add(certStore);
            return this;
        }

        public X509RevocationChecker build() {
            return new X509RevocationChecker(this);
        }

        public Builder setCheckEndEntityOnly(boolean z2) {
            this.isCheckEEOnly = z2;
            return this;
        }

        public Builder setSoftFail(boolean z2, long j2) {
            this.canSoftFail = z2;
            this.failLogMaxTime = j2;
            this.failHardMaxTime = -1L;
            return this;
        }

        public Builder setSoftFailHardLimit(boolean z2, long j2) {
            this.canSoftFail = z2;
            this.failLogMaxTime = (3 * j2) / 4;
            this.failHardMaxTime = j2;
            return this;
        }

        public Builder setValidityModel(int i2) {
            this.validityModel = i2;
            return this;
        }

        public Builder usingProvider(String str) {
            this.providerName = str;
            return this;
        }

        public Builder(TrustAnchor trustAnchor) {
            this.crlCertStores = new ArrayList();
            this.crls = new ArrayList();
            this.validityModel = 0;
            this.trustAnchors = Collections.singleton(trustAnchor);
        }

        public Builder addCrls(Store<CRL> store) {
            this.crls.add(store);
            return this;
        }

        public Builder usingProvider(Provider provider) {
            this.provider = provider;
            return this;
        }

        public Builder(Set<TrustAnchor> set) {
            this.crlCertStores = new ArrayList();
            this.crls = new ArrayList();
            this.validityModel = 0;
            this.trustAnchors = new HashSet(set);
        }
    }

    public class LocalCRLStore implements PKIXCRLStore<CRL>, Iterable<CRL> {
        private Collection<CRL> _local;

        public LocalCRLStore(Store<CRL> store) {
            this._local = new ArrayList(store.getMatches(null));
        }

        @Override // org.bouncycastle.jcajce.PKIXCRLStore, org.bouncycastle.util.Store
        public Collection<CRL> getMatches(Selector<CRL> selector) {
            if (selector == null) {
                return new ArrayList(this._local);
            }
            ArrayList arrayList = new ArrayList();
            for (CRL crl : this._local) {
                if (selector.match(crl)) {
                    arrayList.add(crl);
                }
            }
            return arrayList;
        }

        @Override // org.bouncycastle.util.Iterable, java.lang.Iterable
        public Iterator<CRL> iterator() {
            return getMatches(null).iterator();
        }
    }

    private X509RevocationChecker(Builder builder) {
        JcaJceHelper namedJcaJceHelper;
        this.failures = new HashMap();
        this.crls = new ArrayList(builder.crls);
        this.crlCertStores = new ArrayList(builder.crlCertStores);
        this.isCheckEEOnly = builder.isCheckEEOnly;
        this.validityModel = builder.validityModel;
        this.trustAnchors = builder.trustAnchors;
        this.canSoftFail = builder.canSoftFail;
        this.failLogMaxTime = builder.failLogMaxTime;
        this.failHardMaxTime = builder.failHardMaxTime;
        if (builder.provider != null) {
            namedJcaJceHelper = new ProviderJcaJceHelper(builder.provider);
        } else {
            if (builder.providerName == null) {
                this.helper = new DefaultJcaJceHelper();
                return;
            }
            namedJcaJceHelper = new NamedJcaJceHelper(builder.providerName);
        }
        this.helper = namedJcaJceHelper;
    }

    private void addIssuers(final List<X500Principal> list, CertStore certStore) {
        certStore.getCRLs(new X509CRLSelector() { // from class: org.bouncycastle.pkix.jcajce.X509RevocationChecker.1
            @Override // java.security.cert.X509CRLSelector, java.security.cert.CRLSelector
            public boolean match(CRL crl) {
                if (!(crl instanceof X509CRL)) {
                    return false;
                }
                list.add(((X509CRL) crl).getIssuerX500Principal());
                return false;
            }
        });
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 3 */
    private CRL downloadCRLs(X500Principal x500Principal, Date date, ASN1Primitive aSN1Primitive, JcaJceHelper jcaJceHelper) {
        URL url;
        X509CRL x509crl;
        Logger logger;
        Level level;
        StringBuilder sb;
        DistributionPoint[] distributionPoints = CRLDistPoint.getInstance(aSN1Primitive).getDistributionPoints();
        for (int i2 = 0; i2 != distributionPoints.length; i2++) {
            DistributionPointName distributionPoint = distributionPoints[i2].getDistributionPoint();
            if (distributionPoint != null && distributionPoint.getType() == 0) {
                GeneralName[] names = GeneralNames.getInstance(distributionPoint.getName()).getNames();
                for (int i3 = 0; i3 != names.length; i3++) {
                    GeneralName generalName = names[i3];
                    if (generalName.getTagNo() == 6) {
                        Map<GeneralName, WeakReference<X509CRL>> map = crlCache;
                        WeakReference<X509CRL> weakReference = map.get(generalName);
                        if (weakReference != null) {
                            X509CRL x509crl2 = weakReference.get();
                            if (x509crl2 != null && !date.before(x509crl2.getThisUpdate()) && !date.after(x509crl2.getNextUpdate())) {
                                return x509crl2;
                            }
                            map.remove(generalName);
                        }
                        try {
                            url = new URL(generalName.getName().toString());
                            try {
                                CertificateFactory createCertificateFactory = jcaJceHelper.createCertificateFactory("X.509");
                                InputStream openStream = url.openStream();
                                x509crl = (X509CRL) createCertificateFactory.generateCRL(new BufferedInputStream(openStream));
                                openStream.close();
                                logger = LOG;
                                level = Level.INFO;
                                sb = new StringBuilder();
                                sb.append("downloaded CRL from CrlDP ");
                                sb.append(url);
                                sb.append(" for issuer \"");
                            } catch (Exception e2) {
                                e = e2;
                            }
                            try {
                                sb.append(x500Principal);
                                sb.append("\"");
                                logger.log(level, sb.toString());
                                map.put(generalName, new WeakReference<>(x509crl));
                                return x509crl;
                            } catch (Exception e3) {
                                e = e3;
                                Logger logger2 = LOG;
                                Level level2 = Level.FINE;
                                if (logger2.isLoggable(level2)) {
                                    LOG.log(level2, "CrlDP " + url + " ignored: " + e.getMessage(), (Throwable) e);
                                } else {
                                    LOG.log(Level.INFO, "CrlDP " + url + " ignored: " + e.getMessage());
                                }
                            }
                        } catch (Exception e4) {
                            e = e4;
                            url = null;
                        }
                    }
                }
            }
        }
        return null;
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
            throw new AnnotatedException("could not read distribution points could not be read", e2);
        }
    }

    @Override // java.security.cert.PKIXCertPathChecker
    public void check(Certificate certificate, Collection<String> collection) {
        Logger logger;
        Level level;
        StringBuilder sb;
        X509Certificate x509Certificate = (X509Certificate) certificate;
        if (this.isCheckEEOnly && x509Certificate.getBasicConstraints() != -1) {
            this.workingIssuerName = x509Certificate.getSubjectX500Principal();
            this.workingPublicKey = x509Certificate.getPublicKey();
            this.signingCert = x509Certificate;
            return;
        }
        if (this.workingIssuerName == null) {
            this.workingIssuerName = x509Certificate.getIssuerX500Principal();
            TrustAnchor trustAnchor = null;
            for (TrustAnchor trustAnchor2 : this.trustAnchors) {
                if (this.workingIssuerName.equals(trustAnchor2.getCA()) || this.workingIssuerName.equals(trustAnchor2.getTrustedCert().getSubjectX500Principal())) {
                    trustAnchor = trustAnchor2;
                }
            }
            if (trustAnchor == null) {
                throw new CertPathValidatorException("no trust anchor found for " + this.workingIssuerName);
            }
            X509Certificate trustedCert = trustAnchor.getTrustedCert();
            this.signingCert = trustedCert;
            this.workingPublicKey = trustedCert.getPublicKey();
        }
        ArrayList arrayList = new ArrayList();
        try {
            PKIXParameters pKIXParameters = new PKIXParameters(this.trustAnchors);
            pKIXParameters.setRevocationEnabled(false);
            pKIXParameters.setDate(this.currentDate);
            for (int i2 = 0; i2 != this.crlCertStores.size(); i2++) {
                if (LOG.isLoggable(Level.INFO)) {
                    addIssuers(arrayList, this.crlCertStores.get(i2));
                }
                pKIXParameters.addCertStore(this.crlCertStores.get(i2));
            }
            PKIXExtendedParameters.Builder builder = new PKIXExtendedParameters.Builder(pKIXParameters);
            builder.setValidityModel(this.validityModel);
            for (int i3 = 0; i3 != this.crls.size(); i3++) {
                if (LOG.isLoggable(Level.INFO)) {
                    addIssuers(arrayList, this.crls.get(i3));
                }
                builder.addCRLStore(new LocalCRLStore(this.crls.get(i3)));
            }
            if (arrayList.isEmpty()) {
                LOG.log(Level.INFO, "configured with 0 pre-loaded CRLs");
            } else if (LOG.isLoggable(Level.FINE)) {
                for (int i4 = 0; i4 != arrayList.size(); i4++) {
                    LOG.log(Level.FINE, "configuring with CRL for issuer \"" + arrayList.get(i4) + "\"");
                }
            } else {
                LOG.log(Level.INFO, "configured with " + arrayList.size() + " pre-loaded CRLs");
            }
            PKIXExtendedParameters build = builder.build();
            try {
                checkCRLs(build, this.currentDate, RevocationUtilities.getValidityDate(build, this.currentDate), x509Certificate, this.signingCert, this.workingPublicKey, new ArrayList(), this.helper);
            } catch (AnnotatedException e2) {
                throw new CertPathValidatorException(e2.getMessage(), e2.getCause());
            } catch (CRLNotFoundException e3) {
                ASN1ObjectIdentifier aSN1ObjectIdentifier = Extension.cRLDistributionPoints;
                if (x509Certificate.getExtensionValue(aSN1ObjectIdentifier.getId()) == null) {
                    throw e3;
                }
                try {
                    CRL downloadCRLs = downloadCRLs(x509Certificate.getIssuerX500Principal(), this.currentDate, RevocationUtilities.getExtensionValue(x509Certificate, aSN1ObjectIdentifier), this.helper);
                    if (downloadCRLs != null) {
                        try {
                            builder.addCRLStore(new LocalCRLStore(new CollectionStore(Collections.singleton(downloadCRLs))));
                            PKIXExtendedParameters build2 = builder.build();
                            checkCRLs(build2, this.currentDate, RevocationUtilities.getValidityDate(build2, this.currentDate), x509Certificate, this.signingCert, this.workingPublicKey, new ArrayList(), this.helper);
                        } catch (AnnotatedException e4) {
                            throw new CertPathValidatorException(e4.getMessage(), e4.getCause());
                        }
                    } else {
                        if (!this.canSoftFail) {
                            throw e3;
                        }
                        X500Principal issuerX500Principal = x509Certificate.getIssuerX500Principal();
                        Long l2 = this.failures.get(issuerX500Principal);
                        if (l2 != null) {
                            long currentTimeMillis = System.currentTimeMillis() - l2.longValue();
                            long j2 = this.failHardMaxTime;
                            if (j2 != -1 && j2 < currentTimeMillis) {
                                throw e3;
                            }
                            if (currentTimeMillis < this.failLogMaxTime) {
                                logger = LOG;
                                level = Level.WARNING;
                                sb = new StringBuilder("soft failing for issuer: \"");
                            } else {
                                logger = LOG;
                                level = Level.SEVERE;
                                sb = new StringBuilder("soft failing for issuer: \"");
                            }
                            sb.append(issuerX500Principal);
                            sb.append("\"");
                            logger.log(level, sb.toString());
                        } else {
                            this.failures.put(issuerX500Principal, Long.valueOf(System.currentTimeMillis()));
                        }
                    }
                } catch (AnnotatedException e5) {
                    throw new CertPathValidatorException(e5.getMessage(), e5.getCause());
                }
            }
            this.signingCert = x509Certificate;
            this.workingPublicKey = x509Certificate.getPublicKey();
            this.workingIssuerName = x509Certificate.getSubjectX500Principal();
        } catch (GeneralSecurityException e6) {
            throw new RuntimeException(AbstractC0000a.m19o(e6, new StringBuilder("error setting up baseParams: ")));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x00fd  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x010f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void checkCRLs(PKIXExtendedParameters pKIXExtendedParameters, Date date, Date date2, X509Certificate x509Certificate, X509Certificate x509Certificate2, PublicKey publicKey, List list, JcaJceHelper jcaJceHelper) {
        boolean z2;
        AnnotatedException e2;
        int i2;
        PKIXExtendedParameters pKIXExtendedParameters2;
        DistributionPoint[] distributionPointArr;
        try {
            CRLDistPoint cRLDistPoint = CRLDistPoint.getInstance(RevocationUtilities.getExtensionValue(x509Certificate, Extension.cRLDistributionPoints));
            CertStatus certStatus = new CertStatus();
            ReasonsMask reasonsMask = new ReasonsMask();
            if (cRLDistPoint != null) {
                try {
                    DistributionPoint[] distributionPoints = cRLDistPoint.getDistributionPoints();
                    if (distributionPoints != null) {
                        PKIXExtendedParameters.Builder builder = new PKIXExtendedParameters.Builder(pKIXExtendedParameters);
                        try {
                            Iterator<PKIXCRLStore> it = getAdditionalStoresFromCRLDistributionPoint(cRLDistPoint, pKIXExtendedParameters.getNamedCRLStoreMap()).iterator();
                            while (it.hasNext()) {
                                builder.addCRLStore(it.next());
                            }
                            PKIXExtendedParameters build = builder.build();
                            Date validityDate = RevocationUtilities.getValidityDate(build, date);
                            int i3 = 0;
                            z2 = false;
                            e2 = null;
                            while (i3 < distributionPoints.length && certStatus.getCertStatus() == 11 && !reasonsMask.isAllReasons()) {
                                try {
                                    i2 = i3;
                                    pKIXExtendedParameters2 = build;
                                    distributionPointArr = distributionPoints;
                                    try {
                                        RFC3280CertPathUtilities.checkCRL(distributionPoints[i3], build, date, validityDate, x509Certificate, x509Certificate2, publicKey, certStatus, reasonsMask, list, jcaJceHelper);
                                        z2 = true;
                                    } catch (AnnotatedException e3) {
                                        e2 = e3;
                                    }
                                } catch (AnnotatedException e4) {
                                    e2 = e4;
                                    i2 = i3;
                                    pKIXExtendedParameters2 = build;
                                    distributionPointArr = distributionPoints;
                                }
                                i3 = i2 + 1;
                                build = pKIXExtendedParameters2;
                                distributionPoints = distributionPointArr;
                            }
                            if (certStatus.getCertStatus() == 11 && !reasonsMask.isAllReasons()) {
                                try {
                                    RFC3280CertPathUtilities.checkCRL(new DistributionPoint(new DistributionPointName(0, new GeneralNames(new GeneralName(4, X500Name.getInstance(x509Certificate.getIssuerX500Principal().getEncoded())))), null, null), (PKIXExtendedParameters) pKIXExtendedParameters.clone(), date, date2, x509Certificate, x509Certificate2, publicKey, certStatus, reasonsMask, list, jcaJceHelper);
                                    z2 = true;
                                } catch (AnnotatedException e5) {
                                    e2 = e5;
                                }
                            }
                            if (z2) {
                                if (!(e2 instanceof AnnotatedException)) {
                                    throw new CRLNotFoundException("no valid CRL found");
                                }
                                throw new CRLNotFoundException("no valid CRL found", e2);
                            }
                            if (certStatus.getCertStatus() == 11) {
                                if (!reasonsMask.isAllReasons() && certStatus.getCertStatus() == 11) {
                                    certStatus.setCertStatus(12);
                                }
                                if (certStatus.getCertStatus() == 12) {
                                    throw new AnnotatedException("certificate status could not be determined");
                                }
                                return;
                            }
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
                            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                            StringBuilder m22r = AbstractC0000a.m22r("certificate [issuer=\"" + x509Certificate.getIssuerX500Principal() + "\",serialNumber=" + x509Certificate.getSerialNumber() + ",subject=\"" + x509Certificate.getSubjectX500Principal() + "\"] revoked after " + simpleDateFormat.format(certStatus.getRevocationDate()), ", reason: ");
                            m22r.append(crlReasons[certStatus.getCertStatus()]);
                            throw new AnnotatedException(m22r.toString());
                        } catch (AnnotatedException e6) {
                            throw new AnnotatedException("no additional CRL locations could be decoded from CRL distribution point extension", e6);
                        }
                    }
                } catch (Exception e7) {
                    throw new AnnotatedException("cannot read distribution points", e7);
                }
            }
            e2 = null;
            z2 = false;
            if (certStatus.getCertStatus() == 11) {
                RFC3280CertPathUtilities.checkCRL(new DistributionPoint(new DistributionPointName(0, new GeneralNames(new GeneralName(4, X500Name.getInstance(x509Certificate.getIssuerX500Principal().getEncoded())))), null, null), (PKIXExtendedParameters) pKIXExtendedParameters.clone(), date, date2, x509Certificate, x509Certificate2, publicKey, certStatus, reasonsMask, list, jcaJceHelper);
                z2 = true;
            }
            if (z2) {
            }
        } catch (Exception e8) {
            throw new AnnotatedException("cannot read CRL distribution point extension", e8);
        }
    }

    @Override // java.security.cert.PKIXCertPathChecker
    public Object clone() {
        return this;
    }

    @Override // java.security.cert.PKIXCertPathChecker
    public Set<String> getSupportedExtensions() {
        return null;
    }

    @Override // java.security.cert.PKIXCertPathChecker, java.security.cert.CertPathChecker
    public void init(boolean z2) {
        if (z2) {
            throw new IllegalArgumentException("forward processing not supported");
        }
        this.currentDate = new Date();
        this.workingIssuerName = null;
    }

    @Override // java.security.cert.PKIXCertPathChecker, java.security.cert.CertPathChecker
    public boolean isForwardCheckingSupported() {
        return false;
    }

    private void addIssuers(final List<X500Principal> list, Store<CRL> store) {
        store.getMatches(new Selector<CRL>() { // from class: org.bouncycastle.pkix.jcajce.X509RevocationChecker.2
            @Override // org.bouncycastle.util.Selector
            public Object clone() {
                return this;
            }

            @Override // org.bouncycastle.util.Selector
            public boolean match(CRL crl) {
                if (!(crl instanceof X509CRL)) {
                    return false;
                }
                list.add(((X509CRL) crl).getIssuerX500Principal());
                return false;
            }
        });
    }
}
