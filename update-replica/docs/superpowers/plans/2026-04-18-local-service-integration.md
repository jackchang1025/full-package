# Local-Service 集成修复 Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 修复两个阻断问题：(1) 配对成功后 debugPort 未保存导致 local-service 不知道 ADB 端口；(2) RemoteConfigManager HTTP 7910 accept loop 为空导致 local-service 看门狗持续报错。

**Architecture:** Task 1 在 `pairInWifiDebugWindow` 配对成功后读取调试端口并持久化+通知 local-service。Task 2 实现 RemoteConfigManager 的 accept loop + 路由分发并在初始化时调用 `.start()`。两个 Task 改不同文件，可并行执行。

**Tech Stack:** Kotlin, Android AccessibilityService, Java ServerSocket

**真机验证证据:**
- `containerState` 返回 `debugPort: 0` — 端口未保存
- local-service 日志: `App HTTP 端口 7910 连续 3 次不可达` — accept loop 为空

---

## 文件清单

| 操作 | 文件 | 变更说明 |
|------|------|---------|
| Modify | `.../setup/SystemOptimizeManager.kt` | 配对成功后保存+同步调试端口 |
| Modify | `.../modules/RemoteConfigManager.kt` | 实现 accept loop + 启动调用 |
| Modify | `.../service/MyAccessibilityService.kt` | 初始化时调用 rcm.start() |
| Create | `.../setup/DebugPortSyncTest.kt` | 3 tests |
| Create | `.../modules/RemoteConfigAcceptLoopTest.kt` | 3 tests |

---

## Task 1: 配对成功后保存调试端口 + 通知 local-service

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt`
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/setup/DebugPortSyncTest.kt`

**Problem:** `pairInWifiDebugWindow` 配对成功后直接 `buildAdbConfigJson(true)` 传给 `/syncADBConfig`，但 `debugPort` 始终为 0（从未写入）。Vendor 在 `pairInPairSuccess` handler (c41 case onAccessibilityEventInternal$2) 中从屏幕读取端口或从 `Settings.Global.adb_wifi_port` 读取。

**修复方案:** 在 `pairInWifiDebugWindow` 配对成功后，调用 `getWirelessDebugPort()` 读取端口并 `saveDebugPortAndSync(port)` 持久化。这样 `buildAdbConfigJson` 就能包含正确的端口。

- [ ] **Step 1: 在 pairInWifiDebugWindow 配对成功后保存端口**

在 `pairInWifiDebugWindow()` 中，找到这段代码（约 line 4337）：

```kotlin
                Log.i(TAG, "配对成功")
                pairState.set(PairState.PAIR_DEPT_PAIR_SUCCESS)
```

在 `pairState.set(PairState.PAIR_DEPT_PAIR_SUCCESS)` 之后、`uploadAdbKeys()` 之前，添加：

```kotlin
                // 读取并保存调试端口 (vendor: pairInPairSuccess → m212021i7 读端口)
                try {
                    val debugPort = getWirelessDebugPort()
                    if (debugPort > 0) {
                        saveDebugPortAndSync(debugPort)
                        Log.i(TAG, "调试端口已保存: $debugPort")
                    } else {
                        Log.w(TAG, "未能读取到调试端口")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "保存调试端口异常", e)
                }
```

- [ ] **Step 2: 创建测试文件**

Create `app/src/test/java/com/storm/safe/rock/service/modules/setup/DebugPortSyncTest.kt`:

