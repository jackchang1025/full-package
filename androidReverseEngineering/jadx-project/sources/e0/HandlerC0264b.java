package e0;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

/* renamed from: e0.b */
/* loaded from: classes.dex */
public final class HandlerC0264b extends Handler {

    /* renamed from: a */
    public final /* synthetic */ C0265c f437a;

    public HandlerC0264b(C0265c c0265c) {
        this.f437a = c0265c;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        Object obj = message.obj;
        if (obj != null) {
            this.f437a.setImageBitmap((Bitmap) obj);
        }
    }
}
