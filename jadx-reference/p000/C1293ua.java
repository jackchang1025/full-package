package p000;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ua */
/* loaded from: classes.dex */
public final class C1293ua extends C0608i4 {

    /* renamed from: a3 */
    public final /* synthetic */ int f60356a3;

    /* renamed from: a4 */
    public final Object f60357a4;

    /* renamed from: a5 */
    public final Object f60358a5;

    public C1293ua(er0 er0Var) {
        this.f60356a3 = 1;
        this.f60358a5 = new WeakHashMap();
        this.f60357a4 = er0Var;
    }

    @Override // p000.C0608i4
    /* renamed from: a0 */
    public final boolean mo213097a0(View view, AccessibilityEvent accessibilityEvent) {
        int i = this.f60356a3;
        View.AccessibilityDelegate accessibilityDelegate = this.f56792a0;
        Object obj = this.f60358a5;
        switch (i) {
            case 0:
                DrawerLayout drawerLayout = (DrawerLayout) obj;
                if (accessibilityEvent.getEventType() != 32) {
                    break;
                } else {
                    accessibilityEvent.getText();
                    View viewM210108a4 = drawerLayout.m210108a4();
                    if (viewM210108a4 != null) {
                        int iM210110a6 = drawerLayout.m210110a6(viewM210108a4);
                        drawerLayout.getClass();
                        WeakHashMap weakHashMap = xa1.f61054a0;
                        Gravity.getAbsoluteGravity(iM210110a6, ga1.m212904a3(drawerLayout));
                        break;
                    }
                }
                break;
            default:
                C0608i4 c0608i4 = (C0608i4) ((WeakHashMap) obj).get(view);
                if (c0608i4 == null) {
                    break;
                } else {
                    break;
                }
        }
        return accessibilityDelegate.dispatchPopulateAccessibilityEvent(view, accessibilityEvent);
    }

    @Override // p000.C0608i4
    /* renamed from: a1 */
    public tg0 mo213098a1(View view) {
        switch (this.f60356a3) {
            case 1:
                C0608i4 c0608i4 = (C0608i4) ((WeakHashMap) this.f60358a5).get(view);
                if (c0608i4 == null) {
                    break;
                } else {
                    break;
                }
        }
        return super.mo213098a1(view);
    }

    @Override // p000.C0608i4
    /* renamed from: a2 */
    public final void mo212721a2(View view, AccessibilityEvent accessibilityEvent) {
        switch (this.f60356a3) {
            case 0:
                super.mo212721a2(view, accessibilityEvent);
                accessibilityEvent.setClassName("androidx.drawerlayout.widget.DrawerLayout");
                break;
            default:
                C0608i4 c0608i4 = (C0608i4) ((WeakHashMap) this.f60358a5).get(view);
                if (c0608i4 == null) {
                    super.mo212721a2(view, accessibilityEvent);
                    break;
                } else {
                    c0608i4.mo212721a2(view, accessibilityEvent);
                    break;
                }
        }
    }

