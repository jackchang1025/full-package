package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class m61 {

    /* renamed from: a0 */
    public static final ThreadLocal f58265a0 = new ThreadLocal();

    /* renamed from: a0 */
    public static AbstractC1424xo m213943a0() {
        ThreadLocal threadLocal = f58265a0;
        AbstractC1424xo abstractC1424xo = (AbstractC1424xo) threadLocal.get();
        if (abstractC1424xo != null) {
            return abstractC1424xo;
        }
        C0456eh c0456eh = new C0456eh(Thread.currentThread());
        threadLocal.set(c0456eh);
        return c0456eh;
    }
}
