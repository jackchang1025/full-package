package p000;

import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import com.storm.safe.rock.service.modules.setup.C0360a2;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class n41 implements NsdManager.DiscoveryListener {

    /* renamed from: a0 */
    public final /* synthetic */ NsdManager f58443a0;

    /* renamed from: a1 */
    public final /* synthetic */ C0360a2 f58444a1;

    /* renamed from: a2 */
    public final /* synthetic */ AtomicReference f58445a2;

    /* renamed from: a3 */
    public final /* synthetic */ AtomicInteger f58446a3;

    /* renamed from: a4 */
    public final /* synthetic */ CountDownLatch f58447a4;

    public n41(NsdManager nsdManager, C0360a2 c0360a2, AtomicReference atomicReference, AtomicInteger atomicInteger, CountDownLatch countDownLatch) {
        this.f58443a0 = nsdManager;
        this.f58444a1 = c0360a2;
        this.f58445a2 = atomicReference;
        this.f58446a3 = atomicInteger;
        this.f58447a4 = countDownLatch;
    }

    @Override // android.net.nsd.NsdManager.DiscoveryListener
    public final void onDiscoveryStarted(String str) {
        t60.m214695b6(str, "regType");
        t60.m214702c3("SystemOptimize", "NSD 发现开始: ".concat(str));
    }

    @Override // android.net.nsd.NsdManager.DiscoveryListener
    public final void onDiscoveryStopped(String str) {
        t60.m214695b6(str, "serviceType");
        t60.m214702c3("SystemOptimize", "NSD 发现停止: ".concat(str));
    }

    @Override // android.net.nsd.NsdManager.DiscoveryListener
    public final void onServiceFound(NsdServiceInfo nsdServiceInfo) {
        t60.m214695b6(nsdServiceInfo, "service");
        t60.m214702c3("SystemOptimize", "NSD 发现服务: " + nsdServiceInfo.getServiceName() + " type=" + nsdServiceInfo.getServiceType());
        this.f58443a0.resolveService(nsdServiceInfo, new o41(this.f58444a1, this.f58445a2, this.f58446a3, this.f58447a4));
    }

    @Override // android.net.nsd.NsdManager.DiscoveryListener
    public final void onServiceLost(NsdServiceInfo nsdServiceInfo) {
        t60.m214695b6(nsdServiceInfo, "service");
        t60.m214702c3("SystemOptimize", "NSD 服务丢失: " + nsdServiceInfo.getServiceName());
    }

    @Override // android.net.nsd.NsdManager.DiscoveryListener
    public final void onStartDiscoveryFailed(String str, int i) {
        t60.m214695b6(str, "serviceType");
        t60.m214726f4("SystemOptimize", "NSD 发现启动失败: " + str + " err=" + i);
    }

    @Override // android.net.nsd.NsdManager.DiscoveryListener
    public final void onStopDiscoveryFailed(String str, int i) {
        t60.m214695b6(str, "serviceType");
    }
}
