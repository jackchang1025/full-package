package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: rh */
/* loaded from: classes2.dex */
public final class C1180rh extends eu0 {

    /* renamed from: a3 */
    public static final C1180rh f59776a3;

    static {
        int i = l51.f57832a2;
        int i2 = l51.f57833a3;
        long j = l51.f57834a4;
        String str = l51.f57830a0;
        C1180rh c1180rh = new C1180rh();
        c1180rh.f56109a2 = new ExecutorC0919nn(i, i2, j, str);
        f59776a3 = c1180rh;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        throw new UnsupportedOperationException("Dispatchers.Default cannot be closed");
    }

    @Override // kotlinx.coroutines.AbstractC0781a1
    public final String toString() {
        return "Dispatchers.Default";
    }
}
