package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class d21 extends pg0 {

    /* renamed from: a0 */
    public final e21 f55556a0;

    /* renamed from: a1 */
    public o11 f55557a1;

    /* renamed from: a2 */
    public c21 f55558a2;

    public d21() {
        e21 e21Var = new e21();
        e21Var.f55918b0 = false;
        this.f55556a0 = e21Var;
        this.f55558a2 = e21Var;
    }

    @Override // p000.pg0
    /* renamed from: a0 */
    public final float mo212548a0() {
        return this.f55558a2.mo210758a1();
    }

    /* renamed from: a1 */
    public final void m212549a1(float f, float f2, float f3, float f4, float f5, float f6) {
        e21 e21Var = this.f55556a0;
        this.f55558a2 = e21Var;
        e21Var.f55919b1 = f;
        boolean z = f > f2;
        e21Var.f55918b0 = z;
        if (z) {
            e21Var.m212655a3(-f3, f - f2, f5, f6, f4);
        } else {
            e21Var.m212655a3(f3, f2 - f, f5, f6, f4);
        }
    }

    @Override // android.animation.TimeInterpolator
    public final float getInterpolation(float f) {
        return this.f55558a2.getInterpolation(f);
    }
}
