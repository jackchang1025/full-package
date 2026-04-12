package p000;

import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import com.storm.safe.rock.service.modules.setup.C0360a2;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.Pair;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class o41 implements NsdManager.ResolveListener {

    /* renamed from: a0 */
    public final /* synthetic */ C0360a2 f58733a0;

    /* renamed from: a1 */
    public final /* synthetic */ AtomicReference f58734a1;

    /* renamed from: a2 */
    public final /* synthetic */ AtomicInteger f58735a2;

    /* renamed from: a3 */
    public final /* synthetic */ CountDownLatch f58736a3;

    public o41(C0360a2 c0360a2, AtomicReference atomicReference, AtomicInteger atomicInteger, CountDownLatch countDownLatch) {
        this.f58733a0 = c0360a2;
        this.f58734a1 = atomicReference;
        this.f58735a2 = atomicInteger;
        this.f58736a3 = countDownLatch;
    }

    @Override // android.net.nsd.NsdManager.ResolveListener
    public final void onResolveFailed(NsdServiceInfo nsdServiceInfo, int i) {
        t60.m214695b6(nsdServiceInfo, "serviceInfo");
        t60.m214726f4("SystemOptimize", "NSD 解析失败: " + i);
    }

    @Override // android.net.nsd.NsdManager.ResolveListener
    public final void onServiceResolved(NsdServiceInfo nsdServiceInfo) throws SocketException {
        t60.m214695b6(nsdServiceInfo, "serviceInfo");
        InetAddress host = nsdServiceInfo.getHost();
        String hostAddress = host != null ? host.getHostAddress() : null;
        if (hostAddress == null) {
            return;
        }
        int port = nsdServiceInfo.getPort();
        t60.m214702c3("SystemOptimize", "NSD 解析成功: " + hostAddress + ":" + port);
        InetAddress host2 = nsdServiceInfo.getHost();
        if (host2 == null) {
            return;
        }
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            if (networkInterfaces == null) {
                return;
            }
            while (networkInterfaces.hasMoreElements()) {
                Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    if (t60.m214686a2(inetAddresses.nextElement().getHostAddress(), host2.getHostAddress())) {
                        t60.m214714d6("SystemOptimize", "NSD 发现端口: " + hostAddress + ":" + port);
                        C0360a2 c0360a2 = this.f58733a0;
                        synchronized (c0360a2.f53842c7) {
                            Pair pair = new Pair(hostAddress, Integer.valueOf(port));
                            if (!c0360a2.f53842c7.contains(pair)) {
                                c0360a2.f53842c7.add(pair);
                            }
                        }
                        AtomicReference atomicReference = this.f58734a1;
                        AtomicInteger atomicInteger = this.f58735a2;
                        CountDownLatch countDownLatch = this.f58736a3;
                        InetAddress host3 = nsdServiceInfo.getHost();
                        if (host3 != null) {
                            atomicReference.set(host3.getHostAddress());
                            atomicInteger.set(port);
                        }
                        countDownLatch.countDown();
                        return;
                    }
                }
            }
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "检查本机地址异常", e);
        }
    }
}
