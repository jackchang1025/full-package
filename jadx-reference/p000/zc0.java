package p000;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class zc0 implements Iterator, Map.Entry {

    /* renamed from: a0 */
    public int f61497a0;

    /* renamed from: a3 */
    public final /* synthetic */ AbstractC0395cy f61500a3;

    /* renamed from: a2 */
    public boolean f61499a2 = false;

    /* renamed from: a1 */
    public int f61498a1 = -1;

    public zc0(AbstractC0395cy abstractC0395cy) {
        this.f61500a3 = abstractC0395cy;
        this.f61497a0 = abstractC0395cy.mo210653a4() - 1;
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (!this.f61499a2) {
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        Object key = entry.getKey();
        int i = this.f61498a1;
        AbstractC0395cy abstractC0395cy = this.f61500a3;
        Object objMo210651a2 = abstractC0395cy.mo210651a2(i, 0);
        if (key != objMo210651a2 && (key == null || !key.equals(objMo210651a2))) {
            return false;
        }
        Object value = entry.getValue();
        Object objMo210651a22 = abstractC0395cy.mo210651a2(this.f61498a1, 1);
        return value == objMo210651a22 || (value != null && value.equals(objMo210651a22));
    }

    @Override // java.util.Map.Entry
    public final Object getKey() {
        if (!this.f61499a2) {
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }
        return this.f61500a3.mo210651a2(this.f61498a1, 0);
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        if (!this.f61499a2) {
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }
        return this.f61500a3.mo210651a2(this.f61498a1, 1);
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.f61498a1 < this.f61497a0;
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        if (!this.f61499a2) {
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }
        int i = this.f61498a1;
        AbstractC0395cy abstractC0395cy = this.f61500a3;
        Object objMo210651a2 = abstractC0395cy.mo210651a2(i, 0);
        Object objMo210651a22 = abstractC0395cy.mo210651a2(this.f61498a1, 1);
        return (objMo210651a2 == null ? 0 : objMo210651a2.hashCode()) ^ (objMo210651a22 != null ? objMo210651a22.hashCode() : 0);
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        this.f61498a1++;
        this.f61499a2 = true;
        return this;
    }

    @Override // java.util.Iterator
    public final void remove() {
        if (!this.f61499a2) {
            throw new IllegalStateException();
        }
        this.f61500a3.mo210657a8(this.f61498a1);
        this.f61498a1--;
        this.f61497a0--;
        this.f61499a2 = false;
    }

    @Override // java.util.Map.Entry
    public final Object setValue(Object obj) {
        if (this.f61499a2) {
            return this.f61500a3.mo210658a9(this.f61498a1, obj);
        }
        throw new IllegalStateException("This container does not support retaining Map.Entry objects");
    }

    public final String toString() {
        return getKey() + "=" + getValue();
    }
}
