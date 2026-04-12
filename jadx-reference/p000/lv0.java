package p000;

import java.math.BigInteger;
import java.security.SecureRandom;
import p000.AbstractC1316ux;

/* loaded from: classes2.dex */
public class lv0 extends AbstractC1316ux.a2 {
    private static final int SECP128R1_DEFAULT_COORDS = 2;
    protected ov0 infinity;

    /* renamed from: q */
    public static final BigInteger f58201q = nv0.f58697Q;
    private static final AbstractC1330va[] SECP128R1_AFFINE_ZS = {new nv0(InterfaceC1315uw.ONE)};

    /* renamed from: lv0$a0 */
    public class C0837a0 extends AbstractC0484f6 {
        final /* synthetic */ int val$len;
        final /* synthetic */ int[] val$table;

        public C0837a0(int i, int[] iArr) {
            this.val$len = i;
            this.val$table = iArr;
        }

        private AbstractC1341vl createPoint(int[] iArr, int[] iArr2) {
            return lv0.this.createRawPoint(new nv0(iArr), new nv0(iArr2), lv0.SECP128R1_AFFINE_ZS);
        }

        @Override // p000.AbstractC0484f6, p000.InterfaceC1334ve
        public int getSize() {
            return this.val$len;
        }

        @Override // p000.AbstractC0484f6, p000.InterfaceC1334ve
        public AbstractC1341vl lookup(int i) {
            int[] iArrCreate = oh0.create();
            int[] iArrCreate2 = oh0.create();
            int i2 = 0;
            for (int i3 = 0; i3 < this.val$len; i3++) {
                int i4 = ((i3 ^ i) - 1) >> 31;
                for (int i5 = 0; i5 < 4; i5++) {
                    int i6 = iArrCreate[i5];
                    int[] iArr = this.val$table;
                    iArrCreate[i5] = i6 ^ (iArr[i2 + i5] & i4);
                    iArrCreate2[i5] = iArrCreate2[i5] ^ (iArr[(i2 + 4) + i5] & i4);
                }
                i2 += 8;
            }
            return createPoint(iArrCreate, iArrCreate2);
        }

        @Override // p000.AbstractC0484f6, p000.InterfaceC1334ve
        public AbstractC1341vl lookupVar(int i) {
            int[] iArrCreate = oh0.create();
            int[] iArrCreate2 = oh0.create();
            int i2 = i * 8;
            for (int i3 = 0; i3 < 4; i3++) {
                int[] iArr = this.val$table;
                iArrCreate[i3] = iArr[i2 + i3];
                iArrCreate2[i3] = iArr[4 + i2 + i3];
            }
            return createPoint(iArrCreate, iArrCreate2);
        }
    }

    public lv0() {
        super(f58201q);
        this.infinity = new ov0(this, null, null);
        this.f60524a = fromBigInteger(new BigInteger(1, c40.decodeStrict("FFFFFFFDFFFFFFFFFFFFFFFFFFFFFFFC")));
        this.f60525b = fromBigInteger(new BigInteger(1, c40.decodeStrict("E87579C11079F43DD824993C2CEE5ED3")));
        this.order = new BigInteger(1, c40.decodeStrict("FFFFFFFE0000000075A30D1B9038A115"));
        this.cofactor = BigInteger.valueOf(1L);
        this.coord = 2;
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1316ux cloneCurve() {
        return new lv0();
    }

    @Override // p000.AbstractC1316ux
    public InterfaceC1334ve createCacheSafeLookupTable(AbstractC1341vl[] abstractC1341vlArr, int i, int i2) {
        int[] iArr = new int[i2 * 8];
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            AbstractC1341vl abstractC1341vl = abstractC1341vlArr[i + i4];
            oh0.copy(((nv0) abstractC1341vl.getRawXCoord()).f58698x, 0, iArr, i3);
            oh0.copy(((nv0) abstractC1341vl.getRawYCoord()).f58698x, 0, iArr, i3 + 4);
            i3 += 8;
        }
        return new C0837a0(i2, iArr);
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1341vl createRawPoint(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
        return new ov0(this, abstractC1330va, abstractC1330va2);
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1330va fromBigInteger(BigInteger bigInteger) {
        return new nv0(bigInteger);
    }

    @Override // p000.AbstractC1316ux
    public int getFieldSize() {
        return f58201q.bitLength();
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1341vl getInfinity() {
        return this.infinity;
    }

    public BigInteger getQ() {
        return f58201q;
    }

    @Override // p000.AbstractC1316ux.a2, p000.AbstractC1316ux
    public AbstractC1330va randomFieldElement(SecureRandom secureRandom) {
        int[] iArrCreate = oh0.create();
        mv0.random(secureRandom, iArrCreate);
        return new nv0(iArrCreate);
    }

    @Override // p000.AbstractC1316ux.a2, p000.AbstractC1316ux
    public AbstractC1330va randomFieldElementMult(SecureRandom secureRandom) {
        int[] iArrCreate = oh0.create();
        mv0.randomMult(secureRandom, iArrCreate);
        return new nv0(iArrCreate);
    }

    @Override // p000.AbstractC1316ux
    public boolean supportsCoordinateSystem(int i) {
        return i == 2;
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1341vl createRawPoint(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va[] abstractC1330vaArr) {
        return new ov0(this, abstractC1330va, abstractC1330va2, abstractC1330vaArr);
    }
}
