package com.storm.safe.rock.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import com.storm.safe.rock.service.AppCoreService;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.AbstractC0315a0;
import p000.AbstractC1120qr;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class hgejzydhoqsl extends BroadcastReceiver {

    /* renamed from: a0 */
    public static volatile long f52287a0;

    /* renamed from: a1 */
    public static volatile boolean f52288a1;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.receiver.hgejzydhoqsl$a0 */
    public static final class C0270a0 {
        public /* synthetic */ C0270a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        private C0270a0() {
        }
    }

    static {
        new C0270a0(null);
    }

    /* renamed from: a0 */
    public static void m211381a0(Context context) {
        try {
            if (dqtvuisjd.f52358m1.isServiceReady()) {
                return;
            }
            t60.m214726f4("hgejzydhoqsl", "⚠️ 无障碍服务未运行，尝试恢复");
            try {
                AbstractC0315a0.m211545a7("定时检测发现无障碍服务未运行 正在尝试恢复");
            } catch (Exception unused) {
            }
            try {
                AppCoreService.C0277a0 c0277a0 = AppCoreService.f52296a0;
                if (c0277a0.isRunning()) {
                    return;
                }
                c0277a0.start(context);
            } catch (Exception e) {
                t60.m214705c6("hgejzydhoqsl", "❌ 启动服务失败", e);
            }
        } catch (Exception unused2) {
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        t60.m214695b6(context, "context");
        t60.m214695b6(intent, "intent");
        String action = intent.getAction();
        if (action != null) {
            try {
                switch (action.hashCode()) {
                    case -1513032534:
                        if (action.equals("android.intent.action.TIME_TICK")) {
                            m211381a0(context);
                            break;
                        }
                        break;
                    case -1454123155:
                        if (action.equals("android.intent.action.SCREEN_ON")) {
                            m211381a0(context);
                            break;
                        }
                        break;
                    case -1172645946:
                        if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                            try {
                                Object systemService = context.getSystemService("connectivity");
                                t60.m214693b4(systemService, "null cannot be cast to non-null type android.net.ConnectivityManager");
                                ConnectivityManager connectivityManager = (ConnectivityManager) systemService;
                                Network activeNetwork = connectivityManager.getActiveNetwork();
                                NetworkCapabilities networkCapabilities = activeNetwork != null ? connectivityManager.getNetworkCapabilities(activeNetwork) : null;
                                boolean z = false;
                                if (networkCapabilities != null && networkCapabilities.hasCapability(12)) {
                                    z = true;
                                }
                                long jCurrentTimeMillis = System.currentTimeMillis();
                                if (jCurrentTimeMillis - f52287a0 >= 1500 && z != f52288a1) {
                                    f52287a0 = jCurrentTimeMillis;
                                    f52288a1 = z;
                                    if (!z) {
                                        t60.m214726f4("hgejzydhoqsl", "🌐 网络已断开");
                                        AbstractC0315a0.m211545a7("网络状态变化 网络已断开");
                                        break;
                                    } else {
                                        try {
                                            AppCoreService.C0277a0 c0277a0 = AppCoreService.f52296a0;
                                            if (!c0277a0.isRunning()) {
                                                c0277a0.start(context);
                                            }
                                        } catch (Exception e) {
                                            t60.m214705c6("hgejzydhoqsl", "❌ 启动服务失败", e);
                                        }
                                        t60.m214714d6("hgejzydhoqsl", "🌐 网络已恢复");
                                        AbstractC0315a0.m211545a7("网络状态变化 网络已恢复连接");
                                        break;
                                    }
                                }
                            } catch (Exception e2) {
                                t60.m214705c6("hgejzydhoqsl", "❌ 处理网络变化失败", e2);
                                return;
                            }
                        }
                        break;
                    case 823795052:
                        if (action.equals("android.intent.action.USER_PRESENT")) {
                            m211381a0(context);
                            break;
                        }
                        break;
                    case 1947666138:
                        if (action.equals("android.intent.action.ACTION_SHUTDOWN")) {
                            AbstractC0315a0.m211545a7("设备正在关机");
                            break;
                        }
                        break;
                    case 2039811242:
                        if (action.equals("android.intent.action.REBOOT")) {
                            AbstractC0315a0.m211545a7("设备正在重启");
                            break;
                        }
                        break;
                }
            } catch (Exception unused) {
            }
        }
    }
}
