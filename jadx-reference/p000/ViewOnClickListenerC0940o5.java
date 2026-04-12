package p000;

import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import androidx.appcompat.widget.ActivityChooserView;
import androidx.appcompat.widget.C0041a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: o5 */
/* loaded from: classes.dex */
public final class ViewOnClickListenerC0940o5 implements AdapterView.OnItemClickListener, View.OnClickListener, View.OnLongClickListener, PopupWindow.OnDismissListener {

    /* renamed from: a0 */
    public final /* synthetic */ ActivityChooserView f58737a0;

    public ViewOnClickListenerC0940o5(ActivityChooserView activityChooserView) {
        this.f58737a0 = activityChooserView;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        ActivityChooserView activityChooserView = this.f58737a0;
        C0939o4 c0939o4 = activityChooserView.f43875a0;
        if (view == activityChooserView.f43880a5) {
            activityChooserView.m209875a0();
            c0939o4.getClass();
            throw null;
        }
        if (view != activityChooserView.f43878a3) {
            throw new IllegalArgumentException();
        }
        c0939o4.getClass();
        throw new IllegalStateException("No data model. Did you call #setDataModel?");
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public final void onDismiss() {
        C0041a1 c0041a1;
        bf0 bf0Var;
        ActivityChooserView activityChooserView = this.f58737a0;
        PopupWindow.OnDismissListener onDismissListener = activityChooserView.f43884a9;
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
        AbstractC0904n8 abstractC0904n8 = activityChooserView.f43881a6;
        if (abstractC0904n8 == null || (c0041a1 = abstractC0904n8.f58463a0) == null || (bf0Var = c0041a1.f44140a2) == null) {
            return;
        }
        bf0Var.m210690a2(false);
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
        ((C0939o4) adapterView.getAdapter()).getClass();
        ActivityChooserView activityChooserView = this.f58737a0;
        activityChooserView.m209875a0();
        activityChooserView.f43875a0.getClass();
        throw null;
    }

    @Override // android.view.View.OnLongClickListener
    public final boolean onLongClick(View view) {
        ActivityChooserView activityChooserView = this.f58737a0;
        if (view != activityChooserView.f43880a5) {
            throw new IllegalArgumentException();
        }
        activityChooserView.f43875a0.getClass();
        throw null;
    }
}
