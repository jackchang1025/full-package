package p000;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import java.util.concurrent.CountDownLatch;
import kotlin.jvm.internal.Ref$BooleanRef;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class k40 extends AccessibilityService.GestureResultCallback {

    /* renamed from: a0 */
    public final /* synthetic */ Ref$BooleanRef f57450a0;

    /* renamed from: a1 */
    public final /* synthetic */ CountDownLatch f57451a1;

    /* renamed from: a2 */
    public final /* synthetic */ float f57452a2;

    /* renamed from: a3 */
    public final /* synthetic */ float f57453a3;

    public k40(Ref$BooleanRef ref$BooleanRef, CountDownLatch countDownLatch, float f, float f2) {
        this.f57450a0 = ref$BooleanRef;
        this.f57451a1 = countDownLatch;
        this.f57452a2 = f;
        this.f57453a3 = f2;
    }

    @Override // android.accessibilityservice.AccessibilityService.GestureResultCallback
    public final void onCancelled(GestureDescription gestureDescription) {
        super.onCancelled(gestureDescription);
        t60.m214726f4("HuaweiSteps", AbstractC0003a2.m29b0("[performClick] ⚠️ 坐标点击手势被取消: (", this.f57452a2, ", ", this.f57453a3, ")"));
        this.f57451a1.countDown();
    }

    @Override // android.accessibilityservice.AccessibilityService.GestureResultCallback
    public final void onCompleted(GestureDescription gestureDescription) {
        super.onCompleted(gestureDescription);
        this.f57450a0.f57622a0 = true;
        this.f57451a1.countDown();
    }
}
