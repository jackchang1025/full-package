package p000;

import android.view.CollapsibleActionView;
import android.view.View;
import android.widget.FrameLayout;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class hf0 extends FrameLayout implements InterfaceC0699iz {

    /* renamed from: a0 */
    public final CollapsibleActionView f56664a0;

    /* JADX WARN: Multi-variable type inference failed */
    public hf0(View view) {
        super(view.getContext());
        this.f56664a0 = (CollapsibleActionView) view;
        addView(view);
    }

    @Override // p000.InterfaceC0699iz
    public final void onActionViewCollapsed() {
        this.f56664a0.onActionViewCollapsed();
    }

    @Override // p000.InterfaceC0699iz
    public final void onActionViewExpanded() {
        this.f56664a0.onActionViewExpanded();
    }
}
