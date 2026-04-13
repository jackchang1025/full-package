package f0;

/* renamed from: f0.a */
/* loaded from: classes.dex */
public final class RunnableC0280a implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ int f485a;

    /* renamed from: b */
    public final /* synthetic */ C0281b f486b;

    public /* synthetic */ RunnableC0280a(C0281b c0281b, int i2) {
        this.f485a = i2;
        this.f486b = c0281b;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.f485a;
        C0281b c0281b = this.f486b;
        switch (i2) {
            case 0:
                c0281b.m788m();
                break;
            default:
                c0281b.m791p();
                break;
        }
    }
}
