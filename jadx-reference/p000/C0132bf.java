package p000;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: bf */
/* loaded from: classes.dex */
public final class C0132bf implements Collection, Set {

    /* renamed from: a4 */
    public static final int[] f45855a4 = new int[0];

    /* renamed from: a5 */
    public static final Object[] f45856a5 = new Object[0];

    /* renamed from: a6 */
    public static Object[] f45857a6;

    /* renamed from: a7 */
    public static int f45858a7;

    /* renamed from: a8 */
    public static Object[] f45859a8;

    /* renamed from: a9 */
    public static int f45860a9;

    /* renamed from: a0 */
    public int[] f45861a0;

    /* renamed from: a1 */
    public Object[] f45862a1;

    /* renamed from: a2 */
    public int f45863a2;

    /* renamed from: a3 */
    public C0129bc f45864a3;

    public C0132bf(int i) {
        if (i == 0) {
            this.f45861a0 = f45855a4;
            this.f45862a1 = f45856a5;
        } else {
            m210684a0(i);
        }
        this.f45863a2 = 0;
    }

    /* renamed from: a1 */
    public static void m210683a1(int[] iArr, Object[] objArr, int i) {
        if (iArr.length == 8) {
            synchronized (C0132bf.class) {
                try {
                    if (f45860a9 < 10) {
                        objArr[0] = f45859a8;
                        objArr[1] = iArr;
                        for (int i2 = i - 1; i2 >= 2; i2--) {
                            objArr[i2] = null;
                        }
                        f45859a8 = objArr;
                        f45860a9++;
                    }
                } finally {
                }
            }
            return;
        }
        if (iArr.length == 4) {
            synchronized (C0132bf.class) {
                try {
                    if (f45858a7 < 10) {
                        objArr[0] = f45857a6;
                        objArr[1] = iArr;
                        for (int i3 = i - 1; i3 >= 2; i3--) {
                            objArr[i3] = null;
                        }
                        f45857a6 = objArr;
                        f45858a7++;
                    }
                } finally {
                }
            }
        }
    }

    /* renamed from: a0 */
    public final void m210684a0(int i) {
        if (i == 8) {
            synchronized (C0132bf.class) {
                try {
                    Object[] objArr = f45859a8;
                    if (objArr != null) {
                        this.f45862a1 = objArr;
                        f45859a8 = (Object[]) objArr[0];
                        this.f45861a0 = (int[]) objArr[1];
                        objArr[1] = null;
                        objArr[0] = null;
                        f45860a9--;
                        return;
                    }
                } finally {
                }
            }
        } else if (i == 4) {
            synchronized (C0132bf.class) {
                try {
                    Object[] objArr2 = f45857a6;
                    if (objArr2 != null) {
                        this.f45862a1 = objArr2;
                        f45857a6 = (Object[]) objArr2[0];
                        this.f45861a0 = (int[]) objArr2[1];
                        objArr2[1] = null;
                        objArr2[0] = null;
                        f45858a7--;
                        return;
                    }
                } finally {
                }
            }
        }
        this.f45861a0 = new int[i];
        this.f45862a1 = new Object[i];
    }

    /* renamed from: a2 */
    public final int m210685a2(int i, Object obj) {
        int i2 = this.f45863a2;
        if (i2 == 0) {
            return -1;
        }
        int iM214687a3 = t60.m214687a3(i2, i, this.f45861a0);
        if (iM214687a3 < 0 || obj.equals(this.f45862a1[iM214687a3])) {
            return iM214687a3;
        }
        int i3 = iM214687a3 + 1;
        while (i3 < i2 && this.f45861a0[i3] == i) {
            if (obj.equals(this.f45862a1[i3])) {
                return i3;
            }
            i3++;
        }
        for (int i4 = iM214687a3 - 1; i4 >= 0 && this.f45861a0[i4] == i; i4--) {
            if (obj.equals(this.f45862a1[i4])) {
                return i4;
            }
        }
        return ~i3;
    }

