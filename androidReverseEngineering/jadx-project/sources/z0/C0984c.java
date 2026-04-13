package z0;

import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import q0.AbstractC0887c;

/* renamed from: z0.c */
/* loaded from: classes.dex */
public final class C0984c implements HostnameVerifier {

    /* renamed from: a */
    public static final C0984c f2330a = new C0984c();

    /* renamed from: a */
    public static ArrayList m1475a(X509Certificate x509Certificate) {
        List m1476b = m1476b(x509Certificate, 7);
        List m1476b2 = m1476b(x509Certificate, 2);
        ArrayList arrayList = new ArrayList(m1476b2.size() + m1476b.size());
        arrayList.addAll(m1476b);
        arrayList.addAll(m1476b2);
        return arrayList;
    }

    /* renamed from: b */
    public static List m1476b(X509Certificate x509Certificate, int i2) {
        Integer num;
        String str;
        ArrayList arrayList = new ArrayList();
        try {
            Collection<List<?>> subjectAlternativeNames = x509Certificate.getSubjectAlternativeNames();
            if (subjectAlternativeNames == null) {
                return Collections.emptyList();
            }
            for (List<?> list : subjectAlternativeNames) {
                if (list != null && list.size() >= 2 && (num = (Integer) list.get(0)) != null && num.intValue() == i2 && (str = (String) list.get(1)) != null) {
                    arrayList.add(str);
                }
            }
            return arrayList;
        } catch (CertificateParsingException unused) {
            return Collections.emptyList();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x00eb A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:45:? A[LOOP:1: B:15:0x003a->B:45:?, LOOP_END, SYNTHETIC] */
    /* renamed from: c */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean m1477c(String str, X509Certificate x509Certificate) {
        boolean z2;
        int length;
        if (AbstractC0887c.f1944k.matcher(str).matches()) {
            List m1476b = m1476b(x509Certificate, 7);
            int size = m1476b.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (str.equalsIgnoreCase((String) m1476b.get(i2))) {
                    return true;
                }
            }
        } else {
            String lowerCase = str.toLowerCase(Locale.US);
            for (String str2 : m1476b(x509Certificate, 2)) {
                if (lowerCase != null && lowerCase.length() != 0 && !lowerCase.startsWith(".") && !lowerCase.endsWith("..") && str2 != null && str2.length() != 0 && !str2.startsWith(".") && !str2.endsWith("..")) {
                    String concat = !lowerCase.endsWith(".") ? lowerCase.concat(".") : lowerCase;
                    if (!str2.endsWith(".")) {
                        str2 = str2.concat(".");
                    }
                    String lowerCase2 = str2.toLowerCase(Locale.US);
                    if (!lowerCase2.contains("*")) {
                        z2 = concat.equals(lowerCase2);
                    } else if (lowerCase2.startsWith("*.") && lowerCase2.indexOf(42, 1) == -1 && concat.length() >= lowerCase2.length() && !"*.".equals(lowerCase2)) {
                        String substring = lowerCase2.substring(1);
                        if (concat.endsWith(substring) && ((length = concat.length() - substring.length()) <= 0 || concat.lastIndexOf(46, length - 1) == -1)) {
                            z2 = true;
                        }
                    }
                    if (!z2) {
                        return true;
                    }
                }
                z2 = false;
                if (!z2) {
                }
            }
        }
        return false;
    }

    @Override // javax.net.ssl.HostnameVerifier
    public final boolean verify(String str, SSLSession sSLSession) {
        try {
            return m1477c(str, (X509Certificate) sSLSession.getPeerCertificates()[0]);
        } catch (SSLException unused) {
            return false;
        }
    }
}
