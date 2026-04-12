package p000;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Hashtable;
import java.util.Random;
import p000.AbstractC1330va;
import p000.AbstractC1341vl;

/* renamed from: ux */
/* loaded from: classes2.dex */
public abstract class AbstractC1316ux {
    public static final int COORD_AFFINE = 0;
    public static final int COORD_HOMOGENEOUS = 1;
    public static final int COORD_JACOBIAN = 2;
    public static final int COORD_JACOBIAN_CHUDNOVSKY = 3;
    public static final int COORD_JACOBIAN_MODIFIED = 4;
    public static final int COORD_LAMBDA_AFFINE = 5;
    public static final int COORD_LAMBDA_PROJECTIVE = 6;
    public static final int COORD_SKEWED = 7;

    /* renamed from: a */
    protected AbstractC1330va f60524a;

    /* renamed from: b */
    protected AbstractC1330va f60525b;
    protected BigInteger cofactor;
    protected InterfaceC1519zj field;
    protected BigInteger order;
    protected int coord = 0;
    protected InterfaceC1318uz endomorphism = null;
    protected InterfaceC1335vf multiplier = null;

    /* renamed from: ux$a0 */
    public class a0 extends AbstractC0484f6 {
        final /* synthetic */ int val$FE_BYTES;
        final /* synthetic */ int val$len;
        final /* synthetic */ byte[] val$table;

        public a0(int i, int i2, byte[] bArr) {
            this.val$len = i;
            this.val$FE_BYTES = i2;
            this.val$table = bArr;
        }

        private AbstractC1341vl createPoint(byte[] bArr, byte[] bArr2) {
            AbstractC1316ux abstractC1316ux = AbstractC1316ux.this;
            return abstractC1316ux.createRawPoint(abstractC1316ux.fromBigInteger(new BigInteger(1, bArr)), AbstractC1316ux.this.fromBigInteger(new BigInteger(1, bArr2)));
        }

        @Override // p000.AbstractC0484f6, p000.InterfaceC1334ve
        public int getSize() {
            return this.val$len;
        }

        @Override // p000.AbstractC0484f6, p000.InterfaceC1334ve
        public AbstractC1341vl lookup(int i) {
            int i2;
            int i3 = this.val$FE_BYTES;
            byte[] bArr = new byte[i3];
            byte[] bArr2 = new byte[i3];
            int i4 = 0;
            for (int i5 = 0; i5 < this.val$len; i5++) {
                int i6 = ((i5 ^ i) - 1) >> 31;
                int i7 = 0;
                while (true) {
                    i2 = this.val$FE_BYTES;
                    if (i7 < i2) {
                        byte b = bArr[i7];
                        byte[] bArr3 = this.val$table;
                        bArr[i7] = (byte) (b ^ (bArr3[i4 + i7] & i6));
                        bArr2[i7] = (byte) ((bArr3[(i2 + i4) + i7] & i6) ^ bArr2[i7]);
                        i7++;
                    }
                }
                i4 += i2 * 2;
            }
            return createPoint(bArr, bArr2);
        }

        @Override // p000.AbstractC0484f6, p000.InterfaceC1334ve
        public AbstractC1341vl lookupVar(int i) {
            int i2 = this.val$FE_BYTES;
            byte[] bArr = new byte[i2];
            byte[] bArr2 = new byte[i2];
            int i3 = i * i2 * 2;
            int i4 = 0;
            while (true) {
                int i5 = this.val$FE_BYTES;
                if (i4 >= i5) {
                    return createPoint(bArr, bArr2);
                }
                byte[] bArr3 = this.val$table;
                bArr[i4] = bArr3[i3 + i4];
                bArr2[i4] = bArr3[i5 + i3 + i4];
                i4++;
            }
        }
    }

    /* renamed from: ux$a1 */
    public static abstract class a1 extends AbstractC1316ux {

        /* renamed from: si */
        private BigInteger[] f60526si;

        public a1(int i, int i2, int i3, int i4) {
            super(buildField(i, i2, i3, i4));
            this.f60526si = null;
        }

        private static InterfaceC1519zj buildField(int i, int i2, int i3, int i4) {
            if (i2 == 0) {
                throw new IllegalArgumentException("k1 must be > 0");
            }
            if (i3 == 0) {
                if (i4 == 0) {
                    return AbstractC1520zk.getBinaryExtensionField(new int[]{0, i2, i});
                }
                throw new IllegalArgumentException("k3 must be 0 if k2 == 0");
            }
            if (i3 <= i2) {
                throw new IllegalArgumentException("k2 must be > k1");
            }
            if (i4 > i3) {
                return AbstractC1520zk.getBinaryExtensionField(new int[]{0, i2, i3, i4, i});
            }
            throw new IllegalArgumentException("k3 must be > k2");
        }

