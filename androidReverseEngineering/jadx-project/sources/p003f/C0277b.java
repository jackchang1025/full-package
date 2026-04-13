package p003f;

import android.os.Bundle;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;

/* renamed from: f.b */
/* loaded from: classes.dex */
public final class C0277b extends AbstractC0276a {

    /* renamed from: b */
    public final CharSequence f482b;

    public C0277b(CharSequence charSequence) {
        super(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE);
        this.f482b = charSequence;
    }

    @Override // p003f.AbstractC0276a
    /* renamed from: a */
    public final void mo775a(Bundle bundle) {
        bundle.putCharSequence(this.f481a, this.f482b);
    }
}
