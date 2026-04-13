package f0;

import a1.AbstractC0026q;
import android.util.Log;
import g0.InterfaceC0309a;
import g0.InterfaceC0310b;
import g0.InterfaceC0311c;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import n0.C0405a;
import n0.C0407c;
import p012o.RunnableC0415d;

/* renamed from: f0.b */
/* loaded from: classes.dex */
public final class C0281b implements InterfaceC0290k {

    /* renamed from: d */
    public a0 f489d;

    /* renamed from: e */
    public SelectionKey f490e;

    /* renamed from: f */
    public C0289j f491f;

    /* renamed from: h */
    public C0405a f493h;

    /* renamed from: i */
    public boolean f494i;

    /* renamed from: j */
    public InterfaceC0311c f495j;

    /* renamed from: k */
    public InterfaceC0310b f496k;

    /* renamed from: l */
    public InterfaceC0309a f497l;

    /* renamed from: m */
    public boolean f498m;

    /* renamed from: n */
    public Exception f499n;

    /* renamed from: o */
    public InterfaceC0309a f500o;

    /* renamed from: g */
    public final C0292m f492g = new C0292m();

    /* renamed from: p */
    public boolean f501p = false;

    /* renamed from: a */
    public final void m776a() {
        long j2;
        C0292m c0292m = this.f492g;
        boolean z2 = true;
        if (c0292m.f541c > 0) {
            AbstractC0026q.m183p(this, c0292m);
        }
        if (this.f501p) {
            return;
        }
        C0405a c0405a = this.f493h;
        ByteBuffer m801g = C0292m.m801g(Math.min(Math.max(c0405a.f810b, 4096), c0405a.f809a));
        try {
            j2 = this.f489d.read(m801g);
        } catch (Exception e2) {
            this.f490e.cancel();
            try {
                this.f489d.close();
            } catch (IOException unused) {
            }
            m790o(e2);
            m789n(e2);
            j2 = -1;
        }
        if (j2 < 0) {
            this.f490e.cancel();
            try {
                this.f489d.close();
            } catch (IOException unused2) {
            }
        } else {
            z2 = false;
        }
        if (j2 > 0) {
            this.f493h.f810b = ((int) j2) * 2;
            m801g.flip();
            c0292m.m803a(m801g);
            AbstractC0026q.m183p(this, c0292m);
        } else {
            C0292m.m802j(m801g);
        }
        if (z2) {
            m790o(null);
            m789n(null);
        }
    }

    @Override // f0.InterfaceC0295p
    /* renamed from: b */
    public final C0289j mo777b() {
        return this.f491f;
    }

    @Override // f0.InterfaceC0295p
    /* renamed from: c */
    public final void mo778c(C0292m c0292m) {
        boolean isConnected;
        SelectionKey selectionKey;
        int interestOps;
        if (this.f491f.f530e != Thread.currentThread()) {
            this.f491f.m798e(new RunnableC0415d(this, c0292m, 9));
            return;
        }
        a0 a0Var = this.f489d;
        switch (a0Var.f487b) {
            case 0:
                isConnected = false;
                break;
            default:
                isConnected = ((SocketChannel) a0Var.f488c).isConnected();
                break;
        }
        if (isConnected) {
            try {
                int i2 = c0292m.f541c;
                C0407c c0407c = c0292m.f539a;
                ByteBuffer[] byteBufferArr = (ByteBuffer[]) c0407c.toArray(new ByteBuffer[c0407c.size()]);
                c0407c.clear();
                c0292m.f541c = 0;
                a0 a0Var2 = this.f489d;
                switch (a0Var2.f487b) {
                    case 0:
                        throw new IOException("Can't write ServerSocketChannel");
                    default:
                        ((SocketChannel) a0Var2.f488c).write(byteBufferArr);
                        for (ByteBuffer byteBuffer : byteBufferArr) {
                            c0292m.m803a(byteBuffer);
                        }
                        int i3 = c0292m.f541c;
                        if (!this.f490e.isValid()) {
                            throw new IOException(new CancelledKeyException());
                        }
                        if (i3 > 0) {
                            selectionKey = this.f490e;
                            interestOps = selectionKey.interestOps() | 4;
                        } else {
                            selectionKey = this.f490e;
                            interestOps = selectionKey.interestOps() & (-5);
                        }
                        selectionKey.interestOps(interestOps);
                        this.f491f.getClass();
                        return;
                }
            } catch (IOException e2) {
                this.f490e.cancel();
                try {
                    this.f489d.close();
                } catch (IOException unused) {
                }
                m790o(e2);
                m789n(e2);
            }
        }
    }