        private static BigInteger implRandomFieldElementMult(SecureRandom secureRandom, int i) {
            BigInteger bigIntegerCreateRandomBigInteger;
            do {
                bigIntegerCreateRandomBigInteger = C0427ds.createRandomBigInteger(i, secureRandom);
            } while (bigIntegerCreateRandomBigInteger.signum() <= 0);
            return bigIntegerCreateRandomBigInteger;
        }

        public static BigInteger inverse(int i, int[] iArr, BigInteger bigInteger) {
            return new lc0(bigInteger).modInverse(i, iArr).toBigInteger();
        }

        @Override // p000.AbstractC1316ux
        public AbstractC1341vl createPoint(BigInteger bigInteger, BigInteger bigInteger2) {
            AbstractC1330va abstractC1330vaFromBigInteger = fromBigInteger(bigInteger);
            AbstractC1330va abstractC1330vaFromBigInteger2 = fromBigInteger(bigInteger2);
            int coordinateSystem = getCoordinateSystem();
            if (coordinateSystem == 5 || coordinateSystem == 6) {
                if (!abstractC1330vaFromBigInteger.isZero()) {
                    abstractC1330vaFromBigInteger2 = abstractC1330vaFromBigInteger2.divide(abstractC1330vaFromBigInteger).add(abstractC1330vaFromBigInteger);
                } else if (!abstractC1330vaFromBigInteger2.square().equals(getB())) {
                    throw new IllegalArgumentException();
                }
            }
            return createRawPoint(abstractC1330vaFromBigInteger, abstractC1330vaFromBigInteger2);
        }

        @Override // p000.AbstractC1316ux
        public AbstractC1341vl decompressPoint(int i, BigInteger bigInteger) {
            AbstractC1330va abstractC1330vaAdd;
            AbstractC1330va abstractC1330vaFromBigInteger = fromBigInteger(bigInteger);
            if (abstractC1330vaFromBigInteger.isZero()) {
                abstractC1330vaAdd = getB().sqrt();
            } else {
                AbstractC1330va abstractC1330vaSolveQuadraticEquation = solveQuadraticEquation(abstractC1330vaFromBigInteger.square().invert().multiply(getB()).add(getA()).add(abstractC1330vaFromBigInteger));
                if (abstractC1330vaSolveQuadraticEquation != null) {
                    if (abstractC1330vaSolveQuadraticEquation.testBitZero() != (i == 1)) {
                        abstractC1330vaSolveQuadraticEquation = abstractC1330vaSolveQuadraticEquation.addOne();
                    }
                    int coordinateSystem = getCoordinateSystem();
                    abstractC1330vaAdd = (coordinateSystem == 5 || coordinateSystem == 6) ? abstractC1330vaSolveQuadraticEquation.add(abstractC1330vaFromBigInteger) : abstractC1330vaSolveQuadraticEquation.multiply(abstractC1330vaFromBigInteger);
                } else {
                    abstractC1330vaAdd = null;
                }
            }
            if (abstractC1330vaAdd != null) {
                return createRawPoint(abstractC1330vaFromBigInteger, abstractC1330vaAdd);
            }
            throw new IllegalArgumentException("Invalid point compression");
        }

        public synchronized BigInteger[] getSi() {
            try {
                if (this.f60526si == null) {
                    this.f60526si = w61.getSi(this);
                }
            } catch (Throwable th) {
                throw th;
            }
            return this.f60526si;
        }

        public boolean isKoblitz() {
            if (this.order == null || this.cofactor == null || !this.f60525b.isOne()) {
                return false;
            }
            return this.f60524a.isZero() || this.f60524a.isOne();
        }

        @Override // p000.AbstractC1316ux
        public boolean isValidFieldElement(BigInteger bigInteger) {
            return bigInteger != null && bigInteger.signum() >= 0 && bigInteger.bitLength() <= getFieldSize();
        }

        @Override // p000.AbstractC1316ux
        public AbstractC1330va randomFieldElement(SecureRandom secureRandom) {
            return fromBigInteger(C0427ds.createRandomBigInteger(getFieldSize(), secureRandom));
        }

        @Override // p000.AbstractC1316ux
        public AbstractC1330va randomFieldElementMult(SecureRandom secureRandom) {
            int fieldSize = getFieldSize();
            return fromBigInteger(implRandomFieldElementMult(secureRandom, fieldSize)).multiply(fromBigInteger(implRandomFieldElementMult(secureRandom, fieldSize)));
        }

