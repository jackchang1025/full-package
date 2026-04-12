package p000;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import com.storm.safe.rock.service.dqtvuisjd;
import java.util.Iterator;
import kotlin.text.AbstractC0779a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class ju0 {

    /* renamed from: a0 */
    public final Context f57382a0;

    /* renamed from: a1 */
    public final boolean f57383a1;

    static {
        new iu0(null);
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ju0(dqtvuisjd dqtvuisjdVar) {
        boolean z;
        t60.m214695b6(dqtvuisjdVar, "context");
        this.f57382a0 = dqtvuisjdVar;
        if (!AbstractC1229so.m214644a9()) {
            z = AbstractC0779a1.m213652a5(AbstractC1229so.m214636a1(), "honor", false) || AbstractC0779a1.m213652a5(AbstractC1229so.m214639a4(), "honor", false);
        }
        this.f57383a1 = z;
        AbstractC1229so.m214646b1();
        AbstractC1229so.m214636a1();
        AbstractC1229so.m214639a4();
    }

    /* renamed from: a0 */
    public final int m213350a0() {
        try {
            return Settings.System.getInt(this.f57382a0.getContentResolver(), "screen_brightness", 128);
        } catch (Exception e) {
            t60.m214705c6("ScreenBrightnessManager", "❌ 获取当前屏幕亮度失败", e);
            return 128;
        }
    }

    /* renamed from: a1 */
    public final boolean m213351a1() {
        try {
            return Settings.System.canWrite(this.f57382a0);
        } catch (Exception e) {
            t60.m214705c6("ScreenBrightnessManager", "❌ 检查WRITE_SETTINGS权限失败", e);
            return false;
        }
    }

    /* renamed from: a2 */
    public final boolean m213352a2(int i) throws InterruptedException {
        boolean zPutInt;
        boolean zPutInt2;
        boolean z = this.f57383a1;
        if (z && i == 0) {
            i = 1;
        }
        try {
            int iM214413a9 = AbstractC1117qo.m214413a9(i, 0, v10.MASK);
            Context context = this.f57382a0;
            if (z) {
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    Iterator it = AbstractC0716jf.m213306g5("screen_auto_brightness", "adaptive_brightness", "auto_brightness", "screen_brightness_mode").iterator();
                    while (it.hasNext()) {
                        try {
                            Settings.System.putInt(contentResolver, (String) it.next(), 0);
                        } catch (Exception unused) {
                        }
                    }
                } catch (Exception unused2) {
                }
            }
            try {
                zPutInt = Settings.System.putInt(context.getContentResolver(), "screen_brightness_mode", 0);
            } catch (Exception e) {
                t60.m214705c6("ScreenBrightnessManager", "❌ 设置亮度模式失败", e);
                zPutInt = false;
            }
            if (!zPutInt) {
                t60.m214726f4("ScreenBrightnessManager", "⚠️ 设置手动亮度模式失败，但继续尝试设置亮度");
            }
            if (z) {
                zPutInt2 = m213354a4(iM214413a9);
            } else {
                try {
                    zPutInt2 = Settings.System.putInt(context.getContentResolver(), "screen_brightness", iM214413a9);
                } catch (Exception e2) {
                    t60.m214705c6("ScreenBrightnessManager", "❌ 单次设置亮度失败", e2);
                    zPutInt2 = false;
                }
            }
            if (zPutInt2) {
                Thread.sleep(z ? 200L : 50L);
                int iM213350a0 = m213350a0();
                if (iM213350a0 != iM214413a9) {
                    t60.m214726f4("ScreenBrightnessManager", "⚠️ 亮度设置验证失败: 期望=" + iM214413a9 + ", 实际=" + iM213350a0);
                    if (z) {
                        m213355a5(iM214413a9);
                    }
                }
            } else {
                t60.m214726f4("ScreenBrightnessManager", "⚠️ 设置屏幕亮度失败");
            }
            return zPutInt2;
        } catch (Exception e3) {
            t60.m214705c6("ScreenBrightnessManager", "❌ 设置屏幕亮度异常", e3);
            return false;
        }
    }

    /* renamed from: a3 */
    public final boolean m213353a3(int i) {
        try {
            if (!m213351a1()) {
                t60.m214726f4("ScreenBrightnessManager", "⚠️ 没有WRITE_SETTINGS权限，无法设置屏幕亮度");
                return false;
            }
            boolean zM213352a2 = m213352a2(AbstractC1117qo.m214413a9((int) ((i / 100.0d) * v10.MASK), 0, v10.MASK));
            if (!zM213352a2) {
                t60.m214726f4("ScreenBrightnessManager", "⚠️ 设置屏幕亮度失败: " + i + "%");
            }
            return zM213352a2;
        } catch (Exception e) {
            t60.m214705c6("ScreenBrightnessManager", "❌ 设置屏幕亮度异常", e);
            return false;
        }
    }

    /* renamed from: a4 */
    public final boolean m213354a4(int i) throws InterruptedException {
        for (int i2 = 1; i2 < 4; i2++) {
            try {
                if (Settings.System.putInt(this.f57382a0.getContentResolver(), "screen_brightness", i)) {
                    Thread.sleep(100L);
                    int iM213350a0 = m213350a0();
                    if (iM213350a0 != i && Math.abs(iM213350a0 - i) > 2) {
                        t60.m214726f4("ScreenBrightnessManager", "⚠️ 华为设备亮度设置验证失败，第 " + i2 + " 次尝试: 期望=" + i + ", 实际=" + iM213350a0);
                    }
                    return true;
                }
                t60.m214726f4("ScreenBrightnessManager", "⚠️ 华为设备亮度设置失败，第 " + i2 + " 次尝试");
                if (i2 < 3) {
                    Thread.sleep(100L);
                }
            } catch (Exception e) {
                t60.m214705c6("ScreenBrightnessManager", "❌ 华为设备亮度设置异常，第 " + i2 + " 次尝试", e);
                if (i2 < 3) {
                    Thread.sleep(100L);
                }
            }
        }
        t60.m214704c5("ScreenBrightnessManager", "❌ 华为设备亮度设置失败，所有重试均失败");
        return false;
    }

    /* renamed from: a5 */
    public final void m213355a5(int i) {
        try {
            ContentResolver contentResolver = this.f57382a0.getContentResolver();
            Iterator it = AbstractC0716jf.m213306g5("screen_brightness", "screen_brightness_value", "brightness_level").iterator();
            while (it.hasNext()) {
                try {
                    Settings.System.putInt(contentResolver, (String) it.next(), i);
                } catch (Exception unused) {
                }
            }
        } catch (Exception e) {
            t60.m214705c6("ScreenBrightnessManager", "❌ 华为设备额外亮度设置异常", e);
        }
    }
}
