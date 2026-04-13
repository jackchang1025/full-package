package s0;

import a1.AbstractC0021l;
import a1.C0013d;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.RejectedExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import v0.C0939j;
import v0.C0948s;
import v0.C0954y;
import v0.EnumC0931b;

/* renamed from: s0.j */
/* loaded from: classes.dex */
public final class C0907j extends C0013d {

    /* renamed from: k */
    public final /* synthetic */ int f2050k;

    /* renamed from: l */
    public final /* synthetic */ Object f2051l;

    public /* synthetic */ C0907j(Object obj, int i2) {
        this.f2050k = i2;
        this.f2051l = obj;
    }

    @Override // a1.C0013d
    /* renamed from: m */
    public final InterruptedIOException mo75m(IOException iOException) {
        switch (this.f2050k) {
            case 1:
                SocketTimeoutException socketTimeoutException = new SocketTimeoutException("timeout");
                if (iOException != null) {
                    socketTimeoutException.initCause(iOException);
                }
                return socketTimeoutException;
            case 2:
                SocketTimeoutException socketTimeoutException2 = new SocketTimeoutException("timeout");
                if (iOException != null) {
                    socketTimeoutException2.initCause(iOException);
                }
                return socketTimeoutException2;
            default:
                return super.mo75m(iOException);
        }
    }

    @Override // a1.C0013d
    /* renamed from: n */
    public final void mo76n() {
        StringBuilder sb;
        Throwable e2;
        Logger logger;
        Level level;
        int i2 = 0;
        switch (this.f2050k) {
            case 0:
                ((C0909l) this.f2051l).m1362a();
                return;
            case 1:
                ((C0954y) this.f2051l).m1430e(EnumC0931b.CANCEL);
                C0948s c0948s = ((C0954y) this.f2051l).f2255d;
                synchronized (c0948s) {
                    long j2 = c0948s.f2213n;
                    long j3 = c0948s.f2212m;
                    if (j2 < j3) {
                        return;
                    }
                    c0948s.f2212m = j3 + 1;
                    c0948s.f2214o = System.nanoTime() + 1000000000;
                    try {
                        c0948s.f2207h.execute(new C0939j(c0948s, "OkHttp %s ping", new Object[]{c0948s.f2203d}, i2));
                        return;
                    } catch (RejectedExecutionException unused) {
                        return;
                    }
                }
            default:
                Object obj = this.f2051l;
                try {
                    ((Socket) obj).close();
                    return;
                } catch (AssertionError e3) {
                    e2 = e3;
                    Logger logger2 = AbstractC0021l.f38a;
                    if (!((e2.getCause() == null || e2.getMessage() == null || !e2.getMessage().contains("getsockname failed")) ? false : true)) {
                        throw e2;
                    }
                    logger = AbstractC0021l.f38a;
                    level = Level.WARNING;
                    sb = new StringBuilder("Failed to close timed out socket ");
                    sb.append((Socket) obj);
                    logger.log(level, sb.toString(), e2);
                    return;
                } catch (Exception e4) {
                    Logger logger3 = AbstractC0021l.f38a;
                    Level level2 = Level.WARNING;
                    sb = new StringBuilder("Failed to close timed out socket ");
                    e2 = e4;
                    logger = logger3;
                    level = level2;
                    sb.append((Socket) obj);
                    logger.log(level, sb.toString(), e2);
                    return;
                }
        }
    }

    /* renamed from: o */
    public final void m1361o() {
        if (m74l()) {
            throw mo75m(null);
        }
    }
}
