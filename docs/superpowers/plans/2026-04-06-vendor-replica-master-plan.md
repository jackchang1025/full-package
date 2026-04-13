# Vendor-Replica 全面修复总体计划 (Master Plan)

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 将 vendor-replica 项目从当前 22,049 行 (16.9% 覆盖率) 提升到 ~85,000 行 (65%+ 覆盖率)，实现所有核心功能模块的一比一逐方法翻译。

**Architecture:** vendor CFR 反编译输出 (130,439 行) 作为唯一源码参考。按依赖拓扑排序分 10 个子计划顺序执行。每个子计划独立可编译、可验证。混淆包保留原始包名以避免引用混乱。

**Tech Stack:** Android API 21-34, Java 8+, Gradle 8.5 + AGP 8.2.2, OkHttp 4.12.0, Gson 2.10.1, Java-WebSocket 1.5.4

**CFR 源码路径:** `/home/code/php/project/full-package/androidReverseEngineering/src/`  
**JADX 源码路径:** `/home/code/php/project/full-package/app/storage/app/apk/apkstub/decompiled_vendor/sources/`  
**Replica 路径:** `/home/code/php/project/full-package/vendor-replica/app/src/main/java/`

---

## 审计快照 (2026-04-06)

| 包 | Vendor 行 | Replica 行 | 覆盖率 | 缺口 |
|-----|----------|-----------|--------|------|
| server/ | 28,395 | 3,738 | 13.2% | -24,657 |
| o/ (引擎/委托) | 24,234 | 308 | 1.3% | -23,926 |
| entity/ | 11,534 | 3,468 | 30.1% | -8,066 |
| utils/ | 10,740 | 2,979 | 27.7% | -7,761 |
| service/ | 6,569 | 183 | 2.8% | -6,386 |
| thread/ | 6,267 | 101 | 1.6% | -6,166 |
| a1/ (工具) | 6,242 | 113 | 1.8% | -6,129 (注: 仅 q.java 1,962行是业务代码, 其余 20 文件是 Okio 库→Gradle 依赖) |
| resp/ | 5,336 | 2,020 | 37.9% | -3,316 |
| req/ | 4,563 | 3,471 | 76.1% | -1,092 |
| k/ (Selector) | 4,035 | 5 | 0.1% | -4,030 |
| http/ | 3,841 | 521 | 13.6% | -3,320 |
| helper/ | 3,340 | 641 | 19.2% | -2,699 |
| h/ (ADB) | 2,562 | 25 | 1.0% | -2,537 |
| filter/ | 2,133 | 1,604 | 75.2% | -529 |
| receiver/ | 1,910 | 109 | 5.7% | -1,801 |
| l0/ (HTTP框架) | 1,343 | 153 | 11.4% | -1,190 |
| o0/ | 1,471 | 4 | 0.3% | -1,467 |
| condition/ | 1,108 | 602 | 54.3% | -506 |
| activity/ | 1,040 | 38 | 3.7% | -1,002 |
| e/ (权限) | 1,003 | 0 | 0% | -1,003 |
| m/ | 859 | 113 | 13.2% | -746 |
| plug/ | 696 | 61 | 8.8% | -635 |
| 其余小包 | ~2,700 | ~400 | ~15% | -2,300 |
| **总计** | **~130,439** | **22,049** | **16.9%** | **~108,390** |

---

## 子计划拆分 (依赖拓扑序)

按照模块依赖关系从底层到上层排列。每个子计划独立可编译验证。

```
Plan 1: 基础工具层 (a1/ + utils/ + 混淆小包)
   ↓
Plan 2: 数据模型层 (req/ + resp/ 补全)
   ↓
Plan 3: HTTP 框架层 (l0/ + i0/ + http/)
   ↓
Plan 4: UI 自动化核心 (k/ Selector + entity/ + filter/ + z/)
   ↓
Plan 5: 无障碍服务层 (service/ + e/ 权限 + o/e 委托基类)
   ↓
Plan 6: 厂商引擎层 (o/ 36 文件)
   ↓  
Plan 7: 后台线程层 (thread/ + h/ ADB)
   ↓
Plan 8: 广播/数据采集 (receiver/ + helper/ + plug/)
   ↓
Plan 9: HTTP Server Handler 深化 (server/ 剩余 7 handler)
   ↓
Plan 10: 界面+启动流程 (activity/ + MainApplication + 集成测试)
```

