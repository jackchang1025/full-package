package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: aw */
/* loaded from: classes.dex */
public final class C0112aw extends t60 {

    /* renamed from: c7 */
    public static volatile C0112aw f45648c7;

    /* renamed from: c8 */
    public static final ExecutorC0111av f45649c8 = new ExecutorC0111av(0);

    /* renamed from: c6 */
    public final C1187ro f45650c6 = new C1187ro();

    /* renamed from: f5 */
    public static C0112aw m210524f5() {
        if (f45648c7 != null) {
            return f45648c7;
        }
        synchronized (C0112aw.class) {
            try {
                if (f45648c7 == null) {
                    f45648c7 = new C0112aw();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return f45648c7;
    }
}
