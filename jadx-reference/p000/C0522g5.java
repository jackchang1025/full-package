package p000;

import java.util.Collection;
import java.util.Iterator;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: g5 */
/* loaded from: classes2.dex */
public final class C0522g5 {
    public /* synthetic */ C0522g5(AbstractC1120qr abstractC1120qr) {
        this();
    }

    public final void checkBoundsIndexes$kotlin_stdlib(int i, int i2, int i3) {
        if (i < 0 || i2 > i3) {
            StringBuilder sbM38b9 = AbstractC0003a2.m38b9("startIndex: ", i, ", endIndex: ", i2, ", size: ");
            sbM38b9.append(i3);
            throw new IndexOutOfBoundsException(sbM38b9.toString());
        }
        if (i <= i2) {
            return;
        }
        throw new IllegalArgumentException("startIndex: " + i + " > endIndex: " + i2);
    }

    public final void checkElementIndex$kotlin_stdlib(int i, int i2) {
        if (i < 0 || i >= i2) {
            throw new IndexOutOfBoundsException("index: " + i + ", size: " + i2);
        }
    }

    public final void checkPositionIndex$kotlin_stdlib(int i, int i2) {
        if (i < 0 || i > i2) {
            throw new IndexOutOfBoundsException("index: " + i + ", size: " + i2);
        }
    }

    public final void checkRangeIndexes$kotlin_stdlib(int i, int i2, int i3) {
        if (i < 0 || i2 > i3) {
            StringBuilder sbM38b9 = AbstractC0003a2.m38b9("fromIndex: ", i, ", toIndex: ", i2, ", size: ");
            sbM38b9.append(i3);
            throw new IndexOutOfBoundsException(sbM38b9.toString());
        }
        if (i <= i2) {
            return;
        }
        throw new IllegalArgumentException("fromIndex: " + i + " > toIndex: " + i2);
    }

    public final boolean orderedEquals$kotlin_stdlib(Collection<?> collection, Collection<?> collection2) {
        t60.m214695b6(collection, "c");
        t60.m214695b6(collection2, "other");
        if (collection.size() != collection2.size()) {
            return false;
        }
        Iterator<?> it = collection2.iterator();
        Iterator<?> it2 = collection.iterator();
        while (it2.hasNext()) {
            if (!t60.m214686a2(it2.next(), it.next())) {
                return false;
            }
        }
        return true;
    }

    public final int orderedHashCode$kotlin_stdlib(Collection<?> collection) {
        t60.m214695b6(collection, "c");
        Iterator<?> it = collection.iterator();
        int iHashCode = 1;
        while (it.hasNext()) {
            Object next = it.next();
            iHashCode = (iHashCode * 31) + (next != null ? next.hashCode() : 0);
        }
        return iHashCode;
    }

    private C0522g5() {
    }
}
