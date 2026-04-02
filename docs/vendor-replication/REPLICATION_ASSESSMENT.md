# Vendor 反编译代码逆向评估与实施方案

> **日期**: 2026-04-02
> **目标**: 将 vendor 反编译代码转化为可读、可二次开发的 Java 代码

---

## 一、现状分析

### 1.1 代码规模

| 范围 | 文件 | 行数 |
|------|------|------|
| `wallet/` 业务代码 | 294 | 46,248 |
| `o/` 引擎层 | 33 | 11,410 |
| 外部混淆依赖 (a1/, p0/ 等) | ~350 | ~25,000 |
| **需逆向总量** | **~677** | **~82,658** |

### 1.2 混淆分布

**完全可读（0% 混淆）— 211 文件, 16,700 行 (29%)**

| 包 | 文件 | 行数 | 内容 |
|----|------|------|------|
| req/ | 55 | 3,696 | 请求数据模型，字段名/方法名完整 |
| resp/ | 42 | 4,520 | 响应数据模型 |
| entity/ | 24 | 9,200 | 实体类（含 UiObject 3801 行） |
| filter/ | 39 | 1,210 | UI 过滤器框架 |
| condition/ | 8 | 1,103 | 条件匹配 |
| receiver/ | 12 | 970 | 广播接收器 |
| msg/ | 9 | 320 | 消息协议 |
| activity/ | 4 | 726 | Activity |
| service/ | 7 | 2,663 | 服务（类名可读，内部调用混淆） |
| stat/ | 3 | 297 | 统计 VO |
| sync/ | 2 | 66 | 同步服务 |

**完全混淆（100%）— 116 文件, 40,958 行 (71%)**

| 包 | 文件 | 行数 | 混淆特征 |
|----|------|------|---------|
| server/ | 3 | 11,381 | 类名 a/b/c，方法名 A~Z/A0~Z3 |
| utils/ | 11 | 4,723 | 类名 a~k，方法名单字母 |
| helper/ | 18 | 1,741 | 类名 a~r |
| http/ | 34 | 2,608 | 类名 a~z/a0~e0，回调链 |
| thread/ | 13 | 1,912 | 类名 a~m |
| plug/ | 6 | 500 | 类名 a~f |
| bridge/ | 1 | 115 | 类名 a |
| o/ (引擎) | 33 | 11,410 | 全部单字母/双字母 |

### 1.3 反编译质量问题

| 问题 | 数量 | 说明 |
|------|------|------|
| `goto` 语句 | 356 | Java 不支持，需重构为 if/while/break |
| JADX WARN | 1,365 | 类型推断失败、代码重构失败等 |
| 重灾区 | `o/a0.java` 126 goto, `server/b.java` 84 goto, `o/c.java` 79 goto | 控制流严重损坏 |

### 1.4 依赖关系图

核心混淆符号被引用频率：

```
a1.q  → 被 301 处引用（核心工具类，1134 行，含 WebSocket/加密/日志/Base64）
p0.*  → 被 69 处 import（HTTP 服务器框架，40 文件 3423 行）
q.s() → 出现 733 次（日志方法）
q.B() → 出现 511 次（字符串判空）
h.d() → 出现 63 次（JSON 反序列化）
h.N() → JSON 序列化
h.l() → SharedPreferences 读取
```

---

## 二、逆向难度分级

### Tier 1: 直接可用 — 无需逆向

**16,700 行 (29%)**

req/、resp/、entity/、filter/、condition/、receiver/、msg/、stat/、sync/

这些代码类名、方法名、字段名完整，可直接阅读和二次开发。

### Tier 2: 可读框架 + 混淆内部调用 — 需局部逆向

**2,663 行 (5%)**

service/MyAccessibilityService.java (1402 行)、AccessibilityDelegateManager.java (800 行) 等。类名方法名可读，但内部大量调用 `q.s()`、`h.l()` 等混淆方法。

**逆向策略**: 只需逆向被调用的混淆工具方法（约 20 个高频方法），不需要逆向整个工具类。

