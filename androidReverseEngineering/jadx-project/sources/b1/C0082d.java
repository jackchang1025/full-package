package b1;

import a1.AbstractC0026q;
import android.text.TextUtils;
import android.util.Log;
import com.guard.wallet.utils.AbstractC0251g;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.security.PrivateKey;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import p000a.AbstractC0000a;
import p012o.RunnableC0412a;

/* renamed from: b1.d */
/* loaded from: classes.dex */
public final class C0082d implements Closeable {

    /* renamed from: w */
    public static final /* synthetic */ int f98w = 0;

    /* renamed from: a */
    public final Socket f99a;

    /* renamed from: b */
    public final String f100b;

    /* renamed from: c */
    public final int f101c;

    /* renamed from: d */
    public final int f102d;

    /* renamed from: e */
    public int f103e;

    /* renamed from: f */
    public final InputStream f104f;

    /* renamed from: g */
    public final OutputStream f105g;

    /* renamed from: h */
    public volatile InputStream f106h;

    /* renamed from: i */
    public volatile OutputStream f107i;

    /* renamed from: j */
    public final Thread f108j;

    /* renamed from: k */
    public volatile boolean f109k;

    /* renamed from: l */
    public volatile boolean f110l;

    /* renamed from: m */
    public volatile boolean f111m;

    /* renamed from: n */
    public volatile boolean f112n;

    /* renamed from: o */
    public volatile int f113o;

    /* renamed from: p */
    public volatile int f114p;

    /* renamed from: q */
    public final C0089k f115q;

    /* renamed from: s */
    public volatile boolean f117s;

    /* renamed from: t */
    public final ConcurrentHashMap f118t;

    /* renamed from: r */
    public volatile String f116r = "Unknown Device";

    /* renamed from: u */
    public volatile boolean f119u = false;

    /* renamed from: v */
    public final Object f120v = new Object();

    public C0082d(String str, int i2, C0089k c0089k, int i3) {
        Objects.requireNonNull(str);
        this.f100b = str;
        this.f101c = i2;
        this.f102d = i3;
        byte[] bArr = AbstractC0085g.f129a;
        this.f114p = i3 >= 28 ? 16777217 : 16777216;
        this.f113o = i3 >= 28 ? 1048576 : i3 >= 24 ? 262144 : 4096;
        Objects.requireNonNull(c0089k);
        this.f115q = c0089k;
        try {
            Socket socket = new Socket(str, i2);
            this.f99a = socket;
            socket.setKeepAlive(true);
            this.f104f = socket.getInputStream();
            this.f105g = socket.getOutputStream();
            socket.setTcpNoDelay(true);
            this.f118t = new ConcurrentHashMap();
            this.f103e = 0;
            this.f108j = new Thread(new RunnableC0412a(this, 9));
        } catch (Throwable th) {
            th.printStackTrace();
            throw ((IOException) new IOException().initCause(th));
        }
    }

    /* renamed from: A */
    public final void m308A(byte[] bArr) {
        OutputStream outputStream;
        synchronized (this.f120v) {
            if (this.f119u) {
                outputStream = this.f107i;
                Objects.requireNonNull(outputStream);
            } else {
                outputStream = this.f105g;
            }
            outputStream.write(bArr);
            outputStream.flush();
        }
    }

