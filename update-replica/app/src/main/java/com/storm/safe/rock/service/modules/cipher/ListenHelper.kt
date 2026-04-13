package com.storm.safe.rock.service.modules.cipher

import java.io.Serializable

/**
 * 监听辅助类，持有监听模式类型。
 *
 * JADX: ListenHelper.java (35 行)
 * 字段映射:
 *   f53239a0 → a0 (Integer, 监听模式: 0=overlay, 1=pattern(?), 2=adb_coord)
 *   f53238a1 → Companion (C0331a0 实例，包含 clone 方法)
 */
class ListenHelper : Serializable {

    /** 监听模式类型 */
    var a0: Int? = null

    companion object {
        /**
         * 深拷贝 ListenHelper。
         * vendor: C0331a0.clone()
         */
        fun clone(source: ListenHelper?): ListenHelper? {
            if (source == null) return null
            val copy = ListenHelper()
            copy.a0 = source.a0
            return copy
        }
    }
}
