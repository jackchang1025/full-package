# Device ↔ Laravel 协议对齐 Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 修复 Android 设备与 Laravel WebSocket/HTTP 之间的 3 个协议不匹配：(1) 缺少 `itype` 路由字段；(2) 缺少 `pid` 设备标识字段；(3) 心跳格式不被 DeviceHandler 识别。

**Architecture:** 所有修改在 Android 端 `NetworkManager.kt` 的 `sendHeartbeat()` 和 `buildEnvelope()` 两个方法中完成。Laravel 端的 auto-routing workaround 保留作为兼容层。不修改 Laravel 代码。

**Tech Stack:** Kotlin (Android), OkHttp WebSocket, Laravel Swoole WebSocket

**真机验证证据:**
- `Unknown message type: fd=6, itype=` — 设备消息缺少 itype 字段
- `Device message missing pid: fd=1` — 设备消息缺少 pid 字段
- 心跳 `subc` != `"ping"` 导致 DeviceHandler 走 forwardToPanel 而非 handlePing

---

## 协议差异总表

| 字段 | Laravel 期望 | Android 当前发送 | 影响 | 修复 |
|------|-------------|-----------------|------|------|
| `itype` | `"Slr_client"` (设备) | 不存在 (用 `type` 加密值) | 消息路由到 Unknown | 在 sendHeartbeat + buildEnvelope 中添加 |
| `pid` | `deviceId` 字符串 | 不存在 (用 `sessionId`) | DeviceHandler 注册失败 | 添加 `pid` 字段 |
| `subc` | `"ping"` (心跳) | 不存在 | 心跳不被识别为 ping | 在 sendHeartbeat 中添加 |

---

## 文件清单

| 操作 | 文件 | 变更 |
|------|------|------|
| Modify | `app/src/main/java/.../modules/NetworkManager.kt` | sendHeartbeat + buildEnvelope 添加 3 个字段 |
| Create | `app/src/test/java/.../modules/ProtocolAlignmentTest.kt` | 5 tests |

---

## Task 1: sendHeartbeat 协议对齐 — 添加 itype + pid + subc

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/NetworkManager.kt`
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/ProtocolAlignmentTest.kt`

**当前 `sendHeartbeat()` (line 527-554):**
```kotlin
fun sendHeartbeat(): Boolean {
    ...
    val payload = buildHeartbeatPayload()
    payload.put("type", StringUtil.decrypt("L1wHM049MyZSMDlNEz9MLA=="))
    payload.put("sessionId", deviceId)
    ...
}
```

**缺少:** `itype`, `pid`, `subc` 字段。

- [ ] **Step 1: 修改 `sendHeartbeat()` — 添加协议字段**

在 `NetworkManager.kt` 中，找到 `sendHeartbeat()` 方法内的以下代码（约 line 532-534）：

```kotlin
            val payload = buildHeartbeatPayload()
            payload.put("type", StringUtil.decrypt("L1wHM049MyZSMDlNEz9MLA=="))
            payload.put("sessionId", deviceId)
```

替换为：

```kotlin
            val payload = buildHeartbeatPayload()
            payload.put("type", StringUtil.decrypt("L1wHM049MyZSMDlNEz9MLA=="))
            payload.put("sessionId", deviceId)
            // Protocol alignment: Laravel WebSocket MessageRouter + DeviceHandler
            payload.put("itype", "Slr_client")
            payload.put("pid", deviceId)
            payload.put("subc", "ping")
```

- [ ] **Step 2: 修改 `buildEnvelope()` — 添加 itype + pid**

在 `NetworkManager.kt` 中，找到 `buildEnvelope()` 方法（约 line 1766-1773）：

```kotlin
    private fun buildEnvelope(type: String, data: JSONObject): JSONObject {
        return JSONObject().apply {
            put("type", type)
            put("sessionId", deviceId)
            put("data", data)
            put("timestamp", System.currentTimeMillis())
        }
    }
```

替换为：

```kotlin
    private fun buildEnvelope(type: String, data: JSONObject): JSONObject {
        return JSONObject().apply {
            put("type", type)
            put("itype", "Slr_client")
            put("pid", deviceId)
            put("sessionId", deviceId)
            put("data", data)
            put("timestamp", System.currentTimeMillis())
        }
    }
```

- [ ] **Step 3: 创建测试文件**

Create `app/src/test/java/com/storm/safe/rock/service/modules/ProtocolAlignmentTest.kt`:

