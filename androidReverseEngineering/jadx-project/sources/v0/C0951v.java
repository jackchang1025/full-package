package v0;

import a1.C0014e;
import a1.C0017h;
import a1.C0024o;
import a1.InterfaceC0016g;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.RejectedExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bouncycastle.asn1.eac.CertificateBody;
import p022z.C0981d;
import q0.AbstractC0887c;

/* renamed from: v0.v */
/* loaded from: classes.dex */
public final class C0951v implements Closeable {

    /* renamed from: e */
    public static final Logger f2237e = Logger.getLogger(AbstractC0936g.class.getName());

    /* renamed from: a */
    public final InterfaceC0016g f2238a;

    /* renamed from: b */
    public final C0950u f2239b;

    /* renamed from: c */
    public final boolean f2240c;

    /* renamed from: d */
    public final C0933d f2241d;

    public C0951v(InterfaceC0016g interfaceC0016g, boolean z2) {
        this.f2238a = interfaceC0016g;
        this.f2240c = z2;
        C0950u c0950u = new C0950u(interfaceC0016g);
        this.f2239b = c0950u;
        this.f2241d = new C0933d(c0950u);
    }

    /* renamed from: x */
    public static int m1418x(int i2, byte b, short s2) {
        if ((b & 8) != 0) {
            i2--;
        }
        if (s2 <= i2) {
            return (short) (i2 - s2);
        }
        AbstractC0936g.m1407b(new Object[]{Short.valueOf(s2), Integer.valueOf(i2)}, "PROTOCOL_ERROR padding %s > remaining length %s");
        throw null;
    }

