package p000;

import java.util.RandomAccess;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: g8 */
/* loaded from: classes2.dex */
public final class C0527g8 extends AbstractC0528g9 implements RandomAccess {

    /* renamed from: a0 */
    public final AbstractC0528g9 f56424a0;

    /* renamed from: a1 */
    public final int f56425a1;

    /* renamed from: a2 */
    public final int f56426a2;

    public C0527g8(AbstractC0528g9 abstractC0528g9, int i, int i2) {
        this.f56424a0 = abstractC0528g9;
        this.f56425a1 = i;
        AbstractC0528g9.Companion.checkRangeIndexes$kotlin_stdlib(i, i2, abstractC0528g9.size());
        this.f56426a2 = i2 - i;
    }

    @Override // java.util.List
    public final Object get(int i) {
        AbstractC0528g9.Companion.checkElementIndex$kotlin_stdlib(i, this.f56426a2);
        return this.f56424a0.get(this.f56425a1 + i);
    }

    @Override // kotlin.collections.AbstractCollection
    public final int getSize() {
        return this.f56426a2;
    }
}
