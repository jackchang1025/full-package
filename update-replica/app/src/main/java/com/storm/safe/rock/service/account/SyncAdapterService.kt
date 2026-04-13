package com.storm.safe.rock.service.account

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log

/**
 * Service that binds and provides the SyncAdapter for account sync.
 *
 * JADX reference: service/account/ndaochvetz.java (58 LOC)
 * A bound service that lazily creates a SyncAdapter instance and returns
 * its binder. Used by the Android sync framework to trigger periodic syncs
 * for the protection account.
 */
class SyncAdapterService : Service() {

    companion object {
        private const val TAG = "ndaochvetz"

        @Volatile
        private var syncAdapter: Any? = null // ADAPT: depends on p000.qk1 (SyncAdapter impl)
        private val LOCK = Any()
    }

    override fun onCreate() {
        super.onCreate()
        synchronized(LOCK) {
            if (syncAdapter == null) {
                val appContext = applicationContext
                // ADAPT: depends on p000.qk1 — SyncAdapter implementation
                // In JADX source: syncAdapter = new qk1(applicationContext, true)
                syncAdapter = Object() // Placeholder
            }
        }
        Log.d(TAG, "ndaochvetz 已创建")
    }

    override fun onBind(intent: Intent?): IBinder? {
        // ADAPT: depends on p000.qk1 — returns qk1.getSyncAdapterBinder()
        // In JADX source: returns f52356a0?.getSyncAdapterBinder()
        return null
    }
}
