package org.bouncycastle.tsp.ers;

import java.util.Comparator;

/* loaded from: classes.dex */
class ByteArrayComparator implements Comparator {
    @Override // java.util.Comparator
    public int compare(Object obj, Object obj2) {
        byte[] bArr = (byte[]) obj;
        byte[] bArr2 = (byte[]) obj2;
        for (int i2 = 0; i2 < bArr.length && i2 < bArr2.length; i2++) {
            int i3 = bArr[i2] & 255;
            int i4 = bArr2[i2] & 255;
            if (i3 != i4) {
                return i3 - i4;
            }
        }
        return bArr.length - bArr2.length;
    }
}
