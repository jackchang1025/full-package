package android.sun.security.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
class DerInputBuffer extends ByteArrayInputStream implements Cloneable {
    public DerInputBuffer(byte[] bArr) {
        super(bArr);
    }

    /* JADX WARN: Code restructure failed: missing block: B:67:0x0155, code lost:
    
        if (r7 == 2) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0158, code lost:
    
        if (r7 != 3) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x015a, code lost:
    
        r1 = ((java.io.ByteArrayInputStream) r13).pos;
        ((java.io.ByteArrayInputStream) r13).pos = r1 + 1;
        r1 = (java.lang.Character.digit((char) r11[r1], 10) * 100) + 0;
        r10 = ((java.io.ByteArrayInputStream) r13).buf;
        r11 = ((java.io.ByteArrayInputStream) r13).pos;
        ((java.io.ByteArrayInputStream) r13).pos = r11 + 1;
        r10 = (java.lang.Character.digit((char) r10[r11], 10) * 10) + r1;
        r1 = ((java.io.ByteArrayInputStream) r13).buf;
        r11 = ((java.io.ByteArrayInputStream) r13).pos;
        ((java.io.ByteArrayInputStream) r13).pos = r11 + 1;
        r1 = java.lang.Character.digit((char) r1[r11], 10) + r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x019a, code lost:
    
        throw new java.io.IOException(p000a.AbstractC0000a.m16l("Parse ", r15, " time, unsupported precision for seconds value"));
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x019b, code lost:
    
        r1 = ((java.io.ByteArrayInputStream) r13).pos;
        ((java.io.ByteArrayInputStream) r13).pos = r1 + 1;
        r1 = (java.lang.Character.digit((char) r11[r1], 10) * 100) + 0;
        r10 = ((java.io.ByteArrayInputStream) r13).buf;
        r11 = ((java.io.ByteArrayInputStream) r13).pos;
        ((java.io.ByteArrayInputStream) r13).pos = r11 + 1;
        r1 = (java.lang.Character.digit((char) r10[r11], 10) * 10) + r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private Date getTime(int i2, boolean z2) {
        int i3;
        String str;
        int i4;
        int i5;
        byte[] bArr;
        int digit;
        byte[] bArr2 = ((ByteArrayInputStream) this).buf;
        int i6 = ((ByteArrayInputStream) this).pos;
        ((ByteArrayInputStream) this).pos = i6 + 1;
        if (z2) {
            int digit2 = Character.digit((char) bArr2[i6], 10) * 1000;
            byte[] bArr3 = ((ByteArrayInputStream) this).buf;
            int i7 = ((ByteArrayInputStream) this).pos;
            ((ByteArrayInputStream) this).pos = i7 + 1;
            int digit3 = (Character.digit((char) bArr3[i7], 10) * 100) + digit2;
            byte[] bArr4 = ((ByteArrayInputStream) this).buf;
            int i8 = ((ByteArrayInputStream) this).pos;
            ((ByteArrayInputStream) this).pos = i8 + 1;
            int digit4 = (Character.digit((char) bArr4[i8], 10) * 10) + digit3;
            byte[] bArr5 = ((ByteArrayInputStream) this).buf;
            int i9 = ((ByteArrayInputStream) this).pos;
            ((ByteArrayInputStream) this).pos = i9 + 1;
            i3 = Character.digit((char) bArr5[i9], 10) + digit4;
            i2 -= 2;
            str = "Generalized";
        } else {
            int digit5 = Character.digit((char) bArr2[i6], 10) * 10;
            byte[] bArr6 = ((ByteArrayInputStream) this).buf;
            int i10 = ((ByteArrayInputStream) this).pos;
            ((ByteArrayInputStream) this).pos = i10 + 1;
            int digit6 = Character.digit((char) bArr6[i10], 10) + digit5;
            i3 = digit6 < 50 ? digit6 + 2000 : digit6 + 1900;
            str = "UTC";
        }
        int i11 = i3;
        byte[] bArr7 = ((ByteArrayInputStream) this).buf;
        int i12 = ((ByteArrayInputStream) this).pos;
        ((ByteArrayInputStream) this).pos = i12 + 1;
        int digit7 = Character.digit((char) bArr7[i12], 10) * 10;
        byte[] bArr8 = ((ByteArrayInputStream) this).buf;
        int i13 = ((ByteArrayInputStream) this).pos;
        ((ByteArrayInputStream) this).pos = i13 + 1;
        int digit8 = Character.digit((char) bArr8[i13], 10) + digit7;
        byte[] bArr9 = ((ByteArrayInputStream) this).buf;
        int i14 = ((ByteArrayInputStream) this).pos;
        ((ByteArrayInputStream) this).pos = i14 + 1;
        int digit9 = Character.digit((char) bArr9[i14], 10) * 10;
        byte[] bArr10 = ((ByteArrayInputStream) this).buf;
        int i15 = ((ByteArrayInputStream) this).pos;
        ((ByteArrayInputStream) this).pos = i15 + 1;
        int digit10 = Character.digit((char) bArr10[i15], 10) + digit9;
        byte[] bArr11 = ((ByteArrayInputStream) this).buf;
        int i16 = ((ByteArrayInputStream) this).pos;
        ((ByteArrayInputStream) this).pos = i16 + 1;
        int digit11 = Character.digit((char) bArr11[i16], 10) * 10;
        byte[] bArr12 = ((ByteArrayInputStream) this).buf;
        int i17 = ((ByteArrayInputStream) this).pos;
        ((ByteArrayInputStream) this).pos = i17 + 1;
        int digit12 = Character.digit((char) bArr12[i17], 10) + digit11;
        byte[] bArr13 = ((ByteArrayInputStream) this).buf;
        int i18 = ((ByteArrayInputStream) this).pos;
        ((ByteArrayInputStream) this).pos = i18 + 1;
        int digit13 = Character.digit((char) bArr13[i18], 10) * 10;
        byte[] bArr14 = ((ByteArrayInputStream) this).buf;
        int i19 = ((ByteArrayInputStream) this).pos;
        ((ByteArrayInputStream) this).pos = i19 + 1;
        int digit14 = Character.digit((char) bArr14[i19], 10) + digit13;
        int i20 = i2 - 10;
        byte b = 90;
        if (i20 <= 2 || i20 >= 12) {
            i4 = 0;
            i5 = 0;
        } else {
            byte[] bArr15 = ((ByteArrayInputStream) this).buf;
            int i21 = ((ByteArrayInputStream) this).pos;
            ((ByteArrayInputStream) this).pos = i21 + 1;
            int digit15 = Character.digit((char) bArr15[i21], 10) * 10;
            byte[] bArr16 = ((ByteArrayInputStream) this).buf;
            int i22 = ((ByteArrayInputStream) this).pos;
            ((ByteArrayInputStream) this).pos = i22 + 1;
            int digit16 = Character.digit((char) bArr16[i22], 10) + digit15;
            i20 -= 2;
            byte[] bArr17 = ((ByteArrayInputStream) this).buf;
            int i23 = ((ByteArrayInputStream) this).pos;
            byte b2 = bArr17[i23];
            if (b2 == 46 || b2 == 44) {
                int i24 = i20 - 1;
                int i25 = i23 + 1;
                ((ByteArrayInputStream) this).pos = i25;
                int i26 = 0;
                while (true) {
                    bArr = ((ByteArrayInputStream) this).buf;
                    byte b3 = bArr[i25];
                    if (b3 == b || b3 == 43 || b3 == 45) {
                        break;
                    }
                    i25++;
                    i26++;
                    b = 90;
                }
                int i27 = ((ByteArrayInputStream) this).pos;
                ((ByteArrayInputStream) this).pos = i27 + 1;
                digit = (Character.digit((char) bArr[i27], 10) * 100) + 0;
                i20 = i24 - i26;
            } else {
                digit = 0;
            }
            i5 = digit16;
            i4 = digit;
        }
        if (digit8 == 0 || digit10 == 0 || digit8 > 12 || digit10 > 31 || digit12 >= 24 || digit14 >= 60 || i5 >= 60) {
            throw new IOException(AbstractC0000a.m16l("Parse ", str, " time, invalid format"));
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(i11, digit8, digit10, digit12, digit14, i5);
        calendar.setTimeInMillis(i4);
        long timeInMillis = calendar.getTimeInMillis();
        if (i20 != 1 && i20 != 5) {
            throw new IOException(AbstractC0000a.m16l("Parse ", str, " time, invalid offset"));
        }
        byte[] bArr18 = ((ByteArrayInputStream) this).buf;
        int i28 = ((ByteArrayInputStream) this).pos;
        int i29 = i28 + 1;
        ((ByteArrayInputStream) this).pos = i29;
        byte b4 = bArr18[i28];
        if (b4 == 43) {
            ((ByteArrayInputStream) this).pos = i29 + 1;
            int digit17 = Character.digit((char) bArr18[i29], 10) * 10;
            byte[] bArr19 = ((ByteArrayInputStream) this).buf;
            int i30 = ((ByteArrayInputStream) this).pos;
            ((ByteArrayInputStream) this).pos = i30 + 1;
            int digit18 = Character.digit((char) bArr19[i30], 10) + digit17;
            byte[] bArr20 = ((ByteArrayInputStream) this).buf;
            int i31 = ((ByteArrayInputStream) this).pos;
            ((ByteArrayInputStream) this).pos = i31 + 1;
            int digit19 = Character.digit((char) bArr20[i31], 10) * 10;
            byte[] bArr21 = ((ByteArrayInputStream) this).buf;
            int i32 = ((ByteArrayInputStream) this).pos;
            ((ByteArrayInputStream) this).pos = i32 + 1;
            int digit20 = Character.digit((char) bArr21[i32], 10) + digit19;
            if (digit18 >= 24 || digit20 >= 60) {
                throw new IOException(AbstractC0000a.m16l("Parse ", str, " time, +hhmm"));
            }
            timeInMillis -= (((digit18 * 60) + digit20) * 60) * 1000;
        } else if (b4 == 45) {
            ((ByteArrayInputStream) this).pos = i29 + 1;
            int digit21 = Character.digit((char) bArr18[i29], 10) * 10;
            byte[] bArr22 = ((ByteArrayInputStream) this).buf;
            int i33 = ((ByteArrayInputStream) this).pos;
            ((ByteArrayInputStream) this).pos = i33 + 1;
            int digit22 = Character.digit((char) bArr22[i33], 10) + digit21;
            byte[] bArr23 = ((ByteArrayInputStream) this).buf;
            int i34 = ((ByteArrayInputStream) this).pos;
            ((ByteArrayInputStream) this).pos = i34 + 1;
            int digit23 = Character.digit((char) bArr23[i34], 10) * 10;
            byte[] bArr24 = ((ByteArrayInputStream) this).buf;
            int i35 = ((ByteArrayInputStream) this).pos;
            ((ByteArrayInputStream) this).pos = i35 + 1;
            int digit24 = Character.digit((char) bArr24[i35], 10) + digit23;
            if (digit22 >= 24 || digit24 >= 60) {
                throw new IOException(AbstractC0000a.m16l("Parse ", str, " time, -hhmm"));
            }
            timeInMillis += ((digit22 * 60) + digit24) * 60 * 1000;
        } else if (b4 != 90) {
            throw new IOException(AbstractC0000a.m16l("Parse ", str, " time, garbage offset"));
        }
        return new Date(timeInMillis);
    }

    public DerInputBuffer dup() {
        try {
            DerInputBuffer derInputBuffer = (DerInputBuffer) clone();
            derInputBuffer.mark(Integer.MAX_VALUE);
            return derInputBuffer;
        } catch (CloneNotSupportedException e2) {
            throw new IllegalArgumentException(e2.toString());
        }
    }

    public boolean equals(DerInputBuffer derInputBuffer) {
        if (this == derInputBuffer) {
            return true;
        }
        int available = available();
        if (derInputBuffer.available() != available) {
            return false;
        }
        for (int i2 = 0; i2 < available; i2++) {
            if (((ByteArrayInputStream) this).buf[((ByteArrayInputStream) this).pos + i2] != ((ByteArrayInputStream) derInputBuffer).buf[((ByteArrayInputStream) derInputBuffer).pos + i2]) {
                return false;
            }
        }
        return true;
    }

    public BigInteger getBigInteger(int i2, boolean z2) {
        if (i2 > available()) {
            throw new IOException("short read of integer");
        }
        if (i2 == 0) {
            throw new IOException("Invalid encoding: zero length Int value");
        }
        byte[] bArr = new byte[i2];
        System.arraycopy(((ByteArrayInputStream) this).buf, ((ByteArrayInputStream) this).pos, bArr, 0, i2);
        skip(i2);
        return z2 ? new BigInteger(1, bArr) : new BigInteger(bArr);
    }

    public byte[] getBitString() {
        return getBitString(available());
    }

    public Date getGeneralizedTime(int i2) {
        if (i2 > available()) {
            throw new IOException("short read of DER Generalized Time");
        }
        if (i2 < 13 || i2 > 23) {
            throw new IOException("DER Generalized Time length error");
        }
        return getTime(i2, true);
    }

    public int getInteger(int i2) {
        BigInteger bigInteger = getBigInteger(i2, false);
        if (bigInteger.compareTo(BigInteger.valueOf(-2147483648L)) < 0) {
            throw new IOException("Integer below minimum valid value");
        }
        if (bigInteger.compareTo(BigInteger.valueOf(2147483647L)) <= 0) {
            return bigInteger.intValue();
        }
        throw new IOException("Integer exceeds maximum valid value");
    }

    public Date getUTCTime(int i2) {
        if (i2 > available()) {
            throw new IOException("short read of DER UTC Time");
        }
        if (i2 < 11 || i2 > 17) {
            throw new IOException("DER UTC Time length error");
        }
        return getTime(i2, false);
    }

    public BitArray getUnalignedBitString() {
        if (((ByteArrayInputStream) this).pos >= ((ByteArrayInputStream) this).count) {
            return null;
        }
        int available = available();
        byte[] bArr = ((ByteArrayInputStream) this).buf;
        int i2 = ((ByteArrayInputStream) this).pos;
        int i3 = bArr[i2] & 255;
        if (i3 > 7) {
            throw new IOException(AbstractC0000a.m11g("Invalid value for unused bits: ", i3));
        }
        int i4 = available - 1;
        byte[] bArr2 = new byte[i4];
        int i5 = i4 == 0 ? 0 : (i4 * 8) - i3;
        System.arraycopy(bArr, i2 + 1, bArr2, 0, i4);
        BitArray bitArray = new BitArray(i5, bArr2);
        ((ByteArrayInputStream) this).pos = ((ByteArrayInputStream) this).count;
        return bitArray;
    }

    public int hashCode() {
        int available = available();
        int i2 = ((ByteArrayInputStream) this).pos;
        int i3 = 0;
        for (int i4 = 0; i4 < available; i4++) {
            i3 += ((ByteArrayInputStream) this).buf[i2 + i4] * i4;
        }
        return i3;
    }

    public int peek() {
        int i2 = ((ByteArrayInputStream) this).pos;
        if (i2 < ((ByteArrayInputStream) this).count) {
            return ((ByteArrayInputStream) this).buf[i2];
        }
        throw new IOException("out of data");
    }

    public byte[] toByteArray() {
        int available = available();
        if (available <= 0) {
            return null;
        }
        byte[] bArr = new byte[available];
        System.arraycopy(((ByteArrayInputStream) this).buf, ((ByteArrayInputStream) this).pos, bArr, 0, available);
        return bArr;
    }

    public void truncate(int i2) {
        if (i2 > available()) {
            throw new IOException("insufficient data");
        }
        ((ByteArrayInputStream) this).count = ((ByteArrayInputStream) this).pos + i2;
    }

    public DerInputBuffer(byte[] bArr, int i2, int i3) {
        super(bArr, i2, i3);
    }

    public boolean equals(Object obj) {
        if (obj instanceof DerInputBuffer) {
            return equals((DerInputBuffer) obj);
        }
        return false;
    }

    public byte[] getBitString(int i2) {
        if (i2 > available()) {
            throw new IOException("short read of bit string");
        }
        if (i2 == 0) {
            throw new IOException("Invalid encoding: zero length bit string");
        }
        byte[] bArr = ((ByteArrayInputStream) this).buf;
        int i3 = ((ByteArrayInputStream) this).pos;
        byte b = bArr[i3];
        if (b < 0 || b > 7) {
            throw new IOException("Invalid number of padding bits");
        }
        int i4 = i2 - 1;
        byte[] bArr2 = new byte[i4];
        System.arraycopy(bArr, i3 + 1, bArr2, 0, i4);
        if (b != 0) {
            int i5 = i2 - 2;
            bArr2[i5] = (byte) (bArr2[i5] & (255 << b));
        }
        skip(i2);
        return bArr2;
    }
}
