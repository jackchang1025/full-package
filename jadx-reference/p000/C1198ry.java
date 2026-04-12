package p000;

import java.util.Iterator;
import kotlin.jvm.internal.Lambda;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ry */
/* loaded from: classes2.dex */
public final class C1198ry implements nz0 {

    /* renamed from: a0 */
    public final CharSequence f59834a0;

    /* renamed from: a1 */
    public final int f59835a1;

    /* renamed from: a2 */
    public final Lambda f59836a2;

    /* JADX WARN: Multi-variable type inference failed */
    public C1198ry(CharSequence charSequence, int i, l10 l10Var) {
        t60.m214695b6(charSequence, "input");
        this.f59834a0 = charSequence;
        this.f59835a1 = i;
        this.f59836a2 = (Lambda) l10Var;
    }

    @Override // p000.nz0
    public final Iterator iterator() {
        return new C1197rx(this);
    }
}