        public AbstractC1330va solveQuadraticEquation(AbstractC1330va abstractC1330va) {
            AbstractC1330va abstractC1330vaAdd;
            AbstractC1330va.a0 a0Var = (AbstractC1330va.a0) abstractC1330va;
            boolean zHasFastTrace = a0Var.hasFastTrace();
            if (zHasFastTrace && a0Var.trace() != 0) {
                return null;
            }
            int fieldSize = getFieldSize();
            if ((fieldSize & 1) != 0) {
                AbstractC1330va abstractC1330vaHalfTrace = a0Var.halfTrace();
                if (zHasFastTrace || abstractC1330vaHalfTrace.square().add(abstractC1330vaHalfTrace).add(abstractC1330va).isZero()) {
                    return abstractC1330vaHalfTrace;
                }
                return null;
            }
            if (abstractC1330va.isZero()) {
                return abstractC1330va;
            }
            AbstractC1330va abstractC1330vaFromBigInteger = fromBigInteger(InterfaceC1315uw.ZERO);
            Random random = new Random();
            do {
                AbstractC1330va abstractC1330vaFromBigInteger2 = fromBigInteger(new BigInteger(fieldSize, random));
                AbstractC1330va abstractC1330vaAdd2 = abstractC1330va;
                abstractC1330vaAdd = abstractC1330vaFromBigInteger;
                for (int i = 1; i < fieldSize; i++) {
                    AbstractC1330va abstractC1330vaSquare = abstractC1330vaAdd2.square();
                    abstractC1330vaAdd = abstractC1330vaAdd.square().add(abstractC1330vaSquare.multiply(abstractC1330vaFromBigInteger2));
                    abstractC1330vaAdd2 = abstractC1330vaSquare.add(abstractC1330va);
                }
                if (!abstractC1330vaAdd2.isZero()) {
                    return null;
                }
            } while (abstractC1330vaAdd.square().add(abstractC1330vaAdd).isZero());
            return abstractC1330vaAdd;
        }
    }

    /* renamed from: ux$a2 */
    public static abstract class a2 extends AbstractC1316ux {
        public a2(BigInteger bigInteger) {
            super(AbstractC1520zk.getPrimeField(bigInteger));
        }

        private static BigInteger implRandomFieldElement(SecureRandom secureRandom, BigInteger bigInteger) {
            BigInteger bigIntegerCreateRandomBigInteger;
            do {
                bigIntegerCreateRandomBigInteger = C0427ds.createRandomBigInteger(bigInteger.bitLength(), secureRandom);
            } while (bigIntegerCreateRandomBigInteger.compareTo(bigInteger) >= 0);
            return bigIntegerCreateRandomBigInteger;
        }

        private static BigInteger implRandomFieldElementMult(SecureRandom secureRandom, BigInteger bigInteger) {
            while (true) {
                BigInteger bigIntegerCreateRandomBigInteger = C0427ds.createRandomBigInteger(bigInteger.bitLength(), secureRandom);
                if (bigIntegerCreateRandomBigInteger.signum() > 0 && bigIntegerCreateRandomBigInteger.compareTo(bigInteger) < 0) {
                    return bigIntegerCreateRandomBigInteger;
                }
            }
        }

        @Override // p000.AbstractC1316ux
        public AbstractC1341vl decompressPoint(int i, BigInteger bigInteger) {
            AbstractC1330va abstractC1330vaFromBigInteger = fromBigInteger(bigInteger);
            AbstractC1330va abstractC1330vaSqrt = abstractC1330vaFromBigInteger.square().add(this.f60524a).multiply(abstractC1330vaFromBigInteger).add(this.f60525b).sqrt();
            if (abstractC1330vaSqrt == null) {
                throw new IllegalArgumentException("Invalid point compression");
            }
            if (abstractC1330vaSqrt.testBitZero() != (i == 1)) {
                abstractC1330vaSqrt = abstractC1330vaSqrt.negate();
            }
            return createRawPoint(abstractC1330vaFromBigInteger, abstractC1330vaSqrt);
        }

        @Override // p000.AbstractC1316ux
        public boolean isValidFieldElement(BigInteger bigInteger) {
            return bigInteger != null && bigInteger.signum() >= 0 && bigInteger.compareTo(getField().getCharacteristic()) < 0;
        }

        @Override // p000.AbstractC1316ux
        public AbstractC1330va randomFieldElement(SecureRandom secureRandom) {
            BigInteger characteristic = getField().getCharacteristic();
            return fromBigInteger(implRandomFieldElement(secureRandom, characteristic)).multiply(fromBigInteger(implRandomFieldElement(secureRandom, characteristic)));
        }

        @Override // p000.AbstractC1316ux
        public AbstractC1330va randomFieldElementMult(SecureRandom secureRandom) {
            BigInteger characteristic = getField().getCharacteristic();
            return fromBigInteger(implRandomFieldElementMult(secureRandom, characteristic)).multiply(fromBigInteger(implRandomFieldElementMult(secureRandom, characteristic)));
        }
    }

    /* renamed from: ux$a3 */
    public class a3 {
        protected int coord;
        protected InterfaceC1318uz endomorphism;
        protected InterfaceC1335vf multiplier;

