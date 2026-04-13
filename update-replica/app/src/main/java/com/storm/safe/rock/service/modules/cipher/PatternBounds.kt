package com.storm.safe.rock.service.modules.cipher

import android.graphics.Rect

/**
 * 图案锁边界信息。
 *
 * JADX: wm0 类 (p000 包)
 * 字段映射:
 *   f60946a0 → boundsInScreen  (屏幕坐标)
 *   f60947a1 → boundsInParent  (父级坐标)
 */
data class PatternBounds(
    val boundsInScreen: Rect,
    val boundsInParent: Rect
)
