package f0;

import a1.AbstractC0026q;
import android.os.SystemClock;
import android.util.Log;
import g0.InterfaceC0311c;
import java.io.IOException;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import l0.C0375e;
import n0.C0405a;
import p000a.AbstractC0000a;
import p012o.RunnableC0412a;

/* renamed from: f0.j */
/* loaded from: classes.dex */
public final class C0289j {

    /* renamed from: f */
    public static final C0289j f523f = new C0289j();

    /* renamed from: g */
    public static final ThreadPoolExecutor f524g;

    /* renamed from: h */
    public static final ThreadLocal f525h;

    /* renamed from: a */
    public C0305z f526a;

    /* renamed from: e */
    public C0284e f530e;

    /* renamed from: c */
    public int f528c = 0;

    /* renamed from: d */
    public PriorityQueue f529d = new PriorityQueue(1, C0288i.f522a);

    /* renamed from: b */
    public final String f527b = "AsyncServer";

    static {
        ThreadFactoryC0286g threadFactoryC0286g = new ThreadFactoryC0286g("AsyncServer-worker-");
        TimeUnit timeUnit = TimeUnit.SECONDS;
        f524g = new ThreadPoolExecutor(0, 4, 10L, timeUnit, new LinkedBlockingQueue(), threadFactoryC0286g);
        new ThreadPoolExecutor(0, 4, 10L, timeUnit, new LinkedBlockingQueue(), new ThreadFactoryC0286g("AsyncServer-resolver-"));
        f525h = new ThreadLocal();
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x003a, code lost:
    
        r7 = r6.f563a.keys().iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0048, code lost:
    
        if (r7.hasNext() == false) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x004a, code lost:
    
        r2 = r7.next();
        a1.AbstractC0026q.m177h(r2.channel());
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x005b, code lost:
    
        r2.cancel();
     */
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m793a(C0289j c0289j, C0305z c0305z, PriorityQueue priorityQueue) {
        while (true) {
            try {
                m795f(c0289j, c0305z, priorityQueue);
            } catch (C0285f e2) {
                if (!(e2.getCause() instanceof ClosedSelectorException)) {
                    Log.i("NIO", "Selector exception, shutting down", e2);
                }
                AbstractC0026q.m177h(c0305z);
            }
            synchronized (c0289j) {
                if (!c0305z.f563a.isOpen() || (c0305z.f563a.keys().size() <= 0 && priorityQueue.size() <= 0)) {
                    try {
                        break;
                    } catch (Exception unused) {
                    }
                }
            }
        }
        AbstractC0026q.m177h(c0305z);
        if (c0289j.f526a == c0305z) {
            c0289j.f529d = new PriorityQueue(1, C0288i.f522a);
            c0289j.f526a = null;
            c0289j.f530e = null;
        }
    }

    /* renamed from: b */
    public static long m794b(C0289j c0289j, PriorityQueue priorityQueue) {
        RunnableC0287h runnableC0287h;
        long j2 = Long.MAX_VALUE;
        while (true) {
            synchronized (c0289j) {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                runnableC0287h = null;
                if (priorityQueue.size() > 0) {
                    RunnableC0287h runnableC0287h2 = (RunnableC0287h) priorityQueue.remove();
                    long j3 = runnableC0287h2.f520c;
                    if (j3 <= elapsedRealtime) {
                        runnableC0287h = runnableC0287h2;
                    } else {
                        priorityQueue.add(runnableC0287h2);
                        j2 = j3 - elapsedRealtime;
                    }
                }
            }
            if (runnableC0287h == null) {
                c0289j.f528c = 0;
                return j2;
            }
            runnableC0287h.run();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: f */
    public static void m795f(C0289j c0289j, C0305z c0305z, PriorityQueue priorityQueue) {
        Object[] objArr;
        SelectionKey selectionKey;
        long m794b = m794b(c0289j, priorityQueue);
        try {
            synchronized (c0289j) {
                try {
                    if (c0305z.f563a.selectNow() != 0) {
                        objArr = false;
                    } else if (c0305z.f563a.keys().size() == 0 && m794b == Long.MAX_VALUE) {
                        return;
                    } else {
                        objArr = true;
                    }
                    if (objArr != false) {
                        if (m794b == Long.MAX_VALUE) {
                            Semaphore semaphore = c0305z.f565c;
                            try {
                                semaphore.drainPermits();
                                c0305z.f563a.select(0L);
                                semaphore.release(Integer.MAX_VALUE);
                            } finally {
                            }
                        } else {
                            Semaphore semaphore2 = c0305z.f565c;
                            try {
                                semaphore2.drainPermits();
                                c0305z.f563a.select(m794b);
                                semaphore2.release(Integer.MAX_VALUE);
                            } finally {
                            }
                        }
                    }
                    Set<SelectionKey> selectedKeys = c0305z.f563a.selectedKeys();
                    for (SelectionKey selectionKey2 : selectedKeys) {
                        try {
                            SocketChannel socketChannel = null;
                            SelectionKey selectionKey3 = null;
                            if (selectionKey2.isAcceptable()) {
                                try {
                                    SocketChannel accept = ((ServerSocketChannel) selectionKey2.channel()).accept();
                                    if (accept != null) {
                                        try {
                                            accept.configureBlocking(false);
                                            selectionKey3 = accept.register(c0305z.f563a, 1);
                                            C0375e c0375e = (C0375e) selectionKey2.attachment();
                                            C0281b c0281b = new C0281b();
                                            c0281b.f493h = new C0405a();
                                            c0281b.f489d = new a0(accept, 1);
                                            c0281b.f491f = c0289j;
                                            c0281b.f490e = selectionKey3;
                                            selectionKey3.attach(c0281b);
                                            c0375e.m950b(c0281b);
                                        } catch (IOException unused) {
                                            selectionKey = selectionKey3;
                                            socketChannel = accept;
                                            AbstractC0026q.m177h(socketChannel);
                                            if (selectionKey != null) {
                                                selectionKey.cancel();
                                            }
                                        }
                                    }
                                } catch (IOException unused2) {
                                    selectionKey = null;
                                }
                            } else if (selectionKey2.isReadable()) {
                                ((C0281b) selectionKey2.attachment()).m776a();
                            } else {
                                if (!selectionKey2.isWritable()) {
                                    if (!selectionKey2.isConnectable()) {
                                        Log.i("NIO", "wtf");
                                        throw new RuntimeException("Unknown key state.");
                                    }
                                    AbstractC0000a.m27w(selectionKey2.attachment());
                                    SocketChannel socketChannel2 = (SocketChannel) selectionKey2.channel();
                                    selectionKey2.interestOps(1);
                                    try {
                                        socketChannel2.finishConnect();
                                        C0281b c0281b2 = new C0281b();
                                        c0281b2.f491f = c0289j;
                                        c0281b2.f490e = selectionKey2;
                                        c0281b2.f493h = new C0405a();
                                        c0281b2.f489d = new a0(socketChannel2, 1);
                                        selectionKey2.attach(c0281b2);
                                        throw null;
                                    } catch (IOException unused3) {
                                        selectionKey2.cancel();
                                        AbstractC0026q.m177h(socketChannel2);
                                        throw null;
                                    }
                                }
                                C0281b c0281b3 = (C0281b) selectionKey2.attachment();
                                c0281b3.f489d.getClass();
                                SelectionKey selectionKey4 = c0281b3.f490e;
                                selectionKey4.interestOps(selectionKey4.interestOps() & (-5));
                                InterfaceC0311c interfaceC0311c = c0281b3.f495j;
                                if (interfaceC0311c != null) {
                                    interfaceC0311c.mo800c();
                                }
                            }
                        } catch (CancelledKeyException unused4) {
                        }
                    }
                    selectedKeys.clear();
                } finally {
                }
            }
        } catch (Exception e2) {
            throw new C0285f(e2);
        }
    }

    /* renamed from: c */
    public final void m796c(Runnable runnable) {
        synchronized (this) {
            int i2 = this.f528c;
            this.f528c = i2 + 1;
            this.f529d.add(new RunnableC0287h(this, runnable, i2));
            if (this.f526a == null) {
                m797d();
            }
            if (!(this.f530e == Thread.currentThread())) {
                f524g.execute(new RunnableC0412a(this.f526a, 5));
            }
        }
    }

    /* renamed from: d */
    public final void m797d() {
        synchronized (this) {
            try {
                C0305z c0305z = this.f526a;
                if (c0305z != null) {
                    PriorityQueue priorityQueue = this.f529d;
                    try {
                        m795f(this, c0305z, priorityQueue);
                        return;
                    } catch (C0285f e2) {
                        Log.i("NIO", "Selector closed", e2);
                        try {
                            c0305z.f563a.close();
                            return;
                        } catch (Exception unused) {
                            return;
                        }
                    }
                }
                try {
                    C0305z c0305z2 = new C0305z(SelectorProvider.provider().openSelector());
                    this.f526a = c0305z2;
                    C0284e c0284e = new C0284e(this, this.f527b, c0305z2, this.f529d);
                    this.f530e = c0284e;
                    c0284e.start();
                } catch (IOException e3) {
                    throw new RuntimeException("unable to create selector?", e3);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: e */
    public final void m798e(Runnable runnable) {
        Semaphore semaphore;
        if (Thread.currentThread() == this.f530e) {
            m796c(runnable);
            m794b(this, this.f529d);
            return;
        }
        synchronized (this) {
            semaphore = new Semaphore(0);
            m796c(new p012o.b0(runnable, semaphore, 2));
        }
        try {
            semaphore.acquire();
        } catch (InterruptedException e2) {
            Log.e("NIO", "run", e2);
        }
    }
}
