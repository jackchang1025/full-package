package p000;

import java.math.BigInteger;

/* loaded from: classes2.dex */
public class gi1 {
    public int getByteLength(AbstractC1316ux abstractC1316ux) {
        return (abstractC1316ux.getFieldSize() + 7) / 8;
    }

    public byte[] integerToBytes(BigInteger bigInteger, int i) {
        byte[] byteArray = bigInteger.toByteArray();
        if (i < byteArray.length) {
            byte[] bArr = new byte[i];
            System.arraycopy(byteArray, byteArray.length - i, bArr, 0, i);
            return bArr;
        }
        if (i <= byteArray.length) {
            return byteArray;
        }
        byte[] bArr2 = new byte[i];
        System.arraycopy(byteArray, 0, bArr2, i - byteArray.length, byteArray.length);
        return bArr2;
    }

    public int getByteLength(AbstractC1330va abstractC1330va) {
        return (abstractC1330va.getFieldSize() + 7) / 8;
    }
}
