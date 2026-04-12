package p000;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.foreground.SystemForegroundService;
import androidx.work.impl.utils.futures.C0100a1;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class so0 implements InterfaceC1425xp, InterfaceC1282u {

    /* renamed from: a1 */
    public final Context f60039a1;

    /* renamed from: a2 */
    public final C0793kr f60040a2;

    /* renamed from: a3 */
    public final pg1 f60041a3;

    /* renamed from: a4 */
    public final WorkDatabase f60042a4;

    /* renamed from: a8 */
    public final List f60046a8;

    /* renamed from: a6 */
    public final HashMap f60044a6 = new HashMap();

    /* renamed from: a5 */
    public final HashMap f60043a5 = new HashMap();

    /* renamed from: a9 */
    public final HashSet f60047a9 = new HashSet();

    /* renamed from: b0 */
    public final ArrayList f60048b0 = new ArrayList();

    /* renamed from: a0 */
    public PowerManager.WakeLock f60038a0 = null;

    /* renamed from: b1 */
    public final Object f60049b1 = new Object();

    /* renamed from: a7 */
    public final HashMap f60045a7 = new HashMap();

    static {
        C1351vv.m214966b1("Processor");
    }

    public so0(Context context, C0793kr c0793kr, pg1 pg1Var, WorkDatabase workDatabase, List list) {
        this.f60039a1 = context;
        this.f60040a2 = c0793kr;
        this.f60041a3 = pg1Var;
        this.f60042a4 = workDatabase;
        this.f60046a8 = list;
    }

    /* renamed from: a1 */
    public static boolean m214650a1(fh1 fh1Var) {
        if (fh1Var == null) {
            C1351vv.m214963a5().getClass();
            return false;
        }
        fh1Var.f56274b6 = true;
        fh1Var.m212820a7();
        fh1Var.f56273b5.cancel(true);
        if (fh1Var.f56262a4 == null || !(fh1Var.f56273b5.f56381a0 instanceof C0486f8)) {
            Objects.toString(fh1Var.f56261a3);
            C1351vv.m214963a5().getClass();
        } else {
            fh1Var.f56262a4.m214733a5();
        }
        C1351vv.m214963a5().getClass();
        return true;
    }

    /* renamed from: a0 */
    public final void m214651a0(InterfaceC1425xp interfaceC1425xp) {
        synchronized (this.f60049b1) {
            this.f60048b0.add(interfaceC1425xp);
        }
    }

    /* renamed from: a2 */
    public final boolean m214652a2(String str) {
        boolean z;
        synchronized (this.f60049b1) {
            try {
                z = this.f60044a6.containsKey(str) || this.f60043a5.containsKey(str);
            } finally {
            }
        }
        return z;
    }

    /* renamed from: a3 */
    public final void m214653a3(InterfaceC1425xp interfaceC1425xp) {
        synchronized (this.f60049b1) {
            this.f60048b0.remove(interfaceC1425xp);
        }
    }

    @Override // p000.InterfaceC1425xp
    /* renamed from: a4 */
    public final void mo210482a4(jg1 jg1Var, boolean z) {
        synchronized (this.f60049b1) {
            try {
                fh1 fh1Var = (fh1) this.f60044a6.get(jg1Var.f57334a0);
                if (fh1Var != null && jg1Var.equals(cq0.m212483b3(fh1Var.f56261a3))) {
                    this.f60044a6.remove(jg1Var.f57334a0);
                }
                C1351vv.m214963a5().getClass();
                ArrayList arrayList = this.f60048b0;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    ((InterfaceC1425xp) obj).mo210482a4(jg1Var, z);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: a5 */
    public final void m214654a5(String str, C1241t c1241t) {
        synchronized (this.f60049b1) {
            try {
                C1351vv.m214963a5().getClass();
                fh1 fh1Var = (fh1) this.f60044a6.remove(str);
                if (fh1Var != null) {
                    if (this.f60038a0 == null) {
                        PowerManager.WakeLock wakeLockM213032a0 = he1.m213032a0(this.f60039a1, "ProcessorForegroundLck");
                        this.f60038a0 = wakeLockM213032a0;
                        wakeLockM213032a0.acquire();
                    }
                    this.f60043a5.put(str, fh1Var);
                    Intent intentM214473a2 = r31.m214473a2(this.f60039a1, cq0.m212483b3(fh1Var.f56261a3), c1241t);
                    Context context = this.f60039a1;
                    if (Build.VERSION.SDK_INT >= 26) {
                        AbstractC0872mr.m214019a1(context, intentM214473a2);
                    } else {
                        context.startService(intentM214473a2);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: a6 */
    public final boolean m214655a6(x11 x11Var, fh0 fh0Var) throws Throwable {
        Throwable th;
        jg1 jg1Var = x11Var.f60991a0;
        final String str = jg1Var.f57334a0;
        final ArrayList arrayList = new ArrayList();
        wg1 wg1Var = (wg1) this.f60042a4.m212862b1(new Callable() { // from class: ro0
            @Override // java.util.concurrent.Callable
            public final Object call() {
                WorkDatabase workDatabase = this.f59799a0.f60042a4;
                zg1 zg1VarMo210466c0 = workDatabase.mo210466c0();
                String str2 = str;
                arrayList.addAll(zg1VarMo210466c0.m215411a7(str2));
                return workDatabase.mo210465b9().m215185a8(str2);
            }
        });
        if (wg1Var == null) {
            C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
            jg1Var.toString();
            c1351vvM214963a5.getClass();
            ((mg1) this.f60041a3.f59231a3).execute(new RunnableC1052p1(this, 10, jg1Var));
            return false;
        }
        synchronized (this.f60049b1) {
            try {
                try {
                    try {
                        if (m214652a2(str)) {
                            Set set = (Set) this.f60045a7.get(str);
                            if (((x11) set.iterator().next()).f60991a0.f57335a1 == jg1Var.f57335a1) {
                                set.add(x11Var);
                                C1351vv c1351vvM214963a52 = C1351vv.m214963a5();
                                jg1Var.toString();
                                c1351vvM214963a52.getClass();
                            } else {
                                ((mg1) this.f60041a3.f59231a3).execute(new RunnableC1052p1(this, 10, jg1Var));
                            }
                            return false;
                        }
                        if (wg1Var.f60931b9 != jg1Var.f57335a1) {
                            ((mg1) this.f60041a3.f59231a3).execute(new RunnableC1052p1(this, 10, jg1Var));
                            return false;
                        }
                        C0502fn c0502fn = new C0502fn(this.f60039a1, this.f60040a2, this.f60041a3, this, this.f60042a4, wg1Var, arrayList);
                        c0502fn.f56297a6 = this.f60046a8;
                        fh1 fh1Var = new fh1(c0502fn);
                        C0100a1 c0100a1 = fh1Var.f56272b4;
                        c0100a1.mo210459a0(new RunnableC0818lf(this, x11Var.f60991a0, c0100a1, 2), (mg1) this.f60041a3.f59231a3);
                        this.f60044a6.put(str, fh1Var);
                        HashSet hashSet = new HashSet();
                        hashSet.add(x11Var);
                        this.f60045a7.put(str, hashSet);
                        ((ExecutorC0034an) this.f60041a3.f59229a1).execute(fh1Var);
                        C1351vv c1351vvM214963a53 = C1351vv.m214963a5();
                        jg1Var.toString();
                        c1351vvM214963a53.getClass();
                        return true;
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    th = th;
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                th = th;
                throw th;
            }
        }
    }

    /* renamed from: a7 */
    public final void m214656a7() {
        synchronized (this.f60049b1) {
            try {
                if (this.f60043a5.isEmpty()) {
                    Context context = this.f60039a1;
                    int i = r31.f59609a9;
                    Intent intent = new Intent(context, (Class<?>) SystemForegroundService.class);
                    intent.setAction("ACTION_STOP_FOREGROUND");
                    try {
                        this.f60039a1.startService(intent);
                    } catch (Throwable unused) {
                        C1351vv.m214963a5().getClass();
                    }
                    PowerManager.WakeLock wakeLock = this.f60038a0;
                    if (wakeLock != null) {
                        wakeLock.release();
                        this.f60038a0 = null;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
