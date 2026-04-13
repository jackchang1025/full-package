# libadb-android 库替换 ADB 逆向代码 Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 用 libadb-android 3.1.1 原始库替换 26 个手动逆向的 ADB 协议文件，修复 CNXN→STLS fall-through 导致的 shell 失败，减少 ~2000 行代码。

**Architecture:** 保留 `AdbConnectionManager` 作为业务逻辑层（shell 执行、手势回放、文件推送、端口扫描），将其底层从手写 ADB 协议栈改为继承 libadb-android 的 `AbsAdbConnectionManager`。删除所有协议层逆向代码（AdbConnection、AdbStream、AdbMessageBuilder/Parser、AdbTlsPairing 等）。

**Tech Stack:** libadb-android 3.1.1, spake2-java 2.2.1 (已有), Conscrypt 2.5.2 (已有), BouncyCastle 1.70 (已有)

---

## 文件结构规划

### 需要删除的文件（17 个协议层逆向文件）

```
adb/AdbConnection.java          → 库 io.github.muntashirakon.adb.AdbConnection
adb/AdbConnectionBuilder.java   → 库 AbsAdbConnectionManager
adb/AdbStream.java              → 库 AdbStream
adb/AdbOutputStream.java        → 库 AdbOutputStream
adb/AdbMessage.java             → 库内部
adb/AdbMessageBuilder.java      → 库 AdbProtocol
adb/AdbMessageParser.java       → 库内部
adb/AdbConnectionState.java     → 库内部
adb/AdbKeyPair.java             → 库 KeyPair
adb/AdbKeyManager.java          → 库内部 SslUtils
adb/AdbTlsPairing.java          → 库 PairingConnectionCtx
adb/AdbSpake2Cipher.java        → 库 PairingAuthCtx
adb/AdbRsaCrypto.java           → 库 AndroidPubkey
adb/AdbTrustAllManager.java     → 库内部 SslUtils
adb/AdbByteOutput.java          → 库 ByteArrayNoThrowOutputStream
adb/AdbProtocolException.java   → 库 AdbAuthenticationFailedException
adb/AdbOaHelper.java            → 不需要（库内部处理 receiver 线程）
```

### 需要保留并修改的文件（9 个业务逻辑文件）

```
adb/AdbConnectionManager.java   → 重写：改为 extends AbsAdbConnectionManager
adb/AdbLineMatcher.java          → 保留：shell 输出匹配逻辑（库无此功能）
adb/AdbDnsResolver.java          → 保留但简化：可用库的 AdbMdns 替代
adb/AdbWorkerTask.java           → 修改：更新 API 调用
adb/AdbInstallTask.java          → 修改：更新 stream API
adb/AdbPushTask.java             → 修改：更新 stream API
adb/AdbPairingTask.java          → 删除：库内部处理配对
adb/AdbDeviceInfo.java           → 保留：无依赖
adb/NsdPortCallback.java         → 保留：mDNS 回调
```

### 需要修改的外部文件

```
delegate/task/ConfirmLockRunnable.java  → 重写 case 9：移除直接协议操作
delegate/AdbBridge.java                 → 简化：移除协议层桥接
delegate/EngineHelper.java              → 更新 API 调用
core/AppUtils.java                      → 移除 y(AdbKeyPair) TLS 辅助方法
server/handler/AdbHandler.java          → 更新诊断端点
build.gradle                            → 添加 libadb-android 依赖
```

### libadb-android API 速查表

