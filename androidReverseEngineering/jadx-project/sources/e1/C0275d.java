package e1;

import a1.AbstractC0026q;
import android.sun.security.util.DerValue;
import android.support.v4.view.PointerIconCompat;
import android.util.Log;
import g1.AbstractC0312a;
import g1.C0313b;
import i1.C0339b;
import i1.C0340c;
import i1.C0342e;
import i1.C0343f;
import i1.C0344g;
import j1.C0355a;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.SelectionKey;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import k1.AbstractC0367d;
import k1.C0364a;
import k1.C0365b;
import l1.InterfaceC0388a;
import l1.InterfaceC0389b;
import l1.InterfaceC0393f;
import n1.C0410a;
import o1.AbstractC0447a;
import com.guard.wallet.entity.BuildConfig;
import p000a.AbstractC0000a;

/* renamed from: e1.d */
/* loaded from: classes.dex */
public final class C0275d implements InterfaceC0273b {

    /* renamed from: a */
    public final LinkedBlockingQueue f462a;

    /* renamed from: b */
    public final LinkedBlockingQueue f463b;

    /* renamed from: c */
    public final AbstractC0274c f464c;

    /* renamed from: d */
    public SelectionKey f465d;

    /* renamed from: e */
    public ByteChannel f466e;

    /* renamed from: f */
    public C0410a f467f;

    /* renamed from: g */
    public boolean f468g;

    /* renamed from: h */
    public volatile int f469h;

    /* renamed from: i */
    public final List f470i;

    /* renamed from: j */
    public C0313b f471j;

    /* renamed from: k */
    public final int f472k;

    /* renamed from: l */
    public ByteBuffer f473l;

    /* renamed from: m */
    public InterfaceC0388a f474m;

    /* renamed from: n */
    public String f475n;

    /* renamed from: o */
    public Integer f476o;

    /* renamed from: p */
    public Boolean f477p;

    /* renamed from: q */
    public String f478q;

    /* renamed from: r */
    public long f479r;

    /* renamed from: s */
    public final Object f480s;

    public C0275d(AbstractC0274c abstractC0274c, C0313b c0313b) {
        this.f468g = false;
        this.f469h = 1;
        this.f471j = null;
        this.f473l = ByteBuffer.allocate(0);
        this.f474m = null;
        this.f475n = null;
        this.f476o = null;
        this.f477p = null;
        this.f478q = null;
        this.f479r = System.nanoTime();
        this.f480s = new Object();
        if (abstractC0274c == null || (c0313b == null && this.f472k == 2)) {
            throw new IllegalArgumentException("parameters must not be null");
        }
        this.f462a = new LinkedBlockingQueue();
        this.f463b = new LinkedBlockingQueue();
        this.f464c = abstractC0274c;
        this.f472k = 1;
        if (c0313b != null) {
            this.f471j = c0313b.mo829a();
        }
    }

    /* renamed from: q */
    public static ByteBuffer m762q(int i2) {
        String str = i2 != 404 ? "500 Internal Server Error" : "404 WebSocket Upgrade Failure";
        StringBuilder m23s = AbstractC0000a.m23s("HTTP/1.1 ", str, "\r\nContent-Type: text/html\r\nServer: TooTallNate Java-WebSocket\r\nContent-Length: ");
        m23s.append(str.length() + 48);
        m23s.append("\r\n\r\n<html><head></head><body><h1>");
        m23s.append(str);
        m23s.append("</h1></body></html>");
        String sb = m23s.toString();
        CodingErrorAction codingErrorAction = AbstractC0447a.f1052a;
        return ByteBuffer.wrap(sb.getBytes(StandardCharsets.US_ASCII));
    }