### Tier 3: 有结构线索 — 需系统逆向

**11,381 行 (20%)**

`server/b.java` 虽然方法名全混淆（A~Z3），但有极好的结构线索：
- 130+ 个 API 路由字符串（`"/contacts"`、`"/lockScreen"` 等）
- 路由 → hashCode switch → case 编号 → 方法调用，映射关系明确
- 每个方法的参数类型是可读的 VO 类（`ReqStartApp`、`ReqSendSMSVO` 等）
- 中文错误提示（`"你提交的参数有误,委托ID、关键字不能为空"`）

**逆向策略**: 通过路由字符串 + 参数 VO 类型 + 中文提示，可以直接推断每个方法的功能并重命名。

### Tier 4: 需深度分析 — 最困难

**26,514 行 (46%)**

| 文件 | 行数 | 难点 |
|------|------|------|
| `utils/g.java` | 3,142 | 万能工具类，100+ 方法功能杂糅 |
| `o/a0.java` | 2,003 | 126 个 goto，控制流严重损坏 |
| `o/e.java` + `o/c.java` | 1,783 | 引擎核心，goto 密集 |
| `a1/q.java` | 1,134 | 被引用 301 次的基础设施类 |
| `utils/h.java` | 761 | SharedPreferences 封装 |
| `http/` 34 文件 | 2,608 | 回调链，混淆后难追踪 |
| `helper/` 18 文件 | 1,741 | UI 覆盖层/对话框 |
| `thread/` 13 文件 | 1,912 | 定时任务/后台线程 |
| `o/` 其余 | 6,494 | 厂商引擎 |
| `p0/` 40 文件 | 3,423 | HTTP 服务器框架 |

---

## 三、实施方案

### Phase 0: 准备工作（1 天）

**0.1 识别混淆方案**
```bash
pip install apkid
apkid vendor.apk
```
确认是 ProGuard/R8 还是 DexGuard。决定后续工具选择。

**0.2 CFR 重新反编译 goto 重灾区**
```bash
# dex → jar
d2j-dex2jar vendor.apk -o vendor.jar

# CFR 反编译（不产生 goto）
java -jar cfr-0.152.jar vendor.jar \
  --outputdir cfr_output/ \
  --caseinsensitivefs true
```
对比 JADX 和 CFR 的输出，选择每个文件质量更好的版本。重点对比：
- `o/a0.java`（126 goto）
- `server/b.java`（84 goto）
- `o/c.java`（79 goto）

**0.3 Bytecode Viewer 交叉验证**

用 Bytecode Viewer 同时加载 JADX + CFR + Procyon 输出，对关键方法做三方对比。

### Phase 1: 解锁基础设施（2-3 天）

逆向被引用最多的工具类，解锁后续所有代码的理解。

**1.1 `a1/q.java`（1134 行，被引用 301 次）**

已知功能线索：
| 方法 | 线索 | 推断功能 |
|------|------|---------|
| `q.s(String, Exception)` | 出现 733 次，参数是 TAG + Exception | **日志方法** `Log.e(tag, e)` |
| `q.B(String)` | 出现 511 次，返回 boolean | **字符串判空** `TextUtils.isEmpty()` |
| `q.H()` | 返回 String | 获取设备 ID |
| `q.K(String)` | 参数是字符串 | MD5/加密 |
| `q.u(String[], boolean, boolean)` | 返回 CommandResult | Shell 命令执行 |
| `q.y(b1.k)` | 返回 SSLContext | SSL 上下文创建 |
| `q.L(p)` | 参数是 p（HTTP 服务器） | 启动 HTTP 服务器 |

**方法**: 结合参数类型 + 返回类型 + 调用上下文，逐个重命名。完成后全局替换。

**1.2 `h` 类（JSON/SharedPreferences 工具）**

