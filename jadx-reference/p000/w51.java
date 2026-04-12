package p000;

import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.EditText;
import androidx.appcompat.widget.AppCompatTextView;
import com.google.android.material.textfield.TextInputLayout;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class w51 extends C0608i4 {

    /* renamed from: a3 */
    public final TextInputLayout f60775a3;

    public w51(TextInputLayout textInputLayout) {
        this.f60775a3 = textInputLayout;
    }

    @Override // p000.C0608i4
    /* renamed from: a3 */
    public final void mo210912a3(View view, C0748k7 c0748k7) {
        AccessibilityNodeInfo accessibilityNodeInfo = c0748k7.f57472a0;
        this.f56792a0.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
        TextInputLayout textInputLayout = this.f60775a3;
        EditText editText = textInputLayout.getEditText();
        CharSequence text = editText != null ? editText.getText() : null;
        CharSequence hint = textInputLayout.getHint();
        CharSequence error = textInputLayout.getError();
        CharSequence placeholderText = textInputLayout.getPlaceholderText();
        int counterMaxLength = textInputLayout.getCounterMaxLength();
        CharSequence counterOverflowDescription = textInputLayout.getCounterOverflowDescription();
        boolean zIsEmpty = TextUtils.isEmpty(text);
        boolean zIsEmpty2 = TextUtils.isEmpty(hint);
        boolean z = textInputLayout.f49999g6;
        boolean zIsEmpty3 = TextUtils.isEmpty(error);
        boolean z2 = (zIsEmpty3 && TextUtils.isEmpty(counterOverflowDescription)) ? false : true;
        String string = !zIsEmpty2 ? hint.toString() : "";
        w11 w11Var = textInputLayout.f49934a1;
        AppCompatTextView appCompatTextView = w11Var.f60741a1;
        if (appCompatTextView.getVisibility() == 0) {
            accessibilityNodeInfo.setLabelFor(appCompatTextView);
            accessibilityNodeInfo.setTraversalAfter(appCompatTextView);
        } else {
            accessibilityNodeInfo.setTraversalAfter(w11Var.f60743a3);
        }
        if (!zIsEmpty) {
            c0748k7.m213469b2(text);
        } else if (!TextUtils.isEmpty(string)) {
            c0748k7.m213469b2(string);
            if (!z && placeholderText != null) {
                c0748k7.m213469b2(string + ", " + ((Object) placeholderText));
            }
        } else if (placeholderText != null) {
            c0748k7.m213469b2(placeholderText);
        }
        if (!TextUtils.isEmpty(string)) {
            int i = Build.VERSION.SDK_INT;
            if (i >= 26) {
                c0748k7.m213467b0(string);
            } else {
                if (!zIsEmpty) {
                    string = ((Object) text) + ", " + string;
                }
                c0748k7.m213469b2(string);
            }
            if (i >= 26) {
                accessibilityNodeInfo.setShowingHintText(zIsEmpty);
            } else {
                c0748k7.m213463a6(4, zIsEmpty);
            }
        }
        if (text == null || text.length() != counterMaxLength) {
            counterMaxLength = -1;
        }
        accessibilityNodeInfo.setMaxTextLength(counterMaxLength);
        if (z2) {
            if (zIsEmpty3) {
                error = counterOverflowDescription;
            }
            accessibilityNodeInfo.setError(error);
        }
        AppCompatTextView appCompatTextView2 = textInputLayout.f49942a9.f59411c4;
        if (appCompatTextView2 != null) {
            accessibilityNodeInfo.setLabelFor(appCompatTextView2);
        }
        textInputLayout.f49935a2.m215157a1().mo214856b2(c0748k7);
    }

    @Override // p000.C0608i4
    /* renamed from: a4 */
    public final void mo212782a4(View view, AccessibilityEvent accessibilityEvent) {
        super.mo212782a4(view, accessibilityEvent);
        this.f60775a3.f49935a2.m215157a1().mo214857b3(accessibilityEvent);
    }
}