---

## Plan 1: 基础工具层

**预估:** ~4,500 行新增  
**文件:** a1/q.java (补全), utils/ (21→35), i0/ (8)

### 当前状态
- `a1/q.java` 已有 113 行 (vendor 1,962 行) — 缺 Shell 命令 (`u()`), Base64, 文件操作, Bitmap 处理, WakeLock
- `a1/` 其余 22 文件是 **Okio 库** → 通过 Gradle `okio:1.17.6` 依赖引入，**不需翻译**
- `utils/g.java` 门面 270 行 (vendor 6,524) — 已连通但背后专职类不完整
- `utils/h.java` 310 行 (vendor 2,170) — 缺 ~60% 方法
- `utils/e.java` ~100 行 (vendor 996) — 缺设备信息采集
- `utils/d.java` ~100 行 (vendor 460) — 缺配置管理
- `i0/` 2 文件 56 行 (vendor 8 文件 242 行) — 缺 6 文件

### 关键任务
- [ ] Task 1.1: `a1/q.java` 补全 (Shell 命令 `u()`, Base64 `z()`, WakeLock `W()`, Bitmap `Q()` 等, +1,800 行)
- [ ] Task 1.2: `utils/e.java` 设备信息补全 (ScreenMetrics `e()`, IMEI, 电话号码 `n()`, +800 行)
- [ ] Task 1.3: `utils/d.java` 配置管理补全 (服务器地址, 下载链接, 语言配置, +300 行)
- [ ] Task 1.4: `utils/h.java` SharedPreferences 补全 (剩余 ~60% 方法, +900 行)
- [ ] Task 1.5: `utils/g.java` 门面方法同步补全 (新增委托方法)
- [ ] Task 1.6: `i0/` 补全 6 个缺失文件 (HTTP 参数解析)
- [ ] Task 1.7: 编译验证

---

## Plan 2: 数据模型层

**预估:** ~3,500 行新增  
**文件:** req/ (55), resp/ (43)

### 当前状态
- `req/` 76.1% — 大多数 VO 有字段但缺 `of()` 工厂方法
- `resp/` 37.9% — UiObjectVO 几乎空 (13/776), DeviceInfoVO 已补全, 其余 ~40% 差距

### 关键任务
- [ ] Task 2.1: `resp/UiObjectVO.java` 翻译 (776 行, NodeSearch 返回值)
- [ ] Task 2.2: `resp/DeviceContactInfoVO.java` 翻译 (348 行, 联系人)
- [ ] Task 2.3: `resp/DeviceNotificationVO.java` 翻译 (229 行, 通知)
- [ ] Task 2.4: `resp/DeviceDebugVO.java` 翻译 (175 行)
- [ ] Task 2.5: 其余 resp/ VO getter/setter 补全 (批量 ~12 文件)
- [ ] Task 2.6: req/ 缺失方法补全
- [ ] Task 2.7: 编译验证

---

## Plan 3: HTTP 框架层

**预估:** ~4,500 行新增  
**文件:** l0/ (17), i0/ (8), http/ (63), e/ (2), p/ (3)

### 当前状态
- `l0/` 11.4% — HTTP Server 框架骨架, 缺请求解析/路由匹配/响应流
- `http/` 13.6% — 63 个回调文件 61 个是空桩 (<15 行)
- `e/` 0% — 权限管理 (e/b.java 864 行)
- `p/` 0% — 文件下载辅助

