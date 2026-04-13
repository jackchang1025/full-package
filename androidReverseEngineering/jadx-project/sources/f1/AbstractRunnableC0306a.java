package f1;

import a1.AbstractC0026q;
import android.support.v4.view.PointerIconCompat;
import android.util.Log;
import com.guard.wallet.bridge.C0177a;
import com.guard.wallet.http.C0203h;
import com.guard.wallet.msg.BridgeMessage;
import com.guard.wallet.utils.AbstractC0252h;
import e1.AbstractC0272a;
import e1.AbstractC0274c;
import e1.C0275d;
import e1.InterfaceC0273b;
import g1.AbstractC0312a;
import g1.C0313b;
import i1.C0340c;
import i1.C0342e;
import j1.C0355a;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import l1.C0390c;
import l1.InterfaceC0388a;
import l1.InterfaceC0389b;
import m1.C0403b;
import m1.InterfaceC0402a;
import com.guard.wallet.entity.BuildConfig;
import p000a.AbstractC0000a;
import p012o.RunnableC0415d;

/* renamed from: f1.a */
/* loaded from: classes.dex */
public abstract class AbstractRunnableC0306a extends AbstractC0272a implements Runnable, InterfaceC0273b {

    /* renamed from: t */
    public static final /* synthetic */ int f566t = 0;

    /* renamed from: i */
    public final URI f567i;

    /* renamed from: j */
    public final C0275d f568j;

    /* renamed from: k */
    public Socket f569k;

    /* renamed from: l */
    public OutputStream f570l;

    /* renamed from: m */
    public final Proxy f571m;

    /* renamed from: n */
    public Thread f572n;

    /* renamed from: o */
    public Thread f573o;

    /* renamed from: p */
    public final CountDownLatch f574p;

    /* renamed from: q */
    public final CountDownLatch f575q;

    /* renamed from: r */
    public final int f576r;

    /* renamed from: s */
    public final C0203h f577s;

    public AbstractRunnableC0306a(URI uri) {
        C0313b c0313b = new C0313b();
        this.f567i = null;
        this.f568j = null;
        this.f569k = null;
        this.f571m = Proxy.NO_PROXY;
        this.f574p = new CountDownLatch(1);
        this.f575q = new CountDownLatch(1);
        this.f576r = 0;
        this.f577s = null;
        if (uri == null) {
            throw new IllegalArgumentException();
        }
        this.f567i = uri;
        this.f577s = new C0203h(this, 10);
        this.f576r = 0;
        this.f454b = false;
        this.f455c = false;
        this.f568j = new C0275d(this, c0313b);
    }

    /* renamed from: A */
    public final void m821A() {
        this.f569k = ((SSLSocketFactory) SSLSocketFactory.getDefault()).createSocket(this.f569k, this.f567i.getHost(), m824v(), true);
    }

    @Override // e1.InterfaceC0273b
    /* renamed from: a */
    public final void mo746a(byte[] bArr) {
        this.f568j.mo746a(bArr);
    }

    @Override // e1.InterfaceC0273b
    /* renamed from: b */
    public final void mo747b(int i2, String str) {
        this.f568j.m764i(str, false, i2);
    }

    @Override // e1.InterfaceC0273b
    /* renamed from: c */
    public final void mo748c(String str) {
        this.f568j.mo748c(str);
    }

    @Override // e1.InterfaceC0273b
    /* renamed from: d */
    public final String mo749d() {
        return this.f567i.getPath();
    }

    @Override // e1.InterfaceC0273b
    /* renamed from: e */
    public final void mo750e(int i2) {
        this.f568j.mo750e(PointerIconCompat.TYPE_CONTEXT_MENU);
    }

    @Override // e1.InterfaceC0273b
    /* renamed from: f */
    public final void mo751f(String str) {
        this.f568j.m766k(str, false, PointerIconCompat.TYPE_CELL);
    }

    @Override // e1.InterfaceC0273b
    /* renamed from: g */
    public final InetSocketAddress mo752g() {
        return this.f568j.mo752g();
    }

    @Override // e1.AbstractC0274c
    /* renamed from: h */
    public final InetSocketAddress mo753h(InterfaceC0273b interfaceC0273b) {
        Socket socket = this.f569k;
        if (socket != null) {
            return (InetSocketAddress) socket.getRemoteSocketAddress();
        }
        return null;
    }

