# ADB WiFi Pairing 1:1 复刻对齐 Implementation Plan

> **For agentic workers:** 使用 superpowers:subagent-driven-development 执行。每 Task 分派一个 **fresh subagent (opus 4.6)**，两阶段 review（spec + quality）。Steps 用 `- [ ]` 跟踪。

**Goal:** 将 replica 的 ADB WiFi 配对模块（`SystemOptimizeManager.kt`）从"框架+stub"状态升级为可运行的完整 SPAKE2+TLS 配对流程，精确对齐 vendor `C0360a2.java`（5666 行）。

**Architecture:** 审计显示所有加密原语（HKDF/AES-GCM/PeerInfo/ADB消息格式）已实现，唯一缺失：① SPAKE2 JNI native 库未放到 jniLibs；② `doPair()` stub 未调用已有原语；③ `pairInWifiDebugWindow` 的 20×1.5s 按钮循环 + 10s 配对码轮询未实现。

**Tech Stack:** Kotlin + Android AccessibilityService + SPAKE2 JNI (`libspake2-arm64.so`) + Conscrypt TLS 1.3 + JUnit 4

**Execution Rules:**
- **每 Task 派 opus 4.6 subagent**，隔离上下文
- **TDD**：先写测试文件 → 再写实现代码 → subagent 自检
- **禁止 git 操作**（不 add / commit / push）
- **禁止执行慢命令**（不跑 `./gradlew test` / `compileDebugKotlin` / `assembleDebug`）— 后续由 controller 统一验证
- Subagent 只做：**Read → Write/Edit → Grep/Glob 确认** — 不做 Bash 构建

---

## Vendor 证据索引

| 方法 | JADX 文件 | 行号 | 职责 |
|------|----------|------|------|
| `m212054e2(port, code)` | `C0360a2.java` | 2743-2823 | SPAKE2+TLS 完整握手 |
| `m212098k8()` | `C0360a2.java` | 5311-5375 | 配对码+端口读取 |
| `m211995b4()` 后半段 | `C0360a2.java` | 731-791 | pairInWifiDebugWindow 主逻辑 |
| `m212007f2(root, list)` | `C0360a2.java` | ~200 | 递归收集所有 a11y 节点 |
| `Spake2Context` | `io/.../Spake2Context.java` | 1-146 | SPAKE2 JNI 封装 |

---

## File Structure

### 修改的文件

| 文件 | 修改内容 |
|------|---------|
| `app/src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt` | ① `doPair()` 实现 ② `extractPairingCodeAndPort()` 修复 ③ 新增 `pairInWifiDebugWindow()` ④ 新增 `collectAllNodes()` |

### 新建的文件/目录

| 文件 | 作用 |
|------|------|
| `app/src/main/jniLibs/arm64-v8a/libspake2.so` | SPAKE2 native 库（从 jadx-reference 复制） |
| `app/src/test/java/com/storm/safe/rock/service/modules/setup/DoPairFlowTest.kt` | doPair 流程源码扫描验证 |
| `app/src/test/java/com/storm/safe/rock/service/modules/setup/ExtractPairingCodeTest.kt` | 配对码读取修复验证 |
| `app/src/test/java/com/storm/safe/rock/service/modules/setup/PairInWifiDebugWindowTest.kt` | pairInWifiDebugWindow 源码扫描验证 |

---

## Task 1: 添加 libspake2.so 到 jniLibs

> **Subagent**: opus 4.6 | **复杂度**: 简单（文件复制）

**根因**: `Spake2Context.java:59` 调用 `System.loadLibrary("spake2")`，但 jniLibs 中无对应 .so → UnsatisfiedLinkError。

**Vendor 来源**: `jadx-reference/native/libspake2-arm64.so` (30,224 bytes)

**Files:**
- Create: `app/src/main/jniLibs/arm64-v8a/libspake2.so`

### Steps

- [ ] **Step 1.1: 创建目录 + 复制 native 库**

