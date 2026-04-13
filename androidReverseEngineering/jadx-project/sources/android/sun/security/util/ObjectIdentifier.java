package android.sun.security.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Arrays;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public final class ObjectIdentifier implements Serializable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long serialVersionUID = 8697030238860181294L;
    private int componentLen;
    private Object components;
    private transient boolean componentsCalculated;
    private byte[] encoding;
    private volatile transient String stringForm;

    public static class HugeOidNotSupportedByOldJDK implements Serializable {
        private static final long serialVersionUID = 1;
        static HugeOidNotSupportedByOldJDK theOne = new HugeOidNotSupportedByOldJDK();
    }

    public ObjectIdentifier(DerInputBuffer derInputBuffer) {
        this.encoding = null;
        this.components = null;
        this.componentLen = -1;
        this.componentsCalculated = false;
        DerInputStream derInputStream = new DerInputStream(derInputBuffer);
        byte[] bArr = new byte[derInputStream.available()];
        this.encoding = bArr;
        derInputStream.getBytes(bArr);
        check(this.encoding);
    }

    private static void check(byte[] bArr) {
        int length = bArr.length;
        if (length < 1 || (bArr[length - 1] & DerValue.TAG_CONTEXT) != 0) {
            throw new IOException("ObjectIdentifier() -- Invalid DER encoding, not ended");
        }
        for (int i2 = 0; i2 < length; i2++) {
            if (bArr[i2] == Byte.MIN_VALUE && (i2 == 0 || (bArr[i2 - 1] & DerValue.TAG_CONTEXT) == 0)) {
                throw new IOException("ObjectIdentifier() -- Invalid DER encoding, useless extra octet detected");
            }
        }
    }

    private static void checkCount(int i2) {
        if (i2 < 2) {
            throw new IOException("ObjectIdentifier() -- Must be at least two oid components ");
        }
    }

    private static void checkFirstComponent(int i2) {
        if (i2 < 0 || i2 > 2) {
            throw new IOException("ObjectIdentifier() -- First oid component is invalid ");
        }
    }

    private static void checkOtherComponent(int i2, int i3) {
        if (i3 >= 0) {
            return;
        }
        throw new IOException("ObjectIdentifier() -- oid component #" + (i2 + 1) + " must be non-negative ");
    }

    private static void checkSecondComponent(int i2, int i3) {
        if (i3 < 0 || (i2 != 2 && i3 > 39)) {
            throw new IOException("ObjectIdentifier() -- Second oid component is invalid ");
        }
    }

    private void init(int[] iArr, int i2) {
        byte[] bArr = new byte[(i2 * 5) + 1];
        int i3 = iArr[1];
        int i4 = iArr[0];
        int pack7Oid = (i3 < Integer.MAX_VALUE - (i4 * 40) ? pack7Oid((i4 * 40) + i3, bArr, 0) : pack7Oid(BigInteger.valueOf(i3).add(BigInteger.valueOf(iArr[0] * 40)), bArr, 0)) + 0;
        for (int i5 = 2; i5 < i2; i5++) {
            pack7Oid += pack7Oid(iArr[i5], bArr, pack7Oid);
        }
        byte[] bArr2 = new byte[pack7Oid];
        this.encoding = bArr2;
        System.arraycopy(bArr, 0, bArr2, 0, pack7Oid);
    }

    public static ObjectIdentifier newInternal(int[] iArr) {
        try {
            return new ObjectIdentifier(iArr);
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }
    }

    private static byte[] pack(byte[] bArr, int i2, int i3, int i4, int i5) {
        if (i4 == i5) {
            return (byte[]) bArr.clone();
        }
        int i6 = i3 * i4;
        int i7 = ((i6 + i5) - 1) / i5;
        byte[] bArr2 = new byte[i7];
        int i8 = (i7 * i5) - i6;
        int i9 = 0;
        while (i9 < i6) {
            int i10 = i4 - (i9 % i4);
            int i11 = i5 - (i8 % i5);
            int i12 = i10 > i11 ? i11 : i10;
            int i13 = i8 / i5;
            bArr2[i13] = (byte) (((((bArr[(i9 / i4) + i2] + 256) >> (i10 - i12)) & ((1 << i12) - 1)) << (i11 - i12)) | bArr2[i13]);
            i9 += i12;
            i8 += i12;
        }
        return bArr2;
    }

    private static int pack7Oid(int i2, byte[] bArr, int i3) {
        return pack7Oid(new byte[]{(byte) (i2 >> 24), (byte) (i2 >> 16), (byte) (i2 >> 8), (byte) i2}, 0, 4, bArr, i3);
    }

    private static int pack8(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        byte[] pack = pack(bArr, i2, i3, 7, 8);
        int length = pack.length - 1;
        for (int length2 = pack.length - 2; length2 >= 0; length2--) {
            if (pack[length2] != 0) {
                length = length2;
            }
        }
        System.arraycopy(pack, length, bArr2, i4, pack.length - length);
        return pack.length - length;
    }

    private void readObject(ObjectInputStream objectInputStream) {
        objectInputStream.defaultReadObject();
        if (this.encoding == null) {
            init((int[]) this.components, this.componentLen);
        }
    }

    private int[] toIntArray() {
        int i2;
        int length = this.encoding.length;
        int[] iArr = new int[20];
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < length; i5++) {
            if ((this.encoding[i5] & DerValue.TAG_CONTEXT) == 0) {
                int i6 = (i5 - i4) + 1;
                if (i6 > 4) {
                    BigInteger bigInteger = new BigInteger(pack(this.encoding, i4, i6, 7, 8));
                    if (i4 == 0) {
                        int i7 = i3 + 1;
                        iArr[i3] = 2;
                        BigInteger subtract = bigInteger.subtract(BigInteger.valueOf(80L));
                        if (subtract.compareTo(BigInteger.valueOf(2147483647L)) == 1) {
                            return null;
                        }
                        i2 = i7 + 1;
                        iArr[i7] = subtract.intValue();
                    } else {
                        if (bigInteger.compareTo(BigInteger.valueOf(2147483647L)) == 1) {
                            return null;
                        }
                        i2 = i3 + 1;
                        iArr[i3] = bigInteger.intValue();
                    }
                    i3 = i2;
                } else {
                    int i8 = 0;
                    for (int i9 = i4; i9 <= i5; i9++) {
                        i8 = (i8 << 7) | (this.encoding[i9] & Byte.MAX_VALUE);
                    }
                    if (i4 != 0) {
                        iArr[i3] = i8;
                        i3++;
                    } else if (i8 < 80) {
                        int i10 = i3 + 1;
                        iArr[i3] = i8 / 40;
                        i3 = i10 + 1;
                        iArr[i10] = i8 % 40;
                    } else {
                        int i11 = i3 + 1;
                        iArr[i3] = 2;
                        i3 = i11 + 1;
                        iArr[i11] = i8 - 80;
                    }
                }
                i4 = i5 + 1;
            }
            if (i3 >= iArr.length) {
                iArr = Arrays.copyOf(iArr, i3 + 10);
            }
        }
        return Arrays.copyOf(iArr, i3);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) {
        if (!this.componentsCalculated) {
            int[] intArray = toIntArray();
            if (intArray != null) {
                this.components = intArray;
                this.componentLen = intArray.length;
            } else {
                this.components = HugeOidNotSupportedByOldJDK.theOne;
            }
            this.componentsCalculated = true;
        }
        objectOutputStream.defaultWriteObject();
    }

    public void encode(DerOutputStream derOutputStream) {
        derOutputStream.write((byte) 6, this.encoding);
    }

    @Deprecated
    public boolean equals(ObjectIdentifier objectIdentifier) {
        return equals((Object) objectIdentifier);
    }

    public int hashCode() {
        return Arrays.hashCode(this.encoding);
    }

    public String toString() {
        String str = this.stringForm;
        if (str != null) {
            return str;
        }
        int length = this.encoding.length;
        StringBuffer stringBuffer = new StringBuffer(length * 4);
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            if ((this.encoding[i3] & DerValue.TAG_CONTEXT) == 0) {
                if (i2 != 0) {
                    stringBuffer.append('.');
                }
                int i4 = (i3 - i2) + 1;
                if (i4 > 4) {
                    BigInteger bigInteger = new BigInteger(pack(this.encoding, i2, i4, 7, 8));
                    if (i2 == 0) {
                        stringBuffer.append("2.");
                        stringBuffer.append(bigInteger.subtract(BigInteger.valueOf(80L)));
                    } else {
                        stringBuffer.append(bigInteger);
                    }
                } else {
                    int i5 = 0;
                    for (int i6 = i2; i6 <= i3; i6++) {
                        i5 = (i5 << 7) | (this.encoding[i6] & Byte.MAX_VALUE);
                    }
                    if (i2 == 0) {
                        if (i5 < 80) {
                            stringBuffer.append(i5 / 40);
                            stringBuffer.append('.');
                            i5 %= 40;
                        } else {
                            stringBuffer.append("2.");
                            i5 -= 80;
                        }
                    }
                    stringBuffer.append(i5);
                }
                i2 = i3 + 1;
            }
        }
        String stringBuffer2 = stringBuffer.toString();
        this.stringForm = stringBuffer2;
        return stringBuffer2;
    }

    public ObjectIdentifier(DerInputStream derInputStream) {
        this.encoding = null;
        this.components = null;
        this.componentLen = -1;
        this.componentsCalculated = false;
        byte b = (byte) derInputStream.getByte();
        if (b != 6) {
            throw new IOException(AbstractC0000a.m12h("ObjectIdentifier() -- data isn't an object ID (tag = ", b, ")"));
        }
        byte[] bArr = new byte[derInputStream.getLength()];
        this.encoding = bArr;
        derInputStream.getBytes(bArr);
        check(this.encoding);
    }

    private static void checkFirstComponent(BigInteger bigInteger) {
        if (bigInteger.signum() == -1 || bigInteger.compareTo(BigInteger.valueOf(2L)) == 1) {
            throw new IOException("ObjectIdentifier() -- First oid component is invalid ");
        }
    }

    private static void checkOtherComponent(int i2, BigInteger bigInteger) {
        if (bigInteger.signum() != -1) {
            return;
        }
        throw new IOException("ObjectIdentifier() -- oid component #" + (i2 + 1) + " must be non-negative ");
    }

    private static void checkSecondComponent(int i2, BigInteger bigInteger) {
        if (bigInteger.signum() == -1 || (i2 != 2 && bigInteger.compareTo(BigInteger.valueOf(39L)) == 1)) {
            throw new IOException("ObjectIdentifier() -- Second oid component is invalid ");
        }
    }

    private static int pack7Oid(BigInteger bigInteger, byte[] bArr, int i2) {
        byte[] byteArray = bigInteger.toByteArray();
        return pack7Oid(byteArray, 0, byteArray.length, bArr, i2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ObjectIdentifier) {
            return Arrays.equals(this.encoding, ((ObjectIdentifier) obj).encoding);
        }
        return false;
    }

    private static int pack7Oid(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        byte[] pack = pack(bArr, i2, i3, 8, 7);
        int length = pack.length - 1;
        for (int length2 = pack.length - 2; length2 >= 0; length2--) {
            byte b = pack[length2];
            if (b != 0) {
                length = length2;
            }
            pack[length2] = (byte) (b | DerValue.TAG_CONTEXT);
        }
        System.arraycopy(pack, length, bArr2, i4, pack.length - length);
        return pack.length - length;
    }

    public ObjectIdentifier(String str) {
        int indexOf;
        String substring;
        int i2;
        int parseInt;
        int pack7Oid;
        this.encoding = null;
        this.components = null;
        this.componentLen = -1;
        this.componentsCalculated = false;
        byte[] bArr = new byte[str.length()];
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        do {
            try {
                indexOf = str.indexOf(46, i3);
                if (indexOf == -1) {
                    substring = str.substring(i3);
                    i2 = str.length() - i3;
                } else {
                    substring = str.substring(i3, indexOf);
                    i2 = indexOf - i3;
                }
                if (i2 > 9) {
                    BigInteger bigInteger = new BigInteger(substring);
                    if (i4 == 0) {
                        checkFirstComponent(bigInteger);
                        parseInt = bigInteger.intValue();
                        i5 = parseInt;
                    } else {
                        if (i4 == 1) {
                            checkSecondComponent(i5, bigInteger);
                            bigInteger = bigInteger.add(BigInteger.valueOf(i5 * 40));
                        } else {
                            checkOtherComponent(i4, bigInteger);
                        }
                        pack7Oid = pack7Oid(bigInteger, bArr, i6);
                        i6 += pack7Oid;
                    }
                } else {
                    parseInt = Integer.parseInt(substring);
                    if (i4 == 0) {
                        checkFirstComponent(parseInt);
                        i5 = parseInt;
                    } else {
                        if (i4 == 1) {
                            checkSecondComponent(i5, parseInt);
                            parseInt += i5 * 40;
                        } else {
                            checkOtherComponent(i4, parseInt);
                        }
                        pack7Oid = pack7Oid(parseInt, bArr, i6);
                        i6 += pack7Oid;
                    }
                }
                i3 = indexOf + 1;
                i4++;
            } catch (IOException e2) {
                throw e2;
            } catch (Exception e3) {
                throw new IOException(AbstractC0000a.m29y(e3, new StringBuilder("ObjectIdentifier() -- Invalid format: ")), e3);
            }
        } while (indexOf != -1);
        checkCount(i4);
        byte[] bArr2 = new byte[i6];
        this.encoding = bArr2;
        System.arraycopy(bArr, 0, bArr2, 0, i6);
        this.stringForm = str;
    }

    public ObjectIdentifier(int[] iArr) {
        this.encoding = null;
        this.components = null;
        this.componentLen = -1;
        this.componentsCalculated = false;
        checkCount(iArr.length);
        checkFirstComponent(iArr[0]);
        checkSecondComponent(iArr[0], iArr[1]);
        for (int i2 = 2; i2 < iArr.length; i2++) {
            checkOtherComponent(i2, iArr[i2]);
        }
        init(iArr, iArr.length);
    }
}