| 方法 | 线索 | 推断 |
|------|------|------|
| `h.d(String, Class)` | 出现 63 次，第二个参数是 VO.class | **Gson.fromJson()** |
| `h.N(Object)` | 返回 String，参数是 ApiResult | **Gson.toJson()** |
| `h.l(String)` | 参数是 key 字符串 | **SharedPreferences.getString()** |

### Phase 2: 命令路由重建（3-5 天）

**2.1 `server/b.java` 路由映射表**

反编译代码已经暴露了完整的路由分发结构：

```java
switch (str.hashCode()) {
    case -2029212786:
        if (str.equals("/startApp")) { c2 = 0; break; }
    ...
}
switch (c2) {
    case 0:
        ReqStartApp req = h.d(str2, ReqStartApp.class);
        N2(req.getStartPackage(), req.getMainActivity(), ...);
        return;
    ...
}
```

每个路由的信息已经完整：
- 路由路径（`"/startApp"`）
- 参数 VO 类型（`ReqStartApp.class`，完全可读）
- 调用的处理方法（`N2()`，需重命名）

**方法**: 编写脚本自动提取 130+ 个路由映射，生成重命名表：

```
/startApp        → case 0  → N2()  → 重命名为 handleStartApp()
/sendSms         → case 11 → E2()  → 重命名为 handleSendSms()
/unlock          → case 21 → A3()  → 重命名为 handleUnlock()
/contacts        → case ?? → ??()  → 重命名为 handleSyncContacts()
/lockScreen      → case ?? → ??()  → 重命名为 handleLockScreen()
```

**2.2 按功能分组拆分**

将 11,172 行的 `server/b.java` 按路由前缀拆分为独立文件：

| 路由前缀 | 方法数 | 拆分为 |
|---------|--------|--------|
| `/target/*` | ~60 | `TargetActionHandler.java` |
| `/global/*` | ~8 | `GlobalActionHandler.java` |
| `/sync*` | ~12 | `SyncHandler.java` |
| `/start*` | ~10 | `StartHandler.java` |
| `/localAdb*` | ~5 | `AdbHandler.java` |
| 其余 | ~40 | `DeviceCommandHandler.java` |

### Phase 3: 引擎层逆向（5-7 天）

**3.1 `o/` 包 — 按厂商逐个击破**

每个引擎文件对应一个厂商，通过 Android API 调用和字符串常量可推断：

| 文件 | 行数 | 线索 | 推断 |
|------|------|------|------|
| `o/n.java` | 454 | `"huawei"`, `"honor"` | 华为引擎 |
| `o/q.java` | 498 | `"xiaomi"`, `"miui"` | 小米引擎 |
| `o/v.java` | 526 | `"oppo"`, `"realme"` | OPPO 引擎 |
| `o/u.java` | 169 | `"vivo"` | vivo 引擎 |
| `o/s.java` | 107 | `"samsung"` | 三星引擎 |
| `o/a0.java` | 2003 | `PackageInstaller` | 安装代理 |
| `o/t.java` | 677 | `"development_settings"` | 开发者选项 |
| `o/x.java` | 531 | `"accessibility"` | 无障碍引擎 |
| `o/i0.java` | 684 | `"lockscreen"`, `"keyguard"` | 屏幕解锁 |
| `o/e.java` | 982 | 被多个引擎继承 | 引擎基类 |
| `o/c.java` | 801 | 被 e.java 引用 | 引擎接口 |

**方法**: 按需逆向。只逆向目标厂商（如 OPPO），其余标记为 stub。

**3.2 goto 重灾区处理**

对 `o/a0.java`（126 goto）和 `o/c.java`（79 goto）：
1. 先用 CFR 重新反编译，对比 JADX 输出
2. 如果 CFR 输出仍不可读，用 `simplify` 工具简化控制流
3. 最后手段：参考行为重写（通过 Frida 动态观察输入输出）

### Phase 4: 工具类逆向（3-4 天）

**4.1 `utils/g.java`（3142 行）**

不需要一次性全部逆向。按调用频率排序，逐个方法分析：

