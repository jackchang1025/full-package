package p000;

import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputContentInfo;
import android.widget.AutoCompleteTextView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.work.impl.WorkDatabase;
import com.google.android.material.bottomsheet.BottomSheetDragHandleView;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.search.SearchBar;
import java.util.WeakHashMap;
import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.internal.Util;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ez */
/* loaded from: classes2.dex */
public final /* synthetic */ class C0474ez implements InterfaceC0812l9, InterfaceC0702j1, EventListener.Factory, j31 {

    /* renamed from: a0 */
    public final /* synthetic */ int f56125a0;

    /* renamed from: a1 */
    public final /* synthetic */ Object f56126a1;

    public /* synthetic */ C0474ez(int i, Object obj) {
        this.f56125a0 = i;
        this.f56126a1 = obj;
    }

    /* renamed from: a0 */
    public boolean m212727a0(tg0 tg0Var, int i, Bundle bundle) {
        InterfaceC0859mf tg0Var2;
        AppCompatEditText appCompatEditText = (AppCompatEditText) this.f56126a1;
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 25 && (i & 1) != 0) {
            try {
                ((w50) tg0Var.f60218a1).mo214261a5();
                InputContentInfo inputContentInfoM214968a0 = AbstractC1353vw.m214968a0(((w50) tg0Var.f60218a1).mo214259a3());
                bundle = bundle == null ? new Bundle() : new Bundle(bundle);
                bundle.putParcelable("androidx.core.view.extra.INPUT_CONTENT_INFO", inputContentInfoM214968a0);
            } catch (Exception unused) {
                return false;
            }
        }
        w50 w50Var = (w50) tg0Var.f60218a1;
        ClipData clipData = new ClipData(w50Var.mo214257a1(), new ClipData.Item(w50Var.mo214260a4()));
        if (i2 >= 31) {
            tg0Var2 = new tg0(clipData, 2);
        } else {
            C0860mg c0860mg = new C0860mg();
            c0860mg.f58350a1 = clipData;
            c0860mg.f58351a2 = 2;
            tg0Var2 = c0860mg;
        }
        tg0Var2.mo213989b2(w50Var.mo214262a6());
        tg0Var2.setExtras(bundle);
        return xa1.m215147a9(appCompatEditText, tg0Var2.build()) == null;
    }

    @Override // p000.j31
    /* renamed from: a1 */
    public k31 mo212728a1(i31 i31Var) {
        return WorkDatabase.C0095a0.create$lambda$0((Context) this.f56126a1, i31Var);
    }

    @Override // p000.InterfaceC0812l9
    /* renamed from: a2 */
    public boolean mo210913a2(View view) {
        BottomSheetDragHandleView bottomSheetDragHandleView = (BottomSheetDragHandleView) this.f56126a1;
        int i = BottomSheetDragHandleView.f49241b2;
        return bottomSheetDragHandleView.m210955a2();
    }

    @Override // okhttp3.EventListener.Factory
    public EventListener create(Call call) {
        return Util.asFactory$lambda$8((EventListener) this.f56126a1, call);
    }

    @Override // p000.InterfaceC0702j1
    public void onTouchExplorationStateChanged(boolean z) {
        int i = this.f56125a0;
        Object obj = this.f56126a1;
        switch (i) {
            case 1:
                C1309uq c1309uq = (C1309uq) obj;
                AutoCompleteTextView autoCompleteTextView = c1309uq.f60493a7;
                if (autoCompleteTextView != null && autoCompleteTextView.getInputType() == 0) {
                    CheckableImageButton checkableImageButton = c1309uq.f61106a3;
                    int i2 = z ? 2 : 1;
                    WeakHashMap weakHashMap = xa1.f61054a0;
                    fa1.m212781b8(checkableImageButton, i2);
                    break;
                }
                break;
            default:
                int i3 = SearchBar.f49710f8;
                ((SearchBar) obj).setFocusableInTouchMode(z);
                break;
        }
    }
}
