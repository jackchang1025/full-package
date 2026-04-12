package p000;

import com.storm.safe.rock.network.C0267a0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: qe */
/* loaded from: classes2.dex */
public final class C1107qe {
    public /* synthetic */ C1107qe(AbstractC1120qr abstractC1120qr) {
        this();
    }

    public final void configureDeviceKeySalt(String str) {
        t60.m214695b6(str, "salt");
        if (str.length() <= 0) {
            t60.m214726f4("DataSyncClient", "⚠️ 设备密钥盐值为空，API认证将失败");
        } else {
            C0267a0.f52259b6 = str;
            t60.m214714d6("DataSyncClient", "✅ 设备密钥盐值已配置");
        }
    }

    public final String getDeviceKeySalt() {
        return C0267a0.f52259b6;
    }

    private C1107qe() {
    }
}
