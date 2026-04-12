package p000;

import java.math.BigInteger;
import java.security.SecureRandom;
import p000.AbstractC1316ux;

/* loaded from: classes2.dex */
public class pw0 extends AbstractC1316ux.a2 {
    private static final int SECP256K1_DEFAULT_COORDS = 2;
    protected sw0 infinity;

    /* renamed from: q */
    public static final BigInteger f59344q = rw0.f59826Q;
    private static final AbstractC1330va[] SECP256K1_AFFINE_ZS = {new rw0(InterfaceC1315uw.ONE)};

    /* renamed from: pw0$a0 */
    public class C1086a0 extends AbstractC0484f6 {
        final /* synthetic */ int val$len;
        final /* synthetic */ int[] val$table;

        public C1086a0(int i, int[] iArr) {
            this.val$len = i;
            this.val$table = iArr;
        }

        private AbstractC1341vl createPoint(int[] iArr, int[] iArr2) {
            return pw0.this.createRawPoint(new rw0(iArr), new rw0(iArr2), pw0.SECP256K1_AFFINE_ZS);
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

    public pw0() {
        super(f59344q);
        this.infinity = new sw0(this, null, null);
        this.f60524a = fromBigInteger(InterfaceC1315uw.ZERO);
        this.f60525b = fromBigInteger(BigInteger.valueOf(7L));
        this.order = new BigInteger(1, c40.decodeStrict("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141"));
        this.cofactor = BigInteger.valueOf(1L);
        this.coord = 2;
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1316ux cloneCurve() {
        return new pw0();
    }

    @Override // p000.AbstractC1316ux
    public InterfaceC1334ve createCacheSafeLookupTable(AbstractC1341vl[] abstractC1341vlArr, int i, int i2) {
        int[] iArr = new int[i2 * 16];
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            AbstractC1341vl abstractC1341vl = abstractC1341vlArr[i + i4];
            sh0.copy(((rw0) abstractC1341vl.getRawXCoord()).f59827x, 0, iArr, i3);
            sh0.copy(((rw0) abstractC1341vl.getRawYCoord()).f59827x, 0, iArr, i3 + 8);
            i3 += 16;
        }
        return new C1086a0(i2, iArr);
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1341vl createRawPoint(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
        return new sw0(this, abstractC1330va, abstractC1330va2);
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1330va fromBigInteger(BigInteger bigInteger) {
        return new rw0(bigInteger);
    }

    @Override // p000.AbstractC1316ux
    public int getFieldSize() {
        return f59344q.bitLength();
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1341vl getInfinity() {
        return this.infinity;
    }

    public BigInteger getQ() {
        return f59344q;
    }

    @Override // p000.AbstractC1316ux.a2, p000.AbstractC1316ux
    public AbstractC1330va randomFieldElement(SecureRandom secureRandom) {
        int[] iArrCreate = sh0.create();
        qw0.random(secureRandom, iArrCreate);
        return new rw0(iArrCreate);
    }

    @Override // p000.AbstractC1316ux.a2, p000.AbstractC1316ux
    public AbstractC1330va randomFieldElementMult(SecureRandom secureRandom) {
        int[] iArrCreate = sh0.create();
        qw0.randomMult(secureRandom, iArrCreate);
        return new rw0(iArrCreate);
    }

    @Override // p000.AbstractC1316ux
    public boolean supportsCoordinateSystem(int i) {
        return i == 2;
    }

    @Override // p000.AbstractC1316ux
    public AbstractC1341vl createRawPoint(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va[] abstractC1330vaArr) {
        return new sw0(this, abstractC1330va, abstractC1330va2, abstractC1330vaArr);
    }
}
