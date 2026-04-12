package p000;

import java.math.BigInteger;
import java.security.SecureRandom;
import p000.AbstractC1316ux;

/* loaded from: classes2.dex */
public class vv0 extends AbstractC1316ux.a2 {
    private static final int SECP160R2_DEFAULT_COORDS = 2;
    protected yv0 infinity;

    /* renamed from: q */
    public static final BigInteger f60714q = xv0.f61196Q;
    private static final AbstractC1330va[] SECP160R2_AFFINE_ZS = {new xv0(InterfaceC1315uw.ONE)};

    /* renamed from: vv0$a0 */
    public class C1352a0 extends AbstractC0484f6 {
        final /* synthetic */ int val$len;
        final /* synthetic */ int[] val$table;

        public C1352a0(int i, int[] iArr) {
            this.val$len = i;
            this.val$table = iArr;
        }

        private AbstractC1341vl createPoint(int[] iArr, int[] iArr2) {
            return vv0.this.createRawPoint(new xv0(iArr), new xv0(iArr2), vv0.SECP160R2_AFFINE_ZS);
        }

        @Override // p000.AbstractC0484f6, p000.InterfaceC1334ve
        public int getSize() {
            return this.val$len;
        }

        @Override // p000.AbstractC0484f6, p000.InterfaceC1334ve
        public AbstractC1341vl lookup(int i) {
            int[] iArrCreate = ph0.create();
            int[] iArrCreate2 = ph0.create();
            int i2 = 0;
            for (int i3 = 0; i3 < this.val$len; i3++) {
                int i4 = ((i3 ^ i) - 1) >> 31;
                for (int i5 = 0; i5 < 5; i5++) {
                    int i6 = iArrCreate[i5];
                    int[] iArr = this.val$table;
                    iArrCreate[i5] = i6 ^ (iArr[i2 + i5] & i4);
                    iArrCreate2[i5] = iArrCreate2[i5] ^ (iArr[(i2 + 5) + i5] & i4);
                }
                i2 += 10;
            }
            return createPoint(iArrCreate, iArrCreate2);
        }

        @Override // p000.AbstractC0484f6, p000.InterfaceC1334ve
        public AbstractC1341vl lookupVar(int i) {
            int[] iArrCreate = ph0.create();
            int[] iArrCreate2 = ph0.create();
            int i2 = i * 10;
            for (int i3 = 0; i3 < 5; i3++) {
                int[] iArr = this.val$table;
                iArrCreate[i3] = iArr[i2 + i3];
                iArrCreate2[i3] = iArr[5 + i2 + i3];
            }
            return createPoint(iArrCreate, iArrCreate2);
        }
    }

    public vv0() {
        super(f60714q);
        this.infinity = new yv0(this, null, null);
        this.f60524a = fromBigInteger(new BigInteger(1, c40.decodeStrict("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFAC70")));
        this.f60525b = fromBigInteger(new BigInteger(1, c40.decodeStrict("B4E134D3FB59EB8BAB57274904664D5AF50388BA")));
        this.order = new BigInteger(1, c40.decodeStrict("0100000000000000000000351EE786A818F3A1A16B"));
        this.cofactor = BigInteger.valueOf(1L);
        this.coord = 2;
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1316ux cloneCurve() {
        return new vv0();
    }

    @Override // p000.AbstractC1316ux
    public InterfaceC1334ve createCacheSafeLookupTable(AbstractC1341vl[] abstractC1341vlArr, int i, int i2) {
        int[] iArr = new int[i2 * 10];
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            AbstractC1341vl abstractC1341vl = abstractC1341vlArr[i + i4];
            ph0.copy(((xv0) abstractC1341vl.getRawXCoord()).f61197x, 0, iArr, i3);
            ph0.copy(((xv0) abstractC1341vl.getRawYCoord()).f61197x, 0, iArr, i3 + 5);
            i3 += 10;
        }
        return new C1352a0(i2, iArr);
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1341vl createRawPoint(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
        return new yv0(this, abstractC1330va, abstractC1330va2);
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1330va fromBigInteger(BigInteger bigInteger) {
        return new xv0(bigInteger);
    }

    @Override // p000.AbstractC1316ux
    public int getFieldSize() {
        return f60714q.bitLength();
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1341vl getInfinity() {
        return this.infinity;
    }

    public BigInteger getQ() {
        return f60714q;
    }

    @Override // p000.AbstractC1316ux.a2, p000.AbstractC1316ux
    public AbstractC1330va randomFieldElement(SecureRandom secureRandom) {
        int[] iArrCreate = ph0.create();
        wv0.random(secureRandom, iArrCreate);
        return new xv0(iArrCreate);
    }

    @Override // p000.AbstractC1316ux.a2, p000.AbstractC1316ux
    public AbstractC1330va randomFieldElementMult(SecureRandom secureRandom) {
        int[] iArrCreate = ph0.create();
        wv0.randomMult(secureRandom, iArrCreate);
        return new xv0(iArrCreate);
    }

    @Override // p000.AbstractC1316ux
    public boolean supportsCoordinateSystem(int i) {
        return i == 2;
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1341vl createRawPoint(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va[] abstractC1330vaArr) {
        return new yv0(this, abstractC1330va, abstractC1330va2, abstractC1330vaArr);
    }
}
