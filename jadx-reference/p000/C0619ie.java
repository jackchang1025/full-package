package p000;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import java.util.concurrent.CountDownLatch;
import kotlin.jvm.internal.Ref$BooleanRef;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ie */
/* loaded from: classes2.dex */
public final class C0619ie extends AccessibilityService.GestureResultCallback {

    /* renamed from: a0 */
    public final /* synthetic */ int f56871a0;

    /* renamed from: a1 */
    public final /* synthetic */ Ref$BooleanRef f56872a1;

    /* renamed from: a2 */
    public final /* synthetic */ CountDownLatch f56873a2;

    public /* synthetic */ C0619ie(Ref$BooleanRef ref$BooleanRef, CountDownLatch countDownLatch, int i) {
        this.f56871a0 = i;
        this.f56872a1 = ref$BooleanRef;
        this.f56873a2 = countDownLatch;
    }

    @Override // android.accessibilityservice.AccessibilityService.GestureResultCallback
    public final void onCancelled(GestureDescription gestureDescription) {
        switch (this.f56871a0) {
            case 0:
                this.f56872a1.f57622a0 = false;
                this.f56873a2.countDown();
                break;
            case 1:
                super.onCancelled(gestureDescription);
                t60.m214726f4("HuaweiSteps", "[clickFirstUncheckedSwitch] ⚠️ 手势被取消");
                this.f56873a2.countDown();
                break;
            case 2:
                this.f56872a1.f57622a0 = false;
                this.f56873a2.countDown();
                break;
            default:
                this.f56872a1.f57622a0 = false;
                this.f56873a2.countDown();
                break;
        }
    }

    @Override // android.accessibilityservice.AccessibilityService.GestureResultCallback
    public final void onCompleted(GestureDescription gestureDescription) {
        switch (this.f56871a0) {
            case 0:
                this.f56872a1.f57622a0 = true;
                this.f56873a2.countDown();
                break;
            case 1:
                super.onCompleted(gestureDescription);
                this.f56872a1.f57622a0 = true;
                this.f56873a2.countDown();
                break;
            case 2:
                this.f56872a1.f57622a0 = true;
                this.f56873a2.countDown();
                break;
            default:
                this.f56872a1.f57622a0 = true;
                this.f56873a2.countDown();
                break;
        }
    }
}
