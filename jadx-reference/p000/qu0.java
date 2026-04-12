package p000;

import android.os.Build;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.Iterator;
import java.util.List;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final /* synthetic */ class qu0 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f59550a0;

    /* renamed from: a1 */
    public final /* synthetic */ tu0 f59551a1;

    public /* synthetic */ qu0(tu0 tu0Var, int i) {
        this.f59550a0 = i;
        this.f59551a1 = tu0Var;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f59550a0) {
            case 0:
                tu0 tu0Var = this.f59551a1;
                try {
                    AccessibilityNodeInfo accessibilityNodeInfo = (AccessibilityNodeInfo) tu0Var.f60275a0.invoke();
                    if (accessibilityNodeInfo != null) {
                        String[] strArr = tu0.f60270a8;
                        int length = strArr.length;
                        int i = 0;
                        while (true) {
                            if (i < length) {
                                try {
                                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText(strArr[i]);
                                    if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                                        Iterator<AccessibilityNodeInfo> it = listFindAccessibilityNodeInfosByText.iterator();
                                        while (it.hasNext()) {
                                            if (it.next().isVisibleToUser()) {
                                                tu0Var.f60277a2 = 1;
                                                tu0Var.f60278a3 = System.currentTimeMillis();
                                                tu0Var.f60279a4 = false;
                                                if (Build.VERSION.SDK_INT >= 34) {
                                                    tu0Var.f60280a5.postDelayed(new ru0(tu0Var, accessibilityNodeInfo, 0), 100L);
                                                } else {
                                                    tu0Var.f60280a5.postDelayed(new ru0(tu0Var, accessibilityNodeInfo, 1), 100L);
                                                }
                                            }
                                        }
                                    }
                                } catch (Exception unused) {
                                }
                                i++;
                            }
                        }
                    }
                    break;
                } catch (Exception e) {
                    tz0.m214810b0("后台检测投屏弹窗异常: ", e.getMessage(), "ScreenRecordAutoAllower");
                    return;
                }
                break;
            default:
                tu0 tu0Var2 = this.f59551a1;
                AccessibilityNodeInfo accessibilityNodeInfo2 = (AccessibilityNodeInfo) tu0Var2.f60275a0.invoke();
                if (accessibilityNodeInfo2 != null) {
                    for (String str : tu0.f60271a9) {
                        try {
                            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText2 = accessibilityNodeInfo2.findAccessibilityNodeInfosByText(str);
                            if (listFindAccessibilityNodeInfosByText2 != null && !listFindAccessibilityNodeInfosByText2.isEmpty()) {
                                for (AccessibilityNodeInfo accessibilityNodeInfo3 : listFindAccessibilityNodeInfosByText2) {
                                    if (accessibilityNodeInfo3.isVisibleToUser() && tu0.m214783a1(accessibilityNodeInfo3)) {
                                        tu0Var2.f60279a4 = true;
                                        tu0Var2.f60280a5.postDelayed(new ru0(tu0Var2, accessibilityNodeInfo2, 3), 500L);
                                        break;
                                    }
                                }
                            }
                        } catch (Exception unused2) {
                            t60.m214695b6("点击选项异常: " + str, "msg");
                        }
                    }
                    break;
                }
                break;
        }
    }
}
