# HTTP 线程池 + 端口发现修复 Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 修复两个问题：(1) RemoteConfigManager 单线程阻塞 → 8 线程池；(2) 配对成功后 debugPort=5555（USB 端口）→ 从无障碍树读取无线调试端口。

**Architecture:** Task 1 改 RemoteConfigManager 的 accept loop 为线程池分发。Task 2 在 SystemOptimizeManager 添加 `readDebugPortFromScreen()` 方法并在配对成功后调用。两个 Task 改不同文件，可并行。

**Tech Stack:** Kotlin, Java ExecutorService, Regex, AccessibilityNodeInfo

**Vendor 真理源:**
- HTTP 服务器: `jadx-reference/p000/zb0.java` — `Executors.newFixedThreadPool(8)`, backlog=50
- 端口发现: `C0360a2.java:1431-1465` (m212021i7) — regex 解析 IP:port

---

## Task 1: RemoteConfigManager 线程池 + 端口重试

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/RemoteConfigManager.kt`
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/RemoteConfigThreadPoolTest.kt`

**Vendor (zb0.java):**
- `Executors.newFixedThreadPool(8)` 处理客户端
- `ServerSocket.bind(addr, 50)` backlog=50
- 端口 7910-7918 重试（跳过 7912）
- 非 7910 端口时通知 local-service `/setAppPort?port=X`
- 每个 client `setSoTimeout(10000)`

- [ ] **Step 1: 修改 `start()` — 添加线程池 + 端口重试**

在 `RemoteConfigManager.kt` 中，将 `start()` 方法替换为：

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

        // vendor: Executors.newFixedThreadPool(8)
        executor = Executors.newFixedThreadPool(8)

        val thread = Thread({
            var bound = false
            // vendor: try ports 7910-7918, skip 7912
            for (tryPort in port..(port + 8)) {
                if (tryPort == LOCAL_SERVICE_PORT) {
                    Log.w(TAG, "⚠️ 跳过端口 $tryPort（local-service 保留端口）")
                    continue
                }
                try {
                    val ss = ServerSocket()
                    ss.reuseAddress = true
                    ss.bind(java.net.InetSocketAddress("0.0.0.0", tryPort), 50)
                    serverSocket = ss
                    currentPort = tryPort
                    bound = true
                    Log.i(TAG, "✅ 本地HTTP服务器已启动: 0.0.0.0:$tryPort")

                    // vendor: notify local-service if port != 7910
                    if (tryPort != DEFAULT_PORT) {
                        try {
                            val url = java.net.URL("http://127.0.0.1:$LOCAL_SERVICE_PORT/setAppPort?port=$tryPort")
                            val conn = url.openConnection() as java.net.HttpURLConnection
                            conn.connectTimeout = 2000
                            conn.readTimeout = 2000
                            conn.requestMethod = "GET"
                            conn.responseCode
                            conn.disconnect()
                            Log.i(TAG, "📡 已通知 local-service 实际端口: $tryPort")
                        } catch (e: Exception) {
                            Log.w(TAG, "⚠️ 通知 local-service 端口失败: ${e.message}")
                        }
                    }

                    // Accept loop
                    while (isRunningFlag.get()) {
                        val client = try {
                            ss.accept()
                        } catch (e: Exception) {
                            if (isRunningFlag.get()) Log.w(TAG, "accept 异常: ${e.message}")
                            break
                        }
                        // vendor: submit to thread pool, not synchronous
                        executor?.submit {
                            try {
                                client.soTimeout = 10_000
                                handleClient(client)
                            } catch (e: Exception) {
                                Log.w(TAG, "处理客户端异常: ${e.message}")
                            } finally {
                                try { client.close() } catch (_: Exception) {}
                            }
                        }
                    }
                    break
                } catch (e: java.net.BindException) {
                    Log.w(TAG, "⚠️ 端口 $tryPort 被占用: ${e.message}")
                } catch (e: Exception) {
                    Log.e(TAG, "端口 $tryPort 绑定失败: ${e.message}")
                }
            }
            if (!bound) {
                Log.e(TAG, "❌ 所有端口 (${port}..${port + 8}) 绑定失败")
                isRunningFlag.set(false)
            }
        }, "LocalHttpServer")
        serverThread = thread
        thread.isDaemon = true
        thread.start()
        Log.i(TAG, "RemoteConfigManager starting on port $port")
    }
```

- [ ] **Step 2: 修改 `stop()` — 关闭线程池**

找到 `stop()` 方法，在 `serverThread = null` 之前添加线程池关闭：

在 `executor?.shutdownNow()` 之后（已存在），确认 `executor = null` 也存在。如果 `stop()` 已经有这些语句就不需要改。

- [ ] **Step 3: 创建测试文件**

Create `app/src/test/java/com/storm/safe/rock/service/modules/RemoteConfigThreadPoolTest.kt`:

```kotlin
package com.storm.safe.rock.service.modules

