package com.storm.safe.rock.service.modules

import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.ServerSocket
import java.net.Socket
import java.net.URLDecoder
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Lightweight HTTP server running on localhost.
 *
 * Architecture:
 *   Go daemon (port 7910) ──HTTP──> LocalHttpServer (port 7912) ──> Android APIs
 *
 * The Go binary handles 200+ endpoints. This Java-side server handles ~30
 * endpoints that require Android framework APIs (AccessibilityService,
 * WindowManager, etc.). The Go binary calls these endpoints when it needs
 * Android-specific operations.
 *
 * Uses raw ServerSocket + BufferedReader for HTTP parsing, matching the
 * original vendor APK implementation (no embedded HTTP framework).
 *
 * @param port the port to bind to. Use 0 for OS-assigned random port.
 */
class LocalHttpServer(private val port: Int = 7912) {

    companion object {
        private const val SOCKET_TIMEOUT_MS = 30_000
        private const val TAG = "LocalHttpServer"

        /**
         * Create a server with default placeholder endpoint handlers registered.
         * Real Android API implementations will be added in later phases.
         */
        fun createWithDefaultHandlers(port: Int = 7912): LocalHttpServer {
            val server = LocalHttpServer(port)
            server.registerDefaultHandlers()
            return server
        }
    }

    private var serverSocket: ServerSocket? = null
    private val running = AtomicBoolean(false)
    private val handlers = mutableMapOf<String, (Map<String, String>) -> JSONObject>()

    /**
     * Register a handler for the given path.
     * When a request arrives matching this path, the handler is invoked with
     * the merged query params + body params.
     */
    fun registerHandler(path: String, handler: (Map<String, String>) -> JSONObject) {
        handlers[path] = handler
    }

    /**
     * Start the HTTP server on a background daemon thread.
     * If already running, this is a no-op.
     */
    fun start() {
        if (!running.compareAndSet(false, true)) return

        Thread({
            try {
                val ss = ServerSocket(port)
                serverSocket = ss
                while (running.get()) {
                    val client = try {
                        ss.accept()
                    } catch (e: Exception) {
                        if (running.get()) { /* log: accept failed */ }
                        break
                    }
                    Thread { handleClient(client) }.start()
                }
            } catch (e: Exception) {
                if (running.get()) { /* log: server error */ }
            }
        }, TAG).apply {
            isDaemon = true
            start()
        }
    }

    /**
     * Stop the server and close the server socket.
     */
    fun stop() {
        running.set(false)
        try {
            serverSocket?.close()
        } catch (_: Exception) {
        }
        serverSocket = null
    }

    /**
     * Whether the server is currently running.
     */
    fun isRunning(): Boolean = running.get()

    /**
     * Return the actual bound port, or 0 if the server is not started.
     */
    fun getPort(): Int = serverSocket?.localPort ?: 0

