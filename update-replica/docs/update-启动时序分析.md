# Vendor APK (update.apk) 启动日志分析

> 设备: 小米13 (2211133C), Android 16, SDK 36, HyperOS
> 包名: dev.deltalab2964.swift (com.storm.safe.rock)
> 版本: 4.6.4 (versionCode=40604)
> 测试时间: 2026-04-15 01:03-01:04
> PID: 18327

## Vendor 完整 Activity 启动时序

| # | 时间 | Flag | Activity | BAL 权限 | 阶段 |
|---|------|------|----------|---------|------|
| 1 | 01:03:17.670 | 0x10000000 | AccessibilityTrampoline | VISIBLE_WINDOW | 启动引导 |
| 2 | 01:03:17.714 | 0x50800000 | MiuiAccessibilitySettingsActivity | VISIBLE_WINDOW | 打开无障碍设置 |
| 3 | 01:03:17.726 | 0x50800000 | MiuiAccessibilitySettingsActivity | VISIBLE_WINDOW | (重复) |
| — | 01:03:17~33 | — | **用户手动授权无障碍 (16秒)** | — | — |
| 4 | 01:03:33.696 | 0x34000000 | **iuzxujjtqev** | NON_APP_VISIBLE | **smartReturnToApp** |
| 5 | 01:03:36.043 | 0x10800000 | **umrkmgrri** (yw5xud overlay) | VISIBLE_WINDOW | **★ 启动透明 overlay** |
| 6 | 01:03:36.070 | — | GrantPermissionsActivity | VISIBLE_WINDOW | 运行时权限请求 |
| 7 | 01:03:37.002 | — | GrantPermissionsActivity | VISIBLE_WINDOW | 运行时权限请求 |
| 8 | 01:03:38.601 | 0x14000000 | iuzxujjtqev | VISIBLE_WINDOW | 返回 app |
| 9 | 01:03:39.334 | 0x50810000 | **MiuiSettings** | VISIBLE_WINDOW | **品牌引擎开始** |
| 10 | 01:03:41.821 | 0x50810000 | MiuiSettings | NON_APP_VISIBLE | (重复) |
| 11 | 01:03:48.192 | 0x50810000 | ApplicationsDetailsActivity | NON_APP_VISIBLE | 应用详情 |
| 12 | 01:03:51.320 | 0x10800000 | HiddenAppsConfigActivity | — | 电池/省电 |
| 13 | 01:03:51.331 | 0x50810000 | ApplicationsDetailsActivity | NON_APP_VISIBLE | 返回应用详情 |
| 14 | 01:03:54.279 | 0x10800000 | ChannelPanelActivity | NON_APP_VISIBLE | 通知设置 |
| 15 | 01:03:56.082 | 0x50810000 | ApplicationsDetailsActivity | NON_APP_VISIBLE | 返回应用详情 |
| 16 | 01:04:00.033 | 0x50810000 | ApplicationsDetailsActivity | NON_APP_VISIBLE | 返回应用详情 |
| 17 | 01:04:19.543 | 0x10800000 | AppManageExternalStorageActivity | NON_APP_VISIBLE | 文件访问权限 |
| 18 | 01:04:20.935 | 0x10800000 | **iuzxujjtqev** | NON_APP_VISIBLE | **品牌引擎完成，返回 app** |
| 19 | 01:04:21.908 | 0x10800000 | **AppWriteSettingsActivity** | **VISIBLE_WINDOW** | **★ WRITE_SETTINGS** |
| 20 | 01:04:23.542 | 0x10000000 | iuzxujjtqev | NON_APP_VISIBLE | 返回 app |
| 21 | 01:04:23.579 | 0x14000000 | DefaultLauncherAlias | VISIBLE_WINDOW | 启动 launcher |
| 22 | 01:04:25.573 | 0x30000000 | **syuqattwmgit** | VISIBLE_WINDOW | **透明 overlay** |
| 23 | 01:04:25.976 | 0x10000000 | syuqattwmgit | VISIBLE_WINDOW | (重复) |

## 关键发现

### 1. 执行顺序：品牌引擎 → WRITE_SETTINGS（与我们的 finally 块设计一致）

Vendor 的实际顺序：
```
授权无障碍 → smartReturnToApp → umrkmgrri overlay → 运行时权限 → 品牌引擎(~45秒) → WRITE_SETTINGS → syuqattwmgit overlay
```

### 2. 关键差异：umrkmgrri 透明 overlay

Vendor 在品牌引擎开始前启动了 `umrkmgrri` (yw5xud overlay)：
- 时间: 01:03:36 (品牌引擎 01:03:39 之前)
- Flag: `0x10800000` = `NEW_TASK | NO_HISTORY`
- BAL: `VISIBLE_WINDOW` — **有可见窗口才能从后台启动 Activity**

### 3. Flag 分析

| Flag | 十六进制 | 含义 | 使用场景 |
|------|---------|------|---------|
| 0x10000000 | NEW_TASK | 新任务栈 | 基础 flag |
| 0x10800000 | NEW_TASK + NO_HISTORY | 新任务栈 + 不留历史 | WRITE_SETTINGS, overlay |
| 0x14000000 | NEW_TASK + CLEAR_TOP | 新任务栈 + 清栈顶 | 返回 app |
| 0x30000000 | NEW_TASK + SINGLE_TOP | 新任务栈 + 单实例 | syuqattwmgit |
| 0x34000000 | NEW_TASK + CLEAR_TOP + SINGLE_TOP | 全部 | smartReturnToApp |
| 0x50810000 | NEW_TASK + NO_HISTORY + MULTIPLE_TASK + CLEAR_TOP | 全部 | 品牌引擎 settings |

### 4. 与复刻项目的差异

| # | 差异 | Vendor | 复刻 | 影响 |
|---|------|--------|------|------|
| 1 | **umrkmgrri overlay** | 品牌引擎前启动，维持 VISIBLE_WINDOW | 没有 | 复刻无法从后台启动 Activity |
| 2 | **WRITE_SETTINGS flag** | `0x10800000` (NEW_TASK + NO_HISTORY) | `0x10000000` (NEW_TASK only) | NO_HISTORY 防止页面残留 |
| 3 | **品牌引擎 flag** | `0x50810000` (含 MULTIPLE_TASK) | `0x10000000` (NEW_TASK only) | MULTIPLE_TASK 允许多任务栈 |
| 4 | **smartReturnToApp 后** | 先启动 umrkmgrri overlay，再请求运行时权限 | 直接跑品牌引擎 | 缺少 overlay 导致后续 startActivity 被 MIUI 阻止 |
| 5 | **syuqattwmgit** | 品牌引擎+WRITE_SETTINGS 完成后启动 | 未启动 | 维持长期前台权限 |

## 修复方案

1. **在品牌引擎前启动 umrkmgrri overlay** — 维持 BAL_ALLOW_VISIBLE_WINDOW
2. **WRITE_SETTINGS 使用 `0x10800000` flag** — NEW_TASK + NO_HISTORY
3. **品牌引擎 Intent 使用 `0x50810000` flag** — 含 MULTIPLE_TASK
4. **完成后启动 syuqattwmgit** — 维持长期前台权限

## 完整日志

见 `docs/update-启动日志-full.txt` (1014 行)
