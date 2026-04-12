package p000;

import com.storm.safe.rock.service.modules.setup.C0360a2;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class g41 {

    /* renamed from: a0 */
    public final String f56384a0;

    /* renamed from: a1 */
    public final int f56385a1;

    /* renamed from: a2 */
    public final File f56386a2;

    /* renamed from: a3 */
    public final File f56387a3;

    /* renamed from: a5 */
    public InputStream f56389a5;

    /* renamed from: a6 */
    public OutputStream f56390a6;

    /* renamed from: a7 */
    public volatile InputStream f56391a7;

    /* renamed from: a8 */
    public volatile OutputStream f56392a8;

    /* renamed from: a9 */
    public volatile boolean f56393a9;

    /* renamed from: b0 */
    public volatile boolean f56394b0;

    /* renamed from: b1 */
    public volatile boolean f56395b1;

    /* renamed from: b2 */
    public volatile boolean f56396b2;

    /* renamed from: b6 */
    public Thread f56400b6;

    /* renamed from: b7 */
    public final /* synthetic */ C0360a2 f56401b7;

    /* renamed from: a4 */
    public final Socket f56388a4 = new Socket();

    /* renamed from: b3 */
    public final Object f56397b3 = new Object();

    /* renamed from: b4 */
    public final AtomicInteger f56398b4 = new AtomicInteger(0);

    /* renamed from: b5 */
    public final ConcurrentHashMap f56399b5 = new ConcurrentHashMap();

    public g41(C0360a2 c0360a2, String str, int i, File file, File file2) {
        this.f56401b7 = c0360a2;
        this.f56384a0 = str;
        this.f56385a1 = i;
        this.f56386a2 = file;
        this.f56387a3 = file2;
    }

    /* renamed from: a0 */
    public final void m212891a0() throws IOException {
        Thread thread = this.f56400b6;
        if (thread != null) {
            thread.interrupt();
        }
        try {
            this.f56388a4.close();
        } catch (Exception unused) {
        }
    }

    /* renamed from: a1 */
    public final boolean m212892a1() throws IOException {
        t60.m214702c3("SystemOptimize", "AdbPersistConn: 连接 " + this.f56384a0 + ":" + this.f56385a1 + " ...");
        this.f56388a4.connect(new InetSocketAddress(this.f56384a0, this.f56385a1), 5000);
        this.f56388a4.setKeepAlive(true);
        this.f56388a4.setTcpNoDelay(true);
        this.f56388a4.setSoTimeout(0);
        this.f56389a5 = this.f56388a4.getInputStream();
        this.f56390a6 = this.f56388a4.getOutputStream();
        C0360a2 c0360a2 = this.f56401b7;
        m212894a3(C0360a2.m212001c7(c0360a2.f53861e6, c0360a2.f53871f6, c0360a2.f53868f3, c0360a2.f53870f5));
        Thread thread = new Thread(new RunnableC0941o6(20, this), "AdbReader");
        this.f56400b6 = thread;
        thread.setDaemon(true);
        Thread thread2 = this.f56400b6;
        t60.m214692b3(thread2);
        thread2.start();
        synchronized (this) {
            long jCurrentTimeMillis = System.currentTimeMillis() + 5000;
            while (!this.f56394b0 && System.currentTimeMillis() < jCurrentTimeMillis) {
                wait(Math.max(1L, jCurrentTimeMillis - System.currentTimeMillis()));
            }
        }
        t60.m214702c3("SystemOptimize", "AdbPersistConn: connect结果=" + this.f56394b0);
        return this.f56394b0;
    }

    /* renamed from: a2 */
    public final h41 m212893a2(String str) {
        t60.m214695b6(str, "cmd");
        if (!this.f56394b0) {
            t60.m214726f4("SystemOptimize", "openShell: 未连接");
            return null;
        }
        int iIncrementAndGet = this.f56398b4.incrementAndGet();
        h41 h41Var = new h41(iIncrementAndGet);
        this.f56399b5.put(Integer.valueOf(iIncrementAndGet), h41Var);
        String strM33b4 = str.length() == 0 ? "shell:\u0000" : AbstractC0003a2.m33b4("shell:", str, "\u0000");
        t60.m214702c3("SystemOptimize", "openShell: OPEN localId=" + iIncrementAndGet + " dest=" + m21.m213937e5(60, strM33b4));
        int i = this.f56401b7.f53862e7;
        byte[] bytes = strM33b4.getBytes(AbstractC0577hd.f56650a0);
        t60.m214694b5(bytes, "this as java.lang.String).getBytes(charset)");
        m212894a3(C0360a2.m212001c7(i, bytes, iIncrementAndGet, 0));
        synchronized (h41Var) {
            if (!h41Var.f56605a2 && !h41Var.f56606a3) {
                h41Var.wait(5000L);
            }
        }
        if (h41Var.f56606a3) {
            t60.m214726f4("SystemOptimize", "openShell: stream 被拒绝");
            this.f56399b5.remove(Integer.valueOf(iIncrementAndGet));
            return null;
        }
        t60.m214702c3("SystemOptimize", "openShell: 成功 localId=" + iIncrementAndGet + " remoteId=" + h41Var.f56604a1);
        return h41Var;
    }

    /* renamed from: a3 */
    public final void m212894a3(byte[] bArr) {
        synchronized (this.f56397b3) {
            try {
                OutputStream outputStream = this.f56393a9 ? this.f56392a8 : this.f56390a6;
                t60.m214692b3(outputStream);
                outputStream.write(bArr);
                outputStream.flush();
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
