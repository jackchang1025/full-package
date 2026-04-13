package s0;

import java.net.UnknownServiceException;
import java.util.Arrays;
import java.util.List;
import javax.net.ssl.SSLSocket;
import p0.C0866h;
import p0.C0867i;
import p0.C0868j;
import p0.C0869k;
import p0.C0875q;
import q0.AbstractC0887c;

/* renamed from: s0.b */
/* loaded from: classes.dex */
public final class C0899b {

    /* renamed from: a */
    public final List f1995a;

    /* renamed from: b */
    public int f1996b = 0;

    /* renamed from: c */
    public boolean f1997c;

    /* renamed from: d */
    public boolean f1998d;

    public C0899b(List list) {
        this.f1995a = list;
    }

    /* renamed from: a */
    public final C0869k m1333a(SSLSocket sSLSocket) {
        C0869k c0869k;
        boolean z2;
        int i2 = this.f1996b;
        List list = this.f1995a;
        int size = list.size();
        while (true) {
            if (i2 >= size) {
                c0869k = null;
                break;
            }
            c0869k = (C0869k) list.get(i2);
            if (c0869k.m1266a(sSLSocket)) {
                this.f1996b = i2 + 1;
                break;
            }
            i2++;
        }
        if (c0869k == null) {
            throw new UnknownServiceException("Unable to find acceptable protocols. isFallback=" + this.f1998d + ", modes=" + list + ", supported protocols=" + Arrays.toString(sSLSocket.getEnabledProtocols()));
        }
        int i3 = this.f1996b;
        while (true) {
            if (i3 >= list.size()) {
                z2 = false;
                break;
            }
            if (((C0869k) list.get(i3)).m1266a(sSLSocket)) {
                z2 = true;
                break;
            }
            i3++;
        }
        this.f1997c = z2;
        C0875q c0875q = C0875q.f1891c;
        boolean z3 = this.f1998d;
        c0875q.getClass();
        String[] strArr = c0869k.f1846c;
        String[] m1316m = strArr != null ? AbstractC0887c.m1316m(C0867i.f1793b, sSLSocket.getEnabledCipherSuites(), strArr) : sSLSocket.getEnabledCipherSuites();
        String[] strArr2 = c0869k.f1847d;
        String[] m1316m2 = strArr2 != null ? AbstractC0887c.m1316m(AbstractC0887c.f1942i, sSLSocket.getEnabledProtocols(), strArr2) : sSLSocket.getEnabledProtocols();
        String[] supportedCipherSuites = sSLSocket.getSupportedCipherSuites();
        C0866h c0866h = C0867i.f1793b;
        byte[] bArr = AbstractC0887c.f1934a;
        int length = supportedCipherSuites.length;
        int i4 = 0;
        while (true) {
            if (i4 >= length) {
                i4 = -1;
                break;
            }
            if (c0866h.compare(supportedCipherSuites[i4], "TLS_FALLBACK_SCSV") == 0) {
                break;
            }
            i4++;
        }
        if (z3 && i4 != -1) {
            String str = supportedCipherSuites[i4];
            int length2 = m1316m.length + 1;
            String[] strArr3 = new String[length2];
            System.arraycopy(m1316m, 0, strArr3, 0, m1316m.length);
            strArr3[length2 - 1] = str;
            m1316m = strArr3;
        }
        C0868j c0868j = new C0868j(c0869k);
        c0868j.m1261a(m1316m);
        c0868j.m1263c(m1316m2);
        C0869k c0869k2 = new C0869k(c0868j);
        String[] strArr4 = c0869k2.f1847d;
        if (strArr4 != null) {
            sSLSocket.setEnabledProtocols(strArr4);
        }
        String[] strArr5 = c0869k2.f1846c;
        if (strArr5 != null) {
            sSLSocket.setEnabledCipherSuites(strArr5);
        }
        return c0869k;
    }
}
