package p000;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class c90 {

    /* renamed from: a0 */
    public final float f46078a0;

    /* renamed from: a1 */
    public final List f46079a1;

    /* renamed from: a2 */
    public final int f46080a2;

    /* renamed from: a3 */
    public final int f46081a3;

    public c90(float f, ArrayList arrayList, int i, int i2) {
        this.f46078a0 = f;
        this.f46079a1 = Collections.unmodifiableList(arrayList);
        this.f46080a2 = i;
        this.f46081a3 = i2;
    }

    /* renamed from: a0 */
    public final b90 m210772a0() {
        return (b90) this.f46079a1.get(this.f46080a2);
    }

    /* renamed from: a1 */
    public final b90 m210773a1() {
        return (b90) this.f46079a1.get(0);
    }

    /* renamed from: a2 */
    public final b90 m210774a2() {
        return (b90) this.f46079a1.get(this.f46081a3);
    }

    /* renamed from: a3 */
    public final b90 m210775a3() {
        return (b90) this.f46079a1.get(r0.size() - 1);
    }
}
