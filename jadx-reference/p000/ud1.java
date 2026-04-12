package p000;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.os.Handler;
import com.storm.safe.rock.service.modules.yw5xud.C0371a8;
import kotlin.Result;
import kotlin.jvm.internal.Ref$BooleanRef;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class ud1 extends AccessibilityService.GestureResultCallback {

    /* renamed from: a0 */
    public final /* synthetic */ Handler f60408a0;

    /* renamed from: a1 */
    public final /* synthetic */ RunnableC0818lf f60409a1;

    /* renamed from: a2 */
    public final /* synthetic */ Ref$BooleanRef f60410a2;

    /* renamed from: a3 */
    public final /* synthetic */ C0371a8 f60411a3;

    /* renamed from: a4 */
    public final /* synthetic */ C0530gb f60412a4;

    public ud1(Handler handler, RunnableC0818lf runnableC0818lf, Ref$BooleanRef ref$BooleanRef, C0371a8 c0371a8, C0530gb c0530gb) {
        this.f60408a0 = handler;
        this.f60409a1 = runnableC0818lf;
        this.f60410a2 = ref$BooleanRef;
        this.f60411a3 = c0371a8;
        this.f60412a4 = c0530gb;
    }

    @Override // android.accessibilityservice.AccessibilityService.GestureResultCallback
    public final void onCancelled(GestureDescription gestureDescription) {
        this.f60408a0.removeCallbacks(this.f60409a1);
        Ref$BooleanRef ref$BooleanRef = this.f60410a2;
        if (ref$BooleanRef.f57622a0) {
            return;
        }
        ref$BooleanRef.f57622a0 = true;
        t60.m214704c5(this.f60411a3.f55141a2, "[VIVO下滑手势] ❌ 被取消");
        int i = Result.f57558a1;
        this.f60412a4.resumeWith(Boolean.FALSE);
    }

    @Override // android.accessibilityservice.AccessibilityService.GestureResultCallback
    public final void onCompleted(GestureDescription gestureDescription) {
        this.f60408a0.removeCallbacks(this.f60409a1);
        Ref$BooleanRef ref$BooleanRef = this.f60410a2;
        if (ref$BooleanRef.f57622a0) {
            return;
        }
        ref$BooleanRef.f57622a0 = true;
        t60.m214704c5(this.f60411a3.f55141a2, "[VIVO下滑手势] ✅ 执行完成");
        int i = Result.f57558a1;
        this.f60412a4.resumeWith(Boolean.TRUE);
    }
}
