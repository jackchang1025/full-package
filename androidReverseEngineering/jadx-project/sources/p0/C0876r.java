package p0;

import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import q0.AbstractC0887c;

/* renamed from: p0.r */
/* loaded from: classes.dex */
public final class C0876r {

    /* renamed from: a */
    public final n0 f1892a;

    /* renamed from: b */
    public final C0867i f1893b;

    /* renamed from: c */
    public final List f1894c;

    /* renamed from: d */
    public final List f1895d;

    public C0876r(n0 n0Var, C0867i c0867i, List list, List list2) {
        this.f1892a = n0Var;
        this.f1893b = c0867i;
        this.f1894c = list;
        this.f1895d = list2;
    }

    /* renamed from: a */
    public static C0876r m1276a(SSLSession sSLSession) {
        Certificate[] certificateArr;
        String cipherSuite = sSLSession.getCipherSuite();
        if (cipherSuite == null) {
            throw new IllegalStateException("cipherSuite == null");
        }
        if ("SSL_NULL_WITH_NULL_NULL".equals(cipherSuite)) {
            throw new IOException("cipherSuite == SSL_NULL_WITH_NULL_NULL");
        }
        C0867i m1257a = C0867i.m1257a(cipherSuite);
        String protocol = sSLSession.getProtocol();
        if (protocol == null) {
            throw new IllegalStateException("tlsVersion == null");
        }
        if ("NONE".equals(protocol)) {
            throw new IOException("tlsVersion == NONE");
        }
        n0 m1272a = n0.m1272a(protocol);
        try {
            certificateArr = sSLSession.getPeerCertificates();
        } catch (SSLPeerUnverifiedException unused) {
            certificateArr = null;
        }
        List m1315l = certificateArr != null ? AbstractC0887c.m1315l(certificateArr) : Collections.emptyList();
        Certificate[] localCertificates = sSLSession.getLocalCertificates();
        return new C0876r(m1272a, m1257a, m1315l, localCertificates != null ? AbstractC0887c.m1315l(localCertificates) : Collections.emptyList());
    }

    /* renamed from: b */
    public static ArrayList m1277b(List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Certificate certificate = (Certificate) it.next();
            arrayList.add(certificate instanceof X509Certificate ? String.valueOf(((X509Certificate) certificate).getSubjectDN()) : certificate.getType());
        }
        return arrayList;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof C0876r)) {
            return false;
        }
        C0876r c0876r = (C0876r) obj;
        return this.f1892a.equals(c0876r.f1892a) && this.f1893b.equals(c0876r.f1893b) && this.f1894c.equals(c0876r.f1894c) && this.f1895d.equals(c0876r.f1895d);
    }

    public final int hashCode() {
        return this.f1895d.hashCode() + ((this.f1894c.hashCode() + ((this.f1893b.hashCode() + ((this.f1892a.hashCode() + 527) * 31)) * 31)) * 31);
    }

    public final String toString() {
        return "Handshake{tlsVersion=" + this.f1892a + " cipherSuite=" + this.f1893b + " peerCertificates=" + m1277b(this.f1894c) + " localCertificates=" + m1277b(this.f1895d) + '}';
    }
}