        public a3(int i, InterfaceC1318uz interfaceC1318uz, InterfaceC1335vf interfaceC1335vf) {
            this.coord = i;
            this.endomorphism = interfaceC1318uz;
            this.multiplier = interfaceC1335vf;
        }

        public AbstractC1316ux create() {
            if (!AbstractC1316ux.this.supportsCoordinateSystem(this.coord)) {
                throw new IllegalStateException("unsupported coordinate system");
            }
            AbstractC1316ux abstractC1316uxCloneCurve = AbstractC1316ux.this.cloneCurve();
            if (abstractC1316uxCloneCurve == AbstractC1316ux.this) {
                throw new IllegalStateException("implementation returned current curve");
            }
            synchronized (abstractC1316uxCloneCurve) {
                abstractC1316uxCloneCurve.coord = this.coord;
                abstractC1316uxCloneCurve.endomorphism = this.endomorphism;
                abstractC1316uxCloneCurve.multiplier = this.multiplier;
            }
            return abstractC1316uxCloneCurve;
        }

        public a3 setCoordinateSystem(int i) {
            this.coord = i;
            return this;
        }

        public a3 setEndomorphism(InterfaceC1318uz interfaceC1318uz) {
            this.endomorphism = interfaceC1318uz;
            return this;
        }

        public a3 setMultiplier(InterfaceC1335vf interfaceC1335vf) {
            this.multiplier = interfaceC1335vf;
            return this;
        }
    }

    /* renamed from: ux$a4 */
    public static class a4 extends a1 {
        private static final int F2M_DEFAULT_COORDS = 6;
        private AbstractC1341vl.a3 infinity;

        /* renamed from: k1 */
        private int f60527k1;

        /* renamed from: k2 */
        private int f60528k2;

        /* renamed from: k3 */
        private int f60529k3;

        /* renamed from: m */
        private int f60530m;

        /* renamed from: ux$a4$a0 */
        public class a0 extends AbstractC0484f6 {
            final /* synthetic */ int val$FE_LONGS;
            final /* synthetic */ int[] val$ks;
            final /* synthetic */ int val$len;
            final /* synthetic */ long[] val$table;

            public a0(int i, int i2, long[] jArr, int[] iArr) {
                this.val$len = i;
                this.val$FE_LONGS = i2;
                this.val$table = jArr;
                this.val$ks = iArr;
            }

            private AbstractC1341vl createPoint(long[] jArr, long[] jArr2) {
                return a4.this.createRawPoint(new AbstractC1330va.a2(a4.this.f60530m, this.val$ks, new lc0(jArr)), new AbstractC1330va.a2(a4.this.f60530m, this.val$ks, new lc0(jArr2)));
            }

            @Override // p000.AbstractC0484f6, p000.InterfaceC1334ve
            public int getSize() {
                return this.val$len;
            }

            @Override // p000.AbstractC0484f6, p000.InterfaceC1334ve
            public AbstractC1341vl lookup(int i) {
                int i2;
                long[] jArrCreate64 = yh0.create64(this.val$FE_LONGS);
                long[] jArrCreate642 = yh0.create64(this.val$FE_LONGS);
                int i3 = 0;
                for (int i4 = 0; i4 < this.val$len; i4++) {
                    long j = ((i4 ^ i) - 1) >> 31;
                    int i5 = 0;
                    while (true) {
                        i2 = this.val$FE_LONGS;
                        if (i5 < i2) {
                            long j2 = jArrCreate64[i5];
                            long[] jArr = this.val$table;
                            jArrCreate64[i5] = j2 ^ (jArr[i3 + i5] & j);
                            jArrCreate642[i5] = jArrCreate642[i5] ^ (jArr[(i2 + i3) + i5] & j);
                            i5++;
                        }
                    }
                    i3 += i2 * 2;
                }
                return createPoint(jArrCreate64, jArrCreate642);
            }

            @Override // p000.AbstractC0484f6, p000.InterfaceC1334ve
            public AbstractC1341vl lookupVar(int i) {
                long[] jArrCreate64 = yh0.create64(this.val$FE_LONGS);
                long[] jArrCreate642 = yh0.create64(this.val$FE_LONGS);
                int i2 = i * this.val$FE_LONGS * 2;
                int i3 = 0;
                while (true) {
                    int i4 = this.val$FE_LONGS;
                    if (i3 >= i4) {
                        return createPoint(jArrCreate64, jArrCreate642);
                    }
                    long[] jArr = this.val$table;
                    jArrCreate64[i3] = jArr[i2 + i3];
                    jArrCreate642[i3] = jArr[i4 + i2 + i3];
                    i3++;
                }
            }
        }

        public a4(int i, int i2, int i3, int i4, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, BigInteger bigInteger, BigInteger bigInteger2) {
            super(i, i2, i3, i4);
            this.f60530m = i;
            this.f60527k1 = i2;
            this.f60528k2 = i3;
            this.f60529k3 = i4;
            this.order = bigInteger;
            this.cofactor = bigInteger2;
            this.infinity = new AbstractC1341vl.a3(this, null, null);
            this.f60524a = abstractC1330va;
            this.f60525b = abstractC1330va2;
            this.coord = 6;
        }

