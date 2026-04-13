package f0;

import android.os.Looper;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import n0.AbstractC0408d;
import n0.C0406b;
import n0.C0407c;
import p011n.C0404a;

/* renamed from: f0.m */
/* loaded from: classes.dex */
public final class C0292m {

    /* renamed from: d */
    public static final PriorityQueue f532d = new PriorityQueue(8, new C0404a(3));

    /* renamed from: e */
    public static final int f533e = 1048576;

    /* renamed from: f */
    public static final int f534f = 262144;

    /* renamed from: g */
    public static int f535g = 0;

    /* renamed from: h */
    public static int f536h = 0;

    /* renamed from: i */
    public static final Object f537i = new Object();

    /* renamed from: j */
    public static final ByteBuffer f538j = ByteBuffer.allocate(0);

    /* renamed from: a */
    public final C0407c f539a = new C0407c();

    /* renamed from: b */
    public ByteOrder f540b = ByteOrder.BIG_ENDIAN;

    /* renamed from: c */
    public int f541c = 0;

    public C0292m() {
    }

    /* renamed from: g */
    public static ByteBuffer m801g(int i2) {
        if (i2 <= f536h) {
            Looper mainLooper = Looper.getMainLooper();
            PriorityQueue priorityQueue = (mainLooper == null || Thread.currentThread() != mainLooper.getThread()) ? f532d : null;
            if (priorityQueue != null) {
                synchronized (f537i) {
                    while (priorityQueue.size() > 0) {
                        ByteBuffer byteBuffer = (ByteBuffer) priorityQueue.remove();
                        if (priorityQueue.size() == 0) {
                            f536h = 0;
                        }
                        f535g -= byteBuffer.capacity();
                        if (byteBuffer.capacity() >= i2) {
                            return byteBuffer;
                        }
                    }
                }
            }
        }
        return ByteBuffer.allocate(Math.max(8192, i2));
    }

    /* renamed from: j */
    public static void m802j(ByteBuffer byteBuffer) {
        int i2;
        if (byteBuffer == null || byteBuffer.isDirect() || byteBuffer.arrayOffset() != 0 || byteBuffer.array().length != byteBuffer.capacity() || byteBuffer.capacity() < 8192 || byteBuffer.capacity() > f534f) {
            return;
        }
        Looper mainLooper = Looper.getMainLooper();
        PriorityQueue priorityQueue = (mainLooper == null || Thread.currentThread() != mainLooper.getThread()) ? f532d : null;
        if (priorityQueue == null) {
            return;
        }
        synchronized (f537i) {
            while (true) {
                int i3 = f535g;
                i2 = f533e;
                if (i3 <= i2 || priorityQueue.size() <= 0 || ((ByteBuffer) priorityQueue.peek()).capacity() >= byteBuffer.capacity()) {
                    break;
                } else {
                    f535g -= ((ByteBuffer) priorityQueue.remove()).capacity();
                }
            }
            if (f535g > i2) {
                return;
            }
            byteBuffer.position(0);
            byteBuffer.limit(byteBuffer.capacity());
            f535g += byteBuffer.capacity();
            priorityQueue.add(byteBuffer);
            f536h = Math.max(f536h, byteBuffer.capacity());
        }
    }

    /* renamed from: a */
    public final void m803a(ByteBuffer byteBuffer) {
        if (byteBuffer.remaining() <= 0) {
            m802j(byteBuffer);
            return;
        }
        int remaining = byteBuffer.remaining();
        int i2 = this.f541c;
        if (i2 >= 0) {
            this.f541c = i2 + remaining;
        }
        C0407c c0407c = this.f539a;
        if (c0407c.size() > 0) {
            Object obj = c0407c.f815a[(c0407c.f817c - 1) & (r1.length - 1)];
            if (obj == null) {
                throw new NoSuchElementException();
            }
            ByteBuffer byteBuffer2 = (ByteBuffer) obj;
            if (byteBuffer2.capacity() - byteBuffer2.limit() >= byteBuffer.remaining()) {
                byteBuffer2.mark();
                byteBuffer2.position(byteBuffer2.limit());
                byteBuffer2.limit(byteBuffer2.capacity());
                byteBuffer2.put(byteBuffer);
                byteBuffer2.limit(byteBuffer2.position());
                byteBuffer2.reset();
                m802j(byteBuffer);
                m810i(0);
            }
        }
        c0407c.addLast(byteBuffer);
        m810i(0);
    }

    /* renamed from: b */
    public final void m804b(ByteBuffer byteBuffer) {
        if (byteBuffer.remaining() <= 0) {
            m802j(byteBuffer);
            return;
        }
        int remaining = byteBuffer.remaining();
        int i2 = this.f541c;
        if (i2 >= 0) {
            this.f541c = i2 + remaining;
        }
        C0407c c0407c = this.f539a;
        if (c0407c.size() > 0) {
            Object obj = c0407c.f815a[c0407c.f816b];
            if (obj == null) {
                throw new NoSuchElementException();
            }
            ByteBuffer byteBuffer2 = (ByteBuffer) obj;
            if (byteBuffer2.position() >= byteBuffer.remaining()) {
                byteBuffer2.position(byteBuffer2.position() - byteBuffer.remaining());
                byteBuffer2.mark();
                byteBuffer2.put(byteBuffer);
                byteBuffer2.reset();
                m802j(byteBuffer);
                return;
            }
        }
        c0407c.addFirst(byteBuffer);
    }