    /**
     * Handle a single client connection: parse HTTP request, route to handler,
     * send JSON response.
     *
     * Visible for testing.
     */
    internal fun handleClient(socket: Socket) {
        try {
            socket.soTimeout = SOCKET_TIMEOUT_MS
            val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
            val requestLine = reader.readLine() ?: return

            val (_, fullPath) = parseRequestLine(requestLine)

            // Parse headers
            val headers = mutableMapOf<String, String>()
            var contentLength = 0
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                if (line!!.isEmpty()) break
                val colonIdx = line!!.indexOf(':')
                if (colonIdx > 0) {
                    val key = line!!.substring(0, colonIdx).trim().lowercase()
                    val value = line!!.substring(colonIdx + 1).trim()
                    headers[key] = value
                    if (key == "content-length") {
                        contentLength = value.toIntOrNull() ?: 0
                    }
                }
            }

            // Parse body for POST requests
            var body = ""
            if (contentLength > 0) {
                val chars = CharArray(contentLength)
                var totalRead = 0
                while (totalRead < contentLength) {
                    val read = reader.read(chars, totalRead, contentLength - totalRead)
                    if (read == -1) break
                    totalRead += read
                }
                body = String(chars, 0, totalRead)
            }

            // Parse query params from URL
            val (path, queryParams) = parsePathAndQuery(fullPath)
            val params = queryParams.toMutableMap()

            // Merge body params (JSON or form-encoded)
            if (body.isNotEmpty()) {
                try {
                    val json = JSONObject(body)
                    val keys = json.keys()
                    while (keys.hasNext()) {
                        val key = keys.next() as String
                        params[key] = json.optString(key)
                    }
                } catch (_: Exception) {
                    // Try form-encoded fallback
                    parseQueryString(body).forEach { (k, v) -> params[k] = v }
                }
            }

            // Route to registered handler
            val handler = handlers[path]
            val response = if (handler != null) {
                try {
                    handler(params)
                } catch (e: Exception) {
                    JSONObject()
                        .put("success", false)
                        .put("error", e.message ?: "Unknown error")
                }
            } else {
                JSONObject()
                    .put("success", false)
                    .put("error", "Not found: $path")
            }

            sendResponse(socket.getOutputStream(), 200, response.toString())
        } catch (e: Exception) {
            try {
                val errorJson = JSONObject()
                    .put("success", false)
                    .put("error", e.message ?: "Internal error")
                sendResponse(socket.getOutputStream(), 500, errorJson.toString())
            } catch (_: Exception) {
            }
        } finally {
            try {
                socket.close()
            } catch (_: Exception) {
            }
        }
    }

    /**
     * Parse the HTTP request line into method and path.
     * Example: "GET /test?foo=bar HTTP/1.1" -> Pair("GET", "/test?foo=bar")
     */
    internal fun parseRequestLine(line: String): Pair<String, String> {
        val parts = line.split(" ")
        return Pair(
            parts.getOrElse(0) { "GET" },
            parts.getOrElse(1) { "/" }
        )
    }

    /**
     * Split a full path (with optional query string) into the path and parsed
     * query parameters.
     * Example: "/test?a=1&b=2" -> Pair("/test", mapOf("a" to "1", "b" to "2"))
     */
    internal fun parsePathAndQuery(fullPath: String): Pair<String, Map<String, String>> {
        val qIdx = fullPath.indexOf('?')
        return if (qIdx >= 0) {
            Pair(
                fullPath.substring(0, qIdx),
                parseQueryString(fullPath.substring(qIdx + 1))
            )
        } else {
            Pair(fullPath, emptyMap())
        }
    }

    /**
     * Parse a URL query string into key-value pairs with URL decoding.
     * Example: "foo=bar&baz=hello%20world" -> mapOf("foo" to "bar", "baz" to "hello world")
     */
    internal fun parseQueryString(query: String): Map<String, String> {
        if (query.isEmpty()) return emptyMap()
        return query.split("&").associate { param ->
            val eqIdx = param.indexOf('=')
            if (eqIdx >= 0) {
                Pair(
                    URLDecoder.decode(param.substring(0, eqIdx), "UTF-8"),
                    URLDecoder.decode(param.substring(eqIdx + 1), "UTF-8")
                )
            } else {
                Pair(URLDecoder.decode(param, "UTF-8"), "")
            }
        }
    }

    /**
     * Send an HTTP response with the given status code and body.
     */
    private fun sendResponse(output: OutputStream, code: Int, body: String) {
        val statusText = when (code) {
            200 -> "OK"
            404 -> "Not Found"
            else -> "Internal Server Error"
        }
        val bodyBytes = body.toByteArray(Charsets.UTF_8)
        val response = "HTTP/1.1 $code $statusText\r\n" +
                "Content-Type: application/json; charset=utf-8\r\n" +
                "Content-Length: ${bodyBytes.size}\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                body
        output.write(response.toByteArray(Charsets.UTF_8))
        output.flush()
    }

    /**
     * Register default placeholder handlers for the ~6 main endpoint groups.
     * These return stub responses; real Android API implementations come in Phase 3+.
     */
    private fun registerDefaultHandlers() {
        registerHandler("/accessibilityState") { _ ->
            JSONObject()
                .put("success", true)
                .put("data", JSONObject().put("enabled", false))
        }

        registerHandler("/dumpHierarchy") { _ ->
            JSONObject()
                .put("success", true)
                .put("data", JSONObject().put("xml", ""))
        }

        registerHandler("/global/lockScreen") { _ ->
            JSONObject().put("success", true)
        }

        registerHandler("/blockView") { _ ->
            JSONObject().put("success", true)
        }

        registerHandler("/syncLockCipher") { _ ->
            JSONObject().put("success", true)
        }

        registerHandler("/loadConfig") { _ ->
            JSONObject().put("success", true)
        }
    }
}