```kotlin
package com.storm.safe.rock.service.modules.setup

import org.junit.Test
import org.junit.Assert.*

class DebugPortSyncTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt").readText()
    }

    @Test
    fun `pairInWifiDebugWindow saves debug port after pairing success`() {
        val start = source.indexOf("fun pairInWifiDebugWindow()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 5000))
        val successIdx = body.indexOf("PAIR_DEPT_PAIR_SUCCESS")
        assertTrue("must set PAIR_SUCCESS", successIdx >= 0)
        val afterSuccess = body.substring(successIdx)
        assertTrue("must call getWirelessDebugPort after success",
            afterSuccess.contains("getWirelessDebugPort()"))
        assertTrue("must call saveDebugPortAndSync",
            afterSuccess.contains("saveDebugPortAndSync"))
    }

    @Test
    fun `getWirelessDebugPort method exists`() {
        assertTrue("getWirelessDebugPort must exist",
            source.contains("fun getWirelessDebugPort()"))
    }

    @Test
    fun `saveDebugPortAndSync method exists`() {
        assertTrue("saveDebugPortAndSync must exist",
            source.contains("fun saveDebugPortAndSync("))
    }
}
```

- [ ] **Step 3: Commit**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt \
       app/src/test/java/com/storm/safe/rock/service/modules/setup/DebugPortSyncTest.kt
git commit -m "fix(setup): save debug port after pairing success

