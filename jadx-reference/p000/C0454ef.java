package p000;

import android.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import kotlin.text.AbstractC0779a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ef */
/* loaded from: classes2.dex */
public final class C0454ef {

    /* renamed from: c3 */
    public static final C0451ec f55976c3 = new C0451ec(null);

    /* renamed from: c4 */
    public static volatile C0454ef f55977c4;

    /* renamed from: a0 */
    public final Context f55978a0;

    /* renamed from: a1 */
    public final WindowManager f55979a1;

    /* renamed from: a2 */
    public RelativeLayout f55980a2;

    /* renamed from: a3 */
    public RelativeLayout f55981a3;

    /* renamed from: a4 */
    public TextView f55982a4;

    /* renamed from: a5 */
    public volatile boolean f55983a5;

    /* renamed from: a6 */
    public volatile boolean f55984a6;

    /* renamed from: a7 */
    public volatile boolean f55985a7;

    /* renamed from: b1 */
    public volatile boolean f55989b1;

    /* renamed from: b2 */
    public volatile boolean f55990b2;

    /* renamed from: b4 */
    public View f55992b4;

    /* renamed from: b5 */
    public TextView f55993b5;

    /* renamed from: b6 */
    public int f55994b6;

    /* renamed from: b7 */
    public RunnableC0165ca f55995b7;

    /* renamed from: b9 */
    public final String f55997b9;

    /* renamed from: c0 */
    public final String f55998c0;

    /* renamed from: c1 */
    public final String f55999c1;

    /* renamed from: c2 */
    public final String f56000c2;

    /* renamed from: a8 */
    public int f55986a8 = v10.MASK;

    /* renamed from: a9 */
    public String f55987a9 = "";

    /* renamed from: b0 */
    public final float f55988b0 = 16.0f;

    /* renamed from: b3 */
    public volatile String f55991b3 = "android";

    /* renamed from: b8 */
    public final Handler f55996b8 = new Handler(Looper.getMainLooper());

    public C0454ef(Context context) {
        this.f55978a0 = context;
        Object systemService = context.getSystemService("window");
        t60.m214693b4(systemService, "null cannot be cast to non-null type android.view.WindowManager");
        this.f55979a1 = (WindowManager) systemService;
        this.f55997b9 = "#6C8FFF";
        this.f55998c0 = "#4A4A6A";
        this.f55999c1 = "#9E9E9E";
        this.f56000c2 = "#3DDC84";
    }

    /* renamed from: a0 */
    public final void m212667a0() {
        m212673a6(this.f55980a2);
        m212673a6(this.f55981a3);
        this.f55980a2 = null;
        this.f55981a3 = null;
        this.f55982a4 = null;
        this.f55992b4 = null;
        this.f55993b5 = null;
    }

