# Vendor 反编译代码可编译化逆向工程 Implementation Plan (v4 — 审核修复版)

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 用 CFR 替换 JADX 输出消除编译阻塞，然后对核心模块执行深度逆向（重命名/拆分/重构），最终产出完全可编译、可读、可二次开发的 Java 项目。

**Architecture:** 三大阶段 — Phase A 编译化（CFR替换+Gradle+编译修复）→ Phase B 深度逆向（a1/q钥匙类→server/b路由拆分→o/引擎层厂商识别→utils工具类映射→外部包库识别）→ Phase C 验证收尾。

**Tech Stack:** CFR 0.152, dex2jar, Gradle 8.5, Java 17, Android SDK 34

---

## 审核修正记录 (v3 → v4)

| # | 问题 | 修复 |
|---|------|------|
| C1 | com/google/json（77文件）遗漏 | Task 2 增加复制; build.gradle 删除 gson Maven 依赖 |
| C2 | gradle 命令不可用 | Task 4 新增 gradlew 安装步骤, 全部改用 `./gradlew` |
| C3 | Task 7 路由分析错误 | 用 24 条真实路由重写功能域划分 |
| H1 | 12 个遗漏的被引用包 | Task 2 扩展为 38 个包（原 26 + 新增 12） |
| H2 | o/e.java 不是基类 | Task 8 删除继承说法, 改用 implements/Log 标签识别 |
| H3 | f0/ 库识别错误 | Task 10 修正为异步 IO 框架 |
| H4 | q.s()/q.B() 引用数低估 | 修正为全局实际数（1222/914） |
| M1 | 行数偏差 (b.java 8703, g.java 4247) | 更正所有行数 |
| M2 | goto 不在注释块中 | Task 5 明确为裸代码语法错误 |
| M3 | o/ 文件数 33→36 | 更正 |

---

## 前置条件

已完成:
- `/tmp/cfr.jar` — CFR 0.152 反编译器
- `/tmp/vendor-classes.jar` — dex2jar 转换的 JAR (8.1MB, 7165 class)
- `/tmp/cfr-full/` — CFR 全量反编译输出 (4715 Java 文件)
- `androidReverseEngineering/src/` — 已有 328 文件含 759 条注释
- `/home/code/php/project/full-package/android/gradlew` — 可借用的 Gradle Wrapper

**关键数据（已审核校正）:**
- CFR vs JADX: goto 536→9, Method dump 53→0, smali 580→0
- a1/q.java: **63** 方法, 被引用 **1,222+914** 次（含依赖包）
- server/b.java: 238 方法, **24** 条 HTTP 路由, **8,703** 行
- o/ 引擎层: **36** 文件, 6 厂商已识别, **无继承关系（全部 final class）**
- utils/g.java: 129 方法, **4,247** 行
- 外部混淆包: **38** 个包需复制（原 26 + 12 被引用遗漏包）
- com/google/json: **77** 文件（Gson 混淆版，不用 Maven 依赖）

---

## Phase A: 编译化（Task 1-5）

### Task 1: 备份已有逆向注释

**Files:**
- Create: `androidReverseEngineering/annotations-backup/`

- [ ] **Step 1: 提取注释到备份**

```bash
mkdir -p androidReverseEngineering/annotations-backup
for f in \
    "a1/q.java" \
    "com/guard/wallet/server/b.java" \
    "com/guard/wallet/service/MyAccessibilityService.java" \
    "com/guard/wallet/utils/d.java" \
    "com/guard/wallet/receiver/PowerBroadcastReceiver.java" \
    "com/guard/wallet/condition/TargetActionCondition.java" \
    "o/a0.java" "o/c.java" "o/e.java" "o/k.java" \
    "com/guard/wallet/thread/e.java" \
    "com/guard/wallet/thread/i.java"; do
    src="androidReverseEngineering/src/$f"
    if [ -f "$src" ]; then
        dst="androidReverseEngineering/annotations-backup/$f"
        mkdir -p "$(dirname "$dst")"
        cp "$src" "$dst"
        grep -n '@reverse\|@field\|@route\|GOTO_FIX\|RECONSTRUCTED\|@reverse-status\|@reverse-summary' "$src" > "$dst.annotations" 2>/dev/null
    fi
done
```

- [ ] **Step 2: 验证**

