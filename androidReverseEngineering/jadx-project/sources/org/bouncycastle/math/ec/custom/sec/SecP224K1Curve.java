package org.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.math.ec.AbstractECLookupTable;
import org.bouncycastle.math.ec.ECConstants;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.ec.ECLookupTable;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.raw.Nat224;
import org.bouncycastle.util.encoders.Hex;

/* loaded from: classes.dex */
public class SecP224K1Curve extends ECCurve.AbstractFp {
    private static final int SECP224K1_DEFAULT_COORDS = 2;
    protected SecP224K1Point infinity;

    /* renamed from: q */
    public static final BigInteger f1457q = SecP224K1FieldElement.f1459Q;
    private static final ECFieldElement[] SECP224K1_AFFINE_ZS = {new SecP224K1FieldElement(ECConstants.ONE)};

    public SecP224K1Curve() {
        super(f1457q);
        this.infinity = new SecP224K1Point(this, null, null);
        this.f1409a = fromBigInteger(ECConstants.ZERO);
        this.f1410b = fromBigInteger(BigInteger.valueOf(5L));
        this.order = new BigInteger(1, Hex.decodeStrict("010000000000000000000000000001DCE8D2EC6184CAF0A971769FB1F7"));
        this.cofactor = BigInteger.valueOf(1L);
        this.coord = 2;
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public ECCurve cloneCurve() {
        return new SecP224K1Curve();
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public ECLookupTable createCacheSafeLookupTable(ECPoint[] eCPointArr, int i2, final int i3) {
        final int[] iArr = new int[i3 * 7 * 2];
        int i4 = 0;
        for (int i5 = 0; i5 < i3; i5++) {
            ECPoint eCPoint = eCPointArr[i2 + i5];
            Nat224.copy(((SecP224K1FieldElement) eCPoint.getRawXCoord()).f1460x, 0, iArr, i4);
            int i6 = i4 + 7;
            Nat224.copy(((SecP224K1FieldElement) eCPoint.getRawYCoord()).f1460x, 0, iArr, i6);
            i4 = i6 + 7;
        }
        return new AbstractECLookupTable() { // from class: org.bouncycastle.math.ec.custom.sec.SecP224K1Curve.1
            private ECPoint createPoint(int[] iArr2, int[] iArr3) {
                return SecP224K1Curve.this.createRawPoint(new SecP224K1FieldElement(iArr2), new SecP224K1FieldElement(iArr3), SecP224K1Curve.SECP224K1_AFFINE_ZS);
            }

            @Override // org.bouncycastle.math.ec.ECLookupTable
            public int getSize() {
                return i3;
            }

            @Override // org.bouncycastle.math.ec.ECLookupTable
            public ECPoint lookup(int i7) {
                int[] create = Nat224.create();
                int[] create2 = Nat224.create();
                int i8 = 0;
                for (int i9 = 0; i9 < i3; i9++) {
                    int i10 = ((i9 ^ i7) - 1) >> 31;
                    for (int i11 = 0; i11 < 7; i11++) {
                        int i12 = create[i11];
                        int[] iArr2 = iArr;
                        create[i11] = i12 ^ (iArr2[i8 + i11] & i10);
                        create2[i11] = create2[i11] ^ (iArr2[(i8 + 7) + i11] & i10);
                    }
                    i8 += 14;
                }
                return createPoint(create, create2);
            }

            @Override // org.bouncycastle.math.ec.AbstractECLookupTable, org.bouncycastle.math.ec.ECLookupTable
            public ECPoint lookupVar(int i7) {
                int[] create = Nat224.create();
                int[] create2 = Nat224.create();
                int i8 = 0;
                for (int i9 = 0; i9 < i3; i9++) {
                    int i10 = ((i9 ^ i7) - 1) >> 31;
                    for (int i11 = 0; i11 < 7; i11++) {
                        int i12 = create[i11];
                        int[] iArr2 = iArr;
                        create[i11] = i12 ^ (iArr2[i8 + i11] & i10);
                        create2[i11] = create2[i11] ^ (iArr2[(i8 + 7) + i11] & i10);
                    }
                    i8 += 14;
                }
                return createPoint(create, create2);
            }
        };
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        return new SecP224K1Point(this, eCFieldElement, eCFieldElement2);
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public ECFieldElement fromBigInteger(BigInteger bigInteger) {
        return new SecP224K1FieldElement(bigInteger);
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public int getFieldSize() {
        return f1457q.bitLength();
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public ECPoint getInfinity() {
        return this.infinity;
    }

    public BigInteger getQ() {
        return f1457q;
    }

    @Override // org.bouncycastle.math.ec.ECCurve.AbstractFp, org.bouncycastle.math.ec.ECCurve
    public ECFieldElement randomFieldElement(SecureRandom secureRandom) {
        int[] create = Nat224.create();
        SecP224K1Field.random(secureRandom, create);
        return new SecP224K1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECCurve.AbstractFp, org.bouncycastle.math.ec.ECCurve
    public ECFieldElement randomFieldElementMult(SecureRandom secureRandom) {
        int[] create = Nat224.create();
        SecP224K1Field.randomMult(secureRandom, create);
        return new SecP224K1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public boolean supportsCoordinateSystem(int i2) {
        return i2 == 2;
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] eCFieldElementArr) {
        return new SecP224K1Point(this, eCFieldElement, eCFieldElement2, eCFieldElementArr);
    }
}