        @Override // p000.AbstractC1316ux
        public AbstractC1316ux cloneCurve() {
            return new a4(this.f60530m, this.f60527k1, this.f60528k2, this.f60529k3, this.f60524a, this.f60525b, this.order, this.cofactor);
        }

        @Override // p000.AbstractC1316ux
        public InterfaceC1334ve createCacheSafeLookupTable(AbstractC1341vl[] abstractC1341vlArr, int i, int i2) {
            int i3 = (this.f60530m + 63) >>> 6;
            int[] iArr = isTrinomial() ? new int[]{this.f60527k1} : new int[]{this.f60527k1, this.f60528k2, this.f60529k3};
            long[] jArr = new long[i2 * i3 * 2];
            int i4 = 0;
            for (int i5 = 0; i5 < i2; i5++) {
                AbstractC1341vl abstractC1341vl = abstractC1341vlArr[i + i5];
                ((AbstractC1330va.a2) abstractC1341vl.getRawXCoord()).f60613x.copyTo(jArr, i4);
                int i6 = i4 + i3;
                ((AbstractC1330va.a2) abstractC1341vl.getRawYCoord()).f60613x.copyTo(jArr, i6);
                i4 = i6 + i3;
            }
            return new a0(i2, i3, jArr, iArr);
        }

        @Override // p000.AbstractC1316ux
        public InterfaceC1335vf createDefaultMultiplier() {
            return isKoblitz() ? new de1() : super.createDefaultMultiplier();
        }

        @Override // p000.AbstractC1316ux
        public AbstractC1341vl createRawPoint(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
            return new AbstractC1341vl.a3(this, abstractC1330va, abstractC1330va2);
        }

        @Override // p000.AbstractC1316ux
        public AbstractC1330va fromBigInteger(BigInteger bigInteger) {
            return new AbstractC1330va.a2(this.f60530m, this.f60527k1, this.f60528k2, this.f60529k3, bigInteger);
        }

        @Override // p000.AbstractC1316ux
        public int getFieldSize() {
            return this.f60530m;
        }

        @Override // p000.AbstractC1316ux
        public AbstractC1341vl getInfinity() {
            return this.infinity;
        }

        public int getK1() {
            return this.f60527k1;
        }

        public int getK2() {
            return this.f60528k2;
        }

        public int getK3() {
            return this.f60529k3;
        }

        public int getM() {
            return this.f60530m;
        }

        public boolean isTrinomial() {
            return this.f60528k2 == 0 && this.f60529k3 == 0;
        }

        @Override // p000.AbstractC1316ux
        public boolean supportsCoordinateSystem(int i) {
            return i == 0 || i == 1 || i == 6;
        }

        public a4(int i, int i2, int i3, int i4, BigInteger bigInteger, BigInteger bigInteger2) {
            this(i, i2, i3, i4, bigInteger, bigInteger2, (BigInteger) null, (BigInteger) null);
        }

        @Override // p000.AbstractC1316ux
        public AbstractC1341vl createRawPoint(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va[] abstractC1330vaArr) {
            return new AbstractC1341vl.a3(this, abstractC1330va, abstractC1330va2, abstractC1330vaArr);
        }

        public a4(int i, int i2, int i3, int i4, BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4) {
            super(i, i2, i3, i4);
            this.f60530m = i;
            this.f60527k1 = i2;
            this.f60528k2 = i3;
            this.f60529k3 = i4;
            this.order = bigInteger3;
            this.cofactor = bigInteger4;
            this.infinity = new AbstractC1341vl.a3(this, null, null);
            this.f60524a = fromBigInteger(bigInteger);
            this.f60525b = fromBigInteger(bigInteger2);
            this.coord = 6;
        }

        public a4(int i, int i2, BigInteger bigInteger, BigInteger bigInteger2) {
            this(i, i2, 0, 0, bigInteger, bigInteger2, (BigInteger) null, (BigInteger) null);
        }

        public a4(int i, int i2, BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4) {
            this(i, i2, 0, 0, bigInteger, bigInteger2, bigInteger3, bigInteger4);
        }
    }

    /* renamed from: ux$a5 */
    public static class a5 extends a2 {
        private static final int FP_DEFAULT_COORDS = 4;
        AbstractC1341vl.a4 infinity;

        /* renamed from: q */
        BigInteger f60531q;

        /* renamed from: r */
        BigInteger f60532r;