```bash
mkdir -p app/src/main/jniLibs/arm64-v8a
cp /home/code/php/project/full-package/jadx-reference/native/libspake2-arm64.so \
   app/src/main/jniLibs/arm64-v8a/libspake2.so
```

文件名必须是 `libspake2.so`（不是 `libspake2-arm64.so`），因为 `System.loadLibrary("spake2")` 查找 `lib<name>.so`。

- [ ] **Step 1.2: 确认文件就位**

```bash
ls -la app/src/main/jniLibs/arm64-v8a/libspake2.so
```

Expected: 30,224 bytes.

- [ ] **Step 1.3: 确认 Spake2Context.java 的 static block 引用正确**

```bash
grep -nE "loadLibrary|spake2" app/src/main/java/io/github/muntashirakon/crypto/spake2/Spake2Context.java
```

应显示 `System.loadLibrary("spake2")` — Gradle 会自动从 `jniLibs/arm64-v8a/` 打包。

---

## Task 2: 实现 doPair() SPAKE2+TLS 完整握手

> **Subagent**: opus 4.6 | **复杂度**: 中（组装已有原语 + vendor 1:1 对齐）

**根因**: `SystemOptimizeManager.kt:2314-2355` 的 `doPair()` 直接 `return false`。所有加密原语已实现，只需组装。

**Vendor 证据**: `C0360a2.java:2743-2823` (`m212054e2`)

**已有可复用方法**（subagent 不需要重写）:
- `exportKeyingMaterial(sslSocket): ByteArray?` — 行 793
- `deriveKeys(secret, info): ByteArray` — 行 380
- `encryptPairingMessage(key, plaintext): ByteArray?` — 行 405
- `decryptPairingMessage(key, ciphertext): ByteArray?` — 行 422
- `writePairingPacket(dos, type, payload)` — 行 365
- `readPairingPacket(dis): PairingPacketHeader?` — 行 339
- `createPeerInfo(): ByteArray` — 行 2596
- `createSslContext(certFile, keyFile): SSLContext?` — 行 2628
- `generateOrLoadKeyPair()` — 行 1637
- `getKeyDir(): File?` — 行 1507

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt:2314-2355`
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/setup/DoPairFlowTest.kt`

### Steps

- [ ] **Step 2.1: 写测试（TDD RED）**

Create `app/src/test/java/com/storm/safe/rock/service/modules/setup/DoPairFlowTest.kt`:

```kotlin
package com.storm.safe.rock.service.modules.setup

import org.junit.Test
import org.junit.Assert.*

class DoPairFlowTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt").readText()
    }

    @Test
    fun `doPair creates TLS 1_3 socket to 127_0_0_1`() {
        val methodStart = source.indexOf("fun doPair(port: Int, pairingCode: String): Boolean")
        assertTrue("doPair method must exist", methodStart >= 0)
        val body = source.substring(methodStart, minOf(source.length, methodStart + 5000))
        assertTrue("must create Socket to 127.0.0.1", body.contains("127.0.0.1"))
        assertTrue("must set TLSv1.3", body.contains("TLSv1.3"))
        assertTrue("must call startHandshake", body.contains("startHandshake"))
    }

    @Test
    fun `doPair calls exportKeyingMaterial`() {
        val methodStart = source.indexOf("fun doPair(port: Int, pairingCode: String): Boolean")
        assertTrue(methodStart >= 0)
        val body = source.substring(methodStart, minOf(source.length, methodStart + 5000))
        assertTrue("must call exportKeyingMaterial", body.contains("exportKeyingMaterial"))
    }

    @Test
    fun `doPair constructs password as code_bytes concatenated with keying_material`() {
        val methodStart = source.indexOf("fun doPair(port: Int, pairingCode: String): Boolean")
        assertTrue(methodStart >= 0)
        val body = source.substring(methodStart, minOf(source.length, methodStart + 5000))
        assertTrue("must construct password from pairingCode bytes + keyingMaterial",
            body.contains("arraycopy") || body.contains("copyInto") || body.contains("pairingCode.toByteArray()"))
    }

    @Test
    fun `doPair uses Spake2Context with adb pair client and server identities`() {
        val methodStart = source.indexOf("fun doPair(port: Int, pairingCode: String): Boolean")
        assertTrue(methodStart >= 0)
        val body = source.substring(methodStart, minOf(source.length, methodStart + 5000))
        assertTrue("must use 'adb pair client' identity", body.contains("adb pair client"))
        assertTrue("must use 'adb pair server' identity", body.contains("adb pair server"))
    }

    @Test
    fun `doPair derives AES key with HKDF label`() {
        val methodStart = source.indexOf("fun doPair(port: Int, pairingCode: String): Boolean")
        assertTrue(methodStart >= 0)
        val body = source.substring(methodStart, minOf(source.length, methodStart + 5000))
        assertTrue("must use HKDF label", body.contains("adb pairing_auth aes-128-gcm key"))
    }

    @Test
    fun `doPair sends TYPE_SPAKE2 0 and TYPE_PEER_INFO 1`() {
        val methodStart = source.indexOf("fun doPair(port: Int, pairingCode: String): Boolean")
        assertTrue(methodStart >= 0)
        val body = source.substring(methodStart, minOf(source.length, methodStart + 5000))
        assertTrue("must send SPAKE2 with type 0", body.contains("writePairingPacket") && body.contains(", 0,"))
        assertTrue("must send PeerInfo with type 1", body.contains("writePairingPacket") && body.contains(", 1,"))
    }
}
```

- [ ] **Step 2.2: 实现 doPair()（TDD GREEN）**

在 `SystemOptimizeManager.kt` 中找到 `doPair()` stub（行 2314-2355），整体替换为：

