package p000;

/* loaded from: classes2.dex */
public class oe0 implements InterfaceC0617ic {
    public static final int DEFAULT_M = 11;
    public static final int DEFAULT_T = 50;
    private InterfaceC1236sv digest;
    private int fieldPoly;

    /* renamed from: m */
    private int f58792m;

    /* renamed from: n */
    private int f58793n;

    /* renamed from: t */
    private int f58794t;

    public oe0() {
        this(11, 50);
    }

    public int getFieldPoly() {
        return this.fieldPoly;
    }

    public int getM() {
        return this.f58792m;
    }

    public int getN() {
        return this.f58793n;
    }

    public int getT() {
        return this.f58794t;
    }

    public oe0(int i) {
        this(i, (InterfaceC1236sv) null);
    }

    public oe0(int i, int i2) {
        this(i, i2, (InterfaceC1236sv) null);
    }

    public oe0(int i, int i2, int i3) {
        this(i, i2, i3, null);
    }

    public oe0(int i, int i2, int i3, InterfaceC1236sv interfaceC1236sv) {
        this.f58792m = i;
        if (i < 1) {
            throw new IllegalArgumentException("m must be positive");
        }
        if (i > 32) {
            throw new IllegalArgumentException(" m is too large");
        }
        int i4 = 1 << i;
        this.f58793n = i4;
        this.f58794t = i2;
        if (i2 < 0) {
            throw new IllegalArgumentException("t must be positive");
        }
        if (i2 > i4) {
            throw new IllegalArgumentException("t must be less than n = 2^m");
        }
        if (tn0.degree(i3) != i || !tn0.isIrreducible(i3)) {
            throw new IllegalArgumentException("polynomial is not a field polynomial for GF(2^m)");
        }
        this.fieldPoly = i3;
        this.digest = interfaceC1236sv;
    }

    public oe0(int i, int i2, InterfaceC1236sv interfaceC1236sv) {
        if (i < 1) {
            throw new IllegalArgumentException("m must be positive");
        }
        if (i > 32) {
            throw new IllegalArgumentException("m is too large");
        }
        this.f58792m = i;
        int i3 = 1 << i;
        this.f58793n = i3;
        if (i2 < 0) {
            throw new IllegalArgumentException("t must be positive");
        }
        if (i2 > i3) {
            throw new IllegalArgumentException("t must be less than n = 2^m");
        }
        this.f58794t = i2;
        this.fieldPoly = tn0.getIrreduciblePolynomial(i);
        this.digest = interfaceC1236sv;
    }

    public oe0(int i, InterfaceC1236sv interfaceC1236sv) {
        if (i < 1) {
            throw new IllegalArgumentException("key size must be positive");
        }
        this.f58792m = 0;
        this.f58793n = 1;
        while (true) {
            int i2 = this.f58793n;
            if (i2 >= i) {
                int i3 = i2 >>> 1;
                this.f58794t = i3;
                int i4 = this.f58792m;
                this.f58794t = i3 / i4;
                this.fieldPoly = tn0.getIrreduciblePolynomial(i4);
                this.digest = interfaceC1236sv;
                return;
            }
            this.f58793n = i2 << 1;
            this.f58792m++;
        }
    }

    public oe0(InterfaceC1236sv interfaceC1236sv) {
        this(11, 50, interfaceC1236sv);
    }
}
