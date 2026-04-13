package org.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.math.ec.AbstractECLookupTable;
import org.bouncycastle.math.ec.ECConstants;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.ec.ECLookupTable;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.raw.Nat;
import org.bouncycastle.util.encoders.Hex;

/* loaded from: classes.dex */
public class SecP521R1Curve extends ECCurve.AbstractFp {
    private static final int SECP521R1_DEFAULT_COORDS = 2;
    protected SecP521R1Point infinity;

    /* renamed from: q */
    public static final BigInteger f1480q = SecP521R1FieldElement.f1482Q;
    private static final ECFieldElement[] SECP521R1_AFFINE_ZS = {new SecP521R1FieldElement(ECConstants.ONE)};

    public SecP521R1Curve() {
        super(f1480q);
        this.infinity = new SecP521R1Point(this, null, null);
        this.f1409a = fromBigInteger(new BigInteger(1, Hex.decodeStrict("01FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFC")));
        this.f1410b = fromBigInteger(new BigInteger(1, Hex.decodeStrict("0051953EB9618E1C9A1F929A21A0B68540EEA2DA725B99B315F3B8B489918EF109E156193951EC7E937B1652C0BD3BB1BF073573DF883D2C34F1EF451FD46B503F00")));
        this.order = new BigInteger(1, Hex.decodeStrict("01FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFA51868783BF2F966B7FCC0148F709A5D03BB5C9B8899C47AEBB6FB71E91386409"));
        this.cofactor = BigInteger.valueOf(1L);
        this.coord = 2;
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public ECCurve cloneCurve() {
        return new SecP521R1Curve();
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public ECLookupTable createCacheSafeLookupTable(ECPoint[] eCPointArr, int i2, final int i3) {
        final int[] iArr = new int[i3 * 17 * 2];
        int i4 = 0;
        for (int i5 = 0; i5 < i3; i5++) {
            ECPoint eCPoint = eCPointArr[i2 + i5];
            Nat.copy(17, ((SecP521R1FieldElement) eCPoint.getRawXCoord()).f1483x, 0, iArr, i4);
            int i6 = i4 + 17;
            Nat.copy(17, ((SecP521R1FieldElement) eCPoint.getRawYCoord()).f1483x, 0, iArr, i6);
            i4 = i6 + 17;
        }
        return new AbstractECLookupTable() { // from class: org.bouncycastle.math.ec.custom.sec.SecP521R1Curve.1
            private ECPoint createPoint(int[] iArr2, int[] iArr3) {
                return SecP521R1Curve.this.createRawPoint(new SecP521R1FieldElement(iArr2), new SecP521R1FieldElement(iArr3), SecP521R1Curve.SECP521R1_AFFINE_ZS);
            }

            @Override // org.bouncycastle.math.ec.ECLookupTable
            public int getSize() {
                return i3;
            }

            @Override // org.bouncycastle.math.ec.ECLookupTable
            public ECPoint lookup(int i7) {
                int[] create = Nat.create(17);
                int[] create2 = Nat.create(17);
                int i8 = 0;
                for (int i9 = 0; i9 < i3; i9++) {
                    int i10 = ((i9 ^ i7) - 1) >> 31;
                    for (int i11 = 0; i11 < 17; i11++) {
                        int i12 = create[i11];
                        int[] iArr2 = iArr;
                        create[i11] = i12 ^ (iArr2[i8 + i11] & i10);
                        create2[i11] = create2[i11] ^ (iArr2[(i8 + 17) + i11] & i10);
                    }
                    i8 += 34;
                }
                return createPoint(create, create2);
            }

            @Override // org.bouncycastle.math.ec.AbstractECLookupTable, org.bouncycastle.math.ec.ECLookupTable
            public ECPoint lookupVar(int i7) {
                int[] create = Nat.create(17);
                int[] create2 = Nat.create(17);
                int i8 = i7 * 17 * 2;
                for (int i9 = 0; i9 < 17; i9++) {
                    int i10 = create[i9];
                    int[] iArr2 = iArr;
                    create[i9] = i10 ^ iArr2[i8 + i9];
                    create2[i9] = create2[i9] ^ iArr2[(i8 + 17) + i9];
                }
                return createPoint(create, create2);
            }
        };
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        return new SecP521R1Point(this, eCFieldElement, eCFieldElement2);
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public ECFieldElement fromBigInteger(BigInteger bigInteger) {
        return new SecP521R1FieldElement(bigInteger);
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public int getFieldSize() {
        return f1480q.bitLength();
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public ECPoint getInfinity() {
        return this.infinity;
    }

    public BigInteger getQ() {
        return f1480q;
    }

    @Override // org.bouncycastle.math.ec.ECCurve.AbstractFp, org.bouncycastle.math.ec.ECCurve
    public ECFieldElement randomFieldElement(SecureRandom secureRandom) {
        int[] create = Nat.create(17);
        SecP521R1Field.random(secureRandom, create);
        return new SecP521R1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECCurve.AbstractFp, org.bouncycastle.math.ec.ECCurve
    public ECFieldElement randomFieldElementMult(SecureRandom secureRandom) {
        int[] create = Nat.create(17);
        SecP521R1Field.randomMult(secureRandom, create);
        return new SecP521R1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public boolean supportsCoordinateSystem(int i2) {
        return i2 == 2;
    }

    @Override // org.bouncycastle.math.ec.ECCurve
    public ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] eCFieldElementArr) {
        return new SecP521R1Point(this, eCFieldElement, eCFieldElement2, eCFieldElementArr);
    }
}
