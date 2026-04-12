package p000;

import java.util.Collection;
import java.util.Iterator;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class ad0 implements Collection {

    /* renamed from: a0 */
    public final /* synthetic */ AbstractC0395cy f43623a0;

    public ad0(AbstractC0395cy abstractC0395cy) {
        this.f43623a0 = abstractC0395cy;
    }

    @Override // java.util.Collection
    public final boolean add(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
    public final boolean addAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
    public final void clear() {
        this.f43623a0.mo210650a1();
    }

    @Override // java.util.Collection
    public final boolean contains(Object obj) {
        return this.f43623a0.mo210655a6(obj) >= 0;
    }

    @Override // java.util.Collection
    public final boolean containsAll(Collection collection) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Collection
    public final boolean isEmpty() {
        return this.f43623a0.mo210653a4() == 0;
    }

    @Override // java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        return new xc0(this.f43623a0, 1);
    }

    @Override // java.util.Collection
    public final boolean remove(Object obj) {
        AbstractC0395cy abstractC0395cy = this.f43623a0;
        int iMo210655a6 = abstractC0395cy.mo210655a6(obj);
        if (iMo210655a6 < 0) {
            return false;
        }
        abstractC0395cy.mo210657a8(iMo210655a6);
        return true;
    }

    @Override // java.util.Collection
    public final boolean removeAll(Collection collection) {
        AbstractC0395cy abstractC0395cy = this.f43623a0;
        int iMo210653a4 = abstractC0395cy.mo210653a4();
        int i = 0;
        boolean z = false;
        while (i < iMo210653a4) {
            if (collection.contains(abstractC0395cy.mo210651a2(i, 1))) {
                abstractC0395cy.mo210657a8(i);
                i--;
                iMo210653a4--;
                z = true;
            }
            i++;
        }
        return z;
    }

    @Override // java.util.Collection
    public final boolean retainAll(Collection collection) {
        AbstractC0395cy abstractC0395cy = this.f43623a0;
        int iMo210653a4 = abstractC0395cy.mo210653a4();
        int i = 0;
        boolean z = false;
        while (i < iMo210653a4) {
            if (!collection.contains(abstractC0395cy.mo210651a2(i, 1))) {
                abstractC0395cy.mo210657a8(i);
                i--;
                iMo210653a4--;
                z = true;
            }
            i++;
        }
        return z;
    }

    @Override // java.util.Collection
    public final int size() {
        return this.f43623a0.mo210653a4();
    }

    @Override // java.util.Collection
    public final Object[] toArray(Object[] objArr) {
        return this.f43623a0.m212545b7(objArr, 1);
    }

    @Override // java.util.Collection
    public final Object[] toArray() {
        AbstractC0395cy abstractC0395cy = this.f43623a0;
        int iMo210653a4 = abstractC0395cy.mo210653a4();
        Object[] objArr = new Object[iMo210653a4];
        for (int i = 0; i < iMo210653a4; i++) {
            objArr[i] = abstractC0395cy.mo210651a2(i, 1);
        }
        return objArr;
    }
}
