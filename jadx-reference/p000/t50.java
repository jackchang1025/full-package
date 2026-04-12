package p000;

import android.os.Build;
import android.os.Bundle;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.view.inputmethod.InputContentInfo;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class t50 extends InputConnectionWrapper {

    /* renamed from: a0 */
    public final /* synthetic */ C0474ez f60141a0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public t50(InputConnection inputConnection, C0474ez c0474ez) {
        super(inputConnection, false);
        this.f60141a0 = c0474ez;
    }

    @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
    public final boolean commitContent(InputContentInfo inputContentInfo, int i, Bundle bundle) {
        tg0 tg0Var = null;
        if (inputContentInfo != null && Build.VERSION.SDK_INT >= 25) {
            tg0Var = new tg0(23, new v50(inputContentInfo));
        }
        if (this.f60141a0.m212727a0(tg0Var, i, bundle)) {
            return true;
        }
        return super.commitContent(inputContentInfo, i, bundle);
    }
}
