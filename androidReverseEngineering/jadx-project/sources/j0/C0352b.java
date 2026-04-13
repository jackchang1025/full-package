package j0;

import android.text.TextUtils;
import com.guard.wallet.http.C0203h;
import com.guard.wallet.thread.C0241j;
import f0.AbstractC0296q;
import f0.C0292m;
import f0.C0299t;
import f0.InterfaceC0294o;
import i0.C0331b;
import i0.C0334e;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import l0.C0377g;
import com.guard.wallet.entity.BuildConfig;

/* renamed from: j0.b */
/* loaded from: classes.dex */
public final class C0352b extends AbstractC0296q implements InterfaceC0351a {

    /* renamed from: i */
    public byte[] f690i;

    /* renamed from: j */
    public int f691j = 2;

    /* renamed from: k */
    public C0203h f692k;

    /* renamed from: l */
    public C0292m f693l;

    /* renamed from: m */
    public C0353c f694m;

    /* renamed from: n */
    public ArrayList f695n;

    public C0352b(String str) {
        String m875a = C0334e.m874c(str, ";", true, null).m875a("boundary");
        if (m875a == null) {
            mo813c(new Exception("No boundary found for multipart/form-data"));
        } else {
            this.f690i = "\r\n--".concat(m875a).getBytes();
        }
    }

    @Override // f0.AbstractC0296q, g0.InterfaceC0310b
    /* renamed from: b */
    public final void mo294b(InterfaceC0294o interfaceC0294o, C0292m c0292m) {
        C0331b c0331b;
        C0331b c0331b2;
        if (this.f691j > 0) {
            ByteBuffer m801g = C0292m.m801g(this.f690i.length);
            m801g.put(this.f690i, 0, this.f691j);
            m801g.flip();
            c0292m.m804b(m801g);
            this.f691j = 0;
        }
        int i2 = c0292m.f541c;
        byte[] bArr = new byte[i2];
        c0292m.m807e(bArr);
        int i3 = 0;
        int i4 = 0;
        while (i3 < i2) {
            int i5 = this.f691j;
            int i6 = -1;
            if (i5 >= 0) {
                byte b = bArr[i3];
                byte[] bArr2 = this.f690i;
                if (b == bArr2[i5]) {
                    int i7 = i5 + 1;
                    this.f691j = i7;
                    if (i7 != bArr2.length) {
                    }
                    this.f691j = i6;
                } else if (i5 > 0) {
                    i3 -= i5;
                    this.f691j = 0;
                }
            } else {
                if (i5 != -1) {
                    i6 = -3;
                    if (i5 == -2) {
                        if (bArr[i3] != 45) {
                            c0331b2 = new C0331b("Invalid multipart/form-data. Expected -");
                        }
                        this.f691j = i6;
                    } else if (i5 != -3) {
                        if (i5 != -4) {
                            c0331b = new C0331b("Invalid multipart/form-data. Unknown state?");
                        } else if (bArr[i3] == 10) {
                            i4 = i3 + 1;
                            this.f691j = 0;
                        } else {
                            c0331b = new C0331b("Invalid multipart/form-data. Expected \n");
                        }
                        mo813c(c0331b);
                    } else if (bArr[i3] == 13) {
                        this.f691j = -4;
                        int i8 = i3 - i4;
                        ByteBuffer put = C0292m.m801g((i8 - this.f690i.length) - 2).put(bArr, i4, (i8 - this.f690i.length) - 2);
                        put.flip();
                        C0292m c0292m2 = new C0292m();
                        c0292m2.m803a(put);
                        super.mo294b(this, c0292m2);
                        m887l();
                    } else {
                        c0331b2 = new C0331b("Invalid multipart/form-data. Expected \r");
                    }
                    mo813c(c0331b2);
                    return;
                }
                byte b2 = bArr[i3];
                if (b2 == 13) {
                    this.f691j = -4;
                    int length = (i3 - i4) - this.f690i.length;
                    if (i4 != 0 || length != 0) {
                        ByteBuffer put2 = C0292m.m801g(length).put(bArr, i4, length);
                        put2.flip();
                        C0292m c0292m3 = new C0292m();
                        c0292m3.m803a(put2);
                        super.mo294b(this, c0292m3);
                    }
                    C0203h c0203h = new C0203h(4);
                    C0299t c0299t = new C0299t(0);
                    c0299t.f555g = new C0241j(this, c0203h, 3);
                    this.f545f = c0299t;
                } else {
                    if (b2 != 45) {
                        c0331b2 = new C0331b("Invalid multipart/form-data. Expected \r or -");
                        mo813c(c0331b2);
                        return;
                    }
                    this.f691j = -2;
                }
            }
            i3++;
        }
        if (i4 < i2) {
            int max = (i2 - i4) - Math.max(this.f691j, 0);
            ByteBuffer put3 = C0292m.m801g(max).put(bArr, i4, max);
            put3.flip();
            C0292m c0292m4 = new C0292m();
            c0292m4.m803a(put3);
            super.mo294b(this, c0292m4);
        }
    }

    @Override // j0.InterfaceC0351a
    /* renamed from: d */
    public final void mo589d(AbstractC0296q abstractC0296q, C0377g c0377g) {
        m814i(abstractC0296q);
        this.f544e = c0377g;
    }

    @Override // j0.InterfaceC0351a
    /* renamed from: f */
    public final boolean mo590f() {
        return false;
    }

    @Override // j0.InterfaceC0351a
    public final Object get() {
        return new C0334e((C0334e) this.f692k.f245e);
    }

    /* renamed from: l */
    public final void m887l() {
        if (this.f693l == null) {
            return;
        }
        if (this.f692k == null) {
            this.f692k = new C0203h(4);
        }
        String m809h = this.f693l.m809h(null);
        String m875a = TextUtils.isEmpty(this.f694m.f697b.m875a("name")) ? "unnamed" : this.f694m.f697b.m875a("name");
        C0354d c0354d = new C0354d(m875a, m809h);
        c0354d.f696a = this.f694m.f696a;
        if (this.f695n == null) {
            this.f695n = new ArrayList();
        }
        this.f695n.add(c0354d);
        this.f692k.m392f(m875a, m809h);
        this.f694m = null;
        this.f693l = null;
    }

    @Override // j0.InterfaceC0351a
    public final int length() {
        byte[] bArr = this.f690i;
        if ((bArr == null ? null : new String(bArr, 4, bArr.length - 4)) == null) {
            this.f690i = ("\r\n--" + ("----------------------------" + UUID.randomUUID().toString().replace("-", BuildConfig.FLAVOR))).getBytes();
        }
        Iterator it = this.f695n.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            C0353c c0353c = (C0353c) it.next();
            C0203h c0203h = c0353c.f696a;
            byte[] bArr2 = this.f690i;
            String m398l = c0203h.m398l(new String(bArr2, 2, bArr2.length - 2));
            long j2 = c0353c.f698c;
            if (j2 == -1) {
                return -1;
            }
            i2 = (int) (j2 + m398l.getBytes().length + 2 + i2);
        }
        byte[] bArr3 = this.f690i;
        return i2 + new String(bArr3, 2, bArr3.length - 2).concat("--\r\n").getBytes().length;
    }

    public final String toString() {
        Iterator it = (this.f695n == null ? null : new ArrayList(this.f695n)).iterator();
        return it.hasNext() ? ((C0353c) it.next()).toString() : "multipart content is empty";
    }
}
