package android.sun.security.util;

import java.io.IOException;
import java.util.ArrayList;

/* loaded from: classes.dex */
class DerIndefLenConverter {
    private static final int CLASS_MASK = 192;
    private static final int FORM_MASK = 32;
    private static final int LEN_LONG = 128;
    private static final int LEN_MASK = 127;
    private static final int SKIP_EOC_BYTES = 2;
    private static final int TAG_MASK = 31;
    private byte[] data;
    private int dataPos;
    private int dataSize;
    private int index;
    private byte[] newData;
    private int newDataPos;
    private int unresolved = 0;
    private ArrayList<Object> ndefsList = new ArrayList<>();
    private int numOfTotalLenBytes = 0;

    private byte[] getLengthBytes(int i2) {
        return i2 < 128 ? new byte[]{(byte) i2} : i2 < 256 ? new byte[]{-127, (byte) i2} : i2 < 65536 ? new byte[]{-126, (byte) (i2 >> 8), (byte) i2} : i2 < 16777216 ? new byte[]{-125, (byte) (i2 >> 16), (byte) (i2 >> 8), (byte) i2} : new byte[]{-124, (byte) (i2 >> 24), (byte) (i2 >> 16), (byte) (i2 >> 8), (byte) i2};
    }

    private int getNumOfLenBytes(int i2) {
        if (i2 < 128) {
            return 1;
        }
        if (i2 < 256) {
            return 2;
        }
        if (i2 < 65536) {
            return 3;
        }
        return i2 < 16777216 ? 4 : 5;
    }

    private boolean isEOC(int i2) {
        return (i2 & 31) == 0 && (i2 & 32) == 0 && (i2 & 192) == 0;
    }

    public static boolean isIndefinite(int i2) {
        return isLongForm(i2) && (i2 & 127) == 0;
    }

    public static boolean isLongForm(int i2) {
        return (i2 & 128) == 128;
    }

    private int parseLength() {
        int i2 = this.dataPos;
        if (i2 == this.dataSize) {
            return 0;
        }
        byte[] bArr = this.data;
        this.dataPos = i2 + 1;
        int i3 = bArr[i2] & 255;
        if (isIndefinite(i3)) {
            this.ndefsList.add(new Integer(this.dataPos));
            this.unresolved++;
            return 0;
        }
        if (!isLongForm(i3)) {
            return i3 & 127;
        }
        int i4 = i3 & 127;
        if (i4 > 4) {
            throw new IOException("Too much data");
        }
        if (this.dataSize - this.dataPos < i4 + 1) {
            throw new IOException("Too little data");
        }
        int i5 = 0;
        for (int i6 = 0; i6 < i4; i6++) {
            byte[] bArr2 = this.data;
            int i7 = this.dataPos;
            this.dataPos = i7 + 1;
            i5 = (i5 << 8) + (bArr2[i7] & 255);
        }
        return i5;
    }

    private void parseTag() {
        int i2 = this.dataPos;
        if (i2 == this.dataSize) {
            return;
        }
        if (isEOC(this.data[i2]) && this.data[this.dataPos + 1] == 0) {
            int size = this.ndefsList.size() - 1;
            int i3 = 0;
            Object obj = null;
            while (size >= 0) {
                obj = this.ndefsList.get(size);
                if (obj instanceof Integer) {
                    break;
                }
                i3 += ((byte[]) obj).length - 3;
                size--;
            }
            if (size < 0) {
                throw new IOException("EOC does not have matching indefinite-length tag");
            }
            this.ndefsList.set(size, getLengthBytes((this.dataPos - ((Integer) obj).intValue()) + i3));
            this.unresolved--;
            this.numOfTotalLenBytes = (r1.length - 3) + this.numOfTotalLenBytes;
        }
        this.dataPos++;
    }

    private void parseValue(int i2) {
        this.dataPos += i2;
    }

