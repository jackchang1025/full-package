package p000;

import android.content.Context;
import android.view.View;
import android.view.Window;
import okio.internal.Buffer;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class d71 implements View.OnClickListener {

    /* renamed from: a0 */
    public final C0851m7 f55573a0;

    /* renamed from: a1 */
    public final /* synthetic */ f71 f55574a1;

    public d71(f71 f71Var) {
        this.f55574a1 = f71Var;
        Context context = f71Var.f56159a0.getContext();
        CharSequence charSequence = f71Var.f56166a7;
        C0851m7 c0851m7 = new C0851m7();
        c0851m7.f58270a4 = Buffer.SEGMENTING_THRESHOLD;
        c0851m7.f58272a6 = Buffer.SEGMENTING_THRESHOLD;
        c0851m7.f58277b1 = null;
        c0851m7.f58278b2 = null;
        c0851m7.f58279b3 = false;
        c0851m7.f58280b4 = false;
        c0851m7.f58281b5 = 16;
        c0851m7.f58274a8 = context;
        c0851m7.f58266a0 = charSequence;
        this.f55573a0 = c0851m7;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        f71 f71Var = this.f55574a1;
        Window.Callback callback = f71Var.f56169b0;
        if (callback == null || !f71Var.f56170b1) {
            return;
        }
        callback.onMenuItemSelected(0, this.f55573a0);
    }
}
