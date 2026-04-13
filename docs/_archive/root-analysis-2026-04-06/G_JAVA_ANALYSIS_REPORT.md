# 完整分析报告：com.guard.wallet.utils.g.java (6524行，129个PUBLIC STATIC方法)

## 执行摘要

- **源文件**: `/home/code/php/project/full-package/androidReverseEngineering/src/com/guard/wallet/utils/g.java`
- **总行数**: 6524行
- **方法总数**: 129个public static方法
- **已实现方法（vendor-replica）**: 12个 (9.3%)
  - 已实现: `O0`, `S`, `V`, `V0`, `Y0`, `Z`, `d0`, `d1`, `i0`, `j`, `n1`, `z0`
- **待实现方法**: 117个 (90.7%)

---

## 功能分组统计

| 功能域 | 方法数 | 百分比 | 行数范围 |
|------|------|------|--------|
| ADB/Debug设置 | 3 | 2.3% | 825-894 |
| Activity启动 | 3 | 2.3% | 198-5861 |
| 应用信息(PackageManager) | 5 | 3.9% | 1940-6071 |
| 位图/图像操作 | 4 | 3.1% | 888-6396 |
| 证书/TLS/加密 | 5 | 3.9% | 805-6317 |
| 上下文/应用管理 | 12 | 9.3% | 909-6405 |
| 设备管理员/硬件 | 14 | 10.8% | 447-4850 |
| 设备信息(亮度/超时/通话) | 8 | 6.2% | 1426-4603 |
| 文件/路径操作 | 6 | 4.6% | 150-6339 |
| 手势/触摸自动化 | 8 | 6.2% | 679-5817 |
| 网络/WiFi状态 | 2 | 1.5% | 6416-6479 |
| 权限/辅助功能管理 | 27 | 20.9% | 384-6021 |
| SMS/媒体操作 | 5 | 3.9% | 150-705 |
| UI自动化(节点/过滤器) | 17 | 13.2% | 489-6409 |
| 屏幕解锁/锁定(PIN/图案) | 6 | 4.6% | 344-5244 |
| 图案/手势识别 | 2 | 1.5% | 517-1070 |
| 其他/杂项 | 2 | 1.5% | 1492-5876 |

**总计**: 129个方法 (100%)

---

## 详细分组清单

### 1. ADB/Debug设置 (3个方法)

| # | 方法名 | 行号 | 返回类型 | 参数 | 说明 |
|---|------|------|--------|------|------|
| 1 | I | 825 | boolean | - | 检查ADB是否启用 |
| 2 | J | 869 | boolean | - | 检查是否启用无线调试 |
| 3 | K | 894 | boolean | - | 检查是否启用开发者设置 |

---

### 2. Activity启动 (3个方法)

| # | 方法名 | 行号 | 返回类型 | 参数 | 说明 |
|---|------|------|--------|------|------|
| 1 | A0 | 198 | Intent | String var0, String var1 | 通过包名和类名构建Intent |
| 2 | u0 | 5861 | Intent | String var0 | 为包创建启动Intent |
| 3 | d1 | 3374 | boolean | String var0, String var1 | 通过包/类启动Activity |

**状态**: d1 ✓已实现

---

### 3. 应用信息(PackageManager) (5个方法)

| # | 方法名 | 行号 | 返回类型 | 参数 | 说明 |
|---|------|------|--------|------|------|
| 1 | W | 1940 | AppInfo | PackageManager var0, ApplicationInfo var1 | 从PackageManager构建AppInfo |
| 2 | d0 | 3360 | AppInfo | String var0 | 通过包名获取应用信息 |
| 3 | g0 | 3714 | PermissionInfoVO | String var0 | 通过包名获取权限信息 |
| 4 | h0 | 4076 | PermissionsBodyVO | String var0 | 获取所有应用权限 |
| 5 | w0 | 6071 | LinkedList | - | 获取所有已安装包 |

**状态**: d0 ✓已实现

