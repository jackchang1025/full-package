package p000;

import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: oa */
/* loaded from: classes.dex */
public abstract class AbstractC0945oa extends BaseAdapter implements Filterable {

    /* renamed from: a0 */
    public boolean f58764a0;

    /* renamed from: a1 */
    public boolean f58765a1;

    /* renamed from: a2 */
    public Cursor f58766a2;

    /* renamed from: a3 */
    public int f58767a3;

    /* renamed from: a4 */
    public C0931ny f58768a4;

    /* renamed from: a5 */
    public C0933nz f58769a5;

    /* renamed from: a6 */
    public C0946ob f58770a6;

    /* renamed from: a0 */
    public abstract void mo214166a0(View view, Cursor cursor);

    /* renamed from: a1 */
    public void mo214167a1(Cursor cursor) {
        Cursor cursor2 = this.f58766a2;
        if (cursor == cursor2) {
            cursor2 = null;
        } else {
            if (cursor2 != null) {
                C0931ny c0931ny = this.f58768a4;
                if (c0931ny != null) {
                    cursor2.unregisterContentObserver(c0931ny);
                }
                C0933nz c0933nz = this.f58769a5;
                if (c0933nz != null) {
                    cursor2.unregisterDataSetObserver(c0933nz);
                }
            }
            this.f58766a2 = cursor;
            if (cursor != null) {
                C0931ny c0931ny2 = this.f58768a4;
                if (c0931ny2 != null) {
                    cursor.registerContentObserver(c0931ny2);
                }
                C0933nz c0933nz2 = this.f58769a5;
                if (c0933nz2 != null) {
                    cursor.registerDataSetObserver(c0933nz2);
                }
                this.f58767a3 = cursor.getColumnIndexOrThrow("_id");
                this.f58764a0 = true;
                notifyDataSetChanged();
            } else {
                this.f58767a3 = -1;
                this.f58764a0 = false;
                notifyDataSetInvalidated();
            }
        }
        if (cursor2 != null) {
            cursor2.close();
        }
    }

    /* renamed from: a2 */
    public abstract String mo214168a2(Cursor cursor);

    /* renamed from: a3 */
    public abstract View mo214169a3(ViewGroup viewGroup);

    @Override // android.widget.Adapter
    public final int getCount() {
        Cursor cursor;
        if (!this.f58764a0 || (cursor = this.f58766a2) == null) {
            return 0;
        }
        return cursor.getCount();
    }

    @Override // android.widget.BaseAdapter, android.widget.SpinnerAdapter
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        if (!this.f58764a0) {
            return null;
        }
        this.f58766a2.moveToPosition(i);
        if (view == null) {
            x21 x21Var = (x21) this;
            view = x21Var.f60996a9.inflate(x21Var.f60995a8, viewGroup, false);
        }
        mo214166a0(view, this.f58766a2);
        return view;
    }

    @Override // android.widget.Filterable
    public final Filter getFilter() {
        if (this.f58770a6 == null) {
            C0946ob c0946ob = new C0946ob();
            c0946ob.f58777a0 = this;
            this.f58770a6 = c0946ob;
        }
        return this.f58770a6;
    }

    @Override // android.widget.Adapter
    public final Object getItem(int i) {
        Cursor cursor;
        if (!this.f58764a0 || (cursor = this.f58766a2) == null) {
            return null;
        }
        cursor.moveToPosition(i);
        return this.f58766a2;
    }

    @Override // android.widget.Adapter
    public final long getItemId(int i) {
        Cursor cursor;
        if (this.f58764a0 && (cursor = this.f58766a2) != null && cursor.moveToPosition(i)) {
            return this.f58766a2.getLong(this.f58767a3);
        }
        return 0L;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (!this.f58764a0) {
            throw new IllegalStateException("this should only be called when the cursor is valid");
        }
        if (!this.f58766a2.moveToPosition(i)) {
            throw new IllegalStateException(tz0.m214802a2(i, "couldn't move cursor to position "));
        }
        if (view == null) {
            view = mo214169a3(viewGroup);
        }
        mo214166a0(view, this.f58766a2);
        return view;
    }
}
