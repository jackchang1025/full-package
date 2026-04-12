package p000;

import android.content.ClipDescription;
import android.net.Uri;
import android.view.inputmethod.InputContentInfo;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class v50 implements w50 {

    /* renamed from: a0 */
    public final InputContentInfo f60584a0;

    public v50(Object obj) {
        this.f60584a0 = (InputContentInfo) obj;
    }

    @Override // p000.w50
    /* renamed from: a1 */
    public final ClipDescription mo214257a1() {
        return this.f60584a0.getDescription();
    }

    @Override // p000.w50
    /* renamed from: a3 */
    public final Object mo214259a3() {
        return this.f60584a0;
    }

    @Override // p000.w50
    /* renamed from: a4 */
    public final Uri mo214260a4() {
        return this.f60584a0.getContentUri();
    }

    @Override // p000.w50
    /* renamed from: a5 */
    public final void mo214261a5() {
        this.f60584a0.requestPermission();
    }

    @Override // p000.w50
    /* renamed from: a6 */
    public final Uri mo214262a6() {
        return this.f60584a0.getLinkUri();
    }

    public v50(Uri uri, ClipDescription clipDescription, Uri uri2) {
        this.f60584a0 = new InputContentInfo(uri, clipDescription, uri2);
    }
}
