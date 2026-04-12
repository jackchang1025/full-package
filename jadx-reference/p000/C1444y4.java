package p000;

import android.view.View;
import android.widget.AdapterView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.appcompat.widget.SearchView;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: y4 */
/* loaded from: classes.dex */
public final class C1444y4 implements AdapterView.OnItemClickListener {

    /* renamed from: a0 */
    public final /* synthetic */ int f61232a0;

    /* renamed from: a1 */
    public final /* synthetic */ Object f61233a1;

    public /* synthetic */ C1444y4(int i, Object obj) {
        this.f61232a0 = i;
        this.f61233a1 = obj;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
        switch (this.f61232a0) {
            case 0:
                C1446y6 c1446y6 = (C1446y6) this.f61233a1;
                AppCompatSpinner appCompatSpinner = c1446y6.f61244d2;
                appCompatSpinner.setSelection(i);
                if (appCompatSpinner.getOnItemClickListener() != null) {
                    appCompatSpinner.performItemClick(view, i, c1446y6.f61241c9.getItemId(i));
                }
                c1446y6.dismiss();
                break;
            case 1:
                MaterialAutoCompleteTextView materialAutoCompleteTextView = (MaterialAutoCompleteTextView) this.f61233a1;
                ListPopupWindow listPopupWindow = materialAutoCompleteTextView.f49922a4;
                MaterialAutoCompleteTextView.m211136a0(materialAutoCompleteTextView, i < 0 ? !listPopupWindow.f43997c5.isShowing() ? null : listPopupWindow.f43974a2.getSelectedItem() : materialAutoCompleteTextView.getAdapter().getItem(i));
                AdapterView.OnItemClickListener onItemClickListener = materialAutoCompleteTextView.getOnItemClickListener();
                if (onItemClickListener != null) {
                    if (view == null || i < 0) {
                        view = !listPopupWindow.f43997c5.isShowing() ? null : listPopupWindow.f43974a2.getSelectedView();
                        i = !listPopupWindow.f43997c5.isShowing() ? -1 : listPopupWindow.f43974a2.getSelectedItemPosition();
                        j = !listPopupWindow.f43997c5.isShowing() ? Long.MIN_VALUE : listPopupWindow.f43974a2.getSelectedItemId();
                    }
                    onItemClickListener.onItemClick(listPopupWindow.f43974a2, view, i, j);
                }
                listPopupWindow.dismiss();
                break;
            default:
                ((SearchView) this.f61233a1).m209901b3(i);
                break;
        }
    }
}
