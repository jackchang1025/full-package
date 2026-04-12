package p000;

import android.view.View;
import android.view.animation.Interpolator;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class nc1 {

    /* renamed from: a2 */
    public Interpolator f58499a2;

    /* renamed from: a3 */
    public oc1 f58500a3;

    /* renamed from: a4 */
    public boolean f58501a4;

    /* renamed from: a1 */
    public long f58498a1 = -1;

    /* renamed from: a5 */
    public final e71 f58502a5 = new e71(this);

    /* renamed from: a0 */
    public final ArrayList f58497a0 = new ArrayList();

    /* renamed from: a0 */
    public final void m214070a0() {
        if (this.f58501a4) {
            ArrayList arrayList = this.f58497a0;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((mc1) obj).m213968a1();
            }
            this.f58501a4 = false;
        }
    }

    /* renamed from: a1 */
    public final void m214071a1() {
        View view;
        if (this.f58501a4) {
            return;
        }
        ArrayList arrayList = this.f58497a0;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            mc1 mc1Var = (mc1) obj;
            long j = this.f58498a1;
            if (j >= 0) {
                mc1Var.m213969a2(j);
            }
            Interpolator interpolator = this.f58499a2;
            if (interpolator != null && (view = (View) mc1Var.f58331a0.get()) != null) {
                view.animate().setInterpolator(interpolator);
            }
            if (this.f58500a3 != null) {
                mc1Var.m213970a3(this.f58502a5);
            }
            View view2 = (View) mc1Var.f58331a0.get();
            if (view2 != null) {
                view2.animate().start();
            }
        }
        this.f58501a4 = true;
    }
}
