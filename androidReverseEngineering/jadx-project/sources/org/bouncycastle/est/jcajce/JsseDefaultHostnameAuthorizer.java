package org.bouncycastle.est.jcajce;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSession;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.x500.AttributeTypeAndValue;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.est.ESTException;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class JsseDefaultHostnameAuthorizer implements JsseHostnameAuthorizer {
    private static Logger LOG = Logger.getLogger(JsseDefaultHostnameAuthorizer.class.getName());
    private final Set<String> knownSuffixes;

    public JsseDefaultHostnameAuthorizer(Set<String> set) {
        this.knownSuffixes = set;
    }

    public static boolean isValidNameMatch(String str, String str2, Set<String> set) {
        if (!str2.contains("*")) {
            return str.equalsIgnoreCase(str2);
        }
        int indexOf = str2.indexOf(42);
        if (indexOf != str2.lastIndexOf("*") || str2.contains("..") || str2.charAt(str2.length() - 1) == '*') {
            return false;
        }
        int indexOf2 = str2.indexOf(46, indexOf);
        if (set != null && set.contains(Strings.toLowerCase(str2.substring(indexOf2)))) {
            throw new IOException(AbstractC0000a.m16l("Wildcard `", str2, "` matches known public suffix."));
        }
        String lowerCase = Strings.toLowerCase(str2.substring(indexOf + 1));
        String lowerCase2 = Strings.toLowerCase(str);
        if (lowerCase2.equals(lowerCase) || lowerCase.length() > lowerCase2.length()) {
            return false;
        }
        if (indexOf > 0) {
            return lowerCase2.startsWith(str2.substring(0, indexOf)) && lowerCase2.endsWith(lowerCase) && lowerCase2.substring(indexOf, lowerCase2.length() - lowerCase.length()).indexOf(46) < 0;
        }
        if (lowerCase2.substring(0, lowerCase2.length() - lowerCase.length()).indexOf(46) > 0) {
            return false;
        }
        return lowerCase2.endsWith(lowerCase);
    }

    @Override // org.bouncycastle.est.jcajce.JsseHostnameAuthorizer
    public boolean verified(String str, SSLSession sSLSession) {
        try {
            return verify(str, (X509Certificate) CertificateFactory.getInstance("X509").generateCertificate(new ByteArrayInputStream(sSLSession.getPeerCertificates()[0].getEncoded())));
        } catch (Exception e2) {
            if (e2 instanceof ESTException) {
                throw ((ESTException) e2);
            }
            throw new ESTException(e2.getMessage(), e2);
        }
    }

    public boolean verify(String str, X509Certificate x509Certificate) {
        try {
            Collection<List<?>> subjectAlternativeNames = x509Certificate.getSubjectAlternativeNames();
            if (subjectAlternativeNames == null) {
                if (x509Certificate.getSubjectX500Principal() == null) {
                    return false;
                }
                RDN[] rDNs = X500Name.getInstance(x509Certificate.getSubjectX500Principal().getEncoded()).getRDNs();
                for (int length = rDNs.length - 1; length >= 0; length--) {
                    AttributeTypeAndValue[] typesAndValues = rDNs[length].getTypesAndValues();
                    for (int i2 = 0; i2 != typesAndValues.length; i2++) {
                        AttributeTypeAndValue attributeTypeAndValue = typesAndValues[i2];
                        if (attributeTypeAndValue.getType().equals((ASN1Primitive) BCStyle.CN)) {
                            return isValidNameMatch(str, attributeTypeAndValue.getValue().toString(), this.knownSuffixes);
                        }
                    }
                }
                return false;
            }
            for (List<?> list : subjectAlternativeNames) {
                int intValue = ((Number) list.get(0)).intValue();
                if (intValue == 2) {
                    if (isValidNameMatch(str, list.get(1).toString(), this.knownSuffixes)) {
                        return true;
                    }
                } else if (intValue == 7) {
                    if (InetAddress.getByName(str).equals(InetAddress.getByName(list.get(1).toString()))) {
                        return true;
                    }
                } else if (LOG.isLoggable(Level.INFO)) {
                    String hexString = list.get(1) instanceof byte[] ? Hex.toHexString((byte[]) list.get(1)) : list.get(1).toString();
                    LOG.log(Level.INFO, "ignoring type " + intValue + " value = " + hexString);
                }
            }
            return false;
        } catch (Exception e2) {
            throw new ESTException(e2.getMessage(), e2);
        }
    }
}
