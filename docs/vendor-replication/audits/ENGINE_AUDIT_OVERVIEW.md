# 厂商适配引擎代码审计总览

> 审计日期: 2026-03-21
> Vendor 源码: `decompiled_vendor/sources/o/` — 33 个 Java 文件, ~11K 行
> Replica 源码: `android/app/src/main/java/com/vendor/rat/auto/engine/`

## 文件分类与映射

### A. 基础类 (5 个)

| Vendor 文件 | 行数 | Replica 文件 | 类型 | 说明 |
|-------------|------|-------------|------|------|
| `o/e.java` | 982 | `AccessibilityServiceEngine.java` | 基类 | AccessibilityDelegate 核心基类 |
| `o/c.java` | 801 | `AutoEngine.java` | 抽象基类 | KeepAliveEngine 厂商引擎基类 (extends e) |
| `o/b.java` | 177 | *(工具方法分散在各类中)* | 工具类 | CombineFilter 构建 + 加密工具 |
| `o/r.java` | 69 | `AccessibilityEventRecord.java` | 数据类 | ReadScreenWindow 容器 |
| `o/c0.java` | 30 | `ScreenCaptureExecutor.java` | 工具类 | 截屏能力检测 |
| `o/j0.java` | 51 | *(内嵌在 engine 中)* | 数据类 | 事件快照 (UiObject + 时间戳) |

### B. 厂商 KeepAlive 引擎 (6 个, extends c)

| Vendor 文件 | 行数 | Replica 文件 | ListenWindow 方法 | 目标厂商 |
|-------------|------|-------------|-------------------|----------|
| `o/v.java` | 526 | `vendor/HuaweiEngine.java` | `w0()` | 华为 EMUI/HarmonyOS |
| `o/q.java` | 498 | `vendor/XiaomiEngine.java` | `l0()` | 小米 MIUI/HyperOS |
| `o/n.java` | 454 | `vendor/OppoEngine.java` | `s0()` | OPPO ColorOS |
| `o/i0.java` | 684 | `vendor/VivoEngine.java` + `VivoKeepAliveEngine.java` | `u0()` | vivo OriginOS |
| `o/e0.java` | 373 | `TranssionKeepAliveEngine.java` | `n0()` | 传音 HiOS (Tecno/Infinix/itel) |
| `o/g.java` | 316 | `AospKeepAliveEngine.java` | `k0()` | AOSP 原生 Android |

### C. 通用 Delegate (8 个, extends e)

| Vendor 文件 | 行数 | Replica 文件 | ListenWindow 方法 | 功能 |
|-------------|------|-------------|-------------------|------|
| `o/a0.java` | 2003 | `OpenDevelopmentDelegate.java` | `E0()` | 开发者选项/ADB/USB 调试 |
| `o/t.java` | 677 | `ScreenUnlockDelegate.java` | `X()` | 屏幕解锁 (PIN/密码/图案) |
| `o/x.java` | 531 | `PermissionGrantDelegate.java` + `PermissionAutoGrantEngine.java` | `N()` | 运行时权限自动授予 |
| `o/g0.java` | 432 | `DeviceCredentialDelegate.java` | `T()` | 设备凭证验证 |
| `o/k.java` | 382 | `EnableSecureDelegate.java` | `J()` | 安全设置启用 |
| `o/i.java` | 266 | `ConfirmLockDelegate.java` | `L()` | 锁屏密码确认 |
| `o/h.java` | 196 | *(部分在 DeviceCredentialDelegate 中)* | `M()` | 设备凭证验证2 |
| `o/l.java` | 71 | `PairAccessibilityDelegate.java` | `J()` | 配对无障碍服务 |
| `o/o.java` | 55 | `MediaProjectionDelegate.java` | `H()` | 媒体投影权限 |

### D. Runnable 异步任务 (12 个)

