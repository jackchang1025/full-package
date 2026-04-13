# 真机验证脚本 — LocateValues / 保活 UI 自动化

验证 `LocateValuesSeeder`（Plan A）在真机上正确执行，并校验 80-key `locateValues.json` 中各厂商前缀的 key 在实际 ROM UI 上的文本匹配度。

**前置条件**: [`docs/superpowers/plans/2026-04-11-locate-values-asset-seed.md`](../../../docs/superpowers/plans/2026-04-11-locate-values-asset-seed.md) 已合并到 main —— 即 `vendor-replica/app/src/main/java/com/guard/wallet/utils/LocateValuesSeeder.java` 文件存在并且 `MainApplication.init()` 里调用了 `LocateValuesSeeder.seedIfChanged(this)`。没这个前置条件脚本会在 step `pre-flight` 处 fail-fast 退出。

## 目录结构

```
real-device-verify/
├── README.md                      ← 本文
├── lib/
│   ├── common.sh                  ← 共享 bash 函数（前置检查/打包/安装/日志抓取/报告生成）
│   └── compare.py                 ← 纯 Python 的 ui.xml ↔ locateValues.json 比对器
├── verify-oppo-pgfm10.sh          ← OPPO PGFM10 (ColorOS 16)
├── verify-xiaomi-13.sh            ← Xiaomi 13 (HyperOS V816)
├── verify-huawei-harmony.sh       ← 华为鸿蒙
├── verify-huawei-android.sh       ← 华为安卓 (EMUI)
├── verify-all.sh                  ← 4 台设备顺序执行 + 汇总报告
└── reports/                       ← 生成的验证报告（markdown + logcat + ui.xml）
```

## 依赖

- **`bash`** 4+（WSL Ubuntu / 任意 Linux）
- **`adb`**: `/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe`（如果不在这个路径，`export ADB=/your/path/to/adb` 覆盖）
- **`python3`** 3.8+（用于 `compare.py`）
- **`unzip`**（用于校验 APK 里确实打包了 `locateValues.json`）
- **`JAVA_HOME`** — 脚本会自动 export `/usr/lib/jvm/java-17-openjdk-amd64`；如果路径不同，先手动 export

## 用法

### 单设备验证

```bash
cd /home/code/php/project/full-package
bash vendor-replica/scripts/real-device-verify/verify-oppo-pgfm10.sh
```

设备默认从 `ADB_CONNECTION.md` 里的 IP 读取，也可以用环境变量覆盖：

```bash
DEVICE_ID=192.168.1.100:5555 \
  bash vendor-replica/scripts/real-device-verify/verify-oppo-pgfm10.sh
```

### 全量验证（推荐）

```bash
cd /home/code/php/project/full-package
bash vendor-replica/scripts/real-device-verify/verify-all.sh
```

这会依次跑 4 台设备，并在 `reports/SUMMARY.md` 生成汇总。某台设备失败不会阻塞后续设备，最终退出码 `0`=全绿 / `1`=至少一台 FAIL。

## 脚本工作流（每台设备）

```
┌─────────────────────────────────────────────────────────────────┐
│ 1. pre-flight                                                   │
│    检查 ADB, Plan A 是否合并, locateValues.json 是否 ≥60 个 key    │
├─────────────────────────────────────────────────────────────────┤
│ 2. build                                                        │
│    如 APK 已存在且比 locateValues.json 新则跳过, 否则 gradle 打包   │
├─────────────────────────────────────────────────────────────────┤
│ 3. connect                                                      │
│    adb connect <device>, 抓设备的 brand/model/android 版本      │
├─────────────────────────────────────────────────────────────────┤
│ 4. install                                                      │
│    uninstall 老版本 → install -r -g 安装新 APK（授予全部权限）    │
├─────────────────────────────────────────────────────────────────┤
│ 5. launch                                                       │
│    清 logcat → am start MainActivity → 等 8 秒让 seeder 执行     │
│    抓 LocateValuesSeeder:V 的日志到 reports/<label>.seeder.log  │
├─────────────────────────────────────────────────────────────────┤
│ 6. seeder assertion                                             │
│    grep 日志看到 SEEDED_FIRST_TIME/SKIPPED_UP_TO_DATE/         │
│    SKIPPED_ADOPTED_EXISTING/SEEDED_UPDATED 之一 → PASS          │
│    若 ERROR 或没有相关行 → FAIL                                   │
│    远端 stat /sdcard/Android/data/com.guard.wallet/files/       │
│    locateValues.json 文件大小 > 100 字节                         │
├─────────────────────────────────────────────────────────────────┤
│ 7. open-ui                                                      │
│    am start 打开厂商特定的设置页(OPPO PowerControl/MIUI          │
│    AutoStart/Huawei systemmanager) → 等 6 秒让 engine 响应      │
├─────────────────────────────────────────────────────────────────┤
│ 8. ui-dump                                                      │
│    uiautomator dump → pull 到 reports/<label>.ui.xml            │
├─────────────────────────────────────────────────────────────────┤
│ 9. compare                                                      │
│    Python 对比: locateValues.json 里符合前缀(COLORS_/MIUI_/     │
│    HUA_WEI_/OPPO_...)的 key → 检查 value 是否在 ui.xml 的任意   │
│    node text/content-desc 里出现(exact / substring / missing)   │
├─────────────────────────────────────────────────────────────────┤
│ 10. report                                                      │
│    生成 reports/<label>.md 包含:                                │
│    - 设备信息                                                   │
│    - Seeder 结果 + 文件大小                                      │
│    - UI 命中率 + 详细 key 匹配表                                  │
│    - 人工 follow-up 指引                                         │
└─────────────────────────────────────────────────────────────────┘
```

