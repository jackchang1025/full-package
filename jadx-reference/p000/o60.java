package p000;

/* loaded from: classes2.dex */
public final class o60 {
    private o60() {
    }

    public static int[] clone(int[] iArr) {
        int[] iArr2 = new int[iArr.length];
        System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
        return iArr2;
    }

    public static boolean equals(int[] iArr, int[] iArr2) {
        if (iArr.length != iArr2.length) {
            return false;
        }
        boolean z = true;
        for (int length = iArr.length - 1; length >= 0; length--) {
            z &= iArr[length] == iArr2[length];
        }
        return z;
    }

    public static void fill(int[] iArr, int i) {
        for (int length = iArr.length - 1; length >= 0; length--) {
            iArr[length] = i;
        }
    }

    private static int partition(int[] iArr, int i, int i2, int i3) {
        int i4 = iArr[i3];
        iArr[i3] = iArr[i2];
        iArr[i2] = i4;
        int i5 = i;
        while (i < i2) {
            int i6 = iArr[i];
            if (i6 <= i4) {
                int i7 = iArr[i5];
                iArr[i5] = i6;
                iArr[i] = i7;
                i5++;
            }
            i++;
        }
        int i8 = iArr[i5];
        iArr[i5] = iArr[i2];
        iArr[i2] = i8;
        return i5;
    }

    public static void quicksort(int[] iArr) {
        quicksort(iArr, 0, iArr.length - 1);
    }

    public static int[] subArray(int[] iArr, int i, int i2) {
        int i3 = i2 - i;
        int[] iArr2 = new int[i3];
        System.arraycopy(iArr, i, iArr2, 0, i3);
        return iArr2;
    }

    public static String toHexString(int[] iArr) {
        return C0500fl.toHexString(C0426dr.toByteArray(iArr));
    }

    public static String toString(int[] iArr) {
        String string = "";
        for (int i : iArr) {
            StringBuilder sbM37b8 = AbstractC0003a2.m37b8(string);
            sbM37b8.append(i);
            sbM37b8.append(" ");
            string = sbM37b8.toString();
        }
        return string;
    }

    public static void quicksort(int[] iArr, int i, int i2) {
        if (i2 > i) {
            int iPartition = partition(iArr, i, i2, i2);
            quicksort(iArr, i, iPartition - 1);
            quicksort(iArr, iPartition + 1, i2);
        }
    }
}
