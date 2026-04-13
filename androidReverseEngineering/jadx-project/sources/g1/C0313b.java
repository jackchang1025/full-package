package g1;

import a1.AbstractC0026q;
import android.sun.security.util.DerValue;
import android.support.v4.view.PointerIconCompat;
import android.util.Log;
import e1.AbstractC0274c;
import e1.C0275d;
import i1.C0338a;
import i1.C0340c;
import i1.C0341d;
import i1.C0342e;
import i1.C0343f;
import j1.C0355a;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import k1.AbstractC0366c;
import k1.AbstractC0367d;
import k1.C0364a;
import k1.C0365b;
import k1.C0368e;
import k1.C0369f;
import l1.AbstractC0392e;
import l1.C0391d;
import l1.InterfaceC0388a;
import l1.InterfaceC0393f;
import m1.C0403b;
import m1.InterfaceC0402a;
import o1.AbstractC0447a;
import org.bouncycastle.math.ec.Tnaf;
import com.guard.wallet.entity.BuildConfig;
import p000a.AbstractC0000a;
import p014r.AbstractC0888a;

/* renamed from: g1.b */
/* loaded from: classes.dex */
public final class C0313b extends AbstractC0312a {

    /* renamed from: b */
    public C0355a f579b;

    /* renamed from: c */
    public final C0355a f580c;

    /* renamed from: d */
    public final ArrayList f581d;

    /* renamed from: e */
    public C0355a f582e;

    /* renamed from: f */
    public InterfaceC0402a f583f;

    /* renamed from: g */
    public final ArrayList f584g;

    /* renamed from: h */
    public AbstractC0367d f585h;

    /* renamed from: i */
    public final ArrayList f586i;

    /* renamed from: j */
    public ByteBuffer f587j;

    /* renamed from: k */
    public final SecureRandom f588k;

    /* renamed from: l */
    public final int f589l;

    public C0313b() {
        this(Integer.MAX_VALUE, Collections.emptyList(), Collections.singletonList(new C0403b(BuildConfig.FLAVOR)));
    }

    /* renamed from: i */
    public static String m831i(String str) {
        String m30z = AbstractC0000a.m30z(str.trim(), "258EAFA5-E914-47DA-95CA-C5AB0DC85B11");
        try {
            byte[] digest = MessageDigest.getInstance("SHA1").digest(m30z.getBytes());
            try {
                return AbstractC0026q.m185r(digest.length, digest);
            } catch (IOException unused) {
                return null;
            }
        } catch (NoSuchAlgorithmException e2) {
            throw new IllegalStateException(e2);
        }
    }

    /* renamed from: k */
    public static byte m832k(int i2) {
        if (i2 == 1) {
            return DerValue.TAG_APPLICATION;
        }
        if (i2 == 2) {
            return (byte) 32;
        }
        if (i2 != 3) {
            return (byte) 0;
        }
        return Tnaf.POW_2_WIDTH;
    }

    /* renamed from: q */
    public static void m833q(int i2, int i3) {
        if (i2 >= i3) {
            return;
        }
        Log.d("g1.b", "Incomplete frame: maxpacketsize < realpacketsize");
        throw new C0338a(i3);
    }

