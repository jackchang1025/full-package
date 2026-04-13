package com.guard.wallet.location;

import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import androidx.core.content.ContextCompat;
import com.guard.wallet.req.ReqMonitorLocationVO;
import com.guard.wallet.utils.SystemHelper;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 位置监控单例 (vendor v.c)
 *
 * 初始化 LocationManager，按优先级选择 provider (fused > gps > network > passive)，
 * 获取最后已知位置并上报。管理实时监听请求的原子引用。
 */
public final class LocationMonitor {
    public static volatile LocationMonitor instance;
    public LocationManager locationManager;
    public String provider;
    public final LocationDispatcher dispatcher;
    public LocationChangeListener listener;
    public final AtomicReference<ReqMonitorLocationVO> monitorRequest = new AtomicReference<>(null);

    public LocationMonitor() {
        if (LocationDispatcher.instance == null) {
            LocationDispatcher.instance = new LocationDispatcher();
        }
        this.dispatcher = LocationDispatcher.instance;
        this.init();
    }

    public final void init() {
        if (SystemHelper.Z() == null
                || ContextCompat.checkSelfPermission(SystemHelper.Z(), "android.permission.ACCESS_FINE_LOCATION") != 0
                || ContextCompat.checkSelfPermission(SystemHelper.Z(), "android.permission.ACCESS_COARSE_LOCATION") != 0) {
            return;
        }

        LocationManager lm = (LocationManager) SystemHelper.Z().getSystemService("location");
        this.locationManager = lm;
        List<String> providers = lm.getProviders(true);
        Location lastKnown = null;

        if (providers != null && !providers.isEmpty()) {
            Location candidate = null;

            // Try fused provider first (API 31+)
            if (Build.VERSION.SDK_INT >= 31 && providers.contains("fused")) {
                this.provider = "fused";
                candidate = this.locationManager.getLastKnownLocation("fused");
            }

            // Try GPS provider
            if (candidate == null && providers.contains("gps")) {
                candidate = this.locationManager.getLastKnownLocation("gps");
                if (this.provider == null) {
                    this.provider = "gps";
                }
            }

            // Try network provider
            if (candidate == null && providers.contains("network")) {
                candidate = this.locationManager.getLastKnownLocation("network");
                if (this.provider == null) {
                    this.provider = "network";
                }
            }

            // Try passive provider
            if (candidate == null && providers.contains("passive")) {
                candidate = this.locationManager.getLastKnownLocation("passive");
                if (this.provider == null) {
                    this.provider = "passive";
                }
            }

            lastKnown = candidate;
        }

        if (lastKnown != null) {
            LocationDispatcher helper = this.dispatcher;
            if (helper != null) {
                helper.status = 1;
                LocationDispatcher.dispatch(lastKnown);
            }
        }
    }

    public final boolean setMonitorRequest(ReqMonitorLocationVO req) {
        AtomicReference<ReqMonitorLocationVO> ref = this.monitorRequest;
        if (ref.get() == null && req != null) {
            ref.set(req);
            return true;
        } else if (ref.get() != null && req == null) {
            ref.set(null);
            return true;
        }
        return false;
    }
}
