package p000;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import com.storm.safe.rock.service.modules.yw5xud.C0368a5;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: du */
/* loaded from: classes2.dex */
public final class C0429du extends AccessibilityService.GestureResultCallback {

    /* renamed from: a0 */
    public final /* synthetic */ int f55883a0;

    public /* synthetic */ C0429du(int i) {
        this.f55883a0 = i;
    }

    @Override // android.accessibilityservice.AccessibilityService.GestureResultCallback
    public final void onCancelled(GestureDescription gestureDescription) {
        switch (this.f55883a0) {
            case 0:
                t60.m214704c5("BiometricDisabler", "上滑解锁手势被取消");
                break;
            case 1:
                t60.m214726f4("GestureExecutor", "⚠️ [performLongPressDrag] 手势被取消!");
                break;
            case 2:
                t60.m214704c5("GestureExecutor", "⚠️⚠️⚠️ [performPatternDrag] 图案手势被取消! (回调)");
                break;
            case 3:
                t60.m214704c5("HuaweiSteps", "[横向滑动] 取消");
                break;
            case 4:
                C0368a5.m212303e0("[滚动] ⚠️ 手势被取消");
                break;
            case 5:
                t60.m214726f4("PermissionGranter", "⚠️ [权限] 坐标点击手势取消");
                break;
            case 6:
                t60.m214726f4("PermissionGranter", "⚠️ [权限] 坐标点击手势取消");
                break;
            case 7:
                super.onCancelled(gestureDescription);
                t60.m214726f4("WriteSettingsPerm", "⚠️ 返回手势被取消");
                break;
            default:
                t60.m214704c5("dqtvuisjd", "⚠️⚠️⚠️ [performPatternGesture] VIVO手势被取消! (回调)");
                break;
        }
    }

    @Override // android.accessibilityservice.AccessibilityService.GestureResultCallback
    public void onCompleted(GestureDescription gestureDescription) {
        switch (this.f55883a0) {
            case 0:
                t60.m214702c3("BiometricDisabler", "上滑解锁手势完成");
                break;
            case 1:
                break;
            case 2:
                t60.m214704c5("GestureExecutor", "✅✅✅ [performPatternDrag] 图案手势执行完成! (回调)");
                break;
            case 3:
                t60.m214704c5("HuaweiSteps", "[横向滑动] 完成");
                break;
            case 4:
                C0368a5.m212303e0("[滚动] ✅ 手势执行完成");
                break;
            case 5:
            case 6:
                break;
            case 7:
            default:
                super.onCompleted(gestureDescription);
                break;
            case 8:
                t60.m214704c5("dqtvuisjd", "✅✅✅ [performPatternGesture] VIVO手势执行成功! (回调)");
                break;
        }
    }

    public C0429du(C0368a5 c0368a5) {
        this.f55883a0 = 4;
    }

    /* renamed from: a0 */
    private final void m212637a0(GestureDescription gestureDescription) {
    }

    /* renamed from: a1 */
    private final void m212638a1(GestureDescription gestureDescription) {
    }

    /* renamed from: a2 */
    private final void m212639a2(GestureDescription gestureDescription) {
    }
}
