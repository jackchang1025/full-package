package org.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.bouncycastle.math.ec.AbstractECLookupTable;
import org.bouncycastle.math.ec.ECConstants;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.ec.ECLookupTable;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.raw.Nat192;
import org.bouncycastle.util.encoders.Hex;

/* loaded from: classes.dex */
public class SecT131R2Curve extends ECCurve.AbstractF2m {
    private static final ECFieldElement[] SECT131R2_AFFINE_ZS = {new SecT131FieldElement(ECConstants.ONE)};
    private static final int SECT131R2_DEFAULT_COORDS = 6;
    protected SecT131R2Point infinity;

    public SecT131R2Curve() {
        super(131, 2, 3, 8);
        this.infinity = new SecT131R2Point(this, null, null);
        this.f1409a = fromBigInteger(new BigInteger(1, Hex.decodeStrict("03E5A88919D7CAFCBF415F07C2176573B2")));
        this.f1410b = fromBigInteger(new BigInteger(1, Hex.decodeStrict("04B8266A46C55657AC734CE38F018F2192")));
        this.order = new BigInteger(1, Hex.decodeStrict("0400000000000000016954A233049BA98F"));
        this.cofactor = BigInteger.valueOf(2L);
        this.coord = 6;
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public ECCurve cloneCurve() {
        return new SecT131R2Curve();
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public ECLookupTable createCacheSafeLookupTable(ECPoint[] eCPointArr, int i2, final int i3) {
        final long[] jArr = new long[i3 * 3 * 2];
        int i4 = 0;
        for (int i5 = 0; i5 < i3; i5++) {
            ECPoint eCPoint = eCPointArr[i2 + i5];
            Nat192.copy64(((SecT131FieldElement) eCPoint.getRawXCoord()).f1485x, 0, jArr, i4);
            int i6 = i4 + 3;
            Nat192.copy64(((SecT131FieldElement) eCPoint.getRawYCoord()).f1485x, 0, jArr, i6);
            i4 = i6 + 3;
        }
        return new AbstractECLookupTable() { // from class: org.bouncycastle.math.ec.custom.sec.SecT131R2Curve.1
            private ECPoint createPoint(long[] jArr2, long[] jArr3) {
                return SecT131R2Curve.this.createRawPoint(new SecT131FieldElement(jArr2), new SecT131FieldElement(jArr3), SecT131R2Curve.SECT131R2_AFFINE_ZS);
            }

            @Override // org.bouncycastle.math.ec.ECLookupTable
            public int getSize() {
                return i3;
            }

            @Override // org.bouncycastle.math.ec.ECLookupTable
            public ECPoint lookup(int i7) {
                long[] create64 = Nat192.create64();
                long[] create642 = Nat192.create64();
                int i8 = 0;
                for (int i9 = 0; i9 < i3; i9++) {
                    long j2 = ((i9 ^ i7) - 1) >> 31;
                    for (int i10 = 0; i10 < 3; i10++) {
                        long j3 = create64[i10];
                        long[] jArr2 = jArr;
                        create64[i10] = j3 ^ (jArr2[i8 + i10] & j2);
                        create642[i10] = create642[i10] ^ (jArr2[(i8 + 3) + i10] & j2);
                    }
                    i8 += 6;
                }
                return createPoint(create64, create642);
            }

            @Override // org.bouncycastle.math.ec.AbstractECLookupTable, org.bouncycastle.math.ec.ECLookupTable
            public ECPoint lookupVar(int i7) {
                long[] create64 = Nat192.create64();
                long[] create642 = Nat192.create64();
                int i8 = i7 * 3 * 2;
                for (int i9 = 0; i9 < 3; i9++) {
                    long[] jArr2 = jArr;
                    create64[i9] = jArr2[i8 + i9];
                    create642[i9] = jArr2[i8 + 3 + i9];
                }
                return createPoint(create64, create642);
            }
        };
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        return new SecT131R2Point(this, eCFieldElement, eCFieldElement2);
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public ECFieldElement fromBigInteger(BigInteger bigInteger) {
        return new SecT131FieldElement(bigInteger);
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public int getFieldSize() {
        return 131;
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public ECPoint getInfinity() {
        return this.infinity;
    }

    public int getK1() {
        return 2;
    }

    public int getK2() {
        return 3;
    }

    public int getK3() {
        return 8;
    }

    public int getM() {
        return 131;
    }

    @Override // org.bouncycastle.math.ec.ECCurve.AbstractF2m
    public boolean isKoblitz() {
        return false;
    }

    public boolean isTrinomial() {
        return false;
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public boolean supportsCoordinateSystem(int i2) {
        return i2 == 6;
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] eCFieldElementArr) {
        return new SecT131R2Point(this, eCFieldElement, eCFieldElement2, eCFieldElementArr);
    }
}
