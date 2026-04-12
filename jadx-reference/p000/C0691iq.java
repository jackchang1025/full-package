package p000;

import java.lang.reflect.Method;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: iq */
/* loaded from: classes.dex */
public final class C0691iq {

    /* renamed from: a0 */
    public final int f57218a0;

    /* renamed from: a1 */
    public final Method f57219a1;

    public C0691iq(int i, Method method) throws SecurityException {
        this.f57218a0 = i;
        this.f57219a1 = method;
        method.setAccessible(true);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C0691iq)) {
            return false;
        }
        C0691iq c0691iq = (C0691iq) obj;
        return this.f57218a0 == c0691iq.f57218a0 && this.f57219a1.getName().equals(c0691iq.f57219a1.getName());
    }

    public final int hashCode() {
        return this.f57219a1.getName().hashCode() + (this.f57218a0 * 31);
    }
}
