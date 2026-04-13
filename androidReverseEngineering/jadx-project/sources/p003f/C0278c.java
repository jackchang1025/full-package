package p003f;

import android.os.Bundle;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;

/* renamed from: f.c */
/* loaded from: classes.dex */
public final class C0278c extends AbstractC0276a {

    /* renamed from: b */
    public final float f483b;

    public C0278c(float f2) {
        super(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_PROGRESS_VALUE);
        this.f483b = f2;
    }

    @Override // p003f.AbstractC0276a
    /* renamed from: a */
    public final void mo775a(Bundle bundle) {
        bundle.putFloat(this.f481a, this.f483b);
    }
}
