package p000;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import com.storm.safe.rock.service.modules.AbstractC0315a0;
import com.storm.safe.rock.service.modules.C0323a8;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class mj0 extends ConnectivityManager.NetworkCallback {

    /* renamed from: a0 */
    public final /* synthetic */ int f58378a0;

    /* renamed from: a1 */
    public final /* synthetic */ Object f58379a1;

    public /* synthetic */ mj0(int i, Object obj) {
        this.f58378a0 = i;
        this.f58379a1 = obj;
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public void onAvailable(Network network) {
        switch (this.f58378a0) {
            case 0:
                t60.m214695b6(network, "network");
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (jCurrentTimeMillis - ((C0323a8) this.f58379a1).f53116b6 >= 5000) {
                    ((C0323a8) this.f58379a1).f53116b6 = jCurrentTimeMillis;
                    t60.m214714d6("NetworkManager", "📶 网络可用");
                    AbstractC0315a0.m211548b0("网络恢复(WiFi/4G可用)");
                    AbstractC0315a0.m211545a7("网络恢复可用 WiFi或移动数据已连接");
                    ((C0323a8) this.f58379a1).m211669d6();
                    break;
                }
                break;
            default:
                super.onAvailable(network);
                break;
        }
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        switch (this.f58378a0) {
            case 1:
                t60.m214695b6(network, "network");
                t60.m214695b6(networkCapabilities, "capabilities");
                C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
                int i = tj0.f60235a0;
                networkCapabilities.toString();
                c1351vvM214963a5.getClass();
                sj0 sj0Var = (sj0) this.f58379a1;
                sj0Var.m213874a2(tj0.m214752a0(sj0Var.f59998a5));
                break;
            default:
                super.onCapabilitiesChanged(network, networkCapabilities);
                break;
        }
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public final void onLost(Network network) {
        int i = this.f58378a0;
        t60.m214695b6(network, "network");
        switch (i) {
            case 0:
                t60.m214726f4("NetworkManager", "📶 网络丢失");
                AbstractC0315a0.m211548b0("网络丢失(断网)");
                break;
            default:
                C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
                int i2 = tj0.f60235a0;
                c1351vvM214963a5.getClass();
                sj0 sj0Var = (sj0) this.f58379a1;
                sj0Var.m213874a2(tj0.m214752a0(sj0Var.f59998a5));
                break;
        }
    }
}
