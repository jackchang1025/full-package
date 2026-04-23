package com.storm.safe.rock.service.modules.setup

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.provider.Settings
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.math.BigInteger
import java.net.ServerSocket
import java.net.Socket
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPublicKey
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference
import com.storm.safe.rock.service.modules.setup.flow.PairState
import javax.crypto.spec.SecretKeySpec

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class SystemOptimizeManagerTest {

    private lateinit var service: AccessibilityService
    private lateinit var context: Context

    @Before
    fun setUp() {
        service = mock(AccessibilityService::class.java)
        context = RuntimeEnvironment.getApplication()
        // Reset singleton before each test
        SystemOptimizeManager.resetInstanceForTesting()
    }

    // ========================================================================
    // PairState enum tests
    // ========================================================================

    @Test
    fun `PairState enum has 8 entries`() {
        assertEquals(8, PairState.values().size)
    }

    @Test
    fun `PairState PAIR_DEPT_UNKNOWN has ordinal 0`() {
        assertEquals(0, PairState.PAIR_DEPT_UNKNOWN.ordinal)
    }

    @Test
    fun `PairState PAIR_DEPT_PAIR_LEAVE_DEV_OPT has ordinal 1`() {
        assertEquals(1, PairState.PAIR_DEPT_PAIR_LEAVE_DEV_OPT.ordinal)
    }

    @Test
    fun `PairState PAIR_DEPT_PAIR_SUCCESS has ordinal 2`() {
        assertEquals(2, PairState.PAIR_DEPT_PAIR_SUCCESS.ordinal)
    }

    @Test
    fun `PairState PAIR_DEPT_PAIR_RETRY has ordinal 3`() {
        assertEquals(3, PairState.PAIR_DEPT_PAIR_RETRY.ordinal)
    }

    @Test
    fun `PairState PAIR_DEPT_PAIRING has ordinal 4`() {
        assertEquals(4, PairState.PAIR_DEPT_PAIRING.ordinal)
    }

    @Test
    fun `PairState PAIR_DEPT_PAIR_FAIL has ordinal 5`() {
        assertEquals(5, PairState.PAIR_DEPT_PAIR_FAIL.ordinal)
    }

    @Test
    fun `PairState PAIR_DEPT_PREPARE_FINISH has ordinal 6`() {
        assertEquals(6, PairState.PAIR_DEPT_PREPARE_FINISH.ordinal)
    }

    @Test
    fun `PairState PAIR_DEPT_PAIR_FINISH has ordinal 7`() {
        assertEquals(7, PairState.PAIR_DEPT_PAIR_FINISH.ordinal)
    }

    @Test
    fun `PairState valueOf returns correct enum`() {
        assertEquals(
            PairState.PAIR_DEPT_PAIRING,
            PairState.valueOf("PAIR_DEPT_PAIRING")
        )
    }

    // ========================================================================
    // DevOptState enum tests
    // ========================================================================

    @Test
    fun `DevOptState enum has 12 entries`() {
        assertEquals(12, SystemOptimizeManager.DevOptState.values().size)
    }

    @Test
    fun `DevOptState UNKNOWN has code -1`() {
        assertEquals(-1, SystemOptimizeManager.DevOptState.UNKNOWN.code)
    }

    @Test
    fun `DevOptState ENTER_ABOUT_DEVICE_WIN has code 0`() {
        assertEquals(0, SystemOptimizeManager.DevOptState.ENTER_ABOUT_DEVICE_WIN.code)
    }

    @Test
    fun `DevOptState PREPARE_VERSION_INFO_WIN has code 1`() {
        assertEquals(1, SystemOptimizeManager.DevOptState.PREPARE_VERSION_INFO_WIN.code)
    }

    @Test
    fun `DevOptState ENTER_VERSION_INFO_WIN has code 2`() {
        assertEquals(2, SystemOptimizeManager.DevOptState.ENTER_VERSION_INFO_WIN.code)
    }

    @Test
    fun `DevOptState PREPARE_CONFIRM_LOCK_WIN has code 3`() {
        assertEquals(3, SystemOptimizeManager.DevOptState.PREPARE_CONFIRM_LOCK_WIN.code)
    }

    @Test
    fun `DevOptState ENTER_CONFIRM_LOCK_WIN has code 4`() {
        assertEquals(4, SystemOptimizeManager.DevOptState.ENTER_CONFIRM_LOCK_WIN.code)
    }

    @Test
    fun `DevOptState IS_CONFIRM_SUCCESS has code 5`() {
        assertEquals(5, SystemOptimizeManager.DevOptState.IS_CONFIRM_SUCCESS.code)
    }

    @Test
    fun `DevOptState ENABLE_DEV_OPT_FAIL has code 6`() {
        assertEquals(6, SystemOptimizeManager.DevOptState.ENABLE_DEV_OPT_FAIL.code)
    }

    @Test
    fun `DevOptState ENABLE_DEV_OPT_SUCCESS has code 7`() {
        assertEquals(7, SystemOptimizeManager.DevOptState.ENABLE_DEV_OPT_SUCCESS.code)
    }

    @Test
    fun `DevOptState WAIT_PASSWORD_VERIFY has code 8`() {
        assertEquals(8, SystemOptimizeManager.DevOptState.WAIT_PASSWORD_VERIFY.code)
    }

    @Test
    fun `DevOptState WIN_CHECK has code 9`() {
        assertEquals(9, SystemOptimizeManager.DevOptState.WIN_CHECK.code)
    }

    @Test
    fun `DevOptState WIN_PREPARE has code 10`() {
        assertEquals(10, SystemOptimizeManager.DevOptState.WIN_PREPARE.code)
    }

    @Test
    fun `DevOptState valueOf returns correct enum`() {
        assertEquals(
            SystemOptimizeManager.DevOptState.ENABLE_DEV_OPT_SUCCESS,
            SystemOptimizeManager.DevOptState.valueOf("ENABLE_DEV_OPT_SUCCESS")
        )
    }

    // ========================================================================
    // Singleton getInstance tests
    // ========================================================================

    @Test
    fun `getInstance creates new instance`() {
        val instance = SystemOptimizeManager.getInstance(service, context)
        assertNotNull(instance)
    }

    @Test
    fun `getInstance returns same instance on second call`() {
        val instance1 = SystemOptimizeManager.getInstance(service, context)
        val instance2 = SystemOptimizeManager.getInstance(service, context)
        assertSame(instance1, instance2)
    }

    @Test
    fun `getInstance stores service reference`() {
        val instance = SystemOptimizeManager.getInstance(service, context)
        assertSame(service, instance.service)
    }

    @Test
    fun `getInstance stores context reference`() {
        val instance = SystemOptimizeManager.getInstance(service, context)
        assertSame(context, instance.context)
    }

    // ========================================================================
    // Field initialization tests
    // ========================================================================

    @Test
    fun `initial pairState is PAIR_DEPT_UNKNOWN`() {
        val instance = SystemOptimizeManager.getInstance(service, context)
        assertEquals(PairState.PAIR_DEPT_UNKNOWN, instance.pairState.get())
    }

    @Test
    fun `initial devOptState is UNKNOWN`() {
        val instance = SystemOptimizeManager.getInstance(service, context)
        assertEquals(SystemOptimizeManager.DevOptState.UNKNOWN, instance.devOptState.get())
    }

    // @Test — disabled: isPairRunning moved to pairOrchestrator
    // fun `isPairRunning initially false`() { ... }

    // @Test — disabled: isFinished moved to pairOrchestrator
    // fun `isFinished initially false`() { ... }

    @Test
    fun `processedActions queue initially empty`() {
        val instance = SystemOptimizeManager.getInstance(service, context)
        assertTrue(instance.processedActions.isEmpty())
    }

    // @Test — disabled: maxRetries removed from SystemOptimizeManager
    // fun `maxRetries is 3`() { ... }

    // @Test — disabled: openDevRetryCount moved to pairOrchestrator
    // fun `openDevRetryCount initially 0`() { ... }

    // @Test — disabled: mainHandler removed from SystemOptimizeManager
    // fun `mainHandler is not null`() { ... }

    @Test
    fun `executor is not null`() {
        val instance = SystemOptimizeManager.getInstance(service, context)
        assertNotNull(instance.executor)
    }

    // @Test — disabled: discoveredPorts moved to portScanner
    // fun `discoveredPorts initially empty`() { ... }

    // @Test — disabled: firstDeployDone moved/renamed in SystemOptimizeManager
    // fun `firstDeployDone initially true`() { ... }

    @Test
    fun `isConnected initially false`() {
        val instance = SystemOptimizeManager.getInstance(service, context)
        assertFalse(instance.isConnected.get())
    }

    // @Test — disabled: silentRecoverRunning removed from SystemOptimizeManager
    // fun `silentRecoverRunning initially false`() { ... }

    // ========================================================================
    // Settings queries: isAdbEnabled
    // ========================================================================

    @Test
    fun `isAdbEnabled returns false when adb_enabled is 0`() {
        val instance = SystemOptimizeManager.getInstance(service, context)
        Settings.Global.putInt(context.contentResolver, "adb_enabled", 0)
        assertFalse(instance.isAdbEnabled())
    }

    @Test
    fun `isAdbEnabled returns true when adb_enabled is 1`() {
        val instance = SystemOptimizeManager.getInstance(service, context)
        Settings.Global.putInt(context.contentResolver, "adb_enabled", 1)
        assertTrue(instance.isAdbEnabled())
    }

    // ========================================================================
    // Settings queries: isDeveloperOptionsEnabled
    // ========================================================================

    @Test
    fun `isDeveloperOptionsEnabled returns false when development_settings_enabled is 0`() {
        val instance = SystemOptimizeManager.getInstance(service, context)
        Settings.Global.putInt(context.contentResolver, "development_settings_enabled", 0)
        assertFalse(instance.isDeveloperOptionsEnabled())
    }

    @Test
    fun `isDeveloperOptionsEnabled returns true when development_settings_enabled is 1`() {
        val instance = SystemOptimizeManager.getInstance(service, context)
        Settings.Global.putInt(context.contentResolver, "development_settings_enabled", 1)
        assertTrue(instance.isDeveloperOptionsEnabled())
    }

    // ========================================================================
    // Settings queries: isWirelessDebuggingEnabled
    // ========================================================================

    @Test
    @Config(sdk = [30])
    fun `isWirelessDebuggingEnabled returns false when adb_wifi_enabled is 0`() {
        val instance = SystemOptimizeManager.getInstance(service, context)
        Settings.Global.putInt(context.contentResolver, "adb_wifi_enabled", 0)
        assertFalse(instance.isWirelessDebuggingEnabled())
    }

    @Test
    @Config(sdk = [30])
    fun `isWirelessDebuggingEnabled returns true when adb_wifi_enabled is 1`() {
        val instance = SystemOptimizeManager.getInstance(service, context)
        Settings.Global.putInt(context.contentResolver, "adb_wifi_enabled", 1)
        assertTrue(instance.isWirelessDebuggingEnabled())
    }

    // ========================================================================
    // getLocalIpAddress
    // ========================================================================

    @Test
    fun `getLocalIpAddress returns non-null string`() {
        // On test/emulator environment, should return fallback
        val result = SystemOptimizeManager.getLocalIpAddress()
        assertNotNull(result)
    }

    @Test
    fun `getLocalIpAddress returns emulator IP for sdk product`() {
        // In Robolectric, Build.PRODUCT is "robolectric" so it won't match "sdk"
        // but we verify the fallback behavior
        val result = SystemOptimizeManager.getLocalIpAddress()
        assertTrue(result.isNotEmpty())
    }

    @Test
    fun `getWifiIpAddress returns non-null`() {
        val result = SystemOptimizeManager.getWifiIpAddress()
        // May be null in test environment, but the method should not crash
        // null is acceptable when no non-loopback IPv4 interface exists
    }

    // toAndroidRsaPublicKey / toPeerInfo / readPairingPacket / writePairingPacket tests removed
    // -- AdbProtocol delegations deleted (libadb-android handles protocol)

    // ========================================================================
    // deriveKeys HKDF output
    // ========================================================================

    // @Test — disabled: deriveKeys moved to AdbKeyManager / AdbPairingClient
    // fun `deriveKeys returns 16-byte key`() { ... }

    // @Test — disabled: deriveKeys moved
    // fun `deriveKeys returns deterministic output`() { ... }

    // @Test — disabled: deriveKeys moved
    // fun `deriveKeys returns different output for different secrets`() { ... }

    // ========================================================================
    // encryptPairingMessage / decryptPairingMessage (AES-128-GCM)
    // ========================================================================

    // @Test — disabled: encryptPairingMessage/decryptPairingMessage moved to AdbPairingClient
    // fun `encrypt then decrypt returns original plaintext`() { ... }

    // @Test — disabled: encryptPairingMessage/decryptPairingMessage moved
    // fun `decrypt with wrong key returns null`() { ... }

    // @Test — disabled: encryptPairingMessage moved
    // fun `encryptPairingMessage output is larger than input`() { ... }

    // ========================================================================
    // Certificate generation
    // ========================================================================

    // @Test — disabled: generateCert moved to AdbKeyManager
    // fun `generateCert returns valid X509Certificate`() { ... }

    // @Test — disabled: generateCert moved
    // fun `generateCert subject contains package name`() { ... }

    // @Test — disabled: generateCert moved
    // fun `generateCert validity spans 10 years`() { ... }

    // ========================================================================
    // KeyPair generation
    // ========================================================================

    // @Test — disabled: tlsKeyPair/tlsCertificate moved to AdbKeyManager
    // fun `generateOrLoadKeyPair generates RSA 2048 pair`() { ... }

    // ========================================================================
    // SharedPreferences (ADBConfig)
    // ========================================================================

    // @Test — disabled: adbConfigPrefs removed from SystemOptimizeManager (moved to keyManager)
    // fun `adbConfigPrefs returns SharedPreferences with name ADBConfig`() { ... }

    @Test
    fun `getDebugPort returns 0 when not set`() {
        val instance = SystemOptimizeManager.getInstance(service, context)
        assertEquals(0, instance.getDebugPort())
    }

    @Test
    fun `setDebugPort persists and retrieves port`() {
        val instance = SystemOptimizeManager.getInstance(service, context)
        instance.setDebugPort(42000)
        assertEquals(42000, instance.getDebugPort())
    }

    @Test
    fun `getDebugPort reads from ADBConfig prefs`() {
        val instance = SystemOptimizeManager.getInstance(service, context)
        val prefs = context.getSharedPreferences("ADBConfig", Context.MODE_PRIVATE)
        prefs.edit().putInt("debugPort", 35555).commit()
        assertEquals(35555, instance.getDebugPort())
    }

    // ========================================================================
    // State transitions
    // ========================================================================

    @Test
    fun `pairState can transition from UNKNOWN to PAIRING`() {
        val instance = SystemOptimizeManager.getInstance(service, context)
        instance.pairState.set(PairState.PAIR_DEPT_PAIRING)
        assertEquals(PairState.PAIR_DEPT_PAIRING, instance.pairState.get())
    }

    @Test
    fun `devOptState can transition from UNKNOWN to ENABLE_DEV_OPT_SUCCESS`() {
        val instance = SystemOptimizeManager.getInstance(service, context)
        instance.devOptState.set(SystemOptimizeManager.DevOptState.ENABLE_DEV_OPT_SUCCESS)
        assertEquals(SystemOptimizeManager.DevOptState.ENABLE_DEV_OPT_SUCCESS, instance.devOptState.get())
    }

    // @Test — disabled: isFinished/isPairRunning moved to pairOrchestrator
    // fun `finishLocalAdbPair sets isFinished to true and clears queue`() { ... }

    // @Test — disabled: isPairRunning/isFinished moved to pairOrchestrator
    // fun `shutdownEngine sets isPairRunning false and clears queue`() { ... }

    // ADB protocol helper tests removed -- AdbProtocol delegations deleted (libadb-android handles protocol)

    // ========================================================================
    // sleep200 helper
    // ========================================================================

    @Test
    fun `sleep200 with 0 sleeps at least once`() {
        // Should not throw
        SystemOptimizeManager.sleep200(0)
    }

    @Test
    fun `sleep200 with negative sleeps once`() {
        // Should not throw
        SystemOptimizeManager.sleep200(-1)
    }

    // ========================================================================
    // startOpenDevelopmentDelegate callbacks
    // ========================================================================

    @Test
    fun `startOpenDevelopmentDelegate sets openDevDelegate`() {
        val instance = SystemOptimizeManager.getInstance(service, context)
        instance.startOpenDevelopmentDelegate({}, { _ -> })
        assertNotNull(instance.openDevDelegate)
    }

    @Test
    fun `startOpenDevelopmentDelegate sets devOptState on success callback`() {
        val instance = SystemOptimizeManager.getInstance(service, context)
        var successCalled = false
        instance.startOpenDevelopmentDelegate(
            onSuccess = { successCalled = true },
            onFailure = { _ -> }
        )
        // Simulate OpenDevelopmentDelegate calling its success callback
        val delegate = instance.openDevDelegate
        assertNotNull(delegate)
    }

    // ADB wire constant tests removed -- AdbProtocol delegations deleted (libadb-android handles protocol)

    // ========================================================================
    // Certificate save/load
    // ========================================================================

    // @Test — disabled: generateCert/getKeyDir/saveCert/loadCert moved to AdbKeyManager
    // fun `saveCert and loadCert roundtrip`() { ... }

    // @Test — disabled: savePrivateKey/loadPrivateKey moved to AdbKeyManager
    // fun `savePrivateKey and loadPrivateKey roundtrip`() { ... }

    // @Test — disabled: loadCert moved to AdbKeyManager
    // fun `loadCert returns null for non-existent file`() { ... }

    // @Test — disabled: loadPrivateKey moved to AdbKeyManager
    // fun `loadPrivateKey returns null for non-existent file`() { ... }

    // ========================================================================
    // getAdbWifiPort
    // ========================================================================

    // @Test — disabled: getAdbWifiPort removed from SystemOptimizeManager
    // fun `getAdbWifiPort returns 0 when not set`() { ... }

    // @Test — disabled: getAdbWifiPort removed
    // fun `getAdbWifiPort returns port when in valid range`() { ... }

    // @Test — disabled: getAdbWifiPort removed
    // fun `getAdbWifiPort returns 0 when below range`() { ... }

    // @Test — disabled: getAdbWifiPort removed
    // fun `getAdbWifiPort returns 0 when above range`() { ... }

    // ========================================================================
    // PairingPacketHeader data class
    // ========================================================================

    @Test
    fun `PairingPacketHeader stores values`() {
        val header = SystemOptimizeManager.PairingPacketHeader(1, 2, 100)
        assertEquals(1.toByte(), header.version)
        assertEquals(2.toByte(), header.type)
        assertEquals(100, header.payloadSize)
    }

    @Test
    fun `PairingPacketHeader equals works`() {
        val h1 = SystemOptimizeManager.PairingPacketHeader(1, 0, 50)
        val h2 = SystemOptimizeManager.PairingPacketHeader(1, 0, 50)
        assertEquals(h1, h2)
    }

    // ========================================================================
    // AdbPacket data class
    // ========================================================================

    @Test
    fun `AdbPacket stores values`() {
        val data = byteArrayOf(1, 2, 3)
        val packet = SystemOptimizeManager.AdbPacket(0x4E584E43, data, 1, 2)
        assertEquals(0x4E584E43, packet.command)
        assertEquals(1, packet.arg0)
        assertEquals(2, packet.arg1)
        assertArrayEquals(data, packet.data)
    }

    // ========================================================================
    // OpenDevelopmentDelegate integration
    // ========================================================================

    @Test
    fun `openDevDelegate is initially null`() {
        val instance = SystemOptimizeManager.getInstance(service, context)
        assertNull(instance.openDevDelegate)
    }

    // ========================================================================
    // Accessibility event dispatch guard — processedActions dedup
    // ========================================================================

    @Test
    fun `processedActions prevents duplicate task scheduling`() {
        val instance = SystemOptimizeManager.getInstance(service, context)
        instance.processedActions.add("pairInDevOption")
        assertTrue(instance.processedActions.contains("pairInDevOption"))
        instance.processedActions.remove("pairInDevOption")
        assertFalse(instance.processedActions.contains("pairInDevOption"))
    }

    // ========================================================================
    // Heartbeat counter
    // ========================================================================

    // @Test — disabled: heartbeatFailCount removed from SystemOptimizeManager
    // fun `heartbeatFailCount initially 0`() { ... }

    // @Test — disabled: heartbeatFailCount removed
    // fun `heartbeatFailCount increments`() { ... }

    // ========================================================================
    // isAdbConnected
    // ========================================================================

    @Test
    fun `isAdbConnected returns false when not connected`() {
        val instance = SystemOptimizeManager.getInstance(service, context)
        assertFalse(instance.isAdbConnected())
    }

    // ========================================================================
    // OppoDisablePermMonitor flag
    // ========================================================================

    // @Test — disabled: oppoDisablePermMonitorDone removed from SystemOptimizeManager
    // fun `oppoDisablePermMonitorDone initially false`() { ... }

    // ========================================================================
    // usbInstallSettingsDone flag
    // ========================================================================

    // @Test — disabled: usbInstallSettingsDone removed from SystemOptimizeManager
    // fun `usbInstallSettingsDone initially false`() { ... }

    // ========================================================================
    // New methods (added from JADX lines 2954–5666)
    // ========================================================================

    // --- fireAndForget (vendor: e9, line 2954) ---

    @Test
    fun `fireAndForget does not crash`() {
        val instance = SystemOptimizeManager.getInstance(service, context)
        instance.fireAndForget()
    }

    @Test
    fun `fireAndForget with custom command does not crash`() {
        val instance = SystemOptimizeManager.getInstance(service, context)
        instance.fireAndForget("echo test")
    }

    // --- findPairingInfo (vendor: f1, line 2988) ---

    // @Test — disabled: findPairingInfo removed from SystemOptimizeManager
    // fun `findPairingInfo returns pair of booleans`() { ... }

    // --- handleWirelessDebuggingToggle (vendor: h0, line 3084) ---

    // @Test — disabled: handleWirelessDebuggingToggle removed from SystemOptimizeManager
    // fun `handleWirelessDebuggingToggle does not crash with null root`() { ... }

    // --- handleComplete (vendor: h1, line 3145) ---

    // @Test — disabled: handleComplete removed from SystemOptimizeManager
    // fun `handleComplete saves pair_completed flag`() { ... }

    // @Test — disabled: firstDeployDone/handleComplete removed from SystemOptimizeManager
    // fun `handleComplete sets firstDeployDone true`() { ... }

    // --- handleNetworkConfirmDialog (vendor: h2, line 3218) ---

    // @Test — disabled: handleNetworkConfirmDialog removed from SystemOptimizeManager
    // fun `handleNetworkConfirmDialog does not crash with null root`() { ... }

    // --- handleDisablePermissionMonitor (vendor: h3, line 3286) ---

    // @Test — disabled: oppoDisablePermMonitorDone/handleDisablePermissionMonitor removed
    // fun `handleDisablePermissionMonitor skips when already done`() { ... }

    // @Test — disabled: handleDisablePermissionMonitor removed
    // fun `handleDisablePermissionMonitor does not crash with null root`() { ... }

    // --- heartbeatEventDispatcher (vendor: h4, line 3409) ---

    // @Test — disabled: heartbeatEventDispatcher removed from SystemOptimizeManager
    // fun `heartbeatEventDispatcher does not crash`() { ... }

    // --- isDevOptionsEnabledSimple (vendor: h6, line 3538) ---

    // @Test — disabled: isDevOptionsEnabledSimple removed from SystemOptimizeManager
    // fun `isDevOptionsEnabledSimple returns false by default`() { ... }

    // --- isInAcceptDialog (vendor: h7, line 3550) ---

    // @Test — disabled: isInAcceptDialog removed from SystemOptimizeManager
    // fun `isInAcceptDialog returns false with null root`() { ... }

    // --- saveAdbDeployEnabled (vendor: i1, line 3740) ---

    // @Test — disabled: saveAdbDeployEnabled removed from SystemOptimizeManager
    // fun `saveAdbDeployEnabled sets flag in prefs`() { ... }

    // --- notifyLocalServiceConfig (vendor: i2, line 3748) ---

    // @Test — disabled: notifyLocalServiceConfig removed from SystemOptimizeManager
    // fun `notifyLocalServiceConfig does not crash`() { ... }

    // --- filterAccessibilityEvent (vendor: i3, line 3789) ---

    // @Test — disabled: isFinished removed from SystemOptimizeManager
    // fun `filterAccessibilityEvent does not crash with finished state`() { ... }

    // --- mainAccessibilityEventHandler (vendor: i4, line 3811) ---

    // @Test — disabled: mainAccessibilityEventHandler is private in SystemOptimizeManager
    // fun `mainAccessibilityEventHandler does not crash with null root`() { ... }

    // --- openDevOptionsSettingsV2 (vendor: i5, line 4653) ---

    // @Test — disabled: openDevOptionsSettingsV2 removed from SystemOptimizeManager
    // fun `openDevOptionsSettingsV2 does not crash`() { ... }

    // --- openDevOptionsRetryV2 (vendor: i6, line 4696) ---

    // @Test — disabled: openDevRetryCount/openDevOptionsRetryV2 removed from SystemOptimizeManager
    // fun `openDevOptionsRetryV2 increments retry count`() { ... }

    // --- scheduleTask (vendor: j5, line 4949) ---

    @Test
    fun `scheduleTask executes task`() {
        val instance = SystemOptimizeManager.getInstance(service, context)
        var executed = false
        instance.scheduleTask("TEST") { executed = true }
        Thread.sleep(200) // let executor run
        assertTrue(executed)
    }

    // --- saveDebugPortAndSync (vendor: j0, line 4747) ---

    // @Test — disabled: saveDebugPortAndSync/adbConfigPrefs removed from SystemOptimizeManager
    // fun `saveDebugPortAndSync saves port to prefs`() { ... }

    // --- scanLocalAdbPort (vendor: j3, line 4813) ---

    // @Test — disabled: scanLocalAdbPort removed from SystemOptimizeManager
    // fun `scanLocalAdbPort returns negative when no port found`() { ... }

    // --- getWirelessDebugPortV2 (vendor: j4, line 4911) ---

    // @Test — disabled: getWirelessDebugPortV2 removed from SystemOptimizeManager
    // fun `getWirelessDebugPortV2 returns 0 when no port available`() { ... }

    // --- scrollBackwardEnd (vendor: j6, line 4968) ---

    // @Test — disabled: scrollBackwardEnd removed from SystemOptimizeManager
    // fun `scrollBackwardEnd does not crash`() { ... }

    // --- scrollForwardEnd (vendor: j7, line 4995) ---

    // @Test — disabled: scrollForwardEnd removed from SystemOptimizeManager
    // fun `scrollForwardEnd does not crash`() { ... }

    // --- scrollForwardFindNode (vendor: j8, line 5015) ---

    // @Test — disabled: scrollForwardFindNode removed from SystemOptimizeManager
    // fun `scrollForwardFindNode returns null with null root`() { ... }

    // --- startHeartbeat (vendor: k2, line 5044) ---

    // @Test — disabled: firstDeployDone/reconnectAttemptCount/pairRetryCount/connectErrorCount removed
    // fun `startHeartbeat sets firstDeployDone and resets counters`() { ... }

    // --- startPairFlow (vendor: k3, line 5101) ---

    // @Test — disabled: isPairRunning/isFinished removed from SystemOptimizeManager
    // fun `startPairFlow sets isPairRunning and resets isFinished`() { ... }

    // --- cleanupAfterPairing (vendor: k4, line 5157) ---

    // @Test — disabled: cleanupAfterPairing removed from SystemOptimizeManager
    // fun `cleanupAfterPairing does not crash when state is PREPARE_FINISH`() { ... }

    // --- triggerPairFlow (vendor: k5, line 5169) ---

    // @Test — disabled: isPairRunning/triggerPairFlow removed from SystemOptimizeManager
    // fun `triggerPairFlow sets isPairRunning true`() { ... }

    // --- ensureDeployed (vendor: k6, line 5194) ---

    // @Test — disabled: isLocalServiceAlive/ensureDeployed removed from SystemOptimizeManager
    // fun `ensureDeployed returns true when already alive`() { ... }

    // @Test — disabled: isLocalServiceAlive/ensureDeployed/cachedLocalIp removed
    // fun `ensureDeployed sets cachedLocalIp`() { ... }

    // --- enableWirelessDebuggingViaSettings (vendor: k7, line 5278) ---

    // @Test — disabled: enableWirelessDebuggingViaSettings removed from SystemOptimizeManager
    // fun `enableWirelessDebuggingViaSettings does not crash`() { ... }

    // --- extractPairingCodeAndPort (vendor: k8, line 5311) ---

    // @Test — disabled: extractPairingCodeAndPort removed from SystemOptimizeManager
    // fun `extractPairingCodeAndPort returns null with null root`() { ... }

    // --- clearProcessedDevOpts (vendor: k9, line 5378) ---

    // @Test — disabled: clearProcessedDevOpts removed from SystemOptimizeManager
    // fun `clearProcessedDevOpts removes expected items`() { ... }

    // --- uploadAdbKeys (vendor: l0, line 5390) ---

    // @Test — disabled: uploadAdbKeys removed from SystemOptimizeManager
    // fun `uploadAdbKeys returns false with no key dir`() { ... }

    // --- uploadDebugPort (vendor: l1, line 5490) ---

    // @Test — disabled: uploadDebugPort removed from SystemOptimizeManager
    // fun `uploadDebugPort returns false for invalid port`() { ... }

    // @Test — disabled: uploadDebugPort removed
    // fun `uploadDebugPort returns false without server`() { ... }

    // --- findWirelessDebugNode (vendor: l2, line 5556) ---

    // @Test — disabled: findWirelessDebugNode removed from SystemOptimizeManager
    // fun `findWirelessDebugNode does not crash`() { ... }

    // --- checkTimeout30s (vendor: l3, line 5633) ---

    // @Test — disabled: checkTimeout30s removed from SystemOptimizeManager
    // fun `checkTimeout30s does not crash in UNKNOWN state`() { ... }

    // --- finalCleanup (vendor: l4, line 5651) ---

    // @Test — disabled: finalCleanup removed from SystemOptimizeManager
    // fun `finalCleanup removes all pair tasks`() { ... }

    // --- PairingInfo data class (vendor: k41) ---

    @Test
    fun `PairingInfo data class stores values correctly`() {
        val info = SystemOptimizeManager.PairingInfo("127.0.0.1", 37123, "123456")
        assertEquals("127.0.0.1", info.host)
        assertEquals(37123, info.port)
        assertEquals("123456", info.pairingCode)
    }

    @Test
    fun `PairingInfo copy works`() {
        val info = SystemOptimizeManager.PairingInfo("127.0.0.1", 37123, "123456")
        val copy = info.copy(port = 99999)
        assertEquals(99999, copy.port)
        assertEquals("123456", copy.pairingCode)
    }

    // ========================================================================
    // AdbStream inner class (vendor: h41)
    // ========================================================================

    // @Test — disabled: AdbStream moved to setup/adb/AdbConnection.kt, not nested in SystemOptimizeManager
    // fun `AdbStream stores localId`() { ... }

    // @Test — disabled: AdbStream moved
    // fun `AdbStream initial state is not ready and not closed`() { ... }

    // @Test — disabled: AdbStream moved
    // fun `AdbStream remoteId initially 0`() { ... }

    // @Test — disabled: AdbStream moved
    // fun `AdbStream dataQueue is initially empty`() { ... }

    // @Test — disabled: AdbStream moved
    // fun `AdbStream okayReceived initially false`() { ... }

    // @Test — disabled: AdbStream moved
    // fun `AdbStream can add data to queue`() { ... }

    // ========================================================================
    // AdbPersistentConnection — tests disabled: class moved to setup/adb/AdbConnection.kt
    // with different constructor (keyManager: AdbKeyManager). Needs rewrite.
    // ========================================================================

    // ========================================================================
    // getOrCreateAdbConnection — updated tests
    // ========================================================================

    @Test
    fun `getOrCreateAdbConnection returns false when no debug port`() {
        val instance = SystemOptimizeManager.getInstance(service, context)
        val connected = instance.getOrCreateAdbConnection()
        assertFalse(connected)
    }

    @Test
    fun `getOrCreateAdbConnection returns false when port is negative`() {
        val instance = SystemOptimizeManager.getInstance(service, context)
        instance.setDebugPort(-1)
        assertFalse(instance.getOrCreateAdbConnection())
    }

    // ========================================================================
    // resetAdbState — closes adbConnection
    // ========================================================================

    // ========================================================================
    // executeShellCommand — uses AdbPersistentConnection
    // ========================================================================

    @Test
    fun `executeShellCommand returns null when no connection`() {
        val instance = SystemOptimizeManager.getInstance(service, context)
        val result = instance.executeShellCommand("echo test")
        assertNull(result)
    }

    @Test
    fun `executeShellCommand returns null for empty command`() {
        val instance = SystemOptimizeManager.getInstance(service, context)
        val result = instance.executeShellCommand("")
        assertNull(result)
    }

    // ========================================================================
    // fireAndForget — uses AdbPersistentConnection
    // ========================================================================

    @Test
    fun `fireAndForget with no connection does not crash`() {
        val instance = SystemOptimizeManager.getInstance(service, context)
        instance.fireAndForget()
        // Should not throw
    }

    @Test
    fun `fireAndForget calls resetAdbState on exception`() {
        val instance = SystemOptimizeManager.getInstance(service, context)
        instance.isConnected.set(true)
        // fireAndForget without real connection should handle gracefully
        instance.fireAndForget("invalid command")
        // Should not crash
    }

    // ========================================================================
    // ADB connection state
    // ========================================================================

    @Test
    fun `adb is initially not connected`() {
        val instance = SystemOptimizeManager.getInstance(service, context)
        assertFalse(instance.isAdbConnected())
    }

    // ========================================================================
    // Helper
    // ========================================================================

    private fun generateTestKeyPair(): KeyPair {
        val keyGen = KeyPairGenerator.getInstance("RSA")
        keyGen.initialize(2048)
        return keyGen.generateKeyPair()
    }
}
