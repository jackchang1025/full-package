package com.storm.safe.rock.service.modules.setup.vendor

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.view.accessibility.AccessibilityNodeInfo

/**
 * Vendor ADB pairing adapter interface.
 * Template Method + Strategy: base defines flow skeleton, vendor subclasses override hooks.
 * All methods have default implementations (Null Object pattern).
 */
interface VendorPairAdapter {
    val vendorName: String

    // === Phase 0: Developer Options ===
    fun needsVersionInfoPage(): Boolean = false
    fun openDevOptions(context: Context): Boolean = false
    fun isVendorLockDialog(className: String): Boolean = false

    // === Phase 1: Developer Options Page ===
    fun onDevOptionsEntered(
        service: AccessibilityService,
        scrollableView: AccessibilityNodeInfo
    ): Boolean = true

    fun onBeforeWirelessDebugClick(
        service: AccessibilityService,
        clickableNode: AccessibilityNodeInfo
    ) {
    }

    // === Phase 2: Wireless Debugging Page ===
    fun enableWirelessDebug(service: AccessibilityService): Boolean = false

    // === Phase 3: Security Dialog ===
    fun isVendorSecurityDialog(packageName: String?, className: String?): Boolean = false
    fun handleSecurityDialog(service: AccessibilityService): Boolean = false

    // === Post-Pairing ===
    fun onPairingComplete(service: AccessibilityService) {}
}