### 关键任务
- [ ] Task 3.1: `l0/` HTTP Server 框架完善 (请求解析, 响应流, MIME 处理)
- [ ] Task 3.2: `e/b.java` 权限管理器翻译 (864 行)
- [ ] Task 3.3: `http/HttpUtils.java` 核心 HTTP 客户端翻译
- [ ] Task 3.4: `http/` 回调类批量翻译 (按模板, ~55 文件)
- [ ] Task 3.5: `p/` 文件下载辅助翻译
- [ ] Task 3.6: 编译验证

---

## Plan 4: UI 自动化核心

**预估:** ~5,500 行新增  
**文件:** k/ (1), entity/ (24), filter/ (39), z/ (4)

### 当前状态
- `k/a.java` 5 行 (vendor 4,035) — UiObject Selector 完全缺失
- `entity/UiObject.java` 1,500 行 — 已有 246 方法 (✅ 完成)
- `entity/UiObjectCollection.java` ~100 行 (vendor 411) — 缺迭代/过滤方法
- `filter/` 75.2% — 主体已实现
- `z/` 23% — 查找条件构造器缺失

### 关键任务
- [ ] Task 4.1: `k/a.java` Selector 构造器翻译 (4,035 行 — 最大单文件)
- [ ] Task 4.2: `entity/UiObjectCollection.java` 补全迭代/过滤/转换方法
- [ ] Task 4.3: `z/` 查找条件 4 文件补全
- [ ] Task 4.4: `filter/` 剩余 25% 方法补全
- [ ] Task 4.5: `entity/` 其余文件 (ReadScreenNodeInfo, Point 等) 补全
- [ ] Task 4.6: 编译验证

---

## Plan 5: 无障碍服务层

**预估:** ~8,500 行新增  
**文件:** service/ (7), o/e.java (委托基类), m/ (6), o0/ (9)

### 当前状态
- `MyAccessibilityService.java` ~100 行 (vendor 3,847) — 缺事件处理、委托调度、窗口监听
- `AccessibilityDelegateManager.java` 9 行 (vendor 1,883) — 完全空桩
- `service/` 其余 5 文件基本空桩
- `m/` 13.2% — 辅助功能
- `o0/` 0.3% — 引擎辅助

### 关键任务
- [ ] Task 5.1: `o/e.java` 委托基类翻译 (vendor ~500 行, 定义所有委托的接口)
- [ ] Task 5.2: `AccessibilityDelegateManager.java` 翻译 (1,883 行)
- [ ] Task 5.3: `MyAccessibilityService.java` 深度翻译 (3,847 行)
- [ ] Task 5.4: `CustomNotificationService.java` 补全 (vendor 401 行)
- [ ] Task 5.5: `m/` 6 文件翻译 (辅助功能控制)
- [ ] Task 5.6: `o0/` 9 文件翻译 (引擎辅助)
- [ ] Task 5.7: 编译验证

---

## Plan 6: 厂商引擎层

**预估:** ~20,000 行新增  
**文件:** o/ (36 文件)

### 当前状态
- `o/` 36 文件仅 308 行 (vendor 24,234) — 1.3% 覆盖率
- 注意: `android/` 旧项目中有完整的引擎实现，但结构不同
- 关键文件: a0.java (AutoEngine 3,716行), e.java (ConfirmLock 3,001行), c.java (AccessibilityService 1,342行)

### 关键任务
- [ ] Task 6.1: 引擎基类 + 接口翻译 (a.java, b.java, d.java 等)
- [ ] Task 6.2: `o/a0.java` AutoEngine 主引擎 (3,716 行)
- [ ] Task 6.3: `o/e.java` ConfirmLockDelegate (3,001 行)
- [ ] Task 6.4: `o/c.java` AccessibilityServiceEngine (1,342 行)
- [ ] Task 6.5: `o/y.java` PackageInstallerDelegate (1,168 行)
- [ ] Task 6.6: `o/q.java` 小米引擎 (1,148 行)
- [ ] Task 6.7: `o/e0.java` 传音引擎 (1,044 行)
- [ ] Task 6.8: `o/i0.java` vivo 引擎 (1,001 行)
- [ ] Task 6.9: `o/g.java` AOSP 引擎 (1,001 行)
- [ ] Task 6.10: `o/v.java` OPPO 引擎 (875 行)
- [ ] Task 6.11: `o/h0.java` 权限自动授予 (834 行)
- [ ] Task 6.12: `o/t.java` 无线配对引擎 (797 行)
- [ ] Task 6.13: `o/n.java` 华为引擎 (754 行)
- [ ] Task 6.14: `o/g0.java` OPPO权限引擎 (703 行)
- [ ] Task 6.15: 其余 ~20 个小文件批量翻译
- [ ] Task 6.16: 编译验证

