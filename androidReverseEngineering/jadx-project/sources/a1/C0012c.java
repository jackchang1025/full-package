package a1;

/* renamed from: a1.c */
/* loaded from: classes.dex */
public final class C0012c extends Thread {
    public C0012c() {
        super("Okio Watchdog");
        setDaemon(true);
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0015, code lost:
    
        r1.mo76n();
     */
    @Override // java.lang.Thread, java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        while (true) {
            synchronized (C0013d.class) {
                C0013d m70h = C0013d.m70h();
                if (m70h != null) {
                    if (m70h == C0013d.f16j) {
                        C0013d.f16j = null;
                        return;
                    }
                }
            }
        }
    }
}
