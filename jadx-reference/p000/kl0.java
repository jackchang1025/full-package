package p000;

import com.storm.safe.rock.service.modules.cipher.C0335a1;
import com.storm.safe.rock.service.modules.setup.C0358a0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final /* synthetic */ class kl0 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f57540a0;

    /* renamed from: a1 */
    public final /* synthetic */ C0358a0 f57541a1;

    public /* synthetic */ kl0(C0358a0 c0358a0, int i) {
        this.f57540a0 = i;
        this.f57541a1 = c0358a0;
    }

    @Override // java.lang.Runnable
    public final void run() throws InterruptedException {
        switch (this.f57540a0) {
            case 0:
                C0358a0 c0358a0 = this.f57541a1;
                try {
                    try {
                        Thread.sleep(5 * 200);
                        C0335a1 c0600hy = C0335a1.f53283c5.getInstance(c0358a0.f53792a0, c0358a0.f53793a1);
                        boolean zM211809a8 = c0600hy.m211809a8();
                        t60.m214714d6("OpenDevDelegate", "ConfirmLockDelegate 自动输入结果: " + zM211809a8);
                        if (!zM211809a8) {
                            zM211809a8 = c0600hy.m211809a8();
                            t60.m214714d6("OpenDevDelegate", "ConfirmLockDelegate 重试结果: " + zM211809a8);
                        }
                        if (zM211809a8) {
                            t60.m214714d6("OpenDevDelegate", "ConfirmLockDelegate 密码输入成功");
                        } else {
                            t60.m214714d6("OpenDevDelegate", "✗ ConfirmLockDelegate 密码输入失败");
                        }
                    } catch (Exception e) {
                        t60.m214705c6("OpenDevDelegate", "ConfirmLockDelegate 自动输入异常", e);
                    }
                    c0358a0.f53804b2 = false;
                    return;
                } catch (Throwable th) {
                    c0358a0.f53804b2 = false;
                    throw th;
                }
            case 1:
                C0358a0 c0358a02 = this.f57541a1;
                try {
                    c0358a02.m211983c6();
                    Thread.sleep(1000L);
                    c0358a02.f53792a0.performGlobalAction(1);
                    Thread.sleep(300L);
                    t60.m214714d6("OpenDevDelegate", "App 已调到前台，开始打开关于手机页面");
                    c0358a02.m211984c7();
                    return;
                } catch (Exception e2) {
                    t60.m214705c6("OpenDevDelegate", "调起 app 前台失败", e2);
                    c0358a02.m211984c7();
                    return;
                }
            default:
                C0358a0 c0358a03 = this.f57541a1;
                if (c0358a03.m211971a0()) {
                    t60.m214714d6("OpenDevDelegate", "关于手机页面打开成功");
                    c0358a03.f53800a8 = 0;
                    return;
                }
                if (c0358a03.m211982c5()) {
                    t60.m214714d6("OpenDevDelegate", "直接进入了版本信息页面");
                    c0358a03.f53800a8 = 0;
                    return;
                }
                int i = c0358a03.f53800a8;
                int i2 = c0358a03.f53801a9;
                if (i >= i2) {
                    tz0.m214806a6("关于手机页面打开失败，重试次数已达上限(", i2, ")", "OpenDevDelegate");
                    c0358a03.f53800a8 = 0;
                    c0358a03.m211976a5();
                    return;
                } else {
                    t60.m214726f4("OpenDevDelegate", "关于手机页面未打开，重试");
                    c0358a03.f53792a0.performGlobalAction(1);
                    Thread.sleep(3 * 200);
                    c0358a03.m211984c7();
                    return;
                }
        }
    }
}
