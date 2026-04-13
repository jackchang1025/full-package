package n1;

import a1.AbstractC0026q;
import android.util.Log;
import e1.C0275d;
import java.nio.ByteBuffer;
import java.util.concurrent.LinkedBlockingQueue;

/* renamed from: n1.a */
/* loaded from: classes.dex */
public final class C0410a extends Thread {

    /* renamed from: a */
    public final LinkedBlockingQueue f820a = new LinkedBlockingQueue();

    /* renamed from: b */
    public final /* synthetic */ AbstractRunnableC0411b f821b;

    public C0410a(AbstractRunnableC0411b abstractRunnableC0411b) {
        this.f821b = abstractRunnableC0411b;
        setName("WebSocketWorker-" + getId());
    }

    /* renamed from: a */
    public final void m970a(C0275d c0275d, ByteBuffer byteBuffer) {
        AbstractRunnableC0411b abstractRunnableC0411b = this.f821b;
        try {
            try {
                c0275d.m768m(byteBuffer);
            } catch (Exception e2) {
                int i2 = AbstractRunnableC0411b.f822w;
                AbstractC0026q.m186s("n1.b", e2);
            }
        } finally {
            abstractRunnableC0411b.m973D(byteBuffer);
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        C0275d c0275d;
        Throwable th;
        Throwable e2;
        AbstractRunnableC0411b abstractRunnableC0411b = this.f821b;
        while (true) {
            try {
                try {
                    c0275d = (C0275d) this.f820a.take();
                } catch (InterruptedException unused) {
                    Thread.currentThread().interrupt();
                    return;
                }
            } catch (LinkageError e3) {
                e = e3;
                Throwable th2 = e;
                c0275d = null;
                e2 = th2;
                int i2 = AbstractRunnableC0411b.f822w;
                Log.d("n1.b", "Got fatal error in worker thread:" + getName());
                abstractRunnableC0411b.m981z(c0275d, new Exception(e2));
                return;
            } catch (ThreadDeath e4) {
                e = e4;
                Throwable th22 = e;
                c0275d = null;
                e2 = th22;
                int i22 = AbstractRunnableC0411b.f822w;
                Log.d("n1.b", "Got fatal error in worker thread:" + getName());
                abstractRunnableC0411b.m981z(c0275d, new Exception(e2));
                return;
            } catch (VirtualMachineError e5) {
                e = e5;
                Throwable th222 = e;
                c0275d = null;
                e2 = th222;
                int i222 = AbstractRunnableC0411b.f822w;
                Log.d("n1.b", "Got fatal error in worker thread:" + getName());
                abstractRunnableC0411b.m981z(c0275d, new Exception(e2));
                return;
            } catch (Throwable th3) {
                c0275d = null;
                th = th3;
            }
            try {
                m970a(c0275d, (ByteBuffer) c0275d.f463b.poll());
            } catch (LinkageError e6) {
                e2 = e6;
                int i2222 = AbstractRunnableC0411b.f822w;
                Log.d("n1.b", "Got fatal error in worker thread:" + getName());
                abstractRunnableC0411b.m981z(c0275d, new Exception(e2));
                return;
            } catch (ThreadDeath e7) {
                e2 = e7;
                int i22222 = AbstractRunnableC0411b.f822w;
                Log.d("n1.b", "Got fatal error in worker thread:" + getName());
                abstractRunnableC0411b.m981z(c0275d, new Exception(e2));
                return;
            } catch (VirtualMachineError e8) {
                e2 = e8;
                int i222222 = AbstractRunnableC0411b.f822w;
                Log.d("n1.b", "Got fatal error in worker thread:" + getName());
                abstractRunnableC0411b.m981z(c0275d, new Exception(e2));
                return;
            } catch (Throwable th4) {
                th = th4;
                int i3 = AbstractRunnableC0411b.f822w;
                AbstractC0026q.m187t("n1.b", th);
                if (c0275d != null) {
                    abstractRunnableC0411b.mo514C(new Exception(th));
                    c0275d.mo750e(1000);
                    return;
                }
                return;
            }
        }
    }
}