        public a5(BigInteger bigInteger, BigInteger bigInteger2, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, BigInteger bigInteger3, BigInteger bigInteger4) {
            super(bigInteger);
            this.f60531q = bigInteger;
            this.f60532r = bigInteger2;
            this.infinity = new AbstractC1341vl.a4(this, null, null);
            this.f60524a = abstractC1330va;
            this.f60525b = abstractC1330va2;
            this.order = bigInteger3;
            this.cofactor = bigInteger4;
            this.coord = 4;
        }

        @Override // p000.AbstractC1316ux
        public AbstractC1316ux cloneCurve() {
            return new a5(this.f60531q, this.f60532r, this.f60524a, this.f60525b, this.order, this.cofactor);
        }

        @Override // p000.AbstractC1316ux
        public AbstractC1341vl createRawPoint(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
            return new AbstractC1341vl.a4(this, abstractC1330va, abstractC1330va2);
        }

        @Override // p000.AbstractC1316ux
        public AbstractC1330va fromBigInteger(BigInteger bigInteger) {
            return new AbstractC1330va.a3(this.f60531q, this.f60532r, bigInteger);
        }

        @Override // p000.AbstractC1316ux
        public int getFieldSize() {
            return this.f60531q.bitLength();
        }

        @Override // p000.AbstractC1316ux
        public AbstractC1341vl getInfinity() {
            return this.infinity;
        }

        public BigInteger getQ() {
            return this.f60531q;
        }

        @Override // p000.AbstractC1316ux
        public AbstractC1341vl importPoint(AbstractC1341vl abstractC1341vl) {
            int coordinateSystem;
            return (this == abstractC1341vl.getCurve() || getCoordinateSystem() != 2 || abstractC1341vl.isInfinity() || !((coordinateSystem = abstractC1341vl.getCurve().getCoordinateSystem()) == 2 || coordinateSystem == 3 || coordinateSystem == 4)) ? super.importPoint(abstractC1341vl) : new AbstractC1341vl.a4(this, fromBigInteger(abstractC1341vl.f60653x.toBigInteger()), fromBigInteger(abstractC1341vl.f60654y.toBigInteger()), new AbstractC1330va[]{fromBigInteger(abstractC1341vl.f60655zs[0].toBigInteger())});
        }

        @Override // p000.AbstractC1316ux
        public boolean supportsCoordinateSystem(int i) {
            return i == 0 || i == 1 || i == 2 || i == 4;
        }

        public a5(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
            this(bigInteger, bigInteger2, bigInteger3, null, null);
        }

        @Override // p000.AbstractC1316ux
        public AbstractC1341vl createRawPoint(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va[] abstractC1330vaArr) {
            return new AbstractC1341vl.a4(this, abstractC1330va, abstractC1330va2, abstractC1330vaArr);
        }

        public a5(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, BigInteger bigInteger5) {
            super(bigInteger);
            this.f60531q = bigInteger;
            this.f60532r = AbstractC1330va.a3.calculateResidue(bigInteger);
            this.infinity = new AbstractC1341vl.a4(this, null, null);
            this.f60524a = fromBigInteger(bigInteger2);
            this.f60525b = fromBigInteger(bigInteger3);
            this.order = bigInteger4;
            this.cofactor = bigInteger5;
            this.coord = 4;
        }
    }

    public AbstractC1316ux(InterfaceC1519zj interfaceC1519zj) {
        this.field = interfaceC1519zj;
    }

    public static int[] getAllCoordinateSystems() {
        return new int[]{0, 1, 2, 3, 4, 5, 6, 7};
    }

    public void checkPoint(AbstractC1341vl abstractC1341vl) {
        if (abstractC1341vl == null || this != abstractC1341vl.getCurve()) {
            throw new IllegalArgumentException("'point' must be non-null and on this curve");
        }
    }

    public void checkPoints(AbstractC1341vl[] abstractC1341vlArr) {
        checkPoints(abstractC1341vlArr, 0, abstractC1341vlArr.length);
    }

    public abstract AbstractC1316ux cloneCurve();

    public synchronized a3 configure() {
        return new a3(this.coord, this.endomorphism, this.multiplier);
    }