    @Override // p000.C0608i4
    /* renamed from: a3 */
    public final void mo210912a3(View view, C0748k7 c0748k7) {
        int i = this.f60356a3;
        Object obj = this.f60357a4;
        View.AccessibilityDelegate accessibilityDelegate = this.f56792a0;
        switch (i) {
            case 0:
                AccessibilityNodeInfo accessibilityNodeInfo = c0748k7.f57472a0;
                if (DrawerLayout.f44964d0) {
                    accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                } else {
                    AccessibilityNodeInfo accessibilityNodeInfoObtain = AccessibilityNodeInfo.obtain(accessibilityNodeInfo);
                    accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoObtain);
                    c0748k7.f57473a1 = -1;
                    accessibilityNodeInfo.setSource(view);
                    WeakHashMap weakHashMap = xa1.f61054a0;
                    Object objM212768a5 = fa1.m212768a5(view);
                    if (objM212768a5 instanceof View) {
                        accessibilityNodeInfo.setParent((View) objM212768a5);
                    }
                    Rect rect = (Rect) obj;
                    accessibilityNodeInfoObtain.getBoundsInScreen(rect);
                    accessibilityNodeInfo.setBoundsInScreen(rect);
                    accessibilityNodeInfo.setVisibleToUser(accessibilityNodeInfoObtain.isVisibleToUser());
                    accessibilityNodeInfo.setPackageName(accessibilityNodeInfoObtain.getPackageName());
                    c0748k7.m213464a7(accessibilityNodeInfoObtain.getClassName());
                    c0748k7.m213466a9(accessibilityNodeInfoObtain.getContentDescription());
                    accessibilityNodeInfo.setEnabled(accessibilityNodeInfoObtain.isEnabled());
                    accessibilityNodeInfo.setFocused(accessibilityNodeInfoObtain.isFocused());
                    accessibilityNodeInfo.setAccessibilityFocused(accessibilityNodeInfoObtain.isAccessibilityFocused());
                    accessibilityNodeInfo.setSelected(accessibilityNodeInfoObtain.isSelected());
                    c0748k7.m213458a0(accessibilityNodeInfoObtain.getActions());
                    ViewGroup viewGroup = (ViewGroup) view;
                    int childCount = viewGroup.getChildCount();
                    for (int i2 = 0; i2 < childCount; i2++) {
                        View childAt = viewGroup.getChildAt(i2);
                        if (DrawerLayout.m210100a7(childAt)) {
                            accessibilityNodeInfo.addChild(childAt);
                        }
                    }
                }
                c0748k7.m213464a7("androidx.drawerlayout.widget.DrawerLayout");
                accessibilityNodeInfo.setFocusable(false);
                accessibilityNodeInfo.setFocused(false);
                accessibilityNodeInfo.removeAction((AccessibilityNodeInfo.AccessibilityAction) C0745k4.f57435a4.f57446a0);
                accessibilityNodeInfo.removeAction((AccessibilityNodeInfo.AccessibilityAction) C0745k4.f57436a5.f57446a0);
                break;
            default:
                AccessibilityNodeInfo accessibilityNodeInfo2 = c0748k7.f57472a0;
                er0 er0Var = (er0) obj;
                RecyclerView recyclerView = er0Var.f56101a3;
                RecyclerView recyclerView2 = er0Var.f56101a3;
                if (!recyclerView.m210375d7() && recyclerView2.getLayoutManager() != null) {
                    recyclerView2.getLayoutManager().m214318e4(view, c0748k7);
                    C0608i4 c0608i4 = (C0608i4) ((WeakHashMap) this.f60358a5).get(view);
                    if (c0608i4 != null) {
                        c0608i4.mo210912a3(view, c0748k7);
                        break;
                    } else {
                        accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo2);
                        break;
                    }
                } else {
                    accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo2);
                    break;
                }
                break;
        }
    }

    @Override // p000.C0608i4
    /* renamed from: a4 */
    public void mo212782a4(View view, AccessibilityEvent accessibilityEvent) {
        switch (this.f60356a3) {
            case 1:
                C0608i4 c0608i4 = (C0608i4) ((WeakHashMap) this.f60358a5).get(view);
                if (c0608i4 == null) {
                    super.mo212782a4(view, accessibilityEvent);
                    break;
                } else {
                    c0608i4.mo212782a4(view, accessibilityEvent);
                    break;
                }
            default:
                super.mo212782a4(view, accessibilityEvent);
                break;
        }
    }

    @Override // p000.C0608i4
    /* renamed from: a5 */
    public final boolean mo213099a5(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
        switch (this.f60356a3) {
            case 0:
                if (DrawerLayout.f44964d0 || DrawerLayout.m210100a7(view)) {
                    break;
                }
                break;
            default:
                C0608i4 c0608i4 = (C0608i4) ((WeakHashMap) this.f60358a5).get(viewGroup);
                if (c0608i4 == null) {
                    break;
                } else {
                    break;
                }
        }
        return this.f56792a0.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
    }

    @Override // p000.C0608i4
    /* renamed from: a6 */
    public boolean mo211166a6(View view, int i, Bundle bundle) {
        switch (this.f60356a3) {
            case 1:
                er0 er0Var = (er0) this.f60357a4;
                RecyclerView recyclerView = er0Var.f56101a3;
                RecyclerView recyclerView2 = er0Var.f56101a3;
                if (!recyclerView.m210375d7() && recyclerView2.getLayoutManager() != null) {
                    C0608i4 c0608i4 = (C0608i4) ((WeakHashMap) this.f60358a5).get(view);
                    if (c0608i4 == null ? !super.mo211166a6(view, i, bundle) : !c0608i4.mo211166a6(view, i, bundle)) {
                        vq0 vq0Var = recyclerView2.getLayoutManager().f59319a1.f45255a1;
                        break;
                    }
                } else {
                    break;
                }
                break;
        }
        return super.mo211166a6(view, i, bundle);
    }

    @Override // p000.C0608i4
    /* renamed from: a7 */
    public void mo213100a7(View view, int i) {
        switch (this.f60356a3) {
            case 1:
                C0608i4 c0608i4 = (C0608i4) ((WeakHashMap) this.f60358a5).get(view);
                if (c0608i4 == null) {
                    super.mo213100a7(view, i);
                    break;
                } else {
                    c0608i4.mo213100a7(view, i);
                    break;
                }
            default:
                super.mo213100a7(view, i);
                break;
        }
    }

    @Override // p000.C0608i4
    /* renamed from: a8 */
    public void mo213101a8(View view, AccessibilityEvent accessibilityEvent) {
        switch (this.f60356a3) {
            case 1:
                C0608i4 c0608i4 = (C0608i4) ((WeakHashMap) this.f60358a5).get(view);
                if (c0608i4 == null) {
                    super.mo213101a8(view, accessibilityEvent);
                    break;
                } else {
                    c0608i4.mo213101a8(view, accessibilityEvent);
                    break;
                }
            default:
                super.mo213101a8(view, accessibilityEvent);
                break;
        }
    }

    public C1293ua(DrawerLayout drawerLayout) {
        this.f60356a3 = 0;
        this.f60358a5 = drawerLayout;
        this.f60357a4 = new Rect();
    }
}
