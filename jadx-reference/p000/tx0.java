package p000;

import java.math.BigInteger;
import p000.AbstractC1316ux;

/* loaded from: classes2.dex */
public class tx0 extends AbstractC1316ux.a1 {
    private static final AbstractC1330va[] SECT163K1_AFFINE_ZS = {new sx0(InterfaceC1315uw.ONE)};
    private static final int SECT163K1_DEFAULT_COORDS = 6;
    protected ux0 infinity;

    /* renamed from: tx0$a0 */
    public class C1278a0 extends AbstractC0484f6 {
        final /* synthetic */ int val$len;
        final /* synthetic */ long[] val$table;

        public C1278a0(int i, long[] jArr) {
            this.val$len = i;
            this.val$table = jArr;
        }

        private AbstractC1341vl createPoint(long[] jArr, long[] jArr2) {
            return tx0.this.createRawPoint(new sx0(jArr), new sx0(jArr2), tx0.SECT163K1_AFFINE_ZS);
        }

        @Override // p000.AbstractC0484f6, p000.InterfaceC1334ve
        public int getSize() {
            return this.val$len;
        }

        @Override // p000.AbstractC0484f6, p000.InterfaceC1334ve
        public AbstractC1341vl lookup(int i) {
            long[] jArrCreate64 = qh0.create64();
            long[] jArrCreate642 = qh0.create64();
            int i2 = 0;
            for (int i3 = 0; i3 < this.val$len; i3++) {
                long j = ((i3 ^ i) - 1) >> 31;
                for (int i4 = 0; i4 < 3; i4++) {
                    long j2 = jArrCreate64[i4];
                    long[] jArr = this.val$table;
                    jArrCreate64[i4] = j2 ^ (jArr[i2 + i4] & j);
                    jArrCreate642[i4] = jArrCreate642[i4] ^ (jArr[(i2 + 3) + i4] & j);
                }
                i2 += 6;
            }
            return createPoint(jArrCreate64, jArrCreate642);
        }

        @Override // p000.AbstractC0484f6, p000.InterfaceC1334ve
        public AbstractC1341vl lookupVar(int i) {
            long[] jArrCreate64 = qh0.create64();
            long[] jArrCreate642 = qh0.create64();
            int i2 = i * 6;
            for (int i3 = 0; i3 < 3; i3++) {
                long[] jArr = this.val$table;
                jArrCreate64[i3] = jArr[i2 + i3];
                jArrCreate642[i3] = jArr[3 + i2 + i3];
            }
            return createPoint(jArrCreate64, jArrCreate642);
        }
    }

    public tx0() {
        super(163, 3, 6, 7);
        this.infinity = new ux0(this, null, null);
        AbstractC1330va abstractC1330vaFromBigInteger = fromBigInteger(BigInteger.valueOf(1L));
        this.f60524a = abstractC1330vaFromBigInteger;
        this.f60525b = abstractC1330vaFromBigInteger;
        this.order = new BigInteger(1, c40.decodeStrict("04000000000000000000020108A2E0CC0D99F8A5EF"));
        this.cofactor = BigInteger.valueOf(2L);
        this.coord = 6;
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1316ux cloneCurve() {
        return new tx0();
    }

    @Override // p000.AbstractC1316ux
    public InterfaceC1334ve createCacheSafeLookupTable(AbstractC1341vl[] abstractC1341vlArr, int i, int i2) {
        long[] jArr = new long[i2 * 6];
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            AbstractC1341vl abstractC1341vl = abstractC1341vlArr[i + i4];
            qh0.copy64(((sx0) abstractC1341vl.getRawXCoord()).f60103x, 0, jArr, i3);
            qh0.copy64(((sx0) abstractC1341vl.getRawYCoord()).f60103x, 0, jArr, i3 + 3);
            i3 += 6;
        }
        return new C1278a0(i2, jArr);
    }

    @Override // p000.AbstractC1316ux
    public InterfaceC1335vf createDefaultMultiplier() {
        return new de1();
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1341vl createRawPoint(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
        return new ux0(this, abstractC1330va, abstractC1330va2);
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1330va fromBigInteger(BigInteger bigInteger) {
        return new sx0(bigInteger);
    }

    @Override // p000.AbstractC1316ux
    public int getFieldSize() {
        return 163;
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1341vl getInfinity() {
        return this.infinity;
    }

    public int getK1() {
        return 3;
    }

    public int getK2() {
        return 6;
    }

    public int getK3() {
        return 7;
    }

    public int getM() {
        return 163;
    }

    @Override // p000.AbstractC1316ux.a1
    public boolean isKoblitz() {
        return true;
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
        return new ux0(this, abstractC1330va, abstractC1330va2, abstractC1330vaArr);
    }
}