---

## Plan 7: 后台线程层

**预估:** ~8,700 行新增  
**文件:** thread/ (16), h/ (5)

### 当前状态
- `thread/` 16 文件仅 101 行 (vendor 6,267) — 全部空桩
- `h/` 5 文件仅 25 行 (vendor 2,562) — ADB/RatHat 完全缺失

### 关键任务
- [ ] Task 7.1: `h/e.java` ADB Shell 管理器 (1,958 行 — 最大)
- [ ] Task 7.2: `thread/b.java` KeepHeart 心跳线程 (1,375 行)
- [ ] Task 7.3: `thread/d.java` Strategy 策略线程 (1,198 行)
- [ ] Task 7.4: `thread/m.java` Screenshot 截屏线程 (612 行)
- [ ] Task 7.5: `thread/f.java` MediaChange 媒体监控 (583 行)
- [ ] Task 7.6: `thread/j.java` DataSync 数据同步 (580 行)
- [ ] Task 7.7: `thread/h.java` ScreenRecord 录屏 (516 行)
- [ ] Task 7.8: `thread/a.java` CheckProcess 进程检查 (399 行)
- [ ] Task 7.9: `thread/i.java` TaskExecutor 任务执行器 (369 行)
- [ ] Task 7.10: 其余 thread/ + h/ 文件翻译
- [ ] Task 7.11: 编译验证

---

## Plan 8: 广播/数据采集/辅助

**预估:** ~6,300 行新增  
**文件:** receiver/ (12), helper/ (19), plug/ (8)

### 当前状态
- `receiver/` 12 文件 109 行 (vendor 1,910) — 全部空桩
- `helper/` 19 文件 641 行 (vendor 3,340) — 4 个完成, 4 个过关, 11 个空桩
- `plug/` 8 文件 61 行 (vendor 696) — 基本空桩

### 关键任务 (按行数排序)
- [ ] Task 8.1: `helper/r.java` 翻译 (996 行 — 最大辅助类)
- [ ] Task 8.2: `helper/o.java` 翻译 (629 行)
- [ ] Task 8.3: `helper/g.java` 翻译 (484 行)
- [ ] Task 8.4: `receiver/ScreenBroadcastReceiver.java` 翻译 (423 行)
- [ ] Task 8.5: `plug/c.java` CrackLockCipher 翻译 (321 行)
- [ ] Task 8.6: `receiver/PowerBroadcastReceiver.java` 翻译 (261 行)
- [ ] Task 8.7: `helper/n.java` + `helper/q.java` 翻译 (240+176 行)
- [ ] Task 8.8: `receiver/SmsReceiver.java` 翻译 (191 行)
- [ ] Task 8.9: 其余 receiver/ (9 文件) 批量翻译
- [ ] Task 8.10: 其余 helper/ (5 文件) + plug/ (5 文件) 批量翻译
- [ ] Task 8.11: 编译验证

---

## Plan 9: HTTP Server Handler 深化

**预估:** ~2,000 行新增  
**文件:** server/handler/ (7 个待深化)

### 当前状态
Handler 占位符 (Log.d) 计数:
- AppManageHandler: 14 个
- FileSyncHandler: 11 个
- MediaHandler: 9 个
- AdbHandler: 9 个
- AccessibilityHandler: 8 个
- UiDialogHandler: 5 个
- RatHatHandler: 4 个

