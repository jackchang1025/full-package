package p000;

import android.view.accessibility.AccessibilityNodeInfo;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ru0 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f59818a0;

    /* renamed from: a1 */
    public final /* synthetic */ tu0 f59819a1;

    /* renamed from: a2 */
    public final /* synthetic */ AccessibilityNodeInfo f59820a2;

    public /* synthetic */ ru0(tu0 tu0Var, AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        this.f59818a0 = i;
        this.f59819a1 = tu0Var;
        this.f59820a2 = accessibilityNodeInfo;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f59818a0) {
            case 0:
                tu0 tu0Var = this.f59819a1;
                AccessibilityNodeInfo accessibilityNodeInfo = this.f59820a2;
                try {
                    AccessibilityNodeInfo accessibilityNodeInfo2 = (AccessibilityNodeInfo) tu0Var.f60275a0.invoke();
                    if (accessibilityNodeInfo2 != null) {
                        accessibilityNodeInfo = accessibilityNodeInfo2;
                    }
                    if (!tu0Var.f60279a4) {
                        if (tu0Var.m214789a7(accessibilityNodeInfo)) {
                            tu0Var.f60279a4 = true;
                            tu0Var.f60277a2 = 2;
                            tu0Var.f60280a5.postDelayed(new ru0(tu0Var, accessibilityNodeInfo, 2), 500L);
                            break;
                        } else {
                            tu0Var.f60279a4 = true;
                        }
                    }
                    tu0Var.m214788a6(accessibilityNodeInfo);
                    break;
                } catch (Exception e) {
                    t60.m214705c6("ScreenRecordAutoAllower", "❌ Android 14+ 弹窗处理失败", e);
                    tu0Var.f60277a2 = 0;
                    return;
                }
            case 1:
                tu0 tu0Var2 = this.f59819a1;
                AccessibilityNodeInfo accessibilityNodeInfo3 = this.f59820a2;
                t60.m214695b6(tu0Var2, "this$0");
                t60.m214695b6(accessibilityNodeInfo3, "$rootNode");
                tu0Var2.m214788a6(accessibilityNodeInfo3);
                break;
            case 2:
                tu0 tu0Var3 = this.f59819a1;
                AccessibilityNodeInfo accessibilityNodeInfo4 = this.f59820a2;
                t60.m214695b6(tu0Var3, "this$0");
                t60.m214695b6(accessibilityNodeInfo4, "$currentRoot");
                AccessibilityNodeInfo accessibilityNodeInfo5 = (AccessibilityNodeInfo) tu0Var3.f60275a0.invoke();
                if (accessibilityNodeInfo5 != null) {
                    accessibilityNodeInfo4 = accessibilityNodeInfo5;
                }
                tu0Var3.m214788a6(accessibilityNodeInfo4);
                break;
            default:
                tu0 tu0Var4 = this.f59819a1;
                AccessibilityNodeInfo accessibilityNodeInfo6 = this.f59820a2;
                t60.m214695b6(tu0Var4, "this$0");
                t60.m214695b6(accessibilityNodeInfo6, "$newRoot");
                AccessibilityNodeInfo accessibilityNodeInfo7 = (AccessibilityNodeInfo) tu0Var4.f60275a0.invoke();
                if (accessibilityNodeInfo7 != null) {
                    accessibilityNodeInfo6 = accessibilityNodeInfo7;
                }
                tu0Var4.m214788a6(accessibilityNodeInfo6);
                break;
        }
    }
}
