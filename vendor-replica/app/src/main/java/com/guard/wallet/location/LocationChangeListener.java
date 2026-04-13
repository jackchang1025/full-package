package com.guard.wallet.location;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * 位置变更监听器 (vendor v.b)
 *
 * 实现 LocationListener，将 onLocationChanged 转发给 {@link LocationDispatcher}。
 */
public final class LocationChangeListener implements LocationListener {
    public final LocationMonitor monitor;

    public LocationChangeListener(LocationMonitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public final void onLocationChanged(Location location) {
        LocationDispatcher dispatcher = this.monitor.dispatcher;
        if (dispatcher != null) {
            dispatcher.status = 1;
            LocationDispatcher.dispatch(location);
        }
    }

    @Override
    public final void onProviderDisabled(String provider) {
    }

    @Override
    public final void onProviderEnabled(String provider) {
    }

    @Override
    public final void onStatusChanged(String provider, int status, Bundle extras) {
    }
}
