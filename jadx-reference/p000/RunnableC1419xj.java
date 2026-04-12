package p000;

import android.database.Cursor;
import android.os.Build;
import android.text.TextUtils;
import androidx.work.ExistingWorkPolicy;
import androidx.work.WorkInfo$State;
import androidx.work.impl.C0096a0;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkDatabase_Impl;
import androidx.work.impl.background.systemalarm.RescheduleReceiver;
import androidx.work.impl.workers.ConstraintTrackingWorker;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: xj */
/* loaded from: classes2.dex */
public final class RunnableC1419xj implements Runnable {

    /* renamed from: a0 */
    public final dg1 f61137a0;

    /* renamed from: a1 */
    public final eo0 f61138a1;

    static {
        C1351vv.m214966b1("EnqueueRunnable");
    }

    public RunnableC1419xj(dg1 dg1Var, eo0 eo0Var) {
        this.f61137a0 = dg1Var;
        this.f61138a1 = eo0Var;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0271  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x02be  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x02ec A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:174:0x0158 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x013f  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x01a9  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x01be  */
    /* JADX WARN: Type inference failed for: r15v5, types: [java.util.List] */
    /* renamed from: a0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean m215192a0(dg1 dg1Var) throws Throwable {
        boolean z;
        boolean z2;
        boolean z3;
        C0096a0 c0096a0;
        boolean z4;
        WorkInfo$State workInfo$State;
        WorkDatabase workDatabase;
        boolean z5;
        boolean z6;
        Iterator it;
        boolean z7;
        boolean z8;
        wg1 wg1Var;
        boolean z9;
        wg1 wg1VarM215065a1;
        boolean z10;
        boolean z11;
        dg1 dg1Var2 = dg1Var;
        HashSet hashSetM212599g5 = dg1.m212599g5(dg1Var2);
        C0096a0 c0096a02 = dg1Var2.f55741a8;
        List list = dg1Var2.f55744b1;
        String[] strArr = (String[]) hashSetM212599g5.toArray(new String[0]);
        String str = dg1Var2.f55742a9;
        ExistingWorkPolicy existingWorkPolicy = dg1Var2.f55743b0;
        long jCurrentTimeMillis = System.currentTimeMillis();
        WorkDatabase workDatabase2 = c0096a02.f45559a6;
        boolean z12 = strArr != null && strArr.length > 0;
        WorkInfo$State workInfo$State2 = WorkInfo$State.f45528a2;
        WorkInfo$State workInfo$State3 = WorkInfo$State.f45531a5;
        WorkInfo$State workInfo$State4 = WorkInfo$State.f45529a3;
        if (z12) {
            int length = strArr.length;
            int i = 0;
            z2 = false;
            z3 = false;
            z = true;
            while (i < length) {
                List list2 = list;
                wg1 wg1VarM215185a8 = workDatabase2.mo210465b9().m215185a8(strArr[i]);
                if (wg1VarM215185a8 == null) {
                    C1351vv.m214963a5().getClass();
                    break;
                }
                WorkInfo$State workInfo$State5 = wg1VarM215185a8.f60913a1;
                z &= workInfo$State5 == workInfo$State2;
                if (workInfo$State5 == workInfo$State4) {
                    z3 = true;
                } else if (workInfo$State5 == workInfo$State3) {
                    z2 = true;
                }
                i++;
                list = list2;
            }
        } else {
            z = true;
            z2 = false;
            z3 = false;
        }
        List list3 = list;
        boolean zIsEmpty = TextUtils.isEmpty(str);
        WorkInfo$State workInfo$State6 = WorkInfo$State.f45526a0;
        if (zIsEmpty || z12) {
            c0096a0 = c0096a02;
            z4 = zIsEmpty;
            workInfo$State = workInfo$State6;
            workDatabase = workDatabase2;
            z5 = false;
            z6 = z5;
            it = list3.iterator();
            z7 = z6;
            while (it.hasNext()) {
                tg1 tg1Var = (tg1) it.next();
                wg1 wg1Var2 = tg1Var.f60220a1;
                UUID uuid = tg1Var.f60219a0;
                if (!z12 || z) {
                    wg1Var2.f60925b3 = jCurrentTimeMillis;
                } else if (z3) {
                    wg1Var2.f60913a1 = workInfo$State4;
                } else if (z2) {
                    wg1Var2.f60913a1 = workInfo$State3;
                } else {
                    wg1Var2.f60913a1 = WorkInfo$State.f45530a4;
                }
                WorkInfo$State workInfo$State7 = workInfo$State;
                if (wg1Var2.f60913a1 == workInfo$State7) {
                    z7 = true;
                }
                xg1 xg1VarMo210465b9 = workDatabase.mo210465b9();
                C0096a0 c0096a03 = c0096a0;
                Iterator it2 = it;
                t60.m214695b6(c0096a03.f45561a8, "schedulers");
                try {
                    if (Build.VERSION.SDK_INT < 26) {
                        C0836lv c0836lv = wg1Var2.f60921a9;
                        String str2 = wg1Var2.f60914a2;
                        z9 = z7;
                        if (t60.m214686a2(str2, ConstraintTrackingWorker.class.getName()) || !(c0836lv.f58196a3 || c0836lv.f58197a4)) {
                            wg1Var = wg1Var2;
                        } else {
                            C1105qc c1105qc = new C1105qc(0);
                            c1105qc.m214373a1(wg1Var2.f60916a4.f59468a0);
                            c1105qc.f59459a0.put("androidx.work.impl.workers.ConstraintTrackingWorker.ARGUMENT_CLASS_NAME", str2);
                            C1106qd c1106qd = new C1106qd(c1105qc.f59459a0);
                            C1106qd.m214381a1(c1106qd);
                            wg1VarM215065a1 = wg1.m215065a1(wg1Var2, null, null, ConstraintTrackingWorker.class.getName(), c1106qd, 0, 0L, 0, 1048555);
                            WorkDatabase_Impl workDatabase_Impl = (WorkDatabase_Impl) xg1VarMo210465b9.f61125a0;
                            workDatabase_Impl.m212857a1();
                            workDatabase_Impl.m212858a2();
                            ((C1216sb) xg1VarMo210465b9.f61126a1).m214590a5(wg1VarM215065a1);
                            workDatabase_Impl.m212863b2();
                            workDatabase_Impl.m212860a9();
                            if (z12) {
                                int length2 = strArr.length;
                                int i2 = 0;
                                while (i2 < length2) {
                                    String str3 = strArr[i2];
                                    String[] strArr2 = strArr;
                                    String string = uuid.toString();
                                    t60.m214694b5(string, "id.toString()");
                                    C1200rz c1200rz = new C1200rz(string, str3);
                                    C1217sc c1217scMo210460b4 = workDatabase.mo210460b4();
                                    WorkDatabase_Impl workDatabase_Impl2 = (WorkDatabase_Impl) c1217scMo210460b4.f59951a1;
                                    workDatabase_Impl2.m212857a1();
                                    workDatabase_Impl2.m212858a2();
                                    try {
                                        ((C1216sb) c1217scMo210460b4.f59952a2).m214590a5(c1200rz);
                                        workDatabase_Impl2.m212863b2();
                                        workDatabase_Impl2.m212860a9();
                                        i2++;
                                        strArr = strArr2;
                                    } finally {
                                    }
                                }
                            }
                            String[] strArr3 = strArr;
                            zg1 zg1VarMo210466c0 = workDatabase.mo210466c0();
                            String string2 = uuid.toString();
                            t60.m214694b5(string2, "id.toString()");
                            zg1VarMo210466c0.m215412a8(string2, tg1Var.f60221a2);
                            if (z4) {
                                og1 og1VarMo210463b7 = workDatabase.mo210463b7();
                                String string3 = uuid.toString();
                                t60.m214694b5(string3, "id.toString()");
                                ng1 ng1Var = new ng1(str, string3);
                                WorkDatabase_Impl workDatabase_Impl3 = (WorkDatabase_Impl) og1VarMo210463b7.f58832a0;
                                workDatabase_Impl3.m212857a1();
                                workDatabase_Impl3.m212858a2();
                                try {
                                    ((C1216sb) og1VarMo210463b7.f58833a1).m214590a5(ng1Var);
                                    workDatabase_Impl3.m212863b2();
                                } finally {
                                }
                            }
                            workInfo$State = workInfo$State7;
                            c0096a0 = c0096a03;
                            it = it2;
                            strArr = strArr3;
                            z7 = z9;
                        }
                    } else {
                        wg1Var = wg1Var2;
                        z9 = z7;
                    }
                    ((C1216sb) xg1VarMo210465b9.f61126a1).m214590a5(wg1VarM215065a1);
                    workDatabase_Impl.m212863b2();
                    workDatabase_Impl.m212860a9();
                    if (z12) {
                    }
                    String[] strArr32 = strArr;
                    zg1 zg1VarMo210466c02 = workDatabase.mo210466c0();
                    String string22 = uuid.toString();
                    t60.m214694b5(string22, "id.toString()");
                    zg1VarMo210466c02.m215412a8(string22, tg1Var.f60221a2);
                    if (z4) {
                    }
                    workInfo$State = workInfo$State7;
                    c0096a0 = c0096a03;
                    it = it2;
                    strArr = strArr32;
                    z7 = z9;
                } finally {
                }
                wg1VarM215065a1 = wg1Var;
                WorkDatabase_Impl workDatabase_Impl4 = (WorkDatabase_Impl) xg1VarMo210465b9.f61125a0;
                workDatabase_Impl4.m212857a1();
                workDatabase_Impl4.m212858a2();
            }
            z8 = true;
            dg1Var2 = dg1Var;
        } else {
            ArrayList arrayListM215186a9 = workDatabase2.mo210465b9().m215186a9(str);
            if (!arrayListM215186a9.isEmpty()) {
                ExistingWorkPolicy existingWorkPolicy2 = ExistingWorkPolicy.f45512a2;
                z4 = zIsEmpty;
                ExistingWorkPolicy existingWorkPolicy3 = ExistingWorkPolicy.f45513a3;
                if (existingWorkPolicy == existingWorkPolicy2 || existingWorkPolicy == existingWorkPolicy3) {
                    C1217sc c1217scMo210460b42 = workDatabase2.mo210460b4();
                    ArrayList arrayList = new ArrayList();
                    workDatabase = workDatabase2;
                    int size = arrayListM215186a9.size();
                    int i3 = 0;
                    while (i3 < size) {
                        Object obj = arrayListM215186a9.get(i3);
                        int i4 = i3 + 1;
                        vg1 vg1Var = (vg1) obj;
                        int i5 = size;
                        String str4 = vg1Var.f60643a0;
                        ArrayList arrayList2 = arrayListM215186a9;
                        WorkDatabase_Impl workDatabase_Impl5 = (WorkDatabase_Impl) c1217scMo210460b42.f59951a1;
                        C1217sc c1217sc = c1217scMo210460b42;
                        C0096a0 c0096a04 = c0096a02;
                        WorkInfo$State workInfo$State8 = workInfo$State6;
                        js0 js0VarAcquire = js0.f57367a8.acquire("SELECT COUNT(*)>0 FROM dependency WHERE prerequisite_id=?", 1);
                        if (str4 == null) {
                            js0VarAcquire.mo213343a9(1);
                        } else {
                            js0VarAcquire.mo213341a6(1, str4);
                        }
                        workDatabase_Impl5.m212857a1();
                        Cursor cursorM213580c7 = kj1.m213580c7(workDatabase_Impl5, js0VarAcquire);
                        try {
                            if (cursorM213580c7.moveToFirst()) {
                                z10 = false;
                                if (cursorM213580c7.getInt(0) != 0) {
                                    z11 = true;
                                }
                                if (!z11) {
                                    WorkInfo$State workInfo$State9 = vg1Var.f60644a1;
                                    boolean z13 = z & (workInfo$State9 == workInfo$State2 ? true : z10);
                                    if (workInfo$State9 == workInfo$State4) {
                                        z3 = true;
                                    } else if (workInfo$State9 == workInfo$State3) {
                                        z2 = true;
                                    }
                                    arrayList.add(vg1Var.f60643a0);
                                    z = z13;
                                }
                                size = i5;
                                i3 = i4;
                                arrayListM215186a9 = arrayList2;
                                c1217scMo210460b42 = c1217sc;
                                c0096a02 = c0096a04;
                                workInfo$State6 = workInfo$State8;
                            } else {
                                z10 = false;
                            }
                            z11 = z10;
                            if (!z11) {
                            }
                            size = i5;
                            i3 = i4;
                            arrayListM215186a9 = arrayList2;
                            c1217scMo210460b42 = c1217sc;
                            c0096a02 = c0096a04;
                            workInfo$State6 = workInfo$State8;
                        } finally {
                            cursorM213580c7.close();
                            js0VarAcquire.m213344b0();
                        }
                    }
                    c0096a0 = c0096a02;
                    workInfo$State = workInfo$State6;
                    z5 = false;
                    ArrayList arrayList3 = arrayList;
                    arrayList3 = arrayList;
                    if (existingWorkPolicy == existingWorkPolicy3 && (z2 || z3)) {
                        xg1 xg1VarMo210465b92 = workDatabase.mo210465b9();
                        ArrayList arrayListM215186a92 = xg1VarMo210465b92.m215186a9(str);
                        int size2 = arrayListM215186a92.size();
                        int i6 = 0;
                        while (i6 < size2) {
                            Object obj2 = arrayListM215186a92.get(i6);
                            i6++;
                            xg1VarMo210465b92.m215178a1(((vg1) obj2).f60643a0);
                        }
                        z2 = false;
                        z3 = false;
                        arrayList3 = Collections.EMPTY_LIST;
                    }
                    strArr = (String[]) arrayList3.toArray(strArr);
                    z12 = strArr.length > 0;
                } else {
                    if (existingWorkPolicy == ExistingWorkPolicy.f45511a1) {
                        int size3 = arrayListM215186a9.size();
                        int i7 = 0;
                        while (i7 < size3) {
                            Object obj3 = arrayListM215186a9.get(i7);
                            i7++;
                            WorkInfo$State workInfo$State10 = ((vg1) obj3).f60644a1;
                            if (workInfo$State10 == workInfo$State6 || workInfo$State10 == WorkInfo$State.f45527a1) {
                                z8 = true;
                                z7 = false;
                            }
                        }
                    }
                    new C0511fw(c0096a02, str, false).run();
                    xg1 xg1VarMo210465b93 = workDatabase2.mo210465b9();
                    int size4 = arrayListM215186a9.size();
                    int i8 = 0;
                    while (i8 < size4) {
                        Object obj4 = arrayListM215186a9.get(i8);
                        i8++;
                        xg1VarMo210465b93.m215178a1(((vg1) obj4).f60643a0);
                    }
                    c0096a0 = c0096a02;
                    workInfo$State = workInfo$State6;
                    workDatabase = workDatabase2;
                    z6 = true;
                    it = list3.iterator();
                    z7 = z6;
                    while (it.hasNext()) {
                    }
                    z8 = true;
                    dg1Var2 = dg1Var;
                }
            }
            z6 = z5;
            it = list3.iterator();
            z7 = z6;
            while (it.hasNext()) {
            }
            z8 = true;
            dg1Var2 = dg1Var;
        }
        dg1Var2.f55747b4 = z8;
        return z7;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean z;
        eo0 eo0Var = this.f61138a1;
        dg1 dg1Var = this.f61137a0;
        C0096a0 c0096a0 = dg1Var.f55741a8;
        try {
            HashSet hashSet = new HashSet();
            hashSet.addAll(dg1Var.f55745b2);
            HashSet hashSetM212599g5 = dg1.m212599g5(dg1Var);
            Iterator it = hashSet.iterator();
            while (true) {
                if (!it.hasNext()) {
                    hashSet.removeAll(dg1Var.f55745b2);
                    z = false;
                    break;
                } else if (hashSetM212599g5.contains((String) it.next())) {
                    z = true;
                    break;
                }
            }
            if (z) {
                throw new IllegalStateException("WorkContinuation has cycles (" + dg1Var + ")");
            }
            WorkDatabase workDatabase = c0096a0.f45559a6;
            workDatabase.m212858a2();
            try {
                boolean zM215192a0 = m215192a0(dg1Var);
                workDatabase.m212863b2();
                if (zM215192a0) {
                    xl0.m215196a0(c0096a0.f45557a4, RescheduleReceiver.class, true);
                    fu0.m212865a0(c0096a0.f45558a5, c0096a0.f45559a6, c0096a0.f45561a8);
                }
                eo0Var.m212713b7(eo0.f56085a3);
            } finally {
                workDatabase.m212860a9();
            }
        } catch (Throwable th) {
            eo0Var.m212713b7(new ml0(th));
        }
    }
}