```bash
find androidReverseEngineering/annotations-backup -name '*.java' | wc -l
# 预期: ~12
```

---

### Task 2: CFR 输出替换 src/ + 补全全部依赖

**Files:**
- Replace: `androidReverseEngineering/src/` 全部内容

需复制的完整包清单:
- 核心 scope: `com/guard/wallet/`, `o/`, `a1/`
- Gson 混淆版: `com/google/json/` (77 文件)
- 原 26 个外部包: `a a0 b0 b1 c0 d0 e0 e1 f f0 g h j k l l0 m p0 q0 r s t w x y z`
- 新增 12 个被引用包: `e g0 i0 j0 k0 n n1 o0 p s0 u v`
- **排除**: `android/`, `androidx/`, `org/`（由 Android SDK 和 Maven 依赖提供）

- [ ] **Step 1: 清空并复制**

```bash
rm -rf androidReverseEngineering/src/*
CFR="/tmp/cfr-full"
DEST="androidReverseEngineering/src"

# 核心 scope
mkdir -p "$DEST/com/guard"
cp -r "$CFR/com/guard/wallet" "$DEST/com/guard/"

# Gson 混淆版
cp -r "$CFR/com/google" "$DEST/com/"

# o/ 和 a1/
cp -r "$CFR/o" "$DEST/"
cp -r "$CFR/a1" "$DEST/"

# 38 个外部混淆依赖包
for pkg in a a0 b0 b1 c0 d0 e e0 e1 f f0 g g0 h i0 j j0 k k0 l l0 m n n1 o0 p p0 q0 r s s0 t u v w x y z; do
    [ -d "$CFR/$pkg" ] && cp -r "$CFR/$pkg" "$DEST/"
done
```

- [ ] **Step 2: 验证文件数**

```bash
find androidReverseEngineering/src -name '*.java' | wc -l
# 预期: ~660 (345 wallet + 36 o + 23 a1 + 77 gson + ~180 依赖包)
```

- [ ] **Step 3: 验证 CFR 输出质量**

```bash
echo "goto:"
grep -r 'goto ' androidReverseEngineering/src/ --include='*.java' 2>/dev/null | wc -l
echo "Method dump:"
grep -r 'UnsupportedOperationException.*Method' androidReverseEngineering/src/ --include='*.java' 2>/dev/null | wc -l
```

预期: goto ≤ 9, Method dump = 0。

- [ ] **Step 4: 检查二级依赖是否完整**

```bash
# 新增包自身引用了什么?
grep -rh '^import ' androidReverseEngineering/src/ --include='*.java' | sort -u | \
    awk '{split($2,a,"."); pkg=a[1]; print pkg}' | sort -u | while read pkg; do
    [ -d "androidReverseEngineering/src/$pkg" ] || \
    echo "$pkg" | grep -qE '^(java|javax|android|androidx|org)$' || \
    echo "MISSING_2ND: $pkg/"
done
```

如有缺失，从 `/tmp/cfr-full/` 继续补充。

---

### Task 3: 合并已有逆向注释回 CFR 输出

**Files:**
- Modify: 12 个核心文件

- [ ] **Step 1: 合并 a1/q.java（48 方法注释）**

按方法签名匹配，从 `annotations-backup/a1/q.java` 将 `@reverse:` 注释插入 CFR 版对应方法上方。

- [ ] **Step 2: 合并 server/b.java（226 条路由注释）**

按方法名匹配插入 `@route:`。

- [ ] **Step 3: 合并其余 10 个文件**

逐文件按方法签名匹配。

- [ ] **Step 4: 验证**

```bash
grep -r '@reverse:\|@field:\|@route:' androidReverseEngineering/src/ --include='*.java' | wc -l
# 预期: ≈560
```

---

### Task 4: 搭建 Gradle 构建环境

**Files:**
- Create: `androidReverseEngineering/build.gradle`
- Create: `androidReverseEngineering/settings.gradle`
- Copy: `gradlew`, `gradlew.bat`, `gradle/` (从 android/ 项目借用)

- [ ] **Step 1: 安装 Gradle Wrapper**

```bash
cd androidReverseEngineering
cp ../android/gradlew .
cp ../android/gradlew.bat .
cp -r ../android/gradle .
chmod +x gradlew
./gradlew --version
```

