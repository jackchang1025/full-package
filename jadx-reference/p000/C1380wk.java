package p000;

import android.os.Bundle;
import android.text.Editable;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.EditText;
import java.nio.ByteBuffer;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: wk */
/* loaded from: classes.dex */
public final class C1380wk extends InputConnectionWrapper {

    /* renamed from: a0 */
    public final EditText f60939a0;

    /* renamed from: a1 */
    public final C1351vv f60940a1;

    public C1380wk(EditText editText, InputConnection inputConnection, EditorInfo editorInfo) {
        C1351vv c1351vv = new C1351vv(21);
        super(inputConnection, false);
        this.f60939a0 = editText;
        this.f60940a1 = c1351vv;
        if (C1375wg.f60900a9 != null) {
            C1375wg c1375wgM215058a0 = C1375wg.m215058a0();
            if (c1375wgM215058a0.m215059a1() != 1 || editorInfo == null) {
                return;
            }
            if (editorInfo.extras == null) {
                editorInfo.extras = new Bundle();
            }
            C1370wb c1370wb = c1375wgM215058a0.f60905a4;
            c1370wb.getClass();
            Bundle bundle = editorInfo.extras;
            zf0 zf0Var = (zf0) c1370wb.f60881a2.f61012a0;
            int iM215362a0 = zf0Var.m215362a0(4);
            bundle.putInt("android.support.text.emoji.emojiCompat_metadataVersion", iM215362a0 != 0 ? ((ByteBuffer) zf0Var.f61458a3).getInt(iM215362a0 + zf0Var.f61455a0) : 0);
            editorInfo.extras.putBoolean("android.support.text.emoji.emojiCompat_replaceAll", false);
        }
    }

    @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
    public final boolean deleteSurroundingText(int i, int i2) {
        Editable editableText = this.f60939a0.getEditableText();
        this.f60940a1.getClass();
        return C1351vv.m214965a9(this, editableText, i, i2, false) || super.deleteSurroundingText(i, i2);
    }

    @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
    public final boolean deleteSurroundingTextInCodePoints(int i, int i2) {
        Editable editableText = this.f60939a0.getEditableText();
        this.f60940a1.getClass();
        return C1351vv.m214965a9(this, editableText, i, i2, true) || super.deleteSurroundingTextInCodePoints(i, i2);
    }
}