    @Override // e1.AbstractC0274c
    /* renamed from: i */
    public final void mo754i(InterfaceC0273b interfaceC0273b, int i2, String str, boolean z2) {
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
        Thread thread = this.f572n;
        if (thread != null) {
            thread.interrupt();
        }
        C0177a c0177a = (C0177a) this;
        c0177a.f194w.set(false);
        AbstractC0026q.m176g(c0177a.f192u);
        this.f574p.countDown();
        this.f575q.countDown();
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
        mo337w(exc);
    }

    @Override // e1.AbstractC0274c
    /* renamed from: m */
    public final void mo758m() {
    }

    @Override // e1.AbstractC0274c
    /* renamed from: n */
    public final void mo759n(InterfaceC0273b interfaceC0273b, String str) {
        mo338x(str);
    }

    @Override // e1.AbstractC0274c
    /* renamed from: o */
    public final void mo760o(InterfaceC0273b interfaceC0273b, InterfaceC0389b interfaceC0389b) {
        m745s();
        C0177a c0177a = (C0177a) this;
        c0177a.f194w.set(true);
        BridgeMessage bridgeMessage = c0177a.f193v;
        if (bridgeMessage != null) {
            c0177a.mo748c(AbstractC0252h.m693N(bridgeMessage));
        }
        this.f574p.countDown();
    }

    @Override // e1.AbstractC0274c
    /* renamed from: p */
    public final void mo761p(InterfaceC0273b interfaceC0273b) {
    }

    @Override // e1.AbstractC0272a
    /* renamed from: r */
    public final Collection mo744r() {
        return Collections.singletonList(this.f568j);
    }

    @Override // java.lang.Runnable
    public final void run() {
        Exception e2;
        int read;
        C0275d c0275d = this.f568j;
        try {
            boolean m825y = m825y();
            this.f569k.setTcpNoDelay(this.f454b);
            this.f569k.setReuseAddress(this.f455c);
            boolean isConnected = this.f569k.isConnected();
            URI uri = this.f567i;
            if (!isConnected) {
                this.f569k.connect(this.f577s == null ? InetSocketAddress.createUnresolved(uri.getHost(), m824v()) : new InetSocketAddress(InetAddress.getByName(uri.getHost()), m824v()), this.f576r);
            }
            if (m825y && "wss".equals(uri.getScheme())) {
                m821A();
            }
            Socket socket = this.f569k;
            if (socket instanceof SSLSocket) {
                SSLSocket sSLSocket = (SSLSocket) socket;
                SSLParameters sSLParameters = sSLSocket.getSSLParameters();
                sSLParameters.setEndpointIdentificationAlgorithm("HTTPS");
                sSLSocket.setSSLParameters(sSLParameters);
            }
            InputStream inputStream = this.f569k.getInputStream();
            this.f570l = this.f569k.getOutputStream();
            m826z();
            Thread thread = this.f572n;
            if (thread != null) {
                thread.interrupt();
                try {
                    this.f572n.join();
                } catch (InterruptedException unused) {
                }
            }
            Thread thread2 = new Thread(new RunnableC0415d(this, this, 13));
            this.f572n = thread2;
            thread2.setDaemon(this.f459g);
            this.f572n.start();
            byte[] bArr = new byte[65536];
            while (true) {
                try {
                    boolean z2 = true;
                    if (!(c0275d.f469h == 3)) {
                        if (c0275d.f469h != 4) {
                            z2 = false;
                        }
                        if (z2 || (read = inputStream.read(bArr)) == -1) {
                            break;
                        } else {
                            c0275d.m768m(ByteBuffer.wrap(bArr, 0, read));
                        }
                    } else {
                        break;
                    }
                } catch (IOException e3) {
                    if (e3 instanceof SSLException) {
                        mo337w(e3);
                    }
                    this.f568j.m770o();
                    return;
                } catch (RuntimeException e4) {
                    mo337w(e4);
                    c0275d.m766k(e4.getMessage(), false, PointerIconCompat.TYPE_CELL);
                    return;
                }
            }
            c0275d.m770o();
        } catch (Exception e5) {
            e2 = e5;
            mo337w(e2);
            c0275d.m766k(e2.getMessage(), false, -1);
        } catch (InternalError e6) {
            if (!(e6.getCause() instanceof InvocationTargetException) || !(e6.getCause().getCause() instanceof IOException)) {
                throw e6;
            }
            e2 = (IOException) e6.getCause().getCause();
            mo337w(e2);
            c0275d.m766k(e2.getMessage(), false, -1);
        }
    }

    /* renamed from: t */
    public final void m822t() {
        if (this.f572n != null) {
            this.f568j.mo750e(1000);
        }
    }