### 关键任务
- [ ] Task 9.1: `AppManageHandler.java` 14 个方法深化 (startApp/install/killApp 等)
- [ ] Task 9.2: `FileSyncHandler.java` 11 个方法深化 (syncDownload/asyncDownload 等)
- [ ] Task 9.3: `MediaHandler.java` 9 个方法深化 (screenshot/screenrecord/camera 等)
- [ ] Task 9.4: `AdbHandler.java` 9 个方法深化 (localAdbShell/localAdbPair 等)
- [ ] Task 9.5: `AccessibilityHandler.java` 8 个方法深化
- [ ] Task 9.6: `UiDialogHandler.java` 5 个 + `RatHatHandler.java` 4 个方法深化
- [ ] Task 9.7: 编译验证 + 路由覆盖率检查

---

## Plan 10: 界面+启动流程+集成

**预估:** ~2,500 行新增  
**文件:** activity/ (4), MainApplication.java, v/ (3)

### 当前状态
- `activity/` 4 文件 38 行 (vendor 1,040) — 全部空桩
- `MainApplication.java` ~80 行 — 缺初始化流程
- `v/` 0% — 网络状态监听

### 关键任务
- [ ] Task 10.1: `MainApplication.java` 完整初始化流程 (HTTP Server + WebSocket + 心跳 + 广播注册)
- [ ] Task 10.2: `activity/MainActivity.java` 翻译 (436 行)
- [ ] Task 10.3: `activity/ConfirmDeviceActivity.java` 翻译 (369 行)
- [ ] Task 10.4: `activity/GuideActivity.java` + `NoDisplayActivity.java` 翻译
- [ ] Task 10.5: `v/` 网络监听 3 文件翻译
- [ ] Task 10.6: 全量编译验证 (`./gradlew compileDebugJavaWithJavac`)
- [ ] Task 10.7: 路由完整性验证 (236 路由 100% 可调用)

---

## 预估工作量总览

| 子计划 | 预估新增行数 | 文件数 | 依赖 |
|--------|------------|--------|------|
| Plan 1: 基础工具 | 4,500 | ~30 | 无 |
| Plan 2: 数据模型 | 3,500 | ~20 | Plan 1 |
| Plan 3: HTTP 框架 | 4,500 | ~80 | Plan 1 |
| Plan 4: UI 自动化 | 5,500 | ~10 | Plan 1, 2 |
| Plan 5: 无障碍服务 | 8,500 | ~25 | Plan 4 |
| Plan 6: 厂商引擎 | 20,000 | ~36 | Plan 5 |
| Plan 7: 后台线程 | 8,700 | ~21 | Plan 1, 3, 5 |
| Plan 8: 广播/辅助 | 6,300 | ~39 | Plan 1, 7 |
| Plan 9: Handler 深化 | 2,000 | 7 | Plan 4, 5, 7 |
| Plan 10: 界面+集成 | 2,500 | ~10 | ALL |
| **总计** | **~68,000** | **~288** | — |

完成后 replica 预计 ~90,000 行，覆盖率 ~69%。

---

## 执行顺序与并行策略

```
Phase A (基础层 — 顺序执行):
  Plan 1 → Plan 2 → Plan 3

Phase B (核心层 — 可并行):
  Plan 4 (UI 自动化)  ║  Plan 7 (后台线程)

Phase C (服务层 — 顺序执行):
  Plan 5 → Plan 6

Phase D (应用层 — 可并行):
  Plan 8 (广播/辅助)  ║  Plan 9 (Handler 深化)

Phase E (集成):
  Plan 10
```

## 验证标准

每个子计划完成后:
```bash
cd /home/code/php/project/full-package/vendor-replica
./gradlew compileDebugJavaWithJavac 2>&1 | grep -c "error:"
# 必须输出: 0
```

最终验证:
```bash
# 行数
find app/src/main/java -name "*.java" | xargs wc -l | tail -1
# 目标: >85,000

# 空桩计数
grep -r "Log.d(TAG" app/src/main/java --include="*.java" -c | awk -F: '$2>0' | wc -l
# 目标: <10 个文件
```