---

### 4. 位图/图像操作 (4个方法)

| # | 方法名 | 行号 | 返回类型 | 参数 | 说明 |
|---|------|------|--------|------|------|
| 1 | J0 | 888 | void | Bitmap var0 | 安全回收bitmap |
| 2 | M0 | 1098 | byte[] | Bitmap var0, float var1, int var2 | 将bitmap压缩为字节 |
| 3 | k0 | 4414 | Bitmap | Bitmap var0, double var1 | 按大小缩放bitmap |
| 4 | y | 6396 | Bitmap | Bitmap var0 | 转换bitmap格式 |

---

### 5. 证书/TLS/加密 (5个方法)

| # | 方法名 | 行号 | 返回类型 | 参数 | 说明 |
|---|------|------|--------|------|------|
| 1 | H0 | 805 | Certificate | - | 从文件加载X.509证书 |
| 2 | I0 | 844 | PrivateKey | - | 从文件加载RSA私钥 |
| 3 | O | 1281 | boolean | DeviceCipherStateVO var0 | 设备加密操作 |
| 4 | R0 | 1612 | boolean | String var0, String var1, String var2, String var3 | TLS证书操作 |
| 5 | w1 | 6317 | File | X509CertImpl var0 | 导出证书到文件 |

---

### 6. 上下文/应用管理 (12个方法)

| # | 方法名 | 行号 | 返回类型 | 参数 | 说明 |
|---|------|------|--------|------|------|
| 1 | Z | 2393 | Context | - | 获取全局应用上下文 |
| 2 | Z0 | 2402 | boolean | String var0 | 启动应用详情设置 |
| 3 | a0 | 3040 | String | Context var0 | 获取设备标识符(android_id) |
| 4 | a1 | 3111 | boolean | String var0 | 检查应用是否已安装 |
| 5 | b0 | 3157 | String | - | 获取Android版本字符串 |
| 6 | c0 | 3254 | String | Context var0 | 获取设备标识符(device_id) |
| 7 | e | 3407 | String | - | 获取制造商字符串 |
| 8 | i0 | 4243 | String | - | 获取应用内部文件目录 |
| 9 | K0 | 909 | boolean | String var0 | 检查应用是否为SMS发送方 |
| 10 | V | 1904 | Drawable | String var0 | 通过包名获取应用图标 |
| 11 | x0 | 6355 | String | - | 获取应用版本信息 |
| 12 | y0 | 6405 | String | - | 获取应用版本字符串 |

**状态**: Z ✓已实现, i0 ✓已实现

---

### 7. 设备管理员/硬件 (14个方法)

| # | 方法名 | 行号 | 返回类型 | 参数 | 说明 |
|---|------|------|--------|------|------|
| 1 | C0 | 447 | DeviceAdminVO | - | 获取设备管理员状态 |
| 2 | D | 469 | void | - | 禁用ADB安装确认 |
| 3 | j | 4317 | boolean | - | 检查是否为设备管理员 |
| 4 | j0 | 4321 | boolean | - | 检查设备是否有硬件 |
| 5 | j1 | 4341 | void | - | 执行设备管理员回调 |
| 6 | k | 4400 | boolean | - | 检查是否有硬件键盘 |
| 7 | k1 | 4439 | void | - | 请求设备管理员权限 |
| 8 | l | 4499 | boolean | - | 检查硬件键盘是否可用 |
| 9 | l0 | 4510 | boolean | - | 检查硬件功能是否可用 |
| 10 | l1 | 4528 | void | - | 请求硬件功能 |
| 11 | m1 | 4702 | void | - | 请求电话功能访问 |
| 12 | o | 4821 | boolean | - | 检查电话状态信息是否可用 |
| 13 | o0 | 4839 | boolean | - | 检查电话状态信息是否启用 |
| 14 | o1 | 4850 | boolean | List var0 | 检查电话状态列表是否有效 |

**状态**: j ✓已实现

---