    /* renamed from: a3 */
    public final int m210686a3() {
        int i = this.f45863a2;
        if (i == 0) {
            return -1;
        }
        int iM214687a3 = t60.m214687a3(i, 0, this.f45861a0);
        if (iM214687a3 < 0 || this.f45862a1[iM214687a3] == null) {
            return iM214687a3;
        }
        int i2 = iM214687a3 + 1;
        while (i2 < i && this.f45861a0[i2] == 0) {
            if (this.f45862a1[i2] == null) {
                return i2;
            }
            i2++;
        }
        for (int i3 = iM214687a3 - 1; i3 >= 0 && this.f45861a0[i3] == 0; i3--) {
            if (this.f45862a1[i3] == null) {
                return i3;
            }
        }
        return ~i2;
    }

    /* renamed from: a4 */
    public final void m210687a4(int i) {
        Object[] objArr = this.f45862a1;
        Object obj = objArr[i];
        int i2 = this.f45863a2;
        if (i2 <= 1) {
            m210683a1(this.f45861a0, objArr, i2);
            this.f45861a0 = f45855a4;
            this.f45862a1 = f45856a5;
            this.f45863a2 = 0;
            return;
        }
        int[] iArr = this.f45861a0;
        if (iArr.length <= 8 || i2 >= iArr.length / 3) {
            int i3 = i2 - 1;
            this.f45863a2 = i3;
            if (i < i3) {
                int i4 = i + 1;
                System.arraycopy(iArr, i4, iArr, i, i3 - i);
                Object[] objArr2 = this.f45862a1;
                System.arraycopy(objArr2, i4, objArr2, i, this.f45863a2 - i);
            }
            this.f45862a1[this.f45863a2] = null;
            return;
        }
        m210684a0(i2 > 8 ? i2 + (i2 >> 1) : 8);
        this.f45863a2--;
        if (i > 0) {
            System.arraycopy(iArr, 0, this.f45861a0, 0, i);
            System.arraycopy(objArr, 0, this.f45862a1, 0, i);
        }
        int i5 = this.f45863a2;
        if (i < i5) {
            int i6 = i + 1;
            System.arraycopy(iArr, i6, this.f45861a0, i, i5 - i);
            System.arraycopy(objArr, i6, this.f45862a1, i, this.f45863a2 - i);
        }
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean add(Object obj) {
        int i;
        int iM210685a2;
        if (obj == null) {
            iM210685a2 = m210686a3();
            i = 0;
        } else {
            int iHashCode = obj.hashCode();
            i = iHashCode;
            iM210685a2 = m210685a2(iHashCode, obj);
        }
        if (iM210685a2 >= 0) {
            return false;
        }
        int i2 = ~iM210685a2;
        int i3 = this.f45863a2;
        int[] iArr = this.f45861a0;
        if (i3 >= iArr.length) {
            int i4 = 8;
            if (i3 >= 8) {
                i4 = (i3 >> 1) + i3;
            } else if (i3 < 4) {
                i4 = 4;
            }
            Object[] objArr = this.f45862a1;
            m210684a0(i4);
            int[] iArr2 = this.f45861a0;
            if (iArr2.length > 0) {
                System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
                System.arraycopy(objArr, 0, this.f45862a1, 0, objArr.length);
            }
            m210683a1(iArr, objArr, this.f45863a2);
        }
        int i5 = this.f45863a2;
        if (i2 < i5) {
            int[] iArr3 = this.f45861a0;
            int i6 = i2 + 1;
            System.arraycopy(iArr3, i2, iArr3, i6, i5 - i2);
            Object[] objArr2 = this.f45862a1;
            System.arraycopy(objArr2, i2, objArr2, i6, this.f45863a2 - i2);
        }
        this.f45861a0[i2] = i;
        this.f45862a1[i2] = obj;
        this.f45863a2++;
        return true;
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean addAll(Collection collection) {
        int size = collection.size() + this.f45863a2;
        int[] iArr = this.f45861a0;
        boolean zAdd = false;
        if (iArr.length < size) {
            Object[] objArr = this.f45862a1;
            m210684a0(size);
            int i = this.f45863a2;
            if (i > 0) {
                System.arraycopy(iArr, 0, this.f45861a0, 0, i);
                System.arraycopy(objArr, 0, this.f45862a1, 0, this.f45863a2);
            }
            m210683a1(iArr, objArr, this.f45863a2);
        }
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            zAdd |= add(it.next());
        }
        return zAdd;
    }

    @Override // java.util.Collection, java.util.Set
    public final void clear() {
        int i = this.f45863a2;
        if (i != 0) {
            m210683a1(this.f45861a0, this.f45862a1, i);
            this.f45861a0 = f45855a4;
            this.f45862a1 = f45856a5;
            this.f45863a2 = 0;
        }
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        return (obj == null ? m210686a3() : m210685a2(obj.hashCode(), obj)) >= 0;
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean containsAll(Collection collection) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Set) {
            Set set = (Set) obj;
            if (this.f45863a2 != set.size()) {
                return false;
            }
            for (int i = 0; i < this.f45863a2; i++) {
                try {
                    if (!set.contains(this.f45862a1[i])) {
                        return false;
                    }
                } catch (ClassCastException | NullPointerException unused) {
                }
            }
            return true;
        }
        return false;
    }