- [ ] **Step 2: 创建 settings.gradle**

```groovy
rootProject.name = 'vendor-reverse-engineered'
```

- [ ] **Step 3: 创建 build.gradle**

```groovy
plugins {
    id 'java'
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

sourceSets {
    main {
        java {
            srcDirs = ['src']
        }
    }
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    // 注意: Gson 不用 Maven 依赖, 因为 vendor 用了混淆包名 com.google.json (非标准 com.google.gson)
    // com/google/json/ 已作为源码包含在 src/ 中

    compileOnly 'org.bouncycastle:bcprov-jdk15on:1.70'
    compileOnly 'org.bouncycastle:bcpkix-jdk15on:1.70'

    // Android SDK
    compileOnly files(androidJar())
}

def androidJar() {
    def sdkDir = System.env.ANDROID_HOME ?: System.env.ANDROID_SDK_ROOT ?: '/opt/android-sdk'
    for (ver in ['android-34', 'android-35', 'android-33']) {
        def jar = "$sdkDir/platforms/$ver/android.jar"
        if (new File(jar).exists()) return jar
    }
    return 'libs/android-stubs.jar'
}

tasks.withType(JavaCompile) {
    options.compilerArgs += ['-Xlint:none', '-proc:none']
    options.encoding = 'UTF-8'
}
```

- [ ] **Step 4: 首次编译建立基线**

```bash
cd androidReverseEngineering
./gradlew compileJava 2>&1 | tee /tmp/compile-baseline.txt
echo "错误数:"
grep -c 'error:' /tmp/compile-baseline.txt || echo 0
echo "错误类型:"
grep 'error:' /tmp/compile-baseline.txt | sed 's/.*error: //' | cut -d' ' -f1-4 | sort | uniq -c | sort -rn | head -20
```

---

### Task 5: 迭代修复编译错误至零

- [ ] **Step 1: 分析错误分布**

根据 Task 4 基线分类。预期常见错误:
- `cannot find symbol` → 补充缺失依赖或创建 stub
- `incompatible types` → 添加 cast
- `variable might not have been initialized` → 添加默认值
- `unreachable statement` → 删除死代码
- `android.sun.*` 类不在 android.jar 中 → 创建 stub 或注释掉

- [ ] **Step 2: 处理 9 个 CFR goto（裸代码语法错误，非注释）**

这 9 个 goto 是 CFR 的特殊标记（`** if (xxx) goto lbl-1000` + 裸 `lbl-1000:` 标签），出现在**方法体代码中**而非注释内，会直接导致编译失败。

位置:
- `com/guard/wallet/activity/MainActivity.java:342`
- `com/guard/wallet/thread/f.java:154,175`
- `com/guard/wallet/entity/UiObject.java:2264,2277`
- `com/guard/wallet/receiver/BatteryLevelReceiver.java:57`
- `o/j.java:245`
- `o/i.java:327`
- `o/g0.java:371`

修复方式: 逐个读取上下文，用 if-else 替代，或将整个方法标记为 `/* TODO: CFR decompile incomplete */` 并提供空方法体。

- [ ] **Step 3: 补充缺失依赖**

```bash
grep 'cannot find symbol' /tmp/compile-baseline.txt | grep -oP 'package \S+' | sort -u
```

从 `/tmp/cfr-full/` 补充。

- [ ] **Step 4: 修复类型和语法错误**

逐文件修复。

- [ ] **Step 5: 迭代直到零错误**

```bash
cd androidReverseEngineering && ./gradlew clean compileJava 2>&1 | tail -3
echo $?
# 目标: 0
```

---

## Phase B: 深度逆向（Task 6-10）

### Task 6: a1/q.java 钥匙类 — 63 方法重命名

**Why:** 被全库引用 2,136 次（logError=1,222, isNullOrEmpty=914），理解此文件后全库 70% 调用链变可读。

**Files:**
- Modify: `androidReverseEngineering/src/a1/q.java` + 全局引用文件

**重命名映射（按调用频率）:**

