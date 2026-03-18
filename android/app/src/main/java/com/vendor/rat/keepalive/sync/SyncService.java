package com.vendor.rat.keepalive.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Vendor: com.guard.wallet.sync.SyncService
 * Service that provides sync adapter binder.
 */
public class SyncService extends Service {

    // ADAPT: vendor uses c0.a (AbstractThreadedSyncAdapter), we use a placeholder
    private static Object syncAdapter;
    private static final Object lock = new Object();

    @Override
    public void onCreate() {
        super.onCreate();
        synchronized (lock) {
            if (syncAdapter == null) {
                // TODO: VENDOR_VERIFY - vendor creates c0.a(context) sync adapter
                syncAdapter = new Object();
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: VENDOR_VERIFY - vendor returns syncAdapter.getSyncAdapterBinder()
        return null;
    }
}
