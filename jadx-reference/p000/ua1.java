package p000;

import android.view.ContentInfo;
import android.view.OnReceiveContentListener;
import android.view.View;
import java.util.Objects;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class ua1 implements OnReceiveContentListener {

    /* renamed from: a0 */
    public final cl0 f60370a0;

    public ua1(cl0 cl0Var) {
        this.f60370a0 = cl0Var;
    }

    public final ContentInfo onReceiveContent(View view, ContentInfo contentInfo) {
        C0862mi c0862mi = new C0862mi(new tg0(contentInfo));
        C0862mi c0862miM213004a0 = ((h61) this.f60370a0).m213004a0(view, c0862mi);
        if (c0862miM213004a0 == null) {
            return null;
        }
        if (c0862miM213004a0 == c0862mi) {
            return contentInfo;
        }
        ContentInfo contentInfoMo213997a8 = c0862miM213004a0.f58373a0.mo213997a8();
        Objects.requireNonNull(contentInfoMo213997a8);
        return AbstractC0858me.m213979a6(contentInfoMo213997a8);
    }
}
