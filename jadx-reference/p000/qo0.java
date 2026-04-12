package p000;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Process;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class qo0 {
    static {
        t60.m214694b5(C1351vv.m214966b1("ProcessUtils"), "tagWithPrefix(\"ProcessUtils\")");
    }

    /* renamed from: a0 */
    public static final boolean m214465a0(Context context, C0793kr c0793kr) {
        String strM214813a0;
        Object next;
        Object objInvoke;
        t60.m214695b6(context, "context");
        t60.m214695b6(c0793kr, "configuration");
        if (Build.VERSION.SDK_INT >= 28) {
            strM214813a0 = C1284u1.f60315a0.m214813a0();
        } else {
            strM214813a0 = null;
            try {
                Method declaredMethod = Class.forName("android.app.ActivityThread", false, kg1.class.getClassLoader()).getDeclaredMethod("currentProcessName", null);
                declaredMethod.setAccessible(true);
                objInvoke = declaredMethod.invoke(null, null);
                t60.m214692b3(objInvoke);
            } catch (Throwable unused) {
                C1351vv.m214963a5().getClass();
            }
            if (objInvoke instanceof String) {
                strM214813a0 = (String) objInvoke;
            } else {
                int iMyPid = Process.myPid();
                Object systemService = context.getSystemService("activity");
                t60.m214693b4(systemService, "null cannot be cast to non-null type android.app.ActivityManager");
                List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) systemService).getRunningAppProcesses();
                if (runningAppProcesses != null) {
                    Iterator<T> it = runningAppProcesses.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            next = null;
                            break;
                        }
                        next = it.next();
                        if (((ActivityManager.RunningAppProcessInfo) next).pid == iMyPid) {
                            break;
                        }
                    }
                    ActivityManager.RunningAppProcessInfo runningAppProcessInfo = (ActivityManager.RunningAppProcessInfo) next;
                    if (runningAppProcessInfo != null) {
                        strM214813a0 = runningAppProcessInfo.processName;
                    }
                }
            }
        }
        return t60.m214686a2(strM214813a0, context.getApplicationInfo().processName);
    }
}
