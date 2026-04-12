package p000;

import android.view.accessibility.AccessibilityNodeInfo;
import com.storm.safe.rock.service.dqtvuisjd;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class ou0 {

    /* renamed from: a0 */
    public final dqtvuisjd f59123a0;

    static {
        new nu0(null);
    }

    public ou0(dqtvuisjd dqtvuisjdVar, dqtvuisjd dqtvuisjdVar2) {
        this.f59123a0 = dqtvuisjdVar2;
    }

    /* renamed from: a0 */
    public static final void m214235a0(int i, AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        if (i > 15) {
            return;
        }
        if (accessibilityNodeInfo.isClickable() && accessibilityNodeInfo.isVisibleToUser()) {
            arrayList.add(accessibilityNodeInfo);
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
            if (child != null) {
                m214235a0(i + 1, child, arrayList);
            }
        }
    }
}
