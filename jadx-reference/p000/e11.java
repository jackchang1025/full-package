package p000;

import androidx.constraintlayout.core.SolverVariable$Type;
import java.util.Arrays;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class e11 implements Comparable {

    /* renamed from: a0 */
    public boolean f55896a0;

    /* renamed from: a4 */
    public float f55900a4;

    /* renamed from: a8 */
    public SolverVariable$Type f55904a8;

    /* renamed from: a1 */
    public int f55897a1 = -1;

    /* renamed from: a2 */
    public int f55898a2 = -1;

    /* renamed from: a3 */
    public int f55899a3 = 0;

    /* renamed from: a5 */
    public boolean f55901a5 = false;

    /* renamed from: a6 */
    public final float[] f55902a6 = new float[9];

    /* renamed from: a7 */
    public final float[] f55903a7 = new float[9];

    /* renamed from: a9 */
    public C0131be[] f55905a9 = new C0131be[16];

    /* renamed from: b0 */
    public int f55906b0 = 0;

    /* renamed from: b1 */
    public int f55907b1 = 0;

    public e11(SolverVariable$Type solverVariable$Type) {
        this.f55904a8 = solverVariable$Type;
    }

    /* renamed from: a0 */
    public final void m212649a0(C0131be c0131be) {
        int i = 0;
        while (true) {
            int i2 = this.f55906b0;
            if (i >= i2) {
                C0131be[] c0131beArr = this.f55905a9;
                if (i2 >= c0131beArr.length) {
                    this.f55905a9 = (C0131be[]) Arrays.copyOf(c0131beArr, c0131beArr.length * 2);
                }
                C0131be[] c0131beArr2 = this.f55905a9;
                int i3 = this.f55906b0;
                c0131beArr2[i3] = c0131be;
                this.f55906b0 = i3 + 1;
                return;
            }
            if (this.f55905a9[i] == c0131be) {
                return;
            } else {
                i++;
            }
        }
    }

    /* renamed from: a1 */
    public final void m212650a1(C0131be c0131be) {
        int i = this.f55906b0;
        int i2 = 0;
        while (i2 < i) {
            if (this.f55905a9[i2] == c0131be) {
                while (i2 < i - 1) {
                    C0131be[] c0131beArr = this.f55905a9;
                    int i3 = i2 + 1;
                    c0131beArr[i2] = c0131beArr[i3];
                    i2 = i3;
                }
                this.f55906b0--;
                return;
            }
            i2++;
        }
    }

    /* renamed from: a2 */
    public final void m212651a2() {
        this.f55904a8 = SolverVariable$Type.f44413a3;
        this.f55899a3 = 0;
        this.f55897a1 = -1;
        this.f55898a2 = -1;
        this.f55900a4 = 0.0f;
        this.f55901a5 = false;
        int i = this.f55906b0;
        for (int i2 = 0; i2 < i; i2++) {
            this.f55905a9[i2] = null;
        }
        this.f55906b0 = 0;
        this.f55907b1 = 0;
        this.f55896a0 = false;
        Arrays.fill(this.f55903a7, 0.0f);
    }

    /* renamed from: a3 */
    public final void m212652a3(ab0 ab0Var, float f) {
        this.f55900a4 = f;
        this.f55901a5 = true;
        int i = this.f55906b0;
        this.f55898a2 = -1;
        for (int i2 = 0; i2 < i; i2++) {
            this.f55905a9[i2].m210681a7(ab0Var, this, false);
        }
        this.f55906b0 = 0;
    }

    /* renamed from: a4 */
    public final void m212653a4(ab0 ab0Var, C0131be c0131be) {
        int i = this.f55906b0;
        for (int i2 = 0; i2 < i; i2++) {
            this.f55905a9[i2].mo210682a8(ab0Var, c0131be, false);
        }
        this.f55906b0 = 0;
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        return this.f55897a1 - ((e11) obj).f55897a1;
    }

    public final String toString() {
        return "" + this.f55897a1;
    }
}