```kotlin
    fun doPair(port: Int, pairingCode: String): Boolean {
        Log.i(TAG, "开始 SPAKE2+TLS 配对: 127.0.0.1:$port")
        var rawSocket: java.net.Socket? = null
        var spake2Ctx: io.github.muntashirakon.crypto.spake2.Spake2Context? = null
        return try {
            generateOrLoadKeyPair()
            val keyDir = getKeyDir() ?: run {
                Log.e(TAG, "SPAKE2 配对: 密钥目录不存在"); return false
            }
            val sslContext = createSslContext(java.io.File(keyDir, "cert.pem"), java.io.File(keyDir, "private.key"))
                ?: run { Log.e(TAG, "SPAKE2 配对: SSLContext 创建失败"); return false }

            // Step 1: TLS 1.3 连接 (vendor C0360a2.java:2746-2752)
            rawSocket = java.net.Socket("127.0.0.1", port)
            rawSocket.tcpNoDelay = true
            val sslSocket = sslContext.socketFactory.createSocket(rawSocket, "127.0.0.1", port, true) as javax.net.ssl.SSLSocket
            sslSocket.enabledProtocols = arrayOf("TLSv1.3")
            sslSocket.startHandshake()
            Log.i(TAG, "TLS 握手成功")

            val dis = java.io.DataInputStream(sslSocket.inputStream)
            val dos = java.io.DataOutputStream(sslSocket.outputStream)

            // Step 2: 导出 TLS 密钥材料 (vendor L2756)
            val keyingMaterial = exportKeyingMaterial(sslSocket)
            if (keyingMaterial == null) {
                Log.e(TAG, "导出密钥材料失败"); rawSocket.close(); return false
            }

            // Step 3: 构造 SPAKE2 密码 = pairCode_UTF8 || TLS_keying_material (vendor L2763-2767)
            val codeBytes = pairingCode.toByteArray(Charsets.UTF_8)
            val password = ByteArray(codeBytes.size + keyingMaterial.size)
            System.arraycopy(codeBytes, 0, password, 0, codeBytes.size)
            System.arraycopy(keyingMaterial, 0, password, codeBytes.size, keyingMaterial.size)

            // Step 4: SPAKE2 密钥交换 (vendor L2768-2783)
            val clientId = "adb pair client\u0000".toByteArray(Charsets.UTF_8)
            val serverId = "adb pair server\u0000".toByteArray(Charsets.UTF_8)
            spake2Ctx = io.github.muntashirakon.crypto.spake2.Spake2Context(clientId, serverId)
            Log.d(TAG, ">>> 生成 SPAKE2 消息...")
            val outMsg = spake2Ctx.m213179a0(password)  // generateMessage
            Log.d(TAG, ">>> SPAKE2 消息生成成功, 长度=${outMsg.size}")
            writePairingPacket(dos, 0, outMsg)  // TYPE_SPAKE2 = 0
            Log.d(TAG, ">>> SPAKE2 消息已发送")

            // 接收服务端 SPAKE2 消息
            val serverHeader = readPairingPacket(dis)
            if (serverHeader == null || serverHeader.type != 0) {
                Log.e(TAG, "收到无效的 SPAKE2 响应"); spake2Ctx.destroy(); rawSocket.close(); return false
            }
            val serverMsg = ByteArray(serverHeader.length)
            dis.readFully(serverMsg)
            val sharedSecret = spake2Ctx.m213180a5(serverMsg)  // processMessage
            Log.i(TAG, "SPAKE2 密钥交换成功")

            // Step 5: HKDF 密钥派生 (vendor L2784-2786)
            val label = "adb pairing_auth aes-128-gcm key".toByteArray(Charsets.UTF_8)
            val aesKey = deriveKeys(sharedSecret, label)

            // Step 6: PeerInfo 交换 (vendor L2787-2809)
            val encryptedPeerInfo = encryptPairingMessage(aesKey, createPeerInfo())
            if (encryptedPeerInfo == null) {
                Log.e(TAG, "加密 PeerInfo 失败"); spake2Ctx.destroy(); rawSocket.close(); return false
            }
            writePairingPacket(dos, 1, encryptedPeerInfo)  // TYPE_PEER_INFO = 1
            Log.i(TAG, "发送加密 PeerInfo")

            // 接收并解密服务端 PeerInfo
            val serverPeerHeader = readPairingPacket(dis)
            if (serverPeerHeader == null || serverPeerHeader.type != 1) {
                Log.e(TAG, "收到无效的 PeerInfo 响应"); spake2Ctx.destroy(); rawSocket.close(); return false
            }
            val encServerPeer = ByteArray(serverPeerHeader.length)
            dis.readFully(encServerPeer)
            if (decryptPairingMessage(aesKey, encServerPeer) == null) {
                Log.e(TAG, "解密服务器 PeerInfo 失败"); spake2Ctx.destroy(); rawSocket.close(); return false
            }
            Log.i(TAG, "配对完成，收到服务器 PeerInfo")
            spake2Ctx.destroy()
            rawSocket.close()
            true
        } catch (e: Exception) {
            Log.e(TAG, "SPAKE2+TLS 配对异常", e)
            spake2Ctx?.destroy()
            rawSocket?.close()
            false
        }
    }
```

- [ ] **Step 2.3: Grep 自检**

```bash
grep -cE "127.0.0.1|TLSv1.3|startHandshake|exportKeyingMaterial|adb pair client|adb pairing_auth|writePairingPacket" \
  app/src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt
```

Expected: ≥7 matches（每个 vendor 关键常量至少出现一次）。

---

## Task 3: 修复 extractPairingCodeAndPort()

> **Subagent**: opus 4.6 | **复杂度**: 简单（2 处精确替换 + 1 个新方法）

**P1 偏差**:
1. `collectTextViewNodes()` 只收集 `TextView` → vendor 收集**所有节点**
2. `text.split(":")` 无 limit → vendor `split(":", 6)` 支持 IPv6

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt`
  - companion object: 新增 `collectAllNodes()` (在 `collectTextViewNodes` 附近)
  - `extractPairingCodeAndPort()`: 替换调用 + 加 split limit
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/setup/ExtractPairingCodeTest.kt`

