package org.bouncycastle.util;

import java.math.BigInteger;
import java.util.NoSuchElementException;

/* loaded from: classes.dex */
public final class Arrays {

    public static class Iterator<T> implements java.util.Iterator<T> {
        private final T[] dataArray;
        private int position = 0;

        public Iterator(T[] tArr) {
            this.dataArray = tArr;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.position < this.dataArray.length;
        }

        @Override // java.util.Iterator
        public T next() {
            int i2 = this.position;
            T[] tArr = this.dataArray;
            if (i2 != tArr.length) {
                this.position = i2 + 1;
                return tArr[i2];
            }
            throw new NoSuchElementException("Out of elements: " + this.position);
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("Cannot remove element from an Array.");
        }
    }

    private Arrays() {
    }

    public static byte[] append(byte[] bArr, byte b) {
        if (bArr == null) {
            return new byte[]{b};
        }
        int length = bArr.length;
        byte[] bArr2 = new byte[length + 1];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        bArr2[length] = b;
        return bArr2;
    }

    public static boolean areAllZeroes(byte[] bArr, int i2, int i3) {
        int i4 = 0;
        for (int i5 = 0; i5 < i3; i5++) {
            i4 |= bArr[i2 + i5];
        }
        return i4 == 0;
    }

    public static boolean areEqual(byte[] bArr, int i2, int i3, byte[] bArr2, int i4, int i5) {
        int i6 = i3 - i2;
        if (i6 != i5 - i4) {
            return false;
        }
        for (int i7 = 0; i7 < i6; i7++) {
            if (bArr[i2 + i7] != bArr2[i4 + i7]) {
                return false;
            }
        }
        return true;
    }

    public static void clear(byte[] bArr) {
        if (bArr != null) {
            java.util.Arrays.fill(bArr, (byte) 0);
        }
    }

    public static byte[] clone(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return (byte[]) bArr.clone();
    }

    public static int compareUnsigned(byte[] bArr, byte[] bArr2) {
        if (bArr == bArr2) {
            return 0;
        }
        if (bArr == null) {
            return -1;
        }
        if (bArr2 == null) {
            return 1;
        }
        int min = Math.min(bArr.length, bArr2.length);
        for (int i2 = 0; i2 < min; i2++) {
            int i3 = bArr[i2] & 255;
            int i4 = bArr2[i2] & 255;
            if (i3 < i4) {
                return -1;
            }
            if (i3 > i4) {
                return 1;
            }
        }
        if (bArr.length < bArr2.length) {
            return -1;
        }
        return bArr.length > bArr2.length ? 1 : 0;
    }

    public static byte[] concatenate(byte[] bArr, byte[] bArr2) {
        if (bArr == null) {
            return clone(bArr2);
        }
        if (bArr2 == null) {
            return clone(bArr);
        }
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return bArr3;
    }

    public static boolean constantTimeAreEqual(int i2, byte[] bArr, int i3, byte[] bArr2, int i4) {
        if (bArr == null) {
            throw new NullPointerException("'a' cannot be null");
        }
        if (bArr2 == null) {
            throw new NullPointerException("'b' cannot be null");
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("'len' cannot be negative");
        }
        if (i3 > bArr.length - i2) {
            throw new IndexOutOfBoundsException("'aOff' value invalid for specified length");
        }
        if (i4 > bArr2.length - i2) {
            throw new IndexOutOfBoundsException("'bOff' value invalid for specified length");
        }
        int i5 = 0;
        for (int i6 = 0; i6 < i2; i6++) {
            i5 |= bArr[i3 + i6] ^ bArr2[i4 + i6];
        }
        return i5 == 0;
    }

    public static boolean contains(byte[] bArr, byte b) {
        for (byte b2 : bArr) {
            if (b2 == b) {
                return true;
            }
        }
        return false;
    }