    @Override // g1.AbstractC0312a
    /* renamed from: a */
    public final C0313b mo829a() {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.f581d.iterator();
        while (it.hasNext()) {
            ((C0355a) it.next()).getClass();
            arrayList.add(new C0355a());
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it2 = this.f584g.iterator();
        while (it2.hasNext()) {
            arrayList2.add(new C0403b(((C0403b) ((InterfaceC0402a) it2.next())).f807a));
        }
        return new C0313b(this.f589l, arrayList, arrayList2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: e */
    public final int m834e(InterfaceC0388a interfaceC0388a, InterfaceC0393f interfaceC0393f) {
        String str;
        boolean z2;
        AbstractC0392e abstractC0392e = (AbstractC0392e) interfaceC0393f;
        if (abstractC0392e.m960a("Upgrade").equalsIgnoreCase("websocket") && abstractC0392e.m960a("Connection").toLowerCase(Locale.ENGLISH).contains("upgrade")) {
            AbstractC0392e abstractC0392e2 = (AbstractC0392e) interfaceC0388a;
            if (abstractC0392e2.f789a.containsKey("Sec-WebSocket-Key") && abstractC0392e.f789a.containsKey("Sec-WebSocket-Accept")) {
                if (m831i(abstractC0392e2.m960a("Sec-WebSocket-Key")).equals(abstractC0392e.m960a("Sec-WebSocket-Accept"))) {
                    abstractC0392e.m960a("Sec-WebSocket-Extensions");
                    Iterator it = this.f581d.iterator();
                    if (it.hasNext()) {
                        C0355a c0355a = (C0355a) it.next();
                        c0355a.getClass();
                        this.f579b = c0355a;
                        Log.d("g1.b", "acceptHandshakeAsClient - Matching extension found: " + this.f579b);
                        z2 = true;
                    } else {
                        z2 = 2;
                    }
                    if (m837h(abstractC0392e.m960a("Sec-WebSocket-Protocol")) == 1 && z2) {
                        return 1;
                    }
                    str = "acceptHandshakeAsClient - No matching extension or protocol found.";
                } else {
                    str = "acceptHandshakeAsClient - Wrong key for Sec-WebSocket-Key.";
                }
            } else {
                str = "acceptHandshakeAsClient - Missing Sec-WebSocket-Key or Sec-WebSocket-Accept";
            }
        } else {
            str = "acceptHandshakeAsClient - Missing/wrong upgrade or connection in handshake.";
        }
        Log.d("g1.b", str);
        return 2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C0313b.class != obj.getClass()) {
            return false;
        }
        C0313b c0313b = (C0313b) obj;
        if (this.f589l != c0313b.f589l) {
            return false;
        }
        C0355a c0355a = this.f579b;
        if (c0355a == null ? c0313b.f579b != null : !c0355a.equals(c0313b.f579b)) {
            return false;
        }
        InterfaceC0402a interfaceC0402a = this.f583f;
        return interfaceC0402a != null ? interfaceC0402a.equals(c0313b.f583f) : c0313b.f583f == null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0022  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x001f  */
    /* renamed from: f */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int m835f(InterfaceC0388a interfaceC0388a) {
        int parseInt;
        boolean z2;
        String str;
        AbstractC0392e abstractC0392e = (AbstractC0392e) interfaceC0388a;
        String m960a = abstractC0392e.m960a("Sec-WebSocket-Version");
        if (m960a.length() > 0) {
            try {
                parseInt = Integer.parseInt(m960a.trim());
            } catch (NumberFormatException unused) {
            }
            if (parseInt == 13) {
                str = "acceptHandshakeAsServer - Wrong websocket version.";
            } else {
                abstractC0392e.m960a("Sec-WebSocket-Extensions");
                Iterator it = this.f581d.iterator();
                if (it.hasNext()) {
                    C0355a c0355a = (C0355a) it.next();
                    c0355a.getClass();
                    this.f579b = c0355a;
                    Log.d("g1.b", "acceptHandshakeAsServer - Matching extension found:" + this.f579b);
                    z2 = true;
                } else {
                    z2 = 2;
                }
                if (m837h(abstractC0392e.m960a("Sec-WebSocket-Protocol")) == 1 && z2) {
                    return 1;
                }
                str = "acceptHandshakeAsServer - No matching extension or protocol found.";
            }
            Log.d("g1.b", str);
            return 2;
        }
        parseInt = -1;
        if (parseInt == 13) {
        }
        Log.d("g1.b", str);
        return 2;
    }

    /* renamed from: g */
    public final void m836g() {
        long j2;
        synchronized (this.f586i) {
            j2 = 0;
            while (this.f586i.iterator().hasNext()) {
                j2 += ((ByteBuffer) r1.next()).limit();
            }
        }
        if (j2 <= this.f589l) {
            return;
        }
        synchronized (this.f586i) {
            this.f586i.clear();
        }
        Log.d("g1.b", String.format("Payload limit reached. Allowed: %d Current: %d", Integer.valueOf(this.f589l), Long.valueOf(j2)));
        throw new C0343f(this.f589l);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0045 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:18:? A[LOOP:0: B:2:0x0006->B:18:?, LOOP_END, SYNTHETIC] */
    /* renamed from: h */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int m837h(String str) {
        boolean z2;
        Iterator it = this.f584g.iterator();
        while (it.hasNext()) {
            InterfaceC0402a interfaceC0402a = (InterfaceC0402a) it.next();
            String str2 = ((C0403b) interfaceC0402a).f807a;
            if (!BuildConfig.FLAVOR.equals(str2)) {
                z2 = false;
                for (String str3 : C0403b.f806c.split(C0403b.f805b.matcher(str).replaceAll(BuildConfig.FLAVOR))) {
                    if (!str2.equals(str3)) {
                    }
                }
                if (!z2) {
                    this.f583f = interfaceC0402a;
                    Log.d("g1.b", "acceptHandshake - Matching protocol found: " + this.f583f);
                    return 1;
                }
            }
            z2 = true;
            if (!z2) {
            }
        }
        return 2;
    }

    public final int hashCode() {
        C0355a c0355a = this.f579b;
        int hashCode = (c0355a != null ? c0355a.hashCode() : 0) * 31;
        InterfaceC0402a interfaceC0402a = this.f583f;
        int hashCode2 = (hashCode + (interfaceC0402a != null ? interfaceC0402a.hashCode() : 0)) * 31;
        int i2 = this.f589l;
        return hashCode2 + (i2 ^ (i2 >>> 32));
    }

    /* renamed from: j */
    public final ByteBuffer m838j() {
        ByteBuffer allocate;
        synchronized (this.f586i) {
            long j2 = 0;
            while (this.f586i.iterator().hasNext()) {
                j2 += ((ByteBuffer) r1.next()).limit();
            }
            m836g();
            allocate = ByteBuffer.allocate((int) j2);
            Iterator it = this.f586i.iterator();
            while (it.hasNext()) {
                allocate.put((ByteBuffer) it.next());
            }
        }
        allocate.flip();
        return allocate;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: l */
    public final InterfaceC0393f m839l(InterfaceC0388a interfaceC0388a, C0391d c0391d) {
        c0391d.m961b("Upgrade", "websocket");
        AbstractC0392e abstractC0392e = (AbstractC0392e) interfaceC0388a;
        c0391d.m961b("Connection", abstractC0392e.m960a("Connection"));
        String m960a = abstractC0392e.m960a("Sec-WebSocket-Key");
        if (BuildConfig.FLAVOR.equals(m960a)) {
            throw new C0342e("missing Sec-WebSocket-Key");
        }
        c0391d.m961b("Sec-WebSocket-Accept", m831i(m960a));
        this.f579b.getClass();
        InterfaceC0402a interfaceC0402a = this.f583f;
        if (interfaceC0402a != null && ((C0403b) interfaceC0402a).f807a.length() != 0) {
            c0391d.m961b("Sec-WebSocket-Protocol", ((C0403b) this.f583f).f807a);
        }
        c0391d.f788b = "Web Socket Protocol Handshake";
        c0391d.m961b("Server", "TooTallNate Java-WebSocket");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        c0391d.m961b("Date", simpleDateFormat.format(calendar.getTime()));
        return c0391d;
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x0143 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* renamed from: m */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m840m(C0275d c0275d, AbstractC0367d abstractC0367d) {
        String str;
        int i2;
        int i3 = abstractC0367d.f725b;
        if (i3 == 6) {
            if (abstractC0367d instanceof C0365b) {
                C0365b c0365b = (C0365b) abstractC0367d;
                i2 = c0365b.f721i;
                str = c0365b.f722j;
            } else {
                str = BuildConfig.FLAVOR;
                i2 = 1005;
            }
            if (c0275d.f469h == 3) {
                c0275d.m766k(str, true, i2);
                return;
            } else {
                c0275d.m764i(str, true, i2);
                return;
            }
        }
        if (i3 == 4) {
            c0275d.f464c.getClass();
            c0275d.m773s(Collections.singletonList(new C0369f((C0368e) abstractC0367d)));
            return;
        }
        if (i3 == 5) {
            c0275d.getClass();
            c0275d.f479r = System.nanoTime();
            c0275d.f464c.getClass();
            return;
        }
        boolean z2 = abstractC0367d.f724a;
        if (z2 && i3 != 1) {
            if (this.f585h != null) {
                Log.d("g1.b", "Protocol error: Continuous frame sequence not completed.");
                throw new C0340c(PointerIconCompat.TYPE_HAND, "Continuous frame sequence not completed.");
            }
            if (i3 == 2) {
                try {
                    c0275d.f464c.mo759n(c0275d, AbstractC0447a.m1183b(abstractC0367d.mo942a()));
                    return;
                } catch (RuntimeException e2) {
                    Log.d("g1.b", "Runtime exception during onWebsocketMessage", e2);
                    c0275d.f464c.mo757l(c0275d, e2);
                    return;
                }
            }
            if (i3 != 3) {
                Log.d("g1.b", "non control or continious frame expected");
                throw new C0340c(PointerIconCompat.TYPE_HAND, "non control or continious frame expected");
            }
            try {
                AbstractC0274c abstractC0274c = c0275d.f464c;
                abstractC0367d.mo942a();
                abstractC0274c.mo758m();
                return;
            } catch (RuntimeException e3) {
                Log.d("g1.b", "Runtime exception during onWebsocketMessage", e3);
                c0275d.f464c.mo757l(c0275d, e3);
                return;
            }
        }
        if (i3 != 1) {
            if (this.f585h != null) {
                Log.d("g1.b", "Protocol error: Previous continuous frame sequence not completed.");
                throw new C0340c(PointerIconCompat.TYPE_HAND, "Previous continuous frame sequence not completed.");
            }
            this.f585h = abstractC0367d;
            ByteBuffer mo942a = abstractC0367d.mo942a();
            synchronized (this.f586i) {
                this.f586i.add(mo942a);
            }
            m836g();
        } else if (z2) {
            if (this.f585h == null) {
                Log.d("g1.b", "Protocol error: Previous continuous frame sequence not completed.");
                throw new C0340c(PointerIconCompat.TYPE_HAND, "Continuous frame sequence was not started.");
            }
            ByteBuffer mo942a2 = abstractC0367d.mo942a();
            synchronized (this.f586i) {
                this.f586i.add(mo942a2);
            }
            m836g();
            AbstractC0367d abstractC0367d2 = this.f585h;
            int i4 = abstractC0367d2.f725b;
            try {
            } catch (RuntimeException e4) {
                Log.d("g1.b", "Runtime exception during onWebsocketMessage", e4);
                c0275d.f464c.mo757l(c0275d, e4);
            }
            if (i4 == 2) {
                abstractC0367d2.mo943c(m838j());
                this.f585h.mo941b();
                c0275d.f464c.mo759n(c0275d, AbstractC0447a.m1183b(this.f585h.mo942a()));
            } else {
                if (i4 == 3) {
                    abstractC0367d2.mo943c(m838j());
                    this.f585h.mo941b();
                    AbstractC0274c abstractC0274c2 = c0275d.f464c;
                    this.f585h.mo942a();
                    abstractC0274c2.mo758m();
                }
                this.f585h = null;
                synchronized (this.f586i) {
                    this.f586i.clear();
                }
            }
            this.f585h = null;
            synchronized (this.f586i) {
            }
        } else if (this.f585h == null) {
            Log.d("g1.b", "Protocol error: Continuous frame sequence was not started.");
            throw new C0340c(PointerIconCompat.TYPE_HAND, "Continuous frame sequence was not started.");
        }
        if (i3 == 2 && !AbstractC0447a.m1182a(abstractC0367d.mo942a())) {
            Log.d("g1.b", "Protocol error: Payload is not UTF8");
            throw new C0340c(PointerIconCompat.TYPE_CROSSHAIR);
        }
        if (i3 != 1 || this.f585h == null) {
            return;
        }
        ByteBuffer mo942a3 = abstractC0367d.mo942a();
        synchronized (this.f586i) {
            this.f586i.add(mo942a3);
        }
    }

    /* renamed from: n */
    public final List m841n(ByteBuffer byteBuffer) {
        LinkedList linkedList;
        while (true) {
            linkedList = new LinkedList();
            if (this.f587j == null) {
                break;
            }
            try {
                byteBuffer.mark();
                int remaining = byteBuffer.remaining();
                int remaining2 = this.f587j.remaining();
                if (remaining2 > remaining) {
                    this.f587j.put(byteBuffer.array(), byteBuffer.position(), remaining);
                    byteBuffer.position(byteBuffer.position() + remaining);
                    return Collections.emptyList();
                }
                this.f587j.put(byteBuffer.array(), byteBuffer.position(), remaining2);
                byteBuffer.position(byteBuffer.position() + remaining2);
                linkedList.add(m842o((ByteBuffer) this.f587j.duplicate().position(0)));
                this.f587j = null;
            } catch (C0338a e2) {
                int i2 = e2.f653a;
                if (i2 < 0) {
                    throw new C0340c(PointerIconCompat.TYPE_HAND, "Negative count");
                }
                ByteBuffer allocate = ByteBuffer.allocate(i2);
                this.f587j.rewind();
                allocate.put(this.f587j);
                this.f587j = allocate;
            }
        }
        while (byteBuffer.hasRemaining()) {
            byteBuffer.mark();
            try {
                linkedList.add(m842o(byteBuffer));
            } catch (C0338a e3) {
                byteBuffer.reset();
                int i3 = e3.f653a;
                if (i3 < 0) {
                    throw new C0340c(PointerIconCompat.TYPE_HAND, "Negative count");
                }
                ByteBuffer allocate2 = ByteBuffer.allocate(i3);
                this.f587j = allocate2;
                allocate2.put(byteBuffer);
            }
        }
        return linkedList;
    }

    /* renamed from: o */
    public final AbstractC0366c m842o(ByteBuffer byteBuffer) {
        int i2;
        int i3;
        int i4;
        AbstractC0366c c0364a;
        if (byteBuffer == null) {
            throw new IllegalArgumentException();
        }
        int remaining = byteBuffer.remaining();
        m833q(remaining, 2);
        byte b = byteBuffer.get();
        boolean z2 = (b >> 8) != 0;
        boolean z3 = (b & DerValue.TAG_APPLICATION) != 0;
        boolean z4 = (b & 32) != 0;
        boolean z5 = (b & Tnaf.POW_2_WIDTH) != 0;
        byte b2 = byteBuffer.get();
        boolean z6 = (b2 & DerValue.TAG_CONTEXT) != 0;
        byte b3 = (byte) (b2 & Byte.MAX_VALUE);
        byte b4 = (byte) (b & 15);
        if (b4 == 0) {
            i2 = 1;
        } else if (b4 == 1) {
            i2 = 2;
        } else if (b4 != 2) {
            switch (b4) {
                case 8:
                    i2 = 6;
                    break;
                case 9:
                    i2 = 4;
                    break;
                case 10:
                    i2 = 5;
                    break;
                default:
                    throw new C0341d("Unknown opcode " + ((int) b4));
            }
        } else {
            i2 = 3;
        }
        if (b3 >= 0 && b3 <= 125) {
            i3 = b3;
            i4 = 2;
        } else {
            if (i2 == 4 || i2 == 5 || i2 == 6) {
                Log.d("g1.b", "Invalid frame: more than 125 octets");
                throw new C0341d("more than 125 octets");
            }
            if (b3 == 126) {
                m833q(remaining, 4);
                i3 = new BigInteger(new byte[]{0, byteBuffer.get(), byteBuffer.get()}).intValue();
                i4 = 4;
            } else {
                m833q(remaining, 10);
                byte[] bArr = new byte[8];
                for (int i5 = 0; i5 < 8; i5++) {
                    bArr[i5] = byteBuffer.get();
                }
                long longValue = new BigInteger(bArr).longValue();
                m843p(longValue);
                i3 = (int) longValue;
                i4 = 10;
            }
        }
        m843p(i3);
        m833q(remaining, i4 + (z6 ? 4 : 0) + i3);
        if (i3 < 0) {
            throw new C0340c(PointerIconCompat.TYPE_HAND, "Negative count");
        }
        ByteBuffer allocate = ByteBuffer.allocate(i3);
        if (z6) {
            byte[] bArr2 = new byte[4];
            byteBuffer.get(bArr2);
            for (int i6 = 0; i6 < i3; i6++) {
                allocate.put((byte) (byteBuffer.get() ^ bArr2[i6 % 4]));
            }
        } else {
            allocate.put(byteBuffer.array(), byteBuffer.position(), allocate.limit());
            byteBuffer.position(allocate.limit() + byteBuffer.position());
        }
        int m1325a = AbstractC0888a.m1325a(i2);
        if (m1325a == 0) {
            c0364a = new C0364a(1);
        } else if (m1325a == 1) {
            c0364a = new C0364a(2);
        } else if (m1325a == 2) {
            c0364a = new C0364a(0);
        } else if (m1325a == 3) {
            c0364a = new C0368e();
        } else if (m1325a == 4) {
            c0364a = new C0369f();
        } else {
            if (m1325a != 5) {
                throw new IllegalArgumentException("Supplied opcode is invalid");
            }
            c0364a = new C0365b();
        }
        c0364a.f724a = z2;
        c0364a.f728e = z3;
        c0364a.f729f = z4;
        c0364a.f730g = z5;
        allocate.flip();
        c0364a.mo943c(allocate);
        int i7 = c0364a.f725b;
        C0355a c0355a = this.f580c;
        if (i7 != 1) {
            if (c0364a.f728e || c0364a.f729f || c0364a.f730g) {
                this.f582e = this.f579b;
            } else {
                this.f582e = c0355a;
            }
        }
        if (this.f582e == null) {
            this.f582e = c0355a;
        }
        this.f582e.getClass();
        if (!c0364a.f728e && !c0364a.f729f && !c0364a.f730g) {
            this.f582e.getClass();
            c0364a.mo941b();
            return c0364a;
        }
        throw new C0341d("bad rsv RSV1: " + c0364a.f728e + " RSV2: " + c0364a.f729f + " RSV3: " + c0364a.f730g);
    }

    /* renamed from: p */
    public final void m843p(long j2) {
        if (j2 > 2147483647L) {
            Log.d("g1.b", "Limit exedeed: Payloadsize is to big...");
            throw new C0343f("Payloadsize is to big...");
        }
        int i2 = this.f589l;
        if (j2 > i2) {
            Log.d("g1.b", String.format("Payload limit reached. Allowed: %d Current: %d", Integer.valueOf(i2), Long.valueOf(j2)));
            throw new C0343f("Payload limit reached.", i2);
        }
        if (j2 >= 0) {
            return;
        }
        Log.d("g1.b", "Limit underflow: Payloadsize is to little...");
        throw new C0343f("Payloadsize is to little...");
    }

    @Override // g1.AbstractC0312a
    public final String toString() {
        String abstractC0312a = super.toString();
        if (this.f579b != null) {
            StringBuilder m22r = AbstractC0000a.m22r(abstractC0312a, " extension: ");
            m22r.append(this.f579b.toString());
            abstractC0312a = m22r.toString();
        }
        if (this.f583f != null) {
            StringBuilder m22r2 = AbstractC0000a.m22r(abstractC0312a, " protocol: ");
            m22r2.append(((C0403b) this.f583f).f807a);
            abstractC0312a = m22r2.toString();
        }
        StringBuilder m22r3 = AbstractC0000a.m22r(abstractC0312a, " max frame size: ");
        m22r3.append(this.f589l);
        return m22r3.toString();
    }

    public C0313b(int i2, List list, List list2) {
        this.f579b = new C0355a();
        this.f580c = new C0355a();
        this.f588k = new SecureRandom();
        if (list == null || list2 == null || i2 < 1) {
            throw new IllegalArgumentException();
        }
        this.f581d = new ArrayList(list.size());
        this.f584g = new ArrayList(list2.size());
        this.f586i = new ArrayList();
        Iterator it = list.iterator();
        boolean z2 = false;
        while (it.hasNext()) {
            if (((C0355a) it.next()).getClass().equals(C0355a.class)) {
                z2 = true;
            }
        }
        this.f581d.addAll(list);
        if (!z2) {
            ArrayList arrayList = this.f581d;
            arrayList.add(arrayList.size(), this.f579b);
        }
        this.f584g.addAll(list2);
        this.f589l = i2;
        this.f582e = null;
    }
}
