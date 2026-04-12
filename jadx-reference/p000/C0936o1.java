package p000;

import android.database.DataSetObserver;
import androidx.appcompat.widget.ActivityChooserView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: o1 */
/* loaded from: classes.dex */
public final class C0936o1 extends DataSetObserver {

    /* renamed from: a0 */
    public final /* synthetic */ int f58707a0;

    /* renamed from: a1 */
    public final /* synthetic */ ActivityChooserView f58708a1;

    public /* synthetic */ C0936o1(ActivityChooserView activityChooserView, int i) {
        this.f58707a0 = i;
        this.f58708a1 = activityChooserView;
    }

    @Override // android.database.DataSetObserver
    public final void onChanged() {
        switch (this.f58707a0) {
            case 0:
                super.onChanged();
                this.f58708a1.f43875a0.notifyDataSetChanged();
                return;
            default:
                super.onChanged();
                this.f58708a1.f43875a0.getClass();
                throw null;
        }
    }

    @Override // android.database.DataSetObserver
    public void onInvalidated() {
        switch (this.f58707a0) {
            case 0:
                super.onInvalidated();
                this.f58708a1.f43875a0.notifyDataSetInvalidated();
                break;
            default:
                super.onInvalidated();
                break;
        }
    }
}