    @Override // f0.InterfaceC0294o
    public final void close() {
        this.f490e.cancel();
        try {
            this.f489d.close();
        } catch (IOException unused) {
        }
        m789n(null);
    }

    @Override // f0.InterfaceC0295p
    /* renamed from: d */
    public final void mo779d(InterfaceC0311c interfaceC0311c) {
        this.f495j = interfaceC0311c;
    }

    @Override // f0.InterfaceC0294o
    /* renamed from: e */
    public final boolean mo780e() {
        return this.f501p;
    }

    @Override // f0.InterfaceC0295p
    /* renamed from: f */
    public final void mo781f(InterfaceC0309a interfaceC0309a) {
        this.f497l = interfaceC0309a;
    }

    @Override // f0.InterfaceC0294o
    /* renamed from: g */
    public final String mo782g() {
        return "UTF-8";
    }

    @Override // f0.InterfaceC0294o
    /* renamed from: h */
    public final void mo783h(InterfaceC0310b interfaceC0310b) {
        this.f496k = interfaceC0310b;
    }

    @Override // f0.InterfaceC0295p
    /* renamed from: i */
    public final InterfaceC0311c mo784i() {
        return this.f495j;
    }

    @Override // f0.InterfaceC0294o
    /* renamed from: j */
    public final void mo785j(InterfaceC0309a interfaceC0309a) {
        this.f500o = interfaceC0309a;
    }

    @Override // f0.InterfaceC0294o
    /* renamed from: k */
    public final InterfaceC0310b mo786k() {
        return this.f496k;
    }

    @Override // f0.InterfaceC0295p
    /* renamed from: l */
    public final void mo787l() {
        a0 a0Var = this.f489d;
        switch (a0Var.f487b) {
            case 0:
                break;
            default:
                try {
                    ((SocketChannel) a0Var.f488c).socket().shutdownOutput();
                    break;
                } catch (Exception unused) {
                    return;
                }
        }
    }

    /* renamed from: m */
    public final void m788m() {
        if (this.f491f.f530e != Thread.currentThread()) {
            this.f491f.m798e(new RunnableC0280a(this, 0));
        } else {
            if (this.f501p) {
                return;
            }
            this.f501p = true;
            try {
                SelectionKey selectionKey = this.f490e;
                selectionKey.interestOps(selectionKey.interestOps() & (-2));
            } catch (Exception unused) {
            }
        }
    }

    /* renamed from: n */
    public final void m789n(Exception exc) {
        if (this.f494i) {
            return;
        }
        this.f494i = true;
        InterfaceC0309a interfaceC0309a = this.f497l;
        if (interfaceC0309a != null) {
            interfaceC0309a.mo293a(exc);
            this.f497l = null;
        }
    }

    /* renamed from: o */
    public final void m790o(Exception exc) {
        if (this.f492g.f541c > 0) {
            this.f499n = exc;
            return;
        }
        if (this.f498m) {
            return;
        }
        this.f498m = true;
        InterfaceC0309a interfaceC0309a = this.f500o;
        if (interfaceC0309a != null) {
            interfaceC0309a.mo293a(exc);
        } else if (exc != null) {
            Log.e("NIO", "Unhandled exception", exc);
        }
    }

    /* renamed from: p */
    public final void m791p() {
        boolean isConnected;
        if (this.f491f.f530e != Thread.currentThread()) {
            this.f491f.m798e(new RunnableC0280a(this, 1));
            return;
        }
        if (this.f501p) {
            this.f501p = false;
            try {
                SelectionKey selectionKey = this.f490e;
                selectionKey.interestOps(selectionKey.interestOps() | 1);
            } catch (Exception unused) {
            }
            C0292m c0292m = this.f492g;
            if (c0292m.f541c > 0) {
                AbstractC0026q.m183p(this, c0292m);
            }
            a0 a0Var = this.f489d;
            switch (a0Var.f487b) {
                case 0:
                    isConnected = false;
                    break;
                default:
                    isConnected = ((SocketChannel) a0Var.f488c).isConnected();
                    break;
            }
            if (isConnected && this.f490e.isValid()) {
                return;
            }
            m790o(this.f499n);
        }
    }
}