| 原名 | 功能 | 全局引用次数 | 重命名 |
|------|------|-------------|--------|
| `s()` | Log.e 异常日志 | 1,222 | `logError` |
| `B()` | null/empty 检查 | 914 | `isNullOrEmpty` |
| `m()` | AES/ECB 解密 | ~50 | `decryptAES` |
| `t()` | 异常堆栈日志 | ~40 | `logException` |
| `y()` | TLS SSLContext | ~30 | `createSSLContext` |
| `u()` | shell exec | ~20 | `execShellCommand` |
| `D()` | 时间格式化 | 18 | `formatTimestamp` |
| `K()` | 读文件内容 | ~15 | `readFileContent` |
| `E()` | 端口可用检查 | 14 | `isPortAvailable` |
| `H()` | SimpleDateFormat | ~10 | `formatTime` |
| `c()` | NIO 写出 | ~5 | `writeToChannel` |

- [ ] **Step 1: 确认全部 63 个方法签名**

```bash
grep -nP '^\s+(public|private|protected|static)\s+' androidReverseEngineering/src/a1/q.java
```

- [ ] **Step 2: 对每个方法读取方法体推断功能，添加 @reverse 注释**

- [ ] **Step 3: 执行全局重命名**

**安全策略:** 使用 `q.方法名(` 模式（含点号和括号）避免误替换:

```bash
# 高频方法先替换
sed -i 's/q\.s(/q.logError(/g' androidReverseEngineering/src/a1/q.java
find androidReverseEngineering/src -name '*.java' -exec sed -i 's/q\.s(/q.logError(/g' {} +

sed -i 's/q\.B(/q.isNullOrEmpty(/g' androidReverseEngineering/src/a1/q.java
find androidReverseEngineering/src -name '*.java' -exec sed -i 's/q\.B(/q.isNullOrEmpty(/g' {} +

# 同时重命名方法定义
sed -i 's/public static void s(/public static void logError(/' androidReverseEngineering/src/a1/q.java
sed -i 's/public static boolean B(/public static boolean isNullOrEmpty(/' androidReverseEngineering/src/a1/q.java
# ... 其余方法同理
```

- [ ] **Step 4: 编译验证**

```bash
cd androidReverseEngineering && ./gradlew compileJava 2>&1 | grep -c 'error:' || echo 0
# 必须 = 0
```

---

### Task 7: server/b.java 命令路由重建 — 拆分 8,703 行

**Why:** 238 个方法挤在一个 8,703 行的文件中。24 条真实路由提供功能域划分依据。

**Files:**
- Modify: `androidReverseEngineering/src/com/guard/wallet/server/b.java`
- Create: `androidReverseEngineering/src/com/guard/wallet/server/handler/` 下多个 Handler 类

**24 条真实路由（已审核确认）:**

| 功能域 | 路由 |
|--------|------|
| ADB/开发者 | `/openADBDebug`, `/closeADBDebug`, `/openWifiDebug`, `/closeWifiDebug`, `/resetWifiDebug`, `/openDevelopment`, `/closeDevelopment`, `/rewriteDebugPort`, `/shareADBConfig`, `/syncADBConfig`, `/api/pairKeyFile/query.json` |
| 设备状态 | `/`, `/version`, `/deviceId`, `/containerState`, `/isDeviceOwner`, `/noticeAlive` |
| 权限/管理 | `/syncAdminActivating`, `/syncPowerControl`, `/syncLockCipher` |
| 无障碍服务 | `/resetAccessibilityService`, `/listenHelper`, `/finishListenHelper` |
| UI | `/blockView` |

**路由分发机制:** 不是 switch/case，而是 `string.equals()` 链。

- [ ] **Step 1: 读取 server/b.java 定位路由分发入口**

```bash
grep -n '.equals("/' androidReverseEngineering/src/com/guard/wallet/server/b.java | head -30
```

找到路由分发方法，建立完整的 路由→被调用方法 映射。

- [ ] **Step 2: 在文件头部添加路由索引注释**

```java
/*
 * === 路由索引 ===
 * /                     → 根状态
 * /version              → 版本信息
 * /deviceId             → 设备 ID
 * /openADBDebug         → 开启 ADB 调试
 * /closeADBDebug        → 关闭 ADB 调试
 * ... (24 条全部列出)
 */
```

- [ ] **Step 3: 按功能域创建 Handler 类**

```bash
mkdir -p androidReverseEngineering/src/com/guard/wallet/server/handler
```