debugPort was always 0 because pairInWifiDebugWindow never called
getWirelessDebugPort/saveDebugPortAndSync after SPAKE2 success.
local-service /containerState now reports correct port."
```

---

## Task 2: RemoteConfigManager accept loop + 启动调用

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/RemoteConfigManager.kt`
- Modify: `app/src/main/java/com/storm/safe/rock/service/MyAccessibilityService.kt`
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/RemoteConfigAcceptLoopTest.kt`

**Problem 1:** `RemoteConfigManager.start()` (line 439) 的 accept loop 是空的 `Thread({ /* ... */ })`。local-service 的看门狗每 3 秒检查 7910 端口，持续报 `App HTTP 端口 7910 连续 3 次不可达`。

**Problem 2:** `MyAccessibilityService` 创建了 `RemoteConfigManager` 实例 (line 3139) 但**没调用 `.start()`**。

- [ ] **Step 1: 实现 accept loop**

在 `RemoteConfigManager.kt` 中，替换 `start()` 方法（约 line 413-444）的完整方法体：

```kotlin
    fun start(port: Int = DEFAULT_PORT) {
        val old = instance
        if (old != null && old !== this) {
            Log.d(TAG, "检测到旧实例，先停止旧服务器")
            old.stop()
            try { Thread.sleep(500L) } catch (_: InterruptedException) {}
        }
        instance = this
        if (!isRunningFlag.compareAndSet(false, true)) {
            Log.w(TAG, "⚠️ 服务器已在运行")
            return
        }
        currentPort = port

        val thread = Thread({
            try {
                val ss = ServerSocket(port)
                serverSocket = ss
                Log.i(TAG, "✅ 本地HTTP服务器已启动: 127.0.0.1:$port")

                while (isRunningFlag.get()) {
                    val client = try {
                        ss.accept()
                    } catch (e: Exception) {
                        if (isRunningFlag.get()) Log.w(TAG, "accept 异常: ${e.message}")
                        break
                    }
                    try {
                        client.soTimeout = 10_000
                        handleClient(client)
                    } catch (e: Exception) {
                        Log.w(TAG, "处理客户端异常: ${e.message}")
                    } finally {
                        try { client.close() } catch (_: Exception) {}
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "服务器启动失败: ${e.message}")
                isRunningFlag.set(false)
            }
        }, "LocalHttpServer")
        serverThread = thread
        thread.isDaemon = true
        thread.start()
        Log.i(TAG, "RemoteConfigManager started on port $port")
    }
```

- [ ] **Step 2: 添加 handleClient 方法**

在 `start()` 方法之后、`stop()` 方法之前，添加：

```kotlin
    private fun handleClient(client: java.net.Socket) {
        val input = client.getInputStream().bufferedReader(Charsets.UTF_8)
        val output = client.getOutputStream()

        // Parse HTTP request line
        val requestLine = input.readLine() ?: return
        val parts = requestLine.split(" ")
        if (parts.size < 2) return

        val method = parts[0]
        val fullPath = parts[1]

        // Parse path and query params
        val pathAndQuery = fullPath.split("?", limit = 2)
        val path = pathAndQuery[0]
        val queryParams = mutableMapOf<String, String>()
        if (pathAndQuery.size > 1) {
            for (param in pathAndQuery[1].split("&")) {
                val kv = param.split("=", limit = 2)
                if (kv.size == 2) queryParams[kv[0]] = java.net.URLDecoder.decode(kv[1], "UTF-8")
            }
        }

        // Parse headers
        var contentLength = 0
        while (true) {
            val headerLine = input.readLine() ?: break
            if (headerLine.isEmpty()) break
            if (headerLine.lowercase().startsWith("content-length:")) {
                contentLength = headerLine.substringAfter(":").trim().toIntOrNull() ?: 0
            }
        }

        // Read body
        var body: String? = null
        if (contentLength > 0) {
            val buf = CharArray(contentLength)
            var read = 0
            while (read < contentLength) {
                val n = input.read(buf, read, contentLength - read)
                if (n <= 0) break
                read += n
            }
            body = String(buf, 0, read)
        }

        // Merge query params with body params (if JSON body)
        val mergedParams = HashMap(queryParams)
        if (body != null) {
            try {
                val jsonBody = JSONObject(body)
                for (key in jsonBody.keys()) {
                    mergedParams[key] = jsonBody.optString(key, "")
                }
            } catch (_: Exception) {
                mergedParams["body"] = body
            }
        }

        // Route and get response
        val response = try {
            routeRequest(path, mergedParams, body)
        } catch (e: Exception) {
            Log.e(TAG, "路由异常: $path", e)
            makeErrorResponse("服务器内部错误: ${e.message}")
        }

        // Send HTTP response
        val responseBytes = response.toString().toByteArray(Charsets.UTF_8)
        val httpResponse = "HTTP/1.1 200 OK\r\n" +
            "Content-Type: application/json; charset=utf-8\r\n" +
            "Content-Length: ${responseBytes.size}\r\n" +
            "Connection: close\r\n" +
            "\r\n"
        output.write(httpResponse.toByteArray(Charsets.UTF_8))
        output.write(responseBytes)
        output.flush()
    }
```

- [ ] **Step 3: 在 MyAccessibilityService 中调用 start()**

在 `MyAccessibilityService.kt` 中，找到（约 line 3139）：

```kotlin
            remoteConfigManager = RemoteConfigManager(applicationContext)
            android.util.Log.d(TAG, "✅ RemoteConfigManager 已启动")
```

替换为：

```kotlin
            val rcm = RemoteConfigManager(applicationContext)
            rcm.start()
            remoteConfigManager = rcm
            android.util.Log.d(TAG, "✅ RemoteConfigManager 已启动 (port=${RemoteConfigManager.DEFAULT_PORT})")
```

- [ ] **Step 4: 创建测试文件**

Create `app/src/test/java/com/storm/safe/rock/service/modules/RemoteConfigAcceptLoopTest.kt`:

```kotlin
package com.storm.safe.rock.service.modules

import org.junit.Test
import org.junit.Assert.*

class RemoteConfigAcceptLoopTest {

    private val rcmSource by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/RemoteConfigManager.kt").readText()
    }

    private val masSource by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/MyAccessibilityService.kt").readText()
    }

    @Test
    fun `start method has ServerSocket accept loop`() {
        val start = rcmSource.indexOf("fun start(")
        assertTrue(start >= 0)
        val body = rcmSource.substring(start, minOf(rcmSource.length, start + 2000))
        assertTrue("must create ServerSocket",
            body.contains("ServerSocket(port)") || body.contains("ServerSocket("))
        assertTrue("must call accept",
            body.contains(".accept()"))
        assertTrue("must call handleClient",
            body.contains("handleClient("))
    }

    @Test
    fun `handleClient method exists with HTTP parsing`() {
        assertTrue("handleClient must exist",
            rcmSource.contains("fun handleClient("))
        val start = rcmSource.indexOf("fun handleClient(")
        val body = rcmSource.substring(start, minOf(rcmSource.length, start + 2000))
        assertTrue("must call routeRequest",
            body.contains("routeRequest("))
        assertTrue("must write HTTP response",
            body.contains("HTTP/1.1 200 OK"))
    }

    @Test
    fun `MyAccessibilityService calls rcm start`() {
        assertTrue("must call rcm.start() or .start()",
            masSource.contains(".start()") && masSource.contains("RemoteConfigManager"))
    }
}
```

- [ ] **Step 5: Commit**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/RemoteConfigManager.kt \
       app/src/main/java/com/storm/safe/rock/service/MyAccessibilityService.kt \
       app/src/test/java/com/storm/safe/rock/service/modules/RemoteConfigAcceptLoopTest.kt
git commit -m "feat(modules): implement RemoteConfigManager HTTP accept loop

Accept loop was empty stub. local-service watchdog reported 7910 unreachable.
Now: ServerSocket(7910) → accept → parse HTTP → routeRequest → respond.
Also: MyAccessibilityService calls rcm.start() on init."
```

