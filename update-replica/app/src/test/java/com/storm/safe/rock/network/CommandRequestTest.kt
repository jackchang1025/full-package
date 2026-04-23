package com.storm.safe.rock.network

import org.json.JSONObject
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CommandRequestTest {

    @Test
    fun `fromJson parses command and params`() {
        val json = JSONObject().apply {
            put("command", "LAUNCH_APP")
            put("params", JSONObject().apply {
                put("packageName", "com.example.app")
                put("retry", 3)
            })
        }
        val req = CommandRequest.fromJson(json)
        assertEquals("LAUNCH_APP", req.command)
        assertEquals("com.example.app", req.params["packageName"])
        assertEquals(3, req.params["retry"])
    }

    @Test
    fun `fromJson with missing params returns empty map`() {
        val json = JSONObject().apply {
            put("command", "MUTE")
        }
        val req = CommandRequest.fromJson(json)
        assertEquals("MUTE", req.command)
        assertTrue(req.params.isEmpty())
    }

    @Test
    fun `fromJson merges top-level fields into params`() {
        val json = JSONObject().apply {
            put("command", "FILE_DOWNLOAD")
            put("params", JSONObject().apply {
                put("url", "https://example.com/file")
            })
            put("taskId", "abc123")
            put("priority", 1)
        }
        val req = CommandRequest.fromJson(json)
        assertEquals("FILE_DOWNLOAD", req.command)
        assertEquals("https://example.com/file", req.params["url"])
        assertEquals("abc123", req.params["taskId"])
        assertEquals(1, req.params["priority"])
    }

    @Test
    fun `fromJson with empty command returns empty string`() {
        val json = JSONObject()
        val req = CommandRequest.fromJson(json)
        assertEquals("", req.command)
        assertTrue(req.params.isEmpty())
    }

    @Test
    fun `getStringParam returns param or default`() {
        val req = CommandRequest("TEST", mapOf("key" to "value"))
        assertEquals("value", req.getStringParam("key"))
        assertEquals("", req.getStringParam("missing"))
        assertEquals("fallback", req.getStringParam("missing", "fallback"))
    }

    @Test
    fun `getIntParam returns param or default`() {
        val req = CommandRequest("TEST", mapOf("count" to 5))
        assertEquals(5, req.getIntParam("count"))
        assertEquals(0, req.getIntParam("missing"))
        assertEquals(-1, req.getIntParam("missing", -1))
    }
}
