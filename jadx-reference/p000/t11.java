package p000;

import android.content.Context;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.C0041a1;
import java.lang.ref.WeakReference;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class t11 extends AbstractC0903n7 implements ze0 {

    /* renamed from: a2 */
    public Context f60119a2;

    /* renamed from: a3 */
    public ActionBarContextView f60120a3;

    /* renamed from: a4 */
    public eo0 f60121a4;

    /* renamed from: a5 */
    public WeakReference f60122a5;

    /* renamed from: a6 */
    public boolean f60123a6;

    /* renamed from: a7 */
    public bf0 f60124a7;

    @Override // p000.AbstractC0903n7
    /* renamed from: a0 */
    public final void mo214038a0() {
        if (this.f60123a6) {
            return;
        }
        this.f60123a6 = true;
        this.f60121a4.m212714b8(this);
    }

    @Override // p000.AbstractC0903n7
    /* renamed from: a1 */
    public final View mo214039a1() {
        WeakReference weakReference = this.f60122a5;
        if (weakReference != null) {
            return (View) weakReference.get();
        }
        return null;
    }

    @Override // p000.AbstractC0903n7
    /* renamed from: a2 */
    public final bf0 mo214040a2() {
        return this.f60124a7;
    }

    @Override // p000.AbstractC0903n7
    /* renamed from: a3 */
    public final MenuInflater mo214041a3() {
        return new c31(this.f60120a3.getContext());
    }

    @Override // p000.ze0
    /* renamed from: a4 */
    public final boolean mo214682a4(bf0 bf0Var, MenuItem menuItem) {
        return ((x31) this.f60121a4.f56088a1).m215112a4(this, menuItem);
    }

    @Override // p000.AbstractC0903n7
    /* renamed from: a5 */
    public final CharSequence mo214042a5() {
        return this.f60120a3.getSubtitle();
    }

    @Override // p000.AbstractC0903n7
    /* renamed from: a6 */
    public final CharSequence mo214043a6() {
        return this.f60120a3.getTitle();
    }

    @Override // p000.AbstractC0903n7
    /* renamed from: a7 */
    public final void mo214044a7() {
        this.f60121a4.m212715b9(this, this.f60124a7);
    }

    @Override // p000.AbstractC0903n7
    /* renamed from: a8 */
    public final boolean mo214045a8() {
        return this.f60120a3.f43833b8;
    }

    @Override // p000.AbstractC0903n7
    /* renamed from: a9 */
    public final void mo214046a9(View view) {
        this.f60120a3.setCustomView(view);
        this.f60122a5 = view != null ? new WeakReference(view) : null;
    }

    @Override // p000.ze0
    /* renamed from: b0 */
    public final void mo214683b0(bf0 bf0Var) {
        mo214044a7();
        C0041a1 c0041a1 = this.f60120a3.f43818a3;
        if (c0041a1 != null) {
            c0041a1.m209942b3();
        }
    }

    @Override // p000.AbstractC0903n7
    /* renamed from: b1 */
    public final void mo214047b1(int i) {
        mo214048b2(this.f60119a2.getString(i));
    }

    @Override // p000.AbstractC0903n7
    /* renamed from: b2 */
    public final void mo214048b2(CharSequence charSequence) {
        this.f60120a3.setSubtitle(charSequence);
    }

    @Override // p000.AbstractC0903n7
    /* renamed from: b3 */
    public final void mo214049b3(int i) {
        mo214050b4(this.f60119a2.getString(i));
    }

    @Override // p000.AbstractC0903n7
    /* renamed from: b4 */
    public final void mo214050b4(CharSequence charSequence) {
        this.f60120a3.setTitle(charSequence);
    }

    @Override // p000.AbstractC0903n7
    /* renamed from: b5 */
    public final void mo214051b5(boolean z) {
        this.f58461a1 = z;
        this.f60120a3.setTitleOptional(z);
    }
}
