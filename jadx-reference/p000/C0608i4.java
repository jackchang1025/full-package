package p000;

import android.os.Bundle;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeProvider;
import androidx.core.R$id;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: i4 */
/* loaded from: classes.dex */
public class C0608i4 {

    /* renamed from: a2 */
    public static final View.AccessibilityDelegate f56791a2 = new View.AccessibilityDelegate();

    /* renamed from: a0 */
    public final View.AccessibilityDelegate f56792a0;

    /* renamed from: a1 */
    public final C0606i2 f56793a1;

    public C0608i4() {
        this(f56791a2);
    }

    /* renamed from: a0 */
    public boolean mo213097a0(View view, AccessibilityEvent accessibilityEvent) {
        return this.f56792a0.dispatchPopulateAccessibilityEvent(view, accessibilityEvent);
    }

    /* renamed from: a1 */
    public tg0 mo213098a1(View view) {
        AccessibilityNodeProvider accessibilityNodeProviderM213095a0 = AbstractC0607i3.m213095a0(this.f56792a0, view);
        if (accessibilityNodeProviderM213095a0 != null) {
            return new tg0(1, accessibilityNodeProviderM213095a0);
        }
        return null;
    }

    /* renamed from: a2 */
    public void mo212721a2(View view, AccessibilityEvent accessibilityEvent) {
        this.f56792a0.onInitializeAccessibilityEvent(view, accessibilityEvent);
    }

    /* renamed from: a3 */
    public void mo210912a3(View view, C0748k7 c0748k7) {
        this.f56792a0.onInitializeAccessibilityNodeInfo(view, c0748k7.f57472a0);
    }

    /* renamed from: a4 */
    public void mo212782a4(View view, AccessibilityEvent accessibilityEvent) {
        this.f56792a0.onPopulateAccessibilityEvent(view, accessibilityEvent);
    }

    /* renamed from: a5 */
    public boolean mo213099a5(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
        return this.f56792a0.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
    }

    /* renamed from: a6 */
    public boolean mo211166a6(View view, int i, Bundle bundle) {
        boolean zM213096a1;
        WeakReference weakReference;
        ClickableSpan clickableSpan;
        List list = (List) view.getTag(R$id.tag_accessibility_actions);
        if (list == null) {
            list = Collections.EMPTY_LIST;
        }
        boolean z = false;
        int i2 = 0;
        while (true) {
            if (i2 >= list.size()) {
                break;
            }
            C0745k4 c0745k4 = (C0745k4) list.get(i2);
            if (c0745k4.m213448a0() == i) {
                Class cls = c0745k4.f57448a2;
                InterfaceC0812l9 interfaceC0812l9 = c0745k4.f57449a3;
                if (interfaceC0812l9 != null) {
                    if (cls != null) {
                        try {
                            if (cls.getDeclaredConstructor(null).newInstance(null) == null) {
                                throw null;
                            }
                            throw new ClassCastException();
                        } catch (Exception unused) {
                        }
                    }
                    zM213096a1 = interfaceC0812l9.mo210913a2(view);
                }
            } else {
                i2++;
            }
        }
        zM213096a1 = false;
        if (!zM213096a1) {
            zM213096a1 = AbstractC0607i3.m213096a1(this.f56792a0, view, i, bundle);
        }
        if (zM213096a1 || i != R$id.accessibility_action_clickable_span || bundle == null) {
            return zM213096a1;
        }
        int i3 = bundle.getInt("ACCESSIBILITY_CLICKABLE_SPAN_ID", -1);
        SparseArray sparseArray = (SparseArray) view.getTag(R$id.tag_accessibility_clickable_spans);
        if (sparseArray != null && (weakReference = (WeakReference) sparseArray.get(i3)) != null && (clickableSpan = (ClickableSpan) weakReference.get()) != null) {
            CharSequence text = view.createAccessibilityNodeInfo().getText();
            ClickableSpan[] clickableSpanArr = text instanceof Spanned ? (ClickableSpan[]) ((Spanned) text).getSpans(0, text.length(), ClickableSpan.class) : null;
            int i4 = 0;
            while (true) {
                if (clickableSpanArr == null || i4 >= clickableSpanArr.length) {
                    break;
                }
                if (clickableSpan.equals(clickableSpanArr[i4])) {
                    clickableSpan.onClick(view);
                    z = true;
                    break;
                }
                i4++;
            }
        }
        return z;
    }

    /* renamed from: a7 */
    public void mo213100a7(View view, int i) {
        this.f56792a0.sendAccessibilityEvent(view, i);
    }

    /* renamed from: a8 */
    public void mo213101a8(View view, AccessibilityEvent accessibilityEvent) {
        this.f56792a0.sendAccessibilityEventUnchecked(view, accessibilityEvent);
    }

    public C0608i4(View.AccessibilityDelegate accessibilityDelegate) {
        this.f56792a0 = accessibilityDelegate;
        this.f56793a1 = new C0606i2(this);
    }
}
