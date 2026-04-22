package com.storm.safe.rock.service.modules.overlay

import android.graphics.Color

/**
 * Unified configuration for the overlay module.
 *
 * Reverse-engineered from JADX: dd0 (MaskConfig, 10 fields) + fd0 (BlackScreen params).
 * Vendor fields: f55699a0..f55708a9 (dd0), f56200a2 (fd0 alpha).
 */
data class OverlayConfig(
    val background: OverlayBackground = OverlayBackground.Image(),
    val touchMode: TouchMode = TouchMode.PASSTHROUGH,
    val preventScreenshot: Boolean = false,
    val showAppIcon: Boolean = true,
    val progressBar: ProgressBarStyle = ProgressBarStyle.GradientBlue(),
    val titleText: String = "配置中请稍后...",
    val subtitleText: String = "正在自动配置和连接\n请勿操作设备",
    val statusText: String = "配置完成后将自动返回应用",
    val titleColor: String = "#FFFFFF",
    val subtitleColor: String = "#CCCCCC",
    val loadingTips: List<String> = DEFAULT_TIPS,
    val keepScreenOn: Boolean = true,
) {
    enum class TouchMode { PASSTHROUGH, INTERCEPT }

    sealed interface OverlayBackground {
        data class SolidColor(
            val color: Int = Color.BLACK,
            val alpha: Float = 1f
        ) : OverlayBackground

        data class Image(
            val assetPaths: List<String> = listOf(
                "app_loading_bg.webp",
                "app_loading_bg.png"
            ),
            val fallbackDrawable: String? = "bg_config_mask",
            val fallbackColor: Int = Color.BLACK
        ) : OverlayBackground
    }

    sealed interface ProgressBarStyle {
        data object None : ProgressBarStyle

        data class GradientBlue(
            val startColor: Int = 0xFF4A90D9.toInt(),
            val endColor: Int = 0xFF67B8F7.toInt(),
            val startFromMax: Boolean = false
        ) : ProgressBarStyle

        data class SystemOrange(
            val color: Int = 0xFFFF9800.toInt(),
            val startFromMax: Boolean = false
        ) : ProgressBarStyle
    }

    companion object {
        val DEFAULT_TIPS = listOf(
            "检查最优线路中",
            "正在连接服务器...",
            "正在加载资源...",
            "正在初始化配置...",
            "正在启动"
        )

        fun configMask() = OverlayConfig()

        fun blackScreen(
            text: String = "",
            alpha: Float = 0.99f,
            interceptTouch: Boolean = true
        ) = OverlayConfig(
            background = OverlayBackground.SolidColor(alpha = alpha),
            touchMode = if (interceptTouch) TouchMode.INTERCEPT else TouchMode.PASSTHROUGH,
            preventScreenshot = true,
            showAppIcon = false,
            progressBar = ProgressBarStyle.None,
            titleText = text,
            subtitleText = "",
            statusText = "",
            loadingTips = emptyList()
        )
    }
}
