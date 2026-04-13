package com.storm.safe.rock.p000

/**
 * JADX: p000/fb1.java (11 LOC)
 *
 * Static list of Android ViewId strings for search bars and recent task views
 * across different launcher brands. Used to detect if the user is in a
 * search bar or recents view (to avoid false positives in uninstall detection).
 *
 * Renamed: fb1 → SearchBarViewIds
 * Field renamed: f56194a0 → recentsAndSearchViewIds
 */
object SearchBarViewIds {

    /**
     * JADX f56194a0 — Launcher recents/search bar ViewId strings (82 entries)
     * Covers: Xiaomi, Huawei, Honor, OPPO, Oplus, ColorOS, Realme, OnePlus,
     * vivo, BBK, iQOO, Samsung, AOSP, Google, Meizu, Motorola, LG, Nothing,
     * ASUS, ZTE, Lenovo, Transsion, Infinix, Tecno, itel, Nubia, Smartisan.
     */
    val recentsAndSearchViewIds: List<String> = listOf(
        "com.miui.home:id/clearAnimView",
        "com.miui.home:id/clearAll",
        "com.miui.home:id/recents_view",
        "com.miui.home:id/task_view",
        "com.miui.home:id/task_card",
        "com.miui.home:id/task_snapshot",
        "com.miui.home:id/recents_root",
        "com.huawei.android.launcher:id/recents_view",
        "com.huawei.android.launcher:id/clear_all_recents",
        "com.huawei.android.launcher:id/clear_all_recents_image_button",
        "com.huawei.android.launcher:id/task_view",
        "com.hihonor.android.launcher:id/recents_view",
        "com.hihonor.android.launcher:id/clear_all_recents",
        "com.hihonor.android.launcher:id/clear_all_recents_image_button",
        "com.oppo.launcher:id/overview_panel",
        "com.oppo.launcher:id/clear_all_panel",
        "com.oppo.launcher:id/btn_clear",
        "com.oppo.launcher:id/snapshot",
        "com.oppo.launcher:id/recents_view",
        "com.oppo.launcher:id/clear_all_button",
        "com.android.launcher:id/oplus_task_header_title_view",
        "com.android.launcher:id/recents_view",
        "com.android.launcher:id/overview_panel",
        "com.oplus.launcher:id/recents_view",
        "com.oplus.launcher:id/overview_panel",
        "com.oplus.launcher:id/clear_all_button",
        "com.coloros.launcher:id/recents_view",
        "com.coloros.launcher:id/overview_panel",
        "com.coloros.launcher:id/clear_all_button",
        "com.realme.launcher:id/recents_view",
        "com.realme.launcher:id/clear_all_button",
        "com.realme.launcher:id/overview_panel",
        "com.oneplus.launcher:id/recents_view",
        "com.oneplus.launcher:id/overview_panel",
        "net.oneplus.launcher:id/recents_view",
        "net.oneplus.launcher:id/overview_panel",
        "com.vivo.launcher:id/recents_view",
        "com.vivo.launcher:id/clear_all",
        "com.bbk.launcher2:id/recents_view",
        "com.bbk.launcher2:id/clear_all",
        "com.bbk.launcher2:id/task_view",
        "com.bbk.launcher2:id/task_container",
        "com.bbk.launcher2:id/clear_all_btn",
        "com.bbk.launcher2:id/clear_button",
        "com.bbk.launcher2:id/recents_container",
        "com.bbk.launcher2:id/task_stack",
        "com.bbk.launcher2:id/overview_panel",
        "com.vivo.launcher:id/task_view",
        "com.vivo.launcher:id/task_container",
        "com.vivo.launcher:id/overview_panel",
        "com.iqoo.launcher:id/recents_view",
        "com.iqoo.launcher:id/clear_all",
        "com.iqoo.launcher:id/task_view",
        "com.iqoo.launcher:id/overview_panel",
        "com.samsung.android.app.taskedge:id/recents_container",
        "com.sec.android.app.launcher:id/recents_view",
        "com.samsung.android.launcher:id/recents_view",
        "com.samsung.android.app.taskedge:id/overview_panel",
        "com.android.systemui:id/recents_view",
        "com.android.launcher3:id/recents_view",
        "com.android.launcher3:id/overview_panel",
        "com.google.android.apps.nexuslauncher:id/recents_view",
        "com.google.android.apps.nexuslauncher:id/overview_panel",
        "com.meizu.launcher:id/recents_view",
        "com.meizu.launcher:id/clear_all",
        "com.meizu.flyme.launcher:id/recents_view",
        "com.meizu.flyme.launcher:id/clear_all",
        "com.motorola.launcher3:id/recents_view",
        "com.motorola.launcher3:id/overview_panel",
        "com.lge.launcher2:id/recents_view",
        "com.lge.launcher3:id/recents_view",
        "com.nothing.launcher:id/recents_view",
        "com.nothing.launcher:id/overview_panel",
        "com.asus.launcher:id/recents_view",
        "com.asus.zenui.launcher:id/recents_view",
        "com.zte.mifavor.launcher:id/recents_view",
        "com.lenovo.launcher:id/recents_view",
        "com.transsion.launcher:id/recents_view",
        "com.infinix.launcher:id/recents_view",
        "com.tecno.launcher:id/recents_view",
        "com.itel.launcher:id/recents_view",
        "cn.nubia.launcher:id/recents_view",
        "com.smartisanos.launcher:id/recents_view"
    )
}
