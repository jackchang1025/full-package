package p000;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import com.google.android.material.R$attr;
import com.google.android.material.R$drawable;
import com.google.android.material.R$string;
import com.google.android.material.textfield.TextInputLayout;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: uq */
/* loaded from: classes2.dex */
public final class C1309uq extends AbstractC1416xg {

    /* renamed from: a4 */
    public final int f60490a4;

    /* renamed from: a5 */
    public final int f60491a5;

    /* renamed from: a6 */
    public final TimeInterpolator f60492a6;

    /* renamed from: a7 */
    public AutoCompleteTextView f60493a7;

    /* renamed from: a8 */
    public final ViewOnClickListenerC1203s1 f60494a8;

    /* renamed from: a9 */
    public final ViewOnFocusChangeListenerC0694iu f60495a9;

    /* renamed from: b0 */
    public final C0474ez f60496b0;

    /* renamed from: b1 */
    public boolean f60497b1;

    /* renamed from: b2 */
    public boolean f60498b2;

    /* renamed from: b3 */
    public boolean f60499b3;

    /* renamed from: b4 */
    public long f60500b4;

    /* renamed from: b5 */
    public AccessibilityManager f60501b5;

    /* renamed from: b6 */
    public ValueAnimator f60502b6;

    /* renamed from: b7 */
    public ValueAnimator f60503b7;

    public C1309uq(C1415xf c1415xf) {
        super(c1415xf);
        this.f60494a8 = new ViewOnClickListenerC1203s1(3, this);
        this.f60495a9 = new ViewOnFocusChangeListenerC0694iu(2, this);
        this.f60496b0 = new C0474ez(1, this);
        this.f60500b4 = Long.MAX_VALUE;
        this.f60491a5 = kg1.m213536e3(c1415xf.getContext(), R$attr.motionDurationShort3, 67);
        this.f60490a4 = kg1.m213536e3(c1415xf.getContext(), R$attr.motionDurationShort3, 50);
        this.f60492a6 = kg1.m213537e4(c1415xf.getContext(), R$attr.motionEasingLinearInterpolator, AbstractC1249t7.f60178a0);
    }

    @Override // p000.AbstractC1416xg
    /* renamed from: a0 */
    public final void mo213189a0() {
        if (this.f60501b5.isTouchExplorationEnabled() && this.f60493a7.getInputType() != 0 && !this.f61106a3.hasFocus()) {
            this.f60493a7.dismissDropDown();
        }
        this.f60493a7.post(new RunnableC0941o6(11, this));
    }

    @Override // p000.AbstractC1416xg
    /* renamed from: a2 */
    public final int mo213190a2() {
        return R$string.exposed_dropdown_menu_content_description;
    }

    @Override // p000.AbstractC1416xg
    /* renamed from: a3 */
    public final int mo213191a3() {
        return R$drawable.mtrl_dropdown_arrow;
    }

    @Override // p000.AbstractC1416xg
    /* renamed from: a4 */
    public final View.OnFocusChangeListener mo213192a4() {
        return this.f60495a9;
    }

    @Override // p000.AbstractC1416xg
    /* renamed from: a5 */
    public final View.OnClickListener mo213193a5() {
        return this.f60494a8;
    }

    @Override // p000.AbstractC1416xg
    /* renamed from: a7 */
    public final InterfaceC0702j1 mo214854a7() {
        return this.f60496b0;
    }

    @Override // p000.AbstractC1416xg
    /* renamed from: a8 */
    public final boolean mo214855a8(int i) {
        return i != 0;
    }

    @Override // p000.AbstractC1416xg
    /* renamed from: b0 */
    public final boolean mo214233b0() {
        return this.f60499b3;
    }

