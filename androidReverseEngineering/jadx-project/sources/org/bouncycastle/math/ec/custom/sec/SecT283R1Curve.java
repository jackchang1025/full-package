package org.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.bouncycastle.math.ec.AbstractECLookupTable;
import org.bouncycastle.math.ec.ECConstants;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.ec.ECLookupTable;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.raw.Nat320;
import org.bouncycastle.util.encoders.Hex;

/* loaded from: classes.dex */
public class SecT283R1Curve extends ECCurve.AbstractF2m {
    private static final ECFieldElement[] SECT283R1_AFFINE_ZS = {new SecT283FieldElement(ECConstants.ONE)};
    private static final int SECT283R1_DEFAULT_COORDS = 6;
    protected SecT283R1Point infinity;

    public SecT283R1Curve() {
        super(283, 5, 7, 12);
        this.infinity = new SecT283R1Point(this, null, null);
        this.f1409a = fromBigInteger(BigInteger.valueOf(1L));
        this.f1410b = fromBigInteger(new BigInteger(1, Hex.decodeStrict("027B680AC8B8596DA5A4AF8A19A0303FCA97FD7645309FA2A581485AF6263E313B79A2F5")));
        this.order = new BigInteger(1, Hex.decodeStrict("03FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEF90399660FC938A90165B042A7CEFADB307"));
        this.cofactor = BigInteger.valueOf(2L);
        this.coord = 6;
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public ECCurve cloneCurve() {
        return new SecT283R1Curve();
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public ECLookupTable createCacheSafeLookupTable(ECPoint[] eCPointArr, int i2, final int i3) {
        final long[] jArr = new long[i3 * 5 * 2];
        int i4 = 0;
        for (int i5 = 0; i5 < i3; i5++) {
            ECPoint eCPoint = eCPointArr[i2 + i5];
            Nat320.copy64(((SecT283FieldElement) eCPoint.getRawXCoord()).f1490x, 0, jArr, i4);
            int i6 = i4 + 5;
            Nat320.copy64(((SecT283FieldElement) eCPoint.getRawYCoord()).f1490x, 0, jArr, i6);
            i4 = i6 + 5;
        }
        return new AbstractECLookupTable() { // from class: org.bouncycastle.math.ec.custom.sec.SecT283R1Curve.1
            private ECPoint createPoint(long[] jArr2, long[] jArr3) {
                return SecT283R1Curve.this.createRawPoint(new SecT283FieldElement(jArr2), new SecT283FieldElement(jArr3), SecT283R1Curve.SECT283R1_AFFINE_ZS);
            }

            @Override // org.bouncycastle.math.ec.ECLookupTable
            public int getSize() {
                return i3;
            }

            @Override // org.bouncycastle.math.ec.ECLookupTable
            public ECPoint lookup(int i7) {
                long[] create64 = Nat320.create64();
                long[] create642 = Nat320.create64();
                int i8 = 0;
                for (int i9 = 0; i9 < i3; i9++) {
                    long j2 = ((i9 ^ i7) - 1) >> 31;
                    for (int i10 = 0; i10 < 5; i10++) {
                        long j3 = create64[i10];
                        long[] jArr2 = jArr;
                        create64[i10] = j3 ^ (jArr2[i8 + i10] & j2);
                        create642[i10] = create642[i10] ^ (jArr2[(i8 + 5) + i10] & j2);
                    }
                    i8 += 10;
                }
                return createPoint(create64, create642);
            }

            @Override // org.bouncycastle.math.ec.AbstractECLookupTable, org.bouncycastle.math.ec.ECLookupTable
            public ECPoint lookupVar(int i7) {
                long[] create64 = Nat320.create64();
                long[] create642 = Nat320.create64();
                int i8 = i7 * 5 * 2;
                for (int i9 = 0; i9 < 5; i9++) {
                    long[] jArr2 = jArr;
                    create64[i9] = jArr2[i8 + i9];
                    create642[i9] = jArr2[i8 + 5 + i9];
                }
                return createPoint(create64, create642);
            }
        };
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        return new SecT283R1Point(this, eCFieldElement, eCFieldElement2);
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public ECFieldElement fromBigInteger(BigInteger bigInteger) {
        return new SecT283FieldElement(bigInteger);
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public int getFieldSize() {
        return 283;
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public ECPoint getInfinity() {
        return this.infinity;
    }

    public int getK1() {
        return 5;
    }

    public int getK2() {
        return 7;
    }

    public int getK3() {
        return 12;
    }

    public int getM() {
        return 283;
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
        return new SecT283R1Point(this, eCFieldElement, eCFieldElement2, eCFieldElementArr);
    }
}
