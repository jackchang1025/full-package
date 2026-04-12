package p000;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import java.util.concurrent.CountDownLatch;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class l40 extends AccessibilityService.GestureResultCallback {

    /* renamed from: a0 */
    public final /* synthetic */ CountDownLatch f57823a0;

    /* renamed from: a1 */
    public final /* synthetic */ float f57824a1;

    /* renamed from: a2 */
    public final /* synthetic */ float f57825a2;

    public l40(CountDownLatch countDownLatch, float f, float f2) {
        this.f57823a0 = countDownLatch;
        this.f57824a1 = f;
        this.f57825a2 = f2;
    }

    @Override // android.accessibilityservice.AccessibilityService.GestureResultCallback
    public final void onCancelled(GestureDescription gestureDescription) {
        super.onCancelled(gestureDescription);
        t60.m214726f4("HuaweiSteps", AbstractC0003a2.m29b0("[performSingleClick] 手势被取消: (", this.f57824a1, ", ", this.f57825a2, ")"));
        this.f57823a0.countDown();
    }

    @Override // android.accessibilityservice.AccessibilityService.GestureResultCallback
    public final void onCompleted(GestureDescription gestureDescription) {
        super.onCompleted(gestureDescription);
        this.f57823a0.countDown();
    }
}