### 8. 设备信息(亮度/超时/通话) (8个方法)

| # | 方法名 | 行号 | 返回类型 | 参数 | 说明 |
|---|------|------|--------|------|------|
| 1 | O0 | 1426 | int | - | 获取屏幕亮度值 |
| 2 | T0 | 1775 | void | int var0 | 通过RPC设置屏幕亮度 |
| 3 | g | 3678 | CallStateVO | - | 获取通话状态(响铃/空闲) |
| 4 | g1 | 4042 | boolean | - | 检查电话服务是否可用 |
| 5 | m | 4585 | boolean | - | 检查电话服务是否可用 |
| 6 | m0 | 4603 | boolean | - | 检查电话服务是否启用 |
| 7 | n | 4759 | boolean | - | 检查是否有电话状态权限 |
| 8 | n0 | 4773 | boolean | - | 检查电话状态是否可用 |

**状态**: O0 ✓已实现

---

### 9. 文件/路径操作 (6个方法)

| # | 方法名 | 行号 | 返回类型 | 参数 | 说明 |
|---|------|------|--------|------|------|
| 1 | U | 1812 | byte[] | String var0 | 读取文件/URI内容为字节 |
| 2 | Y | 2184 | byte[] | String var0 | 读取文件/URI内容为字节(替代) |
| 3 | B | 321 | boolean | String var0, String var1 | 通过URI删除相册媒体 |
| 4 | N0 | 1234 | String | String var0 | 获取文件/URI字符串表示 |
| 5 | S0 | 1694 | boolean | - | 检查ContentResolver是否可用 |
| 6 | x | 6339 | boolean | - | 检查文件是否可用 |

---

### 10. 手势/触摸自动化 (8个方法)

| # | 方法名 | 行号 | 返回类型 | 参数 | 说明 |
|---|------|------|--------|------|------|
| 1 | S | 1648 | boolean | Long var0, Long var1, Point... var2 | 分发多点手势 |
| 2 | G0 | 700 | boolean | Integer var0, Integer var1, Long var2 | 单点手势分发 |
| 3 | F0 | 679 | boolean | int var0 | 执行全局辅助功能操作 |
| 4 | a | 2446 | boolean | GlobalActionCondition var0 | 执行全局辅助功能操作 |
| 5 | r | 5666 | boolean | - | 检查是否应该执行手势 |
| 6 | r0 | 5678 | boolean | - | 检查手势是否启用 |
| 7 | t0 | 5817 | void | boolean var0 | 启用/禁用手势执行 |
| 8 | u | 5844 | boolean | - | 检查手势执行是否启用 |

**状态**: S ✓已实现

---

### 11. 网络/WiFi状态 (2个方法)

| # | 方法名 | 行号 | 返回类型 | 参数 | 说明 |
|---|------|------|--------|------|------|
| 1 | z | 6416 | WIFIState | Context var0 | 获取WiFi连接状态 |
| 2 | z0 | 6479 | NetStateVO | - | 获取完整网络状态 |

**状态**: z0 ✓已实现

---

### 12. 权限/辅助功能管理 (27个方法)

