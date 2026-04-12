package p000;

import java.util.ListIterator;
import java.util.NoSuchElementException;
import kotlin.collections.builders.ListBuilder;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class bb0 implements ListIterator, d80 {

    /* renamed from: a0 */
    public final ListBuilder f45778a0;

    /* renamed from: a1 */
    public int f45779a1;

    /* renamed from: a2 */
    public int f45780a2 = -1;

    public bb0(ListBuilder listBuilder, int i) {
        this.f45778a0 = listBuilder;
        this.f45779a1 = i;
    }

    @Override // java.util.ListIterator
    public final void add(Object obj) {
        int i = this.f45779a1;
        this.f45779a1 = i + 1;
        this.f45778a0.add(i, obj);
        this.f45780a2 = -1;
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final boolean hasNext() {
        return this.f45779a1 < this.f45778a0.f57581a2;
    }

    @Override // java.util.ListIterator
    public final boolean hasPrevious() {
        return this.f45779a1 > 0;
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final Object next() {
        int i = this.f45779a1;
        ListBuilder listBuilder = this.f45778a0;
        if (i >= listBuilder.f57581a2) {
            throw new NoSuchElementException();
        }
        this.f45779a1 = i + 1;
        this.f45780a2 = i;
        return listBuilder.f57579a0[listBuilder.f57580a1 + i];
    }

    @Override // java.util.ListIterator
    public final int nextIndex() {
        return this.f45779a1;
    }

    @Override // java.util.ListIterator
    public final Object previous() {
        int i = this.f45779a1;
        if (i <= 0) {
            throw new NoSuchElementException();
        }
        int i2 = i - 1;
        this.f45779a1 = i2;
        this.f45780a2 = i2;
        ListBuilder listBuilder = this.f45778a0;
        return listBuilder.f57579a0[listBuilder.f57580a1 + i2];
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        return this.f45779a1 - 1;
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final void remove() {
        int i = this.f45780a2;
        if (i == -1) {
            throw new IllegalStateException("Call next() or previous() before removing element from the iterator.");
        }
        this.f45778a0.mo210618a1(i);
        this.f45779a1 = this.f45780a2;
        this.f45780a2 = -1;
    }

    @Override // java.util.ListIterator
    public final void set(Object obj) {
        int i = this.f45780a2;
        if (i == -1) {
            throw new IllegalStateException("Call next() or previous() before replacing element from the iterator.");
        }
        this.f45778a0.set(i, obj);
    }
}
