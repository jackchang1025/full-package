package com.storm.safe.rock.service.modules.cipher.vendor

/**
 * SystemUI resource extraction result for brand-specific pattern lock styling.
 *
 * Used by CipherBrandStrategy.readBrandResources() to return brand-resolved
 * dimension/color values from the system UI package.
 */
data class BrandResourceResult(
    val haloSize: Int,
    val innerDotSize: Int,
    val pathWidth: Int,
    val dotColor: Int = 0,
    val pathColor: Int = 0,
    val outerCircleAlpha: Float? = null
)
