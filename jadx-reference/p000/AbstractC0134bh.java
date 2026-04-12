package p000;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.collections.AbstractC0770a1;
import kotlin.collections.EmptyList;
import kotlin.collections.EmptySet;
import kotlin.text.AbstractC0778a0;

/* renamed from: bh */
/* loaded from: classes2.dex */
public abstract class AbstractC0134bh extends kj1 {
    /* renamed from: e2 */
    public static List m210719e2(Object[] objArr) {
        t60.m214695b6(objArr, "<this>");
        List listAsList = Arrays.asList(objArr);
        t60.m214694b5(listAsList, "asList(this)");
        return listAsList;
    }

    /* renamed from: e3 */
    public static void m210720e3(int i, byte[] bArr, int i2, byte[] bArr2, int i3) {
        t60.m214695b6(bArr, "<this>");
        t60.m214695b6(bArr2, "destination");
        System.arraycopy(bArr, i2, bArr2, i, i3 - i2);
    }

    /* renamed from: e4 */
    public static void m210721e4(Object[] objArr, Object[] objArr2, int i, int i2, int i3) {
        t60.m214695b6(objArr, "<this>");
        t60.m214695b6(objArr2, "destination");
        System.arraycopy(objArr, i2, objArr2, i, i3 - i2);
    }

    /* renamed from: e5 */
    public static byte[] m210722e5(byte[] bArr, int i, int i2) {
        t60.m214695b6(bArr, "<this>");
        kj1.m213562a9(i2, bArr.length);
        byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr, i, i2);
        t60.m214694b5(bArrCopyOfRange, "copyOfRange(this, fromIndex, toIndex)");
        return bArrCopyOfRange;
    }

    /* renamed from: e6 */
    public static Object[] m210723e6(Object[] objArr, int i, int i2) {
        t60.m214695b6(objArr, "<this>");
        kj1.m213562a9(i2, objArr.length);
        Object[] objArrCopyOfRange = Arrays.copyOfRange(objArr, i, i2);
        t60.m214694b5(objArrCopyOfRange, "copyOfRange(this, fromIndex, toIndex)");
        return objArrCopyOfRange;
    }

    /* renamed from: e7 */
    public static void m210724e7(Object[] objArr, int i, int i2) {
        t60.m214695b6(objArr, "<this>");
        Arrays.fill(objArr, i, i2, (Object) null);
    }

    /* renamed from: e8 */
    public static ArrayList m210725e8(Object[] objArr) {
        ArrayList arrayList = new ArrayList();
        for (Object obj : objArr) {
            if (obj != null) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    /* renamed from: e9 */
    public static String m210726e9(byte[] bArr, h10 h10Var) {
        StringBuilder sb = new StringBuilder();
        sb.append((CharSequence) "");
        int i = 0;
        for (byte b : bArr) {
            i++;
            if (i > 1) {
                sb.append((CharSequence) "");
            }
            if (h10Var != null) {
                sb.append((CharSequence) h10Var.invoke(Byte.valueOf(b)));
            } else {
                sb.append((CharSequence) String.valueOf((int) b));
            }
        }
        sb.append((CharSequence) "");
        String string = sb.toString();
        t60.m214694b5(string, "joinTo(StringBuilder(), …ed, transform).toString()");
        return string;
    }

    /* renamed from: f0 */
    public static String m210727f0(Object[] objArr, int i) {
        String str = (i & 1) != 0 ? ", " : "";
        StringBuilder sb = new StringBuilder();
        sb.append((CharSequence) "");
        int i2 = 0;
        for (Object obj : objArr) {
            i2++;
            if (i2 > 1) {
                sb.append((CharSequence) str);
            }
            AbstractC0778a0.m213648a0(sb, obj, null);
        }
        sb.append((CharSequence) "");
        String string = sb.toString();
        t60.m214694b5(string, "joinTo(StringBuilder(), …ed, transform).toString()");
        return string;
    }

    /* renamed from: f1 */
    public static Object[] m210728f1(Object[] objArr, Object[] objArr2) {
        t60.m214695b6(objArr, "<this>");
        t60.m214695b6(objArr2, "elements");
        int length = objArr.length;
        int length2 = objArr2.length;
        Object[] objArrCopyOf = Arrays.copyOf(objArr, length + length2);
        System.arraycopy(objArr2, 0, objArrCopyOf, length, length2);
        t60.m214694b5(objArrCopyOf, "result");
        return objArrCopyOf;
    }

    /* renamed from: f2 */
    public static char m210729f2(char[] cArr) {
        int length = cArr.length;
        if (length == 0) {
            throw new NoSuchElementException("Array is empty.");
        }
        if (length == 1) {
            return cArr[0];
        }
        throw new IllegalArgumentException("Array has more than one element.");
    }

    /* renamed from: f3 */
    public static byte[] m210730f3(byte[] bArr, n60 n60Var) {
        t60.m214695b6(n60Var, "indices");
        return n60Var.isEmpty() ? new byte[0] : m210722e5(bArr, n60Var.f57461a0, n60Var.f57462a1 + 1);
    }

    /* renamed from: f4 */
    public static List m210731f4(byte[] bArr) {
        int i = 0;
        if (8 < bArr.length) {
            ArrayList arrayList = new ArrayList(8);
            int length = bArr.length;
            int i2 = 0;
            while (i < length) {
                arrayList.add(Byte.valueOf(bArr[i]));
                i2++;
                if (i2 == 8) {
                    break;
                }
                i++;
            }
            return arrayList;
        }
        int length2 = bArr.length;
        if (length2 == 0) {
            return EmptyList.f57568a0;
        }
        if (length2 == 1) {
            return AbstractC1117qo.m214451e7(Byte.valueOf(bArr[0]));
        }
        ArrayList arrayList2 = new ArrayList(bArr.length);
        int length3 = bArr.length;
        while (i < length3) {
            arrayList2.add(Byte.valueOf(bArr[i]));
            i++;
        }
        return arrayList2;
    }

    /* renamed from: f5 */
    public static List m210732f5(Object[] objArr, int i) {
        if (i < 0) {
            throw new IllegalArgumentException(AbstractC0003a2.m30b1("Requested element count ", i, " is less than zero.").toString());
        }
        if (i == 0) {
            return EmptyList.f57568a0;
        }
        if (i >= objArr.length) {
            return m210733f6(objArr);
        }
        if (i == 1) {
            return AbstractC1117qo.m214451e7(objArr[0]);
        }
        ArrayList arrayList = new ArrayList(i);
        int i2 = 0;
        for (Object obj : objArr) {
            arrayList.add(obj);
            i2++;
            if (i2 == i) {
                break;
            }
        }
        return arrayList;
    }

    /* renamed from: f6 */
    public static List m210733f6(Object[] objArr) {
        t60.m214695b6(objArr, "<this>");
        int length = objArr.length;
        return length != 0 ? length != 1 ? new ArrayList(new C0114ay(objArr, false)) : AbstractC1117qo.m214451e7(objArr[0]) : EmptyList.f57568a0;
    }

    /* renamed from: f7 */
    public static Set m210734f7(Object[] objArr) {
        int length = objArr.length;
        if (length == 0) {
            return EmptySet.f57570a0;
        }
        if (length == 1) {
            Set setSingleton = Collections.singleton(objArr[0]);
            t60.m214694b5(setSingleton, "singleton(element)");
            return setSingleton;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet(AbstractC0770a1.m213612f7(objArr.length));
        for (Object obj : objArr) {
            linkedHashSet.add(obj);
        }
        return linkedHashSet;
    }
}
