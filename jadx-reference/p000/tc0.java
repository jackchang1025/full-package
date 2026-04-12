package p000;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.collections.builders.MapBuilder;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class tc0 implements Iterator, d80 {

    /* renamed from: a0 */
    public final MapBuilder f60196a0;

    /* renamed from: a1 */
    public int f60197a1;

    /* renamed from: a2 */
    public int f60198a2;

    /* renamed from: a3 */
    public final /* synthetic */ int f60199a3;

    public tc0(MapBuilder mapBuilder, int i) {
        this.f60199a3 = i;
        t60.m214695b6(mapBuilder, "map");
        this.f60196a0 = mapBuilder;
        this.f60198a2 = -1;
        m214735a0();
    }

    /* renamed from: a0 */
    public final void m214735a0() {
        while (true) {
            int i = this.f60197a1;
            MapBuilder mapBuilder = this.f60196a0;
            if (i >= mapBuilder.f57592a5 || mapBuilder.f57589a2[i] >= 0) {
                return;
            } else {
                this.f60197a1 = i + 1;
            }
        }
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.f60197a1 < this.f60196a0.f57592a5;
    }

    @Override // java.util.Iterator
    public final Object next() {
        switch (this.f60199a3) {
            case 0:
                int i = this.f60197a1;
                MapBuilder mapBuilder = this.f60196a0;
                if (i >= mapBuilder.f57592a5) {
                    throw new NoSuchElementException();
                }
                this.f60197a1 = i + 1;
                this.f60198a2 = i;
                uc0 uc0Var = new uc0(mapBuilder, i);
                m214735a0();
                return uc0Var;
            case 1:
                int i2 = this.f60197a1;
                MapBuilder mapBuilder2 = this.f60196a0;
                if (i2 >= mapBuilder2.f57592a5) {
                    throw new NoSuchElementException();
                }
                this.f60197a1 = i2 + 1;
                this.f60198a2 = i2;
                Object obj = mapBuilder2.f57587a0[i2];
                m214735a0();
                return obj;
            default:
                int i3 = this.f60197a1;
                MapBuilder mapBuilder3 = this.f60196a0;
                if (i3 >= mapBuilder3.f57592a5) {
                    throw new NoSuchElementException();
                }
                this.f60197a1 = i3 + 1;
                this.f60198a2 = i3;
                Object[] objArr = mapBuilder3.f57588a1;
                t60.m214692b3(objArr);
                Object obj2 = objArr[this.f60198a2];
                m214735a0();
                return obj2;
        }
    }

    @Override // java.util.Iterator
    public final void remove() {
        if (this.f60198a2 == -1) {
            throw new IllegalStateException("Call next() before removing element from the iterator.");
        }
        MapBuilder mapBuilder = this.f60196a0;
        mapBuilder.m213628a2();
        mapBuilder.m213636b0(this.f60198a2);
        this.f60198a2 = -1;
    }
}