import org.junit.Test
import org.junit.Assert.*

class RemoteConfigThreadPoolTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/RemoteConfigManager.kt").readText()
    }

    @Test
    fun `start uses fixed thread pool of 8`() {
        val start = source.indexOf("fun start(")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 3000))
        assertTrue("must use newFixedThreadPool",
            body.contains("newFixedThreadPool(8)"))
    }

    @Test
    fun `accept loop submits to thread pool not synchronous`() {
        val start = source.indexOf("fun start(")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 3000))
        assertTrue("must submit to executor",
            body.contains("executor?.submit") || body.contains("executor!!.submit"))
        assertFalse("must NOT call handleClient synchronously in accept loop",
            body.contains("handleClient(client)\n"))
    }

    @Test
    fun `start tries multiple ports and skips 7912`() {
        val start = source.indexOf("fun start(")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 3000))
        assertTrue("must skip LOCAL_SERVICE_PORT",
            body.contains("LOCAL_SERVICE_PORT") || body.contains("7912"))
        assertTrue("must try port range",
            body.contains("port + 8") || body.contains("7918"))
    }
}
```

- [ ] **Step 4: Commit**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/RemoteConfigManager.kt \
       app/src/test/java/com/storm/safe/rock/service/modules/RemoteConfigThreadPoolTest.kt
git commit -m "fix(modules): RemoteConfigManager thread pool + port retry

vendor: zb0.java — Executors.newFixedThreadPool(8), ports 7910-7918, backlog=50
Fixes: /adbShell timeout blocking entire 7910 server"
```

---

## Task 2: 从无障碍树读取无线调试端口

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt`
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/setup/ReadDebugPortFromScreenTest.kt`

**Vendor (C0360a2.java:1431-1465, m212021i7):**
- 遍历 accessibility tree 所有节点
- 用 regex `(\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}):(\d+)` 匹配 IP:port
- 提取 group(2) 作为端口
- 验证 30000 <= port < 65536
- 在 pairInPairSuccess 中重试 5 次，每次等 5 秒

**当前问题:** `pairInWifiDebugWindow` 配对成功后调用 `getWirelessDebugPort()`，但该方法只读 Settings.Global + netstat，不从屏幕读。配对弹窗关闭后屏幕回到无线调试详情页，页面上显示 `IP 地址和端口: 192.168.x.x:端口`，这就是无线调试端口。

- [ ] **Step 1: 添加 `readDebugPortFromScreen()` 方法**

在 `getWirelessDebugPort()` 方法之后添加：

```kotlin
    /**
     * Read wireless debug port from accessibility tree.
     * vendor: m212021i7 (line 1431)
     *
     * Traverses all nodes, matches IP:port pattern via regex,
     * returns port if in range 30000-65535.
     */
    fun readDebugPortFromScreen(): Int {
        return try {
            val root = service.rootInActiveWindow ?: return 0
            val nodes = ArrayList<AccessibilityNodeInfo>()
            collectAllNodes(root, nodes)

            val ipPortRegex = Regex("(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}):(\\d+)")
            for (node in nodes) {
                val text = node.text?.toString() ?: continue
                val match = ipPortRegex.find(text) ?: continue
                val port = match.groupValues[2].toIntOrNull() ?: continue
                if (port in 30000 until 65536) {
                    Log.i(TAG, "从屏幕读取到调试端口: $port (text='$text')")
                    return port
                }
            }
            Log.d(TAG, "屏幕上未找到调试端口")
            0
        } catch (e: Exception) {
            Log.e(TAG, "readDebugPortFromScreen 异常", e)
            0
        }
    }
```

- [ ] **Step 2: 修改配对成功后的端口保存逻辑**

在 `pairInWifiDebugWindow()` 中，找到之前添加的端口保存代码（`getWirelessDebugPort()` 调用处），替换为优先从屏幕读取的版本：

找到：
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

