package k0;

import b0.C0078b;
import f0.C0300u;
import f0.C0302w;
import f0.C0304y;
import f0.InterfaceC0294o;
import f0.InterfaceC0301v;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.LinkedList;
import java.util.Locale;

/* renamed from: k0.e */
/* loaded from: classes.dex */
public final class C0361e implements InterfaceC0301v {

    /* renamed from: d */
    public int f711d;

    /* renamed from: e */
    public boolean f712e;

    /* renamed from: f */
    public final /* synthetic */ InterfaceC0294o f713f;

    /* renamed from: g */
    public final /* synthetic */ C0304y f714g;

    /* renamed from: h */
    public final /* synthetic */ C0362f f715h;

    public C0361e(C0362f c0362f, InterfaceC0294o interfaceC0294o, C0304y c0304y) {
        this.f715h = c0362f;
        this.f713f = interfaceC0294o;
        this.f714g = c0304y;
    }

    /* renamed from: a */
    public final void m939a() {
        InterfaceC0294o interfaceC0294o = this.f713f;
        C0304y c0304y = new C0304y(interfaceC0294o);
        C0360d c0360d = new C0360d(this, 1);
        int i2 = this.f711d;
        int i3 = i2 & 8;
        LinkedList linkedList = c0304y.f560d;
        if (i3 != 0) {
            linkedList.add(new C0302w(c0360d));
            return;
        }
        if ((i2 & 16) != 0) {
            linkedList.add(new C0302w(c0360d));
            return;
        }
        if (this.f712e) {
            this.f714g.f560d.add(new C0300u(2, new C0360d(this, 2)));
        } else {
            C0362f c0362f = this.f715h;
            c0362f.f716k = false;
            c0362f.m814i(interfaceC0294o);
        }
    }

    @Override // f0.InterfaceC0301v
    /* renamed from: c */
    public final void mo391c(Object obj) {
        byte[] bArr = (byte[]) obj;
        short m940l = C0362f.m940l(bArr, ByteOrder.LITTLE_ENDIAN);
        C0362f c0362f = this.f715h;
        if (m940l != -29921) {
            c0362f.mo813c(new IOException(String.format(Locale.ENGLISH, "unknown format (magic number %x)", Short.valueOf(m940l))));
            this.f713f.mo783h(new C0078b(24));
            return;
        }
        byte b = bArr[3];
        this.f711d = b;
        boolean z2 = (b & 2) != 0;
        this.f712e = z2;
        if (z2) {
            c0362f.f717l.update(bArr, 0, bArr.length);
        }
        if ((this.f711d & 4) != 0) {
            this.f714g.f560d.add(new C0300u(2, new C0360d(this, 0)));
        } else {
            m939a();
        }
    }
}
