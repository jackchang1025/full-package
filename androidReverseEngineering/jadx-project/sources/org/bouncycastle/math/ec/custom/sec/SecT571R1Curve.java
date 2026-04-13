package org.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.bouncycastle.math.ec.AbstractECLookupTable;
import org.bouncycastle.math.ec.ECConstants;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.ec.ECLookupTable;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.raw.Nat576;
import org.bouncycastle.util.encoders.Hex;

/* loaded from: classes.dex */
public class SecT571R1Curve extends ECCurve.AbstractF2m {
    private static final ECFieldElement[] SECT571R1_AFFINE_ZS = {new SecT571FieldElement(ECConstants.ONE)};
    private static final int SECT571R1_DEFAULT_COORDS = 6;
    static final SecT571FieldElement SecT571R1_B;
    static final SecT571FieldElement SecT571R1_B_SQRT;
    protected SecT571R1Point infinity;

    static {
        SecT571FieldElement secT571FieldElement = new SecT571FieldElement(new BigInteger(1, Hex.decodeStrict("02F40E7E2221F295DE297117B7F3D62F5C6A97FFCB8CEFF1CD6BA8CE4A9A18AD84FFABBD8EFA59332BE7AD6756A66E294AFD185A78FF12AA520E4DE739BACA0C7FFEFF7F2955727A")));
        SecT571R1_B = secT571FieldElement;
        SecT571R1_B_SQRT = (SecT571FieldElement) secT571FieldElement.sqrt();
    }

    public SecT571R1Curve() {
        super(571, 2, 5, 10);
        this.infinity = new SecT571R1Point(this, null, null);
        this.f1409a = fromBigInteger(BigInteger.valueOf(1L));
        this.f1410b = SecT571R1_B;
        this.order = new BigInteger(1, Hex.decodeStrict("03FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFE661CE18FF55987308059B186823851EC7DD9CA1161DE93D5174D66E8382E9BB2FE84E47"));
        this.cofactor = BigInteger.valueOf(2L);
        this.coord = 6;
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public ECCurve cloneCurve() {
        return new SecT571R1Curve();
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public ECLookupTable createCacheSafeLookupTable(ECPoint[] eCPointArr, int i2, final int i3) {
        final long[] jArr = new long[i3 * 9 * 2];
        int i4 = 0;
        for (int i5 = 0; i5 < i3; i5++) {
            ECPoint eCPoint = eCPointArr[i2 + i5];
            Nat576.copy64(((SecT571FieldElement) eCPoint.getRawXCoord()).f1492x, 0, jArr, i4);
            int i6 = i4 + 9;
            Nat576.copy64(((SecT571FieldElement) eCPoint.getRawYCoord()).f1492x, 0, jArr, i6);
            i4 = i6 + 9;
        }
        return new AbstractECLookupTable() { // from class: org.bouncycastle.math.ec.custom.sec.SecT571R1Curve.1
            private ECPoint createPoint(long[] jArr2, long[] jArr3) {
                return SecT571R1Curve.this.createRawPoint(new SecT571FieldElement(jArr2), new SecT571FieldElement(jArr3), SecT571R1Curve.SECT571R1_AFFINE_ZS);
            }

            @Override // org.bouncycastle.math.ec.ECLookupTable
            public int getSize() {
                return i3;
            }

            @Override // org.bouncycastle.math.ec.ECLookupTable
            public ECPoint lookup(int i7) {
                long[] create64 = Nat576.create64();
                long[] create642 = Nat576.create64();
                int i8 = 0;
                for (int i9 = 0; i9 < i3; i9++) {
                    long j2 = ((i9 ^ i7) - 1) >> 31;
                    for (int i10 = 0; i10 < 9; i10++) {
                        long j3 = create64[i10];
                        long[] jArr2 = jArr;
                        create64[i10] = j3 ^ (jArr2[i8 + i10] & j2);
                        create642[i10] = create642[i10] ^ (jArr2[(i8 + 9) + i10] & j2);
                    }
                    i8 += 18;
                }
                return createPoint(create64, create642);
            }

            @Override // org.bouncycastle.math.ec.AbstractECLookupTable, org.bouncycastle.math.ec.ECLookupTable
            public ECPoint lookupVar(int i7) {
                long[] create64 = Nat576.create64();
                long[] create642 = Nat576.create64();
                int i8 = i7 * 9 * 2;
                for (int i9 = 0; i9 < 9; i9++) {
                    long[] jArr2 = jArr;
                    create64[i9] = jArr2[i8 + i9];
                    create642[i9] = jArr2[i8 + 9 + i9];
                }
                return createPoint(create64, create642);
            }
        };
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        return new SecT571R1Point(this, eCFieldElement, eCFieldElement2);
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public ECFieldElement fromBigInteger(BigInteger bigInteger) {
        return new SecT571FieldElement(bigInteger);
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public int getFieldSize() {
        return 571;
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public ECPoint getInfinity() {
        return this.infinity;
    }

    public int getK1() {
        return 2;
    }

    public int getK2() {
        return 5;
    }

    public int getK3() {
        return 10;
    }

    public int getM() {
        return 571;
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
        return new SecT571R1Point(this, eCFieldElement, eCFieldElement2, eCFieldElementArr);
    }
}
