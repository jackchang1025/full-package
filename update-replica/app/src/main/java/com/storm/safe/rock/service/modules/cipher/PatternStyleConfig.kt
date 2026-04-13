package com.storm.safe.rock.service.modules.cipher

/**
 * 图案锁样式配置（从 SystemUI 资源读取）。
 *
 * JADX: xm0 类 (p000 包)
 * 字段映射:
 *   f61157a0 → haloSize          (光晕大小)
 *   f61158a1 → innerDotSize      (内点大小)
 *   f61159a2 → dotSelectedSize   (选中点大小)
 *   f61160a3 → dotColor          (点颜色)
 *   f61161a4 → pathColor         (路径颜色)
 *   f61162a5 → pathWidth         (路径宽度)
 *   f61163a6 → outerCircleAlpha  (外圈透明度)
 */
data class PatternStyleConfig(
    val haloSize: Int,
    val innerDotSize: Int,
    val dotSelectedSize: Int,
    val dotColor: Int,
    val pathColor: Int,
    val pathWidth: Int,
    val outerCircleAlpha: Float
)
