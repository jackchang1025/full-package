package p000;

import java.util.ConcurrentModificationException;
import java.util.Map;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public class t01 {

    /* renamed from: a3 */
    public static Object[] f60111a3;

    /* renamed from: a4 */
    public static int f60112a4;

    /* renamed from: a5 */
    public static Object[] f60113a5;

    /* renamed from: a6 */
    public static int f60114a6;

    /* renamed from: a0 */
    public int[] f60115a0 = t60.f60154a6;

    /* renamed from: a1 */
    public Object[] f60116a1 = t60.f60155a7;

    /* renamed from: a2 */
    public int f60117a2 = 0;

    /* renamed from: a2 */
    public static void m214672a2(int[] iArr, Object[] objArr, int i) {
        if (iArr.length == 8) {
            synchronized (t01.class) {
                try {
                    if (f60114a6 < 10) {
                        objArr[0] = f60113a5;
                        objArr[1] = iArr;
                        for (int i2 = (i << 1) - 1; i2 >= 2; i2--) {
                            objArr[i2] = null;
                        }
                        f60113a5 = objArr;
                        f60114a6++;
                    }
                } finally {
                }
            }
            return;
        }
        if (iArr.length == 4) {
            synchronized (t01.class) {
                try {
                    if (f60112a4 < 10) {
                        objArr[0] = f60111a3;
                        objArr[1] = iArr;
                        for (int i3 = (i << 1) - 1; i3 >= 2; i3--) {
                            objArr[i3] = null;
                        }
                        f60111a3 = objArr;
                        f60112a4++;
                    }
                } finally {
                }
            }
        }
    }

    /* renamed from: a0 */
    public final void m214673a0(int i) {
        if (i == 8) {
            synchronized (t01.class) {
                try {
                    Object[] objArr = f60113a5;
                    if (objArr != null) {
                        this.f60116a1 = objArr;
                        f60113a5 = (Object[]) objArr[0];
                        this.f60115a0 = (int[]) objArr[1];
                        objArr[1] = null;
                        objArr[0] = null;
                        f60114a6--;
                        return;
                    }
                } finally {
                }
            }
        } else if (i == 4) {
            synchronized (t01.class) {
                try {
                    Object[] objArr2 = f60111a3;
                    if (objArr2 != null) {
                        this.f60116a1 = objArr2;
                        f60111a3 = (Object[]) objArr2[0];
                        this.f60115a0 = (int[]) objArr2[1];
                        objArr2[1] = null;
                        objArr2[0] = null;
                        f60112a4--;
                        return;
                    }
                } finally {
                }
            }
        }
        this.f60115a0 = new int[i];
        this.f60116a1 = new Object[i << 1];
    }

    /* renamed from: a1 */
    public final void m214674a1(int i) {
        int i2 = this.f60117a2;
        int[] iArr = this.f60115a0;
        if (iArr.length < i) {
            Object[] objArr = this.f60116a1;
            m214673a0(i);
            if (this.f60117a2 > 0) {
                System.arraycopy(iArr, 0, this.f60115a0, 0, i2);
                System.arraycopy(objArr, 0, this.f60116a1, 0, i2 << 1);
            }
            m214672a2(iArr, objArr, i2);
        }
        if (this.f60117a2 != i2) {
            throw new ConcurrentModificationException();
        }
    }

    /* renamed from: a3 */
    public final int m214675a3(int i, Object obj) {
        int i2 = this.f60117a2;
        if (i2 == 0) {
            return -1;
        }
        try {
            int iM214687a3 = t60.m214687a3(i2, i, this.f60115a0);
            if (iM214687a3 < 0 || obj.equals(this.f60116a1[iM214687a3 << 1])) {
                return iM214687a3;
            }
            int i3 = iM214687a3 + 1;
            while (i3 < i2 && this.f60115a0[i3] == i) {
                if (obj.equals(this.f60116a1[i3 << 1])) {
                    return i3;
                }
                i3++;
            }
            for (int i4 = iM214687a3 - 1; i4 >= 0 && this.f60115a0[i4] == i; i4--) {
                if (obj.equals(this.f60116a1[i4 << 1])) {
                    return i4;
                }
            }
            return ~i3;
        } catch (ArrayIndexOutOfBoundsException unused) {
            throw new ConcurrentModificationException();
        }
    }

    /* renamed from: a4 */
    public final int m214676a4(Object obj) {
        return obj == null ? m214677a5() : m214675a3(obj.hashCode(), obj);
    }

    /* renamed from: a5 */
    public final int m214677a5() {
        int i = this.f60117a2;
        if (i == 0) {
            return -1;
        }
        try {
            int iM214687a3 = t60.m214687a3(i, 0, this.f60115a0);
            if (iM214687a3 < 0 || this.f60116a1[iM214687a3 << 1] == null) {
                return iM214687a3;
            }
            int i2 = iM214687a3 + 1;
            while (i2 < i && this.f60115a0[i2] == 0) {
                if (this.f60116a1[i2 << 1] == null) {
                    return i2;
                }
                i2++;
            }
            for (int i3 = iM214687a3 - 1; i3 >= 0 && this.f60115a0[i3] == 0; i3--) {
                if (this.f60116a1[i3 << 1] == null) {
                    return i3;
                }
            }
            return ~i2;
        } catch (ArrayIndexOutOfBoundsException unused) {
            throw new ConcurrentModificationException();
        }
    }

    /* renamed from: a6 */
    public final int m214678a6(Object obj) {
        int i = this.f60117a2 * 2;
        Object[] objArr = this.f60116a1;
        if (obj == null) {
            for (int i2 = 1; i2 < i; i2 += 2) {
                if (objArr[i2] == null) {
                    return i2 >> 1;
                }
            }
            return -1;
        }
        for (int i3 = 1; i3 < i; i3 += 2) {
            if (obj.equals(objArr[i3])) {
                return i3 >> 1;
            }
        }
        return -1;
    }

    /* renamed from: a7 */
    public final Object m214679a7(int i) {
        return this.f60116a1[i << 1];
    }

    /* renamed from: a8 */
    public final Object m214680a8(int i) {
        Object[] objArr = this.f60116a1;
        int i2 = i << 1;
        Object obj = objArr[i2 + 1];
        int i3 = this.f60117a2;
        int i4 = 0;
        if (i3 <= 1) {
            m214672a2(this.f60115a0, objArr, i3);
            this.f60115a0 = t60.f60154a6;
            this.f60116a1 = t60.f60155a7;
        } else {
            int i5 = i3 - 1;
            int[] iArr = this.f60115a0;
            if (iArr.length <= 8 || i3 >= iArr.length / 3) {
                if (i < i5) {
                    int i6 = i + 1;
                    int i7 = i5 - i;
                    System.arraycopy(iArr, i6, iArr, i, i7);
                    Object[] objArr2 = this.f60116a1;
                    System.arraycopy(objArr2, i6 << 1, objArr2, i2, i7 << 1);
                }
                Object[] objArr3 = this.f60116a1;
                int i8 = i5 << 1;
                objArr3[i8] = null;
                objArr3[i8 + 1] = null;
            } else {
                m214673a0(i3 > 8 ? i3 + (i3 >> 1) : 8);
                if (i3 != this.f60117a2) {
                    throw new ConcurrentModificationException();
                }
                if (i > 0) {
                    System.arraycopy(iArr, 0, this.f60115a0, 0, i);
                    System.arraycopy(objArr, 0, this.f60116a1, 0, i2);
                }
                if (i < i5) {
                    int i9 = i + 1;
                    int i10 = i5 - i;
                    System.arraycopy(iArr, i9, this.f60115a0, i, i10);
                    System.arraycopy(objArr, i9 << 1, this.f60116a1, i2, i10 << 1);
                }
            }
            i4 = i5;
        }
        if (i3 != this.f60117a2) {
            throw new ConcurrentModificationException();
        }
        this.f60117a2 = i4;
        return obj;
    }

    /* renamed from: a9 */
    public final Object m214681a9(int i) {
        return this.f60116a1[(i << 1) + 1];
    }

    public final void clear() {
        int i = this.f60117a2;
        if (i > 0) {
            int[] iArr = this.f60115a0;
            Object[] objArr = this.f60116a1;
            this.f60115a0 = t60.f60154a6;
            this.f60116a1 = t60.f60155a7;
            this.f60117a2 = 0;
            m214672a2(iArr, objArr, i);
        }
        if (this.f60117a2 > 0) {
            throw new ConcurrentModificationException();
        }
    }

    public final boolean containsKey(Object obj) {
        return m214676a4(obj) >= 0;
    }

    public final boolean containsValue(Object obj) {
        return m214678a6(obj) >= 0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof t01) {
            t01 t01Var = (t01) obj;
            if (this.f60117a2 != t01Var.f60117a2) {
                return false;
            }
            for (int i = 0; i < this.f60117a2; i++) {
                try {
                    Object objM214679a7 = m214679a7(i);
                    Object objM214681a9 = m214681a9(i);
                    Object orDefault = t01Var.getOrDefault(objM214679a7, null);
                    if (objM214681a9 == null) {
                        if (orDefault != null || !t01Var.containsKey(objM214679a7)) {
                            return false;
                        }
                    } else if (!objM214681a9.equals(orDefault)) {
                        return false;
                    }
                } catch (ClassCastException | NullPointerException unused) {
                    return false;
                }
            }
            return true;
        }
        if (obj instanceof Map) {
            Map map = (Map) obj;
            if (this.f60117a2 != map.size()) {
                return false;
            }
            for (int i2 = 0; i2 < this.f60117a2; i2++) {
                try {
                    Object objM214679a72 = m214679a7(i2);
                    Object objM214681a92 = m214681a9(i2);
                    Object obj2 = map.get(objM214679a72);
                    if (objM214681a92 == null) {
                        if (obj2 != null || !map.containsKey(objM214679a72)) {
                            return false;
                        }
                    } else if (!objM214681a92.equals(obj2)) {
                        return false;
                    }
                } catch (ClassCastException | NullPointerException unused2) {
                }
            }
            return true;
        }
        return false;
    }

    public final Object get(Object obj) {
        return getOrDefault(obj, null);
    }

    public final Object getOrDefault(Object obj, Object obj2) {
        int iM214676a4 = m214676a4(obj);
        return iM214676a4 >= 0 ? this.f60116a1[(iM214676a4 << 1) + 1] : obj2;
    }

    public final int hashCode() {
        int[] iArr = this.f60115a0;
        Object[] objArr = this.f60116a1;
        int i = this.f60117a2;
        int i2 = 1;
        int i3 = 0;
        int iHashCode = 0;
        while (i3 < i) {
            Object obj = objArr[i2];
            iHashCode += (obj == null ? 0 : obj.hashCode()) ^ iArr[i3];
            i3++;
            i2 += 2;
        }
        return iHashCode;
    }

    public final boolean isEmpty() {
        return this.f60117a2 <= 0;
    }

    public final Object put(Object obj, Object obj2) {
        int i;
        int iM214675a3;
        int i2 = this.f60117a2;
        if (obj == null) {
            iM214675a3 = m214677a5();
            i = 0;
        } else {
            int iHashCode = obj.hashCode();
            i = iHashCode;
            iM214675a3 = m214675a3(iHashCode, obj);
        }
        if (iM214675a3 >= 0) {
            int i3 = (iM214675a3 << 1) + 1;
            Object[] objArr = this.f60116a1;
            Object obj3 = objArr[i3];
            objArr[i3] = obj2;
            return obj3;
        }
        int i4 = ~iM214675a3;
        int[] iArr = this.f60115a0;
        if (i2 >= iArr.length) {
            int i5 = 8;
            if (i2 >= 8) {
                i5 = (i2 >> 1) + i2;
            } else if (i2 < 4) {
                i5 = 4;
            }
            Object[] objArr2 = this.f60116a1;
            m214673a0(i5);
            if (i2 != this.f60117a2) {
                throw new ConcurrentModificationException();
            }
            int[] iArr2 = this.f60115a0;
            if (iArr2.length > 0) {
                System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
                System.arraycopy(objArr2, 0, this.f60116a1, 0, objArr2.length);
            }
            m214672a2(iArr, objArr2, i2);
        }
        if (i4 < i2) {
            int[] iArr3 = this.f60115a0;
            int i6 = i4 + 1;
            System.arraycopy(iArr3, i4, iArr3, i6, i2 - i4);
            Object[] objArr3 = this.f60116a1;
            System.arraycopy(objArr3, i4 << 1, objArr3, i6 << 1, (this.f60117a2 - i4) << 1);
        }
        int i7 = this.f60117a2;
        if (i2 == i7) {
            int[] iArr4 = this.f60115a0;
            if (i4 < iArr4.length) {
                iArr4[i4] = i;
                Object[] objArr4 = this.f60116a1;
                int i8 = i4 << 1;
                objArr4[i8] = obj;
                objArr4[i8 + 1] = obj2;
                this.f60117a2 = i7 + 1;
                return null;
            }
        }
        throw new ConcurrentModificationException();
    }

    public final Object putIfAbsent(Object obj, Object obj2) {
        Object orDefault = getOrDefault(obj, null);
        return orDefault == null ? put(obj, obj2) : orDefault;
    }

    public final Object remove(Object obj) {
        int iM214676a4 = m214676a4(obj);
        if (iM214676a4 >= 0) {
            return m214680a8(iM214676a4);
        }
        return null;
    }

    public final Object replace(Object obj, Object obj2) {
        int iM214676a4 = m214676a4(obj);
        if (iM214676a4 < 0) {
            return null;
        }
        int i = (iM214676a4 << 1) + 1;
        Object[] objArr = this.f60116a1;
        Object obj3 = objArr[i];
        objArr[i] = obj2;
        return obj3;
    }

    public final int size() {
        return this.f60117a2;
    }

    public final String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.f60117a2 * 28);
        sb.append('{');
        for (int i = 0; i < this.f60117a2; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            Object objM214679a7 = m214679a7(i);
            if (objM214679a7 != this) {
                sb.append(objM214679a7);
            } else {
                sb.append("(this Map)");
            }
            sb.append('=');
            Object objM214681a9 = m214681a9(i);
            if (objM214681a9 != this) {
                sb.append(objM214681a9);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    public final boolean remove(Object obj, Object obj2) {
        int iM214676a4 = m214676a4(obj);
        if (iM214676a4 < 0) {
            return false;
        }
        Object objM214681a9 = m214681a9(iM214676a4);
        if (obj2 != objM214681a9 && (obj2 == null || !obj2.equals(objM214681a9))) {
            return false;
        }
        m214680a8(iM214676a4);
        return true;
    }

    public final boolean replace(Object obj, Object obj2, Object obj3) {
        int iM214676a4 = m214676a4(obj);
        if (iM214676a4 < 0) {
            return false;
        }
        Object objM214681a9 = m214681a9(iM214676a4);
        if (objM214681a9 != obj2 && (obj2 == null || !obj2.equals(objM214681a9))) {
            return false;
        }
        int i = (iM214676a4 << 1) + 1;
        Object[] objArr = this.f60116a1;
        Object obj4 = objArr[i];
        objArr[i] = obj3;
        return true;
    }
}
