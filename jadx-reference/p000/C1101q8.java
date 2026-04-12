package p000;

import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: q8 */
/* loaded from: classes.dex */
public final class C1101q8 implements AdapterView.OnItemClickListener {

    /* renamed from: a0 */
    public final /* synthetic */ C1165r2 f59425a0;

    /* renamed from: a1 */
    public final /* synthetic */ C1102q9 f59426a1;

    public C1101q8(C1102q9 c1102q9, C1165r2 c1165r2) {
        this.f59426a1 = c1102q9;
        this.f59425a0 = c1165r2;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
        C1102q9 c1102q9 = this.f59426a1;
        DialogInterface.OnClickListener onClickListener = c1102q9.f59436a7;
        C1165r2 c1165r2 = this.f59425a0;
        onClickListener.onClick(c1165r2.f59583a1, i);
        if (c1102q9.f59437a8) {
            return;
        }
        c1165r2.f59583a1.dismiss();
    }
}
