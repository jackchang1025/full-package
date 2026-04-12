package p000;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class yc0 implements Set {

    /* renamed from: a0 */
    public final /* synthetic */ int f61290a0;

    /* renamed from: a1 */
    public final /* synthetic */ AbstractC0395cy f61291a1;

    public /* synthetic */ yc0(AbstractC0395cy abstractC0395cy, int i) {
        this.f61290a0 = i;
        this.f61291a1 = abstractC0395cy;
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean add(Object obj) {
        switch (this.f61290a0) {
            case 0:
                throw new UnsupportedOperationException();
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean addAll(Collection collection) {
        switch (this.f61290a0) {
            case 0:
                AbstractC0395cy abstractC0395cy = this.f61291a1;
                int iMo210653a4 = abstractC0395cy.mo210653a4();
                Iterator it = collection.iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    abstractC0395cy.mo210656a7(entry.getKey(), entry.getValue());
                }
                return iMo210653a4 != abstractC0395cy.mo210653a4();
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override // java.util.Set, java.util.Collection
    public final void clear() {
        switch (this.f61290a0) {
            case 0:
                this.f61291a1.mo210650a1();
                break;
            default:
                this.f61291a1.mo210650a1();
                break;
        }
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean contains(Object obj) {
        switch (this.f61290a0) {
            case 0:
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry entry = (Map.Entry) obj;
                Object key = entry.getKey();
                AbstractC0395cy abstractC0395cy = this.f61291a1;
                int iMo210654a5 = abstractC0395cy.mo210654a5(key);
                if (iMo210654a5 < 0) {
                    return false;
                }
                Object objMo210651a2 = abstractC0395cy.mo210651a2(iMo210654a5, 1);
                Object value = entry.getValue();
                return objMo210651a2 == value || (objMo210651a2 != null && objMo210651a2.equals(value));
            default:
                return this.f61291a1.mo210654a5(obj) >= 0;
        }
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean containsAll(Collection collection) {
        switch (this.f61290a0) {
            case 0:
                Iterator it = collection.iterator();
                while (it.hasNext()) {
                    if (!contains(it.next())) {
                        break;
                    }
                }
                break;
            default:
                Map mapMo210652a3 = this.f61291a1.mo210652a3();
                Iterator it2 = collection.iterator();
                while (it2.hasNext()) {
                    if (!mapMo210652a3.containsKey(it2.next())) {
                        break;
                    }
                }
                break;
        }
        return true;
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean equals(Object obj) {
        switch (this.f61290a0) {
        }
        return AbstractC0395cy.m212537b0(obj, this);
    }

    @Override // java.util.Set, java.util.Collection
    public final int hashCode() {
        switch (this.f61290a0) {
            case 0:
                AbstractC0395cy abstractC0395cy = this.f61291a1;
                int iHashCode = 0;
                for (int iMo210653a4 = abstractC0395cy.mo210653a4() - 1; iMo210653a4 >= 0; iMo210653a4--) {
                    Object objMo210651a2 = abstractC0395cy.mo210651a2(iMo210653a4, 0);
                    Object objMo210651a22 = abstractC0395cy.mo210651a2(iMo210653a4, 1);
                    iHashCode += (objMo210651a2 == null ? 0 : objMo210651a2.hashCode()) ^ (objMo210651a22 == null ? 0 : objMo210651a22.hashCode());
                }
                return iHashCode;
            default:
                AbstractC0395cy abstractC0395cy2 = this.f61291a1;
                int iHashCode2 = 0;
                for (int iMo210653a42 = abstractC0395cy2.mo210653a4() - 1; iMo210653a42 >= 0; iMo210653a42--) {
                    Object objMo210651a23 = abstractC0395cy2.mo210651a2(iMo210653a42, 0);
                    iHashCode2 += objMo210651a23 == null ? 0 : objMo210651a23.hashCode();
                }
                return iHashCode2;
        }
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean isEmpty() {
        switch (this.f61290a0) {
            case 0:
                if (this.f61291a1.mo210653a4() == 0) {
                }
                break;
            default:
                if (this.f61291a1.mo210653a4() == 0) {
                }
                break;
        }
        return false;
    }

    @Override // java.util.Set, java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        switch (this.f61290a0) {
            case 0:
                return new zc0(this.f61291a1);
            default:
                return new xc0(this.f61291a1, 0);
        }
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean remove(Object obj) {
        switch (this.f61290a0) {
            case 0:
                throw new UnsupportedOperationException();
            default:
                AbstractC0395cy abstractC0395cy = this.f61291a1;
                int iMo210654a5 = abstractC0395cy.mo210654a5(obj);
                if (iMo210654a5 < 0) {
                    return false;
                }
                abstractC0395cy.mo210657a8(iMo210654a5);
                return true;
        }
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean removeAll(Collection collection) {
        switch (this.f61290a0) {
            case 0:
                throw new UnsupportedOperationException();
            default:
                Map mapMo210652a3 = this.f61291a1.mo210652a3();
                int size = mapMo210652a3.size();
                Iterator it = collection.iterator();
                while (it.hasNext()) {
                    mapMo210652a3.remove(it.next());
                }
                return size != mapMo210652a3.size();
        }
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean retainAll(Collection collection) {
        switch (this.f61290a0) {
            case 0:
                throw new UnsupportedOperationException();
            default:
                Map mapMo210652a3 = this.f61291a1.mo210652a3();
                int size = mapMo210652a3.size();
                Iterator it = mapMo210652a3.keySet().iterator();
                while (it.hasNext()) {
                    if (!collection.contains(it.next())) {
                        it.remove();
                    }
                }
                return size != mapMo210652a3.size();
        }
    }

    @Override // java.util.Set, java.util.Collection
    public final int size() {
        switch (this.f61290a0) {
        }
        return this.f61291a1.mo210653a4();
    }

    @Override // java.util.Set, java.util.Collection
    public final Object[] toArray(Object[] objArr) {
        switch (this.f61290a0) {
            case 0:
                throw new UnsupportedOperationException();
            default:
                return this.f61291a1.m212545b7(objArr, 0);
        }
    }

    @Override // java.util.Set, java.util.Collection
    public final Object[] toArray() {
        switch (this.f61290a0) {
            case 0:
                throw new UnsupportedOperationException();
            default:
                AbstractC0395cy abstractC0395cy = this.f61291a1;
                int iMo210653a4 = abstractC0395cy.mo210653a4();
                Object[] objArr = new Object[iMo210653a4];
                for (int i = 0; i < iMo210653a4; i++) {
                    objArr[i] = abstractC0395cy.mo210651a2(i, 0);
                }
                return objArr;
        }
    }
}
