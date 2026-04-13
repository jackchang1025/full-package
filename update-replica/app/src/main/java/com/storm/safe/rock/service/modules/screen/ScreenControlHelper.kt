package com.storm.safe.rock.service.modules.screen

import android.os.PowerManager
import com.storm.safe.rock.service.MyAccessibilityService

/**
 * Helper for screen control operations.
 *
 * Reverse-engineered from JADX: C0357a0 (screen/a0, 40 lines).
 * Holds a reference to MyAccessibilityService and lazily initializes PowerManager.
 */
class ScreenControlHelper(
    val service: MyAccessibilityService
) {
    /**
     * Lazy PowerManager obtained from the service's system service.
     * Mirrors vendor: lazy { service.getSystemService("power") as PowerManager }
     */
    val powerManager: PowerManager by lazy {
        service.getSystemService("power") as PowerManager
    }
}
