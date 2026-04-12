package p000;

import java.math.BigInteger;
import java.security.SecureRandom;
import p000.AbstractC1316ux;

/* loaded from: classes2.dex */
public class bx0 extends AbstractC1316ux.a2 {
    private static final int SECP521R1_DEFAULT_COORDS = 2;
    protected ex0 infinity;

    /* renamed from: q */
    public static final BigInteger f46011q = dx0.f55889Q;
    private static final AbstractC1330va[] SECP521R1_AFFINE_ZS = {new dx0(InterfaceC1315uw.ONE)};

    /* renamed from: bx0$a0 */
    public class C0151a0 extends AbstractC0484f6 {
        final /* synthetic */ int val$len;
        final /* synthetic */ int[] val$table;

        public C0151a0(int i, int[] iArr) {
            this.val$len = i;
            this.val$table = iArr;
        }

        private AbstractC1341vl createPoint(int[] iArr, int[] iArr2) {
            return bx0.this.createRawPoint(new dx0(iArr), new dx0(iArr2), bx0.SECP521R1_AFFINE_ZS);
        }

        @Override // p000.AbstractC0484f6, p000.InterfaceC1334ve
        public int getSize() {
            return this.val$len;
        }

        @Override // p000.AbstractC0484f6, p000.InterfaceC1334ve
        public AbstractC1341vl lookup(int i) {
            int[] iArrCreate = yh0.create(17);
            int[] iArrCreate2 = yh0.create(17);
            int i2 = 0;
            for (int i3 = 0; i3 < this.val$len; i3++) {
                int i4 = ((i3 ^ i) - 1) >> 31;
                for (int i5 = 0; i5 < 17; i5++) {
                    int i6 = iArrCreate[i5];
                    int[] iArr = this.val$table;
                    iArrCreate[i5] = i6 ^ (iArr[i2 + i5] & i4);
                    iArrCreate2[i5] = iArrCreate2[i5] ^ (iArr[(i2 + 17) + i5] & i4);
                }
                i2 += 34;
            }
            return createPoint(iArrCreate, iArrCreate2);
        }

        @Override // p000.AbstractC0484f6, p000.InterfaceC1334ve
        public AbstractC1341vl lookupVar(int i) {
            int[] iArrCreate = yh0.create(17);
            int[] iArrCreate2 = yh0.create(17);
            int i2 = i * 34;
            for (int i3 = 0; i3 < 17; i3++) {
                int i4 = iArrCreate[i3];
                int[] iArr = this.val$table;
                iArrCreate[i3] = i4 ^ iArr[i2 + i3];
                iArrCreate2[i3] = iArrCreate2[i3] ^ iArr[(i2 + 17) + i3];
            }
            return createPoint(iArrCreate, iArrCreate2);
        }
    }

    public bx0() {
        super(f46011q);
        this.infinity = new ex0(this, null, null);
        this.f60524a = fromBigInteger(new BigInteger(1, c40.decodeStrict("01FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFC")));
        this.f60525b = fromBigInteger(new BigInteger(1, c40.decodeStrict("0051953EB9618E1C9A1F929A21A0B68540EEA2DA725B99B315F3B8B489918EF109E156193951EC7E937B1652C0BD3BB1BF073573DF883D2C34F1EF451FD46B503F00")));
        this.order = new BigInteger(1, c40.decodeStrict("01FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFA51868783BF2F966B7FCC0148F709A5D03BB5C9B8899C47AEBB6FB71E91386409"));
        this.cofactor = BigInteger.valueOf(1L);
        this.coord = 2;
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1316ux cloneCurve() {
        return new bx0();
    }

    @Override // p000.AbstractC1316ux
    public InterfaceC1334ve createCacheSafeLookupTable(AbstractC1341vl[] abstractC1341vlArr, int i, int i2) {
        int[] iArr = new int[i2 * 34];
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            AbstractC1341vl abstractC1341vl = abstractC1341vlArr[i + i4];
            yh0.copy(17, ((dx0) abstractC1341vl.getRawXCoord()).f55890x, 0, iArr, i3);
            yh0.copy(17, ((dx0) abstractC1341vl.getRawYCoord()).f55890x, 0, iArr, i3 + 17);
            i3 += 34;
        }
        return new C0151a0(i2, iArr);
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1341vl createRawPoint(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
        return new ex0(this, abstractC1330va, abstractC1330va2);
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1330va fromBigInteger(BigInteger bigInteger) {
        return new dx0(bigInteger);
    }

    @Override // p000.AbstractC1316ux
    public int getFieldSize() {
        return f46011q.bitLength();
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1341vl getInfinity() {
        return this.infinity;
    }

    public BigInteger getQ() {
        return f46011q;
    }

    @Override // p000.AbstractC1316ux.a2, p000.AbstractC1316ux
    public AbstractC1330va randomFieldElement(SecureRandom secureRandom) {
        int[] iArrCreate = yh0.create(17);
        cx0.random(secureRandom, iArrCreate);
        return new dx0(iArrCreate);
    }

    @Override // p000.AbstractC1316ux.a2, p000.AbstractC1316ux
    public AbstractC1330va randomFieldElementMult(SecureRandom secureRandom) {
        int[] iArrCreate = yh0.create(17);
        cx0.randomMult(secureRandom, iArrCreate);
        return new dx0(iArrCreate);
    }

    @Override // p000.AbstractC1316ux
    public boolean supportsCoordinateSystem(int i) {
        return i == 2;
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1341vl createRawPoint(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va[] abstractC1330vaArr) {
        return new ex0(this, abstractC1330va, abstractC1330va2, abstractC1330vaArr);
    }
}
