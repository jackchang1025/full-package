package p000;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import com.storm.safe.rock.service.modules.C0319a4;
import org.json.JSONArray;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class e30 extends AccessibilityService.GestureResultCallback {

    /* renamed from: a0 */
    public final /* synthetic */ long f55921a0;

    /* renamed from: a1 */
    public final /* synthetic */ C0319a4 f55922a1;

    /* renamed from: a2 */
    public final /* synthetic */ JSONArray f55923a2;

    /* renamed from: a3 */
    public final /* synthetic */ int f55924a3;

    public e30(long j, C0319a4 c0319a4, JSONArray jSONArray, int i) {
        this.f55921a0 = j;
        this.f55922a1 = c0319a4;
        this.f55923a2 = jSONArray;
        this.f55924a3 = i;
    }

    @Override // android.accessibilityservice.AccessibilityService.GestureResultCallback
    public final void onCancelled(GestureDescription gestureDescription) {
        int i = this.f55924a3;
        t60.m214726f4("GestureRecorderManager", "⚠️ 手势 " + (i + 1) + " 被取消");
        C0319a4 c0319a4 = this.f55922a1;
        c0319a4.f53066b2.postDelayed(new c30(c0319a4, this.f55923a2, i, 3), 100L);
    }

    @Override // android.accessibilityservice.AccessibilityService.GestureResultCallback
    public final void onCompleted(GestureDescription gestureDescription) {
        long j = this.f55921a0;
        if (j <= 0) {
            j = 100;
        }
        C0319a4 c0319a4 = this.f55922a1;
        c0319a4.f53066b2.postDelayed(new c30(c0319a4, this.f55923a2, this.f55924a3, 2), j);
    }
}
