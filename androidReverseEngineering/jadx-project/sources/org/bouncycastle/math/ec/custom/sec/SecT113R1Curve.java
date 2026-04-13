package org.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.bouncycastle.math.ec.AbstractECLookupTable;
import org.bouncycastle.math.ec.ECConstants;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.ec.ECLookupTable;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.raw.Nat128;
import org.bouncycastle.util.encoders.Hex;

/* loaded from: classes.dex */
public class SecT113R1Curve extends ECCurve.AbstractF2m {
    private static final ECFieldElement[] SECT113R1_AFFINE_ZS = {new SecT113FieldElement(ECConstants.ONE)};
    private static final int SECT113R1_DEFAULT_COORDS = 6;
    protected SecT113R1Point infinity;

    public SecT113R1Curve() {
        super(113, 9, 0, 0);
        this.infinity = new SecT113R1Point(this, null, null);
        this.f1409a = fromBigInteger(new BigInteger(1, Hex.decodeStrict("003088250CA6E7C7FE649CE85820F7")));
        this.f1410b = fromBigInteger(new BigInteger(1, Hex.decodeStrict("00E8BEE4D3E2260744188BE0E9C723")));
        this.order = new BigInteger(1, Hex.decodeStrict("0100000000000000D9CCEC8A39E56F"));
        this.cofactor = BigInteger.valueOf(2L);
        this.coord = 6;
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public ECCurve cloneCurve() {
        return new SecT113R1Curve();
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public ECLookupTable createCacheSafeLookupTable(ECPoint[] eCPointArr, int i2, final int i3) {
        final long[] jArr = new long[i3 * 2 * 2];
        int i4 = 0;
        for (int i5 = 0; i5 < i3; i5++) {
            ECPoint eCPoint = eCPointArr[i2 + i5];
            Nat128.copy64(((SecT113FieldElement) eCPoint.getRawXCoord()).f1484x, 0, jArr, i4);
            int i6 = i4 + 2;
            Nat128.copy64(((SecT113FieldElement) eCPoint.getRawYCoord()).f1484x, 0, jArr, i6);
            i4 = i6 + 2;
        }
        return new AbstractECLookupTable() { // from class: org.bouncycastle.math.ec.custom.sec.SecT113R1Curve.1
            private ECPoint createPoint(long[] jArr2, long[] jArr3) {
                return SecT113R1Curve.this.createRawPoint(new SecT113FieldElement(jArr2), new SecT113FieldElement(jArr3), SecT113R1Curve.SECT113R1_AFFINE_ZS);
            }

            @Override // org.bouncycastle.math.ec.ECLookupTable
            public int getSize() {
                return i3;
            }

            @Override // org.bouncycastle.math.ec.ECLookupTable
            public ECPoint lookup(int i7) {
                long[] create64 = Nat128.create64();
                long[] create642 = Nat128.create64();
                int i8 = 0;
                for (int i9 = 0; i9 < i3; i9++) {
                    long j2 = ((i9 ^ i7) - 1) >> 31;
                    for (int i10 = 0; i10 < 2; i10++) {
                        long j3 = create64[i10];
                        long[] jArr2 = jArr;
                        create64[i10] = j3 ^ (jArr2[i8 + i10] & j2);
                        create642[i10] = create642[i10] ^ (jArr2[(i8 + 2) + i10] & j2);
                    }
                    i8 += 4;
                }
                return createPoint(create64, create642);
            }

            @Override // org.bouncycastle.math.ec.AbstractECLookupTable, org.bouncycastle.math.ec.ECLookupTable
            public ECPoint lookupVar(int i7) {
                long[] create64 = Nat128.create64();
                long[] create642 = Nat128.create64();
                int i8 = i7 * 2 * 2;
                for (int i9 = 0; i9 < 2; i9++) {
                    long[] jArr2 = jArr;
                    create64[i9] = jArr2[i8 + i9];
                    create642[i9] = jArr2[i8 + 2 + i9];
                }
                return createPoint(create64, create642);
            }
        };
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        return new SecT113R1Point(this, eCFieldElement, eCFieldElement2);
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public ECFieldElement fromBigInteger(BigInteger bigInteger) {
        return new SecT113FieldElement(bigInteger);
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public int getFieldSize() {
        return 113;
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public ECPoint getInfinity() {
        return this.infinity;
    }

    public int getK1() {
        return 9;
    }

    public int getK2() {
        return 0;
    }

    public int getK3() {
        return 0;
    }

    public int getM() {
        return 113;
    }

    @Override // org.bouncycastle.math.ec.ECCurve.AbstractF2m
    public boolean isKoblitz() {
        return false;
    }

    public boolean isTrinomial() {
        return true;
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public boolean supportsCoordinateSystem(int i2) {
        return i2 == 6;
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] eCFieldElementArr) {
        return new SecT113R1Point(this, eCFieldElement, eCFieldElement2, eCFieldElementArr);
    }
}
