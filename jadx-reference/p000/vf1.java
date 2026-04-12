package p000;

import android.os.Build;
import android.view.View;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public class vf1 {

    /* renamed from: a1 */
    public static final xf1 f60623a1;

    /* renamed from: a0 */
    public final xf1 f60624a0;

    static {
        int i = Build.VERSION.SDK_INT;
        f60623a1 = (i >= 30 ? new of1() : i >= 29 ? new mf1() : new lf1()).mo213836a1().f61102a0.mo214611a0().f61102a0.mo214535a1().f61102a0.mo214536a2();
    }

    public vf1(xf1 xf1Var) {
        this.f60624a0 = xf1Var;
    }

    /* renamed from: a0 */
    public xf1 mo214611a0() {
        return this.f60624a0;
    }

    /* renamed from: a1 */
    public xf1 mo214535a1() {
        return this.f60624a0;
    }

    /* renamed from: a2 */
    public xf1 mo214536a2() {
        return this.f60624a0;
    }

    /* renamed from: a4 */
    public C1264tl mo214612a4() {
        return null;
    }

    /* renamed from: a5 */
    public f60 mo214391a5(int i) {
        return f60.f56153a4;
    }

    /* renamed from: a6 */
    public f60 mo214739a6() {
        return mo214392a9();
    }

    /* renamed from: a7 */
    public f60 mo214537a7() {
        return f60.f56153a4;
    }

    /* renamed from: a8 */
    public f60 mo214740a8() {
        return mo214392a9();
    }

    /* renamed from: a9 */
    public f60 mo214392a9() {
        return f60.f56153a4;
    }

    /* renamed from: b0 */
    public f60 mo214741b0() {
        return mo214392a9();
    }

    /* renamed from: b1 */
    public xf1 mo214393b1(int i, int i2, int i3, int i4) {
        return f60623a1;
    }

    /* renamed from: b2 */
    public boolean mo214538b2() {
        return false;
    }

    /* renamed from: b3 */
    public boolean mo214394b3() {
        return false;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof vf1)) {
            return false;
        }
        vf1 vf1Var = (vf1) obj;
        return mo214394b3() == vf1Var.mo214394b3() && mo214538b2() == vf1Var.mo214538b2() && tk0.m214759a0(mo214392a9(), vf1Var.mo214392a9()) && tk0.m214759a0(mo214537a7(), vf1Var.mo214537a7()) && tk0.m214759a0(mo214612a4(), vf1Var.mo214612a4());
    }

    public int hashCode() {
        return tk0.m214760a1(Boolean.valueOf(mo214394b3()), Boolean.valueOf(mo214538b2()), mo214392a9(), mo214537a7(), mo214612a4());
    }

    /* renamed from: a3 */
    public void mo214390a3(View view) {
    }

    /* renamed from: b4 */
    public void mo214395b4(f60[] f60VarArr) {
    }

    /* renamed from: b5 */
    public void mo214396b5(xf1 xf1Var) {
    }

    /* renamed from: b6 */
    public void mo214539b6(f60 f60Var) {
    }
}