    /* renamed from: c */
    public final void m805c(C0292m c0292m) {
        m806d(c0292m, this.f541c);
    }

    /* renamed from: d */
    public final void m806d(C0292m c0292m, int i2) {
        if (this.f541c < i2) {
            throw new IllegalArgumentException("length");
        }
        int i3 = 0;
        while (true) {
            if (i3 >= i2) {
                break;
            }
            C0407c c0407c = this.f539a;
            ByteBuffer byteBuffer = (ByteBuffer) c0407c.remove();
            int remaining = byteBuffer.remaining();
            if (remaining == 0) {
                m802j(byteBuffer);
            } else {
                int i4 = remaining + i3;
                if (i4 > i2) {
                    int i5 = i2 - i3;
                    ByteBuffer m801g = m801g(i5);
                    m801g.limit(i5);
                    byteBuffer.get(m801g.array(), 0, i5);
                    c0292m.m803a(m801g);
                    c0407c.addFirst(byteBuffer);
                    break;
                }
                c0292m.m803a(byteBuffer);
                i3 = i4;
            }
        }
        this.f541c -= i2;
    }

    /* renamed from: e */
    public final void m807e(byte[] bArr) {
        int length = bArr.length;
        if (this.f541c < length) {
            throw new IllegalArgumentException("length");
        }
        int i2 = 0;
        int i3 = length;
        while (i3 > 0) {
            C0407c c0407c = this.f539a;
            ByteBuffer byteBuffer = (ByteBuffer) c0407c.peek();
            int min = Math.min(byteBuffer.remaining(), i3);
            byteBuffer.get(bArr, i2, min);
            i3 -= min;
            i2 += min;
            if (byteBuffer.remaining() == 0) {
                m802j(byteBuffer);
            }
        }
        this.f541c -= length;
    }

    /* renamed from: f */
    public final char m808f() {
        char c = (char) m810i(1).get();
        this.f541c--;
        return c;
    }

    /* renamed from: h */
    public final String m809h(Charset charset) {
        byte[] array;
        int remaining;
        int i2;
        if (charset == null) {
            charset = AbstractC0408d.f818a;
        }
        StringBuilder sb = new StringBuilder();
        Iterator it = this.f539a.iterator();
        while (true) {
            C0406b c0406b = (C0406b) it;
            if (!c0406b.hasNext()) {
                return sb.toString();
            }
            ByteBuffer byteBuffer = (ByteBuffer) c0406b.next();
            if (byteBuffer.isDirect()) {
                array = new byte[byteBuffer.remaining()];
                remaining = byteBuffer.remaining();
                byteBuffer.get(array);
                i2 = 0;
            } else {
                array = byteBuffer.array();
                int arrayOffset = byteBuffer.arrayOffset() + byteBuffer.position();
                remaining = byteBuffer.remaining();
                i2 = arrayOffset;
            }
            sb.append(new String(array, i2, remaining, charset));
        }
    }

    /* renamed from: i */
    public final ByteBuffer m810i(int i2) {
        ByteBuffer byteBuffer;
        ByteBuffer byteBuffer2;
        if (this.f541c < i2) {
            throw new IllegalArgumentException("count : " + this.f541c + "/" + i2);
        }
        C0407c c0407c = this.f539a;
        while (true) {
            byteBuffer = (ByteBuffer) c0407c.peek();
            if (byteBuffer == null || byteBuffer.hasRemaining()) {
                break;
            }
            m802j((ByteBuffer) c0407c.remove());
        }
        if (byteBuffer == null) {
            return f538j;
        }
        if (byteBuffer.remaining() < i2) {
            byteBuffer = m801g(i2);
            byteBuffer.limit(i2);
            byte[] array = byteBuffer.array();
            int i3 = 0;
            loop1: while (true) {
                byteBuffer2 = null;
                while (i3 < i2) {
                    byteBuffer2 = (ByteBuffer) c0407c.remove();
                    int min = Math.min(i2 - i3, byteBuffer2.remaining());
                    byteBuffer2.get(array, i3, min);
                    i3 += min;
                    if (byteBuffer2.remaining() == 0) {
                        break;
                    }
                }
                m802j(byteBuffer2);
            }
            if (byteBuffer2 != null && byteBuffer2.remaining() > 0) {
                c0407c.addFirst(byteBuffer2);
            }
            c0407c.addFirst(byteBuffer);
        }
        return byteBuffer.order(this.f540b);
    }

    /* renamed from: k */
    public final void m811k() {
        while (true) {
            C0407c c0407c = this.f539a;
            if (c0407c.size() <= 0) {
                this.f541c = 0;
                return;
            }
            m802j((ByteBuffer) c0407c.remove());
        }
    }

    /* renamed from: l */
    public final ByteBuffer m812l() {
        ByteBuffer byteBuffer = (ByteBuffer) this.f539a.remove();
        this.f541c -= byteBuffer.remaining();
        return byteBuffer;
    }

    public C0292m(byte[] bArr) {
        m803a(ByteBuffer.wrap(bArr));
    }
}