```
高频（被调用 50+ 次）: 优先逆向，约 15 个方法
中频（被调用 10-50 次）: 按需逆向，约 30 个方法
低频（被调用 <10 次）: 暂不逆向，标记 TODO
```

**4.2 `utils/h.java`（761 行）— SharedPreferences**

通过 key 字符串常量可推断每个方法的功能。

### Phase 5: 辅助模块（2-3 天）

| 模块 | 文件 | 策略 |
|------|------|------|
| `http/` 34 文件 | 回调类 | 通过 API 路由 + 响应 VO 类型反推 |
| `helper/` 18 文件 | UI 覆盖层 | 通过 Android View API 调用推断 |
| `thread/` 13 文件 | 后台任务 | 通过 TimerTask/Callable 接口推断 |
| `plug/` 6 文件 | 插件 | 通过 Predicate/Serializable 接口推断 |

### Phase 6: 外部依赖识别（1 天）

| 包 | 文件 | 推断 |
|----|------|------|
| `a1/` | 23 文件, 4598 行 | 核心工具库（WebSocket + 加密 + 日志） |
| `p0/` | 40 文件, 3423 行 | NanoHTTPD 或类似的嵌入式 HTTP 服务器 |
| `f0/` | 28 文件, 1918 行 | OkHttp 或网络库 |
| `b1/` | 17 文件, 1456 行 | SSL/TLS 相关 |

**方法**: 对比已知开源库的类结构和方法签名，识别原始库。识别后直接替换为 Maven 依赖，不需要逆向。

---

## 四、工具链

| 阶段 | 工具 | 用途 |
|------|------|------|
| 准备 | **APKiD** | 识别混淆方案 |
| 准备 | **CFR** | 重新反编译 goto 重灾区 |
| 准备 | **Bytecode Viewer** | 多反编译器对比 |
| Phase 1-5 | **jadx-gui** | 交互式重命名，保存为项目 |
| Phase 2 | **自定义脚本** | 自动提取路由映射表 |
| Phase 3 | **simplify** | 简化控制流混淆 |
| Phase 3-4 | **Frida** | 动态 hook 观察方法行为 |
| Phase 6 | **java-deobfuscator** | 自动检测混淆器 + transformer |

---

## 五、工作量与产出

| Phase | 内容 | 工时 | 产出 |
|-------|------|------|------|
| 0 | 准备 + CFR 重编译 | 1 天 | goto 消除版代码 |
| 1 | 基础设施类逆向 | 2-3 天 | a1/q + h 类重命名 |
| 2 | 命令路由重建 | 3-5 天 | server/b.java 拆分 + 重命名 |
| 3 | 引擎层逆向 | 5-7 天 | o/ 包重命名 + 注释 |
| 4 | 工具类逆向 | 3-4 天 | utils/ 高频方法重命名 |
| 5 | 辅助模块 | 2-3 天 | http/helper/thread 重命名 |
| 6 | 外部依赖 | 1 天 | 识别原始库 |
| **合计** | | **17-24 天** | **可读可二次开发的 Java 代码** |

### 加速方案

使用 Frida 动态分析可将 Phase 3-4 压缩 40%：

| 方案 | 工时 | 说明 |
|------|------|------|
| 纯静态逆向 | 17-24 天 | 只用 JADX + CFR |
| 静态 + Frida 动态 | 12-17 天 | 混淆方法通过运行时 hook 观察行为 |
| 静态 + Frida + AI 辅助 | 10-14 天 | 用 LLM 批量分析反编译代码推断功能 |

---

## 六、风险与缓解

| 风险 | 概率 | 缓解 |
|------|------|------|
| CFR 对部分文件输出也不可读 | 中 | 用 simplify 预处理，或参考行为重写 |
| 外部依赖包无法识别原始库 | 低 | 保持混淆代码，只逆向被业务调用的方法 |
| Frida 被 vendor APK 检测 | 低 | 用 fridare 工具修改 Frida 特征 |
| 逆向后代码无法编译 | 中 | 逐步重命名 + 持续编译验证 |