| Vendor 文件 | 行数 | 调用者 | 功能 |
|-------------|------|--------|------|
| `o/a.java` | 346 | `AccessibilityDelegateManager` | delegate 清理/移除回调 |
| `o/b0.java` | 136 | `e (AccessibilityDelegate)` | 事件处理异步执行 |
| `o/d.java` | 292 | `c (KeepAliveEngine)` | 厂商引擎启动任务 |
| `o/d0.java` | 261 | `a0 (OpenDevelopmentDelegate)` | 开发者选项操作任务 |
| `o/f.java` | 31 | `c (KeepAliveEngine)` | 引擎状态重置 |
| `o/h0.java` | 307 | `t (ScreenUnlockDelegate)` | 屏幕解锁操作任务 |
| `o/m.java` | 32 | `g0 (DeviceCredentialDelegate)` | 凭证验证任务 |
| `o/p.java` | 80 | `k (EnableSecureDelegate)` | 安全设置操作任务 |
| `o/s.java` | 107 | `i (ConfirmLockDelegate)` | 锁屏确认操作任务 |
| `o/u.java` | 169 | `x (PermissionGrantDelegate)` | 权限授予操作任务 |
| `o/w.java` | 33 | `l (PairAccessibilityDelegate)` | 配对操作任务 |
| `o/z.java` | 42 | `o (MediaProjectionDelegate)` | 媒体投影操作任务 |

## 继承层次

```
AccessibilityService (Android SDK)
  └─ AccessibilityDelegateManager (service/AccessibilityDelegateManager.java)
       └─ MyAccessibilityService (service/MyAccessibilityService.java)

o.e (AccessibilityDelegate 基类, 982L)
  ├─ o.c (KeepAliveEngine 抽象基类, 801L, abstract)
  │   ├─ o.v  → HuaweiEngine (526L)
  │   ├─ o.q  → XiaomiEngine (498L)
  │   ├─ o.n  → OppoEngine (454L)
  │   ├─ o.i0 → VivoEngine (684L)
  │   ├─ o.e0 → TranssionEngine (373L)
  │   └─ o.g  → AospEngine (316L)
  ├─ o.a0 → OpenDevelopmentDelegate (2003L)
  ├─ o.t  → ScreenUnlockDelegate (677L)
  ├─ o.x  → PermissionGrantDelegate (531L)
  ├─ o.g0 → DeviceCredentialDelegate (432L)
  ├─ o.k  → EnableSecureDelegate (382L)
  ├─ o.i  → ConfirmLockDelegate (266L)
  ├─ o.h  → DeviceCredentialDelegate2 (196L)
  ├─ o.l  → PairAccessibilityDelegate (71L)
  └─ o.o  → MediaProjectionDelegate (55L)
```

## 审计文档索引

| 文档 | 覆盖文件 | 状态 |
|------|----------|------|
| [ENGINE_AUDIT_e_AccessibilityDelegate.md](./ENGINE_AUDIT_e_AccessibilityDelegate.md) | `o/e.java` | ✅ 完成 |
| [ENGINE_AUDIT_c_KeepAliveEngine.md](./ENGINE_AUDIT_c_KeepAliveEngine.md) | `o/c.java` | ✅ 完成 |
| [ENGINE_AUDIT_v_HuaweiEngine.md](./ENGINE_AUDIT_v_HuaweiEngine.md) | `o/v.java` | ✅ 完成 |
| [ENGINE_AUDIT_q_XiaomiEngine.md](./ENGINE_AUDIT_q_XiaomiEngine.md) | `o/q.java` | ✅ 完成 |
| [ENGINE_AUDIT_n_HuaweiStartupEngine.md](./ENGINE_AUDIT_n_HuaweiStartupEngine.md) | `o/n.java` | ✅ 完成 (映射修正: 实际是华为启动管理) |
| [ENGINE_AUDIT_i0_VivoEngine.md](./ENGINE_AUDIT_i0_VivoEngine.md) | `o/i0.java` | ✅ 完成 |
| [ENGINE_AUDIT_e0_TranssionEngine.md](./ENGINE_AUDIT_e0_TranssionEngine.md) | `o/e0.java` | ✅ 完成 |
| [ENGINE_AUDIT_g_AospEngine.md](./ENGINE_AUDIT_g_AospEngine.md) | `o/g.java` | ✅ 完成 |
| [ENGINE_AUDIT_a0_OpenDevelopmentDelegate.md](./ENGINE_AUDIT_a0_OpenDevelopmentDelegate.md) | `o/a0.java` | ✅ 完成 |
| [ENGINE_AUDIT_delegates.md](./ENGINE_AUDIT_delegates.md) | `o/t,x,g0,k,i,h,l,o` + Runnables | ✅ 完成 (8 个 Delegate + 12 个 Runnable) |

<!-- PLACEHOLDER_FOR_APPEND -->
