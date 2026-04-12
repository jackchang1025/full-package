package p000;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import com.storm.safe.rock.service.modules.yw5xud.C0371a8;
import java.util.concurrent.CountDownLatch;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class sd1 extends AccessibilityService.GestureResultCallback {

    /* renamed from: a0 */
    public final /* synthetic */ CountDownLatch f59963a0;

    /* renamed from: a1 */
    public final /* synthetic */ C0371a8 f59964a1;

    /* renamed from: a2 */
    public final /* synthetic */ int f59965a2;

    /* renamed from: a3 */
    public final /* synthetic */ int f59966a3;

    /* renamed from: a4 */
    public final /* synthetic */ int f59967a4;

    public sd1(int i, int i2, int i3, C0371a8 c0371a8, CountDownLatch countDownLatch) {
        this.f59963a0 = countDownLatch;
        this.f59964a1 = c0371a8;
        this.f59965a2 = i;
        this.f59966a3 = i2;
        this.f59967a4 = i3;
    }

    @Override // android.accessibilityservice.AccessibilityService.GestureResultCallback
    public final void onCancelled(GestureDescription gestureDescription) {
        String str = this.f59964a1.f55141a2;
        int i = this.f59967a4 + 1;
        StringBuilder sbM38b9 = AbstractC0003a2.m38b9("[f] ⚠️ 手势取消: (", this.f59965a2, ", ", this.f59966a3, ") [");
        sbM38b9.append(i);
        sbM38b9.append("/1]");
        t60.m214726f4(str, sbM38b9.toString());
        this.f59963a0.countDown();
    }

    @Override // android.accessibilityservice.AccessibilityService.GestureResultCallback
    public final void onCompleted(GestureDescription gestureDescription) {
        this.f59963a0.countDown();
    }
}
