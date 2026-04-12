package p000;

import android.database.Cursor;
import android.widget.Filter;
import androidx.appcompat.widget.SearchView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ob */
/* loaded from: classes.dex */
public final class C0946ob extends Filter {

    /* renamed from: a0 */
    public AbstractC0945oa f58777a0;

    @Override // android.widget.Filter
    public final CharSequence convertResultToString(Object obj) {
        return ((x21) this.f58777a0).mo214168a2((Cursor) obj);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0029  */
    @Override // android.widget.Filter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Filter.FilterResults performFiltering(CharSequence charSequence) {
        Cursor cursorM215103a6;
        x21 x21Var = (x21) this.f58777a0;
        SearchView searchView = x21Var.f60997b0;
        String string = charSequence == null ? "" : charSequence.toString();
        if (searchView.getVisibility() == 0 && searchView.getWindowVisibility() == 0) {
            try {
                cursorM215103a6 = x21Var.m215103a6(x21Var.f60998b1, string);
                if (cursorM215103a6 != null) {
                    cursorM215103a6.getCount();
                }
            } catch (RuntimeException unused) {
            }
        } else {
            cursorM215103a6 = null;
        }
        Filter.FilterResults filterResults = new Filter.FilterResults();
        if (cursorM215103a6 != null) {
            filterResults.count = cursorM215103a6.getCount();
            filterResults.values = cursorM215103a6;
        } else {
            filterResults.count = 0;
            filterResults.values = null;
        }
        return filterResults;
    }

    @Override // android.widget.Filter
    public final void publishResults(CharSequence charSequence, Filter.FilterResults filterResults) {
        AbstractC0945oa abstractC0945oa = this.f58777a0;
        Cursor cursor = abstractC0945oa.f58766a2;
        Object obj = filterResults.values;
        if (obj == null || obj == cursor) {
            return;
        }
        ((x21) abstractC0945oa).mo214167a1((Cursor) obj);
    }
}