## 产出文件

每台设备产生 3 个文件 + 1 个全局汇总文件：

| 文件 | 用途 |
|---|---|
| `reports/<label>.md` | 人类可读的验证报告（markdown） |
| `reports/<label>.seeder.log` | 冷启动后 8 秒内的 logcat，含 `LocateValuesSeeder:V`、`LocateValuesUtils:V` 两个 tag |
| `reports/<label>.ui.xml` | uiautomator dump 的完整 UI hierarchy，供人工 grep 验证 |
| `reports/SUMMARY.md`（仅 `verify-all.sh`） | 4 台设备汇总 + 命中率对比 + 下一步建议 |

## 设备 → 脚本 → 检查的 key 前缀映射

| 脚本 | 设备 | IP | 检查前缀 | 命中目标 |
|---|---|---|---|---|
| `verify-oppo-pgfm10.sh` | OPPO PGFM10 (ColorOS 16) | 192.168.31.249 | `COLORS_` `OPPO_` | 9 + 5 = 14 个 key |
| `verify-xiaomi-13.sh` | Xiaomi 13 (HyperOS) | 192.168.31.102 | `MIUI_` | 4 个 key |
| `verify-huawei-harmony.sh` | 华为鸿蒙 | 192.168.31.162 | `HUA_WEI_` | 7 个 key |
| `verify-huawei-android.sh` | 华为 Android (EMUI) | 192.168.31.211 | `HUA_WEI_` | 7 个 key |

**未覆盖的 engine**：
- `VivoEngine`：无真机（项目 memory 没列 vivo 设备）
- `TranssionEngine`：无真机
- `AospKeepAliveEngine`：无真机兜底测试设备

如果将来有 vivo 或其他设备，复制 `verify-huawei-android.sh` 改 IP + 前缀（`VIVO_` / `COMMON_` / `*`）即可。

## 命中率解读

| 命中率 | 含义 | 行动 |
|---|---|---|
| **100%** | 当前 UI dump 页面里**所有**被检查的 key 都能找到对应文本。✅ | 可以立即进入端到端流程测试（触发 ADB 配对/保活） |
| **50-99%** | 部分 key 命中。大概率是**当前采样页面不包含**那些 key 对应的 UI 元素（例如 OPPO 的"允许关联启动"只在 PowerControl 页面存在）。🟡 | 对未命中的 key，到对应的设置子页面手动采样一次：`adb shell uiautomator dump && adb pull /sdcard/ui-dump.xml`，再 grep key 对应的值 |
| **<50%** | 可能 HyperOS/ColorOS 文案大改，或**当前页面完全错了**。🔴 | 先确认 `UI dump` 的 `<hierarchy rotation=...>` 开头是不是期望的 Activity；如果是，对比 `assets/locateValues.json` 里的值和 dump 里实际的中文字符串；更新 JSON 里的值 + 同步 `LocateValuesAssetTest#allRequiredKeysPresent` 的 `required[]` 数组 |

## 命中率 < 100% 的处理示例

假设 `xiaomi-13.md` 报告显示 `MIUI_APP_POWER_CONSUME_TEXT` 未命中：

