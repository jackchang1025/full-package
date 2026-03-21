# ENGINE AUDIT: o/e0.java → 传音 TranssionEngine

> Vendor: `decompiled_vendor/sources/o/e0.java` (373行)
> Replica: `TranssionKeepAliveEngine.java`
> 审计日期: 2026-03-21

## 1. 类定义

| 属性 | Vendor |
|------|--------|
| 类名 | `o.e0` |
| 继承 | `extends o.c` (KeepAliveEngine) |
| 构造 | `super(n0(), "com.android.settings")` |
| 超时 | 60 秒 |

## 2. 字段 (7 个)

| Vendor 字段 | 类型 | 说明 |
|-------------|------|------|
| `f627r` | `AtomicReference<r.e>` | 当前保活目标 |
| `f628s` | `AtomicBoolean` | 主应用自启动 |
| `f629t` | `AtomicBoolean` | 备份应用自启动 |
| `f630u` | `AtomicBoolean` | 主应用关联启动 (默认 true) |
| `f631v` | `AtomicBoolean` | 备份应用关联启动 (默认 true) |
| `f632w` | `AtomicBoolean` | 主应用完全后台 |
| `f633x` | `AtomicBoolean` | 备份应用完全后台 |

## 3. n0() — ListenWindow 列表 (7 个)

| # | 方法 | packageName | className | 说明 |
|---|------|-------------|-----------|------|
| 0 | `c.J()` | com.android.settings | android.app.Dialog | 电池优化对话框 (共享) |
| 1 | `i0()` | com.transsion.phonemaster | ...AutoStartActivity | 传音自启动管理 |
| 2 | `h0()` | com.transsion.phonemaster | android.widget.FrameLayout | 传音手机管家 FrameLayout |
| 3 | `d0(null)` | com.android.settings | ...InstalledAppDetailsTop | 应用详情 (通用) |
| 4 | `e0(null)` | com.android.settings | ...transsion...AppInfoSettings | 传音应用信息 |
| 5 | `m0(null)` | com.android.settings | android.widget.FrameLayout | 设置 FrameLayout |
| 6 | `c0()` | com.android.settings | ...SubSettings | 子设置页 |

## 4. CombineFilter 配置 Key

| 方法 | 配置 Key | 说明 |
|------|----------|------|
| `b0()` | COMMON_SETTINGS_BATTERY_TEXT | 电池 |
| `f0()` | COMMON_SETTINGS_POWER_TEXT | 电源 |
| `g0()` | COMMON_SETTINGS_USE_POWER_TEXT | 耗电 |
| `q0()` | COMMON_SETTINGS_UNRESTRICTED_TEXT / NO_RESTRICTED_TEXT / HAS_CANCEL_RESTRICTED_TEXT | 不受限/无限制/已取消限制 (OR 匹配) |

## 5. 状态机 — u() (3 状态)

```
事件到达 → u()
  ├─ k0() → keepAliveInAppDetail → d0(this, 0)
  │   └─ 应用详情页: 查找电池/耗电入口 → 点击
  ├─ j0() → keepAliveInAppBattery → d0(this, 1)
  │   └─ 耗电管理页: 操作"不受限"选项
  └─ l0() → keepAliveInAutoStart → d0(this, 2)
      └─ 自启动管理页: 操作自启动开关
```

## 6. 目标包名

| 包名 | 说明 |
|------|------|
| `com.android.settings` | 系统设置 |
| `com.transsion.phonemaster` | 传音手机管家 (Tecno/Infinix/itel) |

## 7. 特殊方法

### q0() — 不受限 OR 匹配 (3 个条件)

```java
CombineFiltersWithOr:
  Filter1: COMMON_SETTINGS_UNRESTRICTED_TEXT (不受限)
  Filter2: COMMON_SETTINGS_NO_RESTRICTED_TEXT (无限制)
  Filter3: COMMON_SETTINGS_HAS_CANCEL_RESTRICTED_TEXT (已取消限制)
```

### o0(UiObject) — 电池优化操作 (smali, 未完全反编译)

从 smali 推断: 在耗电管理页查找"不受限"选项并点击选中。