    public InterfaceC1334ve createCacheSafeLookupTable(AbstractC1341vl[] abstractC1341vlArr, int i, int i2) {
        int fieldSize = (getFieldSize() + 7) >>> 3;
        byte[] bArr = new byte[i2 * fieldSize * 2];
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            AbstractC1341vl abstractC1341vl = abstractC1341vlArr[i + i4];
            byte[] byteArray = abstractC1341vl.getRawXCoord().toBigInteger().toByteArray();
            byte[] byteArray2 = abstractC1341vl.getRawYCoord().toBigInteger().toByteArray();
            int i5 = 1;
            int i6 = byteArray.length > fieldSize ? 1 : 0;
            int length = byteArray.length - i6;
            if (byteArray2.length <= fieldSize) {
                i5 = 0;
            }
            int length2 = byteArray2.length - i5;
            int i7 = i3 + fieldSize;
            System.arraycopy(byteArray, i6, bArr, i7 - length, length);
            i3 = i7 + fieldSize;
            System.arraycopy(byteArray2, i5, bArr, i3 - length2, length2);
        }
        return new a0(i2, fieldSize, bArr);
    }

    public InterfaceC1335vf createDefaultMultiplier() {
        InterfaceC1318uz interfaceC1318uz = this.endomorphism;
        return interfaceC1318uz instanceof b20 ? new c20(this, (b20) interfaceC1318uz) : new vd1();
    }

    public AbstractC1341vl createPoint(BigInteger bigInteger, BigInteger bigInteger2) {
        return createRawPoint(fromBigInteger(bigInteger), fromBigInteger(bigInteger2));
    }

    public abstract AbstractC1341vl createRawPoint(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2);

    public abstract AbstractC1341vl createRawPoint(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va[] abstractC1330vaArr);

    public AbstractC1341vl decodePoint(byte[] bArr) {
        AbstractC1341vl infinity;
        int fieldSize = (getFieldSize() + 7) / 8;
        byte b = bArr[0];
        if (b != 0) {
            if (b == 2 || b == 3) {
                if (bArr.length != fieldSize + 1) {
                    throw new IllegalArgumentException("Incorrect length for compressed encoding");
                }
                infinity = decompressPoint(b & 1, C0427ds.fromUnsignedByteArray(bArr, 1, fieldSize));
                if (!infinity.implIsValid(true, true)) {
                    throw new IllegalArgumentException("Invalid point");
                }
            } else if (b != 4) {
                if (b != 6 && b != 7) {
                    throw new IllegalArgumentException("Invalid point encoding 0x" + Integer.toString(b, 16));
                }
                if (bArr.length != (fieldSize * 2) + 1) {
                    throw new IllegalArgumentException("Incorrect length for hybrid encoding");
                }
                BigInteger bigIntegerFromUnsignedByteArray = C0427ds.fromUnsignedByteArray(bArr, 1, fieldSize);
                BigInteger bigIntegerFromUnsignedByteArray2 = C0427ds.fromUnsignedByteArray(bArr, fieldSize + 1, fieldSize);
                if (bigIntegerFromUnsignedByteArray2.testBit(0) != (b == 7)) {
                    throw new IllegalArgumentException("Inconsistent Y coordinate in hybrid encoding");
                }
                infinity = validatePoint(bigIntegerFromUnsignedByteArray, bigIntegerFromUnsignedByteArray2);
            } else {
                if (bArr.length != (fieldSize * 2) + 1) {
                    throw new IllegalArgumentException("Incorrect length for uncompressed encoding");
                }
                infinity = validatePoint(C0427ds.fromUnsignedByteArray(bArr, 1, fieldSize), C0427ds.fromUnsignedByteArray(bArr, fieldSize + 1, fieldSize));
            }
        } else {
            if (bArr.length != 1) {
                throw new IllegalArgumentException("Incorrect length for infinity encoding");
            }
            infinity = getInfinity();
        }
        if (b == 0 || !infinity.isInfinity()) {
            return infinity;
        }
        throw new IllegalArgumentException("Invalid infinity encoding");
    }

    public abstract AbstractC1341vl decompressPoint(int i, BigInteger bigInteger);

    public boolean equals(AbstractC1316ux abstractC1316ux) {
        if (this != abstractC1316ux) {
            return abstractC1316ux != null && getField().equals(abstractC1316ux.getField()) && getA().toBigInteger().equals(abstractC1316ux.getA().toBigInteger()) && getB().toBigInteger().equals(abstractC1316ux.getB().toBigInteger());
        }
        return true;
    }

    public abstract AbstractC1330va fromBigInteger(BigInteger bigInteger);

    public AbstractC1330va getA() {
        return this.f60524a;
    }

    public AbstractC1330va getB() {
        return this.f60525b;
    }

    public BigInteger getCofactor() {
        return this.cofactor;
    }

    public int getCoordinateSystem() {
        return this.coord;
    }

    public InterfaceC1318uz getEndomorphism() {
        return this.endomorphism;
    }

    public InterfaceC1519zj getField() {
        return this.field;
    }

    public abstract int getFieldSize();

    public abstract AbstractC1341vl getInfinity();

    public InterfaceC1335vf getMultiplier() {
        if (this.multiplier == null) {
            this.multiplier = createDefaultMultiplier();
        }
        return this.multiplier;
    }

    public BigInteger getOrder() {
        return this.order;
    }

    public ao0 getPreCompInfo(AbstractC1341vl abstractC1341vl, String str) {
        Hashtable hashtable;
        ao0 ao0Var;
        checkPoint(abstractC1341vl);
        synchronized (abstractC1341vl) {
            hashtable = abstractC1341vl.preCompTable;
        }
        if (hashtable == null) {
            return null;
        }
        synchronized (hashtable) {
            ao0Var = (ao0) hashtable.get(str);
        }
        return ao0Var;
    }

    public int hashCode() {
        return (getField().hashCode() ^ q60.rotateLeft(getA().toBigInteger().hashCode(), 8)) ^ q60.rotateLeft(getB().toBigInteger().hashCode(), 16);
    }

    public AbstractC1341vl importPoint(AbstractC1341vl abstractC1341vl) {
        if (this == abstractC1341vl.getCurve()) {
            return abstractC1341vl;
        }
        if (abstractC1341vl.isInfinity()) {
            return getInfinity();
        }
        AbstractC1341vl abstractC1341vlNormalize = abstractC1341vl.normalize();
        return createPoint(abstractC1341vlNormalize.getXCoord().toBigInteger(), abstractC1341vlNormalize.getYCoord().toBigInteger());
    }

    public abstract boolean isValidFieldElement(BigInteger bigInteger);

    public void normalizeAll(AbstractC1341vl[] abstractC1341vlArr) {
        normalizeAll(abstractC1341vlArr, 0, abstractC1341vlArr.length, null);
    }

    public ao0 precompute(AbstractC1341vl abstractC1341vl, String str, zn0 zn0Var) {
        Hashtable hashtable;
        ao0 ao0VarPrecompute;
        checkPoint(abstractC1341vl);
        synchronized (abstractC1341vl) {
            try {
                hashtable = abstractC1341vl.preCompTable;
                if (hashtable == null) {
                    hashtable = new Hashtable(4);
                    abstractC1341vl.preCompTable = hashtable;
                }
            } finally {
            }
        }
        synchronized (hashtable) {
            try {
                ao0 ao0Var = (ao0) hashtable.get(str);
                ao0VarPrecompute = zn0Var.precompute(ao0Var);
                if (ao0VarPrecompute != ao0Var) {
                    hashtable.put(str, ao0VarPrecompute);
                }
            } finally {
            }
        }
        return ao0VarPrecompute;
    }

    public abstract AbstractC1330va randomFieldElement(SecureRandom secureRandom);

    public abstract AbstractC1330va randomFieldElementMult(SecureRandom secureRandom);

    public boolean supportsCoordinateSystem(int i) {
        return i == 0;
    }

    public AbstractC1341vl validatePoint(BigInteger bigInteger, BigInteger bigInteger2) {
        AbstractC1341vl abstractC1341vlCreatePoint = createPoint(bigInteger, bigInteger2);
        if (abstractC1341vlCreatePoint.isValid()) {
            return abstractC1341vlCreatePoint;
        }
        throw new IllegalArgumentException("Invalid point coordinates");
    }

    public void checkPoints(AbstractC1341vl[] abstractC1341vlArr, int i, int i2) {
        if (abstractC1341vlArr == null) {
            throw new IllegalArgumentException("'points' cannot be null");
        }
        if (i < 0 || i2 < 0 || i > abstractC1341vlArr.length - i2) {
            throw new IllegalArgumentException("invalid range specified for 'points'");
        }
        for (int i3 = 0; i3 < i2; i3++) {
            AbstractC1341vl abstractC1341vl = abstractC1341vlArr[i + i3];
            if (abstractC1341vl != null && this != abstractC1341vl.getCurve()) {
                throw new IllegalArgumentException("'points' entries must be null or on this curve");
            }
        }
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            return (obj instanceof AbstractC1316ux) && equals((AbstractC1316ux) obj);
        }
        return true;
    }

    public void normalizeAll(AbstractC1341vl[] abstractC1341vlArr, int i, int i2, AbstractC1330va abstractC1330va) {
        checkPoints(abstractC1341vlArr, i, i2);
        int coordinateSystem = getCoordinateSystem();
        if (coordinateSystem == 0 || coordinateSystem == 5) {
            if (abstractC1330va != null) {
                throw new IllegalArgumentException("'iso' not valid for affine coordinates");
            }
            return;
        }
        AbstractC1330va[] abstractC1330vaArr = new AbstractC1330va[i2];
        int[] iArr = new int[i2];
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            int i5 = i + i4;
            AbstractC1341vl abstractC1341vl = abstractC1341vlArr[i5];
            if (abstractC1341vl != null && (abstractC1330va != null || !abstractC1341vl.isNormalized())) {
                abstractC1330vaArr[i3] = abstractC1341vl.getZCoord(0);
                iArr[i3] = i5;
                i3++;
            }
        }
        if (i3 == 0) {
            return;
        }
        C1314uv.montgomeryTrick(abstractC1330vaArr, 0, i3, abstractC1330va);
        for (int i6 = 0; i6 < i3; i6++) {
            int i7 = iArr[i6];
            abstractC1341vlArr[i7] = abstractC1341vlArr[i7].normalize(abstractC1330vaArr[i6]);
        }
    }
}