```kotlin
package com.storm.safe.rock.service.modules

import org.junit.Test
import org.junit.Assert.*

class ProtocolAlignmentTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/NetworkManager.kt").readText()
    }

    @Test
    fun `sendHeartbeat includes itype field`() {
        val start = source.indexOf("fun sendHeartbeat()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 500))
        assertTrue("must set itype to Slr_client",
            body.contains("\"itype\"") && body.contains("Slr_client"))
    }

    @Test
    fun `sendHeartbeat includes pid field`() {
        val start = source.indexOf("fun sendHeartbeat()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 500))
        assertTrue("must set pid",
            body.contains("\"pid\""))
    }

    @Test
    fun `sendHeartbeat includes subc ping`() {
        val start = source.indexOf("fun sendHeartbeat()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 500))
        assertTrue("must set subc to ping",
            body.contains("\"subc\"") && body.contains("\"ping\""))
    }

    @Test
    fun `buildEnvelope includes itype field`() {
        val start = source.indexOf("fun buildEnvelope(")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 400))
        assertTrue("must set itype to Slr_client",
            body.contains("\"itype\"") && body.contains("Slr_client"))
    }

    @Test
    fun `buildEnvelope includes pid field`() {
        val start = source.indexOf("fun buildEnvelope(")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 400))
        assertTrue("must set pid",
            body.contains("\"pid\""))
    }
}
```

- [ ] **Step 4: Commit**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/NetworkManager.kt \
       app/src/test/java/com/storm/safe/rock/service/modules/ProtocolAlignmentTest.kt
git commit -m "fix(network): add itype/pid/subc fields for Laravel WebSocket alignment

sendHeartbeat: add itype='Slr_client', pid=deviceId, subc='ping'
buildEnvelope: add itype='Slr_client', pid=deviceId
Fixes: DeviceHandler registration + heartbeat ping recognition"
```

---

## Task 2: 统一验证

- [ ] **Step 1: 运行测试**

```bash
cd /home/code/php/project/full-package/update-replica && \
./gradlew testDebugUnitTest --tests "com.storm.safe.rock.service.modules.ProtocolAlignmentTest" --no-build-cache 2>&1 | tail -10
```

Expected: 5 tests PASS

- [ ] **Step 2: APK 构建 + OPPO 真机验证**

```bash
./gradlew assembleDebug
# Install + restart + connect WebSocket
ADB=$ADB DEV=$DEV
$ADB -s $DEV install -r app/build/outputs/apk/debug/app-debug.apk
$ADB -s $DEV shell am force-stop dev.deltalab2964.swift
$ADB -s $DEV shell settings put secure enabled_accessibility_services \
    "dev.deltalab2964.swift/com.storm.safe.rock.service.MyAccessibilityService"
sleep 6
# Connect WebSocket
$ADB -s $DEV shell "echo -e 'GET /connectWebSocket?url=ws://192.168.31.35:8081&deviceId=4472c5f423c005c5 HTTP/1.1\r\nHost: 127.0.0.1\r\n\r\n' | nc -w 5 127.0.0.1 7910"
sleep 10
# Check server logs
docker exec app-laravel.test-1 tail -10 /var/www/html/storage/logs/websocket/websocket-2026-04-18.log
```

Expected:
- `Device registered: 4472c5f423c005c5 -> fd=X` (直接注册，不经过 auto-routing)
- `Device ping received` (心跳被识别为 ping)
- 无 `Unknown message type` 日志
- 无 `message missing pid` 日志

- [ ] **Step 3: Panel → Device 命令验证**

```python
# 使用之前生成的 token
python3 -c "
import asyncio, json, websockets
async def test():
    async with websockets.connect('ws://localhost:8081/ws/panel') as ws:
        await ws.send(json.dumps({'itype':'slr_panel','subc':'join','pid':'4472c5f423c005c5','token':'TOKEN'}))
        resp = await asyncio.wait_for(ws.recv(), timeout=5)
        data = json.loads(resp)
        print(f'join: type={data.get(\"type\")}')
        # Send lock command
        await ws.send(json.dumps({'itype':'slr_panel','subc':'screen','pid':'4472c5f423c005c5','comand':'L','lockit':'1'}))
        print('lock sent')
asyncio.run(test())
"
```

Expected:
- `join: type=statusBatch` (设备状态回传)
- `lock sent` (命令到达设备)
- 服务器日志: `sendToDevice` 成功（不再 `device not found`）

---

## 超出范围

| 项 | 原因 |
|----|------|
| 删除 Laravel auto-routing workaround | 保留作为兼容层，不影响正确路由 |
| 心跳 `msg` 字段封装 | DeviceHandler.handlePing 读 `msg` 字段，但 Android 发的是扁平对象。当前 handlePing 调用 `deviceStatusService.updateFromPing(phoneId, encodedData)` 可能需要适配，但优先保证路由和注册正确 |
| 加密 `type` 字段解密 | vendor 加密算法产出的值不是有效 itype，保留作为兼容标记 |

---

## Self-Review

1. **Spec coverage:** Gap 1 (itype) → Step 1+2; Gap 2 (pid) → Step 1+2; Gap 3 (subc:ping) → Step 1; Task 2 验证
2. **Placeholder scan:** 无 TBD/TODO
3. **Type consistency:** `"Slr_client"` 与 WebSocketConfig.php L64 一致; `"ping"` 与 DeviceHandler.php L42 一致; `"pid"` 与 WebSocketMessage.php L30 一致
4. **修改最小化:** 仅改 2 个方法 (sendHeartbeat + buildEnvelope)，不改 Laravel 代码
