package c1;

import android.content.Context;
import android.net.nsd.NsdManager;
import java.util.Objects;

/* renamed from: c1.d */
/* loaded from: classes.dex */
public final class C0101d {

    /* renamed from: a */
    public final Context f172a;

    /* renamed from: b */
    public final String f173b;

    /* renamed from: c */
    public final InterfaceC0099b f174c;

    /* renamed from: d */
    public final C0098a f175d;

    /* renamed from: e */
    public final NsdManager f176e;

    /* renamed from: f */
    public boolean f177f;

    /* renamed from: g */
    public boolean f178g;

    /* renamed from: h */
    public String f179h;

    public C0101d(Context context, String str, InterfaceC0099b interfaceC0099b) {
        Objects.requireNonNull(context);
        this.f172a = context;
        this.f173b = String.format("_%s._tcp", str);
        this.f174c = interfaceC0099b;
        this.f176e = (NsdManager) context.getSystemService("servicediscovery");
        this.f175d = new C0098a(this);
    }

    /* renamed from: a */
    public final void m328a() {
        if (this.f178g) {
            return;
        }
        this.f178g = true;
        if (this.f177f) {
            return;
        }
        this.f176e.discoverServices(this.f173b, 1, this.f175d);
    }

    /* renamed from: b */
    public final void m329b() {
        if (this.f178g) {
            this.f178g = false;
            if (this.f177f) {
                this.f176e.stopServiceDiscovery(this.f175d);
            }
        }
    }
}