    @Override // p000.AbstractC1416xg
    /* renamed from: b1 */
    public final void mo213195b1(EditText editText) {
        if (!(editText instanceof AutoCompleteTextView)) {
            throw new RuntimeException("EditText needs to be an AutoCompleteTextView if an Exposed Dropdown Menu is being used.");
        }
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) editText;
        this.f60493a7 = autoCompleteTextView;
        autoCompleteTextView.setOnTouchListener(new ViewOnTouchListenerC1307uo(0, this));
        this.f60493a7.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() { // from class: up
            @Override // android.widget.AutoCompleteTextView.OnDismissListener
            public final void onDismiss() {
                C1309uq c1309uq = this.f60486a0;
                c1309uq.f60498b2 = true;
                c1309uq.f60500b4 = System.currentTimeMillis();
                c1309uq.m214858b8(false);
            }
        });
        this.f60493a7.setThreshold(0);
        TextInputLayout textInputLayout = this.f61103a0;
        textInputLayout.setErrorIconDrawable((Drawable) null);
        if (editText.getInputType() == 0 && this.f60501b5.isTouchExplorationEnabled()) {
            WeakHashMap weakHashMap = xa1.f61054a0;
            fa1.m212781b8(this.f61106a3, 2);
        }
        textInputLayout.setEndIconVisible(true);
    }

    @Override // p000.AbstractC1416xg
    /* renamed from: b2 */
    public final void mo214856b2(C0748k7 c0748k7) {
        boolean zIsShowingHintText;
        if (this.f60493a7.getInputType() == 0) {
            c0748k7.m213464a7(Spinner.class.getName());
        }
        AccessibilityNodeInfo accessibilityNodeInfo = c0748k7.f57472a0;
        if (Build.VERSION.SDK_INT >= 26) {
            zIsShowingHintText = accessibilityNodeInfo.isShowingHintText();
        } else {
            Bundle bundleM213449a0 = AbstractC0746k5.m213449a0(accessibilityNodeInfo);
            zIsShowingHintText = bundleM213449a0 != null && (bundleM213449a0.getInt("androidx.view.accessibility.AccessibilityNodeInfoCompat.BOOLEAN_PROPERTY_KEY", 0) & 4) == 4;
        }
        if (zIsShowingHintText) {
            c0748k7.m213467b0(null);
        }
    }

    @Override // p000.AbstractC1416xg
    /* renamed from: b3 */
    public final void mo214857b3(AccessibilityEvent accessibilityEvent) {
        if (this.f60501b5.isEnabled() && this.f60493a7.getInputType() == 0) {
            boolean z = accessibilityEvent.getEventType() == 32768 && this.f60499b3 && !this.f60493a7.isPopupShowing();
            if (accessibilityEvent.getEventType() == 1 || z) {
                m214859b9();
                this.f60498b2 = true;
                this.f60500b4 = System.currentTimeMillis();
            }
        }
    }

    @Override // p000.AbstractC1416xg
    /* renamed from: b6 */
    public final void mo213197b6() {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        TimeInterpolator timeInterpolator = this.f60492a6;
        valueAnimatorOfFloat.setInterpolator(timeInterpolator);
        valueAnimatorOfFloat.setDuration(this.f60491a5);
        int i = 0;
        valueAnimatorOfFloat.addUpdateListener(new C1306un(i, this));
        this.f60503b7 = valueAnimatorOfFloat;
        ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(1.0f, 0.0f);
        valueAnimatorOfFloat2.setInterpolator(timeInterpolator);
        valueAnimatorOfFloat2.setDuration(this.f60490a4);
        valueAnimatorOfFloat2.addUpdateListener(new C1306un(i, this));
        this.f60502b6 = valueAnimatorOfFloat2;
        valueAnimatorOfFloat2.addListener(new C0847m3(2, this));
        this.f60501b5 = (AccessibilityManager) this.f61105a2.getSystemService("accessibility");
    }

    @Override // p000.AbstractC1416xg
    /* renamed from: b7 */
    public final void mo213198b7() {
        AutoCompleteTextView autoCompleteTextView = this.f60493a7;
        if (autoCompleteTextView != null) {
            autoCompleteTextView.setOnTouchListener(null);
            this.f60493a7.setOnDismissListener(null);
        }
    }

    /* renamed from: b8 */
    public final void m214858b8(boolean z) {
        if (this.f60499b3 != z) {
            this.f60499b3 = z;
            this.f60503b7.cancel();
            this.f60502b6.start();
        }
    }

    /* renamed from: b9 */
    public final void m214859b9() {
        if (this.f60493a7 == null) {
            return;
        }
        long jCurrentTimeMillis = System.currentTimeMillis() - this.f60500b4;
        if (jCurrentTimeMillis < 0 || jCurrentTimeMillis > 300) {
            this.f60498b2 = false;
        }
        if (this.f60498b2) {
            this.f60498b2 = false;
            return;
        }
        m214858b8(!this.f60499b3);
        if (!this.f60499b3) {
            this.f60493a7.dismissDropDown();
        } else {
            this.f60493a7.requestFocus();
            this.f60493a7.showDropDown();
        }
    }
}
