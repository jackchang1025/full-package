package p000;

import android.os.FileObserver;
import com.storm.safe.rock.service.modules.setup.C0360a2;
import java.io.File;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class p41 extends FileObserver {

    /* renamed from: a0 */
    public final /* synthetic */ C0360a2 f59155a0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public p41(File file, C0360a2 c0360a2) {
        super(file, 8);
        this.f59155a0 = c0360a2;
    }

    @Override // android.os.FileObserver
    public final void onEvent(int i, String str) {
        if (str == null) {
            return;
        }
        t60.m214714d6("SystemOptimize", "【FileObserver】文件变化: ".concat(str));
        if (str.equals("cert.pem") ? true : str.equals("private.key")) {
            t60.m214714d6("SystemOptimize", "【FileObserver】密钥文件变化，清除 SSL 缓存");
            C0360a2.f53810f9.clearSslCache();
            C0360a2 c0360a2 = this.f59155a0;
            c0360a2.f53843c8 = null;
            c0360a2.f53844c9 = null;
        }
    }
}
