package p000;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.os.Handler;
import kotlin.Result;
import kotlin.jvm.internal.Ref$BooleanRef;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class m40 extends AccessibilityService.GestureResultCallback {

    /* renamed from: a0 */
    public final /* synthetic */ Handler f58260a0;

    /* renamed from: a1 */
    public final /* synthetic */ RunnableC0884n2 f58261a1;

    /* renamed from: a2 */
    public final /* synthetic */ Ref$BooleanRef f58262a2;

    /* renamed from: a3 */
    public final /* synthetic */ C0530gb f58263a3;

    public m40(Handler handler, RunnableC0884n2 runnableC0884n2, Ref$BooleanRef ref$BooleanRef, C0530gb c0530gb) {
        this.f58260a0 = handler;
        this.f58261a1 = runnableC0884n2;
        this.f58262a2 = ref$BooleanRef;
        this.f58263a3 = c0530gb;
    }

    @Override // android.accessibilityservice.AccessibilityService.GestureResultCallback
    public final void onCancelled(GestureDescription gestureDescription) {
        this.f58260a0.removeCallbacks(this.f58261a1);
        Ref$BooleanRef ref$BooleanRef = this.f58262a2;
        if (ref$BooleanRef.f57622a0) {
            return;
        }
        ref$BooleanRef.f57622a0 = true;
        t60.m214704c5("HuaweiSteps", "[下滑手势] 被取消");
        int i = Result.f57558a1;
        this.f58263a3.resumeWith(Boolean.FALSE);
    }

    @Override // android.accessibilityservice.AccessibilityService.GestureResultCallback
    public final void onCompleted(GestureDescription gestureDescription) {
        this.f58260a0.removeCallbacks(this.f58261a1);
        Ref$BooleanRef ref$BooleanRef = this.f58262a2;
        if (ref$BooleanRef.f57622a0) {
            return;
        }
        ref$BooleanRef.f57622a0 = true;
        t60.m214704c5("HuaweiSteps", "[下滑手势] 执行完成");
        int i = Result.f57558a1;
        this.f58263a3.resumeWith(Boolean.TRUE);
    }
}
