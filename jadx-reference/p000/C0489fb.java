package p000;

import android.content.res.Resources;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.google.android.material.R$string;
import com.google.android.material.bottomsheet.BottomSheetDragHandleView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.datepicker.C0202b2;
import com.google.android.material.datepicker.MaterialCalendar;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.NavigationMenuItemView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: fb */
/* loaded from: classes2.dex */
public final class C0489fb extends C0608i4 {

    /* renamed from: a3 */
    public final /* synthetic */ int f56192a3;

    /* renamed from: a4 */
    public final /* synthetic */ Object f56193a4;

    public /* synthetic */ C0489fb(int i, Object obj) {
        this.f56192a3 = i;
        this.f56193a4 = obj;
    }

    @Override // p000.C0608i4
    /* renamed from: a2 */
    public void mo212721a2(View view, AccessibilityEvent accessibilityEvent) {
        switch (this.f56192a3) {
            case 1:
                super.mo212721a2(view, accessibilityEvent);
                accessibilityEvent.setChecked(((CheckableImageButton) this.f56193a4).f49538a3);
                break;
            default:
                super.mo212721a2(view, accessibilityEvent);
                break;
        }
    }

    @Override // p000.C0608i4
    /* renamed from: a3 */
    public void mo210912a3(View view, C0748k7 c0748k7) throws Resources.NotFoundException {
        int i;
        int i2 = this.f56192a3;
        Object obj = this.f56193a4;
        View.AccessibilityDelegate accessibilityDelegate = this.f56792a0;
        switch (i2) {
            case 1:
                AccessibilityNodeInfo accessibilityNodeInfo = c0748k7.f57472a0;
                accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                CheckableImageButton checkableImageButton = (CheckableImageButton) obj;
                accessibilityNodeInfo.setCheckable(checkableImageButton.f49539a4);
                accessibilityNodeInfo.setChecked(checkableImageButton.f49538a3);
                break;
            case 2:
                accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, c0748k7.f57472a0);
                MaterialButtonToggleGroup materialButtonToggleGroup = (MaterialButtonToggleGroup) obj;
                int i3 = MaterialButtonToggleGroup.f49269b0;
                if (view instanceof MaterialButton) {
                    int i4 = 0;
                    for (int i5 = 0; i5 < materialButtonToggleGroup.getChildCount(); i5++) {
                        if (materialButtonToggleGroup.getChildAt(i5) == view) {
                            i = i4;
                        } else {
                            if ((materialButtonToggleGroup.getChildAt(i5) instanceof MaterialButton) && materialButtonToggleGroup.m210964a2(i5)) {
                                i4++;
                            }
                        }
                    }
                    i = -1;
                } else {
                    i = -1;
                }
                c0748k7.m213465a8(C0747k6.m213451a1(0, 1, i, 1, false, ((MaterialButton) view).f49265b4));
                break;
            case 3:
                accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, c0748k7.f57472a0);
                MaterialCalendar materialCalendar = (MaterialCalendar) obj;
                c0748k7.m213467b0(materialCalendar.f49383f7.getVisibility() == 0 ? materialCalendar.m210152c5().getResources().getString(R$string.mtrl_picker_toggle_to_year_selection) : materialCalendar.m210152c5().getResources().getString(R$string.mtrl_picker_toggle_to_day_selection));
                break;
            case 4:
                accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, c0748k7.f57472a0);
                c0748k7.m213466a9(((C0202b2) obj).m211024d2().mo211006b1() + ", " + ((Object) c0748k7.m213462a5()));
                break;
            case 5:
                AccessibilityNodeInfo accessibilityNodeInfo2 = c0748k7.f57472a0;
                accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo2);
                accessibilityNodeInfo2.setCheckable(((NavigationMenuItemView) obj).f49556c3);
                break;
            default:
                super.mo210912a3(view, c0748k7);
                break;
        }
    }

    @Override // p000.C0608i4
    /* renamed from: a4 */
    public void mo212782a4(View view, AccessibilityEvent accessibilityEvent) {
        switch (this.f56192a3) {
            case 0:
                super.mo212782a4(view, accessibilityEvent);
                if (accessibilityEvent.getEventType() == 1) {
                    BottomSheetDragHandleView bottomSheetDragHandleView = (BottomSheetDragHandleView) this.f56193a4;
                    int i = BottomSheetDragHandleView.f49241b2;
                    bottomSheetDragHandleView.m210955a2();
                    break;
                }
                break;
            default:
                super.mo212782a4(view, accessibilityEvent);
                break;
        }
    }
}
