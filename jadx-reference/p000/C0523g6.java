package p000;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: g6 */
/* loaded from: classes2.dex */
public class C0523g6 implements Iterator, d80 {

    /* renamed from: a0 */
    public final /* synthetic */ int f56413a0 = 0;

    /* renamed from: a1 */
    public int f56414a1;

    /* renamed from: a2 */
    public final Object f56415a2;

    public C0523g6(Object[] objArr) {
        t60.m214695b6(objArr, "array");
        this.f56415a2 = objArr;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        switch (this.f56413a0) {
            case 0:
                return this.f56414a1 < ((AbstractC0528g9) this.f56415a2).size();
            case 1:
                return this.f56414a1 < ((Object[]) this.f56415a2).length;
            default:
                Iterator it = (Iterator) this.f56415a2;
                while (this.f56414a1 > 0 && it.hasNext()) {
                    it.next();
                    this.f56414a1--;
                }
                return it.hasNext();
        }
    }

    @Override // java.util.Iterator
    public final Object next() {
        switch (this.f56413a0) {
            case 0:
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                AbstractC0528g9 abstractC0528g9 = (AbstractC0528g9) this.f56415a2;
                int i = this.f56414a1;
                this.f56414a1 = i + 1;
                return abstractC0528g9.get(i);
            case 1:
                try {
                    Object[] objArr = (Object[]) this.f56415a2;
                    int i2 = this.f56414a1;
                    this.f56414a1 = i2 + 1;
                    return objArr[i2];
                } catch (ArrayIndexOutOfBoundsException e) {
                    this.f56414a1--;
                    throw new NoSuchElementException(e.getMessage());
                }
            default:
                Iterator it = (Iterator) this.f56415a2;
                while (this.f56414a1 > 0 && it.hasNext()) {
                    it.next();
                    this.f56414a1--;
                }
                return it.next();
        }
    }

    @Override // java.util.Iterator
    public final void remove() {
        switch (this.f56413a0) {
            case 0:
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
            case 1:
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
            default:
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }
    }

    public C0523g6(AbstractC0528g9 abstractC0528g9) {
        this.f56415a2 = abstractC0528g9;
    }

    public C0523g6(C1305um c1305um) {
        this.f56415a2 = c1305um.f60472a0.iterator();
        this.f56414a1 = c1305um.f60473a1;
    }
}
