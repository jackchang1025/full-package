package p000;

import java.math.BigInteger;
import p000.AbstractC1316ux;

/* loaded from: classes2.dex */
public class hx0 extends AbstractC1316ux.a1 {
    private static final AbstractC1330va[] SECT113R1_AFFINE_ZS = {new gx0(InterfaceC1315uw.ONE)};
    private static final int SECT113R1_DEFAULT_COORDS = 6;
    protected ix0 infinity;

    /* renamed from: hx0$a0 */
    public class C0599a0 extends AbstractC0484f6 {
        final /* synthetic */ int val$len;
        final /* synthetic */ long[] val$table;

        public C0599a0(int i, long[] jArr) {
            this.val$len = i;
            this.val$table = jArr;
        }

        private AbstractC1341vl createPoint(long[] jArr, long[] jArr2) {
            return hx0.this.createRawPoint(new gx0(jArr), new gx0(jArr2), hx0.SECT113R1_AFFINE_ZS);
        }

        @Override // p000.AbstractC0484f6, p000.InterfaceC1334ve
        public int getSize() {
            return this.val$len;
        }

        @Override // p000.AbstractC0484f6, p000.InterfaceC1334ve
        public AbstractC1341vl lookup(int i) {
            long[] jArrCreate64 = oh0.create64();
            long[] jArrCreate642 = oh0.create64();
            int i2 = 0;
            for (int i3 = 0; i3 < this.val$len; i3++) {
                long j = ((i3 ^ i) - 1) >> 31;
                for (int i4 = 0; i4 < 2; i4++) {
                    long j2 = jArrCreate64[i4];
                    long[] jArr = this.val$table;
                    jArrCreate64[i4] = j2 ^ (jArr[i2 + i4] & j);
                    jArrCreate642[i4] = jArrCreate642[i4] ^ (jArr[(i2 + 2) + i4] & j);
                }
                i2 += 4;
            }
            return createPoint(jArrCreate64, jArrCreate642);
        }

        @Override // p000.AbstractC0484f6, p000.InterfaceC1334ve
        public AbstractC1341vl lookupVar(int i) {
            long[] jArrCreate64 = oh0.create64();
            long[] jArrCreate642 = oh0.create64();
            int i2 = i * 4;
            for (int i3 = 0; i3 < 2; i3++) {
                long[] jArr = this.val$table;
                jArrCreate64[i3] = jArr[i2 + i3];
                jArrCreate642[i3] = jArr[2 + i2 + i3];
            }
            return createPoint(jArrCreate64, jArrCreate642);
        }
    }

    public hx0() {
        super(113, 9, 0, 0);
        this.infinity = new ix0(this, null, null);
        this.f60524a = fromBigInteger(new BigInteger(1, c40.decodeStrict("003088250CA6E7C7FE649CE85820F7")));
        this.f60525b = fromBigInteger(new BigInteger(1, c40.decodeStrict("00E8BEE4D3E2260744188BE0E9C723")));
        this.order = new BigInteger(1, c40.decodeStrict("0100000000000000D9CCEC8A39E56F"));
        this.cofactor = BigInteger.valueOf(2L);
        this.coord = 6;
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1316ux cloneCurve() {
        return new hx0();
    }

    @Override // p000.AbstractC1316ux
    public InterfaceC1334ve createCacheSafeLookupTable(AbstractC1341vl[] abstractC1341vlArr, int i, int i2) {
        long[] jArr = new long[i2 * 4];
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            AbstractC1341vl abstractC1341vl = abstractC1341vlArr[i + i4];
            oh0.copy64(((gx0) abstractC1341vl.getRawXCoord()).f56588x, 0, jArr, i3);
            oh0.copy64(((gx0) abstractC1341vl.getRawYCoord()).f56588x, 0, jArr, i3 + 2);
            i3 += 4;
        }
        return new C0599a0(i2, jArr);
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1341vl createRawPoint(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
        return new ix0(this, abstractC1330va, abstractC1330va2);
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1330va fromBigInteger(BigInteger bigInteger) {
        return new gx0(bigInteger);
    }

    @Override // p000.AbstractC1316ux
    public int getFieldSize() {
        return 113;
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1341vl getInfinity() {
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

    @Override // p000.AbstractC1316ux.a1
    public boolean isKoblitz() {
        return false;
    }

    public boolean isTrinomial() {
        return true;
    }

    @Override // p000.AbstractC1316ux
    public boolean supportsCoordinateSystem(int i) {
        return i == 6;
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1341vl createRawPoint(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va[] abstractC1330vaArr) {
        return new ix0(this, abstractC1330va, abstractC1330va2, abstractC1330vaArr);
    }
}
