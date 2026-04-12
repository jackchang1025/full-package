package p000;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import com.google.android.material.R$string;
import com.google.android.material.chip.Chip;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ho */
/* loaded from: classes2.dex */
public final class C0588ho extends AbstractC1430xu {

    /* renamed from: b6 */
    public final /* synthetic */ Chip f56686b6;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0588ho(Chip chip, Chip chip2) {
        super(chip2);
        this.f56686b6 = chip;
    }

    @Override // p000.AbstractC1430xu
    /* renamed from: b3 */
    public final int mo211128b3(float f, float f2) {
        int i = Chip.f49328c3;
        Chip chip = this.f56686b6;
        return (chip.m210989a3() && chip.getCloseIconTouchBounds().contains(f, f2)) ? 1 : 0;
    }

    @Override // p000.AbstractC1430xu
    /* renamed from: b4 */
    public final void mo211129b4(ArrayList arrayList) {
        C0590hq c0590hq;
        arrayList.add(0);
        int i = Chip.f49328c3;
        Chip chip = this.f56686b6;
        if (!chip.m210989a3() || (c0590hq = chip.f49332a4) == null || !c0590hq.f56702d6 || chip.f49335a7 == null) {
            return;
        }
        arrayList.add(1);
    }

    @Override // p000.AbstractC1430xu
    /* renamed from: b8 */
    public final boolean mo211130b8(int i, int i2, Bundle bundle) {
        boolean z = false;
        if (i2 == 16) {
            Chip chip = this.f56686b6;
            if (i == 0) {
                return chip.performClick();
            }
            if (i == 1) {
                chip.playSoundEffect(0);
                View.OnClickListener onClickListener = chip.f49335a7;
                if (onClickListener != null) {
                    onClickListener.onClick(chip);
                    z = true;
                }
                if (chip.f49347b9) {
                    chip.f49346b8.m215216c3(1, 1);
                }
            }
        }
        return z;
    }

    @Override // p000.AbstractC1430xu
    /* renamed from: b9 */
    public final void mo213055b9(C0748k7 c0748k7) {
        AccessibilityNodeInfo accessibilityNodeInfo = c0748k7.f57472a0;
        Chip chip = this.f56686b6;
        C0590hq c0590hq = chip.f49332a4;
        accessibilityNodeInfo.setCheckable(c0590hq != null && c0590hq.f56708e2);
        accessibilityNodeInfo.setClickable(chip.isClickable());
        c0748k7.m213464a7(chip.getAccessibilityClassName());
        c0748k7.m213469b2(chip.getText());
    }

    @Override // p000.AbstractC1430xu
    /* renamed from: c0 */
    public final void mo211131c0(int i, C0748k7 c0748k7) {
        AccessibilityNodeInfo accessibilityNodeInfo = c0748k7.f57472a0;
        if (i != 1) {
            c0748k7.m213466a9("");
            accessibilityNodeInfo.setBoundsInParent(Chip.f49329c4);
            return;
        }
        Chip chip = this.f56686b6;
        CharSequence closeIconContentDescription = chip.getCloseIconContentDescription();
        if (closeIconContentDescription != null) {
            c0748k7.m213466a9(closeIconContentDescription);
        } else {
            CharSequence text = chip.getText();
            c0748k7.m213466a9(chip.getContext().getString(R$string.mtrl_chip_close_icon_content_description, TextUtils.isEmpty(text) ? "" : text).trim());
        }
        accessibilityNodeInfo.setBoundsInParent(chip.getCloseIconTouchBoundsInt());
        c0748k7.m213459a1(C0745k4.f57437a6);
        accessibilityNodeInfo.setEnabled(chip.isEnabled());
    }

    @Override // p000.AbstractC1430xu
    /* renamed from: c1 */
    public final void mo213056c1(int i, boolean z) {
        if (i == 1) {
            Chip chip = this.f56686b6;
            chip.f49341b3 = z;
            chip.refreshDrawableState();
        }
    }
}
