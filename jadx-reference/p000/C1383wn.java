package p000;

import android.text.Editable;
import android.text.method.KeyListener;
import android.text.method.MetaKeyKeyListener;
import android.view.KeyEvent;
import android.view.View;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: wn */
/* loaded from: classes.dex */
public final class C1383wn implements KeyListener {

    /* renamed from: a0 */
    public final KeyListener f60948a0;

    /* renamed from: a1 */
    public final C1351vv f60949a1;

    public C1383wn(KeyListener keyListener) {
        C1351vv c1351vv = new C1351vv(22);
        this.f60948a0 = keyListener;
        this.f60949a1 = c1351vv;
    }

    @Override // android.text.method.KeyListener
    public final void clearMetaKeyState(View view, Editable editable, int i) {
        this.f60948a0.clearMetaKeyState(view, editable, i);
    }

    @Override // android.text.method.KeyListener
    public final int getInputType() {
        return this.f60948a0.getInputType();
    }

    @Override // android.text.method.KeyListener
    public final boolean onKeyDown(View view, Editable editable, int i, KeyEvent keyEvent) {
        boolean z;
        this.f60949a1.getClass();
        if (i != 67 ? i != 112 ? false : og1.m214199a2(editable, keyEvent, true) : og1.m214199a2(editable, keyEvent, false)) {
            MetaKeyKeyListener.adjustMetaAfterKeypress(editable);
            z = true;
        } else {
            z = false;
        }
        return z || this.f60948a0.onKeyDown(view, editable, i, keyEvent);
    }

    @Override // android.text.method.KeyListener
    public final boolean onKeyOther(View view, Editable editable, KeyEvent keyEvent) {
        return this.f60948a0.onKeyOther(view, editable, keyEvent);
    }

    @Override // android.text.method.KeyListener
    public final boolean onKeyUp(View view, Editable editable, int i, KeyEvent keyEvent) {
        return this.f60948a0.onKeyUp(view, editable, i, keyEvent);
    }
}