    @Override // e1.InterfaceC0273b
    /* renamed from: a */
    public final void mo746a(byte[] bArr) {
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        if (wrap == null) {
            throw new IllegalArgumentException("Cannot send 'null' data to a WebSocketImpl.");
        }
        C0313b c0313b = this.f471j;
        boolean z2 = this.f472k == 1;
        c0313b.getClass();
        C0364a c0364a = new C0364a(0);
        c0364a.f726c = wrap;
        c0364a.f727d = z2;
        try {
            c0364a.mo941b();
            m773s(Collections.singletonList(c0364a));
        } catch (C0340c e2) {
            throw new C0344g(e2);
        }
    }

    @Override // e1.InterfaceC0273b
    /* renamed from: b */
    public final void mo747b(int i2, String str) {
        m764i(str, false, i2);
    }

    @Override // e1.InterfaceC0273b
    /* renamed from: c */
    public final void mo748c(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Cannot send 'null' data to a WebSocketImpl.");
        }
        C0313b c0313b = this.f471j;
        boolean z2 = this.f472k == 1;
        c0313b.getClass();
        C0364a c0364a = new C0364a(2);
        CodingErrorAction codingErrorAction = AbstractC0447a.f1052a;
        c0364a.f726c = ByteBuffer.wrap(str.getBytes(StandardCharsets.UTF_8));
        c0364a.f727d = z2;
        try {
            c0364a.mo941b();
            m773s(Collections.singletonList(c0364a));
        } catch (C0340c e2) {
            throw new C0344g(e2);
        }
    }

    @Override // e1.InterfaceC0273b
    /* renamed from: d */
    public final String mo749d() {
        return this.f478q;
    }

    @Override // e1.InterfaceC0273b
    /* renamed from: e */
    public final void mo750e(int i2) {
        m764i(BuildConfig.FLAVOR, false, i2);
    }

    @Override // e1.InterfaceC0273b
    /* renamed from: f */
    public final void mo751f(String str) {
        m766k(str, false, PointerIconCompat.TYPE_CELL);
    }

    @Override // e1.InterfaceC0273b
    /* renamed from: g */
    public final InetSocketAddress mo752g() {
        return this.f464c.mo753h(this);
    }

    /* renamed from: h */
    public final void m763h(C0340c c0340c) {
        m764i(c0340c.getMessage(), false, c0340c.f655a);
    }

    /* renamed from: i */
    public final synchronized void m764i(String str, boolean z2, int i2) {
        if (this.f469h == 3 || this.f469h == 4) {
            return;
        }
        boolean z3 = true;
        if (this.f469h != 2) {
            if (i2 == -3) {
                m771p(str, true, -3);
            } else if (i2 != 1002) {
                m771p(str, false, -1);
            }
            this.f469h = 3;
            this.f473l = null;
        }
        if (i2 == 1006) {
            this.f469h = 3;
            m771p(str, false, i2);
            return;
        }
        this.f471j.getClass();
        try {
            if (!z2) {
                try {
                    this.f464c.mo755j();
                } catch (RuntimeException e2) {
                    this.f464c.mo757l(this, e2);
                }
            }
            if (this.f469h != 2) {
                z3 = false;
            }
            if (z3) {
                C0365b c0365b = new C0365b();
                c0365b.f722j = str == null ? BuildConfig.FLAVOR : str;
                c0365b.m944d();
                c0365b.f721i = i2;
                if (i2 == 1015) {
                    c0365b.f721i = 1005;
                    c0365b.f722j = BuildConfig.FLAVOR;
                }
                c0365b.m944d();
                c0365b.mo941b();
                m773s(Collections.singletonList(c0365b));
            }
        } catch (C0340c e3) {
            AbstractC0026q.m186s("generated frame is invalid", e3);
            this.f464c.mo757l(this, e3);
            m771p("generated frame is invalid", false, PointerIconCompat.TYPE_CELL);
        }
        m771p(str, z2, i2);
        this.f469h = 3;
        this.f473l = null;
    }

    /* renamed from: j */
    public final void m765j(int i2) {
        m766k(BuildConfig.FLAVOR, true, i2);
    }

    /* renamed from: k */
    public final synchronized void m766k(String str, boolean z2, int i2) {
        if (this.f469h == 4) {
            return;
        }
        if (this.f469h == 2 && i2 == 1006) {
            this.f469h = 3;
        }
        SelectionKey selectionKey = this.f465d;
        if (selectionKey != null) {
            selectionKey.cancel();
        }
        ByteChannel byteChannel = this.f466e;
        if (byteChannel != null) {
            try {
                byteChannel.close();
            } catch (IOException e2) {
                if (e2.getMessage() == null || !e2.getMessage().equals("Broken pipe")) {
                    AbstractC0026q.m186s("Exception during channel.close()", e2);
                    this.f464c.mo757l(this, e2);
                } else {
                    AbstractC0026q.m186s("Caught IOException: Broken pipe during closeConnection()", e2);
                }
            }
        }
        try {
            this.f464c.mo754i(this, i2, str, z2);
        } catch (RuntimeException e3) {
            this.f464c.mo757l(this, e3);
        }
        C0313b c0313b = this.f471j;
        if (c0313b != null) {
            c0313b.f587j = null;
            C0355a c0355a = c0313b.f579b;
            c0313b.f579b = new C0355a();
            c0313b.f583f = null;
        }
        this.f474m = null;
        this.f469h = 4;
    }

    /* renamed from: l */
    public final void m767l(C0340c c0340c) {
        this.f462a.add(m762q(404));
        this.f464c.mo761p(this);
        m771p(c0340c.getMessage(), false, c0340c.f655a);
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0098, code lost:
    
        r13.f478q = ((l1.C0390c) r9).f787b;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x009f, code lost:
    
        r5.getClass();
        r12 = new l1.C0391d();
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00a7, code lost:
    
        r1.m839l(r9, r12);
        m774t(g1.AbstractC0312a.m827b(r12));
        r13.f471j = r1;
        m772r(r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00dd, code lost:
    
        r1 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00de, code lost:
    
        android.util.Log.d("e1.d", "Closing due to wrong handshake. Possible handshake rejection", r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00e3, code lost:
    
        m767l(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00bb, code lost:
    
        r1 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00bc, code lost:
    
        android.util.Log.d("e1.d", "Closing due to internal server error", r1);
        r5.mo757l(r13, r1);
        r13.f462a.add(m762q(500));
        r13.f464c.mo761p(r13);
        m771p(r1.getMessage(), false, -1);
     */
    /* JADX WARN: Removed duplicated region for block: B:33:0x01c5  */
    /* JADX WARN: Removed duplicated region for block: B:50:? A[RETURN, SYNTHETIC] */
    /* renamed from: m */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m768m(ByteBuffer byteBuffer) {
        ByteBuffer byteBuffer2;
        boolean z2;
        String str;
        InterfaceC0389b m830d;
        if (this.f469h == 1) {
            if (this.f473l.capacity() == 0) {
                byteBuffer2 = byteBuffer;
            } else {
                if (this.f473l.remaining() < byteBuffer.remaining()) {
                    ByteBuffer allocate = ByteBuffer.allocate(byteBuffer.remaining() + this.f473l.capacity());
                    this.f473l.flip();
                    allocate.put(this.f473l);
                    this.f473l = allocate;
                }
                this.f473l.put(byteBuffer);
                this.f473l.flip();
                byteBuffer2 = this.f473l;
            }
            byteBuffer2.mark();
            int i2 = this.f472k;
            AbstractC0274c abstractC0274c = this.f464c;
            try {
                try {
                } catch (C0342e e2) {
                    Log.d("e1.d", "Closing due to invalid handshake", e2);
                    m763h(e2);
                }
            } catch (C0339b e3) {
                if (this.f473l.capacity() == 0) {
                    byteBuffer2.reset();
                    int i3 = e3.f654a;
                    if (i3 == 0) {
                        i3 = byteBuffer2.capacity() + 16;
                    }
                    ByteBuffer allocate2 = ByteBuffer.allocate(i3);
                    this.f473l = allocate2;
                    allocate2.put(byteBuffer);
                } else {
                    ByteBuffer byteBuffer3 = this.f473l;
                    byteBuffer3.position(byteBuffer3.limit());
                    ByteBuffer byteBuffer4 = this.f473l;
                    byteBuffer4.limit(byteBuffer4.capacity());
                }
            }
            if (i2 == 2) {
                C0313b c0313b = this.f471j;
                if (c0313b == null) {
                    Iterator it = this.f470i.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            C0313b mo829a = ((AbstractC0312a) it.next()).mo829a();
                            try {
                                mo829a.f578a = i2;
                                byteBuffer2.reset();
                                m830d = mo829a.m830d(byteBuffer2);
                            } catch (C0342e unused) {
                            }
                            if (!(m830d instanceof InterfaceC0388a)) {
                                Log.d("e1.d", "Closing due to wrong handshake");
                                C0340c e4 = new C0340c(PointerIconCompat.TYPE_HAND, "wrong http function");
                                break;
                            } else {
                                InterfaceC0388a interfaceC0388a = (InterfaceC0388a) m830d;
                                if (mo829a.m835f(interfaceC0388a) == 1) {
                                    break;
                                }
                            }
                        } else if (this.f471j == null) {
                            Log.d("e1.d", "Closing due to protocol error: no draft matches");
                            m767l(new C0340c(PointerIconCompat.TYPE_HAND, "no draft matches"));
                        }
                    }
                    z2 = false;
                } else {
                    InterfaceC0389b m830d2 = c0313b.m830d(byteBuffer2);
                    if (m830d2 instanceof InterfaceC0388a) {
                        InterfaceC0388a interfaceC0388a2 = (InterfaceC0388a) m830d2;
                        if (this.f471j.m835f(interfaceC0388a2) == 1) {
                            m772r(interfaceC0388a2);
                        } else {
                            Log.d("e1.d", "Closing due to protocol error: the handshake did finally not match");
                            str = "the handshake did finally not match";
                            m764i(str, false, PointerIconCompat.TYPE_HAND);
                            z2 = false;
                        }
                    }
                    Log.d("e1.d", "Closing due to protocol error: wrong http function");
                    m771p("wrong http function", false, PointerIconCompat.TYPE_HAND);
                    z2 = false;
                }
                z2 = true;
            } else {
                if (i2 == 1) {
                    C0313b c0313b2 = this.f471j;
                    c0313b2.f578a = i2;
                    InterfaceC0389b m830d3 = c0313b2.m830d(byteBuffer2);
                    if (m830d3 instanceof InterfaceC0393f) {
                        InterfaceC0393f interfaceC0393f = (InterfaceC0393f) m830d3;
                        if (this.f471j.m834e(this.f474m, interfaceC0393f) == 1) {
                            try {
                                abstractC0274c.getClass();
                                m772r(interfaceC0393f);
                                z2 = true;
                            } catch (C0340c e5) {
                                Log.d("e1.d", "Closing due to invalid data exception. Possible handshake rejection", e5);
                                m771p(e5.getMessage(), false, e5.f655a);
                            } catch (RuntimeException e6) {
                                Log.d("e1.d", "Closing since client was never connected", e6);
                                abstractC0274c.mo757l(this, e6);
                                m771p(e6.getMessage(), false, -1);
                            }
                        } else {
                            Log.d("e1.d", "Closing due to protocol error: draft {} refuses handshake");
                            str = "draft " + this.f471j + " refuses handshake";
                            m764i(str, false, PointerIconCompat.TYPE_HAND);
                        }
                    } else {
                        Log.d("e1.d", "Closing due to protocol error: wrong http function");
                        m771p("wrong http function", false, PointerIconCompat.TYPE_HAND);
                    }
                }
                z2 = false;
            }
            if (z2) {
                return;
            }
            if (this.f469h == 3) {
                return;
            }
            if (this.f469h == 4) {
                return;
            }
            if (!byteBuffer.hasRemaining()) {
                if (!this.f473l.hasRemaining()) {
                    return;
                } else {
                    byteBuffer = this.f473l;
                }
            }
        } else if (this.f469h != 2) {
            return;
        }
        m769n(byteBuffer);
    }

    /* renamed from: n */
    public final void m769n(ByteBuffer byteBuffer) {
        String str;
        C0340c c0340c;
        C0340c c0340c2;
        AbstractC0274c abstractC0274c = this.f464c;
        try {
            for (AbstractC0367d abstractC0367d : this.f471j.m841n(byteBuffer)) {
                Log.d("e1.d", "matched frame: " + abstractC0367d);
                this.f471j.m840m(this, abstractC0367d);
            }
        } catch (C0343f e2) {
            int i2 = e2.f656b;
            c0340c = e2;
            if (i2 == Integer.MAX_VALUE) {
                str = "Closing due to invalid size of frame";
                c0340c2 = e2;
                AbstractC0026q.m186s(str, c0340c2);
                abstractC0274c.mo757l(this, c0340c2);
                c0340c = c0340c2;
            }
            m763h(c0340c);
        } catch (C0340c e3) {
            str = "Closing due to invalid data in frame";
            c0340c2 = e3;
            AbstractC0026q.m186s(str, c0340c2);
            abstractC0274c.mo757l(this, c0340c2);
            c0340c = c0340c2;
            m763h(c0340c);
        } catch (LinkageError e4) {
            e = e4;
            AbstractC0026q.m187t("Got fatal error during frame processing", e);
            throw e;
        } catch (ThreadDeath e5) {
            e = e5;
            AbstractC0026q.m187t("Got fatal error during frame processing", e);
            throw e;
        } catch (VirtualMachineError e6) {
            e = e6;
            AbstractC0026q.m187t("Got fatal error during frame processing", e);
            throw e;
        } catch (Error e7) {
            AbstractC0026q.m187t("Closing web socket due to an error during frame processing", e7);
            abstractC0274c.mo757l(this, new Exception(e7));
            m764i("Got error ".concat(e7.getClass().getName()), false, PointerIconCompat.TYPE_COPY);
        }
    }

    /* renamed from: o */
    public final void m770o() {
        int i2;
        if (this.f469h == 1) {
            i2 = -1;
        } else if (this.f468g) {
            m766k(this.f475n, this.f477p.booleanValue(), this.f476o.intValue());
            return;
        } else {
            this.f471j.getClass();
            this.f471j.getClass();
            i2 = PointerIconCompat.TYPE_CELL;
        }
        m765j(i2);
    }

    /* renamed from: p */
    public final synchronized void m771p(String str, boolean z2, int i2) {
        if (this.f468g) {
            return;
        }
        this.f476o = Integer.valueOf(i2);
        this.f475n = str;
        this.f477p = Boolean.valueOf(z2);
        this.f468g = true;
        this.f464c.mo761p(this);
        try {
            this.f464c.mo756k();
        } catch (RuntimeException e2) {
            AbstractC0026q.m186s("Exception in onWebsocketClosing", e2);
            this.f464c.mo757l(this, e2);
        }
        C0313b c0313b = this.f471j;
        if (c0313b != null) {
            c0313b.f587j = null;
            C0355a c0355a = c0313b.f579b;
            c0313b.f579b = new C0355a();
            c0313b.f583f = null;
        }
        this.f474m = null;
    }

    /* renamed from: r */
    public final void m772r(InterfaceC0389b interfaceC0389b) {
        Log.d("e1.d", "open using draft: " + this.f471j);
        this.f469h = 2;
        this.f479r = System.nanoTime();
        try {
            this.f464c.mo760o(this, interfaceC0389b);
        } catch (RuntimeException e2) {
            this.f464c.mo757l(this, e2);
        }
    }

    /* renamed from: s */
    public final void m773s(List list) {
        byte b;
        int i2;
        byte b2;
        int i3;
        byte b3 = 2;
        if (!(this.f469h == 2)) {
            throw new C0344g();
        }
        if (list == null) {
            throw new IllegalArgumentException();
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            AbstractC0367d abstractC0367d = (AbstractC0367d) it.next();
            Log.d("e1.d", "send frame:" + abstractC0367d.toString());
            C0313b c0313b = this.f471j;
            c0313b.f579b.getClass();
            ByteBuffer mo942a = abstractC0367d.mo942a();
            boolean z2 = c0313b.f578a == 1;
            int i4 = mo942a.remaining() <= 125 ? 1 : mo942a.remaining() <= 65535 ? b3 : 8;
            ByteBuffer allocate = ByteBuffer.allocate(mo942a.remaining() + (i4 > 1 ? i4 + 1 : i4) + 1 + (z2 ? 4 : 0));
            int i5 = abstractC0367d.f725b;
            if (i5 == 1) {
                b = 0;
            } else if (i5 == b3) {
                b = 1;
            } else if (i5 == 3) {
                b = b3;
            } else if (i5 == 6) {
                b = 8;
            } else if (i5 == 4) {
                b = 9;
            } else {
                if (i5 != 5) {
                    throw new IllegalArgumentException("Don't know how to handle ".concat(AbstractC0000a.m3D(i5)));
                }
                b = 10;
            }
            boolean z3 = abstractC0367d.f724a;
            byte b4 = DerValue.TAG_CONTEXT;
            byte b5 = (byte) (b | ((byte) (z3 ? -128 : 0)));
            if (abstractC0367d.f728e) {
                b5 = (byte) (b5 | C0313b.m832k(1));
            }
            if (abstractC0367d.f729f) {
                b5 = (byte) (b5 | C0313b.m832k(b3));
            }
            if (abstractC0367d.f730g) {
                b5 = (byte) (C0313b.m832k(3) | b5);
            }
            allocate.put(b5);
            long remaining = mo942a.remaining();
            byte[] bArr = new byte[i4];
            int i6 = (i4 * 8) - 8;
            for (int i7 = 0; i7 < i4; i7++) {
                bArr[i7] = (byte) (remaining >>> (i6 - (i7 * 8)));
            }
            if (i4 == 1) {
                i2 = 0;
                byte b6 = bArr[0];
                if (!z2) {
                    b4 = 0;
                }
                allocate.put((byte) (b6 | b4));
                b2 = 2;
            } else {
                i2 = 0;
                b2 = 2;
                if (i4 == 2) {
                    if (!z2) {
                        b4 = 0;
                    }
                    i3 = b4 | 126;
                } else {
                    if (i4 != 8) {
                        throw new IllegalStateException("Size representation not supported/specified");
                    }
                    if (!z2) {
                        b4 = 0;
                    }
                    i3 = b4 | Byte.MAX_VALUE;
                }
                allocate.put((byte) i3);
                allocate.put(bArr);
            }
            if (z2) {
                ByteBuffer allocate2 = ByteBuffer.allocate(4);
                allocate2.putInt(c0313b.f588k.nextInt());
                allocate.put(allocate2.array());
                int i8 = i2;
                while (mo942a.hasRemaining()) {
                    allocate.put((byte) (mo942a.get() ^ allocate2.get(i8 % 4)));
                    i8++;
                }
            } else {
                allocate.put(mo942a);
                mo942a.flip();
            }
            allocate.flip();
            arrayList.add(allocate);
            b3 = b2;
        }
        m774t(arrayList);
    }

    /* renamed from: t */
    public final void m774t(List list) {
        synchronized (this.f480s) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                this.f462a.add((ByteBuffer) it.next());
                this.f464c.mo761p(this);
            }
        }
    }

    public final String toString() {
        return super.toString();
    }

    public C0275d(AbstractC0274c abstractC0274c, List list) {
        this(abstractC0274c, (C0313b) null);
        this.f472k = 2;
        if (list != null && !list.isEmpty()) {
            this.f470i = list;
            return;
        }
        ArrayList arrayList = new ArrayList();
        this.f470i = arrayList;
        arrayList.add(new C0313b());
    }
}
