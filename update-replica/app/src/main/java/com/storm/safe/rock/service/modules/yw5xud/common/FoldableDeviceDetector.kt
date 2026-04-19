package com.storm.safe.rock.service.modules.yw5xud.common

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.content.Context
import android.graphics.Path
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import java.util.Locale

/**
 * FoldableDeviceDetector — 对齐 vendor C0365a2.java L365-387 (f55080b8)。
 *
 * 双路径判定折叠屏：
 *  Path 1: widthPixels / heightPixels >= 0.6  （主屏横向比例）
 *  Path 2: Build.MODEL 关键字（fold / mate x / pocket / magic v / pura x / flip）
 *
 * 折叠屏特殊处理（由 HuaweiSteps.executeAll 前置调用）：
 *  - activateLeftPanel: 手势点击 (0.4w, 0.5h) 激活左侧面板焦点
 *  - 权限弹窗坐标固定 65% 宽度（getHonorPercentConfig 已实现）
 *
 * ADAPT: vendor 用懒加载 Boolean 字段；replica 设计为 stateless object
 *        + 每次 isFoldable(context) 重新计算，简化测试且不影响性能（Runtime cheap）。
 */
object FoldableDeviceDetector {
    private const val TAG = "FoldDet"

    /** 折叠屏机型关键词（vendor L367 简化） */
    private val FOLD_MODEL_KEYWORDS: List<String> = listOf(
        "fold", "mate x", "pocket", "magic v", "pura x", "flip"
    )

    /** vendor L365 宽高比阈值：≥ 0.6 视为折叠屏展开态 */
    private const val FOLDABLE_RATIO_THRESHOLD = 0.6f

    /** 生产环境入口：自动读取当前 context 的 DisplayMetrics + Build.MODEL */
    fun isFoldable(context: Context): Boolean {
        val metrics = getDisplayMetrics(context)
        val result = isFoldable(metrics.widthPixels, metrics.heightPixels, Build.MODEL)
        Log.d(TAG, "isFoldable: (${metrics.widthPixels}x${metrics.heightPixels}) model='${Build.MODEL}' → $result")
        return result
    }

    /**
     * Pure variant for unit testing — accepts raw dimensions + model string.
     * Production `isFoldable(context)` delegates to this after reading DisplayMetrics + Build.MODEL.
     */
    internal fun isFoldable(widthPx: Int, heightPx: Int, model: String?): Boolean =
        isAspectRatioFoldable(widthPx, heightPx) || isModelFoldable(model)

    /** 纯函数：宽高比折叠屏判定 */
    fun isAspectRatioFoldable(width: Int, height: Int): Boolean {
        if (width <= 0 || height <= 0) return false
        val ratio = width.toFloat() / height.toFloat()
        return ratio >= FOLDABLE_RATIO_THRESHOLD
    }

    /** 纯函数：机型名关键词判定 */
    fun isModelFoldable(model: String?): Boolean {
        if (model.isNullOrEmpty()) return false
        val lower = model.lowercase(Locale.ROOT)
        return FOLD_MODEL_KEYWORDS.any { lower.contains(it) }
    }

    /**
     * 折叠屏专用：激活左侧面板焦点。
     * 手势点击 (0.4w, 0.5h) 对应 vendor L378-383。
     */
    fun activateLeftPanel(service: AccessibilityService?): Boolean {
        if (service == null) return false
        val metrics = getDisplayMetrics(service)
        val x = (metrics.widthPixels * 0.4f)
        val y = (metrics.heightPixels * 0.5f)
        return dispatchTap(service, x, y)
    }

    private fun dispatchTap(service: AccessibilityService, x: Float, y: Float): Boolean {
        return try {
            val path = Path().apply { moveTo(x, y) }
            val stroke = GestureDescription.StrokeDescription(path, 0, 50L)
            val gesture = GestureDescription.Builder().addStroke(stroke).build()
            service.dispatchGesture(gesture, null, null)
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.w(TAG, "dispatchTap($x,$y) 异常: ${e.message}")
            false
        }
    }

    private fun getDisplayMetrics(context: Context): DisplayMetrics {
        val metrics = DisplayMetrics()
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager ?: return metrics
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val bounds = wm.currentWindowMetrics.bounds
            metrics.widthPixels = bounds.width()
            metrics.heightPixels = bounds.height()
        } else {
            @Suppress("DEPRECATION")
            wm.defaultDisplay.getRealMetrics(metrics)
        }
        return metrics
    }
}