| # | 方法名 | 行号 | 返回类型 | 参数 | 说明 |
|---|------|------|--------|------|------|
| 1 | C | 384 | boolean | - | 禁用辅助功能服务 |
| 2 | R | 1525 | boolean | - | 检查辅助功能服务权限是否已授予 |
| 3 | X | 2074 | LinkedList | - | 获取启用的辅助功能服务列表 |
| 4 | X0 | 2166 | boolean | - | 检查辅助功能服务是否绑定 |
| 5 | L | 1001 | boolean | - | 检查辅助功能服务是否启用和卸载 |
| 6 | f1 | 3659 | boolean | - | 检查辅助功能服务是否可用 |
| 7 | e0 | 3419 | LinkedList | - | 获取禁用的辅助功能服务 |
| 8 | f0 | 3645 | LinkedList | - | 获取启用的辅助功能服务(替代) |
| 9 | e1 | 3574 | void | - | 请求辅助功能权限 |
| 10 | i1 | 4259 | void | - | 请求辅助功能权限(替代) |
| 11 | d | 3343 | void | - | 清除辅助功能数据 |
| 12 | w | 6021 | void | - | 启用辅助功能服务 |
| 13 | i | 4225 | boolean | - | 检查辅助功能按钮是否可用 |
| 14 | q | 5210 | boolean | - | 检查辅助功能服务是否有效 |
| 15 | q0 | 5228 | LinkedHashSet | - | 获取所有启用的辅助功能服务 |
| 16 | b | 3138 | boolean | - | 检查WRITE_EXTERNAL_STORAGE权限 |
| 17 | c | 3236 | boolean | - | 检查READ_EXTERNAL_STORAGE权限 |
| 18 | b1 | 3178 | void | - | 请求WRITE_EXTERNAL_STORAGE权限 |
| 19 | c1 | 3281 | void | - | 请求READ_EXTERNAL_STORAGE权限 |
| 20 | h | 4061 | boolean | - | 检查文件读取是否可用 |
| 21 | h1 | 4167 | void | - | 请求文件读取权限 |
| 22 | f | 3631 | boolean | String var0 | 检查应用是否有SMS权限 |
| 23 | p | 4890 | boolean | - | 检查是否应该读取SMS |
| 24 | p0 | 4904 | boolean | - | 检查SMS读取是否启用 |
| 25 | n1 | 4802 | boolean | - | 请求电话状态权限 |
| 26 | Y0 | 2189 | boolean | String var0, String var1 | 检查权限授予状态 |
| 27 | V0 | 1916 | boolean | - | 检查是否需要主屏幕解锁 |

**状态**: Y0 ✓已实现, V0 ✓已实现

---

### 13. SMS/媒体操作 (5个方法)

| # | 方法名 | 行号 | 返回类型 | 参数 | 说明 |
|---|------|------|--------|------|------|
| 1 | A | 150 | void | String var0 | 通过电话号码删除SMS |
| 2 | E | 500 | int | String var0 | 按电话号码计算SMS消息数 |
| 3 | F | 535 | int | List var0 | 计算SMS消息列表 |
| 4 | G | 683 | int | String var0 | 按电话号码计算窗口事件数 |
| 5 | H | 705 | int | List var0 | 计算窗口事件列表 |

---

### 14. UI自动化(节点/过滤器) (17个方法)

| # | 方法名 | 行号 | 返回类型 | 参数 | 说明 |
|---|------|------|--------|------|------|
| 1 | M | 1085 | void | UiObject var0 | 对UI节点执行回车键 |
| 2 | N | 1177 | void | UiObject var0 | 对UI节点执行返回/转义键 |
| 3 | P | 1444 | void | - | 执行点击辅助功能操作 |
| 4 | Q | 1477 | void | - | 执行按住点击辅助功能操作 |
| 5 | W0 | 2011 | void | - | 执行主页辅助功能操作 |
| 6 | D0 | 489 | CombineFilter | - | 为数字描述创建过滤器 |
| 7 | r1 | 5694 | CombineFilter | - | 为点坐标创建过滤器 |
| 8 | s1 | 5732 | CombineFilter | - | 创建替代点过滤器 |
| 9 | t1 | 5833 | CombineFilter | - | 创建第三点过滤器 |
| 10 | v | 5907 | CombineFilter | - | 创建第四点过滤器 |
| 11 | y1 | 6409 | CombineFilter | - | 创建第五点过滤器 |
| 12 | s | 5705 | boolean | Integer var0, Integer var1 | 检查整数范围条件 |
| 13 | s0 | 5709 | boolean | String var0 | 检查字符串条件 |
| 14 | t | 5744 | boolean | List var0 | 检查列表条件是否有效 |
| 15 | v0 | 5919 | String | String var0, String var1, String var2 | 构建约束字符串 |
| 16 | v1 | 6006 | boolean | int var0 | 检查约束是否有效 |
| 17 | x1 | 6377 | boolean | Long var0 | 检查长整数条件是否有效 |