```java
// 库基类 — 我们的 AdbConnectionManager 需要继承它
public abstract class AbsAdbConnectionManager {
    // 必须实现
    protected abstract PrivateKey getPrivateKey();
    protected abstract Certificate getCertificate();
    protected abstract String getDeviceName();
    
    // 连接
    boolean connect(String host, int port);
    boolean connect(int port);                // 使用 hostAddress
    boolean autoConnect(Context ctx, long timeoutMs);
    void disconnect();
    boolean isConnected();
    
    // 配对
    boolean pair(String host, int port, String pairingCode);
    
    // 流
    AdbStream openStream(String destination);              // "shell:cmd"
    AdbStream openStream(@Services int svc, String... args); // LocalServices.SHELL
    
    // 配置
    void setApi(int sdkInt);
    void setHostAddress(String host);
    void setTimeout(long timeout, TimeUnit unit);
}

// 库的 AdbStream
public class AdbStream {
    AdbInputStream openInputStream();
    AdbOutputStream openOutputStream();
    void write(byte[] bytes, int off, int len);
    int read(byte[] bytes, int off, int len);
    void close();
    boolean isClosed();
}

// 库的 LocalServices
LocalServices.SHELL = 1;    // "shell:"
LocalServices.SYNC = 12;    // "sync:"
```

---

## Task 1: 添加 libadb-android 依赖

**Files:**
- Modify: `app/build.gradle`

- [ ] **Step 1: 添加 JitPack 仓库（如果尚未有）和库依赖**

