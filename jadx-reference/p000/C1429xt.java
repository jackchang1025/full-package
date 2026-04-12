package p000;

import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: xt */
/* loaded from: classes.dex */
public final class C1429xt extends tg0 {

    /* renamed from: a3 */
    public final /* synthetic */ AbstractC1430xu f61175a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1429xt(AbstractC1430xu abstractC1430xu) {
        super(1);
        this.f61175a3 = abstractC1430xu;
    }

    @Override // p000.tg0
    /* renamed from: b8 */
    public final C0748k7 mo214746b8(int i) {
        return new C0748k7(AccessibilityNodeInfo.obtain(this.f61175a3.m215214b7(i).f57472a0));
    }

    @Override // p000.tg0
    /* renamed from: b9 */
    public final C0748k7 mo214747b9(int i) {
        AbstractC1430xu abstractC1430xu = this.f61175a3;
        int i2 = i == 2 ? abstractC1430xu.f61190b0 : abstractC1430xu.f61191b1;
        if (i2 == Integer.MIN_VALUE) {
            return null;
        }
        return mo214746b8(i2);
    }

    @Override // p000.tg0
    /* renamed from: c3 */
    public final boolean mo214749c3(int i, int i2, Bundle bundle) {
        int i3;
        AbstractC1430xu abstractC1430xu = this.f61175a3;
        View view = abstractC1430xu.f61188a8;
        if (i == -1) {
            WeakHashMap weakHashMap = xa1.f61054a0;
            return fa1.m212772a9(view, i2, bundle);
        }
        if (i2 == 1) {
            return abstractC1430xu.m215215c2(i);
        }
        if (i2 == 2) {
            return abstractC1430xu.m215208a9(i);
        }
        if (i2 != 64) {
            if (i2 != 128) {
                return abstractC1430xu.mo211130b8(i, i2, bundle);
            }
            if (abstractC1430xu.f61190b0 != i) {
                return false;
            }
            abstractC1430xu.f61190b0 = Integer.MIN_VALUE;
            view.invalidate();
            abstractC1430xu.m215216c3(i, 65536);
            return true;
        }
        AccessibilityManager accessibilityManager = abstractC1430xu.f61187a7;
        if (!accessibilityManager.isEnabled() || !accessibilityManager.isTouchExplorationEnabled() || (i3 = abstractC1430xu.f61190b0) == i) {
            return false;
        }
        if (i3 != Integer.MIN_VALUE) {
            abstractC1430xu.f61190b0 = Integer.MIN_VALUE;
            view.invalidate();
            abstractC1430xu.m215216c3(i3, 65536);
        }
        abstractC1430xu.f61190b0 = i;
        view.invalidate();
        abstractC1430xu.m215216c3(i, 32768);
        return true;
    }
}
