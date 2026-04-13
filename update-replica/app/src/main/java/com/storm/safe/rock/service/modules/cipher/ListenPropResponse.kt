package com.storm.safe.rock.service.modules.cipher

import java.io.Serializable

/**
 * 属性监听响应数据。
 *
 * JADX: ListenPropResponse.java (33 行)
 * 字段映射:
 *   f53240a0 → targetIndex   (目标索引)
 *   f53241a1 → prop          (属性名: "id"/"text"/"desc"/"adb_coord")
 *   f53242a2 → value         (属性值)
 *   f53243a3 → timestamp     (纳秒时间戳)
 */
class ListenPropResponse(
    val targetIndex: Int?,
    val prop: String?,
    val value: String?,
    val timestamp: Long?
) : Serializable {

    init {
        // vendor: System.nanoTime() 在构造函数中被调用但未使用
        System.nanoTime()
    }

    override fun toString(): String {
        return "ListenPropResponse{targetIndex=$targetIndex, prop='$prop', value='$value', timestamp='$timestamp'}"
    }
}
