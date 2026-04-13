package p0;

import a1.AbstractC0021l;
import a1.AbstractC0026q;
import a1.C0011b;
import a1.C0031v;
import a1.InterfaceC0015f;
import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Logger;

/* loaded from: classes.dex */
public final class h0 extends AbstractC0026q {

    /* renamed from: o */
    public final /* synthetic */ C0882x f1791o;

    /* renamed from: p */
    public final /* synthetic */ File f1792p;

    public h0(C0882x c0882x, File file) {
        this.f1791o = c0882x;
        this.f1792p = file;
    }

    @Override // a1.AbstractC0026q
    /* renamed from: V */
    public final void mo194V(InterfaceC0015f interfaceC0015f) {
        Logger logger = AbstractC0021l.f38a;
        File file = this.f1792p;
        if (file == null) {
            throw new IllegalArgumentException("file == null");
        }
        C0011b c0011b = new C0011b(new C0031v(), new FileInputStream(file));
        try {
            interfaceC0015f.mo95d(c0011b);
            c0011b.close();
        } catch (Throwable th) {
            try {
                c0011b.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // a1.AbstractC0026q
    /* renamed from: i */
    public final long mo196i() {
        return this.f1792p.length();
    }

    @Override // a1.AbstractC0026q
    /* renamed from: j */
    public final C0882x mo197j() {
        return this.f1791o;
    }
}
