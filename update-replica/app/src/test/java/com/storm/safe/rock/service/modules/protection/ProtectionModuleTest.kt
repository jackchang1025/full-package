package com.storm.safe.rock.service.modules.protection

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Phase 9 Protection Module Tests.
 *
 * JADX: C0355a0.java (2282 lines) — UninstallProtectionManager
 * JADX: C0356a1.java (203 lines)  — RecentsGuardManager
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class ProtectionModuleTest {

    // ==================== RecentsGuardManager ====================

    @Test
    fun `RecentsGuardManager LAUNCHER_PACKAGES contains all major brands`() {
        val pkgs = RecentsGuardManager.LAUNCHER_PACKAGES
        assertTrue(pkgs.contains("com.android.systemui"))
        assertTrue(pkgs.contains("com.huawei.android.launcher"))
        assertTrue(pkgs.contains("com.huawei.home"))
        assertTrue(pkgs.contains("com.hihonor.android.launcher"))
        assertTrue(pkgs.contains("com.miui.home"))
        assertTrue(pkgs.contains("com.oplus.launcher"))
        assertTrue(pkgs.contains("com.coloros.launcher"))
        assertTrue(pkgs.contains("com.samsung.android.launcher"))
        assertTrue(pkgs.contains("com.meizu.flyme.launcher"))
        assertTrue(pkgs.contains("com.google.android.apps.nexuslauncher"))
    }

    @Test
    fun `RecentsGuardManager LAUNCHER_PACKAGES has at least 40 entries`() {
        assertTrue(RecentsGuardManager.LAUNCHER_PACKAGES.size >= 40)
    }

    @Test
    fun `RecentsGuardManager TAG is npweufstehlb`() {
        assertEquals("npweufstehlb", RecentsGuardManager.TAG)
    }

    @Test
    fun `RecentsGuardManager DEDUP_INTERVAL_MS is 2000`() {
        assertEquals(2000L, RecentsGuardManager.DEDUP_INTERVAL_MS)
    }

    @Test
    fun `RecentsGuardManager HANDLER_THREAD_NAME is RecentsGuardBg`() {
        assertEquals("RecentsGuardBg", RecentsGuardManager.HANDLER_THREAD_NAME)
    }

    @Test
    fun `RecentsGuardManager HOME_GLOBAL_ACTION is 2`() {
        assertEquals(2, RecentsGuardManager.HOME_GLOBAL_ACTION)
    }

    @Test
    fun `RecentsGuardManager WINDOW_STATE_CHANGED is 32`() {
        assertEquals(32, RecentsGuardManager.EVENT_TYPE_WINDOW_STATE_CHANGED)
    }

    @Test
    fun `RecentsGuardManager WINDOW_CONTENT_CHANGED is 2048`() {
        assertEquals(2048, RecentsGuardManager.EVENT_TYPE_WINDOW_CONTENT_CHANGED)
    }

    @Test
    fun `RecentsGuardManager LAUNCHER_PACKAGES contains vivo brands`() {
        val pkgs = RecentsGuardManager.LAUNCHER_PACKAGES
        assertTrue(pkgs.contains("com.bbk.launcher2"))
        assertTrue(pkgs.contains("com.vivo.launcher.two"))
        assertTrue(pkgs.contains("com.iqoo.launcher.two"))
    }

    @Test
    fun `RecentsGuardManager default state flags are false`() {
        // Verify the initial values as specified in JADX
        assertFalse(RecentsGuardManager.globalHomeFlag)
    }

    // ==================== UninstallProtectionManager Constants ====================

    @Test
    fun `TAG is UninstallProtectionMgr`() {
        assertEquals("UninstallProtectionMgr", UninstallProtectionManager.TAG)
    }

    @Test
    fun `PURE_MODE_KEYWORDS has 3 entries`() {
        assertArrayEquals(
            arrayOf("纯净模式", "純淨模式", "Pure Mode"),
            UninstallProtectionManager.PURE_MODE_KEYWORDS
        )
    }

    @Test
    fun `ENHANCED_PROTECTION_KEYWORDS has 5 entries`() {
        assertEquals(5, UninstallProtectionManager.ENHANCED_PROTECTION_KEYWORDS.size)
        assertTrue(UninstallProtectionManager.ENHANCED_PROTECTION_KEYWORDS.contains("增强防护"))
        assertTrue(UninstallProtectionManager.ENHANCED_PROTECTION_KEYWORDS.contains("Enhanced protection"))
    }

    // ==================== Sensitive Activity ClassNames ====================

    @Test
    fun `SETTINGS_SENSITIVE_CLASSNAMES has 19 entries`() {
        assertEquals(19, UninstallProtectionManager.SETTINGS_SENSITIVE_CLASSNAMES.size)
    }

    @Test
    fun `SETTINGS_SENSITIVE_CLASSNAMES contains key activities`() {
        val arr = UninstallProtectionManager.SETTINGS_SENSITIVE_CLASSNAMES
        assertTrue(arr.contains("com.android.settings.applications.InstalledAppDetailsTop"))
        assertTrue(arr.contains("com.android.settings.Settings\$AccessibilitySettingsActivity"))
        assertTrue(arr.contains("com.android.settings.Settings\$FactoryResetActivity"))
        assertTrue(arr.contains("com.android.settings.MasterClear"))
    }

    @Test
    fun `OPPO_SENSITIVE_CLASSNAMES has 21 entries`() {
        assertEquals(21, UninstallProtectionManager.OPPO_SENSITIVE_CLASSNAMES.size)
    }

    @Test
    fun `XIAOMI_SENSITIVE_CLASSNAMES has 15 entries`() {
        assertEquals(15, UninstallProtectionManager.XIAOMI_SENSITIVE_CLASSNAMES.size)
    }

    @Test
    fun `VIVO_SENSITIVE_CLASSNAMES has 26 entries`() {
        assertEquals(26, UninstallProtectionManager.VIVO_SENSITIVE_CLASSNAMES.size)
    }

    @Test
    fun `HUAWEI_SENSITIVE_CLASSNAMES has 12 entries`() {
        assertEquals(12, UninstallProtectionManager.HUAWEI_SENSITIVE_CLASSNAMES.size)
    }

    @Test
    fun `HONOR_SENSITIVE_CLASSNAMES has 10 entries`() {
        assertEquals(10, UninstallProtectionManager.HONOR_SENSITIVE_CLASSNAMES.size)
    }

    @Test
    fun `SAMSUNG_SENSITIVE_CLASSNAMES has 8 entries`() {
        assertEquals(8, UninstallProtectionManager.SAMSUNG_SENSITIVE_CLASSNAMES.size)
    }

    @Test
    fun `MEIZU_SENSITIVE_CLASSNAMES has 6 entries`() {
        assertEquals(6, UninstallProtectionManager.MEIZU_SENSITIVE_CLASSNAMES.size)
    }

    @Test
    fun `OTHER_SENSITIVE_CLASSNAMES has 11 entries`() {
        assertEquals(11, UninstallProtectionManager.OTHER_SENSITIVE_CLASSNAMES.size)
    }

    // ==================== Package Installer Packages ====================

    @Test
    fun `PACKAGE_INSTALLER_PACKAGES has 35 entries`() {
        assertEquals(35, UninstallProtectionManager.PACKAGE_INSTALLER_PACKAGES.size)
    }

    @Test
    fun `PACKAGE_INSTALLER_PACKAGES contains major installers`() {
        val arr = UninstallProtectionManager.PACKAGE_INSTALLER_PACKAGES
        assertTrue(arr.contains("com.android.packageinstaller"))
        assertTrue(arr.contains("com.google.android.packageinstaller"))
        assertTrue(arr.contains("com.samsung.android.packageinstaller"))
        assertTrue(arr.contains("com.miui.packageinstaller"))
        assertTrue(arr.contains("com.huawei.packageinstaller"))
    }

    // ==================== Security App Packages ====================

    @Test
    fun `QIHOO_PACKAGES has 6 entries`() {
        assertEquals(6, UninstallProtectionManager.QIHOO_PACKAGES.size)
    }

    @Test
    fun `TENCENT_PACKAGES has 3 entries`() {
        assertEquals(3, UninstallProtectionManager.TENCENT_PACKAGES.size)
    }

    @Test
    fun `GENERAL_SECURITY_PACKAGES has 14 entries`() {
        assertEquals(14, UninstallProtectionManager.GENERAL_SECURITY_PACKAGES.size)
    }

    // ==================== Launcher Package Lists ====================

    @Test
    fun `HUAWEI_LAUNCHER_PACKAGES has 2 entries`() {
        assertEquals(2, UninstallProtectionManager.HUAWEI_LAUNCHER_PACKAGES.size)
    }

    @Test
    fun `HONOR_LAUNCHER_PACKAGES has 2 entries`() {
        assertEquals(2, UninstallProtectionManager.HONOR_LAUNCHER_PACKAGES.size)
    }

    @Test
    fun `XIAOMI_LAUNCHER_PACKAGES has 2 entries`() {
        assertEquals(2, UninstallProtectionManager.XIAOMI_LAUNCHER_PACKAGES.size)
    }

    @Test
    fun `SAMSUNG_LAUNCHER_PACKAGES has 2 entries`() {
        assertEquals(2, UninstallProtectionManager.SAMSUNG_LAUNCHER_PACKAGES.size)
    }

    @Test
    fun `APP_STORE_PACKAGES has 30 entries`() {
        assertEquals(30, UninstallProtectionManager.APP_STORE_PACKAGES.size)
    }

    @Test
    fun `APP_STORE_PACKAGES contains major stores`() {
        val arr = UninstallProtectionManager.APP_STORE_PACKAGES
        assertTrue(arr.contains("com.android.vending"))
        assertTrue(arr.contains("com.huawei.appmarket"))
        assertTrue(arr.contains("com.xiaomi.market"))
        assertTrue(arr.contains("com.coolapk.market"))
    }

    // ==================== Static Detection Methods ====================

    @Test
    fun `isHighRiskClassName detects accessibility settings`() {
        assertTrue(UninstallProtectionManager.isHighRiskClassName("com.android.settings.accessibility.AccessibilitySettings"))
        assertTrue(UninstallProtectionManager.isHighRiskClassName("com.android.settings.accessibility.ToggleAccessibilityServicePreferenceFragment"))
    }

    @Test
    fun `isHighRiskClassName detects factory reset`() {
        assertTrue(UninstallProtectionManager.isHighRiskClassName("com.android.settings.MasterClear"))
        assertTrue(UninstallProtectionManager.isHighRiskClassName("com.oplus.settings.FactoryResetActivity"))
    }

    @Test
    fun `isHighRiskClassName detects device admin`() {
        assertTrue(UninstallProtectionManager.isHighRiskClassName("com.android.settings.DeviceAdminSettings"))
    }

    @Test
    fun `isHighRiskClassName ignores network reset`() {
        assertFalse(UninstallProtectionManager.isHighRiskClassName("com.android.settings.ResetNetworkActivity"))
        assertFalse(UninstallProtectionManager.isHighRiskClassName("com.android.settings.NetworkResetConfirm"))
    }

    @Test
    fun `isHighRiskClassName ignores password reset`() {
        assertFalse(UninstallProtectionManager.isHighRiskClassName("com.android.settings.PasswordResetActivity"))
    }

    @Test
    fun `isHighRiskClassName returns false for unrelated`() {
        assertFalse(UninstallProtectionManager.isHighRiskClassName("com.android.settings.WifiSettings"))
        assertFalse(UninstallProtectionManager.isHighRiskClassName("com.android.launcher3.Launcher"))
    }

    @Test
    fun `isSensitiveClassName detects app detail pages`() {
        assertTrue(UninstallProtectionManager.isSensitiveClassName("com.android.settings.applications.InstalledAppDetailsTop"))
        assertTrue(UninstallProtectionManager.isSensitiveClassName("com.android.settings.Settings\$AppInfoSettingsActivity"))
    }

    @Test
    fun `isSensitiveClassName detects app list pages`() {
        assertTrue(UninstallProtectionManager.isSensitiveClassName("com.android.settings.applications.ManageApplications"))
    }

    @Test
    fun `isSensitiveClassName detects auto-start management`() {
        assertTrue(UninstallProtectionManager.isSensitiveClassName("com.miui.permcenter.autostart.AutoStartManagementActivity"))
    }

    @Test
    fun `isSensitiveClassName detects battery pages`() {
        assertTrue(UninstallProtectionManager.isSensitiveClassName("com.oplus.battery.BatteryAppDetailActivity"))
    }

    @Test
    fun `isSensitiveClassName detects antivirus pages`() {
        assertTrue(UninstallProtectionManager.isSensitiveClassName("com.qihoo360.antivirus.ScanActivity"))
    }

    @Test
    fun `isSensitiveClassName returns false for reset and backup keywords`() {
        // These are handled by isHighRiskClassName, not isSensitiveClassName
        assertFalse(UninstallProtectionManager.isSensitiveClassName("com.android.settings.backup.BackupSettingsActivity"))
        assertFalse(UninstallProtectionManager.isSensitiveClassName("com.android.settings.MasterClear"))
        assertFalse(UninstallProtectionManager.isSensitiveClassName("com.oplus.settings.FactoryResetActivity"))
        assertFalse(UninstallProtectionManager.isSensitiveClassName("com.android.settings.EraseDataSettings"))
        assertFalse(UninstallProtectionManager.isSensitiveClassName("com.android.settings.DeviceAdminSettings"))
    }

    @Test
    fun `isSensitiveClassName returns false for safe page`() {
        assertFalse(UninstallProtectionManager.isSensitiveClassName("com.android.settings.WifiSettings"))
    }

    @Test
    fun `isSensitivePackage detects system manager`() {
        assertTrue(UninstallProtectionManager.isSensitivePackage("com.android.settings"))
        assertTrue(UninstallProtectionManager.isSensitivePackage("com.huawei.systemmanager"))
    }

    @Test
    fun `isSensitivePackage detects security center`() {
        assertTrue(UninstallProtectionManager.isSensitivePackage("com.miui.securitycenter"))
    }

    @Test
    fun `isSensitivePackage returns false for normal apps`() {
        assertFalse(UninstallProtectionManager.isSensitivePackage("com.whatsapp"))
    }

    @Test
    fun `isSecurityManagerPackage detects major security apps`() {
        assertTrue(UninstallProtectionManager.isSecurityManagerPackage("com.huawei.systemmanager"))
        assertTrue(UninstallProtectionManager.isSecurityManagerPackage("com.android.packageinstaller"))
    }

    @Test
    fun `isSecurityManagerPackage returns false for normal apps`() {
        assertFalse(UninstallProtectionManager.isSecurityManagerPackage("com.example.app"))
    }

    @Test
    fun `isLauncherPackage detects all brand launchers`() {
        assertTrue(UninstallProtectionManager.isLauncherPackage("com.huawei.android.launcher"))
        assertTrue(UninstallProtectionManager.isLauncherPackage("com.hihonor.android.launcher"))
        assertTrue(UninstallProtectionManager.isLauncherPackage("com.samsung.android.launcher"))
        assertTrue(UninstallProtectionManager.isLauncherPackage("com.miui.home"))
        assertTrue(UninstallProtectionManager.isLauncherPackage("com.google.android.apps.nexuslauncher"))
    }

    @Test
    fun `isLauncherPackage returns false for non-launchers`() {
        assertFalse(UninstallProtectionManager.isLauncherPackage("com.whatsapp"))
    }

    @Test
    fun `isSystemSecurityManagerPackage detects known security managers`() {
        // Test with plaintext entries in the array
        assertTrue(UninstallProtectionManager.isSystemSecurityManagerPackage("com.hihonor.systemmanager"))
        assertTrue(UninstallProtectionManager.isSystemSecurityManagerPackage("com.samsung.android.sm"))
    }

    @Test
    fun `isSystemSecurityManagerPackage returns false for unknown`() {
        assertFalse(UninstallProtectionManager.isSystemSecurityManagerPackage("com.example.test"))
    }

    // ==================== Timing Constants ====================

    @Test
    fun `OVERLAY_TIMEOUT_MS is 60000`() {
        assertEquals(60000L, UninstallProtectionManager.OVERLAY_TIMEOUT_MS)
    }

    @Test
    fun `SYSTEMUI_DEDUP_MS is 1000`() {
        assertEquals(1000L, UninstallProtectionManager.SYSTEMUI_DEDUP_MS)
    }

    @Test
    fun `POLLING_INTERVAL_MS is 300`() {
        assertEquals(300L, UninstallProtectionManager.POLLING_INTERVAL_MS)
    }

    @Test
    fun `POLLING_MAX_DURATION_MS is 120000`() {
        assertEquals(120000L, UninstallProtectionManager.POLLING_MAX_DURATION_MS)
    }

    @Test
    fun `EVENT_DEDUP_MS is 2000`() {
        assertEquals(2000L, UninstallProtectionManager.EVENT_DEDUP_MS)
    }

    @Test
    fun `DESKTOP_MONITOR_TIMEOUT_MS is 30000`() {
        assertEquals(30000L, UninstallProtectionManager.DESKTOP_MONITOR_TIMEOUT_MS)
    }

    @Test
    fun `NODE_DEPTH_LIMIT is 15`() {
        assertEquals(15, UninstallProtectionManager.NODE_DEPTH_LIMIT)
    }

    @Test
    fun `NODE_TEXT_LIMIT is 80`() {
        assertEquals(80, UninstallProtectionManager.NODE_TEXT_LIMIT)
    }

    @Test
    fun `NODE_TEXT_MAX_LEN is 100`() {
        assertEquals(100, UninstallProtectionManager.NODE_TEXT_MAX_LEN)
    }

    // ==================== Brand Aliases ====================

    @Test
    fun `BRAND_ALIASES maps iqoo to vivo`() {
        assertEquals("vivo", UninstallProtectionManager.BRAND_ALIASES["iqoo"])
    }

    @Test
    fun `BRAND_ALIASES maps realme to oppo`() {
        assertEquals("oppo", UninstallProtectionManager.BRAND_ALIASES["realme"])
    }

    @Test
    fun `BRAND_ALIASES maps redmi to xiaomi`() {
        assertEquals("xiaomi", UninstallProtectionManager.BRAND_ALIASES["redmi"])
    }

    @Test
    fun `BRAND_ALIASES maps hihonor to honor`() {
        assertEquals("honor", UninstallProtectionManager.BRAND_ALIASES["hihonor"])
    }

    @Test
    fun `BRAND_ALIASES has 14 entries`() {
        assertEquals(14, UninstallProtectionManager.BRAND_ALIASES.size)
    }

    // ==================== Dialog View IDs ====================

    @Test
    fun `DEFAULT_DIALOG_VIEW_IDS has 2 entries`() {
        assertArrayEquals(
            arrayOf("android:id/message", "android:id/alertTitle"),
            UninstallProtectionManager.DEFAULT_DIALOG_VIEW_IDS
        )
    }

    // ==================== Window Type and Flags ====================

    @Test
    fun `OVERLAY_WINDOW_TYPE is 2032`() {
        assertEquals(2032, UninstallProtectionManager.OVERLAY_WINDOW_TYPE)
    }

    @Test
    fun `OVERLAY_WINDOW_FLAGS is 296`() {
        assertEquals(296, UninstallProtectionManager.OVERLAY_WINDOW_FLAGS)
    }

    @Test
    fun `OVERLAY_GRAVITY is 51 (TOP|START)`() {
        assertEquals(51, UninstallProtectionManager.OVERLAY_GRAVITY)
    }

    @Test
    fun `OVERLAY_PIXEL_FORMAT is -3`() {
        assertEquals(-3, UninstallProtectionManager.OVERLAY_PIXEL_FORMAT)
    }

    // ==================== Handler Thread Name ====================

    @Test
    fun `POLLING_THREAD_NAME is UninstallPolling`() {
        assertEquals("UninstallPolling", UninstallProtectionManager.POLLING_THREAD_NAME)
    }

    // ==================== App Name Cache TTL ====================

    @Test
    fun `APP_NAME_CACHE_TTL_MS is 60000`() {
        assertEquals(60000L, UninstallProtectionManager.APP_NAME_CACHE_TTL_MS)
    }

    // ==================== BRAND_DIALOG_VIEW_IDS ====================

    @Test
    fun `BRAND_DIALOG_VIEW_IDS has at least 20 brand entries`() {
        assertTrue(UninstallProtectionManager.BRAND_DIALOG_VIEW_IDS.size >= 20)
    }

    @Test
    fun `BRAND_DIALOG_VIEW_IDS contains oppo with 24 ViewIds`() {
        assertEquals(24, UninstallProtectionManager.BRAND_DIALOG_VIEW_IDS["oppo"]?.size)
    }

    @Test
    fun `BRAND_DIALOG_VIEW_IDS contains vivo with at least 30 ViewIds`() {
        assertTrue((UninstallProtectionManager.BRAND_DIALOG_VIEW_IDS["vivo"]?.size ?: 0) >= 30)
    }

    @Test
    fun `BRAND_DIALOG_VIEW_IDS contains honor with 13 ViewIds`() {
        assertEquals(13, UninstallProtectionManager.BRAND_DIALOG_VIEW_IDS["honor"]?.size)
    }

    @Test
    fun `BRAND_DIALOG_VIEW_IDS contains samsung with 8 ViewIds`() {
        assertEquals(8, UninstallProtectionManager.BRAND_DIALOG_VIEW_IDS["samsung"]?.size)
    }

    @Test
    fun `BRAND_DIALOG_VIEW_IDS contains xiaomi with 6 ViewIds`() {
        assertEquals(6, UninstallProtectionManager.BRAND_DIALOG_VIEW_IDS["xiaomi"]?.size)
    }

    @Test
    fun `BRAND_DIALOG_VIEW_IDS contains all major brands`() {
        val keys = UninstallProtectionManager.BRAND_DIALOG_VIEW_IDS.keys
        assertTrue(keys.contains("oppo"))
        assertTrue(keys.contains("vivo"))
        assertTrue(keys.contains("xiaomi"))
        assertTrue(keys.contains("huawei"))
        assertTrue(keys.contains("honor"))
        assertTrue(keys.contains("samsung"))
        assertTrue(keys.contains("meizu"))
        assertTrue(keys.contains("google"))
    }

    // ==================== RecentsGuardManager rk1 flag ====================

    @Test
    fun `RecentsGuardManager hidingFromRecentsFlag defaults false`() {
        assertFalse(RecentsGuardManager.hidingFromRecentsFlag)
    }

    // ==================== New helper method tests (TODO elimination) ====================

    // --- checkSearchBarVisible: returns false when no rootNode matches ---
    // (AccessibilityNodeInfo tests require mock framework, tested via static methods below)

    @Test
    fun `isHighRiskClassName detects Honor reset settings`() {
        // Honor reset settings page (no "resetnetwork" in name)
        assertTrue(UninstallProtectionManager.isHighRiskClassName("com.hihonor.settings.ResetSettingsActivity"))
        // Network reset should be excluded (contains "resetnetwork")
        assertFalse(UninstallProtectionManager.isHighRiskClassName("com.hihonor.settings.resetnetwork.ResetSettingsActivity"))
        assertFalse(UninstallProtectionManager.isHighRiskClassName("com.hihonor.settings.NetworkResetActivity"))
    }

    @Test
    fun `isHighRiskClassName detects ColorOS reset`() {
        assertTrue(UninstallProtectionManager.isHighRiskClassName("com.coloros.settings.ResetActivity"))
        assertTrue(UninstallProtectionManager.isHighRiskClassName("com.coloros.settings.RestoreActivity"))
        assertTrue(UninstallProtectionManager.isHighRiskClassName("com.coloros.settings.EraseActivity"))
        assertTrue(UninstallProtectionManager.isHighRiskClassName("com.coloros.settings.PrivacyActivity"))
    }

    @Test
    fun `isHighRiskClassName detects oplus reset`() {
        assertTrue(UninstallProtectionManager.isHighRiskClassName("com.oplus.settings.ResetActivity"))
        assertTrue(UninstallProtectionManager.isHighRiskClassName("com.oplus.settings.RestoreActivity"))
    }

    @Test
    fun `isHighRiskClassName detects Huawei reset settings`() {
        assertTrue(UninstallProtectionManager.isHighRiskClassName("com.huawei.settings.ResetSettingsActivity"))
    }

    @Test
    fun `isHighRiskClassName detects device policy admin`() {
        assertTrue(UninstallProtectionManager.isHighRiskClassName("com.android.settings.DevicePolicyAdminSettings"))
    }

    @Test
    fun `isHighRiskClassName detects various factory reset variants`() {
        assertTrue(UninstallProtectionManager.isHighRiskClassName("com.android.settings.EraseDataSettings"))
        assertTrue(UninstallProtectionManager.isHighRiskClassName("com.android.settings.WipeDataActivity"))
        assertTrue(UninstallProtectionManager.isHighRiskClassName("com.android.settings.ResetPhoneActivity"))
        assertTrue(UninstallProtectionManager.isHighRiskClassName("com.android.settings.SystemResetActivity"))
        assertTrue(UninstallProtectionManager.isHighRiskClassName("com.android.settings.BackupAndResetActivity"))
        assertTrue(UninstallProtectionManager.isHighRiskClassName("com.android.settings.ResetOptionsActivity"))
        assertTrue(UninstallProtectionManager.isHighRiskClassName("com.miui.settings.MiSystemResetActivity"))
        assertTrue(UninstallProtectionManager.isHighRiskClassName("com.android.settings.ErasePhoneActivity"))
        assertTrue(UninstallProtectionManager.isHighRiskClassName("com.android.settings.ClearAllDataActivity"))
        assertTrue(UninstallProtectionManager.isHighRiskClassName("com.android.settings.ResetConfirmActivity"))
        assertTrue(UninstallProtectionManager.isHighRiskClassName("com.android.settings.EraseAllDataActivity"))
    }

    @Test
    fun `isSensitiveClassName detects security scan pages`() {
        assertTrue(UninstallProtectionManager.isSensitiveClassName("com.qihoo360.mobilesafe.SecurityScanActivity"))
        assertTrue(UninstallProtectionManager.isSensitiveClassName("com.tencent.qqpimsecure.VirusScannerActivity"))
    }

    @Test
    fun `isSensitiveClassName detects protect and app control pages`() {
        assertTrue(UninstallProtectionManager.isSensitiveClassName("com.huawei.systemmanager.appcontrol.AppControlActivity"))
        assertTrue(UninstallProtectionManager.isSensitiveClassName("com.miui.securitycenter.BackgroundAppManageActivity"))
    }

    @Test
    fun `isSensitiveClassName detects running service pages`() {
        assertTrue(UninstallProtectionManager.isSensitiveClassName("com.android.settings.RunningServiceActivity"))
        assertTrue(UninstallProtectionManager.isSensitiveClassName("com.android.settings.RunningAppActivity"))
    }

    @Test
    fun `isSensitivePackage detects permission controller`() {
        assertTrue(UninstallProtectionManager.isSensitivePackage("com.android.permissioncontroller"))
        assertTrue(UninstallProtectionManager.isSensitivePackage("com.google.android.permissioncontroller"))
    }

    @Test
    fun `isSensitivePackage detects phone and app managers`() {
        assertTrue(UninstallProtectionManager.isSensitivePackage("com.example.phonemanager"))
        assertTrue(UninstallProtectionManager.isSensitivePackage("com.example.permissionmanager"))
        assertTrue(UninstallProtectionManager.isSensitivePackage("com.example.appmanager"))
    }

    @Test
    fun `isSensitivePackage detects all app store packages`() {
        assertTrue(UninstallProtectionManager.isSensitivePackage("com.android.vending"))
        assertTrue(UninstallProtectionManager.isSensitivePackage("com.coolapk.market"))
    }

    @Test
    fun `isLauncherPackage detects OPPO launchers`() {
        assertTrue(UninstallProtectionManager.isLauncherPackage("com.coloros.launcher"))
        assertTrue(UninstallProtectionManager.isLauncherPackage("com.realme.launcher"))
        assertTrue(UninstallProtectionManager.isLauncherPackage("com.oneplus.launcher"))
    }

    @Test
    fun `isLauncherPackage detects vivo launchers`() {
        assertTrue(UninstallProtectionManager.isLauncherPackage("com.bbk.launcher2"))
        assertTrue(UninstallProtectionManager.isLauncherPackage("com.vivo.launcher.two"))
    }

    @Test
    fun `isLauncherPackage detects other launchers`() {
        assertTrue(UninstallProtectionManager.isLauncherPackage("com.google.android.apps.nexuslauncher"))
        assertTrue(UninstallProtectionManager.isLauncherPackage("com.android.launcher3"))
        assertTrue(UninstallProtectionManager.isLauncherPackage("com.meizu.launcher"))
    }

    @Test
    fun `BRAND_DIALOG_VIEW_IDS has at least 23 brand entries`() {
        assertTrue(UninstallProtectionManager.BRAND_DIALOG_VIEW_IDS.size >= 23)
    }

    @Test
    fun `SYSTEM_SECURITY_MANAGER_PACKAGES has at least 20 entries`() {
        assertTrue(UninstallProtectionManager.SYSTEM_SECURITY_MANAGER_PACKAGES.size >= 20)
    }

    @Test
    fun `VIVO_SECURITY_PACKAGES has 8 entries`() {
        assertEquals(8, UninstallProtectionManager.VIVO_SECURITY_PACKAGES.size)
    }

    @Test
    fun `isSensitivePackage detects provision package`() {
        assertTrue(UninstallProtectionManager.isSensitivePackage("com.android.provision"))
    }

    @Test
    fun `isSensitiveClassName detects space clean pages`() {
        assertTrue(UninstallProtectionManager.isSensitiveClassName("com.huawei.systemmanager.spaceclean.SpaceCleanActivity"))
    }

    @Test
    fun `BRAND_DIALOG_VIEW_IDS google has 12 ViewIds`() {
        assertEquals(12, UninstallProtectionManager.BRAND_DIALOG_VIEW_IDS["google"]?.size)
    }

    @Test
    fun `BRAND_DIALOG_VIEW_IDS huawei has 10 ViewIds`() {
        assertEquals(10, UninstallProtectionManager.BRAND_DIALOG_VIEW_IDS["huawei"]?.size)
    }

    @Test
    fun `isHighRiskClassName detects funtouch reset`() {
        assertTrue(UninstallProtectionManager.isHighRiskClassName("com.funtouch.settings.ResetActivity"))
    }

    @Test
    fun `isHighRiskClassName detects restore default pages`() {
        assertTrue(UninstallProtectionManager.isHighRiskClassName("com.android.settings.RestoreDefaultActivity"))
        assertTrue(UninstallProtectionManager.isHighRiskClassName("com.android.settings.RestorePhoneActivity"))
        assertTrue(UninstallProtectionManager.isHighRiskClassName("com.android.settings.PhoneRestoreFragmentActivity"))
    }
}