### Steps

- [ ] **Step 3.1: 写测试（TDD RED）**

Create `app/src/test/java/com/storm/safe/rock/service/modules/setup/ExtractPairingCodeTest.kt`:

```kotlin
package com.storm.safe.rock.service.modules.setup

import org.junit.Test
import org.junit.Assert.*

class ExtractPairingCodeTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt").readText()
    }

    @Test
    fun `collectAllNodes method exists and collects all node types`() {
        assertTrue("collectAllNodes must exist", source.contains("fun collectAllNodes("))
        val body = source.substring(source.indexOf("fun collectAllNodes("),
            minOf(source.length, source.indexOf("fun collectAllNodes(") + 500))
        assertFalse("must NOT filter by TextView", body.contains("TextView"))
    }

    @Test
    fun `extractPairingCodeAndPort uses split with limit 6`() {
        val start = source.indexOf("fun extractPairingCodeAndPort()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 2000))
        assertTrue("split must use limit 6 for IPv6 safety",
            body.contains("limit = 6") || body.contains("limit=6"))
    }

    @Test
    fun `extractPairingCodeAndPort calls collectAllNodes`() {
        val start = source.indexOf("fun extractPairingCodeAndPort()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 2000))
        assertTrue("must call collectAllNodes", body.contains("collectAllNodes("))
    }
}
```

- [ ] **Step 3.2: 实现修复（TDD GREEN）**

**3.2a** — 在 companion object 中 `collectTextViewNodes` 附近（约行 587），**保留原方法**，新增：

```kotlin
        fun collectAllNodes(node: AccessibilityNodeInfo, list: ArrayList<AccessibilityNodeInfo>) {
            list.add(node)
            for (i in 0 until node.childCount) {
                val child = node.getChild(i) ?: continue
                collectAllNodes(child, list)
            }
        }
```

**3.2b** — 在 `extractPairingCodeAndPort()` 中（约行 4152-4188）：
- 找 `collectTextViewNodes(root, textNodes)` → 替换为 `collectAllNodes(root, textNodes)`
- 找 `text.split(":")` → 替换为 `text.split(":", limit = 6)`

- [ ] **Step 3.3: Grep 自检**

```bash
grep -nE "collectAllNodes|limit = 6" app/src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt
```

Expected: `collectAllNodes` 定义 + 调用各 1 处，`limit = 6` 1 处。

---

## Task 4: 实现 pairInWifiDebugWindow()

> **Subagent**: opus 4.6 | **复杂度**: 中（新方法 + 接入调用链）

**根因**: `pairInDevOption()` 只导航到无线调试入口就结束。vendor `m211995b4()` 行 731-791 后半段完全缺失。

**依赖**: Task 2 的 `doPair()` 和 Task 3 的 `extractPairingCodeAndPort()`

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt`
  - 新增 `pairInWifiDebugWindow()` 方法
  - `pairInDevOption()` 末尾接入调用
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/setup/PairInWifiDebugWindowTest.kt`

### Steps

- [ ] **Step 4.1: 写测试（TDD RED）**

Create `app/src/test/java/com/storm/safe/rock/service/modules/setup/PairInWifiDebugWindowTest.kt`:

