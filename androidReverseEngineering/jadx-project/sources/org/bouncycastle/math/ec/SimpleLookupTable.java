package org.bouncycastle.math.ec;

/* loaded from: classes.dex */
public class SimpleLookupTable extends AbstractECLookupTable {
    private final ECPoint[] points;

    public SimpleLookupTable(ECPoint[] eCPointArr, int i2, int i3) {
        this.points = copy(eCPointArr, i2, i3);
    }

    private static ECPoint[] copy(ECPoint[] eCPointArr, int i2, int i3) {
        ECPoint[] eCPointArr2 = new ECPoint[i3];
        for (int i4 = 0; i4 < i3; i4++) {
            eCPointArr2[i4] = eCPointArr[i2 + i4];
        }
        return eCPointArr2;
    }

    @Override // org.bouncycastle.math.ec.ECLookupTable
    public int getSize() {
        return this.points.length;
    }

    @Override // org.bouncycastle.math.ec.ECLookupTable
    public ECPoint lookup(int i2) {
        throw new UnsupportedOperationException("Constant-time lookup not supported");
    }

    @Override // org.bouncycastle.math.ec.AbstractECLookupTable, org.bouncycastle.math.ec.ECLookupTable
    public ECPoint lookupVar(int i2) {
        return this.points[i2];
    }
}