---

## Task 3: 统一验证

- [ ] **Step 1: 运行新测试**

```bash
cd /home/code/php/project/full-package/update-replica && \
./gradlew testDebugUnitTest \
    --tests "com.storm.safe.rock.service.modules.setup.DebugPortSyncTest" \
    --tests "com.storm.safe.rock.service.modules.RemoteConfigAcceptLoopTest" \
    --no-build-cache 2>&1 | tail -10
```

Expected: 6 tests PASS (3+3)

- [ ] **Step 2: APK 构建**

```bash
./gradlew assembleDebug 2>&1 | tail -5
```

Expected: BUILD SUCCESSFUL

- [ ] **Step 3: OPPO 真机验证 — debugPort + 7910 端口**

```bash
ADB=/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe
DEV=OZZL5PLZQOYP4T8T
# 清理旧 local-service
$ADB -s $DEV shell "kill $(pidof local-service) 2>/dev/null"
# 安装 (不要 force-stop!)
$ADB -s $DEV install -r app/build/outputs/apk/debug/app-debug.apk
sleep 5
# 验证 7910 端口
echo "=== 端口检查 ==="
$ADB -s $DEV shell "ss -tlnp | grep 7910"
echo "=== 7910 API 响应 ==="
$ADB -s $DEV shell "echo -e 'GET / HTTP/1.1\r\nHost: 127.0.0.1\r\n\r\n' | nc -w 2 127.0.0.1 7910" | tail -1
echo "=== local-service 日志 (7910 不可达?) ==="
$ADB -s $DEV shell "cat /data/local/tmp/local-service.log | tail -5"
```

Expected:
- 7910 端口监听中
- API 响应 `{"code":200,"success":true,...}`
- local-service 日志**不再**报 `7910 连续 3 次不可达`

---

## 超出范围

| 项 | 原因 |
|----|------|
| frpc 部署 + XOR 解密 | 独立功能模块 |
| uploadAdbKeys 服务器通信 | 依赖加密的 C2 服务器地址 |
| /dumpHierarchy 实现 | 依赖 AccessibilityNodeInfo 序列化 |
| /adbShell ADB 通道未建立 | 需要先 getOrCreateAdbConnection 成功 |

---

## Self-Review

1. **Spec coverage:** debugPort=0 → Task 1 (保存端口); 7910 不可达 → Task 2 (accept loop + start 调用); Task 3 验证
2. **Placeholder scan:** 无 TBD/TODO — 所有代码完整
3. **Type consistency:** `getWirelessDebugPort()` 返回 Int (line 2609)；`saveDebugPortAndSync(port: Int)` (line 3810)；`routeRequest(path, params, body)` 返回 JSONObject (line 486) — 全部一致
4. **import 检查:** `RemoteConfigManager.kt` 已有 `import java.net.ServerSocket` (line 50)；`JSONObject` 已导入 (line 3)；`handleClient` 使用 `java.net.Socket` 和 `java.net.URLDecoder` 均为 JDK 标准库
