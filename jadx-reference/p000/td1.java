package p000;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import com.storm.safe.rock.service.modules.yw5xud.C0371a8;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class td1 extends AccessibilityService.GestureResultCallback {

    /* renamed from: a0 */
    public final /* synthetic */ int f60205a0;

    /* renamed from: a1 */
    public final /* synthetic */ C0371a8 f60206a1;

    public /* synthetic */ td1(C0371a8 c0371a8, int i) {
        this.f60205a0 = i;
        this.f60206a1 = c0371a8;
    }

    @Override // android.accessibilityservice.AccessibilityService.GestureResultCallback
    public final void onCancelled(GestureDescription gestureDescription) {
        switch (this.f60205a0) {
            case 0:
                t60.m214704c5(this.f60206a1.f55141a2, "[滑动] ❌取消");
                break;
            default:
                t60.m214704c5(this.f60206a1.f55141a2, "[横向滑动] ❌ 取消");
                break;
        }
    }

    @Override // android.accessibilityservice.AccessibilityService.GestureResultCallback
    public final void onCompleted(GestureDescription gestureDescription) {
        switch (this.f60205a0) {
            case 0:
                break;
            default:
                t60.m214704c5(this.f60206a1.f55141a2, "[横向滑动] ✅ 完成");
                break;
        }
    }

    /* renamed from: a0 */
    private final void m214737a0(GestureDescription gestureDescription) {
    }
}
