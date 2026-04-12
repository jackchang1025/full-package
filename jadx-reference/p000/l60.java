package p000;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class l60 implements Iterator, d80 {

    /* renamed from: a0 */
    public final int f57838a0;

    /* renamed from: a1 */
    public final int f57839a1;

    /* renamed from: a2 */
    public boolean f57840a2;

    /* renamed from: a3 */
    public int f57841a3;

    public l60(int i, int i2, int i3) {
        this.f57838a0 = i3;
        this.f57839a1 = i2;
        boolean z = false;
        if (i3 <= 0 ? i >= i2 : i <= i2) {
            z = true;
        }
        this.f57840a2 = z;
        this.f57841a3 = z ? i : i2;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.f57840a2;
    }

    @Override // java.util.Iterator
    public final /* bridge */ /* synthetic */ Object next() {
        return Integer.valueOf(nextInt());
    }

    public final int nextInt() {
        int i = this.f57841a3;
        if (i != this.f57839a1) {
            this.f57841a3 = this.f57838a0 + i;
            return i;
        }
        if (!this.f57840a2) {
            throw new NoSuchElementException();
        }
        this.f57840a2 = false;
        return i;
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