    @Override // java.util.Collection, java.util.Set
    public final int hashCode() {
        int[] iArr = this.f45861a0;
        int i = this.f45863a2;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            i2 += iArr[i3];
        }
        return i2;
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean isEmpty() {
        return this.f45863a2 <= 0;
    }

    @Override // java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        if (this.f45864a3 == null) {
            this.f45864a3 = new C0129bc(1, this);
        }
        C0129bc c0129bc = this.f45864a3;
        if (((yc0) c0129bc.f55539a1) == null) {
            c0129bc.f55539a1 = new yc0(c0129bc, 1);
        }
        return ((yc0) c0129bc.f55539a1).iterator();
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        int iM210686a3 = obj == null ? m210686a3() : m210685a2(obj.hashCode(), obj);
        if (iM210686a3 < 0) {
            return false;
        }
        m210687a4(iM210686a3);
        return true;
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean removeAll(Collection collection) {
        Iterator it = collection.iterator();
        boolean zRemove = false;
        while (it.hasNext()) {
            zRemove |= remove(it.next());
        }
        return zRemove;
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean retainAll(Collection collection) {
        boolean z = false;
        for (int i = this.f45863a2 - 1; i >= 0; i--) {
            if (!collection.contains(this.f45862a1[i])) {
                m210687a4(i);
                z = true;
            }
        }
        return z;
    }

    @Override // java.util.Collection, java.util.Set
    public final int size() {
        return this.f45863a2;
    }

    @Override // java.util.Collection, java.util.Set
    public final Object[] toArray() {
        int i = this.f45863a2;
        Object[] objArr = new Object[i];
        System.arraycopy(this.f45862a1, 0, objArr, 0, i);
        return objArr;
    }

    public final String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.f45863a2 * 14);
        sb.append('{');
        for (int i = 0; i < this.f45863a2; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            Object obj = this.f45862a1[i];
            if (obj != this) {
                sb.append(obj);
            } else {
                sb.append("(this Set)");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    @Override // java.util.Collection, java.util.Set
    public final Object[] toArray(Object[] objArr) {
        if (objArr.length < this.f45863a2) {
            objArr = (Object[]) Array.newInstance(objArr.getClass().getComponentType(), this.f45863a2);
        }
        System.arraycopy(this.f45862a1, 0, objArr, 0, this.f45863a2);
        int length = objArr.length;
        int i = this.f45863a2;
        if (length > i) {
            objArr[i] = null;
        }
        return objArr;
    }
}
