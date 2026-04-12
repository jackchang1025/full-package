package p000;

import com.storm.safe.rock.service.modules.setup.C0360a2;
import org.conscrypt.FileClientSessionCache;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final /* synthetic */ class c41 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f46070a0;

    /* renamed from: a1 */
    public final /* synthetic */ C0360a2 f46071a1;

    public /* synthetic */ c41(C0360a2 c0360a2, int i) {
        this.f46070a0 = i;
        this.f46071a1 = c0360a2;
    }

    /* JADX WARN: Removed duplicated region for block: B:51:0x0135 A[Catch: all -> 0x00a4, Exception -> 0x00a7, PHI: r9
      0x0135: PHI (r9v3 int) = (r9v2 int), (r9v5 int), (r9v5 int) binds: [B:33:0x00be, B:47:0x0127, B:49:0x012d] A[DONT_GENERATE, DONT_INLINE], TryCatch #3 {Exception -> 0x00a7, blocks: (B:21:0x0065, B:23:0x007f, B:25:0x0085, B:27:0x008b, B:32:0x00aa, B:34:0x00c0, B:36:0x00d3, B:38:0x00dc, B:40:0x00e7, B:42:0x00f8, B:45:0x0106, B:46:0x010c, B:48:0x0129, B:50:0x012f, B:51:0x0135, B:53:0x015b, B:54:0x0165), top: B:111:0x0065, outer: #1 }] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() throws InterruptedException {
        int i;
        switch (this.f46070a0) {
            case 0:
                try {
                    this.f46071a1.m212066h0();
                    return;
                } catch (Exception e) {
                    t60.m214705c6("SystemOptimize", "handleAllowUsbDebuggingDialog 异常", e);
                    return;
                }
            case 1:
                try {
                    this.f46071a1.m212042c9();
                    return;
                } catch (Exception e2) {
                    t60.m214705c6("SystemOptimize", "【CheckProcess】异常", e2);
                    return;
                }
            case 2:
                C0360a2 c0360a2 = this.f46071a1;
                c0360a2.f53815a0.performGlobalAction(1);
                C0360a2.m212025k1(3);
                c0360a2.m212081i6();
                return;
            case 3:
                C0360a2 c0360a22 = this.f46071a1;
                try {
                    c0360a22.m212070h4(c0360a22.f53851d6.incrementAndGet());
                    return;
                } catch (Exception e3) {
                    t60.m214705c6("SystemOptimize", "【Heartbeat】心跳异常", e3);
                    return;
                }
            case 4:
                C0360a2 c0360a23 = this.f46071a1;
                try {
                    v00.f60540a1 = 0L;
                    i = 1;
                } catch (Exception e4) {
                    t60.m214702c3("SystemOptimize", ">>> 通知 local-service App 包名失败: " + e4.getMessage());
                    return;
                }
                while (true) {
                    if (i >= 11) {
                        t60.m214726f4("SystemOptimize", ">>> local-service 启动超时，跳过通知");
                        try {
                            if (c0360a23.m212037b9("cat /data/local/tmp/local-service.log 2>&1 | tail -50")) {
                                t60.m214704c5("SystemOptimize", ">>> local-service 启动日志: 见上一行 adbR 输出");
                                return;
                            }
                            return;
                        } catch (Exception e5) {
                            t60.m214705c6("SystemOptimize", ">>> 无法读取启动日志", e5);
                            return;
                        }
                    }
                    Thread.sleep(1000L);
                    if (v00.m214889a1()) {
                        v00.f60539a0 = true;
                        v00.f60540a1 = System.currentTimeMillis();
                        t60.m214714d6("SystemOptimize", ">>> local-service 已就绪（等待 " + i + " 秒）");
                        String packageName = c0360a23.f53816a1.getPackageName();
                        boolean z = c0360a23.f53816a1.getSharedPreferences("device_region", 0).getBoolean("is_overseas", false);
                        C0360a2.m212002c8(c0360a23, "/setAppPackage", "{\"package\":\"" + packageName + "\",\"overseas\":" + z + "}", 4);
                        StringBuilder sb = new StringBuilder();
                        sb.append(">>> 已通知 local-service App 包名: ");
                        sb.append(packageName);
                        sb.append(", overseas=");
                        sb.append(z);
                        t60.m214714d6("SystemOptimize", sb.toString());
                        c0360a23.m212077i2();
                        Thread.sleep(2000L);
                        try {
                            C0360a2.m212002c8(c0360a23, "/applyAllOptimizations", null, 6);
                            t60.m214714d6("SystemOptimize", ">>> 已触发 local-service 系统优化");
                            return;
                        } catch (Exception e6) {
                            t60.m214702c3("SystemOptimize", ">>> 系统优化触发失败: " + e6.getMessage());
                            return;
                        }
                    }
                    t60.m214702c3("SystemOptimize", ">>> 等待 local-service 启动 (" + i + "/10)...");
                    i++;
                    t60.m214702c3("SystemOptimize", ">>> 通知 local-service App 包名失败: " + e4.getMessage());
                    return;
                }
            case 5:
                C0360a2 c0360a24 = this.f46071a1;
                t60.m214695b6(c0360a24, "this$0");
                c0360a24.m212094k4();
                return;
            case 6:
                C0360a2 c0360a25 = this.f46071a1;
                t60.m214695b6(c0360a25, "this$0");
                c0360a25.m212103l3();
                return;
            case 7:
                C0360a2 c0360a26 = this.f46071a1;
                c0360a26.f53849d4 = true;
                try {
                    try {
                        t60.m214714d6("SystemOptimize", "【SilentRecover】开始无感恢复 local-service");
                        int iM212064g7 = c0360a26.m212064g7();
                        t60.m214714d6("SystemOptimize", "【SilentRecover】已保存端口: " + iM212064g7);
                        if (iM212064g7 > 0) {
                            if (c0360a26.m212045d3(iM212064g7) || c0360a26.m212036b8()) {
                                t60.m214714d6("SystemOptimize", "【SilentRecover】用已保存端口 " + iM212064g7 + " 连接成功");
                            } else {
                                t60.m214726f4("SystemOptimize", "【SilentRecover】已保存端口 " + iM212064g7 + " 连接失败，回退到重新扫描");
                                c0360a26.m212091k0(0);
                                iM212064g7 = 0;
                            }
                        }
                        if (iM212064g7 <= 0) {
                            t60.m214714d6("SystemOptimize", "【SilentRecover】端口未知，尝试开启无线调试");
                            c0360a26.m212097k7();
                            Thread.sleep(2000L);
                            if (c0360a26.m212073h8()) {
                                t60.m214714d6("SystemOptimize", "【SilentRecover】尝试从 Settings.Global.adb_wifi_port 读取端口");
                                int iM212063g6 = c0360a26.m212063g6();
                                if (iM212063g6 > 0) {
                                    t60.m214714d6("SystemOptimize", "【SilentRecover】从系统设置读取到端口: " + iM212063g6);
                                }
                                if (iM212063g6 <= 0) {
                                    t60.m214714d6("SystemOptimize", "【SilentRecover】使用 netstat 查找端口");
                                    iM212064g7 = c0360a26.m212086j4();
                                } else {
                                    iM212064g7 = iM212063g6;
                                }
                                if (iM212064g7 <= 0) {
                                    t60.m214726f4("SystemOptimize", "【SilentRecover】未找到调试端口，放弃恢复");
                                } else {
                                    c0360a26.m212091k0(iM212064g7);
                                    t60.m214714d6("SystemOptimize", "【SilentRecover】重新扫描到端口: " + iM212064g7 + "，连接 ADB");
                                    if (c0360a26.m212045d3(iM212064g7) || c0360a26.m212036b8()) {
                                        t60.m214714d6("SystemOptimize", "【SilentRecover】使用端口: " + iM212064g7);
                                        t60.m214714d6("SystemOptimize", "【SilentRecover】启动 local-service");
                                        c0360a26.m212038c0();
                                        v00.f60540a1 = 0L;
                                        Thread.sleep(3000L);
                                        if (v00.m214888a0()) {
                                            t60.m214714d6("SystemOptimize", "【SilentRecover】local-service 无感恢复成功");
                                            c0360a26.m212077i2();
                                        } else {
                                            t60.m214726f4("SystemOptimize", "【SilentRecover】local-service 启动后仍无响应");
                                        }
                                    } else {
                                        t60.m214726f4("SystemOptimize", "【SilentRecover】ADB 连接失败，放弃恢复");
                                    }
                                }
                            } else {
                                t60.m214726f4("SystemOptimize", "【SilentRecover】无线调试未开启，放弃恢复");
                            }
                        }
                    } finally {
                        c0360a26.f53849d4 = false;
                    }
                } catch (Exception e7) {
                    t60.m214705c6("SystemOptimize", "【SilentRecover】异常", e7);
                }
                c0360a26.f53849d4 = false;
                return;
            case 8:
                C0360a2 c0360a27 = this.f46071a1;
                t60.m214695b6(c0360a27, "this$0");
                c0360a27.m212093k3();
                return;
            case 9:
                C0360a2 c0360a28 = this.f46071a1;
                t60.m214695b6(c0360a28, "this$0");
                c0360a28.m212093k3();
                return;
            case 10:
                C0360a2 c0360a29 = this.f46071a1;
                t60.m214695b6(c0360a29, "this$0");
                c0360a29.m212093k3();
                return;
            case oe0.DEFAULT_M /* 11 */:
                C0360a2 c0360a210 = this.f46071a1;
                t60.m214695b6(c0360a210, "this$0");
                c0360a210.m212094k4();
                return;
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                C0360a2 c0360a211 = this.f46071a1;
                t60.m214695b6(c0360a211, "this$0");
                c0360a211.m212103l3();
                return;
            default:
                C0360a2 c0360a212 = this.f46071a1;
                try {
                    c0360a212.m212070h4(c0360a212.f53851d6.get());
                    return;
                } catch (Exception e8) {
                    t60.m214705c6("SystemOptimize", "【ContentObserver】触发心跳异常", e8);
                    return;
                }
        }
    }
}
