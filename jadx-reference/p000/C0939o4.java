package p000;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import androidx.appcompat.R$id;
import androidx.appcompat.R$layout;
import androidx.appcompat.widget.ActivityChooserView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: o4 */
/* loaded from: classes.dex */
public final class C0939o4 extends BaseAdapter {

    /* renamed from: a0 */
    public final /* synthetic */ ActivityChooserView f58732a0;

    public C0939o4(ActivityChooserView activityChooserView) {
        this.f58732a0 = activityChooserView;
    }

    @Override // android.widget.Adapter
    public final int getCount() {
        throw null;
    }

    @Override // android.widget.Adapter
    public final Object getItem(int i) {
        throw null;
    }

    @Override // android.widget.Adapter
    public final long getItemId(int i) {
        return i;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public final int getItemViewType(int i) {
        return 0;
    }

    @Override // android.widget.Adapter
    public final View getView(int i, View view, ViewGroup viewGroup) {
        ActivityChooserView activityChooserView = this.f58732a0;
        if (view == null || view.getId() != R$id.list_item) {
            view = LayoutInflater.from(activityChooserView.getContext()).inflate(R$layout.abc_activity_chooser_view_list_item, viewGroup, false);
        }
        activityChooserView.getContext().getPackageManager();
        throw null;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public final int getViewTypeCount() {
        return 3;
    }
}