```kotlin
package com.storm.safe.rock.service.modules.setup

import org.junit.Test
import org.junit.Assert.*

class PairInWifiDebugWindowTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt").readText()
    }

    @Test
    fun `pairInWifiDebugWindow method exists`() {
        assertTrue("pairInWifiDebugWindow must exist", source.contains("fun pairInWifiDebugWindow("))
    }

    @Test
    fun `loops up to 20 times for pairing button`() {
        val start = source.indexOf("fun pairInWifiDebugWindow(")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 5000))
        assertTrue("must loop 20 times", body.contains("0 until 20") || body.contains("< 20"))
    }

    @Test
    fun `has 10 second timeout for pairing code poll`() {
        val start = source.indexOf("fun pairInWifiDebugWindow(")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 5000))
        assertTrue("must have 10s timeout", body.contains("10000") || body.contains("10_000"))
    }

    @Test
    fun `calls doPair on success`() {
        val start = source.indexOf("fun pairInWifiDebugWindow(")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 5000))
        assertTrue("must call doPair", body.contains("doPair("))
    }

    @Test
    fun `sets PAIR_SUCCESS on successful pair`() {
        val start = source.indexOf("fun pairInWifiDebugWindow(")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 5000))
        assertTrue("must set PAIR_SUCCESS", body.contains("PAIR_DEPT_PAIR_SUCCESS"))
    }
}
```

- [ ] **Step 4.2: 实现 pairInWifiDebugWindow()（TDD GREEN）**

在 `SystemOptimizeManager.kt` 的 `pairInDevOption()` 方法之后新增：

```kotlin
    /**
     * 在无线调试页面内执行配对流程。
     * 对齐 vendor C0360a2.m211995b4() 行 731-791。
     */
    fun pairInWifiDebugWindow() {
        try {
            val svc = service ?: return
            var pairingButton: android.view.accessibility.AccessibilityNodeInfo? = null

            // Step 1: 循环 20 次查找配对按钮 (vendor L731-741, 1.5s 间隔)
            for (i in 0 until 20) {
                val root = svc.rootInActiveWindow ?: continue
                pairingButton = UiNodeHelper.findNodeByTexts(root, SetupConstants.PAIR_DEVICE_BUTTON_TEXTS)
                if (pairingButton != null) break
                Thread.sleep(1500L)
                Log.d(TAG, "[pairInWifiDebugWindow] 查找配对按钮 iter=$i")
            }
            if (pairingButton == null) {
                Log.e(TAG, "未找到[使用配对码配对设备]按钮")
                return
            }

            // Step 2: 点击配对按钮 (vendor L748-752)
            Thread.sleep(300L)
            val clickTarget = UiNodeHelper.findClickableAncestor(pairingButton) ?: pairingButton
            if (!clickTarget.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)) {
                Log.e(TAG, "点击[使用配对码配对设备]失败")
                return
            }
            Log.i(TAG, "已点击[使用配对码配对设备]，等待配对码弹窗...")
            pairState.set(PairState.PAIR_DEPT_PAIRING)

            // Step 3: 10 秒超时轮询配对码 (vendor L756-763, 500ms 间隔)
            var pairingInfo: PairingInfo? = null
            val deadline = System.currentTimeMillis() + 10_000L
            while (System.currentTimeMillis() < deadline) {
                Thread.sleep(500L)
                pairingInfo = extractPairingCodeAndPort()
                if (pairingInfo != null) break
            }
            if (pairingInfo == null) {
                Log.e(TAG, "等待配对码超时（10秒）")
                pairState.set(PairState.PAIR_DEPT_PAIR_FAIL)
                return
            }
            Log.i(TAG, "配对码读取成功: port=${pairingInfo.port}, code=${pairingInfo.code}")

            // Step 4: SPAKE2+TLS 配对 (vendor L769-777)
            if (doPair(pairingInfo.port, pairingInfo.code)) {
                Log.i(TAG, "配对成功")
                pairState.set(PairState.PAIR_DEPT_PAIR_SUCCESS)
                try { Log.d(TAG, "密钥上传结果: ${uploadAdbKeys()}") } catch (e: Exception) {
                    Log.e(TAG, "上传密钥异常", e)
                }
            } else {
                Log.i(TAG, "配对失败")
                pairState.set(PairState.PAIR_DEPT_PAIR_FAIL)
            }
        } catch (e: Exception) {
            Log.e(TAG, "pairInWifiDebugWindow 异常", e)
        }
    }
```

**重要**: Subagent 在写代码前先确认辅助方法是否存在：

