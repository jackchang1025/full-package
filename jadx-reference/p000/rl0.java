package p000;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import com.storm.safe.rock.service.modules.yw5xud.C0367a4;
import com.storm.safe.rock.service.modules.yw5xud.C0368a5;
import java.util.concurrent.CountDownLatch;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class rl0 extends AccessibilityService.GestureResultCallback {

    /* renamed from: a0 */
    public final /* synthetic */ int f59791a0;

    /* renamed from: a1 */
    public final /* synthetic */ Object f59792a1;

    public /* synthetic */ rl0(int i, Object obj) {
        this.f59791a0 = i;
        this.f59792a1 = obj;
    }

    @Override // android.accessibilityservice.AccessibilityService.GestureResultCallback
    public final void onCancelled(GestureDescription gestureDescription) {
        switch (this.f59791a0) {
            case 0:
                C0368a5.m212303e0("[同步点击] ⚠️ 手势取消");
                ((CountDownLatch) this.f59792a1).countDown();
                break;
            case 1:
                ((CountDownLatch) this.f59792a1).countDown();
                break;
            default:
                ((C0367a4) this.f59792a1).m212274d8("[滑动] ❌ 取消");
                break;
        }
    }

    @Override // android.accessibilityservice.AccessibilityService.GestureResultCallback
    public final void onCompleted(GestureDescription gestureDescription) {
        switch (this.f59791a0) {
            case 0:
                C0368a5.m212303e0("[同步点击] ✅ 手势完成");
                ((CountDownLatch) this.f59792a1).countDown();
                break;
            case 1:
                ((CountDownLatch) this.f59792a1).countDown();
                break;
            default:
                ((C0367a4) this.f59792a1).m212274d8("[滑动] ✅ 完成");
                break;
        }
    }

    public rl0(C0368a5 c0368a5, CountDownLatch countDownLatch) {
        this.f59791a0 = 0;
        this.f59792a1 = countDownLatch;
    }
}
