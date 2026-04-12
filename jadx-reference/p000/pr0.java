package p000;

import java.util.concurrent.atomic.AtomicReferenceArray;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class pr0 {
    private volatile AtomicReferenceArray<Object> array;

    public pr0(int i) {
        this.array = new AtomicReferenceArray<>(i);
    }

    /* renamed from: a0 */
    public final int m214331a0() {
        return this.array.length();
    }

    /* renamed from: a1 */
    public final Object m214332a1(int i) {
        AtomicReferenceArray<Object> atomicReferenceArray = this.array;
        if (i < atomicReferenceArray.length()) {
            return atomicReferenceArray.get(i);
        }
        return null;
    }

    /* renamed from: a2 */
    public final void m214333a2(int i, C0918nm c0918nm) {
        AtomicReferenceArray<Object> atomicReferenceArray = this.array;
        int length = atomicReferenceArray.length();
        if (i < length) {
            atomicReferenceArray.set(i, c0918nm);
            return;
        }
        int i2 = i + 1;
        int i3 = length * 2;
        if (i2 < i3) {
            i2 = i3;
        }
        AtomicReferenceArray<Object> atomicReferenceArray2 = new AtomicReferenceArray<>(i2);
        for (int i4 = 0; i4 < length; i4++) {
            atomicReferenceArray2.set(i4, atomicReferenceArray.get(i4));
        }
        atomicReferenceArray2.set(i, c0918nm);
        this.array = atomicReferenceArray2;
    }
}
