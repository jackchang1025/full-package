package n0;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* renamed from: n0.b */
/* loaded from: classes.dex */
public final class C0406b implements Iterator {

    /* renamed from: a */
    public int f811a;

    /* renamed from: b */
    public int f812b;

    /* renamed from: c */
    public int f813c = -1;

    /* renamed from: d */
    public final /* synthetic */ C0407c f814d;

    public C0406b(C0407c c0407c) {
        this.f814d = c0407c;
        this.f811a = c0407c.f816b;
        this.f812b = c0407c.f817c;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.f811a != this.f812b;
    }

    @Override // java.util.Iterator
    public final Object next() {
        int i2 = this.f811a;
        int i3 = this.f812b;
        if (i2 == i3) {
            throw new NoSuchElementException();
        }
        C0407c c0407c = this.f814d;
        Object obj = c0407c.f815a[i2];
        if (c0407c.f817c != i3 || obj == null) {
            throw new ConcurrentModificationException();
        }
        this.f813c = i2;
        this.f811a = (i2 + 1) & (r3.length - 1);
        return obj;
    }

    @Override // java.util.Iterator
    public final void remove() {
        int i2 = this.f813c;
        if (i2 < 0) {
            throw new IllegalStateException();
        }
        C0407c c0407c = this.f814d;
        if (c0407c.m968b(i2)) {
            this.f811a = (this.f811a - 1) & (c0407c.f815a.length - 1);
            this.f812b = c0407c.f817c;
        }
        this.f813c = -1;
    }
}