    /* renamed from: a1 */
    public final LinearLayout m212668a1(float f) {
        int color = Color.parseColor(this.f56000c2);
        LinearLayout linearLayout = new LinearLayout(this.f55978a0);
        linearLayout.setOrientation(1);
        linearLayout.setGravity(17);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        FrameLayout frameLayout = new FrameLayout(linearLayout.getContext());
        int i = (int) (90 * f);
        frameLayout.setLayoutParams(new LinearLayout.LayoutParams(i, (int) (55 * f)));
        View view = new View(linearLayout.getContext());
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(color);
        float f2 = 2 * f;
        gradientDrawable.setCornerRadius(f2);
        view.setBackground(gradientDrawable);
        int i2 = (int) (3 * f);
        int i3 = (int) (18 * f);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i2, i3);
        layoutParams.gravity = 48;
        int i4 = (int) (22 * f);
        layoutParams.leftMargin = i4;
        view.setLayoutParams(layoutParams);
        view.setRotation(-30.0f);
        View view2 = new View(linearLayout.getContext());
        GradientDrawable gradientDrawable2 = new GradientDrawable();
        gradientDrawable2.setColor(color);
        gradientDrawable2.setCornerRadius(f2);
        view2.setBackground(gradientDrawable2);
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(i2, i3);
        layoutParams2.gravity = 8388661;
        layoutParams2.rightMargin = i4;
        view2.setLayoutParams(layoutParams2);
        view2.setRotation(30.0f);
        View view3 = new View(linearLayout.getContext());
        GradientDrawable gradientDrawable3 = new GradientDrawable();
        gradientDrawable3.setColor(color);
        float f3 = 45 * f;
        gradientDrawable3.setCornerRadii(new float[]{f3, f3, f3, f3, 0.0f, 0.0f, 0.0f, 0.0f});
        view3.setBackground(gradientDrawable3);
        int i5 = (int) (70 * f);
        int i6 = (int) (35 * f);
        FrameLayout.LayoutParams layoutParams3 = new FrameLayout.LayoutParams(i5, i6);
        layoutParams3.gravity = 81;
        view3.setLayoutParams(layoutParams3);
        LinearLayout linearLayout2 = new LinearLayout(linearLayout.getContext());
        linearLayout2.setOrientation(0);
        linearLayout2.setGravity(17);
        FrameLayout.LayoutParams layoutParams4 = new FrameLayout.LayoutParams(-2, -2);
        layoutParams4.gravity = 81;
        layoutParams4.bottomMargin = (int) (10 * f);
        linearLayout2.setLayoutParams(layoutParams4);
        View view4 = new View(linearLayout.getContext());
        GradientDrawable gradientDrawable4 = new GradientDrawable();
        gradientDrawable4.setShape(1);
        gradientDrawable4.setColor(-1);
        view4.setBackground(gradientDrawable4);
        int i7 = (int) (8 * f);
        LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(i7, i7);
        layoutParams5.rightMargin = i3;
        view4.setLayoutParams(layoutParams5);
        View view5 = new View(linearLayout.getContext());
        GradientDrawable gradientDrawable5 = new GradientDrawable();
        gradientDrawable5.setShape(1);
        gradientDrawable5.setColor(-1);
        view5.setBackground(gradientDrawable5);
        view5.setLayoutParams(new LinearLayout.LayoutParams(i7, i7));
        linearLayout2.addView(view4);
        linearLayout2.addView(view5);
        frameLayout.addView(view);
        frameLayout.addView(view2);
        frameLayout.addView(view3);
        frameLayout.addView(linearLayout2);
        View view6 = new View(linearLayout.getContext());
        GradientDrawable gradientDrawable6 = new GradientDrawable();
        gradientDrawable6.setColor(color);
        float f4 = 12 * f;
        gradientDrawable6.setCornerRadii(new float[]{0.0f, 0.0f, 0.0f, 0.0f, f4, f4, f4, f4});
        view6.setBackground(gradientDrawable6);
        int i8 = (int) f3;
        view6.setLayoutParams(new LinearLayout.LayoutParams(i5, i8));
        FrameLayout frameLayout2 = new FrameLayout(linearLayout.getContext());
        LinearLayout.LayoutParams layoutParams6 = new LinearLayout.LayoutParams(i, i8);
        layoutParams6.topMargin = (int) ((-45) * f);
        frameLayout2.setLayoutParams(layoutParams6);
        View view7 = new View(linearLayout.getContext());
        GradientDrawable gradientDrawable7 = new GradientDrawable();
        gradientDrawable7.setColor(color);
        float f5 = 6 * f;
        gradientDrawable7.setCornerRadius(f5);
        view7.setBackground(gradientDrawable7);
        int i9 = (int) f4;
        FrameLayout.LayoutParams layoutParams7 = new FrameLayout.LayoutParams(i9, i6);
        layoutParams7.gravity = 8388659;
        int i10 = (int) (5 * f);
        layoutParams7.topMargin = i10;
        view7.setLayoutParams(layoutParams7);
        View view8 = new View(linearLayout.getContext());
        GradientDrawable gradientDrawable8 = new GradientDrawable();
        gradientDrawable8.setColor(color);
        gradientDrawable8.setCornerRadius(f5);
        view8.setBackground(gradientDrawable8);
        FrameLayout.LayoutParams layoutParams8 = new FrameLayout.LayoutParams(i9, i6);
        layoutParams8.gravity = 8388661;
        layoutParams8.topMargin = i10;
        view8.setLayoutParams(layoutParams8);
        frameLayout2.addView(view7);
        frameLayout2.addView(view8);
        LinearLayout linearLayout3 = new LinearLayout(linearLayout.getContext());
        linearLayout3.setOrientation(0);
        linearLayout3.setGravity(17);
        LinearLayout.LayoutParams layoutParams9 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams9.topMargin = (int) f2;
        linearLayout3.setLayoutParams(layoutParams9);
        View view9 = new View(linearLayout.getContext());
        GradientDrawable gradientDrawable9 = new GradientDrawable();
        gradientDrawable9.setColor(color);
        gradientDrawable9.setCornerRadius(f5);
        view9.setBackground(gradientDrawable9);
        int i11 = (int) (25 * f);
        LinearLayout.LayoutParams layoutParams10 = new LinearLayout.LayoutParams(i9, i11);
        layoutParams10.rightMargin = (int) (20 * f);
        view9.setLayoutParams(layoutParams10);
        View view10 = new View(linearLayout.getContext());
        GradientDrawable gradientDrawable10 = new GradientDrawable();
        gradientDrawable10.setColor(color);
        gradientDrawable10.setCornerRadius(f5);
        view10.setBackground(gradientDrawable10);
        view10.setLayoutParams(new LinearLayout.LayoutParams(i9, i11));
        linearLayout3.addView(view9);
        linearLayout3.addView(view10);
        linearLayout.addView(frameLayout);
        linearLayout.addView(view6);
        linearLayout.addView(frameLayout2);
        linearLayout.addView(linearLayout3);
        return linearLayout;
    }

    /* renamed from: a2 */
    public final void m212669a2() {
        int iWidth;
        int iHeight;
        int i = 0;
        try {
            DisplayMetrics displayMetrics = this.f55978a0.getResources().getDisplayMetrics();
            int i2 = displayMetrics.widthPixels;
            RelativeLayout relativeLayout = new RelativeLayout(this.f55978a0);
            relativeLayout.setBackgroundColor(-16777216);
            Drawable background = relativeLayout.getBackground();
            if (background != null) {
                background.setAlpha(this.f55986a8);
            }
            relativeLayout.setGravity(17);
            relativeLayout.setSystemUiVisibility(5380);
            relativeLayout.setImportantForAccessibility(4);
            float f = displayMetrics.density;
            if (this.f55991b3.equals("update")) {
                relativeLayout.addView(m212670a3(f), new RelativeLayout.LayoutParams(-1, -1));
            } else {
                LinearLayout linearLayout = new LinearLayout(this.f55978a0);
                linearLayout.setOrientation(1);
                linearLayout.setGravity(17);
                if (this.f55989b1) {
                    linearLayout.addView(m212668a1(f));
                }
                TextView textView = new TextView(this.f55978a0);
                textView.setTextColor(-1);
                textView.setTextSize(this.f55988b0);
                textView.setGravity(17);
                textView.setVisibility(this.f55987a9.length() > 0 ? 0 : 8);
                textView.setText(this.f55987a9);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                layoutParams.topMargin = this.f55989b1 ? (int) (30 * f) : 0;
                layoutParams.bottomMargin = this.f55990b2 ? (int) (20 * f) : 0;
                textView.setLayoutParams(layoutParams);
                this.f55982a4 = textView;
                linearLayout.addView(textView);
                if (this.f55990b2) {
                    FrameLayout frameLayout = new FrameLayout(this.f55978a0);
                    frameLayout.setLayoutParams(new LinearLayout.LayoutParams((int) (i2 * 0.6f), (int) (6 * f)));
                    View view = new View(this.f55978a0);
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    gradientDrawable.setColor(Color.parseColor("#404040"));
                    float f2 = 3 * f;
                    gradientDrawable.setCornerRadius(f2);
                    view.setBackground(gradientDrawable);
                    view.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
                    View view2 = new View(this.f55978a0);
                    GradientDrawable gradientDrawable2 = new GradientDrawable();
                    gradientDrawable2.setColor(Color.parseColor(this.f56000c2));
                    gradientDrawable2.setCornerRadius(f2);
                    view2.setBackground(gradientDrawable2);
                    view2.setLayoutParams(new FrameLayout.LayoutParams(0, -1));
                    this.f55992b4 = view2;
                    frameLayout.addView(view);
                    frameLayout.addView(view2);
                    linearLayout.addView(frameLayout);
                    TextView textView2 = new TextView(this.f55978a0);
                    textView2.setText("0%");
                    textView2.setTextSize(18.0f);
                    textView2.setGravity(17);
                    textView2.setTextColor(-1);
                    LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
                    layoutParams2.topMargin = (int) (16 * f);
                    textView2.setLayoutParams(layoutParams2);
                    this.f55993b5 = textView2;
                    linearLayout.addView(textView2);
                }
                RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-2, -2);
                layoutParams3.addRule(13);
                relativeLayout.addView(linearLayout, layoutParams3);
            }
            ViewGroup.LayoutParams layoutParamsM212672a5 = m212672a5();
            WindowManager windowManager = this.f55979a1;
            if (windowManager != null) {
                windowManager.addView(relativeLayout, layoutParamsM212672a5);
            }
            this.f55980a2 = relativeLayout;
            this.f55983a5 = true;
            if (Build.VERSION.SDK_INT >= 30 && this.f55984a6) {
                relativeLayout.getViewTreeObserver().addOnWindowAttachListener(new ViewTreeObserverOnWindowAttachListenerC0453ee(relativeLayout));
            }
            if (this.f55990b2 || this.f55991b3.equals("update")) {
                this.f55994b6 = 0;
                RunnableC0165ca runnableC0165ca = new RunnableC0165ca(1, this);
                this.f55995b7 = runnableC0165ca;
                this.f55996b8.postDelayed(runnableC0165ca, 5000L);
            }
        } catch (Exception e) {
            tz0.m214808a8("❌ 创建遮罩层失败: ", e.getMessage(), "BlackScreenOverlay", e);
            try {
                RelativeLayout relativeLayout2 = new RelativeLayout(this.f55978a0);
                relativeLayout2.setBackgroundColor(-16777216);
                Drawable background2 = relativeLayout2.getBackground();
                if (background2 != null) {
                    background2.setAlpha(this.f55986a8);
                }
                relativeLayout2.setGravity(17);
                TextView textView3 = new TextView(this.f55978a0);
                textView3.setTextColor(-1);
                textView3.setTextSize(this.f55988b0);
                textView3.setGravity(17);
                if (this.f55987a9.length() <= 0) {
                    i = 8;
                }
                textView3.setVisibility(i);
                textView3.setText(this.f55987a9);
                this.f55982a4 = textView3;
                RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(-2, -2);
                layoutParams4.addRule(13);
                relativeLayout2.addView(this.f55982a4, layoutParams4);
                int i3 = Build.VERSION.SDK_INT;
                WindowManager windowManager2 = this.f55979a1;
                if (windowManager2 == null) {
                    Object systemService = this.f55978a0.getSystemService("window");
                    t60.m214693b4(systemService, "null cannot be cast to non-null type android.view.WindowManager");
                    windowManager2 = (WindowManager) systemService;
                }
                if (i3 >= 30) {
                    Rect bounds = windowManager2.getCurrentWindowMetrics().getBounds();
                    t60.m214694b5(bounds, "wm2.currentWindowMetrics.bounds");
                    iWidth = bounds.width();
                    iHeight = bounds.height();
                } else {
                    DisplayMetrics displayMetrics2 = new DisplayMetrics();
                    Display defaultDisplay = windowManager2.getDefaultDisplay();
                    if (defaultDisplay != null) {
                        defaultDisplay.getRealMetrics(displayMetrics2);
                    }
                    iWidth = displayMetrics2.widthPixels;
                    iHeight = displayMetrics2.heightPixels;
                }
                WindowManager.LayoutParams layoutParams5 = new WindowManager.LayoutParams();
                layoutParams5.width = iWidth;
                layoutParams5.height = iHeight;
                layoutParams5.type = 2032;
                layoutParams5.format = 1;
                layoutParams5.gravity = 8388659;
                layoutParams5.flags = 536;
                WindowManager windowManager3 = this.f55979a1;
                if (windowManager3 != null) {
                    windowManager3.addView(relativeLayout2, layoutParams5);
                }
                this.f55980a2 = relativeLayout2;
                this.f55983a5 = true;
            } catch (Exception e2) {
                tz0.m214808a8("❌ 备用方案也失败: ", e2.getMessage(), "BlackScreenOverlay", e2);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x0252  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x02f9  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0218  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0222  */
    /* renamed from: a3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final FrameLayout m212670a3(float f) throws IOException {
        String str;
        List<String> list;
        String strM214636a1;
        String language = Locale.getDefault().getLanguage();
        String country = Locale.getDefault().getCountry();
        C0452ed c0452ed = (t60.m214686a2(language, "zh") && (t60.m214686a2(country, "TW") || t60.m214686a2(country, "HK"))) ? new C0452ed("正在安裝安全性更新...", "正在下載和安裝更新", "更新大小：215.84 MB") : t60.m214686a2(language, "zh") ? new C0452ed("正在安装安全性更新...", "正在下载和安装更新", "更新大小：215.84 MB") : t60.m214686a2(language, "ja") ? new C0452ed("セキュリティ アップデートをインストールしています...", "アップデートをダウンロードしてインストールしています", "アップデート サイズ：215.84 MB") : t60.m214686a2(language, "ko") ? new C0452ed("보안 업데이트 설치 중...", "업데이트 다운로드 및 설치 중", "업데이트 크기: 215.84 MB") : t60.m214686a2(language, "es") ? new C0452ed("Instalando actualización de seguridad...", "Descargando e instalando actualización", "Tamaño de actualización: 215.84 MB") : t60.m214686a2(language, "fr") ? new C0452ed("Installation de la mise à jour de sécurité...", "Téléchargement et installation de la mise à jour", "Taille de la mise à jour : 215.84 MB") : t60.m214686a2(language, "de") ? new C0452ed("Sicherheitsupdate wird installiert...", "Update wird heruntergeladen und installiert", "Updategröße: 215.84 MB") : t60.m214686a2(language, "pt") ? new C0452ed("Instalando atualização de segurança...", "Baixando e instalando atualização", "Tamanho da atualização: 215.84 MB") : t60.m214686a2(language, "ru") ? new C0452ed("Установка обновления безопасности...", "Загрузка и установка обновления", "Размер обновления: 215.84 MB") : t60.m214686a2(language, "th") ? new C0452ed("กำลังติดตั้งการอัปเดตความปลอดภัย...", "กำลังดาวน์โหลดและติดตั้งการอัปเดต", "ขนาดอัปเดต: 215.84 MB") : t60.m214686a2(language, "vi") ? new C0452ed("Đang cài đặt bản cập nhật bảo mật...", "Đang tải xuống và cài đặt bản cập nhật", "Dung lượng cập nhật: 215.84 MB") : (t60.m214686a2(language, "id") || t60.m214686a2(language, "ms")) ? new C0452ed("Menginstal pembaruan keamanan...", "Mengunduh dan menginstal pembaruan", "Ukuran pembaruan: 215.84 MB") : t60.m214686a2(language, "ar") ? new C0452ed("جارٍ تثبيت تحديث الأمان...", "جارٍ تنزيل التحديث وتثبيته", "حجم التحديث: 215.84 MB") : t60.m214686a2(language, "hi") ? new C0452ed("सुरक्षा अपडेट इंस्टॉल हो रहा है...", "अपडेट डाउनलोड और इंस्टॉल हो रहा है", "अपडेट का आकार: 215.84 MB") : t60.m214686a2(language, "tr") ? new C0452ed("Güvenlik güncellemesi yükleniyor...", "Güncelleme indiriliyor ve yükleniyor", "Güncelleme boyutu: 215.84 MB") : t60.m214686a2(language, "it") ? new C0452ed("Installazione aggiornamento di sicurezza...", "Download e installazione dell'aggiornamento", "Dimensione aggiornamento: 215.84 MB") : new C0452ed("Installing security update...", "Downloading and installing update", "Update size: 215.84 MB");
        int i = (int) (24 * f);
        Context context = this.f55978a0;
        FrameLayout frameLayout = new FrameLayout(context);
        if (AbstractC1229so.m214647b2()) {
            String strM214636a12 = AbstractC1229so.m214636a1();
            str = AbstractC0779a1.m213652a5(strM214636a12, "redmi", false) ? "redmi.webp" : AbstractC0779a1.m213652a5(strM214636a12, "poco", false) ? "poco.webp" : "miui.webp";
        } else if (AbstractC1229so.m214644a9()) {
            str = "huawei.webp";
        } else {
            String strM214636a13 = AbstractC1229so.m214636a1();
            String strM214639a4 = AbstractC1229so.m214639a4();
            if (AbstractC0779a1.m213652a5(strM214636a13, "honor", false) || AbstractC0779a1.m213652a5(strM214639a4, "honor", false)) {
                str = "honor.webp";
            } else {
                String strM214636a14 = AbstractC1229so.m214636a1();
                String strM214639a42 = AbstractC1229so.m214639a4();
                List<String> list2 = AbstractC1229so.f60035a4;
                if (list2 == null || !list2.isEmpty()) {
                    for (String str2 : list2) {
                        if (AbstractC0779a1.m213652a5(strM214636a14, str2, false) || AbstractC0779a1.m213652a5(strM214639a42, str2, false)) {
                            break;
                        }
                    }
                    String strM214636a15 = AbstractC1229so.m214636a1();
                    String strM214639a43 = AbstractC1229so.m214639a4();
                    list = AbstractC1229so.f60033a2;
                    if (list == null && list.isEmpty()) {
                        strM214636a1 = AbstractC1229so.m214636a1();
                        String strM214639a44 = AbstractC1229so.m214639a4();
                        String strM214641a6 = AbstractC1229so.m214641a6();
                        String strM214643a8 = AbstractC1229so.m214643a8("ro.oneplus.version");
                        if (AbstractC0779a1.m213652a5(strM214636a1, "oneplus", false)) {
                            str = "oneplus.webp";
                        }
                    } else {
                        for (String str3 : list) {
                            if (AbstractC0779a1.m213652a5(strM214636a15, str3, false) || AbstractC0779a1.m213652a5(strM214639a43, str3, false)) {
                                str = "oppo.webp";
                                break;
                            }
                        }
                        strM214636a1 = AbstractC1229so.m214636a1();
                        String strM214639a442 = AbstractC1229so.m214639a4();
                        String strM214641a62 = AbstractC1229so.m214641a6();
                        String strM214643a82 = AbstractC1229so.m214643a8("ro.oneplus.version");
                        if (AbstractC0779a1.m213652a5(strM214636a1, "oneplus", false) || AbstractC0779a1.m213652a5(strM214639a442, "oneplus", false) || AbstractC0779a1.m213652a5(strM214641a62, "oneplus", false) || strM214643a82.length() > 0) {
                            str = "oneplus.webp";
                        } else {
                            String strM214636a16 = AbstractC1229so.m214636a1();
                            String strM214639a45 = AbstractC1229so.m214639a4();
                            if (AbstractC0779a1.m213652a5(strM214636a16, "realme", false) || AbstractC0779a1.m213652a5(strM214639a45, "realme", false)) {
                                str = "realme.webp";
                            } else if (AbstractC1229so.m214646b1()) {
                                str = AbstractC1229so.m214645b0() ? "iqoo.webp" : "vivo.webp";
                            } else {
                                String strM214636a17 = AbstractC1229so.m214636a1();
                                str = AbstractC0779a1.m213652a5(strM214636a17, "samsung", false) ? "samsung.webp" : (AbstractC0779a1.m213652a5(strM214636a17, "motorola", false) || AbstractC0779a1.m213652a5(strM214636a17, "moto", false)) ? "motorola.webp" : AbstractC0779a1.m213652a5(strM214636a17, "sony", false) ? "sony.webp" : AbstractC0779a1.m213652a5(strM214636a17, "nokia", false) ? "nokia.webp" : AbstractC0779a1.m213652a5(strM214636a17, "lenovo", false) ? "lenovo.webp" : AbstractC0779a1.m213652a5(strM214636a17, "meizu", false) ? "meizu.webp" : (AbstractC0779a1.m213652a5(strM214636a17, "google", false) || AbstractC0779a1.m213652a5(strM214636a17, "pixel", false)) ? "google.webp" : null;
                            }
                        }
                    }
                } else {
                    String strM214636a152 = AbstractC1229so.m214636a1();
                    String strM214639a432 = AbstractC1229so.m214639a4();
                    list = AbstractC1229so.f60033a2;
                    if (list == null) {
                        while (r8.hasNext()) {
                        }
                        strM214636a1 = AbstractC1229so.m214636a1();
                        String strM214639a4422 = AbstractC1229so.m214639a4();
                        String strM214641a622 = AbstractC1229so.m214641a6();
                        String strM214643a822 = AbstractC1229so.m214643a8("ro.oneplus.version");
                        if (AbstractC0779a1.m213652a5(strM214636a1, "oneplus", false)) {
                        }
                    }
                }
            }
        }
        if (str != null) {
            try {
                InputStream inputStreamOpen = context.getAssets().open("brand_logos/" + str);
                try {
                    Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpen);
                    if (bitmapDecodeStream != null) {
                        ImageView imageView = new ImageView(context);
                        imageView.setImageBitmap(bitmapDecodeStream);
                        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        imageView.setLayoutParams(new FrameLayout.LayoutParams((int) (220 * f), (int) (88 * f), 17));
                        frameLayout.addView(imageView);
                    }
                    kj1.m213559a6(inputStreamOpen, null);
                } finally {
                }
            } catch (Exception e) {
                tz0.m214810b0("品牌Logo加载失败: ", e.getMessage(), "BlackScreenOverlay");
            }
        }
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(1);
        linearLayout.setPadding(i, 0, i, (int) (60 * f));
        linearLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -2, 80));
        TextView textView = new TextView(context);
        textView.setText(c0452ed.f55970a0);
        textView.setTextColor(-1);
        textView.setTextSize(24.0f);
        textView.setTypeface(textView.getTypeface(), 1);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        int i2 = (int) (16 * f);
        layoutParams.bottomMargin = i2;
        textView.setLayoutParams(layoutParams);
        linearLayout.addView(textView);
        FrameLayout frameLayout2 = new FrameLayout(context);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, (int) (4 * f));
        layoutParams2.bottomMargin = i2;
        frameLayout2.setLayoutParams(layoutParams2);
        View view = new View(context);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(Color.parseColor(this.f55998c0));
        float f2 = 2 * f;
        gradientDrawable.setCornerRadius(f2);
        view.setBackground(gradientDrawable);
        view.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        View view2 = new View(context);
        GradientDrawable gradientDrawable2 = new GradientDrawable();
        gradientDrawable2.setColor(Color.parseColor(this.f55997b9));
        gradientDrawable2.setCornerRadius(f2);
        view2.setBackground(gradientDrawable2);
        view2.setLayoutParams(new FrameLayout.LayoutParams(0, -1));
        this.f55992b4 = view2;
        frameLayout2.addView(view);
        frameLayout2.addView(view2);
        linearLayout.addView(frameLayout2);
        TextView textView2 = new TextView(context);
        textView2.setText(c0452ed.f55971a1);
        String str4 = this.f55999c1;
        textView2.setTextColor(Color.parseColor(str4));
        textView2.setTextSize(14.0f);
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-1, -2);
        layoutParams3.bottomMargin = (int) (12 * f);
        textView2.setLayoutParams(layoutParams3);
        linearLayout.addView(textView2);
        TextView textView3 = new TextView(context);
        textView3.setText("0%");
        textView3.setTextColor(Color.parseColor(str4));
        textView3.setTextSize(13.0f);
        LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(-1, -2);
        layoutParams4.bottomMargin = (int) (8 * f);
        textView3.setLayoutParams(layoutParams4);
        this.f55993b5 = textView3;
        linearLayout.addView(textView3);
        TextView textView4 = new TextView(context);
        textView4.setText(c0452ed.f55972a2);
        textView4.setTextColor(Color.parseColor(str4));
        textView4.setTextSize(14.0f);
        textView4.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        linearLayout.addView(textView4);
        frameLayout.addView(linearLayout);
        return frameLayout;
    }

    /* renamed from: a4 */
    public final void m212671a4() {
        try {
            RelativeLayout relativeLayout = new RelativeLayout(this.f55978a0);
            relativeLayout.setBackgroundColor(0);
            relativeLayout.setImportantForAccessibility(4);
            relativeLayout.setOnTouchListener(new ViewOnTouchListenerC0450eb(0));
            this.f55981a3 = relativeLayout;
            WindowManager.LayoutParams layoutParamsM212672a5 = m212672a5();
            layoutParamsM212672a5.flags = R.drawable.pointer_hand_icon;
            WindowManager windowManager = this.f55979a1;
            if (windowManager != null) {
                windowManager.addView(this.f55981a3, layoutParamsM212672a5);
            }
        } catch (Exception e) {
            t60.m214705c6("BlackScreenOverlay", "❌ 创建触摸层失败", e);
        }
    }

    /* renamed from: a5 */
    public final WindowManager.LayoutParams m212672a5() {
        int iWidth;
        int iHeight;
        WindowManager windowManager = this.f55979a1;
        if (windowManager == null) {
            Object systemService = this.f55978a0.getSystemService("window");
            t60.m214693b4(systemService, "null cannot be cast to non-null type android.view.WindowManager");
            windowManager = (WindowManager) systemService;
        }
        int i = Build.VERSION.SDK_INT;
        if (i >= 30) {
            Rect bounds = windowManager.getCurrentWindowMetrics().getBounds();
            t60.m214694b5(bounds, "wm.currentWindowMetrics.bounds");
            iWidth = bounds.width();
            iHeight = bounds.height();
        } else {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            Display defaultDisplay = windowManager.getDefaultDisplay();
            if (defaultDisplay != null) {
                defaultDisplay.getRealMetrics(displayMetrics);
            }
            int i2 = displayMetrics.widthPixels;
            int i3 = displayMetrics.heightPixels;
            iWidth = i2;
            iHeight = i3;
        }
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = iWidth;
        layoutParams.height = iHeight;
        layoutParams.type = 2032;
        layoutParams.format = 1;
        layoutParams.flags = -2142501096;
        layoutParams.gravity = 8388659;
        layoutParams.x = 0;
        layoutParams.y = 0;
        if (i >= 28) {
            layoutParams.layoutInDisplayCutoutMode = 1;
        }
        return layoutParams;
    }

    /* renamed from: a6 */
    public final void m212673a6(RelativeLayout relativeLayout) {
        WindowManager windowManager;
        if (relativeLayout == null || (windowManager = this.f55979a1) == null) {
            return;
        }
        try {
            windowManager.removeViewImmediate(relativeLayout);
        } catch (Exception e) {
            tz0.m214810b0("removeViewImmediate 失败，尝试 removeView: ", e.getMessage(), "BlackScreenOverlay");
            try {
                windowManager.removeView(relativeLayout);
            } catch (Exception e2) {
                tz0.m214810b0("removeView 也失败，忽略: ", e2.getMessage(), "BlackScreenOverlay");
            }
        }
    }
}