---

### 15. 屏幕解锁/锁定(PIN/图案) (6个方法)

| # | 方法名 | 行号 | 返回类型 | 参数 | 说明 |
|---|------|------|--------|------|------|
| 1 | B0 | 344 | LockPatternVO | - | 获取锁定图案和屏幕保护状态 |
| 2 | T | 1726 | boolean | - | 检查设备是否解锁 |
| 3 | U0 | 1894 | void | - | 执行辅助功能解锁操作 |
| 4 | p1 | 4908 | boolean | ReqUnlockDeviceVO param0 | 处理设备解锁请求 |
| 5 | q1 | 5244 | boolean | ReqUnlockDeviceVO var0 | 验证设备解锁请求 |

---

### 16. 图案/手势识别 (2个方法)

| # | 方法名 | 行号 | 返回类型 | 参数 | 说明 |
|---|------|------|--------|------|------|
| 1 | E0 | 517 | String | o0.h var0, ArrayList var1 | 构建点阵图案字符串 |
| 2 | L0 | 1070 | String | u var0 | 获取手势图案字符串 |

---

### 17. 其他/杂项 (2个方法)

| # | 方法名 | 行号 | 返回类型 | 参数 | 说明 |
|---|------|------|--------|------|------|
| 1 | Q0 | 1492 | boolean | - | 检查是否应该禁用应用 |
| 2 | u1 | 5876 | boolean | - | 检查是否处于执行模式 |

---

## 已实现方法对比（vendor-replica中）

### 已实现: 12个方法 (9.3%)

| # | 方法名 | 所属分类 | 行号 | 返回类型 | 功能说明 |
|---|------|--------|------|---------|---------|
| 1 | **Z** | 上下文/应用管理 | 2393 | Context | 获取全局应用上下文 |
| 2 | **i0** | 上下文/应用管理 | 4243 | String | 获取应用内部文件目录 |
| 3 | **d0** | 应用信息(PackageManager) | 3360 | AppInfo | 通过包名获取应用信息 |
| 4 | **d1** | Activity启动 | 3374 | boolean | 通过包/类启动Activity |
| 5 | **j** | 设备管理员/硬件 | 4317 | boolean | 检查是否为设备管理员 |
| 6 | **n1** | 权限/辅助功能管理 | 4802 | boolean | 请求电话状态权限 |
| 7 | **O0** | 设备信息 | 1426 | int | 获取屏幕亮度值 |
| 8 | **S** | 手势/触摸自动化 | 1648 | boolean | 分发多点手势 |
| 9 | **V** | 上下文/应用管理 | 1904 | Drawable | 通过包名获取应用图标 |
| 10 | **V0** | 权限/辅助功能管理 | 1916 | boolean | 检查是否需要主屏幕解锁 |
| 11 | **Y0** | 权限/辅助功能管理 | 2189 | boolean | 检查权限授予状态 |
| 12 | **z0** | 网络/WiFi状态 | 6479 | NetStateVO | 获取完整网络状态 |

### 待实现: 117个方法 (90.7%)

**优先级建议**:
1. **高优先级**（核心功能，被广泛调用）:
   - 权限/辅助功能管理: C, R, X, X0, L, f1, e0, f0, e1, i1, d, w, i, q, q0 (15个)
   - 屏幕解锁: B0, T, U0, p1, q1 (5个)
   - UI自动化: M, N, P, Q, W0, D0 (6个)

2. **中优先级**（设备配置和状态查询）:
   - 设备管理员/硬件: C0, D, j0, j1, k, k1, l, l0, l1, m1, o, o0, o1 (13个)
   - 设备信息: T0, g, g1, m, m0, n, n0 (7个)

