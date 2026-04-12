package p000;

import java.math.BigInteger;
import p000.AbstractC1316ux;

/* loaded from: classes2.dex */
public class by0 extends AbstractC1316ux.a1 {
    private static final AbstractC1330va[] SECT193R1_AFFINE_ZS = {new ay0(InterfaceC1315uw.ONE)};
    private static final int SECT193R1_DEFAULT_COORDS = 6;
    protected cy0 infinity;

    /* renamed from: by0$a0 */
    public class C0152a0 extends AbstractC0484f6 {
        final /* synthetic */ int val$len;
        final /* synthetic */ long[] val$table;

        public C0152a0(int i, long[] jArr) {
            this.val$len = i;
            this.val$table = jArr;
        }

        private AbstractC1341vl createPoint(long[] jArr, long[] jArr2) {
            return by0.this.createRawPoint(new ay0(jArr), new ay0(jArr2), by0.SECT193R1_AFFINE_ZS);
        }

        @Override // p000.AbstractC0484f6, p000.InterfaceC1334ve
        public int getSize() {
            return this.val$len;
        }

        @Override // p000.AbstractC0484f6, p000.InterfaceC1334ve
        public AbstractC1341vl lookup(int i) {
            long[] jArrCreate64 = sh0.create64();
            long[] jArrCreate642 = sh0.create64();
            int i2 = 0;
            for (int i3 = 0; i3 < this.val$len; i3++) {
                long j = ((i3 ^ i) - 1) >> 31;
                for (int i4 = 0; i4 < 4; i4++) {
                    long j2 = jArrCreate64[i4];
                    long[] jArr = this.val$table;
                    jArrCreate64[i4] = j2 ^ (jArr[i2 + i4] & j);
                    jArrCreate642[i4] = jArrCreate642[i4] ^ (jArr[(i2 + 4) + i4] & j);
                }
                i2 += 8;
            }
            return createPoint(jArrCreate64, jArrCreate642);
        }

        @Override // p000.AbstractC0484f6, p000.InterfaceC1334ve
        public AbstractC1341vl lookupVar(int i) {
            long[] jArrCreate64 = sh0.create64();
            long[] jArrCreate642 = sh0.create64();
            int i2 = i * 8;
            for (int i3 = 0; i3 < 4; i3++) {
                long[] jArr = this.val$table;
                jArrCreate64[i3] = jArr[i2 + i3];
                jArrCreate642[i3] = jArr[4 + i2 + i3];
            }
            return createPoint(jArrCreate64, jArrCreate642);
        }
    }

    public by0() {
        super(193, 15, 0, 0);
        this.infinity = new cy0(this, null, null);
        this.f60524a = fromBigInteger(new BigInteger(1, c40.decodeStrict("0017858FEB7A98975169E171F77B4087DE098AC8A911DF7B01")));
        this.f60525b = fromBigInteger(new BigInteger(1, c40.decodeStrict("00FDFB49BFE6C3A89FACADAA7A1E5BBC7CC1C2E5D831478814")));
        this.order = new BigInteger(1, c40.decodeStrict("01000000000000000000000000C7F34A778F443ACC920EBA49"));
        this.cofactor = BigInteger.valueOf(2L);
        this.coord = 6;
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1316ux cloneCurve() {
        return new by0();
    }

    @Override // p000.AbstractC1316ux
    public InterfaceC1334ve createCacheSafeLookupTable(AbstractC1341vl[] abstractC1341vlArr, int i, int i2) {
        long[] jArr = new long[i2 * 8];
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            AbstractC1341vl abstractC1341vl = abstractC1341vlArr[i + i4];
            sh0.copy64(((ay0) abstractC1341vl.getRawXCoord()).f45655x, 0, jArr, i3);
            sh0.copy64(((ay0) abstractC1341vl.getRawYCoord()).f45655x, 0, jArr, i3 + 4);
            i3 += 8;
        }
        return new C0152a0(i2, jArr);
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1341vl createRawPoint(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
        return new cy0(this, abstractC1330va, abstractC1330va2);
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1330va fromBigInteger(BigInteger bigInteger) {
        return new ay0(bigInteger);
    }

    @Override // p000.AbstractC1316ux
    public int getFieldSize() {
        return 193;
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1341vl getInfinity() {
        return this.infinity;
    }

    public int getK1() {
        return 15;
    }

    public int getK2() {
        return 0;
    }

    public int getK3() {
        return 0;
    }

    public int getM() {
        return 193;
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
        return new cy0(this, abstractC1330va, abstractC1330va2, abstractC1330vaArr);
    }
}
