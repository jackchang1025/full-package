package com.storm.safe.rock.service.modules.cipher

import android.animation.ValueAnimator

/**
 * 图案锁中每个点的状态。
 *
 * JADX: tm0 类 (p000 包)
 * 字段映射:
 *   f60239a0 → size       (当前大小)
 *   f60240a1 → isAnimating (是否在动画中)
 *   f60241a2 → animX      (动画 X 坐标)
 *   f60242a3 → animY      (动画 Y 坐标)
 *   f60243a4 → animator    (ValueAnimator)
 */
class DotState {
    var size: Int = 0
    var isAnimating: Boolean = false
    var animX: Float = Float.MIN_VALUE
    var animY: Float = Float.MIN_VALUE
    var animator: ValueAnimator? = null
}
