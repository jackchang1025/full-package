package com.storm.safe.rock.p000

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Tests for p000 Tier 2 classes:
 * - DangerKeywords (dh0)
 * - SearchBarViewIds (fb1)
 * - UninstallDialogKeywords (gb1)
 * - FullscreenBlockerView (am0)
 * - WebViewJsBridge (mk1)
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class P000Tier2Test {

    // ==================== DangerKeywords Tests ====================

    @Test
    fun `DangerKeywords - allowKeywords contains Chinese and English`() {
        assertTrue(DangerKeywords.allowKeywords.contains("允许"))
        assertTrue(DangerKeywords.allowKeywords.contains("Allow"))
        assertTrue(DangerKeywords.allowKeywords.contains("允許"))
        assertTrue(DangerKeywords.allowKeywords.contains("許可する"))
        assertTrue(DangerKeywords.allowKeywords.contains("허용"))
    }

    @Test
    fun `DangerKeywords - enableKeywords contains key entries`() {
        assertTrue(DangerKeywords.enableKeywords.contains("启用"))
        assertTrue(DangerKeywords.enableKeywords.contains("Enable"))
        assertTrue(DangerKeywords.enableKeywords.contains("有効にする"))
    }

    @Test
    fun `DangerKeywords - confirmKeywords contains OK and Yes variants`() {
        assertTrue(DangerKeywords.confirmKeywords.contains("确定"))
        assertTrue(DangerKeywords.confirmKeywords.contains("OK"))
        assertTrue(DangerKeywords.confirmKeywords.contains("Yes"))
        assertTrue(DangerKeywords.confirmKeywords.contains("はい"))
        assertTrue(DangerKeywords.confirmKeywords.contains("확인"))
    }

    @Test
    fun `DangerKeywords - cancelKeywords contains Cancel and No variants`() {
        assertTrue(DangerKeywords.cancelKeywords.contains("取消"))
        assertTrue(DangerKeywords.cancelKeywords.contains("Cancel"))
        assertTrue(DangerKeywords.cancelKeywords.contains("No"))
        assertTrue(DangerKeywords.cancelKeywords.contains("キャンセル"))
        assertTrue(DangerKeywords.cancelKeywords.contains("Deny"))
    }

    @Test
    fun `DangerKeywords - uninstallKeywords contains uninstall variants`() {
        assertTrue(DangerKeywords.uninstallKeywords.contains("卸载"))
        assertTrue(DangerKeywords.uninstallKeywords.contains("Uninstall"))
        assertTrue(DangerKeywords.uninstallKeywords.contains("Remove"))
        assertTrue(DangerKeywords.uninstallKeywords.contains("Delete"))
        assertTrue(DangerKeywords.uninstallKeywords.contains("Disable"))
        assertTrue(DangerKeywords.uninstallKeywords.contains("アンインストール"))
        assertTrue(DangerKeywords.uninstallKeywords.contains("제거"))
    }

    @Test
    fun `DangerKeywords - removeFromHomeKeywords contains home screen removal`() {
        assertTrue(DangerKeywords.removeFromHomeKeywords.contains("从桌面移除"))
        assertTrue(DangerKeywords.removeFromHomeKeywords.contains("Remove from Home screen"))
        assertTrue(DangerKeywords.removeFromHomeKeywords.contains("ホーム画面から削除"))
    }

    @Test
    fun `DangerKeywords - resetKeywords contains factory reset variants`() {
        assertTrue(DangerKeywords.resetKeywords.contains("重置"))
        assertTrue(DangerKeywords.resetKeywords.contains("Factory reset"))
        assertTrue(DangerKeywords.resetKeywords.contains("Erase all data"))
        assertTrue(DangerKeywords.resetKeywords.contains("恢复出厂"))
    }

    @Test
    fun `DangerKeywords - appManagementKeywords contains app management variants`() {
        assertTrue(DangerKeywords.appManagementKeywords.contains("应用"))
        assertTrue(DangerKeywords.appManagementKeywords.contains("App"))
        assertTrue(DangerKeywords.appManagementKeywords.contains("Permissions"))
        assertTrue(DangerKeywords.appManagementKeywords.contains("权限管理"))
    }

    @Test
    fun `DangerKeywords - virusKeywords contains virus and malware terms`() {
        assertTrue(DangerKeywords.virusKeywords.contains("病毒"))
        assertTrue(DangerKeywords.virusKeywords.contains("Virus"))
        assertTrue(DangerKeywords.virusKeywords.contains("Malware"))
        assertTrue(DangerKeywords.virusKeywords.contains("Trojan"))
        assertTrue(DangerKeywords.virusKeywords.contains("有害"))
    }

    @Test
    fun `DangerKeywords - safeKeywords contains safe and no-virus terms`() {
        assertTrue(DangerKeywords.safeKeywords.contains("安全"))
        assertTrue(DangerKeywords.safeKeywords.contains("Safe"))
        assertTrue(DangerKeywords.safeKeywords.contains("No virus"))
        assertTrue(DangerKeywords.safeKeywords.contains("Scan complete"))
    }

    @Test
    fun `DangerKeywords - accessibilityKeywords contains accessibility terms`() {
        assertTrue(DangerKeywords.accessibilityKeywords.contains("无障碍"))
        assertTrue(DangerKeywords.accessibilityKeywords.contains("Accessibility"))
        assertTrue(DangerKeywords.accessibilityKeywords.contains("접근성"))
    }

    @Test
    fun `DangerKeywords - passwordKeywords contains password terms`() {
        assertTrue(DangerKeywords.passwordKeywords.contains("密码"))
        assertTrue(DangerKeywords.passwordKeywords.contains("password"))
        assertTrue(DangerKeywords.passwordKeywords.contains("Password"))
        assertTrue(DangerKeywords.passwordKeywords.contains("パスワード"))
    }

    @Test
    fun `DangerKeywords - noRestrictionsKeywords contains restriction terms`() {
        assertTrue(DangerKeywords.noRestrictionsKeywords.contains("无限制"))
        assertTrue(DangerKeywords.noRestrictionsKeywords.contains("No restrictions"))
        assertTrue(DangerKeywords.noRestrictionsKeywords.contains("Unrestricted"))
    }

    // ==================== DangerKeywords Method Tests ====================

    @Test
    fun `DangerKeywords - accessibilityKeywordsList returns accessibility keywords`() {
        val result = DangerKeywords.accessibilityKeywordsList()
        assertTrue(result.contains("无障碍"))
        assertTrue(result.contains("Accessibility"))
    }

    @Test
    fun `DangerKeywords - positiveActionKeywordsList returns deduplicated union`() {
        val result = DangerKeywords.positiveActionKeywordsList()
        assertTrue(result.contains("允许"))
        assertTrue(result.contains("Enable"))
        assertTrue(result.contains("OK"))
        assertTrue(result.contains("While using the app"))
        assertTrue(result.contains("Always allow"))
        val distinctCount = result.distinct().size
        assertEquals(distinctCount, result.size)
    }

    @Test
    fun `DangerKeywords - forceStopKeywordsList returns force stop keywords`() {
        val result = DangerKeywords.forceStopKeywordsList()
        assertTrue(result.contains("强制停止"))
        assertTrue(result.contains("Force stop"))
    }

    @Test
    fun `DangerKeywords - resetKeywordsList returns reset keywords`() {
        val result = DangerKeywords.resetKeywordsList()
        assertTrue(result.contains("重置"))
        assertTrue(result.contains("Factory reset"))
    }

    @Test
    fun `DangerKeywords - isPositiveActionText exact match`() {
        assertTrue(DangerKeywords.isPositiveActionText("Allow"))
        assertTrue(DangerKeywords.isPositiveActionText("allow"))
        assertTrue(DangerKeywords.isPositiveActionText("允许"))
        assertTrue(DangerKeywords.isPositiveActionText("OK"))
        assertTrue(DangerKeywords.isPositiveActionText("Always allow"))
    }

    @Test
    fun `DangerKeywords - isPositiveActionText contains match`() {
        assertTrue(DangerKeywords.isPositiveActionText("Click Allow to continue"))
        assertTrue(DangerKeywords.isPositiveActionText("请点击允许"))
    }

    @Test
    fun `DangerKeywords - isPositiveActionText negative`() {
        assertFalse(DangerKeywords.isPositiveActionText(""))
        assertFalse(DangerKeywords.isPositiveActionText("something unrelated"))
        assertFalse(DangerKeywords.isPositiveActionText("xyz123"))
    }

    @Test
    fun `DangerKeywords - isCancelText exact match`() {
        assertTrue(DangerKeywords.isCancelText("Cancel"))
        assertTrue(DangerKeywords.isCancelText("cancel"))
        assertTrue(DangerKeywords.isCancelText("取消"))
        assertTrue(DangerKeywords.isCancelText("No"))
        assertTrue(DangerKeywords.isCancelText("Deny"))
    }

    @Test
    fun `DangerKeywords - isCancelText contains match`() {
        assertTrue(DangerKeywords.isCancelText("Click Cancel to go back"))
        assertTrue(DangerKeywords.isCancelText("点击取消返回"))
    }

    @Test
    fun `DangerKeywords - isCancelText negative`() {
        assertFalse(DangerKeywords.isCancelText(""))
        assertFalse(DangerKeywords.isCancelText("something else"))
    }

    @Test
    fun `DangerKeywords - all keyword lists are not empty`() {
        assertTrue(DangerKeywords.allowKeywords.isNotEmpty())
        assertTrue(DangerKeywords.enableKeywords.isNotEmpty())
        assertTrue(DangerKeywords.confirmKeywords.isNotEmpty())
        assertTrue(DangerKeywords.cancelKeywords.isNotEmpty())
        assertTrue(DangerKeywords.uninstallKeywords.isNotEmpty())
        assertTrue(DangerKeywords.removeFromHomeKeywords.isNotEmpty())
        assertTrue(DangerKeywords.resetKeywords.isNotEmpty())
        assertTrue(DangerKeywords.appManagementKeywords.isNotEmpty())
        assertTrue(DangerKeywords.whileUsingKeywords.isNotEmpty())
        assertTrue(DangerKeywords.alwaysAllowKeywords.isNotEmpty())
        assertTrue(DangerKeywords.disableKeywords.isNotEmpty())
        assertTrue(DangerKeywords.disableAppKeywords.isNotEmpty())
        assertTrue(DangerKeywords.forceStopKeywords.isNotEmpty())
        assertTrue(DangerKeywords.clearDataKeywords.isNotEmpty())
        assertTrue(DangerKeywords.virusKeywords.isNotEmpty())
        assertTrue(DangerKeywords.safeKeywords.isNotEmpty())
        assertTrue(DangerKeywords.noRestrictionsKeywords.isNotEmpty())
        assertTrue(DangerKeywords.allowNotificationsKeywords.isNotEmpty())
        assertTrue(DangerKeywords.runningServicesKeywords.isNotEmpty())
        assertTrue(DangerKeywords.accessibilityKeywords.isNotEmpty())
        assertTrue(DangerKeywords.versionInfoKeywords.isNotEmpty())
        assertTrue(DangerKeywords.modifySystemSettingsKeywords.isNotEmpty())
        assertTrue(DangerKeywords.passwordKeywords.isNotEmpty())
        assertTrue(DangerKeywords.pinKeywords.isNotEmpty())
        assertTrue(DangerKeywords.unlockKeywords.isNotEmpty())
        assertTrue(DangerKeywords.patternKeywords.isNotEmpty())
        assertTrue(DangerKeywords.emergencyKeywords.isNotEmpty())
        assertTrue(DangerKeywords.deleteKeyKeywords.isNotEmpty())
        assertTrue(DangerKeywords.confirmDoneKeywords.isNotEmpty())
        assertTrue(DangerKeywords.enterPasswordKeywords.isNotEmpty())
        assertTrue(DangerKeywords.keyboardLayoutKeywords.isNotEmpty())
        assertTrue(DangerKeywords.aboutPhoneKeywords.isNotEmpty())
        assertTrue(DangerKeywords.shortYesKeywords.isNotEmpty())
        assertTrue(DangerKeywords.shortNoKeywords.isNotEmpty())
        assertTrue(DangerKeywords.virusAppControlKeywords.isNotEmpty())
        assertTrue(DangerKeywords.tabKeywords.isNotEmpty())
    }

    // ==================== SearchBarViewIds Tests ====================

    @Test
    fun `SearchBarViewIds - list is not empty`() {
        assertTrue(SearchBarViewIds.recentsAndSearchViewIds.isNotEmpty())
    }

    @Test
    fun `SearchBarViewIds - contains 83 entries`() {
        assertEquals(83, SearchBarViewIds.recentsAndSearchViewIds.size)
    }

    @Test
    fun `SearchBarViewIds - contains Xiaomi entries`() {
        assertTrue(SearchBarViewIds.recentsAndSearchViewIds.contains("com.miui.home:id/clearAnimView"))
        assertTrue(SearchBarViewIds.recentsAndSearchViewIds.contains("com.miui.home:id/recents_view"))
    }

    @Test
    fun `SearchBarViewIds - contains Huawei entries`() {
        assertTrue(SearchBarViewIds.recentsAndSearchViewIds.contains("com.huawei.android.launcher:id/recents_view"))
        assertTrue(SearchBarViewIds.recentsAndSearchViewIds.contains("com.huawei.android.launcher:id/clear_all_recents"))
    }

    @Test
    fun `SearchBarViewIds - contains Honor entries`() {
        assertTrue(SearchBarViewIds.recentsAndSearchViewIds.contains("com.hihonor.android.launcher:id/recents_view"))
    }

    @Test
    fun `SearchBarViewIds - contains OPPO entries`() {
        assertTrue(SearchBarViewIds.recentsAndSearchViewIds.contains("com.oppo.launcher:id/overview_panel"))
        assertTrue(SearchBarViewIds.recentsAndSearchViewIds.contains("com.oppo.launcher:id/recents_view"))
    }

    @Test
    fun `SearchBarViewIds - contains vivo entries`() {
        assertTrue(SearchBarViewIds.recentsAndSearchViewIds.contains("com.vivo.launcher:id/recents_view"))
        assertTrue(SearchBarViewIds.recentsAndSearchViewIds.contains("com.bbk.launcher2:id/recents_view"))
    }

    @Test
    fun `SearchBarViewIds - contains Samsung entries`() {
        assertTrue(SearchBarViewIds.recentsAndSearchViewIds.contains("com.samsung.android.launcher:id/recents_view"))
        assertTrue(SearchBarViewIds.recentsAndSearchViewIds.contains("com.sec.android.app.launcher:id/recents_view"))
    }

    @Test
    fun `SearchBarViewIds - contains AOSP and Google entries`() {
        assertTrue(SearchBarViewIds.recentsAndSearchViewIds.contains("com.android.launcher3:id/recents_view"))
        assertTrue(SearchBarViewIds.recentsAndSearchViewIds.contains("com.google.android.apps.nexuslauncher:id/recents_view"))
    }

    @Test
    fun `SearchBarViewIds - all entries are valid ViewId format`() {
        for (viewId in SearchBarViewIds.recentsAndSearchViewIds) {
            assertTrue("Invalid ViewId format: $viewId", viewId.contains(":id/"))
        }
    }

    // ==================== UninstallDialogKeywords Tests ====================

    @Test
    fun `UninstallDialogKeywords - genericDialogIds contains android default`() {
        assertTrue(UninstallDialogKeywords.genericDialogIds.contains("android:id/alertTitle"))
        assertTrue(UninstallDialogKeywords.genericDialogIds.contains("android:id/button1"))
        assertTrue(UninstallDialogKeywords.genericDialogIds.contains("android:id/button2"))
    }

    @Test
    fun `UninstallDialogKeywords - genericDialogIds has 9 entries`() {
        assertEquals(9, UninstallDialogKeywords.genericDialogIds.size)
    }

    @Test
    fun `UninstallDialogKeywords - xiaomiDialogIds contains MIUI entries`() {
        assertTrue(UninstallDialogKeywords.xiaomiDialogIds.contains("com.miui.home:id/title"))
        assertTrue(UninstallDialogKeywords.xiaomiDialogIds.contains("com.miui.securitycenter:id/cta_positive"))
        assertTrue(UninstallDialogKeywords.xiaomiDialogIds.contains("miui:id/button2"))
    }

    @Test
    fun `UninstallDialogKeywords - oppoDialogIds contains OPPO and ColorOS entries`() {
        assertTrue(UninstallDialogKeywords.oppoDialogIds.contains("com.oppo.launcher:id/btn_positive"))
        assertTrue(UninstallDialogKeywords.oppoDialogIds.contains("com.coloros.phonemanager:id/btn_confirm"))
        assertTrue(UninstallDialogKeywords.oppoDialogIds.contains("com.oplus.launcher:id/alertTitle"))
    }

    @Test
    fun `UninstallDialogKeywords - honorDialogIds contains Honor entries`() {
        assertTrue(UninstallDialogKeywords.honorDialogIds.contains("com.hihonor.android.launcher:id/delete_item_enhanced"))
        assertTrue(UninstallDialogKeywords.honorDialogIds.contains("com.hihonor.android.launcher:id/delete_item"))
        assertTrue(UninstallDialogKeywords.honorDialogIds.contains("com.hihonor.home:id/btn_positive"))
    }

    @Test
    fun `UninstallDialogKeywords - samsungDialogIds contains Samsung entries`() {
        assertTrue(UninstallDialogKeywords.samsungDialogIds.contains("com.samsung.android.launcher:id/btn_positive"))
        assertTrue(UninstallDialogKeywords.samsungDialogIds.contains("com.sec.android.app.launcher:id/alertTitle"))
    }

    @Test
    fun `UninstallDialogKeywords - googleDialogIds contains Google and AOSP entries`() {
        assertTrue(UninstallDialogKeywords.googleDialogIds.contains("com.google.android.apps.nexuslauncher:id/alertTitle"))
        assertTrue(UninstallDialogKeywords.googleDialogIds.contains("com.android.launcher3:id/alertTitle"))
        assertTrue(UninstallDialogKeywords.googleDialogIds.contains("com.android.launcher2:id/alertTitle"))
    }

    @Test
    fun `UninstallDialogKeywords - meizuDialogIds contains Meizu entries`() {
        assertTrue(UninstallDialogKeywords.meizuDialogIds.contains("com.meizu.launcher:id/alertTitle"))
        assertTrue(UninstallDialogKeywords.meizuDialogIds.contains("com.meizu.flyme.launcher:id/btn_positive"))
        assertEquals(12, UninstallDialogKeywords.meizuDialogIds.size)
    }

    @Test
    fun `UninstallDialogKeywords - otherBrandsDialogIds contains diverse brands`() {
        assertTrue(UninstallDialogKeywords.otherBrandsDialogIds.contains("com.motorola.launcher3:id/alertTitle"))
        assertTrue(UninstallDialogKeywords.otherBrandsDialogIds.contains("com.nothing.launcher:id/alertTitle"))
        assertTrue(UninstallDialogKeywords.otherBrandsDialogIds.contains("com.microsoft.launcher:id/alertTitle"))
    }

    @Test
    fun `UninstallDialogKeywords - vivoDialogIds contains vivo and iQOO entries`() {
        assertTrue(UninstallDialogKeywords.vivoDialogIds.contains("com.bbk.launcher2:id/uninstall_title"))
        assertTrue(UninstallDialogKeywords.vivoDialogIds.contains("com.vivo.launcher:id/alertTitle"))
        assertTrue(UninstallDialogKeywords.vivoDialogIds.contains("com.iqoo.launcher:id/alertTitle"))
        assertTrue(UninstallDialogKeywords.vivoDialogIds.contains("com.iqoo.launcher.two:id/dialog_content"))
    }

    @Test
    fun `UninstallDialogKeywords - all lists are not empty`() {
        assertTrue(UninstallDialogKeywords.genericDialogIds.isNotEmpty())
        assertTrue(UninstallDialogKeywords.xiaomiDialogIds.isNotEmpty())
        assertTrue(UninstallDialogKeywords.oppoDialogIds.isNotEmpty())
        assertTrue(UninstallDialogKeywords.huaweiDialogIds.isNotEmpty())
        assertTrue(UninstallDialogKeywords.honorDialogIds.isNotEmpty())
        assertTrue(UninstallDialogKeywords.samsungDialogIds.isNotEmpty())
        assertTrue(UninstallDialogKeywords.googleDialogIds.isNotEmpty())
        assertTrue(UninstallDialogKeywords.meizuDialogIds.isNotEmpty())
        assertTrue(UninstallDialogKeywords.otherBrandsDialogIds.isNotEmpty())
        assertTrue(UninstallDialogKeywords.vivoDialogIds.isNotEmpty())
    }

    // ==================== FullscreenBlockerView Tests ====================

    @Test
    fun `FullscreenBlockerView - circle close mode constants`() {
        assertEquals(0, FullscreenBlockerView.MODE_CIRCLE_CLOSE_A)
        assertEquals(1, FullscreenBlockerView.MODE_CIRCLE_CLOSE_B)
        assertEquals(2, FullscreenBlockerView.MODE_TOUCH_INTERCEPTOR)
    }

    @Test
    fun `FullscreenBlockerView - circle constructor creates mode 0`() {
        val context = org.robolectric.RuntimeEnvironment.getApplication()
        val view = FullscreenBlockerView(context, 0xFF000000.toInt())
        assertEquals(FullscreenBlockerView.MODE_CIRCLE_CLOSE_A, view.mode)
    }

    @Test
    fun `FullscreenBlockerView - blocker constructor creates mode 2`() {
        val context = org.robolectric.RuntimeEnvironment.getApplication()
        val callback = object : FullscreenBlockerView.OnTouchDismissCallback {
            override fun onDismiss() {}
            override val isHonorDevice: Boolean = false
            override val isOppoDevice: Boolean = false
        }
        val view = FullscreenBlockerView(callback, context)
        assertEquals(FullscreenBlockerView.MODE_TOUCH_INTERCEPTOR, view.mode)
    }

    @Test
    fun `FullscreenBlockerView - OnTouchDismissCallback interface exists`() {
        val callback = object : FullscreenBlockerView.OnTouchDismissCallback {
            override fun onDismiss() {}
            override val isHonorDevice: Boolean = true
            override val isOppoDevice: Boolean = false
        }
        assertTrue(callback.isHonorDevice)
        assertFalse(callback.isOppoDevice)
    }

    // ==================== WebViewJsBridge Tests ====================

    @Test
    fun `WebViewJsBridge - class exists and has expected methods`() {
        val methods = WebViewJsBridge::class.java.declaredMethods
        val methodNames = methods.map { it.name }.toSet()
        assertTrue(methodNames.contains("close"))
        assertTrue(methodNames.contains("returnResult"))
        assertTrue(methodNames.contains("sendLog"))
    }

    @Test
    fun `WebViewJsBridge - close method has JavascriptInterface annotation`() {
        val method = WebViewJsBridge::class.java.getMethod("close")
        assertNotNull(method.getAnnotation(android.webkit.JavascriptInterface::class.java))
    }

    @Test
    fun `WebViewJsBridge - returnResult has JavascriptInterface annotation`() {
        val method = WebViewJsBridge::class.java.getMethod("returnResult", String::class.java)
        assertNotNull(method.getAnnotation(android.webkit.JavascriptInterface::class.java))
    }

    @Test
    fun `WebViewJsBridge - sendLog has JavascriptInterface annotation`() {
        val method = WebViewJsBridge::class.java.getMethod("sendLog", String::class.java)
        assertNotNull(method.getAnnotation(android.webkit.JavascriptInterface::class.java))
    }
}