创建:
- `AdbCommandHandler.java` — 11 条 ADB/开发者路由
- `DeviceStatusHandler.java` — 6 条设备状态路由
- `PermissionHandler.java` — 3 条权限管理路由
- `AccessibilityHandler.java` — 3 条无障碍服务路由

每个 Handler 接收相关方法的 move。

- [ ] **Step 4: b.java 保留为路由分发入口**

```java
// b.java 路由分发简化为:
if (path.equals("/openADBDebug")) return AdbCommandHandler.openADBDebug(params);
if (path.equals("/deviceId")) return DeviceStatusHandler.deviceId(params);
// ...
```

- [ ] **Step 5: 为每个方法添加功能注释**

- [ ] **Step 6: 编译验证**

```bash
cd androidReverseEngineering && ./gradlew compileJava 2>&1 | grep -c 'error:' || echo 0
```

---

### Task 8: o/ 引擎层 — 按厂商重命名 + 逐个击破

**Why:** 36 个文件全是单字母名（a.java, b.java...），但厂商关键词已确认。

**关键事实:** o/ 下**所有类都是 final class，没有继承关系**。类之间通过 import 和方法调用关联，不通过 extends。

**Files:**
- Rename + Modify: `androidReverseEngineering/src/o/` 下 36 个文件

**已识别映射（11 个确认 + 25 个待分析）:**

| 原文件 | 厂商/功能 | 识别依据 | 重命名 |
|--------|----------|---------|--------|
| `o/a0.java` | ADB 无线配对 | Log "PairAccessibilityDelegate" | `PairDelegate.java` |
| `o/k.java` | 安全设置自动化 | USB调试+开发者选项 | `EnableSecureDelegate.java` |
| `o/n.java` | 华为启动管理 | `HUA_WEI_*` keys | `HuaweiStartupEngine.java` |
| `o/h.java` | 华为通用 | 华为关键词 | `HuaweiEngine.java` |
| `o/q.java` | 小米 | xiaomi/miui 关键词 | `XiaomiEngine.java` |
| `o/v.java` | OPPO | `COLORS_*` keys | `OppoEngine.java` |
| `o/i0.java` | Vivo | vivo/funtouch 关键词 | `VivoEngine.java` |
| `o/e0.java` | 传音 | transsion/tecno 关键词 | `TranssionEngine.java` |
| `o/g.java` | AOSP 通用 | 无厂商特定关键词 | `AospEngine.java` |
| `o/c.java` | 核心引擎 | 被大量引用(14文件) | `CoreEngine.java` |
| `o/e.java` | 引擎接口/协调 | 被 19 文件引用 | `EngineCoordinator.java` |

- [ ] **Step 1: 重命名已确认的 11 个文件**

对每个文件执行 4 步:
1. `mv o/e.java o/EngineCoordinator.java`
2. `sed -i 's/class e/class EngineCoordinator/' o/EngineCoordinator.java`
3. `find src/ -name '*.java' -exec sed -i 's/\bo\.e\b/o.EngineCoordinator/g' {} +`
4. `find src/ -name '*.java' -exec sed -i 's/import o\.e;/import o.EngineCoordinator;/g' {} +`

**击破顺序:**
1. `o/e.java`（被引用最多 19 文件）→ 先重命名让全局引用可读
2. `o/c.java`（被引用 14 文件）
3. `o/g.java`（AOSP 通用）→ 最简单引擎
4. `o/n.java` + `o/h.java`（华为）
5. `o/q.java`（小米）
6. 其余

- [ ] **Step 2: 分析剩余 25 个文件**

读取 CFR 输出，通过 Log 标签 + 字符串常量 + import 来源识别功能。

- [ ] **Step 3: 为每个引擎添加功能注释**

- [ ] **Step 4: 编译验证**

```bash
cd androidReverseEngineering && ./gradlew compileJava 2>&1 | grep -c 'error:' || echo 0
```

---

### Task 9: utils/ + helper/ 工具类功能映射

**Files:**
- Modify: `androidReverseEngineering/src/com/guard/wallet/utils/` 11 个文件
- Modify: `androidReverseEngineering/src/com/guard/wallet/helper/` 18 个文件

**utils/g.java 核心数据: 4,247 行, 129 方法。**

Log 标签功能分组:

