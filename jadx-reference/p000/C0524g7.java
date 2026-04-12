package p000;

import java.util.ListIterator;
import java.util.NoSuchElementException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: g7 */
/* loaded from: classes2.dex */
public final class C0524g7 extends C0523g6 implements ListIterator {

    /* renamed from: a3 */
    public final /* synthetic */ AbstractC0528g9 f56423a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0524g7(AbstractC0528g9 abstractC0528g9, int i) {
        super(abstractC0528g9);
        this.f56423a3 = abstractC0528g9;
        AbstractC0528g9.Companion.checkPositionIndex$kotlin_stdlib(i, abstractC0528g9.size());
        this.f56414a1 = i;
    }

    @Override // java.util.ListIterator
    public final void add(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.ListIterator
    public final boolean hasPrevious() {
        return this.f56414a1 > 0;
    }

    @Override // java.util.ListIterator
    public final int nextIndex() {
        return this.f56414a1;
    }

    @Override // java.util.ListIterator
    public final Object previous() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        int i = this.f56414a1 - 1;
        this.f56414a1 = i;
        return this.f56423a3.get(i);
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        return this.f56414a1 - 1;
    }

    @Override // java.util.ListIterator
    public final void set(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
