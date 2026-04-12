package p000;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import kotlin.jvm.internal.Ref$BooleanRef;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class jh1 extends AccessibilityService.GestureResultCallback {

    /* renamed from: a0 */
    public final /* synthetic */ int f57336a0;

    /* renamed from: a1 */
    public final /* synthetic */ Ref$BooleanRef f57337a1;

    /* renamed from: a2 */
    public final /* synthetic */ Ref$BooleanRef f57338a2;

    public /* synthetic */ jh1(Ref$BooleanRef ref$BooleanRef, Ref$BooleanRef ref$BooleanRef2, int i) {
        this.f57336a0 = i;
        this.f57337a1 = ref$BooleanRef;
        this.f57338a2 = ref$BooleanRef2;
    }

    @Override // android.accessibilityservice.AccessibilityService.GestureResultCallback
    public final void onCancelled(GestureDescription gestureDescription) {
        switch (this.f57336a0) {
            case 0:
                super.onCancelled(gestureDescription);
                t60.m214726f4("WriteSettingsPerm", "⚠️ 坐标点击手势被取消");
                this.f57337a1.f57622a0 = true;
                this.f57338a2.f57622a0 = false;
                break;
            default:
                super.onCancelled(gestureDescription);
                this.f57337a1.f57622a0 = true;
                this.f57338a2.f57622a0 = false;
                break;
        }
    }

    @Override // android.accessibilityservice.AccessibilityService.GestureResultCallback
    public final void onCompleted(GestureDescription gestureDescription) {
        switch (this.f57336a0) {
            case 0:
                super.onCompleted(gestureDescription);
                this.f57337a1.f57622a0 = true;
                this.f57338a2.f57622a0 = true;
                break;
            default:
                super.onCompleted(gestureDescription);
                this.f57337a1.f57622a0 = true;
                this.f57338a2.f57622a0 = true;
                break;
        }
    }
}
