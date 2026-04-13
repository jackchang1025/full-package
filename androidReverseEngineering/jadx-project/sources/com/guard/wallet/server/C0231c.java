package com.guard.wallet.server;

import a1.AbstractC0026q;
import android.util.Log;
import com.guard.wallet.utils.AbstractC0251g;
import e1.InterfaceC0273b;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import n1.AbstractRunnableC0411b;
import n1.C0410a;
import com.guard.wallet.entity.BuildConfig;
import p010m.C0397d;

/* renamed from: com.guard.wallet.server.c */
/* loaded from: classes.dex */
public final class C0231c extends AbstractRunnableC0411b {

    /* renamed from: C */
    public static final Integer f294C = 7900;

    /* renamed from: D */
    public static final Integer f295D = 7980;

    /* renamed from: E */
    public static C0231c f296E;

    /* renamed from: A */
    public final ConcurrentLinkedQueue f297A;

    /* renamed from: B */
    public final ConcurrentLinkedQueue f298B;

    /* renamed from: x */
    public final AtomicBoolean f299x;

    /* renamed from: y */
    public final ConcurrentLinkedQueue f300y;

    /* renamed from: z */
    public final ConcurrentLinkedQueue f301z;

    public C0231c(Integer num) {
        super(new InetSocketAddress(num.intValue()));
        this.f299x = new AtomicBoolean(false);
        this.f300y = new ConcurrentLinkedQueue();
        this.f301z = new ConcurrentLinkedQueue();
        this.f297A = new ConcurrentLinkedQueue();
        this.f298B = new ConcurrentLinkedQueue();
    }

    /* renamed from: G */
    public static C0231c m511G() {
        if (f296E == null) {
            Integer num = f294C;
            if (AbstractC0026q.m154E(num.intValue())) {
                f296E = new C0231c(num);
            } else {
                f296E = new C0231c(f295D);
            }
        }
        return f296E;
    }

    /* renamed from: H */
    public static void m512H() {
        C0231c m511G = m511G();
        m511G.f459g = true;
        Iterator it = m511G.f830p.iterator();
        while (it.hasNext()) {
            C0410a c0410a = (C0410a) it.next();
            if (c0410a.isAlive()) {
                throw new IllegalStateException("Cannot call setDaemon after server is already started!");
            }
            c0410a.setDaemon(true);
        }
        C0231c m511G2 = m511G();
        if (m511G2.f828n != null) {
            throw new IllegalStateException(C0231c.class.getName().concat(" can only be started once."));
        }
        Thread thread = new Thread(m511G2);
        thread.setDaemon(m511G2.f459g);
        thread.start();
        Log.d("MyWebSocketServer", "webSocketServer start");
    }

    @Override // n1.AbstractRunnableC0411b
    /* renamed from: B */
    public final void mo513B(InterfaceC0273b interfaceC0273b) {
        Log.d("MyWebSocketServer", "MyWebSocketServer onClose getHostAddress:" + interfaceC0273b.mo752g().getAddress().getHostAddress());
        ConcurrentLinkedQueue concurrentLinkedQueue = this.f300y;
        if (!concurrentLinkedQueue.isEmpty()) {
            concurrentLinkedQueue.remove(interfaceC0273b);
        }
        ConcurrentLinkedQueue concurrentLinkedQueue2 = this.f301z;
        if (!concurrentLinkedQueue2.isEmpty()) {
            concurrentLinkedQueue2.remove(interfaceC0273b);
        }
        ConcurrentLinkedQueue concurrentLinkedQueue3 = this.f297A;
        if (!concurrentLinkedQueue3.isEmpty()) {
            concurrentLinkedQueue3.remove(interfaceC0273b);
        }
        ConcurrentLinkedQueue concurrentLinkedQueue4 = this.f298B;
        if (!concurrentLinkedQueue4.isEmpty()) {
            concurrentLinkedQueue4.remove(interfaceC0273b);
        }
        if (concurrentLinkedQueue4.isEmpty()) {
            C0397d.m963c().m965d(1);
        }
        if (concurrentLinkedQueue3.isEmpty()) {
            C0397d.m963c().m965d(0);
        }
    }

    @Override // n1.AbstractRunnableC0411b
    /* renamed from: C */
    public final void mo514C(Exception exc) {
        Log.e("MyWebSocketServer", "MyWebSocketServer 启动失败:" + exc);
        try {
            try {
                C0231c c0231c = f296E;
                if (c0231c != null) {
                    c0231c.m975F(BuildConfig.FLAVOR);
                    f296E.m976t();
                    f296E = null;
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("MyWebSocketServer", e2);
            }
            AbstractC0251g.T0(5);
            m512H();
        } catch (Exception e3) {
            AbstractC0026q.m186s("MyWebSocketServer", e3);
        }
    }

    /* renamed from: I */
    public final void m515I(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return;
            }
            ConcurrentLinkedQueue concurrentLinkedQueue = this.f301z;
            if (concurrentLinkedQueue.isEmpty()) {
                return;
            }
            Iterator it = concurrentLinkedQueue.iterator();
            while (it.hasNext()) {
                ((InterfaceC0273b) it.next()).mo746a(str.getBytes(StandardCharsets.UTF_8));
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("MyWebSocketServer", e2);
        }
    }

    @Override // n1.AbstractRunnableC0411b
    public final void finalize() {
        m975F(BuildConfig.FLAVOR);
        m976t();
        super.finalize();
        f296E = null;
    }
}
