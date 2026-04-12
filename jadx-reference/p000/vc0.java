package p000;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import kotlin.collections.builders.MapBuilder;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class vc0 extends AbstractC0565h1 {

    /* renamed from: a0 */
    public final /* synthetic */ int f60618a0;

    /* renamed from: a1 */
    public final MapBuilder f60619a1;

    public /* synthetic */ vc0(MapBuilder mapBuilder, int i) {
        this.f60618a0 = i;
        this.f60619a1 = mapBuilder;
    }

    @Override // p000.AbstractC0565h1
    /* renamed from: a0 */
    public final int mo212990a0() {
        switch (this.f60618a0) {
        }
        return this.f60619a1.f57594a7;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean add(Object obj) {
        switch (this.f60618a0) {
            case 0:
                t60.m214695b6((Map.Entry) obj, "element");
                throw new UnsupportedOperationException();
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean addAll(Collection collection) {
        switch (this.f60618a0) {
            case 0:
                t60.m214695b6(collection, "elements");
                throw new UnsupportedOperationException();
            default:
                t60.m214695b6(collection, "elements");
                throw new UnsupportedOperationException();
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        switch (this.f60618a0) {
            case 0:
                this.f60619a1.clear();
                break;
            default:
                this.f60619a1.clear();
                break;
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        switch (this.f60618a0) {
            case 0:
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                return this.f60619a1.m213630a4((Map.Entry) obj);
            default:
                return this.f60619a1.containsKey(obj);
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean containsAll(Collection collection) {
        switch (this.f60618a0) {
            case 0:
                t60.m214695b6(collection, "elements");
                return this.f60619a1.m213629a3(collection);
            default:
                return super.containsAll(collection);
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean isEmpty() {
        switch (this.f60618a0) {
        }
        return this.f60619a1.isEmpty();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        switch (this.f60618a0) {
            case 0:
                MapBuilder mapBuilder = this.f60619a1;
                mapBuilder.getClass();
                return new tc0(mapBuilder, 0);
            default:
                MapBuilder mapBuilder2 = this.f60619a1;
                mapBuilder2.getClass();
                return new tc0(mapBuilder2, 1);
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        switch (this.f60618a0) {
            case 0:
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry entry = (Map.Entry) obj;
                MapBuilder mapBuilder = this.f60619a1;
                mapBuilder.getClass();
                mapBuilder.m213628a2();
                int iM213632a6 = mapBuilder.m213632a6(entry.getKey());
                if (iM213632a6 < 0) {
                    return false;
                }
                Object[] objArr = mapBuilder.f57588a1;
                t60.m214692b3(objArr);
                if (!t60.m214686a2(objArr[iM213632a6], entry.getValue())) {
                    return false;
                }
                mapBuilder.m213636b0(iM213632a6);
                return true;
            default:
                MapBuilder mapBuilder2 = this.f60619a1;
                mapBuilder2.m213628a2();
                int iM213632a62 = mapBuilder2.m213632a6(obj);
                if (iM213632a62 < 0) {
                    iM213632a62 = -1;
                } else {
                    mapBuilder2.m213636b0(iM213632a62);
                }
                return iM213632a62 >= 0;
        }
    }

    @Override // java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean removeAll(Collection collection) {
        switch (this.f60618a0) {
            case 0:
                t60.m214695b6(collection, "elements");
                this.f60619a1.m213628a2();
                break;
            default:
                t60.m214695b6(collection, "elements");
                this.f60619a1.m213628a2();
                break;
        }
        return super.removeAll(collection);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean retainAll(Collection collection) {
        switch (this.f60618a0) {
            case 0:
                t60.m214695b6(collection, "elements");
                this.f60619a1.m213628a2();
                break;
            default:
                t60.m214695b6(collection, "elements");
                this.f60619a1.m213628a2();
                break;
        }
        return super.retainAll(collection);
    }
}
