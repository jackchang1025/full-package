package p000;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class xc0 implements Iterator {

    /* renamed from: a0 */
    public final int f61065a0;

    /* renamed from: a1 */
    public int f61066a1;

    /* renamed from: a2 */
    public int f61067a2;

    /* renamed from: a3 */
    public boolean f61068a3 = false;

    /* renamed from: a4 */
    public final /* synthetic */ AbstractC0395cy f61069a4;

    public xc0(AbstractC0395cy abstractC0395cy, int i) {
        this.f61069a4 = abstractC0395cy;
        this.f61065a0 = i;
        this.f61066a1 = abstractC0395cy.mo210653a4();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.f61067a2 < this.f61066a1;
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Object objMo210651a2 = this.f61069a4.mo210651a2(this.f61067a2, this.f61065a0);
        this.f61067a2++;
        this.f61068a3 = true;
        return objMo210651a2;
    }

    @Override // java.util.Iterator
    public final void remove() {
        if (!this.f61068a3) {
            throw new IllegalStateException();
        }
        int i = this.f61067a2 - 1;
        this.f61067a2 = i;
        this.f61066a1--;
        this.f61068a3 = false;
        this.f61069a4.mo210657a8(i);
    }
}
