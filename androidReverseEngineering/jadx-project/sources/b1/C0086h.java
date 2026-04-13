package b1;

import a1.AbstractC0026q;
import android.util.Log;
import com.guard.wallet.thread.C0235d;
import java.io.Closeable;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import com.guard.wallet.entity.BuildConfig;
import p006i.C0328a;
import p006i.InterfaceC0329b;

/* renamed from: b1.h */
/* loaded from: classes.dex */
public final class C0086h implements Closeable {

    /* renamed from: a */
    public final C0082d f130a;

    /* renamed from: b */
    public final int f131b;

    /* renamed from: c */
    public volatile int f132c;

    /* renamed from: d */
    public final AtomicBoolean f133d;

    /* renamed from: f */
    public final ByteBuffer f135f;

    /* renamed from: g */
    public volatile boolean f136g;

    /* renamed from: h */
    public Timer f137h;

    /* renamed from: i */
    public final LinkedList f138i = new LinkedList();

    /* renamed from: j */
    public final LinkedList f139j = new LinkedList();

    /* renamed from: k */
    public final AtomicInteger f140k = new AtomicInteger(-1);

    /* renamed from: e */
    public final ConcurrentLinkedQueue f134e = new ConcurrentLinkedQueue();

    public C0086h(C0082d c0082d, int i2) {
        this.f130a = c0082d;
        this.f131b = i2;
        if (!c0082d.f109k) {
            throw new IllegalStateException("connect() must be called first");
        }
        c0082d.m309B(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        this.f135f = (ByteBuffer) ByteBuffer.allocate(c0082d.f113o).flip();
        this.f133d = new AtomicBoolean(false);
        this.f136g = false;
    }

    /* renamed from: A */
    public final void m316A(boolean z2) {
        if (!z2 || this.f134e.isEmpty()) {
            this.f136g = true;
        }
        m319y();
        synchronized (this) {
            notifyAll();
        }
        synchronized (this.f134e) {
            this.f134e.notifyAll();
        }
    }

    /* renamed from: B */
    public final void m317B(long j2) {
        if (j2 > 0) {
            C0235d c0235d = new C0235d(this, 3);
            Timer timer = new Timer();
            this.f137h = timer;
            timer.schedule(c0235d, j2);
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        synchronized (this) {
            if (this.f136g) {
                return;
            }
            m316A(false);
            this.f130a.m308A(AbstractC0085g.m315b(1163086915, this.f131b, null, this.f132c));
            m320z();
        }
    }

    public final void finalize() {
        close();
        super.finalize();
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0025 A[Catch: all -> 0x00e3, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0017, B:11:0x0025, B:13:0x0032, B:15:0x003a, B:17:0x0052, B:19:0x005a, B:21:0x0060, B:23:0x0068, B:24:0x006d, B:26:0x0073, B:40:0x008c, B:44:0x0091, B:46:0x0099, B:48:0x00a1, B:50:0x00a9, B:52:0x00af, B:54:0x00b7, B:55:0x00bc, B:57:0x00c2, B:71:0x00dc, B:75:0x00e1), top: B:3:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x008c A[Catch: all -> 0x00e3, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0017, B:11:0x0025, B:13:0x0032, B:15:0x003a, B:17:0x0052, B:19:0x005a, B:21:0x0060, B:23:0x0068, B:24:0x006d, B:26:0x0073, B:40:0x008c, B:44:0x0091, B:46:0x0099, B:48:0x00a1, B:50:0x00a9, B:52:0x00af, B:54:0x00b7, B:55:0x00bc, B:57:0x00c2, B:71:0x00dc, B:75:0x00e1), top: B:3:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00dc A[Catch: all -> 0x00e3, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0017, B:11:0x0025, B:13:0x0032, B:15:0x003a, B:17:0x0052, B:19:0x005a, B:21:0x0060, B:23:0x0068, B:24:0x006d, B:26:0x0073, B:40:0x008c, B:44:0x0091, B:46:0x0099, B:48:0x00a1, B:50:0x00a9, B:52:0x00af, B:54:0x00b7, B:55:0x00bc, B:57:0x00c2, B:71:0x00dc, B:75:0x00e1), top: B:3:0x0003 }] */
    /* renamed from: x */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m318x(byte[] bArr) {
        boolean z2;
        int i2;
        int i3;
        synchronized (this.f134e) {
            this.f134e.add(bArr);
            this.f134e.notifyAll();
            int i4 = 0;
            if (this.f138i.isEmpty() && this.f139j.isEmpty()) {
                z2 = false;
                if (z2) {
                    String str = new String(bArr, StandardCharsets.UTF_8);
                    if (!AbstractC0026q.m151B(str) && !str.equals("shell")) {
                        String replace = str.replace("\n", BuildConfig.FLAVOR);
                        Log.d("addPayload", replace);
                        if (this.f140k.get() != 0 && this.f140k.get() != 1) {
                            if (!AbstractC0026q.m151B(replace)) {
                                LinkedList linkedList = this.f138i;
                                if (!linkedList.isEmpty()) {
                                    Iterator it = linkedList.iterator();
                                    i2 = -1;
                                    while (it.hasNext()) {
                                        i3 = ((C0328a) ((InterfaceC0329b) it.next())).m872a(replace);
                                        if (i3 == 1) {
                                            break;
                                        } else if (i3 == 2 && i2 == -1) {
                                            i2 = 3;
                                        }
                                    }
                                    i3 = i2;
                                    if (i3 != -1) {
                                        this.f140k.set(i3);
                                    }
                                }
                            }
                            i2 = -1;
                            i3 = i2;
                            if (i3 != -1) {
                            }
                        }
                        if (this.f140k.get() != 0 && this.f140k.get() != 1 && this.f140k.get() != 3) {
                            if (!AbstractC0026q.m151B(replace)) {
                                LinkedList linkedList2 = this.f139j;
                                if (!linkedList2.isEmpty()) {
                                    Iterator it2 = linkedList2.iterator();
                                    int i5 = -1;
                                    while (true) {
                                        if (!it2.hasNext()) {
                                            i4 = i5;
                                            break;
                                        }
                                        int m872a = ((C0328a) ((InterfaceC0329b) it2.next())).m872a(replace);
                                        if (m872a == 1) {
                                            break;
                                        } else if (m872a == 2 && i5 == -1) {
                                            i5 = 4;
                                        }
                                    }
                                    if (i4 != -1) {
                                        this.f140k.set(i4);
                                    }
                                }
                            }
                            i4 = -1;
                            if (i4 != -1) {
                            }
                        }
                    }
                }
            }
            z2 = true;
            if (z2) {
            }
        }
    }

    /* renamed from: y */
    public final void m319y() {
        AtomicInteger atomicInteger = this.f140k;
        if (atomicInteger.get() == 3) {
            atomicInteger.set(1);
        }
        if (atomicInteger.get() == 4) {
            atomicInteger.set(0);
        }
        if (atomicInteger.get() == -1) {
            atomicInteger.set(5);
        }
        Timer timer = this.f137h;
        if (timer != null) {
            timer.cancel();
            this.f137h = null;
        }
        try {
            close();
        } catch (Exception e2) {
            AbstractC0026q.m186s("AdbStream", e2);
        }
    }

    /* renamed from: z */
    public final void m320z() {
        LinkedList linkedList = this.f138i;
        try {
            if (!linkedList.isEmpty()) {
                linkedList.clear();
            }
            LinkedList linkedList2 = this.f139j;
            if (!linkedList2.isEmpty()) {
                linkedList2.clear();
            }
            this.f134e.clear();
            this.f135f.clear();
        } catch (Exception e2) {
            AbstractC0026q.m186s("AdbStream", e2);
        }
    }
}
