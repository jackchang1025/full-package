package p000;

import android.os.Handler;
import android.os.Message;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class c11 implements Handler.Callback {

    /* renamed from: a0 */
    public final /* synthetic */ C0747k6 f46059a0;

    public c11(C0747k6 c0747k6) {
        this.f46059a0 = c0747k6;
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        if (message.what != 0) {
            return false;
        }
        C0747k6 c0747k6 = this.f46059a0;
        if (message.obj != null) {
            throw new ClassCastException();
        }
        synchronized (c0747k6.f57459a0) {
            throw null;
        }
    }
}