    private void writeLength(int i2) {
        if (i2 < 128) {
            byte[] bArr = this.newData;
            int i3 = this.newDataPos;
            this.newDataPos = i3 + 1;
            bArr[i3] = (byte) i2;
            return;
        }
        if (i2 < 256) {
            byte[] bArr2 = this.newData;
            int i4 = this.newDataPos;
            int i5 = i4 + 1;
            bArr2[i4] = -127;
            this.newDataPos = i5 + 1;
            bArr2[i5] = (byte) i2;
            return;
        }
        if (i2 < 65536) {
            byte[] bArr3 = this.newData;
            int i6 = this.newDataPos;
            int i7 = i6 + 1;
            bArr3[i6] = -126;
            int i8 = i7 + 1;
            bArr3[i7] = (byte) (i2 >> 8);
            this.newDataPos = i8 + 1;
            bArr3[i8] = (byte) i2;
            return;
        }
        if (i2 < 16777216) {
            byte[] bArr4 = this.newData;
            int i9 = this.newDataPos;
            int i10 = i9 + 1;
            bArr4[i9] = -125;
            int i11 = i10 + 1;
            bArr4[i10] = (byte) (i2 >> 16);
            int i12 = i11 + 1;
            bArr4[i11] = (byte) (i2 >> 8);
            this.newDataPos = i12 + 1;
            bArr4[i12] = (byte) i2;
            return;
        }
        byte[] bArr5 = this.newData;
        int i13 = this.newDataPos;
        int i14 = i13 + 1;
        bArr5[i13] = -124;
        int i15 = i14 + 1;
        bArr5[i14] = (byte) (i2 >> 24);
        int i16 = i15 + 1;
        bArr5[i15] = (byte) (i2 >> 16);
        int i17 = i16 + 1;
        bArr5[i16] = (byte) (i2 >> 8);
        this.newDataPos = i17 + 1;
        bArr5[i17] = (byte) i2;
    }

    private void writeLengthAndValue() {
        int i2;
        int i3 = this.dataPos;
        if (i3 == this.dataSize) {
            return;
        }
        byte[] bArr = this.data;
        this.dataPos = i3 + 1;
        int i4 = bArr[i3] & 255;
        if (isIndefinite(i4)) {
            ArrayList<Object> arrayList = this.ndefsList;
            int i5 = this.index;
            this.index = i5 + 1;
            byte[] bArr2 = (byte[]) arrayList.get(i5);
            System.arraycopy(bArr2, 0, this.newData, this.newDataPos, bArr2.length);
            this.newDataPos += bArr2.length;
            return;
        }
        if (isLongForm(i4)) {
            int i6 = i4 & 127;
            i2 = 0;
            for (int i7 = 0; i7 < i6; i7++) {
                byte[] bArr3 = this.data;
                int i8 = this.dataPos;
                this.dataPos = i8 + 1;
                i2 = (i2 << 8) + (bArr3[i8] & 255);
            }
        } else {
            i2 = i4 & 127;
        }
        writeLength(i2);
        writeValue(i2);
    }

    private void writeTag() {
        int i2 = this.dataPos;
        if (i2 == this.dataSize) {
            return;
        }
        byte[] bArr = this.data;
        this.dataPos = i2 + 1;
        byte b = bArr[i2];
        if (isEOC(b)) {
            byte[] bArr2 = this.data;
            int i3 = this.dataPos;
            if (bArr2[i3] == 0) {
                this.dataPos = i3 + 1;
                writeTag();
                return;
            }
        }
        byte[] bArr3 = this.newData;
        int i4 = this.newDataPos;
        this.newDataPos = i4 + 1;
        bArr3[i4] = b;
    }

    private void writeValue(int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            byte[] bArr = this.newData;
            int i4 = this.newDataPos;
            this.newDataPos = i4 + 1;
            byte[] bArr2 = this.data;
            int i5 = this.dataPos;
            this.dataPos = i5 + 1;
            bArr[i4] = bArr2[i5];
        }
    }

    public byte[] convert(byte[] bArr) {
        int i2;
        this.data = bArr;
        this.dataPos = 0;
        this.index = 0;
        this.dataSize = bArr.length;
        while (true) {
            if (this.dataPos >= this.dataSize) {
                i2 = 0;
                break;
            }
            parseTag();
            parseValue(parseLength());
            if (this.unresolved == 0) {
                int i3 = this.dataSize;
                int i4 = this.dataPos;
                i2 = i3 - i4;
                this.dataSize = i4;
                break;
            }
        }
        this.newData = new byte[this.dataSize + this.numOfTotalLenBytes + i2];
        this.dataPos = 0;
        this.newDataPos = 0;
        this.index = 0;
        while (true) {
            int i5 = this.dataPos;
            int i6 = this.dataSize;
            if (i5 >= i6) {
                System.arraycopy(bArr, i6, this.newData, this.numOfTotalLenBytes + i6, i2);
                return this.newData;
            }
            writeTag();
            writeLengthAndValue();
        }
    }
}
