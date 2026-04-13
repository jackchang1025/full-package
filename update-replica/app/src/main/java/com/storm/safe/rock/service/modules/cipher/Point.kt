package com.storm.safe.rock.service.modules.cipher

import java.io.Serializable
import java.util.Objects

/**
 * 浮点坐标点。
 *
 * JADX: Point.java (42 行)
 * 字段映射:
 *   f53261a0 → x
 *   f53262a1 → y
 */
data class Point(
    val x: Float,
    val y: Float
) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Point::class.java != other.javaClass) return false
        val point = other as Point
        return java.lang.Float.compare(point.x, x) == 0 && java.lang.Float.compare(point.y, y) == 0
    }

    override fun hashCode(): Int {
        return Objects.hash(x, y)
    }

    override fun toString(): String {
        return "Point{x=$x, y=$y}"
    }
}
