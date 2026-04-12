package p000;

import android.content.Context;
import android.database.Cursor;
import androidx.work.WorkInfo$State;
import androidx.work.WorkerParameters;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkDatabase_Impl;
import androidx.work.impl.background.systemalarm.RescheduleReceiver;
import androidx.work.impl.utils.futures.C0100a1;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class fh1 implements Runnable {

    /* renamed from: b7 */
    public static final /* synthetic */ int f56257b7 = 0;

    /* renamed from: a0 */
    public final Context f56258a0;

    /* renamed from: a1 */
    public final String f56259a1;

    /* renamed from: a2 */
    public final List f56260a2;

    /* renamed from: a3 */
    public final wg1 f56261a3;

    /* renamed from: a4 */
    public tb0 f56262a4;

    /* renamed from: a5 */
    public final pg1 f56263a5;

    /* renamed from: a7 */
    public final C0793kr f56265a7;

    /* renamed from: a8 */
    public final so0 f56266a8;

    /* renamed from: a9 */
    public final WorkDatabase f56267a9;

    /* renamed from: b0 */
    public final xg1 f56268b0;

    /* renamed from: b1 */
    public final C1217sc f56269b1;

    /* renamed from: b2 */
    public final ArrayList f56270b2;

    /* renamed from: b3 */
    public String f56271b3;

    /* renamed from: b6 */
    public volatile boolean f56274b6;

    /* renamed from: a6 */
    public sb0 f56264a6 = new pb0();

    /* renamed from: b4 */
    public final C0100a1 f56272b4 = new C0100a1();

    /* renamed from: b5 */
    public final C0100a1 f56273b5 = new C0100a1();

    static {
        C1351vv.m214966b1("WorkerWrapper");
    }

    public fh1(C0502fn c0502fn) {
        this.f56258a0 = (Context) c0502fn.f56291a0;
        this.f56263a5 = (pg1) c0502fn.f56293a2;
        this.f56266a8 = (so0) c0502fn.f56292a1;
        wg1 wg1Var = (wg1) c0502fn.f56296a5;
        this.f56261a3 = wg1Var;
        this.f56259a1 = wg1Var.f60912a0;
        this.f56260a2 = (List) c0502fn.f56297a6;
        this.f56262a4 = null;
        this.f56265a7 = (C0793kr) c0502fn.f56294a3;
        WorkDatabase workDatabase = (WorkDatabase) c0502fn.f56295a4;
        this.f56267a9 = workDatabase;
        this.f56268b0 = workDatabase.mo210465b9();
        this.f56269b1 = workDatabase.mo210460b4();
        this.f56270b2 = (ArrayList) c0502fn.f56298a7;
    }

    /* renamed from: a0 */
    public final void m212813a0(sb0 sb0Var) {
        boolean z = sb0Var instanceof rb0;
        wg1 wg1Var = this.f56261a3;
        if (!z) {
            if (sb0Var instanceof qb0) {
                C1351vv.m214963a5().getClass();
                m212815a2();
                return;
            }
            C1351vv.m214963a5().getClass();
            if (wg1Var.m215068a3()) {
                m212816a3();
                return;
            } else {
                m212819a6();
                return;
            }
        }
        C1351vv.m214963a5().getClass();
        if (wg1Var.m215068a3()) {
            m212816a3();
            return;
        }
        C1217sc c1217sc = this.f56269b1;
        String str = this.f56259a1;
        xg1 xg1Var = this.f56268b0;
        WorkDatabase workDatabase = this.f56267a9;
        workDatabase.m212858a2();
        try {
            xg1Var.m215191b4(WorkInfo$State.f45528a2, str);
            xg1Var.m215190b3(str, ((rb0) this.f56264a6).f59666a0);
            long jCurrentTimeMillis = System.currentTimeMillis();
            ArrayList arrayListM214593a3 = c1217sc.m214593a3(str);
            int size = arrayListM214593a3.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayListM214593a3.get(i);
                i++;
                String str2 = (String) obj;
                if (xg1Var.m215183a6(str2) == WorkInfo$State.f45530a4 && c1217sc.m214594a5(str2)) {
                    C1351vv.m214963a5().getClass();
                    xg1Var.m215191b4(WorkInfo$State.f45526a0, str2);
                    xg1Var.m215189b2(str2, jCurrentTimeMillis);
                }
            }
            workDatabase.m212863b2();
            workDatabase.m212860a9();
            m212817a4(false);
        } catch (Throwable th) {
            workDatabase.m212860a9();
            m212817a4(false);
            throw th;
        }
    }

    /* renamed from: a1 */
    public final void m212814a1() {
        boolean zM212820a7 = m212820a7();
        String str = this.f56259a1;
        WorkDatabase workDatabase = this.f56267a9;
        if (!zM212820a7) {
            workDatabase.m212858a2();
            try {
                WorkInfo$State workInfo$StateM215183a6 = this.f56268b0.m215183a6(str);
                workDatabase.mo210464b8().m214268b2(str);
                if (workInfo$StateM215183a6 == null) {
                    m212817a4(false);
                } else if (workInfo$StateM215183a6 == WorkInfo$State.f45527a1) {
                    m212813a0(this.f56264a6);
                } else if (!workInfo$StateM215183a6.m210457a0()) {
                    m212815a2();
                }
                workDatabase.m212863b2();
                workDatabase.m212860a9();
            } catch (Throwable th) {
                workDatabase.m212860a9();
                throw th;
            }
        }
        List list = this.f56260a2;
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ((du0) it.next()).mo212640a0(str);
            }
            fu0.m212865a0(this.f56265a7, workDatabase, list);
        }
    }

    /* renamed from: a2 */
    public final void m212815a2() {
        String str = this.f56259a1;
        xg1 xg1Var = this.f56268b0;
        WorkDatabase workDatabase = this.f56267a9;
        workDatabase.m212858a2();
        try {
            xg1Var.m215191b4(WorkInfo$State.f45526a0, str);
            xg1Var.m215189b2(str, System.currentTimeMillis());
            xg1Var.m215187b0(str, -1L);
            workDatabase.m212863b2();
        } finally {
            workDatabase.m212860a9();
            m212817a4(true);
        }
    }

    /* renamed from: a3 */
    public final void m212816a3() {
        String str = this.f56259a1;
        xg1 xg1Var = this.f56268b0;
        WorkDatabase workDatabase = this.f56267a9;
        workDatabase.m212858a2();
        try {
            xg1Var.m215189b2(str, System.currentTimeMillis());
            WorkDatabase_Impl workDatabase_Impl = (WorkDatabase_Impl) xg1Var.f61125a0;
            xg1Var.m215191b4(WorkInfo$State.f45526a0, str);
            workDatabase_Impl.m212857a1();
            w31 w31Var = (w31) xg1Var.f61134a9;
            u00 u00VarM210428a0 = w31Var.m210428a0();
            if (str == null) {
                u00VarM210428a0.mo213343a9(1);
            } else {
                u00VarM210428a0.mo213341a6(1, str);
            }
            workDatabase_Impl.m212858a2();
            try {
                u00VarM210428a0.m214812a0();
                workDatabase_Impl.m212863b2();
                workDatabase_Impl.m212860a9();
                w31Var.m210431a3(u00VarM210428a0);
                workDatabase_Impl.m212857a1();
                w31Var = (w31) xg1Var.f61130a5;
                u00VarM210428a0 = w31Var.m210428a0();
                if (str == null) {
                    u00VarM210428a0.mo213343a9(1);
                } else {
                    u00VarM210428a0.mo213341a6(1, str);
                }
                workDatabase_Impl.m212858a2();
                try {
                    u00VarM210428a0.m214812a0();
                    workDatabase_Impl.m212863b2();
                    workDatabase_Impl.m212860a9();
                    w31Var.m210431a3(u00VarM210428a0);
                    xg1Var.m215187b0(str, -1L);
                    workDatabase.m212863b2();
                } finally {
                }
            } finally {
            }
        } finally {
            workDatabase.m212860a9();
            m212817a4(false);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0032  */
    /* renamed from: a4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m212817a4(boolean z) {
        boolean z2;
        boolean zContainsKey;
        this.f56267a9.m212858a2();
        try {
            xg1 xg1VarMo210465b9 = this.f56267a9.mo210465b9();
            xg1VarMo210465b9.getClass();
            js0 js0VarAcquire = js0.f57367a8.acquire("SELECT COUNT(*) > 0 FROM workspec WHERE state NOT IN (2, 3, 5) LIMIT 1", 0);
            WorkDatabase_Impl workDatabase_Impl = (WorkDatabase_Impl) xg1VarMo210465b9.f61125a0;
            workDatabase_Impl.m212857a1();
            Cursor cursorM213580c7 = kj1.m213580c7(workDatabase_Impl, js0VarAcquire);
            try {
                if (cursorM213580c7.moveToFirst()) {
                    z2 = cursorM213580c7.getInt(0) != 0;
                }
                if (!z2) {
                    xl0.m215196a0(this.f56258a0, RescheduleReceiver.class, false);
                }
                if (z) {
                    this.f56268b0.m215191b4(WorkInfo$State.f45526a0, this.f56259a1);
                    this.f56268b0.m215187b0(this.f56259a1, -1L);
                }
                if (this.f56261a3 != null && this.f56262a4 != null) {
                    so0 so0Var = this.f56266a8;
                    String str = this.f56259a1;
                    synchronized (so0Var.f60049b1) {
                        zContainsKey = so0Var.f60043a5.containsKey(str);
                    }
                    if (zContainsKey) {
                        so0 so0Var2 = this.f56266a8;
                        String str2 = this.f56259a1;
                        synchronized (so0Var2.f60049b1) {
                            so0Var2.f60043a5.remove(str2);
                            so0Var2.m214656a7();
                        }
                    }
                }
                this.f56267a9.m212863b2();
                this.f56267a9.m212860a9();
                this.f56272b4.m210484a8(Boolean.valueOf(z));
            } finally {
                cursorM213580c7.close();
                js0VarAcquire.m213344b0();
            }
        } catch (Throwable th) {
            this.f56267a9.m212860a9();
            throw th;
        }
    }

    /* renamed from: a5 */
    public final void m212818a5() {
        WorkInfo$State workInfo$StateM215183a6 = this.f56268b0.m215183a6(this.f56259a1);
        if (workInfo$StateM215183a6 == WorkInfo$State.f45527a1) {
            C1351vv.m214963a5().getClass();
            m212817a4(true);
        } else {
            C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
            Objects.toString(workInfo$StateM215183a6);
            c1351vvM214963a5.getClass();
            m212817a4(false);
        }
    }

    /* renamed from: a6 */
    public final void m212819a6() {
        String str = this.f56259a1;
        WorkDatabase workDatabase = this.f56267a9;
        workDatabase.m212858a2();
        try {
            LinkedList linkedList = new LinkedList();
            linkedList.add(str);
            while (true) {
                boolean zIsEmpty = linkedList.isEmpty();
                xg1 xg1Var = this.f56268b0;
                if (zIsEmpty) {
                    xg1Var.m215190b3(str, ((pb0) this.f56264a6).f59186a0);
                    workDatabase.m212863b2();
                    return;
                } else {
                    String str2 = (String) linkedList.remove();
                    if (xg1Var.m215183a6(str2) != WorkInfo$State.f45531a5) {
                        xg1Var.m215191b4(WorkInfo$State.f45529a3, str2);
                    }
                    linkedList.addAll(this.f56269b1.m214593a3(str2));
                }
            }
        } finally {
            workDatabase.m212860a9();
            m212817a4(false);
        }
    }

    /* renamed from: a7 */
    public final boolean m212820a7() {
        if (!this.f56274b6) {
            return false;
        }
        C1351vv.m214963a5().getClass();
        if (this.f56268b0.m215183a6(this.f56259a1) == null) {
            m212817a4(false);
            return true;
        }
        m212817a4(!r0.m210457a0());
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x007d A[Catch: all -> 0x0067, TryCatch #2 {all -> 0x0067, blocks: (B:13:0x004e, B:16:0x0056, B:21:0x006a, B:23:0x0070, B:25:0x0074, B:35:0x009a, B:30:0x007d, B:32:0x0089), top: B:105:0x004e }] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        boolean z;
        c60 c60Var;
        StringBuilder sb = new StringBuilder("Work [ id=");
        String str = this.f56259a1;
        sb.append(str);
        sb.append(", tags={ ");
        ArrayList arrayList = this.f56270b2;
        int size = arrayList.size();
        int i = 0;
        boolean z2 = true;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            String str2 = (String) obj;
            if (z2) {
                z2 = false;
            } else {
                sb.append(", ");
            }
            sb.append(str2);
        }
        sb.append(" } ]");
        this.f56271b3 = sb.toString();
        wg1 wg1Var = this.f56261a3;
        if (m212820a7()) {
            return;
        }
        WorkDatabase workDatabase = this.f56267a9;
        workDatabase.m212858a2();
        try {
            WorkInfo$State workInfo$State = wg1Var.f60913a1;
            C1106qd c1106qdMo210452a0 = wg1Var.f60916a4;
            WorkInfo$State workInfo$State2 = WorkInfo$State.f45526a0;
            if (workInfo$State != workInfo$State2) {
                m212818a5();
                workDatabase.m212863b2();
                C1351vv.m214963a5().getClass();
                return;
            }
            if (!wg1Var.m215068a3()) {
                if (wg1Var.f60913a1 == workInfo$State2 && wg1Var.f60922b0 > 0) {
                }
            } else if (System.currentTimeMillis() < wg1Var.m215066a0()) {
                C1351vv.m214963a5().getClass();
                m212817a4(true);
                workDatabase.m212863b2();
                return;
            }
            workDatabase.m212863b2();
            workDatabase.m212860a9();
            boolean zM215068a3 = wg1Var.m215068a3();
            xg1 xg1Var = this.f56268b0;
            C0793kr c0793kr = this.f56265a7;
            if (!zM215068a3) {
                C1351vv c1351vv = (C1351vv) c0793kr.f57712a5;
                String str3 = wg1Var.f60915a3;
                c1351vv.getClass();
                int i2 = c60.f46074a0;
                try {
                    c60Var = (c60) Class.forName(str3).getDeclaredConstructor(null).newInstance(null);
                } catch (Exception unused) {
                    C1351vv.m214963a5().getClass();
                    c60Var = null;
                }
                if (c60Var == null) {
                    C1351vv.m214963a5().getClass();
                    m212819a6();
                    return;
                }
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add(c1106qdMo210452a0);
                WorkDatabase_Impl workDatabase_Impl = (WorkDatabase_Impl) xg1Var.f61125a0;
                js0 js0VarAcquire = js0.f57367a8.acquire("SELECT output FROM workspec WHERE id IN\n             (SELECT prerequisite_id FROM dependency WHERE work_spec_id=?)", 1);
                if (str == null) {
                    js0VarAcquire.mo213343a9(1);
                } else {
                    js0VarAcquire.mo213341a6(1, str);
                }
                workDatabase_Impl.m212857a1();
                Cursor cursorM213580c7 = kj1.m213580c7(workDatabase_Impl, js0VarAcquire);
                try {
                    ArrayList arrayList3 = new ArrayList(cursorM213580c7.getCount());
                    while (cursorM213580c7.moveToNext()) {
                        arrayList3.add(C1106qd.m214380a0(cursorM213580c7.isNull(0) ? null : cursorM213580c7.getBlob(0)));
                    }
                    cursorM213580c7.close();
                    js0VarAcquire.m213344b0();
                    arrayList2.addAll(arrayList3);
                    c1106qdMo210452a0 = c60Var.mo210452a0(arrayList2);
                } catch (Throwable th) {
                    cursorM213580c7.close();
                    js0VarAcquire.m213344b0();
                    throw th;
                }
            }
            UUID uuidFromString = UUID.fromString(str);
            ExecutorService executorService = (ExecutorService) c0793kr.f57709a2;
            dh1 dh1Var = (dh1) c0793kr.f57711a4;
            so0 so0Var = this.f56266a8;
            pg1 pg1Var = this.f56263a5;
            ig1 ig1Var = new ig1(workDatabase, so0Var, pg1Var);
            WorkerParameters workerParameters = new WorkerParameters();
            workerParameters.f45534a0 = uuidFromString;
            workerParameters.f45535a1 = c1106qdMo210452a0;
            new HashSet(arrayList);
            workerParameters.f45536a2 = executorService;
            workerParameters.f45537a3 = pg1Var;
            workerParameters.f45538a4 = dh1Var;
            if (this.f56262a4 == null) {
                String str4 = wg1Var.f60914a2;
                dh1Var.getClass();
                this.f56262a4 = dh1.m212607a0(this.f56258a0, str4, workerParameters);
            }
            tb0 tb0Var = this.f56262a4;
            if (tb0Var == null) {
                C1351vv.m214963a5().getClass();
                m212819a6();
                return;
            }
            if (tb0Var.f60193a3) {
                C1351vv.m214963a5().getClass();
                m212819a6();
                return;
            }
            tb0Var.f60193a3 = true;
            workDatabase.m212858a2();
            try {
                if (xg1Var.m215183a6(str) == workInfo$State2) {
                    xg1Var.m215191b4(WorkInfo$State.f45527a1, str);
                    WorkDatabase_Impl workDatabase_Impl2 = (WorkDatabase_Impl) xg1Var.f61125a0;
                    workDatabase_Impl2.m212857a1();
                    w31 w31Var = (w31) xg1Var.f61133a8;
                    u00 u00VarM210428a0 = w31Var.m210428a0();
                    if (str == null) {
                        u00VarM210428a0.mo213343a9(1);
                    } else {
                        u00VarM210428a0.mo213341a6(1, str);
                    }
                    workDatabase_Impl2.m212858a2();
                    try {
                        u00VarM210428a0.m214812a0();
                        workDatabase_Impl2.m212863b2();
                        workDatabase_Impl2.m212860a9();
                        w31Var.m210431a3(u00VarM210428a0);
                        z = true;
                    } catch (Throwable th2) {
                        workDatabase_Impl2.m212860a9();
                        w31Var.m210431a3(u00VarM210428a0);
                        throw th2;
                    }
                } else {
                    z = false;
                }
                workDatabase.m212863b2();
                if (!z) {
                    m212818a5();
                    return;
                }
                if (m212820a7()) {
                    return;
                }
                gg1 gg1Var = new gg1(this.f56258a0, this.f56261a3, this.f56262a4, ig1Var, this.f56263a5);
                ((mg1) pg1Var.f59231a3).execute(gg1Var);
                C0100a1 c0100a1 = gg1Var.f56463a0;
                RunnableC1052p1 runnableC1052p1 = new RunnableC1052p1(this, 16, c0100a1);
                ExecutorC0101ao executorC0101ao = new ExecutorC0101ao(1);
                C0100a1 c0100a12 = this.f56273b5;
                c0100a12.mo210459a0(runnableC1052p1, executorC0101ao);
                c0100a1.mo210459a0(new RunnableC0884n2(this, 17, c0100a1), (mg1) pg1Var.f59231a3);
                c0100a12.mo210459a0(new RunnableC0165ca(this, this.f56271b3), (ExecutorC0034an) pg1Var.f59229a1);
            } catch (Throwable th3) {
                throw th3;
            }
        } finally {
            workDatabase.m212860a9();
        }
    }
}
