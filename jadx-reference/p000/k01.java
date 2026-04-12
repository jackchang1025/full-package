package p000;

import android.graphics.Matrix;
import android.graphics.Path;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class k01 {

    /* renamed from: a0 */
    public float f57411a0;

    /* renamed from: a1 */
    public float f57412a1;

    /* renamed from: a2 */
    public float f57413a2;

    /* renamed from: a3 */
    public float f57414a3;

    /* renamed from: a4 */
    public float f57415a4;

    /* renamed from: a5 */
    public float f57416a5;

    /* renamed from: a6 */
    public final ArrayList f57417a6 = new ArrayList();

    /* renamed from: a7 */
    public final ArrayList f57418a7 = new ArrayList();

    public k01() {
        m213400a4(0.0f, 0.0f, 270.0f, 0.0f);
    }

    /* renamed from: a0 */
    public final void m213396a0(float f, float f2, float f3, float f4, float f5, float f6) {
        g01 g01Var = new g01(f, f2, f3, f4);
        g01Var.f56362a5 = f5;
        g01Var.f56363a6 = f6;
        this.f57417a6.add(g01Var);
        e01 e01Var = new e01(g01Var);
        float f7 = f5 + f6;
        boolean z = f6 < 0.0f;
        if (z) {
            f5 = (f5 + 180.0f) % 360.0f;
        }
        float f8 = z ? (180.0f + f7) % 360.0f : f7;
        m213397a1(f5);
        this.f57418a7.add(e01Var);
        this.f57415a4 = f8;
        double d = f7;
        this.f57413a2 = (((f3 - f) / 2.0f) * ((float) Math.cos(Math.toRadians(d)))) + ((f + f3) * 0.5f);
        this.f57414a3 = (((f4 - f2) / 2.0f) * ((float) Math.sin(Math.toRadians(d)))) + ((f2 + f4) * 0.5f);
    }

    /* renamed from: a1 */
    public final void m213397a1(float f) {
        float f2 = this.f57415a4;
        if (f2 == f) {
            return;
        }
        float f3 = ((f - f2) + 360.0f) % 360.0f;
        if (f3 > 180.0f) {
            return;
        }
        float f4 = this.f57413a2;
        float f5 = this.f57414a3;
        g01 g01Var = new g01(f4, f5, f4, f5);
        g01Var.f56362a5 = this.f57415a4;
        g01Var.f56363a6 = f3;
        this.f57418a7.add(new e01(g01Var));
        this.f57415a4 = f;
    }

    /* renamed from: a2 */
    public final void m213398a2(Matrix matrix, Path path) {
        ArrayList arrayList = this.f57417a6;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ((i01) arrayList.get(i)).mo212877a0(matrix, path);
        }
    }

    /* renamed from: a3 */
    public final void m213399a3(float f, float f2) {
        h01 h01Var = new h01();
        h01Var.f56592a1 = f;
        h01Var.f56593a2 = f2;
        this.f57417a6.add(h01Var);
        f01 f01Var = new f01(h01Var, this.f57413a2, this.f57414a3);
        float fM212731a1 = f01Var.m212731a1() + 270.0f;
        float fM212731a12 = f01Var.m212731a1() + 270.0f;
        m213397a1(fM212731a1);
        this.f57418a7.add(f01Var);
        this.f57415a4 = fM212731a12;
        this.f57413a2 = f;
        this.f57414a3 = f2;
    }

    /* renamed from: a4 */
    public final void m213400a4(float f, float f2, float f3, float f4) {
        this.f57411a0 = f;
        this.f57412a1 = f2;
        this.f57413a2 = f;
        this.f57414a3 = f2;
        this.f57415a4 = f3;
        this.f57416a5 = (f3 + f4) % 360.0f;
        this.f57417a6.clear();
        this.f57418a7.clear();
    }
}
