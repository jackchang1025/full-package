package p000;

import android.text.TextUtils;
import androidx.work.ExistingWorkPolicy;
import androidx.work.impl.C0096a0;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class dg1 extends AbstractC1117qo {

    /* renamed from: a8 */
    public final C0096a0 f55741a8;

    /* renamed from: a9 */
    public final String f55742a9;

    /* renamed from: b0 */
    public final ExistingWorkPolicy f55743b0;

    /* renamed from: b1 */
    public final List f55744b1;

    /* renamed from: b2 */
    public final ArrayList f55745b2;

    /* renamed from: b3 */
    public final ArrayList f55746b3 = new ArrayList();

    /* renamed from: b4 */
    public boolean f55747b4;

    /* renamed from: b5 */
    public eo0 f55748b5;

    static {
        C1351vv.m214966b1("WorkContinuationImpl");
    }

    public dg1(C0096a0 c0096a0, String str, ExistingWorkPolicy existingWorkPolicy, List list, int i) {
        this.f55741a8 = c0096a0;
        this.f55742a9 = str;
        this.f55743b0 = existingWorkPolicy;
        this.f55744b1 = list;
        this.f55745b2 = new ArrayList(list.size());
        for (int i2 = 0; i2 < list.size(); i2++) {
            String string = ((tg1) list.get(i2)).f60219a0.toString();
            t60.m214694b5(string, "id.toString()");
            this.f55745b2.add(string);
            this.f55746b3.add(string);
        }
    }

    /* renamed from: g5 */
    public static HashSet m212599g5(dg1 dg1Var) {
        HashSet hashSet = new HashSet();
        dg1Var.getClass();
        return hashSet;
    }

    /* renamed from: g4 */
    public final eo0 m212600g4() {
        if (this.f55747b4) {
            C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
            TextUtils.join(", ", this.f55745b2);
            c1351vvM214963a5.getClass();
        } else {
            eo0 eo0Var = new eo0(1);
            this.f55741a8.f45560a7.m214272b6(new RunnableC1419xj(this, eo0Var));
            this.f55748b5 = eo0Var;
        }
        return this.f55748b5;
    }
}