3. **低优先级**（特定功能）:
   - 证书/TLS: H0, I0, O, R0, w1 (5个)
   - SMS/媒体: A, E, F, G, H (5个)
   - 位图: J0, M0, k0, y (4个)
   - 文件操作: U, Y, B, N0, S0, x (6个)

---

## 拆分建议 (重构方案)

### 当前问题
- **单一职责违反**: 一个工具类包含6个完全独立的功能域
- **耦合度高**: 许多权限和系统调用混淆在一起
- **可测试性差**: 需要Context、AccessibilityService等全局依赖
- **维护困难**: 129个方法难以追踪和管理

### 推荐拆分架构

```
utils/
├── g.java                          [核心基础, 保留]
│   ├── Context Z()
│   ├── String i0()
│   └── ...其他基础方法
│
├── context/
│   ├── ContextProvider.java        [替代部分Z()调用]
│   ├── ApplicationInfoUtils.java    [d0, V, W, a1, K0]
│   ├── VersionUtils.java            [b0, e, x0, y0]
│   └── IdentifierUtils.java         [a0, c0]
│
├── permission/
│   ├── PermissionChecker.java       [R, Y0, b, c, f, h, p, n1]
│   ├── AccessibilityServiceManager.java [C, X, X0, L, f0, e0, e1, i1, d, w, i, q, q0]
│   └── PermissionRequestor.java     [b1, c1, h1]
│
├── device/
│   ├── DeviceAdminManager.java      [C0, D, j, j0, j1, k1]
│   ├── HardwareManager.java         [k, l, l0, l1]
│   ├── TelephonyManager.java        [g, g1, m, m0, n, n0, o, o0, o1, m1]
│   ├── BrightnessManager.java       [O0, T0]
│   └── AdbManager.java              [I, J, K]
│
├── unlock/
│   ├── UnlockStateManager.java      [B0, T, V0]
│   ├── UnlockExecutor.java          [U0, p1, q1]
│   └── PatternRecognizer.java       [E0, L0]
│
├── gesture/
│   ├── GestureDispatcher.java       [S, G0, F0, a, t0, u, r, r0]
│   └── GestureValidator.java        [E, F, G, H]
│
├── ui/
│   ├── UiNodeOperator.java          [M, N, P, Q, W0]
│   ├── UiFilterBuilder.java         [D0, r1, s1, t1, v, y1, R0]
│   └── ConstraintChecker.java       [s, s0, t, v0, v1, x1]
│
├── media/
│   ├── SmsManager.java              [A, E, F]
│   ├── MediaManager.java            [B, N0]
│   └── BitmapUtils.java             [J0, M0, k0, y]
│
├── file/
│   ├── FileOperations.java          [U, Y, S0, x]
│   └── CertificateManager.java      [H0, I0, O, R0, w1]
│
└── network/
    ├── WifiStateManager.java        [z]
    └── NetworkStateManager.java     [z0]
```

### 拆分收益
- ✅ 每个类职责单一（SRP）
- ✅ 易于测试和模拟
- ✅ 便于代码复用
- ✅ 清晰的依赖关系
- ✅ 更好的可维护性

---

## 总结

**com.guard.wallet.utils.g** 是一个典型的"上帝类"反模式，包含129个公共静态方法，覆盖14个不同的功能域。当前仅有12个方法在vendor-replica中实现（9.3%）。

### 关键指标
- **总方法数**: 129
- **已实现**: 12 (9.3%)
- **待实现**: 117 (90.7%)
- **功能域**: 17个
- **最大功能域**: 权限/辅助功能管理 (27个方法，20.9%)
- **代码质量问题**: 高耦合、低内聚、难于测试

### 行动方案
1. ✅ 优先实现权限和辅助功能管理相关的27个方法
2. ✅ 然后实现屏幕解锁和UI自动化相关的方法
3. ✅ 同步实现拆分重构，降低代码复杂度
4. ⏳ 为每个新类添加完整的单元测试覆盖