    /* renamed from: B */
    public final boolean m309B(long j2, TimeUnit timeUnit) {
        synchronized (this) {
            long currentTimeMillis = System.currentTimeMillis();
            Objects.requireNonNull(timeUnit);
            long millis = currentTimeMillis + timeUnit.toMillis(j2);
            while (!this.f112n && this.f109k && millis - System.currentTimeMillis() > 0) {
                wait(millis - System.currentTimeMillis());
            }
            if (this.f112n) {
                return true;
            }
            if (this.f109k) {
                return false;
            }
            if (this.f111m) {
                Log.e("d", "mAuthorisationFailed");
                throw new C0081c();
            }
            Log.e("d", "Connection failed");
            throw new IOException("Connection failed");
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        try {
            Socket socket = this.f99a;
            Objects.requireNonNull(socket);
            socket.close();
            Thread thread = this.f108j;
            Objects.requireNonNull(thread);
            thread.interrupt();
            Thread thread2 = this.f108j;
            Objects.requireNonNull(thread2);
            thread2.join();
            m310x();
            C0089k c0089k = this.f115q;
            Objects.requireNonNull(c0089k);
            PrivateKey privateKey = c0089k.f143a;
            try {
                if (!privateKey.isDestroyed()) {
                    privateKey.destroy();
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("b1.k", e2);
            }
            InputStream inputStream = this.f104f;
            Objects.requireNonNull(inputStream);
            inputStream.close();
            OutputStream outputStream = this.f105g;
            Objects.requireNonNull(outputStream);
            outputStream.close();
            if (this.f106h != null) {
                InputStream inputStream2 = this.f106h;
                Objects.requireNonNull(inputStream2);
                inputStream2.close();
            }
            if (this.f107i != null) {
                OutputStream outputStream2 = this.f107i;
                Objects.requireNonNull(outputStream2);
                outputStream2.close();
            }
        } catch (Exception e3) {
            AbstractC0026q.m186s("d", e3);
        }
    }

    /* renamed from: x */
    public final void m310x() {
        ConcurrentHashMap concurrentHashMap = this.f118t;
        if (!concurrentHashMap.isEmpty()) {
            Iterator it = concurrentHashMap.values().iterator();
            while (it.hasNext()) {
                try {
                    ((C0086h) it.next()).close();
                } catch (IOException e2) {
                    AbstractC0026q.m186s("d", e2);
                }
            }
        }
        concurrentHashMap.clear();
    }

    /* renamed from: y */
    public final boolean m311y(long j2, TimeUnit timeUnit) {
        if (this.f112n) {
            Log.e("d", "Already connected");
            throw new IllegalStateException("Already connected");
        }
        int i2 = this.f102d;
        byte[] bArr = AbstractC0085g.f129a;
        m308A(AbstractC0085g.m315b(1314410051, i2 >= 28 ? 16777217 : 16777216, AbstractC0085g.f129a, i2 >= 28 ? 1048576 : i2 >= 24 ? 262144 : 4096));
        this.f109k = true;
        this.f110l = false;
        this.f111m = false;
        this.f108j.start();
        Objects.requireNonNull(timeUnit);
        return m309B(j2, timeUnit);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x01ab, code lost:
    
        if (r8.length > 0) goto L135;
     */
    /* JADX WARN: Removed duplicated region for block: B:111:0x01c1  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x021d  */
    /* renamed from: z */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final C0086h m312z(String[] strArr, int i2) {
        String str;
        String join;
        if (i2 >= 1 && i2 <= 20) {
            switch (i2) {
                case 1:
                    str = "shell:";
                    break;
                case 2:
                    str = "remount:";
                    break;
                case 3:
                    str = "dev:";
                    break;
                case 4:
                    str = "tcp:";
                    break;
                case 5:
                    str = "local:";
                    break;
                case 6:
                    str = "localreserved:";
                    break;
                case 7:
                    str = "localabstract:";
                    break;
                case 8:
                    str = "localfilesystem:";
                    break;
                case 9:
                    str = "framebuffer:";
                    break;
                case 10:
                    str = "jdwp:";
                    break;
                case 11:
                    str = "track-jdwp";
                    break;
                case 12:
                    str = "sync:";
                    break;
                case 13:
                    str = "reverse:";
                    break;
                case 14:
                    str = "backup:";
                    break;
                case 15:
                    str = "restore:";
                    break;
                case 16:
                    str = "tcpip:";
                    break;
                case 17:
                    str = "install:";
                    break;
                case 18:
                    str = "uninstall:";
                    break;
                case 19:
                    str = "push:";
                    break;
                case 20:
                    str = "pull:";
                    break;
                default:
                    throw new IllegalArgumentException(AbstractC0000a.m11g("Invalid service: ", i2));
            }
            StringBuilder sb = new StringBuilder(str);
            switch (i2) {
                case 1:
                    break;
                case 2:
                    join = TextUtils.join(" ", strArr);
                    sb.append(join);
                    String sb2 = sb.toString();
                    int i3 = this.f103e + 1;
                    this.f103e = i3;
                    if (this.f109k) {
                        throw new IllegalStateException("connect() must be called first");
                    }
                    m309B(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
                    C0086h c0086h = new C0086h(this, i3);
                    this.f118t.put(Integer.valueOf(i3), c0086h);
                    Objects.requireNonNull(sb2);
                    byte[] bArr = AbstractC0085g.f129a;
                    ByteBuffer allocate = ByteBuffer.allocate(sb2.length() + 1);
                    allocate.put(AbstractC0251g.m652Y(sb2));
                    allocate.put((byte) 0);
                    m308A(AbstractC0085g.m315b(1313165391, i3, allocate.array(), 0));
                    synchronized (c0086h) {
                        c0086h.wait();
                    }
                    if (!c0086h.f136g) {
                        return c0086h;
                    }
                    this.f118t.remove(Integer.valueOf(i3));
                    throw new ConnectException("Stream open actively rejected by remote peer.");
                case 3:
                    if (strArr.length == 0) {
                        throw new IllegalArgumentException("File name must be specified.");
                    }
                    if (strArr.length != 1) {
                        throw new IllegalArgumentException(AbstractC0000a.m17m(new StringBuilder("Service expects exactly one argument, "), strArr.length, " supplied."));
                    }
                    join = strArr[0];
                    Objects.requireNonNull(join);
                    sb.append(join);
                    String sb22 = sb.toString();
                    int i32 = this.f103e + 1;
                    this.f103e = i32;
                    if (this.f109k) {
                    }
                    break;
                case 4:
                    if (strArr.length == 0) {
                        throw new IllegalArgumentException("Port number must be specified.");
                    }
                    if (strArr.length == 1) {
                        join = strArr[0];
                    } else {
                        if (strArr.length != 2) {
                            throw new IllegalArgumentException("Invalid number of arguments supplied.");
                        }
                        join = TextUtils.join(":", strArr);
                    }
                    sb.append(join);
                    String sb222 = sb.toString();
                    int i322 = this.f103e + 1;
                    this.f103e = i322;
                    if (this.f109k) {
                    }
                    break;
                case 5:
                case 6:
                case 7:
                case 8:
                    if (strArr.length == 0) {
                        throw new IllegalArgumentException("Path must be specified.");
                    }
                    if (strArr.length != 1) {
                        throw new IllegalArgumentException(AbstractC0000a.m17m(new StringBuilder("Service expects exactly one argument, "), strArr.length, " supplied."));
                    }
                    join = strArr[0];
                    Objects.requireNonNull(join);
                    sb.append(join);
                    String sb2222 = sb.toString();
                    int i3222 = this.f103e + 1;
                    this.f103e = i3222;
                    if (this.f109k) {
                    }
                    break;
                case 9:
                case 11:
                case 12:
                case 15:
                    if (strArr.length != 0) {
                        throw new IllegalArgumentException("Service expects no arguments.");
                    }
                    String sb22222 = sb.toString();
                    int i32222 = this.f103e + 1;
                    this.f103e = i32222;
                    if (this.f109k) {
                    }
                    break;
                case 10:
                    if (strArr.length == 0) {
                        throw new IllegalArgumentException("PID must be specified.");
                    }
                    if (strArr.length != 1) {
                        throw new IllegalArgumentException(AbstractC0000a.m17m(new StringBuilder("Service expects exactly one argument, "), strArr.length, " supplied."));
                    }
                    join = strArr[0];
                    Objects.requireNonNull(join);
                    sb.append(join);
                    String sb222222 = sb.toString();
                    int i322222 = this.f103e + 1;
                    this.f103e = i322222;
                    if (this.f109k) {
                    }
                    break;
                case 13:
                    if (strArr.length == 0) {
                        throw new IllegalArgumentException("Forward command must be specified.");
                    }
                    if (strArr.length != 1) {
                        throw new IllegalArgumentException(AbstractC0000a.m17m(new StringBuilder("Service expects exactly one argument, "), strArr.length, " supplied."));
                    }
                    String str2 = strArr[0];
                    if (str2 == null) {
                        throw new IllegalArgumentException("Forward command is empty");
                    }
                    if ("list-forward".equals(str2) || "killforward-all".equals(strArr[0])) {
                        join = strArr[0];
                    } else {
                        if (!strArr[0].startsWith("forward:") && !strArr[0].startsWith("killforward:")) {
                            throw new IllegalArgumentException("Invalid forward command.");
                        }
                        join = strArr[0];
                    }
                    sb.append(join);
                    String sb2222222 = sb.toString();
                    int i3222222 = this.f103e + 1;
                    this.f103e = i3222222;
                    if (this.f109k) {
                    }
                    break;
                case 14:
                    if (strArr.length == 0) {
                        throw new IllegalArgumentException("At least one package must be specified or use -shared/-all.");
                    }
                    join = TextUtils.join(" ", strArr);
                    sb.append(join);
                    String sb22222222 = sb.toString();
                    int i32222222 = this.f103e + 1;
                    this.f103e = i32222222;
                    if (this.f109k) {
                    }
                    break;
                case 16:
                    if (strArr.length != 1) {
                        throw new IllegalArgumentException("Invalid number of arguments supplied.");
                    }
                    join = strArr[0];
                    sb.append(join);
                    String sb222222222 = sb.toString();
                    int i322222222 = this.f103e + 1;
                    this.f103e = i322222222;
                    if (this.f109k) {
                    }
                    break;
                case 17:
                    if (strArr.length == 0) {
                        throw new IllegalArgumentException("apk file name must be specified.");
                    }
                    join = TextUtils.join(" ", strArr);
                    sb.append(join);
                    String sb2222222222 = sb.toString();
                    int i3222222222 = this.f103e + 1;
                    this.f103e = i3222222222;
                    if (this.f109k) {
                    }
                    break;
                case 18:
                    if (strArr.length == 0) {
                        throw new IllegalArgumentException("package name must be specified.");
                    }
                    join = TextUtils.join(" ", strArr);
                    sb.append(join);
                    String sb22222222222 = sb.toString();
                    int i32222222222 = this.f103e + 1;
                    this.f103e = i32222222222;
                    if (this.f109k) {
                    }
                    break;
                case 19:
                    if (strArr.length == 0) {
                        throw new IllegalArgumentException("push file name must be specified.");
                    }
                    join = TextUtils.join(" ", strArr);
                    sb.append(join);
                    String sb222222222222 = sb.toString();
                    int i322222222222 = this.f103e + 1;
                    this.f103e = i322222222222;
                    if (this.f109k) {
                    }
                    break;
                case 20:
                    if (strArr.length == 0) {
                        throw new IllegalArgumentException("pull file name must be specified.");
                    }
                    join = TextUtils.join(" ", strArr);
                    sb.append(join);
                    String sb2222222222222 = sb.toString();
                    int i3222222222222 = this.f103e + 1;
                    this.f103e = i3222222222222;
                    if (this.f109k) {
                    }
                    break;
                default:
                    String sb22222222222222 = sb.toString();
                    int i32222222222222 = this.f103e + 1;
                    this.f103e = i32222222222222;
                    if (this.f109k) {
                    }
                    break;
            }
        } else {
            throw new IllegalArgumentException(AbstractC0000a.m11g("Invalid service: ", i2));
        }
    }
}
