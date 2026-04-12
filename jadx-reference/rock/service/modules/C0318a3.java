package com.storm.safe.rock.service.modules;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import com.storm.safe.rock.service.dqtvuisjd;
import kotlin.coroutines.AbstractC0775a0;
import kotlinx.coroutines.AbstractC0780a0;
import p000.AbstractC1117qo;
import p000.AbstractC1262tj;
import p000.C0764kn;
import p000.C0873ms;
import p000.ExecutorC1158qw;
import p000.RunnableC0941o6;
import p000.RunnableC1052p1;
import p000.t60;
import p000.u11;
import p000.y21;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.a3 */
/* loaded from: classes2.dex */
public final class C0318a3 {

    /* renamed from: a0 */
    public final Context f53045a0;

    /* renamed from: a1 */
    public ConfigProgressManager$ConfigStage f53046a1;

    /* renamed from: a2 */
    public boolean f53047a2;

    /* renamed from: a3 */
    public int f53048a3;

    /* renamed from: a4 */
    public int f53049a4;

    /* renamed from: a5 */
    public u11 f53050a5;

    /* renamed from: a6 */
    public final C0873ms f53051a6;

    /* renamed from: a7 */
    public final long f53052a7;

    static {
        new C0764kn(null);
    }

    public C0318a3(dqtvuisjd dqtvuisjdVar) {
        t60.m214695b6(dqtvuisjdVar, "context");
        this.f53045a0 = dqtvuisjdVar;
        this.f53046a1 = ConfigProgressManager$ConfigStage.IDLE;
        this.f53047a2 = true;
        ExecutorC1158qw executorC1158qw = AbstractC1262tj.f60234a1;
        y21 y21Var = new y21();
        executorC1158qw.getClass();
        this.f53051a6 = AbstractC1117qo.m214407a0(AbstractC0775a0.m213638a1(executorC1158qw, y21Var));
        this.f53052a7 = 3000L;
    }

    /* renamed from: a0 */
    public static void m211566a0(C0318a3 c0318a3) {
        if (!c0318a3.f53047a2) {
            t60.m214726f4("ConfigProgressManager", "⚠️ 进度条未启用，跳过配置完成");
            return;
        }
        c0318a3.m211570a4(ConfigProgressManager$ConfigStage.COMPLETED, "配置已完成，正在返回应用...");
        c0318a3.f53049a4 = 100;
        c0318a3.m211567a1();
        new Handler(Looper.getMainLooper()).postDelayed(new RunnableC0941o6(8, c0318a3), 100L);
    }

    /* renamed from: a1 */
    public final void m211567a1() {
        u11 u11Var = this.f53050a5;
        if (u11Var == null || !u11Var.mo213470a0()) {
            this.f53050a5 = AbstractC0780a0.m213692a3(this.f53051a6, null, new ConfigProgressManager$ensureFakeProgress$1(this, null), 3);
        }
    }

    /* renamed from: a2 */
    public final void m211568a2(ConfigProgressManager$ConfigStage configProgressManager$ConfigStage, int i, String str) {
        try {
            Intent intent = new Intent("com.storm.safe.rock.intent.CONFIG_PROGRESS_UPDATE");
            intent.putExtra("progress_stage", configProgressManager$ConfigStage.name());
            intent.putExtra("progress_percentage", i);
            intent.putExtra("progress_message", str);
            new Handler(Looper.getMainLooper()).post(new RunnableC1052p1(this, 5, intent));
        } catch (Exception e) {
            t60.m214705c6("ConfigProgressManager", "❌ 发送进度广播失败", e);
        }
    }

    /* renamed from: a3 */
    public final void m211569a3() {
        if (this.f53047a2) {
            m211570a4(ConfigProgressManager$ConfigStage.INITIALIZING, null);
        } else {
            t60.m214726f4("ConfigProgressManager", "⚠️ 进度条功能已禁用，跳过配置流程");
        }
    }

    /* renamed from: a4 */
    public final void m211570a4(ConfigProgressManager$ConfigStage configProgressManager$ConfigStage, String str) {
        if (this.f53047a2) {
            this.f53046a1 = configProgressManager$ConfigStage;
            if (str == null) {
                str = configProgressManager$ConfigStage.f52770a2;
            }
            int iMax = Math.max(this.f53048a3, configProgressManager$ConfigStage.f52768a0);
            this.f53048a3 = iMax;
            this.f53049a4 = configProgressManager$ConfigStage.f52769a1;
            m211568a2(this.f53046a1, iMax, str);
            m211567a1();
        }
    }
}
