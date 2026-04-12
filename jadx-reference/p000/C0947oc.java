package p000;

import java.math.BigInteger;
import java.security.SecureRandom;
import p000.AbstractC1316ux;

/* renamed from: oc */
/* loaded from: classes2.dex */
public class C0947oc extends AbstractC1316ux.a2 {
    private static final AbstractC1330va[] CURVE25519_AFFINE_ZS;
    private static final int CURVE25519_DEFAULT_COORDS = 4;
    private static final BigInteger C_a;
    private static final BigInteger C_b;

    /* renamed from: q */
    public static final BigInteger f58778q = C0949oe.f58790Q;
    protected C0950of infinity;

    /* renamed from: oc$a0 */
    public class a0 extends AbstractC0484f6 {
        final /* synthetic */ int val$len;
        final /* synthetic */ int[] val$table;

        public a0(int i, int[] iArr) {
            this.val$len = i;
            this.val$table = iArr;
        }

        private AbstractC1341vl createPoint(int[] iArr, int[] iArr2) {
            return C0947oc.this.createRawPoint(new C0949oe(iArr), new C0949oe(iArr2), C0947oc.CURVE25519_AFFINE_ZS);
        }

        @Override // p000.AbstractC0484f6, p000.InterfaceC1334ve
        public int getSize() {
            return this.val$len;
        }

        @Override // p000.AbstractC0484f6, p000.InterfaceC1334ve
        public AbstractC1341vl lookup(int i) {
            int[] iArrCreate = sh0.create();
            int[] iArrCreate2 = sh0.create();
            int i2 = 0;
            for (int i3 = 0; i3 < this.val$len; i3++) {
                int i4 = ((i3 ^ i) - 1) >> 31;
                for (int i5 = 0; i5 < 8; i5++) {
                    int i6 = iArrCreate[i5];
                    int[] iArr = this.val$table;
                    iArrCreate[i5] = i6 ^ (iArr[i2 + i5] & i4);
                    iArrCreate2[i5] = iArrCreate2[i5] ^ (iArr[(i2 + 8) + i5] & i4);
                }
                i2 += 16;
            }
            return createPoint(iArrCreate, iArrCreate2);
        }

        @Override // p000.AbstractC0484f6, p000.InterfaceC1334ve
        public AbstractC1341vl lookupVar(int i) {
            int[] iArrCreate = sh0.create();
            int[] iArrCreate2 = sh0.create();
            int i2 = i * 16;
            for (int i3 = 0; i3 < 8; i3++) {
                int[] iArr = this.val$table;
                iArrCreate[i3] = iArr[i2 + i3];
                iArrCreate2[i3] = iArr[8 + i2 + i3];
            }
            return createPoint(iArrCreate, iArrCreate2);
        }
    }

    static {
        BigInteger bigInteger = new BigInteger(1, c40.decodeStrict("2AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA984914A144"));
        C_a = bigInteger;
        C_b = new BigInteger(1, c40.decodeStrict("7B425ED097B425ED097B425ED097B425ED097B425ED097B4260B5E9C7710C864"));
        CURVE25519_AFFINE_ZS = new AbstractC1330va[]{new C0949oe(InterfaceC1315uw.ONE), new C0949oe(bigInteger)};
    }

    public C0947oc() {
        super(f58778q);
        this.infinity = new C0950of(this, null, null);
        this.f60524a = fromBigInteger(C_a);
        this.f60525b = fromBigInteger(C_b);
        this.order = new BigInteger(1, c40.decodeStrict("1000000000000000000000000000000014DEF9DEA2F79CD65812631A5CF5D3ED"));
        this.cofactor = BigInteger.valueOf(8L);
        this.coord = 4;
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1316ux cloneCurve() {
        return new C0947oc();
    }

    @Override // p000.AbstractC1316ux
    public InterfaceC1334ve createCacheSafeLookupTable(AbstractC1341vl[] abstractC1341vlArr, int i, int i2) {
        int[] iArr = new int[i2 * 16];
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            AbstractC1341vl abstractC1341vl = abstractC1341vlArr[i + i4];
            sh0.copy(((C0949oe) abstractC1341vl.getRawXCoord()).f58791x, 0, iArr, i3);
            sh0.copy(((C0949oe) abstractC1341vl.getRawYCoord()).f58791x, 0, iArr, i3 + 8);
            i3 += 16;
        }
        return new a0(i2, iArr);
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1341vl createRawPoint(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
        return new C0950of(this, abstractC1330va, abstractC1330va2);
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1330va fromBigInteger(BigInteger bigInteger) {
        return new C0949oe(bigInteger);
    }

    @Override // p000.AbstractC1316ux
    public int getFieldSize() {
        return f58778q.bitLength();
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1341vl getInfinity() {
        return this.infinity;
    }

    public BigInteger getQ() {
        return f58778q;
    }

    @Override // p000.AbstractC1316ux.a2, p000.AbstractC1316ux
    public AbstractC1330va randomFieldElement(SecureRandom secureRandom) {
        int[] iArrCreate = sh0.create();
        C0948od.random(secureRandom, iArrCreate);
        return new C0949oe(iArrCreate);
    }

    @Override // p000.AbstractC1316ux.a2, p000.AbstractC1316ux
    public AbstractC1330va randomFieldElementMult(SecureRandom secureRandom) {
        int[] iArrCreate = sh0.create();
        C0948od.randomMult(secureRandom, iArrCreate);
        return new C0949oe(iArrCreate);
    }

    @Override // p000.AbstractC1316ux
    public boolean supportsCoordinateSystem(int i) {
        return i == 4;
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1341vl createRawPoint(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va[] abstractC1330vaArr) {
        return new C0950of(this, abstractC1330va, abstractC1330va2, abstractC1330vaArr);
    }
}