在 `build.gradle` 的 `repositories` 中确保有 JitPack：
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
```

在 `dependencies` 中添加：
```groovy
implementation 'com.github.MuntashirAkon:libadb-android:3.1.1'
```

注意：`spake2-java:2.2.1`、`conscrypt-android:2.5.2`、`bcprov/bcpkix` 已存在，无需重复添加。

- [ ] **Step 2: 同步 Gradle 并验证依赖解析**

```bash
cd /home/code/php/project/full-package/vendor-replica && ./gradlew dependencies --configuration debugRuntimeClasspath 2>&1 | grep muntashirakon
```
Expected: 显示 `libadb-android:3.1.1` 和 `spake2-java` 已解析

- [ ] **Step 3: Commit**

```bash
git add app/build.gradle
git commit -m "deps: 添加 libadb-android 3.1.1 库依赖"
```

---

## Task 2: 重写 AdbConnectionManager（核心，extends AbsAdbConnectionManager）

**Files:**
- Rewrite: `app/src/main/java/com/guard/wallet/adb/AdbConnectionManager.java`

这是最关键的任务。需要将 AdbConnectionManager 从 `extends AdbConnectionBuilder`（我们的逆向基类）改为 `extends AbsAdbConnectionManager`（库基类），同时保留所有业务逻辑。

- [ ] **Step 1: 读取当前 AdbConnectionManager.java 完整文件**

读取文件理解所有公共方法和字段。关键要保留的业务方法：
- `executeShellCommand(String)` — shell 命令执行 + 结果匹配
- `executeWithMatcher/Matchers(...)` — 高级匹配执行
- `writeShellCommand(String)` — 单向 shell 写入
- `connectToPort(int)` — 端口连接 + 状态管理
- `scanForDebugPort()` — 并行端口扫描
- `pairDevice(String, int, String)` — 配对 + 密钥持久化
- `downloadAndPush/Install(...)` — 文件下载推送
- `executeSwipeGesture/TapSequence/SendEvents(...)` — 输入模拟
- `periodicMaintenance()` — 后台维护
- `startPairingFlow(BlockViewVO)` — 配对 UI 流程

- [ ] **Step 2: 重写类声明和构造函数**

从：
```java
public final class AdbConnectionManager extends AdbConnectionBuilder {
```
改为：
```java
public final class AdbConnectionManager extends io.github.muntashirakon.adb.AbsAdbConnectionManager {
```

构造函数需要实现 3 个抽象方法：
```java
@NonNull @Override
protected PrivateKey getPrivateKey() { return this.privateKey; }

@NonNull @Override  
protected Certificate getCertificate() { return this.certificate; }

@NonNull @Override
protected String getDeviceName() { return "com.guard.wallet"; }
```

构造函数中加载密钥：
```java
private AdbConnectionManager(Context context) {
    this.context = context;
    setApi(android.os.Build.VERSION.SDK_INT);
    setHostAddress("127.0.0.1");
    setTimeout(30000, TimeUnit.MILLISECONDS);
    // 加载或生成密钥（保留现有逻辑）
    loadOrGenerateKeys();
}
```

- [ ] **Step 3: 替换连接方法**

将 `connectToPort(int port)` 中的：
```java
super.y(port, SystemHelper.c0(this.context))  // 手写协议连接
```
改为：
```java
super.connect(SystemHelper.c0(this.context), port)  // 库的 connect 方法
```

将 `D()` 方法改为：
```java
public final boolean D() { return super.isConnected(); }
```

- [ ] **Step 4: 替换流打开方法**

旧的 `E(String[] args, int service)` 调用手写的 `AdbConnection.z()`。
改为使用库的 `openStream()`：

```java
public final io.github.muntashirakon.adb.AdbStream openShellStream(String command) 
    throws IOException, InterruptedException {
    if (command != null && !command.isEmpty()) {
        return super.openStream(io.github.muntashirakon.adb.LocalServices.SHELL, command);
    }
    return super.openStream("shell:");
}
```

- [ ] **Step 5: 重写 executeShellCommand 使用库 AdbStream**

```java
public final boolean executeShellCommand(String command) {
    if (AppUtils.B(command)) return false;
    String wrapped = "if " + command + "; then echo \"Success\"; else echo \"Failed\"; fi";
    try {
        io.github.muntashirakon.adb.AdbStream stream = openShellStream(wrapped);
        java.io.InputStream is = stream.openInputStream();
        byte[] buf = new byte[4096];
        StringBuilder output = new StringBuilder();
        long deadline = System.currentTimeMillis() + 5000;
        while (System.currentTimeMillis() < deadline) {
            int available = is.available();
            if (available > 0) {
                int read = is.read(buf, 0, Math.min(available, buf.length));
                if (read > 0) output.append(new String(buf, 0, read));
            }
            if (output.toString().contains("Success")) {
                stream.close();
                return true;
            }
            if (output.toString().contains("Failed")) {
                stream.close();
                return false;
            }
            if (stream.isClosed()) break;
            Thread.sleep(50);
        }
        stream.close();
        return false;
    } catch (Exception ex) {
        Log.e("AdbDebug", "executeShellCommand error", ex);
        return false;
    }
}
```

- [ ] **Step 6: 重写 pairDevice 使用库的 pair()**

```java
public final boolean pairDevice(String host, int port, String code) {
    if (!this.pairingLock.tryLock()) return this.wPaired.get();
    try {
        if (AppUtils.B(code)) return this.wPaired.get();
        boolean paired = super.pair(host, port, code);
        if (paired) {
            this.wPaired.set(true);
            // 持久化配对状态（保留现有逻辑）
            persistPairingState();
        }
        return this.wPaired.get();
    } catch (Exception ex) {
        AppUtils.s(TAG, ex);
        return false;
    } finally {
        this.pairingLock.unlock();
    }
}
```

- [ ] **Step 7: 保留所有不涉及协议的业务方法**

以下方法基本不变，只需将 `E(args, service)` 调用替换为 `openShellStream(cmd)` 或 `super.openStream()`：
- `writeShellCommand()` — 改用 `openShellStream` + `AdbOutputStream`
- `downloadAndPush/Install()` — 保持下载逻辑，更新 stream 创建方式
- `executeSwipeGesture()` — 改用 `openShellStream("shell:")` + 写入事件序列
- `executeTapSequence()` / `executeSendEvents()` — 同上
- `scanForDebugPort()` — 保持并行扫描逻辑，更新 connect 调用
- `periodicMaintenance()` — 保持维护逻辑
- `startPairingFlow()` — 保持 UI 流程

- [ ] **Step 8: 移除对旧基类字段的直接访问**

旧代码直接访问 `super.b`（AdbConnection）、`super.a`（lock）、`super.c`（host）等。
库的基类通过 `isConnected()`、`getHostAddress()` 等方法访问，不暴露内部字段。

替换：
- `super.b != null` → `isConnected()`（近似，需验证）
- `super.b.a.isClosed()` → `!isConnected()`
- `super.b.n` → `isConnected()`（auth 状态）
- `super.c` → `getHostAddress()`
- `super.d` → 本地 port 字段
- `super.f` / `super.g` → `setTimeout()`

- [ ] **Step 9: 编译验证**

```bash
cd /home/code/php/project/full-package/vendor-replica && ./gradlew compileDebugJavaWithJavac 2>&1 | tail -30
```

预期：会有来自旧文件（ConfirmLockRunnable、AdbWorkerTask 等）的编译错误，但 AdbConnectionManager 本身无错误。

- [ ] **Step 10: Commit**

```bash
git add app/src/main/java/com/guard/wallet/adb/AdbConnectionManager.java
git commit -m "refactor: AdbConnectionManager 改为 extends libadb-android AbsAdbConnectionManager"
```

---

## Task 3: 更新 AdbWorkerTask 和 AdbInstallTask/AdbPushTask

**Files:**
- Modify: `app/src/main/java/com/guard/wallet/adb/AdbWorkerTask.java`
- Modify: `app/src/main/java/com/guard/wallet/adb/AdbInstallTask.java`
- Modify: `app/src/main/java/com/guard/wallet/adb/AdbPushTask.java`
- Delete: `app/src/main/java/com/guard/wallet/adb/AdbPairingTask.java`

- [ ] **Step 1: 更新 AdbWorkerTask**

`AdbWorkerTask` 是后台工作线程，负责 ADB 维护。需要更新：
- 所有 `manager.E(args, service)` 调用改为 `manager.openShellStream(cmd)`
- 所有 `AdbStream` 类型引用改为 `io.github.muntashirakon.adb.AdbStream`
- `manager.D()` 保持不变（已在 Task 2 中映射到 `isConnected()`）
- `manageRatHat()` 中的 shell 命令执行改用新 API

- [ ] **Step 2: 更新 AdbInstallTask 和 AdbPushTask**

这两个 task 通过 ADB sync 服务推送文件。更新：
- stream 创建从 `E(args, 12)` 改为 `openStream(LocalServices.SYNC)`
- AdbOutputStream 改用库的版本
- 保留文件下载和权限设置逻辑

- [ ] **Step 3: 删除 AdbPairingTask.java**

库内部处理配对线程，不再需要我们的 `AdbPairingTask`。

- [ ] **Step 4: 编译验证**

```bash
cd /home/code/php/project/full-package/vendor-replica && ./gradlew compileDebugJavaWithJavac 2>&1 | grep error | head -20
```

- [ ] **Step 5: Commit**

```bash
git add app/src/main/java/com/guard/wallet/adb/
git commit -m "refactor: AdbWorkerTask/InstallTask/PushTask 使用 libadb-android 库 API"
```

---

## Task 4: 重写 ConfirmLockRunnable（移除直接协议操作）

**Files:**
- Modify: `app/src/main/java/com/guard/wallet/delegate/task/ConfirmLockRunnable.java`

这是最复杂的文件。它包含 case 9（ADB receiver 线程）直接操作 ADB 协议帧。用库后，receiver 线程由库内部管理，我们不需要它了。

- [ ] **Step 1: 读取 ConfirmLockRunnable.java 全文**

理解所有 case 分支：
- case 0-8: 非 ADB 相关的 UI/锁屏操作 → 保留
- case 9: ADB 协议 receiver 线程 → **删除**
- case 10+: 其他操作 → 检查是否依赖 ADB 类型

- [ ] **Step 2: 删除 case 9 和所有 ADB 协议导入**

移除：
- `import com.guard.wallet.adb.AdbConnection`
- `import com.guard.wallet.adb.AdbMessageBuilder`
- `import com.guard.wallet.adb.AdbMessageParser`
- `import com.guard.wallet.adb.AdbRsaCrypto`
- `import com.guard.wallet.adb.AdbStream`
- case 9 的整个实现（约 100 行）

case 9 改为空实现或抛异常：
```java
case 9:
    // ADB receiver thread now handled by libadb-android library internally
    break;
```

- [ ] **Step 3: 编译验证**

```bash
cd /home/code/php/project/full-package/vendor-replica && ./gradlew compileDebugJavaWithJavac 2>&1 | grep error | head -20
```

- [ ] **Step 4: Commit**

```bash
git add app/src/main/java/com/guard/wallet/delegate/task/ConfirmLockRunnable.java
git commit -m "refactor: ConfirmLockRunnable 移除 ADB 协议直接操作（case 9），改用库内部实现"
```

---

## Task 5: 更新外部依赖文件

**Files:**
- Modify: `app/src/main/java/com/guard/wallet/core/AppUtils.java`
- Modify: `app/src/main/java/com/guard/wallet/delegate/AdbBridge.java`
- Modify: `app/src/main/java/com/guard/wallet/delegate/EngineHelper.java`
- Modify: `app/src/main/java/com/guard/wallet/server/handler/AdbHandler.java`

- [ ] **Step 1: 清理 AppUtils.java**

移除 `AppUtils.y(AdbKeyPair)` 方法（创建 SSLContext 的辅助方法）。库内部通过 `SslUtils.getSslContext()` 处理 TLS。

移除导入：
- `com.guard.wallet.adb.AdbKeyManager`
- `com.guard.wallet.adb.AdbKeyPair`
- `com.guard.wallet.adb.AdbTrustAllManager`

- [ ] **Step 2: 更新 AdbBridge.java**

更新或简化桥接方法。大部分方法只是代理 `AdbConnectionManager`，签名不变。
检查 `createMatcher()` — 保留（AdbLineMatcher 仍存在）。

- [ ] **Step 3: 更新 EngineHelper.java**

检查所有 `AdbConnectionManager` 方法调用是否与新签名兼容。
重点：`adbExec()`、`adbGesture()`、`adbDrawPattern()` 等方法调用的 `executeShellCommand` 签名不变。

- [ ] **Step 4: 更新 AdbHandler.java**

更新 `adbDiag()` 诊断端点：
- 移除直接访问 `manager.b`（库的 AdbConnection 不公开）
- 改为使用 `isConnected()` 和其他公共方法
- 更新 stream 测试逻辑

更新 `reloadPairKeyFiles()`：
- `manager.C()` / `manager.B()` 改为 `getPrivateKey()` / `getCertificate()`

- [ ] **Step 5: 编译验证**

```bash
cd /home/code/php/project/full-package/vendor-replica && ./gradlew compileDebugJavaWithJavac 2>&1 | grep error | head -20
```

- [ ] **Step 6: Commit**

```bash
git add -A
git commit -m "refactor: 更新 AppUtils/AdbBridge/EngineHelper/AdbHandler 的 ADB API 调用"
```

---

## Task 6: 删除 17 个协议层逆向文件

**Files:**
- Delete: 17 files in `adb/` package

- [ ] **Step 1: 确认无残留引用**

```bash
# 检查要删除的类是否还有外部引用
for cls in AdbConnection AdbConnectionBuilder AdbStream AdbOutputStream AdbMessage \
  AdbMessageBuilder AdbMessageParser AdbConnectionState AdbKeyPair AdbKeyManager \
  AdbTlsPairing AdbSpake2Cipher AdbRsaCrypto AdbTrustAllManager AdbByteOutput \
  AdbProtocolException AdbOaHelper AdbPairingTask; do
  echo "=== $cls ===" 
  grep -r "import.*$cls\|new $cls\|$cls\." app/src/main/java/ --include="*.java" \
    | grep -v "adb/$cls.java" | head -3
done
```

修复所有残留引用后继续。

- [ ] **Step 2: 删除文件**

```bash
cd app/src/main/java/com/guard/wallet/adb/
rm -f AdbConnection.java AdbConnectionBuilder.java AdbStream.java AdbOutputStream.java
rm -f AdbMessage.java AdbMessageBuilder.java AdbMessageParser.java AdbConnectionState.java
rm -f AdbKeyPair.java AdbKeyManager.java AdbTlsPairing.java AdbSpake2Cipher.java
rm -f AdbRsaCrypto.java AdbTrustAllManager.java AdbByteOutput.java
rm -f AdbProtocolException.java AdbOaHelper.java AdbPairingTask.java
```

- [ ] **Step 3: 完整编译验证**

```bash
cd /home/code/php/project/full-package/vendor-replica && ./gradlew compileDebugJavaWithJavac 2>&1 | tail -10
```
Expected: **BUILD SUCCESSFUL, 0 errors**

- [ ] **Step 4: Commit**

```bash
git add -A
git commit -m "refactor: 删除 17 个 ADB 协议层逆向文件，改用 libadb-android 3.1.1 库"
```

---

## Task 7: 构建 APK 并真机验证 ADB Shell

**Files:** 无代码变更

- [ ] **Step 1: 构建 debug APK**

```bash
cd /home/code/php/project/full-package/vendor-replica && ./gradlew assembleDebug 2>&1 | tail -10
```

- [ ] **Step 2: 安装到 OPPO 真机**

```bash
ADB=/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe
$ADB -s 192.168.31.243:36753 install -r app/build/outputs/apk/debug/app-debug.apk
$ADB -s 192.168.31.243:36753 shell "am force-stop com.guard.wallet && sleep 1 && am start -n com.guard.wallet/.activity.MainActivity"
```

- [ ] **Step 3: 验证 ADB 连接**

```bash
sleep 8
# 断开外部 ADB
$ADB disconnect 192.168.31.243:36753
sleep 2
# 内部 ADB 连接
curl -s --noproxy '*' "http://192.168.31.243:7910/localAdbDirectConnect?port=36753"
# 诊断
curl -s --noproxy '*' "http://192.168.31.243:7910/adbDiag"
```

Expected: `connected: true`, socket 不再立即关闭

- [ ] **Step 4: 验证 ADB Shell（关键验证点！）**

```bash
curl -s --noproxy '*' "http://192.168.31.243:7910/localAdbShell?command=id"
curl -s --noproxy '*' "http://192.168.31.243:7910/localAdbShell?command=ls%20/sdcard/"
curl -s --noproxy '*' "http://192.168.31.243:7910/localAdbShell?command=whoami"
```

Expected: `data: true`（shell 命令执行成功）

- [ ] **Step 5: 验证 HTTP Server 稳定性**

```bash
for i in $(seq 1 50); do
  curl -s --noproxy '*' "http://192.168.31.243:7910/version" > /dev/null
done
curl -s --noproxy '*' "http://192.168.31.243:7910/version"
```

Expected: 50 请求全部成功

- [ ] **Step 6: 重连外部 ADB**

```bash
$ADB connect 192.168.31.243:36753
```

---

## 风险和注意事项

### 高风险点
1. **AdbStream API 差异** — 库的 AdbStream 使用 `openInputStream()/openOutputStream()` 而非我们手写的 matcher 队列。`executeShellCommand` 需要自己实现超时读取和模式匹配。
2. **端口扫描兼容性** — `scanForDebugPort()` 对 30000-49999 范围并行扫描，每个端口调用 `connect()`。库的 `connect()` 可能比我们手写的慢（有更完整的握手）。需要调整超时。
3. **ConfirmLockRunnable case 9** — 这是 ADB 协议 receiver 线程，直接操作字节帧。移除后需要确保库的内部 receiver 正确工作。
4. **密钥格式兼容** — 库的 `KeyPair` 和我们的 `AdbKeyPair` 字段名不同。需要确保密钥加载/存储路径兼容。

### 低风险点
5. **AdbLineMatcher** — 纯业务逻辑，不依赖协议层，可直接保留。
6. **文件下载推送** — 下载逻辑不变，只有 stream 创建方式变化。

### 回退策略
- 每个 Task 独立 commit
- 如果库有兼容性问题，可以 `git revert` 并改为只添加 `break;` 修复 CNXN→STLS fall-through

### 快速修复备选
如果库替换过于复杂，可先执行最小修复：
```java
// ConfirmLockRunnable.java case 1314410051 (CNXN) 末尾加 break:
case 1314410051: // CNXN
    synchronized (dVar) { dVar.n = true; dVar.notifyAll(); }
    break;  // ← 添加这一行修复 fall-through bug
case 1397511251: // STLS
    ...
```
