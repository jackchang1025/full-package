package p000;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.work.impl.C0096a0;
import androidx.work.impl.foreground.SystemForegroundService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class r31 implements bg1, InterfaceC1425xp {

    /* renamed from: a9 */
    public static final /* synthetic */ int f59609a9 = 0;

    /* renamed from: a0 */
    public final C0096a0 f59610a0;

    /* renamed from: a1 */
    public final pg1 f59611a1;

    /* renamed from: a2 */
    public final Object f59612a2 = new Object();

    /* renamed from: a3 */
    public jg1 f59613a3;

    /* renamed from: a4 */
    public final LinkedHashMap f59614a4;

    /* renamed from: a5 */
    public final HashMap f59615a5;

    /* renamed from: a6 */
    public final HashSet f59616a6;

    /* renamed from: a7 */
    public final zg1 f59617a7;

    /* renamed from: a8 */
    public SystemForegroundService f59618a8;

    static {
        C1351vv.m214966b1("SystemFgDispatcher");
    }

    public r31(Context context) {
        C0096a0 c0096a0M210473g0 = C0096a0.m210473g0(context);
        this.f59610a0 = c0096a0M210473g0;
        this.f59611a1 = c0096a0M210473g0.f45560a7;
        this.f59613a3 = null;
        this.f59614a4 = new LinkedHashMap();
        this.f59616a6 = new HashSet();
        this.f59615a5 = new HashMap();
        this.f59617a7 = new zg1(c0096a0M210473g0.f45566b3, this);
        c0096a0M210473g0.f45562a9.m214651a0(this);
    }

    /* renamed from: a0 */
    public static Intent m214472a0(Context context, jg1 jg1Var, C1241t c1241t) {
        Intent intent = new Intent(context, (Class<?>) SystemForegroundService.class);
        intent.setAction("ACTION_NOTIFY");
        intent.putExtra("KEY_NOTIFICATION_ID", c1241t.f60105a0);
        intent.putExtra("KEY_FOREGROUND_SERVICE_TYPE", c1241t.f60106a1);
        intent.putExtra("KEY_NOTIFICATION", c1241t.f60107a2);
        intent.putExtra("KEY_WORKSPEC_ID", jg1Var.f57334a0);
        intent.putExtra("KEY_GENERATION", jg1Var.f57335a1);
        return intent;
    }

    /* renamed from: a2 */
    public static Intent m214473a2(Context context, jg1 jg1Var, C1241t c1241t) {
        Intent intent = new Intent(context, (Class<?>) SystemForegroundService.class);
        intent.setAction("ACTION_START_FOREGROUND");
        intent.putExtra("KEY_WORKSPEC_ID", jg1Var.f57334a0);
        intent.putExtra("KEY_GENERATION", jg1Var.f57335a1);
        intent.putExtra("KEY_NOTIFICATION_ID", c1241t.f60105a0);
        intent.putExtra("KEY_FOREGROUND_SERVICE_TYPE", c1241t.f60106a1);
        intent.putExtra("KEY_NOTIFICATION", c1241t.f60107a2);
        return intent;
    }

    @Override // p000.bg1
    /* renamed from: a1 */
    public final void mo210487a1(ArrayList arrayList) {
        if (arrayList.isEmpty()) {
            return;
        }
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            wg1 wg1Var = (wg1) obj;
            String str = wg1Var.f60912a0;
            C1351vv.m214963a5().getClass();
            jg1 jg1VarM212483b3 = cq0.m212483b3(wg1Var);
            C0096a0 c0096a0 = this.f59610a0;
            c0096a0.f45560a7.m214272b6(new f21(c0096a0, new x11(jg1VarM212483b3), true));
        }
    }

    @Override // p000.InterfaceC1425xp
    /* renamed from: a4 */
    public final void mo210482a4(jg1 jg1Var, boolean z) {
        Map.Entry entry;
        synchronized (this.f59612a2) {
            try {
                wg1 wg1Var = (wg1) this.f59615a5.remove(jg1Var);
                if (wg1Var != null ? this.f59616a6.remove(wg1Var) : false) {
                    this.f59617a7.m215415b1(this.f59616a6);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        C1241t c1241t = (C1241t) this.f59614a4.remove(jg1Var);
        if (jg1Var.equals(this.f59613a3) && this.f59614a4.size() > 0) {
            Iterator it = this.f59614a4.entrySet().iterator();
            Object next = it.next();
            while (true) {
                entry = (Map.Entry) next;
                if (!it.hasNext()) {
                    break;
                } else {
                    next = it.next();
                }
            }
            this.f59613a3 = (jg1) entry.getKey();
            if (this.f59618a8 != null) {
                C1241t c1241t2 = (C1241t) entry.getValue();
                SystemForegroundService systemForegroundService = this.f59618a8;
                systemForegroundService.f45580a1.post(new s31(systemForegroundService, c1241t2.f60105a0, c1241t2.f60107a2, c1241t2.f60106a1));
                SystemForegroundService systemForegroundService2 = this.f59618a8;
                systemForegroundService2.f45580a1.post(new RunnableC0503fo(systemForegroundService2, c1241t2.f60105a0, 5));
            }
        }
        SystemForegroundService systemForegroundService3 = this.f59618a8;
        if (c1241t == null || systemForegroundService3 == null) {
            return;
        }
        C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
        jg1Var.toString();
        c1351vvM214963a5.getClass();
        systemForegroundService3.f45580a1.post(new RunnableC0503fo(systemForegroundService3, c1241t.f60105a0, 5));
    }

    /* renamed from: a5 */
    public final void m214474a5(Intent intent) {
        int i = 0;
        int intExtra = intent.getIntExtra("KEY_NOTIFICATION_ID", 0);
        int intExtra2 = intent.getIntExtra("KEY_FOREGROUND_SERVICE_TYPE", 0);
        jg1 jg1Var = new jg1(intent.getStringExtra("KEY_WORKSPEC_ID"), intent.getIntExtra("KEY_GENERATION", 0));
        Notification notification = (Notification) intent.getParcelableExtra("KEY_NOTIFICATION");
        C1351vv.m214963a5().getClass();
        if (notification == null || this.f59618a8 == null) {
            return;
        }
        C1241t c1241t = new C1241t(intExtra, notification, intExtra2);
        LinkedHashMap linkedHashMap = this.f59614a4;
        linkedHashMap.put(jg1Var, c1241t);
        if (this.f59613a3 == null) {
            this.f59613a3 = jg1Var;
            SystemForegroundService systemForegroundService = this.f59618a8;
            systemForegroundService.f45580a1.post(new s31(systemForegroundService, intExtra, notification, intExtra2));
            return;
        }
        SystemForegroundService systemForegroundService2 = this.f59618a8;
        systemForegroundService2.f45580a1.post(new RunnableC0707j6(systemForegroundService2, intExtra, notification));
        if (intExtra2 == 0 || Build.VERSION.SDK_INT < 29) {
            return;
        }
        Iterator it = linkedHashMap.entrySet().iterator();
        while (it.hasNext()) {
            i |= ((C1241t) ((Map.Entry) it.next()).getValue()).f60106a1;
        }
        C1241t c1241t2 = (C1241t) linkedHashMap.get(this.f59613a3);
        if (c1241t2 != null) {
            SystemForegroundService systemForegroundService3 = this.f59618a8;
            systemForegroundService3.f45580a1.post(new s31(systemForegroundService3, c1241t2.f60105a0, c1241t2.f60107a2, i));
        }
    }

    /* renamed from: a6 */
    public final void m214475a6() {
        this.f59618a8 = null;
        synchronized (this.f59612a2) {
            this.f59617a7.m215416b2();
        }
        this.f59610a0.f45562a9.m214653a3(this);
    }

    @Override // p000.bg1
    /* renamed from: a3 */
    public final void mo210488a3(List list) {
    }
}
