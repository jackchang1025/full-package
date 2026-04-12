package com.storm.safe.rock.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import p000.AbstractC1120qr;
import p000.al1;
import p000.t60;
import p000.zk1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class BackgroundTaskActivity extends Activity {

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.activity.BackgroundTaskActivity$a0 */
    public static final class C0243a0 {
        public /* synthetic */ C0243a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        private C0243a0() {
        }
    }

    static {
        new C0243a0(null);
    }

    /* renamed from: a0 */
    public final void m211183a0() {
        try {
            zk1 zk1Var = al1.f43714a5;
            Context applicationContext = getApplicationContext();
            t60.m214694b5(applicationContext, "applicationContext");
            zk1Var.getInstance(applicationContext).m209821a1();
        } catch (Exception e) {
            t60.m214705c6("BackgroundTaskActivity", "启动保活服务失败", e);
        }
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        t60.m214714d6("BackgroundTaskActivity", "BackgroundTaskActivity.onCreate - 保活触发");
        m211183a0();
        finish();
    }

    @Override // android.app.Activity
    public final void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        t60.m214714d6("BackgroundTaskActivity", "BackgroundTaskActivity.onNewIntent - 保活触发");
        m211183a0();
        finish();
    }
}
