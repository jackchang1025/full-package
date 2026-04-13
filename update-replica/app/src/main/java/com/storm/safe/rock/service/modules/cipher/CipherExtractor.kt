package com.storm.safe.rock.service.modules.cipher

import java.io.Serializable
import java.util.LinkedList
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

/**
 * 密码提取器单例 — 管理上传回调和提取状态。
 *
 * JADX: CipherExtractor.java (50 行)
 * 字段映射:
 *   f53228a0 → INSTANCE (单例)
 *   f53229a1 → pendingTexts (待处理文本列表)
 *   f53230a2 → isProcessing (是否正在处理)
 *   f53231a3 → uploadCallback (上传回调)
 */
object CipherExtractor : Serializable {

    /** 待处理文本列表 */
    val pendingTexts: LinkedList<ListenPropResponse> = LinkedList()

    /** 是否正在处理 */
    val isProcessing: AtomicBoolean = AtomicBoolean(false)

    /** 上传结果回调 */
    var uploadCallback: ((CipherResult) -> Unit)? = null

    init {
        // vendor 初始化: ConcurrentLinkedQueue, ScheduledExecutor, AtomicReference
        // 这些在当前上下文中不直接使用，保留结构
        ConcurrentLinkedQueue<Any>()
        Executors.newSingleThreadScheduledExecutor()
        AtomicReference<Any?>(null)
    }

    /**
     * 判断字符串是否全为数字。
     * vendor: a0 — 遍历每个字符检查 isDigit
     *
     * 注意: vendor 逻辑 — 如果发现 digit 就 continue, 循环结束返回 true。
     * 即: 全部是 digit → return true; 任何非 digit → return false。
     * 空/null → return false。
     */
    fun isAllDigits(str: String?): Boolean {
        if (str.isNullOrEmpty()) return false
        for (i in str.indices) {
            if (!Character.isDigit(str[i])) {
                return false
            }
        }
        // ADAPT: vendor 在循环后 return true, 但逻辑上应该是:
        // 所有字符都是 digit → 不是纯数字密码 (返回 true 表示是数字)
        return true
    }
}
