package p000;

import android.os.Handler;
import android.os.Looper;
import com.storm.safe.rock.p029ui.ibbnqvnvhxg;
import com.storm.safe.rock.service.dqtvuisjd;
import java.util.Set;
import org.json.JSONObject;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: dy */
/* loaded from: classes2.dex */
public final class C0434dy implements InterfaceC0726jp {
    static {
        new C0433dx(null);
    }

    @Override // p000.InterfaceC0726jp
    /* renamed from: a0 */
    public final boolean mo210872a0(String str) {
        return t60.m214690a8(this, str);
    }

    @Override // p000.InterfaceC0726jp
    /* renamed from: a1 */
    public final Set mo210873a1() {
        return kg1.m213542f1("ENABLE_BLACK_SCREEN", "DISABLE_BLACK_SCREEN");
    }

    @Override // p000.InterfaceC0726jp
    /* renamed from: a2 */
    public final Object mo210874a2(String str, JSONObject jSONObject, uz0 uz0Var, InterfaceC0876mv interfaceC0876mv) {
        String str2;
        String str3;
        if (str.equals("ENABLE_BLACK_SCREEN")) {
            String strOptString = jSONObject != null ? jSONObject.optString("text", "") : null;
            final String str4 = strOptString == null ? "" : strOptString;
            final int iOptInt = jSONObject != null ? jSONObject.optInt("fontSize", 24) : 24;
            int iOptInt2 = jSONObject != null ? jSONObject.optInt("alpha", 252) : 252;
            boolean zOptBoolean = jSONObject != null ? jSONObject.optBoolean("showIcon", false) : false;
            boolean zOptBoolean2 = jSONObject != null ? jSONObject.optBoolean("showProgress", false) : false;
            String strOptString2 = jSONObject != null ? jSONObject.optString("style", "android") : null;
            final String str5 = strOptString2 == null ? "android" : strOptString2;
            StringBuilder sbM41c2 = AbstractC0003a2.m41c2("收到 ENABLE_BLACK_SCREEN 命令，样式: ", str5, ", 文字: ", str4, ", 字号: ");
            sbM41c2.append(iOptInt);
            sbM41c2.append(", 透明度: ");
            sbM41c2.append(iOptInt2);
            sbM41c2.append(", 图标: ");
            sbM41c2.append(zOptBoolean);
            sbM41c2.append(", 进度条: ");
            sbM41c2.append(zOptBoolean2);
            t60.m214714d6("BlackScreenCmdHandler", sbM41c2.toString());
            uz0Var.m214879b5("收到 ENABLE_BLACK_SCREEN 命令，样式: " + str5 + ", 透明度: " + iOptInt2);
            final dqtvuisjd dqtvuisjdVar = uz0Var.f60536a0;
            dqtvuisjd.C0290a0 c0290a0 = dqtvuisjd.f52358m1;
            try {
                str2 = "dqtvuisjd";
            } catch (Exception e) {
                e = e;
                str2 = "dqtvuisjd";
            }
            try {
                t60.m214714d6(str2, "🖤 启用黑屏遮盖，样式: " + str5 + ", 文字: " + str4 + ", 字号: " + iOptInt + ", 透明度: " + iOptInt2 + ", 图标: " + zOptBoolean + ", 进度条: " + zOptBoolean2 + ", blockInput: false");
                if (dqtvuisjdVar.f52423f4 == null) {
                    t60.m214704c5(str2, "❌ maskOverlayManager 未初始化");
                } else {
                    fd0 fd0Var = dqtvuisjdVar.f52423f4;
                    if (fd0Var == null) {
                        t60.m214724f2("maskOverlayManager");
                        throw null;
                    }
                    try {
                        fd0Var.f56200a2 = AbstractC1117qo.m214413a9(iOptInt2, 0, v10.MASK);
                        str3 = "MaskOverlayManager";
                    } catch (Exception e2) {
                        str3 = "MaskOverlayManager";
                        t60.m214705c6(str3, "❌ 设置遮罩透明度失败", e2);
                    }
                    fd0 fd0Var2 = dqtvuisjdVar.f52423f4;
                    if (fd0Var2 == null) {
                        t60.m214724f2("maskOverlayManager");
                        throw null;
                    }
                    try {
                        C0454ef c0454ef = fd0Var2.f56199a1;
                        if (c0454ef != null) {
                            c0454ef.f55989b1 = zOptBoolean;
                            c0454ef.f55990b2 = zOptBoolean2;
                        }
                    } catch (Exception e3) {
                        t60.m214705c6(str3, "❌ 设置显示开关失败", e3);
                    }
                    fd0 fd0Var3 = dqtvuisjdVar.f52423f4;
                    if (fd0Var3 == null) {
                        t60.m214724f2("maskOverlayManager");
                        throw null;
                    }
                    try {
                        C0454ef c0454ef2 = fd0Var3.f56199a1;
                        if (c0454ef2 != null) {
                            c0454ef2.f55991b3 = str5;
                        }
                    } catch (Exception e4) {
                        t60.m214705c6(str3, "❌ 设置覆盖层样式失败", e4);
                    }
                    fd0 fd0Var4 = dqtvuisjdVar.f52423f4;
                    if (fd0Var4 == null) {
                        t60.m214724f2("maskOverlayManager");
                        throw null;
                    }
                    fd0Var4.m212792a0();
                    dqtvuisjdVar.f52469k0 = true;
                    final boolean z = zOptBoolean;
                    final int i = iOptInt2;
                    final boolean z2 = zOptBoolean2;
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: pj1
                        /* JADX WARN: Removed duplicated region for block: B:24:0x004a  */
                        /* JADX WARN: Removed duplicated region for block: B:58:0x00ad  */
                        @Override // java.lang.Runnable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final void run() {
                            C0454ef c0451ec;
                            dqtvuisjd dqtvuisjdVar2 = dqtvuisjdVar;
                            String str6 = str5;
                            int i2 = i;
                            boolean z3 = z;
                            boolean z4 = z2;
                            dqtvuisjd.C0290a0 c0290a02 = dqtvuisjd.f52358m1;
                            if (!dqtvuisjdVar2.f52469k0) {
                                t60.m214702c3("dqtvuisjd", "黑屏已被取消，跳过延迟回调");
                                return;
                            }
                            fd0 fd0Var5 = dqtvuisjdVar2.f52423f4;
                            if (fd0Var5 == null) {
                                t60.m214724f2("maskOverlayManager");
                                throw null;
                            }
                            C0454ef c0454ef3 = fd0Var5.f56199a1;
                            if (!(c0454ef3 != null ? c0454ef3.f55983a5 : false)) {
                                if (dqtvuisjdVar2.f52423f4 != null) {
                                    try {
                                        c0451ec = C0454ef.f55976c3.getInstance(dqtvuisjdVar2);
                                    } catch (Exception unused) {
                                    }
                                    if (c0451ec != null || !c0451ec.f55983a5) {
                                        t60.m214726f4("dqtvuisjd", "⚠️ overlay 未成功显示，重试一次...");
                                        if (dqtvuisjdVar2.f52423f4 != null) {
                                            t60.m214724f2("maskOverlayManager");
                                            throw null;
                                        }
                                        fd0 fd0Var6 = dqtvuisjdVar2.f52423f4;
                                        if (fd0Var6 == null) {
                                            t60.m214724f2("maskOverlayManager");
                                            throw null;
                                        }
                                        try {
                                            fd0Var6.f56200a2 = AbstractC1117qo.m214413a9(i2, 0, v10.MASK);
                                        } catch (Exception e5) {
                                            t60.m214705c6("MaskOverlayManager", "❌ 设置遮罩透明度失败", e5);
                                        }
                                        fd0 fd0Var7 = dqtvuisjdVar2.f52423f4;
                                        if (fd0Var7 == null) {
                                            t60.m214724f2("maskOverlayManager");
                                            throw null;
                                        }
                                        try {
                                            C0454ef c0454ef4 = fd0Var7.f56199a1;
                                            if (c0454ef4 != null) {
                                                c0454ef4.f55989b1 = z3;
                                                c0454ef4.f55990b2 = z4;
                                            }
                                        } catch (Exception e6) {
                                            t60.m214705c6("MaskOverlayManager", "❌ 设置显示开关失败", e6);
                                        }
                                        fd0 fd0Var8 = dqtvuisjdVar2.f52423f4;
                                        if (fd0Var8 == null) {
                                            t60.m214724f2("maskOverlayManager");
                                            throw null;
                                        }
                                        try {
                                            C0454ef c0454ef5 = fd0Var8.f56199a1;
                                            if (c0454ef5 != null) {
                                                c0454ef5.f55991b3 = str6;
                                            }
                                        } catch (Exception e7) {
                                            t60.m214705c6("MaskOverlayManager", "❌ 设置覆盖层样式失败", e7);
                                        }
                                        fd0 fd0Var9 = dqtvuisjdVar2.f52423f4;
                                        if (fd0Var9 == null) {
                                            t60.m214724f2("maskOverlayManager");
                                            throw null;
                                        }
                                        fd0Var9.m212792a0();
                                        new Handler(Looper.getMainLooper()).postDelayed(new bm0(dqtvuisjdVar2, 7), 500L);
                                        return;
                                    }
                                } else {
                                    c0451ec = null;
                                    if (c0451ec != null) {
                                    }
                                    t60.m214726f4("dqtvuisjd", "⚠️ overlay 未成功显示，重试一次...");
                                    if (dqtvuisjdVar2.f52423f4 != null) {
                                    }
                                }
                            }
                            dqtvuisjdVar2.m211453e2();
                            dqtvuisjdVar2.m211491i5();
                            t60.m214714d6("dqtvuisjd", "✅ 黑屏遮盖已启用，样式: ".concat(str6));
                        }
                    }, 300L);
                }
            } catch (Exception e5) {
                e = e5;
                t60.m214705c6(str2, "❌ 启用黑屏遮盖失败", e);
                uz0Var.m214879b5("enableBlackScreen() 执行完成, isBlackScreenActive=" + uz0Var.f60536a0.f52469k0);
                t60.m214714d6("BlackScreenCmdHandler", "enableBlackScreen() 执行完成");
                return C1351vv.f60710b1;
            }
            uz0Var.m214879b5("enableBlackScreen() 执行完成, isBlackScreenActive=" + uz0Var.f60536a0.f52469k0);
            t60.m214714d6("BlackScreenCmdHandler", "enableBlackScreen() 执行完成");
        } else if (str.equals("DISABLE_BLACK_SCREEN")) {
            t60.m214714d6("BlackScreenCmdHandler", "收到 DISABLE_BLACK_SCREEN 命令，开始执行 disableBlackScreen()");
            dqtvuisjd dqtvuisjdVar2 = uz0Var.f60536a0;
            try {
                t60.m214714d6("dqtvuisjd", "🖤 取消黑屏遮盖");
                dqtvuisjdVar2.f52469k0 = false;
                dqtvuisjdVar2.m211510k6();
                ibbnqvnvhxg.f55194a0.finishIfRunning();
                fd0 fd0Var5 = dqtvuisjdVar2.f52423f4;
                if (fd0Var5 != null) {
                    try {
                        C0454ef c0454ef3 = fd0Var5.f56199a1;
                        if (c0454ef3 != null) {
                            c0454ef3.f55996b8.post(new RunnableC0449ea(false, c0454ef3));
                        }
                    } catch (Exception e6) {
                        t60.m214705c6("MaskOverlayManager", "❌ 禁用触摸拦截失败", e6);
                    }
                    fd0 fd0Var6 = dqtvuisjdVar2.f52423f4;
                    if (fd0Var6 == null) {
                        t60.m214724f2("maskOverlayManager");
                        throw null;
                    }
                    try {
                        C0454ef c0454ef4 = fd0Var6.f56199a1;
                        if (c0454ef4 != null) {
                            c0454ef4.f55985a7 = false;
                            c0454ef4.f55996b8.postAtFrontOfQueue(new RunnableC0436dz(c0454ef4, 3));
                        }
                        fd0Var6.f56200a2 = 252;
                    } catch (Exception e7) {
                        t60.m214705c6("MaskOverlayManager", "❌ 恢复设备用户输入失败", e7);
                    }
                }
                t60.m214714d6("dqtvuisjd", "✅ 黑屏遮盖已取消");
            } catch (Exception e8) {
                t60.m214705c6("dqtvuisjd", "❌ 取消黑屏遮盖失败", e8);
            }
            t60.m214714d6("BlackScreenCmdHandler", "disableBlackScreen() 执行完成");
        }
        return C1351vv.f60710b1;
    }
}
