package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class o91 extends n91 {

    /* renamed from: a0 */
    public qm0[] f58761a0;

    /* renamed from: a1 */
    public String f58762a1;

    /* renamed from: a2 */
    public int f58763a2;

    public o91() {
        this.f58761a0 = null;
        this.f58763a2 = 0;
    }

    public qm0[] getPathData() {
        return this.f58761a0;
    }

    public String getPathName() {
        return this.f58762a1;
    }

    public void setPathData(qm0[] qm0VarArr) {
        if (!t60.m214691a9(this.f58761a0, qm0VarArr)) {
            this.f58761a0 = t60.m214703c4(qm0VarArr);
            return;
        }
        qm0[] qm0VarArr2 = this.f58761a0;
        for (int i = 0; i < qm0VarArr.length; i++) {
            qm0VarArr2[i].f59534a0 = qm0VarArr[i].f59534a0;
            int i2 = 0;
            while (true) {
                float[] fArr = qm0VarArr[i].f59535a1;
                if (i2 < fArr.length) {
                    qm0VarArr2[i].f59535a1[i2] = fArr[i2];
                    i2++;
                }
            }
        }
    }

    public o91(o91 o91Var) {
        this.f58761a0 = null;
        this.f58763a2 = 0;
        this.f58762a1 = o91Var.f58762a1;
        this.f58761a0 = t60.m214703c4(o91Var.f58761a0);
    }
}
