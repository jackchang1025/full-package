package p000;

import android.content.Context;
import android.graphics.Rect;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.PopupWindow;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class kf0 implements p01, tf0, AdapterView.OnItemClickListener {

    /* renamed from: a0 */
    public Rect f57514a0;

    /* renamed from: b4 */
    public static int m213497b4(ListAdapter listAdapter, Context context, int i) {
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
        int count = listAdapter.getCount();
        int i2 = 0;
        int i3 = 0;
        FrameLayout frameLayout = null;
        View view = null;
        for (int i4 = 0; i4 < count; i4++) {
            int itemViewType = listAdapter.getItemViewType(i4);
            if (itemViewType != i3) {
                view = null;
                i3 = itemViewType;
            }
            if (frameLayout == null) {
                frameLayout = new FrameLayout(context);
            }
            view = listAdapter.getView(i4, view, frameLayout);
            view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
            int measuredWidth = view.getMeasuredWidth();
            if (measuredWidth >= i) {
                return i;
            }
            if (measuredWidth > i2) {
                i2 = measuredWidth;
            }
        }
        return i2;
    }

    @Override // p000.tf0
    /* renamed from: a2 */
    public final boolean mo62a2(ff0 ff0Var) {
        return false;
    }

    @Override // p000.tf0
    /* renamed from: a6 */
    public final boolean mo64a6(ff0 ff0Var) {
        return false;
    }

    /* renamed from: b3 */
    public abstract void mo212967b3(bf0 bf0Var);

    /* renamed from: b5 */
    public abstract void mo212968b5(View view);

    /* renamed from: b6 */
    public abstract void mo212969b6(boolean z);

    /* renamed from: b7 */
    public abstract void mo212970b7(int i);

    /* renamed from: b8 */
    public abstract void mo212971b8(int i);

    /* renamed from: b9 */
    public abstract void mo212972b9(PopupWindow.OnDismissListener onDismissListener);

    /* renamed from: c0 */
    public abstract void mo212973c0(boolean z);

    /* renamed from: c1 */
    public abstract void mo212974c1(int i);

    @Override // p000.tf0
    public final int getId() {
        return 0;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
        ListAdapter listAdapter = (ListAdapter) adapterView.getAdapter();
        (listAdapter instanceof HeaderViewListAdapter ? (ye0) ((HeaderViewListAdapter) listAdapter).getWrappedAdapter() : (ye0) listAdapter).f61296a0.m210704b6((MenuItem) listAdapter.getItem(i), this, !(this instanceof ViewOnKeyListenerC0542gn) ? 0 : 4);
    }

    @Override // p000.tf0
    /* renamed from: a8 */
    public final void mo65a8(Context context, bf0 bf0Var) {
    }
}