替换为：
```kotlin
                // 读取并保存调试端口 (vendor: pairInPairSuccess → m212021i7 读端口)
                // 优先从屏幕读取 (无线调试页面显示 IP:port)，fallback 到 Settings.Global + netstat
                try {
                    var debugPort = 0
                    // vendor: 重试 5 次从屏幕读取，每次等 1 秒
                    for (attempt in 1..5) {
                        debugPort = readDebugPortFromScreen()
                        if (debugPort > 0) {
                            Log.i(TAG, "从屏幕读取到调试端口: $debugPort (第${attempt}次)")
                            break
                        }
                        Log.d(TAG, "第${attempt}次未读到端口，等待重试...")
                        sleep200(5)
                    }
                    // fallback
                    if (debugPort <= 0) {
                        debugPort = getWirelessDebugPort()
                        if (debugPort > 0) Log.i(TAG, "从系统/netstat 读取到调试端口: $debugPort")
                    }
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

- [ ] **Step 3: 创建测试文件**

Create `app/src/test/java/com/storm/safe/rock/service/modules/setup/ReadDebugPortFromScreenTest.kt`:

```kotlin
package com.storm.safe.rock.service.modules.setup

import org.junit.Test
import org.junit.Assert.*

class ReadDebugPortFromScreenTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt").readText()
    }

    @Test
    fun `readDebugPortFromScreen method exists with IP port regex`() {
        assertTrue("readDebugPortFromScreen must exist",
            source.contains("fun readDebugPortFromScreen()"))
        val start = source.indexOf("fun readDebugPortFromScreen()")
        val body = source.substring(start, minOf(source.length, start + 800))
        assertTrue("must use IP:port regex pattern",
            body.contains("\\d{1,3}") && body.contains(":(\\\\d+)"))
    }

    @Test
    fun `readDebugPortFromScreen validates port range 30000-65536`() {
        val start = source.indexOf("fun readDebugPortFromScreen()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 800))
        assertTrue("must check port >= 30000",
            body.contains("30000"))
        assertTrue("must check port < 65536",
            body.contains("65536"))
    }

    @Test
    fun `pairInWifiDebugWindow calls readDebugPortFromScreen with retry`() {
        val start = source.indexOf("fun pairInWifiDebugWindow()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 5000))
        assertTrue("must call readDebugPortFromScreen",
            body.contains("readDebugPortFromScreen()"))
        assertTrue("must retry",
            body.contains("attempt in 1..5") || body.contains("1..5"))
    }
}
```

- [ ] **Step 4: Commit**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt \
       app/src/test/java/com/storm/safe/rock/service/modules/setup/ReadDebugPortFromScreenTest.kt
git commit -m "feat(setup): readDebugPortFromScreen — parse IP:port from accessibility tree

vendor: m212021i7 (C0360a2.java:1431-1465)
Regex: (IP):(port), validates 30000 <= port < 65536
Called after pairing success with 5 retries before fallback to Settings.Global/netstat"
```

---

## Task 3: 统一验证

- [ ] **Step 1: 运行新测试**

```bash
cd /home/code/php/project/full-package/update-replica && \
./gradlew testDebugUnitTest \
    --tests "com.storm.safe.rock.service.modules.RemoteConfigThreadPoolTest" \
    --tests "com.storm.safe.rock.service.modules.setup.ReadDebugPortFromScreenTest" \
    --no-build-cache 2>&1 | tail -10
```

Expected: 6 tests PASS (3+3)

- [ ] **Step 2: APK 构建 + OPPO 真机验证**

```bash
./gradlew assembleDebug 2>&1 | tail -3
ADB=/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe
DEV=OZZL5PLZQOYP4T8T
$ADB -s $DEV install -r app/build/outputs/apk/debug/app-debug.apk
# 验证 7910 不阻塞: 连续发两个请求
$ADB -s $DEV shell "echo -e 'GET / HTTP/1.1\r\nHost: 127.0.0.1\r\n\r\n' | nc -w 2 127.0.0.1 7910" | tail -1
$ADB -s $DEV shell "echo -e 'GET /containerState HTTP/1.1\r\nHost: 127.0.0.1\r\n\r\n' | nc -w 2 127.0.0.1 7910" | tail -1
```

---

## 超出范围

| 项 | 原因 |
|----|------|
| frpc 部署 | 独立功能模块 |
| pairInPairSuccess handler (c41 case onAccessibilityEventInternal$2) | 完整复刻需要单独 plan |
| ADB 连接后的 local-service 部署验证 | 需要真实的无线调试端口（非 USB 5555） |

---

## Self-Review

1. **Spec coverage:** P1 (7910 阻塞) → Task 1 线程池; P2 (debugPort=5555) → Task 2 屏幕读取
2. **Placeholder scan:** 无
3. **Type consistency:** `readDebugPortFromScreen()` 返回 Int (同 `getWirelessDebugPort`); `collectAllNodes` 已存在于文件中 (用于 extractPairingCodeAndPort)
4. **Vendor 对齐:** 线程池 8 个线程 (vendor: `newFixedThreadPool(8)`); 端口重试 7910-7918 跳过 7912; regex `(\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}):(\d+)` 与 vendor m212021i7 一致
