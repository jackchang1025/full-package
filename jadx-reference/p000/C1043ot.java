package p000;

import java.io.IOException;
import java.util.Date;
import org.bouncycastle.util.Strings;

/* renamed from: ot */
/* loaded from: classes2.dex */
public class C1043ot extends C0123b6 {
    public C1043ot(String str) {
        super(str);
    }

    private byte[] getDERTime() {
        byte[] bArr = this.contents;
        if (bArr[bArr.length - 1] != 90) {
            return bArr;
        }
        if (!hasMinutes()) {
            byte[] bArr2 = this.contents;
            byte[] bArr3 = new byte[bArr2.length + 4];
            System.arraycopy(bArr2, 0, bArr3, 0, bArr2.length - 1);
            System.arraycopy(Strings.toByteArray("0000Z"), 0, bArr3, this.contents.length - 1, 5);
            return bArr3;
        }
        if (!hasSeconds()) {
            byte[] bArr4 = this.contents;
            byte[] bArr5 = new byte[bArr4.length + 2];
            System.arraycopy(bArr4, 0, bArr5, 0, bArr4.length - 1);
            System.arraycopy(Strings.toByteArray("00Z"), 0, bArr5, this.contents.length - 1, 3);
            return bArr5;
        }
        if (!hasFractionalSeconds()) {
            return this.contents;
        }
        int length = this.contents.length - 2;
        while (length > 0 && this.contents[length] == 48) {
            length--;
        }
        byte[] bArr6 = this.contents;
        if (bArr6[length] == 46) {
            byte[] bArr7 = new byte[length + 1];
            System.arraycopy(bArr6, 0, bArr7, 0, length);
            bArr7[length] = 90;
            return bArr7;
        }
        byte[] bArr8 = new byte[length + 2];
        int i = length + 1;
        System.arraycopy(bArr6, 0, bArr8, 0, i);
        bArr8[i] = 90;
        return bArr8;
    }

    @Override // p000.C0123b6, p000.AbstractC0164c9
    public void encode(C0163c8 c0163c8, boolean z) throws IOException {
        c0163c8.writeEncodingDL(z, 24, getDERTime());
    }

    @Override // p000.C0123b6, p000.AbstractC0164c9
    public int encodedLength(boolean z) {
        return C0163c8.getLengthOfEncodingDL(z, getDERTime().length);
    }

    public C1043ot(Date date) {
        super(date);
    }

    public C1043ot(byte[] bArr) {
        super(bArr);
    }

    @Override // p000.C0123b6, p000.AbstractC0164c9
    public AbstractC0164c9 toDERObject() {
        return this;
    }

    @Override // p000.C0123b6, p000.AbstractC0164c9
    public AbstractC0164c9 toDLObject() {
        return this;
    }
}