    /* renamed from: u */
    public final void m823u() {
        if (this.f573o != null) {
            throw new IllegalStateException("WebSocketClient objects are not reuseable");
        }
        Thread thread = new Thread(this);
        this.f573o = thread;
        thread.setDaemon(this.f459g);
        this.f573o.setName("WebSocketConnectReadThread-" + this.f573o.getId());
        this.f573o.start();
    }

    /* renamed from: v */
    public final int m824v() {
        URI uri = this.f567i;
        int port = uri.getPort();
        String scheme = uri.getScheme();
        if ("wss".equals(scheme)) {
            if (port == -1) {
                return 443;
            }
            return port;
        }
        if (!"ws".equals(scheme)) {
            throw new IllegalArgumentException(AbstractC0000a.m15k("unknown scheme: ", scheme));
        }
        if (port == -1) {
            return 80;
        }
        return port;
    }

    /* renamed from: w */
    public abstract void mo337w(Exception exc);

    /* renamed from: x */
    public abstract void mo338x(String str);

    /* renamed from: y */
    public final boolean m825y() {
        Socket socket;
        Proxy proxy = Proxy.NO_PROXY;
        Proxy proxy2 = this.f571m;
        if (proxy2 != proxy) {
            socket = new Socket(proxy2);
        } else {
            Socket socket2 = this.f569k;
            if (socket2 != null) {
                if (socket2.isClosed()) {
                    throw new IOException();
                }
                return false;
            }
            socket = new Socket(proxy2);
        }
        this.f569k = socket;
        return true;
    }

    /* renamed from: z */
    public final void m826z() {
        String str;
        URI uri = this.f567i;
        String rawPath = uri.getRawPath();
        String rawQuery = uri.getRawQuery();
        if (rawPath == null || rawPath.length() == 0) {
            rawPath = "/";
        }
        if (rawQuery != null) {
            rawPath = rawPath + '?' + rawQuery;
        }
        int m824v = m824v();
        StringBuilder sb = new StringBuilder();
        sb.append(uri.getHost());
        sb.append((m824v == 80 || m824v == 443) ? BuildConfig.FLAVOR : AbstractC0000a.m11g(":", m824v));
        String sb2 = sb.toString();
        C0390c c0390c = new C0390c();
        if (rawPath == null) {
            throw new IllegalArgumentException("http resource descriptor must not be null");
        }
        c0390c.f787b = rawPath;
        c0390c.m961b("Host", sb2);
        C0275d c0275d = this.f568j;
        AbstractC0274c abstractC0274c = c0275d.f464c;
        C0313b c0313b = c0275d.f471j;
        c0313b.getClass();
        c0390c.m961b("Upgrade", "websocket");
        c0390c.m961b("Connection", "Upgrade");
        byte[] bArr = new byte[16];
        c0313b.f588k.nextBytes(bArr);
        try {
            str = AbstractC0026q.m185r(16, bArr);
        } catch (IOException unused) {
            str = null;
        }
        c0390c.m961b("Sec-WebSocket-Key", str);
        c0390c.m961b("Sec-WebSocket-Version", "13");
        StringBuilder sb3 = new StringBuilder();
        Iterator it = c0313b.f581d.iterator();
        while (it.hasNext()) {
            ((C0355a) it.next()).getClass();
        }
        if (sb3.length() != 0) {
            c0390c.m961b("Sec-WebSocket-Extensions", sb3.toString());
        }
        StringBuilder sb4 = new StringBuilder();
        Iterator it2 = c0313b.f584g.iterator();
        while (it2.hasNext()) {
            C0403b c0403b = (C0403b) ((InterfaceC0402a) it2.next());
            if (c0403b.f807a.length() != 0) {
                if (sb4.length() > 0) {
                    sb4.append(", ");
                }
                sb4.append(c0403b.f807a);
            }
        }
        if (sb4.length() != 0) {
            c0390c.m961b("Sec-WebSocket-Protocol", sb4.toString());
        }
        c0275d.f474m = c0390c;
        c0275d.f478q = c0390c.f787b;
        try {
            abstractC0274c.getClass();
            C0313b c0313b2 = c0275d.f471j;
            InterfaceC0388a interfaceC0388a = c0275d.f474m;
            c0313b2.getClass();
            c0275d.m774t(AbstractC0312a.m827b(interfaceC0388a));
        } catch (C0340c unused2) {
            throw new C0342e("Handshake data rejected by client.");
        } catch (RuntimeException e2) {
            AbstractC0026q.m186s("Exception in startHandshake", e2);
            abstractC0274c.mo757l(c0275d, e2);
            throw new C0342e("rejected because of " + e2);
        }
    }
}
