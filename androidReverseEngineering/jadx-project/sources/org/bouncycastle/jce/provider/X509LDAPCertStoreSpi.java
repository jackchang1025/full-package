package org.bouncycastle.jce.provider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CRL;
import java.security.cert.CRLSelector;
import java.security.cert.CertSelector;
import java.security.cert.CertStoreException;
import java.security.cert.CertStoreParameters;
import java.security.cert.CertStoreSpi;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRLSelector;
import java.security.cert.X509CertSelector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.x509.CertificatePair;
import org.bouncycastle.jce.X509LDAPCertStoreParameters;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class X509LDAPCertStoreSpi extends CertStoreSpi {
    private static String LDAP_PROVIDER = "com.sun.jndi.ldap.LdapCtxFactory";
    private static String REFERRALS_IGNORE = "ignore";
    private static final String SEARCH_SECURITY_LEVEL = "none";
    private static final String URL_CONTEXT_PREFIX = "com.sun.jndi.url";
    private X509LDAPCertStoreParameters params;

    public X509LDAPCertStoreSpi(CertStoreParameters certStoreParameters) {
        super(certStoreParameters);
        if (certStoreParameters instanceof X509LDAPCertStoreParameters) {
            this.params = (X509LDAPCertStoreParameters) certStoreParameters;
            return;
        }
        throw new InvalidAlgorithmParameterException(X509LDAPCertStoreSpi.class.getName() + ": parameter must be a " + X509LDAPCertStoreParameters.class.getName() + " object\n" + certStoreParameters.toString());
    }

    private Set certSubjectSerialSearch(X509CertSelector x509CertSelector, String[] strArr, String str, String str2) {
        String name;
        String str3;
        Set search;
        HashSet hashSet = new HashSet();
        try {
            if (x509CertSelector.getSubjectAsBytes() == null && x509CertSelector.getSubjectAsString() == null && x509CertSelector.getCertificate() == null) {
                search = search(str, "*", strArr);
                hashSet.addAll(search);
                return hashSet;
            }
            if (x509CertSelector.getCertificate() != null) {
                name = x509CertSelector.getCertificate().getSubjectX500Principal().getName("RFC1779");
                str3 = x509CertSelector.getCertificate().getSerialNumber().toString();
            } else {
                name = x509CertSelector.getSubjectAsBytes() != null ? new X500Principal(x509CertSelector.getSubjectAsBytes()).getName("RFC1779") : x509CertSelector.getSubjectAsString();
                str3 = null;
            }
            hashSet.addAll(search(str, "*" + parseDN(name, str2) + "*", strArr));
            if (str3 != null && this.params.getSearchForSerialNumberIn() != null) {
                search = search(this.params.getSearchForSerialNumberIn(), "*" + str3 + "*", strArr);
                hashSet.addAll(search);
            }
            return hashSet;
        } catch (IOException e2) {
            throw new CertStoreException(AbstractC0000a.m13i("exception processing selector: ", e2));
        }
    }

    private DirContext connectLDAP() {
        Properties properties = new Properties();
        properties.setProperty("java.naming.factory.initial", LDAP_PROVIDER);
        properties.setProperty("java.naming.batchsize", "0");
        properties.setProperty("java.naming.provider.url", this.params.getLdapURL());
        properties.setProperty("java.naming.factory.url.pkgs", URL_CONTEXT_PREFIX);
        properties.setProperty("java.naming.referral", REFERRALS_IGNORE);
        properties.setProperty("java.naming.security.authentication", SEARCH_SECURITY_LEVEL);
        return new InitialDirContext(properties);
    }

    private Set getCACertificates(X509CertSelector x509CertSelector) {
        String[] strArr = {this.params.getCACertificateAttribute()};
        Set certSubjectSerialSearch = certSubjectSerialSearch(x509CertSelector, strArr, this.params.getLdapCACertificateAttributeName(), this.params.getCACertificateSubjectAttributeName());
        if (certSubjectSerialSearch.isEmpty()) {
            certSubjectSerialSearch.addAll(search(null, "*", strArr));
        }
        return certSubjectSerialSearch;
    }

    private Set getCrossCertificates(X509CertSelector x509CertSelector) {
        String[] strArr = {this.params.getCrossCertificateAttribute()};
        Set certSubjectSerialSearch = certSubjectSerialSearch(x509CertSelector, strArr, this.params.getLdapCrossCertificateAttributeName(), this.params.getCrossCertificateSubjectAttributeName());
        if (certSubjectSerialSearch.isEmpty()) {
            certSubjectSerialSearch.addAll(search(null, "*", strArr));
        }
        return certSubjectSerialSearch;
    }

    private Set getEndCertificates(X509CertSelector x509CertSelector) {
        return certSubjectSerialSearch(x509CertSelector, new String[]{this.params.getUserCertificateAttribute()}, this.params.getLdapUserCertificateAttributeName(), this.params.getUserCertificateSubjectAttributeName());
    }

    private String parseDN(String str, String str2) {
        String substring = str.substring(str2.length() + str.toLowerCase().indexOf(str2.toLowerCase()));
        int indexOf = substring.indexOf(44);
        if (indexOf == -1) {
            indexOf = substring.length();
        }
        while (substring.charAt(indexOf - 1) == '\\') {
            indexOf = substring.indexOf(44, indexOf + 1);
            if (indexOf == -1) {
                indexOf = substring.length();
            }
        }
        String substring2 = substring.substring(0, indexOf);
        String substring3 = substring2.substring(substring2.indexOf(61) + 1);
        if (substring3.charAt(0) == ' ') {
            substring3 = substring3.substring(1);
        }
        if (substring3.startsWith("\"")) {
            substring3 = substring3.substring(1);
        }
        return substring3.endsWith("\"") ? substring3.substring(0, substring3.length() - 1) : substring3;
    }

    private Set search(String str, String str2, String[] strArr) {
        String str3 = str + "=" + str2;
        DirContext dirContext = null;
        if (str == null) {
            str3 = null;
        }
        HashSet hashSet = new HashSet();
        try {
            try {
                dirContext = connectLDAP();
                SearchControls searchControls = new SearchControls();
                searchControls.setSearchScope(2);
                searchControls.setCountLimit(0L);
                for (String str4 : strArr) {
                    String[] strArr2 = {str4};
                    searchControls.setReturningAttributes(strArr2);
                    String str5 = "(&(" + str3 + ")(" + strArr2[0] + "=*))";
                    if (str3 == null) {
                        str5 = "(" + strArr2[0] + "=*)";
                    }
                    NamingEnumeration search = dirContext.search(this.params.getBaseDN(), str5, searchControls);
                    while (search.hasMoreElements()) {
                        NamingEnumeration all = ((Attribute) ((SearchResult) search.next()).getAttributes().getAll().next()).getAll();
                        while (all.hasMore()) {
                            hashSet.add(all.next());
                        }
                    }
                }
                if (dirContext != null) {
                    try {
                        dirContext.close();
                    } catch (Exception unused) {
                    }
                }
                return hashSet;
            } catch (Exception e2) {
                throw new CertStoreException("Error getting results from LDAP directory " + e2);
            }
        } catch (Throwable th) {
            if (dirContext != null) {
                try {
                    dirContext.close();
                } catch (Exception unused2) {
                }
            }
            throw th;
        }
    }

    @Override // java.security.cert.CertStoreSpi
    public Collection engineGetCRLs(CRLSelector cRLSelector) {
        String certificateRevocationListIssuerAttributeName;
        String name;
        String[] strArr = {this.params.getCertificateRevocationListAttribute()};
        if (!(cRLSelector instanceof X509CRLSelector)) {
            throw new CertStoreException("selector is not a X509CRLSelector");
        }
        X509CRLSelector x509CRLSelector = (X509CRLSelector) cRLSelector;
        HashSet hashSet = new HashSet();
        String ldapCertificateRevocationListAttributeName = this.params.getLdapCertificateRevocationListAttributeName();
        HashSet hashSet2 = new HashSet();
        if (x509CRLSelector.getIssuerNames() != null) {
            for (Object obj : x509CRLSelector.getIssuerNames()) {
                if (obj instanceof String) {
                    certificateRevocationListIssuerAttributeName = this.params.getCertificateRevocationListIssuerAttributeName();
                    name = (String) obj;
                } else {
                    certificateRevocationListIssuerAttributeName = this.params.getCertificateRevocationListIssuerAttributeName();
                    name = new X500Principal((byte[]) obj).getName("RFC1779");
                }
                hashSet2.addAll(search(ldapCertificateRevocationListAttributeName, AbstractC0000a.m16l("*", parseDN(name, certificateRevocationListIssuerAttributeName), "*"), strArr));
            }
        } else {
            hashSet2.addAll(search(ldapCertificateRevocationListAttributeName, "*", strArr));
        }
        hashSet2.addAll(search(null, "*", strArr));
        Iterator it = hashSet2.iterator();
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509", BouncyCastleProvider.PROVIDER_NAME);
            while (it.hasNext()) {
                CRL generateCRL = certificateFactory.generateCRL(new ByteArrayInputStream((byte[]) it.next()));
                if (x509CRLSelector.match(generateCRL)) {
                    hashSet.add(generateCRL);
                }
            }
            return hashSet;
        } catch (Exception e2) {
            throw new CertStoreException(AbstractC0000a.m14j("CRL cannot be constructed from LDAP result ", e2));
        }
    }

    @Override // java.security.cert.CertStoreSpi
    public Collection engineGetCertificates(CertSelector certSelector) {
        if (!(certSelector instanceof X509CertSelector)) {
            throw new CertStoreException("selector is not a X509CertSelector");
        }
        X509CertSelector x509CertSelector = (X509CertSelector) certSelector;
        HashSet hashSet = new HashSet();
        Set<byte[]> endCertificates = getEndCertificates(x509CertSelector);
        endCertificates.addAll(getCACertificates(x509CertSelector));
        endCertificates.addAll(getCrossCertificates(x509CertSelector));
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509", BouncyCastleProvider.PROVIDER_NAME);
            for (byte[] bArr : endCertificates) {
                if (bArr != null && bArr.length != 0) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(bArr);
                    try {
                        CertificatePair certificatePair = CertificatePair.getInstance(new ASN1InputStream(bArr).readObject());
                        arrayList.clear();
                        if (certificatePair.getForward() != null) {
                            arrayList.add(certificatePair.getForward().getEncoded());
                        }
                        if (certificatePair.getReverse() != null) {
                            arrayList.add(certificatePair.getReverse().getEncoded());
                        }
                    } catch (IOException | IllegalArgumentException unused) {
                    }
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        try {
                            Certificate generateCertificate = certificateFactory.generateCertificate(new ByteArrayInputStream((byte[]) it.next()));
                            if (x509CertSelector.match(generateCertificate)) {
                                hashSet.add(generateCertificate);
                            }
                        } catch (Exception unused2) {
                        }
                    }
                }
            }
            return hashSet;
        } catch (Exception e2) {
            throw new CertStoreException(AbstractC0000a.m14j("certificate cannot be constructed from LDAP result: ", e2));
        }
    }
}
