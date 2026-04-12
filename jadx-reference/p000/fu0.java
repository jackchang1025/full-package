package p000;

import androidx.work.impl.WorkDatabase;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class fu0 {

    /* renamed from: a0 */
    public static final /* synthetic */ int f56332a0 = 0;

    static {
        C1351vv.m214966b1("Schedulers");
    }

    /* renamed from: a0 */
    public static void m212865a0(C0793kr c0793kr, WorkDatabase workDatabase, List list) {
        if (list == null || list.size() == 0) {
            return;
        }
        xg1 xg1VarMo210465b9 = workDatabase.mo210465b9();
        workDatabase.m212858a2();
        try {
            ArrayList arrayListM215180a3 = xg1VarMo210465b9.m215180a3(c0793kr.f57708a1);
            ArrayList arrayListM215179a2 = xg1VarMo210465b9.m215179a2();
            if (arrayListM215180a3.size() > 0) {
                long jCurrentTimeMillis = System.currentTimeMillis();
                int size = arrayListM215180a3.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayListM215180a3.get(i);
                    i++;
                    xg1VarMo210465b9.m215187b0(((wg1) obj).f60912a0, jCurrentTimeMillis);
                }
            }
            workDatabase.m212863b2();
            workDatabase.m212860a9();
            if (arrayListM215180a3.size() > 0) {
                wg1[] wg1VarArr = (wg1[]) arrayListM215180a3.toArray(new wg1[arrayListM215180a3.size()]);
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    du0 du0Var = (du0) it.next();
                    if (du0Var.mo212642a5()) {
                        du0Var.mo212641a2(wg1VarArr);
                    }
                }
            }
            if (arrayListM215179a2.size() > 0) {
                wg1[] wg1VarArr2 = (wg1[]) arrayListM215179a2.toArray(new wg1[arrayListM215179a2.size()]);
                Iterator it2 = list.iterator();
                while (it2.hasNext()) {
                    du0 du0Var2 = (du0) it2.next();
                    if (!du0Var2.mo212642a5()) {
                        du0Var2.mo212641a2(wg1VarArr2);
                    }
                }
            }
        } catch (Throwable th) {
            workDatabase.m212860a9();
            throw th;
        }
    }
}
