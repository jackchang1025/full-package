package p000;

import androidx.work.WorkInfo$State;
import androidx.work.impl.C0096a0;
import androidx.work.impl.WorkDatabase;
import java.util.Iterator;
import java.util.LinkedList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: fx */
/* loaded from: classes2.dex */
public abstract class AbstractRunnableC0512fx implements Runnable {

    /* renamed from: a0 */
    public final eo0 f56340a0 = new eo0(1);

    /* renamed from: a0 */
    public static void m212867a0(C0096a0 c0096a0, String str) {
        fh1 fh1Var;
        boolean z;
        WorkDatabase workDatabase = c0096a0.f45559a6;
        xg1 xg1VarMo210465b9 = workDatabase.mo210465b9();
        C1217sc c1217scMo210460b4 = workDatabase.mo210460b4();
        LinkedList linkedList = new LinkedList();
        linkedList.add(str);
        while (!linkedList.isEmpty()) {
            String str2 = (String) linkedList.remove();
            WorkInfo$State workInfo$StateM215183a6 = xg1VarMo210465b9.m215183a6(str2);
            if (workInfo$StateM215183a6 != WorkInfo$State.f45528a2 && workInfo$StateM215183a6 != WorkInfo$State.f45529a3) {
                xg1VarMo210465b9.m215191b4(WorkInfo$State.f45531a5, str2);
            }
            linkedList.addAll(c1217scMo210460b4.m214593a3(str2));
        }
        so0 so0Var = c0096a0.f45562a9;
        synchronized (so0Var.f60049b1) {
            try {
                C1351vv.m214963a5().getClass();
                so0Var.f60047a9.add(str);
                fh1Var = (fh1) so0Var.f60043a5.remove(str);
                z = fh1Var != null;
                if (fh1Var == null) {
                    fh1Var = (fh1) so0Var.f60044a6.remove(str);
                }
                if (fh1Var != null) {
                    so0Var.f60045a7.remove(str);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        so0.m214650a1(fh1Var);
        if (z) {
            so0Var.m214656a7();
        }
        Iterator it = c0096a0.f45561a8.iterator();
        while (it.hasNext()) {
            ((du0) it.next()).mo212640a0(str);
        }
    }

    /* renamed from: a1 */
    public abstract void mo212866a1();

    @Override // java.lang.Runnable
    public final void run() {
        eo0 eo0Var = this.f56340a0;
        try {
            mo212866a1();
            eo0Var.m212713b7(eo0.f56085a3);
        } catch (Throwable th) {
            eo0Var.m212713b7(new ml0(th));
        }
    }
}
