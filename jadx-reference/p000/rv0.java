package p000;

import java.math.BigInteger;
import java.security.SecureRandom;
import p000.AbstractC1316ux;

/* loaded from: classes2.dex */
public class rv0 extends AbstractC1316ux.a2 {
    private static final int SECP160R1_DEFAULT_COORDS = 2;
    protected uv0 infinity;

    /* renamed from: q */
    public static final BigInteger f59825q = tv0.f60285Q;
    private static final AbstractC1330va[] SECP160R1_AFFINE_ZS = {new tv0(InterfaceC1315uw.ONE)};

    /* renamed from: rv0$a0 */
    public class C1195a0 extends AbstractC0484f6 {
        final /* synthetic */ int val$len;
        final /* synthetic */ int[] val$table;

        public C1195a0(int i, int[] iArr) {
            this.val$len = i;
            this.val$table = iArr;
        }

        private AbstractC1341vl createPoint(int[] iArr, int[] iArr2) {
            return rv0.this.createRawPoint(new tv0(iArr), new tv0(iArr2), rv0.SECP160R1_AFFINE_ZS);
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

    public rv0() {
        super(f59825q);
        this.infinity = new uv0(this, null, null);
        this.f60524a = fromBigInteger(new BigInteger(1, c40.decodeStrict("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF7FFFFFFC")));
        this.f60525b = fromBigInteger(new BigInteger(1, c40.decodeStrict("1C97BEFC54BD7A8B65ACF89F81D4D4ADC565FA45")));
        this.order = new BigInteger(1, c40.decodeStrict("0100000000000000000001F4C8F927AED3CA752257"));
        this.cofactor = BigInteger.valueOf(1L);
        this.coord = 2;
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1316ux cloneCurve() {
        return new rv0();
    }

    @Override // p000.AbstractC1316ux
    public InterfaceC1334ve createCacheSafeLookupTable(AbstractC1341vl[] abstractC1341vlArr, int i, int i2) {
        int[] iArr = new int[i2 * 10];
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            AbstractC1341vl abstractC1341vl = abstractC1341vlArr[i + i4];
            ph0.copy(((tv0) abstractC1341vl.getRawXCoord()).f60286x, 0, iArr, i3);
            ph0.copy(((tv0) abstractC1341vl.getRawYCoord()).f60286x, 0, iArr, i3 + 5);
            i3 += 10;
        }
        return new C1195a0(i2, iArr);
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1341vl createRawPoint(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
        return new uv0(this, abstractC1330va, abstractC1330va2);
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1330va fromBigInteger(BigInteger bigInteger) {
        return new tv0(bigInteger);
    }

    @Override // p000.AbstractC1316ux
    public int getFieldSize() {
        return f59825q.bitLength();
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1341vl getInfinity() {
        return this.infinity;
    }

    public BigInteger getQ() {
        return f59825q;
    }

    @Override // p000.AbstractC1316ux.a2, p000.AbstractC1316ux
    public AbstractC1330va randomFieldElement(SecureRandom secureRandom) {
        int[] iArrCreate = ph0.create();
        sv0.random(secureRandom, iArrCreate);
        return new tv0(iArrCreate);
    }

    @Override // p000.AbstractC1316ux.a2, p000.AbstractC1316ux
    public AbstractC1330va randomFieldElementMult(SecureRandom secureRandom) {
        int[] iArrCreate = ph0.create();
        sv0.randomMult(secureRandom, iArrCreate);
        return new tv0(iArrCreate);
    }

    @Override // p000.AbstractC1316ux
    public boolean supportsCoordinateSystem(int i) {
        return i == 2;
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1341vl createRawPoint(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va[] abstractC1330vaArr) {
        return new uv0(this, abstractC1330va, abstractC1330va2, abstractC1330vaArr);
    }
}
