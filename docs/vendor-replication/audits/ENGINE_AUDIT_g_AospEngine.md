# ENGINE AUDIT: o/g.java → AOSP AospEngine

> Vendor: `decompiled_vendor/sources/o/g.java` (316行, 最简单的厂商引擎)
> Replica: `AospKeepAliveEngine.java`
> 审计日期: 2026-03-21

## 1. 类定义

| 属性 | Vendor |
|------|--------|
| 类名 | `o.g` |
| 继承 | `extends o.c` (KeepAliveEngine) |
| 构造 | `super(k0(), "com.android.settings")` |
| 超时 | 30 秒 (最短) |

## 2. 字段 (4 个 — 最少)

| Vendor 字段 | 类型 | 说明 |
|-------------|------|------|
| `f637r` | `AtomicReference<r.e>` | 当前保活目标 |
| `f638s` | `AtomicBoolean` | 完全后台 已完成 |
| `f639t` | `AtomicBoolean` | 自启动 已完成 |
| `f640u` | `AtomicBoolean` | 关联启动 已完成 |

## 3. k0() — ListenWindow 列表 (8 个)

| # | 方法 | packageName | className | matchs | 说明 |
|---|------|-------------|-----------|--------|------|
| 0 | `c.J()` | com.android.settings | android.app.Dialog | *(无)* | 电池优化对话框 (共享) |
| 1-2 | `e0(包名)` | com.android.settings | ...InstalledAppDetailsTop | H(包名) | 应用详情 (主/备份) |
| 3-4 | `m0(包名)` | com.android.settings | ...SpaActivity | H(包名) | Settings SPA (Android 13+) |
| 5-6 | `j0(包名)` | com.android.settings | android.widget.FrameLayout | H(包名) | 设置 FrameLayout |
| 7 | `d0()` | com.android.settings | ...SubSettings | *(无)* | 子设置页 |

## 4. CombineFilter 配置 Key

| 方法 | 配置 Key | 说明 |
|------|----------|------|
| `b0()` | COMMON_ALLOW_BACKGROUND_USAGE_TEXT | 允许后台使用 |
| `c0()` | COMMON_SETTINGS_BATTERY_TEXT | 电池 |
| `f0()` | COMMON_SETTINGS_POWER_TEXT | 电源 |
| `g0()` | COMMON_SETTINGS_USE_POWER_TEXT | 耗电 |
| `o0()` | COMMON_SETTINGS_UNRESTRICTED_TEXT / NO_RESTRICTED_TEXT / HAS_CANCEL_RESTRICTED_TEXT | 不受限 (OR 匹配, 与传音共享) |

## 5. 状态机 — u() (2 状态, 最简单)

```
事件到达 → u()
  ├─ i0() → keepAliveInAppDetail → f(this, 0)
  │   └─ 应用详情页: 查找电池/耗电入口 → 点击进入
  └─ h0() → keepAliveInAppBattery → f(this, 1)
      └─ 耗电管理页: 操作"不受限"选项
```

## 6. 核心操作

### l0(UiObject) — 电池优化操作 (smali, 未完全反编译)

从 smali 推断逻辑:
```
1. 查找 "不受限" 文本 (o0() OR 匹配)
2. 如果找到 → 检查是否已选中
3. 如果未选中 → 点击选中
4. 如果没找到 → 查找 "允许后台使用" (b0())
5. 操作 Switch/RadioButton
```

### n0(String) — 保活状态持久化

```java
PowerControlStateVO vo = h.k(packageName);
vo.setAllowAllFullBackground(f638s.get());
vo.setAllowAutoStart(f639t.get());
vo.setAllowRelateStart(f640u.get());
vo.setRetryCount(vo.getRetryCount() + 1);
h.L(vo);  // 保存到 SharedPreferences
```

## 7. Z() — 完成流程

与其他引擎相同的标准流程:
```
lock → !T() → X() → g.h(100) → P().x()
→ 保存状态 (主/备份)
→ shutdownNow → clear → T0(5) → g.c() (移除遮罩)
→ c.W() → d()
```

## 8. 与传音引擎的对比

AOSP 和传音引擎非常相似，共享大量配置 Key:

| 维度 | AOSP (o/g) | 传音 (o/e0) |
|------|-----------|------------|
| ListenWindow 数 | 8 | 7 |
| 状态数 | 2 | 3 |
| 字段数 | 4 | 7 |
| 超时 | 30s | 60s |
| 行数 | 316 | 373 |
| 特有包名 | *(无, 纯 AOSP)* | com.transsion.phonemaster |
| SpaActivity | ✅ (Android 13+) | ❌ |
| 自启动管理 | ❌ | ✅ |
| 共享 Key | COMMON_SETTINGS_BATTERY_TEXT, COMMON_SETTINGS_POWER_TEXT, COMMON_SETTINGS_UNRESTRICTED_TEXT | 同左 |

## 9. 全部 6 个厂商引擎对比总表

| 维度 | 华为电池(v) | 小米(q) | 华为启动(n) | vivo(i0) | 传音(e0) | AOSP(g) |
|------|-----------|---------|-----------|---------|---------|---------|
| 行数 | 526 | 498 | 454 | 684 | 373 | 316 |
| LW数 | 12 | 16 | 7 | 17 | 7 | 8 |
| 状态数 | 4 | 2 | 4 | 7 | 3 | 2 |
| 字段数 | 4 | 8 | 7 | 11 | 7 | 4 |
| 超时 | 100s | 100s | 50s | 120s | 60s | 30s |
| 主包名 | settings | securitycenter | settings | settings | settings | settings |
| Switch方式 | R()坐标 | O()click | a0()+click | *(混合)* | *(通用)* | *(通用)* |
| 双应用 | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| 复杂度 | 中 | 中 | 低 | 高 | 低 | 最低 |
