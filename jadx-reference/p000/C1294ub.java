package p000;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ScrollView;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ub */
/* loaded from: classes.dex */
public final class C1294ub extends C0608i4 {

    /* renamed from: a3 */
    public final /* synthetic */ int f60371a3;

    public /* synthetic */ C1294ub(int i) {
        this.f60371a3 = i;
    }

    @Override // p000.C0608i4
    /* renamed from: a2 */
    public void mo212721a2(View view, AccessibilityEvent accessibilityEvent) {
        switch (this.f60371a3) {
            case 4:
                super.mo212721a2(view, accessibilityEvent);
                NestedScrollView nestedScrollView = (NestedScrollView) view;
                accessibilityEvent.setClassName(ScrollView.class.getName());
                accessibilityEvent.setScrollable(nestedScrollView.getScrollRange() > 0);
                accessibilityEvent.setScrollX(nestedScrollView.getScrollX());
                accessibilityEvent.setScrollY(nestedScrollView.getScrollY());
                AbstractC0803l0.m213770a2(accessibilityEvent, nestedScrollView.getScrollX());
                AbstractC0803l0.m213771a3(accessibilityEvent, nestedScrollView.getScrollRange());
                break;
            default:
                super.mo212721a2(view, accessibilityEvent);
                break;
        }
    }

    @Override // p000.C0608i4
    /* renamed from: a3 */
    public final void mo210912a3(View view, C0748k7 c0748k7) {
        int scrollRange;
        switch (this.f60371a3) {
            case 0:
                AccessibilityNodeInfo accessibilityNodeInfo = c0748k7.f57472a0;
                this.f56792a0.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                if (!DrawerLayout.m210100a7(view)) {
                    accessibilityNodeInfo.setParent(null);
                    break;
                }
                break;
            case 1:
                AccessibilityNodeInfo accessibilityNodeInfo2 = c0748k7.f57472a0;
                this.f56792a0.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo2);
                accessibilityNodeInfo2.setCollectionInfo(null);
                break;
            case 2:
                this.f56792a0.onInitializeAccessibilityNodeInfo(view, c0748k7.f57472a0);
                c0748k7.m213468b1(false);
                break;
            case 3:
                AccessibilityNodeInfo accessibilityNodeInfo3 = c0748k7.f57472a0;
                this.f56792a0.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo3);
                accessibilityNodeInfo3.setCollectionInfo(null);
                break;
            default:
                this.f56792a0.onInitializeAccessibilityNodeInfo(view, c0748k7.f57472a0);
                NestedScrollView nestedScrollView = (NestedScrollView) view;
                c0748k7.m213464a7(ScrollView.class.getName());
                if (nestedScrollView.isEnabled() && (scrollRange = nestedScrollView.getScrollRange()) > 0) {
                    c0748k7.m213468b1(true);
                    if (nestedScrollView.getScrollY() > 0) {
                        c0748k7.m213459a1(C0745k4.f57439a8);
                        c0748k7.m213459a1(C0745k4.f57443b2);
                    }
                    if (nestedScrollView.getScrollY() < scrollRange) {
                        c0748k7.m213459a1(C0745k4.f57438a7);
                        c0748k7.m213459a1(C0745k4.f57444b3);
                        break;
                    }
                }
                break;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0075  */
    @Override // p000.C0608i4
    /* renamed from: a6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean mo211166a6(View view, int i, Bundle bundle) {
        switch (this.f60371a3) {
            case 4:
                if (super.mo211166a6(view, i, bundle)) {
                    return true;
                }
                NestedScrollView nestedScrollView = (NestedScrollView) view;
                if (nestedScrollView.isEnabled()) {
                    int height = nestedScrollView.getHeight();
                    Rect rect = new Rect();
                    if (nestedScrollView.getMatrix().isIdentity() && nestedScrollView.getGlobalVisibleRect(rect)) {
                        height = rect.height();
                    }
                    if (i == 4096) {
                        int iMin = Math.min(nestedScrollView.getScrollY() + ((height - nestedScrollView.getPaddingBottom()) - nestedScrollView.getPaddingTop()), nestedScrollView.getScrollRange());
                        if (iMin != nestedScrollView.getScrollY()) {
                            nestedScrollView.m210097b9(0 - nestedScrollView.getScrollX(), iMin - nestedScrollView.getScrollY(), true);
                            return true;
                        }
                    } else if (i == 8192 || i == 16908344) {
                        int iMax = Math.max(nestedScrollView.getScrollY() - ((height - nestedScrollView.getPaddingBottom()) - nestedScrollView.getPaddingTop()), 0);
                        if (iMax != nestedScrollView.getScrollY()) {
                            nestedScrollView.m210097b9(0 - nestedScrollView.getScrollX(), iMax - nestedScrollView.getScrollY(), true);
                            return true;
                        }
                    } else if (i == 16908346) {
                    }
                }
                return false;
            default:
                return super.mo211166a6(view, i, bundle);
        }
    }
}
