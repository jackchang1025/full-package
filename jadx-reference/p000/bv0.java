package p000;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;
import com.google.android.material.textfield.TextInputLayout;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class bv0 implements TextWatcher {

    /* renamed from: a0 */
    public final /* synthetic */ int f46007a0;

    /* renamed from: a1 */
    public final /* synthetic */ ViewGroup f46008a1;

    public /* synthetic */ bv0(ViewGroup viewGroup, int i) {
        this.f46007a0 = i;
        this.f46008a1 = viewGroup;
    }

    @Override // android.text.TextWatcher
    public final void afterTextChanged(Editable editable) {
        switch (this.f46007a0) {
            case 0:
            case 1:
                break;
            default:
                TextInputLayout textInputLayout = (TextInputLayout) this.f46008a1;
                textInputLayout.m211157b9(!textInputLayout.f50005h2, false);
                if (textInputLayout.f49943b0) {
                    textInputLayout.m211151b3(editable);
                }
                if (textInputLayout.f49951b8) {
                    textInputLayout.m211158c0(editable);
                    break;
                }
                break;
        }
    }

    @Override // android.text.TextWatcher
    public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        int i4 = this.f46007a0;
    }

    @Override // android.text.TextWatcher
    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        switch (this.f46007a0) {
            case 0:
                SearchView searchView = (SearchView) this.f46008a1;
                Editable text = searchView.f43999b5.getText();
                searchView.f44031e7 = text;
                boolean zIsEmpty = TextUtils.isEmpty(text);
                searchView.m209909c1(!zIsEmpty);
                int i4 = 8;
                if (searchView.f44030e6 && !searchView.f44023d9 && zIsEmpty) {
                    searchView.f44004c0.setVisibility(8);
                    i4 = 0;
                }
                searchView.f44006c2.setVisibility(i4);
                searchView.m209905b7();
                searchView.m209908c0();
                charSequence.toString();
                break;
            case 1:
                ((com.google.android.material.search.SearchView) this.f46008a1).f49739b0.setVisibility(charSequence.length() > 0 ? 0 : 8);
                break;
        }
    }

    /* renamed from: a0 */
    private final void m210744a0(Editable editable) {
    }

    /* renamed from: a1 */
    private final void m210745a1(Editable editable) {
    }

    /* renamed from: a2 */
    private final void m210746a2(int i, int i2, int i3, CharSequence charSequence) {
    }

    /* renamed from: a3 */
    private final void m210747a3(int i, int i2, int i3, CharSequence charSequence) {
    }

    /* renamed from: a4 */
    private final void m210748a4(int i, int i2, int i3, CharSequence charSequence) {
    }

    /* renamed from: a5 */
    private final void m210749a5(int i, int i2, int i3, CharSequence charSequence) {
    }
}
