package p000;

import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class a90 {

    /* renamed from: a0 */
    public final float f48a0;

    /* renamed from: a2 */
    public b90 f50a2;

    /* renamed from: a3 */
    public b90 f51a3;

    /* renamed from: a1 */
    public final ArrayList f49a1 = new ArrayList();

    /* renamed from: a4 */
    public int f52a4 = -1;

    /* renamed from: a5 */
    public int f53a5 = -1;

    /* renamed from: a6 */
    public float f54a6 = 0.0f;

    public a90(float f) {
        this.f48a0 = f;
    }

    /* renamed from: a0 */
    public final void m70a0(float f, float f2, float f3, boolean z) {
        if (f3 <= 0.0f) {
            return;
        }
        b90 b90Var = new b90(Float.MIN_VALUE, f, f2, f3);
        ArrayList arrayList = this.f49a1;
        if (z) {
            if (this.f50a2 == null) {
                this.f50a2 = b90Var;
                this.f52a4 = arrayList.size();
            }
            if (this.f53a5 != -1 && arrayList.size() - this.f53a5 > 1) {
                throw new IllegalArgumentException("Keylines marked as focal must be placed next to each other. There cannot be non-focal keylines between focal keylines.");
            }
            if (f3 != this.f50a2.f45754a3) {
                throw new IllegalArgumentException("Keylines that are marked as focal must all have the same masked item size.");
            }
            this.f51a3 = b90Var;
            this.f53a5 = arrayList.size();
        } else {
            if (this.f50a2 == null && f3 < this.f54a6) {
                throw new IllegalArgumentException("Keylines before the first focal keyline must be ordered by incrementing masked item size.");
            }
            if (this.f51a3 != null && f3 > this.f54a6) {
                throw new IllegalArgumentException("Keylines after the last focal keyline must be ordered by decreasing masked item size.");
            }
        }
        this.f54a6 = f3;
        arrayList.add(b90Var);
    }

    /* renamed from: a1 */
    public final c90 m71a1() {
        if (this.f50a2 == null) {
            throw new IllegalStateException("There must be a keyline marked as focal.");
        }
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (true) {
            ArrayList arrayList2 = this.f49a1;
            int size = arrayList2.size();
            float f = this.f48a0;
            if (i >= size) {
                return new c90(f, arrayList, this.f52a4, this.f53a5);
            }
            b90 b90Var = (b90) arrayList2.get(i);
            arrayList.add(new b90((i * f) + (this.f50a2.f45752a1 - (this.f52a4 * f)), b90Var.f45752a1, b90Var.f45753a2, b90Var.f45754a3));
            i++;
        }
    }
}