| Log 标签 | 方法群 | 估算数 |
|----------|--------|--------|
| `AccountUtils` | 设备账号 | ~5 |
| `ApplicationUtil` | 安装/卸载/启动 | ~15 |
| `ReceiverUtils` | 广播接收器 | ~8 |
| `UnLockUtils` | 屏幕解锁/PIN | ~10 |
| `ContactUtils` | 联系人 | ~5 |
| `CertificateUtils` | SSL 证书 | ~5 |
| 其他 | 杂项 | ~81 |

- [ ] **Step 1: utils/g.java 按 Log 标签分组注释**

```bash
grep -n 'Log\.' androidReverseEngineering/src/com/guard/wallet/utils/g.java | head -30
```

用注释分段: `// ========== AccountUtils 方法组 ==========`

- [ ] **Step 2: utils/ 其余 10 个文件注释**

每文件: 读取 → 推断功能 → 加 @reverse 注释。

- [ ] **Step 3: helper/ 18 个文件重命名**

逐文件识别功能并重命名（如 `helper/a.java` → `helper/RectHelper.java`）。

- [ ] **Step 4: 编译验证**

---

### Task 10: 外部混淆包 — 识别原始开源库

**Files:**
- Modify: 38 个外部包

**已识别映射（已审核修正）:**

| 混淆包 | 原始库 | 依据 | 文件数 |
|--------|--------|------|--------|
| `p0/` | OkHttp 4.x 核心 | "OkHttp Dispatcher" 字符串 | 40 |
| `f0/` | **异步 IO 框架**（AsyncServer 类） | `AsyncServer`/`Selector closed` 字符串 | 28 |
| `b1/` | Conscrypt SSL/TLS | `import org.conscrypt.Conscrypt` | 18 |
| `l0/` | OkIO 缓冲 IO | OkHttp 依赖 | 17 |
| `h/` | WebSocket 客户端 | WebSocket 协议 | 5 |
| `k/` | 证书验证器 | X509/TrustManager | 1 |
| `com/google/json/` | **Gson（混淆包名版）** | 完整 Gson API 结构 | 77 |
| 其余 | 小型工具/胶水代码 | — | ~100 |

- [ ] **Step 1: 为每个包创建 package-info.java**

```java
/**
 * @reverse-library: OkHttp 4.x
 * @original-package: okhttp3
 * @evidence: "OkHttp Dispatcher" in d0.java
 */
package p0;
```

- [ ] **Step 2: 创建映射文档 `docs/OBFUSCATION_MAP.md`**

- [ ] **Step 3: 编译验证**

---

## Phase C: 验证收尾（Task 11）

### Task 11: 最终验证 + 文档

- [ ] **Step 1: 完整编译**

```bash
cd androidReverseEngineering && ./gradlew clean compileJava 2>&1
echo $?
```

- [ ] **Step 2: 质量统计**

```bash
echo "文件数:" && find src/ -name '*.java' | wc -l
echo "代码行:" && find src/ -name '*.java' -exec cat {} + | wc -l
echo "注释:" && grep -r '@reverse:\|@field:\|@route:\|@reverse-library' src/ --include='*.java' | wc -l
echo "goto:" && grep -r 'goto ' src/ --include='*.java' | grep -v '/\*' | grep -v '//' | wc -l
```

- [ ] **Step 3: 更新 README.md**

- [ ] **Step 4: 清理临时文件**

```bash
rm -rf androidReverseEngineering/annotations-backup
rm -rf androidReverseEngineering/analysis_reports
rm -rf DIAGNOSTIC_REPORTS
```

- [ ] **Step 5: 等待用户确认后提交**

---

## 验证标准

| 阶段 | 命令 | 预期 |
|------|------|------|
| Task 2 | `find src/ -name '*.java' \| wc -l` | ~660 |
| Task 2 | `grep -r 'goto ' src/ \| wc -l` | ≤ 9 |
| Task 5 | `./gradlew compileJava && echo $?` | 0 |
| Task 6 | `grep 'q\.s(' src/ -r \| wc -l` | 0 |
| Task 7 | `wc -l src/.../server/b.java` | < 3000 |
| Task 8 | `ls src/o/ \| grep -cP '^[a-z]\.java$'` | 0 |
| Task 10 | `find src/ -name 'package-info.java' \| wc -l` | ≥ 38 |
| Task 11 | `./gradlew clean compileJava && echo $?` | 0 |
