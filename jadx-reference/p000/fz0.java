package p000;

import java.math.BigInteger;
import p000.AbstractC1316ux;

/* loaded from: classes2.dex */
public class fz0 extends AbstractC1316ux.a1 {
    private static final AbstractC1330va[] SECT571R1_AFFINE_ZS = {new cz0(InterfaceC1315uw.ONE)};
    private static final int SECT571R1_DEFAULT_COORDS = 6;
    static final cz0 SecT571R1_B;
    static final cz0 SecT571R1_B_SQRT;
    protected gz0 infinity;

    /* renamed from: fz0$a0 */
    public class C0515a0 extends AbstractC0484f6 {
        final /* synthetic */ int val$len;
        final /* synthetic */ long[] val$table;

        public C0515a0(int i, long[] jArr) {
            this.val$len = i;
            this.val$table = jArr;
        }

        private AbstractC1341vl createPoint(long[] jArr, long[] jArr2) {
            return fz0.this.createRawPoint(new cz0(jArr), new cz0(jArr2), fz0.SECT571R1_AFFINE_ZS);
        }

        @Override // p000.AbstractC0484f6, p000.InterfaceC1334ve
        public int getSize() {
            return this.val$len;
        }

        @Override // p000.AbstractC0484f6, p000.InterfaceC1334ve
        public AbstractC1341vl lookup(int i) {
            long[] jArrCreate64 = xh0.create64();
            long[] jArrCreate642 = xh0.create64();
            int i2 = 0;
            for (int i3 = 0; i3 < this.val$len; i3++) {
                long j = ((i3 ^ i) - 1) >> 31;
                for (int i4 = 0; i4 < 9; i4++) {
                    long j2 = jArrCreate64[i4];
                    long[] jArr = this.val$table;
                    jArrCreate64[i4] = j2 ^ (jArr[i2 + i4] & j);
                    jArrCreate642[i4] = jArrCreate642[i4] ^ (jArr[(i2 + 9) + i4] & j);
                }
                i2 += 18;
            }
            return createPoint(jArrCreate64, jArrCreate642);
        }

        @Override // p000.AbstractC0484f6, p000.InterfaceC1334ve
        public AbstractC1341vl lookupVar(int i) {
            long[] jArrCreate64 = xh0.create64();
            long[] jArrCreate642 = xh0.create64();
            int i2 = i * 18;
            for (int i3 = 0; i3 < 9; i3++) {
                long[] jArr = this.val$table;
                jArrCreate64[i3] = jArr[i2 + i3];
                jArrCreate642[i3] = jArr[9 + i2 + i3];
            }
            return createPoint(jArrCreate64, jArrCreate642);
        }
    }

    static {
        cz0 cz0Var = new cz0(new BigInteger(1, c40.decodeStrict("02F40E7E2221F295DE297117B7F3D62F5C6A97FFCB8CEFF1CD6BA8CE4A9A18AD84FFABBD8EFA59332BE7AD6756A66E294AFD185A78FF12AA520E4DE739BACA0C7FFEFF7F2955727A")));
        SecT571R1_B = cz0Var;
        SecT571R1_B_SQRT = (cz0) cz0Var.sqrt();
    }

    public fz0() {
        super(571, 2, 5, 10);
        this.infinity = new gz0(this, null, null);
        this.f60524a = fromBigInteger(BigInteger.valueOf(1L));
        this.f60525b = SecT571R1_B;
        this.order = new BigInteger(1, c40.decodeStrict("03FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFE661CE18FF55987308059B186823851EC7DD9CA1161DE93D5174D66E8382E9BB2FE84E47"));
        this.cofactor = BigInteger.valueOf(2L);
        this.coord = 6;
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1316ux cloneCurve() {
        return new fz0();
    }

    @Override // p000.AbstractC1316ux
    public InterfaceC1334ve createCacheSafeLookupTable(AbstractC1341vl[] abstractC1341vlArr, int i, int i2) {
        long[] jArr = new long[i2 * 18];
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            AbstractC1341vl abstractC1341vl = abstractC1341vlArr[i + i4];
            xh0.copy64(((cz0) abstractC1341vl.getRawXCoord()).f55543x, 0, jArr, i3);
            xh0.copy64(((cz0) abstractC1341vl.getRawYCoord()).f55543x, 0, jArr, i3 + 9);
            i3 += 18;
        }
        return new C0515a0(i2, jArr);
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1341vl createRawPoint(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
        return new gz0(this, abstractC1330va, abstractC1330va2);
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1330va fromBigInteger(BigInteger bigInteger) {
        return new cz0(bigInteger);
    }

    @Override // p000.AbstractC1316ux
    public int getFieldSize() {
        return 571;
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1341vl getInfinity() {
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

    @Override // p000.AbstractC1316ux.a1
    public boolean isKoblitz() {
        return false;
    }

    public boolean isTrinomial() {
        return false;
    }

    @Override // p000.AbstractC1316ux
    public boolean supportsCoordinateSystem(int i) {
        return i == 6;
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1341vl createRawPoint(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va[] abstractC1330vaArr) {
        return new gz0(this, abstractC1330va, abstractC1330va2, abstractC1330vaArr);
    }
}