    /* renamed from: A */
    public final void m1419A(C0946q c0946q, int i2, int i3) {
        EnumC0931b enumC0931b;
        C0954y[] c0954yArr;
        if (i2 < 8) {
            AbstractC0936g.m1407b(new Object[]{Integer.valueOf(i2)}, "TYPE_GOAWAY length < 8: %s");
            throw null;
        }
        if (i3 != 0) {
            AbstractC0936g.m1407b(new Object[0], "TYPE_GOAWAY streamId != 0");
            throw null;
        }
        int readInt = this.f2238a.readInt();
        int readInt2 = this.f2238a.readInt();
        int i4 = i2 - 8;
        EnumC0931b[] values = EnumC0931b.values();
        int length = values.length;
        int i5 = 0;
        while (true) {
            if (i5 >= length) {
                enumC0931b = null;
                break;
            }
            enumC0931b = values[i5];
            if (enumC0931b.f2129a == readInt2) {
                break;
            } else {
                i5++;
            }
        }
        if (enumC0931b == null) {
            AbstractC0936g.m1407b(new Object[]{Integer.valueOf(readInt2)}, "TYPE_GOAWAY unexpected error code: %d");
            throw null;
        }
        C0017h c0017h = C0017h.f24e;
        if (i4 > 0) {
            c0017h = this.f2238a.mo99h(i4);
        }
        c0946q.getClass();
        c0017h.mo125j();
        synchronized (((C0948s) c0946q.f2195d)) {
            c0954yArr = (C0954y[]) ((C0948s) c0946q.f2195d).f2202c.values().toArray(new C0954y[((C0948s) c0946q.f2195d).f2202c.size()]);
            ((C0948s) c0946q.f2195d).f2206g = true;
        }
        for (C0954y c0954y : c0954yArr) {
            if (c0954y.f2254c > readInt && c0954y.m1431f()) {
                EnumC0931b enumC0931b2 = EnumC0931b.REFUSED_STREAM;
                synchronized (c0954y) {
                    if (c0954y.f2262k == null) {
                        c0954y.f2262k = enumC0931b2;
                        c0954y.notifyAll();
                    }
                }
                ((C0948s) c0946q.f2195d).m1409B(c0954y.f2254c);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x00e9, code lost:
    
        throw new java.io.IOException("Invalid dynamic table size update " + r3.f2147d);
     */
    /* renamed from: B */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final ArrayList m1420B(int i2, short s2, byte b, int i3) {
        int m1399e;
        C0932c c0932c;
        C0950u c0950u = this.f2239b;
        c0950u.f2235e = i2;
        c0950u.f2232b = i2;
        c0950u.f2236f = s2;
        c0950u.f2233c = b;
        c0950u.f2234d = i3;
        while (true) {
            C0933d c0933d = this.f2241d;
            C0024o c0024o = c0933d.f2145b;
            boolean mo104n = c0024o.mo104n();
            ArrayList arrayList = c0933d.f2144a;
            if (mo104n) {
                ArrayList arrayList2 = new ArrayList(arrayList);
                arrayList.clear();
                return arrayList2;
            }
            int readByte = c0024o.readByte() & 255;
            if (readByte == 128) {
                throw new IOException("index == 0");
            }
            boolean z2 = false;
            if ((readByte & 128) == 128) {
                m1399e = c0933d.m1399e(readByte, CertificateBody.profileType) - 1;
                if (m1399e >= 0 && m1399e <= AbstractC0935f.f2161a.length - 1) {
                    z2 = true;
                }
                if (!z2) {
                    int length = c0933d.f2149f + 1 + (m1399e - AbstractC0935f.f2161a.length);
                    if (length < 0) {
                        break;
                    }
                    C0932c[] c0932cArr = c0933d.f2148e;
                    if (length >= c0932cArr.length) {
                        break;
                    }
                    c0932c = c0932cArr[length];
                } else {
                    c0932c = AbstractC0935f.f2161a[m1399e];
                }
                arrayList.add(c0932c);
            } else if (readByte == 64) {
                C0017h m1398d = c0933d.m1398d();
                AbstractC0935f.m1405a(m1398d);
                c0933d.m1397c(new C0932c(m1398d, c0933d.m1398d()));
            } else if ((readByte & 64) == 64) {
                c0933d.m1397c(new C0932c(c0933d.m1396b(c0933d.m1399e(readByte, 63) - 1), c0933d.m1398d()));
            } else if ((readByte & 32) == 32) {
                int m1399e2 = c0933d.m1399e(readByte, 31);
                c0933d.f2147d = m1399e2;
                if (m1399e2 < 0 || m1399e2 > c0933d.f2146c) {
                    break;
                }
                int i4 = c0933d.f2151h;
                if (m1399e2 < i4) {
                    if (m1399e2 == 0) {
                        Arrays.fill(c0933d.f2148e, (Object) null);
                        c0933d.f2149f = c0933d.f2148e.length - 1;
                        c0933d.f2150g = 0;
                        c0933d.f2151h = 0;
                    } else {
                        c0933d.m1395a(i4 - m1399e2);
                    }
                }
            } else if (readByte == 16 || readByte == 0) {
                C0017h m1398d2 = c0933d.m1398d();
                AbstractC0935f.m1405a(m1398d2);
                arrayList.add(new C0932c(m1398d2, c0933d.m1398d()));
            } else {
                arrayList.add(new C0932c(c0933d.m1396b(c0933d.m1399e(readByte, 15) - 1), c0933d.m1398d()));
            }
        }
        throw new IOException("Header index too large " + (m1399e + 1));
    }

    /* renamed from: C */
    public final void m1421C(C0946q c0946q, int i2, byte b, int i3) {
        if (i2 != 8) {
            AbstractC0936g.m1407b(new Object[]{Integer.valueOf(i2)}, "TYPE_PING length != 8: %s");
            throw null;
        }
        if (i3 != 0) {
            AbstractC0936g.m1407b(new Object[0], "TYPE_PING streamId != 0");
            throw null;
        }
        int readInt = this.f2238a.readInt();
        int readInt2 = this.f2238a.readInt();
        boolean z2 = (b & 1) != 0;
        c0946q.getClass();
        if (!z2) {
            try {
                Object obj = c0946q.f2195d;
                ((C0948s) obj).f2207h.execute(new C0945p((C0948s) obj, readInt, readInt2));
                return;
            } catch (RejectedExecutionException unused) {
                return;
            }
        }
        synchronized (((C0948s) c0946q.f2195d)) {
            try {
                if (readInt == 1) {
                    ((C0948s) c0946q.f2195d).f2211l++;
                } else if (readInt == 2) {
                    ((C0948s) c0946q.f2195d).f2213n++;
                } else if (readInt == 3) {
                    Object obj2 = c0946q.f2195d;
                    ((C0948s) obj2).getClass();
                    ((C0948s) obj2).notifyAll();
                }
            } finally {
            }
        }
    }

    /* renamed from: D */
    public final void m1422D(C0946q c0946q, int i2, int i3) {
        if (i2 != 4) {
            AbstractC0936g.m1407b(new Object[]{Integer.valueOf(i2)}, "TYPE_WINDOW_UPDATE length !=4: %s");
            throw null;
        }
        long readInt = this.f2238a.readInt() & 2147483647L;
        if (readInt == 0) {
            AbstractC0936g.m1407b(new Object[]{Long.valueOf(readInt)}, "windowSizeIncrement was 0");
            throw null;
        }
        if (i3 == 0) {
            synchronized (((C0948s) c0946q.f2195d)) {
                Object obj = c0946q.f2195d;
                ((C0948s) obj).f2216q += readInt;
                ((C0948s) obj).notifyAll();
            }
            return;
        }
        C0954y m1417z = ((C0948s) c0946q.f2195d).m1417z(i3);
        if (m1417z != null) {
            synchronized (m1417z) {
                m1417z.f2253b += readInt;
                if (readInt > 0) {
                    m1417z.notifyAll();
                }
            }
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        this.f2238a.close();
    }

    /* renamed from: y */
    public final boolean m1423y(boolean z2, C0946q c0946q) {
        short s2;
        boolean z3;
        boolean z4;
        long j2;
        EnumC0931b enumC0931b;
        try {
            this.f2238a.mo108r(9L);
            InterfaceC0016g interfaceC0016g = this.f2238a;
            int readByte = (interfaceC0016g.readByte() & 255) | ((interfaceC0016g.readByte() & 255) << 16) | ((interfaceC0016g.readByte() & 255) << 8);
            if (readByte < 0 || readByte > 16384) {
                AbstractC0936g.m1407b(new Object[]{Integer.valueOf(readByte)}, "FRAME_SIZE_ERROR: %s");
                throw null;
            }
            byte readByte2 = (byte) (this.f2238a.readByte() & 255);
            if (z2 && readByte2 != 4) {
                AbstractC0936g.m1407b(new Object[]{Byte.valueOf(readByte2)}, "Expected a SETTINGS frame but was %s");
                throw null;
            }
            byte readByte3 = (byte) (this.f2238a.readByte() & 255);
            int readInt = this.f2238a.readInt() & Integer.MAX_VALUE;
            Logger logger = f2237e;
            if (logger.isLoggable(Level.FINE)) {
                logger.fine(AbstractC0936g.m1406a(true, readInt, readByte, readByte2, readByte3));
            }
            switch (readByte2) {
                case 0:
                    if (readInt == 0) {
                        AbstractC0936g.m1407b(new Object[0], "PROTOCOL_ERROR: TYPE_DATA streamId == 0");
                        throw null;
                    }
                    boolean z5 = (readByte3 & 1) != 0;
                    if ((readByte3 & 32) != 0) {
                        AbstractC0936g.m1407b(new Object[0], "PROTOCOL_ERROR: FLAG_COMPRESSED without SETTINGS_COMPRESS_DATA");
                        throw null;
                    }
                    short readByte4 = (readByte3 & 8) != 0 ? (short) (this.f2238a.readByte() & 255) : (short) 0;
                    int m1418x = m1418x(readByte, readByte3, readByte4);
                    InterfaceC0016g interfaceC0016g2 = this.f2238a;
                    ((C0948s) c0946q.f2195d).getClass();
                    if (readInt != 0 && (readInt & 1) == 0) {
                        C0948s c0948s = (C0948s) c0946q.f2195d;
                        c0948s.getClass();
                        C0014e c0014e = new C0014e();
                        long j3 = m1418x;
                        interfaceC0016g2.mo108r(j3);
                        interfaceC0016g2.mo69u(c0014e, j3);
                        if (c0014e.f22b != j3) {
                            throw new IOException(c0014e.f22b + " != " + m1418x);
                        }
                        c0948s.m1408A(new C0941l(c0948s, new Object[]{c0948s.f2203d, Integer.valueOf(readInt)}, readInt, c0014e, m1418x, z5));
                    } else {
                        C0954y m1417z = ((C0948s) c0946q.f2195d).m1417z(readInt);
                        if (m1417z != null) {
                            C0953x c0953x = m1417z.f2258g;
                            long j4 = m1418x;
                            while (true) {
                                if (j4 > 0) {
                                    synchronized (c0953x.f2251f) {
                                        z3 = c0953x.f2250e;
                                        s2 = readByte4;
                                        z4 = c0953x.f2247b.f22b + j4 > c0953x.f2248c;
                                    }
                                    if (z4) {
                                        interfaceC0016g2.skip(j4);
                                        c0953x.f2251f.m1430e(EnumC0931b.FLOW_CONTROL_ERROR);
                                    } else if (z3) {
                                        interfaceC0016g2.skip(j4);
                                    } else {
                                        long mo69u = interfaceC0016g2.mo69u(c0953x.f2246a, j4);
                                        if (mo69u == -1) {
                                            throw new EOFException();
                                        }
                                        j4 -= mo69u;
                                        synchronized (c0953x.f2251f) {
                                            if (c0953x.f2249d) {
                                                C0014e c0014e2 = c0953x.f2246a;
                                                j2 = c0014e2.f22b;
                                                c0014e2.m113x();
                                            } else {
                                                C0014e c0014e3 = c0953x.f2247b;
                                                boolean z6 = c0014e3.f22b == 0;
                                                c0014e3.mo95d(c0953x.f2246a);
                                                if (z6) {
                                                    c0953x.f2251f.notifyAll();
                                                }
                                                j2 = 0;
                                            }
                                        }
                                        if (j2 > 0) {
                                            c0953x.f2251f.f2255d.m1411D(j2);
                                        }
                                        readByte4 = s2;
                                    }
                                } else {
                                    s2 = readByte4;
                                    c0953x.getClass();
                                }
                            }
                            if (z5) {
                                m1417z.m1433h(AbstractC0887c.f1936c, true);
                            }
                            this.f2238a.skip(s2);
                            return true;
                        }
                        ((C0948s) c0946q.f2195d).m1413F(readInt, EnumC0931b.PROTOCOL_ERROR);
                        long j5 = m1418x;
                        ((C0948s) c0946q.f2195d).m1411D(j5);
                        interfaceC0016g2.skip(j5);
                    }
                    s2 = readByte4;
                    this.f2238a.skip(s2);
                    return true;
                case 1:
                    if (readInt == 0) {
                        AbstractC0936g.m1407b(new Object[0], "PROTOCOL_ERROR: TYPE_HEADERS streamId == 0");
                        throw null;
                    }
                    boolean z7 = (readByte3 & 1) != 0;
                    short readByte5 = (readByte3 & 8) != 0 ? (short) (this.f2238a.readByte() & 255) : (short) 0;
                    if ((readByte3 & 32) != 0) {
                        InterfaceC0016g interfaceC0016g3 = this.f2238a;
                        interfaceC0016g3.readInt();
                        interfaceC0016g3.readByte();
                        c0946q.getClass();
                        readByte -= 5;
                    }
                    ArrayList m1420B = m1420B(m1418x(readByte, readByte3, readByte5), readByte5, readByte3, readInt);
                    ((C0948s) c0946q.f2195d).getClass();
                    if (readInt != 0 && (readInt & 1) == 0) {
                        C0948s c0948s2 = (C0948s) c0946q.f2195d;
                        c0948s2.getClass();
                        c0948s2.m1408A(new C0940k(c0948s2, new Object[]{c0948s2.f2203d, Integer.valueOf(readInt)}, readInt, m1420B, z7));
                        return true;
                    }
                    synchronized (((C0948s) c0946q.f2195d)) {
                        try {
                            C0954y m1417z2 = ((C0948s) c0946q.f2195d).m1417z(readInt);
                            if (m1417z2 == null) {
                                Object obj = c0946q.f2195d;
                                if (!((C0948s) obj).f2206g && readInt > ((C0948s) obj).f2204e && readInt % 2 != ((C0948s) obj).f2205f % 2) {
                                    C0954y c0954y = new C0954y(readInt, (C0948s) c0946q.f2195d, false, z7, AbstractC0887c.m1323t(m1420B));
                                    Object obj2 = c0946q.f2195d;
                                    ((C0948s) obj2).f2204e = readInt;
                                    ((C0948s) obj2).f2202c.put(Integer.valueOf(readInt), c0954y);
                                    C0948s.f2199x.execute(new C0946q(c0946q, new Object[]{((C0948s) c0946q.f2195d).f2203d, Integer.valueOf(readInt)}, c0954y));
                                }
                            } else {
                                m1417z2.m1433h(AbstractC0887c.m1323t(m1420B), z7);
                            }
                        } finally {
                        }
                    }
                    return true;
                case 2:
                    if (readByte != 5) {
                        AbstractC0936g.m1407b(new Object[]{Integer.valueOf(readByte)}, "TYPE_PRIORITY length: %d != 5");
                        throw null;
                    }
                    if (readInt == 0) {
                        AbstractC0936g.m1407b(new Object[0], "TYPE_PRIORITY streamId == 0");
                        throw null;
                    }
                    InterfaceC0016g interfaceC0016g4 = this.f2238a;
                    interfaceC0016g4.readInt();
                    interfaceC0016g4.readByte();
                    c0946q.getClass();
                    return true;
                case 3:
                    if (readByte != 4) {
                        AbstractC0936g.m1407b(new Object[]{Integer.valueOf(readByte)}, "TYPE_RST_STREAM length: %d != 4");
                        throw null;
                    }
                    if (readInt == 0) {
                        AbstractC0936g.m1407b(new Object[0], "TYPE_RST_STREAM streamId == 0");
                        throw null;
                    }
                    int readInt2 = this.f2238a.readInt();
                    EnumC0931b[] values = EnumC0931b.values();
                    int length = values.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 < length) {
                            enumC0931b = values[i2];
                            if (enumC0931b.f2129a != readInt2) {
                                i2++;
                            }
                        } else {
                            enumC0931b = null;
                        }
                    }
                    if (enumC0931b == null) {
                        AbstractC0936g.m1407b(new Object[]{Integer.valueOf(readInt2)}, "TYPE_RST_STREAM unexpected error code: %d");
                        throw null;
                    }
                    C0948s c0948s3 = (C0948s) c0946q.f2195d;
                    c0948s3.getClass();
                    if (readInt != 0 && (readInt & 1) == 0) {
                        c0948s3.m1408A(new C0937h(c0948s3, "OkHttp %s Push Reset[%s]", new Object[]{c0948s3.f2203d, Integer.valueOf(readInt)}, readInt, enumC0931b, 1));
                    } else {
                        C0954y m1409B = c0948s3.m1409B(readInt);
                        if (m1409B != null) {
                            synchronized (m1409B) {
                                if (m1409B.f2262k == null) {
                                    m1409B.f2262k = enumC0931b;
                                    m1409B.notifyAll();
                                }
                            }
                        }
                    }
                    return true;
                case 4:
                    if (readInt != 0) {
                        AbstractC0936g.m1407b(new Object[0], "TYPE_SETTINGS streamId != 0");
                        throw null;
                    }
                    if ((readByte3 & 1) != 0) {
                        if (readByte == 0) {
                            c0946q.getClass();
                            return true;
                        }
                        AbstractC0936g.m1407b(new Object[0], "FRAME_SIZE_ERROR ack frame should be empty!");
                        throw null;
                    }
                    if (readByte % 6 != 0) {
                        AbstractC0936g.m1407b(new Object[]{Integer.valueOf(readByte)}, "TYPE_SETTINGS length %% 6 != 0: %s");
                        throw null;
                    }
                    C0981d c0981d = new C0981d();
                    for (int i3 = 0; i3 < readByte; i3 += 6) {
                        InterfaceC0016g interfaceC0016g5 = this.f2238a;
                        int readShort = interfaceC0016g5.readShort() & 65535;
                        int readInt3 = interfaceC0016g5.readInt();
                        if (readShort != 2) {
                            if (readShort == 3) {
                                readShort = 4;
                            } else if (readShort == 4) {
                                if (readInt3 < 0) {
                                    AbstractC0936g.m1407b(new Object[0], "PROTOCOL_ERROR SETTINGS_INITIAL_WINDOW_SIZE > 2^31 - 1");
                                    throw null;
                                }
                                readShort = 7;
                            } else if (readShort == 5 && (readInt3 < 16384 || readInt3 > 16777215)) {
                                AbstractC0936g.m1407b(new Object[]{Integer.valueOf(readInt3)}, "PROTOCOL_ERROR SETTINGS_MAX_FRAME_SIZE: %s");
                                throw null;
                            }
                        } else if (readInt3 != 0 && readInt3 != 1) {
                            AbstractC0936g.m1407b(new Object[0], "PROTOCOL_ERROR SETTINGS_ENABLE_PUSH != 0 or 1");
                            throw null;
                        }
                        c0981d.m1474e(readShort, readInt3);
                    }
                    c0946q.getClass();
                    Object obj3 = c0946q.f2195d;
                    ((C0948s) obj3).f2207h.execute(new C0947r(c0946q, new Object[]{((C0948s) obj3).f2203d}, c0981d));
                    return true;
                case 5:
                    if (readInt == 0) {
                        AbstractC0936g.m1407b(new Object[0], "PROTOCOL_ERROR: TYPE_PUSH_PROMISE streamId == 0");
                        throw null;
                    }
                    short readByte6 = (readByte3 & 8) != 0 ? (short) (this.f2238a.readByte() & 255) : (short) 0;
                    int readInt4 = this.f2238a.readInt() & Integer.MAX_VALUE;
                    ArrayList m1420B2 = m1420B(m1418x(readByte - 4, readByte3, readByte6), readByte6, readByte3, readInt);
                    C0948s c0948s4 = (C0948s) c0946q.f2195d;
                    synchronized (c0948s4) {
                        if (c0948s4.f2222w.contains(Integer.valueOf(readInt4))) {
                            c0948s4.m1413F(readInt4, EnumC0931b.PROTOCOL_ERROR);
                        } else {
                            c0948s4.f2222w.add(Integer.valueOf(readInt4));
                            c0948s4.m1408A(new C0937h(c0948s4, "OkHttp %s Push Request[%s]", new Object[]{c0948s4.f2203d, Integer.valueOf(readInt4)}, readInt4, m1420B2, 2));
                        }
                    }
                    return true;
                case 6:
                    m1421C(c0946q, readByte, readByte3, readInt);
                    return true;
                case 7:
                    m1419A(c0946q, readByte, readInt);
                    return true;
                case 8:
                    m1422D(c0946q, readByte, readInt);
                    return true;
                default:
                    this.f2238a.skip(readByte);
                    return true;
            }
        } catch (EOFException unused) {
            return false;
        }
    }

    /* renamed from: z */
    public final void m1424z(C0946q c0946q) {
        if (this.f2240c) {
            if (m1423y(true, c0946q)) {
                return;
            }
            AbstractC0936g.m1407b(new Object[0], "Required SETTINGS preface not received");
            throw null;
        }
        C0017h c0017h = AbstractC0936g.f2163a;
        C0017h mo99h = this.f2238a.mo99h(c0017h.f25a.length);
        Level level = Level.FINE;
        Logger logger = f2237e;
        if (logger.isLoggable(level)) {
            logger.fine(String.format("<< CONNECTION %s", mo99h.mo122f()));
        }
        if (c0017h.equals(mo99h)) {
            return;
        }
        AbstractC0936g.m1407b(new Object[]{mo99h.mo128m()}, "Expected a connection header but was %s");
        throw null;
    }
}
