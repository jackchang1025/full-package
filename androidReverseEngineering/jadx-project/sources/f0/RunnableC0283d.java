package f0;

import a1.AbstractC0026q;
import android.util.Log;
import com.guard.wallet.http.C0203h;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import l0.C0375e;

/* renamed from: f0.d */
/* loaded from: classes.dex */
public final class RunnableC0283d implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ InetAddress f507a = null;

    /* renamed from: b */
    public final /* synthetic */ int f508b = 7910;

    /* renamed from: c */
    public final /* synthetic */ C0375e f509c;

    /* renamed from: d */
    public final /* synthetic */ C0203h f510d;

    /* renamed from: e */
    public final /* synthetic */ C0289j f511e;

    public RunnableC0283d(C0289j c0289j, C0375e c0375e, C0203h c0203h) {
        this.f511e = c0289j;
        this.f509c = c0375e;
        this.f510d = c0203h;
    }

    @Override // java.lang.Runnable
    public final void run() {
        a0 a0Var;
        IOException e2;
        ServerSocketChannel serverSocketChannel;
        C0375e c0375e = this.f509c;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            try {
                a0Var = new a0(serverSocketChannel, 0);
                int i2 = this.f508b;
                InetAddress inetAddress = this.f507a;
                try {
                    serverSocketChannel.socket().bind(inetAddress == null ? new InetSocketAddress(i2) : new InetSocketAddress(inetAddress, i2));
                    SelectionKey register = ((ServerSocketChannel) a0Var.f488c).register(this.f511e.f526a.f563a, 16);
                    register.attach(c0375e);
                    C0203h c0203h = this.f510d;
                    C0282c c0282c = new C0282c(a0Var, register);
                    c0203h.f245e = c0282c;
                    c0375e.f744d.f746b.add(c0282c);
                } catch (IOException e3) {
                    e2 = e3;
                    Log.e("NIO", "wtf", e2);
                    AbstractC0026q.m177h(a0Var, serverSocketChannel);
                    c0375e.mo293a(e2);
                }
            } catch (IOException e4) {
                a0Var = null;
                e2 = e4;
            }
        } catch (IOException e5) {
            a0Var = null;
            e2 = e5;
            serverSocketChannel = null;
        }
    }
}
