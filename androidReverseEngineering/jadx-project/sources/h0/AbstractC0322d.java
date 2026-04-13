package h0;

/* renamed from: h0.d */
/* loaded from: classes.dex */
public abstract class AbstractC0322d implements InterfaceC0319a {

    /* renamed from: a */
    public boolean f629a;

    /* renamed from: b */
    public boolean f630b;

    /* renamed from: c */
    public InterfaceC0319a f631c;

    static {
        new C0321c(0);
        new C0321c(1);
    }

    /* renamed from: a */
    public void mo864a() {
    }

    /* renamed from: b */
    public final boolean m865b() {
        synchronized (this) {
            if (this.f630b) {
                return false;
            }
            if (this.f629a) {
                return false;
            }
            this.f629a = true;
            this.f631c = null;
            return true;
        }
    }

    @Override // h0.InterfaceC0319a
    public boolean cancel() {
        synchronized (this) {
            if (this.f629a) {
                return false;
            }
            if (this.f630b) {
                return true;
            }
            this.f630b = true;
            InterfaceC0319a interfaceC0319a = this.f631c;
            this.f631c = null;
            if (interfaceC0319a != null) {
                interfaceC0319a.cancel();
            }
            mo864a();
            return true;
        }
    }

    @Override // h0.InterfaceC0319a
    public final boolean isCancelled() {
        boolean z2;
        InterfaceC0319a interfaceC0319a;
        synchronized (this) {
            z2 = this.f630b || ((interfaceC0319a = this.f631c) != null && interfaceC0319a.isCancelled());
        }
        return z2;
    }

    public final boolean isDone() {
        return this.f629a;
    }

    public boolean cancel(boolean z2) {
        return cancel();
    }
}