```bash
# 1. 在 ui.xml 里 grep 它的期望值
grep -oE 'text="[^"]*应用耗电[^"]*"' reports/xiaomi-13.ui.xml
# 空输出 → 说明 "应用耗电" 在当前 dump 里不存在

# 2. 反向搜索类似的文本
grep -oE 'text="[^"]*耗电[^"]*"' reports/xiaomi-13.ui.xml
# 可能输出: text="耗电情况"   ← HyperOS 的新叫法

# 3. 更新 locateValues.json
python3 -c "
import json
p = 'vendor-replica/app/src/main/assets/locateValues.json'
m = json.load(open(p))
m['MIUI_APP_POWER_CONSUME_TEXT'] = '耗电情况'
with open(p, 'w') as f:
    json.dump(m, f, ensure_ascii=False, indent=2)
"

# 4. 更新 JUnit canary test 里如果硬编码了旧值（通常是 canaryMiuiKey）
grep -n '应用耗电' vendor-replica/app/src/test/java/com/guard/wallet/utils/LocateValuesAssetTest.java
# 如果有命中，手动改成 "耗电情况"

# 5. 重跑单元测试确认 green
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
cd vendor-replica && ./gradlew :app:testDebugUnitTest --rerun-tasks

# 6. 重跑真机验证确认命中率提升
cd .. && bash vendor-replica/scripts/real-device-verify/verify-xiaomi-13.sh
# 期望: MIUI_ 命中率 100%
```

## 常见故障排查

### `Plan A not merged yet: LocateValuesSeeder.java missing`

Plan A 的 4 个 commit 还没全部合并。检查：
```bash
git log --oneline --all | grep LocateValuesSeeder
```
应看到至少 4 个 commit。否则等另一个会话完成 plan 执行。

### `APK 未打包 locateValues.json`

assets 目录配置错了。检查 `vendor-replica/app/build.gradle` 里有没有 `android.sourceSets.main.assets.srcDirs += ['src/main/assets']` — 默认是有的，不需要手动加。如果出现这个问题说明被改过，先恢复。

### `设备不在线`

- 确认设备 IP 正确（物理设备开了 WiFi ADB，端口 5555）
- 在另一个终端 `adb connect <ip>:5555` 手动连接，看返回值
- 如果是 HarmonyOS，可能需要在设备上重新勾选"无线调试"

### `am start ... 失败`

- 厂商的 Activity 被标记 `android:exported="false"` 禁止外部 am start。脚本会自动 fallback 到 `APPLICATION_DETAILS_SETTINGS`，能抓到应用详情页的 UI dump 就够用
- 如果连应用详情页都打不开，说明 App 卸载后没重装成功 — 重跑脚本的 step 4（install）

### `uiautomator dump` 失败

- 某些时刻屏幕正在转场（screen fade），uiautomator 拒绝 dump。脚本自动重试 1 次
- 如果还是失败，手动 `adb shell "uiautomator dump /sdcard/ui.xml"` 看错误信息

### `compare.py` 报 `XML ParseError`

- ui.xml 文件损坏（通常是 dump 过程中被截断）。手动删除 `reports/<label>.ui.xml` 后重跑

## 退出码

| 退出码 | 含义 |
|---|---|
| 0 | 一切正常（或 verify-all 里所有设备都 PASS） |
| 1 | verify-all 里至少一台 FAIL（但其他设备仍继续跑完） |
| 2 | 前置检查失败：ADB 路径错、缺少 python3/unzip |
| 3 | 前置检查失败：Plan A 没合并，或 `locateValues.json` 不存在 |
| 4 | APK 打包失败 |
| 10 | 设备连接失败 |
| 11 | APK 安装失败 |

## 与 LocateValuesAssetTest 的关系

本脚本**补充**而不是**替代** `LocateValuesAssetTest`：

| 维度 | `LocateValuesAssetTest` (JVM) | 本脚本 (真机) |
|---|---|---|
| 运行环境 | 本地 JVM（CI 友好） | 真实 Android 设备 |
| 验证对象 | `assets/locateValues.json` 自身结构 + 80 key 存在性 | 文本在实际 ROM UI 上是否真的出现 |
| 运行时间 | 毫秒级 | 每台 ~1 分钟 |
| 失败场景 | JSON 结构错 / key 丢失 | ROM 文案与 JSON 不一致 |
| 必要性 | **每次 commit 必跑** | **每次修改 `locateValues.json` 后跑一次** |

两者都 green 才能说 "UI 自动化真机可用"。
