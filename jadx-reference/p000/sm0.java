package p000;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import com.storm.safe.rock.service.modules.cipher.C0337a3;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class sm0 {
    public /* synthetic */ sm0(AbstractC1120qr abstractC1120qr) {
        this();
    }

    public final C0337a3 getInstance(AccessibilityService accessibilityService, Context context) {
        C0337a3 c0337a3;
        t60.m214695b6(accessibilityService, "service");
        t60.m214695b6(context, "context");
        C0337a3 c0337a32 = C0337a3.f53344b7;
        if (c0337a32 != null) {
            return c0337a32;
        }
        synchronized (this) {
            c0337a3 = C0337a3.f53344b7;
            if (c0337a3 == null) {
                c0337a3 = new C0337a3(accessibilityService, context);
                C0337a3.f53344b7 = c0337a3;
                if (C0337a3.f53345b8 == null) {
                    try {
                        C0337a3.f53345b8 = c0337a3.m211846a9();
                        t60.m214714d6("PatternCaptureOverlay", "★ 预缓存 SystemUI 样式完成: " + C0337a3.f53345b8);
                    } catch (Exception e) {
                        t60.m214726f4("PatternCaptureOverlay", "预缓存 SystemUI 样式失败: " + e.getMessage());
                    }
                }
            }
        }
        return c0337a3;
    }

    public final void releaseInstance() {
        C0337a3 c0337a3 = C0337a3.f53344b7;
        if (c0337a3 != null) {
            c0337a3.m211848b1(false);
            AbstractC1117qo.m214410a3(c0337a3.f53361b5);
        }
        C0337a3.f53344b7 = null;
    }

    private sm0() {
    }
}
