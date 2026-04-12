package p000;

import android.content.Context;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class z21 extends ActionMode {

    /* renamed from: a0 */
    public final Context f61428a0;

    /* renamed from: a1 */
    public final AbstractC0903n7 f61429a1;

    public z21(Context context, AbstractC0903n7 abstractC0903n7) {
        this.f61428a0 = context;
        this.f61429a1 = abstractC0903n7;
    }

    @Override // android.view.ActionMode
    public final void finish() {
        this.f61429a1.mo214038a0();
    }

    @Override // android.view.ActionMode
    public final View getCustomView() {
        return this.f61429a1.mo214039a1();
    }

    @Override // android.view.ActionMode
    public final Menu getMenu() {
        return new wf0(this.f61428a0, this.f61429a1.mo214040a2());
    }

    @Override // android.view.ActionMode
    public final MenuInflater getMenuInflater() {
        return this.f61429a1.mo214041a3();
    }

    @Override // android.view.ActionMode
    public final CharSequence getSubtitle() {
        return this.f61429a1.mo214042a5();
    }

    @Override // android.view.ActionMode
    public final Object getTag() {
        return this.f61429a1.f58460a0;
    }

    @Override // android.view.ActionMode
    public final CharSequence getTitle() {
        return this.f61429a1.mo214043a6();
    }

    @Override // android.view.ActionMode
    public final boolean getTitleOptionalHint() {
        return this.f61429a1.f58461a1;
    }

    @Override // android.view.ActionMode
    public final void invalidate() {
        this.f61429a1.mo214044a7();
    }

    @Override // android.view.ActionMode
    public final boolean isTitleOptional() {
        return this.f61429a1.mo214045a8();
    }

    @Override // android.view.ActionMode
    public final void setCustomView(View view) {
        this.f61429a1.mo214046a9(view);
    }

    @Override // android.view.ActionMode
    public final void setSubtitle(CharSequence charSequence) {
        this.f61429a1.mo214048b2(charSequence);
    }

    @Override // android.view.ActionMode
    public final void setTag(Object obj) {
        this.f61429a1.f58460a0 = obj;
    }

    @Override // android.view.ActionMode
    public final void setTitle(CharSequence charSequence) {
        this.f61429a1.mo214050b4(charSequence);
    }

    @Override // android.view.ActionMode
    public final void setTitleOptionalHint(boolean z) {
        this.f61429a1.mo214051b5(z);
    }

    @Override // android.view.ActionMode
    public final void setSubtitle(int i) {
        this.f61429a1.mo214047b1(i);
    }

    @Override // android.view.ActionMode
    public final void setTitle(int i) {
        this.f61429a1.mo214049b3(i);
    }
}