    public static byte[] copyOf(byte[] bArr, int i2) {
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, 0, bArr2, 0, Math.min(bArr.length, i2));
        return bArr2;
    }

    public static byte[] copyOfRange(byte[] bArr, int i2, int i3) {
        int length = getLength(i2, i3);
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, i2, bArr2, 0, Math.min(bArr.length - i2, length));
        return bArr2;
    }

    public static void fill(byte[] bArr, byte b) {
        java.util.Arrays.fill(bArr, b);
    }

    private static int getLength(int i2, int i3) {
        int i4 = i3 - i2;
        if (i4 >= 0) {
            return i4;
        }
        StringBuffer stringBuffer = new StringBuffer(i2);
        stringBuffer.append(" > ");
        stringBuffer.append(i3);
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    public static int hashCode(byte[] bArr) {
        if (bArr == null) {
            return 0;
        }
        int length = bArr.length;
        int i2 = length + 1;
        while (true) {
            length--;
            if (length < 0) {
                return i2;
            }
            i2 = (i2 * 257) ^ bArr[length];
        }
    }

    public static boolean isNullOrContainsNull(Object[] objArr) {
        if (objArr == null) {
            return true;
        }
        for (Object obj : objArr) {
            if (obj == null) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNullOrEmpty(byte[] bArr) {
        return bArr == null || bArr.length < 1;
    }

    public static byte[] prepend(byte[] bArr, byte b) {
        if (bArr == null) {
            return new byte[]{b};
        }
        int length = bArr.length;
        byte[] bArr2 = new byte[length + 1];
        System.arraycopy(bArr, 0, bArr2, 1, length);
        bArr2[0] = b;
        return bArr2;
    }

    public static byte[] reverse(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        int length = bArr.length;
        byte[] bArr2 = new byte[length];
        int i2 = 0;
        while (true) {
            length--;
            if (length < 0) {
                return bArr2;
            }
            bArr2[length] = bArr[i2];
            i2++;
        }
    }

    public static byte[] reverseInPlace(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        int length = bArr.length - 1;
        for (int i2 = 0; i2 < length; i2++) {
            byte b = bArr[i2];
            bArr[i2] = bArr[length];
            bArr[length] = b;
            length--;
        }
        return bArr;
    }

    public static int[] append(int[] iArr, int i2) {
        if (iArr == null) {
            return new int[]{i2};
        }
        int length = iArr.length;
        int[] iArr2 = new int[length + 1];
        System.arraycopy(iArr, 0, iArr2, 0, length);
        iArr2[length] = i2;
        return iArr2;
    }

    public static boolean areEqual(byte[] bArr, byte[] bArr2) {
        return java.util.Arrays.equals(bArr, bArr2);
    }

    public static void clear(int[] iArr) {
        if (iArr != null) {
            java.util.Arrays.fill(iArr, 0);
        }
    }

    public static byte[] clone(byte[] bArr, byte[] bArr2) {
        if (bArr == null) {
            return null;
        }
        if (bArr2 == null || bArr2.length != bArr.length) {
            return clone(bArr);
        }
        System.arraycopy(bArr, 0, bArr2, 0, bArr2.length);
        return bArr2;
    }

    public static byte[] concatenate(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        if (bArr == null) {
            return concatenate(bArr2, bArr3);
        }
        if (bArr2 == null) {
            return concatenate(bArr, bArr3);
        }
        if (bArr3 == null) {
            return concatenate(bArr, bArr2);
        }
        byte[] bArr4 = new byte[bArr.length + bArr2.length + bArr3.length];
        System.arraycopy(bArr, 0, bArr4, 0, bArr.length);
        int length = bArr.length + 0;
        System.arraycopy(bArr2, 0, bArr4, length, bArr2.length);
        System.arraycopy(bArr3, 0, bArr4, length + bArr2.length, bArr3.length);
        return bArr4;
    }

    public static boolean constantTimeAreEqual(byte[] bArr, byte[] bArr2) {
        if (bArr == null || bArr2 == null) {
            return false;
        }
        if (bArr == bArr2) {
            return true;
        }
        int length = bArr.length < bArr2.length ? bArr.length : bArr2.length;
        int length2 = bArr.length ^ bArr2.length;
        for (int i2 = 0; i2 != length; i2++) {
            length2 |= bArr[i2] ^ bArr2[i2];
        }
        while (length < bArr2.length) {
            byte b = bArr2[length];
            length2 |= b ^ (~b);
            length++;
        }
        return length2 == 0;
    }

    public static boolean contains(char[] cArr, char c) {
        for (char c2 : cArr) {
            if (c2 == c) {
                return true;
            }
        }
        return false;
    }

    public static char[] copyOf(char[] cArr, int i2) {
        char[] cArr2 = new char[i2];
        System.arraycopy(cArr, 0, cArr2, 0, Math.min(cArr.length, i2));
        return cArr2;
    }

    public static char[] copyOfRange(char[] cArr, int i2, int i3) {
        int length = getLength(i2, i3);
        char[] cArr2 = new char[length];
        System.arraycopy(cArr, i2, cArr2, 0, Math.min(cArr.length - i2, length));
        return cArr2;
    }

    public static void fill(byte[] bArr, int i2, int i3, byte b) {
        java.util.Arrays.fill(bArr, i2, i3, b);
    }

    public static int hashCode(byte[] bArr, int i2, int i3) {
        if (bArr == null) {
            return 0;
        }
        int i4 = i3 + 1;
        while (true) {
            i3--;
            if (i3 < 0) {
                return i4;
            }
            i4 = (i4 * 257) ^ bArr[i2 + i3];
        }
    }

    public static boolean isNullOrEmpty(int[] iArr) {
        return iArr == null || iArr.length < 1;
    }

    public static int[] prepend(int[] iArr, int i2) {
        if (iArr == null) {
            return new int[]{i2};
        }
        int length = iArr.length;
        int[] iArr2 = new int[length + 1];
        System.arraycopy(iArr, 0, iArr2, 1, length);
        iArr2[0] = i2;
        return iArr2;
    }

    public static int[] reverse(int[] iArr) {
        if (iArr == null) {
            return null;
        }
        int length = iArr.length;
        int[] iArr2 = new int[length];
        int i2 = 0;
        while (true) {
            length--;
            if (length < 0) {
                return iArr2;
            }
            iArr2[length] = iArr[i2];
            i2++;
        }
    }

    public static int[] reverseInPlace(int[] iArr) {
        if (iArr == null) {
            return null;
        }
        int length = iArr.length - 1;
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = iArr[i2];
            iArr[i2] = iArr[length];
            iArr[length] = i3;
            length--;
        }
        return iArr;
    }

    public static String[] append(String[] strArr, String str) {
        if (strArr == null) {
            return new String[]{str};
        }
        int length = strArr.length;
        String[] strArr2 = new String[length + 1];
        System.arraycopy(strArr, 0, strArr2, 0, length);
        strArr2[length] = str;
        return strArr2;
    }

    public static boolean areEqual(char[] cArr, char[] cArr2) {
        return java.util.Arrays.equals(cArr, cArr2);
    }

    public static char[] clone(char[] cArr) {
        if (cArr == null) {
            return null;
        }
        return (char[]) cArr.clone();
    }

    public static byte[] concatenate(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) {
        if (bArr == null) {
            return concatenate(bArr2, bArr3, bArr4);
        }
        if (bArr2 == null) {
            return concatenate(bArr, bArr3, bArr4);
        }
        if (bArr3 == null) {
            return concatenate(bArr, bArr2, bArr4);
        }
        if (bArr4 == null) {
            return concatenate(bArr, bArr2, bArr3);
        }
        byte[] bArr5 = new byte[bArr.length + bArr2.length + bArr3.length + bArr4.length];
        System.arraycopy(bArr, 0, bArr5, 0, bArr.length);
        int length = bArr.length + 0;
        System.arraycopy(bArr2, 0, bArr5, length, bArr2.length);
        int length2 = length + bArr2.length;
        System.arraycopy(bArr3, 0, bArr5, length2, bArr3.length);
        System.arraycopy(bArr4, 0, bArr5, length2 + bArr3.length, bArr4.length);
        return bArr5;
    }

    public static boolean contains(int[] iArr, int i2) {
        for (int i3 : iArr) {
            if (i3 == i2) {
                return true;
            }
        }
        return false;
    }

    public static int[] copyOf(int[] iArr, int i2) {
        int[] iArr2 = new int[i2];
        System.arraycopy(iArr, 0, iArr2, 0, Math.min(iArr.length, i2));
        return iArr2;
    }

    public static int[] copyOfRange(int[] iArr, int i2, int i3) {
        int length = getLength(i2, i3);
        int[] iArr2 = new int[length];
        System.arraycopy(iArr, i2, iArr2, 0, Math.min(iArr.length - i2, length));
        return iArr2;
    }

    public static void fill(char[] cArr, char c) {
        java.util.Arrays.fill(cArr, c);
    }

    public static int hashCode(char[] cArr) {
        if (cArr == null) {
            return 0;
        }
        int length = cArr.length;
        int i2 = length + 1;
        while (true) {
            length--;
            if (length < 0) {
                return i2;
            }
            i2 = (i2 * 257) ^ cArr[length];
        }
    }

    public static boolean isNullOrEmpty(Object[] objArr) {
        return objArr == null || objArr.length < 1;
    }

    public static short[] prepend(short[] sArr, short s2) {
        if (sArr == null) {
            return new short[]{s2};
        }
        int length = sArr.length;
        short[] sArr2 = new short[length + 1];
        System.arraycopy(sArr, 0, sArr2, 1, length);
        sArr2[0] = s2;
        return sArr2;
    }

    public static short[] append(short[] sArr, short s2) {
        if (sArr == null) {
            return new short[]{s2};
        }
        int length = sArr.length;
        short[] sArr2 = new short[length + 1];
        System.arraycopy(sArr, 0, sArr2, 0, length);
        sArr2[length] = s2;
        return sArr2;
    }

    public static boolean areEqual(int[] iArr, int[] iArr2) {
        return java.util.Arrays.equals(iArr, iArr2);
    }

    public static int[] clone(int[] iArr) {
        if (iArr == null) {
            return null;
        }
        return (int[]) iArr.clone();
    }

    public static byte[] concatenate(byte[][] bArr) {
        int i2 = 0;
        for (int i3 = 0; i3 != bArr.length; i3++) {
            i2 += bArr[i3].length;
        }
        byte[] bArr2 = new byte[i2];
        int i4 = 0;
        for (int i5 = 0; i5 != bArr.length; i5++) {
            byte[] bArr3 = bArr[i5];
            System.arraycopy(bArr3, 0, bArr2, i4, bArr3.length);
            i4 += bArr[i5].length;
        }
        return bArr2;
    }

    public static boolean contains(long[] jArr, long j2) {
        for (long j3 : jArr) {
            if (j3 == j2) {
                return true;
            }
        }
        return false;
    }

    public static long[] copyOf(long[] jArr, int i2) {
        long[] jArr2 = new long[i2];
        System.arraycopy(jArr, 0, jArr2, 0, Math.min(jArr.length, i2));
        return jArr2;
    }

    public static long[] copyOfRange(long[] jArr, int i2, int i3) {
        int length = getLength(i2, i3);
        long[] jArr2 = new long[length];
        System.arraycopy(jArr, i2, jArr2, 0, Math.min(jArr.length - i2, length));
        return jArr2;
    }

    public static void fill(char[] cArr, int i2, int i3, char c) {
        java.util.Arrays.fill(cArr, i2, i3, c);
    }

    public static int hashCode(int[] iArr) {
        if (iArr == null) {
            return 0;
        }
        int length = iArr.length;
        int i2 = length + 1;
        while (true) {
            length--;
            if (length < 0) {
                return i2;
            }
            i2 = (i2 * 257) ^ iArr[length];
        }
    }

    public static boolean areEqual(long[] jArr, long[] jArr2) {
        return java.util.Arrays.equals(jArr, jArr2);
    }

    public static long[] clone(long[] jArr) {
        if (jArr == null) {
            return null;
        }
        return (long[]) jArr.clone();
    }

    public static int[] concatenate(int[] iArr, int[] iArr2) {
        if (iArr == null) {
            return clone(iArr2);
        }
        if (iArr2 == null) {
            return clone(iArr);
        }
        int[] iArr3 = new int[iArr.length + iArr2.length];
        System.arraycopy(iArr, 0, iArr3, 0, iArr.length);
        System.arraycopy(iArr2, 0, iArr3, iArr.length, iArr2.length);
        return iArr3;
    }

    public static boolean contains(short[] sArr, short s2) {
        for (short s3 : sArr) {
            if (s3 == s2) {
                return true;
            }
        }
        return false;
    }

    public static BigInteger[] copyOf(BigInteger[] bigIntegerArr, int i2) {
        BigInteger[] bigIntegerArr2 = new BigInteger[i2];
        System.arraycopy(bigIntegerArr, 0, bigIntegerArr2, 0, Math.min(bigIntegerArr.length, i2));
        return bigIntegerArr2;
    }

    public static BigInteger[] copyOfRange(BigInteger[] bigIntegerArr, int i2, int i3) {
        int length = getLength(i2, i3);
        BigInteger[] bigIntegerArr2 = new BigInteger[length];
        System.arraycopy(bigIntegerArr, i2, bigIntegerArr2, 0, Math.min(bigIntegerArr.length - i2, length));
        return bigIntegerArr2;
    }

    public static void fill(int[] iArr, int i2) {
        java.util.Arrays.fill(iArr, i2);
    }

    public static int hashCode(int[] iArr, int i2, int i3) {
        if (iArr == null) {
            return 0;
        }
        int i4 = i3 + 1;
        while (true) {
            i3--;
            if (i3 < 0) {
                return i4;
            }
            i4 = (i4 * 257) ^ iArr[i2 + i3];
        }
    }

    public static boolean areEqual(Object[] objArr, Object[] objArr2) {
        return java.util.Arrays.equals(objArr, objArr2);
    }

    public static long[] clone(long[] jArr, long[] jArr2) {
        if (jArr == null) {
            return null;
        }
        if (jArr2 == null || jArr2.length != jArr.length) {
            return clone(jArr);
        }
        System.arraycopy(jArr, 0, jArr2, 0, jArr2.length);
        return jArr2;
    }

    public static short[] concatenate(short[] sArr, short[] sArr2) {
        if (sArr == null) {
            return clone(sArr2);
        }
        if (sArr2 == null) {
            return clone(sArr);
        }
        short[] sArr3 = new short[sArr.length + sArr2.length];
        System.arraycopy(sArr, 0, sArr3, 0, sArr.length);
        System.arraycopy(sArr2, 0, sArr3, sArr.length, sArr2.length);
        return sArr3;
    }

    public static boolean contains(boolean[] zArr, boolean z2) {
        for (boolean z3 : zArr) {
            if (z3 == z2) {
                return true;
            }
        }
        return false;
    }

    public static short[] copyOf(short[] sArr, int i2) {
        short[] sArr2 = new short[i2];
        System.arraycopy(sArr, 0, sArr2, 0, Math.min(sArr.length, i2));
        return sArr2;
    }

    public static short[] copyOfRange(short[] sArr, int i2, int i3) {
        int length = getLength(i2, i3);
        short[] sArr2 = new short[length];
        System.arraycopy(sArr, i2, sArr2, 0, Math.min(sArr.length - i2, length));
        return sArr2;
    }

    public static void fill(int[] iArr, int i2, int i3, int i4) {
        java.util.Arrays.fill(iArr, i2, i3, i4);
    }

    public static int hashCode(long[] jArr) {
        if (jArr == null) {
            return 0;
        }
        int length = jArr.length;
        int i2 = length + 1;
        while (true) {
            length--;
            if (length < 0) {
                return i2;
            }
            long j2 = jArr[length];
            i2 = (((i2 * 257) ^ ((int) j2)) * 257) ^ ((int) (j2 >>> 32));
        }
    }

    public static boolean areEqual(short[] sArr, short[] sArr2) {
        return java.util.Arrays.equals(sArr, sArr2);
    }

    public static BigInteger[] clone(BigInteger[] bigIntegerArr) {
        if (bigIntegerArr == null) {
            return null;
        }
        return (BigInteger[]) bigIntegerArr.clone();
    }

    public static boolean[] copyOf(boolean[] zArr, int i2) {
        boolean[] zArr2 = new boolean[i2];
        System.arraycopy(zArr, 0, zArr2, 0, Math.min(zArr.length, i2));
        return zArr2;
    }

    public static boolean[] copyOfRange(boolean[] zArr, int i2, int i3) {
        int length = getLength(i2, i3);
        boolean[] zArr2 = new boolean[length];
        System.arraycopy(zArr, i2, zArr2, 0, Math.min(zArr.length - i2, length));
        return zArr2;
    }

    public static void fill(long[] jArr, int i2, int i3, long j2) {
        java.util.Arrays.fill(jArr, i2, i3, j2);
    }

    public static int hashCode(long[] jArr, int i2, int i3) {
        if (jArr == null) {
            return 0;
        }
        int i4 = i3 + 1;
        while (true) {
            i3--;
            if (i3 < 0) {
                return i4;
            }
            long j2 = jArr[i2 + i3];
            i4 = (((i4 * 257) ^ ((int) j2)) * 257) ^ ((int) (j2 >>> 32));
        }
    }

    public static boolean areEqual(boolean[] zArr, boolean[] zArr2) {
        return java.util.Arrays.equals(zArr, zArr2);
    }

    public static short[] clone(short[] sArr) {
        if (sArr == null) {
            return null;
        }
        return (short[]) sArr.clone();
    }

    public static void fill(long[] jArr, long j2) {
        java.util.Arrays.fill(jArr, j2);
    }

    public static int hashCode(Object[] objArr) {
        if (objArr == null) {
            return 0;
        }
        int length = objArr.length;
        int i2 = length + 1;
        while (true) {
            length--;
            if (length < 0) {
                return i2;
            }
            i2 = (i2 * 257) ^ Objects.hashCode(objArr[length]);
        }
    }

    public static boolean[] clone(boolean[] zArr) {
        if (zArr == null) {
            return null;
        }
        return (boolean[]) zArr.clone();
    }

    public static void fill(Object[] objArr, int i2, int i3, Object obj) {
        java.util.Arrays.fill(objArr, i2, i3, obj);
    }

    public static int hashCode(short[] sArr) {
        if (sArr == null) {
            return 0;
        }
        int length = sArr.length;
        int i2 = length + 1;
        while (true) {
            length--;
            if (length < 0) {
                return i2;
            }
            i2 = (i2 * 257) ^ (sArr[length] & 255);
        }
    }

    public static byte[][] clone(byte[][] bArr) {
        if (bArr == null) {
            return null;
        }
        int length = bArr.length;
        byte[][] bArr2 = new byte[length][];
        for (int i2 = 0; i2 != length; i2++) {
            bArr2[i2] = clone(bArr[i2]);
        }
        return bArr2;
    }

    public static void fill(Object[] objArr, Object obj) {
        java.util.Arrays.fill(objArr, obj);
    }

    public static int hashCode(int[][] iArr) {
        int i2 = 0;
        for (int i3 = 0; i3 != iArr.length; i3++) {
            i2 = (i2 * 257) + hashCode(iArr[i3]);
        }
        return i2;
    }

    public static byte[][][] clone(byte[][][] bArr) {
        if (bArr == null) {
            return null;
        }
        int length = bArr.length;
        byte[][][] bArr2 = new byte[length][][];
        for (int i2 = 0; i2 != length; i2++) {
            bArr2[i2] = clone(bArr[i2]);
        }
        return bArr2;
    }

    public static void fill(short[] sArr, int i2, int i3, short s2) {
        java.util.Arrays.fill(sArr, i2, i3, s2);
    }

    public static int hashCode(short[][] sArr) {
        int i2 = 0;
        for (int i3 = 0; i3 != sArr.length; i3++) {
            i2 = (i2 * 257) + hashCode(sArr[i3]);
        }
        return i2;
    }

    public static void fill(short[] sArr, short s2) {
        java.util.Arrays.fill(sArr, s2);
    }

    public static int hashCode(short[][][] sArr) {
        int i2 = 0;
        for (int i3 = 0; i3 != sArr.length; i3++) {
            i2 = (i2 * 257) + hashCode(sArr[i3]);
        }
        return i2;
    }

    public static void fill(boolean[] zArr, int i2, int i3, boolean z2) {
        java.util.Arrays.fill(zArr, i2, i3, z2);
    }

    public static void fill(boolean[] zArr, boolean z2) {
        java.util.Arrays.fill(zArr, z2);
    }
}
