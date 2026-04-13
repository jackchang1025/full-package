package e1;

import android.support.v4.view.PointerIconCompat;
import android.util.Log;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import k1.C0368e;
import o1.ThreadFactoryC0448b;
import p012o.RunnableC0415d;

/* renamed from: e1.a */
/* loaded from: classes.dex */
public abstract class AbstractC0272a extends AbstractC0274c {

    /* renamed from: b */
    public boolean f454b;

    /* renamed from: c */
    public boolean f455c;

    /* renamed from: d */
    public ScheduledExecutorService f456d;

    /* renamed from: e */
    public ScheduledFuture f457e;

    /* renamed from: f */
    public final long f458f = TimeUnit.SECONDS.toNanos(60);

    /* renamed from: g */
    public boolean f459g = false;

    /* renamed from: h */
    public final Object f460h = new Object();

    /* renamed from: q */
    public static void m743q(AbstractC0272a abstractC0272a, InterfaceC0273b interfaceC0273b, long j2) {
        abstractC0272a.getClass();
        if (interfaceC0273b instanceof C0275d) {
            C0275d c0275d = (C0275d) interfaceC0273b;
            if (c0275d.f479r < j2) {
                Log.d("e1.a", "Closing connection due to no pong received");
                c0275d.m766k("The connection was closed because the other endpoint did not respond with a pong in time. For more information check: https://github.com/TooTallNate/Java-WebSocket/wiki/Lost-connection-detection", false, PointerIconCompat.TYPE_CELL);
                return;
            }
            if (!(c0275d.f469h == 2)) {
                Log.d("e1.a", "Trying to ping a non open connection");
                return;
            }
            AbstractC0274c abstractC0274c = c0275d.f464c;
            if (abstractC0274c.f461a == null) {
                abstractC0274c.f461a = new C0368e();
            }
            C0368e c0368e = abstractC0274c.f461a;
            if (c0368e == null) {
                throw new NullPointerException("onPreparePing(WebSocket) returned null. PingFrame to sent can't be null.");
            }
            c0275d.m773s(Collections.singletonList(c0368e));
        }
    }

    /* renamed from: r */
    public abstract Collection mo744r();

    /* renamed from: s */
    public final void m745s() {
        synchronized (this.f460h) {
            if (this.f458f <= 0) {
                Log.d("e1.a", "Connection lost timer deactivated");
                return;
            }
            Log.d("e1.a", "Connection lost timer started");
            ScheduledExecutorService scheduledExecutorService = this.f456d;
            if (scheduledExecutorService != null) {
                scheduledExecutorService.shutdownNow();
                this.f456d = null;
            }
            ScheduledFuture scheduledFuture = this.f457e;
            if (scheduledFuture != null) {
                scheduledFuture.cancel(false);
                this.f457e = null;
            }
            this.f456d = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryC0448b(this.f459g));
            RunnableC0415d runnableC0415d = new RunnableC0415d(this);
            ScheduledExecutorService scheduledExecutorService2 = this.f456d;
            long j2 = this.f458f;
            this.f457e = scheduledExecutorService2.scheduleAtFixedRate(runnableC0415d, j2, j2, TimeUnit.NANOSECONDS);
        }
    }
}
