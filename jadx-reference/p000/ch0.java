package p000;

import android.os.IInterface;
import android.os.RemoteCallbackList;
import androidx.room.MultiInstanceInvalidationService;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class ch0 extends RemoteCallbackList {

    /* renamed from: a0 */
    public final /* synthetic */ MultiInstanceInvalidationService f46136a0;

    public ch0(MultiInstanceInvalidationService multiInstanceInvalidationService) {
        this.f46136a0 = multiInstanceInvalidationService;
    }

    @Override // android.os.RemoteCallbackList
    public final void onCallbackDied(IInterface iInterface, Object obj) {
        t60.m214695b6((p40) iInterface, "callback");
        t60.m214695b6(obj, "cookie");
        this.f46136a0.f45362a1.remove((Integer) obj);
    }
}
