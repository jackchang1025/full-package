package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class pf1 {

    /* renamed from: a0 */
    public final xf1 f59224a0;

    /* renamed from: a1 */
    public f60[] f59225a1;

    public pf1() {
        this(new xf1());
    }

    /* renamed from: a0 */
    public final void m214253a0() {
        f60[] f60VarArr = this.f59225a1;
        if (f60VarArr != null) {
            f60 f60VarMo214391a5 = f60VarArr[0];
            f60 f60VarMo214391a52 = f60VarArr[1];
            xf1 xf1Var = this.f59224a0;
            if (f60VarMo214391a52 == null) {
                f60VarMo214391a52 = xf1Var.f61102a0.mo214391a5(2);
            }
            if (f60VarMo214391a5 == null) {
                f60VarMo214391a5 = xf1Var.f61102a0.mo214391a5(1);
            }
            mo213838a6(f60.m212747a0(f60VarMo214391a5, f60VarMo214391a52));
            f60 f60Var = this.f59225a1[b81.m210579c7(16)];
            if (f60Var != null) {
                mo213993a5(f60Var);
            }
            f60 f60Var2 = this.f59225a1[b81.m210579c7(32)];
            if (f60Var2 != null) {
                mo213992a3(f60Var2);
            }
            f60 f60Var3 = this.f59225a1[b81.m210579c7(64)];
            if (f60Var3 != null) {
                mo213994a7(f60Var3);
            }
        }
    }

    /* renamed from: a1 */
    public abstract xf1 mo213836a1();

    /* renamed from: a2 */
    public void mo214190a2(int i, f60 f60Var) {
        if (this.f59225a1 == null) {
            this.f59225a1 = new f60[9];
        }
        for (int i2 = 1; i2 <= 256; i2 <<= 1) {
            if ((i & i2) != 0) {
                this.f59225a1[b81.m210579c7(i2)] = f60Var;
            }
        }
    }

    /* renamed from: a4 */
    public abstract void mo213837a4(f60 f60Var);

    /* renamed from: a6 */
    public abstract void mo213838a6(f60 f60Var);

    public pf1(xf1 xf1Var) {
        this.f59224a0 = xf1Var;
    }

    /* renamed from: a3 */
    public void mo213992a3(f60 f60Var) {
    }

    /* renamed from: a5 */
    public void mo213993a5(f60 f60Var) {
    }

    /* renamed from: a7 */
    public void mo213994a7(f60 f60Var) {
    }
}