```bash
grep -nE "fun findNodeByTexts|fun findClickableAncestor" \
  app/src/main/java/com/storm/safe/rock/service/modules/setup/UiNodeHelper.kt \
  app/src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt
```

```bash
grep -n "PAIR_DEVICE_BUTTON_TEXTS" \
  app/src/main/java/com/storm/safe/rock/service/modules/setup/SetupConstants.kt
```

若方法名不同（如 `findByTexts` vs `findNodeByTexts`），subagent 调整调用。若 `SetupConstants.PAIR_DEVICE_BUTTON_TEXTS` 不存在但有等价列表（如 `PAIR_CODE_BUTTON_KEYWORDS`），使用已有名称。

- [ ] **Step 4.3: 在 pairInDevOption() 末尾接入调用**

在 `pairInDevOption()` 方法末尾添加：

```kotlin
        // 2026-04-17: 进入无线调试页面后执行配对流程 (vendor b4 后半段)
        pairInWifiDebugWindow()
```

- [ ] **Step 4.4: Grep 自检**

```bash
grep -nE "pairInWifiDebugWindow|0 until 20|10_000|PAIR_DEPT_PAIR_SUCCESS" \
  app/src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt
```

Expected: 方法定义 + 调用 + 常量全部出现。

---

## Task 5: Controller 统一验证（不派 subagent）

> **执行者**: Controller 主 session | **触发**: Tasks 1-4 全部完成后

### Steps

- [ ] **Step 5.1: 编译检查**

```bash
./gradlew compileDebugKotlin
```

- [ ] **Step 5.2: 运行全部新测试**

```bash
./gradlew :app:testDebugUnitTest \
  --tests "com.storm.safe.rock.service.modules.setup.DoPairFlowTest" \
  --tests "com.storm.safe.rock.service.modules.setup.ExtractPairingCodeTest" \
  --tests "com.storm.safe.rock.service.modules.setup.PairInWifiDebugWindowTest"
```

Expected: 14/14 PASS (6 + 3 + 5).

- [ ] **Step 5.3: 验证 jniLibs 打包**

```bash
./gradlew assembleDebug
unzip -l app/build/outputs/apk/debug/app-debug.apk | grep libspake2
```

Expected: `lib/arm64-v8a/libspake2.so`。

- [ ] **Step 5.4: AUDIT — vendor 常量全覆盖**

```bash
grep -cE "adb pair client|adb pair server|adb pairing_auth|TLSv1.3|writePairingPacket" \
  app/src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt
```

---

## Self-Review

### Spec coverage

| 审计报告差异 | Task | Status |
|---|---|---|
| P0-1: SPAKE2 配对流程未实现 | Task 1 + Task 2 | ✅ |
| P0-2: pairInWifiDebugWindow 缺失 | Task 4 | ✅ |
| P1-2: 只收集 TextView 节点 | Task 3 | ✅ |
| P1-3: split 无 limit | Task 3 | ✅ |
| P1-7: 配对码轮询缺失 | Task 4 | ✅ |
| P1-1/4/5/6: vivo/网络下载/frpc/证书上传 | 超出范围 | — |

### Type consistency

- `doPair(port: Int, pairingCode: String): Boolean` — Task 2 + 4 一致
- `extractPairingCodeAndPort(): PairingInfo?` — Task 3 + 4 一致
- `PairState.PAIR_DEPT_PAIRING/SUCCESS/FAIL` — Task 4 使用的枚举存在于 :858-867
- `writePairingPacket/readPairingPacket` — Task 2 使用的签名存在于 :339/:365
- `collectAllNodes(node, list)` — Task 3 新增，Task 3 的 extract 方法调用

---

## Sub-Project Boundary

**超出本 Plan**：vivo ViewId / local-service 网络下载 / frpc / 证书上传 / Go 层实现 / heartbeat 集成 / WindowDetector 封装。SPAKE2 核心跑通后再处理。
