package com.storm.safe.rock.service.modules.yw5xud.huawei

import android.util.Log

/**
 * HuaweiStepLogger — 华为 10 步流程的结构化日志工具。
 *
 * 统一每一步的日志格式，方便真机调试定位失败点：
 * - `▶ action` — 开始某个动作
 * - `✅ action` — 动作成功
 * - `❌ action` — 动作失败
 * - `⏭ reason` — 跳过
 * - `🔍 probe` — 诊断信息（页面状态、节点查找结果等）
 *
 * 真机 logcat 过滤示例：
 *   adb logcat -v time HuaweiSteps:* *:S
 *
 * 每条日志**同时**打 `Log.i/d/w` 到 logcat **和** 追加到 `logs` / `failures` 列表（用于 auth 总结报告）。
 */
internal object HuaweiStepLogger {
    private const val TAG = "HuaweiSteps"

    /** 开始某个动作 —— `▶`。 */
    fun phase(step: Int, action: String, detail: String = "", logs: MutableList<String>? = null) {
        val msg = "[Step $step/10] ▶ $action${if (detail.isNotEmpty()) " | $detail" else ""}"
        Log.i(TAG, msg)
        logs?.add(msg)
    }

    /** 动作成功 —— `✅`。 */
    fun success(step: Int, action: String, detail: String = "", successes: MutableList<String>? = null) {
        val msg = "[Step $step/10] ✅ $action${if (detail.isNotEmpty()) " | $detail" else ""}"
        Log.i(TAG, msg)
        successes?.add(msg)
    }

    /** 动作失败 —— `❌`。 */
    fun fail(step: Int, action: String, detail: String = "", failures: MutableList<String>? = null) {
        val msg = "[Step $step/10] ❌ $action${if (detail.isNotEmpty()) " | $detail" else ""}"
        Log.w(TAG, msg)
        failures?.add(msg)
    }

    /** 跳过 —— `⏭`。 */
    fun skip(step: Int, reason: String, logs: MutableList<String>? = null) {
        val msg = "[Step $step/10] ⏭ SKIP | $reason"
        Log.i(TAG, msg)
        logs?.add(msg)
    }

    /** 诊断信息 —— `🔍`。只打 logcat（Debug 级别），不追加到 logs（避免污染 auth 汇总）。 */
    fun probe(step: Int, what: String, value: Any?) {
        Log.d(TAG, "[Step $step/10] 🔍 $what = $value")
    }

    /** 警告 —— `⚠️`。不归类为 failure，仅提示注意（例如"开关已是目标状态"）。 */
    fun warn(step: Int, action: String, detail: String = "", logs: MutableList<String>? = null) {
        val msg = "[Step $step/10] ⚠️ $action${if (detail.isNotEmpty()) " | $detail" else ""}"
        Log.w(TAG, msg)
        logs?.add(msg)
    }
}
