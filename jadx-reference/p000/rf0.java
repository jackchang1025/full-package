package p000;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import androidx.appcompat.view.menu.ListMenuItemView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class rf0 extends C1304ul {

    /* renamed from: b2 */
    public final int f59762b2;

    /* renamed from: b3 */
    public final int f59763b3;

    /* renamed from: b4 */
    public ef0 f59764b4;

    /* renamed from: b5 */
    public ff0 f59765b5;

    public rf0(Context context, boolean z) {
        super(context, z);
        if (1 == qf0.m214385a0(context.getResources().getConfiguration())) {
            this.f59762b2 = 21;
            this.f59763b3 = 22;
        } else {
            this.f59762b2 = 22;
            this.f59763b3 = 21;
        }
    }

    @Override // p000.C1304ul, android.view.View
    public final boolean onHoverEvent(MotionEvent motionEvent) {
        ye0 ye0Var;
        int headersCount;
        int iPointToPosition;
        int i;
        if (this.f59764b4 != null) {
            ListAdapter adapter = getAdapter();
            if (adapter instanceof HeaderViewListAdapter) {
                HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) adapter;
                headersCount = headerViewListAdapter.getHeadersCount();
                ye0Var = (ye0) headerViewListAdapter.getWrappedAdapter();
            } else {
                ye0Var = (ye0) adapter;
                headersCount = 0;
            }
            ff0 ff0VarM215277a1 = (motionEvent.getAction() == 10 || (iPointToPosition = pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY())) == -1 || (i = iPointToPosition - headersCount) < 0 || i >= ye0Var.getCount()) ? null : ye0Var.getItem(i);
            ff0 ff0Var = this.f59765b5;
            if (ff0Var != ff0VarM215277a1) {
                bf0 bf0Var = ye0Var.f61296a0;
                if (ff0Var != null) {
                    this.f59764b4.mo209943a5(bf0Var, ff0Var);
                }
                this.f59765b5 = ff0VarM215277a1;
                if (ff0VarM215277a1 != null) {
                    this.f59764b4.mo209944a9(bf0Var, ff0VarM215277a1);
                }
            }
        }
        return super.onHoverEvent(motionEvent);
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.view.View, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        ListMenuItemView listMenuItemView = (ListMenuItemView) getSelectedView();
        if (listMenuItemView != null && i == this.f59762b2) {
            if (listMenuItemView.isEnabled() && listMenuItemView.getItemData().hasSubMenu()) {
                performItemClick(listMenuItemView, getSelectedItemPosition(), getSelectedItemId());
            }
            return true;
        }
        if (listMenuItemView == null || i != this.f59763b3) {
            return super.onKeyDown(i, keyEvent);
        }
        setSelection(-1);
        ListAdapter adapter = getAdapter();
        (adapter instanceof HeaderViewListAdapter ? (ye0) ((HeaderViewListAdapter) adapter).getWrappedAdapter() : (ye0) adapter).f61296a0.m210690a2(false);
        return true;
    }

    public void setHoverListener(ef0 ef0Var) {
        this.f59764b4 = ef0Var;
    }

    @Override // p000.C1304ul, android.widget.AbsListView
    public /* bridge */ /* synthetic */ void setSelector(Drawable drawable) {
        super.setSelector(drawable);
    }
}
